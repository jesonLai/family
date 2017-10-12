<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
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
<style>
.list-inline {
padding-left: 0;
margin-left: -5px;
list-style: none;
}
.list-inline > li {
display: inline-block;
padding-right: 5px;
padding-left: 5px;
}
</style>
</head>
<body>
<div class="container container-body">	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/ancestral/ancestralList">宗族祠堂</a></li>
		    <li><a href="javascript:;">正文</a></li>
		    <li></li>
		</ol>
	</div>
<!-- Information Blokcs -->
	<div class="row margin-bottom-20">
    	<!-- Who We Are -->
        <!-- Latest Shots -->
        <div class="col-md-3">
			
           <div class="posts margin-bottom-20">
				<%@ include file="/layout/left.jsp"%>
           </div>
        
        </div><!--/span4-->
        <div class="col-md-9">
        	<div class="blog-post">
               <h3>祠堂简介</h3>
                <div class="headlineInfo">
	                 <h3>${ancestral.atTitle }</h3>
					<div class="infoBar">
						<div class="info">发布时间：<fmt:formatDate value="${ancestral.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						&nbsp;&nbsp;来源：</div>
					</div>
				   ${ancestral.atContent}
             	</div>
            </div>
        	
          
        </div><!--/col-md-9-->        

	</div><!--/row-fluid-->	
	<!-- //End Information Blokcs -->

</div>
</body>
</html>