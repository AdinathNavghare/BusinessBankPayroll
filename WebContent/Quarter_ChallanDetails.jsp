<%@page import="payroll.Model.Quarter_ChallanBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quarter_Challan Details</title>
 <script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/MONTHPICK/jquery-ui.css">

<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>

<!-- <style>
.ui-datepicker-calendar {
	display: none;
}
</style> -->

<%Quarter_ChallanBean qcbean=new Quarter_ChallanBean();
qcbean=(Quarter_ChallanBean)request.getAttribute("qcbean");
String str=(request.getParameter("flag")== null?"0":request.getParameter("flag")).toString();
//System.out.println("value of flag is : "+(request.getAttribute("flag")== null?"0":request.getAttribute("flag")).toString());
System.out.println("flag value "+((request.getParameter("flag")==null?"":request.getParameter("flag"))));
%>

<script>

function report(){
	
   var flag1=document.getElementById("flag").value;
	
	
	if(flag1=="1")
		{
		alert("Records Updated Successfully!!");
		}
	else if(flag1=="2")
		{
		alert("Records Inserted Successfully!!!");
		}
	
}
		/* function setMonth(){
			var month = document.getElementById("month").value;
			var year = document.getElementById("year").value;
			var arr = month.split("-");
			var selectYear = arr[1];
			var month = 0;
			if (arr[0] == "Jan") {
				month = 1;
			}
			if (arr[0] == "Feb") {
				month = 2;
			}
			if (arr[0] == "Mar") {
				month = 3;
			}
			if (arr[0] == "Apr") {
				month = 4;
			}
			if (arr[0] == "May") {
				month = 5;
			}
			if (arr[0] == "Jun") {
				month = 6;
			}
			if (arr[0] == "Jul") {
				month = 7;
			}
			if (arr[0] == "Aug") {
				month = 8;
			}
			if (arr[0] == "Sep") {
				month = 9;
			}
			if (arr[0] == "Oct") {
				month = 10;
			}
			if (arr[0] == "Nov") {
				month = 11;
			}
			if (arr[0] == "Dec") {
				month = 12;
			}
			
			var currMonth;
			if(month <= 9){
			 currMonth = selectYear +"-0"+ month+"-01";
		}else{
			 currMonth = selectYear +"-"+ month+"-01";
		}
			var currstartYr = year+"-04-01";
			year=Number(year) +1;
			var currEndYr = year+"-03-31";
			alert("currMonth :"+currMonth);
			alert("currstartYr : "+currstartYr);
			alert("currEndYr : "+currEndYr);
			
			var d1 = new Date(currMonth);	 	
		 	var d2 =new  Date(currstartYr);
			var d3 =new  Date(currEndYr);
			if (d1.getTime() < d2.getTime())
			 {
				   alert("Please Select Proper Month for academic year");
				   document.getElementById("month").value=" ";
				   document.getElementById("month").focus();
				   return false;
			  }
			if (d1.getTime() > d3.getTime())
			 {
				   alert("Please Select Proper Month...");
				   document.getElementById("month").value=" ";
				   document.getElementById("month").focus();
				   return false;
			  }
		} */


	function selectReport() {

		var detail = document.getElementById("selecteddetail").value;
		
		
		//alert(detail);

		if (detail == "1") {

			document.getElementById("quar1").style.display = 'table-row';
			document.getElementById("quar2").style.display = 'table-row';
			document.getElementById("quar3").style.display = 'table-row';
			document.getElementById("quar4").style.display = 'table-row';

			document.getElementById("chal1").style.display = 'none';
			document.getElementById("chal2").style.display = 'none';
			document.getElementById("chal3").style.display = 'none';
			document.getElementById("chal4").style.display = 'none';
			document.getElementById("chal5").style.display = 'none';
			//document.getElementById("sub").style.display = 'block';

		} else if (detail == "2") {
			document.getElementById("quar1").style.display = 'none';
			document.getElementById("quar2").style.display = 'none';
			document.getElementById("quar3").style.display = 'none';
			document.getElementById("quar4").style.display = 'none';

			document.getElementById("chal1").style.display = 'table-row';
			document.getElementById("chal2").style.display = 'table-row';
			document.getElementById("chal3").style.display = 'table-row';
			document.getElementById("chal4").style.display = 'table-row';
			document.getElementById("chal5").style.display = 'table-row';
			//document.getElementById("sub").style.display = 'block';

		}	
		else if (detail == "0") {
			document.getElementById("quar1").style.display = 'none';
			document.getElementById("quar2").style.display = 'none';
			document.getElementById("quar3").style.display = 'none';
			document.getElementById("quar4").style.display = 'none';

			document.getElementById("chal1").style.display = 'none';
			document.getElementById("chal2").style.display = 'none';
			document.getElementById("chal3").style.display = 'none';
			document.getElementById("chal4").style.display = 'none';
			document.getElementById("chal5").style.display = 'none';
			document.getElementById("branchcode").style.display = 'none';
			document.getElementById("branchName").style.display = 'none';
			//document.getElementById("sub").style.display = 'block';

		}	
		else if (detail == "3") {
			document.getElementById("branchcode").style.display = 'table-row';
			document.getElementById("branchName").style.display = 'table-row';
			document.getElementById("chal1").style.display = 'table-row';
			
			document.getElementById("chal2").style.display = 'none';
			document.getElementById("chal3").style.display = 'none';
			document.getElementById("chal4").style.display = 'none';
			document.getElementById("chal5").style.display = 'none';
			document.getElementById("quar1").style.display = 'none';
			document.getElementById("quar2").style.display = 'none';
			document.getElementById("quar3").style.display = 'none';
			document.getElementById("quar4").style.display = 'none';
		}
		
	}

	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890';
		}
		var k;
		k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
		if (k != 13 && k != 8 && k != 0) {
			if ((e.ctrlKey == false) && (e.altKey == false)) {
				return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	function validation() {
		
		var detail = document.getElementById("selecteddetail").value;
		var Financial_Year = document.getElementById("Financial_Year").value;
		if(detail == "1"){
		  var quar = document.getElementById("quar").value;
		  var rno = document.getElementById("rno").value;
		  var cname = document.getElementById("cname").value;
		  if(Financial_Year == ""){
			  alert("Please Select Year!!");
	  		  return false;
		  }
		  if(quar == "select"){
			  alert("Please Select Quarter!!");
	  		  return false;
		  }
		  if(rno == ""){
			  alert("Please Enter Receipt Number!!");
	  		  return false;
		  }
		  if(cname == ""){
			  alert("Please Enter Challan!!");
	  		  return false;
		  }
		}
		if(detail == "2"){
			var month = document.getElementById("month").value;
			month = month.replace(/-/g, "/");
			var cno = document.getElementById("cno").value;
			var dateofpay = document.getElementById("dateofpay").value;
			dateofpay = dateofpay.replace(/-/g, "/");
			var duedateofpay = document.getElementById("duedateofpay").value;
			duedateofpay = duedateofpay.replace(/-/g, "/");
			
			if(Financial_Year == ""){
				  alert("Please Select Year!!");
		  		  return false;
			  }
			if(month == ""){
				  alert("Please Select Month!!");
		  		  return false;
			  }
			if(cno == ""){
				  alert("Please Enter Challan Number!!");
		  		  return false;
			  }
			if(dateofpay == ""){
				  alert("Please Select date of payment!!");
		  		  return false;
			  }
			if(duedateofpay == ""){
				  alert("Please Select due date of payment!!");
		  		  return false;
			  }
			var month = document.getElementById("month").value;
			var year = document.getElementById("year").value;
			var arr = month.split("-");
			var selectYear = arr[1];
			var month = 0;
			if (arr[0] == "Jan") {
				month = 1;
			}
			if (arr[0] == "Feb") {
				month = 2;
			}
			if (arr[0] == "Mar") {
				month = 3;
			}
			if (arr[0] == "Apr") {
				month = 4;
			}
			if (arr[0] == "May") {
				month = 5;
			}
			if (arr[0] == "Jun") {
				month = 6;
			}
			if (arr[0] == "Jul") {
				month = 7;
			}
			if (arr[0] == "Aug") {
				month = 8;
			}
			if (arr[0] == "Sep") {
				month = 9;
			}
			if (arr[0] == "Oct") {
				month = 10;
			}
			if (arr[0] == "Nov") {
				month = 11;
			}
			if (arr[0] == "Dec") {
				month = 12;
			}
			
			var currMonth;
			if(month <= 9){
			 currMonth = selectYear +"-0"+ month+"-01";
		}else{
			 currMonth = selectYear +"-"+ month+"-01";
		}
			var currstartYr = year+"-04-01";
			year=Number(year) +1;
			var currEndYr = year+"-03-31";
			
			var d1 = new Date(currMonth);	 	
		 	var d2 =new  Date(currstartYr);
			var d3 =new  Date(currEndYr);
			if (d1.getTime() < d2.getTime())
			 {
				   alert("Please Select Proper Quarter Month");
			//	   document.getElementById("month").value=" ";
			//	   document.getElementById("month").focus();
				   return false;
			  }
			if (d1.getTime() > d3.getTime())
			 {
				   alert("Please Select Proper Quarter Month");
			//	   document.getElementById("month").value=" ";
			//	   document.getElementById("month").focus();
				   return false;
			  }
		}
		if(detail == "3"){
			var Year = document.getElementById("year").value;
			var BSR_Code = document.getElementById("branchcd").value;
			var BranchName = document.getElementById("branchname").value;
			if(Year == ""){
				  alert("Please Select Year!!");
		  		  return false;
			  }
			if(BSR_Code == ""){
				  alert("Please Enter BSR Code!!");
				  document.getElementById("branchcd").focus();
		  		  return false;
			  }
			if(BranchName == ""){
				  alert("Please Enter Bank Name!!");
				  document.getElementById("branchname").focus();
		  		  return false;
			  }
		}
	}
	function textvalidate() {
		  var branch = document.getElementById("branchname");
		  branch.value = branch.value.replace(/[^a-zA-Z@]+/, " ");
		};
	
	function fn() {
		var dob = document.getElementById("dateofpay").value;
		var arr = dateofpay.split("-"); 
		var counter = 0;
		var day = arr[0];
		var month = 0;
		document.getElementById("dateofpay").style.display = "row";
		if (arr[1] == "Jan") {
			month = 0;
		}
		if (arr[1] == "Feb") {
			month = 1;
		}
		if (arr[1] == "Mar") {
			month = 2;
		}
		if (arr[1] == "Apr") {
			month = 3;
		}
		if (arr[1] == "May") {
			month = 4;
		}
		if (arr[1] == "Jun") {
			month = 5;
		}
		if (arr[1] == "Jul") {
			month = 6;
		}
		if (arr[1] == "Aug") {
			month = 7;
		}
		if (arr[1] == "Sep") {
			month = 8;
		}
		if (arr[1] == "Oct") {
			month = 9;
		}
		if (arr[1] == "Nov") {
			month = 10;
		}
		if (arr[1] == "Dec") {
			month = 11;
		}

		var year = arr[2];

		var d = new Date(year, month, day - 1);
		d.setFullYear(d.getFullYear() + 58);

		document.getElementById("anotation").value = GetDate(convertDate(d));
		document.getElementById("extperdate").value = GetDate(convertDate(d));
	}
</script>

<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript">
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'M-yy',
            onClose: function(dateText, inst) { 
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                $(':focus').blur();
                
            },
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    
                }
            }
        });
        
    });
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>

