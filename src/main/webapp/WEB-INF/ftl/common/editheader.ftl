<#macro editheader >
<link href="${base}/res/admin/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/theme.css?t=${curtime}" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/jquery.validate.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/jquery.treeview.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/jquery.ui.css" rel="stylesheet" type="text/css"/>

<script src="${base}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${base}/res/common/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/common.js"></script>
<script src="${base}/js/jquery.json-2.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/js/easyloader.js"></script>
<script src="${base}/res/common/js/jquery.ext.js" type="text/javascript"></script>
<script src="${base}/res/common/js/pony.js" type="text/javascript"></script>
<script src="${base}/res/admin/js/admin.js?t=${curtime}" type="text/javascript"></script>
<script type="text/javascript">
	  easyloader.locale = "${lan}"; 
	  easyloader.theme = "${themeconfig.theme}";
	  $(document).ready(function(){
	  		using([ 'messager', 'linkbutton', 'dialog' ], function() {});
	  });
</script>
</#macro>