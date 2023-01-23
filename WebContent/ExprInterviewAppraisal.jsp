
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

<script> 
  $(document).ready(function() {
	    $("#RDate,#ADate,#AHRDate,#AMDDate").datepicker({
	        showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});
  function add()
  { 	
  	
  	var a = document.getElementById('txt1').value;
    var b = document.getElementById('txt2').value;
    var c = document.getElementById('txt3').value;
    var d = document.getElementById('txt4').value;
    var e = document.getElementById('txt5').value;
    var f = document.getElementById('txt6').value;
    var g = document.getElementById('txt7').value;
    var h = document.getElementById('txt8').value;
    var result = parseInt(a) + parseInt(b) + parseInt(c) + parseInt(d) + parseInt(e) + parseInt(f) + parseInt(g) + parseInt(h);
    if(!isNaN(result)){
        document.getElementById('total').value = result;
        document.getElementById('score').value = result/10;
    }
  	
  }
  </script>

</head>
<body > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto;height:80%;">
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="content" align="center"  ><!--  start page-heading -->
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
		<form action="ExprInterviewServlet?action=AddAppraisalReport" method="Post">
			<!--  start table-content  -->
			
			<div id="table-content" align="center">
			<h2><b>Interview Appraisal Report</b></h2>
			<table id="customers" align="center"  width="850px">
				<tr class="alt" style="border:solid;border-color:black;  ">
					<th style="width:150px;">Name</th>
					<th >Age</th>
					<th >Qualification</th> 
					<th >Experience</th>
					<th >Present CTC</th>
					<th >Expected CTC</th>
				</tr>
				
				<tr class="alt">
					<td ><input type="text" name="aname" id="aname"></td>
					<td  style="width:10px;"><input type="text" style="width:30px;" name="aage" id="aage"></td>
					 <td style="width:100px;"><input type="text" style="width:90px;" name="quali" id="quali"></td>
					<td style="width:100px;"><input type="text" style="width:100px;" name="expr" id="expr"></td>
					<td style="width:100px;"><input type="text" style="width:100px;" name="pctc" id="pctc"></td>
					<td style="width:100px;"><input type="text" style="width:100px;" name="ectc" id="ectc"></td> 
				</tr>
				<!-- style=" height:1.5em;" -->
				<tr class="alt" style="border:solid;border-color:black;">
					<th colspan="8"></th>
				<tr>
				
				<tr>
					<td colspan="8" align="center">Position : <input type="text" name="position" id="position" style="width:30%; height:1.5em;" ></td>
				</tr>
			</table>
			
			<br>
			
			<table id="customers">
				<tr class="alt">
					<h2>Evaluation: (Evaluate the candidate on a 3 to 8 Scale for each criteria)</h2>
				</tr>
				<tr class="alt" style="border:solid;border-color:black;">
					<th style="width:100px; text-align: center;">Srno.</th>
					<th style="width:600px; text-align: center;">Criteria</th>
					<th style="width:100px; text-align: center;">Score</th>
				</tr>
				<tr>
					<td>1</td>
					<td>Communication & Clarity</td>
					<td align="center"><input type="text" id="txt1" style="width:100px; height:1.5em;" oninput="add()"></td>
				</tr>
				<tr class="alt">
					<td>2</td>
					<td>Education & Technical Knowledge relevant to job profile</td>
					<td align="center"><input type="text" style="width:100px; height:1.5em;" id="txt2" oninput="add()"></td>
				</tr>
				<tr>
					<td>3</td>
					<td>Suitability of previous experience</td>
					<td align="center"><input type="text" id="txt3" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr class="alt">
					<td>4</td>
					<td>Leadership capabilities & ability to guide subordinates</td>
					<td align="center"><input type="text" id="txt4" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr>
					<td>5</td>
					<td>Ability to work as an effective team player</td>
					<td align="center"><input type="text" id="txt5" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr class="alt">
					<td>6</td>
					<td>Analytical ability & grasping power</td>
					<td align="center"><input type="text" id="txt6" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr>
					<td>7</td>
					<td>Overall bearing & general impression</td>
					<td align="center"><input type="text" id="txt7" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr class="alt">
					<td>8</td>
					<td>Communication & Clarity</td>
					<td align="center"><input type="text" id="txt8" oninput="add()" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;"><b>Total Score</b></td>
					<td align="center"><input type="text" name="total" id="total" style="width:100px; height:1.5em;" ></td>
				</tr>
				<tr class="alt">
					<td colspan="2" style="text-align: right;">Score Index ( Total score /10)</td>
					<td align="center"><input type="text" name="score" id="score" style="width:100px; height:1.5em;" ></td>
				</tr>

			</table>
			
			<br>
			
			<table id="customers" width="850px">
				<tr class="alt" style="border:solid;border-color:black;  ">
					<th colspan="8"></th>
					</tr>
				<tr>
					<td> Interviewer's comments :</td>
					<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea   name="incomment" cols="85" rows="4" tabindex="101" data-wz-state="256" data-min-length="" ></textarea></td>
				</tr>
				<tr class="alt">
					<td style="text-align: right;">Interviewed by :</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="interviewer" id="interviewer"></td>
				</tr>
				<tr>
					<td style="text-align: right;">Date :</td>
					<td width="20" colspan="8" > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' class='datepick' id='RDate' name="RDate" readonly="readonly"></td>
				</tr>
				<tr class="alt">
					<th>Decision:  </th>
					<!-- <td><b>Appoint / Reject </b></td> -->
					<td><select id="result" name="result">
					      <option value="appoint">Appoint</option>
					      <option value="reject">Reject</option>
					</select></td>
				</tr>
				<tr>
					<td>1.Grade</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="grade" id="grade"></td>
				</tr>
				<tr class="alt">
					<td>2.Salary-Rs.</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="salmonth" id="salmonth"> Per Month</td>
				</tr>
				<tr>
					<td>3.CTC-Rs</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="ctcmonth" id="ctcmonth"> Per Month</td>
				</tr>
			</table>

			<br>
			
			<table id="customers" width="850px">
				<tr class="alt" style="border:solid;border-color:black;  ">
					<th colspan="8"></th>
				<tr>
				<tr class="alt">
					<td>Approved By :PM: </td>
					<td style="width:250px;"><input type="text" name="pmname" id="pmname"></td>
					<td style="width:100px;">Date :</td>
					<td style="width:250px;"><input type='text' class='datepick' id='ADate' name="ADate" readonly="readonly"></td>
				</tr>
				
				<tr>
					<td>Approved By: HR & Admin. </td>
					<td><input type="text" name="hrname" id="hrname"></td>
					<td>Date :</td>
					<td><input type='text' class='datepick' id='AHRDate' name="AHRDate" readonly="readonly"></td>
				</tr>
				
				<tr class="alt">
					<td>Authorized By : MD  </td>
					<td><input type="text" name="mdname" id="mdname"></td>
					<td>Date :</td>
					<td><input type='text' class='datepick' id='AMDDate' name="AMDDate" readonly="readonly"></td>
				</tr>
			
			</table>
			<div>
		<input type="submit"    style="padding:4px 8px 4px;background-color:#1F5FA7;color:white;" id="Submit" class="myButton" />
		</div>
			
			
	</div>

</form>

<!--  end table-content  -->
	
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
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>

		

</body>
</html>