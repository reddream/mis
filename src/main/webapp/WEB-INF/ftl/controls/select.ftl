<#macro select id names=[] values=[] >
 <select id="${id}" >
  <#list names as n>
  <#assign v=values[n_index] />
  <option value="${v}" >${n}</option>
  </#list>
 </select>
</#macro>