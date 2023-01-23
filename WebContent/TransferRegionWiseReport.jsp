<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.DAO.*"%>
<%@page import ="java.util.ArrayList"%>

<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TransferRegionWise LIST</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

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
		$("#EMPNO").autocomplete("searchList.jsp");
	});
</script>
    
<%
  EmpOffHandler eh = new EmpOffHandler();
  ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
  ebn = eh.getprojectCode();
  %> 

<script type="text/javascript">
  
function validate()
{
	var a=document.getElementById("type");
  	var b=a.options[a.selectedIndex].value;
  	
  	if(document.getElementById("date").value == ""){
  		alert("please select Date First!");
  			return false;
  	}
  	else if(b=="")
  	{
  		alert("Please Select Report Type !!!");
  		return false;
  	}
  	else
  	{
  		document.getElementById("reporttype").value=b;
  	}	
}

function getCode(code)
{
	document.getElementById("tname").value=code;
}		    
  	
function focus() 
{
	document.getElementById("EMPNO").focus();
}
	
function selectReport() 
{
	debugger;
	var type = document.getElementById("reporttyp").value;
	if (type == "1") {
		document.getElementById('empname').style.display = 'none';
		document.getElementById('branc').style.display = 'none';
		document.getElementById('desig').style.display = 'none';
	} else if (type == "2") {
		document.getElementById('empname').style = 'table-row';
		document.getElementById('branc').style.display = 'none';
		document.getElementById('desig').style.display = 'none';
	}
	else if(type == "3") {
		document.getElementById('empname').style.display = 'none';
		document.getElementById('branc').style.display = 'table-row';
		document.getElementById('desig').style.display = 'none';
	}
	else if(type == "4") {
		document.getElementById('empname').style.display = 'none';
		document.getElementById('branc').style.display = 'none';
		document.getElementById('desig').style = 'table-row';
	}		
}

</script>
</head>
<body style="overflow: hidden;"> 
<%
String pageName = "TransferRegionWiseReport.jsp";
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


<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>TransferRegionWise List</h1>
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

			<form name="TransferRegionWiseForm" action="ReportServlet" onSubmit="  return validate()">
			<table border="1" id="customers">
				<tr><th> Employees RegionWise Transfer List</th></tr>
				<tr class="alt">			
					<td height="120" align="center">
					<input type="hidden" id="action" name="action" value="TransferRegionWise"></input>
					<input type="hidden" id="reporttype" name="reporttype" ></input>
						
						<table>
							<tr class="alt">
								<td>Select Date</td>
								<td bgcolor="#FFFFFF">
									<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
								</td>
							</tr>
							
							<tr class="alt" height="30" id="branches">
								<td>Select Report Type</td>
								<td><select id="reporttyp" name="reporttyp" onchange="selectReport()">
										<option value="1">ALL</option>
						 				<option value="2">Specific</option>
										<option value="3">Branch Wise</option>
										<option value="4">Designation Wise</option>
									</select>
								</td>
							</tr>
									
							<tr class="alt" height="30" align="left" id="empname" style="display: none">
								<td align="left" >Enter Employee Name or Emp-Id</td>
								<td align="left">
									<input class="form-control" type="text" name="EMPNO" id="EMPNO" title="Enter Employee Name" style="width: 265px;  font-size: 15px;">
								</td>
							</tr>
									
							<tr class="alt" id="branc" style="display:none">
					   			<td>Select Branch Wise </td>
     			       			<td>
					   				<select name="Branch" id="Branch" style="width:200px;"> 
			      				    	<option value="Select">Select</option>  
						    				   <%
												for(EmpOffBean eopbn : ebn)
												{
												%>
												<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
						     		</select>
						     	</td>
						     </tr>
						     		
							<tr class="alt" id="desig" style="display:none">
					   			<td>Select Designation Wise </td>
     			       			<td>
					   				<select name="design" id="design" style="width:200px;" >  
						      			<option value="Select">Select</option>  
						      			<option value="All">All</option> 
						    			  <%
										    		LookupHandler lkh7=new LookupHandler();
										    		ArrayList<Lookup> lkp_list7=lkh7.getSubLKP_DESC("DESIG");
												    for(Lookup lkp_bean:lkp_list7)
													{
													%>
													<option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
													<%	  
													 }
													 %>	
						     		</select>
						     	</td>
						     </tr>
						    					
							<tr class="alt">
								<td>Select Type</td>
								<td>
									<select id="type" style="width:100px";>
										<option value="excelfile">EXCEL</option>
					 				</select>
					 			</td>
							</tr>
						     		
							<tr>
								<td colspan="2" align="center">
									<input type="submit" name="after" class="myButton" value="Generate File" onclick="getCode('paytran_stage')"/>
									<input type="hidden" id="tname" name="tname" value="">
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