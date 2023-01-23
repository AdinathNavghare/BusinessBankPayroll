<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Earning/Deduction Report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
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

<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>
<script type="text/javascript">
function check(){
	if( document.getElementById("trncd").selectedIndex==0)
	{
		alert("Please Select Transaction Code");	
		document.getElementById("trncd").focus;
		return false;
	}
	var fg = validation();
	if(fg==false||fg=="false")
	{
		return false;
	}else{
		return true;
	}
}
function validation() {
	
	var type = document.getElementById("branchtyp").value;
	
	 
	if (document.getElementById("date").value == "") {
		alert("Please Select Date");
		document.getElementById("date").focus();
		return false;
	}
	else if (type == "0")
	{
		alert("Please Select Report Type");
		document.getElementById("branchtyp").focus();
		return false;
	}
	else if (type == "2") 
	{
		 if (document.getElementById("Branch").value =="0") {
				alert("Please select Branch");
				document.getElementById("Branch").focus();
				return false;
			}
	}
	else if(type == "3")
		{
			
			var fromrng = document.getElementById("rangeFrom").value;
			var torng = document.getElementById("rangeTo").value;
			if (fromrng =="0") {
				 		alert("Please select From Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
			else if(torng =="0"){
				alert("Please select To Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			else if(parseInt(fromrng)>parseInt(torng))
				{
					alert("Please select Proper Range For Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
		
		
		}
	else {
		return true;
	}
}
function getCode(code)
{
	    document.getElementById("tname").value=code;
       var filename = trncd.options[trncd.selectedIndex].innerHTML;
       document.getElementById("fname").value=filename;

}
function selectReport() {
	var type = document.getElementById("reporttyp").value;
	if (type == "1") {
		document.getElementById('frmdate').style.display = 'none';
		document.getElementById('todate').style.display = 'none';
		document.getElementById('empname').style.display = 'none';
		document.getElementById('forbranches').style.display = 'none';
		document.getElementById('branc').style.display = 'none';
		document.getElementById('specific').style.display = 'none';
	} else if (type == "2") {
		document.getElementById('empname').style.display = 'none';
		//document.getElementById('frmdate').style = 'table-row';
		//document.getElementById('todate').style = 'table-row';
		document.getElementById('forbranches').style.display = 'none';
		document.getElementById('branc').style.display = 'table-row';
		document.getElementById('specific').style.display = 'none';
	} else if (type == "3") {
		document.getElementById('frmdate').style.display = 'none';
		document.getElementById('todate').style.display = 'none';
		//document.getElementById('empname').style = 'table-row';
		document.getElementById('forbranches').style.display = 'none';
		document.getElementById('branc').style.display = 'none';
		document.getElementById('specific').style.display = 'none';
	}
	else if(type == "4") {
		document.getElementById('frmdate').style.display = 'none';
		document.getElementById('todate').style.display = 'none';
		document.getElementById('empname').style.display = 'none';
		//document.getElementById('specific').style.display = 'none';
		//document.getElementById('branc').style.display = 'none';
		document.getElementById('forbranches').style = 'table-row';
	}
}
function selectbranch()
{
	var type =document.getElementById("branchtyp").value;
   
	if(type =="1")
	{
     	document.getElementById('branc').style.display='none';
		 document.getElementById('rangewise').style.display='none';
	}
	else if(type =="2")
	{
   	 	document.getElementById('rangewise').style.display='none';
		document.getElementById('branc').style='table-row';
		
	}
	else if(type =="0")
	{
		document.getElementById('branc').style.display='none';
		document.getElementById('rangewise').style.display='none';
	}
	else if(type =="3")
	{
		document.getElementById('rangewise').style='table-row';	
		document.getElementById('branc').style.display='none';
	}
}
</script>
<%
	String pageName = "incDedRpt.jsp";
	try
	{
		ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
		if(!urls.contains(pageName))
		{
			response.sendRedirect("NotAvailable.jsp");
		}
	}
	catch(Exception e)
	{
		//response.sendRedirect("login.jsp?action=0");
	}

%>
<%
  	EmpOffHandler eh2 = new EmpOffHandler();
	ArrayList<EmpOffBean> ebn2 = new ArrayList<EmpOffBean>();
	ebn2 = eh2.getprojectCode();
  
  
  %> 
</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Earning/Deduction Report</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			<center>
			
			<form name="bonusrpt" action="ReportServlet" onSubmit="return check()" >
			<table border="1" id="customers" align="center">
			
			<tr>
				<th>Earning/Deduction Report</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				
			<table align="center">
				<tr  height="20" align="center"> 
				<input type="hidden" id="action" name="action" value="incDedRpt"></input>
						<td align="center" colspan="2"><b>Select Date :</b>
							<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
						<td align="center" colspan="2"><b>Report For :</b>
							<select id="branchtyp" name="branchtyp"  onchange="selectbranch()" style="width: 145px; height: 20px;"> 
											<option value="0" selected="selected">SELECT</option>
											<option value="1">ALL</option>
											<option value="2">Specific Branch</option>
											<option value="3">BR_Range Wise</option>
											</select>
						</td>
			</tr>
			<tr  id="branc" style="display:none" align="center">
					   						<td colspan="4" ><b>Select Branch :</b> 
					   						<select name="Branch" id="Branch" style="width:145px; height: 20px;" >  
						      				   <option value="0">Select</option>  
						    				   <%
												
												for(EmpOffBean eopbn : ebn2)
												{
												%>
												<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
						     			      	</select>
						     			      	</td>
						     			      	
						    </tr>
						     			      	
						  
    				
						        <tr class="alt" height="20"  id="rangewise" style="display:none;align:center">
												<td align="center" colspan="2"><b>Range From :</b>
												
												<select name="rangeFrom" id="rangeFrom" style="width:145px; height: 20px;">  
      					  						<option value="0">Select</option>  
    											
    											<%
													
														for(EmpOffBean eopbn1 : ebn2)
														{
												%>
														<option value="<%=eopbn1.getSite_id()%>"><%=eopbn1.getSite_id()%></option>
												<%		}
												%>
     			      							</select></td>
												<td align="center" colspan="2"><b>Range To:</b>&nbsp;&nbsp;&nbsp;&nbsp;
												<select name="rangeTo" id="rangeTo" style="width:145px; height: 20px;">  
      					  						<option value="0">Select</option>  
    											<%
													
														for(EmpOffBean eopbn2 : ebn2)
														{
												%>
														<option value="<%=eopbn2.getSite_id()%>"><%=eopbn2.getSite_id()%></option>
												<%		}
												%>
     			      							</select>
												</td>			
									</tr>
			
			
			
			
				<tr  height="20" align="center" >	
					<td align="center" colspan="4"><b>Allowances / Expenses :</b>
					
					<select style="width:325px" name="trncd" id="trncd">
						<option value="0" selected>Select</option>
					<%
    					ArrayList<CodeMasterBean> getresult =new ArrayList<CodeMasterBean>();
    					CodeMasterHandler cmd = new CodeMasterHandler();
    					getresult = cmd.getNoAutocalCDListReport();
    					for(CodeMasterBean lkbean : getresult){
 					%>
      					<option value="<%=lkbean.getTRNCD()%>"><%=lkbean.getDISC()%></option>  
     				<%
     					}
     				%>
						</select>
					</td>
				</tr>
				
											
						
				<tr  class="alt" height="20" align="center">
				<td  colspan="4">
				<!-- <table width="100%" bordercolor="white" style="border-collapse: collapse;" border="0"><tr> -->
					<!-- <td align="center"> --><input type="submit" class="myButton"  style="float: center; margin-left: 5%;" value="Before Finalize" onclick="getCode('paytran')" />
						     	<!-- <input type="hidden" value="" id="tname" name="tname"> --><!-- </td> -->
					 <!-- <td align="center"> --><input type="submit" class="myButton" id ="2"  style="float:center; margin-right: 1%; vertical-align: top;"  value="After Finalize" onclick="getCode('paytran_stage')" />
					<!-- </td> -->
					<input type="hidden" id="tname" name="tname" value="">
						<input type="hidden" id="fname" name="fname" value="">
					<!-- </tr>
				</table> -->
				</td>
				</tr>
				
				</table>

			  </td>
			</tr>
</table>
</form>
			   
			</center>
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>