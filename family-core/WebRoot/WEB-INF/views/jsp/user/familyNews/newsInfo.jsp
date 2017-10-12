<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族新闻</title>
</head>
<body>
<div class="container container-body" >	
	<div class="row">
			<ol class="breadcrumb">
			    <li class="action"><a href="<%=basePath%>" >首页</a></li>
			    <li class="action">
			    	<a href="<%=basePath%>/user/news/newsMain/${news.flag}.html" >
			    		<c:if test="${news.flag==0}">宗族新闻</c:if>
			    		<c:if test="${news.flag==1}">宗族文化</c:if>
			    		<c:if test="${news.flag==2}">能人贤士</c:if>
			    		<c:if test="${news.flag==3}">宗族财务</c:if>
			    		<c:if test="${news.flag==4}">宗族族谱</c:if>
			    		<c:if test="${news.flag==5}">功德榜</c:if>
			   		</a>
			   	</li>
			    <li class="action"><a href="<%=basePath%>/user/familyNews/newsList/${news.eventType}/${news.flag}" >${news.eventType}</a></li>
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
			<h3>${news.eventType }</h3>
			<div class="headlineInfo">
                <h3>${news.title }</h3>
				<div class="infoBar">
					<div class="info">发布时间：<fmt:formatDate value="${news.releseDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
					&nbsp;&nbsp;来源：</div>
				</div>
				 ${news.htmlContent }
             </div>
               
            </div>
        </div>
	</div>	
</div>
</body>
</html>