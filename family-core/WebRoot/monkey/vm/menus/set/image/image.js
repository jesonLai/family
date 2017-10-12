//图片管理
var FormFileUpload = function () {
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
	        	 "url":basePath+"/admin/menus/image/familyImageList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "imgId"},
		            { "data": "thumbnailUrl" ,"searchable": false,"orderable": false},
		            { "data": "name" },
		            { "data": "title" },
	                { "data": "htmlContent"},
	                { "data": "eventType"},
	                { "data": "lastModified"},
	                { "data": "uploadMan"},
	                { "data": "detail"},
	                { "data": "remove","searchable": false,"orderable": false}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"searchable": false,"render":function(data,type,full){
	                     return " <a href="+basePath+"/"+data+" class='fancybox-button' data-rel='fancybox-button' >" +
	                     		"<img src="+basePath+"/"+data+" width='160' height='90'/></a>"}},
	         		 {"targets": [ 2 ],"visible": false,"searchable": false},
	                {"targets": -2,"data": "ancestralTempleDetail","defaultContent": 
	     	                	'<a href="javascript:void(0)" onclick="FormFileUpload.detail(this);">详细</a>'},
                    {"targets": [ -1 ],"data": "remove","defaultContent": 
                    	'<a href="javascript:void(0)" onclick="FormFileUpload.remove(this);">删除</a>'}
	               
	            ],
	            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
	            	var htmlContent=aData.htmlContent;
	            	var div=document.createElement("div");
	            	$(div).html(htmlContent);
	            	var text=$(div).text();
	                if (text.length > 13) {
	                	text=text.substr(0, 12)+"...";
	                }
	                $('td:eq(2)', nRow).text(text);
	            }
	        });
	    };
	    var addNew=function(){
	  		$("#fileupload").validate({
	              invalidHandler: function(event, validator) { //display error alert on form submit  
	              	return false; 
	              },
	              submitHandler: function(form) {
	            	   var formData = new FormData(form);  
	  				  $.ajax({
	  					url : basePath+'/admin/menus/image/action/upload',
	  					type : 'POST',
	  					dataType : 'json',
	  					data : formData,
	  				  async: false,  
	  		          cache: false,  
	  		          contentType: false,  
	  		          processData: false,  
	  					success : function(data) {
	  						if (data.result) {
	  							toastrs.success(data.message);
	  							FormFileUpload.reload();
	  							FormFileUpload.closeDialog();
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
        //main function to initiate the module
        init: function () {
        	initTable("imageTable");
             // Initialize the jQuery File Upload widget:
//            $('#fileupload').fileupload({
//                disableImageResize: false,
//                autoUpload: false,
//                disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
//                maxFileSize: 5000000,
//                acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//                // Uncomment the following to send cross-domain cookies:
//                //xhrFields: {withCredentials: true},        
//            });
//
//            // Enable iframe cross-domain access via redirect option:
//            $('#fileupload').fileupload(
//                'option',
//                'redirect',
//                window.location.href.replace(
//                    /\/[^\/]*$/,
//                    '/cors/result.html?%s'
//                )
//            );
//
//            // Upload server status check for browsers with CORS support:
//            if ($.support.cors) {
//                $.ajax({
//                    type: 'HEAD',
//                    error:function(){
//                    	 $('<div class="alert alert-danger"/>')
//                         .text('Upload server currently unavailable - ' +
//                                 new Date())
//                         .appendTo('#fileupload');
//                    }
//                })
//            }

            // Load & display existing files:
//            $('#fileupload').addClass('fileupload-processing');
//            $.ajax({
//                // Uncomment the following to send cross-domain cookies:
//                //xhrFields: {withCredentials: true},
//                url: $('#fileupload').attr("action"),
//                dataType: 'json',
//                context: $('#fileupload')[0]
//            }).always(function () {
//                $(this).removeClass('fileupload-processing');
//            }).done(function (result) {
//                $(this).fileupload('option', 'done')
//                .call(this, $.Event('done'), {result: result});
//            });
            $(".fancybox-button").fancybox();
        },
        ueditor:function(parameterName,token){
			 UE.delEditor("editor");
			 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
			 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
		},
		reload:function(){
			var table = $('#imageTable').DataTable();
			table.ajax.reload();
			
		},
	    showDialog : function() {
			$("#add-image").modal({
				backdrop: 'static', 
				keyboard: false,
				show:true,
				width : '80%'
			}).draggable({
				handle : '.modal-header',
				cursor : 'move',
				refreshPositions : false
			});
			$('#add-image').on('hide.bs.modal', function () {
				initTable("imageTable");
			})
			
	    },
		closeDialog:function(){
			$("#add-image").modal('hide');
		},
		detail:function(obj){
			var table = $('#imageTable').DataTable();
			var data = table.rows($(obj).parents("tr")).data();
			var htmlContent=data[0].htmlContent;
			var imgId=data[0].imgId;
			var thumbnailUrl=data[0].thumbnailUrl;
			var title=data[0].title;
			var name=data[0].name;
			var folder=data[0].folder;
			var eventType=data[0].eventType
			 $.ajax({
					url : basePath+'/admin/menus/image/imageAddPage',
					type : 'POST',
					dataType : 'html',
					success : function(data) {
						$(".add-new-image").html(data);
						$("#imageNew").attr("src",basePath+"/"+thumbnailUrl);
						$("#title").val(title);
						$("#title").addClass("edited");
						$("#eventType").addClass("edited");
						$("#eventType").val(eventType)
						$("#imgId").val(imgId);
						$("#name").val(name)
						$("#folder").val(folder)
						 ue.addListener("ready", function () {
							 ue.setContent($.trim(htmlContent),false);
					     });  
						addNew();
						 FormFileUpload.showDialog();
					}
			  });
		},
	    editor:function(){
	    	$.ajax({
				url : basePath+'/admin/menus/image/imageAddPage',
				type : 'POST',
				dataType : 'html',
				success : function(data) {
					$(".add-new-image").html(data);
					addNew();
					FormFileUpload.showDialog();
					
				}
		  });
	    	
	    },
	    remove:function(obj){
	    	if(confirm("是否删除!")){
		    	var table = $('#imageTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var id=data[0].imgId;
				  $.ajax({
					url : basePath+'/admin/menus/image/removeImageUpload',
					type : 'POST',
					dataType : 'json',
					data : {"imgId":id},
					success : function(data) {
						toastrs.success(data.message)
	
					}
				});
				  initTable("imageTable");
	    	}
	    }
    };

}();