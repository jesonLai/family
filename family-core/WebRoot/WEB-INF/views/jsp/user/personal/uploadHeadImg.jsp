<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}"/>
<c:set var="USERINFOPERSON" value="${sessionScope.USERINFOPERSON}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_paraeter" content="${_csrf.parameterName}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>上传头像</title>
<link href="<%=basePath%>/monkey/global/plugins/googleapis/css/family.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/front/css/sky-forms.css" />
<link rel="stylesheet"
	href="<%=basePath%>/monkey/global/css/metroStyle/metroStyle.css"
	type="text/css">


<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/jquery-multi-select/css/multi-select.css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />

<link
	href="<%=basePath%>/monkey/global/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=basePath%>/monkey/global/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/bootstrap-toastr/toastr.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" />
<link href="<%=basePath%>/monkey/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />



<link href="<%=basePath%>/monkey/pages/css/profile.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/front/css/settings.css" rel="stylesheet"
	type="text/css" media="screen">
<link rel="stylesheet" href="<%=basePath%>/front/css/headers/header1.css" />
<link href="<%=basePath%>/monkey/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />

<!-- END THEME STYLES -->
<link rel="shortcut icon" href="<%=basePath%>/monkey/favicon.ico" />
<script src="<%=basePath%>/front/js/jquery.min.js"></script>
<script src="<%=basePath%>/front/js/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/front/jquery-reAjax.js" type="text/javascript"></script>
<link
	href="<%=basePath%>/monkey/global/plugins/bootstrap-fileinput/css/bootstrap-fileinput.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>/monkey/global/plugins/bootstrap-fileinput/js/bootstrap-fileinput.js"></script>

<script type="text/javascript">
// 		jQuery(function() {
			function loadHeadImg(){
				 var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
				   var csrfHeader = $("meta[name='_csrf_header']").attr("content");
				   var csrfToken = $("meta[name='_csrf']").attr("content");
				   var headers = {};
				var form=document.getElementById("headImageForm");
				var formData=new FormData(form);
				$.ajax({
					url:'<%=basePath%>/user/saveUploadHeadImg',
					type : 'POST',
					dataType : 'json',
					data : formData,
					  async: false,  
			          cache: false,  
			          contentType: false,  
			          processData: false, 
					success:function(data){
						var result = data.result;
						
						if(result){
							$("#hImg").attr("src","<%=basePath%>"+"/"+data.imgPath);
							$("#currHeadImg").attr("src","<%=basePath%>"+"/"+data.imgPath);
							$("#headImageName").val(data.headImageName);
							$("#headImageFolder").val(data.headImageFolder);
							
							
						}else{
							alert(data.message);
						}
					},
					error:function(data){
						alert("上传失败！")
					}
				})
			}
// 		})
	</script>
<style type="text/css">
#owmain_menu>li{float: left;}
</style>
</head>
<body>
<div class="testi-slide ">
		<div class="container">
		   <ul class="slides">
			<c:forEach items="${NAVIGATIONIMG}" var="IMG">
				<li class="" >
					<a href="${IMG.href}"> <img src="<%=basePath%>/${IMG.httpUrl}"style="max-width: 1180px;max-height: 354px;" > </a>
				</li>
			</c:forEach>
			
		   </ul>
		</div><!--flex slider testimonials end here-->
		  			
