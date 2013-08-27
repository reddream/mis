<#macro listjs >
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}

function optBatch(url) {
	if(Pn.checkedCount('ids')<=0) {
		showMsg("<@s.m 'error.checkRecord'/>");
		return;
	}
	if(url=="delete.htm"&&!confirm("<@s.m 'global.confirm.delete'/>")) {
		return;
	}
	if(url=="recover.htm"&&!confirm("你确定要恢复这些记录吗?")) {
		return;
	}
	var f = getTableForm();
	f.action=url;
	preParams();
	f.submit();
}
</script>
</#macro>