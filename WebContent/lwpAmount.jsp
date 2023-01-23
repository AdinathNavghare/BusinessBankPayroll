<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.DeductBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.extra_duty_paymentBean"%>
<%@page import="payroll.DAO.ShiftHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LWP AMOUNT</title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
    <script type="text/javascript" src="js/date.js"></script>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />



<script type="text/javascript" src="js/datetimepicker.js"></script>
    <script type="text/javascript">
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'M-yy',
            onClose: function(dateText, inst) { 
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                $(':focus').blur();
                
            },
        });
        
    });
    
 
   
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
        
    </style>
  
<script type="text/javascript">
<%
int eNo = 0;
String action=request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action "+action);
ArrayList<extra_duty_paymentBean> LWPAMOUNTList = new ArrayList<extra_duty_paymentBean>();
ArrayList<extra_duty_paymentBean> defaultlist = new ArrayList<extra_duty_paymentBean>();
ShiftHandler shifthandler1=new  ShiftHandler();
defaultlist=shifthandler1.getAllExtraPayment("ALL");
String empName="",day="";
String empno="";
String EmpNo="";
String action_value="";
String reason="";
String date2="";
String date3="";
String total="";
if(action.equalsIgnoreCase("getdetails"))
{

	date3=(request.getParameter("date3")==null?"0":request.getParameter("date3"));
 	empno=(request.getParameter("empnoo")==null?"0":request.getParameter("empnoo"));
 	action_value=(request.getParameter("action_value")==null?"0":request.getParameter("action_value"));
 	reason=(request.getParameter("reason")==null?"0":request.getParameter("reason"));
	System.out.println("EmpNo1111...."+empno+"....date is..."+action_value);
	day = (request.getParameter("dayss")==null?"0":request.getParameter("dayss"));
	System.out.println("day1111...."+day);
	int total_days =Integer.parseInt(day);
	String employ[]=empno.split(":");
	 EmpNo = (employ[2]);
 	empName= employ[0];
	System.out.println("EmpNo "+employ[2]+"  "+employ[0]);


	ShiftHandler shifthandler=new  ShiftHandler();
	LWPAMOUNTList = shifthandler.LWPAMOUNT(date3,EmpNo,day,action_value);
	String string="";
}
%>

function getvalue()
{
	var empnoo = document.getElementById("EMPNO").value;
	var action_value = document.getElementById("action_value").value;
	var date3 = document.getElementById("date3").value;
	var reason = document.getElementById("reason").value;
	var dayss = document.getElementById("days").value;
	 
	if(empnoo=="")
	{
		alert("Please Select Employee...!");
	}
	 else if(dayss==""||dayss<=0)
		{
		alert("Please Insert Proper Days...!");
		}
	 else if(date3==""){
			alert("Please Select  Month...!");
	 }
	 else if(action_value=="0"||action_value==0||action_value==""){
			alert("Please Select  type...!");
	 }
	 else if(reason==""||reason==" "||reason=="  "||reason=="   "||reason=="    "){
			alert("Please Select  reason.... !");
	 }
	 else
		{
		window.location.href="lwpAmount.jsp?action=getdetails&date3="+date3+"&action_value="+action_value+"&reason="+reason+"&empnoo="+empnoo+"&dayss="+dayss;
		}
}

