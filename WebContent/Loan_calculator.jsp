
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 

<html>
<script type="text/javascript" src="js/Loan_calc.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<script type="text/javascript">
 function calculate()
 {

	 
	 var amount=parseFloat(document.getElementById("amt1").value);
	  
	 var days=document.getElementById("pay1").value;
	 
	 var interest=parseFloat(document.getElementById("rate1").value);
	  
	 
	 var calamt=(amount*(interest/365)*days)/100;
	 calamt=calamt.toFixed(2);
	 document.getElementById("interestamount").value=calamt;
	 
	 
 }
	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890.';
		}
		var k;
		k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
		if (k != 13 && k != 8 && k != 0) {
			if ((e.ctrlKey == false) && (e.altKey == false)) {
				return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

</script>

</head>
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:82%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Loan Calculator</h1> 
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
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content" align="center">
			  <!--  <p align="left"><a href="loan_master.jsp"><h4 align="left">Back To Loan Master</h4></a></p> -->
			    <table align="center" id="customers" width="700">
				<tr><th>Loan Calculator</th></tr>
				
			    <tr><td>
			        
			              
				<form name="loan_form">
				<table align='center'  border="1" id="customers" width="584">
				<tr>

					<td width="272">Loan Amount:&nbsp; <font color="red"><b>*</b></font></td>
					<td><input type="text" name="amt" size="15" onkeypress="return inputLimiter(event,'Numbers')" /></td>
				</tr>
				<tr>
					<td>Number of Payments(in Months):&nbsp; <font color="red"><b>*</b></font></td>
					<td><input type="text" name="pay" size="15" onkeypress="return inputLimiter(event,'Numbers')" /></td>
				</tr>
				<tr>

					<td>Annual Interest Rate:&nbsp; <font color="red"><b>*</b></font><br /><span style="font-size:75%">(ex. 8.5% = 8.5)</span></td>
					<td><input type="text" name="rate" size="15" onkeypress="return inputLimiter(event,'Numbers')"/></td>
				</tr>
				<tr>
					<td>Start Date:&nbsp; <font color="red"><b>*</b></font><br /></td>
					<td width="296" align="left"><input name="date" size="15" id="date"
							type="text" value="" readonly="readonly"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
				</tr>
				<tr>
					<td colspan='2' align='center' style='padding-top:5px'>
						<input type="button" class="myButton" onClick=" return check()" value="  Calculate  " />&nbsp;&nbsp;&nbsp;
						<input type="reset" class="myButton" onClick="clearScreen()" value="Start Over" />
					</td>

				</tr>
				</table>
				<br>
				
				</form>
			 
			<div class="loan_pmt" id="pmt"></div>
			<div class="loan_out" id="det"></div>
			<!--[if IE 6]>
			<script>document.all.srcamt.style.width="235px";document.all.pmt.style.width="235px";</script>
			<![endif]-->
		
			        
			        </td> </tr>
			    
			    </table>
			    <br>
			    <form name="loan_form1">
			<table border="1" id="customers">
			  <tr class="alt"><th colspan="2">Interest calculator</th></tr>
			  <tr class="alt">

					<td width="272">Loan Amount:&nbsp; <font color="red"><b>*</b></font></td>
					<td><input onkeypress="return inputLimiter(event,'Numbers')" type="text" id="amt1" name="amt1" size="15" /></td>
				</tr>
				<tr class="alt">
					<td>Number of day:&nbsp; <font color="red"><b>*</b></font></td>
					<td><input  onkeypress="return inputLimiter(event,'Numbers')" type="text" id="pay1" name="pay1" size="15" /></td>
				</tr>
				<tr class="alt">

					<td>Annual Interest Rate:&nbsp; <font color="red"><b>*</b></font><br /><span style="font-size:75%">(ex. 8.5% = 8.5)</span></td>
					<td><input onkeypress="return inputLimiter(event,'Numbers')" type="text" id="rate1" name="rate1" size="15"/></td>
				</tr>
               <tr class="alt">
					<td colspan='2' align='center' style='padding-top:5px'>
						<input type="button" class="myButton" onClick="return calculate()" value="Calculate" />&nbsp;&nbsp;&nbsp;
						<input type="reset" class="myButton" onClick="clearScreen()" value="Start Over" />
					</td>

				</tr>
			  <tr class="alt">
			        <td>Total Amount<br /><span style="font-size:75%">(ex. 8.5% = 8.5)</span></td>
					<td><input type="text" readonly="readonly" id="interestamount" name="interestamount" size="15"/></td>
				</tr>
			</table>
			</form>
		
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