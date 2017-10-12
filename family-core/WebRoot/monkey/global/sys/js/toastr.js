var toastrs=function(){
	toastr.clear();
	return {
		init:function(){
            toastr.options = {
                    closeButton: true,
                    debug: false,
                    positionClass:'toast-top-center',
                    onclick: null
                };
                toastr.options.showDuration = "1000";
                toastr.options.hideDuration = "1000";
                toastr.options.timeOut = "5000";
                toastr.options.extendedTimeOut = "1000";
                toastr.options.showEasing = "swing";
                toastr.options.hideEasing = "linear";
                toastr.options.showMethod = "fadeIn";
                toastr.options.hideMethod = "fadeOut";
		},
		success:function(msg){
            toastr["success"](msg, "系统提示");
		},
		error:function(msg){
            toastr["error"](msg, "系统提示");
		},
		info:function(msg){
            toastr["info"](msg, "系统提示");
		},
		warning:function(msg){
            toastr["info"](msg, "系统提示");
		}
	};
}();