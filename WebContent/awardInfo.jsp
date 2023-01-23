<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpAwardBean"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="payroll.Model.EmpExperBean"%>
 <%@page import="java.util.*"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/DeleteRow.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/awardValidation.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/awardValidation.js"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript">

function Showhide()
{
	
document.getElementById("empAwardEdit").hidden=true;
document.getElementById("addRelInfo").hidden=false;

	}

</script>


<% 
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp"; 
String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
LookupHandler lkhp1 = new LookupHandler();
%>
</head>
<body style="overflow:hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<% if(action.equalsIgnoreCase("addemp"))
{
		%>
	<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empQual.jsp">Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="empFamily.jsp">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">7</div>
			<div class="step-dark-left"><a href="awardInfo.jsp">Award</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		</div>
<%
	}
else if(action.equalsIgnoreCase("showemp"))
	{

	%>
<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual"> Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=Family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
			<div class="step-no">7</div>
			<div class="step-dark-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
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
<h1> Employee Award Information</h1>
</div>
<!-- end page-heading -->
<%
if(action.equalsIgnoreCase("showemp"))
{
	String srNo = request.getParameter("srno")!=null?request.getParameter("srno"):"0";
	ArrayList<EmpAwardBean> empawardList = (ArrayList<EmpAwardBean>)session.getAttribute("empawardList");
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);
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
	<form>
			<table id="customers" width="100%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input type="text" name="empNo" id="empNo" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr>
			</table>
		<table id="customers"  width="100%" >
		<tr ><th width="5%">SRNO</th>
		<th width="10%" >ORDER_NO</th>
		<th width="10%" >TRANSACTION</th>
		<th width="10%" >DATE</th>
		<th width="5%" >AMOUNT</th>
		<th width="30%">DESCRIPTION</th>
		<th width="40%">Edit / Delete</th>
		</tr>
		<%
			
		if(empawardList.size()!=0)
		{
		for(EmpAwardBean empawardbean : empawardList)
			{ 
				
				%>
			<tr align="center"> 
				<td><%=empawardbean.getSRNO() %></td>
				<td><%=empawardbean.getORDER_NO() %></td>
				<%-- <td><%=empawardbean.getTRNCD()%></td> --%>
				<td><%=lkhp1.getLKP_Desc("AWARD", empawardbean.getTRNCD())%></td>
				<td><%=empawardbean.getEFFDATE() %></td>
				<td><%=empawardbean.getAMOUNT() %></td>
				<td align="left"><%=empawardbean.getMDESC() %></td>
				
				<td><input type="button" value="Edit"  class="myButton"  onClick="window.location='awardInfo.jsp?action=showemp&hidediv=yes&srno=<%=empawardbean.getSRNO() %>'"/>
					&nbsp;<input type="button"  class="myButton" value="Delete" onclick="deleteRow('emp','award','<%=empawardbean.getSRNO() %>','EmployeeServlet?action=awardInfo')">
				</td>
		
			</tr>
			

				<% //onClick="window.location='empExper.jsp?action=showemp&srno=<%=empexperbean.getSRNO() >'";
			}
		}
		else
		{
			%>
			<tr> <td colspan="7" height="30">There Is No Information</td> </tr>
		
			<%}
	%>
	<tr bgcolor="#1F5FA7"><td align="right" colspan="7"></td></tr>
	</table>
</form>
<form action="EmployeeServlet?action=addAwardInfo&award=addmore" method="post" onSubmit="return addAwardvalidation()">
	<%
	if(hidediv.equalsIgnoreCase("yes"))
	{
		%>
	<div id="addAwardInfo"  hidden="true" style="width: 100%;" >
	<%}
	else{
		
	%>
	<div id="addAwardInfo" style="width: 100%;"  >
	<%}%>
	<h2>Add the Award Information of <%=session.getAttribute("empname") %> </h2>
<table id="customers" style="width: 90%;" align="center">
		
	
		<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input class="form-control" type="text" name="aempName" id="aempName" size="40" readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td>
		  		</tr>
		<tr class="alt">
		  <td>Transaction Code </td>
		  <td>
		  		<select class="form-control" name="atranCode" id="atranCode" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						/* LookupHandler lkhp1= new LookupHandler(); */
    						result=lkhp1.getSubLKP_DESC("AWARD");
 							for(Lookup lkbean : result){
     						%>
     						
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     						}%>
		      </select></td>
		<td >Effective Date </td>
		  <td ><input class="form-control" type="text" name="aEffDate" id="aEffDate" value="">
		  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aEffDate', 'ddmmmyyyy')" />
		  
		  </td></tr>
	   <tr class="alt">	
			<td>Order Number </td>
			<td align="left" ><input class="form-control" name="aOrderNo" type="text" id="aOrderNo" value="" maxlength="10"></td>
			<td>Certificate/Award Amount </td>
			<td align="left" ><input  class="form-control" name="aamount" type="text" id="aamount" value="" maxlength="10"></td>
			
			</tr>
		<tr class="alt">
		  <td valign="top">Description </td>
		  <td align="left" colspan="3">
		  <textarea class="form-control" name="aDescrp" id="aDescrp" cols="50" >
		  </textarea>
		  
		  </td>
		</tr>

		
		<tr class="alt"><td colspan="4" align="center"><input type="submit"  class="myButton" value="Save" /> &nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel" /></td></tr></table>
    </div>
<div class="clear">&nbsp;</div>
</form>	
<%

	for(EmpAwardBean empawardbean : empawardList)
	{
		
		if(srNo.equalsIgnoreCase(String.valueOf(empawardbean.getSRNO())))
		{
			
	%>
	<form name="editform" id="editform" action="EmployeeServlet?action=empAwardEdit" method="post" onSubmit="return editAwardvalidation()">
	<div id="empAwardEdit"  >
	<br/>
	<h2>Edit The Information of <%=session.getAttribute("empname") %> </h2>
			<table width="719" id="customers">
		  		<tr class="alt"><td width="104" >Employee Code</td>
	  			<td width="158" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="eempNo" id="eempNo"  readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="156" >Employee Name</td>
			<td width="281" ><input class="form-control" type="text" name="eempName" id="eempName" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td></tr>
		<tr class="alt"><td>Transaction Serail No </td>
			<td><input type="text" class="form-control" name="eTranSerNO" id="eTranSerNO" value="<%=empawardbean.getSRNO() %>" readonly="readonly"></td>
		 <td >Effective Date </td>
		  <td ><input type="text" class="form-control" name="eEffDate" id="eEffDate" value="<%=empawardbean.getEFFDATE() %>">
		  <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eEffDate', 'ddmmmyyyy')" />		  	</td>
		</tr>
	   <tr class="alt">	
	    <td>Transaction Code </td>
		  <td>
		  		<select class="form-control" name="etranCode" id="etranCode" >  
      					 <option value=0>Select....</option>  
    						<%
  							  ArrayList<Lookup> result11=new ArrayList<Lookup>();
    						LookupHandler lkhp11= new LookupHandler();
    						result11=lkhp11.getSubLKP_DESC("AWARD");
 							for(Lookup lkbean : result11){
     						if(lkbean.getLKP_SRNO()== empawardbean.getTRNCD())
     						{
     							%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>
     							<%
     						}
     						else
     						{
     						%>
     						
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     						}}%>
		      </select></td>
			<td>Certificate/Award Amount </td>
			<td align="left" ><input class="form-control" name="eamount" type="text" id="eamount" value="<%=empawardbean.getAMOUNT() %>" maxlength="10"></td></tr>
			<tr class="alt">
			<td>Order Number </td>
			<td align="left" colspan="3"><input class="form-control" name="eOrderNo" type="text" id="eOrderNo" value="<%=empawardbean.getORDER_NO() %>" maxlength="10"></td>
			
			</tr>
		<tr class="alt">
		  <td>Description </td>
		  <td align="left" colspan="3">
		    <input class="form-control" type="text" name="eDescrp" id="eDescrp" value="<%=empawardbean.getMDESC() %>" size="100"></td>
		</tr>

		
		<tr class="alt"><td colspan="4" align="center"><input type="submit"  class="myButton" value="Update" /> &nbsp;&nbsp;&nbsp;<input type="button" value="Cancel"  class="myButton" onClick="Showhide()"/></td></tr>
    </table>
    </div>
    <div class="clear">&nbsp;</div>
    </form>
<%
	}
	
	}
	
}
else if(action.equalsIgnoreCase("addemp"))
{
	ArrayList<EmpAwardBean> empawardList2 = (ArrayList<EmpAwardBean>)session.getAttribute("empawardList");
	
%>	
<form action="EmployeeServlet?action=addAwardInfo" method="post" onSubmit="return addAwardvalidation()">
		<table border="0" width="auto" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="30" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="30" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
<form action="EmployeeServlet?action=addAwardInfo" method="post" onSubmit="return addAwardvalidation()">
<div >
<table width="677" id="customers">
		  		<tr class="alt"><td width="143" >Employee Code</td>
	  			<td width="172" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
				<td width="145" >Employee Name</td>
			<td width="197" ><input class="form-control" type="text" name="aempName" id="aempName" readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td></tr>
		<tr class="alt">
		  <td>Transaction Code </td>
		  <td>
		  		<select class="form-control" name="atranCode" id="atranCode" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						/* LookupHandler lkhp1= new LookupHandler(); */
    						result=lkhp1.getSubLKP_DESC("AWARD");
 							for(Lookup lkbean : result){
     						%>
     						
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     						}%>
		      </select></td>
		<td >Effective Date </td>
		  <td ><input class="form-control" type="text" name="aEffDate" id="aEffDate" value="">
		  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('aEffDate', 'ddmmmyyyy')" />
		  
		  </td></tr>
	   <tr class="alt">	
			<td>Order Number </td>
			<td ><input class="form-control" name="aOrderNo" type="text" id="aOrderNo" value="" maxlength="10"></td>
			<td>Certificate/Award Amount </td>
			<td align="left" ><input class="form-control" name="aamount" type="text" id="aamount" value="" maxlength="10"></td>
			</tr>
			
		<tr class="alt">
		  <td  valign="top">Description </td>
		  <td align="left" colspan="3"><p>
		   
		  <textarea class="form-control" name="aDescrp" id="aDescrp" cols="50" >
		  </textarea></td>
		</tr>
		<tr class="alt">
		<td >Add More Awards</td>
			<td valign="top" colspan="3"><input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="YES" >YES &nbsp;&nbsp;&nbsp;&nbsp;
			<input class="form-control" type="radio" name="moreAdd" id="moreAdd" value="NO"  checked="checked"> NO</td>
		</tr>
		
		<tr class="alt"><td colspan="4" align="center"><input type="submit"  class="myButton" value="Save" /> &nbsp;&nbsp;&nbsp;<input type="button" value="Cancel" class="myButton"  onClick="Showhide()"/></td></tr>
    </table>
    </div>
<div class="clear">&nbsp;</div>
</form>	

<%
if(empawardList2!=null)
{
%>
<br><br>
<form>
<table width="1849" id="customers" >
		<tr ><th >SRNO</th>
		<th >ORDER_NO</th>
		<th >TRANSACTION</th>
		<th >DATE</th>
		<th >AMOUNT</th>
		<th width="626">DESCRIPTION</th>
		
		</tr>
		<%
			for(EmpAwardBean empawardbean : empawardList2)
			{ 
				
				%>
				<tr><td><%=empawardbean.getSRNO() %></td>
				<td><%=empawardbean.getORDER_NO() %></td>
				<td><%=empawardbean.getTRNCD()%></td>
				<td><%=empawardbean.getEFFDATE() %></td>
				<td><%=empawardbean.getAMOUNT() %></td>
				<td><%=empawardbean.getMDESC() %></td>
	</tr>
			

				<% 
			}
	%>
	<tr bgcolor="#2f747e"><td align="right" colspan="9"></td></tr>
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

</form>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    </div>

</body>
</html>