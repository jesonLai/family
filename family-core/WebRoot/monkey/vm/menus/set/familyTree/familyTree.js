/**
 * 家族族谱
 */
var familyTree = function() {
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
	        	 "url":basePath+"/admin/menus/familyTree/familyTreeList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyTreeId"},
		            { "data": "familyTreeHtmlContent" },
		            { "data": "familyEventType" },
		            { "data": "familyTreeTitle" },
		            { "data": "familyTreeContent" },
	                { "data": "familyTreePushMan"},
	                { "data": "releseDate"},
	                { "data": "familyTreeDetail","searchable": false,"orderable": false},
	                { "data": "familyTreeDelete","searchable": false,"orderable": false},
	                { "data": "familyTreePlacedTop","searchable": false,"orderable": false}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"visible": false,"searchable": false},
	                {"targets": -3,"data": "familyTreeDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyTree.detail(this);">详细</a>'},
	                {"targets": -2,"data": "familyTreeDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyTree.remove(this);">删除</a>'},
			        {"targets": -1,"data": "familyTreePlacedTop","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyTree.top(this);">置顶</a>'}
	            ]
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
					url : basePath+'/admin/menus/familyTree/COM_FAMILYTREE_SAVE',
					type : 'POST',
					dataType : 'json',
					data : $(form).serialize(),
					success : function(data) {
						if (!isEmpty(data.FAMILYNEWID)) {
							toastrs.success(data.message)
							$("#familyTreeId").val(data.FAMILYNEWID);
							familyTree.reload();
							familyTree.closeDialog();
						} else {
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
			initTable("familyTreeTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyTree").modal({
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
			$("#add-UEditor-familyTree").modal('hide');
		},
		reload:function(){
			var table = $('#familyTreeTable').DataTable();
			table.ajax.reload();
			
		},
		detail:function(obj){
			var table = $('#familyTreeTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyTreeId=data[0].familyTreeId;
			var htmlContent=data[0].familyTreeHtmlContent;
			var familyTreeTitle=data[0].familyTreeTitle;
			var eventType=data[0].familyEventType;
			$.ajax({
				url : basePath+'/admin/menus/familyTree/familyTreeAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyTree-list").html(data);
					addNew();
					$("#familyTreeId").val(familyTreeId);
					$("#familyTreeTitle").val(familyTreeTitle);
					$("#eventType").val(eventType);
					$("#familyTreeTitle").addClass("edited");
					$("#eventType").addClass("edited");
					
					 ue.addListener("ready", function () {
						 ue.setContent(htmlContent,false);
				     });  
					 familyTree.showDialog();
				}
		  });  
		},
		edit:function(){
			$.ajax({
				url : basePath+'/admin/menus/familyTree/familyTreeAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyTree-list").html(data);
					addNew();
					familyTree.showDialog();
						
				}
		  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyTreeTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyTreeId=data[0].familyTreeId;
				$.ajax({
					url : basePath+'/admin/menus/familyTree/FAMILY_TREE_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"familyNewsId":familyTreeId},
					success : function(data) {
						toastrs.success(data.message);
						familyTree.reload();
					}
				});
			}
		},
		top:function(obj){
			var table = $('#familyTreeTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyTreeId=data[0].familyTreeId;
			$.ajax({
				url : basePath+'/admin/menus/familyTree/FAMILY_TREE_TOP',
				type : 'POST',
				dataType : 'json',
				data : {"familyNewsId":familyTreeId},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					
					familyTree.reload();
				}
			});
			
		}
	}

}();
