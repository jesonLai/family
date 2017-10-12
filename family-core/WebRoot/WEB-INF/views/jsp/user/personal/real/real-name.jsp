<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<c:set var="person" value="${sessionScope.SPRING_SECURITY_CONTEXT}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_paraeter" content="${_csrf.parameterName}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>实名认证</title>
<link href="<%=basePath%>/monkey/global/plugins/googleapis/css/family.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/front/css/sky-forms.css" />
<!-- <link rel="stylesheet" href="<%=basePath%>/monkey/global/css/demo.css" type="text/css"> -->
<link rel="stylesheet"
	href="<%=basePath%>/monkey/global/css/metroStyle/metroStyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>

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
<script src="<%=basePath%>/front/js/jquery-migrate.min.js"></script>
<script src="<%=basePath%>/monkey/jsp/real-name.js"></script>
<script src="<%=basePath%>/front/js/jquery.validate.min.js" type="text/javascript"></script>
	

<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
	
<script type="text/javascript" src="<%=basePath%>/monkey/global/plugins/jquery-autocomplete/js/jquery.autocomplete.js"></script>
<style type="text/css">
#owmain_menu>li{float: left;}
</style>	
</head>
<body>
<div class="testi-slide">
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
			    <li><a href="javascript:;">实名认证</a></li>
			    <li></li>
			</ol>
		</div>
		<div class="col-md-12" style="background: white; padding-bottom: 20px">
			<div class="profile-sidebar">
				<!-- PORTLET MAIN -->
				<div class="portlet light profile-sidebar-portlet">
					<!-- SIDEBAR USERPIC -->
					<div class="profile-userpic">
						<%-- <img src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg"
							class="img-responsive" id="currHeadImg" alt=""
							onerror="onerror=null;src='<%=basePath%>/monkey/global/img/default_avatar_male.jpg'" /> --%>
							
							<c:if test="${empty HEANDIMAGESRC}">
								<img class="img-responsive" id="currHeadImg" src="<%=basePath%>/monkey/global/img/default_avatar_male.jpg" />
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
							<li><a href="<%=basePath%>"> <i class="icon-home"></i>
									主页
							</a></li>
							<li><a href="<%=basePath%>/user/center.html"> <i
									class="icon-settings"></i> 个人信息
							</a></li>
							<li><a href="<%=basePath%>/user/updatepwd.html" > <i
									class="icon-check"></i> 密码修改
							</a></li>
							<li><a href="<%=basePath%>/user/relationship.html"> <i
									class="icon-info"></i> 宗族关系
							</a></li>
							<li class="active"><a href="<%=basePath%>/user/front/real-name.html">
									<i class="icon-info"></i> 信息认证
							</a></li>
						</ul>
					</div>
					<!-- END MENU -->
				</div>
			</div>
			<!--sidebar-->
			<div class="profile-content">
			<c:set var="btnVal" value="提&nbsp;交"></c:set>
			<c:if test="${not empty USERINFOSELF}">
				<div class="col-md-11.5 message">
					
					<c:if test="${USERINFOSELF.userFlag==3}">
						<c:set var="addClass" value="alert alert-warning"></c:set>
						<c:set var="information" value="审核中"></c:set>
					</c:if>
					<c:if test="${USERINFOSELF.userFlag==1}">
						<c:set var="addClass" value="alert alert-success"></c:set>
						<c:set var="information" value="审核通过"></c:set>
					</c:if>
					<c:if test="${USERINFOSELF.userFlag==2}">
						<c:set var="addClass" value="alert alert-danger"></c:set>
						<c:set var="information" value="审核不通过，原因：${USERINFOSELF.remarks}"></c:set>
						<c:set var="btnVal" value="重新提交"></c:set>
					</c:if>
					
					<c:if test="${not empty addClass}">
					<div id="" class="${addClass}">${information}<button data-dismiss="alert" class="close">×</button></div>	
					</c:if>	
				</div>
			</c:if>
				<div class="row">
				
					<div class="col-md-12">
						
						<form autocomplete="off" id="realNameForm"
							action="#" method="post">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
							<input type="hidden" id="userChildrenId" name="userInfoList[0].userInfoId" value="${USERINFOSELF.userInfoId}" /> 
							
							<div class="portlet light ">
								<div class="portlet-title">
									<div class="caption caption-sm">
										<i class="icon-bar-chart theme-font hide"></i> <span
											class="caption-subject font-blue-madison bold uppercase">个人信息</span>
									</div>
									<div class="actions">
										<c:if test="${USERINFOSELF.userFlag!=1}">
											<div class="btn-group btn-group-devided" data-toggle="buttons">
												<button type="submit"
													class="btn btn-transparent green btn-circle btn-md active"
													style="margin-right: 20px">${btnVal}</button>
											</div>
										</c:if>
									</div>
								</div>
								<div class="portlet-body">


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">真实姓名</label>
											</div>
											<div class="col-md-9">
												<input type="text" id="userName"
													name="userInfoList[0].userName" class="form-control "
													placeholder="真实姓名"
													value="${USERINFOSELF.userName}" />
											</div>
											<p class="help-block"></p>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">性别</label>
											</div>
											<div class="col-md-9">
												<label class="select"> <select class="form-control"
													name="userInfoList[0].userSex">
														<option value="1">男</option>
														<option value="2">女</option>
														<option value="3">未知</option>
												</select>
												</label>
											</div>
										</div>
									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">出生年月</label>
											</div>
											<div class="col-md-9 " >
												<input type="text" name="userInfoList[0].userBornDate"
													placeholder="出生年月" id="full_name" class="form-control date-picker" data-date-format="yyyy-mm-dd"
													value="<fmt:formatDate value="${USERINFOSELF.userBornDate}" pattern="yyyy-mm-dd"/>" />
													
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">年龄</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userAge"
													placeholder="年龄" id="full_name" class="form-control "
													value="${USERINFOSELF.userAge}" />
											</div>
										</div>

									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">身份证</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userIdentityCard"
													placeholder="身份证" id="userIdentityCard"
													class="form-control "
													value="${USERINFOSELF.userIdentityCard}" />
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">家庭住址</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].homeAddress"
													placeholder="家庭住址" id="homeAddress" class="form-control "
													value="${USERINFOSELF.homeAddress}" />
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">婚姻状况</label>
											</div>
											<div class="col-md-9">
													<select class="form-control" name="userInfoList[0].maritalStatus">
														<option value="1" <c:if test="${USERINFOSELF.maritalStatus==1}">selected=selected</c:if>>未婚</option>
														<option value="2"  <c:if test="${USERINFOSELF.maritalStatus==2}">selected=selected</c:if>>已婚</option>
												</select>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">配偶名称</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].spouseName"
													placeholder="配偶名称" id="spouseName" class="form-control "
													value="${USERINFOSELF.spouseName}" />
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">QQ账号</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userQq"
													placeholder="QQ账号" id="userChildrenQq" class="form-control "
													value="${USERINFOSELF.userQq}" />
												
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">邮箱地址</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userEmail"
													placeholder="邮箱地址" id="userChildrenEmail" class="form-control"
													value="${USERINFOSELF.userEmail}" />
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">微信账号</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userWeixin"
													placeholder="微信账号" id="userChildrenWeixin" class="form-control "
													value="${USERINFOSELF.userWeixin}" />
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">手机号码</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[0].userPhone"
													placeholder="手机号码" id="userChildrenPhone" class="form-control "
													value="${USERINFOSELF.userPhone}" />
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="portlet light ">
								<div class="portlet-title">
									<div class="caption caption-sm">
										<i class="icon-bar-chart theme-font hide"></i> <span
											class="caption-subject font-blue-madison bold uppercase">父亲信息
