var USERSLOGIN=function(){
	console.info(isSystem)
	 var initTable = function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      oTable = table.dataTable({
	        	"language": {
	                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json?v=20160719001"
	            },
	          	"processing":true,
	            "serverSide":true,
	            "JQueryUI":true,
	            "Paginate":true,
	            "Filter":true,
	            "LengthChange":true,
	            "iDisplayStart":0,
	            "iDisplayLength": 5,
	            "ort":false,
	            "Info":true,
	            "Width":true,
	            "ScrollConllapse":true,
	            "sPaginationType":"full_numbers",
	            "bDestroy":true,
	            "bSortCellsTop": true,
	            "bStateSave":true,
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] 
	            ],
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/users/login/usersLoginList",
	        	 "type":"POST"
	            },
	            "columns": [
		            { "data": "loginId"},
	                { "data": "loginAccount" },
	                { "data": "loginName"},
	                { "data": "loginSex"},
	                { "data": "loginPhone"},
	                { "data": "loginIp"},
	                { "data": "loginLastTime"},
	                { "data": "loginRoles"},
	                { "data": "loginBrowserName"},
	                { "data": "loginOsName"},
	                { "data": "loginUpdateROle"},
	                { "data": "loginResetPwd"},
	                { "data": "loginBindData"},
	                { "data": "loginRemoveAccount","visible":true},
	                { "data": "loginStatus","visible":true}
             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets":-1,"searchable": false,"data":"loginRemoveAccount","render":function(data,type,full){
	                	return'<a href="javascript:void(0)" onclick="USERSLOGIN.removeAccount(\''+full.loginId+'\');">删除</a>'}},
	                {"targets":-3,"searchable": false,"data":"loginResetPwd","render":function(data,type,full){
	                	return'<a href="javascript:void(0)" onclick="USERSLOGIN.resetPwd(\''+full.loginId+'\');">重置密码</a>'}},
	                {"targets":-2,"searchable": false,"data":"loginBindData","render":function(data,type,full,meta){
	                	return '<a href="javascript:void(0)" onclick="USERSLOGIN.userInfosList('+full.loginId+');">绑定</a>'}
	                },
	                {"targets":-4,"searchable": false,"data":"loginBinloginUpdateROledData","render":function(data,type,full,meta){
	                	return '<a href="javascript:void(0)" onclick="USERSLOGIN.updateRoleAndAccount('+full.loginId+');">修改</a>'}
	                },
	                {"targets":-5,"searchable": false,"data":"loginStatus","render":function(data,type,full,meta){
	                	var flag="禁用";
	                	if(full.loginStatus=="1"){
	                		flag="启用"
	                	}
	                	return '<a href="javascript:void(0)" onclick="USERSLOGIN.changFlag('+full.loginId+');">'+flag+'</a>'}
	                }
	                ]
	        });
	    };
	    
	    
	    var userInfosTable = function (id) {
	        var table = $('#'+id);
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      var userTable = table.dataTable({
	        	"language": {
	                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json?v=20160719001"
	            },
	          	"processing":true,
	            "serverSide":true,
	            "JQueryUI":true,
	            "Paginate":true,
	            "Filter":true,
	            "LengthChange":true,
	            "iDisplayStart":0,
	            "iDisplayLength": 5,
	            "ort":false,
	            "Info":true,
	            "Width":true,
	            "ScrollConllapse":true,
	            "sPaginationType":"full_numbers",
	            "bDestroy":true,
	            "bSortCellsTop": true,
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] 
	            ],
	          	"ajax":{
	        	 "url":basePath+"/admin/menus/users/usersList",
	        	 "type":"POST"
	            },
	            "columns": [
	                        {
	                            "class": "text-center",
	                            "data": "userInfoId",
	                            "render": function (data, type, full, meta) {
	                                return '<input type="radio" name="userInfoId" class="checkchild"  value="'+ data + '" on/>';
	                            },
	                            "sortable": false
	                        },
		            { "data": "userInfoId"},
	                { "data": "userName" },
	                { "data": "userSex"},
	                { "data": "userPhone"},
	                { "data": "createDate"}
                ],
	            "columnDefs": [
	                {"targets": [ 1 ],"visible": false,"searchable": false}
	            ]
	        });
	    };
	    
	    
	    var addNew=function(){
	  		$(".user-add-new-form").validate({
	  			errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	              invalidHandler: function(event, validator) { //display error alert on form submit  
	              	return false; 
	              },
	              highlight: function(element) { // hightlight error inputs
	                  $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	              },

	              success: function(label) {
	                  label.closest('.form-group').removeClass('has-error');
	                  label.remove();
	              },

	              errorPlacement: function(error, element) {
	                  error.insertAfter(element.next());
	              },
	              rules: {
					userAccount:{
						 required: true,
						 remote:{
	                    	 url: basePath+"/admin/menus/users/login/checkUserAccount",
	                    	    type: "post",
	                    	    dataType: "json",   
	                    	    data: {
	                    	    	uId:function(data){
	                    	    		var uId=$("#uId").val();
	                    	    		if(isEmpty(uId)){
	                    	    			uId=0;
	                    	    		}
	                    	    		 return uId;
	                    	    	},
	                    	    	userAccount: function(data) {
	                    	            return $("#userAccount").val();
	                    	        }
	                    	    }
						 }
					},
					userPassword:{
				            	minlength:6
					}
	              },
	              messages:{
	            	userAccount:{
	            		required:"账号不能为空",
	            		remote:"账号已注册"
	            	} ,
	            	userPassword:{
	 	            	minlength: "密码长度不能小于 6个字母"
	            	}
	              },
	              
	              submitHandler: function(form) {
		            var checkedRoleId=[];
		            var delRoleIds=[];
		            var insRoleIds=[];
		            $('#custom-headers :selected').each(function(){
		            	checkedRoleId.push($(this).val());
		            })
		            for (var k = 0; k < userLoginInitRole.length; k++) {
						delRoleIds.push(userLoginInitRole[k]);
					}
		            for (var i = 0; i < checkedRoleId.length; i++) {
						if(delRoleIds.contains(checkedRoleId[i])){
							delRoleIds.remove(checkedRoleId[i])
						}else{
							insRoleIds.push(checkedRoleId[i]);
						}
					}
	  				  $.ajax({
	  					url : basePath+'/admin/menus/users/login/COM_USERSLOGIN_SAVE',
	  					type : 'POST',
	  					dataType : 'json',
	  					data : $(form).serialize()+"&delRoleIds="+delRoleIds+"&insRoleIds="+insRoleIds,
	  					success : function(data) {
	  						if(data.result){
	  							toastrs.success(data.message);
	  							USERSLOGIN.reload();
	  							USERSLOGIN.closeDialog();
	  						}else{
	  							toastrs.error(data.message);
	  						}
	  					}
	  				});
	              	 return false;
	              }
	  		});
	    }
	    
	    
	    
	    
	    
	    
	return{
		init:function(){
			initTable("usersLoginTable");
		},
		reload:function(){
			var table = $('#usersLoginTable').DataTable();
			table.ajax.reload();
		},
    	showDialog : function() {
			$("#add-new-form").modal({
				backdrop: 'static',
				keyboard: false,
				show:true,
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			})
		},
		closeDialog:function(){
			$("#add-new-form").modal('hide');
		},
	 	showUserInfoDialog : function() {
			$("#add-bind-userInfo-form").modal({
				backdrop: 'static',
				keyboard: false,
				show:true,
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			})
		},
	edit:function(uId){
		$.ajax({
			url : basePath+'/admin/menus/users/login/usersLoginAddPage',
			type : 'POST',
			data:{"uId":uId},
			dataType : 'html',
			success : function(data) {
				$(".add-new-userLogin").html(data);
				addNew();
				USERSLOGIN.showDialog();
				
				
			}
	  });
	},
	updateRoleAndAccount:function(uId){
		$.ajax({
			url : basePath+'/admin/menus/users/login/usersLoginAddPage',
			type : 'POST',
			data:{"uId":uId},
			dataType : 'html',
			success : function(data) {
				$(".add-new-userLogin").html(data);
				addNew();
				USERSLOGIN.showDialog();
				
			}
	  });
	},
	resetPwd:function(id){
		if(confirm("是否重置密码")){
			$.ajax({
				url : basePath+'/admin/menus/users/login/COM_RESET_PWD',
				type : 'POST',
				data:{"uId":id},
				dataType : 'json',
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
										
				}
		  });
		}
	},
	removeAccount:function(id){
		if(confirm("是否删除账号")){
			$.ajax({
				url : basePath+'/admin/menus/users/login/COM_LOGIN_ACCOUNT_REMOVE',
				type : 'POST',
				data:{"uId":id},
				dataType : 'json',
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
						USERSLOGIN.reload();		
					}else{
						toastrs.error(data.message);
					}
				}
		  });
		}
	},
	userInfosList:function(id){
		$.ajax({
			url : basePath+'/admin/menus/users/login/userInfosLoginAllPage',
			type : 'POST',
			dataType : 'html',
			success : function(data) {
				$(".add-bind-userInfo").html(data);
				userInfosTable("usersTable_Login")
				USERSLOGIN.showUserInfoDialog();
				$("#uid").val(id);
			}
	  });
	},
	changFlag:function(id){
	   if(/^[0-9]+$/.test(id)){
			$.ajax({
				url : basePath+'/admin/menus/users/login/COM_CHANGE_FLAG',
				type : 'POST',
				dataType : 'json',
				data:{"uId":id},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					USERSLOGIN.reload();
				}
		   });
	    }
	},
	addBindUserInfos:function(id){
		var radio=$("#"+id).find(":checked");
		if(radio.length!=1){
			alert("请选择家族成员！");
		}else{
			
			$.ajax({
				url : basePath+'/admin/menus/users/login/userInfosLoginSet',
				type : 'POST',
				dataType : 'json',
				data:{"uiId":radio.val(),"uId":$("#uid").val()},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					USERSLOGIN.reload();
				}
		  });
		}
	 }
	}
}();