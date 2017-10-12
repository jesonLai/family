<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_paraeter" content="${_csrf.parameterName}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>个人信息</title>
<link href="<%=basePath%>/monkey/global/plugins/googleapis/css/family.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />

<link href="<%=basePath%>/monkey/pages/css/profile.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/monkey/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
	
<link rel="stylesheet" href="<%=basePath%>/front/css/headers/header1.css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=basePath%>/monkey/favicon.ico" />
<script src="<%=basePath%>/front/js/jquery.min.js"></script>
<script src="<%=basePath%>/front/js/jquery.validate.min.js" type="text/javascript"></script>
<style type="text/css">
#owmain_menu>li{float: left;}
</style>
</head>
<body>
<div class="testi-slide ">
		<div class="container">
		   <ul class="slides">
			<c:forEach items="${NAVIGATIONIMG}" var="IMG">
				<li class="" >
					<a href="${IMG.href}"> <img src="<%=basePath%>/${IMG.httpUrl}"style="max-width: 1180px;max-height: 354px;" > </a>
				</li>
			</c:forEach>
			
		   </ul>
		</div><!--flex slider testimonials end here-->
		  			
</div>
	<div class="container container-body">
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">个人信息</a></li>
			    <li></li>
			</ol>
		</div>
<!-- 	 <div id="message" class="alert alert-success"> -->
<!-- 					<button data-dismiss="alert" class="close">×</button> -->
<!-- 					保存成功 -->
<!-- 				</div> -->
		<div class="col-md-12">
			<div class="profile-sidebar " >
				<!-- PORTLET MAIN -->
				<div class="portlet light profile-sidebar-portlet">
					<!-- SIDEBAR USERPIC -->
					<div class="profile-userpic">
						<%-- <img src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg"
							class="img-responsive" id="currHeadImg" alt=""
							onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'" /> --%>
							<c:if test="${empty HEANDIMAGESRC}">
								<img class="img-responsive" id="currHeadImg" src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg" />
							</c:if>
							<c:if test="${not empty HEANDIMAGESRC}">
								<img class="img-responsive" id="currHeadImg" src="<%=basePath%>/${HEANDIMAGESRC}" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'"/>
							</c:if>
					</div>
					<!-- END SIDEBAR USERPIC -->
					<!-- SIDEBAR USER TITLE -->
					<div class="profile-usertitle">
						<div class="profile-usertitle-name">
							${person.authentication.principal.username}</div>
						<!-- <div class="profile-usertitle-job">普通用户</div> -->
					</div>
					<!-- END SIDEBAR USER TITLE -->
					<!-- SIDEBAR BUTTONS -->
					<div class="profile-userbuttons">
						<button type="button" class="btn btn-circle green-haze btn-sm" onclick="window.location.href='<%=basePath%>/user/uploadHeadImg.html'"
							id="upload_img">上传头像</button>
						<!-- <button type="button" class="btn btn-circle btn-danger btn-sm"
							id="update_password">修改密码</button> -->
					</div>
					<!-- END SIDEBAR BUTTONS -->
					<!-- SIDEBAR MENU -->
					<div class="profile-usermenu">
						<ul class="nav">
							<li><a href="<%=basePath%>"> <i class="icon-home"></i>
									主页
							</a></li>
							<li class="active"><a href="<%=basePath%>/user/center.html"> <i
									class="icon-settings"></i> 个人信息
							</a></li>
							<li><a href="<%=basePath%>/user/updatepwd.html"> <i
									class="icon-check"></i> 密码修改
							</a></li>
							<li><a href="<%=basePath%>/user/relationship.html"> <i
									class="icon-info"></i> 宗族关系
							</a></li>
							<li><a href="<%=basePath%>/user/front/real-name.html"> <i
									class="icon-info"></i> 信息认证
							</a></li>
						</ul>
					</div>
					<!-- END MENU -->
				</div>
			</div>
			<!--sidebar-->
			<div class="profile-content">
				<div class="row">
					<div class="col-md-12">
					
						<form autocomplete="off" id="personInfoForm" onsubmit="return false"
							method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
