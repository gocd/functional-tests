class CreateGadgetsOauthClients < ActiveRecord::Migration
  def self.up
    create_table :gadgets_oauth_clients do |t|
      t.string :gadget_url
      t.string :client_id
      t.string :client_secret
      t.string :service_name
      t.string :redirect_uri

      t.timestamps
    end
  end

  def self.down
    drop_table :gadgets_oauth_clients
  end
end
