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
<title>宗族公告</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#gonggao").attr("class", "active");
})
</script>
</head>
<style>
	.left-lbzw {
    margin-top: 16px;
    overflow: hidden;
    padding-bottom: 15px;
}
.left-lbzw li {
    line-height: 24px;
    padding: 0.2em 0 0.2em 10px;
    font-size: 14px;
}
.time {
    float: right;
    color: #999999;
    width: 150px;
    font-size: 12px;
}
</style>
<body>
<div class="container container-body">	
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">宗族公告</a></li>
			    <li></li>
			</ol>
		</div>
<!-- Information Blokcs -->
	<div class="row margin-bottom-20">
        <!-- Latest Shots -->
        <div class="col-md-3">
           <div class="posts margin-bottom-20">
				<%@ include file="/layout/left.jsp"%>
           </div>
        
        </div><!--/span4-->
        <div class="col-md-9">
			<div class="headline">
				<h3>公告列表</h3>
			</div>
			<div class="left-lbzw">
			<c:forEach items="${resultList.content }" var="announcement">
			    <ul class="basic-info cmn-clearfix">
						<li class="basicInfo-block basicInfo-left">
							<dt class="basicInfo-item name">
								<a href="<%=basePath%>/user/announcement/announcementDetail?id=${announcement.id}"   target="_blank">
									${announcement.title }
								</a>
							</dt>
							<dd class="basicInfo-item value">
								<fmt:formatDate value="${announcement.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</dd>
						</li>
				</ul>
             </c:forEach>
             </div>
			<div class="dataTables_paginate paging_simple_numbers" id="data-table_paginate">
              <div>
				<tags:pagination page="${resultList}" paginationSize="5" />
			</div>
			</div>
			
          
        </div><!--/span8-->        

	</div><!--/row-->	
	<!-- //End Information Blokcs -->

</div>
</body>
</html>