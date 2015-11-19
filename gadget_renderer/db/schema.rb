##########################GO-LICENSE-START################################
# Copyright 2015 ThoughtWorks, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##########################GO-LICENSE-END##################################

# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20100715001151) do

  create_table "access_tokens", :force => true do |t|
    t.string   "access_token"
    t.integer  "user_id"
    t.integer  "bank_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "accounts", :force => true do |t|
    t.integer  "user_id"
    t.integer  "bank_id"
    t.string   "account_type"
    t.string   "account_number"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "authorization_codes", :force => true do |t|
    t.integer  "user_id"
    t.string   "code"
    t.integer  "bank_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "banks", :force => true do |t|
    t.string   "name"
    t.string   "url"
    t.string   "client_id"
    t.string   "client_secret"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "gadgets_oauth_clients", :force => true do |t|
    t.string   "gadget_url"
    t.string   "client_id"
    t.string   "client_secret"
    t.string   "service_name"
    t.string   "redirect_uri"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "oauth_access_tokens", :force => true do |t|
    t.integer  "gadgets_oauth_client_id"
    t.string   "user_id"
    t.string   "access_token"
    t.string   "refresh_token"
    t.integer  "expires_in"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "oauth_authorization_codes", :force => true do |t|
    t.integer  "gadgets_oauth_client_id"
    t.string   "user_id"
    t.string   "code"
    t.integer  "expires_in"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", :force => true do |t|
    t.string   "email"
    t.string   "password"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
