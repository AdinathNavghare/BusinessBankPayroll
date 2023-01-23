<%@page import="com.ibm.icu.math.BigDecimal"%>
<%@page import="javax.activity.ActivityRequiredException"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.EmpAddressBean"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmpAwardBean"%>
<%@page import="payroll.Model.EmpExperBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.EmpFamilyBean"%>
<%@page import="payroll.Model.EmpQualBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmpAwrdHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.DAO.EmpExperHandler"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.DAO.EmpAddrHandler"%>
<%@page import="payroll.DAO.EmpFamHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT Calculation </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>



<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<%
try
{
String empno = (String)session.getAttribute("empno1")==null?"":session.getAttribute("empno1").toString();
String action=request.getParameter("action")==null?"":request.getParameter("action");
 EmployeeHandler eh = new  EmployeeHandler();
 TranHandler th=new TranHandler();
 EmployeeBean empbean=new EmployeeBean();
 LookupHandler lkp = new LookupHandler();
 String ename = "";
 ArrayList list=new ArrayList();
 String emp1=request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");
 String emp=emp1;
 String stringYear="";
 int year=0,nextYear=0;
 if(action.equalsIgnoreCase("salstruct"))
 {
	 //session.setAttribute("ename", emp1.toString());
	 stringYear=(request.getParameter("date")==null?(String)session.getAttribute("year").toString():request.getParameter("date"));
	 year=Integer.parseInt(stringYear);
	  nextYear=year+1;
	if(emp1.equals("")){
		emp = empno;
		ename = lkp.getLKP_Desc("ET",Integer.parseInt(empno));
	} else {
		String []a= request.getParameter("EMPNO").split(":");
		emp =a[2]; // session.getAttribute("empno1")==null?"0":session.getAttribute("empno1").toString();	
	}
	empbean =eh.getEmployeeInformation(emp);
	session.setAttribute("empno1", emp);
	session.setAttribute("year", year);
	list=th.getTranforSalalryStruct(Integer.parseInt(emp),year);
	
 }

%>
<%
	String pageName = "salaryStructure.jsp";
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

%>


<script language="javascript">
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
	
	var date = document.getElementById("date").value;
	if(date==""){
		alert("please select the Date !");
		document.getElementById("date").focus();
		return false;
	}
	}
</script>

<script>
	jQuery(function() {
          //$("#EMPNO").autocomplete("list.jsp");
		 $("#EMPNO").autocomplete("searchList.jsp");
	});
</script>

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
    <script>
    function changeYear(){
    	if(document.getElementById("date").value=="")
    	{
    	alert("Please select year");
    	return false;
    	}
    	var date=document.getElementById("date").value;
    	document.getElementById("toYear").value=parseInt(date)+1;	
    }
    </script>
    <%if(action.equalsIgnoreCase("salstruct"))
    { %>
    <script>
    function focus(){
    	 document.getElementById("date").style.borderWidth = "thick";
    	 document.getElementById("date").style.borderColor = "black";
    	 document.getElementById("toYear").style.borderWidth = "thick";
    	 document.getElementById("toYear").style.borderColor = "black";
    }
    </script>
    <% }%>
 
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>


</head>
<style >
#headerlist
{
color:#CCCCCC;
}
</style>
<body style="overflow:hidden; " onload="focus()" > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>

 <% LookupHandler lh = new LookupHandler();
 EmpOffHandler ofh = new EmpOffHandler();
 String date=ReportDAO.getSysDate().substring(7,11);
 int dateInInt=Integer.parseInt(date);
 %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:78%; " >
