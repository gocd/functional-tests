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

class BanksController < ApplicationController
  # GET /banks
  # GET /banks.xml
  def index
    @banks = Bank.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @banks }
    end
  end

  # GET /banks/1
  # GET /banks/1.xml
  def show
    @bank = Bank.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @bank }
    end
  end

  # GET /banks/new
  # GET /banks/new.xml
  def new
    @bank = Bank.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @bank }
    end
  end

  # GET /banks/1/edit
  def edit
    @bank = Bank.find(params[:id])
  end

  # POST /banks
  # POST /banks.xml
  def create
    @bank = Bank.new(params[:bank])

    respond_to do |format|
      if @bank.save
        flash[:notice] = 'Bank was successfully created.'
        format.html { redirect_to(@bank) }
        format.xml  { render :xml => @bank, :status => :created, :location => @bank }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @bank.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /banks/1
  # PUT /banks/1.xml
  def update
    @bank = Bank.find(params[:id])

    respond_to do |format|
      if @bank.update_attributes(params[:bank])
        flash[:notice] = 'Bank was successfully updated.'
        format.html { redirect_to(@bank) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @bank.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /banks/1
  # DELETE /banks/1.xml
  def destroy
    @bank = Bank.find(params[:id])
    @bank.destroy

    respond_to do |format|
      format.html { redirect_to(banks_url) }
      format.xml  { head :ok }
    end
  end
end
