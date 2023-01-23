<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.EmpQualBean"%>
<%@page import="payroll.Model.Temp_Emp_Sal_Detail"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/DeleteRow.js"></script>
<script src="js/qualValidation.js"></script>
<script type="text/javascript">
	function Showhide()
	{
	//document.getElementById("empQualEdit").hidden=true;
	//document.getElementById("addempQual").hidden=false;
		document.getElementById("sal").value="";
	}
	function validation()
	{
		var name=document.getElementById("aempName").value;
		var sal=document.getElementById("sal").value;
		if(name=="")
			{
			alert("First Fill The Employee Details !!!");
			return false;
			}
		else if(sal=="")
			{
			alert("Please Enter Salary !!!");
			return false;
			}
		else
			{
			return true;
			}
	}
	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890.';
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
	
	function check1(num)
	{
		var checkemp=validation();
		if(checkemp){
		var days=parseInt(document.getElementById("ABSENTDAY").value);
		var sal=parseInt(document.getElementById("sal").value);
		var ded=parseInt(document.getElementById("DEDUCTION").value);
		var ptamt=parseInt(document.getElementById("PTAMT").value);
		var tax=parseInt(document.getElementById("TAX").value);
		if(sal==""||sal==null)
			{
				alert("Please Enter The Monthly Salary First !!!");
				document.getElementById("ABSENTDAY").value="";
				document.getElementById("sal").focus();
				return false;
			}
		else if(days>num){
			alert("Absent Days Should not be greater than "+num+" Days");
			document.getElementById("ABSENTDAY").value="";
			document.getElementById("ABSENTDAY").focus();
			return false;
		}
		else if(ded==""||ded==null)
			{
			alert("Please Enter The Monthly Deduction !!!");
			document.getElementById("DEDUCTION").focus();
			return false;
			}
		
		else if(ded>sal)
			{
			alert("Deduction Must Be Less Than Salary !!!");
			document.getElementById("DEDUCTION").focus();
			return false;
			}
		else if(ptamt==""||ptamt==null)
			{
			alert("Please Enter The Profession Tax Amount!!!");
			document.getElementById("PTAMT").focus();
			return false;
			}
		else if(tax==""||tax==null)
			{
			alert("Please Enter The Tax Amount!!!");
			document.getElementById("TAX").focus();
			return false;
			}
		else
			{
				var presentdays=num-days;
				var net=(((sal/num)*presentdays)-(ded+tax+ptamt));
				document.getElementById("NETAMT").value=Math.round(net);
				return true;
			}
		}
		else
			{
			return false;
			}
	}
</script>
<% 
int i=1;
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp";// Getting action from servlet for showing pages 
//LookupHandler lh = new LookupHandler();
ArrayList<Temp_Emp_Sal_Detail> tempsaldetails=new ArrayList<Temp_Emp_Sal_Detail>();
%>
</head>
<body style="overflow:hidden;"> 
	<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:scroll; max-height:70%; ">
<!-- start content -->
<% if(action.equalsIgnoreCase("addemp"))
{
	//In this If for New Employee page ......show menus for nevigation 
	%>
<div id="content"  ><!--  start page-heading -->
		<div id="step-holder">
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no">1</div>
		<div class="step-dark-left"><a href="TempEmployee.jsp" >Employee Detail</a></div>
		<div class="step-dark-right">&nbsp;</div>
		<div class="step-no-off">2</div>
		<div class="step-light-left"><a href="SalTempEmployee.jsp">Salary Detail</a></div>
			<!--<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">3</div>
		<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
		<div class="step-light-right">&nbsp;</div>
		<div class="step-no-off">4</div>
		<div class="step-light-left"><a href="empFamily.jsp">Family</a></div>
        <div class="step-light-right">&nbsp;</div>
        <div class="step-no-off">5</div>
		<div class="step-light-left"><a href="empExper.jsp">Experience</a></div>
		<div class="step-light-right">&nbsp;</div>
	
		<div class="step-no-off">6</div>
		<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
		<div class="step-light-right">&nbsp;</div>
	
		<div class="step-no-off">7</div>
		<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
		<div class="step-light-right">&nbsp;</div>
		
		
		<div class="step-no-off">8</div>
		<div class="step-light-left"><a href="Attachment.jsp">Attachment</a></div>
		<div class="step-light-right">&nbsp;</div>
		
		<div class="step-no-off">9</div>
		<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
		<div class="step-light-round">&nbsp;</div>
		<div class="clear"></div> -->
		
	
	
	</div>
<%
}
else if(action.equalsIgnoreCase("showemp"))
	{
	//In else for Existing Employee Menus.........
		%>
	<div id="content"  ><!--  start page-heading -->
				<div id="step-holder">
				<div class="step-light-right">&nbsp;</div>
				<div class="step-no">1</div>
				<div class="step-dark-left"><a href="EmployeeServlet?action=Tempemployee" >Employee Detail</a></div>
				<div class="step-dark-right">&nbsp;</div>
				<div class="step-no-off">2</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=saldetails">Salary Detail</a></div>
				<!--<div class="step-light-right">&nbsp;</div>
				<div class="step-no-off">3</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
				<div class="step-light-right">&nbsp;</div>
				<div class="step-no-off">4</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=family">Family </a></div>
	            <div class="step-light-right">&nbsp;</div>
	            <div class="step-no-off">5</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience </a></div>
				<div class="step-light-right">&nbsp;</div>
				<div class="step-no-off">6</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
				<div class="step-light-right">&nbsp;</div>
			
				<div class="step-no-off">7</div>
				<div class="step-light-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
				<div class="step-light-right">&nbsp;</div>
				
				<div class="step-no-off">8</div>
				<div class="step-light-left"><a href="Attachment.jsp?action=showemp">Attachment</a></div>
				<div class="step-light-right">&nbsp;</div>
				
				<div class="step-no-off">9</div>
				<div class="step-light-left"><a href="otherDetail.jsp?action=showemp">Other Detail</a></div>
				<div class="step-light-round">&nbsp;</div>
				<div class="clear"></div> -->
			</div>
	<% 
		} 
