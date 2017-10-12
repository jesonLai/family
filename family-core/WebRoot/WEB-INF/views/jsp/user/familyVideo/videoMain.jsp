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
		    <li><a href="javascript:;">宗族视频</a></li>
		    <li></li>
		</ol>
	</div>
	<c:forEach items="${map}" var="entry">
	<div class="headline">
		<h3>${entry.key }</h3>
          <a href="<%=basePath%>/user/familyVideo/videoList.html?type=${entry.key }"  class="btn-more"> 
      		 更多<i class="fa fa-angle-double-right"></i>
          </a>
	 </div>
		<ul class="thumbnails" id="dsx" >
		<c:forEach items="${entry.value }" var="video" varStatus="status">
		<c:if test="${status.count<=4 }">
			<li class="col-md-3">
					<div class="thumbnail-style thumbnail-kenburn">
						<div class="thumbnail-img">
							<a href="<%=basePath%>/user/familyVideo/videoDetail/${video.familyVideoId}">
								<div class="overflow-hidden">
									<img style="height: 165px;width:100%;" src="<%=basePath%>/${video.familyVideoUrl}" />
								</div>
							</a> 
						</div>
						<h3 style="overflow: hidden; height: 35px;">
							<a class="hover-effect" href="<%=basePath%>/user/familyVideo/videoDetail/${video.familyVideoId}"> ${video.familyVideoTitle}</a>
						</h3>
					</div>
				</li>
			</c:if>
			</c:forEach>
		</ul>
</c:forEach>

</div>
</body>
</html>