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
<script type="text/javascript">
$(document).ready(function() {
// 	$("#fimalyMessage").attr("class", "active");
// 	//表单验证
// 	jQuery("#addMessage").validationEngine('attach');
})
</script>
<title>留言板</title>
</head>

<body>
<div class="container container-body" >	
<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="<%=basePath%>">留言板</a></li>
		</ol>
	</div>
<!-- Information Blokcs -->
	<div class="row margin-bottom-20">
    	<!-- Who We Are -->

<div class="row blog-page blog-item">		
        <!-- Latest Shots -->
        <div class="col-md-3">
           <div class="posts margin-bottom-20">
				<%@ include file="/layout/left.jsp"%>
           </div>
        </div><!--/span4-->
        <div class="col-md-9">
        	<div class="headline">
        		<h3>留言板</h3>
		     </div>
		    <ul class="media-list">
        <c:forEach items="${resultList.content }" var="message">
				<li class="media">
					<a class="pull-left" href="#">
						<img class="media-object" src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg"  alt="族员头像">
					</a>
					<div class="media-body">
						<div class="row message-header">
							<div class="col-md-4">
								<h3 class="media-heading">${message.pubUser==null?"匿名用户":message.pubUser.userInfo.userName } </h3>
							</div>
							<div class="col-md-4  col-md-offset-4 "><span class="pull-right"><fmt:formatDate value="${message.messageDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </span></div>
						</div>
						<div class="col-md-12 message-body">
							<p>${message.content }</p>
						</div>
					</div>
				</li>
             </c:forEach>
      
              	</ul>
			<div class="dataTables_paginate paging_simple_numbers" id="data-table_paginate">

	              <div>
					<tags:pagination page="${resultList}" paginationSize="5" />
				</div>
	
			</div>
			<br/>
			<br/>
			<div class="post-comment">
            	<h3 class="color-yellow ">我要留言</h3>
                <form action="<%=basePath%>/user/message/addMessage" id="addMessage" method="POST">
                    <textarea rows="4" class="col-md-12 validate[required,maxSize[100]]" name="content" placeholder="100字内"></textarea>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <p><button type="submit" class="btn-u">发送</button></p>
                </form>
            </div>
        </div><!--/span8-->        
</div>
	</div><!--/row-->	
	<!-- //End Information Blokcs -->

</div>
</body>
</html>