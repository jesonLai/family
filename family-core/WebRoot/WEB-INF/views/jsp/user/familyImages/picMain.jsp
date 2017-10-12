<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族图片</title>
</head>
<body>
<div class="container container-body" >
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="<%=basePath%>">宗族图片</a></li>
		    <li></li>
		</ol>
	</div>
<c:forEach items="${map}" var="entry">
	<div class="headline">
		<h3>${entry.key }</h3>
          <a href="<%=basePath%>/user/image/imgList?type=${entry.key }" class="btn-more">
          	更多<i class="fa fa-angle-double-right"></i>
          </a>
	</div>
		<ul class="thumbnails" id="dsx" >
		<c:forEach items="${entry.value }" var="picImg" varStatus="status">
		<c:if test="${status.count<=4 }">
			<li class="col-md-3">
					<div class="thumbnail-kenburn">
						<div class="thumbnail-img">
							<a href="<%=basePath%>/user/image/imgInfo?imgId=${picImg.familyImgId}">
							<div class="overflow-hidden thumbnail">
									<img style="height: 165px;width:100%;" src="<%=basePath%>/${picImg.familyImgUrl}" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
								
								<div class="caption">
									<h3 style="overflow: hidden; height: 35px;">
										<a class="hover-effect" href="<%=basePath%>/user/image/imgInfo?imgId=${picImg.familyImgId}"> ${picImg.familyImgTitle}</a>
									</h3>
								</div>
								</div>
							</a>
						</div>
						
					</div>
				</li>
			</c:if>
			</c:forEach>
			
		</ul>
</c:forEach>
</div>
</body>
</html>