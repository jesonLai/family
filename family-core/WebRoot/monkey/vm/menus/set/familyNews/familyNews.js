/**
 * 新闻编辑
 */

var familyNews = function() {
	 var initTable = function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      oTable = table.dataTable({
	        	"language": {
	                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json"
	            },
	          	"processing":true,
	            "serverSide":true,
	            "JQueryUI":true,
	            "Paginate":true,
	            "Filter":true,
	            "LengthChange":true,
	            "iDisplayStart":0,
	            "iDisplayLength": 5,
	            "ort":false,
	            "Info":true,
	            "Width":true,
	            "ScrollConllapse":true,
	            "sPaginationType":"full_numbers",
	            "bDestroy":true,
	            "bSortCellsTop": true,
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] 
	            ],
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/familynews/familynewsList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyNewId"},
		            { "data": "familyNewHtmlContent" },
		            { "data": "familyNewsImgName" },
		            { "data": "familyNewImage" },
		            { "data": "familyEventType" },
		            { "data": "familyNewTitle" },
		            { "data": "familyNewContent" },
	                { "data": "familyNewPushMan"},
	                { "data": "releseDate"},
	                { "data": "familyNewDetail","searchable": false,"orderable": false},
	                { "data": "familyNewDelete","searchable": false,"orderable": false},
	                { "data": "familyNewPlacedTop","searchable": false,"orderable": false}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"visible": false,"searchable": false},
	                {"targets": [ 2 ],"visible": false,"searchable": false},
	                {"targets": [ 3 ],"searchable": false,"render":function(data,type,full){
	                     return " <a href="+basePath+"/"+data+" class='fancybox-button' data-rel='fancybox-button' >" +
	                     		"<img src="+basePath+"/"+data+"  width='160' height='90'/></a>"}},
	               
	                
	                {"targets": -3,"data": "familyNewDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyNews.detail(this);">详细</a>'},
	                {"targets": -2,"data": "familyNewDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyNews.remove(this);">删除</a>'},
	                {"targets": -1,"data": "familyNewDelete","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyNews.top(this);">置顶</a>'}
	            ]
	        });
	    };
	    var addNew=function(){
			$(".ueditor-form").validate({
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	            	return false; 
	            },
	            submitHandler: function(form) {
	            	 var formData = new FormData(form);  
	            	var txt=ue.getContentTxt();
	            	if(txt.length>13)
	            		$("#content").val(txt.substr(0,12)+"...");
	            	else
	            		$("#content").val(txt);
					  $.ajax({
						url : basePath+'/admin/menus/familynews/family_news_save',
						type : 'POST',
						dataType : 'json',
						data : formData,
		  				async: false,  
		  		        cache: false,  
		  		        contentType: false,  
		  		        processData: false,  
						success : function(data) {
							if(data.result){
								toastrs.success(data.message);
								familyNews.reload();
								familyNews.closeDialog();
							}else{
								toastrs.error(data.message);
							}
	
						}
					});
	            	 return false;
	            }
			});
	    }
	return {
		init:function(){
			initTable("familyNewTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.addListener("ready", function () {
				 ue.execCommand('serverparam',parameterName, token); 
			});
		},
		showDialog : function() {
			$("#add-UEditor").modal({
				backdrop: 'static',
				keyboard: false,
				show:true,
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			});
		},
		closeDialog:function(){
			$("#add-UEditor").modal('hide');
		},
		detail:function(obj){
			var table = $('#familyNewTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyNewId=data[0].familyNewId;
			var htmlContent=data[0].familyNewHtmlContent;
			var familyNewTitle=data[0].familyNewTitle;
			var eventType=data[0].familyEventType;
			var familyNewsImgFolder=data[0].familyNewsImgFolder;
			var familyNewsImgName=data[0].familyNewsImgName;
			var familyNewImage=data[0].familyNewImage;
			
			
			 $.ajax({
					url : basePath+'/admin/menus/familynews/familynewsAddPage',
					type : 'POST',
					dataType : 'html',
					success : function(data) {
						$(".add-UEditor-list").html(data);
						addNew();
						$("#familyNewId").val(familyNewId);
						 ue.addListener("ready", function () {
							 ue.setContent(htmlContent,false);
					     });  
						 $("#eventType").val(eventType);
						$("#title").val(familyNewTitle);
						$("#eventType").addClass("edited");
						$("#title").addClass("edited");
						$("#familyNewsImgName").val(familyNewsImgName);
						$("#familyNewsImgFolder").val(familyNewsImgFolder);
						$("#familyNewImg").attr("src",basePath+familyNewImage);
						familyNews.showDialog();
					}
			  });
		},
		edit:function(parameterName,token){
			  $.ajax({
					url : basePath+'/admin/menus/familynews/familynewsAddPage',
					type : 'POST',
					dataType : 'html',
					success : function(data) {
						$(".add-UEditor-list").html(data);
						addNew();
						familyNews.showDialog();
							
					}
			  });
		},
		
		reload:function(){
			var table = $('#familyNewTable').DataTable();
			table.ajax.reload();
			
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyNewTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyNewsId=data[0].familyNewId;
				$.ajax({
					url : basePath+'/admin/menus/familynews/family_news_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"familyNewsId":familyNewsId},
					success : function(data) {
						toastrs.success(data.message);
						familyNews.reload();
					}
				});
			}
		},
		top:function(obj){
			var table = $('#familyNewTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyNewsId=data[0].familyNewId;
			$.ajax({
				url : basePath+'/admin/menus/familynews/family_news_top',
				type : 'POST',
				dataType : 'json',
				data : {"familyNewsId":familyNewsId},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					
					familyNews.reload();
				}
			});
			
		}
	 }

}();
