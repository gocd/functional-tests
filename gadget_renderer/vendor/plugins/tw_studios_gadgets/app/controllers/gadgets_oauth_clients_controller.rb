class GadgetsOauthClientsController < ApplicationController
  # GET /gadgets_oauth_clients
  # GET /gadgets_oauth_clients.xml
  def index
    @gadgets_oauth_clients = GadgetsOauthClient.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @gadgets_oauth_clients }
    end
  end

  # GET /gadgets_oauth_clients/1
  # GET /gadgets_oauth_clients/1.xml
  def show
    @gadgets_oauth_client = GadgetsOauthClient.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @gadgets_oauth_client }
    end
  end

  # GET /gadgets_oauth_clients/new
  # GET /gadgets_oauth_clients/new.xml
  def new
    @gadgets_oauth_client = GadgetsOauthClient.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @gadgets_oauth_client }
    end
  end

  # GET /gadgets_oauth_clients/1/edit
  def edit
    @gadgets_oauth_client = GadgetsOauthClient.find(params[:id])
  end

  # POST /gadgets_oauth_clients
  # POST /gadgets_oauth_clients.xml
  def create
    @gadgets_oauth_client = GadgetsOauthClient.new(params[:gadgets_oauth_client])

    respond_to do |format|
      if @gadgets_oauth_client.save
        flash[:notice] = 'GadgetsOauthClient was successfully created.'
        format.html { redirect_to(@gadgets_oauth_client) }
        format.xml  { render :xml => @gadgets_oauth_client, :status => :created, :location => @gadgets_oauth_client }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @gadgets_oauth_client.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /gadgets_oauth_clients/1
  # PUT /gadgets_oauth_clients/1.xml
  def update
    @gadgets_oauth_client = GadgetsOauthClient.find(params[:id])

    respond_to do |format|
      if @gadgets_oauth_client.update_attributes(params[:gadgets_oauth_client])
        flash[:notice] = 'GadgetsOauthClient was successfully updated.'
        format.html { redirect_to(@gadgets_oauth_client) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @gadgets_oauth_client.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /gadgets_oauth_clients/1
  # DELETE /gadgets_oauth_clients/1.xml
  def destroy
    @gadgets_oauth_client = GadgetsOauthClient.find(params[:id])
    @gadgets_oauth_client.destroy

    respond_to do |format|
      format.html { redirect_to(gadgets_oauth_clients_url) }
      format.xml  { head :ok }
    end
  end
end
