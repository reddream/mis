<#macro addressbox height="200px" inputWidth="481px" >
<div id="ContainerLayer" style="height:${height};">
<div id="addrTTips" style="overflow-y: auto; overflow-x: hidden; height: ${height};">
<div id="addrInputdiv" class="addrInputdiv">
<input id="addressInput" class="addrInput ac_input" onpaste="getAddressValue();" autocomplete="off" readonly="true" style="width:${inputWidth};">
</div>
<div id="cleardiv" class="Clear"> </div>
</div>
<input id="hdInputValue" type="hidden" name="hdInputValue" value="" />
</div>
</#macro>