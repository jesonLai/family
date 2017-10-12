<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<title>首页</title>
<script type="text/javascript">
jQuery(function($){
	$('#familyNewsImage').slideBox({
		duration : 0.3,//滚动持续时间，单位：秒
		easing : 'linear',//swing,linear//滚动特效
		delay : 5,//滚动延迟时间，单位：秒
		hideClickBar : false,//不自动隐藏点选按键
		clickBarRadius : 10
	});
	$('#familyVideo').slideBox({
		duration : 0.3,//滚动持续时间，单位：秒
		easing : 'linear',//swing,linear//滚动特效
		delay : 5,//滚动延迟时间，单位：秒
		hideClickBar : false,//不自动隐藏点选按键
		clickBarRadius : 10
	});
	 
	
});
</script>
</head>
<body>
	<div class="container container-body">
<!-- The first START-->
		<div class="row margin-bottom-20 ">

<!-- 新闻列表START -->
			<div class="col-md-3" >
				<div class="headline">
					<h3>宗族新闻</h3>
					<a href="<%=basePath%>/user/news/newsMain/0.html" class="btn-more">
						更多<i class="fa fa-angle-double-right"></i>
					</a>
				</div>
				<ul class="basic-info cmn-clearfix">
				<c:forEach items="${newsList}" var="news">
					<li class="basicInfo-block basicInfo-left">
						<dt class="basicInfo-item name">
							<a target="_blank" href="<%=basePath%>/user/familyNews/newsInfo/${news.familyNewsId}" >${news.title}</a>
						</dt>
						<dd class="basicInfo-item value">
							<fmt:formatDate value="${news.releseDate}" pattern="yyyy-MM-dd"/>
						</dd>
					</li>
				</c:forEach>

				</ul>
			</div>
<!-- 新闻列表END -->
<!-- 新闻图片 START-->
			<div class="col-md-6" >
				<div id="familyNewsImage" class="slideBox">
				  <ul class="items">
				  	<c:forEach items="${newsListImg}" var="newImg" varStatus="status">
					<c:if test="${status.count<5}">
				    <li>
				    	<a href="<%=basePath%>/user/familyNews/newsInfo/${newImg.familyNewsId}" title=" ${fn:substring(newImg.title,0,10)}">
							<img src="<%=basePath%>/${newImg.thumbnailUrl}" style="width:100%; height: 278px;border: 1px solid #ddd;" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
				    	</a>
					</li>
					</c:if>
					
				  </c:forEach>
				  </ul>
				</div>
			</div>
<!-- 新闻图片END	-->			
<!-- 公告START -->
			<div class="col-md-3" >
				<div class="headline">
					<h3>最近公告</h3>
					<a href="<%=basePath%>/user/announcement/announcementList" class="btn-more">
						更多<i class="fa fa-angle-double-right"></i>
					</a>
				</div>
				<ul class="basic-info cmn-clearfix">
				<c:forEach items="${announcementList}" var="announcement">
					<li class="basicInfo-block basicInfo-left">
						<dt class="basicInfo-item name">
							<a target="_blank" href="<%=basePath%>/user/announcement/announcementDetail?id=${announcement.id}">${announcement.title}</a>
						</dt>
						<dd class="basicInfo-item value">
							<fmt:formatDate value="${announcement.createDate}" pattern="yyyy-MM-dd "/>
						</dd>
					</li>
				</c:forEach>
				
				</ul>
			</div>
<!-- 公告END			 -->
		</div>
<!-- 	The first END		 -->
<!-- 	The second START		 -->
<!-- 宗族祠堂START -->
		<div class="row margin-bottom-20">
				<div class="headline col-md-12" >
						<h3>宗族祠堂</h3>
						<a href="<%=basePath%>/user/ancestral/ancestralList" class="btn-more" >
							更多<i class="fa fa-angle-double-right"></i>
						</a>
				</div>
				<div class="col-md-12" style="padding: 0">
					<ul class="thumbnails">
						<c:forEach items="${ancestralTempleList }" var="ancestralTemple">
								<li class="col-md-3">
										<div class=" thumbnail-kenburn">
											<div class="thumbnail-img">
												<a href="<%=basePath%>/user/ancestral/ancestralDetail?id=${ancestralTemple.ancestralTempleId}" >
													<div class="overflow-hidden thumbnail">
															<img style="height: 160px;width:280px;" src="<%=basePath%>/${ancestralTemple.ancestralTempleImg}" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
															<div class="caption">
																<h5  style="overflow: hidden;margin-bottom: 0;text-align:center"><a href="<%=basePath%>/user/ancestral/ancestralDetail?id=${ancestralTemple.ancestralTempleId}" class="apostrophe">${ancestralTemple.ancestralTempleTitle }</a></h5>
															</div>
													</div>
												</a> 
											</div>
										</div>
									</li>
						</c:forEach>
					</ul>
					</div>
		</div>		
