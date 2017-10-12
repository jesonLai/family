/**
 * 权限资源
 */
var FUNCTIONS = function() {
	 var initTable = function (tableId,authorityId) {
			var table=$("#"+tableId);
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
	        	 "data": function ( d ) {
	                 d.authorityId =authorityId;
	             },
	        	 "type":"POST"
	            },
	            "columns": [
                    {"data":"functionId","title":"编号","visible":false},
		            { "data": "functionName","title":"名称"},
		            { "data": "functionUrl","title":"地址"},
		            { "data": "functionCreateDate","title":"创建时间"},
		            { "data": "functionCreatePerson","title":"创建者"},
		            { "data": "functionUpdateDate","title":"更新时间"},
		            { "data": "functionUpdatePerson","title":"更新者"},
		            { "data": "functionremove","title":"删除","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="FUNCTIONS.remove(this);">删除</a>' },
	                { "data": "functionremove","title":"详情","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="FUNCTIONS.editor(this);">详情</a>' }

	             ] ,"treeGrid": {
		                'left': 10,
		                'expandIcon': '<span><i class="fa fa-caret-right" aria-hidden="true"></i></span>',
		                'collapseIcon': '<span><i class="fa fa-caret-down" aria-hidden="true"></i></span>'
		            }
	        });
	    
	      
	    };
	    var addNew=function(){
	  		$(".function-add-new-form").validate({
	              invalidHandler: function(event, validator) { //display error alert on form submit  
	              	return false; 
	              },
	              submitHandler: function(form) {
	  				  $.ajax({
	  					url : basePath+'/admin/menus/function/COM_FUNCTIONS_SAVE',
	  					type : 'POST',
	  					dataType : 'json',
	  					data:$(form).serialize(),
	  					success : function(data) {
	  						if(data.result){
	  							toastrs.success(data.message);
	  							AUTHORITY.closeDialog();
	  						}else{
	  							toastrs.error(data.message)
	  						}
	  						FUNCTIONS.reload();
	  					}
	  				});
	              	 return false;
	              }
	  		});
	    };
	    var saveRelationship=function(){
	    	var table = $('#functionTable').DataTable();
			var datas=table.rows('.active').data();
			var insFunctionIds=[];
			var delFunctionIds=[];
			for (var k = 0; k < initFunctionIds.length; k++) {
				delFunctionIds.push(parseInt(initFunctionIds[k]))
			}
			for (var i = 0; i < datas.length; i++) {
				if(delFunctionIds.contains(datas[i].functionId)){
					delFunctionIds.remove(datas[i].functionId)
				}else{
					insFunctionIds.push(datas[i].functionId);
				}
			}
			$.ajax({
				url : basePath+'/admin/menus/function/COM_AUTHORITY_FUNCTION_RELATIONSHIP',
				type : 'POST',
				data:{"authorityId":authorityVal,"delFunctionIds":delFunctionIds,"insFunctionIds":insFunctionIds},
				dataType:'JSON',
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					refreshing(authorityVal)
				}
		  });
			
	    };
	    var refreshing=function(authorityVal){
	    	$.ajax({
	    		url:basePath+'/admin/menus/authority/authorityFunctionListPage',
	    		Type:'JSON',
	    		dataType:'HTML',
	    		success:function(data){
	    			$("#function_operation").html(data);
	    			FUNCTIONS.init(authorityVal);
	    		}
	    	})
	    }
	    return{
	    	init:function(authorityId){
	    		initTable("functionTable",authorityId);
	    	},
	    	reload:function(){
	    		var table=$("#functionTable").DataTable();
	    		table.ajax.reload( null, false );
	    	},
	    	showDialog : function() {
				$("#add-new-function").modal({
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
			editor:function(obj){
				event.stopPropagation();
				var menuId=0;
				if(obj!=undefined){
					var table = $('#functionTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var functionId=data[0].functionId;
				}
				$.ajax({
					url:basePath+"/admin/menus/function/functionAddPage",
					dataType:"HTML",
					data:{"functionId":functionId},
					success:function(data){
						$(".add-new-function").html(data);
						addNew();
						FUNCTIONS.showDialog();
					}
				})
			},
	    	reloadInit:function(authorityId){
	    		refreshing(authorityId);
	    	},
	    	saveRelationship:function(){
	    		var activeVal=$(".authoritys").find(".active").find(":radio").val();
	    		
	    		$.ajax({
	    			url:basePath+'/admin/menus/function/COM_AUTHORITY_FUNCTION_RELATIONSHIP',
	    			type:'POST',
	    			dataType:'JSON',
	    			data:{"authorityId":activeVal},
	    			success:function(){
	    				//不作提示
	    			}
	    		})
	    		
	    	},
	    	addRelationship:function(){
	    		saveRelationship();
	    	},
			remove:function(obj){
				event.stopPropagation();
				if(confirm("是否删除!")){
					var table = $('#functionTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var functionId=data[0].functionId;
					$.ajax({
						url : basePath+'/admin/menus/authority/COM_AUTHORITY_FUNCTION_REMOVE',
						type : 'POST',
						dataType : 'json',
						data : {"functionId":functionId},
						success : function(data) {
							if(data.result){
								toastrs.success(data.message);
							}else{
								toastrs.error(data.message);
							}
							FUNCTIONS.reload();
						}
					});
				}
			}
	    };
}();