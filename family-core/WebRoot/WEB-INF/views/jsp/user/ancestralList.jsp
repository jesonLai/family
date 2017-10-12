<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族祠堂</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#jianjie").attr("class", "active");
})
</script>

</head>
<body>
<div class="container container-body">
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="javascript:;">宗族祠堂</a></li>
		    <li></li>
		</ol>
	</div>
	
	<div class="headline">
	    	<h4>祠堂简介</h4>
	     </div>
		<div class="row" style="padding: 0">
		<ul class="thumbnails" id="dsx" >
		<c:forEach items="${resultList.content }" var="ancestralTemple">
				<li class="col-md-3">
						<div class=" thumbnail-kenburn">
							<div class="thumbnail-img">
								<a href="<%=basePath%>/user/ancestral/ancestralDetail?id=${ancestralTemple.atId}" >
<%-- 										<img style="height: 165px;width:100%;" src="${ancestralTemple.atImg }" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/> --%>
								
									<div class="overflow-hidden thumbnail">
													<img style="height: 165px;width:100%;" src="<%=basePath%>/monkey/image/ancestralTemple/${ancestralTemple.atImg}" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
													<div class="caption">
														<h5>
															<a class="hover-effect apostrophe" href="<%=basePath%>/user/ancestral/ancestralDetail?id=${ancestralTemple.atId}" style="width: 252px;height: 1px;"> ${ancestralTemple.atTitle }</a>
														</h5>
														<!--<div style="display: none" id="divContent">${ancestralTemple.atContent}</div>-->
														<p></p>
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
	       		<p>未查询到 <span style="color: #E73100">祠堂简介</span> 相关的记录...</p>
	        </div>
	      </c:if>
	      <c:if test="${not empty resultList.content}">
	     	<div class="headline" style="margin-top: 5px; margin-bottom: 5px;"></div>
			<div class="dataTables_paginate paging_simple_numbers" id="data-table_paginate">
	             <div>
					<tags:pagination page="${resultList}" paginationSize="5" />
				</div>
			</div>
		</c:if>
</div>
<!--<script type="text/javascript">
$("div[id='divContent']").each(function(){
	var txt=$(this).text();
	if(txt.length>11){
		txt=txt.substr(0,11)+"...";
	}
	$(this).next('p').text(txt);
})

</script>-->
</body>
</html>