<!-- 宗族祠堂END-->
<!-- 	The second END		 -->		
<!-- The third START-->
<div class="row margin-bottom-20">
<!-- 文化历史START -->
	<div class="col-md-4">
		<div class="headline">
			<h3>留言板</h3>
			<a href="<%=basePath%>/user/news/newsMain/1.html" class="btn-more">
				更多<i class="fa fa-angle-double-right"></i>
			</a>
		</div>
		<ul class="basic-info cmn-clearfix">
			<c:forEach items="${messgaeList}" var="messgae">
				<li  class="basicInfo-block basicInfo-left">
					<dt class="basicInfo-item name">
						<a target="_blank" href="<%=basePath%>/user/message/messageList">${messgae.content}</a>
					</dt>
					<dd class="basicInfo-item value">
						<fmt:formatDate value="${messgae.messageDate}" pattern="yyyy-MM-dd "/>
					</dd>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="col-md-4" >
		<div class="headline">
			<h3>文化历史</h3>
			<a href="<%=basePath%>/user/news/newsMain/1.html" class="btn-more">
				更多<i class="fa fa-angle-double-right"></i>
			</a>
		</div>
		<ul class="basic-info cmn-clearfix">
			<c:forEach items="${wenhuList}" var="news">
				<li  class="basicInfo-block basicInfo-left">
					<dt class="basicInfo-item name">
						<a target="_blank" href="<%=basePath%>/user/familyNews/newsInfo/${news.familyNewsId}">${news.title}</a>
					</dt>
					<dd class="basicInfo-item value">
						<fmt:formatDate value="${news.releseDate}" pattern="yyyy-MM-dd "/>
					</dd>
				</li>
			</c:forEach>
		</ul>
	</div>
<!-- 文化历史END -->
<!-- 宗族视频START -->
	<div class="col-md-4">
		<div class="headline">
			<h3>宗族视频</h3>
			<a href="<%=basePath%>/user/familyVideo/videoList.html"  class="btn-more">
				更多<i class="fa fa-angle-double-right"></i>
			</a>	
		</div>
		<div id="familyVideo" class="slideBox">
		  <ul class="items">
		  	<c:forEach items="${videoList}" var="video">
		    <li>
		    	<a href="<%=basePath%>/user/familyVideo/videoDetail/${video.videoId}" title=" ${fn:substring(video.VideoTitle,0,10)}">
		    	<img src="<%=basePath%>/${video.videoImg}" style="width:365px; height: 278px;border: 1px solid #ddd;" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
		    	</a>
		    </li>
		  </c:forEach>
		  </ul>
		</div>
		
		
		
		
		
		
	</div>
<!-- 宗族视频END -->
</div>	
<!-- The third END-->
<!-- The fourth START-->
<!-- 能人贤达START -->
	<div class="row margin-bottom-20">
		<div class="headline col-md-12">
			<h3>能人贤达</h3>
			<a href="<%=basePath%>/user/news/mingrenList" class="btn-more">
				更多<i class="fa fa-angle-double-right"></i>
			</a>
		</div>
		<div id="familyCelebrity" class="col-md-12" style="padding: 0;overflow: hidden;"> 
			<ul class="thumbnails">
				<c:forEach items="${mingrenList}" var="mingren" varStatus="xIndex_MR">
					<li class="col-md-2">
						<div class=" thumbnail-kenburn">
							<div class="thumbnail-kenburn">
								<a href="<%=basePath%>/user/news/mingrenInfo?id=${mingren.userInfoId}" >
									<div class="overflow-hidden thumbnail">
										<img style=" height: 120px;width:100%" src="<%=basePath%>/${mingren.userInfoImg}" onerror="onerror=null;src='<%=basePath%>/front/image/zongzu.png'"/>
											<div class="caption">
												<p style="overflow: hidden; height: 25px;text-align:center">
													<a class="hover-effect" href="<%=basePath%>/user/news/mingrenInfo?id=${mingren.userInfoId}"> ${mingren.userInfoName }</a>
												</p>
											</div>
									</div>
								</a> 
							</div>
						</div>
					</li>
					<c:if test="${xIndex_MR.index>5 && xIndex_MR.index==fn:length(mingrenList)-1}">
							<script type="text/javascript">
								$('#familyCelebrity').jcMarquee({ 
									'marquee':'x',
									'speed':20 
								});
							</script>
					</c:if>
	
				</c:forEach>
			</ul>
		</div>
	</div>
<!-- 能人贤达END-->
<!-- The fourth END-->
</div>
</body>
</html>