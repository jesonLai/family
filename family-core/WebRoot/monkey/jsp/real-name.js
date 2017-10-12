//##实名认证
var realName=function(){
	var isEmpty=function (str){
		if(typeof(str)!="undefined"&&str!=""&&str!="null"&&str!=null){
			return false;
		}else{
			return true;
		}
	} 
	   var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	   var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	   var csrfToken = $("meta[name='_csrf']").attr("content");
	   var headers = {};
	   headers[csrfHeader] = csrfToken;
	return{
		init:function(basePath){
			jQuery.validator.addMethod("userInfoList[0].userEmail",function(value,element){
				return true;
			},"请输入正确的邮箱！");
			$("#realNameForm").validate({
				errorElement: 'p', //default input error message container
	            errorClass: 'color-red', // default input error message class
				rules:{
					"userInfoList[0].userEmail":{
						 required : true,  
						 email:true,
						 remote:{
			            	 url:basePath+"/user/front/checkUserEmail",
			            	    type: "post",
			            	    dataType: "json",   
			            	    headers:headers,
			            	    data: {
			            	    	userInfoId:function(data){
			            	    		var userInfoId=$("#userChildrenId").val();
			            	    		if(isEmpty(userInfoId)){
			            	    			userInfoId=0;
			            	    		}
			            	    		 return userInfoId;
			            	    	},
			            	    	userEmail: function(data) {
			            	            return $("#userChildrenEmail").val();
			            	        }
			            	    }
						 }
					},
					"userInfoList[0].userBornDate":{
						required:false,
						dateISO:true
					},
					"userInfoList[0].userAge":{
						required:false,
						digits:true
					},
					"userInfoList[0].userName":{
						 required : true,
						 remote:{
	                    	 url: basePath+"/user/front/checkUserName",
	                    	    type: "post",
	                    	    dataType: "json",  
	                    	    headers: headers,
	                    	    data: {
	                    	    	userInfoId:function(data){
	                    	    		var userInfoId=$("#userChildrenId").val();
	                    	    		if(isEmpty(userInfoId)){
	                    	    			userInfoId=0;
	                    	    		}
	                    	    		 return userInfoId;
	                    	    	},
	                    	    	userName: function(data) {
	                    	            return $("#userChildrenName").val();
	                    	        }
	                    	    }
						 }
						 
					},
					"userInfoList[0].userQq":{
						 required: false,
						 remote:{
			               	 url:basePath+"/user/front/checkUserQq",
			               	    type: "post",
			               	    dataType: "json", 
			               	    headers: headers,
			               	    data: {
			               	    	userInfoId:function(data){
			               	    		var userInfoId=$("#userInfoId").val();
			            	    		if(isEmpty(userInfoId)){
			            	    			userInfoId=0;
			            	    		}
			            	    		 return userInfoId;
			               	    	},
			               	    	userQq: function(data) {
			               	            return $("#userChildrenQq").val();
			               	        }
			               	    }
			               }
					},
					
					"userInfoList[0].userWeixin":{
						 required: false,
						 remote:{
			            	 url:basePath+"/user/front/checkUserWeixin",
			            	    type: "post",
			            	    dataType: "json", 
			            	    headers: headers,
			            	    data: {
			            	    	userInfoId:function(data){
			            	    		var userInfoId=$("#userInfoId").val();
			            	    		if(isEmpty(userInfoId)){
			            	    			userInfoId=0;
			            	    		}
			            	    		 return userInfoId;
			            	    	},
			            	    	userWeixin: function(data) {
			            	            return $("#userChildrenWeixin").val();
			            	        }
			            	    }
			                }
			        },
			        "userInfoList[0].userPhone":{
			            	required: false,
							 remote:{
				               	 	url:basePath+"/user/front/checkUserPhone",
				               	    type: "post",
				               	    dataType: "json", 
				               	    headers: headers,
				               	    data: {
				               	    	userInfoId:function(data){
				               	    		var userInfoId=$("#userInfoId").val();
			                	    		if(isEmpty(userInfoId)){
			                	    			userInfoId=0;
			                	    		}
			                	    		 return userInfoId;
			                	    	},
				                        userPhone: function(data) {
				               	            return $("#userChildrenPhone").val();
				               	        }
				               	    }
							 }
			           },
					"userInfoList[1].userName":{
						 remote:{
	                    	 url: basePath+"/user/front/checkUserName",
	                    	    type: "post",
	                    	    dataType: "json", 
	                    	    headers: headers,
	                    	    data: {
	                    	    	userInfoId:function(data){
	                    	    		var userInfoId=$("#userParentId").val();
	                    	    		console.info(userInfoId)
	                    	    		if(isEmpty(userInfoId)){
	                    	    			userInfoId=0;
	                    	    		}
	                    	    		 return userInfoId;
	                    	    	},
	                    	    	userName: function(data) {
	                    	            return $("#userFatherName").val();
	                    	        }
	                    	    }
						 }
					},
					"userInfoList[1].userQq":{
						 required: false,
						 remote:{
			               	 url:basePath+"/user/front/checkUserQq",
			               	    type: "post",
			               	    dataType: "json",
			               	    headers: headers,
			               	    data: {
			               	    	userInfoId:function(data){
			               	    		var userInfoId=$("#userInfoId").val();
			            	    		if(isEmpty(userInfoId)){
			            	    			userInfoId=0;
			            	    		}
			            	    		 return userInfoId;
			               	    	},
			               	    	userQq: function(data) {
			               	            return $("#userFatherQq").val();
			               	        }
			               	    }
			               }
					},
					
					"userInfoList[1].userWeixin":{
						 required: false,
						 remote:{
			            	 url:basePath+"/user/front/checkUserWeixin",
			            	    type: "post",
			            	    dataType: "json",
			            	    headers: headers,
			            	    data: {
			            	    	userInfoId:function(data){
			            	    		var userInfoId=$("#userInfoId").val();
			            	    		if(isEmpty(userInfoId)){
			            	    			userInfoId=0;
			            	    		}
			            	    		 return userInfoId;
			            	    	},
			            	    	userWeixin: function(data) {
			            	            return $("#userFatherWeixin").val();
			            	        }
			            	    }
			                }
			        },
			        "userInfoList[1].userPhone":{
			            	required: false,
							 remote:{
				               	 	url:basePath+"/user/front/checkUserPhone",
				               	    type: "post",
				               	    dataType: "json",
				               	    headers: headers,
				               	    data: {
				               	    	userInfoId:function(data){
				               	    		var userInfoId=$("#userInfoId").val();
			                	    		if(isEmpty(userInfoId)){
			                	    			userInfoId=0;
			                	    		}
			                	    		 return userInfoId;
			                	    	},
				                        userPhone: function(data) {
				               	            return $("#userFatherPhone").val();
				               	        }
				               	    }
							 }
			           },
					"userInfoList[1].userBornDate":{
						required:false,
						dateISO:true
					},
					"userInfoList[1].userAge":{
						required:false,
						digits:true
					}
					
				},  
	            messages : {  
	            	"userInfoList[0].userName" : {
	                	required : "请输入姓名！",
	                	remote:"该名称已存在，请选择"
	                	
	                 },
					"userInfoList[0].userBornDate":{
						dateISO:"请输入有效的日期 (年-月-日)"
					},
	                 "userInfoList[0].userAge":{
	                	 digits:"请输入纯数字"
	                 },
	                "userInfoList[0].userEmail" : {
	                	required : "请输入邮箱",  
	                	email:"请输入正确邮箱地址"
	                 },
	                 "userInfoList[1].userName":{
	                	remote:"父亲名称已存在，请选择", 
	                 },
					"userInfoList[1].userBornDate":{
						dateISO:"请输入有效的日期 (年-月-日)"
					},
	                 "userInfoList[1].userAge":{
	                	 digits:"请输入纯数字"
	                 },
	                 "userInfoList[1].userEmail":{
	                	 required : "请输入邮箱",  
		                 email:"请输入正确邮箱地址"
					}
	            },  
				invalidHandler : function(event, validator) { 
					return false;
				},
				
				submitHandler : function(form) {
					$.ajax({
						url : basePath + '/front/REAL_NAME_SAVE',
						type : 'POST',
						dataType : 'json',
						headers: headers,
						data : $(form).serialize(),
						success : function(data) {
							alert(data.message);
						}
					});
					return false;
				}
			});
		}
	}
}();