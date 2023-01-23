<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.awt.Desktop.Action"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Retirement EncashList</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">

<script src="js/MONTHPICK/jquery.js"></script>
    <script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
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
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>

<script type="text/javascript" language="javascript">

function ABC(formname, checktoggle)
{

  if(checktoggle)	{
	  document.getElementById("encashBT").disabled = false;
	  document.getElementById("uncheckAll").disabled = false;
  }
  else{
	  document.getElementById("encashBT").disabled = true;
  }
  var checkboxes = new Array(); 
  checkboxes = document[formname].getElementsByTagName('input');
  for (var i=0; i<checkboxes.length; i++)  {
    if (checkboxes[i].type == 'checkbox')   {
      checkboxes[i].checked = checktoggle;
    }
  }
}

 function enableButton(){	

	if(document.getElementById("checklist").checked)
	{
		document.getElementById("encashBT").disabled=false;
	 }else{
		document.getElementById("encashBT").disabled=true;
	} 
} 
</script>


<%

try
{  
	int flag=-1;
	String action = null;
	String date = "";
	action = request.getParameter("action")== null?"":request.getParameter("action");
	flag =  Integer.parseInt(request.getParameter("flag1")==null?"0":request.getParameter("flag1"));
	
	RetirementBean rbean = new RetirementBean();
	RetirementHandler eHandler =  new RetirementHandler();
	ArrayList<RetirementBean> list= new ArrayList<RetirementBean>();
	
	
	if(action.equalsIgnoreCase("getEmployeeList")){
		
		date = request.getParameter("date")==null?"":request.getParameter("date");
		date = "01-"+date;
		System.out.println("date in getRetirementList action is @ "+ date);
				
		list =	eHandler.getRetirementList(date);
		
	}
		 
%>
 <script type = "text/javascript" >
  /*  function preventBack(){window.history.forward();}
    setTimeout("preventBack()", 0);
    window.onunload=function(){null}; */
    
 // Prevent page for noBack
	function  noBack() { window.history.forward(); }
	setTimeout("noBack()", 0);
	window.onunload = function() {	null }; 
	
</script> 
<script type="text/javascript">
	
	
	function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);
		if (f == 1) {
			alert("Encah Details Saved Successfully");
			
		}
	
		if (f == 2) {
			alert("Error in Saving Encah Details !");
		}
		if (f == 3) {
			alert("Record updated Successfully");
		}
		if (f == 4) {
			alert("Employee Already Exist");
		}	
	}
	
	

	// Form Validation start from here.........
	function validation(){
	//	alert("Form validation start here..");	
				var result = updateCount();
				 function updateCount(){
					  var count  = $("input[type=checkbox]:checked").size();
					  if(count==0){
						  alert("Al Least Select One CheckBox");
						  return false;
					  }
					  else{
						  return true;
					  }					 
				 }
				 
				 if(result == true){
					var r=  confirm("Are you sure to Encash Leave.");
				 }
				 if(r==true && result== true){
					var p = prompt("Type Yes to Encash the Leave to selected Employee."); 
					 if(p=="yes" || p=="Yes" || p=="YES"){	
						 return true;  // false for testing purpose either set true
					 }
					 else{
						 return false;
					 }
				 }
				 else{
						return false;
					}		
	
		return true;  // false for testing purpose either set true
	}
	
	
	function getEmployeeList(){
		
		var date = document.getElementById("date").value;
		window.location.href ="RetirementEncashList.jsp?action=getEmployeeList&date="+date;

	}
	
	
	
	
</script>




</head>
<body onLoad="checkFlag()" onunload="noBack()" style="overflow: hidden;">
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>

