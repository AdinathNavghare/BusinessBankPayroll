	<%@page import="payroll.Model.EmpOffBean"%>
	<%@page import="payroll.DAO.EmpOffHandler"%>
	<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.LMB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

 <script>
	
	jQuery(function() {
	//	alert("alert 2");
		$("#empno").autocomplete("list.jsp");
	}); 
</script>
 


<script type="text/javascript">
function validation() { 
//	alert("i am in validate");
	
	var checkboxes = document.getElementsByName('chk');
	var vals = "";
	for (var i=0, n=checkboxes.length;i<n;i++) 
	{
	    if (checkboxes[i].checked) 
	    {
	//    	alert("i am in chk");
	        vals += ","+checkboxes[i].value;
	        
	    }
	}
	if(vals==""||vals==null) {
		alert("No Filter(s) Selected !");
		return false;
	}
	var type =document.getElementById("branchtyp").value;
	var RangeFrom = document.getElementById("rangeFrom").value;
	var RangeTo =document.getElementById("rangeTo").value;
	if(type =="4"){
	if(RangeFrom > RangeTo){
		alert("Please select Proper Branch Range");
		document.getElementById("rangeFrom").focus();
		return false;
	}
	}
	
	
	
/* 	var checkboxes = document.getElementsByName('chk');
	alert(checkboxes);
	var vals = "";
//	for (var i=0, n=checkboxes.length;i<n;i++) {
	  if (checkboxes[i].checked) 
	  {
		  alert("i am in chk");
	  vals += ","+checkboxes[i].value;
	  }
//	}
	
//	var cont=(vals.match(/,/g)).length;
	if(vals==""||vals==null) {
		alert("No Employee(s) Selected !");
		return false;
	} */
}


function selectbranch()
{
	var type =document.getElementById("branchtyp").value;
	if(type =="1")
	{
     document.getElementById('branc').style.display='none';
	 document.getElementById('desi').style.display='none';
	 document.getElementById('rangefrom').style.display='none';
	 document.getElementById('rangeto').style.display='none';
	 document.getElementById('departmrnt').style.display='none';
	}
	else if(type =="2")
	{
    document.getElementById('desi').style.display='none';
	document.getElementById('branc').style='table-row';
	document.getElementById('rangefrom').style.display='none';
	document.getElementById('rangeto').style.display='none';
	document.getElementById('departmrnt').style.display='none';	
	}
	/* else if(type =="0")
		{
		document.getElementById('branc').style.display='none';
		document.getElementById('desi').style.display='none';
		} */
	 else if(type =="3")
	{
	document.getElementById('desi').style='table-row';	
	document.getElementById('branc').style.display='none';
	document.getElementById('rangefrom').style.display='none';
	document.getElementById('rangeto').style.display='none';
	document.getElementById('departmrnt').style.display='none';
	} 
	 else if(type =="4")
		{
		document.getElementById('desi').style.display='none';	
		document.getElementById('branc').style.display='none';
		document.getElementById('rangefrom').style='table-row';
		document.getElementById('rangeto').style='table-row';
		document.getElementById('departmrnt').style.display='none';
		} 
	 else if(type =="5")
	{
		document.getElementById('branc').style.display='none';
		document.getElementById('desi').style.display='none';
		document.getElementById('rangefrom').style.display='none';
		document.getElementById('rangeto').style.display='none';
		document.getElementById('departmrnt').style='table-row';
	}
}
 	
</script>
<script type="text/javascript">
  
    </script>
 
   <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style> 
    

