var tw_gadget = function() {
  var lastMoudleId = 0;
  var renderingUrl = null;
  
  function resizeHeight(height) {
    var iframe = document.getElementById(this.f);
    if(iframe) {
      iframe.style.height = height + "px";
    }
  }

  function buildupIframeUrl(moduleId, gadgetUrl, params) {
    var url =  renderingUrl;
    url += "?url=" + escape(gadgetUrl);
    url += "&parent=" + escape(document.location.href)
    url += "&mid=" + escape(moduleId)
    for (var key in params) {
      url += "&" + key + "=" + escape(params[key]);
    }
    return url;
  }

  function iframeId(moduleId) {
    return "gadget_iframe_" + moduleId;
  }

  return {
    init: function(url) {
      renderingUrl = url || '/gadgets/ifr';
      gadgets.rpc.register('resize_iframe', resizeHeight);
    },
 
    addGadget: function(containerId, gadgetUrl, params) {
      params = params || {};
      var container = document.getElementById(containerId);
      var iframe = document.createElement('iframe');
      var moduleId = lastMoudleId++;
      iframe.id = iframeId(moduleId);

      iframe.width = params.width || 200;
      iframe.height = params.height || 200;
      delete params.width;
      delete params.height;

      iframe.src = buildupIframeUrl(moduleId, gadgetUrl, params);
      container.appendChild(iframe);
    }
  }
}();