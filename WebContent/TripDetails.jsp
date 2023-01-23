<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.awt.Desktop.Action"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String travelcode = request.getParameter("trvlcode")==null?"":request.getParameter("trvlcode");

TripBean trbn = (TripBean)session.getAttribute("tbean");
TripHandler th = new TripHandler();

%>
<script type="text/javascript">
function openJourneyBox()
{
	var trvlcode = document.getElementById("trvlcode").value;	
	window.showModalDialog("TripInfo.jsp?action=journey&trcode="+trvlcode,null,"dialogWidth:680px; dialogHeight:210px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TripDetails.jsp?trvlcode="+trvlcode;
}
function openLocalConvBox()
{
	var trvlcode = document.getElementById("trvlcode").value;	
	window.showModalDialog("TripInfo.jsp?action=localConv&trcode="+trvlcode,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TripDetails.jsp?trvlcode="+trvlcode;
}
function openFoodExpBox()
{
	var trvlcode = document.getElementById("trvlcode").value;	
	window.showModalDialog("TripInfo.jsp?action=foodExp&trcode="+trvlcode,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TripDetails.jsp?trvlcode="+trvlcode;
}
function openOtherMiscExpBox()
{
	var trvlcode = document.getElementById("trvlcode").value;	
	window.showModalDialog("TripInfo.jsp?action=otherMiscExp&trcode="+trvlcode,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TripDetails.jsp?trvlcode="+trvlcode;
}
function openMiscExpBox()
{
	var trvlcode = document.getElementById("trvlcode").value;	
	window.showModalDialog("TripInfo.jsp?action=miscExp&trcode="+trvlcode,null,"dialogWidth:580px; dialogHeight:160px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TripDetails.jsp?trvlcode="+trvlcode;
}

</script>
<script type="text/javascript">

function confirmation(){
	var conf = confirm("       Are you sure?\nYou want to store the details");
	if(conf==true)
	{
		alert("Record stored successfully.");
		return true;
	}
	else 
	{
		return false;
	}
}
</script>

<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>


</head>
<body style="overflow:hidden"> 
<input type="hidden" name="trvlcode" id="trvlcode" value="<%=trbn.getTRCODE()%>">
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Travel Master </h1>
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
		<div id="table-content" style="overflow-y:scroll; height:515px;" >
		
			<!--  start table-content  -->

<div>
<form>
<%-- <%
	TripBean trbn = (TripBean)session.getAttribute("tbean");
	TripHandler th = new TripHandler();
	
%> --%>
	<table id="customers">
	<tr class="alt">
	<td>Employee Name</td><td><%=trbn.getENAME() %></td> 
	<td>Emp No.</td><td><%=trbn.getEMPNO() %></td>
	<td>Travel Code</td><td><%=trbn.getTRCODE() %></td>	
	</tr>
	<tr class="alt">
	<td>Application Date</td><td><%=trbn.getAPPDATE() %></td>
	<td>Start Date</td><td><%=trbn.getSTARTDATE()%></td>
	<td>End Date</td><td><%=trbn.getENDDATE()%></td>
	</tr>
	</table>
	<br>
	</form>
</div>

<div>
<%
ArrayList<TripBean> al = new ArrayList<TripBean>();
TripHandler trh = new TripHandler();
al = trh.getTripDetails(trbn.getTRCODE());

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

%>

<form >
	<div id="Trip">
	
	<table id="customers">
		<tr class="alt"><td colspan="7">A) Journey Details &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" name="jdetails" value="Add" onclick="openJourneyBox()"></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th>Sr.No</th>
			<th>Date</th>
			<th>From</th>
			<th>To</th>
			<th width="100">Mode of Travel</th>
			<th>Class</th>
			<th>Fair</th>
			<th>Amount</th>
		</tr>
		<%	for(TripBean tb : al){
				
			String TMode = new LookupHandler().getLKP_Desc("TM", tb.getDTMODE());
			 String TClass = new LookupHandler().getLKP_Desc("TC", tb.getDCLASS());
			
			if(tb.getDTYP()=='A'){
		%>
		<tr align="center">
			<%-- <td><%=tb.getSRNO()%></td> --%>
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDFROM() %></td>
			<td><%=tb.getDTO() %></td>
			<td><%=TMode %></td>
			<td><%=TClass %></td>
			<td><%=tb.getDFAIRDP() %></td>
			<td><%=tb.getDAMT() %></td>
		</tr>
		<% }
		}%>
		</table>
	<br>
		
	<table id="customers">
		<tr class="alt"><td>B) Local Conv. Exp. Details &nbsp;&nbsp;<input type="button" name="ldetails" value="Add" onclick="openLocalConvBox()"></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th>Sr.No</th>
			<th>Date</th>
			<th>From</th>
			<th>To</th>
			<th>Mode of Local Conv.</th>
			<th>Distance</th>
			<th>Amount</th>
		</tr>
			<%for(TripBean tb : al){
				
			String TMode = new LookupHandler().getLKP_Desc("TM", tb.getDTMODE());	
				 
			if(tb.getDTYP()=='B'){ %>
					
		<tr align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDFROM() %></td>
			<td><%=tb.getDTO() %></td>
			<td><%=TMode %></td>
			<td><%=tb.getDFAIRDP() %></td>
			<td><%=tb.getDAMT() %></td>
		</tr>
			<%}
				}%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td>C) Food Exp. Details &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" name="fdetails" value="Add" onclick="openFoodExpBox()"></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th>Sr.No</th>
			<th>Date</th>
			<th>From</th>
			<th>To</th>
			<th width="60">Per Day</th>
			<th width="100">No Of Days</th>
			<th width="60">Amount</th>
		</tr>
			<%for(TripBean tb : al){
				 
			if(tb.getDTYP()=='C'){%>
					
		<tr align="center">
				<td><%=tb.getSRNO()%></td>
				<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
				<td><%=tb.getDFROM() %></td>
				<td><%=tb.getDTO() %></td>
				<td><%=tb.getDFAIRDP() %></td>
				<td><%=tb.getNOFDAY()%></td>
				<td><%=tb.getDAMT() %></td>
		</tr>
			<%}
				}%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td>D) Other Misc. Exp. Details &nbsp;&nbsp;<input type="button" name="omdetails" value="Add" onclick="openOtherMiscExpBox()"></td></tr></table>
		<table id="customers">
		<tr class="alt">
			<th>Sr.No</th>
			<th>Particulars</th>
			<th>Name of the Party</th>
			<th>Bill No.</th>
			<th>Date</th>
			<th width="60">Amount</th>
		</tr>
			<%for(TripBean tb : al){
				 
			if(tb.getDTYP()=='D'){ %>
		<tr align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=tb.getDPARTI() %></td>
			<td><%=tb.getDPNAME() %></td>
			<td><%=tb.getDBILLNO() %></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDAMT() %></td>
		</tr>
			<%}
				}%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td>E) Misc. Exp. Details &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" name="mdetails" value="Add" onclick="openMiscExpBox()"></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th>Sr.No</th>
			<th>Date</th>
			<th>Particulars</th>
			<th>Location</th>
			<th>Amount</th>
		</tr>
		<%for(TripBean tb : al){
			 
		if(tb.getDTYP()=='E'){ %>
		<tr align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDPARTI() %></td>
			<td><%=tb.getDLOCATION() %></td>
			<td><%=tb.getDAMT() %></td>
		</tr>
		<%}
			} %>
	</table>
