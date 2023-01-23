<%@page import="javax.xml.crypto.Data"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>

<title>User</title>
<% 

	String empno = session.getAttribute("EMPNO").toString(); 
	String action = request.getParameter("action")==null?"":request.getParameter("action");
	LeaveMasterHandler lmh = new LeaveMasterHandler();
	ArrayList<LeaveMasterBean> leaveballist = lmh.leaveDisplay(Integer.parseInt(empno));
	
	Date dt1 = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(dt1);
%>
<script language="javascript">


function validate()
{

	
	
   
   var fromdate = document.searchform.frmdate.value;
   var todate = document.searchform.todate.value;
   fromdate = fromdate.replace(/-/g,"/");
   todate = todate.replace(/-/g,"/");
   var d1 = new Date(fromdate);
   var d2 = new Date(todate);
   if( document.searchform.todate.value=="")
   {
	   alert("please enter the todate");
	      document.searchform.todate.focus();
	      return false;
	   }
   if( d1.getTime() > d2.getTime())
   {
	   alert("From Date can not Greater Than To Date ");
	      document.searchform.todate.focus();
	      return false;

	   }  
 
}
</script>
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
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="empleave.jsp" >Leave </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">2</div>
			<div class="step-dark-left"><a href="empLeaveHistory.jsp">Leave Balance</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empLeaveCancel.jsp">Leave Cancel</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		
		</div>
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
			<% if(action.equalsIgnoreCase("transaction"))
			{
				
				ArrayList<LeaveMasterBean> slist= (ArrayList<LeaveMasterBean>)session.getAttribute("searchlist");
				%>
				<form  name="searchform" action="LeaveMasterServlet?action=search" method="Post" onSubmit="return validate()">
			 	<table width="700" cellpadding="0" cellspacing="0" id="customers"align="center">
	       			<tr><th width="700">Search Transaction <input type="hidden" id="EMPNO" name="EMPNO" value="<%=empno %>" ><input type="hidden" id="user" name="user" value="employee" ></th></tr>
  			   </table>
			  <table width="700" border="1" align="center" id="customers">
               		<tr class="alt">
                 	 
                	</tr>
               	<tr class="alt">
                     <td height="44">
				     <table width="700"> 
				     	<tr class="alt">
				     		<td>Fromdate</td><td bgcolor="#FFFFFF"><input name="frmdate" id="frmdate" type="text" readonly="readonly" value="<%=request.getAttribute("frmdate") %>" onBlur="if(value=='')" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
				     		<td>Todate</td> <td bgcolor="#FFFFFF"><input name="todate" id="todate" type="text" readonly="readonly" value="<%=request.getAttribute("todate") %>" onBlur="if(value=='') " >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
				     		
							<td>
							<%
							String ALL = "notselected";
							String SL = "notselected";
							String PL = "notselected";
							String CL = "notselected"; 
							if(request.getAttribute("lcode1").toString().equalsIgnoreCase("ALL")){
								ALL = "selected";
							}else if(request.getAttribute("lcode1").toString().equalsIgnoreCase("SL")){
								SL = "selected";
							}else if(request.getAttribute("lcode1").toString().equalsIgnoreCase("PL"))
							{
								PL = "selected";
							}else if(request.getAttribute("lcode1").toString().equalsIgnoreCase("CL")){
								CL = "selected";
							}
							
							%>
							
							<select name="lcode">
							
							<option value="">Select Leave Type</option>
								<option value="ALL" <%=ALL %> >ALL</option>
								<option value="SL" <%=SL %> >SL</option>
								<option value="PL" <%=PL %>>PL </option>
								<option value="CL" <%=CL %>>CL </option>
								
								</select>
							</td>
							<td><input type="submit" value="Search"/></td>
						 </tr>
					</table>
				</td>
                </tr>
              </table>
			</form>
			</center>
			<center>
			<br><br>
			<table id="customers" width="845">
				<tr>
					<td>
						<table border="1" id="customers">
							<tr class="alt" style="font-size: 2">
				     			<th width="68" height="20">LeaveCD</th>
								<th width="62">EmpNo</th>
								<th width="70">TransDate</th>
								<th width="69" align="right">TransType</th>
								<th width="81">Appl No</th>
								<th width="77">TeleNo</th>
								<th width="73">Appl Date</th>
								<th width="75">From Date</th>
								<th width="80">To Date</th>
								<th width="129">SancAth</th>
								<th width="55">Status</th>
							</tr>
						</table>
					</td>
			  </tr>
			  
			<tr>
			   <td>
			   
				 <div style="height: 250px; overflow-y: scroll; width: auto" id="div1">
					
					<table id="customers" align="center">
						<% 
						 if(leaveballist.size()!=0)
     						{
	 							for(LeaveMasterBean leave:slist)
									{
						%>

					<tr class="row" bgcolor="#FFFFFF">
						<td width="65"><%=leave.getLEAVECD() %></td>
						<td width="65"><%=leave.getEMPNO() %></td>
						<td width="70"><%=leave.getTRNDATE() %></td>
						<td width="70"><%=leave.getTRNTYPE() %></td>
						<td width="80"><%=leave.getAPPLNO() %></td>
						<td width="75"><%=leave.getLTELNO() %></td>
						<td width="75"><%=leave.getAPPLDT() %></td>
						<td width="75"><%=leave.getFRMDT() %></td>
						<td width="80"><%=leave.getTODT() %></td>
						<td width="130"><%=leave.getSANCAUTH() %></td>
						<td width="40"><%=leave.getSTATUS() %></td>
					</tr>
					<%
						   }
     						}
	 					else
	 						{
						%>
					<tr align="center">
						<td colspan="11" align="center">Their Is No Any Record Found</td>
					</tr>
						<%
							}
					  %>
					</table>
				</div>
				</td></tr></table></center>
				</div> <!--  end table-content  -->
		<div class="clear"></div>
		</div> <!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
		</tr>
		<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
		</tr>
		</table>
			
			<%}
			else
			{
				%>
			
			<form  name="searchform" action="LeaveMasterServlet?action=search" method="Post" onSubmit="return validate()">
			 	<table width="682" cellpadding="0" cellspacing="0" id="customers"align="center">
	       			<tr><th width="682">Search Transaction <input type="hidden" id="EMPNO" name="EMPNO" value="<%=empno %>" ><input type="hidden" id="user" name="user" value="employee" ></th></tr>
  			   </table>
			  <table width="682" border="1" align="center" id="customers">
               		<tr class="alt">
                 	 
                	</tr>
               	<tr class="alt">
                     <td height="44">
				     <table width="680"> 
				     	<tr class="alt">
				     		<td>Fromdate</td><td bgcolor="#FFFFFF"><input name="frmdate" id="frmdate" type="text" value="" onBlur="if(value=='')" readonly="readonly" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td>
				     		<td>Todate</td> <td bgcolor="#FFFFFF"><input name="todate" id="todate" type="text" value="" onBlur="if(value=='') "  readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
				     		
							<td><select name="lcode">
								<option>ALL</option>
								<option>SL</option>
								<option>PL</option>
								<option>CL</option>
								</select>
							</td>
							<td><input type="submit" value="Search"/></td>
						 </tr>
					</table>
				</td>
                </tr>
              </table>
			</form>
			</center>
			<center>
			<br><br>
			<table id="customers" width="845">
				<tr>
					<td>
						<table border="1" id="customers">
							<tr class="alt" style="font-size: 2">
				     			<th width="68" height="20">LeaveCD</th>
								<th width="62">EmpNo</th>
								<th width="70">TransDate</th>
								<th width="69" align="right">TransType</th>
								<th width="81">Appl No</th>
								<th width="77">TeleNo</th>
								<th width="73">Appl Date</th>
								<th width="75">From Date</th>
								<th width="80">To Date</th>
								<th width="129">SancAth</th>
								<th width="55">Status</th>
							</tr>
						</table>
					</td>
			  </tr>
			  
			<tr>
			   <td>
			   
				 <div style="height: 250px; overflow-y: scroll; width: auto" id="div1">
					
					<table id="customers" align="center">
						<% 
						 if(leaveballist.size()!=0)
     						{
	 							for(LeaveMasterBean leave:leaveballist)
									{
						%>

					<tr class="row" bgcolor="#FFFFFF">
						<td width="65"><%=leave.getLEAVECD() %></td>
						<td width="65"><%=leave.getEMPNO() %></td>
						<td width="70"><%=leave.getTRNDATE() %></td>
						<td width="70"><%=leave.getTRNTYPE() %></td>
						<td width="80"><%=leave.getAPPLNO() %></td>
						<td width="75"><%=leave.getLTELNO() %></td>
						<td width="75"><%=leave.getAPPLDT() %></td>
						<td width="75"><%=leave.getFRMDT() %></td>
						<td width="80"><%=leave.getTODT() %></td>
						<td width="130"><%=leave.getSANCAUTH() %></td>
						<td width="40"><%=leave.getSTATUS() %></td>
					</tr>
					<%
						   }
     						}
	 					else
	 						{
						%>
					<tr align="center">
						<td colspan="11" align="center">Their Is No Any Record Found</td>
					</tr>
						<%
							}
					  %>
					</table>
				</div>
				</td></tr></table></center>
				</div> <!--  end table-content  -->
		<div class="clear"></div>
		</div> <!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
		</tr>
		<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
		</tr>
		</table>
		<%}
			%>
			
</center>
			
	<div class="clear">&nbsp;</div>
</div>
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>