</div>
	<div class="container container-body">
		<div class="row">
			<ol class="breadcrumb">
			    <li  class="action"><a href="<%=basePath%>">首页</a></li>
			    <li><a href="javascript:;">上传头像</a></li>
			    <li></li>
			</ol>
		</div>
		<div class="col-md-12" >
			<div class="profile-sidebar">
				<!-- PORTLET MAIN -->
				<div class="portlet light profile-sidebar-portlet">
					<!-- SIDEBAR USERPIC -->
					<div class="profile-userpic">
							<c:if test="${empty HEANDIMAGESRC}">
								<img class="img-responsive" id="currHeadImg" src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'"/>
							</c:if>
							<c:if test="${not empty HEANDIMAGESRC}">
								<img class="img-responsive" id="currHeadImg" src="<%=basePath%>/${HEANDIMAGESRC}" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'"/>
							</c:if>
							
					</div>
					<!-- END SIDEBAR USERPIC -->
					<!-- SIDEBAR USER TITLE -->
					<div class="profile-usertitle">
						<div class="profile-usertitle-name">
							${person.authentication.principal.username}</div>
						<!-- <div class="profile-usertitle-job">普通用户</div> -->
					</div>
					<!-- END SIDEBAR USER TITLE -->
					<!-- SIDEBAR BUTTONS -->
					<div class="profile-userbuttons">
						<button type="button" class="btn btn-circle green-haze btn-sm" onclick="window.location.href='<%=basePath%>/user/uploadHeadImg.html'"
							id="upload_img">上传头像</button>
						<!-- <button type="button" class="btn btn-circle btn-danger btn-sm"
							id="update_password">修改密码</button> -->
					</div>
					<!-- END SIDEBAR BUTTONS -->
					<!-- SIDEBAR MENU -->
					<div class="profile-usermenu">
						<ul class="nav">
							<li><a href="<%=basePath%>"> <i class="icon-home"></i> 主页
							</a></li>
							<li><a href="<%=basePath%>/user/center.html"> <i
									class="icon-settings"></i> 个人信息
							</a></li>
							<li><a href="<%=basePath%>/user/updatepwd.html">
									<i class="icon-check"></i> 密码修改
							</a></li>
							<li><a href="<%=basePath%>/user/relationship.html"> <i
									class="icon-info"></i> 宗族关系
							</a></li>
							<li><a href="<%=basePath%>/user/front/real-name.html"> <i
									class="icon-info"></i> 信息认证
							</a></li>
						</ul>
					</div>
					<!-- END MENU -->
				</div>
			</div>
			<!--sidebar-->
			<div class="profile-content">
				<div class="row">
					<div class="col-md-12">

						<%-- <form novalidate="" action="<%=basePath%>/user/updatePassWord.html" data-parsley-validate="true" class="sky-form" id="passwordForm"  method="post"> --%>
						<div class="portlet light ">
							<div class="portlet-title">
								<div class="caption caption-sm">
									<i class="icon-bar-chart theme-font hide"></i> <span
										class="caption-subject font-blue-madison bold uppercase">上传头像</span>
								</div>
								<!-- <div class="actions">
										<div class="btn-group btn-group-devided" data-toggle="buttons">
											<button type="submit" class="btn btn-transparent green btn-circle btn-md active" style="margin-right: 20px">
												提&nbsp;交
											</button> 
										</div>
									</div> -->
							</div>


							<div class="portlet-body" style="margin-top: 100px;">
								<div class="tab-pane" id="tab_1_2">
									<form action="<%=basePath%>/user/uploadHeadImg" method="post" enctype="multipart/form-data"  id="headImageForm">
									<input type="hidden" id="headImageName" name="headImageName" value="${USERINFOPERSON.headImageName}"/>
										<input type="hidden" id="headImageFolder" name="headImageFolder" value="${USERINFOPERSON.headImageFolder}"/>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										<div class="form-group last" style="height: 200px">
											<div class="col-md-12">
												<div class="fileinput fileinput-new"
													data-provides="fileinput">
													<div class="fileinput-new thumbnail"
														style="width: 200px; height: 150px;">
															<c:if test="${empty HEANDIMAGESRC}">
																<img class="img-responsive" src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg" alt="头像" id="hImg" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'"/>
															</c:if>
															<c:if test="${not empty HEANDIMAGESRC}">
																<img src="<%=basePath%>/${HEANDIMAGESRC}" alt="头像" id="hImg" onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'"/>
															</c:if>
															
													</div>
													<div class="fileinput-preview fileinput-exists thumbnail"
														style="max-width: 200px; max-height: 150px;"></div>
													<div>
														<span class="btn default btn-file"> <span
															class="fileinput-new"> 选择图片 </span> <span
															class="fileinput-exists"> 重新选择 </span> <input type="file"
															name="...">
														</span>
														<button type="button"
															class="btn green-haze fileinput-exists" onclick="loadHeadImg()" data-dismiss="fileinput">
															上传</button>
														<a href="javascript:;" class="btn red fileinput-exists"
															data-dismiss="fileinput"> 移除 </a>
													</div>
												</div>

											</div>
										</div>
									</form>

								</div>

							</div>


						</div>
						<!-- </form> -->
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>