<input type="hidden" id="userInfoId" name="userInfoId" value="${user.userInfoId}" />
							<div class="portlet light ">
								<div class="portlet-title">
									<div class="caption caption-sm">
										<i class="icon-bar-chart theme-font hide"></i> <span
											class="caption-subject font-blue-madison bold uppercase">个人信息</span>
									</div>
									<div class="actions">
										<div class="btn-group btn-group-devided" data-toggle="buttons">
											<button type="submit" class="btn btn-transparent green btn-circle btn-md active" style="margin-right: 20px">
												提&nbsp;交
											</button> 
										</div>
									</div> 
								</div>
								<div class="portlet-body">
									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">真实姓名</label>
											</div>
											<div class="col-md-9">
												<div>
													 <input type="text" name="userName" id="userName" 
													 placeholder="姓名" class="form-control " value="${user.userName}" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">性别</label>
											</div>
											<div class="col-md-9">
													<select class="form-control edited" id="form_control_1" name="userSex">
				        								<option value="1" <c:if test="${USERINFO.userSex==1}">selected</c:if>>男</option>
				        								<option value="2" <c:if test="${USERINFO.userSex==2}">selected</c:if>>女</option>
				        								<option value="3" <c:if test="${USERINFO.userSex==3}">selected</c:if>>未知</option>
			        								</select>
											</div>
										</div>
									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">出生年月</label>
											</div>
											<div class="col-md-9">
												<div> <input type="text" name="userBornDate" 
														placeholder="出生年月" id="full_name" class="form-control"
														 value="<fmt:formatDate value="${user.userBornDate}" pattern="yyyy-MM-dd"  />">
												</div>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">年龄</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userAge"  value="${user.userAge}"
													placeholder="年龄" id="full_name" class="form-control ">
											</div>
										</div>

									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">世代</label>
											</div>
											<div class="col-md-9">
												<div> 
												<input type="text" name="userIdentityCard"  value="${user.userIdentityCard}"
														placeholder="世代" id="userIdentityCard"
														class="form-control ">
												</div>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">家庭住址</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="homeAddress"  value="${user.homeAddress}"
													placeholder="家庭住址" id="homeAddress" class="form-control ">
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">婚姻状况</label>
											</div>
											<div class="col-md-9">
												<div>
												<select class="form-control edited" id="form_control_1" name="maritalStatus">
				        								<option value="1" <c:if test="${USERINFO.maritalStatus==1}">selected</c:if>>未婚</option>
				        								<option value="2" <c:if test="${USERINFO.maritalStatus==2}">selected</c:if>>已婚</option>
				        							</select>
												</div>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">配偶名称</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="spouseName" value="${user.spouseName}"
													 placeholder="配偶名称" id="spouseName" class="form-control ">
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">QQ账号</label>
											</div>
											<div class="col-md-9">
												<div> <input type="text" name="userQq" value="${user.userQq}" 
														placeholder="QQ账号" id="userQq" class="form-control ">
												</div>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">邮箱地址</label>
											</div>
											<div class="col-md-9">
												<div>
														<input type="text" name="userEmail" value="${user.userEmail}" 
														placeholder="邮箱地址" id="userEmail" class="form-control ">
												</div>
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">微信账号</label>
											</div>
											<div class="col-md-9">
												<div> <input type="text" name="userWeixin" value="${user.userWeixin}" 
														placeholder="微信账号" id="userWeixin" class="form-control ">
												</div>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">手机号码</label>
											</div>
											<div class="col-md-9">
											<div>
												<input type="text" name="userPhone" value="${user.userPhone}" 
													placeholder="手机号码" id="userPhone" class="form-control ">
													</div>
											</div>
										</div>
									</div>
									<div class="form-group row">
									<div class="col-md-2" style="padding-right: 0">
											<div class="col-md-12" style="padding-right: 0">
												<label class="control-label">个人简介</label>
											</div>
									</div>		
											<div class="col-md-9" style="padding: 0">
													<textarea rows="5" cols="85%" placeholder="个人简介" name="userDesc" >${user.userDesc}</textarea>
													<p class="help-block"></p>
											</div>
									</div>
									<!-- <div class="form-group row">
										<div class="col-md-10 col-sm-5 col-xs-5">
											<div class="col-md-8 col-sm-1 col-xs-1">
												<button class="btn green-haze" type="submit"> 提交 </button> 
											</div>
										</div>
									</div> -->
									
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
<script type="text/javascript">
jQuery(function(){
	function isEmpty(str){
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
	$("#personInfoForm").validate({
		errorElement: 'p', 
	    errorClass: 'color-red',
	    rules: {
			userName:{
				 required : true,
				 remote:{
	            	 url:"<%=basePath%>/front/checkUserName",
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
	            	    	userName: function(data) {
	            	            return $("#userName").val();
	            	        }
	            	    }
				 }
				 
			},
			userEmail:{
				 required: false,
				 remote:{
	            	 url:"<%=basePath%>/user/front/checkUserEmail",
	            	    type: "post",
	            	    dataType: "json",   
	            	    headers:headers,
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
	               	 url:" <%=basePath%>/user/front/checkUserQq",
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
	               	            return $("#userQq").val();
	               	        }
	               	    }
	               }
			},
			
	        userWeixin:{
				 required: false,
				 remote:{
	            	 url:"<%=basePath%>/user/front/checkUserWeixin",
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
	            	            return $("#userWeixin").val();
	            	        }
	            	    }
	                }
	        },
			 userPhone:{
	            	required: false,
					 remote:{
		               	 	url:"<%=basePath%>/user/front/checkUserPhone",
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
		               	            return $("#userPhone").val();
		               	        }
		               	    }
					 }
	           }
	            
	    },
	   
	    messages: {
	    	userName:{
	        	required : "请输入姓名！",
	        	remote:"该名称已存在，请选择"
	        	
	        },
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
				url:'<%=basePath%>/user/front/userAddNew',
				type:'POST',
				dataType:'json',
				data:$(form).serialize(),
				headers:headers,
				success:function(data){
					if(data.result){
						alert(data.message)
					}else{
						alert(data.message);
					}
					
				}
			});
	    	 return false;
	    }
	});
})


</script>
</html>