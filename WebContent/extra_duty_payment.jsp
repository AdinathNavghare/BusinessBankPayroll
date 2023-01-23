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
<title>Extra Duty Payment </title>
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
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    
                }
            }
        });
        
    });
    
    function CheckDemo()
    {
    	
    	Cal.SwitchMth($("#ui-datepicker-div .ui-datepicker-month :selected").val());
    	
    	Cal.SwitchYear($("#ui-datepicker-div .ui-datepicker-year :selected").val());
    	RenderCssCal();
    
    	document.getElementById("datemonthdisable").disabled=true;
    	document.getElementById("dateyrdisable").disabled=true;
    	
    	//document.getElementsByName("MonthSelector").setAttribute("disabled", "disabled");
      	//document.getElementsByName("MonthSelector").disabled="disabled";DP_jQuery.datepicker._hideDatepicker
    	
    }
   
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
ArrayList<extra_duty_paymentBean> extraDutyPayList = new ArrayList<extra_duty_paymentBean>();
ArrayList<extra_duty_paymentBean> defaultlist = new ArrayList<extra_duty_paymentBean>();
ShiftHandler shifthandler1=new  ShiftHandler();
defaultlist=shifthandler1.getAllExtraPayment("ALL");
String empName="",day="";
String empno="";
String EmpNo="";
String date="";
String date2="";
String date3="";
String total="";
if(action.equalsIgnoreCase("getdetails"))
{

	date=(request.getParameter("date")==null?"0":request.getParameter("date"));
	date2=(request.getParameter("date2")==null?"0":request.getParameter("date2"));
	date3=(request.getParameter("date3")==null?"0":request.getParameter("date3"));
 	empno=(request.getParameter("empnoo")==null?"0":request.getParameter("empnoo"));
	System.out.println("EmpNo1111...."+empno+"....date is..."+date);
	day = (request.getParameter("dayss")==null?"0":request.getParameter("dayss"));
	System.out.println("day1111...."+day);
	int total_days =Integer.parseInt(day);
	String employ[]=empno.split(":");
	 EmpNo = (employ[2]);
 	empName= employ[0];
	System.out.println("EmpNo "+employ[2]+"  "+employ[0]);


	ShiftHandler shifthandler=new  ShiftHandler();
	extraDutyPayList = shifthandler.getExtraPaymentList(date,EmpNo,day,date2,date3);
	String string="";
}
%>
function caldisplay()
{ 
	document.getElementById("datemonthdisable").disabled=false;
	document.getElementById("dateyrdisable").disabled=false;
	
	
}
function getvalue()
{
	var empnoo = document.getElementById("EMPNO").value;
	var date = document.getElementById("date").value;
	var date2 = document.getElementById("date2").value;
	var date3 = document.getElementById("date3").value;
	var dayss = document.getElementById("days").value;
	var chkcnt=document.getElementById("cnt").value;
	//alert("finally cntr.."+chkcnt);
	/* document.getElementById("EMPNO").value="";
	document.getElementById("date").value="";
	document.getElementById("days").value="";
	 */if(empnoo=="")
	{
		alert("Please Select Employee...!");
	}
	 else if(dayss==""||dayss<=0)
		{
		alert("Please Insert Proper Days...!");
		}
	 else if(date3==""){
			alert("Please Select Basic Month...!");
	 }
	 else if(date==""){
		alert("Please Select Date...!");
	}
	 else if(parseInt((chkcnt))<parseInt(dayss))
	{
		alert("Some Dates Are Misssing As You Entered "+dayss+" Day's...!");
	}
	
	 else
		{
		window.location.href="extra_duty_payment.jsp?action=getdetails&date3="+date3+"&date2="+date2+"&date="+date+"&empnoo="+empnoo+"&dayss="+dayss;
		}
}

