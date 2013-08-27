<#macro cascade  src dst dsturl hasblank=true  >
<script type="text/javascript" >
  $(document).ready(function(){
  	 $.cascade.init({"src":"${src}","dst":"${dst}","dsturl":"${virtualPath}/${dsturl}","hasblank":${hasblank?string}});
  });
</script>
</#macro>