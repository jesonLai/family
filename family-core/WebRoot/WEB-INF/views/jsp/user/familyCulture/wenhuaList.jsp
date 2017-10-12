<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族文化</title>
</head>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="<%=basePath%>/user/news/newsMain/1.html">宗族文化</a></li>
		    <li><a href="javascript:;">${eventType}</a></li>
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
			<div class="headline">
				<h3>${eventType}</h3>
			</div>
	          <c:forEach items="${resultList.content }" var="news">
				   <ul class="basic-info cmn-clearfix">
					<li class="basicInfo-block basicInfo-left">
						<dt class="basicInfo-item name">
							<a href="<%=basePath%>/user/familyNews/newsInfo/${news.familyNewsId}" target="_blank">
								${news.title}
							</a>
						</dt>
						<dd class="basicInfo-item value">
							<fmt:formatDate value="${news.releseDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</dd>
					</li>
				</ul>
				</c:forEach>
				<c:if test="${empty resultList.content}">
			        <div class="basic-info cmn-clearfix" style="text-align: center;">
			       		<p>未查询到 <span style="color: #E73100">${eventType}</span> 相关的记录...</p>
			        </div>
		      </c:if>
		      <c:if test="${not empty resultList.content}">
				<div class="dataTables_paginate paging_simple_numbers" id="data-table_paginate">
			              <div>
							<tags:pagination page="${resultList}" paginationSize="5" />
						</div>
					</div>
			</c:if>
        </div>       
	</div>
</div>
</body>
</html>