var headers=new Array();
var taskNames=new Array();
var projectNames=new Array();
var ajax_data =
	[
		{id:1,empId:"2",projectName:"Namko", taskName:"Testing", day1:"9",day2:"03",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"12"}, 
		{id:2,empId:"2",projectName:"PM", taskName:"Testing", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"9"}, 
		{id:3,empId:"2",projectName:"Namko", taskName:"Devlopement", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"9"}, 
		{id:4,empId:"2",projectName:"PM", taskName:"Devlopement", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"9"}, 
		{id:5,empId:"2",projectName:"Namko", taskName:"Design", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"9"}, 
		{id:6,empId:"2",projectName:"PM", taskName:"Design", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"9"}, 
		
	]
$(document).ready(function($)
{ 
	
	
	/*// get task names
	$.ajax({
		url : 'ProjectTimesheet',
		data : {
			action : $('#TaskName').val()
		},
		success : function(responseText) {
			var data = JSON.parse(responseText);
			var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Task--</option>';
			for (i = 0; i < data.length; i++) {
				//alert(data);
				optionHtml += '<option>'
						+ data[i].taskName
						+ '</option>';

			}
			

			$('.TaskNames').append(optionHtml);
			$('.TaskNames').html(optionHtml);
		}
	});

	var empID=2; // get from session
	// get Project  names with employee id
	$.ajax({
		url : 'ProjectTimesheet',
		data: 'action='+$('#ProjectName').val()+'&employeeId='+empID,
		success : function(responseText) {
			var data = JSON.parse(responseText);
			var optionHtml = ' <option value="0" disabled="disabled"  selected>--Select Project--</option>';
			for (i = 0; i < data.length; i++) {
				//alert(data);
				optionHtml += '<option>'
						+ data[i].projectName
						+ '</option>';

			}
			

			$('.ProjectNames').append(optionHtml);
			$('.ProjectNames').html(optionHtml);
			
		}
	});

*/	
	
	var startDate=$('#startDate').val();
	var endDate=$('#endDate').val();
	var empId=$('#empId').val();
	//get Headers
	$.ajax({
		url : 'ProjectTimesheet',
		data: 'action=getHeadersProjectAndTaskNames&startDate='+startDate+'&endDate='+endDate+'&empID='+empId,
		success : function(responseText) {
			var data = JSON.parse(responseText);
			console.log(data);
			headers=data.headers;
			taskNames=data.taskNames;
			projectNames=data.projectNames;
	
			console.log(headers);
			console.log(taskNames);
			console.log(projectNames);
			
			
			listTimesheetEntries.loadTimesheetEntries();
			populateDropDown($(".ProjectName"), projectNames,null,null,"Project Names ");
			populateDropDown($(".TaskName"), taskNames,null,null,"Task Names ");
		}
	});

	
	
	$('.ProjectName').multiselect({
    	buttonWidth: '280px',
    	includeSelectAllOption:true,
    	enableCaseInsensitiveFiltering: true,
    	nonSelectedText: 'Select Project Names',
    	maxHeight:300 
    });
	

	$('.TaskName').multiselect({
    	buttonWidth: '280px',
    	includeSelectAllOption:true,
    	enableCaseInsensitiveFiltering: true,
    	nonSelectedText: 'Select Project Names',
    	maxHeight:300 
    });
	
}); 
 
function populateDropDown(dropdownObj, data, value, text, firstOption){
	dropdownObj.empty(); //Remove All option
	if(isNES(firstOption)){
		dropdownObj.append($('<option></option>').val("").html(firstOption));
	}
	
	if(data != null){
		var dataArray = new Array();
		if(typeof data == "string"){
			dataArray.push(data);
		} else {
			$.merge(dataArray, data);
		}
	
		for(var i=0;i<dataArray.length;i++){
			var label = dataArray[i];
			var val = dataArray[i];
			
			if(isNES(text)){
				label = dataArray[i][text]+"".trim();
			}
			if(isNES(value)){
				if(dataArray[i][value] != null){
					val = dataArray[i][value]+"".trim();
				}
			}
			dropdownObj.append($('<option></option>').val(val).html(label.trim()));
		}
	}
}


