<%@page import="payroll.DAO.datefun"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.Model.TransactionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="java.util.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>


<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">
	function TakeCustId() {
		var EMPNO = document.getElementById("EMPNO").value;
         
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		}
	
</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
<script type="text/javascript">
var no="";
function modifyRec(key)
	{
		no=document.getElementById("empno1").value;
	  document.getElementById("myModal").style.display = 'Block';
	  $("#myModal").load("updateTransaction.jsp?key="+key);
	  $("#myModal").fadeTo('slow', 0.9);
	  parent_disable();
	 /* var no=document.getElementById("empno1").value;
	  window.showModalDialog("updateTransaction.jsp?key="+key,null,"dialogWidth:750px; dialogHeight:250px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	  window.location.href="TransactionServlet?no="+no; */
	}
	
	function addNewTran()
	{
		no=document.getElementById("empno1").value;
		document.getElementById("myModal").style.display = 'Block';
		$("#myModal").load("addNewTransaction.jsp?no="+no);
		$("#myModal").fadeTo('slow', 0.9);
		parent_disable();
		/* var no=document.getElementById("empno1").value;
		window.showModalDialog("addNewTransaction.jsp?no="+no,null,"dialogWidth:850px; dialogHeight:170px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		window.location.href="TransactionServlet?no="+no; */
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
	function postTran()
	{
		var empno = document.getElementById("empno1").value;
		url = "TransactionServlet?action=postTran&empno="+empno;
		
		//xmlhttp.onreadystatechange=callback;
		//xmlhttp.open("POST", url, true);
		//xmlhttp.send();
	}
	
	function callback() 
	{
		if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	if(response == "true")
        	{
        		alert("Records Moved to History Table");
        	}
        	else
        	{
        		alert("Error in Record Moving, Please try again");
        	}
		}
	}
	function parent_disable() 
	{
		jQuery(function() {
			  $("input[type=Submit]").attr("disabled", "disabled");
			  $("input[type=button]").attr("disabled", true);
			  });

		$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
		document.getElementById("myModal").focus();
	}
	
	function closediv()
	{
		document.getElementById("myModal").innerHTML= "";
		document.getElementById("myModal").style.display = "none";
		jQuery(function(){
			  $("input[type=Submit]").removeAttr("disabled");
			  $("input[type=button]").attr("disabled", false);
		});

		$('.nav-outer').css('pointer-events', 'auto');
	}
	
</script>
<%
	String pageName = "tranMaintainences.jsp";
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

<%
String empno="";
 empno =(String)request.getAttribute("empno")==null?"":request.getAttribute("empno").toString();
%>
</head>

