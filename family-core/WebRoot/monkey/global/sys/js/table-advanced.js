var TableAdvanced = function () {
	 var initTable1 = function () {
	        var table = $('#sample_2');
	        $.extend(true, $.fn.DataTable.TableTools.classes, {
	            "container": "btn-group tabletools-btn-group pull-right",
	            "buttons": {
	                "normal": "btn btn-sm default",
	                "disabled": "btn btn-sm default disabled"
	            }
	        });

	        var oTable = table.dataTable({
	            "language": {
	                "aria": {
	                    "sortAscending": ": activate to sort column ascending",
	                    "sortDescending": ": activate to sort column descending"
	                },
	                "emptyTable": "没有数据在这表格",
	                "info": "显示  _START_  到 _END_ 总_TOTAL_ 条",
	                "infoEmpty": "数据为空",
	                "infoFiltered": "(filtered1 from _MAX_ total entries)",
	                "lengthMenu": " 显示 _MENU_ 条",
	                "search": "搜索:",
	                "zeroRecords": "No matching records found"
	            },

	            "order": [
	                [0, 'asc']
	            ],
	            "lengthMenu": [
	                [5, 10, 15, 20, -1],
	                [5, 10, 15, 20, "全部"] // change per page values here
	            ],
	           
	            // set the initial value
	            "pageLength": 10,
	            "dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // horizobtal scrollable datatable

	            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
	            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: monkey/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
	            // So when dropdowns used the scrollable div should be removed. 
	            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

	            "tableTools": {
	                "sSwfPath": "monkey/global/plugins/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
	                "aButtons": [{
	                    "sExtends": "pdf",
	                    "sButtonText": "PDF"
	                }, {
	                    "sExtends": "csv",
	                    "sButtonText": "CSV"
	                }, {
	                    "sExtends": "xls",
	                    "sButtonText": "Excel"
	                }, {
	                    "sExtends": "print",
	                    "sButtonText": "Print",
	                    "sInfo": 'Please press "CTRL+P" to print or "ESC" to quit',
	                    "sMessage": "Generated by DataTables"
	                }, {
	                    "sExtends": "copy",
	                    "sButtonText": "Copy"
	                }, {
	                    "sExtends": "copyFlash",
	                    "sButtonText": "Print",
	                    "sInfo": 'Please press "CTRL+P" to print or "ESC" to quit',
	                    "sMessage": "Generated by DataTables"
	                }]
	            }
	           
	        });

	        var tableWrapper = $('#sample_2_wrapper'); // datatable creates the table wrapper by adding with id {your_table_jd}_wrapper
	        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
	    }
	return {
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }
            initTable1();
        }

    };

}();