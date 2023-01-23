<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/DeleteRow.js"></script>

<style type="text/css">

.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}

</style>
<script type="text/javascript">
function valid(){
	alert("Settlement has already done.");
	return true;
}
</script>
<% int trvlcode = request.getParameter("trcode")==null?0:Integer.parseInt(request.getParameter("trcode"));

TripHandler trh = new TripHandler();
TripBean tbn = new TripBean();
ArrayList<TripBean> trbn = new ArrayList<TripBean>();
	%>
</head>
<body style="overflow:hidden"> 
<input type="hidden" name="trcode" id="trcode" value="<%=trvlcode%>">
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Travel Settlement </h1>
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
		<div id="table-content" >
		
			<!--  start table-content  -->
			<div id="trip">
			
			<center>
					
			<form>
			<% tbn = trh.getAdminDetails(trvlcode);	

				EmployeeHandler emph = new EmployeeHandler();
				EmployeeBean ebean = new EmployeeBean();
				ebean = emph.getEmployeeInformation(Integer.toString(tbn.getEMPNO()));
			%>
			
				<table id="customers">
				
					<tr align="center"><th colspan="2">Employee Details</th></tr>
					
					<tr class="alt">
						<td width="100">Employee No</td>
						<td width="250"><%=tbn.getEMPNO()%></td>
					</tr>
					<tr class="alt" > 
						<td width="100">Employee Name</td>
						<td width="250"><%=new LookupHandler().getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ ebean.getMNAME()+" "+ ebean.getLNAME() %></td>
			    	</tr>
					<tr class="alt">
						<td width="100">Tour Name</td>
						<td width="250"><%=tbn.getTOURRPT()%></td>
					</tr>
									
				</table>
				<br>
				
			<%  trbn = trh.getTripDetails(trvlcode);
				float jamount = 0, lexpAmt = 0, foodAmt = 0, otherAmt = 0, miscAmt = 0;
				for(TripBean trip : trbn){
					
					if(trip.getDTYP() == 'A' && trip.getDAMT()>=0){
						 jamount = jamount + trip.getDAMT();
					}
					if(trip.getDTYP() == 'B' && trip.getDAMT()>=0){
						lexpAmt = lexpAmt + trip.getDAMT();
					}
					if(trip.getDTYP() == 'C' && trip.getDAMT()>=0){
						foodAmt = foodAmt + trip.getDAMT();
					}
					if(trip.getDTYP() == 'D' && trip.getDAMT()>=0){
						otherAmt = otherAmt + trip.getDAMT();
					}
					if(trip.getDTYP() == 'E' && trip.getDAMT()>=0){
						miscAmt = miscAmt + trip.getDAMT();
					}
				}
				float Total = jamount+lexpAmt+foodAmt+otherAmt+miscAmt;
			%>
				<table id="customers">
					<tr class="alt"><th width="250">Tour Details</th><th width="100">Total</th></tr>
					<tr class="alt"><td>A) Journey Details</td>
									<td align="right"><%=jamount %>  </td></tr>
					
					<tr class="alt"><td>B) Local Conv. Exp. Details</td>
									<td align="right"><%=lexpAmt %>  </td></tr>
					
					<tr class="alt"><td>C) Food Exp. Details</td>
									<td align="right"><%=foodAmt %>   </td></tr>
					
					<tr class="alt"><td>D) Other Misc. Exp. Details</td>
									<td align="right"><%=otherAmt %>  </td></tr>
					
					<tr class="alt"><td>E) Misc. Exp. Details (Without Bill)</td>
									<td align="right"><%=miscAmt %>  </td></tr>
					
					<tr class="alt" align="right"><td><b>Total:</b></td>
									<td align="right"><%=Total %>  </td></tr> 
				</table>
				<br>
			
			<% trbn = trh.getAcImprestDetails(trvlcode);
				for(TripBean trip : trbn){
					
					if(trip.getACPARTI().contains("Opening")){
			%>
				<div align="center"><h2>A/C Settlement </h2></div>
				<table id="customers">
					<tr class="alt" align="center"><th width="250">Particulars</th><th width="100">Amount</th></tr>
					<tr class="alt"><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Opening Balance</td>
									<td align="right"><%=trip.getACAMT() %>  </td></tr>
									
					<%}if(trip.getACPARTI().contains("Imprest")){ %>
					<tr class="alt"><td>Add : Imprest/Advance Taken For Tour</td>
									<td align="right"> <%=trip.getACAMT() %> </td></tr>
									
					<%}if(trip.getACPARTI().contains(" Advance")){ %>
					<tr class="alt"><td>Add : Advance Taken From Site</td>
									<td align="right"> <%=trip.getACAMT() %> </td></tr>
									
					<%}if(trip.getACPARTI().contains("Submited")){ %>
					<tr class="alt"><td>Less : Tour Bill Submitted</td>
									<td align="right"> <%=trip.getACAMT() %> </td></tr>
									
					<%}if(trip.getACPARTI().contains("Returned/Refund")){ %>
					<tr class="alt"><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cash Returned/Refund (TOTAL)</td>
									<td align="right"> <%=trip.getACAMT() %>  </td></tr>
				
				</table> 
				<br>
			<%} 
				}%>
			
			<div align="center">
				<input type="button" value="Submit" onclick="settlement('<%=trvlcode%>','TravelDetails.jsp')"/> &nbsp;&nbsp;&nbsp;&nbsp;	
				<input type="reset" value="Cancel" onclick="javascript:history.go(-1)"/>
			</div>
			
			</form>	
			</center>
			</div>	 		
			
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