module Shindig
  class Oauth2TokenStore
    def find_oauth_client(gadget_url, service_name)
      GadgetsOauthClient.find_by_gadget_url_and_service_name(gadget_url, service_name)
    end
    
    def find_access_token(oauth_client, user_id)
      oauth_client.oauth_access_tokens.find_by_user_id(user_id)
    end
    
    def find_authorization_code(oauth_client, user_id)
      oauth_client.oauth_authorization_codes.find_by_user_id(user_id)
    end
    
    def save_access_token(oauth_client, user_id, access_token, expires_in, refresh_token)
      if old_token = find_access_token(oauth_client, user_id)
        old_token.delete
      end
      
      oauth_client.oauth_access_tokens.create!(:user_id => user_id, 
        :access_token => access_token,
        :expires_in => expires_in,
        :refresh_token => refresh_token
      )
    end
    
    def save_authorization_code(oauth_client, user_id, code, expires_in)
      delete_authorization_code(oauth_client, user_id)
      oauth_client.oauth_authorization_codes.create(:user_id => user_id, 
        :code => code, 
        :expires_in => expires_in
      )
    end
    
    def delete_authorization_code(oauth_client, user_id)
      if authorizaiton_code = find_authorization_code(oauth_client, user_id)
        authorizaiton_code.delete
      end
    end
  end
end