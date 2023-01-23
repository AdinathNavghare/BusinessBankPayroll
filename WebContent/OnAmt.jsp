<%@page import="java.util.List"%>
<%@page import="payroll.Model.OnAmtBean"%>
<%@page import="payroll.DAO.OnAmtHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.StringTokenizer"%>
 <%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
	<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		
	
<script src="js/filter.js"></script>
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<style type="text/css">

<!--
body,td,th {
	font-family: Georgia;
	font-size: 14px;
}
-->
</style>
 <script>
	jQuery(function() {
          $("#empcat").autocomplete("lookuplist.jsp");
	});
</script>
 
  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>On Amount Dependencies Management</title>
<%
	String pageName = "OnAmt.jsp";
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
	LookupHandler lkph = new LookupHandler();
	CodeMasterHandler CMH=new CodeMasterHandler();
	OnAmtHandler OAH = new OnAmtHandler();
	
	ArrayList<Lookup> ECAT = new ArrayList<Lookup>();
	ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();
	ArrayList<OnAmtBean> OALIST=new ArrayList<OnAmtBean>();
	
	ECAT=lkph.getSubLKP_DESC("ET");
	TRNCODE=CMH.getAllCDMASTList();
	
	int empcat=0;
	int eno=0;
	int trncd=0;
	String[] emp=new String[3];
	String ecat="";
	String action =request.getParameter("action");
	
	try
	{
		if(action.equalsIgnoreCase("getList"))
		{
				emp=request.getParameter("ecat").split(":");
				ecat=request.getParameter("ecat");
				session.setAttribute("empcat", ecat);
				
			    trncd=Integer.parseInt(request.getParameter("trncd"));
				//empcat=Integer.parseInt(ecat[0]);
				empcat=Integer.parseInt(emp[1]);
				OALIST=OAH.getOnAmtList(empcat,trncd);
		}
		if(action.equalsIgnoreCase("getList2"))
		{
				eno = Integer.parseInt(request.getParameter("elist"));
			    trncd=Integer.parseInt(request.getParameter("trncd"));
				OALIST=OAH.getOnAmtList(eno,trncd);
		}
		
	}
	catch(Exception e)
	{
		
	}
%>

<script type="text/javascript">
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

	function getList()
	{
		var ecat=document.getElementById("empcat").value;
		var trncd=document.getElementById("select.trncd").value;
		//window.showModalDialog("Lookup.jsp","Lookup Entry","dialogWidth=300px;dialogHeight=300px;center=yes;addressBar=no;");
		window.location.href="OnAmt.jsp?action=getList&ecat="+ecat+"&trncd="+trncd;
	}

	function deleteRow(key)
	{
		var flag=confirm("Do you really want to Delete this row?");
		if(flag)
		{
			//alert("Deleted "+key);
			url="OnAmtServlet?action=del&key="+key;
			xmlhttp.onreadystatechange=callback;
    		xmlhttp.open("POST", url, true);
    		xmlhttp.send();
			
		}
	}

	function callback()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	if(response=="true")
        	{
				alert("Record Deleted");
				getList();
            }
        	else
        	{
        		alert("Error occured while deleting Record");
            }        	
    	}
	}
	
	function validate()
	{ 
		if( document.getElementById('select.trncd').value =="select")
		{
			alert("Please Select Transaction Code");
			document.getElementById('select.trncd').focus();
			return false;
			
			}
		if( document.getElementById('select.addTrncd').value =="select")
		{
			alert("Please Select on Amount Transaction Code");
			return false;
			
			}
		if( document.getElementById('amtType').value =="Select")
		{
			alert("Please Select on Amount Type");
			return false;
			
			} 
		document.getElementById('emplist').value=numList;	
		if( document.getElementById('empcat').value =="" && numList == "")
		{
			alert("Please Select employee(s) !");
			return false;
				
			}
		if(!numList == ""){
			var check = prompt("Are you really sure ? Then type Yes ");	
			if(check == "yes" || check == "Yes" || check == "YES"){
				return true;
			} else {
				alert("Sorry ! Wrong Input ");
				return false;
			}
		}  
	}
	function rset()
	 {
		 
		 document.getElementById('empcat').value = '';
		 
	 }
	function getFilt()
	{
		getFilter('onAmt',new Date());
	}
	
</script>
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>
</head>
<body onLoad="hideDiv()" style="overflow:hidden">
<div><%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%></div>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>On Amount</h1>
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
<h3>On Amount Dependencies Management</h3>
<form action="OnAmtServlet?action=addnew" method="post" name="add" onSubmit="return validate()">
<input type="hidden" name="emplist" id="emplist">
<input type="hidden" value="OnAmt.jsp" id="pageName" name="pageName">
<table width="100%" id="customers">
<tr ><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td></tr>
<tr><td align="center" valign="middle">
<table  id="customers"  >
<tr id="alt">
<td  width="20%" height="20" align="center" valign="middle" > <!-- Select Multiple Employee's --> Employee Category</td>
<td  width="235" height="20" align="center" valign="middle" colspan="2"><!-- Employee Category -->Transaction Codes Description</td>
<!-- <td width="304" align="center" valign="middle" >Transaction Codes Description</td>
 --><td width="45" rowspan="2" bgcolor="#FFFFFF"><label>
  <input type="button"  class="myButton" name="go" value="Search" onClick="getList()" style="margin-bottom :-4px;">
