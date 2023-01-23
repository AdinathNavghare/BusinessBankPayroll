<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.SequenceFileBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.SequenceFileDAO"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>&copy; DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<%

String flag=request.getParameter("flag")==null?"0":request.getParameter("flag");


	String pageName = "Seq_File.jsp";
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
<script type="text/javascript">
/* function spiner()
{
document.getElementById("process").hidden=false;
} */
function chkFlag()
{
	var flag=document.getElementById("flag").value;
	
	if(flag==1)
		{
		alert("File Created Successfully !");
		}
	if(flag==2)
	{
	alert("Error in generating File !\n Please Check the Data!");
	}
		
}


</script>
</head>
<body onload="chkFlag()">
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"   style="overflow-y:auto;max-height:100%; ">
<!-- start content -->
<div id="content"    style="overflow-y:auto;max-height:100%; ">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Generate Sequential File</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" >
		<tr>
			<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="350" alt="" /></th>
			<th class="topleft"></th>
			<td id="tbl-border-top">&nbsp;</td>
			<th class="topright"></th>
			<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="350" alt="" /></th>
		</tr>
		<tr>
			<td id="tbl-border-left"></td>
			<td align="center">
			<!--  start content-table-inner ...................................................................... START -->
			<div id="content-table-inner">
				<!--  start table-content  -->
				<div id="table-content" >
				
				<form action="SequenceFileServlet?action=seqfile" method="post">
					<table border="1" id="customers">
						 <tr>
						 	<th colspan="2">Generate Sequance File</th>
						</tr>		
					  	<tr>
						 	<td>Select Account :</td>
						  	<td> 
							  	<select name="ac" id="ac">
						  		<%	  		
							  		SequenceFileDAO SF=new SequenceFileDAO();
							  		ArrayList<SequenceFileBean> list=new ArrayList<SequenceFileBean>();
							  		list=SF.getAllSubsysCode();
							  		
							  		for(SequenceFileBean sfBean:list)
							  		{
							  			%>
							  			<option value='<%=sfBean.getTrncd()+":"+sfBean.getSubsys()%>'><%=sfBean.getDisc()+":"+sfBean.getSubsys()%> </option>					  			
							  			<%
							  		}
							  	%>
							  	
							  	 <option value="LOANALL">LOANALL </option>
								  <option value="BRDR">BRDR </option>
								  <option value="SALNET">SALNET </option>
								  <option value="HOCR">HOCR </option>
								  <option value="PLDR">PLDR </option>
								 <!--for encashment  -->
								  <option value="BRDRE">BRDR:encash </option>
								  <option value="SALNETE">SALNET:encash </option>
								  <option value="HOCRE">HOCR:encash </option>
								  <option value="PLDRE">PLDR:encash </option>
								  <option value="BONUS">BONUS</option>
								  <option value="LEAVEENCASH">LEAVE:encash</option>
								 <!-- <option value="PMJJBY_PMSBY">PMSSBY/PMJTBY</option>  -->
							  	</select>   </td>
						</tr>
						
						<tr>
							<td>Select Date</td>
							<td>
								<input type="text" id="date" name="date" readonly="readonly">
								<img src="images/cal.gif" align="middle"
									style="vertical-align: middle; cursor: pointer;"
									onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
							</td>
						
						</tr>
						<tr>
							<td width="150">Branch / Project Name</td>
			 				<td width="100">
								<select name="branch" id="branch"  style="width: 100px;">  
						      		<option value="All">All</option>  
						    		<%
						    			ArrayList<EmpOffBean> list1= new ArrayList<EmpOffBean>();
						    			EmpOffHandler ofh = new EmpOffHandler();
						    			list1=ofh.getprojectCode();
						    			for(EmpOffBean lkb :list1)
						 				{
						 				%>
						      			<option value="<%=lkb.getPrj_srno()%>"> <%=lkb.getPrj_name()%></option>
						      						  
						     			<%
						     			}
						     		%>				
						     	  </select>
							  </td>	
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="submit" class='myButton' Value='Generate Sequential File' >
							</td>
						</tr>
					</table>
				</form>
	
	<input type="hidden" id="flag" name='flag' value='<%=flag%>'>
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
<!-- <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				
				<h1> File Generation IN Progress ....Be Patience</h1>
				
				<img alt="" src="images/process.gif">
				</div>
			</div> -->
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>