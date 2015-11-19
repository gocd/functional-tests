module Shindig
  module OauthCallbackState
    def self.encode(oauth_client)
      Base64.encode64({'gadget_url' => oauth_client.gadget_url, 'service_name' => oauth_client.service_name }.to_json)
    end
  
    def self.decode(state_str)
      return nil, nil if state_str.blank?
      state = ActiveSupport::JSON::decode(Base64.decode64(state_str))
      return state['gadget_url'], state['service_name']
    end
  end
end