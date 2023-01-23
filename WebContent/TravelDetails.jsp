<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Controller.ReportServlet"%>
<%@page import="payroll.Controller.DeleteServlet"%>
<%@page import="java.awt.Desktop.Action"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
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

<script src="js/DeleteRow.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>
<script type="text/javascript">

function openJourneyBox(srno)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("srno").value= srno;
	window.showModalDialog("EditTripInfo.jsp?action=journey&trvlcode="+trvlcode+"&srno="+srno,null,"dialogWidth:700px; dialogHeight:210px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
function openLocalConvBox(srno)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("srno").value= srno;
	window.showModalDialog("EditTripInfo.jsp?action=localConv&trvlcode="+trvlcode+"&srno="+srno,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
function openFoodExpBox(srno)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("srno").value= srno;
	window.showModalDialog("EditTripInfo.jsp?action=foodExp&trvlcode="+trvlcode+"&srno="+srno,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
function openOtherMiscExpBox(srno)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("srno").value= srno;
	window.showModalDialog("EditTripInfo.jsp?action=otherMiscExp&trvlcode="+trvlcode+"&srno="+srno,null,"dialogWidth:700px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
function openMiscExpBox(srno)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("srno").value= srno;
	window.showModalDialog("EditTripInfo.jsp?action=miscExp&trvlcode="+trvlcode+"&srno="+srno,null,"dialogWidth:550px; dialogHeight:160px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
function openImprestDetails(dparti)
{
	var trvlcode = document.getElementById("trvcode").value;
	document.getElementById("particulars").value = dparti;
	window.showModalDialog("EditTripInfo.jsp?action=ImprestDetails&trvlcode="+trvlcode+"&dparti="+dparti,null,"dialogWidth:600px; dialogHeight:200px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="TravelDetails.jsp?action=show&trcode="+trvlcode;
}
</script>
 <script type="text/javascript">
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
	}
</script>
 
<script type="text/javascript">

function goBack(){
	history.go(-1);
}

function validate()
{
  /* var fromDate=document.travelreportform.frmdate.value;
   var toDate=document.travelreportform.todate.value;
   fromDate = fromDate.replace(/-/g,"/");
	toDate = toDate.replace(/-/g,"/"); */
   
   
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
	  document.getElementById("EMPNO").focus();
	  return false;
	  }
	
	var EMPNO1 = document.getElementById("EMPNO1").value;
    
	if (document.getElementById("EMPNO1").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("EMPNO1").focus();
		return false;
	}
	var atpos=EMPNO1.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  document.getElementById("EMPNO1").focus();
	  return false;
	  }
	
 var emp_no = [];
 var emp_no1 = [];
	 emp_no=EMPNO.split(":");
	 emp_no1=EMPNO1.split(":");
	 if(emp_no1 > emp_no)
		 {
		 
		 alert("Please Select Proper  Employee Number");
		 return false;
		 }
	 
}
function confirmation(){
	var conf = confirm(" Delete Record ?");
	if(conf==true)
	{
		window.forward="TravelDetails.jsp?action=show&trcode="+trvlcode;
		return true;
	}
	else 
	{
		return false;
	}
}
</script>
<% 
TransactionBean trbn  = new TransactionBean();
String action=request.getParameter("action")!=null?request.getParameter("action"):"";
int trcode = request.getParameter("trcode")==null?0:Integer.parseInt(request.getParameter("trcode"));

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

ArrayList<TripBean> al = new ArrayList<TripBean>();
TripHandler trh = new TripHandler();
al = trh.getTripDetails(trcode);

ArrayList<TripBean> list = new ArrayList<TripBean>();
list = trh.getAcImprestDetails(trcode);

%>

</head>
<body style="overflow: hidden;"> 
<input type="hidden" name="trvcode" id="trvcode" value="<%=trcode%>">

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Search Travel Details </h1>
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
			<div id="table-content">
			<center>
			 
			
			<%if(!action.equalsIgnoreCase("show")){%>
			
			<form action="TravelReportServlet?action=getDetails" method="post" >
				
			<table class="customers">
			<tr class="alt">
						<td><font style="font-weight: bold;">Enter Employee Name / Id &nbsp;&nbsp;</font></td>
						<td colspan="10"><input type="text" name="EMPNO" size="41" id="EMPNO" onclick="showHide()" title="Enter Employee Name or No." >
						<input type="Submit" value="Search" onclick="return TakeCustId()" class="myButton"/>
						</td>
					</tr>
					
					</table>
			</form>
			<%} %>
			<form>  
						
			 <table  id="customers"  align="center" >
			 
			 <%
					if(action.equalsIgnoreCase("getdata"))
					{
						trbn = (TransactionBean) request.getAttribute("trbn");
						System.out.println("in getdata");
			
			 %>
				 	<tr class="alt">
						<td>Employee No</td>
						<td><input type="text" name="EMPNO" id="EMPNO" readonly="readonly" value="<%=trbn.getEmpno() %>" ></td>
						<td>Employee Name</td>
						<td colspan="3" ><input type="text" name="EMPNO1" id="EMPNO1" size="45" readonly="readonly" value="<%=trbn.getEmpname() %>" ></td>
					</tr>
					
					<tr class="alt" align="center" valign="middle" bgcolor="#CCCCCC" height="auto">
					  <th>Travel Code</th>
					  <th>Tour Report Name</th>
					  <th>From Date </th>
					  <th>To Date</th>
					  <th>Settlement Status</th>
					  <th>Details</th>
					    
		     		</tr>
	    
	 
					<%	
						ArrayList<TripBean> result=new ArrayList<TripBean>();
						result = (ArrayList<TripBean>)request.getAttribute("list");
						if(result.size()!=0)
						{
							for(TripBean hb:result)
							{
						%>
						 
						<tr class="alt" align="center">
						 	 <td><%=hb.getTRCODE() %></td>
						 	 <td><%=hb.getTOURRPT()%></td>
						  	 <td> <%=output.format(sdf.parse(hb.getSTARTDATE())) %></td>
						  	 <td><%=output.format(sdf.parse(hb.getENDDATE())) %></td>
						  	 <%-- <% int status = trh.getState(hb.getTRCODE()); --%>
						  	 <% int status = trh.getState(hb.getTRCODE());
						  	 	if(status == 41){ %>
						  	 <td>Done</td>
						  	 <%}else{ %>
						  	 <td>Pending..</td>
						  	 <%} %>
						  	 <td><input type="button" value="Show" onclick="window.location='TravelDetails.jsp?action=show&trcode=<%=hb.getTRCODE() %>'" /></td>
															    
						</tr>
					 <%
							}
						}
						else{ %>
						<tr class="alt"><td colspan="6">No Records Found.. </td></tr>
						<%}
						}
					%> 
					
			  </table>
			
     	    </form>

	<%		 
	if(action.equalsIgnoreCase("show"))
		{
			int status = trh.getState(trcode);	
		%>
			
	<!-- <div align="left"><a href="javascript:history.go(-1)"><img src="images/forms/back_button.gif"  height=34 width=45></a></div> -->		
								
	<div style="height: 515px; overflow-y: scroll; width: auto" id="div1" align="center">		 
	<form>
	<table id="customers">
		<tr class="alt"><td colspan="7"><b>A) Journey Details</b></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th width="80">Sr.No</th>
			<th width="80">Date</th>
			<th width="80">From</th>
			<th width="80">To</th>
			<th width="80">Mode of Travel</th>
			<th width="80">Class</th>
			<th width="80">Fair</th>
			<th width="80">Amount</th>
			<%if(status == 1){ %>
			<th width="100">Edit / Delete</th>
			<%} %>
		</tr>
		<% 
		    for(TripBean tb : al){
			
			 String TMode = new LookupHandler().getLKP_Desc("TM", tb.getDTMODE());
			 String TClass = new LookupHandler().getLKP_Desc("TC", tb.getDCLASS());
			 
			 if(trcode == tb.getTRCODE()){
				
				if(tb.getDTYP()=='A'){
					
		%>
		<tr class="alt" align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDFROM() %></td>
			<td><%=tb.getDTO() %></td>
			<td><%=TMode %></td>
			<td><%=TClass %></td>
			<td><%=tb.getDFAIRDP() %></td>
			<td><%=tb.getDAMT() %></td>
			<%if(status == 1){ %>
			<td><input type="button" value="Edit" onclick="openJourneyBox('<%=tb.getSRNO()%>')">
				<input type="button" value="Delete" onclick="deleteRecord('delete','journey','<%=tb.getSRNO()%>','TravelDetails.jsp?action=show&trcode=<%=trcode%>')"/>
				</td>
			<%} %>
		</tr>
		<%  }
				}		 
			 }%>
		</table>
		
	<br>
	
	<table id="customers">
		<tr class="alt"><td><b>B) Local Conv. Exp. Details</b></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th width="80">Sr.No</th>
			<th width="80">Date</th>
			<th width="80">From</th>
			<th width="80">To</th>
			<th width="180">Mode of Local Conv.</th>
			<th width="80">Distance</th>
			<th width="80">Amount</th>
			<%if(status == 1){ %>
			<th width="100">Edit / Delete</th>
			<%} %>
		</tr>
		<%
		for(TripBean tb : al){
			
			String TMode = new LookupHandler().getLKP_Desc("TM", tb.getDTMODE());				
			if(trcode == tb.getTRCODE()){
							
			  if(tb.getDTYP()=='B'){
				 %>
					
		<tr class="alt" align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDFROM() %></td>
			<td><%=tb.getDTO() %></td>
			<td><%=TMode %></td>
			<td><%=tb.getDFAIRDP() %></td>
			<td><%=tb.getDAMT() %></td>
			<%if(status == 1){ %>
			<td><input type="button" value="Edit" onclick="openLocalConvBox('<%=tb.getSRNO()%>')">
				<input type="button" value="Delete" onclick="deleteRecord('delete','localExp','<%=tb.getSRNO()%>','TravelDetails.jsp?action=show&trcode=<%=trcode%>')"/>
			<%} %>
		</tr>
			<% }
				}
			}%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td><b>C) Food Exp. Details</b></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th width="80">Sr.No</th>
			<th width="90">Date</th>
			<th width="100">From</th>
			<th width="100">To</th>
			<th width="80">No Of Days</th>
			<th width="100">Per Day</th>
			<th width="100">Amount</th>
			<%if(status == 1){ %>
			<th width="110">Edit / Delete</th>
			<%} %>
		</tr>
		<%
		for(TripBean tb : al){
				
			if(trcode == tb.getTRCODE()){
							
			  if(tb.getDTYP()=='C'){
			%>
					
		<tr class="alt" align="center">
				<td><%=tb.getSRNO()%></td>
				<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
				<td><%=tb.getDFROM() %></td>
				<td><%=tb.getDTO() %></td>
				<td><%=tb.getNOFDAY() %></td>
				<td><%=tb.getDFAIRDP() %></td>
				<td><%=tb.getDAMT() %></td>
				<%if(status == 1){ %>
				<td><input type="button" value="Edit" onclick="openFoodExpBox('<%=tb.getSRNO()%>')">
					<input type="button" value="Delete" onclick="deleteRecord('delete','foodExp','<%=tb.getSRNO()%>','TravelDetails.jsp?action=show&trcode=<%=trcode%>')"/>
				<%} %>
		</tr>
			<% }
				}
			}	%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td><b>D) Other Misc. Exp. Details</b></td></tr></table>
		<table id="customers">
		<tr class="alt">
			<th width="80">Sr.No</th>
			<th width="120">Particulars</th>
			<th width="150">Name of the Party</th>
			<th width="100">Bill No.</th>
			<th width="100">Date</th>
			<th width="100">Amount</th>
			<%if(status == 1){ %>
			<th width="120">Edit / Delete</th>
			<%} %>
		</tr>
		<%
		for(TripBean tb : al){
				
			if(trcode == tb.getTRCODE()){
							
				if(tb.getDTYP()=='D'){
			%>
		<tr class="alt" align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=tb.getDPARTI() %></td>
			<td><%=tb.getDPNAME() %></td>
			<td><%=tb.getDBILLNO() %></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDAMT() %></td>
			<%if(status == 1){ %>
			<td><input type="button" value="Edit" onclick="openOtherMiscExpBox('<%=tb.getSRNO()%>')">
				<input type="button" value="Delete" onclick="deleteRecord('delete','otherExp','<%=tb.getSRNO()%>','TravelDetails.jsp?action=show&trcode=<%=trcode%>')"/>
			<%} %>
		</tr>
			<% }
				}
			}	%>
	</table>
	<br>
	
	<table id="customers">
		<tr class="alt"><td><b>E) Misc. Exp. Details</b></td></tr></table>
		<table id="customers">
			<tr class="alt">
			<th width="80">Sr.No</th>
			<th width="100">Date</th>
			<th width="100">Particulars</th>
			<th width="100">Location</th>
			<th width="100">Amount</th>
			<%if(status == 1){ %>
			<th width="100">Edit / Delete</th>
			<%} %>
		</tr>
		<%
		for(TripBean tb : al){
				
			if(trcode == tb.getTRCODE()){
							
				if(tb.getDTYP()=='E'){
		 %>
		<tr class="alt" align="center">
			<td><%=tb.getSRNO()%></td>
			<td><%=output.format(sdf.parse(tb.getDDATE())) %></td>
			<td><%=tb.getDPARTI() %></td>
			<td><%=tb.getDLOCATION() %></td>
			<td><%=tb.getDAMT() %></td>
			<%if(status == 1){ %>
			<td><input type="button" value="Edit" onclick="openMiscExpBox('<%=tb.getSRNO()%>')">
				<input type="button" value="Delete" onclick="deleteRecord('delete','miscExp','<%=tb.getSRNO()%>','TravelDetails.jsp?action=show&trcode=<%=trcode%>')"/>
			<%} %>
		</tr>
		<% }
				}
			} %>
	</table>
	<input type="hidden" name="srno" id="srno" value="">
	<br>
	
		<table id="customers">
			<tr class="alt"><td><b>F) A/C of Imprest Details</b> </td></tr></table>
			<table id="customers">
				<tr class="alt">
					
					<th width="80">Date</th>
					<th width="200">Particulars</th>
					<th width="80">Receipt(Rs)</th>
					<th width="80">Payment(Rs)</th>
					<th width="80">Amount</th>
					<%if(status == 1){ %>
					<th width="80">Edit</th>
					<%} %>
				</tr>
				<%
				for(TripBean tb : list){
					
				 %>	
				<tr class="alt">
					
					<td align="center"><%=output.format(sdf.parse(tb.getACDATE())) %></td>
					<td><%=tb.getACPARTI() %></td>
					<td align="center"><%=tb.getACREPTNO() %></td>
					<td align="center"><%=tb.getACPAYMNT() %></td>
					<td align="center"><%=tb.getACAMT() %></td>
					<%if(status == 1){ %>
					<td align="center"><input type="button" value="Edit" onclick="openImprestDetails('<%=tb.getACPARTI()%>')">
										<input type="hidden" name="particulars" id="particulars" value=""></td>
					<%} %>
				</tr>
			
				<% }
				for(TripBean tb : list){
				if(tb.getACPARTI().equalsIgnoreCase("Opening Balance")){ %>
				
				<tr class="alt" ><th align="center" colspan="7">Tour Report</th></tr>
				<tr class="alt" ><td colspan="7"><textarea rows="2" cols="100" name="TReport" readonly="readonly"><%=tb.getTOURRPT() %></textarea></td></tr>
				<%}
				}
				%>
			</table>
			<br/>
		
		</form>
		<%if(status == 1){ %>
	 	<input type="submit" name="stlmnt" value="Go For Settlement" onclick="window.location.href='TravelSettlement.jsp?trcode=<%=trcode%>'"/>&nbsp;&nbsp;&nbsp;&nbsp;
	 	<input type="reset" value="Cancel" onclick="javascript:history.go(-1)"/>
		<%} 
		}%>
</div>	    
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