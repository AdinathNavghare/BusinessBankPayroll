<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page import="java.util.*"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/TransactionType.js"></script>

<title></title>
<style type="text/css">

body,td,th {
	font-family: Georgia;
	font-size: 14px;
	color:#000000;
}

</style>


<script type="text/javascript">

jQuery(function() 
	{
          $("#EMPNO1").autocomplete("list.jsp");
	});

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		
		if(flag == 1)
		{
			alert("Record inserted Successfully!");
			window.close();
		}
		else if(flag==2)
		{
			alert("Error in  inserting Record");
			//window.close();
		}
		else if(flag==3)
		{
			alert("Record Already Present");
			window.close();
		}
		datePick();
	}
	

			
		
	 
	function validate()
	{
  
	
	if (document.f1.trandate.value =="") {
			alert("please enter trandate");
			document.f1.trandate.focus();
			return false;

		}

		if (document.f1.trancd.value =="0" && document.f1.trancd1.value =="Default") {
			alert("please select transaction code !");
			document.f1.trancd.focus();
			return false;
		}
		if (document.f1.trancd.value !="0" && document.f1.trancd1.value !="Default") {
			alert("please select only one transaction code !");
			document.f1.trancd.focus();
			return false;
		}
		if(document.f1.inpamnt.value =="")
		{
			alert("please enter input amount");
			document.f1.inpamnt.focus();
			return false;
		}
		if(document.f1.cryfwd.value =="")
		{
		alert("please enter carryforward");
		document.f1.cryfwd.focus();
		return false;
		}
	}
</script>


<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

Date date = new Date();
String currentdate = dateFormat.format(date); 

String empno = request.getParameter("no")==null?"":request.getParameter("no");

%>
<%


int check=0;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record inserted
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not inserted
		}
		else if(action.equalsIgnoreCase("present"))
		{
			check=3;	// Error Record not inserted
		}
	}
	catch(Exception e)
	{
		
	}
%>
</head>
<body onLoad=" checkVal()">
<input type="hidden" name="trantype" id="trantype" value="">
<br/>
<form id="f1" name="f1" action="TransactionServlet?action=addnewTran" method="post" onSubmit="return validate()">
<table width="700" border="0" align="center" id="customers">
  <tr class="alt">
    <th colspan="5" align="center">Add new Transaction </th>
  </tr>
  <tr class="alt">
    <td width="139" align="right" valign="middle" >Transaction date </td>
    <td width="194" bgcolor="#FFFFFF"><input name="trandate" id="trandate" type="text" value="<%=currentdate%>" readonly="readonly">
	
	</td>
    
    <td width="129" align="right" bgcolor="#FFFFFF">EMPNO</td>
    <td width="165" bgcolor="#FFFFFF"> <input type="text" name="EMPNO"  id="EMPNO" value="<%=empno%>" readonly="readonly"></td>
  
  
  <tr class="alt">
  	<td width="129" align="right" bgcolor="#FFFFFF">Earning Codes</td>
    <td width="165" bgcolor="#FFFFFF">
    <select name="trancd">
	<option value="0" selected="selected">select</option>
	<%
	  ArrayList<CodeMasterBean> list=new ArrayList<CodeMasterBean>();  
	  CodeMasterHandler codeHandler=new CodeMasterHandler();
	  list=codeHandler.gettrancd();
	  for(CodeMasterBean bean:list)
	  {
		  if(bean.getTRNCD()<200 || bean.getTRNCD() == 999){ 	  
	  %>
	  
	  <option value="<%=bean.getTRNCD()%>"><%=bean.getDISC() %></option>
	  <%	} 
		  }%>
    </select>
    </td>
     
    <td width="139" align="right" valign="middle" >Deduction Codes </td>
    <td width="194" bgcolor="#FFFFFF">
	<select name="trancd1">
	<option value="Default" selected="selected">select</option>
	<%
	  for(CodeMasterBean bean:list)
	  {
		  if(bean.getTRNCD()>=200 && bean.getTRNCD() != 999){
	  %>
	  <option value="<%=bean.getTRNCD()%>"><%=bean.getDISC() %></option>
	  <%	} 
		  }%>
    </select>
	</td>
   
    
  </tr>
 
  <tr class="alt">
  	<td width="129" align="right" bgcolor="#FFFFFF">Input Amount </td>
    <td width="165" bgcolor="#FFFFFF"><input type="text" name="inpamnt" id="inpamnt"> </td>
    
    <td align="right" valign="middle" bgcolor="#FFFFFF">Carry Forward</td>
    <td bgcolor="#FFFFFF">
    <select name="cryfwd" id="cryfwd">
    <option value="n">No</option>
    <option value="*">Yes</option>
    
    </select>
    
    
    <!-- <input type="text" name="cryfwd" id="cryfwd" > </td> -->
      
  </tr>
 
  <tr align="center"  class="alt">
    <td colspan="5" valign="middle">
      <label><input type="submit" class="myButton" name="save" value="Save" />
      </label>
      <label><input type="button" name="clear" class="myButton" value="Cancel" onClick="getClose()"/>
      </label>
    </td>
    </tr>
</table>

</form>
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</body>
</html>


