<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<!--  jquery core -->
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>

<!--  checkbox styling script -->
<script src="js/jquery/ui.core.js" type="text/javascript"></script>
<script src="js/jquery/ui.checkbox.js" type="text/javascript"></script>
<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
function multiply()
{
	debugger;
	var i=document.getElementById("txt1").value;
	var j=i*12;
	var p=document.getElementById("txt2");
	p.value=j;
	//alert(p);
	
	var i1=document.getElementById("txt3").value;
	var j1=i1*12;
	var p1=document.getElementById("txt4");
	p1.value=j1;
	
	var i2=document.getElementById("txt5").value;
	var j2=i2*12;
	var p2=document.getElementById("txt6");
	p2.value=j2;
	
	var i3=document.getElementById("txt7").value;
	var j3=i3*12;
	var p3=document.getElementById("txt8");
	p3.value=j3;
	
	var i4=document.getElementById("txt9").value;
	var j4=i4*12;
	var p4=document.getElementById("txt10");
	p4.value=j4;
	
	var i5=document.getElementById("txt11").value;
	var j5=i5*12;
	var p5=document.getElementById("txt12");
	p5.value=j5;
	
	var i6=document.getElementById("txt13").value;
	var j6=i6*12;
	var p6=document.getElementById("txt14");
	p6.value=j6;
	
	var i7=document.getElementById("txt15").value;
	var j7=i7*12;
	var p7=document.getElementById("txt16");
	p7.value=j7;
	
	var i8=document.getElementById("txt17").value;
	var j8=i8*12;
	var p8=document.getElementById("txt18");
	p8.value=j8;
	
	var i9=document.getElementById("txt19").value;
	var j9=i9*12;
	var p9=document.getElementById("txt20");
	p9.value=j9;
	
	var i10=document.getElementById("txt21").value;
	var j10=parseInt(i)+parseInt(i1)+parseInt(i2)+parseInt(i3)+parseInt(i4)+parseInt(i5)+parseInt(i6)+parseInt(i7)+parseInt(i8)+parseInt(i9);
	if(!isNaN(j10))
	{	
	document.getElementById("txt21").value=j10;
	}
	
	var p10=document.getElementById("txt22").value;
	var u=j10*12
	if(!isNaN(j10))
		{
		document.getElementById("txt22").value=u;
		}
	
	
	var i11=document.getElementById("txt23").value;
	var j11=i11*12;
	var p11=document.getElementById("txt24");
	p11.value=j11;
	
	var i12=document.getElementById("txt25").value;
	var j12=i12*12;
	var p12=document.getElementById("txt26");
	p12.value=j12;
	
	var i13=document.getElementById("txt27").value;
	var j13=i13*12;
	var p13=document.getElementById("txt28");
	p13.value=j13;
	
	var i14=document.getElementById("txt29").value;
	var j14=i14*12;
	var p14=document.getElementById("txt30");
	p14.value=j14;
	
	var i15=document.getElementById("txt31").value;
	var j15=i15*12;
	var p15=document.getElementById("txt32");
	p15.value=j15;
	
	var i16=document.getElementById("txt33").value;
	var j16=i16*12;
	var p16=document.getElementById("txt34");
	p16.value=j16;
	
	var i17=document.getElementById("txt35").value;
	var j17=i17*12;
	var p17=document.getElementById("txt36");
	p17.value=j17;
	
}

</script>

