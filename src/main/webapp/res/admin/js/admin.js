Cms={};
Cms.lmenu = function(id) {
	var m = $('#' + id);
	m.addClass('lmenu');
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("lmenu-focus");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("lmenu-focus");
			}
			m.focusElement=this;
		});
	});	
}

$.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

$.extend(
 {
	 doActById:function(self,url,id){
		 if(preParams!=undefined && typeof preParams == "function"){
			 preParams();
		 }	
		 $("#hidFields").before("<input type=\"hidden\" name=\"idparam\" value="+id+" />");
		 var tf = document.getElementById("tableForm");
		 tf.action=url;
		 tf.submit();
	 },
	 
 }		
);

$.cascade = {
	init:function(s){
		var settings = {
			src:"",
			dst:"",
			dsturl:"",
			hasblank:true
		};
		$.extend(settings,s);
		var getchildren = function(idnum){
			var id=idnum;
			if(id==0 || id=="0"){
				$("#"+settings.dst+" option").remove();	
				var c2 = $("#"+settings.dst);
				var c2ddl = c2[0];
				options = c2ddl.options;
				options.add(new Option("","0"));
				return;
			}
			$.ajax({
				type : "POST",
				url : settings.dsturl,
				data:{"id":id,"hasblank":settings.hasblank},
				dataType : "json",
				timeout : 120000,
				beforeSend : function() {
				},
				success : function(data) {
					$("#"+settings.dst+" option").remove();	
					var c2 = $("#"+settings.dst);
					var c2ddl = c2[0];
					options = c2ddl.options;
					$.each(data,function(i){
						var vv=data[i];
						options.add(new Option(vv.name,vv.id));
					});
				},
				error : function() {
				}
			});
		};
		$("#"+settings.src).click(function(){
			var id=$(this).val();
			getchildren(id);
		});
	}	
		
};