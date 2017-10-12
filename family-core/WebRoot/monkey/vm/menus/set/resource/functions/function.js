//如是父功能【所属父功能】这一项为空 如不是菜单需要做对应操作
var functions=function(){
	var AddNew=function(){
		$(".function-add-new-form").validate({
			errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            debug:true,
            rules: {
            	functionName: {
                    required: true
                },
                functionUrl: {
                    required: true
                }
	         },
            
            messages: {
            	functionName: {
                    required: "请输入功能名称！",
                },
                functionUrl:{
					 required: "请输入功能地址！",
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
					url:'menus/resources/functions/functionAddNew',
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
		$(".function-update-old-form").validate({
				errorElement: 'span', //default input error message container
		        errorClass: 'help-block', // default input error message class
		        debug:true,
		        rules: {
	            	functionName: {
	                    required: true
	                },
	                functionUrl: {
	                    required: true
	                }
		         },
	            
	            messages: {
	            	functionName: {
	                    required: "请输入功能名称！",
	                },
	                functionUrl:{
						 required: "请输入功能地址！",
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
						url:'menus/resources/functions/functionUpdate',
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
				url:'menus/resources/functions/functionUpdateData',
				data:{'functionId':id},
				type:'POST',
				success:function(data){
					$(".update-old-function").html(data);
					$('#update-old-function-div').modal('show');
					$('.function-update-old-form').find("input[name=functionId]").val(id);
					update(id);
				}
			});
		},//	/user/uploadHeadImg
		remove:function(obj,id){
			if(window.confirm("是否删除该项")){
				$.ajax({
					url:'menus/resources/functions/functionRemove',
					data:{'functionId':id},
					type:'POST',
					success:function(data){
						toastrs.success(data.message)
						if(data.check){
							$(obj).parents("tr").remove();
						}
					}
				});
			}
			
		}
	};
}();