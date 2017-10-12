<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
    <c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN" class="no-js">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_paraeter" content="${_csrf.parameterName}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>宗族关系</title>
<link href="<%=basePath%>/monkey/global/plugins/googleapis/css/family.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/monkey/global/plugins/select2/select2.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/front/css/sky-forms.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/monkey/global/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>/monkey/pages/css/profile.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/front/css/settings.css" rel="stylesheet" type="text/css" media="screen">
<link href="<%=basePath%>/monkey/global/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link href="<%=basePath%>/monkey/global/css/plugins.css" rel="stylesheet" type="text/css"/>

<script src="<%=basePath%>/front/js/jquery-migrate.min.js"></script>
<script src="<%=basePath%>/monkey/jsp/real-name.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/monkey/global/plugins/jOrgChart-master/css/jquery.jOrgChart.css" />
<link rel="stylesheet" href="<%=basePath%>/monkey/global/plugins/jOrgChart-master/css/custom.css" />
    <!-- jQuery includes -->
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="<%=basePath%>/monkey/global/plugins/jOrgChart-master/js/jquery.jOrgChart.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jOrgChart-master/js/taffy.js"></script>

<style type="text/css">
#profile-content-relationship{overflow: auto;background-color: #5D5140;height: 500px}
#owmain_menu>li{float: left; }
.fullScroll-div{ z-index: 10065 }
.full{z-index: 10060;margin: 0;position: fixed;top: 0;left: 0;bottom: 0;right: 0;width: 100%;height: 100%;background: #fff;}
</style>
  <script type="text/javascript">
  <!-- 
  jQuery(function($){
	  $.getScript( "<%=basePath%>/monkey/jsp/relationship.js", function( data, textStatus, jqxhr ) {
		  var relationshipData=${RELATIONSHIP};
		  console.info(relationshipData)
		  	relationship.init(relationshipData,'<%=basePath%>') 
		  	relationship.searchPersons();
	  });
	  $("#profile-content-relationship").css("height",$(window).height())
  })
 -->
  </script>
</head>
<body>
<form action="<%=basePath%>/user/relationship.html" method="POST" id="selectPersonForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="userInfoId" id="userInfoId"/>
</form>
<div class="testi-slide" >
		<div class="container">
		   <ul class="slides">
			<c:forEach items="${NAVIGATIONIMG}" var="IMG">
				<li class="" >
					<a href="${IMG.href}"> <img src="<%=basePath%>/${IMG.httpUrl}"style="max-width: 1180px;max-height: 354px;" > </a>
				</li>
			</c:forEach>
			
		   </ul>
		</div>
		  			
</div>
	<div class="container container-body">
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">宗族关系</a></li>
			    <li></li>
			</ol>
		</div>
		<div class="col-md-12">
			<div class="profile-sidebar">
				<!-- PORTLET MAIN -->
				<div class="portlet light profile-sidebar-portlet">
					<!-- SIDEBAR USERPIC -->
					<div class="profile-userpic">
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
							<li><a href="<%=basePath%>/user/updatepwd.html" > <i
									class="icon-check"></i> 密码修改
							</a></li>
							<li class="active"><a href="<%=basePath%>/user/relationship.html"> <i
									class="icon-info"></i> 宗族关系
							</a></li>
							<li><a href="<%=basePath%>/user/front/real-name.html">
									<i class="icon-info"></i> 信息认证
							</a></li>
						</ul>
					</div>
					<!-- END MENU -->
				</div>
			</div>
			<!--sidebar-->
			
			<div class="profile-content" >
				<div class="" style="margin-right:0">
					<div >
						<div class="input-append" style="margin-top: 10px;margin-bottom: -15px;width: 100%;">
				            <form role="form" action="/<%=basePath%>/front/dynamic/list" id="dynamic_form" method="post">
			               		<div class="form-group form-md-line-input form-md-floating-label ">
									<input type="hidden" id="userName" name="userInfoId" class="form-control select2" />
	            				</div>
			                </form>
			            </div>
					</div>
				</div>
				<div class=" full-div" style="margin-right:0">
					<div class="fullScroll-div" >	
							<button type="button" class="btn btn-transparent green  btn-md active" id="fullScroll">
												全屏
							</button> 
					</div>
					<div id="profile-content-relationship" >
						<div class="col-md-12">
							 <div class="topbar">
		    				</div>
						    <div id="in" style="display:none">
						    </div>
		   
		   					 <div id="chart" class="orgChart" ></div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	
	
	
	
	


</body>
</html>