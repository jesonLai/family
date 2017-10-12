
(function($){//重构ajax
   var _ajax=$.ajax;  
   var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
   csrfHeader = $("meta[name='_csrf_header']").attr("content");
   csrfToken = $("meta[name='_csrf']").attr("content");
    headers = {};
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
            	   toastrs.info("无法理解的请求，请检查请求路径格式!");
                   return;
               },
               404 : function() {
            	   toastrs.info("资源不存在!");
                      return;
                },
	            500 : function() {
	            	toastrs.error("程序错误!");
	                  return;
	            },
                403 : function() {
                	toastrs.info("您没有权限!");
                      return;
                },
	            440 :function(){
	            	console.info("...")
	            	toastrs.info("会话失效!");
	            	window.top.location.href="/family/admin/login.html"
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


/* Copyright (c) 2006 Brandon Aaron (http://brandonaaron.net)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php) 
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 * $LastChangedDate: 2007-07-21 18:44:59 -0500 (Sat, 21 Jul 2007) $
 * $Rev: 2446 $
 *
 * Version 2.1.1
 */

(function($){

/**
 * The bgiframe is chainable and applies the iframe hack to get 
 * around zIndex issues in IE6. It will only apply itself in IE6 
 * and adds a class to the iframe called 'bgiframe'. The iframe
 * is appeneded as the first child of the matched element(s) 
 * with a tabIndex and zIndex of -1.
 * 
 * By default the plugin will take borders, sized with pixel units,
 * into account. If a different unit is used for the border's width,
 * then you will need to use the top and left settings as explained below.
 *
 * NOTICE: This plugin has been reported to cause perfromance problems
 * when used on elements that change properties (like width, height and
 * opacity) a lot in IE6. Most of these problems have been caused by 
 * the expressions used to calculate the elements width, height and 
 * borders. Some have reported it is due to the opacity filter. All 
 * these settings can be changed if needed as explained below.
 *
 * @example $('div').bgiframe();
 * @before <div><p>Paragraph</p></div>
 * @result <div><iframe class="bgiframe".../><p>Paragraph</p></div>
 *
 * @param Map settings Optional settings to configure the iframe.
 * @option String|Number top The iframe must be offset to the top
 * 		by the width of the top border. This should be a negative 
 *      number representing the border-top-width. If a number is 
 * 		is used here, pixels will be assumed. Otherwise, be sure
 *		to specify a unit. An expression could also be used. 
 * 		By default the value is "auto" which will use an expression 
 * 		to get the border-top-width if it is in pixels.
 * @option String|Number left The iframe must be offset to the left
 * 		by the width of the left border. This should be a negative 
 *      number representing the border-left-width. If a number is 
 * 		is used here, pixels will be assumed. Otherwise, be sure
 *		to specify a unit. An expression could also be used. 
 * 		By default the value is "auto" which will use an expression 
 * 		to get the border-left-width if it is in pixels.
 * @option String|Number width This is the width of the iframe. If
 *		a number is used here, pixels will be assume. Otherwise, be sure
 * 		to specify a unit. An experssion could also be used.
 *		By default the value is "auto" which will use an experssion
 * 		to get the offsetWidth.
 * @option String|Number height This is the height of the iframe. If
 *		a number is used here, pixels will be assume. Otherwise, be sure
 * 		to specify a unit. An experssion could also be used.
 *		By default the value is "auto" which will use an experssion
 * 		to get the offsetHeight.
 * @option Boolean opacity This is a boolean representing whether or not
 * 		to use opacity. If set to true, the opacity of 0 is applied. If
 *		set to false, the opacity filter is not applied. Default: true.
 * @option String src This setting is provided so that one could change 
 *		the src of the iframe to whatever they need.
 *		Default: "javascript:false;"
 *
 * @name bgiframe
 * @type jQuery
 * @cat Plugins/bgiframe
 * @author Brandon Aaron (brandon.aaron@gmail.com || http://brandonaaron.net)
 */
$.fn.bgIframe = $.fn.bgiframe = function(s) {
	// This is only for IE6
	if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
		s = $.extend({
			top     : 'auto', // auto == .currentStyle.borderTopWidth
			left    : 'auto', // auto == .currentStyle.borderLeftWidth
			width   : 'auto', // auto == offsetWidth
			height  : 'auto', // auto == offsetHeight
			opacity : true,
			src     : 'javascript:false;'
		}, s || {});
		var prop = function(n){return n&&n.constructor==Number?n+'px':n;},
		    html = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="'+s.src+'"'+
		               'style="display:block;position:absolute;z-index:-1;'+
			               (s.opacity !== false?'filter:Alpha(Opacity=\'0\');':'')+
					       'top:'+(s.top=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')':prop(s.top))+';'+
					       'left:'+(s.left=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')':prop(s.left))+';'+
					       'width:'+(s.width=='auto'?'expression(this.parentNode.offsetWidth+\'px\')':prop(s.width))+';'+
					       'height:'+(s.height=='auto'?'expression(this.parentNode.offsetHeight+\'px\')':prop(s.height))+';'+
					'"/>';
		return this.each(function() {
			if ( $('> iframe.bgiframe', this).length == 0 )
				this.insertBefore( document.createElement(html), this.firstChild );
		});
	}
	return this;
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

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};
Array.prototype.contains= function (obj) {
    for (var i = 0; i < this.length; i++) {
        if (obj == this[i]) {
            return true;
        }
    }
    return false;
} 