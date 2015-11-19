ActionController::Routing::Routes.draw do |map|
  map.connect "/gadgets/oauthcallback", :controller => "gadget_oauth_callback", :action => 'oauth_callback'

  map.gadget_rendering "/gadgets/ifr", :controller => "gadget_rendering", :action => 'ifr'
  map.connect "/gadgets/makeRequest", :controller => "gadget_make_request", :action => 'make_request'
  map.connect "/gadgets/proxy", :controller => "gadget_proxy", :action => 'proxy'
  map.connect "/gadgets/concat", :controller => "gadget_proxy", :action => 'concat'
  map.connect "/gadgets/js/:features.js", :controller => 'gadget_js_request', :action => 'js'
  
  map.resources :gadgets_oauth_clients, :as => 'gadgets/oauth_clients'
end
