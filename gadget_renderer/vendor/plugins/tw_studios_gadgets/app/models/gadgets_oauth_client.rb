class GadgetsOauthClient < ActiveRecord::Base
  has_many :oauth_authorization_codes
  has_many :oauth_access_tokens
  
  def write_attribute(attribute, value)
    value = value.strip if value.respond_to?(:strip)
    super(attribute, value)
  end
end
