<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="payroll.Core.originalNumToLetter"%>
<%@page import="payroll.Model.SlabBean"%>
<%@page import="payroll.DAO.SlabHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.OnAmtBean"%>
<%@page import="payroll.DAO.OnAmtHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="java.util.Locale"%>
<%@page import="com.ibm.icu.text.NumberFormat"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.AutopostBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Gratuity Calculation  &copy; DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->


<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
	
	function Clickheretoprint(panel)
	{ 
			var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
			    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
			var content_value = document.getElementById(panel).innerHTML; 
			
			var docprint=window.open("","",disp_setting); 
				docprint.document.open(); 
				docprint.document.write('<html><head><title>Inel Power System</title> <link rel="stylesheet" href="css/cGraph1.css"> <link rel="stylesheet" href="css/Chart1.css">'); 
				docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
				docprint.document.write(content_value);          
				docprint.document.write('</center></body></html>'); 
				docprint.document.close(); 
				docprint.focus(); 
	}	
	
	function checkFlag()
	{
		var flag=document.getElementById("flag").value;	
			
		if(flag==1)
		{
			alert("Gratuity Posted Successfully !");
		}		
	}
	
</script>

<%
	String action = "first";
	String[] employee = null;
	CodeMasterHandler CMH = new CodeMasterHandler();
	OnAmtHandler OAH = new OnAmtHandler();
	EmployeeHandler EH = new EmployeeHandler();
	SlabHandler SH = new SlabHandler();
	EmployeeBean emp = new EmployeeBean();
	ArrayList<Integer> values = new ArrayList<Integer>();
	ArrayList<OnAmtBean> codes = new ArrayList<OnAmtBean>();
	String flag=request.getParameter("flag")==null?"":request.getParameter("flag");
	String empno="";
	String ename="";
	String today="";
	String years="";
	String diff="";
	String minYears="";
	String daysPerYear="";
	String maxamount="";
	int diffYear=0;
	int month=0;
	float amt =0;
	int totdiffYear=0;
	int totmonth=0;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	Date dt = new Date();
	Date dt1 = new Date();
	try
	{
		action = request.getParameter("action")==null?"first":request.getParameter("action");
		if(action.equalsIgnoreCase("calc"))
		{
			Connection con=ConnectionManager.getConnection();
			
			employee = request.getParameter("EMPNO").split(":");
			empno = employee[2];
			ename = employee[0];
			
			emp = EH.getEmployeeInformation(empno);
			
			values = OAH.getOnAmtValues(Integer.parseInt(empno), 144);	// replaced 9 to 	144 for Gratuity
			
			codes = OAH.getOnAmtList(0, 144);
					
			System.out.println("if size 0 then insert on amt and slb");		
			System.out.println("size="+values.size());
			
			ArrayList<SlabBean> SlabList = SH.getSlabs(0, 144);	//	replaced 9 to 	 144 for Gratuity
			SlabBean SB = SlabList.get(0);
			
			minYears = String.valueOf(SB.getMINAMT());
			daysPerYear = String.valueOf(SB.getFIXAMT());
			maxamount= String.valueOf(SB.getMAXAMT());
			
			today = (emp.getDOL()==null || emp.getDOL()=="")?sdf.format(dt):emp.getDOL();
			
			String S_Date="";
			
			
			PreparedStatement pst2=con.prepareStatement("select * from GratuityDetails where empno="+empno+" and " +
					"trndt =(select max(trndt) from GratuityDetails where empno="+empno+" )");
          	ResultSet rs=pst2.executeQuery();
          	
          	if(rs.next())
          	{
          		S_Date= sdf.format(rs.getDate("trndt"));
          		System.out.println(empno+" IF============="+S_Date);
          	}
          	else
          	{   		
          		S_Date=emp.getDOJ();
          		System.out.println(empno+" ELSE============="+S_Date);
          	}
	
          	
          	dt = sdf.parse(emp.getDOJ());
			dt1 = sdf.parse(today);
			
			float totdateDiff = (dt1.getTime() - dt.getTime())/(1000*60*60*24*365F);
			String totdiff=String.valueOf((dt1.getTime() - dt.getTime())/(1000*60*60*24*365F));
			
			totdiffYear =(int)Math.floor(totdateDiff);
			totmonth =(int) Math.floor((totdateDiff-totdiffYear)*12);
		
			dt = sdf.parse(S_Date);
			dt1 = sdf.parse(today);
			
			float dateDiff = (dt1.getTime() - dt.getTime())/(1000*60*60*24*365F);
			diff=String.valueOf((dt1.getTime() - dt.getTime())/(1000*60*60*24*365F));
			diffYear =(int)Math.floor(dateDiff);
			
			month =(int) Math.floor((dateDiff-diffYear)*12);
			
		}
		
	}
	catch(Exception e)
	{
		System.out.println("first");
		e.printStackTrace();
	}
%>
<%
	/* String pageName = "Gratuity.jsp";
	try
	{
		ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
		if(!urls.contains(pageName))
		{
			response.sendRedirect("NotAvailable.jsp");
		}
	}
	catch(Exception e)
	{
		//response.sendRedirect("login.jsp?action=0");
	} 
	 */
%>


