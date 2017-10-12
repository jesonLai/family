<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>世界三欧</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/monkey/pages/css/login.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/monkey/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/global/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/layout/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/monkey/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${pageContext.request.contextPath}/monkey/layout/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
<script type="text/javascript">
function changeImg(obj,baePath){
	var timestamp=new Date().getTime();
	var validateCode=$(obj);
	validateCode.attr("src",baePath+"/SimpleCaptcha"+"?d="+timestamp);
}
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
<!-- <form action="../loginUser" method="POST"> -->
<!-- 	<input type="text" name="userAccount" value="admin"/> -->
<!-- 	<input type="password" name="userPassword" value="123456"/> -->
<%-- 	${sessionScope.SECURITY_LOGIN_EXCEPTION} --%>
	
<%--  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
<!-- 	<input type="submit" value="提交" > -->
<!-- </form> -->







<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
<!-- 	<a href="index.html"> -->
<!-- 		世界三欧 -->
<%-- 	<img src="${pageContext.request.contextPath}/monkey/layout/img/logo-big.png" alt=""/> --%>
<!-- 	</a> -->
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="${pageContext.request.contextPath}/loginUser" method="post">
		<h3 class="form-title">登录</h3>
		<c:if test="${not empty sessionScope.SECURITY_LOGIN_EXCEPTION}">
		<div class="alert alert-danger display-block">
			<button class="close" data-close="alert"></button>
			<span>
			 ${sessionScope.SECURITY_LOGIN_EXCEPTION}
			 ${sessionScope.SPRING_SECURITY_SAVED_REQUEST}
			 ${SPRING_SECURITY_SAVED_REQUEST}
			 
			 </span>
		</div>
		</c:if>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">账号</label>
<%-- 			${sessionScope.USERNAME} --%>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="账号" name="userAccount" value="lxr"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" onfocus="this.type='password'" autocomplete="off" placeholder="密码" name="userPassword" value="213845498"/>
		</div>
		 <c:if test="${not empty sessionScope.showCheckCode}">
			<div  class="input-prepend row">
				<div class="col-md-6">
					<label class="control-label visible-ie8 visible-ie9">验证码</label>
					<input type="text" autocomplete="off" class="form-control form-control-solid placeholder-no-fix"
						name="simpleCaptcha" placeholder="输入右侧验证码"   id="validateCode" style="min-height:40px;">
				</div>
				<div class="col-md-6">
					<img  onclick="changeImg(this,'<%=basePath%>')" src="<%=basePath%>/SimpleCaptcha"  style="cursor:pointer;" alt="看不清楚?点图标可以换一个" title="看不清楚?点图标可以换一个"/>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn btn-success uppercase">登录</button>
<!-- 			<label class="rememberme check"> -->
<%-- 			<input type="checkbox" name="remember" value="1"/>记住密码${loginType} </label> --%>
<!-- 			<a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a> -->
		</div>
<!-- 		<div class="login-options"> -->
<!-- 			<h4>Or login with</h4> -->
<!-- 			<ul class="social-icons"> -->
<!-- 				<li> -->
<!-- 					<a class="social-icon-color facebook" data-original-title="facebook" href="javascript:;"></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="social-icon-color twitter" data-original-title="Twitter" href="javascript:;"></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="social-icon-color googleplus" data-original-title="Goole Plus" href="javascript:;"></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="social-icon-color linkedin" data-original-title="Linkedin" href="javascript:;"></a> -->
<!-- 				</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 		<div class="create-account"> -->
<!-- 			<p> -->
<!-- 				<a href="javascript:;" id="register-btn" class="uppercase">注册</a> -->
<!-- 			</p> -->
<!-- 		</div> -->
	</form>
	<!-- END LOGIN FORM -->
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<form class="forget-form" action="index.html" method="post">
		<h3>Forget Password ?</h3>
		<p>
			提交你的邮箱地址.
		</p>
		<div class="form-group">
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="邮箱" name="email"/>
		</div>
		<div class="form-actions">
			<button type="button" id="back-btn" class="btn btn-default">返回</button>
			<button type="submit" class="btn btn-success uppercase pull-right">提交</button>
		</div>
	</form>
</div>
<!-- <div class="copyright"> -->
<!-- </div> -->
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${pageContext.request.contextPath}/monkey/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/monkey/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${pageContext.request.contextPath}/monkey/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/monkey/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {     
Metronic.init(); // init metronic core components
Layout.init(); // init current layout
Login.init();
// Demo.init();
document.forms[0].submit();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
</html>