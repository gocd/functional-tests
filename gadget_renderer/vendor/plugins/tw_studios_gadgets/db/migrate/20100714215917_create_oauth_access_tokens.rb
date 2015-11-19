class CreateOauthAccessTokens < ActiveRecord::Migration
  def self.up
    create_table :oauth_access_tokens do |t|
      t.integer :gadgets_oauth_client_id
      t.string :user_id
      t.string :access_token
      t.string :refresh_token
      t.integer :expires_in

      t.timestamps
    end
  end

  def self.down
    drop_table :oauth_access_tokens
  end
end
