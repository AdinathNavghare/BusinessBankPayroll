<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
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
<title>CTC  &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>


<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
.style3 {font-size: 1.5em}
-->
</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
	
	
	function chk()
	{ var empno=document.getElementById("EMPNO").value;
	var res=empno.indexOf(":");
	if(res>0)
		{
		return true;
		}
	else
		{
		alert("Please select Employee !");
		document.getElementById("EMPNO").value="";
		document.getElementById("EMPNO").focus();
		return false;}
	
	}
	
</script>
<%
	CodeMasterHandler CMH = new CodeMasterHandler();
	String action = "";
	String emp="";
	ArrayList<TranBean> incTranVals = new ArrayList<TranBean>();
	try
	{
		action = request.getParameter("action").toString();
		if(action.equalsIgnoreCase("2"))	//return from Save
		{
			emp = request.getAttribute("emp").toString();
			incTranVals = (ArrayList<TranBean>)request.getAttribute("incTranVals");
			
		}
	}
	catch(Exception e)
	{
		
	}
		

%>
<script type="text/javascript">


<%if(incTranVals.size()<=0)
{
	
%>
<%-- window.alert("CTC for employee <%=emp%> is not Filled !"); --%>
<%} %>
</script>

</head>
<body style="overflow: hidden;" onLoad="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Display Cost To Company (CTC)</h1>
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
			<div id="table-content" style="overflow-y:auto; max-height:570px; ">
			<table width="850" border="1" align="center">
			  <tr><td align="center"><br/>
			  <form action="NewCTCServlet?action=checkCTC" method="post" >
			  <table width="564" height="66" border="1">
			   <tr bgcolor="#eaeaea">
			     <td height="33" colspan="2">&nbsp;Type Employee Name or Number 
			       <input name="EMPNO" type="text" id="EMPNO" onClick="showHide()" value="<%=emp %>" size="35">
			       <input type="submit" value="Check" onclick="return chk()">
			     </td>
			   </tr>
			   <tr>
			   <%
			   double sum = 0.0d;
			   double gross = 0.0d;
			   float pf=0.0f;
			   float pt=0.0f;
			   float esic=0.0f;
			   for(TranBean tb : incTranVals){
				  sum = sum+tb.getNET_AMT();
				  pf=tb.getPf();
				  pt=tb.getPt();
				  esic=tb.getEsic();
				  
			   }
			   gross = 12*sum;
			   //System.out.println(sum);
			   //System.out.println(sum*12);
			   %>
			     <td width="261">&nbsp;Annual Total  
			       <input name="annual" type="text" id="annual" value="<%=gross %>" dir="rtl" tabindex="1" size="17">
			       Rs.</td>
			     <td width="272">&nbsp;Monthly Gross 
			       <input name="monthly" type="text" id="monthly" size="17" readonly="readonly" value="<%= sum %>" style="background-color:silver;" dir="rtl">
			       Rs.</td>
			   </tr>
			   <tr height="25">
			      <td colspan="2">&nbsp;Eligible for PF &nbsp;
			      				<%if(pf==1){%>
			       				<input name="pf" type="checkbox" id="pf" value="true" checked="checked">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%}else{ %>
			       				<input name="pf" type="checkbox" id="pf" value="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%} %>
			       				Eligible for PT &nbsp;
			       				<%if(pt==1){ %>
			       				<input name="pt" type="checkbox" id="pt" value="true" checked="checked">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%}else{ %>
			       				<input name="pt" type="checkbox" id="pt" value="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%} %>
			   					Eligible for ESIC &nbsp;
			   					<%if(esic==1){ %>
			   					<input name="esic" type="checkbox" id="esic" value="true" checked="checked">
			   					<%}else{ %>
			      				<input name="esic" type="checkbox" id="esic" value="true">
			      				<%} %>
			      </td>
			   </tr>
			   </table>
			   
			   <p>&nbsp;</p>
			   <table width="780" border="1">
               	<tr align="center" bgcolor="#eaeaea">
                  <td width="50" align="center"><b>Sr. </b></td>
                  <td width="132"><b>Type</b></td>
                  <td width="80"><b>Value Type</b> </td>
                  <td width="70"><b>Value</b></td>
                  <td width="70"><b>Based On</b></td>
                  <td width="130"><b>Monthly Amount</b></td>
                  <td width="120"><b>Yearly Amount</b></td>
                  
                </tr>
                <tr>
                <% float addition=0.0f;
			   		for(TranBean tb : incTranVals){
						if(tb.getTRNCD()==101){%>
				 <td align="center" bgcolor="#FFFFFF">1</td>
			     <td align="left" bgcolor="#FFFFFF">&nbsp;Basic </td>
			     <td align="center"><select name="basicValType" id="basicValType" >
			     <%if(tb.getSRNO()==0){ %>
			     <option value="0" selected>Percentage</option>
			     <%}else{ %>
			     <option value="1">Fixed Value</option>
			     <%} %>
			     </select></td>
			     <td align="center">&nbsp;<input name="basic" type="text" value="<%= tb.getINP_AMT() %>" id="basic" size="8" readonly="readonly" autocomplete="off"  dir="rtl" tabindex="2" autocomplete="false"></td>
			     <td align="center" bgcolor="#FFFFFF"><select name="basicOn" id="basicOn">
			     <option value="199">Gross</option>
			     </select></td>
			     <%addition=addition+tb.getNET_AMT(); %>
			     <td bgcolor="#FFFFFF">&nbsp;<input name="basicVal" type="text" size="12" id="basicVal" value="<%= tb.getNET_AMT() %>" readonly="readonly" dir="rtl"> Rs.</td>
			     <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyBasicVal" type="text" id="yearlyBasicVal" value="<%= tb.getNET_AMT()*12 %>" readonly="readonly" size="10" dir="rtl"> Rs.</td>
			     </tr>
				<%}		  
			   	}
                	for(TranBean tb : incTranVals){
                		if(tb.getTRNCD()==104){	%>
                <tr>
                  <td align="center" bgcolor="#FFFFFF">2</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Medical Allowance </td>
                  <td align="center"><select name="daValType" id="daValType" >
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="da" type="text" value="<%= tb.getINP_AMT() %>" id="da" size="8"  readonly="readonly" dir="rtl" tabindex="3"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="daOn" id="daOn" >
                    <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="daVal" type="text" id="daVal" size="12" value="<%= tb.getNET_AMT() %>" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyDaVal" type="text" id="yearlyDaVal" value="<%= tb.getNET_AMT()*12 %>" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==103){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">3</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;HRA </td>
                  <td align="center"><select name="hraValType" id="hraValType" >
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="hra" type="text" id="hra" value="<%= tb.getINP_AMT() %>" size="8" autocomplete="off" readonly="readonly" dir="rtl" tabindex="4"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="hraOn" id="hraOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="hraVal" type="text" size="12" value="<%= tb.getNET_AMT() %>" id="hraVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyHraVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyHraVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==105){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">4</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Education  </td>
                  <td align="center"><select name="eduValType" id="eduValType" >
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="edu" type="text" id="edu" readonly="readonly" value="<%= tb.getINP_AMT() %>" size="8" autocomplete="off"  dir="rtl" tabindex="5"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="eduOn" id="eduOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="eduVal" type="text" value="<%= tb.getNET_AMT() %>" size="12" id="eduVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyEduVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyEduVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==126){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">5</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Min Insurance </td>
                  <td align="center"><select name="insuValType" id="insuValType">
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="insu" type="text" value="<%= tb.getINP_AMT() %>" id="insu" size="8" readonly="readonly" autocomplete="off"  dir="rtl" tabindex="6"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="insuOn" id="insuOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="insuVal" type="text" size="12" value="<%= tb.getNET_AMT() %>" id="insuVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyInsuVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyInsuVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==108){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">6</td>
                  <td align="left">&nbsp;Conveyance</td>
                  <td align="center"><select name="convValType" id="convValType" >
                   <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="conv" type="text" value="<%= tb.getINP_AMT() %>" id="conv" size="8" autocomplete="off" readonly="readonly"  dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="convOn" id="convOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="convVal" type="text" id="convVal" size="12" value="<%= tb.getNET_AMT() %>" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyConvVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyConvVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==107){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">7</td>
                  <td align="left">&nbsp;Special Allowance</td>
                  <td align="center"><select name="spAllowValType" id="spAllowValType" >
                   <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="spAllow" type="text" value="<%= tb.getINP_AMT() %>" id="spAllow" size="8" autocomplete="off" readonly="readonly" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="spAllowOn" id="spAllowOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="spAllowVal" type="text" id="spAllowVal" size="12" readonly="readonly" value="<%= tb.getNET_AMT() %>" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlySpAllowVal" type="text" id="yearlySpAllowVal" value="<%= tb.getNET_AMT()*12 %>" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==135){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">8</td>
                  <td align="left">&nbsp;Bonus</td>
                  <td align="center"><select name="bonusValType" id="bonusValType" >
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="bonus" type="text" value="<%= tb.getINP_AMT() %>" id="bonus" size="8" autocomplete="off" readonly="readonly" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="bonusOn" id="bonusOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="bonusVal" type="text" id="bonusVal" size="12" readonly="readonly" value="<%= tb.getNET_AMT() %>" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyBonusVal" type="text" id="yearlyBonusVal" readonly="readonly" value="<%= tb.getNET_AMT()*12 %>" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==128){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">9</td>
                  <td align="left">&nbsp;COL</td>
                  <td align="center"><select name="colValType" id="colValType" >
                    <%if(tb.getSRNO()==0){ %>
                    <option value="0" selected>Percentage</option>
                    <%}else{ %>
                    <option value="1">Fixed Value</option>
                    <%} %>
                     </select></td>
                  <td align="center">&nbsp;<input name="col" type="text" value="<%= tb.getINP_AMT() %>" id="col" size="8" autocomplete="off" readonly="readonly" dir="rtl" tabindex="10"></td>
                  <td align="center"><select name="colOn" id="colOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="colVal" type="text" id="colVal" value="<%= tb.getNET_AMT() %>" size="12" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyColVal" value="<%= tb.getNET_AMT()*12 %>" type="text" id="yearlyColVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                
                 <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total_Ern" type="text" readonly="readonly" value="<%=addition %>" size="12" id="total_Ern" dir="rtl" style="background-color:#7fe5ff">/- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotalErn" type="text" value="<%=addition *12%>" id="yearlyTotalErn" readonly="readonly" size="10" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>
                
                <tr>
                	<td colspan="7"><b>Deductions</b></td>
                </tr>
                <%} 
                	}
                float deduction=0.0f;
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==201){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">10</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;PF Employer 12% </td>
                  <td align="center"><select name="pfeValType" id="pfeValType">
                    <option value="0" selected>Percentage</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="pfe" type="text" value="<%= tb.getINP_AMT() %>" id="pfe" size="8" autocomplete="off" value="12" readonly="readonly" dir="rtl" tabindex="8"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="pfeOn" id="pfeOn">
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                  <%deduction =deduction + tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="pfeVal" type="text" id="pfeVal" value="<%= tb.getNET_AMT() %>" size="12" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyPfeVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyPfeVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==127){%>
                <tr bgcolor="#FFFFFF">
                  <td align="center">11</td>
                  <td align="left">&nbsp;Add Less Amount </td>
                  <td align="center"><select name="addLessValType" id="addLessValType">
                    <option value="1">Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="addLess" type="text" value="<%= tb.getINP_AMT() %>" id="addLess" size="8" autocomplete="off" readonly="readonly" dir="rtl" tabindex="9"></td>
                  <td align="center"><select name="addLessOn" id="addLessOn" >
                     <%if(tb.getADJ_AMT()==101){ %>
                    <option value="101" selected>Basic</option>
                    <%}else{ %>
                    <option value="199">Gross</option>
                    <%} %>
                  </select></td>
                   <%deduction =deduction + tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="addLessVal" type="text" id="addLessVal" size="12" value="<%= tb.getNET_AMT() %>" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyAddLessVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyAddLessVal" readonly="readonly" size="10" dir="rtl"> Rs.</td>
                </tr>
                <%}} %>
                
               <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total_Ded" type="text" readonly="readonly" size="12" value="<%=deduction %>" id="total_Ded" dir="rtl" style="background-color:#7fe5ff"> /- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotalDed" type="text" value="<%=deduction*12 %>" id="yearlyTotalDed" readonly="readonly" size="10" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>
                
                <tr bgcolor="#CCCCCC">
                  <td align="center">&nbsp;</td>
                  <td align="left">&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total" type="text" readonly="readonly" size="12" id="total" value="<%=sum %>" dir="rtl" style="background-color:#7fe5ff"> /- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotal" type="text" id="yearlyTotal" value="<%=gross %>" readonly="readonly" size="11" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>	
              </table>
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
    
</body>
