<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.EmpFamilyBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.LookupHandler.*"%>
<%@page import="payroll.Model.Lookup"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Family </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/familyValidation.js"></script>
<script src="js/DeleteRow.js"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<script type="text/javascript">
function TakeCustId() {
	var EDEGREE = document.getElementById("eRelation").value;
     
	if (document.getElementById("eRelation").value == "") {
		alert("Please Insert Customer Name");
		document.getElementById("eRelation").focus();
		return false;
	}
	var atpos=EDEGREE.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Customer Name");
	  return false;
	  }
	}
function Showhide()
{
	
document.getElementById("empFamilyEdit").hidden=true;
document.getElementById("addempFamily").hidden=false;

	}
	function Text(id)
	{
		
	
	var check = document.getElementById(id).value;
		fcs = document.getElementById(id);
		var matches = check.match(/\d+/g);
		if (matches != null) {
		    alert("Please Insert Characters Only ");
		    //document.getElementById(id).focus();
		    setTimeout("fcs.focus()", 50);
		    return false;
		}
}
	//validation..........
	
	// JavaScript Document

//..........................end validation
</script>

<%
	String action = request.getParameter("action") != null ? request
			.getParameter("action") : "addemp";
			LookupHandler lh = new LookupHandler();
%>
</head>
<body style="overflow: hidden;">
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>

<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content --> <%
 	if (action.equalsIgnoreCase("addemp")) {
 %>
<div id="content"><!--  start page-heading -->
<div id="step-holder">
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">1</div>
<div class="step-light-left"><a href="employee.jsp">Employee Detail</a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">2</div>
<div class="step-light-left"><a href="empQual.jsp"> Qualification </a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">3</div>
<div class="step-light-left"><a href="empAddress.jsp"> Address</a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no">4</div>
<div class="step-dark-left"><a href="empFamily.jsp">Family</a></div>
<div class="step-dark-right">&nbsp;</div>
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
			<div class="clear"></div>
</div>
<%
	} else if (action.equalsIgnoreCase("showemp")) {
%>
<div id="content"><!--  start page-heading -->
<div id="step-holder">
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">1</div>
<div class="step-light-left"><a
	href="EmployeeServlet?action=employee">Employee Detail</a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">2</div>
<div class="step-light-left"><a
	href="EmployeeServlet?action=empQual"> Qualification </a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no-off">3</div>
<div class="step-light-left"><a
	href="EmployeeServlet?action=address"> Address </a></div>
<div class="step-light-right">&nbsp;</div>
<div class="step-no">4</div>
<div class="step-dark-left"><a
	href="EmployeeServlet?action=family">Family</a></div>
<div class="step-dark-right">&nbsp;</div>
<div class="step-no-off">5</div>
<div class="step-light-left"><a
	href="EmployeeServlet?action=experience">Experience</a></div>
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
			<div class="clear"></div>
</div>
<%
	}
%>