function extraAmtSave()
{  
	//var date = document.getElementById("trndt").value;
	var month = document.getElementById("month").value;
	var empnoo = document.getElementById("empno1").value;
	var empbasic = document.getElementById("basic").value;
	var empda = document.getElementById("da").value;
	var empvda = document.getElementById("vda").value;
	var trntype = document.getElementById("trntype").value;
	var dayss = document.getElementById("day").value;
	var pfcal_amt = document.getElementById("pftotal").value;
	var cal_amt = document.getElementById("total").value;
	var reason1 = document.getElementById("reason1").value;
	
	var HRA = document.getElementById("HRA").value;
	var MEDALLOW = document.getElementById("MEDALLOW").value;
	var EDUALLOW = document.getElementById("EDUALLOW").value;
	var CONVEYANCE = document.getElementById("CONVEYANCE").value;
	var SPECIAL = document.getElementById("SPECIAL").value;
	//alert(pfcal_amt+" "+cal_amt);
	
			try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response2="";
		 	
url="ShiftServlet?action=lwpamount_post&empno="+empnoo+"&basic="+empbasic+"&reason1="+reason1+"&da="+empda+"&vda="+empvda+"&day="+dayss+"&pfcal_amt="+pfcal_amt+"&cal_amt="+cal_amt+"&month="+month+"&trntype="+trntype+"&HRA="+HRA+"&MEDALLOW="+MEDALLOW+"&EDUALLOW="+EDUALLOW+"&CONVEYANCE="+CONVEYANCE+"&SPECIAL="+SPECIAL;
		
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
				
				response2 = xmlhttp.responseText;
				 if(response2=="save")
					{
					 alert("Record Saved Sucessfully...");
					window.location.href="lwpAmount.jsp";
						
					} 
				}
				
			};
		 
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
			}
			catch(e)
			{
				
			}
	
	
};


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
	
	
	
	
	
</script>


<script type="text/javascript" src="js/datetimepicker.js"></script>


<script type="text/javascript">



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



</head>
<body style="overflow: hidden;" onunload= "caldisplay();">
   

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> LWP AMOUNT</h1>
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


<table width="1150" border="1" >
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle"><font size="3" color="white"><b>LWP AMOUNT</b></font>&nbsp;</td></tr>
<tr><td align="center" valign="middle">
 
	<table   width="100%" height="20" id="customers" border="1" style="margin-top:10px">
	  <tr class="alt">
	  <td width="100%" height="10" align="left" bgcolor="#CCCCCC">
	      Employee ID :-  
		<input type="text" name="EMPNO" size="30" class="form-control" 
						id="EMPNO"  title="Enter Employee Id / Name " "> &nbsp;
						
		  Total Day's :- 
	<input type="text" align="center" name="days" style="width:2%" class="form-control" 
						id="days"  title="Enter total day's. " onkeypress=" return inputLimiter(event,'Numbers'); " > &nbsp;
		 
		 Basic Month  :-  
							 
							<input name="date3" id="date3" style="width:6%"  readonly="readonly" class="date-picker" placeholder="Select Basic"/>
								
		<!--  
		 Select Month :- 
							 
							<input name="date" id="date" style="width:6%"  readonly="readonly" class="date-picker" placeholder="Select month"/>
		 -->
						<input type="hidden" id="nopresent" value=""/>
	  					<input type="hidden" id="cnt" value="0"/>
	  					
	  					
	  	Transactions Type  :- 
					 
					<select name="action_value" id="action_value">
					     <option value="0" selected="selected">Select</option>
						 <option value="Credit">Credit</option>
						  <option value="Debit">Debit</option>
					</select>
						
		  Reason  :- 
	<input type="text" align="center" name="reason" size="40" class="form-control" 
						id="reason"  title="Enter reason. "  > &nbsp;
		 			 
					<label><input type="submit"  class="myButton" value="Submit" style="margin-left: 10px" onclick="getvalue()"></label>
					
					
					</td>				
  		</tr>
  		
  </table>

<br/>
<div align="left">
<table width="900" height="44" border="1" id="customers" align="center" style="margin-left: 114px;">
  <tr align="center" valign="middle" bgcolor="#CCCCCC">
  
  
  	<!-- <th width="48" align="center">FORDATE </th> -->
    <th width="130" align="center">EMPLOYEE NAME </th>
    <th width="70" align="center">BASIC_LWP </th>
    <th width="67" align="center">DA_LWP </th>
    <th width="89" align="center">VDA_LWP </th>
    <th width="60" align="center">DAY'S </th>
    <th width="80" align="center">TOTAL PF AMOUNT </th>
    <th width="80" align="center">TOTAL AMOUNT </th>
    <!--  <th width="50" align="center">STATUS </th> -->
   </tr>
   
   
