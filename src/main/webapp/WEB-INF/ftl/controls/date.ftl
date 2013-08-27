<#macro date id value name="" >
  <input class="Wdate" type="text" id="${id}" name="${name}"
				onFocus="WdatePicker({isShowClear:false,readOnly:true})" value="${value}" />
</#macro>