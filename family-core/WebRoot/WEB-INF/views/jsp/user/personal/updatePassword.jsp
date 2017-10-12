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
<title>修改密码</title>
<link href="<%=basePath%>/monkey/global/plugins/googleapis/css/family.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/front/css/sky-forms.css" />
<link rel="stylesheet"
	href="<%=basePath%>/monkey/global/css/metroStyle/metroStyle.css"
	type="text/css">


<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/jquery-multi-select/css/multi-select.css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />

<link
	href="<%=basePath%>/monkey/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=basePath%>/monkey/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/bootstrap-toastr/toastr.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />
<link href="<%=basePath%>/monkey/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />



<link href="<%=basePath%>/monkey/pages/css/profile.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/front/css/settings.css" rel="stylesheet"
	type="text/css" media="screen">
<link rel="stylesheet" href="<%=basePath%>/front/css/headers/header1.css" />
<link href="<%=basePath%>/monkey/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />

<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=basePath%>/monkey/favicon.ico" />
<script src="<%=basePath%>/front/js/jquery.min.js"></script>
<script src="<%=basePath%>/front/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/front/jquery-reAjax.js" type="text/javascript"></script>
<style type="text/css">
#owmain_menu>li{float: left;}
</style>
</head>
<body>
<div class="testi-slide">
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
	<div class="container container-body" >
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">修改密码</a></li>
			    <li></li>
			</ol>
		</div>
		<div class="col-md-12" style="background: white; padding-bottom: 20px">
			<div class="profile-sidebar">
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
							<li><a href="<%=basePath%>/user/center.html"> <i
									class="icon-settings"></i> 个人信息
							</a></li>
							<li class="active"><a href="<%=basePath%>/user/updatepwd.html"> <i
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
					<div class="col-md-12" >
					
						<form novalidate="" action="<%=basePath%>/user/updatePassWord.html" id="passwordForm"  method="post">
							<div class="portlet light ">
								<div class="portlet-title">
									<div class="caption caption-sm">
										<i class="icon-bar-chart theme-font hide"></i> <span
											class="caption-subject font-blue-madison bold uppercase">修改密码</span>
									</div>
									<!-- <div class="actions">
										<div class="btn-group btn-group-devided" data-toggle="buttons">
											<button type="submit" class="btn btn-transparent green btn-circle btn-md active" style="margin-right: 20px">
												提&nbsp;交
											</button> 
										</div>
									</div> -->
								</div>
								<div class="portlet-body" style="margin-top: 100px;">
									<div class="form-group row">
												<div class="col-md-2">
												<label class="input">
													原密码： 
												</label>
												</div>
												<div class="col-md-8">
													<input  id="oldPass" name="oldPass" placeholder="原密码" type="password"  class="form-control"/>
													</div>
										</div>
									<div class="form-group row">
											<div class="col-md-2">
												<label class="input">
													新密码：
												</label>
												</div>
												<div class="col-md-8">
													<input id="newOnePwd" name="newOnePwd" placeholder="新密码" type="password"  class="form-control"/>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-md-2">
												<label class="input">
													确认密码：
												</label>
												</div>
												<div class="col-md-8">
													<input class="parsley-error form-control" id="newTowPwd" name="newTowPwd" placeholder="确认密码" type="password" />
											</div>
										</div>
										<div class="margin-top-10">
											<button class="btn green-haze" type="submit"> 提交 </button> 
										</div>
								</div>
								
								
								
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
	
	
<script type="text/javascript">

$("#passwordForm").validate({
 	errorElement: 'span', 
    errorClass: 'help-red', 
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
    	oldPass:{
        	required:true,
        	minlength:6,
        	remote:{
              	 url: "<%=basePath%>/user/validatePassWord",
              	    type: "get",
              	    dataType: "json",   
              	    data: {
              	    	oldPass:function(){
          	    		 return $("#oldPass").val();
              	    	}
              	    }
              }
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
    	oldPass:{
         	required:"请输入密码",
         	minlength: "密码长度不能小于 6个字母",
          	remote : "原密码输入不正确"
         } ,
         newOnePwd:{
    		required:"请输入密码",
    		minlength: "密码长度不能小于 6个字母"
    	} ,
    	newTowPwd:{
    		required:"请再次输入密码",
    		minlength: "密码长度不能小于 6个字母",
    		equalTo:"两次密码输入不一致"
    	}
    }
});
</script>
</body>
</html>