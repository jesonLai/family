/**
 * 家族文化
 */

var familyCulture = function() {
	 var initTable = function (id) {
	        var table = $('#'+id);
	        table.children("tbody").empty();
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });
	      oTable = table.dataTable({
	        	"language": {
	                "url": basePath+"/monkey/global/plugins/datatables/Chinese.json"
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
	        	 "url":basePath+"/admin/menus/familyCulture/familyCultureList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyCultureId"},
		            { "data": "familyCultureHtmlContent" },
		            { "data": "familyEventType" },
		            { "data": "familyCultureTitle" },
		            { "data": "familyCultureContent" },
	                { "data": "familyCulturePushMan"},
	                { "data": "releseDate"},
	                { "data": "familyCultureDetail"},
	                { "data": "familyCultureDelete"},
	                { "data": "familyCulturePlacedTop"}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"visible": false,"searchable": false},
	                {"targets": -3,"data": "familyCultureDetail","searchable": false,"orderable": false,"defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyCulture.detail(this);">详细</a>'},
	                {"targets": -2,"data": "familyCultureDelete","searchable": false,"orderable": false,"defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyCulture.remove(this);">删除</a>'},
			        {"targets": -1,"data": "familyCulturePlacedTop","searchable": false,"orderable": false,"defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyCulture.top(this);">置顶</a>'}
	            ]
	        });
	    };
	var addNew=function(){
		$(".ueditor-form").validate({
			errorElement: 'span', 
            errorClass: 'help-block', 
              invalidHandler: function(event, validator) { 
              	return false; 
              },
              highlight: function(element) { 
                  $(element).closest('.form-group').addClass('has-error');
              },

              success: function(label) {
                  label.closest('.form-group').removeClass('has-error');
                  label.remove();
              },

              errorPlacement: function(error, element) {
                  error.insertAfter(element.next());
              },
            rules:{
            	eventType:{
            		required:true
            	},
              title:{
            	  required:true
              }
            },
            messages:{
            	eventType:{
            		required:"请输入家族文化类型",
            	},
            title:{
          	  required:"请输入家族文化标题"
            }
            },
            submitHandler: function(form) {
            	var txt=ue.getContentTxt();
            	if(txt.length>13)
            		$("#content").val(txt.substr(0,12)+"...");
            	else
            		$("#content").val(txt);
				  $.ajax({
					url : basePath+'/admin/menus/familyCulture/COM_FAMILYCULTURE_SAVE',
					type : 'POST',
					dataType : 'json',
					data : $(form).serialize(),
					success : function(data) {
						if (!isEmpty(data.FAMILYNEWID)) {
							toastrs.success(data.message)
							$("#familyCultureId").val(data.FAMILYNEWID);
							familyCulture.reload();
							familyCulture.closeDialog();
						} else {
							toastrs.error(data.message);
						}

					}
				});
            	 return false;
            }
		});
	}
	return {
		init:function(){
			initTable("familyCultureTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyCulture").modal({
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
			$("#add-UEditor-familyCulture").modal('hide');
		},
		reload:function(){
			var table = $('#familyCultureTable').DataTable();
			table.ajax.reload();
			
		},
		detail:function(obj){
			var table = $('#familyCultureTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyCultureId=data[0].familyCultureId;
			var htmlContent=data[0].familyCultureHtmlContent;
			var familyCultureTitle=data[0].familyCultureTitle;
			var eventType=data[0].familyEventType;
			$.ajax({
				url : basePath+'/admin/menus/familyCulture/familyCultureAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyCulture-list").html(data);
					addNew();
					$("#familyCultureId").val(familyCultureId);
					$("#familyCultureTitle").val(familyCultureTitle);
					$("#eventType").val(eventType);
					$("#familyCultureTitle").addClass("edited");
					$("#eventType").addClass("edited");
					 ue.addListener("ready", function () {
						 ue.setContent(htmlContent,false);
				     });  
					 familyCulture.showDialog();
				}
		  });
		},
		edit:function(){
			$.ajax({
				url : basePath+'/admin/menus/familyCulture/familyCultureAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyCulture-list").html(data);
					addNew();
					familyCulture.showDialog();
					
				}
		  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyCultureTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyCultureId=data[0].familyCultureId;
				$.ajax({
					url : basePath+'/admin/menus/familyCulture/FAMILY_CULTURE_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"familyNewsId":familyCultureId},
					success : function(data) {
						toastrs.success(data.message);
						familyCulture.reload();
					}
				});
			}
		},
		top:function(obj){
			var table = $('#familyCultureTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyCultureId=data[0].familyCultureId;
			$.ajax({
				url : basePath+'/admin/menus/familyCulture/FAMILY_CULTURE_TOP',
				type : 'POST',
				dataType : 'json',
				data : {"familyNewsId":familyCultureId},
				success : function(data) {
					if(data.result){
						toastrs.success(data.message);
					}else{
						toastrs.error(data.message);
					}
					
					familyCulture.reload();
				}
			});
			
		}
	}

}();
