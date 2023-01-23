<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ page import="payroll.Model.*" %>  
  <%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="payroll.DAO.UserMedicalHandler" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Medical</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
 <script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<% 
   
String action=request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<UserMedicalBean> Medilist=null;
if(action.equals("showaddbill"))
{
	Medilist= (ArrayList<UserMedicalBean> )session.getAttribute("medicallist"); 
	  
}



%>


<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>

</head>
<body> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>User Medical Bill </h1>
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
			<div id="table-content" align="center">
			
			<h3>Medical</h3>
			
			
			
			
			 <form  name="" action="medical?action=addbill" method="post" >
				     <table width="569" align="center" id="customers">
					  <tr><th colspan="4">Medical Bill</th></tr>
					  <tr class="alt">
					     <td width="85">EMPNO</td>
				        <td width="190"> <input type="text" name="EMPNO"  id="EMPNO" onclick="showHide()" title="Enter Employee No"></td><td width="84">RELATION</td>
				        <td width="190"><input type="text" name="relation"  /></td>
					  </tr>
					 
					  <tr class="alt">
					     <td>TRANDATE</td><td><input name="trandate"  size="20" id="trandate" type="text" readonly="readonly" onBlur="if(value=='')">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('trandate', 'ddmmmyyyy')" /></td>    <td width="85">BILLAMOUNT</td>
					     <td width="190"><input type="text" name="amount" id="amount" /></td>  
					  </tr>
					 
					  <tr class="alt">
					     <td>FROMDATE</td>
					     <td><input name="frmdate" size="20" id="frmdate" type="text" readonly="readonly" onBlur="if(value=='')" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" /></td><td>TODATE</td><td><input name="todate"  size="20" id="todate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
					  </tr>
					 <tr class="alt">
					     <td width="84">DESCRIPTION</td>
			           <td width="190"><input type="text" name="desc" /></td> <td colspan="2">&nbsp;</td>
					  </tr>
					 <tr class="alt"><td align="center" colspan="4"><input type="submit" value="Save" /> &nbsp; <input type="reset" value="Cancel"/>
					  </td>
					 </tr>
					 <tr bgcolor="#92b22c"><td colspan="4">&nbsp;</td></tr>
				   </table>
				 
				 
				 </form>
				 <br>
				   <table id="customers">
				    <tr><td width="609"><table>
					 <tr>
					    <th width="60"> EMPNO</th><th width="60">  TRANID </th> <th width="60">TRANDATE</th><th width="70">RELATION  </th><th width="50">AMOUNT</th><th width="70">FROMDATE </th><th width="60">TODATE</th>
						<th width="60">DESC</th>
                      </tr>
				   </table>
				   </td>
				   </tr>
				  
				   <tr><td>
				    <div style="height:120px; overflow-y: scroll; width:auto" id="div1" >
				   <table width="609">
				   
	                  
			           <%
			           
			          
			           if(action.equals("showaddbill"))
				        {
			        	   if(Medilist.size()!=0)
				           {	   
					for(UserMedicalBean ubean1:Medilist)
				    	 {
				    	 %>
	                  <tr class="row">
			           <td width="80"><%=ubean1.getEMPNO()%> </td><td width="90"><%=ubean1.getTRANID()%> </td><td width="90"><%=ubean1.getTRANDATE()%> </td><td width="90"> <%=ubean1.getRELATION()%></td><td width="70"><%=ubean1.getAMOUNT()%> </td><td width="90"><%=ubean1.getFROMDATE()%> </td><td width="90"> <%=ubean1.getTODATE()%></td><td width="60"><%=ubean1.getDESCRIPTION()%></td>
			          </tr>
			          <%} 
				        } 
				        }
			        	   else
			        	   {		   
					  %> 
			          <tr>
			           <td colspan="7" align="center">Currently their no  Transaction</td>
			          </tr>
			         
			         <%} %> 
	                </table>
					</div>
				</td>
				</tr>
					
	              <tr bgcolor="#92b22c"><td>&nbsp; </td></tr>
				 </table>
			
			
			
			
			
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