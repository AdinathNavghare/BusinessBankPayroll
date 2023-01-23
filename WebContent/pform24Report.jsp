<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>


<script type="text/javascript">



function validate()
{
	
	var year = document.getElementById("year").value;
	if(year == "-1")
		{
			alert("Please Select Year !!!");
			return false;
		}
	else if(document.getElementById("branchtyp").value== "-1")
		{
			alert("Please Select Branch Type !!!");
			return false;
		}
	else if(document.getElementById("quarter").value== "-1")
		{
			alert("Please Select Quarter !!!");
			return false;
		}
	else if(document.getElementById("emptype").value== "-1")
		{
				alert("Please Select Employee Type !!!");
				return false;
		}
	else
		{
			return true;
		}
   
}


function selectemp()
{
	var emptype = document.getElementById("emptype").value;
	if(emptype == "Specific")
		document.getElementById("eempno").style.display="table-row";
	if(emptype == "All" || emptype == "-1")
		document.getElementById("eempno").style.display="none";
	
}

function selectbranch()
{
	var type =document.getElementById("branchtyp").value;
	if(type =="All")
	{
     document.getElementById('branc').style.display='none';
	 //document.getElementById('desi').style.display='none';
	}
	else if(type =="Specific")
	{
    //document.getElementById('desi').style.display='none';
	document.getElementById('branc').style='table-row';
	}
	/* else if(type =="0")
		{
		document.getElementById('branc').style.display='none';
		document.getElementById('desi').style.display='none';
		} */
	 /* else if(type =="3")
	{
	document.getElementById('desi').style='table-row';	
	document.getElementById('branc').style.display='none';
	} */ 
}

</script>



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
<%
String date= ReportDAO.getSysDate();
%>
</head>
<body style="overflow: hidden;"> 
<%
	String pageName = "pform24Report.jsp";
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
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>pform24 Report </h1>
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
			 
			
			
			<form  name="leaveLedgerform" action="ReportServlet"  method="get" onSubmit="return validate()">
				<table id="customers" width="553" align="center">
				
				   <tr> <th colspan="4">Form24 Report</th></tr>
				   <tr class="alt">
				   		<td>Select Year :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				   		<select id="year" name="year" style="width:140px" style="text-align:center" ><option value="-1">Select</option>
				   		<%for(int i=2015;i<=Integer.parseInt(date.substring(7,11));i++){ %>
				   		<option value="<%=i %>-<%=i+1 %>"><%=i %>-<%=i+1 %></option>
				   		<%} %>
				   		</select></td>
				   		
				   		<td>Select Quarter :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   		<select id="quarter" name="quarter" style="width:140px"><option value="-1">Select</option>
				   			<option value="quarter1">Quarter 1</option>
				   			<option value="quarter2">Quarter 2</option>
				   			<option value="quarter3">Quarter 3</option>
				   			<option value="quarter4">Quarter 4</option>
				   		</select></td>
									
				   
													<!-- <td>From Empno</td>
													<td><input type="text" name="EMPNO" id="EMPNO"
														onClick="showHide()" title="Enter Employee No"></td>
													<td>To Empno</td>
													<td><input type="text" name="EMPNO1" id="EMPNO1"
														onClick="showHide()" title="Enter Employee No"></td> -->
					</tr>
					
					<tr class="alt">
				   		<td>Select EmpType : 
				   		<select id="emptype" name="emptype" style="width:140px" >
				   			<option value="-1">Select</option>
				   			<option value="All">All</option>
				   			<option value="Active">Active</option>
				   			<option value="NonActive">Non-Active</option>
				   		</select></td>
				   		
				   		<td>Select BranchType : 	
						<select id="branchtyp" name="branchtyp" style="width:140px"  onchange="selectbranch()"> 
							<option value="All">All</option>
							<option value="Specific">Branch_Wise</option>
						</select></td>
									
				   
					</tr>
						
					<!-- <tr  class="alt" id="eempno" style="display:none">	
						<td>Select Employee:
						<input type="text" name="EMPNO" id="EMPNO"
						onClick="showHide()" title="Enter Employee No"></td>
						<td></td>
					</tr> -->	
						
						
					<tr  class="alt" id="branc" style="display:none">
						<td  colspan="2" align="center">Select Branch :&nbsp;&nbsp;&nbsp;&nbsp; 
							<select name="Branch_no" id="Branch_no" style="width:180px">  
      	
    							<%
								EmpOffHandler eh = new EmpOffHandler();
								ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
								ebn = eh.getprojectCode();
								for(EmpOffBean eopbn : ebn){%>
								<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
								<%}%>
     						</select>
     					</td>
     					<!-- <td></td> -->
     				</tr>
						
					
						
						<tr class="alt"><td colspan="4" align="center"> <input type="submit" class="myButton" value="Get Report"/> &nbsp;&nbsp;
						<input type="reset" class="myButton" value="Cancel"/> <input type="hidden" name="action" value="pform24_report"> </td></tr>
			
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