<body style="overflow:hidden;" onload="closediv();"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:82%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Salary Transaction</h1>
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
		 
		 <%
		 String action = request.getParameter("action")== null?"":request.getParameter("action");
		 ArrayList<TranBean> trlist = (ArrayList<TranBean>)session.getAttribute("trlist");
		//System.out.println("emp issss "+request.getAttribute("serachemp"));
		 
		 %>
		 			<center>
		 			<%if(action.equalsIgnoreCase("getdata"))
		 				{
		 				 TransactionBean trbn = new TransactionBean();
		 				 trbn = (TransactionBean) request.getAttribute("trbn");
		 				// System.out.println("emp no issss jsp"+request.getAttribute("empno1"));
		 				%>
		 			 <center>
						 <form  action="TransactionServlet?action=trnlist" method="Post" onSubmit="return TakeCustId()" name="form1" id="form1">
<table border="1">
	<tr>
		<td>Enter Employee Name Or Emp-Id<input type="text" name="EMPNO" size="40" 
			id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "> &nbsp;</td>
<td valign="top"><input type="Submit" value="Submit" class="myButton" />
						
						
					  </td>
			
	</tr>
	<tr></tr>
</table>
</form><br>
						 
			<table width="765" id="customers">
				<tr class="alt"><th colspan="4">Salary Transaction </th></tr>
				<tr class="alt">
				  <td width="155">Employee Detail </td>
				  <td colspan="3"><input type="text" size="10" name="empno1" id="empno1"  value="<%=trbn.getEmpno()%>" readonly="readonly"> <input type="text" size="60" readonly="readonly" value="<%=trbn.getEmpname()%>">
				  
				  </td>
				</tr>
				<tr class="alt">
				<td width="155" >SB A/c No</td>
				  <td width="233"><input type="text" name="accno" id="accno" value="<%=trbn.getAccno()%>" readonly="readonly"></td>
				  <td width="125">Department</td>
				<td>
					<select name="Depart" id="Depart" disabled="disabled" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult1 =new ArrayList<Lookup>();
    						LookupHandler lkhp1 = new LookupHandler();
    						getresult1=lkhp1.getSubLKP_DESC("DEPT");
    						for(Lookup lkbean : getresult1)
 							{
 							if(lkbean.getLKP_SRNO()== trbn.getDept())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			   </select>				</td> 
				</tr>
			
			   <tr class="alt">
				 <td>BRANCH </td><td><input type="text" size="15" readonly="readonly" disabled="disabled" value="<%=trbn.getPrj_Code()%>"></td><%-- <td width="233"><select name="select" id="select" disabled="disabled">
                   <option value="0">Select</option>
                   <%
    						
  							  ArrayList<Lookup> resultb=new ArrayList<Lookup>();
    						 EmpOffHandler ekhp1= new EmpOffHandler();
    						 resultb=ekhp1.getGradeBranchList("PRJ");
 							for(Lookup lkbean : resultb)
 							{
     						if(lkbean.getLKP_SRNO()== trbn.getBranch())
     						{
     							
     						%>
                   <option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>
                   <%}
     						else {
     								%>
                   <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
                   <%}
     					 		}%>
                 </select></td> --%>
						<td width="125">Designation</td>
						<td width="232">
						<select name="Desgn" id="Desgn" disabled="disabled" style="width:150px">  
      					  <option value="none">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult =new ArrayList<Lookup>();
    						LookupHandler lkhp = new LookupHandler();
    						getresult=lkhp.getSubLKP_DESC("DESIG");
    						for(Lookup lkbean : getresult)
 							{
 							if(lkbean.getLKP_SRNO()== trbn.getDesg())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
   			        </select>					  </td>  
			</tr>
			<tr class="alt">
			<td>Grade</td>
				<td>
				  <select name="grade" id="grade"disabled="disabled" >  
      				 <option value="none">Select</option>  
    					<%
    					 ArrayList<Lookup> result2 =new ArrayList<Lookup>();
    					 EmpOffHandler  ekhp2=new EmpOffHandler();
    					 result2=ekhp2.getGradeBranchList("GRADE");
 						for(Lookup lkbean : result2)
 						{
     					if(lkbean.getLKP_SRNO()== trbn.getGrade())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			  </select>     			 </td>
			<td>PAN No</td><td><input type="text" name="panno" id="panno" value="<%=trbn.getPanno()%>" readonly="readonly"></td>
				  </tr>
						<tr class="alt">
							<td>PF No</td>
			<td colspan="3"><input type="text" name="pfno" id="pfno" value="<%=trbn.getPfno() %>"readonly="readonly" ></td> 
					</tr>
						
						
				</table>
				</center>
			</form>
			<br>
			<table width="808" align="center" id="customers" >

	<tr>
		<td width="800">
		<table style="margin-right: 0px">

			<tr class="alt" style="font-size:2;">
				<th width="20%">Earnings/Deductions Name</th>
				<th width="10%">Input Amt</th>
				<th width="10%" style="text-align: center;">Adj.Amt</th>
				<th width="10%" style="text-align: center;">Net Amt</th>
				<th width="10%" style="text-align: center;">Cal Amt</th>
				<th width="10%">Last Updated Date</th>
				<th width="13%">Action</th>
			
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 240px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" id="customers">
			<% for(TranBean tb : trlist)
		{
		
		String str=tb.getEMPNO()+":"+tb.getTRNCD()+":"+tb.getSRNO()+":"+tb.getTRNDT();
		%>
			<tr class="row" bgcolor="#FFFFFF">
				<td width="20%"><%=CodeMasterHandler.getCDesc(tb.getTRNCD()) %></td>
				<td width="10%" style="text-align: center;"><%=tb.getINP_AMT() %></td>
				<td width="10%" style="text-align: center;"><%=tb.getADJ_AMT()%></td>
				<td width="10%" style="text-align: center;"><%=tb.getNET_AMT() %></td>
				<td width="10%" style="text-align: center;"><%=tb.getCAL_AMT() %></td>
				<td width="10%"><%=tb.getUPDDT()%></td>
				<td width="10%" ><input type="button" value="Update" onClick="modifyRec('<%=str %>')" class="myButton"></td>

			</tr>
			<%} %>
			
		</table>
		</div>

		</td>
	</tr>
	<tr bgcolor="#929292">
		<td >
		<table id="customers"><tr><td width="125" align="left"><input type="button" value="Post Transaction" onclick="postTran()" class="myButton"></td><td width="654" align="right"><input type="button" value="Add New" onClick="addNewTran()" class="myButton"></td></tr></table>
		</td>
	</tr>
</table>
 				
		 				<%
		 				
		 				}
		 			else
		 			{
		 				%>
						<center>
		 				<form  action="TransactionServlet?action=trnlist"
	method="Post" onSubmit="return TakeCustId()" name="form1" id="form1">
<table border="1">
	<tr>
		<td>&nbsp;Enter Employee Name Or Emp-Id &nbsp;<input type="text" name="EMPNO" size="40"
			id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "> </td>
						<td valign="top"><input type="Submit" value="Submit" class="myButton" /></td>
			
	</tr>
	<tr></tr>
</table>
</form><br>
		 <form >
			<table width="773" id="customers">
				<tr class="alt"><th colspan="4">Salary Transaction </th></tr>
				<tr class="alt">
				  <td width="144">Employee Detail </td>
				  <td colspan="3"><input type="text" size="10" value=""> <input type="text" size="40"></td>
				</tr>
			<tr class="alt">
					<td width="144">SB A/c No</td>
				  <td width="224"><input type="text" ></td> 
				  <td width="119">Department</td>
				<td>
					<select name="Depart" id="Depart" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult1 =new ArrayList<Lookup>();
    						LookupHandler lkhp1 = new LookupHandler();
    						getresult1=lkhp1.getSubLKP_DESC("DEPT");
    						for(Lookup lkbean : getresult1)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			   </select>
						
				</td>
			</tr>
			   <tr class="alt">
				 <td>BRANCH </td><td><input type="text" size="15" readonly="readonly" value=""></td><%-- <td width="224">
				 <select name="branch" id="branch" >  
      					 <option value="0">Select</option>  
    						<%
    						
  							  ArrayList<Lookup> resultb=new ArrayList<Lookup>();
    						 EmpOffHandler ekhp1= new EmpOffHandler();
    						 resultb=ekhp1.getGradeBranchList("PRJ");
 							for(Lookup lkbean : resultb)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 	<%} %>	
     			 </select>
				 
				 </td> --%>
						<td width="119">Designation</td>
						<td width="266">
						<select name="Desgn" id="Desgn" style="width:80px">  
      					  <option value="none">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult =new ArrayList<Lookup>();
    						LookupHandler lkhp = new LookupHandler();
    						getresult=lkhp.getSubLKP_DESC("DESIG");
    						for(Lookup lkbean : getresult)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			      </select>
						
					  </td>  
				
			</tr>
			<tr class="alt">
			<td>Grade</td>
				<td>
				  <select name="grade" id="grade" >  
      				 <option value="none">Select</option>  
    					<%
    					 ArrayList<Lookup> result2 =new ArrayList<Lookup>();
    					 EmpOffHandler  ekhp2=new EmpOffHandler();
    					 result2=ekhp2.getGradeBranchList("GRADE");
 						for(Lookup lkbean : result2)
 						{
     					%>
      					<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     				 	<%	}%>
     			  </select>
     			 </td>
			<td>PAN No</td><td><input type="text"></td>
						
						
					</tr>
						<tr class="alt">
							<td>PF No</td><td colspan="3"><input type="text" ></td> 
				
     			
					</tr>
						
						<tr class="alt">
						
						</tr>
				</table>
				
			</form>
			<br>
			<form>
			<table width="787" align="center" id="customers">

	<tr>
		<td width="737">
		<table>

			<tr class="alt" style="font-size:2;">
				<th width="179">Earnings/Deductions Name</th>
				<th width="93">Input Amt</th>
				<th width="91">Adj.Amt</th>
				<th width="83">Net Amt</th>
				<th width="83">C/F</th>
				<th width="134">Last Updated Date</th>
			</tr>
		</table>		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 122px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" id="customers">
		</table>
		</div>		</td>
	</tr>
	<tr bgcolor="#1F5FA7">
		<td width="800" align="right">&nbsp;</td>
	</tr>
</table>
</form>
</center>
		 				
		 				<%
		 			}
		 				%>
		 			
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