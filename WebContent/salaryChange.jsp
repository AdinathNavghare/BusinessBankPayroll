<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="org.w3c.dom.CDATASection"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Core.ReportDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; Salary Change</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
function seturlparam()
{
	
var date=document.getElementById('date').value;
var proj=document.getElementById('proj').value;
var range=document.getElementById('rng').value;
//var range2=document.getElementById('rng2').value;
var url="?action=details&proj="+proj+"&date="+date+"&rng="+range;
return url;
}
function getClose()
{
	//window.close();
	var param=seturlparam();
	window.location.href="SalaryDetails.jsp"+param;
}
function validate(){
	var chk = confirm("Are you sure ?");
	if(chk==true){
		return true;
	}else{
		return false;
	}
}
function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='.1234567890';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {
	    return true;
	  }
	}

function checkVal()
{
	var flag = document.getElementById("chek").value;
	var param=seturlparam();
	if(flag == 1)
	{
		alert("Record Updated Successfully!");
		//window.close();
		window.location.href="SalaryDetails.jsp"+param;
	}
	else if(flag==2)
	{
		alert("Error in  Updating Record");
		//window.close();
		window.location.href="SalaryDetails.jsp"+param;
	}
	else if(flag==3)
	{
		alert("No Change !");
		//window.close();
		window.location.href="SalaryDetails.jsp"+param;
	}
}


</script>
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
	else if(action.equalsIgnoreCase("nochange"))
	{
		check=3;	// Error Record not inserted
	}
}
catch(Exception e)
{
	
}
String date = request.getParameter("date")==null?"":request.getParameter("date");
String empno = request.getParameter("eno")==null?"0":request.getParameter("eno");
String action = request.getParameter("action")==null?"details":request.getParameter("action");
String proj = request.getParameter("proj")==null?"all":request.getParameter("proj");
String rng = request.getParameter("rng")==null?"0":request.getParameter("rng");
LookupHandler lkp = new LookupHandler();
ArrayList<TranBean> list = new ArrayList<TranBean>();
list = TranHandler.getSalChange(empno, date);
session.setAttribute("list", list);
int count=0;
for(TranBean tbn : list) {
	   if(tbn.getTRNCD()>200 && tbn.getTRNCD()<300 && tbn.getTRNCD()!=999){
		   count++;
	   }
}
session.setAttribute("deduct_counter", count);
double totalEar = 0;
double totalDed = 0;
double netPay = 0;

