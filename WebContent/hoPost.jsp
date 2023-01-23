<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="org.w3c.dom.CDATASection"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.HoPostBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<%
	String pageName = "hoPost.jsp";
	/* try
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
	} */
%>
<%
PostingHandler ph = new PostingHandler();
ArrayList<HoPostBean> hplist = ph.gethopostlist();
%>
</head>
<body style="overflow:hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; " >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>HOPOST </h1>
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
				 <table id="customers" width="1090">
					<tr><td width="946">
	     					<table width="728" border="1" id="customers">
	              				<tr class="alt" style="font-size:1" align="center">
	     							<th width="109">Branch Code</th>           			
	               					<th width="227">Transaction Code</th>
	             					<th width="130" align="right">SRNO</th>
	               					<th width="145">Sub System_Code</th>
	                 				<th width="125">Account Number</th>
	                 				<th width="137">Amount</th>
	                      			<th width="157">Voucher Type</th>
	                     
	             				</tr>
	      		</table>
						</td>
					</tr>
    				<tr><td>
	 			<div style="height:450px; overflow-y: scroll; width:auto" id="div1" >
				 <table  id="customers" align="center">
	 			<% 
 				if(hplist.size()!=0)
     			{
	 
	   				for(HoPostBean hb:hplist)
					{
				%>
		
				<tr  class="row"  bgcolor="#FFFFFF" align="center" >
	    			<td width="112" ><%=hb.getBRCD() %> </td>
	    			<td width="222"><%=hb.getTRNCD() %></td>
	    			<td width="114" ><%=hb.getSRNO() %> </td>
	    			<td width="153" ><%=hb.getSUBSYS_CD() %> </td>
	    			<td width="123" ><%=hb.getAC_NO() %> </td>
        			<td width="137" ><%=hb.getAMOUNT() %> </td>
	    			<td width="136" ><%=hb.getVOUC_TYPE() %> </td>
	    
	  
				</tr>
	 
				<%
					}
     			}	
	 		else
	 			{
		
				%>
				<tr align="center"><td colspan="11" align="center">Their Is No Any Record Found</td></tr>
				<%
				}
				%>
		
	 			</table>
		</div>
	
	 		</td>
	 		</tr>
			<tr bgcolor="#2f747e"><td>&nbsp;</td></tr>
			</table>
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