</head>
<body style="overflow-y:auto;"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto;height:80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
<div id="content" align="center"  ><!--  start page-heading -->
			<div id="step-holder" align="center">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprPersonalDetails.jsp" >1</a></div>
			<div class="step-light-left"><a href="ExprPersonalDetails.jsp" >PersonalDetail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEmploymentDetails.jsp" >2</a></div>
			<div class="step-light-left"><a href="ExprEmploymentDetails.jsp">EMPLOYMENTDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprFamilyDetails.jsp" >3</a></div>
			<div class="step-light-left"><a href="ExprFamilyDetails.jsp"> FamilyDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEducation.jsp" >4</a></div>
			<div class="step-light-left"><a href="ExprEducation.jsp">Qualification</a></div>
            <div class="step-light-right">&nbsp;</div>
			<div class="step-no"><a href="ExprSalaryDrawn.jsp" style="color: white;" >5</a></div>
			<div class="step-dark-left"><a href="ExprSalaryDrawn.jsp">LAST DRAWN SALARY</a></div>
            <div class="step-dark-right">&nbsp;</div>
            <div class="step-no-off"><a href="ExprReference.jsp" >6</a></div>
			<div class="step-light-left"><a href="ExprReference.jsp">REFERENCES</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprOther.jsp" >7</a></div>
			<div class="step-light-left"><a href="ExprOther.jsp">OtherDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			
		
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
		<form>
			<!--  start table-content  -->
			<div id="table-content" align="center">
			<h2><b>DETAILS OF LAST DRAWN SALARY:</b></h2>
			<table width="788" id="customers" align="center" >
				<tr class="alt"  ">
					<th width="150">Monthly Emoluments</th>
					<th width="100">Monthly</th>
					<th  width="100">Yearly</th> 
				</tr>
			</table>
			
										 
								<div style="overflow-y: scroll; height: 400px; width:788px;border-bottom-style: groove;">
										 <table width="770" id="customers" align="center" style="width:698x; " >
										 <!-- <colgroup>
										 	<col style="width: 300px" />
											<col style="width:200px;" />
											<col style="width:200px" />
										 </colgroup> -->
										<tr>
										 <td>Basic</td>
										 <td align="center"><input type="text" id="txt1"  oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt2" disabled></td>
										 </tr>
										 <tr>
										 <td>DA</td>
										 <td align="center"><input type="text" id="txt3" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt4" disabled></td>
										 </tr>
										 <tr>
										 <td>HRA</td>
										 <td align="center"><input type="text" id="txt5" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt6"  disabled></td>
										 </tr>
										 <tr>
										 <td>Conveyance</td>
										 <td align="center"><input type="text" id="txt7" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt8"  disabled></td>
										 </tr>
										 <tr>
										 <td>Educational Allowance</td>
										 <td align="center"><input type="text" id="txt9" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt10"  disabled></td>
										 </tr>
										 <tr>
										 <td>Any other monthly allowance</td>
										 <td align="center"><input type="text" id="txt11" oninput="multiply()"></td>
										 <td align="center"><input type="text"  id="txt12" disabled></td>
										 </tr>
										 <tr>
										 <td>1. <input type="text"></td>
										 <td align="center"><input type="text" id="txt13" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt14"  disabled></td>
										 </tr>
										 <tr>
										 <td>2. <input type="text"></td>
										 <td align="center"><input type="text" id="txt15" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt16"  disabled></td>
										 </tr>
										 <tr>
										 <td>3. <input type="text"></td>
										 <td align="center"><input type="text" id="txt17" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt18"  disabled></td>
										 </tr>
										 <tr>
										 <td>4. <input type="text"></td>
										 <td align="center"><input type="text" id="txt19" oninput="multiply()"></td>
										 <td align="center"><input type="text" id="txt20" disabled></td>
										 </tr>
										 <tr>
										 <td><b>Total Monthly Pay</b></td>
										 <td align="center"><input type="text" id="txt21" oninput="multiply()"></td>
										 <td align="center"><input type="text"  id="txt22"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>Yearly Value( Above x 12)</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>Annual Benefits</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>LTA</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Medical Allowance</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Bonus</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Any other</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>1.</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>2.</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>3.</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>Total Annual Benefits</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>Deferred Benefits</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Employer's contribution to PF</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Employer's contribution to Gratuity fund</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Employer's contribution to superannuation fund</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>GRAND TOTAL</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td><b>Compensation in Kind</b></td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Club Membership</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Group Insurance</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Mediclaim</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Credit Card</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Co. House</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Co. Car/ Two Wheeler</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Co. Driver</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 <tr>
										 <td>Co. Telephone</td>
										 <td align="center"><input type="text"></td>
										 <td align="center"><input type="text"  disabled></td>
										 </tr>
										 </table>
										 </div>
										 




<div>
		<input type="submit"    style="padding:4px 8px 4px;background-color:#1F5FA7;color:white;" value="Submit" class="myButton" />
		</div>
			
</div>

			<!--  end table-content  -->
	</form>
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		
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