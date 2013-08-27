<#macro float name id integerLength="2" decimalLength="2" required="false" requiredtxt=""  >
	<input type="text" name="${name}" id="${id}" class="float" integerLength="${integerLength}" decimalLength="${decimalLength}"  	<#include "commonattributes.ftl"/> />
</#macro>	