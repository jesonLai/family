/**
 * 广告
 */
var advertisement = function() {
	
	    var addNew=function(){
	  		$(".advertisement-form").validate({
	              invalidHandler: function(event, validator) { //display error alert on form submit  
	              	return false; 
	              },
	              submitHandler: function(form) {
	            	   var formData = new FormData(form);  
	  				  $.ajax({
	  					url : basePath+'/admin/menus/advertisement/COM_ADVERTISEMENT_ADD',
	  					type : 'POST',
	  					dataType : 'json',
	  					data : formData,
	  				  async: false,  
	  		          cache: false,  
	  		          contentType: false,  
	  		          processData: false,  
	  					success : function(data) {
	  							toastrs.success(data.message);
	  							advertisement.loading();
	  					}
	  				});
	              	 return false;
	              }
	  		});
	    }
	    return{
	    	init:function(){
	    		addNew();
	    	},
	    	loading:function(){
	    		Menu.refresh_menu_content('admin/menus/advertisement/advertisementAllPage');
	    	},
	    	remove:function(id){
	    		if(confirm("是否移除")){
	    			 $.ajax({
		  					url : basePath+'/admin/menus/advertisement/COM_ADVERTISEMENT_REMOVE',
		  					type : 'POST',
		  					dataType : 'json',
		  					data : {"id":id},
		  					success : function(data) {
		  							toastrs.success(data.message);
		  							advertisement.loading();
		  					}
		  				});
	    			
	    		}
	    	}
	    }
	    
}();