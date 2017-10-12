var MENUITEM=function(){
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
	            "processing":false,
	            "serverSide":false,
	            "bPaginate":false,
	            "bSort":false,
	            "bFilter":false,
	            "bLengthChange":false,
	            "ort":false,
	            "Info":false,
	            "ScrollConllapse":false,
	            "bDestroy":true,
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/menuItem/menuItemList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [

	                        {"data":"children","className": 'treegrid-control',render:function(data,type,full) {
	                            if (data) {
	                                return '<span><span><i class="fa fa-caret-right" aria-hidden="true"></i></span></span>';
	                            }
	                            return '';
	                        	}
	                        },
		             {"data": "MenuId","title":"菜单编号","visible":false},
		             {"data": "MenuName","title":"菜单名称"},
		             {"data": "MenuItemIcon","title":"菜单图标","render":function(data,type,full){
	                     return " <i class='"+data+"'></i>"}},
		             {"data": "MenuUrl" ,"title":"菜单地址"},
		             {"data": "MenuCreateDate" ,"title":"创建时间"},
	                 {"data": "MenuCreatePerson","title":"创建者"},
	                 {"data": "MenuUpdateDate","title":"更新时间"},
	                 {"data": "MenuUpdatePerson","title":"更新者"},
	                 {"data": "MenuUpMove","title":"上移","defaultContent": 
              	   		'<a href="javascript:void(0)" onclick="MENUITEM.upMove(this)"><i class="fa fa-arrow-up"></i></a>'},
              	   	 {"data": "MenuDownMove","title":"下移","defaultContent": 
              	   	 '<a href="javascript:void(0)" onclick="MENUITEM.downMove(this)"><i class="fa fa-arrow-down"></i></a>'},
	                 {"data": "MenuRemove","title":"删除","defaultContent": 
                	  '<a href="javascript:void(0)" onclick="MENUITEM.remove(this);">删除</a>'},
                	 {"data": "MenuUpdate","title":"详细","defaultContent": 
                	  '<a href="javascript:void(0)" onclick="MENUITEM.editor(this);">详情</a>'}
	             ]
	            ,"treeGrid": {
	                'left': 10,
	                'expandIcon': '<span><i class="fa fa-caret-right" aria-hidden="true"></i></span>',
	                'collapseIcon': '<span><i class="fa fa-caret-down" aria-hidden="true"></i></span>'
	            }
	        });
	      
	    };	
	    var addNew=function(){
	  		$("#menuItemForm").validate({
	              invalidHandler: function(event, validator) { 
	              	return false; 
	              },
	              submitHandler: function(form) {
	  				  $.ajax({
	  					url : basePath+'/admin/menus/menuItem/COM_MENUITEM_SAVE',
	  					type : 'POST',
	  					dataType : 'json',
	  					data : $(form).serialize(),
	  					success : function(data) {
	  						if (data.result) {
	  							toastrs.success(data.message);
	  							MENUITEM.reload();
	  							MENUITEM.closeDialog();
	  						} else {
	  							toastrs.error(data.message);
	  						}
	  					}
	  				});
	              	 return false;
	              }
	  		});
	    };
	return{
		init:function(){
			initTable("menuItemTable");
		},
		
	 showDialog : function() {
			$("#add-menuItem-modal").modal({
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
			$("#add-menuItem-modal").modal('hide');
		},
		reload:function(){
			var table = $('#menuItemTable').DataTable();
			table.ajax.reload( null, false );
			
		},
		editor:function(obj){
			event.stopPropagation();
			var menuId=0;
			if(obj!=undefined){
				var table = $('#menuItemTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var menuId=data[0].MenuId;
			}
			$.ajax({
				url:basePath+"/admin/menus/menuItem/menuItemAddPage",
				dataType:"HTML",
				data:{"menuId":menuId},
				success:function(data){
					$(".add-new-menuItem").html(data);
					addNew();
					MENUITEM.showDialog();
				}
			})
		},
		remove:function(obj){
			event.stopPropagation();
			if(confirm("是否删除!")){
				var table = $('#menuItemTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var menuId=data[0].MenuId;
				$.ajax({
					url:basePath+"/admin/menus/menuItem/COM_MENUITEM_REMOVE",
					dataType:"JSON",
					data:{"menuId":menuId},
					success:function(data){
						if(data.result){
							toastrs.success(data.message);
						}else{
							toastrs.error(data.message);
						}
						MENUITEM.reload();
					}
				})
			}
		},
		upMove:function(obj){
			event.stopPropagation();
			var table = $('#menuItemTable').DataTable();
			var tr=$(obj).parents("tr");
			var data = table.rows(tr).data();
			var menuId=data[0].MenuId;
			$.ajax({
				url:basePath+"/admin/menus/menuItem/COM_MENUITEM_UPMOVE",
				dataType:"JSON",
				data:{"menuId":menuId},
				success:function(data){
					 tr.prev().insertAfter(tr);
				}
			})
			
		    
		},
		downMove:function(obj){
			event.stopPropagation();
			var table = $('#menuItemTable').DataTable();
			var tr=$(obj).parents("tr");
			var data = table.rows(tr).data();
			var menuId=data[0].MenuId;
			$.ajax({
				url:basePath+"/admin/menus/menuItem/COM_MENUITEM_DOWNMOVE",
				dataType:"JSON",
				data:{"menuId":menuId},
				success:function(data){
					 tr.next().insertBefore(tr);
				}
			})
		},
    	addRelationship:function(){
    		saveRelationship();
    	}
	}
}();