<!-- start content -->
<div id="content" >

	<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<%if(emp.equals("") && empno.equals("")){ %>
			<div class="step-dark-left"><a href="salaryStructure.jsp">ITR Wages</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">2</div>
			<div class="step-light-left"><a href="Form16Entry.jsp" > Form 16</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div>
			<%} else { %>
			<div class="step-dark-left"><a href="salaryStructure.jsp?action=salstruct">ITR Wages</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">2</div>
			<div class="step-light-left"><a href="Form16Entry.jsp?action=getdata" > Form 16</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="taxCompute.jsp">Tax Computation</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="setParameter.jsp">Setup Parameters</a></div>
			<div class="step-light-round">&nbsp;</div>
            <div class="clear"></div>
            <%} %>
	</div>
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
 <form  action="salaryStructure.jsp?action=salstruct" method="Post" onSubmit="return TakeCustId()">
				<table border="1" align="center">
					<tr >
						
							<td><b>Enter Employee Name or Emp-Id </b>
						<input type="text" name="EMPNO"  placeholder="Enter Employee Name"
						style="margin-left: 15px;"
						 size="40" id="EMPNO" title="Enter Employee Name"   ></td>
						<%-- <%=emp1==""?ename:emp1%> --%>
						<td style="width:150px; text-align:center;"><b>Financial Year : April-</b></td>
					<td>
					<select id="date" onchange="changeYear()" name="date">
						
									
<%
if(action.equalsIgnoreCase("salstruct"))
{
		
	if(year==dateInInt-1){
		%>
						<option value="">Select</option>	
						<option value="<%=dateInInt-1%>" selected><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
<% }else if(year==dateInInt){
	%>
	<option value="">Select</option>	
	<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
	<option value="<%=dateInInt%>" selected><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
<% } else { %>
	<option value="">Select</option>	
	<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
	<option value="<%=dateInInt%>" ><%=dateInInt%></option>
	<option value="<%=dateInInt+1%>" selected><%=dateInInt+1%></option>	


<%}
} else {%>

						<option value="">Select</option>	
						<option value="<%=dateInInt-1%>"><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
			<% } %>			
</select>
</td>
									<td style="width:80px; text-align:center;"><b>To March-</b></td>
									<td>
									<%
if(action.equalsIgnoreCase("salstruct"))
{
%>	
									<input type="text" name="toYear" id="toYear" 
							style="width: 40px;" readonly="readonly" value="<%=nextYear%>" />
<% } else {   %>	
							<input type="text" name="toYear" id="toYear" 
									style="width: 40px;" readonly="readonly" />
					<% }%>		
					</td>				
					<!-- 	<td><input name="date" id="date" style="margin-left: 25px;"
						 readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td> -->
						<td valign="top"> <input type="submit" class="myButton" style="margin-left:50px;"
						  value="Submit" ></td>
						
	</tr>
	<tr></tr>
</table>
</form> 
<br/>		
			
