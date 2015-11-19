class OauthAccessToken < ActiveRecord::Base
  belongs_to :gadgets_oauth_client
  validates_uniqueness_of :user_id, :scope => :gadgets_oauth_client_id
  validates_presence_of :access_token, :user_id, :gadgets_oauth_client_id  
end
