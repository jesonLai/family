var PERSONS=function(){
	 var testEmail=function(){
	    	$("#emailForm").validate({
				errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            rules: {
	            	email:{
						 required: true
					}
	            },
	            messages: {
	            	email:{
	            		required:"收件邮箱不能为空"
					},
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
						url:basePath+'/admin/menus/email/TEST',
						type:'POST',
						dataType:'json',
						data:$(form).serialize(),
						success:function(data){
							toastrs.success(data.message)
						}
					});
	            	 return false;
	            }
			});
	    }
	return {
		init:function(){
			PERSONS.settingEmail();
//			var btnCust = '<button type="button" class="btn btn-default" title="上传" ' + 
//		     'onclick="alert(\'Call your custom code here.\')">' +
//		     '<i class="glyphicon glyphicon-tag"></i>' +
//		     '</button>'; 

//		$("#avatar").fileinput({
//		uploadUrl: 'admin/menus/users/headImage',
//		    language: 'zh', //设置语言
//		    overwriteInitial: true,
//		  dropZoneEnabled: false,//是否显示拖拽区域
////		     minImageWidth: 50, //图片的最小宽度
////		     minImageHeight: 50,//图片的最小高度
////		     maxImageWidth: 1000,//图片的最大宽度
////		     maxImageHeight: 1000,//图片的最大高度
////		     maxFileSize: 1500,
//		    showClose: false,
//		    showCaption: false,
//		    showZoom:false,
//		    showDrag:false,
//		    showUpload:true,
//		    browseLabel: '',
//		    removeLabel: '',
//		    browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
//		    removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
//		    removeTitle: '取消或重新选择',
//		    elErrorContainer: '#kv-avatar-errors',
//		    msgErrorClass: 'alert alert-block alert-danger',
//		    defaultPreviewContent: '<img src="'+$("#currHeadImg").attr("src")+'" alt="头像" style="width:160px">',
////		     layoutTemplates: {main2: '{preview} ' +  btnCust + ' {remove} {browse}'},
//		    allowedFileExtensions: ["jpg", "png", "gif"]
//		    
//		    
//		    
//		    
//		});
//		$("#avatar").on("fileuploaded", function (event, data, previewId, index) {
//			var result = data.response.result;
//			
//			if (result != undefined&&result) {
//				$("#currHeadImg").attr("src",data.response.imgPath);
//				$("#h_Img").attr("src",data.response.imgPath);
//				toastrs.success("上传成功!");
//		    }else{
//		    	toastrs.error(data.response.message);
//		        return;
//		    }
//		 });
		
		
		
		
		 $("#upload_img").on("click",function(){
			 var ul=$(".head-ul");
			 var tab=$("#person_tab");
			 
			 ul.children().removeClass("active");
			 ul.children(":eq(1)").addClass("active")
			 tab.children().removeClass("active");
			 tab.children(":eq(1)").addClass("active")
		 })
		 $("#update_password").on("click",function(){
			 var ul=$(".head-ul");
			 var tab=$("#person_tab");
			 
			 ul.children().removeClass("active");
			 ul.children(":eq(2)").addClass("active")
			 tab.children().removeClass("active");
			 tab.children(":eq(2)").addClass("active")
		 });
		 //密码修改
		 $("#updatePWD").validate({
			 	errorElement: 'span', 
	            errorClass: 'help-block', 
	            invalidHandler: function(event, validator) {
	            	return false; 
	            },
	            	
	            highlight: function(element) { 
	                $(element).closest('.form-group').addClass('has-error'); 
	            },

	            success: function(label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function(error, element) {
	                error.insertAfter(element.next());
	            },
	            rules:{
		            oldPwd:{
		            	required:true
		            },
		            newOnePwd:{
		            	required:true,
		            	minlength:6
		            },
		            newTowPwd:{
		            	required:true,
		            	minlength:6,
		            	equalTo:"#newOnePwd"
		            	
		            }
	            },
	            messages: {
	            	 newOnePwd:{
	 	            	required:"请输入密码",
	 	            	minlength: "密码长度不能小于 6个字母"
	 	            	
	 	            },
	            	newTowPwd:{
	            		required:"请再次输入密码",
	            		minlength: "密码长度不能小于 56个字母",
	            		equalTo:"两次密码输入不一致"
	            	}
	            },
				 submitHandler:function(form){
					 $.ajax({
						 url:basePath+"/admin/menus/users/login/COM_UPDTAE_PWD",
						 type:"POST",
						 data:$(form).serialize(),
						 dataType:"JSON",
						 success:function(data){
							 if(data.result){
								 toastrs.success(data.message);
							 }else{
								 toastrs.error(data.message);
							 }
						 }
					 })
					 return false;
				 }
		 });
		},
		settingEmail:function(){
			//密码修改
			 $(".setting-email").validate({
				 	errorElement: 'span', 
		            errorClass: 'help-block', 
		            invalidHandler: function(event, validator) {
		            	return false; 
		            },
		            	
		            highlight: function(element) { 
		                $(element).closest('.form-group').addClass('has-error'); 
		            },

		            success: function(label) {
		                label.closest('.form-group').removeClass('has-error');
		                label.remove();
		            },

		            errorPlacement: function(error, element) {
		                error.insertAfter(element.next());
		            },
		            rules:{
		            	host:{
			            	required:true
			            },
			            protocol:{
			            	required:true
			            },
			            port:{
			            	required:true
			            	
			            },
			            from:{
			            	required:true
			            },
			            pwd:{
			            	required:true
			            }
			            
		            },
		            messages: {
		            	host:{
		 	            	required:"请输入邮箱服务地址"
		 	            },
		 	           protocol:{
		            		required:"请输入协议"
		            	},
		            	port:{
		            		required:"请输入端口号"
		            	},
		            	from:{
		            		required:"请输入账号"
		            	},
		            	pwd:{
		            		required:"请输入密码"
		            	}
		            },
					 submitHandler:function(form){
						 $.ajax({
							 url:basePath+"/admin/menus/emailParameter/COM_EMAILPARAMETER_SAVE",
							 type:"POST",
							 data:$(form).serialize(),
							 dataType:"JSON",
							 success:function(data){
								 if(data.result){
									 toastrs.success(data.message);
								 }else{
									 toastrs.error(data.message);
								 }
							 }
						 })
						 return false;
					 }
			 });
		},
		showTestEmail:function(){
			$("#email-test").modal('show').draggable({   
			    handle: '.modal-header',
			    cursor: 'move',   
			    refreshPositions: false  
			}).css({
				width:'50%'
			});
			testEmail();
		},
		headImg:function(){
			var form=document.getElementById("headImageForm");
				var formData=new FormData(form);
				$.ajax({
					url:basePath+'/admin/menus/users/headImage',
					type : 'POST',
					dataType : 'json',
					data : formData,
					  async: false,  
			          cache: false,  
			          contentType: false,  
			          processData: false, 
					success:function(data){
						var result = data.result;
						if (result != undefined&&result) {
							$("#currHeadImg").attr("src",basePath+"/"+data.imgPath);
							$("#h_Img").attr("src",basePath+"/"+data.imgPath);
							$("#hImg").attr("src",basePath+"/"+data.imgPath);
							$("#headImageName").val(data.headImageName)
							$("#headImageFolder").val(data.headImageFolder)
							toastrs.success("上传成功!");
					    }else{
					    	toastrs.error(data.message);
					        return;
					    }
					}
				})
		}
	}
}()