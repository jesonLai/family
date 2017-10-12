<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<!-- Meta -->
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<sitemesh:write property='meta'/>
<title><sitemesh:write property='title'/></title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/monkey/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/monkey/global/plugins/font-awesome/css/font-awesome.min.css"  />
<link href="<%=basePath%>/monkey/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>/monkey/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/front/css/headers/header1.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/front/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/front/css/jquery.slideBox.css"  media="screen" />
<script type="text/javascript" src="<%=basePath%>/front/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/bootstrap/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>/front/js/jquery.slideBox.js"  ></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/front/js/jQuery-jcMarquee.js" type="text/javascript"></script>
<!--[if lt IE 9]>
      <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
      <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.js"></script>
<![endif] -->
<sitemesh:write property='head'/>
</head>
<body id="top">
<div class="family_suspension">
	<div class="family_suspension_box">
		<div class="family_suspension_box_Top">
			<a href="javascript:scroll(0,0)">返回顶部</a>
		</div>
	</div>
</div>
<%@ include file="header.jsp"%>
<div class="container"  >
 <sitemesh:write property='body'/>
 </div>
<%@ include file="foot.jsp"%>
<script type="text/javascript">
<!--
function init() {
        function backToTop() {
            preDistance = document.body.scrollTop;
          var timer = setInterval(function () {
            var distance = document.body.scrollTop;
            if (distance === 0 || preDistance < distance) {
              clearInterval(timer);
            }
            preDistance = distance - distance / 10
            document.body.scrollTop = preDistance;
          }, 16.67);
        }
        function showBackToTop() {

            if ((document.documentElement.scrollTop || 0) + (document.body.scrollTop || 0) > 100) {
				
                $(".family_suspension").addClass('show-backtotop-box');
            } else {
                $(".family_suspension").removeClass('show-backtotop-box');
            }
        }
        $(".family_suspension").on('click', backToTop);
        $(window).on('scroll', showBackToTop);
    }
	$(function(){
		init();
	})
//-->
</script>
</body>
</html>