var roles = function() {
	 var initTable = function (id,authorityId) {
        var table = $('#'+id);
        table.children("tbody").empty();
        $.extend(true, $.fn.DataTable.TableTools.classes, {
            "container": "btn-group tabletools-btn-group pull-right",
            "buttons": {
                "normal": "btn btn-sm default",
                "disabled": "btn btn-sm default disabled"
            }
        });
      var oTable = table.dataTable({
        	"language": {
                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json"
            },
          	"processing":true,
            "serverSide":true,
            "JQueryUI":true,
            "bPaginate":false,
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
        	 "url":basePath+"/admin/menus/roles/rolesList",
        	 "data":function(d){
        		 d.authorityId=authorityId
        	 },
        	 "type":"POST"
            }
            ,
            "columns": [
	            { "data": "rolesId","title":"角色编号"},
	            { "data": "rolesName","title":"角色名称"},
	            { "data": "rolesSystemName","title":"角色系统名称"},
	            { "data": "rolesStatus","title":"角色状态"},
	            { "data": "rolesIsSys","title":"角色是否系统"},
                { "data": "rolesDesc","title":"详细"},
                { "data": "rolesRemove","title":"删除","defaultContent": 
            	'<a href="javascript:void(0)" onclick="roles.remove(this);">删除</a>'},
                { "data": "rolesUpdate","title":"编辑","defaultContent": 
                	'<a href="javascript:void(0)" onclick="roles.update(this);">编辑</a>'},
                { "data": "rolesGrant","title":"授权","defaultContent": 
                	'<a href="javascript:void(0)" onclick="roles.showDialog()">授权</a>'}

             ]
        });
    };
    //授权
    var grant=function(){
    	console.info($("#menuItems").html())
    }
	return {
		init : function(authorityId) {
			initTable("roleTable",authorityId);
		},
	   	showDialog : function() {
			$("#role-authority").modal({
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
	   	grant:function(obj){
	   		grant();
	   	}
	}
}();
