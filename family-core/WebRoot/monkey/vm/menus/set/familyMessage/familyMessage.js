/**
 * 新闻编辑
 */

var FAMILYMESSAGE = function() {
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
	        	 "url":basePath+"/admin/menus/familyMessage/familyMessageList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyMessageId"},
//		            { "data": "familyMessageTitle" },
		            { "data": "familyMessageContent" },
		            { "data": "familyMessageMan"},
	                { "data": "familyMessageDate"},
	                { "data": "familyMessageDelete","searchable": false,"orderable": false}
	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": -1,"data": "familyMessageDelete","defaultContent": 
                	'<a href="javascript:void(0)" onclick="FAMILYMESSAGE.remove(this);">删除</a>'}
	            ]
	        });
	    };
	return {
		init:function(){
			initTable("familyMessageTable");
		},
		reload:function(){
			var table = $('#familyMessageTable').DataTable();
			table.ajax.reload();
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyMessageTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyMessageId=data[0].familyMessageId;
				$.ajax({
					url : basePath+'/admin/menus/familyMessage/FAMILY_MESSAGE_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"id":familyMessageId},
					success : function(data) {
						if(data.result){
							toastrs.success(data.message);
						}else{
							toastrs.error(data.message);
						}
						FAMILYMESSAGE.reload();
					}
				});
			}
		}
	
	}
}();
