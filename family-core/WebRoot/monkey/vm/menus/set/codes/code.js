var codes=function(){
	var AddNew=function(){
		$(".code-add-new-form").validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            debug:true,
            rules: {
            	codeChinese: {
                    required: true
                },
                codePrefix: {
                    required: true
                }
	         },
            
            messages: {
            	codeChinese: {
                    required: "请输入显示名称！",
                },
                codePrefix:{
					 required: "请输入操作名称！",
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
					url:basePath+'menus/codes/codeAddNew',
					type:'POST',
					dataType:'JSON',
					data:$(form).serialize(),
					success:function(data){
						toastrs.success(data.message)
					}
				});
            	 return false;
            }
		});
	};
	var update=function(id){
		$(".role-update-old-form").validate({
				errorElement: 'span', //default input error message container
		        errorClass: 'help-block', // default input error message class
		        debug:true,
		        rules: {
		        	roleName: {
		                required: true,
		                remote:{
		                	 url: basePath+"menus/roles/checkRoleName",
		                	    type: "post",
		                	    dataType: "json",   
		                	    data: {
		                	    	roleName: function(data) {
		                	            return $("#update_roleName").val();
		                	        },
									roleId:function(){
										return id;
									}
		                	    }
		                }
		            },
		            roleSysName: {
		                required: true,
		                remote:{
		                	 url: basePath+"menus/roles/checkRoleSysName",
		                	    type: "post",
		                	    dataType: "json",   
		                	    data: {
		                	    	roleSysName: function(data) {
		                	            return $("#update_roleSysName").val();
		                	        },
									roleId:function(){
										return id;
									}
		                	    }
		                }
		            }
		         },
		        
		        messages: {
		        	roleName: {
		                required: "请输入角色显示名称！",
		                remote:"角色显示名称已存在！"
		            },
		            roleSysName:{
						 required: "请输入角色操作名称！",
						 remote:"角色操作名称已存在！"
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
						url:basePath+'menus/roles/roleUpdateOld',
						type:'POST',
						dataType:'JSON',
						data:$(form).serialize(),
						success:function(data){
							toastrs.success(data.message)
						}
					});
		        	 return false;
		        }
			});
		};
		
	return{
		init:function(){
			AddNew();
		},
		update:function(obj,id){
			$.ajax({
				url:basePath+'menus/roles/roleUpdateData',
				data:{'roleId':id},
				type:'POST',
				success:function(data){
					$(".update-old-role").html(data);
					$('#update-old-role-div').modal('show');
					$('#role-update-old-form').find("input[name=userId]").val(id);
					update(id);
				}
			});
		},
		remove:function(obj,id){
			if(window.confirm("是否删除该项")){
				$.ajax({
					url:basePath+'menus/codes/codeRemove',
					data:{'codeId':id},
					type:'POST',
					success:function(data){
						toastrs.success(data.message)
						if(data.code){
							$(obj).parents("tr").remove();
						}
					}
				});
			}
			
		}
	};
}();