<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
	<!-- start content -->
	<div id="content">
	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Retirement Encash Details</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
		<tr>
			<th rowspan="3" class="sized">
			<img  src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
			<th class="topleft"></th>
			<td id="tbl-border-top">&nbsp;</td>
			<th class="topright"></th>
			<th rowspan="3" class="sized">
				<img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
		</tr>
		<tr>
			<td id="tbl-border-left"></td>
			<td>
			<!--  start content-table-inner ...................................................................... START -->
			<div id="content-table-inner">
			<!--  start table-content  -->
			<div id="table-content">
			<center>
	
			<br>
			 <form name="form3" action="RetirementServlet?action=encashEmpList" method="post" onsubmit="return validation()"> 
				<table id="customers" width="800" align="center">
					<tr>
						<th>Retirement Encash Details</th>
					</tr>
					<tr>
						<td width="400" align="center">
								<table>
								<tr>			
									<td >Select Month for Encashment:<font color="white" size="3">*</font></td>
						     		<td>
						     			<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						     		</td>	
						     		<td>  
						     			<input type="button" value="getList"  class="myButton" onclick="getEmployeeList()" >
						     		</td> 																					
								</tr> 
								</table>
						</td>
					</tr>
					<tr>
						<td>
							<table width="900">
								<tr>
									<th width="70"  colspan="2">Emp Number</th>
									<th width="200">Employee Name</th>
									<th width="70">Basic</th>
									<th width="70">D.A.</th>
									<th width="70">V.D.A.</th>												
									<th width="100">Ammount</th>
									<th width="90">Days</th>
									<th width="100">Retirement Date</th>																
								</tr>
							</table>
						</td>
				   </tr>
			<tr>
				<td>
					<div id="scrolling"	style="height: 380px; overflow-y: scroll; max-width: 100%; background-color: #FFFFFF;"align="center">
						<table width="900">
								
					<% 
					System.out.println("Size Of list is "+ list.size());
					if(list.size()!= 0)
					{
						for(RetirementBean rBean : list){	
					%>
							<tr align="center">
								<td width= "20"> 
									<input type="checkbox" class="empCheckbox" id="checklist" value="<%=rBean.getEmpno()%>"
										   name="checklist" onclick="enableButton()" /> 
								</td>
							 	<td width="40"><%=rBean.getEmpno()%></td> 
								<td width="200"><%=rBean.getName()%></td>
				
								<td width="70">
										<input type="text"  id="basic_<%=rBean.getEmpno()%>" name="basic_<%=rBean.getEmpno()%>" value="<%=rBean.getBasic()%>"  size="8"  readonly="readonly" >
								</td>			
								<td width="70">
										<input type="text"  id="da_<%=rBean.getEmpno()%>" name="da_<%=rBean.getEmpno()%>" size="8"  readonly="readonly" value="<%=rBean.getDa()%>">
								</td>			
								<td width="70" >
										<input type="text"  id="vda_<%=rBean.getEmpno()%>" name="vda_<%=rBean.getEmpno()%>" size="8"  readonly="readonly" value="<%=rBean.getVda()%>">
								</td>	
								<td width="100">
										<input type="text"  id="amt_<%=rBean.getEmpno()%>" name="amt_<%=rBean.getEmpno()%>" size="10"  readonly="readonly" value="<%=rBean.getAmount()%>">
								</td>								
					
								
								<td width="90">
									<input type="text"  id="days_<%=rBean.getEmpno()%>" name="days_<%=rBean.getEmpno()%>" size="8"  readonly="readonly" value="<%=rBean.getDays()%>">
								</td>							
				                
				                <td width="100">
				                	<input type="text"  id="date_<%=rBean.getEmpno()%>" name="date_<%=rBean.getEmpno()%>" size="10"  readonly="readonly" value="<%=rBean.getRetirmentDate()%>">
				                </td>											
							</tr>
					
					<% }
					}
					else{
					%>
						<h3> No Record Found for This Month. </h3>
					<%
					}
					
					%>	<!-- End of for loop -->																										
						</table>
					</div>
				</td>
			</tr>
		
			<tr>
				<td align="center"> <!--  bgcolor="#1F5FA7" -->
					<!-- <input type=button id="checkAll" value="Check All" onClick="this.value=check5(this.form.checklist)"> 
					<input type="submit" id="encashBT" name ="encashBT" disabled="disabled" value="Encash" /> -->
					
					<input type="button" id="checkAll"    value="Check All" onclick="ABC('form3',true)" /> 
					<input type="button" id="uncheckAll"  value="UnCheck All"  onclick="ABC('form3',false)" disabled="disabled" />  
					<input type="submit" id="encashBT"  value="Encash"  name ="encashBT" disabled="disabled"  />
											
				</td>
			</tr>
						
			<tr>
				<td align="left" bgcolor="#1F5FA7">&nbsp;</td>
			</tr>
			</table>
		 	</form> 
		
			<br>
			<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
			</center>
		</div>		
	
<% 	} 
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>
	
		
		<!--  end table-content  -->
		<div class="clear"></div>

		</div> <!--  end content-table-inner ............................................END  -->
		
		 <!-- To display pdf on brawser -->
		<div id="viewPdf"  hidden="true" align="center"></div>
		<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
					<img alt="" src="images/process.gif">
				</div>
		</div>
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