</div>	
</form>
<br>

<form action="TripMasterServlet?action=addacimprestdetails" method="post" name="StoreDetails" onclick="return validate()">
	<table id="customers">
	<tr class="alt"><td>F) A/C of Imprest Details </td></tr></table>
	<table id="customers">
	<tr class="alt">
	<td>Employee No</td><td><input type="text" name="aceno" value="<%=trbn.getEMPNO()%>" readonly="readonly" ></td>
	<td>Travel Code</td><td colspan="2"><input type="text" name="actrcode" value="<%=trbn.getTRCODE()%>" readonly="readonly"></td>
	</tr>
	<tr class="alt">
	<th>Date</th><th>Particulars</th><th>Receipt(Rs)</th><th>Payment(Rs)</th><th>Amount</th></tr>
	<tr class="alt">
	<td><input type="text" name="acDetails" id="acDetails" value="" readonly="readonly" ><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('acDetails', 'ddmmmyyyy')" />
				 </td>
	<td><input type="text" name="acPar" value="Opening Balance" readonly="readonly" size="35"></td>
	<td><input type="text" name="acReceipt"></td>
	<td><input type="text" name="acPayment"></td>
	<td><input type="text" name="acAmount"></td></tr>	
	<tr class="alt">
	<td><input type="text" name="acDetails1" id="acDetails1" readonly="readonly"><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('acDetails1', 'ddmmmyyyy')" /></td>
	<td><input type="text" name="acPar1" value="Add :- Imprest/Advance Taken For Tour" readonly="readonly" size="35"></td>
	<td><input type="text" name="acReceipt1"></td>
	<td><input type="text" name="acPayment1"></td>
	<td><input type="text" name="acAmount1"></td></tr>
	<tr class="alt">
	<td><input type="text" name="acDetails2" id="acDetails2" readonly="readonly"><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('acDetails2', 'ddmmmyyyy')" /></td>
	<td><input type="text" name="acPar2" value="Add :- Advance Taken From Site" readonly="readonly" size="35"></td>
	<td><input type="text" name="acReceipt2"></td>
	<td><input type="text" name="acPayment2"></td>
	<td><input type="text" name="acAmount2"></td></tr>
	<tr class="alt">
	<td><input type="text" name="acDetails3" id="acDetails3" readonly="readonly"><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('acDetails3', 'ddmmmyyyy')" /></td>
	<td><input type="text" name="acPar3" value="Less :- Tour Bill Submited" readonly="readonly" size="35"></td>
	<td><input type="text" name="acReceipt3"></td>
	<td><input type="text" name="acPayment3"></td>
	<td><input type="text" name="acAmount3"></td></tr>
	<tr class="alt">
	<td><input type="text" name="acDetails4" id="acDetails4" readonly="readonly"><img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('acDetails4', 'ddmmmyyyy')" /></td>
	<td><input type="text" name="acPar4" value="Cash Returned/Refund" readonly="readonly" size="35"></td>
	<td><input type="text" name="acReceipt4"></td>
	<td><input type="text" name="acPayment4"></td>
	<td><input type="text" name="acAmount4"></td></tr>	
	</table><br>
	
	<table id="customers">
	<tr class="alt">
		<td>Tour Report</td>
		<td><textarea rows="2" cols="80" name="acTrReport"></textarea>
		</td>
	</tr>
	</table>
	<br>
	
	<div align="justify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="Submit" onclick="return confirmation()"> &nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="reset" value="Clear">
	</div>
	
	
	
</form>	 

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