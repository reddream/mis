<#macro alertmessage >
<#if message??>
<script type="text/javascript">
showMsg("<@s.mt code=message text=message/>");
</script>
</#if>
</#macro>