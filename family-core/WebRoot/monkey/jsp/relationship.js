 relationship=function(){
	 
	 
	 var node_to_edit;
	    function loadjson(datas) {
	        var items = [];
	        var data = TAFFY(datas);
	        data({
	            "parent": ""
	        }).each(function(record, recordnumber) {
	            loops(record);
	        });
	        function loops(root) {
	        	var detail='<div class="top-news "><div class="portlet light bordered">'+
            	'<div class="portlet-title">'+
            		'<div class="caption font-green-haze">'+
            			'<i class="icon-user font-green-haze"></i> <span'+
            				'class="caption-subject bold uppercase" id="user_title_table">用户详情</span>'+
            		'</div><div class="actions"><div class="btn-group btn-group-devided" data-toggle="buttons"><button type="button" data-dismiss="modal" class="btn btn-default btn-circle" id="fullscreen-detail">全屏</button></div></div>'+
            	'</div>'+
            	'<div class="portlet-body "><div class="scroller" style="height:100px;" data-always-visible="1" data-rail-visible="1">' + root.userDesc +'</div></div>'+
            '</div></div>';
	            if (root.parent == "") {
	            	
	                items.push("<li class='unic" + root.id + " root' id='" + root.name + "'>" +
	                		"<span class='label_node'><a href='javascript:void(0);' onclick=relationship.selectPersons('"+root.id+"')>" +
	                				"<span id='userInfoName'>" + root.name + "</span></a></br>" +
	                						"<i id='appellation'>" + root.level + "</i></br>" +
	                						"<i id='spouseName'>"+root.spouseName+"</i>" +
	                						"<input type='hidden' value='"+root.cardId+"' id='cardId'>"+
	                						"</span>"+
	                		detail);
//	                "<div class='top-news'><a class='btn yellow'><p style='color:#FFFFFF;'><font style='line-height:150%;'>个人简介:" + root.userDesc + "</font></p></a></div>"
	            } else {
	            	
	                items.push("<li class='child unic" + root.id + "' id='" + root.name + "'>" +
	                		"<span class='label_node'><a href='javascript:void(0);' onclick=relationship.selectPersons('"+root.id+"')>" +
	                				"<span id='userInfoName'>" + root.name + "<span></a></br>" +
	                						"<i id='appellation'>" + root.level + "</i></br>" +
	                						"<input type='hidden' value='"+root.cardId+"' id='cardId'>"+
	                						"<i id='spouseName'>"+root.spouseName+"</i></span>"+
	                		detail);
	            }
	            var c = data({
	                "parent": root.id
	            }).count();
	            if (c != 0) {
	                items.push("<ul>");
	                data({
	                    "parent": root.id
	                }).each(function(record, recordnumber) {
	                    loops(record);
	                });
	                items.push("</ul></li>");
	            } else {
	                items.push("</li>");
	            }
	        } // End the generate html code

	        //push to html code
	        $("#org").remove();
	        $("<ul/>", {
	            "id": "org",
	            "style": "float:right;",
	            html: items.join("")
	        }).appendTo("body");
	    }
	    // End Load HTML
	    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		   var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		   var csrfToken = $("meta[name='_csrf']").attr("content");
		   var headers = {};
	    function init_tree() {
	        var opts = {
	        	cursor:"move",
	            chartElement: '#chart',
	            dragAndDrop: true,
	            expand: true,
	            control: false,
	            rowcolor: true
	        };
	        $("#chart").empty();
	        $("#org").jOrgChart(opts);
	    }

	    function scroll() {
	        $(".node").click(function() {
	            $("#chart").scrollTop(0)
	            $("#chart").scrollTop($(this).offset().top - 140);
	        })
	    }



	    function makeArrays() {
	        var hierarchy = [];

	        $("#org li").each(function() {
	            var uid = $(this).attr("id");
	            var name = $(this).find(">:first-child a").text();
	            var hidSTR = "";
	            var hid = $(this).parents("li");
	            if (hid.length == 0) //If this object is the root user, substitute id with "orgName" so the DB knows it's the name of organization and not a user
	            {
	                hidSTR = "orgName";
	                var user = new Object();
	                user.key = name;
	                user.hierarchy = hidSTR;
	                hierarchy.push(user);
	            } else {
	                for (var i = hid.length - 1; i >= 0; i--) {
	                    if (i != hid.length - 1) {
	                        hidSTR = hidSTR + hid[i].id + ",";
	                    } else {
	                        hidSTR = hidSTR + hid[i].id + '"';
	                    }
	                }
	                var user = new Object();
	                user.key = name;
	                user.hierarchy = hidSTR;
	                hierarchy.push(user);
	            }
	        });
	    }

		function selectPerson(id){
			$.ajax({
				url:basePath+'/user/isReletionship',
				data:{'userInfoId':id},
				type:'POST',
				 headers:headers,
				success:function(data){
					if(data){
//	 					$("#userInfoId").val(id);
//	 					$("#selectPersonForm").submit();
						$.ajax({
							url:basePath+'/user/relationshipData',
							data:{'userInfoId':id},
							type:'POST',
							dataType:"JSON",
							 headers:headers,
							success:function(data){
								
//								if(data.RELATIONSHIP==null||data.RELATIONSHIP==""||data.RELATIONSHIP.length==0){
//				 			  		$(".full-div").hide();
//						  		}else{
						  			$(".full-div").show();
						  			loadjson(data.RELATIONSHIP);
									init_tree();
									scroll()
									$( "#relationship" ).draggable({snapMode: "both",opacity: 0.7, cursor: "move", scroll: true}).css("cursor","move").find("div").css("cursor","pointer");
					    	        $("#fullScroll").toggle(function(){
					    	    		$(".full-div").addClass("full");
					    	    		$("#profile-content-relationship").css({"height":"100%"})
					    	    		$(this).html("还原")
					    	    	},function(){
					    	    		$(".full-div").removeClass("full");
					    	    		$("#profile-content-relationship").css({"height":""})
					    	    		$(this).html("全屏")
					    	    		
					    	    	})
//						  		}
							
							}
						});
					}else{
						alert('该成员没有关系成员');
					}
				}
			})
		}
		  function movieFormatResult(movie) {
	          return movie.text;
	      }

	      function movieFormatSelection(movie) {
	    	  selectPerson(movie.id);
	    	  
	          return movie.text;
	      }
		function searchPerson(){
			   headers[csrfHeader] = csrfToken;
			$("#userName").select2({
	            placeholder: '家族姓名',
	            minimumInputLength: 1,
	            ajax: {
	                url: basePath+"/user/userName",
	                type:'POST',
	                dataType: 'json',
	                headers:headers,
	                data: function (term, page) {
	                    return {
	                        q: term, // search term
	                        page_limit: 10,
	                    };
	                },
	                results: function (data, page) { 
	                    return {
	                        results: data
	                    };
	                },escapeMarkup: function (m) {
		                return m;
		            }
	            }, 
	            initSelection: function (element, callback) {
	            	  var id = $(element).val();
	                  if (id !== "") {
	                      $.ajax({
	                    	  url:basePath+"/user/userName", 
	                    	  data:{"id":id},
	                          dataType: "json",
	                          headers:headers,
	                          success:function(data){
	                        	  callback(data);
	                          }
	                      });
	                  }
	            },
	            formatResult: movieFormatResult, 
	            formatSelection: movieFormatSelection, 
	            dropdownCssClass: "bigdrop" // 
			});
		}
	    document.onselectstart=new Function("return false") 
    	return{
    		init:function(datas,ctx){
    			basePath=ctx;
			  	if(datas==null||datas==""||datas.length==0){
// 			  		$(".full-div").hide();
			  		alert("您的信息不详,无法显示您关系!");
		  		}
//			  		else{
		  			loadjson(datas);
	    	        init_tree();
	    	        scroll()
	    	        $( "#relationship" ).draggable({snapMode: "both",opacity: 0.7, cursor: "move", scroll: true}).css("cursor","move").find("div").css("cursor","pointer");
	    	        $("#fullScroll").toggle(function(){
	    	    		$(".full-div").addClass("full");
	    	    		$("#profile-content-relationship").css({"height":"100%"})
	    	    		$(this).html("还原")
	    	    	},function(){
	    	    		$(".full-div").removeClass("full");
	    	    		$("#profile-content-relationship").css({"height":""})
	    	    		$(this).html("全屏")
	    	    		
	    	    	})
//		  		}
    		},
    		searchPersons:function(){
    			searchPerson();
    		},
    		selectPersons:function(id){
    			selectPerson(id);
    		}
    	}
 }()