</head>
<body style="overflow: hidden;" onLoad="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="height: 600px;" >
<!-- start content -->
<div id="content" style="max-width: 100% !important;">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Gratuity Calculation</h1>
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
		<td align="center">
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content" >
				<form action="Gratuity.jsp?action=calc" method="post">
					<table id="customers">
						<tr>
							<td>Enter Employee Name / Number&nbsp;&nbsp;<input type="text" id="EMPNO" name="EMPNO" size="50" value="<%=request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO")%>"></td>
							<td><input type="submit" value="Calculate"></td>
						</tr>
					</table>
				</form>
				
			  <p>&nbsp;</p>
			  <a href="javascript:Clickheretoprint('gp')">	Click here to print</a>
			  <div id="gp">
			  
			   
			  
				<table width="897"  border="1" id="customers">
				<tr align="center">
                       <th colspan="2"><b style="font-size: 15px;color: white">Gratuity Details</b> </th>
                  </tr>
				
				
                  <tr>
                    <td>Employee Code/Number :-&nbsp;&nbsp;<b><%=empno==null?"":empno%></b></td>
                    <td>Name of an Employee :-&nbsp;&nbsp;<b><%=ename==null?"":ename%></b></td>
                  </tr>
                  <tr>
                    <td width="343">Date of Joining :-&nbsp;&nbsp;<b><%=emp.getDOJ()==null?"":emp.getDOJ()%></b></td>
                    <td width="347">Date of Leaving / Todays Date :-&nbsp;&nbsp;<b><%=today%></b></td>
                  </tr>
                  <tr>
                    <td>Years of Service Completed :-&nbsp;&nbsp;<b><%=totdiffYear%> &nbsp;Years and &nbsp;<%=totmonth %> &nbsp;Months</b></td>
                     <td>Years of Service for Eligibility :-&nbsp;&nbsp;<b><%=diffYear+"."+month%> &nbsp;Years</b></td>
                  </tr>
                  <tr align="center">
                       <td colspan="2" bgcolor="#1F5FA7"><b style="font-size: 15px;color: white">Gratuity Dependency</b> </td>
                  </tr>
                  
                  <tr align="center">
                    <td colspan="2"><br/>
                    	<table width="477" border="1">
	                      <tr align="center" bgcolor="#1F5FA7">
	                        <td width="229" scope="col"><b style="color: white"> Salary Code </b></td>
	                        <td width="232" scope="col"><b style="color: white">Current Value</b> </td>
	                      </tr>
	                      <%
	                      	int i=0;
	                      	int total = 0;
	                      	for(OnAmtBean OAB:codes)
	                      	{
	                 
	                      		total+= values.get(i);
	                      %>
		                      <tr>
		                        <td>&nbsp;<b><%=CodeMasterHandler.getCDesc(OAB.getONAMTCD())%></b></td>
		                        <td align="right">&nbsp;<b><%=values.get(i) %></b></td>
		                      </tr>
                      	<%
                      		i++;
	                      	}
						%>
						<tr>
							<td>&nbsp;<b>Total</b></td>
							<td  align="right">&nbsp;<b><%=total %></b></td>
						</tr>
                    	</table><br/>
                    </td>
                  </tr>
                  <tr>
                    <td>Gratuity Rule :-&nbsp;&nbsp;<b><%=daysPerYear%> &nbsp; Days Per Year </b></td>
                    <%
                    	if(total >= 0)
                    	{
                    %>
                    	<td>&nbsp;<b>(Total/30)*(Days per Year)*(Years of Service Completed)</b></td>
                    <%
                    	}
                    	else
                    	{
                    %>
                     	<td>&nbsp;</td>
                    <%
                    	}
                    %>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <%
                    	if(action.equalsIgnoreCase("calc"))
                    	{
	                    	if(Float.parseFloat(diff) >= Integer.parseInt(minYears))
	                    	{
                    %>
                    			<td>&nbsp;<b>(<%=total%>/30)*(<%=daysPerYear %>)*(<%=diff %>)</b></td>
                    <%
                    		}
                    		else
                    		{
                    %>
                     			<td>&nbsp;<b style="color: red">Not Eligible for Gratuity, Years of Service is less than <%=minYears %>.</b></td>
                    <%
                    		}
                    	}
                    	else
                    	{
                    %>
                    		<td>&nbsp;</td>
                    <%
                    	}
                    %>
                  </tr>
                  <tr>
                    <td>Gratuity Amount to be Paid :- </td>
                    <%
	                    if(action.equalsIgnoreCase("calc"))
	                	{
	                    	NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
	                    	if(Float.parseFloat(diff) >= Integer.parseInt(minYears))
	                    	{
	                    		System.out.println(" if diff============="+diff);
	                    		 amt = (total/30)*Integer.parseInt(daysPerYear)*Float.parseFloat(diff);
                    %>
	                    	<td>&nbsp;<b><%=format.format(Math.round(amt<Integer.parseInt(maxamount)?amt:Integer.parseInt(maxamount)))%><br/>
	                    	<%=originalNumToLetter.getInWords(String.valueOf(Math.round(amt))) %>
	                    	</b></td>
                    <%
	                    	}
	                    	else
	                    	{
                    %>
                     			<td>&nbsp;<b style="color: red">Not Eligible for Gratuity, Years of Service is less than <%=minYears %>.</b></td>
                    <%
                    		}
	                	}
                    	else
                    	{
                    %>
                    		<td>&nbsp;</td>
                    <%
                    	}
                    %>
                  </tr>
                  <%
	                    if(action.equalsIgnoreCase("calc"))
	                	{
	                    
               
               if(amt>0)
                	  {%>
                <tr>
                    <td>Gratuity Post :- </td>
                    
	                    	
	                
                 
                  <td>
                  <form action="Gratuity?action=postgratuity&empno=<%=empno%>" method="post">
                  
                  <input type="submit" value="Post Grauity">
                 
                  </form>
                  </td>
                  </tr>
                   <%} 
                   }
                 %> 
                 
                 
                 
                 <input type="hidden" id="flag" value='<%=flag%>'>
              </table>
              </div>
              <p><br/>
	          </p>
			</div>
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
    
<input type="hidden" id="action" name="action" value="<%=action%>">
</body>
</html>