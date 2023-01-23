<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
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
<title>Salary Definition  &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->


<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<%
	String action = "";
	try
	{
		action = request.getParameter("action");
	}
	catch(Exception e)
	{
		
	}
		
	CodeMasterHandler CMH = new CodeMasterHandler();
	ArrayList<CodeMasterBean> incomeList = CMH.getCDMAST("1"); 	// Income Codes
	ArrayList<CodeMasterBean> deductList = CMH.getCDMAST("2"); 	// Deduction Codes
	

%>

<%
	/* String pageName = "CTC.jsp";
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
	} */
	
%>
<script type="text/javascript">
	function validate() 
	{
		if(document.getElementById("EMPNO").value == "")
		{
			alert("Invalid Employee");
			return false;
		}
		if(document.getElementById("annual").value == "")
		{
			alert("No Annual CTC entered, Can't create Salary Structure");
			return false;
		}
		
		
		var monthly_gross = document.getElementById("monthly").value;
		var total = document.getElementById("total").value;
		
		if(monthly_gross != total){
			alert("Salary Structure is not Match");
			return false;
		}
		
		
	}
	
	function checkFlag() 
	{
		
		var action = document.getElementById("action").value;
		if(action == "saved")
		{
			alert("Salary Structure Created Successfully.\n"+
					"You can verify it through Slab Management,"+
					" On Amount and Salary transaction Menu");
		}
	}
	
	function openOnAmt(trncdID)
	{
		var ecat = document.getElementById("EMPNO").value;
		var trncd = document.getElementById(trncdID).value;
		if(trncd!="0")
			var depend = window.showModalDialog("OnAmt.jsp?action=getList&ecat="+ecat+"&trncd="+trncd,"Code Dependency","dialogWidth=1200px;dialogHeight=700px;center=yes;addressBar=no;");
		//alert(depend);
	}
	function openSlab(trncdID)
	{
		var ecat = document.getElementById("EMPNO").value;
		var trncd = document.getElementById(trncdID).value;
		if(trncd!="0")
			var depend = window.showModalDialog("Slab.jsp?action=getList&ecat="+ecat+"&trncd="+trncd,"Slab Management","dialogWidth=1200px;dialogHeight=700px;center=yes;addressBar=no;");
		//alert(depend);
	}
	
	var incDepend="";
	var incVal="";
	var dedDepend="";
	var dedVal="";
	
	function addNewRow(tab,sel,valID)
	{
		var len= document.getElementById(tab).rows.length;
		var select = document.getElementById(sel);
		if(select.value!="0")
		{	
			var codeName=select.options[select.selectedIndex].innerHTML;
			select.remove(select.selectedIndex);
			var newSelect="<select id='"+sel+"'>"+select.innerHTML+"</select>";
			document.getElementById(tab).rows[len-1].cells[1].innerHTML=codeName;
			document.getElementById(tab).rows[len-1].cells[2].innerHTML=(tab=="incomeTab"? incDepend:dedDepend);
			document.getElementById(tab).rows[len-1].cells[3].innerHTML="Slab";
			document.getElementById(tab).rows[len-1].cells[4].innerHTML=(tab=="incomeTab"? incVal:dedVal);
			document.getElementById(tab).innerHTML = document.getElementById(tab).innerHTML+
													"<tr><td align='center' height='22'>&nbsp;"+len+"</td><td>"+newSelect+"</td>"+
														"<td align='center'>&nbsp;<input type='button' value='Set' onclick=\"openOnAmt('"+sel+"')\"></td>"+
														"<td align='center'><input type='button' value='Set' onclick=\"openSlab('"+sel+"')\"></td>"+
														"<td align='center'>&nbsp;<input type=\"text\" id=\""+valID+"\" name=\""+valID+"\" style=\"background-color: #C8DBFB;\" size=\"12\"></td></tr>";
		}	
	}	
</script>

