(function($) {
	var showteamsinfo=null;
	function initpage() {
		$("#selectTeams").click(function() {
			$('#teamsDlg').window('open');
		});
		$("input[name='mc']", document.body).each(
				function(i) {
					$(this).click(
							function() {
								showteamsinfo(this);
							});
		});		
		$("#tbName").keyup(function(){
			search();
		});
		$("#chkAllInResults").click(function(){
			$("li[v=1]",$("#teamOptions")).each(function(){
				var cbn=$(this).attr("cb");
				var c = $("#"+cbn);
				var ce = c[0];
				if(!ce.checked){
					ce.checked=true;
					showteamsinfo(ce);
				}
			});
		});
		$("#unchkAll").click(function(){
			$("li[v=1]",$("#teamOptions")).each(function(){
			var cbn=$(this).attr("cb");
			var c = $("#"+cbn);
			var ce = c[0];
			if(ce.checked){
				ce.checked=false;
				showteamsinfo(ce);
			}
			});
		});
	}
	
	function search(){
		var svalue = $.trim($("#tbName").val());
		if(svalue!="")
		$("li",$("#teamOptions")).each(function(){
				var vn=$(this).attr("vname");
				if(vn.indexOf(svalue)>=0){
					$(this).show().attr("v","1");
				}else
					$(this).hide().attr("v","0");
		});
		else{
			$("li",$("#teamOptions")).show().attr("v","1");	
		} 
			
	}	

	$.teamsselect = {
		init : function(callback) {
			showteamsinfo=callback;
			initpage();
		},
		uncheck:function(id){
			var c=$("#cb"+id);
			if(c!=undefined && c.length >0){
				c[0].checked=false;
			}
		}
	};

})(jQuery);