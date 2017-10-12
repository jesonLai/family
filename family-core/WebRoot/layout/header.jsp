<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />

<div class="container ">
<!-- <nav class="navbar">                 -->
                <div class="row" >
	                <div class="col-md-3" >
	                    <form name="instationSearch" method="GET" target="_blank">
						    <input type="text" class="form-search-control" placeholder="站内搜索" id="searchVal" />
						    <button type="button" class="btn btn-default" >站内搜索</button>
						</form>
		            </div>
	                <div class="col-md-9" style="padding: 0">
	                  	<ul class="loginbar" >
				            <c:if test="${not empty person.authentication.principal}">
				           		 <li id="member_log1" >
				           		 	<a href="<%=basePath%>/user/center.html" >你好，${person.authentication.principal.username}</a>
				            	</li>|
				            	<li id="member_log2" >
				            		 <a href="<%=basePath%>/logout" >退出</a>
				            	</li>
				            </c:if>
				            <c:if test="${empty person.authentication.principal}">
					            <li id="member_log1" >
					           		 <a href="<%=basePath%>/login.html" >登录</a>
					            </li>|
					            <li id="member_log2">
					                <a href="<%=basePath%>/user/register.html" >注册</a>
					            </li>
							</c:if>
			       		</ul>
			       		</div>
		       		</div>

<!-- </nav> -->
<center class="index-movie">
      <object
        classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
        codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,42,0"
        id="广告"
        width="1170" height="268"
      >
        <param name="movie" value="<%=basePath%>/monkey/indexImg.swf">
        <param name="bgcolor" value="#CCE8CF">
        <param name="quality" value="high">
        <param name="seamlesstabbing" value="false">
        <param name="allowscriptaccess" value="samedomain">
        <embed
          type="application/x-shockwave-flash"
          pluginspage="http://www.adobe.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"
          name="广告"
          width="1170" height="268"
          src="<%=basePath%>/monkey/indexImg.swf"
          bgcolor="#CCE8CF"
          quality="high"
          seamlesstabbing="false"
          allowscriptaccess="samedomain"
        >
          <noembed>
          </noembed>
        </embed>
      </object>
    </center>
   
    <div class="header container">   
         <nav class="navbar">                 
            <div class="navbar-inner">                                  
                <div class="collapse navbar-collapse ">                                     
                    <ul class="nav navbar-nav" id="owmain_menu">
                        <li  id="indexMenu">
                            <a href="<%=basePath%>" class="index-one">首页</a>                       
                        </li>
                        <li id="fimalyNewMenu">
                            <a href="<%=basePath%>/user/news/newsMain/0.html" class=" a-radius" >宗族新闻</a>                      
                        </li>
                        <li id="jiapuMenu">
                            <a href="<%=basePath%>/user/news/newsMain/4.html" class=" a-radius" >宗族族谱</a>                       
                        </li>
                        <li id="wenhuaMenu">
                            <a href="<%=basePath%>/user/news/newsMain/1.html" class=" a-radius" >宗族文化</a>                    
                        </li>
                        <li id="mingrenMenu">
                            <a href="<%=basePath%>/user/news/mingrenList" class=" a-radius" >能人贤达</a>              
                        </li>
                        <li id="picMenu">
                            <a href="<%=basePath%>/user/image/imgMain" class=" a-radius" >宗族图册</a>
                                                  
                        </li>
                        <li id="vedioMenu">
                            <a href="<%=basePath%>/user/familyVideo/videoMain.html" class=" a-radius">宗族视频</a>                        
                        </li>    
                        <li id="caiwuMenu">
                            <a href="<%=basePath%>/user/news/newsMain/3.html" class=" a-radius" >宗族财务</a>                        
                        </li>  
                        <li id="fimalyMessage">
                            <a href="<%=basePath%>/user/message/messageList" class=" a-radius" >留言板</a>                        
                        </li>
                        <li id="jianjie">
                            <a href="<%=basePath%>/user/ancestral/ancestralList" class=" a-radius">宗族祠堂</a>                        
                        </li>   
                        <li id="gonggao">
                            <a href="<%=basePath%>/user/announcement/announcementList" class=" a-radius">宗族公告</a>                        
                        </li> 
                    </ul>
       
                </div>                  
            </div>
  </nav>  
        </div>     
                             
    </div>          
<script type="text/javascript">
<!--
 jQuery(function($){
    $.getScript( "<%=basePath%>/monkey/jsp/header-front.js", function() {
    	instationSearch.init('<%=basePath%>');
    });
});
-->
</script>
