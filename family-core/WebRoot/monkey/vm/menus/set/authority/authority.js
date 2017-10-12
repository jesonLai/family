/**
 * 权限资源
 */
 var AUTHORITY = function() {
	 var addNewAuthority=function(){
  		$(".authority-form").validate({
  			errorElement: 'span', 
            errorClass: 'help-block',
            invalidHandler: function(event, validator) { //display error alert on form submit   
            	return false; 
            },

            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.next());
            },
              rules: {
            	  authorityId:{
            		  required: true,
            	  },
            	  authorityName:{
  					 required: true,
  					 remote:{
  						 url: basePath+"/admin/menus/authority/checkAuthorityName",
                    	    type: "post",
                    	    dataType: "json",   
                    	    data: {
                    	    	authorityId:function(data){
                    	    		var authorityId=$("#authorityId").val();
                    	    		if(isEmpty(authorityId)){
                    	    			authorityId=0;
                    	    		}
                    	    		 return authorityId;
                    	    	},
                    	    	authorityName: function(data) {
                    	            return $("#authorityName").val();
                    	        }
                    	    }
  					 }
  				  }
              },
              messages: {
	        	  authorityId:{
	        		 required: "权限编号不能为空",
	        	  },
                  authorityName:{
                 	required:'权限名称不能为空!',
  					remote:"权限名称已存在!"
  				 }
  			 },
              submitHandler: function(form) {
  				  $.ajax({
  					url : basePath+'/admin/menus/authority/COM_AUTHORITY_SAVE',
  					type : 'POST',
  					dataType : 'json',
  					data:$(form).serialize(),
  					success : function(data) {
  						if(data.result){
  							toastrs.success(data.message);
  							AUTHORITY.reload();
  							$("#add-modal-authority").modal('hide');
  						}else{
  							toastrs.error(data.message)
  						}
  					}
  				});
              	 return false;
              }
  		});
    }
	 var addNewAuthorityGroup=function(){
	  		$(".authority-group-form").validate({
	  			errorElement: 'span', 
	            errorClass: 'help-block',
	            invalidHandler: function(event, validator) { //display error alert on form submit   
	            	return false; 
	            },

	            highlight: function(element) { // hightlight error inputs
	                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function(label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
	            errorPlacement: function(error, element) {
	                error.insertAfter(element.next());
	            },
	              submitHandler: function(form) {
	  				  $.ajax({
	  					url : basePath+'/admin/menus/authority/COM_AUTHORITY_GROUP_SAVE',
	  					type : 'POST',
	  					dataType : 'json',
	  					data:$(form).serialize(),
	  					success : function(data) {
	  						if(data.result){
	  							toastrs.success(data.message);
	  							AUTHORITY.reload();
	  							$("#add-modal-authority-group").modal('hide');
	  						}else{
	  							toastrs.error(data.message)
	  						}
	  					}
	  				});
	              	 return false;
	              }
	  		});
	    }
	 var initTable = function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      table.dataTable({
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
	            "dom": '<"toolbar">frtip',
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/authority/authorityList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		             {"data": "authorityId","title":"权限编号"},
		             {"data": "authorityName","title":"权限名称"},
		             {"data": "createName","title":"权限创建者"},
		             {"data": "createDate","title":"权限创建时间"},
		             {"data": "updateName","title":"权限更新者"},
	                 {"data": "updateDate","title":"权限更新时间"},
	                 {"data": "authorityDesc","title":"权限详细"},
	                 {"data": "resourceType","title":"资源类型"},
	                 {"data": "resourceName","title":"资源名称"},
	                 {"data": "authorityRemove","title":"删除","defaultContent": 
	               	  '<a href="javascript:void(0)" onclick="AUTHORITY.delAuthority(this);">删除</a>'},
	               	 {"data": "authorityUpdate","title":"详情","defaultContent": 
	               	  '<a href="javascript:void(0)" onclick="AUTHORITY.editor(this);">详情</a>'},
	               	{"data": "authorityUpdate","title":"分配","defaultContent": 
	               	  '<a href="javascript:void(0)" onclick="AUTHORITY.allocation(this);">资源</a>'}
	             ]
	            ,"treeGrid": {
	                'left': 10,
	                'expandIcon': '<span><i class="fa fa-caret-right" aria-hidden="true"></i></span>',
	                'collapseIcon': '<span><i class="fa fa-caret-down" aria-hidden="true"></i></span>'
	            },"fnInitComplete": function (oSettings) {
	            	$("div.toolbar").html('<button class="btn green" onclick="AUTHORITY.editorAuthority()">新增权限<i class="fa fa-plus"></i></button>'
	            			+'<button class="btn green" onclick="AUTHORITY.editor()">新增权限组<i class="fa fa-plus"></i></button>');
	            }
	        });
	      
	    };	
	    
	    var menuTable=function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      table.dataTable({
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
	   		             {"data": "MenuItemIcon","title":"菜单图标"
	   		            	 ,"render":function(data,type,full){
	   		            		 return " <i class='"+data+"'></i>"
	   		            		}
	   		             },
//	   		             {"data": "MenuParent","title":"菜父级菜单"},
//	   		             {"data": "MenuUrl","title":"菜单地址"},
	   		             {"data": "MenuCreateDate","title":"创建时间"},
	   	                 {"data": "MenuCreatePerson","title":"创建者"},
	   	                 {"data": "MenuUpdateDate","title":"更新时间"},
	   	                 {"data": "MenuUpdatePerson","title":"更新者"},
	   	                 {"data": "MenuConfirm","title":"选项"
	   	                	 ,"defaultContent":'<a href="javascript:void(0)" onclick="AUTHORITY.menuConfirm(this);">确定</a>'
	   	                 },
	             ]
	            ,"treeGrid": {
	                'left': 10,
	                'expandIcon': '<span><i class="fa fa-caret-right" aria-hidden="true"></i></span>',
	                'collapseIcon': '<span><i class="fa fa-caret-down" aria-hidden="true"></i></span>'
	            }
	        });
	      
	    };	
	    var functionTable=function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      table.dataTable({
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
	          		 "url":basePath+"/admin/menus/function/functionList",
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
                        { "data":"functionId","title":"功能编号","visible":false},
    		            { "data": "functionName","title":"功能名称"},
    		            { "data": "functionUrl","title":"功能地址"},
    		            { "data": "functionCreateDate","title":"创建时间"},
    		            { "data": "functionCreatePerson","title":"创建者"},
    		            { "data": "functionUpdateDate","title":"更新时间"},
    		            { "data": "functionUpdatePerson","title":"更新者"},
	   	                { "data": "MenuConfirm","title":"选项"
	   	                	 ,"defaultContent":'<a href="javascript:void(0)" onclick="AUTHORITY.funConfirm(this);">确定</a>'
	   	                },
	             ]
	            ,"treeGrid": {
	                'left': 10,
	                'expandIcon': '<span><i class="fa fa-caret-right" aria-hidden="true"></i></span>',
	                'collapseIcon': '<span><i class="fa fa-caret-down" aria-hidden="true"></i></span>'
	            }
	        });
	      
	    };	
	    var allocationConfirm=function(authorityId,type,resourceId){
	    	$.ajax({
				url:basePath+'/admin/menus/authority/COM_AUTHORITY_ALLOCATION',
				type:'POST',
				data:{'authorityId':authorityId,"type":type,"resourceId":resourceId},
				success:function(data){
					if(data.result){
							toastrs.success(data.message);
							AUTHORITY.reload();
							$("#authority-resource").modal('hide');
					}else{
						toastrs.error(data.message)
					}
				}
			});
	    }
	    
	    return{
	    	init:function(){
	    		initTable('authorityCenter');
	    		menuTable('menuItemsTable');
	    		functionTable('functionTable');
	    		
	    	},
			reload:function(){
				var table = $('#authorityCenter').DataTable();
				table.ajax.reload( null, false );
				
			},
		   	showAllocationDialog : function() {
				$("#authority-resource").modal({
					backdrop: 'static',
					keyboard: false,
					show:true,
					width : '50%'
				}).draggable({
					handle : '.modal-header',
					cursor : 'move',
					refreshPositions : false
				})
		   	},
	    	allocation:function(obj){
	    		var table=new $.fn.dataTable.Api( '#authorityCenter' );
	    		var data = table.rows($(obj).parents("tr")).data();
				var id=data[0].authorityId;
				$("#authorityId").val(id);
	    		AUTHORITY.showAllocationDialog();
	    		
	    	},
	    	menuConfirm:function(obj){
	    		var table=new $.fn.dataTable.Api('#menuItemsTable');
	    		var authorityId=$("#authority-resource").find("#authorityId").val();
	    		var data = table.rows($(obj).parents("tr")).data();
	    		var id=data[0].MenuId;
	    		allocationConfirm(authorityId,"ARM",id)
	    	},
	    	funConfirm:function(obj){
	    		var table=new $.fn.dataTable.Api('#functionTable');
	    		var authorityId=$("#authority-resource").find("#authorityId").val();
	    		var data = table.rows($(obj).parents("tr")).data();
	    		var id=data[0].functionId;
	    		allocationConfirm(authorityId,"ARF",id)
	    	},
	    	showAddPage: function(){
	    		$("#add-modal-authority").modal({
					backdrop: 'static',
					keyboard: false,
					show:true,
					width : '50%'
				}).draggable({
					handle : '.modal-header',
					cursor : 'move',
					refreshPositions : false
				})
	    	},
	    	showAddGroupPage: function(){
	    		$("#add-modal-authority-group").modal({
					backdrop: 'static',
					keyboard: false,
					show:true,
					width : '50%'
				}).draggable({
					handle : '.modal-header',
					cursor : 'move',
					refreshPositions : false
				})
	    	},
	    	delAuthority:function(obj){
	    		var table=new $.fn.dataTable.Api('#authorityCenter');
	    		var data = table.rows($(obj).parents("tr")).data();
				var id=data[0].authorityId;
	    		$.ajax({
	    			url: basePath+'/admin/menus/authority/delete/authority',
	    			type:'POST',
	    			dataType:'JSON',
	    			data:{"authorityId":id},
	    			success:function(data){
	    				if(data.result){
							toastrs.success(data.message);
							AUTHORITY.reload();
						}else{
							toastrs.error(data.message)
						}
	    			}
	    		})
	    	},
	    	editorAuthority:function(){
	    		$.ajax({
	    			url: basePath+'/admin/menus/authority/get/add/authorityPage',
	    			type:'POST',
	    			dataType:'html',
	    			success:function(data){
	    				$(".add-new-authority").html(data);
	    				AUTHORITY.showAddPage();
	    				addNewAuthority();
	    			}
	    		})
	    	},
	    	editor:function(){
	    		$.ajax({
	    			url: basePath+'/admin/menus/authority/get/authorityGroup/page',
	    			type:'POST',
	    			dataType:'html',
	    			success:function(data){
	    				
	    				$(".add-new-authority-group").html(data);
	    				AUTHORITY.showAddGroupPage();
	    		$( "#authorityParentGroupName" ).autocomplete({
	    			minLength:0,
	    			 source: function (request, response) {  
	    		         var url = basePath+'/admin/menus/authority/get/add/authorityGroup/names';  
	    		         $.ajax({  
	    		             'url': url,  
	    		             dataType: 'json',  
	    		             success: function (dataObj) {  
	    		                 response(dataObj);  
	    		             }  
	    		         });  
	    		     }, 
	    		     focus: function (event, ui) {
	    		         $("#authorityParentGroupName").val(ui.item.label);
	    		         return false;
	    		     },
	    		     select: function (event, ui) {
	    		    	 $("#authorityParentGroupName").val(ui.item.label);
	    		         $("#authorityParentGroupId").val(ui.item.value);  
	    		     }  
	    		});
	    				addNewAuthorityGroup();
	    			}
	    		})
	    	}
	    
	    }
	    
}();