/**
 * 家族财务
 */
var familyFinancial = function() {
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
	        	 "url":basePath+"/admin/menus/familyFinancial/familyFinancialList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyFinancialId"},
		            { "data": "familyFinancialHtmlContent" },
		            { "data": "familyEventType"},
		            { "data": "familyFinancialTitle" },
		            { "data": "familyFinancialContent" },
	                { "data": "familyFinancialPushMan"},
	                { "data": "releseDate"},
	                { "data": "familyFinancialDetail"},
	                { "data": "familyFinancialDelete"},
	                { "data": "familyFinancialPlacedTop"}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"visible": false,"searchable": false},
	                {"targets": -3,"searchable": false,"orderable": false,"data": "familyFinancialDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyFinancial.detail(this);">详细</a>'},
	                {"targets": -2,"searchable": false,"orderable": false,"data": "familyFinancialDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyFinancial.remove(this);">删除</a>'},
			        {"targets": -1,"searchable": false,"orderable": false,"data": "familyFinancialPlacedTop","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyFinancial.top(this);">置顶</a>'}
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
					url : basePath+'/admin/menus/familyFinancial/COM_FAMILYFINANCIAL_SAVE',
					type : 'POST',
					dataType : 'json',
					data : $(form).serialize(),
					success : function(data) {
						if (!isEmpty(data.FAMILYNEWID)) {
							toastrs.success(data.message)
							$("#familyFinancialId").val(data.FAMILYNEWID);
							familyFinancial.reload();
							familyFinancial.closeDialog();
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
			initTable("familyFinancialTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyFinancial").modal({
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
			$("#add-UEditor-familyFinancial").modal('hide');
		},
		reload:function(){
			var table = $('#familyFinancialTable').DataTable();
			table.ajax.reload();
			
		},
		detail:function(obj){
			var table = $('#familyFinancialTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyFinancialId=data[0].familyFinancialId;
			var htmlContent=data[0].familyFinancialHtmlContent;
			var familyFinancialTitle=data[0].familyFinancialTitle;
			var eventType=data[0].familyEventType;
			$.ajax({
				url : basePath+'/admin/menus/familyFinancial/familyFinancialAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyFinancial-list").html(data);
					addNew();
					$("#familyFinancialId").val(familyFinancialId);
					$("#familyFinancialTitle").val(familyFinancialTitle);
					$("#eventType").val(eventType);
					 ue.addListener("ready", function () {
						 ue.setContent(htmlContent,false);
				     });  
					 $("#eventType").addClass("edited");
						$("#familyFinancialTitle").addClass("edited");
					 familyFinancial.showDialog();
				}
		  }); 
		},
		edit:function(){
			$.ajax({
				url : basePath+'/admin/menus/familyFinancial/familyFinancialAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyFinancial-list").html(data);
					addNew();
					familyFinancial.showDialog();
					
				}
		  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyFinancialTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyFinancialId=data[0].familyFinancialId;
				$.ajax({
					url : basePath+'/admin/menus/familyFinancial/FAMILY_FINANCIAL_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"familyNewsId":familyFinancialId},
					success : function(data) {
						toastrs.success(data.message);
						familyFinancial.reload();
					}
				});
			}
		},
		top:function(obj){
			var table = $('#familyFinancialTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyFinancialId=data[0].familyFinancialId;
			$.ajax({
				url : basePath+'/admin/menus/familyFinancial/FAMILY_FINANCIA_TOP',
				type : 'POST',
				dataType : 'json',
				data : {"familyNewsId":familyFinancialId},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					
					familyFinancial.reload();
				}
			});
			
		}
	}

}();
