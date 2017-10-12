<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片展示</title>
</head>
<body>
<div class="container container-body">
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/image/imgMain">宗族图片</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/image/imgList?type=${img.eventType}">${img.eventType}</a></li>
		    <li><a href="javascript:;">正文</a></li>
		    <li></li>
		</ol>
	</div>	
	<div class="row margin-bottom-20">
        <div class="col-md-3">
           <div class="posts margin-bottom-20">
			<%@ include file="/layout/left.jsp"%>
           </div>
        </div>
        <div class="col-md-9">
        	<div class="blog-post">
        			<h3>${img.eventType}</h3>
                <div class="headlineInfo">
                <h3>${img.title }</h3>
				<div class="infoBar">
					<div class="info">发布时间：<fmt:formatDate value="${img.lastModified }" pattern="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;&nbsp;来源：</div>
				</div>
				 ${img.htmlContent }
             </div>
                
                <h2>${img.title }</h2>
                <h4 style="font-size: 15px; color: #A1A1A1;">来源：原创</h4>
               
            </div>
        </div>
        
	</div>
</div>
</body>
</html>