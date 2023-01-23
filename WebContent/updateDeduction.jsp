<%@page import="payroll.Model.DeductBean"%>
<%@page import="payroll.DAO.DeductHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />

<script type="text/javascript" src="js/datetimepicker1.js"></script>

<title>Update Deduction</title>
<style type="text/css">
<!--
body,td,th {
	font-family: Georgia;
	font-size: 14px;
}
-->
</style>
<%
	int check=0;
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
	}
	catch(Exception e)
	{
		System.out.println("First Time Loading dialog");
	}
	CodeMasterHandler CMH = new CodeMasterHandler();
	ArrayList<CodeMasterBean> CMBList = CMH.getCDMAST(String.valueOf(2));  // Here 2 is for CDTYPE for Deductions
	
	String[] key= request.getParameter("key").split("-");
	int empno = Integer.parseInt(key[0]);
	int trncd= Integer.parseInt(key[1]);
	int srno= Integer.parseInt(key[2]);
	
	DeductHandler DH = new DeductHandler();
	DeductBean DB = DH.getSingleDed(empno, trncd, srno);
	
%>
<script type="text/javascript">

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		no=document.getElementById("empno").value;
		var flag = document.getElementById("chek").value;
		if(flag == 1)
		{
			alert("Record Updated Successfully!");
			//window.close();
			window.location.href="DeductionServlet?action=homeforDeduction&no="+no;
		}
		else if(flag==2)
		{
			alert("Error in Modification of Record");
		}
		
	}
	function updateValidation()
	{
		
		var selection=document.getElementById("select.trncd");
		var trncd=selection.options[selection.selectedIndex].value;
		if(document.getElementById("select.trncd").selectedIndex==0)
		 {
		 alert("Please Select Transaction Code");
		 document.getElementById("select.trncd").focus();
		 return false;
		 }
	 if(document.getElementById("subSysCode").value =="" && trncd!=205)
	 {
	 alert("Please Enter SubSystem Code");
	 document.getElementById("subSysCode").focus();
	 return false;
	 }
	 if(document.getElementById("acno").value =="")
	 {
	 alert("Please Enter Account Number");
	 document.getElementById("acno").focus();
	 return false;
	 }
	 if(document.getElementById("sancDate").value =="" || document.getElementById("sancDate").value =="dd-mmm-yyyy")
	 {
	 alert("Please Select Sanction  Date");
	 document.getElementById("sancDate").focus();
	 return false;
	 }
	 if((isNaN(document.getElementById("sancNo").value)||document.getElementById("sancNo").value =="" ) && trncd!=205)
	 {
	 alert("Please Enter Sanction  Number");
	 document.getElementById("sancNo").focus();
	 return false;
	 }
	 if(isNaN(document.getElementById("sacnAmt").value)||document.getElementById("sacnAmt").value =="")
	 {
	 alert("Please Enter Sanction Amount");
	 document.getElementById("sacnAmt").focus();
	 return false;
	 }
	 if((isNaN(document.getElementById("install").value)||document.getElementById("install").value =="") && trncd!=205)
	 {
	 alert("Please Enter Installment Amount");
	 document.getElementById("install").focus();
	 return false;
	 }
	 if((isNaN(document.getElementById("intRate").value)||document.getElementById("intRate").value =="")&& trncd!=205)
	 {
	 alert("Please Enter Intresr Rate");
	 document.getElementById("intRate").focus();
	 return false;
	 }
	 if((document.getElementById("startDate").value =="" || document.getElementById("startDate").value =="dd-mmm-yyyy")&& trncd!=205)
	 {
	 alert("Please Select Start  Date");
	 document.getElementById("startDate").focus();
	 return false;
	 }
	 if((document.getElementById("endDate").value =="" || document.getElementById("endDate").value =="dd-mmm-yyyy")&& trncd!=205)
	 {
	 alert("Please Select End  Date");
	 document.getElementById("endDate").focus();
	 return false;
	 }
	}
	
	function selectActive()
	{
		var status =document.getElementById("select.active").value;
		var trncode = document.getElementById("select.trncd");
		var trndisc = trncode.options[trncode.selectedIndex].text;
		if(status == "N")
		{
		var r=  confirm("Are you sure want close this  "+trndisc+"?");
		 if(r==true){
				var p = prompt("Type Yes To Allow Otherwise Type NO"); 
				 if(p=="yes" || p=="Yes" || p=="YES"){	
					 return true; 
				 }
				 else{
					 return false;
				 }
			 }
			 else{
					return false;
				}
		}
			
	}
	function getClose1()
	{
		no=document.getElementById("empno").value;
		window.location.href="DeductionServlet?action=homeforDeduction&no="+no;
		
	}
