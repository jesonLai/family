<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>家族新闻</title>
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
<style>
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
		  console.info("................")
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

<form action="<%=basePath%>/user/cus-relationship.html" method="POST" id="selectPersonForm" style="margin: 0 0 0px;">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="userInfoId" id="userInfoId"/>
</form>
<div class="container container-body">	
	<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">我的宗族</a></li>
			    <li></li>
			</ol>
		</div>
<!-- Information Blokcs -->
	<div class="row margin-bottom-20">
        <div class="col-md-3">
           <div class="posts margin-bottom-20">
				<%@ include file="/layout/left.jsp"%>
           </div>
        </div>
        <div class="col-md-9">
						<div class="input-append" style="margin-top: 10px;margin-bottom: -15px;width: 100%;">
				            <form role="form" action="/<%=basePath%>/front/dynamic/list" id="dynamic_form" method="post">
			               		<div class="form-group form-md-line-input form-md-floating-label ">
									<input type="hidden" id="userName" name="userInfoId" class="form-control select2" />
	            				</div>
			                </form>
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

	</div><!--/row-->	
	<!-- //End Information Blokcs -->
</div>
</body>
</html>