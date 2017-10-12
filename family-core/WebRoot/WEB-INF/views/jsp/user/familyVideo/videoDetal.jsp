<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频展示</title>
</head>
<body>
<div class="container container-body" >	
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/familyVideo/videoMain.html">宗族视频</a></li>
		    <li  class="action"><a href="<%=basePath%>/user/familyVideo/videoList.html?type=${video.eventType}">${video.eventType}</a></li>
		    <li><a href="javascript:;">正文</a></li>
		    <li></li>
		</ol>
	</div>
	<div class="row margin-bottom-20">
        <div class="col-md-3">
           <div class="posts margin-bottom-20">
			<%@ include file="/layout/left.jsp"%>
           </div>
        </div>
        <div class="col-md-9">
        <div class="headline">
            	<h3>${video.title}</h3>
            	</div>
          		<div align="center">
                    <embed src="${video.flashUrl }" allowFullScreen="true" quality="high" width="870" height="600" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>
                </div>
	           	<div data-bd-bind="1464357974987" class="bdsharebuttonbox bdshare-button-style0-16">
	           	<a class="bds_more" href="#" data-cmd="more"></a>
	           	<a title="分享到QQ空间" class="bds_qzone" href="#" data-cmd="qzone"></a>
	           	<a title="分享到新浪微博" class="bds_tsina" href="#" data-cmd="tsina"></a>
	           	<a title="分享到腾讯微博" class="bds_tqq" href="#" data-cmd="tqq"></a>
	           	<a title="分享到人人网" class="bds_renren" href="#" data-cmd="+"></a>
	           	<a title="分享到微信" class="bds_weixin" href="#" data-cmd="weixin"></a></div>
				<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
        </div>
	</div>
</div>
</body>
</html>