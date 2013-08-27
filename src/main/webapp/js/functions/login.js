(function($) {
	var contextpath;
	$.extend({
				initLogin : function(cp) {
					contextpath = cp;
					using(
							[ 'form', 'dialog' ],
							function() {
								$("#loginForm")
										.form(
												{
													url : contextpath
															+ "/loginAjax.do",
													onSubmit : function() {
														var flag = $(this)
																.form(
																		'validate');
														return flag;
													},
													success : function(data) {
														var re = $
																.evalJSON(data);
														if (re.isOK
																&& re.redirectUrl != "") {											
																getwin().href = re.redirectUrl;
														} else if (re.isOK) {
																getwin().href = contextpath + "/admin/index.htm";

														} else {
															$("#showErrMsg")
																	.html(
																			re.message);
														}
													}
												});
							});
				},
				logout2 : function(cp) {
					var url = cp + "/login.htm";
					getwin().href = url;
				}
			});
	
	var uh =window.location.href;
	if(uh.indexOf("login")>-1){
		var p=getwin();
		if(p.href.indexOf("login")==-1 && p.href!=uh){
			alert("已经超时，请重新登录.");
			p.href=uh;
		}
	}	
})(jQuery);