/**
 * 在线视频
 */
var familyVideo = function() {
	 var initTable = function (id) {
	        var table = $('#'+id);
	        table.children("tbody").empty();
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
	        	 "url":basePath+"/admin/menus/familyVideo/familyVideoList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyVideoId"},
		            { "data": "familyVideoThumbnail","searchable": false,"orderable": false},
		            { "data": "familyVideoFlashUrl"},
		           
		            { "data": "familyVideoTitle" },
//		            { "data": "familyVideoSource" },
		            { "data": "familyVideoEventType"},
		            { "data": "familyVideoLastModified"},
	                
	                { "data": "familyVideoUploadMan"},
	                { "data": "familyVideoUpdate","searchable": false,"orderable": false,"defaultContent": 
                	'<a href="javascript:void(0)" onclick="familyVideo.detail(this);">修改</a>'},
	                { "data": "familyVideoDelete","searchable": false,"orderable": false,"defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyVideo.remove(this);">删除</a>'}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"searchable": false,"render":function(data,type,full){
	                     return " <a href='"+basePath+"/"+data+"' class='fancybox-button' data-rel='fancybox-button' >" +
	                     		"<img src='"+basePath+"/"+data+"' width='160' height='90'/></a>"}},
	            ]
	        });
	    };
	var addNew=function(){
		$(".familyVideo-form").validate({
            invalidHandler: function(event, validator) { //display error alert on form submit   
            	return false; 
            },
            submitHandler: function(form) {
            	 var formData = new FormData(form);  
				  $.ajax({
					url : basePath+'/admin/menus/familyVideo/COM_FAMILYVIDEO_SAVE',
					type : 'POST',
					dataType : 'json',
					data : formData,
	  				async: false,  
	  		        cache: false,  
	  		        contentType: false,  
	  		        processData: false,  
					success : function(data) {
						if (!isEmpty(data.result)&&data.result) {
							toastrs.success(data.message)
							familyVideo.reload();
							familyVideo.closeDialog();
						} else {
							toastrs.error(data.message);
						}
					}
				});
            	 return false;
            }
		});
	
	}
	 $(".fancybox-button").fancybox();
	return {
		init:function(){
			initTable("familyVideoTable");
			addNew();
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor');
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyVideo").modal({
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
			$("#add-UEditor-familyVideo").modal('hide');
		},
		reload:function(){
			var table = $('#familyVideoTable').DataTable();
			table.ajax.reload( null, false );
		},
		detail:function(obj){
			$.ajax({
				url : basePath+'/admin/menus/familyVideo/familyVideoAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyVideo-list").html(data);
					var table = $('#familyVideoTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var familyVideoId= $("<input>");
					familyVideoId.attr("type","hidden");
					familyVideoId.attr("name","id");
					familyVideoId.attr("value",data[0].familyVideoId);
					$("#familyVideoImgNameOld").before(familyVideoId);
					$("#familyVideoImgNameOld").val(data[0].familyVideoThumbnail);
					$("#familyVideoImgNameNewInit").attr("src",basePath+"/"+data[0].familyVideoThumbnail);
					$("#eventType").val(data[0].familyVideoEventType);
					$("#eventType").addClass("edited");
					$("#title").val(data[0].familyVideoTitle);
					$("#title").addClass("edited");
					$("#flashUrl").val(data[0].familyVideoFlashUrl);
					$("#flashUrl").addClass("edited");
					addNew();
					familyVideo.showDialog();
						
				}
		  });
		},
		edit:function(){
			$.ajax({
				url : basePath+'/admin/menus/familyVideo/familyVideoAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyVideo-list").html(data);
					addNew();
					familyVideo.showDialog();
						
				}
		  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyVideoTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyVideoId=data[0].familyVideoId;
				$.ajax({
					url : basePath+'/admin/menus/familyVideo/COM_FAMILYVIDEO_REMOVE',
					type : 'POST',
					dataType : 'json',
					data : {"familyVideoId":familyVideoId},
					success : function(data) {
						if(data.result){
							toastrs.success(data.message);
						}else{
							toastrs.error(data.message);
						}
						familyVideo.reload();
					}
				});
			}
		}
	}

}();
