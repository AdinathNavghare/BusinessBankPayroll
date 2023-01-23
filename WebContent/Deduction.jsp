<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.DeductBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.DeductHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Deduction Management</title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

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
		 document.getElementById("addrec").style.display='';
		
		}
	function tagname()
	{    var tag = document.getElementById("select.trncd").value;
	     if(parseInt(tag)==205)//lic
	    	 {
		      document.getElementById('tag1').innerHTML = "Policy Number";
		      document.getElementById('sanction').innerHTML = "Premium Amount";
		      document.getElementById('installs').readOnly = false;
		      document.getElementById('startdt').style.display='none';
		      document.getElementById('installs').style.display='none';
		      document.getElementById('system').style.display='none';
		      
	    	 }
	     else
	    	 {
	    	 document.getElementById('tag1').innerHTML = "Account Number";
	    	 document.getElementById('sanction').innerHTML = "Sanction Amount";
	    	 document.getElementById('installs').readOnly = true;
	    	 document.getElementById('startdt').style='table-row';
	    	 document.getElementById('installs').style='table-row';
	    	 document.getElementById('system').style='table-row';
	    	 }
	}
	 function DeductValidation()
	 {
		 var EMPNO = document.getElementById("empno").value;
         var TRNCD = parseInt(document.getElementById("select.trncd").value);
        
			if (document.getElementById("empno").value == "") {
				alert("Please Insert Employee Name");
				document.getElementById("empno").focus();
				return false;
			}
			
			if(document.getElementById("select.trncd").selectedIndex==0)
			 {
			 alert("Please Select Transaction Code");
			 document.getElementById("select.trncd").focus();
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
			 if(isNaN(document.getElementById("sacnAmt").value)||document.getElementById("sacnAmt").value =="")
			 {
			 alert("Please Enter Sanction Amount");
			 document.getElementById("sacnAmt").focus();
			 return false;
			 }
			
			 
	if(TRNCD!=205)
	{
		
		  if(document.getElementById("subSysCode").value =="")
		 {
		 alert("Please Enter SubSystem Code");
		 document.getElementById("subSysCode").focus();
		 return false;
		 } 
		
		  if(isNaN(document.getElementById("sancNo").value)||document.getElementById("sancNo").value =="")
		 {
		 alert("Please Enter Sanction  Number");
		 document.getElementById("sancNo").focus();
		 return false;
		 } 
		 
		  if(document.getElementById("startDate").value =="" || document.getElementById("startDate").value =="dd-mmm-yyyy")
		 {
		 alert("Please Select Start  Date");
		 document.getElementById("startDate").focus();
		 return false;
		 }
		 if(document.getElementById("endDate").value =="" || document.getElementById("endDate").value =="dd-mmm-yyyy")
		 {
		 alert("Please Select End  Date");
		 document.getElementById("endDate").focus();
		 return false;
		 }
		 var startDate = document.getElementById("startDate").value;
	     var endDate = document.getElementById("endDate").value;
		  
			var d1 = new Date(startDate);
			var d2 = new Date(endDate);  
			
			if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\tInvalid Date Range!\n FromDate can't be greater than TODate!");
				   document.getElementById("endDate").focus();
				   return false;
			  }
		 if(isNaN(document.getElementById("install").value)||document.getElementById("install").value =="")
		 {
		 alert("Please Enter Installment Amount");
		 document.getElementById("install").focus();
		 return false;
		 }
		 if(isNaN(document.getElementById("Installments").value)||document.getElementById("Installments").value =="")
		 {
		 alert("Please Enter Installments");
		 document.getElementById("intRate").focus();
		 return false;
		 } 
	   }
	 }
	 function calculate(){
		var months;
		var startDate = document.getElementById("startDate").value;
	    var endDate = document.getElementById("endDate").value;
	    var principal = parseInt(document.getElementById("sacnAmt").value);
	    //var rate = document.getElementById("intRate").value;
		var d1 = new Date(startDate);
		var d2 = new Date(endDate);  
		months = ((d2.getFullYear() - d1.getFullYear()) * 12)+(d2.getMonth() - d1.getMonth());
		if (d1.getTime() > d2.getTime())
		 {
			   alert("\t\tInvalid Date Range!\n FromDate can't be greater than TODate!");
			   document.getElementById("endDate").focus();
			   return false;
		  }
		//rate = rate/100;
		//var monthly = rate/12;
		//var payment = ((prinipal*monthly)/(1-Math.pow((1+monthly),-months)));
		//var payment = (principal/months).toFixed(2);
	
		
		/*  commented by akshay nikam , according to malpani sir requirment ,for flexibility of installment amt 
		 var payment = (principal/months).toFixed(2);
		document.getElementById("install").value = payment;
		document.getElementById("Installments").value = months; */
		 
	 }
	
