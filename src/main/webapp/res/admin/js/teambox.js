(function($) {
	var x1gif="/res/common/img/address/X_1.gif";
	var ad=null;

	function addValue(id){
		var hd=document.getElementById("hdInputValue");
		var v=","+hd.value+",";
		if(v.indexOf(","+id+",")==-1){
			if(hd.value=="")
				hd.value=id+"";
			else
				hd.value=hd.value+","+id;
		}	
	}
	
	function delValue(id){
		var hd=document.getElementById("hdInputValue");
		var v=","+hd.value+",";
		if(v.indexOf(","+id+",")>-1){
			v = v.replace(","+id+",",",");
			while(v.substr(0,1)==",")
				v=v.substr(1,v.length-1);
			while(v.substr(v.length-1,1)==",")
				v=v.substr(0,v.length-1);
			hd.value=v;
		}	
	}
	
	function addCell(id,n){
		var cellhtml="<div id=\"contentDiv_"+id+"\" class=\"CS1\"  onblur=\"javascript:$.teambox.setcs(this,'CS1');\" " +
				"onmouseout=\"javascript:if (this.className!='CS3') $.teambox.setcs(this,'CS1');\" " +
				"onmouseover=\"javascript:if (this.className!='CS3') $.teambox.setcs(this,'CS2');\" " +
				"onfocus=\"javascript:$.teambox.setcs(this,'CS3');\" title=\""+n+"\">" +
			    n+"<img id=\"delete_"+id+"\" onclick=\"javascript:$.teambox.delcell('"+id+"');\" src=\""+x1gif+"\" /></div>";
		ad.before(cellhtml);
		addValue(id);
	}
	
	function delCell(id){
		$("#contentDiv_"+id).remove();
		delValue(id);
		if($.teamsselect){
			$.teamsselect.uncheck(id);
		}
	}
	
	$.teambox={
		init:function(cp){
			x1gif=cp+x1gif;
			ad=$("#addrInputdiv");
		},
		setcs:function(t,n){
			$(t).removeClass(t.className).addClass(n);
		},
		addcell:function(id,n){
			addCell(id,n);
		},
		delcell:function(id){
			delCell(id);
		}
	};
	
})(jQuery);