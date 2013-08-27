<#--
<input type="checkbox"/>
-->
<#macro checkbox2
	value="" cvalue="" labelFor="" readonly="" checked=""
	label="" noHeight="false" required="false" colspan="" help="" helpPosition="3"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="checkbox"<#rt/>
 value="${value}"<#rt/>
 id="${id+'-'+name}"<#rt/>
<#if readonly!=""> readonly="${readonly}"</#if><#rt/>
<#if checked!=""> checked="${checked}"<#elseif cvalue!="" && cvalue==value> checked="checked"</#if><#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#if labelFor!=""><label for="${id+'-'+name}">${labelFor}</label></#if>
</#macro>

<#macro checkbox id name value hidname="" hidvalue=-1 ispost=false formid="actionForm" >
 <#if hidname !="" && hidvalue !=-1 ><input type="hidden" id="${hidname}" name="${hidname}" value="${hidvalue}" /></#if>
 <input type="checkbox" id="${id}" name="${name}"  <#if value>checked<#else></#if> 
 <#if hidname!="" && hidvalue!=-1 >onclick="if(this.checked) $('#${hidname}').val('1');else $('#${hidname}').val('0');"</#if>
 <#if hidname=="" && ispost >onclick="$('#${formid}')[0].submit();"</#if>
   />
</#macro>