<!-- 											<small class="color-red">*姓名与身份证号填写好不用等待审核</small> -->
										</span>
									</div>
								</div>
								<div class="portlet-body" id="fatherBody">
									<input type="hidden" id="userParentId" name="userInfoList[1].userInfoId" value="${USERINFOSELF.fatherUserInfo.userInfoId}" />
									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">真实姓名</label>
											</div>
											<div class="col-md-9">
												<input type="text" id="userFatherName"
													name="userInfoList[1].userName" class="form-control "
													placeholder="真实姓名"
													value="${USERINFOSELF.fatherUserInfo.userName}" />
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">性别</label>


											</div>
											<div class="col-md-9">
												<!-- 												 <input type="text" name="userInfoList[1].userSex" -->
												<!-- 														placeholder="性别" id="userSex" class="form-control "> -->

												<label class="select"> <select class="form-control"
													name="userInfoList[1].userSex" >
														<option value="1" selected="selected" >男</option>
												</select>
												</label>
											</div>
										</div>
									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">出生年月</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userBornDate" readonly="readonly"
													placeholder="出生年月" id="full_name" class="form-control date-picker" data-date-format="yyyy-mm-dd"/>
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">年龄</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userAge"
													placeholder="年龄" id="full_name" class="form-control ">
											</div>
										</div>

									</div>


									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">身份证</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userIdentityCard"
													placeholder="身份证" id="userIdentityCard"
													class="form-control "
													value="${USERINFOSELF.fatherUserInfo.userIdentityCard}" />
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">家庭住址</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].homeAddress"
													placeholder="家庭住址" id="homeAddress" class="form-control ">
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">婚姻状况</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].maritalStatus"
													placeholder="婚姻状况" id="maritalStatus" class="form-control ">
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">配偶名称</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].spouseName"
													placeholder="配偶名称" id="spouseName" class="form-control ">
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">QQ账号</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userQq"
													placeholder="QQ账号" id="userFatherQq" class="form-control ">
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">邮箱地址</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userEmail"
													placeholder="邮箱地址" id="userFatherEmail" class="form-control ">
											</div>
										</div>

									</div>

									<div class="form-group row">
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">微信账号</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userWeixin"
													placeholder="微信账号" id="userFatherWeixin" class="form-control ">
												<p class="help-block"></p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-3">
												<label class="control-label">手机号码</label>
											</div>
											<div class="col-md-9">
												<input type="text" name="userInfoList[1].userPhone"
													placeholder="手机号码" id="userFatherPhone" class="form-control ">
											</div>
										</div>
									</div>
								</div>

							</div>

							<!-- 							<div class="controls form-inline"> -->
							<!-- 								<button class="btn btn-lg green btn-md" id="register_member" -->
							<!-- 									type="submit">提交</button> -->
							<!-- 							</div> -->
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>


	<script type="text/javascript">
		realName.init('<%=basePath%>');
		
		//关闭提示框 
		jQuery(function(){
			$(".close").click(function(){
				$(".message").hide();
			})
			$('.date-picker').datepicker({
			    autoclose: true,
			    language: 'zh-CN',
			    autoclose: true,
                todayHighlight: true,
                format: "yyyy-mm-dd"
			});
		  url = encodeURI(encodeURI('<%=basePath%>/front/findUserName'));  
		  fatherPageUrl=encodeURI(encodeURI('<%=basePath%>/front/getFatherPage'));
		  fatherPage="<%=basePath%>/front/getFatherPage";
		  var userName=$('#userFatherName');
		  var userParentId=$("#userParentId").val();
			if( typeof(userParentId)!="undefined"&&userParentId!=""&&userParentId!="null"&&userParentId!=null){
				$.ajax({
		    		url:fatherPageUrl,
		    		data:{"id":userParentId},
		    		success:function(data){
		    			$("#fatherBody").html(data);
		    		}
		    	})
			}else{
				userName.autocomplete({
					serviceUrl:url,
				    minChars:1,
				    selectFirst:false,matchContains:false,
				    autoFill:true,
				    onSelect: function(suggestion) {
				    	if($("#userParentId").val()!=suggestion.data){
					    	$.ajax({
					    		url:fatherPageUrl,
					    		data:{"id":suggestion.data},
					    		success:function(data){
					    			$("#fatherBody").html(data);
					    		}
					    	})
				    	}
				    	$("#userParentId").val(suggestion.data);
				    	
				    }
				   
				})
				
			}
			
		})
		
	</script>
</body>
</html>