function isNES(str){
	return !isEmptyString(str);
}


function isEmptyString(s) {
	if (typeof s == "undefined") {
		return true;
	}

	if (s == null) {
		return true;
	}
	
	if (s == "&nbsp;" || s == "&NBSP;" ) {
		return true;
	}
	if (s["@nil"] == "true" ||  s["@nil"] == true) {
		return true;
	}
	
	s = $.trim(s);
	if (s == "") {
		return true;
	}
	if(s == "null"){
		return true;
	}

	return false;
}

var listTimesheetEntries = { 
		
		
		loadListTimesheetDataTable:function(){
			ListTimesheetTable=$("#customer").DataTable(
					{
						"bFilter" : false,
						"destroy" : true,
						"bPaginate": false,
						"bInfo": false,
						"data": ajax_data,
						"aoColumns" : listTimesheetEntries.getColumns(),
						"footerCallback":function ( row, data, start, end, display ) {
						    var api = this.api(), data;

						    // converting to interger to find total
						    var intVal = function ( i ) {
						        return typeof i === 'string' ?
						            i.replace(/[\$,]/g, '')*1 :
						            typeof i === 'number' ?
						                i : 0;
						    };

						    // computing column Total the complete result 
						    var monTotal = api
						        .column( 2 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
								
							 var tueTotal = api
						        .column( 3 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
								
							 var wedTotal = api
						        .column( 4 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
								
							 var thuTotal = api
						        .column( 5 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
								
							 var friTotal = api
						        .column( 6 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
							 var satTotal = api
						        .column( 7 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
							 var sunTotal = api
						        .column( 8 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
							 var allTotal = api
						        .column( 9 )
						        .data()
						        .reduce( function (a, b) {
						            return intVal(a) + intVal(b);
						        }, 0 );
							
								
						    // Update footer by showing the total with the reference of the column index 
							$( api.column( 0 ).footer() ).html('Total');
							 $( api.column( 1 ).footer() ).html('------');
						    $( api.column( 2 ).footer() ).html(monTotal);
						    $( api.column( 3 ).footer() ).html(tueTotal);
						    $( api.column( 4 ).footer() ).html(wedTotal);
						    $( api.column( 5 ).footer() ).html(thuTotal);
						    $( api.column( 6 ).footer() ).html(friTotal);
						    $( api.column( 7 ).footer() ).html(satTotal);
						    $( api.column( 8 ).footer() ).html(sunTotal);
						    $( api.column( 9 ).footer() ).html(allTotal);
						    
						}, 
						"processing" : true,
						"aaSorting": [],
						/* "order": [[ 1, 'asc' ]], */
						"rowId" : 'id'
					});
		},
		
		getColumns:function(){
			 //{id:1,empId:"2",projectName:"Namko", taskName:"Testing", day1:"9",day2:"0",day3:"0",day4:"0",day5:"0",day6:"0",day7:"0",total:"0"}, 
			 var colArray = new Array();
			 
				
				var projectName = new Object();
				projectName.mDataProp = "projectName", 
				projectName.title= "Project Name", 
				projectName.sClass = "alignCenter";
				projectName.bSortable = false;
				
				projectName.mRender = function(obj, data, type, row) {
					
					return '<select id="ProjectName" name="ProjectName" class="form-control ProjectName" style="width:150px";> <option value="null" selected="selected">SELECT</option> </select>';
					
					
				};
				colArray.push(projectName);
				
				 var taskName = new Object();
				 taskName.mDataProp = "taskName", 
				 taskName.title= "Task Name", 
				 taskName.sClass = "alignCenter";
				 taskName.bSortable = false;
				 taskName.mRender = function(obj, data, type, row) {
					
					 return '<select id="TaskName" name="TaskName" class="form-control TaskName" style="width:150px";> <option value="null" selected="selected">SELECT</option> </select>';
					
					
				};
				colArray.push(taskName); 
				
				var day1 = new Object();
				day1.mDataProp = "day1", 
				day1.title= ""+headers[0], 
				day1.sClass = "alignCenter";
				day1.bSortable = false;
				
				day1.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day1'>" + type.day1 + "</div>";
					
					
				};
				colArray.push(day1);
				
				var day2 = new Object();
				day2.mDataProp = "day2", 
				day2.title= ""+headers[1], 
				day2.sClass = "alignCenter";
				day2.bSortable = false;
				
				day2.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day2'>" + type.day2 + "</div>";
					
					
				};
				colArray.push(day2);
				
				var day3 = new Object();
				day3.mDataProp = "day3", 
				day3.title= ""+headers[2], 
				day3.sClass = "alignCenter";
				day3.bSortable = false;
				
				day3.mRender = function(obj, data, type, row) {
					
					return"<div class='row_data' edit_type='click' col_name='day3'>" + type.day3 + "</div>";
					
					
				};
				colArray.push(day3);
				
				var day4 = new Object();
				day4.mDataProp = "day4", 
				day4.title= ""+headers[3], 
				day4.sClass = "alignCenter";
				day4.bSortable = false;
				
				day4.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day4'>" + type.day4 + "</div>";
					
					
				};
				colArray.push(day4);
				
				var day5 = new Object();
				day5.mDataProp = "day5", 
				day5.title= ""+headers[4], 
				day5.sClass = "alignCenter";
				day5.bSortable = false;
				
				day5.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day5'>" + type.day5 + "</div>";
					
					
				};
				colArray.push(day5);
				
				var day6 = new Object();
				day6.mDataProp = "day6", 
				day6.title= ""+headers[5], 
				day6.sClass = "alignCenter";
				day6.bSortable = false;
				
				day6.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day6'>" + type.day6 + "</div>";
					
					
				};
				colArray.push(day6);
				
				var day7 = new Object();
				day7.mDataProp = "day7", 
				day7.title= ""+headers[6], 
				day7.sClass = "alignCenter";
				day7.bSortable = false;
				
				day7.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click' col_name='day7'>" + type.day7 + "</div>";
					
					
				};
				colArray.push(day7);
				
				var total = new Object();
				total.mDataProp = "total", 
				total.title= " Total", 
				total.sClass = "alignCenter";
				total.bSortable = false;
				
				total.mRender = function(obj, data, type, row) {
					
					return "<div class='row_data' edit_type='click'   id='total"+type.id+"'  col_name='total'>" + type.total + "</div>";

					
					
				};
				colArray.push(total);
				
				
				
			 	var editLinkCol = new Object();
				editLinkCol.mDataProp = "editLinkCol",
				editLinkCol.title='<a class="btn btn-sm btn-info" onclick="listTimesheetEntries.addTimesheetrowForCL()"><i class="fa fa-fw fa-plus"></i>Add ROW</a> ',
				editLinkCol.sClass = "alignCenter";
				editLinkCol.bSortable = false;
				editLinkCol.mRender = function(obj, data, type, row) {
					return '<a class="btn btn-sm btn-info" onclick="listTimesheetEntries.dltTimesheetrowForCL('+type.id+')"><i class="fa fa-fw fa-minus"></i>Dlt ROW</a> '
				};
				colArray.push(editLinkCol);
		
				return colArray;
		},
		
		/* addTimesheetrowForCL:function(data,total){
			var tbl='';
			var i=0;
			var len_array=[];
			var table = $("table tbody");
			table.find('tr').each(function(i) {
				var $tds = $(this).find('td'),
				day1Value = $tds.eq(1).text();
				len_array.push(day1Value);
				
			});
			var row_id_newRow=len_array.length+1;
			
			tbl +='<tr id="'+row_id_newRow+'">';
			tbl +='<td ><div   col_name="projectName"></div></td>';
			
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day1">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day2">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day3">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day4">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day5">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day6">0</div></td>';
			tbl +='<td ><div class="row_data" edit_type="click" col_name="day7">0</div></td>';
			tbl +='<td ><div class="total_sum" edit_type="change"  id="total'+row_id_newRow+'" col_name="total">0 </div></td>';
			tbl +='<td ><a class="btn btn-sm btn-info deleteRow"><i class="fa fa-fw fa-minus">Delete Row</i></a></td></td>';
			//--->edit options > end
			
		tbl +='</tr>';
		
		/* $("#timesheet").append(tbl); 
		ListOfContactTable.row.add(jsonData).draw();
		
		
		$(".deleteRow").click(function(){
            
            $(this).parents("tr").remove();
            row_id_newRow--;
   			 });
		
		
		
		
		}, */
		
		/**/
		
		
		 addTimesheetrowForCL:function(){
				var jsonData = new Object();
				var table_length = ListTimesheetTable.rows().data().count();
	    		var cnt=table_length+1;
	    		var cnte=cnt.toString();
	    		
	    		jsonData.id= cnte;			
				//jsonData.empId = "2";
				jsonData.projectName = "Namko";
			 	jsonData.taskName = "Testing";
			 	jsonData.day1 = "0";
			 	jsonData.day2 = "0";
			 	jsonData.day3 = "0";
			 	jsonData.day4 = "0";
			 	jsonData.day5 = "0";
			 	jsonData.day6 = "0";
			 	jsonData.day7 = "0";
			 	jsonData.total = "0"; 
			 	//jsonData.editLinkCol='<a class="btn btn-sm btn-info" onclick="listTimesheetEntries.dltTimesheetrowForCL('+jsonData.id+')"><i class="fa fa-fw fa-plus"></i>Add ROW</a>';
	        				//unique ID for CRUD 
	    		ListTimesheetTable.row.add(jsonData).draw(false);
	    		//ListTimesheetTable.Rows(jsonData.id).Item(1) = "2";
		 },
		 
		 dltTimesheetrowForCL:function(id_LOC){
			 var c_intex=ListTimesheetTable.row(id_LOC).index();
				
				if(c_intex==undefined){
					ListTimesheetTable.row(id_LOC-1).remove().draw( false );
				}
				else{
					ListTimesheetTable.row(c_intex-1).remove().draw( false );
				} 
		 },
		 
		calFooterColumn : function(data,total){
			// converting to interger to find total
		    var intVal = function ( i ) {
		        return typeof i === 'string' ?
		            i.replace(/[\$,]/g, '')*1 :
		            typeof i === 'number' ?
		                i : 0;
		    };
		   
			//var aavc=$('tfoot th#day1Footer').text(); // footer valu by <th> id
			
			var day;
			
	switch (data.col_location) {

			case "day1":

				var changedValue = data.day1;
				var day1 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'),
					day1Value = $tds.eq(2).text();
					totalValue = $tds.eq(9).text();
					day1.push(day1Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day1[index_id] = changedValue;
				all[index_id] = total;

				var monTotal = day1.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(2).footer()).html(monTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			case "day2":
				
				var changedValue = data.day2;
				var day2 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day2Value = $tds.eq(3).text();
					totalValue = $tds.eq(9).text();
					day2.push(day2Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day2[index_id] = changedValue;
				all[index_id] = total;

				var tueTotal = day2.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(3).footer()).html(tueTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);

				break;
			case "day3":
				var changedValue = data.day3;
				var day3 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day3Value = $tds.eq(4).text();
					totalValue = $tds.eq(9).text();
					day3.push(day3Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day3[index_id] = changedValue;
				all[index_id] = total;

				var wedTotal = day3.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(4).footer()).html(wedTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			case "day4":
				var changedValue = data.day4;
				var day4 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day4Value = $tds.eq(5).text();
					totalValue = $tds.eq(9).text();
					day4.push(day4Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day4[index_id] = changedValue;
				all[index_id] = total;

				var thuTotal = day4.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(5).footer()).html(thuTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			case "day5":
				var changedValue = data.day5;
				var day5 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day5Value = $tds.eq(6).text();
					totalValue = $tds.eq(9).text();
					day5.push(day5Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day5[index_id] = changedValue;
				all[index_id] = total;

				var friTotal = day5.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(6).footer()).html(friTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			case "day6":
				var changedValue = data.day6;
				var day6 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day6Value = $tds.eq(7).text();
					totalValue = $tds.eq(9).text();
					day6.push(day6Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day6[index_id] = changedValue;
				all[index_id] = total;

				var satTotal = day6.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(7).footer()).html(satTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			case "day7":
				var changedValue = data.day7;
				var day7 = [];
				var all = [];

				var table = $("table tbody");
				table.find('tr').each(function(i) {
					var $tds = $(this).find('td'), 
					day7Value = $tds.eq(8).text();
					totalValue = $tds.eq(9).text();
					day7.push(day7Value);
					all.push(totalValue);
				});

				var index_id = ListTimesheetTable.row(data.row_id - 1).index(
						'visible');
				day7[index_id] = changedValue;
				all[index_id] = total;

				var sunTotal = day7.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				var allTotal = all.reduce(function(a, b) {
					return intVal(a) + intVal(b);
				}, 0);

				$(ListTimesheetTable.column(8).footer()).html(sunTotal);
				$(ListTimesheetTable.column(9).footer()).html(allTotal);
				break;
			}

			console.log(day);

		},

		loadTimesheetEntries : function() {
			listTimesheetEntries.loadListTimesheetDataTable();
		}

	}

	$(document).on('click', '.row_data', function(event) {
		event.preventDefault();

		if ($(this).attr('edit_type') == 'button') {
			return false;
		}

		//make div editable
		$(this).closest('div').attr('contenteditable', 'true');
		//add bg css
		$(this).addClass('bg-warning').css('padding', '5px');

		$(this).focus();
	})
	//--->make div editable > end

	//--->save single field data > start
	$(document).on(
			'focusout',
			'.row_data',
			function(event) {
				event.preventDefault();
				var tbl_row = $(this).closest('tr');

				if ($(this).attr('edit_type') == 'button') {
					return false;
				}

				var row_id = $(this).closest('tr').attr('id');
				
				var row_div = $(this).removeClass('bg-warning') //add bg css
				.css('padding', '')

				var col_name = row_div.attr('col_name');
				var col_val = row_div.html();
				
				if(col_val>24){
					alert("Please enter less than 24 hours");
					row_div.html("0");
					col_val=0;
				}
				var pattern =  /^[0-9]*$/; 
				if(!pattern.test(col_val)){
				    alert("Please enter only numbers");
					row_div.html("0");
					col_val=0;
				}
				
				
				var arr = {};
				arr[col_name] = col_val;
				arr.col_location = col_name;
				var total = 0;
				var total_sum = 0;
				tbl_row.find('.row_data').each(function(index, val) {
					var col_name = $(this).attr('col_name');
					var col_val = $(this).html();
					//arr[col_name] = col_val;
					if (col_name != 'total' && col_val != "") {
						total += parseInt(col_val);
					}
					/* else if(col_name=='total'){
						total_sum+=parseInt(col_val);
					} */
				});
				console.log(total);
				//arr["total"]=total;
				//use the "arr"	object for your ajax call
				$.extend(arr, {
					row_id : row_id
				});
				$('#total' + row_id).html(total);

				var data1 = ListTimesheetTable.column(1).data();

				listTimesheetEntries.calFooterColumn(arr, total);
				//$('#total_sum').html(total_sum);
				//out put to show
				$('.post_msg').html(
						'<pre class="bg-success">'
								+ JSON.stringify(arr, null, 2) + '</pre>');

			})
