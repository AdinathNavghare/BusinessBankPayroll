<%@page import="java.util.Locale"%>
<%@page import="com.ibm.icu.text.NumberFormat"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.AutopostBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Autopost Transactions  &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<%
	String pageName = "AutoPost.jsp";
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
<%int branch =0;
	try
	{
		branch = Integer.parseInt(request.getParameter("branch").toString());
	}
	catch(Exception e)
	{
		
	}
	PostingHandler PH = new PostingHandler();
	ArrayList<AutopostBean> list = PH.getAutoPostList(branch);
	int[] branches = PH.getBranches();
	NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
	
%>
<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
.style3 {font-size: 1.5em}
-->
</style></head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Autopost Transactions</h1>
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
		<td align="center">
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content" >
			  <table border="1" id="customers">
			  <tr>
				  <td><span class="style3"><label>Branch Wise View</label>
				  &nbsp;
				  	<%
				  		if(branch!=0)
				  		{
				  	%>
				  	&nbsp;<a href="AutoPost.jsp?branch=0">All</a>&nbsp;
				  	<%
				  		}
				  		else
				  		{
				  			%>
						  	&nbsp;All&nbsp;
						  	<%
				  		}
				  	%>
				  <%
				  		for(int i=0; i<branches.length; i++)
				  		{
				  			if(branch != branches[i])
				  			{
				  %>
				  	&nbsp;<a href="AutoPost.jsp?branch=<%=branches[i] %>"><%=branches[i] %></a>&nbsp;
				  <%
				  			}
				  			else
				  			{
				  			%>
							  	&nbsp;<%=branches[i] %>&nbsp;
							<%
				  			}
				  		}
				  %>
				</span>   </td>
			  </tr>
                <tr><td><table><tr>
                  <th width="52">Number</th>
                  <th width="120">Employee Number </th>
                  <th width="88">Branch Code </th>
                  <th width="114">Transaction Code </th>
                  <th width="51">SRNO</th>
                  <th width="109">Sub System Code </th>
                  <th width="107">Account Number </th>
                  <th width="58">Amount(Rs.)</th>
                  <th width="89">Voucher Type </th>
                  <th width="80">INST Number </th>
                </tr></table></td></tr>
              <tr><td>
             <div style="overflow-y: scroll; height: 450px; width: auto;">
              <table id="customers" >
             <%
              	int i=1;
             	String amt = null;
              	for(AutopostBean auto : list)
              	{
              		amt = format.format(auto.getAMOUNT());
              		amt = amt.substring(4);
              %>  
	                <tr align="center" valign="middle">
	                  <td width="52"><%=i++ %></td>
	                  <td width="120"><%=auto.getEMPNO() %></td>
	                  <td width="88"><%=auto.getBRCD() %></td>
	                  <td width="114"><%=auto.getTRNCD() %></td>
	                  <td width="51"><%=auto.getSRNO() %></td>
	                  <td width="109"><%=auto.getSUBSYS_CD() %></td>
	                  <td width="107" align="right"><%=auto.getAC_NO() %></td>
	                  <td width="68" align="right"><%=auto.getAMOUNT() %></td>
	                  <td width="89"><%=auto.getVOUC_TYPE() %></td>
	                  <td width="80"><%=auto.getINST_NO()%></td>
                </tr>
              <%
              	}
              %>
              </table>
              </div></td></tr></table>
			</div>
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