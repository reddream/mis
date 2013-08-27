<#macro inventories value listAction="main.htm" >	
<#if value?is_sequence><#local pageList=value/><#else><#local pageList=value.list/></#if>									
					<div id="mainContainerWrapper" class="dealersMainContainerWrapperTopPadding">
					<form id="tableForm" method="post" >
					<input type="hidden" name="pageNo" value="${value.pageNo}" />					
						<div class="viewInventoryInfoPanelLeft"><span>${value.totalCount}</span> inventories found in database</div>
						<div class="viewInventoryInfoPanelRight"></div>
						<ul id="globalTableWrapper">
							<li id="globalTableHeader">
								<ul>								
		<li class="inventoryCode"><a href="#">VIN</a></li>
		<li class="inventoryDealerCode"><a href="">Dealer Code</a></li>
		<li class="inventoryMake"><a href="#">Make</a></li>
		<li class="inventoryModel"><a href="#">Model</a></li>
		<li class="inventoryYear"><a href="#">Year</a></li>
		<li class="inventoryDrive"><a href="#">Drive</a></li>
								</ul>
							</li>
	<#list pageList as row> 
		<#assign  i=row_index licssclass="globalTableRow globalTableRowBG"  />			
			<#if i%2==1 >
			  <#assign licssclass="globalTableRow" />
			</#if>						
							<li class="${licssclass}">
								<ul>
		<li class="inventoryCode"><a href="#">${row.vin}</a></li>
		<li class="inventoryDealerCode">${row.dealer.code}</li>
		<li class="inventoryMake">${row.make.make}</li>
		<li class="inventoryModel">${row.makeModel.name}</li>
		<li class="inventoryYear">${row.year}</li>
		<li class="inventoryDrive">${row.drive.name}</li>
								</ul>
							</li>						
						
	</#list>			
	</ul>				
	             <#if value.totalPage gt 1 >
	             	<#assign pnumbers=value.currentPageList />
	             	
						<ul id="globalPagination">
						    <#if !value.firstPPage >
						    <li><a herf="javascript:void();" onclick="javascript:_gotoPage('1');">First</a></li>	
						    </#if>
					<#list pnumbers as pnumber>
					    <#if pnumber==value.pageNo>
				   			<li  id="globalPaginationPageSelected" ><a herf="javascript:void();"  >${pnumber}</a></li>
					    <#else>
					       <li><a herf="javascript:void();" onclick="javascript:_gotoPage('${pnumber}');" >${pnumber}</a></li>       
					   </#if>
					</#list>			
							<#if !value.lastPPage >
							
							<li><a herf="javascript:void();" onclick="javascript:_gotoPage('${value.totalPage}');" >Last</a></li>
							</#if >
						</ul>
				</#if>	
				<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
function _gotoPage(pageNo) {
	try{
		var tableForm = getTableForm();
		$("input[name=pageNo]").val(pageNo);
		tableForm.action="${listAction}";
		tableForm.onsubmit=null;
		tableForm.submit();
	} catch(e) {
		showMsg('_gotoPage(pageNo) wrong.');
	}
}
</script>	
				</form>	
					</div>				
</#macro>			