<div id="page-heading">
<h1> Employee Family Information</h1>
</div>
<!-- end page-heading --> <%
if (action.equalsIgnoreCase("showemp"))
 {
 		
	String srNo=request.getParameter("srno")!=null?request.getParameter("srno"):"0";
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	ArrayList<EmpFamilyBean> empafamilyList = (ArrayList<EmpFamilyBean>) session.getAttribute("empfamilyList");
 		
 %>
 <form>
		<table border="0" width="100%" cellpadding="0" cellspacing="0" >
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

<form action="" name="" method="post" >
		<table id="customers" width="100%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input type="text" name="empName" id="empName" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr>
			</table>
			<table id="customers" width="100%">
			<tr>
				<th width="5%">SRNO</th>
				<th width="15%" >Name</th>
				<th width="10%">Relation</th>
				<th width="5%">Gender</th>
				<th width="10%">DOB</th>
				<th width="10%">Qualification</th>
				<th width="10%">Depenedency</th>
				<th width="10%">Occupation</th>
				<th width="40%"> Edit / Delete</th>
			</tr>

<%
if(empafamilyList.size()!=0)
{
	for (EmpFamilyBean empfamilybean : empafamilyList) 
	{
	%>
	<tr align="center">
		<td width="5%"><%=empfamilybean.getSRNO() %></td>
		<td width="15%"><%=empfamilybean.getNAME()%></td>
		<td width="10%"><%=lh.getLKP_Desc("RELATION", empfamilybean.getRELATION())%></td>
		<td width="5%"><%=empfamilybean.getGENDER()%></td>
		<td width="10%"><%=empfamilybean.getDOB()%></td>
		<td width="10%"><%=lh.getLKP_Desc("ED", empfamilybean.getQUALI())%></td>
		<td width="10%"><%=empfamilybean.getDEPENDYN()%></td>
		<td width="10%"><%=empfamilybean.getOCCUPATION()%></td>
		<td width="40%"><input type="button" value="Edit" class="myButton" onClick="window.location='empFamily.jsp?action=showemp&hidediv=yes&&srno=<%=empfamilybean.getSRNO() %>'" >
			&nbsp;<input type="button" value="Delete" class="myButton" onclick="deleteRow('emp','family','<%=empfamilybean.getSRNO() %>','EmployeeServlet?action=family')"></td>
	</tr>

	<%
		}
	}
	else
	{
	%>
	<tr> <td colspan="9" height="30">There Is No Information</td> </tr>
	<%	
	}
	%>
	<tr bgcolor="#1F5FA7"><td align="right" colspan="9"></td></tr>
	</table>
</form>
<br>
			<form action="EmployeeServlet?action=addFamily&family=addmore" method="post" onSubmit="return addFamilyvalidation()">
	<%
	if(hidediv.equalsIgnoreCase("yes"))
	{
	
	%>
	<div id="addempFamily"  hidden="true" style="width: 100%;">
	<%}
	else{
		
	%>
	<div id="addempFamily"  style="width: 100%;">
	<%}%>
	<h2>Add the Family Information of <%=session.getAttribute("empname") %> </h2>
<table style="width: 90%"; align="center" id="customers">
			  <tr class="alt"><td width="15%" >Employee Code</td>
	  		<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
				<td width="15%" >Employee Name</td>
			<td width="50%" ><input type="text" name="aempName" id="aempName" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>" readonly="readonly"></td>
			</tr>
			<tr class="alt">
				 
				<td>Gender</td>
				<td>
					<select class="form-control" name="rGender" id="rGender">
					<option value="0">Select.....</option>
					<option value="M"> Male </option>
					<option value="F"> Female </option>
					</select>
				</td>
				<td>Name.</td>
				<td><input class="form-control" name="rName" type="text" id="rName" maxlength="30" onBlur="Text(this.id)"></td>
			</tr>
			<tr class="alt">
				
				<td>Relation</td>
				<td>
				<select class="form-control" name="rRelation" id="rRelation" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RELATION");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
				
				
				</td>
				<td>Date Of Birth</td>
				<td><input class="form-control" type="text" name="rDOB" id="rDOB" readonly="readonly">
				<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('rDOB', 'ddmmmyyyy')" />
				</td>
			</tr>
			<tr class="alt">
				
				<td>Qualifiaction</td>
				<td>
				<select class="form-control" name="rQuali" id="rQuali" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result3=new ArrayList<Lookup>();
    						  LookupHandler lkhp3= new LookupHandler();
    					      result3=lkhp.getSubLKP_DESC("ED");
 							for(Lookup lkbean : result3){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
				
				</td>
				<td>Occuption</td>
				<td><input class="form-control" name="rOccup" type="text" id="rOccup" maxlength="20" onBlur="Text(this.id)"></td>
			</tr>
			<tr class="alt">
				
				
				<td>Depend</td>
				<td colspan="3">
			
				<input type="text" class="form-control" name="rDepend" id="rDepend" size="2"><font size="1">* Please Insert Only 'Y' or 'N'</font>
				 </td>

			</tr>
			<tr>
				<td colspan="4" align="center"><input type="submit" class="myButton" value="Submit"  />&nbsp; &nbsp;
		<input type="button" class="myButton" value="Cancel" onClick="document.getElementById('addempFamily').hidden=true; return false;"/></td>
			</tr>

	  </table>
    </div>
<div class="clear">&nbsp;</div>
</form>	
	
		<%

	for(EmpFamilyBean empfamilybean : empafamilyList)
	{
		
		if(srNo.equalsIgnoreCase(String.valueOf(empfamilybean.getSRNO())))
		{
			
	%>
<form name="editform" action="EmployeeServlet?action=editempFamily" method="post" onSubmit="return editFamilyvalidation()">
	<div id="empFamilyEdit">
		<table style="width: 90%"; id="customers">
				   <tr class="alt"><td width="15%" >Employee Code</td>
	  		<td width="20%" > <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="eempNo" id="eempNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="15%" >Employee Name</td>
			<td width="50%" ><input type="text" name="eempName" id="eempName" readonly="readonly"  value="<%=session.getAttribute("empname") %>"></td>
			</tr>
			<tr class="alt">
				<td>Sr No.</td>
				<td><input type="text"  readonly="readonly" name="esrNo" id="esrNo" value="<%=empfamilybean.getSRNO() %>"></td>
				<td>Gender</td>
				
				<td>
           			<select class="form-control" name="eGender" id="eGender">
					<% 
					if(empfamilybean.getGENDER().equalsIgnoreCase("M"))
					{	%>
					<option value="0">Select.....</option>
					<option value="M" selected="selected"> Male </option>
					<option value="F"> Female </option>
					<%}
            		else
            			{
            			%>
            			<option>Select.....</option>
						<option value="M" > Male </option>
						<option value="F" selected="selected"> Female </option>
            			<% 
            			}
           				%>
           </select>
            </td>
			
			</tr>
			<tr class="alt">
				<td>Name.</td>
				<td><input class="form-control" name="eName" type="text" id="eName" value="<%=empfamilybean.getNAME() %>" maxlength="30" onBlur="TextCheck(this.id)"></td>
				<td>Date Of Birth</td>
				<td><input type="text" class="form-control" name="eDOB" id="eDOB" value="<%= empfamilybean.getDOB() %>" readonly="readonly">
				<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eDOB', 'ddmmmyyyy')" />
				
				</td>
				
				
			</tr>
			<tr class="alt">
				<td>Relation</td>
				<td>
				<select class="form-control" name="eRelation" id="eRelation" >  
      					 <option value="0">Select</option>  
    						<%
    						
  							  ArrayList<Lookup> results=new ArrayList<Lookup>();
    						LookupHandler lkhps= new LookupHandler();
    						results=lkhp.getSubLKP_DESC("RELATION");
 							for(Lookup lkbean : results)
 							{
     						if(lkbean.getLKP_SRNO()== empfamilybean.getRELATION())
     						{
     							
     							%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
				
				
				
				</td>
				<td>Qualifiaction</td>
				<td>
				<select class="form-control" name="eQuali" id="eQuali" >  
      					  <option value="0">Select</option>  
    						<%
    						
  							  ArrayList<Lookup> result21=new ArrayList<Lookup>();
    						LookupHandler lkhp21 = new LookupHandler();
    						result21=lkhp21.getSubLKP_DESC("ED");
    					
 							
    						for(Lookup lkbean : result21)
 							{
 									
     						if(lkbean.getLKP_SRNO()== empfamilybean.getQUALI())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
     			 
     			 
     			 
		</td>
			</tr>
			<tr class="alt">
				<td>Occuption</td>
				<td><input class="form-control" name="eOccup" type="text" id="eOccup" value="<%=empfamilybean.getOCCUPATION() %>" maxlength="20" onBlur="TextCheck(this.id)"></td>
				<td>Depend</td>
				<td><input class="form-control" type="text" name="eDepend" id="eDepend" size="2" value="<%=empfamilybean.getDEPENDYN() %>"><font size="1">* Please Insert Only 'Y' or 'N'</font></td>

			</tr>
			<tr><td colspan="4" align="center"><input type="submit" class="myButton" value="Update" /> &nbsp;&nbsp;&nbsp;<input type="button" class="myButton" value="Cancel" onClick="Showhide()"/></td></tr>
			

		</table>
	
	</div>
	
	</form>
	<%
	}
	}
		
		%>
	
		<!--  end table-content  -->

		<div class="clear"></div>

		
		<!--  end content-table-inner ............................................END  -->

<%
}
else if(action.equalsIgnoreCase("addemp"))
{
	ArrayList<EmpFamilyBean> empafamilyList2 = (ArrayList<EmpFamilyBean>) session.getAttribute("empfamilyList");
	%>	
	 <form action="EmployeeServlet?action=addFamily" method="post" onSubmit="return addFamilyvalidation()">
			<table border="0" width="100%" cellpadding="0" cellspacing="0" >
		<tr>
			<th rowspan="3" class="sized"><img
				src="images/shared/side_shadowleft.jpg" width="25" height="200"
				alt="" /></th>
			<th class="topleft"></th>
			<td id="tbl-border-top">&nbsp;</td>
			<th class="topright"></th>
			<th rowspan="3" class="sized"><img
				src="images/shared/side_shadowright.jpg" width="25" height="200"
				alt="" /></th>
		</tr>
		<tr>
			<td id="tbl-border-left"></td>
			<td>
	<form action="EmployeeServlet?action=addFamily" method="post" onSubmit="return addFamilyvalidation()">
	<div >
<table width="90%" id="customers" >
				   <tr class="alt"><td width="15%" >Employee Code</td>
	  		<td width="20%" > <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="15%" >Employee Name</td>
			<td width="50%" ><input type="text" name="aempName"  id="aempName" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
			<input type="hidden" name="aempNo"  id="aempNo" readonly="readonly" value="<%=session.getAttribute("empno") %>">
			</tr>
			<tr class="alt">
				
				<td>Gender</td>
				<td>
					<select class="form-control" name="rGender" id="rGender">
					<option value="0">Select.....</option>
					<option value="M"> Male </option>
					<option value="F"> Female </option>
					</select>
				</td>
				<td>Name.</td>
				<td><input class="form-control" name="rName" type="text" id="rName" maxlength="30"></td>
			</tr>
			<tr class="alt">
				
				<td>Relation</td>
				<td>
				<select  class="form-control" name="rRelation" id="rRelation" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RELATION");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
				
				</td>
				<td>Date Of Birth</td>
				<td><input class="form-control" type="text" name="rDOB" id="rDOB" readonly="readonly">
				<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('rDOB', 'ddmmmyyyy')" />
				</td>
			</tr>
			<tr class="alt">
				
				<td>Qualifiaction</td>
				<td>
				<select class="form-control" name="rQuali" id="rQuali" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> results =new ArrayList<Lookup>();
    						LookupHandler lkhps= new LookupHandler();
    						results=lkhps.getSubLKP_DESC("ED");
 							for(Lookup lkbean : results){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
			</td>
				<td>Occuption</td>
				<td><input class="form-control" name="rOccup" type="text" id="rOccup" maxlength="20"></td>
			</tr>
			<tr class="alt">
				
				<td>Depend</td>
				<td >
				<input type="text" class="form-control" name="rDepend" id="rDepend" size="3"><font size="1">* Please Insert Only 'Y' or 'N'</font>
				</td>
				<td>Add More Member</td>
				<td valign="top"><input type="radio" name="moreAdd" id="moreAdd" value="YES" >YES &nbsp;&nbsp;&nbsp;&nbsp;
				 <input type="radio" name="moreAdd" id="moreAdd" value="NO"  checked="checked"> NO</td>
				
			</tr>
			<tr><td colspan="4" align="center"><input type="submit"  class="myButton" value="Submit"  /> 
		&nbsp; &nbsp;
		<input type="reset"  class="myButton"  value="Cancel"  /></td></tr>

	  </table>
	    </div>

	</form>
	
	
	<%
	
if(empafamilyList2!=null )
{
%>
<br>
<form>
	<table id="customers">
			<tr>
				<th width="0">SRNO</th>
				<th>Name</th>
				<th width="100">Relation</th>
				<th width="10">Gender</th>
				<th width="60">DOB</th>
				<th width="150">Qualification</th>
				<th width="50">Depenedency</th>
				<th width="220">Occupation</th>
				
			</tr><%
			for (EmpFamilyBean empfamilybean : empafamilyList2) 
	{
	%>
	<tr>
	<td><%=empfamilybean.getSRNO() %></td>
	<td><%=empfamilybean.getNAME()%></td>
	<td><%=lh.getLKP_Desc("RELATION",empfamilybean.getRELATION())%></td>
	<td><%=empfamilybean.getGENDER()%></td>
	<td><%=empfamilybean.getDOB()%></td>
	<td><%=lh.getLKP_Desc("ED",empfamilybean.getQUALI()) %></td>
	<td><%=empfamilybean.getDEPENDYN()%></td>
	<td><%=empfamilybean.getOCCUPATION()%></td>
	<td></td>
	</tr>

	<%
		}
	%>
	<tr bgcolor="#1F5FA7"><td align="right" colspan="9"></td></tr>
	</table>
			</table>
	</form>
<%	
}
}
%>
</td>
	<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>
</div>
</form>


</div>

<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>

</body>
</html>