%>
<div id="page-heading">
<h1><br>Employee Salary Information</h1>
</div>
<!-- end page-heading -->


<%
if(action.equalsIgnoreCase("showemp"))
{
		String srNo = request.getParameter("srno")!=null?request.getParameter("srno"):"zero";
		String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
		//ArrayList<EmpQualBean> empQualList = (ArrayList<EmpQualBean>)session.getAttribute("empQualList");
		EmployeeHandler eoh= new EmployeeHandler();
		tempsaldetails=eoh.getsalarydetails(Integer.parseInt(String.valueOf(session.getAttribute("empno"))));
%>		
<form name="employeeform" id="employeeform" action="EmployeeServlet?action=editsaldetails" method="post" onSubmit="return validation()">


		<table border="0" width="100%"  height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="300"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="300"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>

<br>

<div id="empQualEdit" style="width: 100%">

<h2>Edit The Salary Details of <%=session.getAttribute("empname") %> </h2>
<table  id="customers">
		
		   <tr class="alt"><td width="15%" >Employee Code</td>
	  		<td width="15%" > <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="15%" >Employee Name</td>
		<td ><input type="text" readonly="readonly" class="form-control" name="aempName" id="aempName" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td>
		<input type="hidden" name="aempNo" id="aempNo" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>">
		</tr>
<%if(tempsaldetails.size()>0)
{ 
	for(Temp_Emp_Sal_Detail saldetails:tempsaldetails)
	{
		if(i==1){%>
		<tr class="alt"><td width="15%" >Salary Per Month<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="sal" name="sal" value="<%=saldetails.getSalary() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td >Absent Days<font color="red"><b>*</b></font></td><td><input type="text" class="form-control" id="ABSENTDAY" name="ABSENTDAY" value="<%=saldetails.getAbsentDay() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		
		<tr class="alt"><td width="15%" >Total Deduction<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="DEDUCTION" name="DEDUCTION" value="<%=saldetails.getDeduction() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td >PT Applicable<font color="red"><b>*</b></font></td><td><input type="text" class="form-control" id="PTAMT" name="PTAMT" value="<%=saldetails.getPTAmt() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		
		<tr class="alt"><td width="15%" >Salary Month<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="MONTH" name="MONTH" value="<%=ReportDAO.EOM(ReportDAO.getSysDate())%>" readonly="readonly" ></td>
		<td width="15%" >Income Tax<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="TAX" name="TAX" value="<%=saldetails.getITax() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		
		<tr class="alt"><td>Net Salary</td><td><input type="text" class="form-control" id="NETAMT" readonly="readonly" name="NETAMT" value="<%=saldetails.getNet_Amt() %>" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td></td><td ></td>
		</tr>
		
		<tr class="alt"><td colspan="4" align="center">
		<input type="submit"   class="myButton"  value="Update" onclick="return check1(<%=ReportDAO.EOM(ReportDAO.getSysDate()).substring(0,2) %>)"/> &nbsp;&nbsp;&nbsp;
		<input type="button"   class="myButton"  value="Cancel" onClick="Showhide()"/></td>
		</tr>
		
		<div align="left">
	<table  height="44" border="1" id="customers" align="center" >
	  	<tr align="center" valign="middle" bgcolor="#CCCCCC">
	  	<br>
	  
	  	<th width="50" align="center">SRNO </th>
	    <th width="80" align="center">DATE </th>
	    <th width="100" align="center">SALARY </th>
	    <th width="50" align="center">ABSENT DAY </th>
	    <th width="100" align="center">DEDUCTION </th>
	    <th width="100" align="center">PT-AMT </th>
	    <th width="85" align="center">ITAX </th>
	     <th width="100" align="center">NET-AMT </th>
	   </tr>
   
	</table>
	</div>
	<div align="left">
	<table   border="1" id="customers1" align="center" >
	  	
		<%}i++;%>
		<tr align="center" valign="middle" bgcolor="#CCCCCC">
		  <td  width="63" 	align="center"><font size="2"><%=i-1%></font></td>
	      <td  width="96"	align="center"><font size="2"><%=saldetails.getTrndt() %></font></td>
	      <td  width="114" 	align="center"><font size="2"><%=saldetails.getSalary() %></font></td>
	      <td  width="75" 	align="center"><font size="2"><%=saldetails.getAbsentDay() %></font></td>
	      <td  width="115" 	align="center"><font size="2"><%=saldetails.getDeduction() %></font></td>
	      <td  width="114" 	align="center"><font size="2"><%=saldetails.getPTAmt()%></font></td>
	      <td  width="99"	align="center"><font size="2"><%=saldetails.getITax() %></font></td>
		  <td  width="114" 	align="center"><font size="2"><%=saldetails.getNet_Amt()%></font></td>	
		  </tr>
		  <%
	}
} %>
		
		</table></div>
    </table>
    
    </div>
	</td></tr></table>
   </form>
	<div class="clear">&nbsp;</div>
  <%
if(srNo.equalsIgnoreCase("addinfo"))
	{	
%><br>


<%
	}
}
else if(action.equalsIgnoreCase("addemp"))
{
	ArrayList<EmpQualBean> empQualList2 = (ArrayList<EmpQualBean>)session.getAttribute("empQualList");
	String session_empno=(String )session.getAttribute("empno");
%>
<form name="employeeform" id="employeeform" action ="EmployeeServlet?action=addsaldetails" method="post" >
		<table border="0" width="auto" cellpadding="0" cellspacing="0" >
	
<%	if(session_empno!= null)
	{
	%>
	
	<%
	
	}%>
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="15" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="15" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
<div id="addempQuall">
<table  id="customers">
		
		   <tr class="alt"><td width="15%" >Employee Code</td>
	  		<td width="20%" > <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="15%" >Employee Name</td>
		<td width="50%" ><input type="text" readonly="readonly" class="form-control" name="aempName" id="aempName" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td>
		<input type="hidden" name="aempNo" id="aempNo" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>">
		</tr>
		
		<tr class="alt"><td width="15%" >Salary Per Month<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="sal" name="sal" value="" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td>Absent Days<font color="red"><b>*</b></font></td><td><input type="text" class="form-control" id="ABSENTDAY" name="ABSENTDAY" value="" onkeypress="return inputLimiter(event,'Numbers')" ></td>
		</tr>
		
		<tr class="alt"><td width="15%" >Total Deduction<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="DEDUCTION" name="DEDUCTION" value="" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td>PT Applicable<font color="red"><b>*</b></font></td><td><input type="text" class="form-control" id="PTAMT" name="PTAMT" value="" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		
		<tr class="alt"><td width="15%" >Salary Month<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="MONTH" name="MONTH" value="<%=ReportDAO.EOM(ReportDAO.getSysDate())%>" readonly="readonly" ></td>
		<td width="15%" >Income Tax<font color="red"><b>*</b></font></td>
		<td width="50%" ><input type="text" class="form-control" id="TAX" name="TAX" value="" onkeypress="return inputLimiter(event,'Numbers')"></td>
		</tr>
		
		<tr class="alt"><td>Net Salary</td><td><input type="text" class="form-control" id="NETAMT" name="NETAMT" value="" readonly="readonly" onkeypress="return inputLimiter(event,'Numbers')"></td>
		<td></td><td></td>
		</tr>
		
		<tr class="alt"><td colspan="4" align="center">
		<input type="submit"   class="myButton"  value="Submit" onclick="return check1(<%=ReportDAO.EOM(ReportDAO.getSysDate()).substring(0,2) %>)"/> &nbsp;&nbsp;&nbsp;
		<input type="button"   class="myButton"  value="Cancel" onClick="Showhide()"/></td>
		</tr>
		
    </table>
	</div>
<div class="clear">&nbsp;</div>


</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>

</form>
<%} %>
</div>

<!--  end content -->
<div class="clear">&nbsp;</div>
</div>

<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
 </div>  

</body>
</html>