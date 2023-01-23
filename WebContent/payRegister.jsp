<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayRegister</title>

<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<title>PayRegister Reports</title>
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


<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
	function validation()
	{
		
		var flag = true;
		if(document.getElementById("date").value=="")
		{
			alert("Please Select Date");
			document.getElementById("date").focus();
			flag = false;
			
		}
		if((document.getElementById("Branch").value=="" || document.getElementById("Branch").value=="Select") && (document.getElementById("Desgn").value=="1"))
			{
				alert("Please Select Branch");
				flag = false;
			}
		if((document.getElementById("dewise").value==""||document.getElementById("dewise").value=="Select")&&(document.getElementById("Desgn").value=="2"))
		{
			alert("Please Select Designation");
			flag = false;
		}
		if(flag == true)
		{
			ViewReport();
		}
		
	}
	
	var xmlhttp;
	var url ="";
	if(window.XMLHttpRequest)
	{
		xmlhttp = new XMLHttpRequest;
	}
	else
	{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function ViewReport() 
	{
		document.getElementById("process").hidden=false;
		var date = "01-"+document.getElementById("date").value;
		var Desgn = document.getElementById("Desgn").value;
		var Branch = document.getElementById("Branch").value;
		var dewise = document.getElementById("dewise").value;
		var report = document.getElementById("report").value;
		
		var salaryType = document.getElementById("salaryType").value;
		report
		
		url="ReportServlet?action=payreg&Desgn="+Desgn+"&date="+date+"&Branch="+Branch+"&dewise="+dewise+"&report="+report+"&salaryType="+salaryType;
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				var response=xmlhttp.responseText;
	        	document.getElementById("viewPdf").innerHTML=response;
	        	document.getElementById("viewPdf").hidden=false;
	        	document.getElementById("process").hidden=true;
			}
		};
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
	
	function selectbranch()
	{
		var type =document.getElementById("Desgn").value;
	   
		if(type =="1")
		{
		document.getElementById('branc').style='table-row';
		document.getElementById('desi').style.display='none';
		}
		else if(type =="2")
		{
			document.getElementById('desi').style='table-row';
			document.getElementById('branc').style.display='none';
		}
		else if(type =="0")
			{
			document.getElementById('branc').style.display='none';
			document.getElementById('desi').style.display='none';
			}
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
<body style="overflow: hidden;"> 
 <%
	String pageName = "payRegister.jsp";
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
		response.sendRedirect("login.jsp?action=0");
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
		<h1>PayRegister Report</h1>
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
			
			<input type="hidden" id="action" name="action" value="payreg"></input>
				<table border="1" id="customers">
					<tr>
						<th colspan="5">Employees PayRegister Report</th>
					</tr>
					 <tr class="alt">
					  
							<td>Select Date</td>
							<td bgcolor="#FFFFFF">
							<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>

							</td>
						  </tr>
					      <tr class="alt">
							<td>Select Type </td>
							<td>
								<select name="Desgn" id="Desgn" style="width:140px"  onchange="selectbranch()">  
      					  			<%-- <option value="none">Select</option>  
    									<%
    						 				ArrayList<Lookup> getresult =new ArrayList<Lookup>();
    										LookupHandler lkhp = new LookupHandler();
    										getresult=lkhp.getSubLKP_DESC("DESIG");
    									%> --%>
    										<option value="0" selected="selected">All</option>
    										<option value="1"  >Branch_Wise</option>
    										<%--<option value="2"  >Designation_Wise</option>
    									<%-- <%
    										for(Lookup lkbean : getresult)
 											{
 										%>
 											<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 				<%
     					 					}
     					 				%> --%>
     			      			</select>
     			      		</td>
					   </tr>
					    
					   
    							 <tr class="alt" id="branc" style="display:none">
    				
					   <td>Select Branch Wise </td>
     			      		<td>
								<select name="Branch" id="Branch" style="width:140px">  
      					  			<option value="Select">Select</option>  
    											
    											<%
														EmpOffHandler eh = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
														ebn = eh.getprojectCode();
														for(EmpOffBean eopbn : ebn)
														{
												%>
														<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
    										
    									
     			      			</select>
     			      		</td>
     			      		</tr>
     			      		
     			      		<tr  class="alt" id="desi" style="display:none">
    				
					   <td>Select Designation Wise </td>
     			      		<td>
								<select name="dewise" id="dewise" style="width:140px">  
      					  			<option value="Select">Select</option>  
    											
    			<%
    			LookupHandler lkh=new LookupHandler();
    			ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
			      for(Lookup lkp_bean:lkp_list)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>
    										 
    									
     			      			</select>
     			      		</td>
     			      		</tr>
     			      		
     			      		  <tr class="alt" id="SalaryType">
							<td>Select Salary Type </td>
							<td>
								<select name="salaryType" id="salaryType" style="width:140px">  
    										<option value="0">Select</option>
				      					  	<option value="paytran">Pre-Finalize</option>
				      					  	<option value="paytran_stage">Finalize</option>
				      					  	<option value="ytdtran">Release</option> 
     			      			</select>
     			      		</td>
					   </tr>	
     			      		
     			      		<tr class="alt">
							<td>Select Type </td>
							<td>
								<select name="report" id="report" style="width:140px"  onchange="selectbranch()">  
    										<option value="TXT" selected="selected">TXT</option>
    										<option value="PDF"  >PDF</option>
    										<option value="EXCEL"  >EXCEL</option>
     			      			</select>
     			      		</td>
					   </tr>
     			      		
     			      		
					   <tr class="alt">
						 <td colspan="4" align="center">
					 	 <input type="button" value="Get Report" class="myButton" onclick="validation()" /></td>
					 	 
					  </tr>
			 		</table>
			 	
			  		<br/>
			   <div id="viewPdf"  hidden="true">
			   </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
			</div>
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