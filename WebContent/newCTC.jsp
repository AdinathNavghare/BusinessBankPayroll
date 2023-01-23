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
	CodeMasterHandler CMH = new CodeMasterHandler();
	ArrayList<CodeMasterBean> incomeList = CMH.getCDMAST("1"); 	// Income Codes
	ArrayList<CodeMasterBean> deductList = CMH.getCDMAST("2"); 	// Deduction Codes
	incomeList.remove(incomeList.size()-1);	//	Removed 199
	incomeList.remove(incomeList.size()-1);//	Removed 999
	String action = "";
	String emp="";
	String monthly="";
	String yearly="";
	ArrayList<Integer> incVals = new ArrayList<Integer>();
	ArrayList<Integer> dedVals = new ArrayList<Integer>();
	ArrayList<String> incTranVals = new ArrayList<String>();
	ArrayList<String> dedTranVals = new ArrayList<String>();
	try
	{
		action = request.getParameter("action").toString();
		if(action.equalsIgnoreCase("2"))	//return from Save
		{
			emp = request.getAttribute("emp").toString();
			incTranVals = (ArrayList<String>)request.getAttribute("incTranVals");
			dedTranVals = (ArrayList<String>)request.getAttribute("dedTranVals");
			
			monthly = incTranVals.get(incTranVals.size()-2)==null?"":incTranVals.get(incTranVals.size()-2);
			//int temp = Float.parseFloat(monthly)*12;
			yearly = String.valueOf(Float.parseFloat(monthly)*12);
			int dot=0;
			dot = yearly.indexOf('.');
			if(dot>0)
				yearly = yearly.substring(0, dot);
			int dot1 = monthly.indexOf('.');
			if(dot1>0)
				monthly = monthly.substring(0, dot1);
		}
		else if(action.equals("1"))	//return from check
		{
			emp = request.getAttribute("emp").toString();
			incVals = (ArrayList<Integer>)request.getAttribute("incVals");
			dedVals = (ArrayList<Integer>)request.getAttribute("dedVals");
		}
	}
	catch(Exception e)
	{
		
	}
		

%>

<%
	 String pageName = "newCTC.jsp";
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
	function validate() 
	{
		if(document.getElementById("EMPNO").value == "")
		{
			alert("Please Enter Employee Name OR Number");
			return false;
		}
		else if(document.getElementById("yearly").value=="")
		{
			alert("Please Enter CTC");
			return false;
		}
		else
		{
			document.getElementById("saveEmp").value = document.getElementById("EMPNO").value;
			document.getElementById("saveCTC").value = document.getElementById("monthly").value;
			return true;
		}
		return false;
	}
	
	function checkFlag() 
	{
		var action = document.getElementById("action").value;
		if(action =="1")
		{
			alert("Salary Structure Created Successfully.");
		}
	}
	
	function calMonthly()
	{
		if(document.getElementById("yearly").value!="")
		{	
			if(isNaN(document.getElementById("yearly").value))
			{
				alert("Please Enter only Numeric Value");
			}
			else
				document.getElementById("monthly").value =Math.round(parseInt(document.getElementById("yearly").value)/12);
		}
		else        
			document.getElementById("monthly").value="";
	}
	
	function openOnAmt(trncd)
	{
		var ecat = document.getElementById("EMPNO").value;
		if(ecat!="" && document.getElementById("yearly").value!="")
			var depend = window.showModalDialog("onAmtCTC.jsp?action=getList&ecat="+ecat+"&trncd="+trncd,"Code Dependency","dialogWidth=1000px;dialogHeight=600px;center=yes;addressBar=no;");
		else
			alert("Please Enter Employee Name or Number AND CTC");
	}
	function openSlab(trncd,emptype)
	{
		var ecat = document.getElementById("EMPNO").value;
		if(emptype!="-1")
			ecat="Employee Type:"+emptype;
		if(ecat!="" && document.getElementById("yearly").value!="")
			var depend = window.showModalDialog("SlabCTC.jsp?action=getList&ecat="+ecat+"&trncd="+trncd,"Slab Management","dialogWidth=1000px;dialogHeight=600px;center=yes;addressBar=no;");
		else
			alert("Please Enter Employee Name or Number AND CTC");
	}
	function addNewCode()
	{
		window.showModalDialog("CodeMastCTCMTN.jsp","New Transaction Code","dialogWidth=1000px;dialogHeight=650px;center=yes;addressBar=no;");
		window.location.href="newCTC.jsp";
	}
