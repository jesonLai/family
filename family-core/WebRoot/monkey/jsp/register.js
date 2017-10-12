//注册
var register = function() {
	   var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	   var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	   var csrfToken = $("meta[name='_csrf']").attr("content");
	   var headers = {};
	   headers[csrfHeader] = csrfToken;
	return {
		init : function(basePath) {
			$("#memberForm").validate({
				errorElement : 'div',
				errorClass : 'alert alert-danger', 
				errorPlacement: function(error, element) { //错误信息位置设置方法
//					error.after( element.parent().next()); 
					
				},
				invalidHandler : function(event, validator) { 
					return false;
				},
				rules : {
					userAccount : {
						required : true,
						 remote:{
			               	 	url:basePath+"/front/CHECK_USERACCOUNT",
			               	    type: "post",
			               	    dataType: "json", 
			               	    headers: headers,
			               	    data: {
			               	    	userAccount:function(data){
	                    	    		 return $("#userAccount").val();
	                    	    	}
			               	    }
						 }
						
					},
					userPassword : {
						required : true,
						minlength : 6
					},
					surePassword : {
						required : true,
						minlength : 6,
						equalTo : "#userPassword"
					},
					captcha:{
						required : true,
						remote:{
		               	 	url:basePath+"/isCaptcha",
		               	    type: "post",
		               	    dataType: "json", 
		               	    headers: headers,
		               	    data: {
		               	    	captchaVal:function(data){
                    	    		 return $("#captcha").val();
                    	    	}
		               	    }
						}
					}
				},
				messages : {
					userAccount : {
						required : "请输入登录账号",
						remote:"登录名称已经被注册"
					},
					userPassword : {
						required : "请输入密码",
						minlength : "密码长度不能小于 6个字母"
					},
					surePassword : {
						required : "请输入确认密码",
						minlength : "密码长度不能小于 6个字母",
						equalTo : "两次密码输入不一致"
					},
					captcha:{
						required : "请输入验证码",
						remote:"验证码输入错误"
					}
				},
				submitHandler : function(form) {
					$.ajax({
						url : basePath + '/front/COM_REGISTER_SAVE',
						type : 'POST',
						dataType : 'json',
						headers: headers,
						data : $(form).serialize(),
						success : function(data) {
							$("#btn-hin").children("q").html(data.message);
							$("#btn-hin").parent().css("display","block");
						}
					});
					return false;
				}
			});
		},
		changeCaptcha:function(obj,basePath){
			var timestamp=new Date().getTime();
			var captcha=$(obj);
			captcha.attr("src",basePath+"/SimpleCaptcha"+"?d="+timestamp);
		}
	}

}();