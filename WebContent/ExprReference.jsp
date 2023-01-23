<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.*" %>
<%@page import="payroll.Model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

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
	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4").datepicker({
	        showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});
  
  function check(ckType)
  {
	  var ckName = document.getElementsByName(ckType.name);
	    var checked = document.getElementById(ckType.id);

	    if (checked.checked) {
	      for(var i=0; i < ckName.length; i++){

	          if(!ckName[i].checked){
	              ckName[i].disabled = true;
	          }else{
	              ckName[i].disabled = false;
	          }
	      } 
	    }
	    else {
	      for(var i=0; i < ckName.length; i++){
	        ckName[i].disabled = false;
	      } 
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
			<div class="step-no-off"><a href="ExprSalaryDrawn.jsp" >5</a></div>
			<div class="step-light-left"><a href="ExprSalaryDrawn.jsp">LAST DRAWN SALARY</a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no"><a href="ExprReference.jsp" style="color: white;" >6</a></div>
			<div class="step-dark-left"><a href="ExprReference.jsp">REFERENCES</a></div>
			<div class="step-dark-right">&nbsp;</div>
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
		
			<!--  start table-content  -->
			<div id="table-content" align="center">
			
				<table id="customers">
					<tr class="alt">
						<th style="width:850px;">SysTech Solution Interview form</th>
					</tr>
				</table>
	
   
     <form action="#" method="Post">
		<div>
			<table width="861" border="1" bordercolor="#000000" id="customers">
					<tr bordercolor="#CCCCCC" align="center" id="radioYes" >
					 <td width="40%" align="center">1.	Have you suffered from any serious illness in the past or are you suffering at present?	If yes, give details:-</td>
					  <td width="40%"    style="text-align: left;">
								  <input type="radio" name="yes1" id="yes1" onclick="openTB()" value="Yes" >Yes &nbsp;&nbsp;
								  <input type="radio" name="yes1" id="no1" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip1"  placeholder="Specify illness discription... " hidden></textarea> 
								  
								  </td>
					</tr>
					
										
					<tr bordercolor="#CCCCCC" align="center" class="alt">
					 <td align="center">2.	Have you been interviewed at THE BUSINESS CO.OP BANK LTD before?	<br>If yes, give details:-</td>
					  <td style="text-align: left;"><input type="radio" name="yes2" id="yes2" value="Yes" >Yes &nbsp;&nbsp;
					                  <input type="radio" name="yes2" id="no2" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip2"  placeholder="Detailes... " hidden></textarea></td>
					</tr>
					
					
					
					<tr bordercolor="#CCCCCC" align="center">
					 <td align="center">3.Do you have any relatives or Friends working in SysTech Solution?<br> If yes, give details:-</td>
					  <td style="text-align: left;"><input type="radio" name="yes3" id="yes3" value="Yes" >Yes &nbsp;&nbsp;
					                  <input type="radio" name="yes3" id="no3" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip3"  placeholder="Detailes... " hidden></textarea></td>
					</tr>
					
					<tr bordercolor="#CCCCCC" align="center" class="alt">
					 <td align="center">4. Willingness to travel overseas ? <br>If yes, give details:-</td>
					  <td style="text-align: left;">
					  		<input type="radio" name="yes4" id="yes4" value="Yes" >Yes &nbsp;&nbsp;
					       <input type="radio" name="yes4" id="no4" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip4"  placeholder="Detailes... " hidden></textarea>
					     <br><br>
					     <input type="checkbox" name="yes5" id="yes5" onclick="check(this)" value="0-6 Months" >For 0-6 Months &nbsp;
					      <input type="checkbox" name="yes5" id="yes9" onclick="check(this)" value="UpTo 1 Year" >UpTo 1 Year &nbsp;      
					       <input type="checkbox" name="yes5" id="yes7" onclick="check(this)" value="One Year and Above" >One Year and Above
					     
					     </td>
					</tr>
					
					
					<tr bordercolor="#CCCCCC" align="center"  >
					 <td>5.	Do you have any bond with your current organization?<br> If yes, give details:-</td>
					  <td style="text-align: left;"><input type="radio" name="yes6" id="yes6" value="Yes" >Yes &nbsp;&nbsp;
					  
					       <input type="radio" name="yes6" id="no6" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip6"  placeholder="Detailes... " hidden></textarea>
					    </td>
					</tr>
					<tr bordercolor="#CCCCCC" align="center" class="alt" >
					 <td>6.	 Are you ready to sign on bond for working in our company ? (Yes / No)</td>
					  <td style="text-align: left;"><input type="radio" name="yes6" id="yes6" value="Yes" >Yes &nbsp;&nbsp;
					  
					       <input type="radio" name="yes6" id="no6" value="no" >No &nbsp;&nbsp;<textarea rows="1" cols="20" id="radioyesip6"  placeholder="Detailes... " hidden></textarea>
					       
					       <br><br>
					        <input type="checkbox" name="yes5" id="yes5" onclick="check(this)" value="6 Months" >6 Months &nbsp;
					      <input type="checkbox" name="yes5" id="yes9" onclick="check(this)" value="12 Month" >12 Month &nbsp;      
					       <input type="checkbox" name="yes5" id="yes7" onclick="check(this)" value="18 Month" >18 Month &nbsp;
					       <input type="checkbox" name="yes5" id="yes7" onclick="check(this)" value="24 Month" >24 Month 
					    
					    </td>
					</tr>
					
					
		  </table>	
		</div>
		<br><br>
		<div align="center"><h2>REFERENCES:</h2> 
		<h3>Please provide References of Non Relatives/ Other than THE BUSINESS CO.OP BANK LTD employees (Any Two):</h3></div>
		
		<div>
		<table width="861" border="1" bordercolor="#000000" id="customers">
								<colgroup>			 
									<col style="width: 100px" />
									<col style="width:100px;" />
									<col style="width:100px" />
								    <col style="width:100px;" />  
							    </colgroup>	
					<tr>
					<th colspan="4"></th>
					</tr>
					<tr bordercolor="#CCCCCC" align="center"  class="alt" >
					 <td width="40%">Name :                                                                                                         </td>
					  <td width="40%"><input type="text" name="yes" id="yes"></td>
					 <td width="40%">Name :<td width="40%">
					 <input type="text" name="yes" id="yes" ></td>
					</tr>
					
					<tr bordercolor="#CCCCCC" align="center"  >
					 <td width="40%">Designation :                                                                                                          </td>
					  <td width="40%"><input type="text" name="yes" id="yes"></td>
					 <td width="40%">Designation :<td width="40%">
					 <input type="text" name="yes" id="yes" ></td>
					</tr>
					
					<tr bordercolor="#CCCCCC" align="center"  class="alt" >
					 <td width="40%">Organization:                                                                                                           </td>
					  <td width="40%"><input type="text" name="yes" id="yes"></td>
					 <td width="40%">Organization: <td width="40%">
					 <input type="text" name="yes" id="yes" ></td>
					</tr>
					
					<tr bordercolor="#CCCCCC" align="center"  >
					 <td width="40%">Tel. / Mob. No.                                                                                                          </td>
					  <td width="40%"><input type="text" name="yes" id="yes"></td>
					 <td width="40%">Tel. / Mob. No<td width="40%">
					 <input type="text" name="yes" id="yes" ></td>
					</tr>
					
					<tr bordercolor="#CCCCCC" align="center"  class="alt" >
					 <td width="40%">Email Id:                                                                                                           </td>
					  <td width="40%"><input type="text" name="yes" id="yes"></td>
					 <td width="40%">Email Id: <td width="40%">
					 <input type="text" name="yes" id="yes" ></td>
					</tr>
		</table>
		</div>
		<br><br>
		
		<div>
		<input type="submit"    style="padding:4px 8px 4px;background-color:#1F5FA7;color:white;" value="Submit" class="myButton" />
		</div>
	
		
		
		
</form>
			
			
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
    <script type="text/javascript">
    function addRow()
    {
    	/* $("#customers").append('<tr id="myTableRow1" bordercolor="#CCCCCC" align="center"  class="alt"> <td><input type="text" style="width:90%; height:2.0em;"></td><td><input type="text" style="width:90%; height:2.0em;"></td> <td><input type="text" style="width:90%; height:2.0em;"></td><td><input type="text" style="width:90%; height:2.0em;"></td><td><button type="button" onclick="addRow()">Add</button> </td></tr>'); */
    	
    	$('.myTable').append('<tr id="myTableRow1" bordercolor="#CCCCCC" align="center"  class="alt"> <td><input type="text" style="width:90%; height:2.0em;"></td><td><input type="text" style="width:90%; height:2.0em;"></td> <td><input type="text" style="width:90%; height:2.0em;"></td><td><input type="text" style="width:90%; height:2.0em;"></td><td><button type="button" style="background-color:#1F5FA7 ;color:white;"  onclick="addRow()">Add</button>&nbsp;&nbsp;<button   type="button" onclick="removeRow()" style="background-color:#1F5FA7 ;color:white;">Remove</button> </td></tr>');
    	
    }
    
    function removeRow(){
    	$("#myTableRow1").remove();
    }
    
   /*  $("input[@name='lom']").change(function(){
        // Do something interesting here
    }); */
    
    $(document).ready(function(){
    	debugger;
    //	document.getElementById("radioyesip").style.display = ;
    //  $('input[@name="radioyesip"]').prop("disabled", false);
    	
    })
    
    $("#yes1").click(function(){
    	$("#radioyesip1").show();
    });
    
    $("#no1").click(function(){
    	$("#radioyesip1").hide();
    });
    
    
    $("#yes2").click(function(){
    	$("#radioyesip2").show();
    });
    
    $("#no2").click(function(){
    	$("#radioyesip2").hide();
    });
    
    $("#yes3").click(function(){
    	$("#radioyesip3").show();
    });
    
    $("#no3").click(function(){
    	$("#radioyesip3").hide();
    });
    
    $("#yes4").click(function(){
    	$("#radioyesip4").show();
    });
    
    $("#no4").click(function(){
    	$("#radioyesip4").hide();
    });
    
    
    $("#yes6").click(function(){
    	$("#radioyesip6").show();
    });
    
    $("#no6").click(function(){
    	$("#radioyesip6").hide();
    });
    </script>

</body>
</html>
