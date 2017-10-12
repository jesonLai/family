var RECORDFAMILYLOG=function(){
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
	            "bStateSave":true,
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] 
	            ],
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/recordFamilyLog/recordFamilyLogList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
	                { "data": "description" ,"searchable": false,"orderable": false},
		            { "data": "createMan" ,"searchable": false,"orderable": false},
		            { "data": "roleName" ,"searchable": false,"orderable": false},
		            { "data": "createDate" ,"searchable": false,"orderable": false},
		            { "data": "requestIp","searchable": false,"orderable": false}
	             ]
	        });
	    };	
	return{
		init:function(){
			initTable("recordFamilyLogTable");
			
		}
	}
}();