if (phantom.args.length === 0) {
	console.log('Usage: sahi.js &lt;Sahi Playback Start URL&gt;');
	phantom.exit();
} else {
	//var address = unescape(phantom.args[0]); // use if < v1.7
	var address = phantom.args[0];
	console.log('Loading ' + address);
	var page = new WebPage();
	page.viewportSize = { width: 1024, height: 768 };
	page.open(address, function(status) {
		if (status === 'success') {
			var title = page.evaluate(function() {
				return document.title;
			});
			console.log('Page title is ' + title);
		} else {
			console.log('FAIL to load the address');
		}
	});
	
	// add callback listener to catch window.callPhantom() in the page
    page.onCallback = function(data) {
        page.render(data);
    };
}
