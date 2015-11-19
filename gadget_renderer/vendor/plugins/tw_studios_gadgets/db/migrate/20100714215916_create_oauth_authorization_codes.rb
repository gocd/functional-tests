class CreateOauthAuthorizationCodes < ActiveRecord::Migration
  def self.up
    create_table :oauth_authorization_codes do |t|
      t.integer :gadgets_oauth_client_id
      t.string :user_id
      t.string :code
      t.integer :expires_in

      t.timestamps
    end
  end

  def self.down
    drop_table :oauth_authorization_codes
  end
end
