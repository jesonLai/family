/**
 * 依赖的js:DateTables:reload()
 * 		 bootstarp-model:showDialog()、closeDialog()
 * 
 */
BASE=function(){
		return{
			init:function(){
				
			},
			reload:function(obj){
				var table = obj.DataTable();
				table.ajax.reload();
			},
			showDialog:function(obj){
				obj.modal({
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
			closeDialog:function(obj){
				obj.modal('hide');
			}
		}
}();