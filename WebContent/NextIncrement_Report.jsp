<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Next Increment Report</title>

<title>Next Increment Report</title>
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
	
	function validation()
	{
	
	 	var filterType = document.getElementById("LeftRep").value;
 		if(filterType=="1"){
 			if(document.getElementById("Branch").value=="Select")
 			{
 				alert("Please Select Branch");
 				document.getElementById("Branch").focus();
 				return false;
 			}
 		}
 		if(filterType=="2"){
 			if(document.getElementById("dewise").value=="Select")
 			{
 				alert("Please Select Designation");
 				document.getElementById("Branch").focus();
 				return false;
 			}
 		}
 		if(document.getElementById("Reptype").value=="Select")
			{
				alert("Please Select Report Type");
				document.getElementById("Reptype").focus();
				return false;
			}
 			
	}
	
	function selectbranch()
	{
		var type =document.getElementById("LeftRep").value;
	   
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
<%@include file="mainHeader.jsp" %>
<%@include file="subHeader.jsp" %>
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<div id="content">

	<div id="page-heading">
		<h1 style="font-size: 25px;">Next Increment Report</h1>
	</div>
	
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
		<div id="content-table-inner">
		
			<div id="table-content">
			<center>							
			<form action="ReportServlet?action=NextIncrementReport" method="post" name="add" onsubmit="return  validation()">
				<table border="1" id="customers" style="width: 600px;height: 165px;margin-top: 40px;">
					<tr>
						<th colspan="5" style="font-size: 20px;">Next Increment Report</th>
					</tr>
					<tr class="alt">
							<td><font size="3">Select Type </font></td>
							<td>
								<select name="LeftRep" id="LeftRep"  onchange="selectbranch()" style="width: 185px; height: 24px; font-size: 15px;">  
    										<option value="0" selected="selected">All</option>
    										<option value="1"  >Branch_Wise</option>
    										<option value="2"  >Designation_Wise</option>
     			      			</select>
     			     </td></tr>
					   <tr  class="alt" id="desi" style="display:none">
					   <td>Select Designation Wise </td>
     			       <td>
					   <select name="dewise" id="dewise" style="width: 185px; height: 24px; font-size: 15px;">  
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
     			  </td></tr>
				  <tr class="alt" id="branc" style="display:none">
				  <td>Select Branch Wise </td>
     			  <td>
				  <select name="Branch" id="Branch" style="width: 185px; height: 24px; font-size: 15px;">  
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
					
					<!--  <tr class="alt" id="leftempdate" style="display: none" > -->
					<!--  <tr class="alt" id="leftempdate">
					        <td><font size="3">From Date </font></td>
		     				<td><input name="frmdate" id="frmdate" type="text"  readonly="readonly" size="18" style="width: 183px; height: 24px; font-size: 15px;">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
							
						  </tr>
						   <tr class="alt" id="toodate">
							<td><font size="3">To Date </font></td>
		     				<td><input name="todate" id="todate" type="text"  readonly="readonly" size="18" style="width: 183px; height: 24px; font-size: 15px;">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
							</td>
							</tr> -->
					<tr class="alt">
					<td><font size="3">Select Report Type </font></td>
					<td>
					<select name="Reptype" id="Reptype" style="width: 185px; height: 24px; font-size: 15px;">  
    				<option value="Select">Select</option>
    				<!-- <option value="1"  >PDF</option> -->
    				<option value="2"  >EXCEL</option>
     			    </select>
     			    </td></tr>
					<!-- <tr class="alt">
					<td><font size="3">Select Increment Type </font></td>
					<td>
					<select name="incrtype" id="incrtype" style="width: 185px; height: 24px; font-size: 15px;">  
    				<option value="Select">Select</option>
    				<option value="yes">YES</option>
    				<option value="no">NO</option>
     			    </select>
     			    </td></tr>  --> 
     			    <!-- <tr class="alt">
					<td><font size="3">Report Status </font></td>
					<td>
					<select name="repstatus" id="repstatus" style="width: 185px; height: 24px; font-size: 15px;">  
    				<option value="Select">Select</option>
    				<option value="Pending">Pending</option>
    				<option value="Release">Release</option>
     			    </select>
     			    </td></tr>  -->  	
					   <tr class="alt">
						 <td colspan="4" align="center">
					 	 <input type="submit" value="Get Report" class="myButton" /></td>
					 	 
					  </tr>
			 		
			  		</table>
			  		</form>
			  		</center>
			  		</div>
			   <div id="viewPdf"  hidden="true">
			   
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
			</div>
			
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
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