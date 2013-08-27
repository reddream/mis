<#macro privilege operate checkRight="true" >
<#if operate?starts_with('/')>
	<#local opr=operate>
<#else>
	<#local opr=requesturi?substring(base?length,requesturi?last_index_of('/')+1) + operate>
</#if>
<#if (currentuser.actionUrls?contains(";"+opr+";"))>
<#nested/>
</#if>
</#macro>