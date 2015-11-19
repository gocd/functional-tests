class GadgetJsRequestController < ApplicationController
  
  def js
    if request.headers['If-Modified-Since']
      render :nothing => true, :status => :not_modified
      return
    end
    
    feature_registry = Shindig::Guice.instance_of(Shindig::FeatureRegistry)
    
    features = feature_registry.getFeatureResources(Shindig::GadgetContext.new, requested_features, nil)
    
    render :content_type => 'text/javascript', :text => proc { |response, output|
      features.each do |feature|
        output.write( debug? ? feature.debug_content : feature.content)
      end
    }
  end
  
  private
  def debug?
    return false if params[:debug].blank?
    params[:debug] != '0'
  end
  
  def requested_features
    params[:features].split(':')
  end
end
