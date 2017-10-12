<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-Frame-options" content="samorin">
<style>
.btn-buj {
border: 0px none;
font-size: 16px !important;
cursor: pointer;
padding: 5px 13px;
position: relative;
background: #0aa957 none repeat scroll 0% 0%;
display: inline-block;
color: white !important;
text-decoration: none !important;
width: auto;
height: 40px;
margin-top: 15px;
}

.input-prepend .add-on {
display: inline-block;
width: auto;
height: 40px;
min-width: 30px !important;
padding: 4px 5px;
font-size: 14px;
font-weight: normal;
line-height: 40px;
text-align: center;
text-shadow: 0px 1px 0px white;
background-color: #EEE;
border: 1px solid #CCC;
}
.log-e-page{
widht:100% ;
}
</style>
<script type="text/javascript">
function changeImg(obj,baePath){
	var timestamp=new Date().getTime();
	var validateCode=$(obj);
	validateCode.attr("src",baePath+"/SimpleCaptcha"+"?d="+timestamp);
}
</script>
<title>登录</title>
</head>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="javascript:;">登录</a></li>
		    <li></li>
		</ol>
	</div>
<div class="row">
			<div class="col-md-4 col-md-offset-3" >
		        <form novalidate="" class="bs-example bs-example-form" role="form" action="loginUser" id="logForm" method="post" >   
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			        <c:if test="${not empty sessionScope.SECURITY_LOGIN_EXCEPTION}">
			        <div id="" class="alert alert-danger" > ${sessionScope.SECURITY_LOGIN_EXCEPTION}<button data-dismiss="alert" class="close">×</button></div>	
			           </c:if>
			           <br>
			            <div class="input-group input-group-lg">
			                <span class="input-group-addon"><i class="icon-user"></i></span>
			                <input  class="form-control" name="userAccount" value="" autocomplete="off" placeholder="手机号码或电子邮箱" style=" min-height: 40px;" type="text">
			            </div>
						<br>
			            <div class="input-group input-group-lg">
			                <span class="input-group-addon"><i class="icon-lock"></i></span>
			                <input class="form-control" name="userPassword" value="" autocomplete="off" placeholder="登录密码"  type="password">
			            </div>
			            
			            <c:if test="${not empty sessionScope.showCheckCode}">
			            	<br>
			            	<div class="row">
			            		<div class="col-md-10">	
								<div  class="input-group input-group-lg">
										
											<span class="input-group-addon">
												<i class="fa fa-barcode" ></i>
											</span>
											<input type="text" autocomplete="off" class="form-control" name="simpleCaptcha" placeholder="输入右侧验证码"   id="validateCode" >
								</div>
								</div>
										<div class="col-md-2">	
											<img  onclick="changeImg(this,'<%=basePath%>')" src="<%=basePath%>/SimpleCaptcha" classs="span4" style="cursor:pointer;" alt="看不清楚?点图标可以换一个" title="看不清楚?点图标可以换一个"/>
										</div>
							</div>
						</c:if>
						<br>
			            <div class="controls form-inline">
			                <button class="col-md-12  btn btn-success btn-lg btn-block " id="loginFormSubmit">登录</button>
			            </div>
			            <h4> </h4>
		        </form>
	    </div><!--/row-fluid-->
	    </div>

</div>
</body>
</html>