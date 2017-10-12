/**
 * 祠堂
 */
var ancestralTemple = function() {
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
	        	 "url":basePath+"/admin/menus/ancestralTemple/ancestralTempleList",
	        	 "type":"POST"
	            }
	            ,
	            "columns": [
		            { "data": "ancestralTempleId"},
		            { "data": "ancestralTempleSrc" },
		            { "data": "ancestralTempleImg" },
		            { "data": "ancestralTempleTitle" },
		            { "data": "ancestralTempleContent" },
	                { "data": "ancestralTemplePushMan"},
	                { "data": "ancestralTempleCreateDate"},
	                { "data": "ancestralTempleDetail","searchable": false,"orderable": false},
	                { "data": "ancestralTempleDelete","searchable": false,"orderable": false},
	                { "data": "announcementPlacedTop","searchable": false,"orderable": false}

	             ],
	            "columnDefs": [
	                {"targets": [ 0 ],"visible": false,"searchable": false},
	                {"targets": [ 1 ],"searchable": false,"render":function(data,type,full){
	                     return " <a href="+basePath+"/"+data+" class='fancybox-button' data-rel='fancybox-button' >" +
	                     		"<img src="+basePath+"/"+data+" width='160' height='90'/></a>"}},
             		{"targets": [ 2 ],"visible": false,"searchable": false},
	                {"targets": -3,"data": "ancestralTempleDetail","defaultContent": 
	                	'<a href="javascript:void(0)" onclick="ancestralTemple.detail(this);">详细</a>'},
	                {"targets": -2,"data": "ancestralTempleDelete","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="ancestralTemple.remove(this);">删除</a>'},
			        {"targets": -1,"data": "announcementPlacedTop","defaultContent": 
		                	'<a href="javascript:void(0)" onclick="ancestralTemple.top(this);">置顶</a>'}
	            ],
	            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
	            	var ancestralTempleContent=aData.ancestralTempleContent;
	            	var div=document.createElement("div");
	            	$(div).html(ancestralTempleContent);
	            	var text=$.trim($(div).text());
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
	  				  $.ajax({
	  					url : basePath+'/admin/menus/ancestralTemple/COM_ANCESRALTEMPLE_SAVE',
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
	  							ancestralTemple.reload();
	  							ancestralTemple.closeDialog();
	  						} else {
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
	    		initTable("ancestralTempleTable")
	    	},
			ueditor:function(parameterName,token){
				 UE.delEditor("editor");
				 ue = UE.getEditor('editor',{initialFrameWidth :0,initialFrameHeight:0,scaleEnabled:true});
				 ue.ready(function() {ue.execCommand('serverparam',parameterName, token); });
			},
	    	showDialog : function() {
				$("#add-UEditor-ancestralTemple").modal({
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
				$("#add-UEditor-ancestralTemple").modal('hide');
			},
			reload:function(){
				var table = $('#ancestralTempleTable').DataTable();
				table.ajax.reload();
				
			},
			
			detail:function(obj){
				var table = $('#ancestralTempleTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var htmlContent=data[0].ancestralTempleContent;
				var ancestralTempleId=data[0].ancestralTempleId;
				var ancestralTempleSrc=data[0].ancestralTempleSrc;
				var ancestralTempleFolder=data[0].ancestralTempleFolder;
				var ancestralTempleImg=data[0].ancestralTempleImg;
				var ancestralTempleTitle=data[0].ancestralTempleTitle;
				 $.ajax({
						url : basePath+'/admin/menus/ancestralTemple/ancestralTempleAddPage',
						type : 'POST',
						dataType : 'html',
						success : function(data) {
							$(".add-UEditor-ancestralTemple-list").html(data);
							$("#ancestralTempleId").val(ancestralTempleId);
							$("#ancestralTempleSrc").attr("src",basePath+ancestralTempleSrc);
							$("#ancestralTempleFolder").val(ancestralTempleFolder);
							$("#ancestralTempleImg").val(ancestralTempleImg);
							$("#ancestralTempleTitle").val(ancestralTempleTitle);
							$("#ancestralTempleTitle").addClass("edited");
							 ue.addListener("ready", function () {
								 ue.setContent(htmlContent,false);
						     });  
							 addNew();
							 ancestralTemple.showDialog();
						}
				  });
			},
			edit:function(){
				$.ajax({
					url : basePath+'/admin/menus/ancestralTemple/ancestralTempleAddPage',
					type : 'POST',
					dataType : 'html',
					success : function(data) {
						$(".add-UEditor-ancestralTemple-list").html(data);
						addNew();
						ancestralTemple.showDialog();
						
					}
			  });
			},
			remove:function(obj){
				if(confirm("是否删除!")){
					var table = $('#ancestralTempleTable').DataTable();
					var data = table.rows($(obj).parents("tr")).data();
					var ancestralTempleId=data[0].ancestralTempleId;
					$.ajax({
						url : basePath+'/admin/menus/ancestralTemple/FAMILY_ANCESTRALTEMPLE_DEL',
						type : 'POST',
						dataType : 'json',
						data : {"atId":ancestralTempleId},
						success : function(data) {
							toastrs.success(data.message);
							ancestralTemple.reload();
						}
					});
				}
			},
			top:function(obj){
				var table = $('#ancestralTempleTable').DataTable();
				var data = table.rows($(obj).parents("tr")).data();
				var ancestralTempleId=data[0].ancestralTempleId;
				$.ajax({
					url : basePath+'/admin/menus/ancestralTemple/FAMILY_ANCESTRALTEMPLE_TOP',
					type : 'POST',
					dataType : 'json',
					data : {"atId":ancestralTempleId},
					success : function(data) {
						if(data.result){
							toastrs.success(data.message);
						}else{
							toastrs.error(data.message);
						}
						
						ancestralTemple.reload();
					}
				});
			}
	    }
	    
}();