</script>


<script type="text/javascript" src="js/datetimepicker.js"></script>

<%	
int licflag=0 ;
LookupHandler lkp = new LookupHandler();
	CodeMasterHandler CMH = new CodeMasterHandler();
	ArrayList<CodeMasterBean> CMBList = CMH.getCDMAST(String.valueOf(2));
	ArrayList<DeductBean> Dlist = new ArrayList<DeductBean>();
	LeaveMasterHandler obj=new LeaveMasterHandler();
 
	String empcd="";
	String empno="";
	String ename="";
	String action="";
	try
	{
		 action=request.getParameter("action");
		 System.out.println("action..."+action);
		if(action.equalsIgnoreCase("showList"))
		{
			Dlist = (ArrayList<DeductBean>)session.getAttribute("Dlist");
			empno = request.getParameter("empno");
			empcd = obj.getempCode(Integer.parseInt(empno));
			ename = lkp.getLKP_Desc("ET", Integer.parseInt(empno));
			System.out.println("///////////////"+Dlist.size());
		}
	}
	catch(Exception e)
	{
		
	}
	

%>
<script type="text/javascript">
var no="";
	function deleteRec(key)
	{
		var flag= confirm("Are you sure to delete this Record?");
		if(flag)
		{	
			window.location.href="DeductionServlet?action=delete&key="+key;
		}
	}
	function modifyRec(key,active)
	{
		if(active == "N")
			{
				alert("You can not modify this record...");
				return false;
			}
		else
			{
			 /* window.showModalDialog("updateDeduction.jsp?key="+key,null,"dialogWidth:690px; dialogHeight:400px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
			 document.form1.submit(); */
			 no=document.getElementById("empno").value;
			  document.getElementById("myModal").style.display = 'Block';
			  $("#myModal").load("updateDeduction.jsp?key="+key);
			  $("#myModal").fadeTo('slow', 0.9);
			  parent_disable();
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
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
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

</head>
<body style="overflow: hidden;">
   

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Deduction Maintenance</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" >
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

<h3>Deduction Management</h3>
<table width="950" border="1">
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="middle">
   <form action="DeductionServlet?action=list" method="post" name="form1" id="form1" onsubmit="return TakeCustId()">
	<table  height="26" id="customers" border="1" style="margin-top:10px">
	  <tr class="alt">
		<td width="84" height="20" align="left" bgcolor="#CCCCCC">Employee ID </td>
		<!-- <td align="left" valign="middle" bgcolor="#FFFFFF">
  			<input type="text" name="empno1" id="empno1" />
  			<label><input type="submit" value="Get List"></label>
  		</td> -->
  		<td width="80%"><input type="text" name="EMPNO" size="40" class="form-control" 
						id="EMPNO" onClick="showHide()" title="Enter Employee Id / Name "> &nbsp;
						<label><input type="submit"  class="myButton" value="Get List" style="margin-left: 10px"></label></td>
  		</tr>
  		<%if((action==null?"":action).equalsIgnoreCase("showList")){ %>
  		<tr class="alt" >
		<td width="84" align="center" bgcolor="#CCCCCC"><%= empcd%></td>
		<td width="273" align="left" valign="middle" bgcolor="#FFFFFF"> <%= ename%> </td>
	    
	</tr>
	<%} %>
  </table>
</form>
<br/>
<div align="left">
<table width="900" height="44" border="1" id="customers" align="center" style="margin-left: 15px;">
  <tr align="center" valign="middle" bgcolor="#CCCCCC">
    <th width="20">Sr.NO.</th>
    <th width="150">Transaction Type</th>
    <th width="70">Account Number</th>
    <th width="67">Installment Amount</th>
    <th width="89">Start Date</th>
    <th width="91">End Date</th>
    <th width="60">Actual Amt Paid</th>
    <th width="46">No Of Install/Install Left</th>
    <th width="46">Status</th>
	 <th width="100">Operation</th>
   </tr>
</table>
</div>
<div style="height:250px; overflow-y:scroll; width:auto;" align="left">
<table width="900" border="1" align="left" id="customers" style="margin-left: 15px;">  
<!-- <table width="781" border="1" align="left" id="customers" style="margin-left: 20px;"> -->  
 <%
 	if(Dlist.size()>0)
 	{
 		int i=0;       
 		for(DeductBean DB: Dlist)
 		{
 			i++;
 			String str=DB.getEMPNO()+"-"+DB.getTRNCD()+"-"+DB.getSRNO();
 %>  	
  		<tr align="center" valign="middle" class="alt">
    		<td bgcolor="#F0F0F0" width="43"><%=i %></td>
    		<td width="130"><%=CMH.getCDesc(DB.getTRNCD()) %></td>
    		<td width="69"><%=DB.getAC_NO() %></td>
    		<td width="65"><%=DB.getAMOUNT() %></td>
    		<td width="75"><%=DB.getREPAY_START() %></td>
    		<td width="75"><%=DB.getEND_DATE() %></td>
    		<td width="60"><%=DB.getActual_amt_paid() %></td>
    		<td width="77"><%=DB.getNo_Of_Installment() %></td>
    	    <td width="47"><%=DB.getACTYN().equalsIgnoreCase("Y")?"OPEN":"CLOSED"%></td>
			<td align="center" valign="middle" width="90">
			<input type="button" name="modify" value="Modify" onClick="modifyRec('<%=str %>','<%=DB.getACTYN() %>')" class="myButton"
				style="margin-right: 0px;">
			  <%-- <input type="button" name="delete" value="Delete" onClick="deleteRec('<%=str %>')" class="myButton"
			 	style="margin-left: 10px;"> --%>
		    </td>
  		</tr>
<%
 		}
 	} else {
%>
<tr><td align="left" width="870" valign="middle" class="alt">No Records Available</td></tr>
<%} %>
</table>
</div>
<br/>

<tr bgcolor="#1F5FA7"><td align="center" valign="middle">&nbsp;</td></tr>
<%if((action==null?"":action).equalsIgnoreCase("showList")){ %>
<tr id="addrec" ><td align="center" valign="middle">
<!-- <td align="center" valign="middle" id="customerss" style="display:none"> -->
<h4>Add Deduction</h4>
All fields are Mandatory
<form action="DeductionServlet?action=addnew" method="post" onsubmit="return DeductValidation()">
<table width="681" border="2" id="customers">
  <tr class="alt">
    <td width="120" align="right" valign="middle" >Transaction Type </td>
    <td width="179" >
    <select name="select.trncd" id="select.trncd" class="form-control"  style="width: 175px;"onchange="tagname();">
    <option value="0" selected>Select</option>
    			<%
						for (CodeMasterBean temp1:CMBList)
						{
				%>			
							<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
							
   				<%   	}
				%>
				
				
				
    </select>
 <%--    <input type="hidden" id="trncode" name="trncode" value="<%=temp1.getTRNCD() %>"> --%>
 </td>
	<td width="12" bgcolor="#CCCCCC">&nbsp;</td>
	<td width="122" align="right" >Cumulative </td>
 <!--  <td height="18" align="right" valign="middle" >Cummulative</td> -->
    <td >
      <select name="select.cuml" id="select.cuml" class="form-control"  width="182">
        <option value="Y">YES</option>
        <option value="N" selected="selected">NO</option>
      </select>
  </td>
 
 
    <!-- <td width="12" bgcolor="#CCCCCC">&nbsp;</td>
    <td width="122" align="right" >Subsystem Code </td>
    <td width="182" ><input type="text" name="subSysCode" class="form-control"  id="subSysCode"> </td> -->
  </tr>
  <tr class="alt">
  <td id="tag1" align="right" valign="middle" >Account Number </td>
  
  <td>
     			      			<select name="branch" id="branch" >  
      	    						<%
			    						  ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
			    						  EmpOffHandler eh = new EmpOffHandler();
			    						  ebn = eh.getprojectCode();
			 								for(EmpOffBean eopbn : ebn)
			 								{%>
			      									<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>  
			     					 	  <%}%>
			     				</select>
     			      <!-- 		</td>
               <td > -->
               <input type="text" name="acno" class="form-control"  id="acno" maxlength="15"></td>
   <!--  <td ><input type="text" name="acno" class="form-control"  id="acno" maxlength="15"></td> -->
    <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" >Sanction Date </td>
    <td ><input name="sancDate" id="sancDate"  class="form-control" type="text" value="yyyy-mmm-dd" onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('sancDate', 'yyyymmdd')" /></td>
  </tr>
  <tr class="alt">
    <td align="right" valign="middle" >Active</td>
    <td >
      <select name="select.active" id="select.active" class="form-control" >
        <option value="Y" selected="selected">YES</option>
        <option value="N">NO</option>
      </select>
 </td>
  
    <!-- <td align="right" valign="middle" >BOD Sanction No.</td>
    <td ><input type="text" name="sancNo" id="sancNo" class="form-control" > </td> -->
    <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" id="sanction" >Sanction Amount </td>
    <td ><input type="text" name="sacnAmt" class="form-control"  id="sacnAmt" onkeypress="return inputLimiter(event,'Numbers')"></td>
  </tr>
  <tr class="alt" id="startdt" >
    <td align="right" valign="middle" >Start Date</td>
    <td ><input name="startDate" id="startDate" type="text" class="form-control"  value="yyyy-mmm-dd" onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}" >&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('startDate', 'yyyymmdd')" /></td>
    <td>&nbsp;</td>
    <td align="right">End Date</td>
    <td ><input name="endDate" id="endDate" type="text" class="form-control"  value="yyyy-mmm-dd" onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}" onchange="return calculate()">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('endDate', 'yyyymmdd')" /></td>
  </tr>
  <tr class="alt" id="installs" >
  	<td align="right" >Total Installments </td>
    <td ><input type="text" name="Installments" id="Installments"  class="form-control" onkeypress="return inputLimiter(event,'Numbers')" ></td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" valign="middle" >Installment Amt. </td>
    <td ><input type="text" name="install" id="install" class="form-control" onkeypress="return inputLimiter(event,'Numbers')"  ></td>
  </tr>
  
  <tr class="alt" id="system" >
  
 
    <td height="18" align="right" valign="middle" >Subsystem Code </td>
    <td width="182" ><input type="text" name="subSysCode" class="form-control"  id="subSysCode"> </td> 
  
    <!-- <td height="18" align="right" valign="middle" >Cummulative</td>
    <td >
      <select name="select.cuml" id="select.cuml" class="form-control" >
        <option value="Y">YES</option>
        <option value="N" selected="selected">NO</option>
      </select>
  </td> -->
  <td bgcolor="#CCCCCC">&nbsp;</td>
  <td align="right" valign="right" >BOD Sanction No.</td>
    <td ><input type="text" name="sancNo" id="sancNo" class="form-control" > </td>
  
  
    <!-- <td bgcolor="#CCCCCC">&nbsp;</td>
    <td align="right" >Active</td>
    <td >
      <select name="select.active" id="select.active" class="form-control" >
        <option value="Y" selected="selected">YES</option>
        <option value="N">NO</option>
      </select>
 </td> -->
  </tr>
  <tr align="center" class="alt">
    <td colspan="5" valign="middle">
      <label><input type="submit"  name="save"   class="myButton" value="Save" /></label>
      <label><input type="reset" name="clear"  class="myButton" value="Clear"   /></label>
    </td>
    </tr>
</table>
<input type="hidden" value="<%=empno%>" name="empno" id="empno">
</form>
<br/>
</td></tr> <%} %>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>

</table>

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