</label></td>
</tr>
<tr>
	<!-- <td height="20" align="center" bgcolor="#FFFFFF" width="20%">
		<input type="button" value="Select" onclick="getFilt()" class="myButton">&nbsp;&nbsp;
		<input type="button" value="Clear" onclick="cancelAll()" class="myButton"> 
	</td> -->
  <td height="20" align="left" bgcolor="#FFFFFF" width="40%"> 
 
 <input type="text" name="empcat" id="empcat" size="40" onClick="rset()" value="<%=session.getAttribute("empcat")==null?"":session.getAttribute("empcat") %>" title="Select Employee ">
 <!-- <input type="text" name="empcat" id="empcat" size="35" onClick="rset()" title="Select Employee "> -->
   </td>
  <td bgcolor="#FFFFFF" valign="middle"> 
    <select name="select.trncd" id="select.trncd">
    <option value="select" selected>Select</option>
    			<%
						for (CodeMasterBean temp1:TRNCODE)
						{
							
							if (trncd==temp1.getTRNCD())
							{
				 %>
    							<option value="<%=temp1.getTRNCD()%>" selected><%=temp1.getDISC()%></option>
   				 <%
							} 
							else
							{
				%>
    							<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
    			<%
							}

						}
				%>
    
    </select>
  </td>
  </tr>
</table>
<!-- <div style="height: 80px; overflow-y: auto; width:90%;" id="displayDiv"></div>
 --> </br>
    <div style="height: 150px; overflow-y: auto; width:100%;" id="div1" >
     <table border="0" bgcolor="#CCCCCC" width="100%">
    <tr bgcolor="#999999">
      <th width="50"  align="center"   scope="col">Code</th>
      <th width="200"  align="center" scope="col">Description</th>
      <th width="200"  scope="col">Depends On</th>
      <th >Amount Type</th>
      <th width="70"  scope="col">&nbsp;</th>
    </tr>
     <%String desc="";
     	int maxsr=0;
  		if (OALIST.size() > 0)
  		{
			for (OnAmtBean test1 :OALIST )
			{
  				String sub=test1.getEMP_CAT()+"-"+test1.getTRNCD()+"-"+test1.getSRNO();
  				desc=CodeMasterHandler.getCDesc(test1.getTRNCD());
  				maxsr=test1.getSRNO();
  	%>
    <tr class="row" bgcolor="#FFFFFF" >
      <td height="26"  align="center" valign="middle" scope="col"><%=test1.getTRNCD()%></td>
      <td  align="center" valign="middle" scope="col"><%=CodeMasterHandler.getCDesc(test1.getTRNCD())%></td>
      <td align="left" valign="middle"  scope="col" ><%=CodeMasterHandler.getCDesc(test1.getONAMTCD())%></td>
      <td align="center" valign="middle" ><%=test1.getAMT_TYPE() %></td>
      <td align="center" valign="top"  scope="col" ><input type="button" class="myButton" value="Delete" onClick="deleteRow('<%=sub%>')" 
      style="margin-top: 0px;margin-bottom: 0px;"/></td>
    </tr>
    <%    		
			}
  		}
  	%>
  </table>
</div>
 
<tr  class="alt" ><td align="center" valign="middle">
					<label>[I:- Input Amount] &nbsp; [C:- Calculated Amount]</label></td></tr>
<tr>
  <td align="center" valign="top">

Add OnAmount Dependency
		<table  border="1" width="100%">
          <tr align="center" valign="middle">
            <th width="302" align="center" scope="col">Description</th>
            <th width="308" scope="col">Select On which It is Dependent ? </th>
            <th>Select Amount Type</th>
            </tr>
          <tr align="left" valign="middle">
            <td><%=CodeMasterHandler.getCDesc(trncd)%></td>
            <td valign="middle"> 
              <select name="select.addTrncd" id="select.addTrncd">
    		     <option value="select" selected>Select</option>
    			<%
						for (CodeMasterBean temp1:TRNCODE)
						{
				 %>
    							<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
   				<%
						}
				%>
    
    		</select>
            </td>
            <td>
            <select name="amtType" id="amtType">
            	<option value="C" selected="selected">Select</option>
            	<option value="I" >Input Amount</option>
            	<option value="C" >Calculated Amount</option>
            </select>
            </td>
            </tr>
        </table>
		<p>
    <label>
  <input type="submit" name="save"  class="myButton" id="save"  value="Save" />
  </label>
  <label>
  <input type="reset" name="cancel" class="myButton"  value="Cancel" />
  </label>
  <input type="hidden" value="<%=maxsr+1%>" name="maxsr" />
</p>
<br/>

</td></tr>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>
</table>

 </form>
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