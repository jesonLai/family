<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>站内搜索</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/front/css/instation_search/instation_search.css" />
</head>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="javascript:;">站内搜索</a></li>
		    <li></li>
		</ol>
	</div>
	<div class="row margin-bottom-20">
		<div class=" blog-page blog-item">		
	        <div class="col-md-3">
	           <div class="posts margin-bottom-20">
					<%@ include file="/layout/left.jsp"%>
	           </div>
	        </div><!--/span4-->
	        <div class="col-md-9" >
	        	<div class="headline">
			   		<h3>站内搜索</h3>
			     </div>
			     <div class="instatiom-search">
			     <ul>
	        	<c:forEach items="${familyData.content}" var="instaS">
	        		<c:if test="${instaS[12]==1}">
						<li class="instationSearch-item ">
							<div class="row" style="padding: 0">
								<div class="col-md-9 instationSearchInfo-name">
									<a href="<%=basePath%>/${instaS[8]}" target="_blank" id="resultSearch">[${instaS[1]}]</a>
									<a href="<%=basePath%>/${instaS[9]}" target="_blank" id="resultSearch">[${instaS[2]}]</a>
									<a href="<%=basePath%>/${instaS[10]}" target="_blank" id="resultSearch">${instaS[3]}</a>
								</div>
								<div class="col-md-3 instationSearchInfo-date">
									<fmt:formatDate value="${instaS[11]}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</div>
							</div>
							<div class="search-description">
								<div class="celebrity-content">
									${instaS[7]}
								</div>
							</div>
						</li>
					</c:if>
					<c:if test="${instaS[12]==2}">
						<li class="instationSearch-item">
							<div class="row" style="padding: 0;margin-bottom: 15px;">
								<div class="col-md-9 instationSearchInfo-name">
									<a href="<%=basePath%>/user/news/mingrenList" target="_blank" id="resultSearch">[${instaS[1]}]</a>
								</div>
								<div class="col-md-3 instationSearchInfo-date">
									<fmt:formatDate value="${instaS[11]}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</div>
							</div>
							<a class="instationSearch-picture" href="<%=basePath%>/user/news/mingrenInfo?id=${instaS[0]}" target="_blank">
								<img src="<%=basePath%>/monkey/image/celebrity/${instaS[5]}" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'" alt="${instaS[4]}"/>
							</a>
							<dl class="instationSearch-descritpion">
								<dt>
									<div class="role-name">
										<span class="item-value">
											<a target="_blank" href="<%=basePath%>/user/news/mingrenInfo?id=${instaS[0]}" data-lemmaid="113835" id="resultSearch">${instaS[4]}</a>
										</span>
									</div>
									<div class="role-actor">
										<span class="item-key">世代</span><span class="item-value">
											<a target="_blank" href="/item/%E5%94%90%E5%9B%BD%E5%BC%BA/13438" data-lemmaid="13438" id="resultSearch">${instaS[6]}</a><div></div><div></div>
										</span>
									</div>
									<div class="search-voice">
									</div>
								</dt>
								<dd class="search-description">
									<div class="celebrity-content">${instaS[7]}</div>
									
								</dd>
							</dl>
						</li>
					</c:if>
	        	</c:forEach>
	        	</ul>
	        	</div>
		        <c:if test="${empty familyData.content}">
			        <div class="basic-info cmn-clearfix" style="text-align: center;">
			       		<p>未搜索到任何与 <span style="color: #E73100">${searchParamVal}</span> 相关的记录...</p>
			        </div>
		        </c:if>
	       		 <c:if test="${not empty familyData.content}">
				<div class="dataTables_paginate paging_simple_numbers" id="data-table_paginate">
	              <div>
					<tags:pagination page="${familyData}" paginationSize="10" />
				  </div>
				</div>
				</c:if>
	        </div>        
</div>
	</div><!--/row-->	
</div>
<script type="text/javascript">
	var searchVal='${searchParamVal}';
	$('a[id=resultSearch]').each(function(){
		var resultSearch=$(this).text();
		var re=new RegExp(searchVal,"g");
		$(this).html(resultSearch.replace(re,"<font color='red'>"+searchVal+"</font>"));
	})
	
</script>
</body>
</html>