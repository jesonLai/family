
(function($){//重构ajax
   var _ajax=$.ajax;  
   var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
   var csrfHeader = $("meta[name='_csrf_header']").attr("content");
   var csrfToken = $("meta[name='_csrf']").attr("content");
   var headers = {};
   headers[csrfHeader] = csrfToken;
   $.ajax=function(opt){   
       var fn = {   
           error:function(XMLHttpRequest, textStatus, errorThrown){},   
            success:function(data, textStatus){}   
        }   
        if(opt.error){   
            fn.error=opt.error;   
        }   
        if(opt.success){   
            fn.success=opt.success;   
        }
        var _opt = $.extend(opt,{   
//        	contentType:'application/x-www-form-urlencoded;charset=utf-8',
            error:function(XMLHttpRequest, textStatus, errorThrown){   
                //错误方法增强处理   
            
                fn.error(XMLHttpRequest, textStatus, errorThrown);   
            },   
            success:function(data, textStatus){   
                //成功回调方法增强处理   
                fn.success(data, textStatus);   
            },
            headers: headers,
            statusCode: {
               400:function(){
            	   alert("无法理解的请求，请检查请求路径格式!");
                   return;
               },
               404 : function() {
            	   alert("资源不存在!");
                      return;
                },
	            500 : function() {
	            	alert("程序错误!");
	                  return;
	            },
                403 : function() {
                	alert("您没有权限!");
                      return;
                },
	            440 :function(){
	            	alert("会话失效!");
	            	window.top.location.href="/family/sessionTimeOut.jsp"
	            	return;
	            }
            }
        });   
        var def = _ajax.call($, _opt);                                                                                                                             // 兼容不支持异步回调的版本
        if('done' in def){
            var done = def.done;
            def.done = function (func) {
                function _done(data) {
                   
                    func(data);
                }

                done.call(def, _done);
                return def;
            };
        }
        return def;
//        _ajax(_opt);   
    };   
})(jQuery);  
function isEmpty(str){
	if(typeof(str)!="undefined"&&str!=""&&str!="null"&&str!=null){
		return false;
	}else{
		return true;
	}
} 
//截取根目录
function getBasePathHome(){
	var docUrl=self.document.URL || self.location.href;
	var confUrl=getConfigFilePath();
	 var basePath = confUrl;
     if (/^(\/|\\\\)/.test(confUrl)) {

         basePath = /^.+?\w(\/|\\\\)/.exec(docUrl)[0] + confUrl.replace(/^(\/|\\\\)/, '');

     } else if (!/^[a-z]+:/i.test(confUrl)) {

         docUrl = docUrl.split("#")[0].split("?")[0].replace(/[^\\\/]+$/, '');

         basePath = docUrl + "" + confUrl;

     }
     var path=basePath;
     protocol = /^[a-z]+:\/\//.exec(path)[ 0 ],
     tmp = null,
     res = [];
	    path = path.replace(protocol, "").split("?")[0].split("#")[0];
	    path = path.replace(/\\/g, '/').split(/\//);
	    path[ path.length - 1 ] = "";
	    while (path.length) {
	        if (( tmp = path.shift() ) === "..") {
	            res.pop();
	        } else if(tmp !== ".") {
	            res.push(tmp);
	        }
	    }
	    var resA=[];
	    resA.push(res[0]);
	    resA.push(res[1]);
	    resA.push("");
	    return protocol + resA.join("/");
}

