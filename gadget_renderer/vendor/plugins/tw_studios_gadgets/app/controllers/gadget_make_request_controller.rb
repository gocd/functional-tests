class GadgetMakeRequestController < ApplicationController
  UNPARSEABLE_CRUFT = "throw 1; < don't be evil' >"

  def make_request
    request_pipeline = Shindig::Guice.instance_of(Shindig::RequestPipeline)
    rewriter_registry = Shindig::Guice.instance_of(Shindig::RequestRewriterRegistry)
    remote_request = build_http_request
    remote_response = rewriter_registry.rewriteHttpResponse(remote_request, request_pipeline.execute(remote_request))
    set_cache_control(remote_response)
    logging_error(remote_response) unless remote_response.http_status_code == 200
    render :text => UNPARSEABLE_CRUFT + convert_to_json(remote_response), :content_type => "text/json"
  end
  
  private
    
  def convert_to_json(remote_response)
    headers = extract_headers(remote_response)
    hash = {}
    hash['rc'] = remote_response.http_status_code
    hash['body'] = remote_response.response_as_string
    hash['headers'] = headers if headers.any?
    remote_response.metadata.each do |key, value|
      hash[key] = value
    end

    { params['url'] => hash }.to_json
  end

  def extract_headers(remote_response)
    headers = {}
    cookies = remote_response.getHeaders("set-cookie")
    location = remote_response.getHeaders("location")
    headers["set-cookie"] = cookies if cookies.any?
    headers["location"] = location if location.any?
    headers
  end

  def build_http_request
    req = Shindig::HttpRequest.new(Shindig::Uri.parse(params["url"]))
    req.set_method params["httpMethod"] || "GET"
    
    header_string = params["headers"] || ""
    CGI.parse(header_string).each do |header_name, header_value|
      unless ["HOST", "ACCEPT-ENCODING"].include?(header_name.upcase)
        req.add_header(header_name, header_value.first)
      end
    end
    
    if params['rewriteMime']
      req.set_rewrite_mime_type(params['rewriteMime'])
    end
    
    if post_to_remote? 
      content_type = req.get_header("Content-Type") || "application/x-www-form-urlencode"
      req.add_header("Content-Type", content_type) 
      req.set_post_body((params["postData"] || "").to_java.bytes)
    end
    
    req.setIgnoreCache(ignore_cache?)
    req.setCacheTtl(params_refresh) if params_refresh
    
    req.setGadget(Shindig::Uri.parse(params[:gadget])) if params[:gadget]
    
    
    if params[:authz] && params[:authz].downcase == 'oauth'
      req.setAuthType(Shindig::AuthType::OAUTH)
      req.setSecurityToken(create_security_token)
      req.setOAuthArguments(Shindig::OAuthArguments.new(Shindig::AuthType::OAUTH, params))
    end
    
    req
  end

  def post_to_remote?
    params["httpMethod"] && params["httpMethod"].upcase == "POST"
  end
  
  def set_cache_control(remote_response)
    if ignore_cache? || remote_response.strict_no_cache?
      expires_now
    else
      expires_in cache_ttl(remote_response), :public => true
    end
  end
  
  def params_refresh
    params[:refresh] =~ /^\d+$/ && params[:refresh].to_i
  end
  
  def cache_ttl(remote_response)
    params_refresh || [1.hour, (remote_response.cache_ttl / 1000)].max
  end
  
  def ignore_cache?
    return false if params[:nocache].blank?
    params[:nocache] != "0"
  end
  
  def create_security_token
    Shindig::BasicSecurityToken.new(current_user_id.to_s,  #owner
                           current_user_id.to_s,           #viewer
                           params[:gadget],      #app
                           "localhost",          #domain
                           params[:gadget],      #app url
                           "0",                  #module id
                           "default",            #container
                           nil)                 #active url
    
  end
  
  def logging_error(remote_response)
      logger.debug { <<-LOGGING }
Remote request failed: 
url: #{params[:url]}
status code: #{remote_response.http_status_code}
metadata: #{remote_response.metadata}
body:
******************************** start of response body *****************************************
#{remote_response.response_as_string}
********************************  end of response body ******************************************
LOGGING
  end
end
