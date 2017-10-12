<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int current =  page.getNumber() + 1;
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());
System.out.println("page:"+page);
System.out.println("paginationSize:"+paginationSize);
System.out.println("current:"+current);
System.out.println("begin:"+begin);
System.out.println("end:"+end);

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
java.util.Map<String, Object> searchParams = (java.util.Map)request.getAttribute("searchParams");
String sql="";
if(searchParams!=null&&searchParams.size()!=0){
	for (String key : searchParams.keySet()) {
		sql+="&search_"+ key + "=" + searchParams.get(key);
		  }
}
%>

<%-- <div class="pagination">
	<ul>
		 <% if (page.hasPreviousPage()){%>
               	<li><a href="?page=1<%=sql%>">&lt;&lt;</a></li>
                <li><a href="?page=${current-1}<%=sql%>">&lt;</a></li>
         <%}else{%>
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
         <%} %>
 
		<c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="?page=${i}<%=sql%>">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="?page=${i}<%=sql%>">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
	  
	  	 <% if (page.hasNextPage()){%>
               	<li><a href="?page=${current+1}<%=sql%>">&gt;</a></li>
                <li><a href="?page=${page.totalPages}<%=sql%>">&gt;&gt;</a></li>
         <%}else{%>
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
         <%} %>
		<li style="float:left;margin-top:-3px;">
		<script type="text/javascript">
		$(function() {
			$("#pageGotoForm").submit(function(){
				var pageText = $("#page").val();
				var sumPage = ${page.totalPages};
				if(pageText>sumPage||pageText<1||isNaN(pageText)){
					alert("跳转的页数超出范围");
					return false;
				}
				return true;
			});
		});
			
		</script>
			<form id="pageGotoForm" action="" method="get" >
			<input type="text" name="page" id="page" class="form-control" style="width: 90px;margin-left:20px;"></input>
		<%			
			if(searchParams!=null){
				for (String key : searchParams.keySet()) {
					%>
			<input type="hidden" name="<%="search_"+key%>" id="<%="&search_"+key%>" value="<%=searchParams.get(key)%>"></input>	
					<%
				}
			}
		 %>		
			
			<input type="submit" value="跳转" class="btn btn-success" style="margin-top:-10px;">
			</form>
		</li>
	</ul>
</div> --%>
<c:if test="${end>1}">
<div>
		 <% if (page.hasPrevious()){%>
               <a href="?page=1<%=sql%>">第一页</a>
               <a href="?page=${current-1}<%=sql%>">上一页</a>
         <%}else{%>
               <a href="#">第一页</a>
               <a href="#">上一页</a>
         <%} %>
 
		<c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                  <a class="current" href="?page=${i}<%=sql%>">${i}</a>
                </c:when>
                <c:otherwise>
                   <a href="?page=${i}<%=sql%>">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
	  
	  	 <% if (page.hasNext()){%>
               <a href="?page=${current+1}<%=sql%>">下一页</a>
                <a href="?page=${page.totalPages}<%=sql%>">最后一页</a>
         <%}else{%>
                <a href="#">下一页</a>
                <a href="#">最后一页</a>
         <%} %>
		<script type="text/javascript">
		$(function() {
			$("#pageGotoForm").submit(function(){
				var pageText = $("#page").val();
				var sumPage = ${page.totalPages};
				if(pageText>sumPage||pageText<1||isNaN(pageText)){
					alert("跳转的页数超出范围");
					return false;
				}
				return true;
			});
		});
			
		</script>
			<form id="pageGotoForm" action="" method="get" >
			 <!-- <input type="text" name="page" id="page" class="form-control" style="width: 90px;"></input> -->
		<%			
			if(searchParams!=null){
				for (String key : searchParams.keySet()) {
					%>
						<input type="hidden" name="<%="search_"+key%>" id="<%="&search_"+key%>" value="<%=searchParams.get(key)%>"></input>	
					<%
				}
			}
		 %>		
			
			<!-- <input type="submit" value="跳转" class="btn btn-success" style="margin-top:-10px;"> -->
			</form>
</div>
</c:if>
