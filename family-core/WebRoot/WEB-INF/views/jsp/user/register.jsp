<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_paraeter" content="${_csrf.parameterName}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>注册</title>
<script src="<%=basePath%>/monkey/jsp/register.js" type="text/javascript"></script>
</head>
<body>
<div >
	<div class="container container-body">
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">注册</a></li>
			    <li></li>
			</ol>
		</div>
		<div class="row">
			<div class="col-md-5 col-md-offset-3" >
				<form novalidate="" autocomplete="off" class="bs-example bs-example-form" role="form" 
					id="memberForm" onsubmit="return false" 
					method="post" >
					
						<div class=" message" style="display: none;">
							<div id="btn-hin" class="alert alert-danger"><q></q>
							<button data-dismiss="alert" class="close">×</button></div>	
						</div>
						<br>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">账号<span class="color-red">*</span></span>
						<input type="text" autocomplete="off" class="form-control" name="userAccount" placeholder="输入账号"
							id="userAccount"/>
					</div>
					
					<br>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">密码 <span class="color-red">*</span></span>
						 <input
							type="password" autocomplete="off" class="form-control"
							name="userPassword" id="userPassword" placeholder="输入密码"
							>
					</div>
					<br>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">确认密码 <span class="color-red">*</span></span> <input
							type="password" autocomplete="off" class="form-control col-md-8"
							name="surePassword" placeholder="确认密码"   id="surePassword" >
					</div>
					<br>
					<div class="row">
			        	<div class="col-md-10">	
							<div class="input-group input-group-lg">
									<span class="input-group-addon">验证码 <span class="color-red">*</span></span> 
									<input type="text" autocomplete="off" class="form-control span6"
										name="captcha" placeholder="输入右侧验证码"   id="captcha" >
							</div>
						</div>
						<div class="col-md-2">	
							<img  onclick="register.changeCaptcha(this,'<%=basePath%>')" src="<%=basePath%>/SimpleCaptcha" style="cursor:pointer;" alt="看不清楚?点图标可以换一个" title="看不清楚?点图标可以换一个"/>
						</div>
					</div>
					<br>
					<div class="input-group input-group-lg col-md-12 ">
						<button type="submit"
							class="col-md-12  btn btn-success btn-lg btn-block "
							role="button" id="register_member">注册</button>
							
					</div>
				</form>
			</div>
		</div>

	</div>
</div>	
	<script type="text/javascript">
		register.init('<%=basePath%>');
		(function() {
			if (navigator.userAgent.toLowerCase().indexOf("chrome") != -1) {
				var selectors = document.getElementsByTagName("input");
				for (var i = 0; i < selectors.length; i++) {
					if ((selectors[i].type !== "submit")
							&& (selectors[i].type !== "password")) {
						var input = selectors[i];
						var inputName = selectors[i].name;
						var inputid = selectors[i].id;
						selectors[i].removeAttribute("name");
						selectors[i].removeAttribute("id");
						setTimeout(function() {
							input.setAttribute("name", inputName);
							input.setAttribute("id", inputid);
						}, 1)
					}

				}

			}
		})();
	</script>
</body>
</html>