<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <sitemesh:write property='head'/> -->
<c:set var="ctx" value="${pageContext.request.contextPath}" />
		<div class="headline">
			<h3>本站推荐</h3>
		</div>
           <div class="posts">
           		<c:if test="${not empty person.authentication.principal}">
	           		<blockquote>
	                    <p><a href="${ctx}/user/cus-relationship.html" style="line-height:1;font-size:25px;color:black;">我的宗族</a></p>
	            	</blockquote>
	            </c:if>
           		<blockquote>
	                    <p><a href="${ctx}/user/announcement/announcementList" style="line-height:1;font-size:14px; color:#555">最近公告</a></p>
	            </blockquote>
	            <blockquote>
	                    <p><a href="${ctx}/user/news/newsMain/3.html" style="line-height:1;font-size:14px;color:#555">宗族财务</a></p>
	            </blockquote>
			 <blockquote>
	                    <p><a href="${ctx}/user/ancestral/ancestralList" style="line-height:1;font-size:14px; color:#555">宗族祠堂</a></p>
	            </blockquote>
                 <blockquote>
	                    <p><a href="${ctx}/user/news/mingrenList" style="line-height:1;font-size:14px;color:#555">能人贤达</a></p>
	            </blockquote>
                 <blockquote>
	                    <p><a href="${ctx}/user/news/newsMain/0.html" style="line-height:1;font-size:14px;color:#555">家族新闻</a></p>
	            </blockquote>
	            
	            <blockquote>
	                    <p><a href="${ctx}/user/familyNews/newsList/familyContribution/5" style="line-height:1;font-size:14px;color:#555">功德榜</a></p>
	            </blockquote>
           </div>