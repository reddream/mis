<#macro submit code class=""  action="" formid="actionForm" >
<input class=${class} type="submit" value="<@s.m code />" onclick='if("${action}"!=""){document.getElementById("${formid}").action="${action}";}' />
</#macro>