class GadgetOauthCallbackController < ApplicationController

  def oauth_callback
    if error = params[:error]
      render :text => 'error:' + error, :status => :bad_request
      return
    end

    token_store = Shindig::Oauth2TokenStore.new 
    
    gadget_url, service_name = Shindig::OauthCallbackState.decode(params[:state])

    oauth_client = token_store.find_oauth_client(gadget_url, service_name)
    if oauth_client.blank?
      render :text => 'expect oauth authentication server passing back the state parameter', :status => :bad_request
      return
    end
    

    auth_code = token_store.save_authorization_code(oauth_client, current_user_id, params[:code], params[:expires_in])
    
    
    if auth_code.valid?    
      render :text => <<-HTML
      <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
      <html>
      <head>
      <title>Close this window</title>
      </head>
      <body>
      <script type='text/javascript'>
      try {
        window.opener.gadgets.io.oauthReceivedCallbackUrl_ = document.location.href;
      } catch (e) {
      }
      window.close();
      </script>
      Close this window.
      </body>
      </html>
      HTML
    else
      render :text => auth_code.errors.full_messages.join("\n"), :status => :bad_request
    end
  end
end