function extraAmtSave()
{
	
	var date = document.getElementById("trndt").value;
	var month = document.getElementById("month").value;
	var empnoo = document.getElementById("empno1").value;
	
	var empbasic = document.getElementById("basic").value;
	
	var empda = document.getElementById("da").value;
	
	var empvda = document.getElementById("vda").value;
	
	var dayss = document.getElementById("day").value;
	
	var cal_amt = document.getElementById("total").value;
	
	 try
		{
		 var fg1="";
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
	 	
	 	url="ShiftServlet?action=checkrecord&empno="+empnoo;

		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
			
			response2 = xmlhttp.responseText;
			
			if(response2=="present")
				{
 				alert("There is already pending record to post...\n Either post it or cancel it & then recalculate again...");
 				fg1="false";
				document.getElementById("nopresent").value=fg1;
				//return false;
				}
			else if(response2=="nopresent")
				{
				//alert(response2);
				fg1="true";
				document.getElementById("nopresent").value=fg1;
				chk();
				}
			/* document.getElementById("date").value=response2; */
			
			}
			
		};
	 
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
		}
		catch(e)
		{
			
		}  
		
		
		
		function chk(){
	var yesno=document.getElementById("nopresent").value;	
	
	  if(yesno=="false")
		{
			//alert("There is already pending record to post...\n Either post it or cancel it & then recalculate again...");

			return false;
		}
	else  if(yesno=="true"){
	
	
	
			try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response2="";
		 	
		 	url="ShiftServlet?action=addextra_duty_time&date="+date+"&empno="+empnoo+"&basic="+empbasic+"&da="+empda+"&vda="+empvda+"&day="+dayss+"&calamt="+cal_amt+"&month="+month;
		
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
				
				response2 = xmlhttp.responseText;
				 if(response2=="save")
					{
					 alert("Record Saved Sucessfully...");
					window.location.href="extra_duty_payment.jsp";
						
					} 
				}
				
			};
		 
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
			}
			catch(e)
			{
				
			}
	}
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

