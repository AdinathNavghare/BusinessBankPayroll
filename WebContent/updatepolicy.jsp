<%@page import="payroll.Model.PolicyBean"%>
<%@page import="payroll.DAO.PolicyHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.TranHandler"%>
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
<title></title>
<style type="text/css">

body,td,th {
	font-family: Georgia;
	font-size: 14px;
	color:#000000;
}

</style>


<%

int check=0;
PolicyHandler ph = new PolicyHandler();
PolicyBean pb = new PolicyBean();
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record Modified
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not Modified
		}
		if(action.equalsIgnoreCase("update"))
		{
			String[] str1=request.getParameter("str").split(":");
			int empno=Integer.parseInt(str1[0]);
			int srno=Integer.parseInt(str1[1]);
			 pb = ph.getSinglePolicy(empno, srno);
		}
	}
	catch(Exception e)
	{
	}
%>
<script type="text/javascript">

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		if(flag == 1)
		{
			alert("Record Updated Successfully!");
			window.close();
		}
		else if(flag==2)
		{
			alert("Error in Modification of Record");
		}
		datePick();
	}


</script>
</head>
<body onLoad="checkVal()">
<form id="f1" name="f1" action="PolicyMasterServlet?action=updatepolicy" method="post" onSubmit="return validate()">
<table width="672" border="0" align="center" id="customers">
  <tr class="alt">
    <th colspan="5" align="center">Edit Policy </th>
  </tr>
  <tr class="alt"> <td>Employee Number</td><td colspan="4"><input name="empno" id="empno" type="text" readonly="readonly" value="<%=pb.getEMPNO()%>"></td></tr>
  <tr class="alt">
    <td width="156"  valign="middle" >Policy Name </td>
    <td width="170" bgcolor="#FFFFFF">
    <input name="polyname" id="polyname" type="text" value="<%=pb.getPOLICY_NAME()%>">
	
	</td>
    <td width="25" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="141"  bgcolor="#FFFFFF">Policy Type</td>
    <td width="158" bgcolor="#FFFFFF">
     <input name="polytype" type="text"  id="polytype" value="<%=pb.getPOLICY_TYPE()%>" maxlength="100"></td>
  
  
  <tr class="alt">
    <td width="156" valign="middle" >Policy Date</td>
    <td width="170" bgcolor="#FFFFFF">
	<input type="text" name="polydate" id="polydate" readonly="readonly" value="<%=pb.getPOLICY_DATE()%>">
	<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('polydate', 'ddmmmyyyy')" />
	
	
	</td>
    <td width="25" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="141"  bgcolor="#FFFFFF">Policy Amount </td>
    <td width="158" bgcolor="#FFFFFF"><input name="polyamnt" type="text" id="polyamnt" onBlur="checkNumeric(this.id)" maxlength="10" value="<%=pb.getPOLICY_AMOUNT()%>"> </td>
  </tr>
 
  <tr class="alt">
    <td  bgcolor="#FFFFFF">Premium Amount</td>
    <td bgcolor="#FFFFFF"><input name="preamnt" type="text" id="preamnt" value="<%=pb.getPREMIUM_AMOUNT() %>" onBlur="checkNumeric(this.id)" maxlength="10" > </td><td bgcolor="#CCCCCC">&nbsp;</td>
      <td width="141"  bgcolor="#FFFFFF" >Premium Status</td>
      <td><input type="text" name="prestatus" id="prestatus" value="<%=pb.getPREMIUM_STATUS()%>" ></td>
  </tr>
  <tr class="alt">
    <td  bgcolor="#FFFFFF">Maturity Date</td>
    <td bgcolor="#FFFFFF"><input type="text" name="matdate" id="matdate" readonly="readonly" value="<%=pb.getMATURITY_DATE() %>" ><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('matdate', 'ddmmmyyyy')" /> </td><td bgcolor="#CCCCCC">&nbsp;</td>
      <td width="141"  bgcolor="#FFFFFF" >Maturity Amount</td>
      <td><input name="matamnt" type="text" id="matamnt" onBlur="checkNumeric(this.id)" maxlength="10" value="<%=pb.getMATURITY_AMOUNT() %>" /></td>
  </tr>
  <tr class="alt"><td>Deduction Month</td>
  <td >
  <select name="dedmonth" id="dedmonth">
  <option value="0">Select Month</option>
  <option value="1">January</option>
  <option value="2">February</option>
  <option value="3">March</option>
  <option value="4">April</option>
  <option value="5">May</option>
  <option value="6">June</option>
  <option value="7">July</option>
  <option value="8">August</option>
  <option value="9">September</option>
  <option value="10">October</option>
  <option value="11">November</option>
  <option value="12">December</option>
  </select>
	</td>
	<td></td>
	<td>Insurance Company</td>
	<td><input type="text" name="compname" id="compname" value="<%=pb.getINS_COMP_NAME() %>" ></td>
	</tr>
 
  <tr align="center"  class="alt">
    <td colspan="5" valign="middle">
      <label><input type="submit" name="save" value="Save" />
      </label>
      <label><input type="button" name="clear" value="Cancel" onClick="getClose()"/>
      </label>
    </td>
    </tr>
</table>
<input type="hidden" name="srno" value="<%=pb.getSRNO()%>">
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</form>


</body>
</html>