</head>
<body style="overflow: hidden;" onLoad="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; height: 600px;" >
<!-- start content -->
<div id="content" style="max-width: 100% !important;">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Cost To Company (CTC)</h1>
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
			<table width="100%" border="1" align="center">
			  <tr><td align="center"><br/>
			  <form method="post" onKeyPress="return event.keyCode != 13;" onSubmit="return validate()">
			  <table width="564" height="66" border="1">
			   <tr bgcolor="#92B22C">
			     <td height="33" colspan="2">&nbsp;Type Employee Name or Number 
			       <input name="EMPNO" type="text" id="EMPNO" onClick="showHide()" size="50"></td>
			   </tr>
			   <tr>
			     <td width="261">&nbsp;Annual Total  
			       <input name="annual" type="text" id="annual" onKeyUp="calcMonthly(0)" dir="rtl" tabindex="1" autocomplete="off">
			       Rs.</td>
			     <td width="272">&nbsp;Monthly Gross 
			       <input name="monthly" type="text" id="monthly" readonly="readonly" style="background-color:silver;" dir="rtl">
			       Rs.</td>
			   </tr>
			   <tr>
			     <td>&nbsp;Eligible for PF &nbsp;
			       <input name="pf" type="checkbox" id="pf" value="true" ></td>
			     <td>&nbsp;Eligible for PT &nbsp;
			       <input name="pt" type="checkbox" id="pt" value="true"></td>
			     </tr>
			   </table>
			   
			   <p>&nbsp;</p>
			   
			   <table width="100%" border="1">
			   	<tr>
			   		<th width="50%"><h3 style="margin-bottom: 0px;">INCOMES</h3></th>
			   		<th width="50%"><h3 style="margin-bottom: 0px;">DEDUCTIONS</h3></th>
			   	</tr>
			   	<tr>
			   		<td style="padding: 3px;" valign="top" align="center">
			   			<table border="1" width="100%" id="incomeTab">
			   				<tr>
			   					<th width="10%">SR. NO.</th>
			   					<th width="35%">Income Code Name</th>
			   					<th width="20%">Depends On</th>
			   					<th width="20%">Apply Slab</th>
			   					<th width="15%">Value</th>
			   				</tr>
		   				
			   					<tr>
				   					<td align="center">&nbsp;1</td>
				   					<td align="left">
				   						<select id="incSelect" name="incSelect">
				   							<option value="0">Select</option>
				   						<%	for(CodeMasterBean CMB:incomeList)
							   				{
		   								%>
				   							<option value="<%=CMB.getTRNCD()%>"><%=CMB.getDISC()%></option>
				   						<%
							   				}
							   			%>	
				   						</select>
				   					</td>
				   					<td align="center">&nbsp;<input type="button" value="Set" onclick="openOnAmt('incSelect')"></td>
				   					<td align="center">&nbsp;<input type="button" value="Set" onclick="openSlab('incSelect')"></td>
				   					<td>&nbsp;<input type="text" id="incAmount" name="incAmount" style="background-color: #C8DBFB;" size="12"></td>
				   				</tr>
			   			</table><br/>
			   			<input type="button" value="Add" onclick="addNewRow('incomeTab','incSelect','incAmount')">
			   		</td>
			   		<td style="padding: 3px;" valign="top" align="center">
			   			<table border="1" width="100%" id="deductTab">
			   				<tr>
			   					<th width="10%">SR. NO.</th>
			   					<th width="35%">Deduction Code Name</th>
			   					<th width="20%">Depends On</th>
			   					<th width="20%">Apply Slab</th>
			   					<th width="15%">Value</th>
			   				<tr>
				   					<td align="center">&nbsp;1</td>
				   					<td>
				   						<select id="dedSelect" name="dedSelect">
				   							<option value="0">Select</option>
				   						<%	
							   				for(CodeMasterBean CMB:deductList)
							   				{
		   								%>
				   							<option value="<%=CMB.getTRNCD()%>"><%=CMB.getDISC()%></option>
				   						<%
							   				}
							   			%>	
				   						</select>
				   					</td>
				   					<td align="center">&nbsp;<input type="button" value="Set" onclick="openOnAmt('dedSelect')"></td>
				   					<td align="center">&nbsp;<input type="button" value="Set" onclick="openSlab('dedSelect')"></td>
				   					<td>&nbsp;<input type="text" id="dedAmount" name="dedAmount" style="background-color: #C8DBFB;" size="12"></td>
				   				</tr>   		
			   			</table><br/>
			   			<input type="button" value="Add" onclick="addNewRow('deductTab','dedSelect','dedAmount')">
			   		</td>
			   	</tr>
			   </table>
			   <p>&nbsp;</p>
			   <input type="reset" name="reset" value="Clear">
			   <input type="submit" name="Submit" value="Create Salary Structure">
			   <br/></form></td>
			  </tr></table>
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
    
<input type="hidden" id="action" name="action" value="<%=action%>">
</body>
</html>