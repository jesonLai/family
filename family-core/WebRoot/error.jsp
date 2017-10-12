<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
</head>
<body>
<div class="container" style="min-height:1200px">		
	<div class="row-fluid page-404">
    	<p ><i style="font-size:100px">Error</i> <span style="font-size:20px">您访问的页面可能已经删除、更名或暂时不可用。您可以尝试搜索一下</span></p>
    </div><!--/row-fluid-->  
	<div align="center">
		<a href="javascript:history.back();" class="btn-buj">请点击这里返回上一页</a>
	</div>
</div>
</body>
</html>