function getwin(){
	var p = window.parent;
	var win = window;
	while(p!=undefined && p!=null && p.location.href != win.location.href){
		p=p.parent;
		if(p!=undefined && p!=null && p.location.href != win.location.href)
			win=p;
	}
	return win.location;
}
function getwindow(){
	var p = window.parent;
	var win = window;
	while(p!=undefined && p!=null && p.location.href != win.location.href){
		p=p.parent;
		if(p!=undefined && p!=null && p.location.href != win.location.href)
			win=p;
	}
	return win;
}
function showMsg(msg,cb){
	  using([ 'messager', 'linkbutton', 'dialog' ], function() {
	   	 if(cb!=undefined)
		  $.messager.alert("梵华采购软件消息",msg,"info");
	   	 else{
	   	  $.messager.alert("梵华采购软件消息",msg,"info",cb); 
	   	 }
});}

function confirmex(msg,f,ef){
	  using([ 'messager', 'linkbutton', 'dialog' ], function() {
		  $.messager.confirm('梵华采购软件',msg,function(r){
			  if(r){
				  if(f!=undefined)
					  f();
			  }else{
				  if(ef!=undefined){
					  ef();
				  }
			  }
		  }); 	 
	});
}


$.extend({
	_urlencode : function(a) {
		return encodeURIComponent(a).replace(/!/g, "%21").replace(/'/g, "%27")
				.replace(/\(/g, "%28").replace(/\)/g, "%29").replace(/\*/g,
						"%2A").replace(/%20/g, "+").replace(/%2C/g, ",");
	},
	_urldecode : function(a) {
		return decodeURIComponent(a).replace(/\+/g, " ").replace(/%21/g, "!")
				.replace(/%27/g, "'").replace(/%28/g, "(").replace(/%29/g, ")")
				.replace(/%2A/g, "*");
	}
});
$.ajaxEx = $.ajax;
$.extend({
	ajax : function(ops) {
		var oldOps = ops;
		var options = {};
		$.extend(options, oldOps);
		options.success = function(op) {
			if (typeof op == "object") {
				if (op.type != undefined && op.type == "redirect") {
					getwindow().alert(op.message);
					if(op.url!=""){
						getwin().href = op.url;
					}	
					return;
				}
			}
			if (oldOps.success != undefined)
				oldOps.success(op);
		};
		$.ajaxEx(options);
	}
});