<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族视频</title>
</head>
<body>
<div class="container container-body">
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li class="action"><a href="<%=basePath%>/user/familyVideo/videoMain.html">宗族视频</a></li>
		    <li><a href="javascript:;">${resultList.content[0].eventType }</a></li>
		    <li></li>
		</ol>
	</div>
	<div class="headline">
	 <h3> ${resultList.content[0].eventType }</h3>
	     </div>
		<ul class="thumbnails" id="dsx" >
		<c:forEach items="${resultList.content }" var="video">
				<li class="col-md-3">
					<div class="thumbnail-style thumbnail-kenburn">
						<div class="thumbnail-img">
							<a href="<%=basePath%>/user/familyVideo/videoDetail/${video.id}">
								<div class="overflow-hidden">
										<img style="height: 165px;width:100%;" src="<%=basePath%>/monkey/image/familyVideo/${video.thumbnail}" />
								</div></a> 
						</div>
						<h3 style="overflow: hidden; height: 35px;">
							<a class="hover-effect" href="<%=basePath%>/user/familyVideo/videoDetail/${video.id}"> ${video.title }</a>
						</h3>
					</div>
				</li>
         </c:forEach>       
		</ul>
		<c:if test="${empty resultList.content}">
		        <div class="basic-info cmn-clearfix" style="text-align: center;">
		       		<p>未查询到 <span style="color: #E73100">${resultList.content[0].eventType}</span> 相关的记录...</p>
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
</body>
</html>