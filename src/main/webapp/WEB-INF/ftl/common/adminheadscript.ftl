<#macro adminheadscript >
<link href="${base}/res/admin/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/theme.css" rel="stylesheet" type="text/css"/>
<!--

<link href="${base}/res/common/css/jquery.validate.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/jquery.treeview.css" rel="stylesheet" type="text/css"/>
<link href="${base}/res/common/css/jquery.ui.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" type="text/css" href="${base}/js/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${base}/js/themes/demo.css" />
-->
<link rel="stylesheet" type="text/css" href="${base}/js/themes/css.css" />	
<script type="text/javascript" src="${base}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${base}/js/easyloader.js"></script>
<script src="${base}/res/common/js/pony.js?t=${curtime}" type="text/javascript"></script>
<script src="${base}/res/admin/js/admin.js?t=${curtime}" type="text/javascript"></script>
<script type="text/javascript">
	  easyloader.locale = "${lan}"; 
	  easyloader.theme = "${themeconfig.theme}";
	  $(document).ready(function(){
	  		using([ 'messager', 'linkbutton', 'dialog' ], function() {});
	  });
</script>
</#macro>