module Shindig
  class Oauth2Request < OAuthRequest
    def initialize(gadget_spec_factory, http_fetcher, oauth_token_store)
      super(nil, nil)
      @gadget_spec_factory = gadget_spec_factory
      @http_fetcher = http_fetcher
      @oauth_token_store = oauth_token_store
    end
    
    def fetch(request)
      security_token = request.security_token
      gadget_url = security_token.app_url
      service_name = request.oauth_arguments.service_name
      
      oauth_service = lookup_oauth_service(security_token, service_name)
      
      unless oauth_service.authorization_url.scheme == 'https'
        return oauth_error_response(403, 'BAD_OAUTH_CONFIGURATION', "authorization url must be a valid https url")
      end
      
      unless oauth_service.access_url.url.scheme == 'https'
        return oauth_error_response(403, 'BAD_OAUTH_CONFIGURATION', "access token url must be a valid https url")
      end
      
      unless request.uri.scheme == 'https'
        return oauth_error_response(403, 'BAD_OAUTH_CONFIGURATION', "resource visited through OAuth2 must be under protection of https")
      end
      
      oauth_client = @oauth_token_store.find_oauth_client(gadget_url, service_name)
      
      unless oauth_client
        return oauth_error_response(403, 'UNKNOWN_PROBLEM', "can not find client info for gadget: #{gadget_url} and service: #{service_name}")
      end
      
      if need_approval?(oauth_client, security_token.viewer_id)
        return oauth_approval_url_response(oauth_client, oauth_service) 
      end
      
      fetch_with_approval(request, oauth_service, oauth_client, security_token.viewer_id)
    end
    
    private
    
    def fetch_with_approval(real_request, oauth_service, oauth_client, user_id)
      if authorization_code = @oauth_token_store.find_authorization_code(oauth_client, user_id)
        response = fetch_access_token(oauth_service, oauth_client, authorization_code)
        return oauth_error_response_from(response) unless response.http_status_code == 200
        store_access_token(response, oauth_client, user_id)
        @oauth_token_store.delete_authorization_code(oauth_client, user_id)
      end
      
      fetch_with_access_token(real_request, oauth_service, oauth_client, user_id)
    end
    
    def fetch_with_access_token(request, oauth_service, oauth_client, user_id)
      access_token = @oauth_token_store.find_access_token(oauth_client, user_id)
      
      request.set_header("Authorization", "Token token=\"#{access_token.access_token}\"")
      response = @http_fetcher.fetch(request)
      response.http_status_code == 401 ? oauth_approval_url_response(oauth_client, oauth_service) : response
    end
    
    def fetch_access_token(oauth_service, oauth_client, authorization_code)
      http_request = HttpRequest.new(oauth_service.access_url.url)
      http_request.set_method("POST")
      http_request.set_header("Content-Type", "application/x-www-form-urlencoded")
      http_request.set_post_body({
        "grant_type" => "authorization-code",
        "code" => authorization_code.code,
        "redirect_uri" => oauth_client.redirect_uri,
        "client_id" => oauth_client.client_id,
        "client_secret" => oauth_client.client_secret
      }.to_query.to_java_bytes)
      @http_fetcher.fetch(http_request)
    end
    
    def store_access_token(access_token_response, oauth_client, user_id)
      attrs = ActiveSupport::JSON.decode(access_token_response.response_as_string)
      @oauth_token_store.save_access_token(oauth_client, user_id, attrs['access_token'], attrs['expires_in'], attrs['refresh_token'])
    end
    
    def oauth_error_response_from(response)
      json = ActiveSupport::JSON.decode(response.response_as_string) || {}
      oauth_error_response(response.http_status_code, json["error"])
    end
    
    def oauth_error_response(status_code, oauth_error, oauth_error_text=nil)
      meta_data_response(status_code, {'oauthError' => oauth_error || 'UNKNOWN_PROBLEM', 'oauthErrorText' => oauth_error_text})
    end
    
    def need_approval?(oauth_client, user_id)
      !@oauth_token_store.find_access_token(oauth_client, user_id) && !@oauth_token_store.find_authorization_code(oauth_client, user_id)
    end
    
    def oauth_approval_url_response(oauth_client, oauth_service)
      meta_data_response(200, { 'oauthApprovalUrl' => build_authorization_url(oauth_client, oauth_service) })
    end
    
    def meta_data_response(status_code, meta_data)
      response_builder = HttpResponseBuilder.new
      response_builder.set_http_status_code(status_code).set_strict_no_cache
      meta_data.each do |key, value|
        response_builder.set_metadata(key, value)
      end
      response_builder.create      
    end
    
    def build_authorization_url(oauth_client, oauth_service)
      oauth_service.authorization_url.to_string + "?" +  
          { "client_id" => oauth_client.client_id, 
            "redirect_uri" => oauth_client.redirect_uri,
            "response_type" => "code",
            "state" => OauthCallbackState.encode(oauth_client)
          }.to_query
    end
    
    def lookup_oauth_service(security_token, using_service_name)
      gadget = @gadget_spec_factory.get_gadget_spec(Oauth2GadgetContext.new(security_token))
      oauth_spec = gadget.module_prefs.oauth_spec
      oauth_spec.services[using_service_name]      
    end
  end
end