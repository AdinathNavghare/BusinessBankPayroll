<%@page import="org.apache.poi.hssf.record.MMSRecord"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.TaxReportBean"%>
<%@page import="payroll.Core.YearlyPDFReport"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="payroll.DAO.Form16Handler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

 function taxCompute(){ 
	var f = parseInt(document.getElementById("flag").value);
	if (f ==1) {
		alert("Income tax updated Successfully");
	
	}
	if (f == 2) {
		alert("some problem in updating income tax");
	}
 }
 
 function  check() {
	var check=confirm("Are you sure to post the TDS?");
	if(check)
		return true;
	else
		return false;
}
</script>



<%
try{ 
String empno="";
String action = "";
int flag=-1;
flag=Integer.parseInt(request.getParameter("flag")==null?"0":request.getParameter("flag"));
System.out.println("flAg"+flag);
Form16Handler f16 = new Form16Handler();
LookupHandler lkp = new LookupHandler();
TaxReportBean taxRepBean = new TaxReportBean();
Map<Integer,Integer> map = new HashMap<Integer, Integer>(); 
empno =(String)session.getAttribute("empno1")==null?"":session.getAttribute("empno1").toString();
action = request.getParameter("action")== null?"":request.getParameter("action");
String year="";
if(!empno.equals(""))
	 year=session.getAttribute("year").toString();
map = f16.taxComputation(empno,year);
String month = TranHandler.currentSalMonth(empno);
String []mnth = month.split("-");
//System.out.println("out"+mnth[1]);
int Month = Integer.parseInt(mnth[1]);
if(Month==1) {
	Month = 2;
} else if (Month==2) {
	Month = 1;
} else if (Month==3) {
	Month = 0;
} else {
	Month = 15 -Month;
}
int pt = 2500;
float basic = (Float)session.getAttribute("basic");
float hra = (Float)session.getAttribute("hra");
float tds = (Float)session.getAttribute("tds");
double income = (Double)session.getAttribute("totalInc");
%>




</head>
<body style="overflow: hidden;" onload="taxCompute()"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:100%; ">
<!-- start content -->
<div id="content">

	<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<%if(empno.equals("") || empno.equals(null)){ %>
			<div class="step-light-left"><a href="salaryStructure.jsp">ITR Wages</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="Form16Entry.jsp" > Form 16</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-dark-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div>
			<%} else { %>
			<div class="step-light-left"><a href="salaryStructure.jsp?action=salstruct">ITR Wages</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="Form16Entry.jsp?action=getdata" > Form 16</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-dark-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div>
            <%} %>
	</div>

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
			<font size="3" style="font-weight: bold;">Financial Year:<%=session.getAttribute("year")%>-
            <%=Integer.parseInt(session.getAttribute("year").toString())+1%> </font>
           <br/>
			<h3> Employee Name :<font size="3" style="font-weight: bold;"> <%=lkp.getLKP_Desc("ET", Integer.parseInt(empno))%>
			</font> </h3>
		<br/>
		<% float tax=0.0f;
		if(map.isEmpty()) {
			int net = (int)income-pt;
			if(year.equalsIgnoreCase("2016")){
			tax = ((net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0)+((net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0))+((net-1000000)>0?((net-1000000)*3/10):0)-(net>250000?(net<500000?5000:0):0);
			}
			else
			{
			 tax = ((net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0)+((net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0))+((net-1000000)>0?((net-1000000)*3/10):0)-(net>250000?(net<500000?2000:0):0);
			}
		%>		
		<form action="ITServlet?action=create" method="post" name="form" id="form" onsubmit="return check()"  >
		
		<table id="customers" align="right" width="550" >
		<tr class="alt"> <th>Tax Computation </th></tr>
		<tr><td>
		<div style="height: 400px; width:507px; overflow-y: scroll;">
		<table  id="customers">
		<tr bgcolor="#929292" ><td colspan="4"> <font color="white"><h4> Tax Computation</h4></font></td></tr>
 		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Salary</td>
			<td><input type="text" size="10" name="tgsa" id="tgsa" value="<%=(int)income %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Profession Tax</td>
			<td><input type="text" size="10" name="tpt" id="tpt" value=" <%=pt %>" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exemptions under section 10 & 17</td>
			<td><input type="text" size="10" name="teus" id="teus" value="0 " readonly="readonly" ></td>
		</tr>
		
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Salary after section 10 & 17 exemptions</td>
			<td><input type="text" size="10" name="tgse" id="tgse" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Accommodation & car Perquisites</td>
			<td><input type="text" size="10" name="tacp" id="tacp" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "salaries"</td>
			<td><input type="text" size="10" name="tics" id="tics" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "House/Property"</td>
			<td><input type="text" size="10" name="tichp" id="tichp" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interest on housing loan(for tax exemption)"</td>
			<td><input type="text" size="10" name="tichl" id="tichl" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "Capital Gains" at nominal rate</td>
			<td><input type="text" size="10" name="ticnr" id="ticnr" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "Other sources"</td>
			<td><input type="text" size="10" name=" " id=" " value="0 " readonly="readonly"  ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Total Income</td>
			<td><input type="text" size="10" name="tgti" id="tgti" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deduction under chapter VI-A</td>
			<td><input type="text" size="10" name="tducvi" id="tducvi" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deduction under sec-80c</td>
			<td><input type="text" size="10" name="tduc" id="tduc" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Net taxable income</td>
			<td><input type="text" size="10" name="tnti" id="tnti" value="<%=net %> " readonly="readonly" ></td>
		</tr>
		</table>
		
				<table id="customers">
				<tr bgcolor="#929292" ><td><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax Slabs</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Tax rate</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Appl Amt</h4></font></td>	
		<td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Balance</h4></font></td>	<td><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax</h4></font></td>	
		
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;00000-250000</td>
			<td><input type="text" size="10" name="tts1" id="tts1" value="0%" readonly="readonly"  ></td>
						<td><input type="text" size="10" name="tts2" id="tts2" value="<%=net>250000?250000:net %> " readonly="readonly"  ></td>
			
						<td><input type="text" size="10" name="tts3" id="tts3" value="<%=(net-250000)>0?(net-250000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts4" id="tts4" value="0 " readonly="readonly" ></td>
			
		</tr >
		<tr class="alt" >
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;250001-500000</td>
			<td><input type="text" size="10" name="tts5" id="tts5" value="10%" readonly="readonly" ></td>
						<td><input type="text" size="10" name="tts6" id="tts6" value="<%=(net-250000)>0?((net-250000)>250000?250000:(net-250000)):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts7" id="tts7" value="<%=(net-500000)>0?(net-500000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts8" id="tts8" value="<%=(net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0 %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;500001-1000000</td>
			<td><input type="text" size="10" name="tts9" id="tts9" value="20%" readonly="readonly"></td>
						<td><input type="text" size="10" name="tts10" id="tts10" value="<%=(net-500000)>500000?500000:(net-500000>0?net-500000:0) %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts11" id="tts11" value="<%=(net-500000)>500000?(net-1000000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts12" id="tts12" value="<%=(net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0) %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>1000000</td>
			<td><input type="text" size="10" name=" " id=" " value="30%"  readonly="readonly"></td>
						<td><input type="text" size="10" name="tts13" id="tts13" value="<%=(net-1000000)>0?(net-1000000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts14" id="tts14" value="0 " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts15" id="tts15" value="<%=(net-1000000)>0?((net-1000000)*3/10):0 %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax credit (sec 87A)</td>
			<td>
			<%if((year.equalsIgnoreCase("2016"))) {%>
			<input type="text" size="10" name="ttc" id="ttc" value="<%=net>250000?(net<500000?5000:0):0%> " readonly="readonly" >
			<%} else {%>
			<input type="text" size="10" name="ttc" id="ttc" value="<%=net>250000?(net<500000?2000:0):0%> " readonly="readonly" >
			<% }%>
			</td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax on Income</td>
			<td><input type="text" size="10" name="ttoi" id="ttoi" value="<%=tax>0?tax:0 %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Capital Gains Tax(from stocks & MFs)</td>
			<td><input type="text" size="10" name="tcgts" id="tcgts" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Capital Gains Tax(from property)</td>
			<td><input type="text" size="10" name="tcg" id="tcg" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Surcharge on Income Tax</td>
			
			<%int surcharge=0;
			if(net>10000000){
				if((year.equalsIgnoreCase("2016"))) {
					surcharge=(int)(tax*0.15);
				}
				else{
			 surcharge=(int)(tax*0.12);
				}
			 tax=tax+surcharge;
			} %>
			<td><input type="text" size="10" name="tsit" id="tsit" value="<%=surcharge%>" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Education Cess</td>
			<td><input type="text" size="10" name="tec" id="tec" value="<%=Math.round(tax>0?(tax*0.03):0) %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Tax liability</td>
			<% float totalTaxLiability=(tax>0)?Math.round(tax+(tax*0.03)):0;%>
			<td><input type="text" size="10" name="tttl" id="tttl" value="<%=totalTaxLiability%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Income Tax Paid from salary</td>
			<td><input type="text" size="10" name="ttitp " id="ttitp" value="<%=(int)tds %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Tax Due</td>
			<td><input type="text" size="10" name="titd" id="titd" value="<%=tax>0?(Math.round((tax+(tax*3/100))-(int)tds)):0%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Remaining month in the year(i.e. yet to finalize)</td>
			<td><input type="text" size="10" name="trmy" id="trmy" value="<%=Month %> " readonly="readonly" ></td>
		</tr>
		
		<tr bgcolor="#929292" >
		<%if(Month==0){ %>
			<td colspan="4" ><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax remaining</h4></font></td>
			<td><input type="text" size="10" name="ttpm" id="ttpm" value="<%=tax>0?(Math.round((tax+(tax*3/100))-(int)tds)):0%> " readonly="readonly" ></td>
			<% }else{ %>
				<td colspan="4" ><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax per month</h4></font></td>
				<td><input type="text" size="10" name="ttpm" id="ttpm" value="<%=tax>0?Math.round(((tax+(tax*0.03))-(int)tds)/Month):0 %> " readonly="readonly" ></td>
		<%	}%>
		</tr>
		
		<tr>
		</tr>
		
		
		<tr class="alt" align="left">
		</tr></table></div></td></tr>
	</table>
		
		<table id="customers" align="center">
	<tr class="alt"><th colspan="4" width="570px">Exemptions
	</th></tr></table>
	
	<div id="table-content" style="height: 408px; width:570px; overflow-y: scroll;">
			
			
		<table id="customers">
	<tr  bgcolor="#929292" ><td><font color="white"><h4>DEDUCTIONS UNDER SECTION 10 & 17</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td></tr>	
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;HRA Exemption (sec 10(13A))</td>
		<td> <input type="text" size="10" name="thrae" id="thraep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="thral"  id="thral" value="" readonly="readonly" ></td>
		</tr>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Transport Exemption (sec 10(14))</td>
		<td> <input type="text" size="10" name="ttep"  id="ttep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="ttel" id="ttel" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Other Exemption under (sec 10(10)(gratuity,etc.))</td>
		<td> <input type="text" size="10" name="toep" id="toep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="toel" id="toel" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Bills Exemption (sec 10(2))</td>
		<td> <input type="text" size="10" name="tmbep" id="tmbep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmbel" id="tmbel" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Children's Education Allowance Exemption (sec 10(14))</td>
		<td> <input type="text" size="10" name="tceaep" id="tceaep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tceael" id="tceael" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;LTA Exemption (sec 10(5))</td>
		<td> <input type="text" size="10" name="tltaep" id="tltaep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tltal" id="tltal" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Uniform expenses (sec 10(14))</td>
		<td> <input type="text" size="10" name="tuep" id="tuep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tuel" id="tuel" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Total Exempted Allowances</td>
		<td> <input type="text" size="10" name="tteap" id="tteap" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tteal" id="tteal" value="" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"  ><td><font color="white"><h4>OTHER INCOME</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
	    <tr class="alt" ><td>&nbsp;&nbsp;&nbsp;House/property income or loss </td>
		<td> <input type="text" size="10" name="thinlp" id="thinlp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="thinll" id="thinll" value="" readonly="readonly" ></td>
		</tr>

		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Interest on housing loan(for tax exemption)</td>
		<td> <input type="text" size="10" name="tihlp" id="tihlp" value="" readonly="readonly" > </td>
		<td> <input type="text" size="10" name="tihll" id="tihll" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Saving bank interest</td>
		<td> <input type="text" size="10" name="tsbip" id="tsbip" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tsbil" id="tsbil" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Other income (interest, etc.excluding SB int)</td>
		<td> <input type="text" size="10" name="toip" id="toip" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="toil" id="toil" value="" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"  ><td><font color="white"><h4>DEDUCTIONS UNDER CHAPTER VI-A</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Insurance Premium/health check (sec 80D)</td>
		<td> <input type="text" size="10" name="tmiphp" id="tmiphp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmiphl" id="tmiphl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Insurance Premium for parents (sec 80D)</td>
		<td> <input type="text" size="10" name="tmifpp" id="tmifpp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmifpl" id="tmifpl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical for handicapped dependents (sec 80DD)</td>
		<td> <input type="text" size="10" name="tmhdp" id="tmhdp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmhdl" id="tmhdl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical for specified diseases (sec 80DDB)</td>
		<td> <input type="text" size="10" name="tmsdp" id="tmsdp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmsdl" id="tmsdl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Higher Education Loan Interest Repayment (sec 80E)</td>
		<td> <input type="text" size="10" name="thelirp" id="thelirp" value=" " readonly="readonly"  > </td>
		<td> <input type="text" size="10" name="thelirl" id="thelirl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Donation to approved fund and charities (sec 80G)</td>
		<td> <input type="text" size="10" name="tdafcp" id="tdafcp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tdafcl" id="tdafcl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Rent deduction(sec 80GG) only if HRA not received</td>
		<td> <input type="text" size="10" name="trdhrp" id="trdhrp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="trdhrp" id="trdhrp" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Saving Bank interest exemption (sec 80TTA)</td>
		<td> <input type="text" size="10" name="tsbiep" id="tsbiep" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tsbiel" id="tsbiel" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Deduction for permanent disability (sec 80U)</td>
		<td> <input type="text" size="10" name="tdfpdp" id="tdfpdp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tdfpdl" id="tdfpdl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Any other deduction(incl. donations u/s 35AC/sec80GGA)</td>
		<td> <input type="text" size="10" name="taodp" id="taodp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="taodl" id="taodl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Total Deductibles</td>
		<td> <input type="text" size="10" name="ttdp" id="ttdp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="ttdl" id="ttdl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"><td><font color="white"><h4> DEDUCTIONS UNDER CHAPTER VI (sec 80C)</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Pension scheme(sec 80C)</td>
		<td> <input type="text" size="10" name="tpsp" id="tpsp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tpsl" id="tpsl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;NSC (sec 80C)</td>
		<td> <input type="text" size="10" name="tnscp" id="tnscp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tnscl" id="tnscl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Public provident fund (sec 80C)</td>
		<td> <input type="text" size="10" name="tppfp" id="tppfp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tppfl" id="tppfl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Employees provident fund & voluntry PF(sec 80C)</td>
		<td> <input type="text" size="10" name="tepfvp" id="tepfvp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tepfvl" id="tepfvl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Childrens Education Tuition Fees (sec 80C)</td>
		<td> <input type="text" size="10" name="tcetfp" id="tcetfp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tcetfp" id="tcetfp" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Housing loan principal rapayment, regt/stamp duty (sec 80C)</td>
		<td> <input type="text" size="10" name="thlprp" id="thprp" value="" readonly="readonly"  ></td>
		<td> <input type="text" size="10" name="thlprl" id="thlprl" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Insurance premium & others(MF,ULIP,FD,etc.)(sec 80C)</td>
		<td> <input type="text" size="10" name="tipop" id="tipop" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tipol" id="tipol" value="" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Rajiv gandhi equity savings scheme (sec 80CCG)</td>
		<td> <input type="text" size="10" name="tressp"  id="tressp" value="" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tressl" id="tressl" value="" readonly="readonly"  > </td>
		</tr>
		<tr bgcolor="#929292" ><td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Total Investments</h4></font></td>
		<td> <input type="text" size="10" name="ttinp" id="ttinp" value="" readonly="readonly"  ></td>
		<td> <input type="text" size="10" name="ttinl" id="ttinl" value="" readonly="readonly"  ></td>
		</tr>
		
		</table>
		
		</div>
		</form>
		<%} else { 
					//System.out.println("map is not empty");
					//System.out.println("basic"+basic);
					//System.out.println("hra"+hra);
					float hraExmpCal=0.0f; 
				//	hraExmpCal= YearlyPDFReport.calHraExempt(Integer.parseInt(empno), basic, hra);
				//	System.out.println("hraExmpCal"+hraExmpCal);
					int hraExmp = map.get(565)==null?0:map.get(565);
					//System.out.println("hraExmp"+hraExmp);
					int TrnsExmp = map.get(108)==null?0:map.get(108);
					//System.out.println("TrnsExmp"+TrnsExmp);
					int othrExmp = map.get(586)==null?0:map.get(586);
					//System.out.println("othrExmp"+othrExmp);
					int MediExmp = map.get(524)==null?0:map.get(524);
					//System.out.println("MediExmp"+MediExmp);
					int EduExmp = map.get(523)==null?0:map.get(523)+(map.get(522)==null?0:map.get(522));
					//System.out.println("EduExmp"+EduExmp);
					int ltaExmp = map.get(525)==null?0:map.get(525);
					//System.out.println("ltaExmp"+ltaExmp);
					int UniExmp = map.get(582)==null?0:map.get(582);
					//System.out.println("UniExmp"+UniExmp);
					int TrnsExmp1 = map.get(108)==null?0:map.get(108)>9600?9600:map.get(108);
					//System.out.println("TrnsExmp1"+TrnsExmp1);
					int MediExmp1 = map.get(524)==null?0:map.get(524)>15000?15000:map.get(524);
					//System.out.println("MediExmp1"+MediExmp1);
					int EduExmp1 = EduExmp>0?EduExmp>9600?9600:EduExmp:0;
					//System.out.println("EduExmp1"+EduExmp1);
					int totalExmp10_17 = hraExmp+TrnsExmp+othrExmp+MediExmp+EduExmp+ltaExmp+UniExmp;
					//System.out.println("totalExmp10_17"+totalExmp10_17);
					int totalExmpCal10_17 = ((int)hraExmpCal>0?(int)hraExmpCal:0)+TrnsExmp1+othrExmp+MediExmp1+EduExmp1+ltaExmp+UniExmp;
					//System.out.println("totalExmpCal10_17"+totalExmpCal10_17);
					int mediIns = map.get(549)==null?0:map.get(549);
					//System.out.println("mediIns"+mediIns);
					int mediInsP = map.get(550)==null?0:map.get(550);
					//System.out.println("mediInsP"+mediInsP);
					int mediHand = map.get(552)==null?0:map.get(552);
					//System.out.println("mediHand"+mediHand);
					int medispds = map.get(553)==null?0:map.get(553);
					//System.out.println("medispds"+medispds);
					int loanInsRp = map.get(554)==null?0:map.get(554);
					//System.out.println("loanInsRp"+loanInsRp);
					int donation = map.get(551)==null?0:map.get(551);
					//System.out.println("donation"+donation);
					int rendDed = map.get(555)==null?0:map.get(555);
					//System.out.println("rendDed"+rendDed);
					int bankIns = map.get(559)==null?0:map.get(559);
					//System.out.println("bankIns"+bankIns);
					int permDsbl = map.get(558)==null?0:map.get(558);
					//System.out.println("permDsbl"+permDsbl);
					int totalChptr6 = mediIns+mediInsP+mediHand+medispds+loanInsRp+donation+rendDed+bankIns+permDsbl;
					//System.out.println("totalChptr6"+totalChptr6);
					int pension = map.get(546)==null?0:map.get(546);
					//System.out.println("pension"+pension);
					int nsc = map.get(538)==null?0:map.get(538);
					//System.out.println("nsc"+nsc);
					int ppf = map.get(501)==null?0:map.get(501);
					//System.out.println("ppf"+ppf);
					int epf = map.get(536)==null?0:map.get(536);
					//System.out.println("epf"+epf);
					int childedu = map.get(540)==null?0:map.get(540);
					//System.out.println("childedu"+childedu);
					int Hloan = map.get(539)==null?0:map.get(539);
					//System.out.println("Hloan"+Hloan);
					int insprm = map.get(537)==null?0:map.get(537);
					//System.out.println("insprm"+insprm);
					int equity = map.get(548)==null?0:map.get(548);
					//System.out.println("equity"+equity);
					int tot80c = pension+nsc+ppf+epf+childedu+Hloan+insprm+equity;
					int loanTotal=0;
					if(year.equalsIgnoreCase("2016")){
						loanTotal=(map.get(535)==null?0:map.get(535)>200000?200000:map.get(535));
					}
					else
					{
						loanTotal=(map.get(535)==null?0:map.get(535)>150000?150000:map.get(535));
					}
					int grossInc = (int)(income-(pt+totalExmpCal10_17))-loanTotal;
					//System.out.println("grossInc"+grossInc);
					int net = grossInc-(totalChptr6+(tot80c>150000?150000:tot80c));
					//System.out.println("net"+net);
				if(year.equalsIgnoreCase("2016"))
					 tax = ((net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0)+((net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0))+((net-1000000)>0?((net-1000000)*3/10):0)-(net>250000?(net<500000?5000:0):0);
				else
					 tax = ((net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0)+((net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0))+((net-1000000)>0?((net-1000000)*3/10):0)-(net>250000?(net<500000?2000:0):0);
					System.out.println("tax"+tax);
		%>
			<form action="ITServlet?action=create" method="post" name="form" id="form" onsubmit="return check()" >
		
		
		<table><tr><td>
		<table id="customers" align="right" width="550" >
		<tr class="alt"> <th>Tax Computation </th></tr>
		<tr><td>
		<div style="height: 408px; width:507px; overflow-y: scroll;">
		<table  id="customers">
		<tr bgcolor="#929292" ><td colspan="4"> <font color="white"><h4> Tax Computation</h4></font></td></tr>
 		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Salary</td>
			<td><input type="text" size="10" name="tgsa" id="tgsa" value=" <%=(int)income %>" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Profession Tax</td>
			<td><input type="text" size="10" name="tpt" id="tpt" value="<%=pt %>" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exemptions under section 10 & 17</td>
			<td><input type="text" size="10" name="teus" id="teus" value="<%=totalExmpCal10_17 %> " readonly="readonly" ></td>
		</tr>
		
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Salary after section 10 & 17 exemptions</td>
			<td><input type="text" size="10" name="tgse" id="tgse" value="<%=(int)(income-(pt+totalExmpCal10_17)) %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Accommodation & car Perquisites</td>
			<td><input type="text" size="10" name="tacp" id="tacp" value="0" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "salaries"</td>
			<td><input type="text" size="10" name="tics" id="tics" value="0" readonly="readonly" ></td>
		</tr>
		<%-- <tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "House/Property"</td>
			<td><input type="text" size="10" name="tichp" id="tichp" value="<%=map.get(570)==null?0:map.get(570)%> " readonly="readonly" ></td>
		</tr> --%>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interest on housing loan(for tax exemption)"</td>
			<%if(year.equalsIgnoreCase("2016")){ %>
			<td><input type="text" size="10" name="tichl" id="tichl" value="<%=map.get(535)==null?0:map.get(535)>200000?200000:map.get(535)%> " readonly="readonly" ></td>
			<% } else {%>
			<td><input type="text" size="10" name="tichl" id="tichl" value="<%=map.get(535)==null?0:map.get(535)>200000?200000:map.get(535)%> " readonly="readonly" ></td>
			<% }%>
		</tr>
		<tr class="alt">
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "Capital Gains" at nominal rate</td>
			<td><input type="text" size="10" name="ticnr" id="ticnr" value="0" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Chargeable under head "Other sources"</td>
			<td><input type="text" size="10" name=" " id=" " value="0" readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gross Total Income</td>
			<td><input type="text" size="10" name="tgti" id="tgti" value="<%= grossInc%>  " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deduction under chapter VI-A</td>
			<td><input type="text" size="10" name="tducvi" id="tducvi" value="<%=totalChptr6 %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deduction under sec-80c</td>
			<td><input type="text" size="10" name="tduc" id="tduc" value="<%=tot80c>150000?150000:tot80c%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Net taxable income</td>
			<td><input type="text" size="10" name="tnti" id="tnti" value=" <%=net %>" readonly="readonly" ></td>
		</tr>
		</table>
		
				<table id="customers">
				<tr bgcolor="#929292" ><td><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax Slabs</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Tax rate</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Appl Amt</h4></font></td>	
		<td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Balance</h4></font></td>	<td><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax</h4></font></td>	
		
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;00000-250000</td>
			<td><input type="text" size="10" name="tts1" id="tts1" value="0%" readonly="readonly"  ></td>
						<td><input type="text" size="10" name="tts2" id="tts2" value="<%=net>250000?250000:net %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts3" id="tts3" value="<%=(net-250000)>0?(net-250000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts4" id="tts4" value="0 " readonly="readonly" ></td>
			
		</tr >
		<tr class="alt" >
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;250001-500000</td>
			<td><input type="text" size="10" name="tts5" id="tts5" value="10%" readonly="readonly" ></td>
						<td><input type="text" size="10" name="tts6" id="tts6" value="<%=(net-250000)>0?((net-250000)>250000?250000:(net-250000)):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts7" id="tts7" value="<%=(net-500000)>0?(net-500000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts8" id="tts8" value="<%=(net-250000)>0?((net-250000)>250000?25000:(net-250000)/10):0 %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;500001-1000000</td>
			<td><input type="text" size="10" name="tts9" id="tts9" value="20%" readonly="readonly"></td>
						<td><input type="text" size="10" name="tts10" id="tts10" value="<%=(net-500000)>500000?500000:(net-500000>0?net-500000:0) %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts11" id="tts11" value="<%=(net-500000)>500000?(net-1000000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts12" id="tts12" value="<%=(net-500000)>500000?100000:(net-500000>0?(net-500000)/5:0) %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>1000000</td>
			<td><input type="text" size="10" name=" " id=" " value="30%"  readonly="readonly"></td>
						<td><input type="text" size="10" name="tts13" id="tts13" value="<%=(net-1000000)>0?(net-1000000):0 %> " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts14" id="tts14" value="0 " readonly="readonly" ></td>
			
						<td><input type="text" size="10" name="tts15" id="tts15" value="<%=(net-1000000)>0?((net-1000000)*3/10):0 %> " readonly="readonly" ></td>
			
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax credit (sec 87A)</td>
				<%if(year.equalsIgnoreCase("2016")){ %>
			<td><input type="text" size="10" name="ttc" id="ttc" value="<%=5000 %> " readonly="readonly" ></td>
			<% }else {%>
			<td><input type="text" size="10" name="ttc" id="ttc" value="<%=2000 %> " readonly="readonly" ></td>
			<% }%>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax on Income</td>
			<td><input type="text" size="10" name="ttoi" id="ttoi" value="<%=(tax>0?(int)(tax):0)%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Capital Gains Tax(from stocks & MFs)</td>
			<td><input type="text" size="10" name="tcgts" id="tcgts" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Capital Gains Tax(from property)</td>
			<td><input type="text" size="10" name="tcg" id="tcg" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Surcharge on Income Tax</td>
			<td><input type="text" size="10" name="tsit" id="tsit" value="0 " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Education Cess</td>
			<td><input type="text" size="10" name="tec" id="tec" value="<%=Math.round(tax>0?(tax*0.03):0)%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Tax liability</td>
			<td><input type="text" size="10" name="tttl" id="tttl" value="<%=tax>0?Math.round(tax+(tax*0.03)):0 %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Income Tax Paid from salary</td>
			<td><input type="text" size="10" name="ttitp " id="ttitp" value="<%=(int)tds %> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Income Tax Due</td>
			<td><input type="text" size="10" name="titd" id="titd" value="<%=tax>0?(Math.round((tax+(tax*3/100))-(int)tds)):0%> " readonly="readonly" ></td>
		</tr>
		<tr class="alt">
			<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Remaining month in the year(i.e. yet to finalize)</td>
			<td><input type="text" size="10" name="trmy" id="trmy" value="<%=Month %> " readonly="readonly" ></td>
		</tr>
		
		<tr bgcolor="#929292" >
				<%if(Month==0){ %>
			<td colspan="4" ><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax remaining</h4></font></td>
			<td><input type="text" size="10" name="ttpm" id="ttpm" value="<%=tax>0?(Math.round((tax+(tax*3/100))-(int)tds)):0%> " readonly="readonly" ></td>
			<% }else{ %>
				<td colspan="4" ><font color="white"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tax per month</h4></font></td>
				<td><input type="text" size="10" name="ttpm" id="ttpm" value="<%=tax>0?Math.round(((tax+(tax*0.03))-(int)tds)/Month):0 %> " readonly="readonly" ></td>
		<%	}%>
		</tr>
		
		<tr class="alt" >
		<td align="center" colspan="5"><input type="Submit" value="Process Income Tax" class="myButton"></td>
		</tr>
		<tr class="alt" align="left">
		</tr></table></div></td></tr>
	</table></td>
		
		<td width="20"></td>
		
	<td>	<table id="customers" align="center">
	<tr class="alt"><th colspan="4" width="570px">Exemptions
	</th></tr></table>
	
	<div id="table-content" style="height: 408px; width:570px; overflow-y: scroll;">
			
			
		<table id="customers">
	<tr  bgcolor="#929292" ><td><font color="white"><h4>DEDUCTIONS UNDER SECTION 10 & 17</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td></tr>	
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;HRA Exemption (sec 10(13A))</td>
		<td> <input type="text" size="10" name="thrae" id="thraep" value="<%=hraExmp%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="thral"  id="thral" value="<%=(int)hraExmpCal>0?(int)hraExmpCal:0%>" readonly="readonly" ></td>
		</tr>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Transport Exemption (sec 10(14))</td>
		<td align="right"> <input type="text" size="10" name="ttep"  id="ttep" value="<%=map.get(108)==null?0:map.get(108)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="ttel" id="ttel" value="<%=map.get(108)==null?0:map.get(108)>9600?9600:map.get(108)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Other Exemption under (sec 10(10)(gratuity,etc.))</td>
		<td> <input type="text" size="10" name="toep" id="toep" value="<%=map.get(586)==null?0:map.get(586)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="toel" id="toel" value="<%=map.get(586)==null?0:map.get(586)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Bills Exemption (sec 10(2))</td>
		<td> <input type="text" size="10" name="tmbep" id="tmbep" value="<%=map.get(524)==null?0:map.get(524)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmbel" id="tmbel" value="<%=map.get(524)==null?0:map.get(524)>15000?15000:map.get(524)%>" readonly="readonly" ></td>
		</tr>
		<% int edu = map.get(522)==null?0:map.get(522) + (map.get(523)==null?0:map.get(523));%>
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Children's Education Allowance Exemption (sec 10(14))</td>
		<td> <input type="text" size="10" name="tceaep" id="tceaep" value="<%=edu%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tceael" id="tceael" value="<%=edu>9600?9600:edu%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;LTA Exemption (sec 10(5))</td>
		<td> <input type="text" size="10" name="tltaep" id="tltaep" value="<%=map.get(525)==null?0:map.get(525)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tltal" id="tltal" value="<%=map.get(525)==null?0:map.get(525)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Uniform expenses (sec 10(14))</td>
		<td> <input type="text" size="10" name="tuep" id="tuep" value="<%=map.get(582)==null?0:map.get(582)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tuel" id="tuel" value="<%=map.get(582)==null?0:map.get(582)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Total Exempted Allowances</td>
		<td> <input type="text" size="10" name="tteap" id="tteap" value="<%=totalExmp10_17%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tteal" id="tteal" value="<%=totalExmpCal10_17%>" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"  ><td><font color="white"><h4>OTHER INCOME</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
	    <%-- <tr class="alt" ><td>&nbsp;&nbsp;&nbsp;House/property income or loss </td>
		<td> <input type="text" size="10" name="thinlp" id="thinlp" value="<%=map.get(570)==null?0:map.get(570)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="thinll" id="thinll" value="<%=map.get(570)==null?0:map.get(570)%>" readonly="readonly" ></td>
		</tr> --%>

		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Interest on housing loan(for tax exemption)</td>
		<td> <input type="text" size="10" name="tihlp" id="tihlp" value="<%=map.get(535)==null?0:map.get(535)%>" readonly="readonly" ></td>
			<%if(year.equalsIgnoreCase("2016")){ %>
		<td> <input type="text" size="10" name="tihll" id="tihll" value="<%=map.get(535)==null?0:map.get(535)>200000?200000:map.get(535)%>" readonly="readonly" ></td>
		<% } else {%>
		<td> <input type="text" size="10" name="tihll" id="tihll" value="<%=map.get(535)==null?0:map.get(535)>150000?150000:map.get(535)%>" readonly="readonly" ></td>
		<% }%>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Saving bank interest</td>
		<td> <input type="text" size="10" name="tsbip" id="tsbip" value="<%=map.get(526)==null?0:map.get(526)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tsbil" id="tsbil" value="<%=map.get(526)==null?0:map.get(526)%>" readonly="readonly" > </td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Other income (interest, etc.excluding SB int)</td>
		<td> <input type="text" size="10" name="toip" id="toip" value="<%=map.get(581)==null?0:map.get(581)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="toil" id="toil" value="<%=map.get(581)==null?0:map.get(581)%>" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"  ><td><font color="white"><h4>DEDUCTIONS UNDER CHAPTER VI-A</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Insurance Premium/health check (sec 80D)</td>
		<td> <input type="text" size="10" name="tmiphp" id="tmiphp" value="<%=map.get(549)==null?0:map.get(549)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmiphl" id="tmiphl" value="<%=map.get(549)==null?0:map.get(549)>15000?15000:map.get(549)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical Insurance Premium for parents (sec 80D)</td>
		<td> <input type="text" size="10" name="tmifpp" id="tmifpp" value="<%=map.get(550)==null?0:map.get(550)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmifpl" id="tmifpl" value="<%=map.get(550)==null?0:map.get(550)>20000?20000:map.get(550)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical for handicapped dependents (sec 80DD)</td>
		<td> <input type="text" size="10" name="tmhdp" id="tmhdp" value="<%=map.get(552)==null?0:map.get(552)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmhdl" id="tmhdl" value="<%=map.get(552)==null?0:map.get(552)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Medical for specified diseases (sec 80DDB)</td>
		<td> <input type="text" size="10" name="tmsdp" id="tmsdp" value="<%=map.get(553)==null?0:map.get(553)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tmsdl" id="tmsdl" value="<%=map.get(553)==null?0:map.get(553)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Higher Education Loan Interest Repayment (sec 80E)</td>
		<td> <input type="text" size="10" name="thelirp" id="thelirp" value="<%=map.get(554)==null?0:map.get(554)%> " readonly="readonly" > </td>
		<td> <input type="text" size="10" name="thelirl" id="thelirl" value="<%=map.get(554)==null?0:map.get(554)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Donation to approved fund and charities (sec 80G)</td>
		<td> <input type="text" size="10" name="tdafcp" id="tdafcp" value="<%=map.get(551)==null?0:map.get(551)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tdafcl" id="tdafcl" value="<%=map.get(551)==null?0:map.get(551)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Rent deduction(sec 80GG) only if HRA not received</td>
		<td> <input type="text" size="10" name="trdhrp" id="trdhrp" value="<%=map.get(555)==null?0:map.get(555)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="trdhrp" id="trdhrp" value="<%=map.get(555)==null?0:map.get(555)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Saving Bank interest exemption (sec 80TTA)</td>
		<td> <input type="text" size="10" name="tsbiep" id="tsbiep" value="<%=map.get(559)==null?0:map.get(559)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tsbiel" id="tsbiel" value="<%=map.get(559)==null?0:map.get(559)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Deduction for permanent disability (sec 80U)</td>
		<td> <input type="text" size="10" name="tdfpdp" id="tdfpdp" value="<%=map.get(558)==null?0:map.get(558)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tdfpdl" id="tdfpdl" value="<%=map.get(558)==null?0:map.get(558)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Any other deduction(incl. donations u/s 35AC/sec80GGA)</td>
		<td> <input type="text" size="10" name="taodp" id="taodp" value="0" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="taodl" id="taodl" value="0" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Total Deductibles</td>
		<td> <input type="text" size="10" name="ttdp" id="ttdp" value="<%=totalChptr6%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="ttdl" id="ttdl" value="<%=totalChptr6%>" readonly="readonly" ></td>
		</tr>
		
		<tr  bgcolor="#929292"><td><font color="white"><h4> DEDUCTIONS UNDER CHAPTER VI (sec 80C)</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Produced</h4></font></td> <td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Limited</h4></font></td>	
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Pension scheme(sec 80C)</td>
		<td> <input type="text" size="10" name="tpsp" id="tpsp" value="<%=map.get(546)==null?0:map.get(546)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tpsl" id="tpsl" value="<%=map.get(546)==null?0:map.get(546)>150000?150000:map.get(546)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;NSC (sec 80C)</td>
		<td> <input type="text" size="10" name="tnscp" id="tnscp" value="<%=map.get(538)==null?0:map.get(538)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tnscl" id="tnscl" value="<%=map.get(538)==null?0:map.get(538)>150000?150000:map.get(538)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Public provident fund (sec 80C)</td>
		<td> <input type="text" size="10" name="tppfp" id="tppfp" value="<%=map.get(501)==null?0:map.get(501)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tppfl" id="tppfl" value="<%=map.get(501)==null?0:map.get(501)>150000?150000:map.get(501)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Employees provident fund & voluntry PF(sec 80C)</td>
		<td> <input type="text" size="10" name="tepfvp" id="tepfvp" value="<%=map.get(536)==null?0:map.get(536)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tepfvl" id="tepfvl" value="<%=map.get(536)==null?0:map.get(536)>150000?150000:map.get(536)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Childrens Education Tution Fees (sec 80C)</td>
		<td> <input type="text" size="10" name="tcetfp" id="tcetfp" value="<%=map.get(540)==null?0:map.get(540)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tcetfp" id="tcetfp" value="<%=map.get(540)==null?0:map.get(540)>9600?9600:map.get(540)%>" readonly="readonly" > </td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Housing loan principal repayment, regt/stamp duty (sec 80C)</td>
		<td> <input type="text" size="10" name="thlprp" id="thprp" value="<%=map.get(539)==null?0:map.get(539)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="thlprl" id="thlprl" value="<%=map.get(539)==null?0:map.get(539)>150000?150000:map.get(539)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Insurance premium & others(MF,ULIP,FD,etc.)(sec 80C)</td>
		<td> <input type="text" size="10" name="tipop" id="tipop" value="<%=map.get(537)==null?0:map.get(537)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tipol" id="tipol" value="<%=map.get(537)==null?0:map.get(537)>150000?150000:map.get(537)%>" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt" ><td>&nbsp;&nbsp;&nbsp;Rajiv gandhi equity savings scheme (sec 80CCG)</td>
		<td> <input type="text" size="10" name="tressp"  id="tressp" value="<%=map.get(548)==null?0:map.get(548)%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="tressl" id="tressl" value="<%=map.get(548)==null?0:map.get(548)>50000?50000:map.get(548)%>" readonly="readonly" ></td>
		</tr>
		<tr bgcolor="#929292" ><td><font color="white"><h4>&nbsp;&nbsp;&nbsp;Total Investments</h4></font></td>
		<td> <input type="text" size="10" name="ttinp" id="ttinp" value="<%=tot80c%>" readonly="readonly" ></td>
		<td> <input type="text" size="10" name="ttinl" id="ttinl" value="<%=tot80c>150000?150000:tot80c%>" readonly="readonly" ></td>
		</tr>
		
		</table></td></tr></table>
		
		</div>
		</form>
		<input type="hidden" name="flag" id="flag" value="<%=flag%>">
		<%} }catch(Exception e){
			e.printStackTrace();

			%>
			<script type="text/javascript">

			window.location.href="salaryStructure.jsp";

			</script>
			<%
			}%>
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
		