<%
if(action.equalsIgnoreCase("salstruct"))
{
	
	String employeeName=session.getAttribute("empno1").toString();
%>


<table style="border:none;width: 500px;">
<tr style="font-size: 10;font-weight: bold;">
<td style="text-align: left;" >EMPLOYEE NAME : <%=lkp.getLKP_Desc("ET", Integer.parseInt(employeeName))%> </td>
 <td style="text-align: right;"  >FOR YEAR : <%=year%>-<%=nextYear%></td> </tr>
		</table>	
<%-- <center> <h2> Employee Name : <%=empbean.getFNAME()+" "+empbean.getLNAME() %> </h2> --%>
<%

Date d=new Date();
String yy[]=ReportDAO.getSysDate().split("-");
int yyyy=Integer.parseInt(yy[2]);
int mm=d.getMonth();
String startdate ="",enddate="";
if(mm>=4)
{
	 startdate=yyyy+"-04-01";enddate=(yyyy+1)+"-03-31";
}
else
{
	startdate=(yyyy-1)+"-04-01";enddate=(yyyy)+"-03-31";
}
%>
<%-- <center> <h2>Salary Structure for The FY <%=startdate %> to <%=enddate %></h2></center> --%>


<table id="customers">
  <tr class="alt">
  <td>
  <table>
  <tr>
  <th style="width: 175px; "> &nbsp;</th>
    <!-- <th style="width: 40px; ">Incl PF</th> -->
    <th style="width: 50px; ">Apr</th>
    <th style="width: 50px; ">May</th>
    <th style="width: 50px; ">Jun</th>
    <th style="width: 50px; ">Jul</th>
    <th style="width: 50px; ">Aug</th>
    <th style="width: 50px; ">Sept</th>
    <th style="width: 50px; ">Oct</th>
    <th style="width: 50px; ">Nov</th>
    <th style="width: 50px; ">Dec</th>
    <th style="width: 50px; ">Jan</th>
    <th style="width: 50px; ">Feb</th>
    <th style="width: 50px; ">Mar</th>
    <th style="width: 50px; ">Total</th>
    <!-- <th >Perks</th>
    <th style="width: 50px; ">Bonus</th>
    <th style="width: 50px; ">Others</th>
    <th style="width: 50px; ">Gross</th> -->
   </tr>
    </table>
    </td>
  </tr>
  <tr  class="alt">  
		<td>
		<div  style="overflow-y:auto; max-height: 390px;">
		<table id="customers">
  <%
  int cds[]={101,108,103,104,105,106,126,130,131,132,133,202,201,228,205,212,225};
int z=0;
float hra=0;
float basic=0;
float tds=0;
double totalInc=0;
  for(int i=0;i<list.size();i++)
  {
	  
	
  ArrayList list_in=(ArrayList)list.get(i);
  for(int k=0;k<list_in.size();k++)
  {
	  %>
		
		<tr class="alt">
	  <%
	  ArrayList<TranBean> alist=new ArrayList<TranBean>();
	  alist=(ArrayList<TranBean>)list_in.get(k);
	  
	  long total=0;
	  for(int j=0;j<alist.size();j++)
	  {
		  TranBean  tb = new TranBean();
		  tb=alist.get(j);
		  total+=tb.getNET_AMT();
		if(j==0)
		{
		  %>
		 
		  <td style="width: 181px; "> <%=(list_in.size()-1==k?(i==0?"TOTAL INCOME" : i==1?(list_in.size()-2==k?"TOTAL DEDUCTION":"NET PAY"):""):(CodeMasterHandler.getCDesc(tb.getTRNCD())==""?"TOTAL DEDUCTION":CodeMasterHandler.getCDesc(tb.getTRNCD())))%> </td>
			<!-- <td style="width: 40px;">   &nbsp; </td> -->
		  
		<%} 
		/* if(tb.getNET_AMT()==0)
		{ */
		%>
		
		<%-- <td align="right" style="width: 51px;"><%=TranHandler.LastSalaryFortdsDisplay(Integer.parseInt(emp), tb.getTRNCD())%></td> --%>  
		<%
		 /*  total+=TranHandler.LastSalaryFortdsDisplay(Integer.parseInt(emp), tb.getTRNCD());
		}else{ */%> 
		<td align="right" style="width: 51px;"><%=tb.getNET_AMT()%></td>    
			
		
		<%/* } */%>
		<%	if(tb.getTRNCD()==103){
			  hra += tb.getNET_AMT();
		   } else if (tb.getTRNCD()==101) {
			  basic += tb.getNET_AMT(); 
		   }else if (tb.getTRNCD()==228) {
				  tds += tb.getNET_AMT(); 
			   }
			if(i==0)//j==10 && k==10){
				{	totalInc= total;
			}
			
	  }
  
  %>
 <td align="right" style="width: 50px;"><%=BigDecimal.valueOf(total).setScale(1)%></td>
 <%--   
  <td style="width: 50px; ">0.0</td>
  <td style="width: 50px; ">0.0</td>
  <td style="width: 50px; ">0.0</td>
  
  <td style="width: 50px; "><%=total%></td>
  
    --%>
  </tr>
  
  
  
  <%}
  %>
  <tr class="alt"><td colspan="19"> &nbsp;</td></tr>
  
  <%
  }session.setAttribute("basic", basic);
  session.setAttribute("hra", hra); 
  session.setAttribute("tds", tds); 
  session.setAttribute("totalInc", totalInc); %>
  
  </table>
  </div>
  </td>
  </tr>
  
</table>



</center>


								



<%

}
}
catch(Exception e)
{
e.printStackTrace();

%>
<script type="text/javascript">

window.location.href="login.jsp?action=0";

</script>
<%
}%>






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