//##注册审核
var register=function(){
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
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] 
	            ],
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/register/registerList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "userInfoId"},
		            { "data": "userName" },
		            { "data": "userSex"},
		            { "data": "userFather" },
	                { "data": "userPhone"},
	                { "data": "userEmail"},
	                { "data": "userBornDate"},
	                { "data": "userAge"},
	                { "data": "remove","searchable": false,"orderable": false}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
                 {"targets": [ -1 ],"data": "remove","defaultContent": 
                 	'<a href="javascript:void(0)" onclick="register.check(this);">查看</a>'}
	               
	            ],
	        });
	    };
	   var checkUserInfo=function(){
	    	  $(".check-user_info").validate({
					errorElement: 'span', //default input error message container
		            errorClass: 'help-block', // default input error message class
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
		            	if(confirm("确定审核")){
			            	$.ajax({
								url:basePath+'/admin/menus/register/checkRealName',
								type:'POST',
								dataType:'json',
								data:$(form).serialize(),
								success:function(data){
									if (data.result) {
										toastrs.success(data.message)
										var table = $('#checkRegisterTable').DataTable();
										table.ajax.reload();
									} else {
										toastrs.error(data.message);
									}
								}
							});
		            	}
		            	 return false;
		            }
				});
	    }
	  var checkNotThroughUserInfo=function(){
		  $.ajax({
				url:basePath+'/admin/menus/register/checkNotThroughUserInfo',
				type:'POST',
				dataType:'json',
				data:{"remarks":$("#remarks").val(),"userInfoId":$("#userInfoId").val()},
				success:function(data){
					if (data.result) {
						toastrs.success(data.message)
						var table = $('#checkRegisterTable').DataTable();
						table.ajax.reload();
					} else {
						toastrs.error(data.message);
					}
				}
			});
	  }
	return{
		init:function(){
			initTable('checkRegisterTable');
		},
		showDialog:function(){
			$("#register-check-form").modal('show').draggable({   
			    handle: '.modal-header',
			    cursor: 'move',   
			    refreshPositions: false  
			}).css({
				width:'50%'
			});
		},
		check:function(obj){
			var table = $('#checkRegisterTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var id=data[0].userInfoId;
			$.ajax({
				url:basePath+"/admin/menus/register/registerDetail",
				data:{"userInfoId":id},
				dataType:'html',
				success:function(data){
					$(".register-check-content").html(data);
					register.showDialog();
					checkUserInfo();
				}
			});
		},
		notCheck:function(){
			$("#register-feedback-form").modal('show').draggable({   
			    handle: '.modal-header',
			    cursor: 'move',  
			    
			    refreshPositions: false  
			}).css({
				width:'50%'
			});
//			checkNotThroughUserInfo();
		},
		sureNotCheck:function(){
			checkNotThroughUserInfo()
		}
	
	}
}();