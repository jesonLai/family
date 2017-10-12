//##邮箱参数设置
var emailParameter = function() {
	return {
		init:function(){
			$(".email-parameter-form").validate({
	            invalidHandler: function(event, validator) { 
	            	return false; 
	            },
	            submitHandler: function(form) {
//	            	var txt=ue.getContentTxt();
//	            	if(txt.length>13)
//	            		$("#content").val(txt.substr(0,12)+"...");
//	            	else
//	            		$("#content").val(txt);
//					  $.ajax({
//						url : basePath+'/admin/menus/familyCelebrity/COM_FAMILYCELEBRITY_SAVE',
//						type : 'POST',
//						dataType : 'json',
//						data : $(form).serialize(),
//						success : function(data) {
//							if (!isEmpty(data.FAMILYNEWID)) {
//								toastrs.success(data.message)
//								$("#familyCelebrityId").val(data.FAMILYNEWID);
//								initTable("familyCelebrityTable");
//							} else {
//								toastrs.error(data.message);
//							}
//						}
//					});
	            	 $.ajax({
							url : basePath+'/admin/menus/emailParameter/COM_EMAILPARAMETER_SAVE',
							type : 'POST',
							dataType : 'json',
							data : $(form).serialize(),
							success : function(data) {
								toastrs.success(data.message)
							}
						});
	            	 return false;
	            }
			});
			$("#email-parameter-modal").modal({
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			})
			
		}
	}
}();
