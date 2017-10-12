<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>能人贤士</title>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/news/mingrenList">能人贤士</a></li>
		    <li><a href="javascript:;">正文</a></li>
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
	        <div class="blog-post">
			<h3>能人贤士</h3>
			<div class="headlineInfo">
                <h3>${news.title }</h3>
				<div class="infoBar">
					<div class="info">发布时间：<fmt:formatDate value="${userInfo.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;&nbsp;来源：</div>
				</div>
				  	<div class="row">
		          <div class="col-md-12 booking-blocks">
		             <div class="pull-left booking-img">
		                <img src="<%=basePath%>/monkey/image/celebrity/${userInfo.headImageName}" alt="" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
		             </div>
		             <div style="overflow:hidden;text-align: left;">
		                <h2 >${userInfo.userName }</h2>
		                <span>出生年月：<fmt:formatDate value="${userInfo.userBornDate }" pattern="yyyy-MM-dd"/></span><br/>
		                <span>年龄：${userInfo.userAge }</span><br/>
		                <span>性别：<c:if test="${userInfo.userSex==1 }">男</c:if><c:if test="${userInfo.userSex==2 }">女</c:if><c:if test="${userInfo.userSex==3 }">未知</c:if></span>
		             </div>
		             <p style="text-align: left;">名人简介：${userInfo.userDesc }</p>
		          </div>
		        </div>
             </div>
               
            </div>
	        
        </div>
	</div>
</div>
<div style="height:20px"></div>
</body>
</html>