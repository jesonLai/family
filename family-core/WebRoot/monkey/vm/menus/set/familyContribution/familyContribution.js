/**
 * 功德榜
 */
var familyContribution = function() {
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
	        	 "url":basePath+"/admin/menus/familyContribution/familyContributionList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "familyContributionId"},
		            { "data": "familyContributionHtmlContent" },
		            { "data": "familyEventType" },
		            { "data": "familyContributionTitle" },
		            { "data": "familyContributionContent" },
	                { "data": "familyContributionPushMan"},
	                { "data": "releseDate"},
	                { "data": "familyContributionDetail"},
	                { "data": "familyContributionDelete"}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"visible": false,"searchable": false},
	                {"targets": -2,"data": "familyContributionDetail","searchable": false,"orderable": false,"defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyContribution.detail(this);">详细</a>'},
	                {"targets": -1,"data": "familyContributionDelete","searchable": false,"orderable": false,"defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyContribution.remove(this);">删除</a>'}
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
            		required:"请输入功德榜类型",
            	},
            title:{
          	  required:"请输入功德榜标题"
            }
            },
            submitHandler: function(form) {
            	var txt=ue.getContentTxt();
            	if(txt.length>13)
            		$("#content").val(txt.substr(0,12)+"...");
            	else
            		$("#content").val(txt);
				  $.ajax({
					url : basePath+'/admin/menus/familyContribution/COM_familyContribution_SAVE',
					type : 'POST',
					dataType : 'json',
					data : $(form).serialize(),
					success : function(data) {
						if (!isEmpty(data.FAMILYNEWID)) {
							toastrs.success(data.message)
							$("#familyContributionId").val(data.FAMILYNEWID);
							familyContribution.reload();
							familyContribution.closeDialog();
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
			initTable("familyContributionTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyContribution").modal({
				backdrop: 'static',
				keyboard: false,
				show:true,
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			});
			
		},
		closeDialog:function(){
			$("#add-UEditor-familyContribution").modal('hide');
		},
		reload:function(){
			var table = $('#familyContributionTable').DataTable();
			table.ajax.reload();
			
		},
		detail:function(obj){
			var table = $('#familyContributionTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var familyContributionId=data[0].familyContributionId;
			var htmlContent=data[0].familyContributionHtmlContent;
			var familyContributionTitle=data[0].familyContributionTitle;
			var eventType=data[0].familyEventType;
			$.ajax({
				url : basePath+'/admin/menus/familyContribution/familyContributionAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyContribution-list").html(data);
					addNew();
					$("#familyContributionId").val(familyContributionId);
					$("#familyContributionTitle").val(familyContributionTitle);
					$("#eventType").val(eventType);
					$("#familyContributionTitle").addClass("edited");
					$("#eventType").addClass("edited");
					 ue.addListener("ready", function () {
						 ue.setContent(htmlContent,false);
				     });  
					 familyContribution.showDialog();
				}
		  }); 
		},
		edit:function(){
			$.ajax({
				url : basePath+'/admin/menus/familyContribution/familyContributionAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-UEditor-familyContribution-list").html(data);
					addNew();
					familyContribution.showDialog();
					
				}
		  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyContributionTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var familyContributionId=data[0].familyContributionId;
				$.ajax({
					url : basePath+'/admin/menus/familyContribution/FAMILY_FINANCIAL_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"familyNewsId":familyContributionId},
					success : function(data) {
						toastrs.success(data.message);
						familyContribution.reload();
					}
				});
			}
		}
	}

}();
