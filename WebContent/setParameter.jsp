<%@page import="payroll.DAO.LookupHandler"%>
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

<%
String empno="";
String action = "";
Form16Handler f16 = new Form16Handler();
LookupHandler lkp = new LookupHandler();
Map<Integer,Integer> map = new HashMap<Integer, Integer>(); 
empno =(String)session.getAttribute("empno1")==null?"":session.getAttribute("empno1").toString();
action = request.getParameter("action")== null?"":request.getParameter("action");
if(!empno.equals(""))
//map = f16.getSetupParams(empno);
%>
</head>
<body style="overflow: hidden;"> 
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
			<div class="step-no">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-dark-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div>
			<%} else { %>
			<div class="step-light-left"><a href="salaryStructure.jsp?action=salstruct">ITR Wages</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="Form16Entry.jsp?action=getdata" > Form 16</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">4</div>
			<div class="step-dark-left"><a href="setParameter.jsp">Setup Parameters</a></div>
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
		<% try{ %>
		<center> <h3> Employee Name : <%=lkp.getLKP_Desc("ET", Integer.parseInt(empno))%> </h3>
		<br/>
		<form action=" " method="post" name="form1" id="form1" >
		
		<table id="customers" align="center">
	<tr class="alt"><th colspan="4" width="515px">Setup Parameters 
	</th></tr></table>
	
	<div id="table-content" style="height: 408px; width:515px; overflow-y: scroll;">
			
		
		<table id="customers">
				
   	<tr  bgcolor="#929292" ><td colspan="4" ><font color="white"> <h4> Setup Parameter</h4></font> </td></tr>
	<%if(map.isEmpty()){ %>
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Metro/Non-metro(M or N)</td>
			<td><input type="text" size="10" name="smn" id="smn" readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HRA Exempt limit(of Basic+DA)</td>
			<td><input type="text" size="10" name="shel" id="shel" readonly="readonly"></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  PF/VPF Deductions</h4></font> </td></tr>
	           
	     <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PF as percentage of "salary"</td>
			<td><input type="text" size="10" name="spfps" id="spfps" value="12.00% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PF limited to (if there is a max. limit for deduction)</td>
			<td><input type="text" size="10" name="spfl" id="spfl" value="0 " readonly="readonly" ></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VPF as percentage of "salary"</td>
			<td><input type="text" size="10" name="svpps" id="svpps" value="0.00% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interest rate on PF/VPF</td>
			<td><input type="text" size="10" name="sirpv" id=" sirpv" value="8.50% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VPF as "percentage of salary" or "Fixed amount"?(P or F)</td>
			<td><input type="text" size="10" name="ssfa" id="ssfa" readonly="readonly"></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  For Housing Loan</h4></font> </td></tr>
	       
	      <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;House self-occupied?(Y or N)</td>
			<td><input type="text" size="10" name="shso" id="shos" readonly="readonly"></td>
		</tr>
		  
		   <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Loan taken after Apr1, 1999?</td>
			<td><input type="text" size="10" name="slap" id="slap" readonly="readonly"></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  Miscellaneous</h4></font> </td></tr>
	       
	     <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sr.citizen included in medical insurance premium?(Y or N)</td>
			<td><input type="text" size="10" name="sscip" id="sscip" readonly="readonly"></td>
		</tr>   
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sr.citizen included in medical treatment u/s 80DDB?(Y or N)</td>
			<td><input type="text" size="10" name="sscmt" id="sscmt " readonly="readonly"></td>
		</tr>  
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dependents have permanent physical disability(>80%)?(Y or N)</td>
			<td><input type="text" size="10" name="sdpd" id="sdpd" readonly="readonly"></td>
		</tr> 
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Have permanent physical disability?(Y or N)</td>
			<td><input type="text" size="10" name="sppd" id="sppd" readonly="readonly" ></td>
		</tr> 
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Is the permanent physical disability severe(>80%)?(Y or N)</td>
			<td><input type="text" size="10" name="spd" id="spd" readonly="readonly" ></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Do you live in Company provided accommodation?(Y or N)</td>
			<td><input type="text" size="10" name="sca" id="sca" readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If live in co. acco, population of your city?(Y or N)</td>
			<td><input type="text" size="10" name="spc" id="spc" readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Does compnay bear running/maint expenses for car?(Y or N)</td>
			<td><input type="text" size="10" name="srme" id="srme" readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vehicle cubic capacity >1600cc?(Y or N)</td>
			<td><input type="text" size="10" name="svcc" id="svcc" readonly="readonly" ></td>
		</tr>
	<%} else { %>
	 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Metro/Non-metro(M or N)</td>
			<td><input type="text" size="10" name="smn" id="smn" value="<%=map.get(564)==1?"M":"N" %> " readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;HRA Exempt limit(of Basic+DA)</td>
			<td><input type="text" size="10" name="shel" id="shel" value="<%=map.get(564)==1?"50%":"40%" %>  " readonly="readonly"></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  PF/VPF Deductions</h4></font> </td></tr>
	           
	     <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PF as percentage of "salary"</td>
			<td><input type="text" size="10" name="spfps" id="spfps" value="12.00% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PF limited to (if there is a max. limit for deduction)</td>
			<td><input type="text" size="10" name="spfl" id="spfl" value="0 " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VPF as percentage of "salary"</td>
			<td><input type="text" size="10" name="svpps" id="svpps" value="0.00% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interest rate on PF/VPF</td>
			<td><input type="text" size="10" name="sirpv" id=" sirpv" value="8.50% " readonly="readonly"></td>
		</tr>
		
		 <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;VPF as "percentage of salary" or "Fixed amount"?(P or F)</td>
			<td><input type="text" size="10" name="ssfa" id="ssfa" value="P " readonly="readonly" ></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  For Housing Loan</h4></font> </td></tr>
	       
	      <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;House self-occupied?(Y or N)</td>
			<td><input type="text" size="10" name="shso" id="shos" value="<%=map.get(571)==1?"Y":"N" %> " readonly="readonly"></td>
		</tr>
		  
		   <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Loan taken after Apr1, 1999?</td>
			<td><input type="text" size="10" name="slap" id="slap" value="Y " readonly="readonly"></td>
		</tr>
		
		<tr bgcolor="#929292"><td colspan="4" ><font color="white"> <h4>  Miscellaneous</h4></font> </td></tr>
	       
	     <tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sr.citizen included in medical insurance premium?(Y or N)</td>
			<td><input type="text" size="10" name="sscip" id="sscip" value="N " readonly="readonly"></td>
		</tr>   
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sr.citizen included in medical treatment u/s 80DDB?(Y or N)</td>
			<td><input type="text" size="10" name="sscmt" id="sscmt " value="N " readonly="readonly"></td>
		</tr>  
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dependents have permanent physical disability(>80%)?(Y or N)</td>
			<td><input type="text" size="10" name="sdpd" id="sdpd" value="N " readonly="readonly"></td>
		</tr> 
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Have permanent physical disability?(Y or N)</td>
			<td><input type="text" size="10" name="sppd" id="sppd" value="N " readonly="readonly"></td>
		</tr> 
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Is the permanent physical disability severe(>80%)?(Y or N)</td>
			<td><input type="text" size="10" name="spd" id="spd" value="N " readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Do you live in Company provided accommodation?(Y or N)</td>
			<td><input type="text" size="10" name="sca" id="sca" value="N " readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If live in co. acco, population of your city?(Y or N)</td>
			<td><input type="text" size="10" name="spc" id="spc" value=" " readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Does compnay bear running/maint expenses for car?(Y or N)</td>
			<td><input type="text" size="10" name="srme" id="srme" value="N " readonly="readonly"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vehicle cubic capacity >1600cc?(Y or N)</td>
			<td><input type="text" size="10" name="svcc" id="svcc" value="N " readonly="readonly"></td>
		</tr>
		<%}}catch(Exception e){
			e.printStackTrace();

			%>
			<script type="text/javascript">

			window.location.href="salaryStructure.jsp";

			</script>
			<%
			}%>
		</table>
		</div>
		
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