</script>
<style type="text/css">
.btn{
	width: 40px;
}
.inpText{
	background-color: #C8DBFB;
	font-weight: bold;
}
</style>
</head>
<body style="overflow: hidden;" onLoad="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; height: 650px;" >
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
			  <tr><td align="center">
				<label style="float: left;"><input type="button" value="Add New Salary Code" onclick="addNewCode()"></label><br/>
			  	<form action="NewCTCServlet?action=check" method="post">
					  <table width="600" border="2">
					   <tr bgcolor="#2f747e">
					     <td colspan="2" height="25" style="color: #ffffff;">&nbsp;Enter Employee Name or Number&nbsp;&nbsp;
					       <input name="EMPNO" type="text" id="EMPNO" onClick="showHide()" required="true" value="<%=emp %>" size="50" style="border-style: inset;">
					     	<input type="submit" value="Check">
					     </td>
						</tr>
					   <tr height="25">
					   		<%
					   			if(action.equals("2"))
					   			{
					   		%>
					   				<td align="center">Yearly CTC &nbsp;&nbsp;<input type="text" id="yearly" value="<%=yearly %>" name="yearly" autocomplete="off" style="background-color: #C8DBFB;" onkeyup="calMonthly()"></td>
					   				<td align="center">Monthly CTC &nbsp;&nbsp;<input type="text" id="monthly" value="<%=monthly %>" name="monthly" autocomplete="off" readonly="readonly" style="background-color:#D0E1EB ;"></td>
					   		<%
					   			}
					   			else
					   			{
					   		%>
							   		<td align="center">Yearly CTC &nbsp;&nbsp;<input type="text" id="yearly" name="yearly" autocomplete="off" style="background-color: #C8DBFB;" onkeyup="calMonthly()"></td>
							   		<td align="center">Monthly CTC &nbsp;&nbsp;<input type="text" id="monthly" name="monthly" autocomplete="off" readonly="readonly" style="background-color:#D0E1EB ;"></td>
					  		<%
					   			}
					  		%>
					   </tr>
					   </table>
			   </form>
			   
			   <br/>
			    <form  method="post" action="NewCTCServlet?action=save" onKeyPress="return event.keyCode != 13;" onSubmit="return validate()">
			  	<input type="hidden" name="saveEmp" id="saveEmp">
			  	<input type="hidden" name="saveCTC" id="saveCTC">
			   <table width="100%" border="1">
			   	<tr bgcolor="#2f747e">
			   		<th><h3 style="margin-bottom: 0px;color: #ffffff;">INCOMES</h3></th>
			   		<th><h3 style="margin-bottom: 0px;color: #ffffff;">DEDUCTIONS</h3></th>
			   	</tr>
			   	<tr>
			   		<td style="padding: 3px;" valign="top">
			   			<table border="1" width="100%">
			   				<tr>
			   					<th width="10%">SR. NO.</th>
			   					<th width="35%">Income Code</th>
			   					<th width="18%">Depends On</th>
			   					<th width="18%">Apply Slab</th>
			   					<th width="19%">Value</th>
			   				</tr>
		   				<%int i=1;
			   				for(CodeMasterBean CMB:incomeList)
			   				{
		   				%>
			   					<tr>
				   					<td align="center">&nbsp;<%=i%></td>
				   					<td>&nbsp;<%=CMB.getDISC()%></td>
				   					<td align="center">&nbsp;<input class="btn" type="button" value=" Set " onclick="openOnAmt('<%=CMB.getTRNCD()%>')"></td>
				   					<td align="center">&nbsp;<input class="btn" type="button" value=" Set " onclick="openSlab('<%=CMB.getTRNCD()%>','<%=CMB.getEMPTYPE()%>')"></td>
				   					<%
				   						if(action.equals("1"))
				   						{
				   					%>
				   							<td>&nbsp;<input type="text" class="inpText" id="incAmount<%=i%>" value="<%=incVals.get(i)==0?"":incVals.get(i) %>" name="incAmount<%=i%>"  size="12"></td>
				   					<%
				   						}
				   						else if(action.equals("2"))
				   						{
				   					%>
				   							<td>&nbsp;<input type="text" class="inpText" id="incAmount<%=i%>" value="<%=incTranVals.get(i)==null || incTranVals.get(i).equals("0")?"":incTranVals.get(i) %>" name="incAmount<%=i%>"  size="12"></td>
				   					<%
				   						}
				   						else
				   						{
				   					%>
				   						<td>&nbsp;<input type="text" class="inpText" id="incAmount<%=i%>" name="incAmount<%=i%>" size="12"></td>
				   					<%
				   						}
				   					%>
				   				</tr>
			   			<%
			   					
			   				i++;
			   				}
			   			%>				   						   		
			   			</table>
			   		</td>
			   		<td style="padding: 3px;" valign="top">
			   			<table border="1" width="100%">
			   				<tr>
			   					<th width="10%">SR. NO.</th>
			   					<th width="35%">Deduction Code</th>
			   					<th width="18%">Depends On</th>
			   					<th width="18%">Apply Slab</th>
			   					<th width="19%">Value</th>
			   				</tr>
			   			<%int j=1;
			   				for(CodeMasterBean CMB:deductList)
			   				{
		   				%>
				   				<tr>
				   					<td align="center">&nbsp;<%=j%></td>
				   					<td>&nbsp;<%=CMB.getDISC()%></td>
				   					<td align="center">&nbsp;<input class="btn" type="button" value=" Set " onclick="openOnAmt('<%=CMB.getTRNCD()%>')"></td>
				   					<td align="center">&nbsp;<input class="btn" type="button" value=" Set " onclick="openSlab('<%=CMB.getTRNCD()%>','<%=CMB.getEMPTYPE()%>')"></td>
				   					<%
				   						if(action.equals("1"))
				   						{
				   					%>
				   							<td>&nbsp;<input type="text" class="inpText" id="dedAmount<%=j%>" value="<%=dedVals.get(j)==0?"":dedVals.get(j) %>" name="dedAmount<%=j%>" size="12"></td>
				   					<%
				   						}
				   						else if(action.equals("2"))
				   						{
				   					%>
				   							<td>&nbsp;<input type="text" class="inpText" id="dedAmount<%=j%>" value="<%=dedTranVals.get(j)==null || dedTranVals.get(j).equals("0")?"":dedTranVals.get(j)%>" name="dedAmount<%=j%>" size="12"></td>
				   					<%
				   						}
				   						else
				   						{
				   					%>
				   							<td>&nbsp;<input type="text" class="inpText" id="dedAmount<%=j%>"  name="dedAmount<%=j%>"  size="12"></td>
				   					<%
				   						}
				   					%>
				   				</tr>	
				   		<%
				   			j++;
			   				}
			   			%>					   		
			   			</table>
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