function showrecord()
{
	//window.showModalDialog("SalaryDetails.jsp?action=details&proj=all&date=31-Jul-2017&rng=0&eno="+empno+"&date="+date,null,"dialogWidth:860px; dialogHeight:420px; scroll:off; status:off;dialogLeft:350px;dialogTop:200px; ");
	window.open("PostExtraPayment.jsp ","_self");
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
	
	function checkdate1()
	{
		var emp=document.getElementById("EMPNO").value;
		
		
		var dy=document.getElementById("days").value;
		var cnt=document.getElementById("cnt").value;
		var month=document.getElementById("date2").value;
	
		if(emp=="")
		{
			alert("First Select The Employee...!");
			return false;
			} 
		else if(month=="")
		{
			alert("First Select Month...!");
			return false;
			}
		else if(dy==""){
			alert("First Enter Total Day's...");
			return false;
		}
			else{
				/* for(var i=0;i<parseInt(dy);i++)
					{
					alert("select date no "+(parseInt(i)+1));
					return true;
					} */
					
				setdate();
					return true;
			}
	}
	
	function reset()
	{
		document.getElementById("cnt").value="0";
		document.getElementById("date").value="";
		document.getElementById("date1").value="";
	}
	
	function gohome()
	{
		window.location.href="extra_duty_payment.jsp";	
	}
	
	function setdate(){
		var emp=document.getElementById("EMPNO").value;
		if(emp=="")
			{
			alert("First Select The Employee...!");
			return false;
			}
		
		else
			{
				
				
				var rcnt=document.getElementById("cnt").value;
				var seldatecnt=document.getElementById("date").value;
				var cnt1=seldatecnt.split(",");
				var daycnt=document.getElementById("days").value;
				if(parseInt(rcnt)<parseInt(daycnt)){
					var d=document.getElementById("date1").value;
					var fg="";
					
					if(d!==""){
					
					 try
					{
					
					var xmlhttp=new XMLHttpRequest();
				 	var url="";
				 	var response2="";
				 	url="ShiftServlet?action=checkdate&empno="+emp+"&date="+document.getElementById("date1").value;
		
					xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{
						
						response2 = xmlhttp.responseText;
						if(response2=="present")
							{
								alert("Already calculation is done for this date you cant select this date again...");
								fg="false";
								return false;
							}
						else if(response2=="nopresent")
							{
								fg="true";
								if((parseInt(rcnt)==0 || rcnt=="0") &&(parseInt(rcnt)!==parseInt((daycnt)-1)))
								{
									
									var dt=document.getElementById("date1").value;
									
									document.getElementById("date").value=dt+",";
									document.getElementById("date1").value="";
									rcnt++;
									document.getElementById("cnt").value=rcnt;
									return true ;
									
									
								
								}
							else
								{
									
									var alldate=document.getElementById("date").value;
									var dt1=document.getElementById("date1").value;
									var dates=alldate.split(",");
									
										//	if(dates.indexOf(dt1)>-1)
											//	{
											//	alert("Already Selected This Date..");
											//	 }
										//	else{
													if(parseInt(rcnt)==(parseInt(daycnt)-1))
														{
															document.getElementById("date").value=document.getElementById("date").value+dt1;
															document.getElementById("date1").value="";
															rcnt++;
															document.getElementById("cnt").value=rcnt;
														}
													else{
															document.getElementById("date").value=document.getElementById("date").value+dt1+",";
															document.getElementById("date1").value="";
															rcnt++;
															document.getElementById("cnt").value=rcnt;
														}
										 
									//	}
									
									
									
								}
							}
						 
						}
						
					};
				 
					xmlhttp.open("POST", url, true);
					xmlhttp.send();
					}
					catch(e)
					{
						
					} 
					
					}
					
					//alert("this is.."+fg);
					/* if(fg=="false")
						{
						return false;
						}
						
					else if(fg=="true"){ */
						/* if(parseInt(rcnt)==0 || rcnt=="0")
							{
								
								//document.getElementById("date1").focus();
								var dt=document.getElementById("date1").value;
								//document.getElementById("date1").value="";
								
								document.getElementById("date").value=dt+",";
								document.getElementById("date1").value="";
								rcnt++;
								document.getElementById("cnt").value=rcnt;
								return true ;
								//document.getElementById("date").value=document.getElementById("date1").value+",";
								
								
							
							}
						else
							{
								//alert("Select the date no"+rnct+1);
								//document.getElementById("date1").focus();
								//var flg="";
								//var flgcnt=0;
								var alldate=document.getElementById("date").value;
								var dt1=document.getElementById("date1").value;
								var dates=alldate.split(",");
								
										if(dates.indexOf(dt1)>-1)
											{
											alert("Already Selected This Date..");
											//flg="back";
											//flgcnt++;
											
											//alert("prvious selcteddtcnt.."+document.getElementById("cnt").value);
											//document.getElementById("cnt").value=parseInt(document.getElementById("cnt").value)-flgcnt;
											//alert("updated selcteddtcnt.."+document.getElementById("cnt").value);
											}
										else{
												if(parseInt(rcnt)==(parseInt(daycnt)-1))
													{
														document.getElementById("date").value=document.getElementById("date").value+dt1;
														document.getElementById("date1").value="";
														rcnt++;
														document.getElementById("cnt").value=rcnt;
													}
												else{
														document.getElementById("date").value=document.getElementById("date").value+dt1+",";
														document.getElementById("date1").value="";
														rcnt++;
														document.getElementById("cnt").value=rcnt;
													}
									//return true ;
									}
								
								
								
							} */
						
					//}
				
				}
			}
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
		<h1> Extra Duty Payment</h1>
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
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle"><font size="3" color="white"><b>Extra Duty Payment</b></font>&nbsp;</td></tr>
<tr><td align="center" valign="middle">
 
	<table   width="100%" height="20" id="customers" border="1" style="margin-top:10px">
	  <tr class="alt">
	  <td width="100%" height="10" align="left" bgcolor="#CCCCCC">Employee ID:- 
		<input type="text" name="EMPNO" size="30" class="form-control" 
						id="EMPNO"  title="Enter Employee Id / Name " "> &nbsp;
						
		  Total Day's:-
		<!-- <input type="text" align="center" name="days" size="10" class="form-control" 
						id="days"  title="Enter total day's. " onkeypress=" setdate(); return inputLimiter(event,'Numbers'); "> &nbsp;
		 --><input type="text" align="center" name="days" style="width:2%" class="form-control" 
						id="days"  title="Enter total day's. " onkeypress=" reset(); return inputLimiter(event,'Numbers'); " > &nbsp;
		 
		 Basic Month:-
							 
							<input name="date3" id="date3" style="width:6%"  readonly="readonly" class="date-picker" placeholder="Select Basic"/>
						
	     Select Month:-
							 
							<input name="date2" id="date2" style="width:6%"  readonly="readonly" class="date-picker" placeholder="Select month"/>

	 	Selected Date:-
						
						<input name="date" size="40" maxlength="500" align="center" id="date" readonly="readonly" />
						&nbsp;<input type="hidden" name="date1" align="center" id="date1" value="" onchange="checkdate1();"/>
						<img src="images/cal.gif" align="middle" id="image1"
						style="vertical-align: middle; cursor: pointer;"
						onclick="javascript:NewCssCal('date1', 'ddmmmyyyy'); CheckDemo();  " />
						<!-- <input  name="date1"  id="date1" value=" " onblur="javascript:NewCssCal('date1', 'ddmmmyyyy');" />
						 --><!-- <input name="date1" id="date1" type="hidden"  >&nbsp;<img
						src="images/cal.gif" align="middle"
						style="vertical-align: middle; cursor: pointer;"
						onfocus="javascript:NewCssCal('date1', 'ddmmmyyyy')" /> -->
						<!-- </td> -->
						<input type="hidden" id="nopresent" value=""/>
	  					<input type="hidden" id="cnt" value="0"/>
		<!-- <td width="84" height="20" align="left" bgcolor="#CCCCCC">Total Day's </td>
		<td width="40%"><input type="text" name="days" size="20" class="form-control" 
						id="days"  title="Enter total day's. " onkeypress="return inputLimiter(event,'Numbers')"> &nbsp;
		 -->				<label><input type="submit"  class="myButton" value="Submit" style="margin-left: 10px" onclick="getvalue()"></label></td>				
  		</tr>
  		
  </table>

<br/>
<div align="left">
<table width="900" height="44" border="1" id="customers" align="center" style="margin-left: 114px;">
  <tr align="center" valign="middle" bgcolor="#CCCCCC">
  
  
  	<th width="48" align="center">FORDATE </th>
    <th width="130" align="center">EMPLOYEE NAME </th>
    <th width="70" align="center">BASIC </th>
    <th width="67" align="center">DA </th>
    <th width="89" align="center">VDA </th>
    <th width="60" align="center">EXTRA DAY'S </th>
    <th width="80" align="center">TOTAL AMOUNT </th>
     <th width="50" align="center">STATUS </th>
   </tr>
   
   
</table>
</div>
<div style="height:250px; overflow-y:scroll; width:auto;" align="left">
 <!--  <form action="ShiftServlet?action=addextra_duty_time" method="post" name="form1" id="form1">  -->
<table width="900" border="1" align="left" id="customers" style="margin-left: 114px;">  

	
			<%if(action.equalsIgnoreCase("getdetails")){
				if (!extraDutyPayList.isEmpty()) {
				for(extra_duty_paymentBean extrapay:extraDutyPayList){
					
					if(extrapay.getTotal()==0.00){%>
							<td align="center" width="870" valign="middle" class="alt"> <h3 style="color:red;">
				Records Are Not Present For Selected Month & Employee...!!! 
				</td><%}else{String fordate[]=extrapay.getTRNDT().split(","); %>
			<tr>
		  <td  width="75" 	align="center"><font size="2"><%for(int i=0;i<fordate.length;i++){ %><%=fordate[i] %><br><%} %></font></td>
	      <td  width="170"	align="left"><font size="2"><%=empName %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getBasic() %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getDA() %></font></td>
	      <td  width="114" 	align="center"><font size="2"><%=extrapay.getVDA() %></font></td>
	      <td  width="82" 	align="center"><font size="2"><%=day%></font></td>
	      <td  width="104"	align="center"><font size="2"><%=total.format("%.2f",extrapay.getTotal()) %></font></td>
		  <td  width="66" 	align="center"><font size="2"><%=extrapay.getSTATUS()%></font></td>	
		  
			<input type="hidden" id="trndt" value="<%=extrapay.getTRNDT()%>"/>
			<input type="hidden" id="month" value="<%=extrapay.getMonth()%>"/>
			 <input type="hidden" id="empno1" value="<%=EmpNo%>"/>
			<input type="hidden" id="ename" value="<%=empName%>"/>
			<input type="hidden" id="basic" value="<%=extrapay.getBasic()%>"/>
			<input type="hidden" id="da" value="<%=extrapay.getDA()%>"/>
			<input type="hidden" id="vda" value="<%=extrapay.getVDA()%>"/>
			<input type="hidden" id="day" value="<%=day%>"/>
			<input type="hidden" id="total" value="<%=total.format("%.2f",extrapay.getTotal())%>"/> 
				
				
										
			 </tr>  
			 <% }
					}
				}else{ %>
				<td align="center" width="870" valign="middle" class="alt"> <h3 style="color:red;">
				Records Are Already Posted For Selected Employee...!!! 
				</td>
				<%} 
				
			}else{
				if (!defaultlist.isEmpty()) {
				for(extra_duty_paymentBean extrapay:defaultlist){
					String fordate[]=extrapay.getTRNDT().split(",");
					/* if(extrapay.getTotal()==0.00){*/ %>
						<!-- 	<td align="center" width="870" valign="middle" class="alt"> <h3 style="color:red;"> -->
		<!-- 		Records Are Not Present For Selected Month & Employee...!!! --> 
				<%-- </td><%}else{ --%> 
			<tr>
		  <td  width="75" 	align="center"><font size="2"><%for(int i=0;i<fordate.length;i++){ %><%=fordate[i] %><br><%} %></font></td>
	      <td  width="170"	align="left"><font size="2"><%=extrapay.getNAME() %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getBasic() %></font></td>
	      <td  width="92" 	align="center"><font size="2"><%=extrapay.getDA() %></font></td>
	      <td  width="114" 	align="center"><font size="2"><%=extrapay.getVDA() %></font></td>
	      <td  width="82" 	align="center"><font size="2"><%=extrapay.getTOTALDAYS()%></font></td>
	      <td  width="104"	align="center"><font size="2"><%=total.format("%.2f",extrapay.getTotal()) %></font></td>
		  <td  width="66" 	align="center"><font size="2"><%=extrapay.getSTATUS()%></font></td>	
		  
				
				
										
			 </tr>  
			 <% //}
					}
				}else{%><td align="center" width="870" valign="middle" class="alt"><h3 style="color:red;">
					No Records Available!!!
					</td>
					<%} 
				}%>
			
					<%-- <td align="center" width="870" valign="middle" class="alt"><h3 style="color:red;">
					No Records Available!!!
					</td>
					<%} %> --%>
					
	
</table>
<!-- </form> -->
<tr align="center">
    <td colspan="8" valign="middle">
    	<%if(action.equalsIgnoreCase("getdetails")){ %>
      <label><input type="submit"  name="save"   class="myButton" value="Save" onClick="extraAmtSave()" /></label>
      <label><input type="button" name="Reset"  class="myButton" value="Reset " onClick="gohome()"  /></label><%} %>
     <%-- <%if(!action.equalsIgnoreCase("getdetails")){ %>  <label><input type="Submit" name="post"  class="myButton" value="Post/Delete" onClick="showrecord()"  /></label><%} %> 
      --%> 
      
      <%if(!action.equalsIgnoreCase("getdetails")){ %> <a href="#" onclick="window.open('PostExtraPayment.jsp','_self')" ><b><u>Click  Here To Post/Delete Record</u></b></a> <%} %>
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
