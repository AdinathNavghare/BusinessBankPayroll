<%@page import="payroll.Model.ItStandardBean"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.DAO.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<%
	String pageName = "ItStandard.jsp";
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

<script type="text/javascript">
function Update(key)
{
	
	window.showModalDialog("updateItstandard.jsp?action=update&key="+key,null,"dialogWidth:690px; dialogHeight:130px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="ItStandard.jsp";
}
var xmlhttp;
var url="";
if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
} 

function deleteRecord(key)
{
	
	
	var resp=confirm("Do you want to Delete this record?");
	if(resp==true)
	{
		
	 	url="ItStandardServlet?action=delete&key="+key;
		xmlhttp.onreadystatechange=callback;
		xmlhttp.open("POST", url, true);
		xmlhttp.send(); 
	}
}
function callback()
{
	if(xmlhttp.readyState==4)
	{
    	var response=xmlhttp.responseXML;
    	window.location.href="ItStandard.jsp";
    	
	}
}
function validation()
{
	
	if(document.getElementById("trancd").value == "0")
	{
		alert("Please Select Transaction Code");
		document.getElementById("trancd").focus;
		return false;
	}
	if(document.getElementById("amount").value=="")
	{
		alert("Please Insert Amount");
		document.getElementById("amount").focus;
		return false;
	}
}

</script>
</head>
<body style="overflow: hidden;" >
 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>IT STANDARD </h1>
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
			<%
			
			ArrayList<ItStandardBean> itsblist = new TranHandler().getITStandardList();
			%>
				<form action="ItStandardServlet" method="post" onsubmit="return validation()">
					<center>
					<table id="customers" bgcolor="#EAF2D3" align="center">
					<tr><td colspan="4" align="center">IT Standard<input type="hidden" name="action" id="action" value="add"></td></tr>
					<tr><td>Transaction Code</td>
						<td>
							<select name="trancd" id="trancd">
							<option value="0" selected="selected">select</option>
							<%
							
							 ArrayList<CodeMasterBean> list=new ArrayList<CodeMasterBean>();  
							  CodeMasterHandler codeHandler=new CodeMasterHandler();
							  list=codeHandler.gettrancd();
							  for(CodeMasterBean bean:list)
							  {
							  %>
							  
							  <option value="<%=bean.getTRNCD()%>"><%=bean.getDISC() %></option>
							  <%} %>
						    </select>
						</td>
						<td>Amount</td><td><input type="text" name="amount" id="amount"></td>
					</tr>
					<tr><td colspan="4" align="center"><input type="submit" value="Save">&nbsp;&nbsp;&nbsp;<input type="reset" value="Cancel"></td></tr>
					<tr><td colspan="4" align="center" ><font color="red" size="2"><%=request.getParameter("status")==null?"":request.getParameter("status") %></font></td></tr>
					</table>
					</center>
				</form>
				<br>
				<center>
				<% 
				if(itsblist.size()!=0)
				{
				%>
				<table id="customers">
						<tr><th>SRNO</th><th>Transaction code</th><th>Amount</th><th>Update</th><th>Delete</th></tr>
						<% 
						int i=1;
						for(ItStandardBean itsb : itsblist)
						{
							String key = itsb.getTRCD()+":"+itsb.getAMOUNT();
							
						%> 
						<tr><td><%=i%></td><td width="300"><%=CodeMasterHandler.getCDesc( itsb.getTRCD()) %> </td><td><%=itsb.getAMOUNT() %></td><td><input type="button" value="Update" onclick="Update('<%=key %>')"/></td><td><input type="button" value="Delete" onclick="deleteRecord('<%=key%>')"/></td></tr>	
						<%
						i++;
						}
						%>
				</table>
				<%} %>
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