</script>
<script type="text/javascript">
function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890';}
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
	</script>

<script type="text/javascript">

function inputLimiterForFloatType(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890.';}
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
	
	
function calculateMonth(frm) {
	
	/* var amt = parseFloat(document.forms[frm]["loan_amt"].value);
	if(amt=="")
		{
		alert("Please enter The Loan Amount ");
		return false;
		} */
	
	var end = document.forms[frm]["EndDate"].value;
	var start = document.forms[frm]["startDate"].value;
	
	if(end=="")
		{
		document.getElementById("calBorder").style.display='none';
		}
	else
		{
		document.getElementById("calBorder").style.display='block';
		}
	
	
	if (start != "") {
		end = parseMyDate(end);
		start = parseMyDate(start);

		if (end > start) {

			months = end.getMonth() - start.getMonth()
					+ (12 * (end.getFullYear() - start.getFullYear()));

			if (end.getDate() < start.getDate()) {
				months--;
			}
			

			document.forms[frm]["pay_month"].value = months;
			 var mon = parseFloat(document.forms[frm]["pay_month"].value);
				if (mon == "0") {
					mon = 1;
					document.forms[frm]["pay_month"].value = "1";
				}	
		} else {
			alert("Please select valid date !");
			document.forms[frm]["EndDate"].value = "";
			document.forms[frm]["EndDate"].focus();
			
		}
	} else {
		alert("Please select start date !");
	
	}

}
	
function count_instalmnt(frm) {

	frm=frm.name;
	
	var amt = parseFloat(document.forms[frm]["loan_amt"].value);

	
	var perc = parseFloat(document.forms[frm]["loan_per"].value);
	if (perc == 0) {
		document.forms[frm]["month_inst"].value = amt;
	}
	var mon = parseFloat(document.forms[frm]["pay_month"].value);
	if (mon == "0") {
		mon = 1;
		document.forms[frm]["pay_month"].value = "1";
	}
	var oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
	var end = document.forms[frm]["EndDate"].value;
	var start = document.forms[frm]["startDate"].value;
	
	end = parseMyDate(end);
	start = parseMyDate(start);
	
	var Days = Math.ceil(((end.getTime() - start.getTime()) / (oneDay)));

		
	 {
		mon = Math.round(Days / 30);
		
		document.forms[frm]["pay_month"].value = mon;
		if(mon=="0"){
			mon = 1;
		document.forms[frm]["pay_month"].value = "1";	
		}
	}

	rate = perc / 100;
	var monthly = rate / 12;

	var payment = ((amt * monthly) / (1 - Math.pow((1 + monthly), -mon)));
	var total = payment * mon;
//	alert("amt    "+amt+"month   "+mon+"result     "+amt/mon);
	
	
	//var interest=total-amt;
	if (isNaN(payment.toFixed(2))) {
		document.forms[frm]["month_inst"].value = amt;
	} else {
		document.forms[frm]["month_inst"].value = Math.ceil( payment.toFixed(2));

	}
	
	if(perc==0)
	{document.forms[frm]["month_inst"].value = Math.ceil(amt/mon);}
	
}
	
</script>

</head>
<body onLoad="checkVal()">
<img src='images/Close.png' style='float:right;' title='Remove' onclick="getClose1()"><br>
<br><br><br><br><br><br><br>
	<div align="center" style="max-height:200px;overflow-y:scroll;margin-top: 0px; ">
	<form id="f1" name="f1" action="DeductionServlet?action=modify"
		method="post" onSubmit="return updateValidation()">
		<table width="681" border="0" align="center" id="customers">
			<tr class="alt">
				<td colspan="5" align="center">Update Deduction</td>
			</tr>
			<tr class="alt">
				<td width="126" align="right" valign="middle">Transaction Type
				</td>
				<td width="177" bgcolor="#FFFFFF"><label> <select
						name="select.trncd" id="select.trncd" style="width: 175px;">
							<option value="0" selected>Select</option>
							<%
						for (CodeMasterBean temp1:CMBList)
						{
							if(temp1.getTRNCD()==DB.getTRNCD())
							{
				%>
							<option value="<%=temp1.getTRNCD()%>" selected="selected"><%=temp1.getDISC()%></option>
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
				</label></td>
				<td  bgcolor="#CCCCCC">&nbsp;</td>
				<td width="122" align="right" >Cumulative </td>
 				<!--  <td height="18" align="right" valign="middle" >Cummulative</td> -->
    		<td >
     			 <select name="select.cuml" id="select.cuml" class="form-control"  width="182">
        		<option value="Y">YES</option>
        		<option value="N" selected="selected">NO</option>
      			</select>
  				</td>
				<%-- <td width="15" bgcolor="#CCCCCC">&nbsp;</td>
				<td width="114" align="right" bgcolor="#FFFFFF">Subsystem Code
				</td>
				<td width="183" bgcolor="#FFFFFF"><input type="text"
					name="subSysCode" id="subSysCode" value="<%=DB.getSUBSYS_CD()%>">
				</td> --%>
			</tr>
			<tr class="alt">
			 <% if(DB.getTRNCD()== 205){
			%>
				<td align="right" valign="middle">Policy Number</td>
				<td bgcolor="#FFFFFF"><input type="text" name="acno" id="acno"
					value="<%=DB.getAC_NO()%>" maxlength="15"></td>
				<%} 	
				
				 else{
			%>
				<td align="right" valign="middle">Account Number</td>
				<td bgcolor="#FFFFFF">
				
						<select name="branch" id="branch" >  
			    						<%
			    						  ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
			    						  EmpOffHandler eh = new EmpOffHandler();
			    						  ebn = eh.getprojectCode();
			 								for(EmpOffBean eopbn : ebn)
			 								{
			 								if(eopbn.getPrj_srno()==DB.getPrj_srno())
							{
				%>
							<option value="<%=eopbn.getPrj_srno()%>" selected="selected"><%=eopbn.getSite_name()%></option>
							<%
							}
							else
							{
				%>
							<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
							<%				
							}
					
			      							
			      								
			      									  
			     					 	  }%>
			     				</select>
				
				<input type="text" name="acno" id="acno"
					value="<%=DB.getAC_NO()%>" maxlength="15"></td>
				<%} %> 	
				<td bgcolor="#CCCCCC">&nbsp;</td>
				<td align="right" bgcolor="#FFFFFF">Sanction Date</td>
				<td bgcolor="#FFFFFF"><input name="sancDate" id="sancDate"
					type="text" value="<%=DB.getSANC_DATE() %>" readonly="readonly"
					onFocus="if(value=='dd-mmm-yyyy') {value=''}"
					onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
					src="images/cal.gif" align="middle"
					style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" /></td>
			</tr>
			<tr class="alt">
    		<td align="right" valign="middle" >Active</td>
    		<td >
      		<select name="select.active" id="select.active" class="form-control" onchange="selectActive()" >
      		<%    
      		String Status=DB.getACTYN();
      		if(Status.equalsIgnoreCase("Y"))
      		{
      		%>
      		<option value="Y" selected="selected">YES</option>
        	<option value="N">NO</option>
        	<% } 
      	   else
      		  { 
      	    %>
      		<option value="N" selected="selected">NO</option>
        	<option value="Y">YES</option>
        	<% } %>
      		</select>
 			</td>
			
			<%-- <tr class="alt">
				<td align="right" valign="middle" bgcolor="#FFFFFF">BOD
					Sanction No.</td>
				<td bgcolor="#FFFFFF"><input type="text" name="sancNo"
					id="sancNo" value="<%=DB.getBODSANCNO() %>"></td> --%>
				<td bgcolor="#CCCCCC">&nbsp;</td>
				<%-- <td align="right" bgcolor="#FFFFFF">Sanction Amount</td>
				<td bgcolor="#FFFFFF"><input type="text" name="sacnAmt"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"
					id="sacnAmt" value="<%=DB.getSANC_AMT() %>"></td> --%>
					
			<% if(DB.getTRNCD()== 205){
			%>
				<td align="right" bgcolor="#FFFFFF">Premium Amount</td>
				<td bgcolor="#FFFFFF"><input type="text" name="sacnAmt"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"
					id="sacnAmt" value="<%=DB.getSANC_AMT() %>"></td>
					</tr>
				<%} 	
				
				 else{
			%>
				<td align="right" bgcolor="#FFFFFF">Sanction Amount</td>
				<td bgcolor="#FFFFFF"><input type="text" name="sacnAmt"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"
					id="sacnAmt" value="<%=DB.getSANC_AMT() %>"></td>
					</tr>
				<%} %>
			
			
			<% if(DB.getTRNCD()!=205){
			%>
			<tr class="alt" id="installs">
				<td align="right" valign="middle" bgcolor="#FFFFFF">Installment
					Amt.</td>
				<td bgcolor="#FFFFFF"><input type="text" name="install"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"
					id="install" value="<%=DB.getAMOUNT() %>"></td>
				<td bgcolor="#CCCCCC">&nbsp;</td>
				<td align="right" bgcolor="#FFFFFF">Installments</td>
				<td bgcolor="#FFFFFF"><input type="text" name="intRate"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"
					id="intRate" value="<%=DB.getNo_Of_Installment() %>"></td>
			</tr>
			<tr class="alt" id="startdt">
				<td align="right" valign="middle" bgcolor="#FFFFFF">Start Date</td>
				<td bgcolor="#FFFFFF"><input name="startDate" id="startDate"
					type="text" value="<%=DB.getREPAY_START() %>" readonly="readonly"
					onFocus="if(value=='dd-mmm-yyyy') {value=''}"
					onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
					src="images/cal.gif" align="middle"
					style="vertical-align: middle; cursor: pointer;"
					onchange="calculateMonth('f1'),count_instalmnt(this.form)"
					onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" /></td>
				<td bgcolor="#CCCCCC">&nbsp;</td>
				<td align="right" bgcolor="#FFFFFF">End Date</td>
				<td bgcolor="#FFFFFF"><input name="endDate" id="endDate"
					type="text" value="<%=DB.getEND_DATE() %>" readonly="readonly"
					onFocus="if(value=='dd-mmm-yyyy') {value=''}"
					onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
					src="images/cal.gif" align="middle"
					style="vertical-align: middle; cursor: pointer;"
					onchange="calculateMonth('f1'),count_instalmnt(this.form)"
					onClick="javascript:NewCssCal('endDate', 'ddmmmyyyy')" /></td>
			</tr>
			<tr class="alt" id="system">
			<!-- <td width="15" bgcolor="#CCCCCC">&nbsp;</td> -->
				<td width="114" align="right" bgcolor="#FFFFFF">Subsystem Code
				</td>
				<td width="183" bgcolor="#FFFFFF"><input type="text"
					name="subSysCode" id="subSysCode" value="<%=DB.getSUBSYS_CD()%>">
				</td>
			
				<%-- <td height="18" align="right" valign="middle" bgcolor="#FFFFFF">Cummulative</td>
				<td bgcolor="#FFFFFF"><select name="select.cuml"
					id="select.cuml">
						<%
      		if((DB.getCUMUYN()==null?"N":DB.getCUMUYN()).equalsIgnoreCase("Y"))
      		{
      	%>
						<option value="Y" selected="selected">YES</option>
						<option value="N">NO</option>
						<%
      		}
      		else
      		{
        %>
						<option value="Y">YES</option>
						<option value="N" selected="selected">NO</option>

						<%
      		}
        %>
				</select></td> --%>
				<%-- <td bgcolor="#CCCCCC">&nbsp;</td>
				<td align="right" bgcolor="#FFFFFF">Active</td>
				<td bgcolor="#FFFFFF">
				<select name="select.active" id="select.active"  onchange="selectActive()">
						<%
      		if(DB.getACTYN().equalsIgnoreCase("Y"))
      		{
      	%>
						<option value="Y" selected="selected">YES</option>
						<option value="N">NO</option>
						<%
      		}
      		else
      		{
        %>
						<option value="Y">YES</option>
						<option value="N" selected="selected">NO</option>

						<%
      		}
        %>
				</select></td> --%>
				<td height="18" align="right" valign="middle" bgcolor="#FFFFFF">
				<td align="right" valign="middle" bgcolor="#FFFFFF">BOD
					Sanction No.</td>
				<td bgcolor="#FFFFFF"><input type="text" name="sancNo"
					id="sancNo" value="<%=DB.getBODSANCNO() %>"></td>
			</tr>
			<%} %>
			<tr align="center" class="alt">
				<td colspan="5" valign="middle"><label><input
						type="submit" name="save" value="Update" /> </label> <label><input
						type="button" name="clear" value="Cancel" onClick="getClose1()" />
				</label></td>
			</tr>
		</table>
		<input type="hidden" value="<%=empno%>" name="empno" id="empno">
		<input type="hidden" value="<%=trncd%>" name="trncd" id="trncd">
		<input type="hidden" value="<%=srno%>" name="srno" id="srno">
		<input type="hidden" value="<%=check%>" name="chek" id="chek">
	</form></div>
</body>
</html>