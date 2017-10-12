<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族文化</title>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="javascript:;">宗族文化</a></li>
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
        	<c:forEach items="${map}" var="entry">
			<div class="headline">
				<h3>${entry.key}</h3>
				<a href="<%=basePath%>/user/familyNews/newsList/${entry.key }/1" class="btn-more">
				更多<i class="fa fa-angle-double-right"></i>
				</a>
				 </div>
			       <ul class="basic-info cmn-clearfix">
		
				<c:forEach items="${entry.value}" var="news" varStatus="status">
					 <c:if test="${status.count<=5}">
						<li class="basicInfo-block basicInfo-left">
						
							<dt class="basicInfo-item name">
								<a href="<%=basePath%>/user/familyNews/newsInfo/${news.familyNewsId}"   target="_blank">
								${news.title}</a>
							</dt>
							<dd class="basicInfo-item value">
								<fmt:formatDate value="${news.releseDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</dd>
						</li>
					 </c:if>
				</c:forEach>
				</ul>
				
              </c:forEach>  
             
        </div>   
</div>
</div>
</body>
</html>