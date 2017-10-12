
var users=function(){
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
	                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json?v=20160719001"
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
	        	 "url":basePath+"/admin/menus/users/usersList",
	        	 "type":"POST"
	            },
	            "columns": [
		            { "data": "userInfoId","visible": false,"searchable": false},
	                { "data": "userName" },
	                { "data": "userFaterName" },
	                { "data": "userSex"},
	                { "data": "userPhone"},
	                { "data": "userStatus"},
	                { "data": "createDate"},
	                { "data": "userDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="users.detail(this);">详细</a>'},
	                { "data": "userDel","visible":isSystem,"defaultContent": 
	                	'<a href="javascript:void(0)" onclick="users.remove(this);">删除</a>'}
                ]
	        });
	    };
	    function movieFormatResult(movie) {
            return movie.text;
        }

        function movieFormatSelection(movie) {
        	$("#fatherName").val(movie.id);
        	$("#userFatherAge").val(movie.userAge);
        	$("#userFatherBornDate").val(movie.userBornDate);
        	$("#userFatherSpouseName").val(movie.spouseName);
            return movie.text;
        }
	var select=function(){
			$("#fatherName").select2({
	            placeholder: '父亲姓名',
	            minimumInputLength: 1,
	            ajax: {
	                url: basePath+"/admin/menus/users/fatherName",
	                type:'post',
	                dataType: 'json',
	                data: function (term, page) {
	                    return {
	                        q: term, // search term
	                        page_limit: 10,
	                        
	                    };
	                },
	                results: function (data, page) { 
	                    return {
	                        results: data
	                    };
	                },escapeMarkup: function (m) {
		                return m;
		            }
	            }, 
	            initSelection: function (element, callback) {
	            	  var id = $(element).val();
	                  if (id !== "") {
	                      $.ajax({
	                    	  url:basePath+"/admin/menus/users/initFatherName", 
	                    	  data:{"id":id},
	                          dataType: "json",
	                          success:function(data){
	                        	  callback(data);
	                          }
	                      });
	                  }
	            },
	            formatResult: movieFormatResult, 
	            formatSelection: movieFormatSelection, 
	            dropdownCssClass: "bigdrop" // 
	        });
	}	
	    
	var AddNew=function(){
		$(".user-add-new-form").validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            rules: {
				userName:{
					 required: true
				},
				userEmail:{
					 required: false,
					 remote:{
                    	 url: basePath+"/admin/menus/users/checkUserEmail",
                    	    type: "post",
                    	    dataType: "json",   
                    	    data: {
                    	    	userInfoId:function(data){
                    	    		var userInfoId=$("#userInfoId").val();
                    	    		if(isEmpty(userInfoId)){
                    	    			userInfoId=0;
                    	    		}
                    	    		 return userInfoId;
                    	    	},
                    	    	userEmail: function(data) {
                    	            return $("#userEmail").val();
                    	        }
                    	    }
					 }
				},
				userQq:{
					 required: false,
					 remote:{
	                   	 url: basePath+"/admin/menus/users/checkUserQq",
	                   	    type: "post",
	                   	    dataType: "json",   
	                   	    data: {
	                   	    	userInfoId:function(data){
	                   	    		var userInfoId=$("#userInfoId").val();
                    	    		if(isEmpty(userInfoId)){
                    	    			userInfoId=0;
                    	    		}
                    	    		 return userInfoId;
	                   	    	},
	                   	    	userQq: function(data) {
	                   	            return $("#userQq").val();
	                   	        }
	                   	    }
	                   }
				},
				
	            userWeixin:{
					 required: false,
					 remote:{
	                	 url: basePath+"/admin/menus/users/checkUserWeixin",
	                	    type: "post",
	                	    dataType: "json",   
	                	    data: {
	                	    	userInfoId:function(data){
	                	    		var userInfoId=$("#userInfoId").val();
                    	    		if(isEmpty(userInfoId)){
                    	    			userInfoId=0;
                    	    		}
                    	    		 return userInfoId;
	                	    	},
	                	    	userWeixin: function(data) {
	                	            return $("#userWeixin").val();
	                	        }
	                	    }
		                }
		        },
				 userPhone:{
		            	required: false,
						 remote:{
			               	 	url: basePath+"/admin/menus/users/checkUserPhone",
			               	    type: "post",
			               	    dataType: "json",   
			               	    data: {
			               	    	userInfoId:function(data){
			               	    		var userInfoId=$("#userInfoId").val();
	                    	    		if(isEmpty(userInfoId)){
	                    	    			userInfoId=0;
	                    	    		}
	                    	    		 return userInfoId;
	                    	    	},
			                        userPhone: function(data) {
			               	            return $("#userPhone").val();
			               	        }
			               	    }
						 }
		           }
		            
            },
           
            messages: {
				userEmail:{
					remote:"邮箱已经被注册！"
				},
				userQq:{
					remote:"QQ号已经被注册！"
				},
				userWeixin:{
					remote:"微信号已经被注册！"
				},
				userPhone:{
					remote:"手机号已经被注册！"
				}
            },
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
					url:basePath+'/admin/menus/users/userAddNew',
					type:'POST',
					dataType:'json',
					data:$(form).serialize(),
					success:function(data){
						if(data.result){
							toastrs.success(data.message)
							users.reload();
							users.closeDialog();
						}else{
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
			initTable("usersTable");
		},
		reload:function(){
			var table = $('#usersTable').DataTable();
			table.ajax.reload( null, false );
			
		},
		showDialog:function(){
			$("#add-new-form").modal('show').draggable({   
			    handle: '.modal-header',
			    cursor: 'move',   
			    refreshPositions: false  
			}).css({
				width:'50%'
			});
			select();
		},
		showDialogImpExcel:function(){
			$("#add-new-excel").modal({
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
		closeDialogImpExcel:function(){
			$("#add-new-excel").modal('hide');
		},
		closeDialog:function(){
			$("#add-new-form").modal('hide');
		},
		addUser:function(){
			$.ajax({
				url:basePath+'/admin/menus/users/userUpdateData',
				type:'POST',
				data:{'userInfoId':""},
				success:function(data){
					$(".add-new-user").html(data);
					AddNew();
					users.showDialog();
					
				}
			});
		},
		detail:function(obj){
			var table = $('#usersTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var id=data[0].userInfoId;
			$.ajax({
				url:basePath+'/admin/menus/users/userUpdateData',
				data:{'userInfoId':id},
				type:'POST',
				success:function(data){
					$(".add-new-user").html(data);
					AddNew();
					users.showDialog();
				}
			});
		},
		formattingSelect:function(){
			userFormattingSelect()
		
			 
		},
		remove:function(obj){
			if(confirm("与之关联的人员将解除关系,是否删除!")){
				var table = $('#usersTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var id=data[0].userInfoId;
				$.ajax({
					url : basePath+'/admin/menus/users/COM_USERINFO_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"userInfoId":id},
					success : function(data) {
						if(data.result){
							toastrs.success(data.message);
						}else{
							toastrs.error(data.message);
						}
						users.reload();
					}
				});
			}
		},
		batchExcel:function(){
			$.ajax({
				url : basePath+'/admin/menus/users/usersExcelImportPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".batch-excel-import").html(data);
					users.showDialogImpExcel();
				}
			});
		},
		getExcelData:function(){
			
			var formData = new FormData(document.forms.namedItem("excelFile"));  
			
			$.ajax({
				url : basePath+'/admin/menus/users/usersDataExcelImportPage',
				type : 'POST',
				dataType : 'html',
				data :formData,
				async: false,  
		        cache: false,  
		        contentType: false,  
		        processData: false,  
				success : function(data) {
					$(".usersTable").html(data);
					users.showDialogImpExcel();
				}
			});
			 
		},
		saveExcel:function(){
			$.ajax({
				url : basePath+'/admin/menus/users/COM_BATCH_USER_EXCEL',
				type : 'POST',
				dataType : 'JSON',
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					users.reload();
					users.closeDialogImpExcel();
				}
			});
		},
		dowloadExcel:function(){
			var form=$("<form>");
			form.attr("style","display:none");
			form.attr("target","excelForm");
			form.attr("method","post");
			form.attr("action",basePath+'/admin/menus/users/COM_DOWLOAD_EXCEL_USERINFO');
			var input=$("input");
			input.attr("type","hidden");
			input.attr("name",$("#token").attr("name"));
			input.attr("value",$("#token").val());
			form.append(input);
			var iframe=$('<iframe>');
			iframe.attr("style","display:none");
			iframe.attr("name","excelForm");
			iframe.append(form)
			
			$(iframe).remove();
			$('body').append(iframe);
			form.submit();
			
			
		}
		
	};
}();