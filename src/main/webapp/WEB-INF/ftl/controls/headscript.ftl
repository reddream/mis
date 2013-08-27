<#macro headscript >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta HTTP-EQUIV="pragma" CONTENT="no-cache" />
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache,   must-revalidate" />
<meta HTTP-EQUIV="expires" CONTENT="0" />
<title><@s.m "login.copyright" /></title>
<meta name="keywords" content='<@s.m "header.title" />' />
<meta name="description" content='<@s.m "header.description" />' />
<link rel="stylesheet" type="text/css"
	href="${base}/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/js/themes/demo.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/js/themes/css.css" />	
<script type="text/javascript"
	src="${base}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/js/common.js?t=${curtime}"></script>
<script type="text/javascript"
	src="${base}/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${base}/js/easyloader.js"></script>
<script type="text/javascript" src="${base}/js/functions/login.js?t=${curtime}"></script>
<script type="text/javascript">
	  easyloader.locale = "${lan}"; 
	  easyloader.theme = "${themeconfig.theme}";
</script>
</#macro>
