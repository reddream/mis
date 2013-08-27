<#macro checkboxlist items name >
 <#list items as item >
 <@p.checkbox id=name+item_index name=name text=item.text value=item.id />&nbsp;
 </#list>
</#macro>