</head>

<body onload="report()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>
	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">
			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Quarter Challan Details</h1>
			</div>
			<!-- end page-heading -->
			<tr>
				<td id="tbl-border-left"></td>
				<!--  start content-table-inner ...................................................................... START -->
				<div id="content-table-inner">
					<!--  start table-content  -->
					<center>
						<div id="table-content">
							<form id="quarchalform" name="quarchalform" action="QuarterChallanServlet?action=adddetail" method="post" onsubmit="return  validation()">
               				
								<!-- <div> -->
								<table border="1" id="customers" align="center"
									style="width: 40%;">
									<tr>
										<th>Quarter Challan Details</th>
									</tr>
									<tr class="alt">
										<td align="center">

											<table id="customers" align="center" style="width: 100%;">

												<tr class="alt" height="30" id="details"
													>
													<td align="left" style="font-weight: 900;"><b>Select Details</b></td>
													<td align="left"><select
														id="selecteddetail" name="selecteddetail"
														onchange="selectReport()" style="width: 145px; height: 30px; font-size: 15px;">
															<option value="0" selected="selected">SELECT</option>
															<option value="1">Quarter_Details</option>
															<option value="2">Challan_Details</option>
															<option value="3">TDS_Payment_Details</option>
													</select></td>
												</tr>

												<!-----------------------------------------------------first for quarter details --------------->

												<tr class="alt" height="30" align="center" id="quar1" style="display: none">
													<td align="left" style="font-weight: 900;"><b>Select Academic Year </b></td>
													<td align="left"><select name="Financial_Year" id="Financial_Year" style="width: 145px; height: 30px; font-size: 15px;"></select> <script>
														var min = 2000, max = 2099, select = document
																.getElementById('Financial_Year');

														for (var i = min; i <= max; i++) {
															var opt = document
																	.createElement('option');
															opt.value = i;
															opt.innerHTML = i;
															select
																	.appendChild(opt);
														}
													</script></td>
												</tr>


												<tr class="alt" id="quar2" height="30" align="left"
													style="display: none" >
													<td align="left" style="font-weight: 900;">Select
														Quarter</td>
													<td align="left" ><select id="quar"
														name="quar" style="width: 145px; height: 30px;">
															<option selected="selected">select</option>
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="4">4</option>
													</select></td>

												</tr>

												<tr class="alt" id="quar3" height="30" align="left"
													style="display: none">
													<td align="left"  style="font-weight: 900;">Enter
														Receipt Number </td>
													<td align="left"><input
														class="form-control" type="text" name="rno" id="rno"
														title="Enter Receipt Number"
														style="width: 145px; height: 30px; font-size: 15px;">
													</td>
												</tr>

												<tr class="alt" id="quar4" height="30" align="left"
													style="display: none">
													<td align="left" style="font-weight: 900;">Challan
														Enter By </td>
													<td align="left"><input
														class="form-control" type="text" name="cname" id="cname"
														title="Challan Enter By"
														style="width: 145px; height: 30px; font-size: 15px;">
													</td>
												</tr>





												<!-- --------------------------------------------------------second for challan details -->


												<tr class="alt" id="chal1" height="30" align="left"
													style="display: none">
													<td align="left" style="font-weight: 900;" >Select Year</td>
													<td ><select name="year" id="year" style="width: 145px; height: 30px; font-size: 15px;"></select> <script>
														var min = 1990, max = 2099, select = document
																.getElementById('year');

														for (var i = min; i <= max; i++) {
															var opt = document
																	.createElement('option');
															opt.value = i;
															opt.innerHTML = i;
															select
																	.appendChild(opt);
														}
													</script></td>
												</tr>

												<tr class="alt" id="chal2" height="30" align="left"
													style="display: none">
													<td align="left" style="font-weight: 900;">Select Quarter
														Month</td>
													<td align="left" style="display: row;"><input name="month"
														id="month" class="date-picker"  readonly="readonly"
														placeholder="Click here for Calender"
														style="width: 145px; height: 30px; font-size: 15px;" /></td>
												</tr>

												<tr class="alt" id="chal3" height="30" align="left"
													style="display: none">
													<td align="left" style="font-weight: 900;">Enter
														Challan Number </td>
													<td align="left"><input
														class="form-control" type="text" name="cno" id="cno"
														title="Enter Challan Number" style="width: 145px; height: 30px; font-size: 15px;"
														onkeypress="return inputLimiter(event,'Numbers')">
													</td>
												</tr>
												<tr class="alt" id="chal4" height="30" align="left"
													style="display: none">
													<td align="left" style="font-weight: 900;">Date
														Of Payment </td>
													<td id="dateofpay1" style="display: row;"><input
														name="dateofpay" id="dateofpay" type="text" style="width: 145px; height: 30px; font-size: 15px;"
														readonly="readonly"> &nbsp;<img
														src="images/cal.gif"
														style="vertical-align: middle; cursor: pointer;"
														onClick="javascript:NewCssCal('dateofpay', 'yyyymmdd')"
														id="dateofpays" /></td>
												</tr>

												<tr class="alt" id="chal5" height="30" align="left"
													style="display: none">
													<td align="left" colspan="1" style="font-weight: 900;">Due
														Date Of Payment </td>
													<td id="duedateofpay1" style="display: row;"><input
														name="duedateofpay" id="duedateofpay" type="text" style="width: 145px; height: 30px; font-size: 15px;"
														readonly="readonly"> &nbsp;<img
														src="images/cal.gif"
														style="vertical-align: middle; cursor: pointer;"
														onClick="javascript:NewCssCal('duedateofpay', 'yyyymmdd')"
														id="duedateofpay" /></td>
												</tr>
												<tr class="alt" id="branchcode" height="30" align="left"
													style="display: none">
													<td align="left"  style="font-weight: 900;">Enter BSR Code </td>
													<td align="left"><input
														class="form-control" type="text" name="branchcd" id="branchcd"
														title="Enter BSR Code"
														style="width: 145px; height: 30px; font-size: 15px;" onkeypress="return inputLimiter(event,'Numbers')">
													</td>
												</tr>
												<tr class="alt" id="branchName" height="30" align="left"
													style="display: none">
													<td align="left"  style="font-weight: 900;">Enter Bank Name </td>
													<td align="left"><input
														class="form-control" type="text" name="branchname" id="branchname"
														title="Enter Branch Name" 
														style="width: 145px; height: 30px; font-size: 15px;" onkeyup="textvalidate();">
													</td>
												</tr>

												<tr>
													    <td colspan="2" align="center">
																<input type="submit" value="Submit"
																	class="myButton" id="ans" name="ans">
													   </td>
												</tr>

											</table>
										</td>
									</tr>

									
								</table>
								<!-- </div> -->
								<input type="hidden" name="flag" id="flag" value="<%=str%>">
								<%-- <input type="hidden" id="flag" value="<%=str%>"> --%>
							</form>
						</div>
					</center>
					<!--  end table-content  -->
					<div class="clear"></div>
				</div>


				<!--  end content-table-inner ............................................END  -->
				<div class="clear">&nbsp;</div>
				<!--  end content -->
				<div class="clear">&nbsp;</div>
				<!--  end content-outer........................................................END -->
				<div class="clear">&nbsp;</div>
			</tr>
		</div>
	</div>
</body>

</html>