<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${resultList.content[0].eventType }</title>
</head>
<body>
<div class="container container-body" >
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/image/imgMain">宗族图片</a></li>
		    <li><a href="javascript:;">${resultList.content[0].eventType }</a></li>
		    <li></li>
		</ol>
	</div>
	<div class="headline">
		<h4>${resultList.content[0].eventType}</h4>
	               		
	</div>
	<div class="row" style="padding: 0">
		<ul class="thumbnails" id="dsx" >
		<c:forEach items="${resultList.content }" var="picImg">
				<li class="col-md-3">
					<div class=" thumbnail-kenburn">
						<div class="thumbnail-img">
							<a href="<%=basePath%>/user/image/imgInfo?imgId=${picImg.imgId}"><div class="overflow-hidden">
								<div class="overflow-hidden thumbnail">
									
								<img style="height: 165px;width:100%;" src="<%=basePath%>/monkey/image/familyImages/${picImg.name}" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
								<div class="caption">
									<h3 style="overflow: hidden; height: 35px;">
										<a class="hover-effect" href="<%=basePath%>/user/image/imgInfo?imgId=${picImg.imgId}"> ${picImg.title }</a>
									</h3>
								</div>
								</div>
							</a> 
						</div>
					</div>
				</li>
		</c:forEach>
		</ul>
	</div>	
		 <c:if test="${empty resultList.content}">
		        <div class="basic-info cmn-clearfix" style="text-align: center;">
		       		<p>未查询到 <span style="color: #E73100">${resultList.content[0].eventType }</span> 相关的记录...</p>
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