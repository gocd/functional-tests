class GadgetProxyController < ApplicationController
  def proxy
    if request.headers['If-Modified-Since']
      render :nothing => true, :status => :not_modified
      return
    end
    
    remote_response = fetch(params[:url])
    content_type = remote_response.get_header("Content-Type")
    
    render :content_type => content_type, :text => proc { |response, output|
      copy_stream_to(remote_response.response, output)
    }
  end
  
  def concat
    if request.headers['If-Modified-Since']
      render :nothing => true, :status => :not_modified
      return
    end
    render :content_type => params[:rewriteMime], :text => proc { |response, output|
      (1..1000).each do |i|
        break unless params[i.to_s]
        copy_stream_to(fetch(params[i.to_s]).response, output)
      end
    }
    
  end
  
  private

  def fetch(url)
    request_pipeline = Shindig::Guice.instance_of(Shindig::RequestPipeline)
    rewriter_registry = Shindig::Guice.instance_of(Shindig::RequestRewriterRegistry)
    remote_request = build_http_request(url)
    remote_response = rewriter_registry.rewriteHttpResponse(remote_request, request_pipeline.execute(remote_request))
  end
  
  def build_http_request(url)
    remote_request = Shindig::HttpRequest.new(Shindig::Uri.parse(url))
    remote_request.set_method params["httpMethod"] || "GET"
    remote_request
  end
  
  def copy_stream_to(input_stream, ruby_io)
    org.apache.commons.io.IOUtils.copy(input_stream, org.jruby.util.IOOutputStream.new(ruby_io))
  end
end