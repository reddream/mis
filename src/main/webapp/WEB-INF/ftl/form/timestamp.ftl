<#macro timestamp  name hour=0 minute=0 >
<#assign hours=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23] />
<#assign minutes=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59] />		
<#assign hiddenid="time_"+name />
<input type="hidden" id="${hiddenid}Hour" name="hour" value="${hour}" />	
<input type="hidden" id="${hiddenid}Minute" name="minute" value="${minute}" />	
	<table border="0" >
	  <tr>
	  	<td>
	  	<select id="hour${name}" >
	  	 <#list hours as h>
	  	   <#if h==hour>
	  	     <option value="${h}" selected="true" ><#if h<10>0${h}<#else>${h}</#if></option>
	  	     <#else>
	  	      <option value="${h}" ><#if h<10>0${h}<#else>${h}</#if></option>
	  	   </#if>   
	  	 </#list>
	  	</select>
	  	</td>
	  	<td>:</td>
	  	<td>
	  	<select id="minute${name}" >
	  	 <#list minutes as m>
	  	  <#if m==minute>
	  	     <option value="${m}" selected="true" ><#if m<10>0${m}<#else>${m}</#if></option>
	  	     <#else>
	  	      <option value="${m}" ><#if m<10>0${m}<#else>${m}</#if></option>
	  	   </#if>
	  	  </#list>    
	  	</select>
	  	</td>
	  </tr>		
	</table>
	<script type="text/javascript" >
	    function fill${hiddenid}(){
	    	$("#${hiddenid}Hour").val($("#hour${name}").val());  
	    	$("#${hiddenid}Minute").val($("#minute${name}").val());  
	    }
	
	
		$(document).ready(
			function(){
				$("#hour${name}").click(function(){fill${hiddenid}();});
				$("#minute${name}").click(function(){fill${hiddenid}();});
			}
		);	
	</script>
</#macro >