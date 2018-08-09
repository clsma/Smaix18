_.templateSettings = {
	interpolate: /\<\#\=(.+?)\#\>/gim,
	evaluate: /\<\#([\s\S]+?)\#\>/gim,
	escape: /\<\#\-(.+?)\#\>/gim
};

$(function(){
	var scripts = document.getElementsByTagName("script");
	for( var i=0 ; i<scripts.length; i++ ){
		var script = scripts[i];
		var type = script.type;
		if( type=="text/html" ){
			var html = script.innerHTML;
			var temp = _.template(html);
			window[script.id] = temp;
		}
	}
});