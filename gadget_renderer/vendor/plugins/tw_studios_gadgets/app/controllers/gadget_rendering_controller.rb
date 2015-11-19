class GadgetRenderingController < ApplicationController

  def ifr
    if request.headers["X-shindig-dos"]
      render :nothing => true, :status => :forbidden
      return
    end

    gadget_context = Shindig::HttpGadgetContext.new(request)
    renderer = Shindig::Guice.instance_of(Shindig::Renderer)
    result = renderer.render(gadget_context)
    
    if result.status.name == "OK"
      gadget_context.getIgnoreCache ? expires_now : expires_in(cache_refresh)
      render :text => result.content
    else
      render :text => org.apache.commons.lang.StringEscapeUtils.escapeHtml(result.getErrorMessage())
    end
  end


  private

  def cache_refresh
    if params[:refresh] && params[:refresh] =~ /^([0-9])+$/
      params[:refresh].to_i
    else
      5.minutes
    end    
  end
  
end
