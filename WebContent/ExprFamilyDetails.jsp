<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>

	
<%@page import="payroll.Model.EmpFamilyBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.LookupHandler.*"%>
<%@page import="payroll.Model.Lookup"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
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

</head>
<body style="overflow-y:auto;"> 
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
			<div class="step-no"><a href="ExprFamilyDetails.jsp"style="color: white;" >3</a></div>
			<div class="step-dark-left"><a href="ExprFamilyDetails.jsp"> FamilyDetails</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEducation.jsp" >4</a></div>
			<div class="step-light-left"><a href="ExprEducation.jsp">Qualification</a></div>
            <div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprSalaryDrawn.jsp" >5</a></div>
			<div class="step-light-left"><a href="ExprSalaryDrawn.jsp">LAST DRAWN SALARY</a></div>
            <div class="step-light-right">&nbsp;</div>
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
		
			<!--  start table-content  -->
			<form action="">
			<div id="table-content" align="center">
			
		<h2>FAMILY BACKGROUND:</h2>
		<table id="customers" width="861" border="1" bordercolor="#000000">
		<tr align="center"  class="alt">
					 <th>Relation</th>
					 <th>Name</th>
					 <th>Age</th>
					 <th>Occupation</th>
					</tr>
					<tr bordercolor="#CCCCCC" align="center"  class="alt">	
					 <td><select class="form-control" name="rRelation" id="rRelation" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RELATION");
 							for(Lookup lkbean : result){
     						%>
      			           <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select></td>
					  <td><input type="text" ></td>
					  <td><input type="text" ></td>
					  <td><input type="text" ></td>  
					</tr>
			

		</table>
			
			<br>
			
				<table id="customers" width="861" border="1" bordercolor="#000000">
					<tr class="alt">						
						<!-- <th style="width: 420px;"> Job Description & Responsibilities in the current job:</th> -->
						<th colspan="3" style="width: 420px;">OUTSTANDING ACHIEVEMENTS:</th>
					</tr>
					<tr class="alt">
						<!-- <td><textarea   name="post-text" cols="50" rows="4" tabindex="101" style="width:100%;" ></textarea></td> -->
						<td><textarea   name="post-text" cols="50" rows="4" style="width:100%;" ></textarea></td>
					</tr> 
				</table>
			
			<br>
			
			
			
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