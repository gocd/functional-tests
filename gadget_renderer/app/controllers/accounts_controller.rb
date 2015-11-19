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

require 'net/https'

class AccountsController < ApplicationController
  # GET /accounts
  # GET /accounts.xml
  def index
    @accounts = current_user.accounts.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @accounts }
    end
  end

  # GET /accounts/1
  # GET /accounts/1.xml
  def show
    @account = current_user.accounts.find(params[:id])
    load_balance(@account)
    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @account }
    end
  end

  # GET /accounts/new
  # GET /accounts/new.xml
  def new
    @account = current_user.accounts.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @account }
    end
  end

  # GET /accounts/1/edit
  def edit
    @account = current_user.accounts.find(params[:id])
  end

  # POST /accounts
  # POST /accounts.xml
  def create
    @account = current_user.accounts.new(params[:account])

    respond_to do |format|
      if @account.save
        flash[:notice] = 'Account was successfully created.'
        format.html { redirect_to([current_user, @account]) }
        format.xml  { render :xml => @account, :status => :created, :location => @account }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @account.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /accounts/1
  # PUT /accounts/1.xml
  def update
    @account = current_user.accounts.find(params[:id])

    respond_to do |format|
      if @account.update_attributes(params[:account])
        flash[:notice] = 'Account was successfully updated.'
        format.html { redirect_to([current_user, @account]) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @account.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /accounts/1
  # DELETE /accounts/1.xml
  def destroy
    @account = current_user.accounts.find(params[:id])
    @account.destroy

    respond_to do |format|
      format.html { redirect_to(user_accounts_url(current_user)) }
      format.xml  { head :ok }
    end
  end
  
  private
  
  def load_balance(account)
    if access_token = current_user.access_tokens.find_by_bank_id(account.bank_id)
      account.balance = fetch_balance_with_access_token(account, access_token)
      return
    end
    
    authorization_code = current_user.authorization_codes.find_by_bank_id(account.bank_id)
    return unless authorization_code
    access_token = get_access_token(account.bank, authorization_code.code)
    authorization_code.delete
    account.balance = fetch_balance_with_access_token(account, access_token)
  end
  
  def fetch_balance_with_access_token(account, token)
    response = HC.get(account.bank.account_url(account.account_number), 
        :headers => {'Authorization' => %Q{Token token="#{token.access_token}"}}
        )
    Hash.from_xml(response)['account']['balance']
  end
  
  def get_access_token(bank, code)
    uri = URI.parse(bank.token_url)
    response = HC.post(bank.token_url, :params => bank.token_post_body(code))
    json = ActiveSupport::JSON.decode(response)
    current_user.access_tokens.create!(:bank => bank, :access_token => json['access_token'])
  end
  
  
  module HC
    include_package 'org.apache.commons.httpclient.methods'
    include_package 'org.apache.commons.httpclient'

    def self.client
      HttpClient.new
    end
    
    def self.request(method, options={})
      params = options[:params] || {}
      headers = options[:headers] || {}

      headers.each {|k,v| method.set_request_header(k.to_s, v.to_s)}
      params.each {|k,v| method.add_parameter(k.to_s, v.to_s)}
      http_status_code = client.execute_method(method)
      raise "HTTP Exception, the status code was #{http_status_code}" if http_status_code != 200
      method.response_body_as_string
    end
    
    def self.get(url, options={})
      request(GetMethod.new(url), options)
    end
    
    def self.post(url, options={})
      request(PostMethod.new(url), options)
    end
  end
  
end
