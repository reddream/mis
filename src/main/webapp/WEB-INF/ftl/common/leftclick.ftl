<#macro  leftclick>
<script type="text/javascript">
 $(document).ready(function(){
	 $(".w-lful li").click(function(){
		var arr = $("a",this); 
		var href = $(arr[0]).attr("href");
		window.parent.refreshRight(href);
	 }).css("cursor","pointer");
 })
</script>
</#macro >