</table>
</div>
<div style="height:250px; overflow-y:scroll; width:auto;" align="left">
 <table width="900" border="1" align="left" id="customers" style="margin-left: 114px;">  

	
			<%if(action.equalsIgnoreCase("getdetails")){
				if (!LWPAMOUNTList.isEmpty()) {
				for(extra_duty_paymentBean extrapay:LWPAMOUNTList){
					
					if(extrapay.getTotal()==0.00){%>
							<td align="center" width="870" valign="middle" class="alt"> <h3 style="color:red;">
				Records Are Not Present For Selected Month & Employee...!!! 
				</td><%}else{String fordate[]=extrapay.getTRNDT().split(","); %>
			<tr>
		<%--   <td  width="75" 	align="center"><font size="2"><%for(int i=0;i<fordate.length;i++){ %><%=fordate[i] %><br><%} %></font></td> --%>
	      <td  width="170"	align="left"><font size="2"><%=empName %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getBasic_lwp() %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getDA_lwp() %></font></td>
	      <td  width="114" 	align="center"><font size="2"><%=extrapay.getVDA_lwp() %></font></td>
	      <td  width="82" 	align="center"><font size="2"><%=day%></font></td>
	      <td  width="104"	align="center"><font size="2"><%=extrapay.getPf_amount()%></font></td>
	       <td  width="104"	align="center"><font size="2"><%=total.format("%.2f",extrapay.getTotal()) %></font></td>
		<%--   <td  width="66" 	align="center"><font size="2"><%=extrapay.getSTATUS()%></font></td> --%>	
		  
			<%-- <input type="hidden" id="trndt" value="<%=extrapay.getTRNDT()%>"/> --%>
			<input type="hidden" id="month" value="<%=extrapay.getMonth()%>"/>
			<input type="hidden" id="trntype" value="<%=action_value%>"/>
			<input type="hidden" id="reason1" value="<%=reason%>"/>
			 <input type="hidden" id="empno1" value="<%=EmpNo%>"/>
			<input type="hidden" id="ename" value="<%=empName%>"/>
			<input type="hidden" id="basic" value="<%=extrapay.getBasic_lwp()%>"/>
			<input type="hidden" id="da" value="<%=extrapay.getDA_lwp()%>"/>
			<input type="hidden" id="vda" value="<%=extrapay.getVDA_lwp()%>"/>
			<input type="hidden" id="HRA" value="<%=extrapay.getHRA_lwp()%>"/>
			<input type="hidden" id="MEDALLOW" value="<%=extrapay.getMEDALLOW_lwp()%>"/>
			<input type="hidden" id="EDUALLOW" value="<%=extrapay.getEDUALLOW_lwp()%>"/>
			<input type="hidden" id="CONVEYANCE" value="<%=extrapay.getCONVEYANCE_lwp()%>"/>
			<input type="hidden" id="SPECIAL" value="<%=extrapay.getSPECIAL_lwp()%>"/> 
			<input type="hidden" id="day" value="<%=day%>"/>
			<input type="hidden" id="pftotal" value="<%=extrapay.getPf_amount()%>"/> 
	        <input type="hidden" id="total" value="<%=total.format("%.2f",extrapay.getTotal())%>"/> 
<%System.out.println("hra "+ extrapay.getHRA_lwp());%>
			 </tr>  
			 <% }
					}
				}else{ %>
				<td align="center" width="870" valign="middle" class="alt"> <h3 style="color:red;">
				 Select Employee...!!! 
				</td>
				<%} 
				
			}else{%><td align="center" width="870" valign="middle" class="alt"><h3 style="color:red;">
					No Records Available!!!
					</td>
					<% 
				}%>
			
			
	
</table>
<!-- </form> -->
<tr align="center">
    <td colspan="8" valign="middle">
    	<%if(action.equalsIgnoreCase("getdetails")){ %>
      <label><input type="submit"  name="save"   class="myButton" value="Save" onClick="extraAmtSave()" /></label>
      <!-- <label><input type="button" name="Reset"  class="myButton" value="Reset " onClick="gohome()"  /></label> -->
      <%} %>
    
      
    <%--   <%if(!action.equalsIgnoreCase("getdetails")){ %> <a href="#" onclick="window.open('PostExtraPayment.jsp','_self')" ><b><u>Click  Here To Post/Delete Record</u></b></a> <%} %> --%>
    </td>
    </tr>
</div>

<br/>

<tr bgcolor=""><td align="center" valign="middle">&nbsp;</td></tr>

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