</head>
<body > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee Reports</h1>
	</div>
	<!-- end page-heading -->
    <form action="ReportServlet?action=newempAllReport" method="post" name="add" onsubmit="return  validation()"> 
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
			<div id="table-content">
			<center>
			<table border="1" id="customers" align="center" width="50%">
			<tr >
			<th align="center" colspan="8">Employee Reports</th>
			</tr>
			<tr class="alt"  >
			<td colspan="2"><input type="checkbox" id="chk"  name="chk" value="EMPCODE">
			<label><b>Employee Code</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk1" name="chk" value="EMPNAME">
			<label><b>Employee Name</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk2" name="chk" value="PFNO">
			<label><b>PF Number</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk3" name="chk" value="PANNO">
			<label><b>PAN Number</b></label></td>
			</tr>
			<tr class="alt" >
			<td colspan="2"><input type="checkbox" id="chk4"  name="chk" value="DOB">
			<label><b>DOB</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk5"  name="chk" value="DOJ">
			<label><b>DOJ</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk6"  name="chk" value="BloodGrp">
			<label><b>Blood Group</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk7"  name="chk" value="AADHAARNUM">
			<label><b>Aadhar Number</b></label></td>
			</tr>
			<tr class="alt" >
			<td colspan="2"><input type="checkbox" id="chk8"  name="chk" value="STATUS">
			<label><b>Active Employee</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk12" name="chk" value="MARRIED">
			<label><b>Married</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk14" name="chk" value="ACNO">
			<label><b>Account Number</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk16" name="chk" value="NET_AMT">
			<label><b>Net Amount</b></label></td>
			<!-- <td colspan="2"><input type="checkbox" id="chk9" name="chk" value="STATUS">
			<label><b>Non Active Employee</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk10" name="chk" value="SALUTE">
			<label><b>Mr.</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk11" name="chk" value="SALUTE">
			<label><b>Mrs.</b></label></td> -->
			</tr>
			<tr class="alt" >
			<!-- <td colspan="2"><input type="checkbox" id="chk12" name="chk" value="MARRIED">
			<label><b>Married</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk13" name="chk" value="MARRIED">
			<label><b>Un Married</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk14" name="chk" value="ACNO">
			<label><b>Account Number</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk15" name="chk" value="BANK_NAME">
			<label><b>Bank Name</b></label></td> -->
			</tr>
			<tr class="alt" >
			<!-- <td colspan="2"><input type="checkbox" id="chk16" name="chk" value="NET_AMT">
			<label><b>Net Amount</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk17" name="chk" value="CASTCD">
			<label><b>Cast</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk18" name="chk" value="RETIREMENTDATE">
			<label><b>Retirement Date</b></label></td> -->
			<td colspan="2"><input type="checkbox" id="chk22" name="chk" value="DISABILYN">
			<label><b>Handicap</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk19" name="chk" value="EMPNO">
			<label><b>Employee Number</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk20" name="chk" value="DOL">
			<label><b>Date of Leaving</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk21" name="chk" value="Qualification">
			<label><b>Qualification</b></label></td>
			</tr>
			<tr class="alt">
			<!-- <td colspan="2"><input type="checkbox" id="chk20" name="chk" value="DOL">
			<label><b>Date of Leaving</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk21" name="chk" value="Qualification">
			<label><b>Qualification</b></label></td> -->
			<!-- <td colspan="2"><input type="checkbox" id="chk22" name="chk" value="DISABILYN">
			<label><b>Handicap</b></label></td> -->
			 <td colspan="2"><input type="checkbox" id="chk23" name="chk" value="DESIGNATION">
			<label><b>Designation</b></label></td> 
			<td colspan="2"><input type="checkbox" id="chk24" name="chk" value="SITE_NAME">
			<label><b>Site Name</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk25" name="chk" value="PERMANENT_ADDRESS">
			<label><b>Permanent Address</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk26" name="chk" value="CURRENT_ADDRESS">
			<label><b>Current Address</b></label></td>
			</tr>
			<tr class="alt">
			<td colspan="2"><input type="checkbox" id="chk31" name="chk" value="PERMANENT_CITY">
			<label><b>Permanent City</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk32" name="chk" value="PERMANENT_PINCODE">
			<label><b>Per Pincode</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk31" name="chk" value="CURRENT_CITY">
			<label><b>Current City</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk32" name="chk" value="CURRENT_PINCODE">
			<label><b>Current Pincode</b></label></td>
			</tr>
			<tr class="alt">
			<td colspan="2"><input type="checkbox" id="chk27" name="chk" value="PERMANENT_TELNO">
			<label><b>Per Contact Num</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk28" name="chk" value="CURRENT_TELNO">
			<label><b>Curr	 Contact Num</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk29" name="chk" value="CONFIRMATIONDATE">
			<label><b>Confirmation Date</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk30" name="chk" value="BRANCH_NANE">
			<label><b>Branch Name</b></label></td>
			</tr>
			<tr class="alt">
			<td colspan="2"><input type="checkbox" id="chk31" name="chk" value="BRANCH_CODE">
			<label><b>Branch Code</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk32" name="chk" value="DEPARTMENT">
			<label><b>Department</b></label></td>
			<td colspan="2"><input type="checkbox" id="chk33" name="chk" value="GENDER">
			<label><b>Gender</b></label></td>
			<!-- <td colspan="2"><input type="checkbox" id="chk34" name="chk" value="FEMALE">
			<label><b>Female</b></label></td> -->
			</tr>
			
			 <tr class="alt" height="30" id="branches" >
			<td colspan="4" align="left"><b>Select Branch </b></td>
			<td colspan="4">	
			<select id="branchtyp" name="branchtyp"  onchange="selectbranch()" style="width:140px; height: 30px;"> 
		<!-- 	<option value="0" selected="selected">SELECT</option> -->
			<option value="1">ALL</option>
			<option value="2">Branch_Wise</option>
			<option value="3">Designation_Wise</option>
			<option value="4">Range_Wise</option>
			<option value="5">Department_Wise</option>
			</select>
			</td></tr> 
			
			 <tr class="alt" id="branc" style="display:none">
			<td colspan="4" align="left"><b>Branch Wise</b> </td>
     		<td align="left" colspan="4">
			<select name="Branch" id="Branch" style="width:140px; height: 30px;">  
      	<!-- 	<option value="Select">Select</option>   -->
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
     		 <tr  class="alt" id="desi"  style="display:none">
    		<td colspan="4" align="left"><b>Designation Wise</b> </td>
     		<td align="left" colspan="4">
			<select name="dewise" id="dewise" style="width:140px; height: 30px;">  
      	<!-- 	<option value="ALL">All</option>   -->
    											
    		<%
    		LookupHandler lkh=new LookupHandler();
    		ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
		    for(Lookup lkp_bean:lkp_list)
			{
			%>
			<option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			<%	  
			 }
			 %>		</select>
     		</td>
     		</tr> 
     											<tr class="alt" height="30"  id="rangefrom" style="display:none">
												<td colspan="4"><b>Range From</b>
												<td  align="left" colspan="4">
												<select name="rangeFrom" id="rangeFrom" style="width:140px; height: 30px;">  
      					  						<!-- <option value="Select">Select</option> -->  
    											
    											<%
														for(EmpOffBean eopbn1 : ebn)
														{
												%>
														<option value="<%=eopbn1.getSite_id()%>"><%=eopbn1.getSite_id()%></option>
												<%		}
												%>
     			      							</select></td>
     			      							<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
												</tr>
												<tr class="alt" id="rangeto" style="display:none">
												<td colspan="4"><b>Range To</b>&nbsp;&nbsp;&nbsp;&nbsp;</td>
												<td  align="left" colspan="4">
												<select name="rangeTo" id="rangeTo" style="width:140px; height: 30px;">  
      					  						<!-- <option value="Select">Select</option>   -->
    											<%
    											for(EmpOffBean eopbn2 : ebn)
														{
												%>
														<option value="<%=eopbn2.getSite_id()%>"><%=eopbn2.getSite_id()%></option>
												<%		}
												%>
     			      							</select>
												</td>			
												</tr>
		 <tr  class="alt" id="departmrnt" style="display:none">
					   <td colspan="4"><b>Select Department Wise </b></td>
     			       <td  align="left" colspan="4">
					   <select name="deptwise" id="deptwise" style="width:140px; height: 30px;">  
      				   <!-- <option value="Select">Select</option>   -->
    			<%
    			LookupHandler lk=new LookupHandler();
    			ArrayList<Lookup> lkplist=lk.getSubLKP_DESC("DEPT");
			      for(Lookup lkp_bean:lkplist)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>
     			  </select>
     			  </td></tr>			
	<tr  id="buttonReport" >
	<td colspan="8" align="center"  >
	 <input  type="submit" value="Get Report" class="myButton"  /></td>
	</tr>
	</table>
	</center>
	<br/>
	<div class="clear"></div>
	</div></div>
	</td>
	<td id="tbl-border-right"></td>
	</tr>
	</table>
	</form>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
</body>
</html>