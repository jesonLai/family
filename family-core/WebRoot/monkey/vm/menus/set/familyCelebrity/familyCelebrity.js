/**
 * 家族名人
 */

var familyCelebrity = function() {
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
	        	 "url":basePath+"/admin/menus/familyCelebrity/familyCelebrityList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "userInfoId"},
		            { "data": "userInfoName" },
		            { "data": "userInfoHeadImage" },
		            { "data": "userInfoDesc" },
		            { "data": "userInfoCreateDate" },
	                { "data": "userInfoReleaseFlag"},
	                { "data": "familyCelebrityDetail"},
	                { "data": "familyCelebrityDelete"}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 2 ],"searchable": false,"orderable": false,"render":function(data,type,full){
	                     return " <a href='"+basePath+"/"+data+"' class='fancybox-button' data-rel='fancybox-button' >" +
	                     		"<img src='"+basePath+"/"+data+"' width='160' height='90' onerror='oerror=null;src=&apos;"+basePath+"/front/image/zongzu.png&apos;'/></a>"}},
	         		 {"targets": [ 5 ],"searchable": false,"orderable": false,"render":function(data,type,full){
	         			 var flag='未在首页展示';
	         			 if(data==1)flag='首页展示中...';
	         				 
	                     return '<a href="javascript:void(0)" onclick="familyCelebrity.release(this);">'+flag+'</a>'}},
	                {"targets":  -2,"searchable": false,"orderable": false,"data": "familyCelebrityDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="familyCelebrity.detail(this);">详细</a>'},
	                {"targets":-1,"searchable": false,"orderable": false,"data": "familyCelebrityDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="familyCelebrity.remove(this);">删除</a>'}
	            ],
	            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
	            	var userInfoDesc=aData.userInfoDesc;
	            	var div=document.createElement("div");
	            	$(div).html(userInfoDesc);
	            	var text=$(div).text();
	            	
	                if (text.length > 13) {
	                	
	                	text=text.substr(0, 12)+"...";
	                }
	                $('td:eq(2)', nRow).text(text);
	            }
	        });
	     
	    };
	var addNew=function(){
		$(".ueditor-form").validate({
            invalidHandler: function(event, validator) { //display error alert on form submit   
            	return false; 
            },
            submitHandler: function(form) {
            	 var formData = new FormData(form);  
            	 var table = $('#familyCelebrityTable').DataTable();
				  $.ajax({
					url : basePath+'/admin/menus/familyCelebrity/COM_FAMILYCELEBRITY_SAVE',
					type : 'POST',
					dataType : 'json',
					data :formData,
					  async: false,  
	  		          cache: false,  
	  		          contentType: false,  
	  		          processData: false,  
					success : function(data) {
						if (!isEmpty(data.FAMILYNEWID)) {
							toastrs.success(data.message)
							$("#userInfoId").val(data.FAMILYNEWID);
							familyCelebrity.reload();
							familyCelebrity.closeDialog();
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
			initTable("familyCelebrityTable");
		},
		ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		showDialog : function() {
			$("#add-UEditor-familyCelebrity").modal({
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
			$("#add-UEditor-familyCelebrity").modal('hide');
		},
		reload:function(){
			var table = $('#familyCelebrityTable').DataTable();
			table.ajax.reload();
			
		},
		
		detail:function(obj){
			var table = $('#familyCelebrityTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var htmlContent=data[0].userInfoDesc;
			var familyCelebrityId=data[0].userInfoId;
			
			 $.ajax({
					url : basePath+'/admin/menus/familyCelebrity/familyCelebrityAddPage',
					type : 'POST',
					data:{"userInfoId":familyCelebrityId},
					dataType : 'html',
					success : function(data) {
						$(".add-UEditor-familyCelebrity-list").html(data);
						addNew();
						$("#userInfoId").val(familyCelebrityId);
						 ue.addListener("ready", function () {
							 ue.setContent(htmlContent,false);
					     });  
						 familyCelebrity.showDialog();
					}
			  });
		},
		edit:function(){
			 $.ajax({
					url : basePath+'/admin/menus/familyCelebrity/familyCelebrityAddPage',
					type : 'POST',
					dataType : 'html',
					success : function(data) {
						$(".add-UEditor-familyCelebrity-list").html(data);
						addNew();
						familyCelebrity.showDialog();
						
					}
			  });
		},
		remove:function(obj){
			if(confirm("是否删除!")){
				var table = $('#familyCelebrityTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var userInfoId=data[0].userInfoId;
				$.ajax({
					url : basePath+'/admin/menus/familyCelebrity/FAMILY_CELEBRITY_DEL',
					type : 'POST',
					dataType : 'json',
					data : {"userInfoId":userInfoId},
					success : function(data) {
						toastrs.success(data.message);
						familyCelebrity.reload();
					}
				});
			}
		},
		release:function(obj){
			var table = $('#familyCelebrityTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var userInfoId=data[0].userInfoId;
			$.ajax({
				url : basePath+'/admin/menus/familyCelebrity/FAMILY_CELEBRITY_RELEASE',
				type : 'POST',
				dataType : 'json',
				data : {"userInfoId":userInfoId},
				success : function(data) {
					toastrs.success(data.message);
					familyCelebrity.reload();
				}
			});
		}
	}

}();
