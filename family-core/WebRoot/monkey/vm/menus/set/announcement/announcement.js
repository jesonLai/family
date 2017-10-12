/**
 * 公告
 */

var announcement = function() {
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
	        	 "url":basePath+"/admin/menus/announcement/announcementList",
	        	 "type":"POST"
	            },
	            "columns": [
		            { "data": "announcementId"},
		            { "data": "announcementTitle" },
		            { "data": "announcementContent" },
		            { "data": "announcementPushMan" },
	                { "data": "announcementCreateDate"},
	                { "data": "announcementDetail","searchable": false,"orderable": false},
	                { "data": "announcementDelete","searchable": false,"orderable": false},
	                { "data": "announcementPlacedTop","searchable": false,"orderable": false}
	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": -3,"data": "announcementDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="announcement.detail(this);">详细</a>'},
	                {"targets": -2,"data": "announcementDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="announcement.remove(this);">删除</a>'},
			        {"targets": -1,"data": "announcementPlacedTop","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="announcement.top(this);">置顶</a>'}
	            ],
	            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
	            	var announcementContent=aData.announcementContent;
	            	var div=document.createElement("div");
	            	$(div).html(announcementContent);
	            	var text=$(div).text();
	                if (text.length > 13) {
	                	text= text.substr(0, 12)+"...";
	                }
	                $('td:eq(1)', nRow).html(text);
	            }
	        });
	    };
	    var addNew=function(){
			$(".ueditor-form").validate({
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	            	return false; 
	            },
	            submitHandler: function(form) {
	            	var txt=ue.getContentTxt();
	            	if(txt.length>13)
	            		$("#content").val(txt.substr(0,12)+"...");
	            	else
	            		$("#content").val(txt);
					  $.ajax({
						url : basePath+'/admin/menus/announcement/COM_ANNOUNCEMENT_SAVE',
						type : 'POST',
						dataType : 'json',
						data : $(form).serialize(),
						success : function(data) {
							if (!isEmpty(data.ANNOUNCEMENTID)) {
								toastrs.success(data.message)
								$("#announcementId").val(data.ANNOUNCEMENTID);
								announcement.reload();
								announcement.closeDialog();
							} else {
								toastrs.error(data.message);
							}
						}
					});
	            	 return false;
	            }
			});
		}
	    return{
	    	init:function(){
	    		initTable("announcementTable")
	    	},
			ueditor:function(parameterName,token){
				 UE.delEditor("editor");
				 ue = UE.getEditor('editor');
				 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
			},
		   	showDialog : function() {
					$("#add-UEditor-announcement").modal({
						backdrop: 'static',
						keyboard: false,
						show:true,
						width : '80%'
					}).draggable({
						handle : '.modal-header',
						cursor : 'move',
						refreshPositions : false
					})
			},
			closeDialog:function(){
				$("#add-UEditor-announcement").modal('hide');
			},
				reload:function(){
					var table = $('#announcementTable').DataTable();
					table.ajax.reload();
					
				},
				
				detail:function(obj){
					var table = $('#announcementTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var htmlContent=data[0].announcementContent;
					var announcementId=data[0].announcementId;
					var announcementTitle=data[0].announcementTitle;
					 $.ajax({
							url : basePath+'/admin/menus/announcement/announcementAddPage',
							type : 'POST',
							dataType : 'html',
							success : function(data) {
								$(".add-UEditor-announcement-list").html(data);
								addNew();
								$("#announcementId").val(announcementId);
								 ue.addListener("ready", function () {
									 $("#title").val(announcementTitle);
									 $("#title").addClass("edited");
									 ue.setContent(htmlContent,false);
							     });  
								 announcement.showDialog();
							}
					  });
				},
				edit:function(){
					$.ajax({
						url : basePath+'/admin/menus/announcement/announcementAddPage',
						type : 'POST',
						dataType : 'html',
						success : function(data) {
							$(".add-UEditor-announcement-list").html(data);
							addNew();
							announcement.showDialog();
							
						}
				  });
				},
				remove:function(obj){
					if(confirm("是否删除!")){
						var table = $('#announcementTable').DataTable();
						var data = table.rows($(obj).parents("tr")).data();
						var announcementId=data[0].announcementId;
						$.ajax({
							url : basePath+'/admin/menus/announcement/FAMILY_ANNOUNCEMENT_DEL',
							type : 'POST',
							dataType : 'json',
							data : {"id":announcementId},
							success : function(data) {
								if(data.result){
									toastrs.success(data.message);
								}else{
									toastrs.error(data.message);
								}
								
								announcement.reload();
							}
						});
					}
				},
				top:function(obj){
					var table = $('#announcementTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var announcementId=data[0].announcementId;
					$.ajax({
						url : basePath+'/admin/menus/announcement/FAMILY_ANNOUNCEMENT_TOP',
						type : 'POST',
						dataType : 'json',
						data : {"id":announcementId},
						success : function(data) {
							if(data.result){
								toastrs.success(data.message);
							}else{
								toastrs.error(data.message);
							}
							
							announcement.reload();
						}
					});
					
				}
		   }
}();