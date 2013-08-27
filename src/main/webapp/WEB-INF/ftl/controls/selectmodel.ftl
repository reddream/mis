<#macro selectmodel id name="" models=[] value=-1 >
 <select id="${id}" name="${name}" >
  <#list models as n>
  <#if n.id?string==(value!-1)?string >
 <option value="${n.id}" selected="true" >${n.name}</option>
  <#else>
  <option value="${n.id}" >${n.name}</option>
  </#if>
  </#list>
 </select>
</#macro>