%>
<script type="text/javascript">
function eval_Deduct()
{
	
	var total_in=parseFloat(document.getElementById("total_income").value);
	
	//var total_deduct=parseFloat(document.getElementById("total_deduct").value);
	var deduct=0;
	//var val = document.getElementsByName("deduct");
	var count=<%=session.getAttribute("deduct_counter")==null?0:Integer.parseInt(session.getAttribute("deduct_counter").toString())%>;
	
	for(var i=0;i<count;i++)
	{
		var x=parseFloat(document.getElementById("deduct"+i).value);
		if(isNaN(x))
			{
			x=0;
			}
		deduct=eval(deduct)+eval(parseFloat(x));
		
	}
	
	document.getElementById("total_deduct").value=(deduct).toFixed(1);
	document.getElementById("net_pay").value=(eval(total_in)-eval(deduct)).toFixed(1);
	
}
</script>
</head>
<body style="overflow: auto;" onLoad=" checkVal()">
<img src='images/Close.png' style='float:right;' title='Remove' onclick="getClose()"><br>
	<br />
	
	<center>
		<form action="TransactionServlet?action=updateSal" method="post" onsubmit="return validate()">
			<div style="height: 450px; overflow-y: scroll; width: auto" id="div1" align="center">
			<table border="1" id="customers">

				<tr class="alt">
					<th colspan="4">Salary Information</th>
				<tr class="alt">
					<td width="70">Employee Name :</td>
					<td width="200"><input type="text" id="empnm" size="35" name="empnm" readonly="readonly" value="<%=lkp.getLKP_Desc("ET", Integer.parseInt(empno))%>"/></td>
					<td width="70">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Salary Month :</td>
					<td width="200"><input type="text" id="month" name="month" size="12" readonly="readonly" value="<%=date %>" /></td>
				</tr>
				<tr class="alt">
					<th colspan="2">Earnings</th>
					<th colspan="2">Deductions</th>
				<tr class="alt">
					<td colspan="2" width="350">
						<table>
							
							<% for(TranBean tb : list) {
								if(tb.getTRNCD()>100 && tb.getTRNCD()<199) {%>
							<tr>
								<td width="250"><%=CodeMasterHandler.getCDesc(tb.getTRNCD()) %></td>
								<td width="100"><input type="text" value="<%=tb.getCAL_AMT() %>" readonly="readonly" /></td>
							</tr>
							<%} 
							}	
							%>
							
						</table>
					</td>
					<td colspan="2" width="350">
						<table>
							
							<%
							int index=0;
							for(TranBean tb : list) {
								if(tb.getTRNCD()==201 || tb.getTRNCD()==202 || tb.getTRNCD()==221) {
									
								%>
							<tr>
								<td width="250"><%=CodeMasterHandler.getCDesc(tb.getTRNCD()) %></td>
								<td width="100"><input type="text" name="deduct<%=index%>" id="deduct<%=index%>" value="<%=tb.getCAL_AMT() %>" readonly="readonly" /><!-- </td> -->
								<input type="hidden" name="dedcode<%=index++%>"  value="<%=tb.getTRNCD() %>" readonly="readonly" /></td>
							</tr>	
							<%	}  else if(tb.getTRNCD()>200 && tb.getTRNCD()<300 && tb.getTRNCD()!=999) {
								
							%>
							 <tr>
								<td width="250"><%=CodeMasterHandler.getCDesc(tb.getTRNCD()) %></td>
								<td width="100"><input type="text" name="deduct<%=index%>" id="deduct<%=index%>" value="<%=tb.getCAL_AMT() %>" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="eval_Deduct()"/>
												<input type="hidden" name="dedcode<%=index++%>"  value="<%=tb.getTRNCD() %>" readonly="readonly" /></td>
							</tr> 
							<%} if(tb.getTRNCD()==999) {
								totalEar = tb.getINP_AMT();
								totalDed = tb.getCAL_AMT();
								netPay = tb.getNET_AMT();
								}
							}
							
							%>
							
						</table>
					</td>
				</tr>
				<tr class="alt">
					<td width="150">TOTAL INCOME :</td>
								<td width="80"><input type="text" value="<%=totalEar %>" id="total_income" readonly="readonly" /></td>
					<td width="150">TOTAL DEDUCTION :</td>
								<td width="80"><input type="text" value="<%=totalDed%>"  name="total_deduct" id="total_deduct" readonly="readonly" /></td>
				</tr>	
				<tr class="alt">
					<td>NET PAY :</td>
					<td colspan="3"><input type="text" value="<%=netPay %>" name="net_pay" id="net_pay" readonly="readonly" /><input type="hidden" name="empno" value="<%=empno%>"></td>
				</tr>
				<tr class="alt">
					<td colspan="4" align="center">
						<input type="submit" id="" name="" value="SAVE" readonly="readonly" /> &nbsp;&nbsp;&nbsp;
						<input type="button" id="cancel" name="cancel" value="Cancel" onclick="getClose()" />
					</td>
				</tr>
			</table>
		</form></div>
	</center>
<input type="hidden" value="<%=check%>" name="chek" id="chek">
<input type="hidden" value="<%=date%>" name="date" id="date">
<input type="hidden" value="<%=proj%>" name="proj" id="proj">
<input type="hidden" value="<%=rng%>" name="rng" id="rng">
</body>
</html>
