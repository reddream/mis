(function($) {
	var mpath = "/admin/dashboard/teamsdatamonth.htm";
	var ypath = "/admin/dashboard/teamsdatayear.htm";
	var reports=[];
	var curp=mpath;
	var condstr="";
	var chart=null;
	function getsubtypes(){
		return $("#subtypes").val();
	}
	
	function conditionchanged(){
		return condstr != getconditionstr();
	}
	
	function getconditionstr(){
		var data = {};
		data.start = $("#start").val();
		data.end = $("#end").val();
		data.subTypes=getsubtypes();
		data.teamreferences = $("#hdInputValue").val();
		var dataStr = $.toJSON(data);
		return dataStr;
	}
	
	function report(p) {
		if($("#hdInputValue").val()==""){
			$.messager.alert("Warning","No team selected.","info"); 
			return;
		}
		var dataStr=getconditionstr();
		condstr = dataStr;
		var path = p;
		$.ajax({
			type : "POST",
			url : path,
			dataType : "json",
			data : dataStr,
			contentType : "application/json",
			timeout : 120000,
			beforeSend : function() {
				$.messager.progress();
			},
			success : function(r) {
				$.messager.progress('close');
				if(r.isOK)
					drawGChart(r.reports, path);
				else
				  $.messager.alert("Warning",r.message,"info"); 
			},
			error : function() {
				$.messager.progress('close');
			}
		});
	}
	
	function getctype(){
		return $("#charttype").val();
	}
	
	function drawGChart(r, p) {
		reports = r;
		curp = p;
		if (reports.length <=1) {
			$("#chartdiv").html("<h3>No data</h3>");
			return;
		}
		var xname = p == mpath ? "Month" : "Year";
		var strst = getsubtypes();
		if(strst=="")
			strst="All";
		xname ="Type:"+strst+",Zoom:"+xname;
		var title="Teams payment from "+$("#start").val()+" to "+$("#start").val();
		var ctype = getctype();
		$.reports[ctype].draw(reports,{"title":title},{an:xname,width:600,height:400});
	}
	
	function initdata(cp){
		mpath = cp + mpath;
		ypath = cp + ypath;
		curp = mpath;
		using([ 'messager', 'linkbutton' ], function() {
			$("#monthchart").click(function() {
				report(mpath);
			});
			$("#yearchart").click(function() {
				report(ypath);
			});
			$("#charttype").change(function(){
				if(conditionchanged())
					report(curp);
				else
					drawGChart(reports,curp);
			});
			//report(mpath);
		});
	}
	
	function showteam(cb){
		var v=cb.value;
		var txt = $("#txt"+cb.value).html();
		if(cb.checked){
			$.teambox.addcell(v,txt);
		}else 
			$.teambox.delcell(v);
	}
	
	$.teamdata={
		init:function(cp){
			$.teambox.init(cp);
			$.teamsselect.init(showteam);
			initdata(cp);
		}
	};
	
})(jQuery);