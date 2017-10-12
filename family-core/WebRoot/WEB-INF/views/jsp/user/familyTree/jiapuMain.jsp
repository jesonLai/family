<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宗族族谱</title>
</head>
<body>
<div class="container container-body">
	<div class="row">
		<ol class="breadcrumb">
		    <li  class="action"><a href="<%=basePath%>">首页</a></li>
		    <li><a href="javascript:;">宗族族谱</a></li>
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
        	<div class="row">
        	<div class="input-append" style="margin-top: 10px;margin-bottom: -15px;width: 100%;">
	            <form role="form" action="/<%=basePath%>/front/dynamic/list" id="dynamic_form" method="post">
               		<input class="col-md-9 search_GTE_title" id="search_GTE_title" style="margin-top: 1px;" placeholder="输入族譜标题搜索(空值查询全部)" value="" type="text">
	                <button type="button" class="btn-u btn-u-yellow" onclick="seachTitle()">搜索</button>
                </form>
            </div>
            </div>
               <div class="row">
            
			<c:forEach items="${map}" var="entry">
			<div class="headline">
				<h3>${entry.key}</h3>
					<a href="<%=basePath%>/user/familyNews/newsList/${entry.key}/0" class="btn-more">
						更多<i class="fa fa-angle-double-right"></i>
					</a>
			</div>
			      <ul class="basic-info cmn-clearfix">
				<c:forEach items="${entry.value}" var="news" varStatus="status">
					 <c:if test="${status.count<=5}">
						<li class="basicInfo-block basicInfo-left">
							<dt class="basicInfo-item name">
								<a href="<%=basePath%>/user/familyNews/newsInfo/${news.familyNewsId}"   target="_blank">
								${news.title}</a>
							</dt>
							<dd class="basicInfo-item value">
								<fmt:formatDate value="${news.releseDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</dd>
						</li>
					 </c:if>
				</c:forEach>
				</ul>
              </c:forEach> 
          </div>
        </div>
	</div>
<!-- 查询提交 -->
<form id="seach_title" action="<%=basePath%>/user/news/newsMain/title" method="post" >
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  <!-- post请求必备条件 -->
	<input type="hidden" name="ht_typeId" id="ht_typeId" /> 
	<input type="hidden" name="ht_title" id="ht_title" /> 
</form>	

</div>

<script type="text/javascript">

//点击查询
function seachTitle(){
	if($("#search_GTE_title").val()!=''){
		$("#ht_typeId").val("${tyId}");
		$("#ht_title").val($("#search_GTE_title").val());
		$("#seach_title").submit();
	}else{
		window.location.href="<%=basePath%>/user/news/newsMain?typeId=${tyId}"
	}
		
}

</script>
</body>
</html>