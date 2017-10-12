var Menu=function(){
	return {
        init: function (menuDatas) {
        		var pageContent=$(".page-content");
				var menuItems=$(".menu-controller").find("a");
				$(menuItems).each(function(){
					var isSend=$(this).attr("send");
					if(isSend=="yes"){
						$(this).bind('click',function(){
							var txt=$(this).text();
							var li=$(this).parent("li");
							li.siblings().removeClass("start active open").find(".arrow ").removeClass("open")
							$.ajax({
								url:unescape($(this).attr("page")),
								type:'POST',
								data:{"menuName":txt},
								dataType:'html',
								success:function(data){
									pageContent.html(data);
								}
							});
							
						});
					}
					
				});	
	        },
		refresh_menu_content:function(url){
			var pageContent=$(".page-content");
			$.ajax({
				url:unescape(basePath+"/"+url),
				type:'POST',
				dataType:'html',
				success:function(data){
					pageContent.html(data);
				}
			});
		}
     };
}()