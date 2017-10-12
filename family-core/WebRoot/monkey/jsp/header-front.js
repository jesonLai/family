//注册
var instationSearch = function() {
	var searchF=$("form[name=instationSearch]");
	
	function sendSearch(basePath){
		var searchVal=searchF.find('#searchVal').val();
		if(searchVal==""){
			alert("请输入关键字")
		}else{
			searchF.attr("action",basePath+"/user/instation/search/"+encodeURI(searchVal));
			console.info(searchF.attr("action"))
			searchF.submit();
		}
	}
	
	return {
		init : function(basePath) {
			searchF.find("button").click(function(){
				sendSearch(basePath)
			});
			searchF.find("#searchVal").keydown(function(event){
				if(event.keyCode==13){
					sendSearch(basePath)
					return true
				}
			});
		}
	}
}();