<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="java.util.Calendar"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.ProjectBean"%>
<%@page import="payroll.DAO.ProjectListDAO"%>
<%@page import="payroll.Model.AdvanceBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.AdvanceHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Advance Application Form</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">


function getTranDetails() {
	
	var proj=document.getElementById("pp").value;
	
		
			var res = proj.indexOf(":"); 
			if(proj=="")
				{			
				//alert("Please Select Project !");
				}
			else
				{
				if(res<0)
					{
					//alert("Please Select Project !");
					document.getElementById("pp").value="";
					document.getElementById("pp").focus();
					}
				else
					{
			var p=proj.split(":");
			var prjCode = p[3];
			
				if(prjCode == ""){
					
				}
			
			else{
				
		proj=proj.replace(/ & /g," and ");
				window.location.href = "Advance.jsp?action=getdetails&prj="+prjCode;

			}
				}
			}
			
		}

function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {			  
	    return true;
	  }
	}	



function redirect()
{
	 var type= document.getElementById("type").value;
       var proj = document.getElementById("project").value;
    
       var res = proj.indexOf(":"); 
		if(proj=="")
			{			
			//alert("Please Select Project !");
			}
		else
			{
			if(res<0)
				{
				//alert("Please Select Project !");
				document.getElementById("project").value="";
				document.getElementById("project").focus();
				}
			else
				{
		var p=proj.split("::");
		
		var prjCode = p[1];
		
			if(prjCode == ""){
				
			}
		
		else{
			
	proj=proj.replace(/ & /g," and ");
			window.location.href = "Advance.jsp?action=getdetails&prj="+prjCode+"&sList="+type;

		}
			}
		}
       

}

function redirect_url(type )
{  
	
	
	
	if(type=="PENDING"){
	
		window.location = "Advance.jsp?sList="+type;
	}else if(type=="SANCTION"){
		window.location = "Advance.jsp?sList="+type;
	}else if(type=="CANCEL"){
		window.location = "Advance.jsp?sList="+type;
	}else 
		window.location = "Advance.jsp?sList="+type;

}
function validate()
{
	
	var typ = document.getElementById("emplist").value;
	if(typ==0){
		alert("please Select Employee");
		return false;
	}

	var month = document.getElementById("month").value;
	var date = document.getElementById("date").value;
	var months = month+"-"+date;	
	var amount=document.forms["advanceForm1"]["amount"].value;	

	
	if(month==0){
		alert("please Select Month");
		return false;
	}
		
	if(date==0){
		alert("please Select year");
		return false;
	}
	 

	var salary=document.getElementById("0").value;

	var salary1=document.getElementById("1").value;
	
	var salary2=document.getElementById("2").value;
	
	var salary3=document.getElementById("3").value;
	

	
	var salarypayable=salary.substring(11,salary.lastIndexOf('.')).split('.');
	
	var monthvalue= salary.substring(0,3);
	var salarymonth= salary.substring(0,8);
	
	
	var salarypayable1=salary1.substring(11,salary1.lastIndexOf('.')).split('.');
	
	var salarymonth1= salary1.substring(0,8);
	

	var salarypayable2=salary2.substring(11,salary2.lastIndexOf('.')).split('.');
	
	var salarymonth2= salary2.substring(0,8);
	
	var salarypayable3=salary3.substring(11,salary3.lastIndexOf('.')).split('.');
	
	var salarymonth3= salary3.substring(0,8);
	
	
	
	
	
    if(amount==""){
 	   alert("Enter the amount value..!");
 	   document.forms["advanceForm1"]["amount"].focus();
 	   return false;
    }
	
	
	if(months!=salarymonth && months!=salarymonth1 && months!=salarymonth2 && months!=salarymonth3)
	{
	alert("You Dont have Any Payable salary for the "+month+" "+date);
	return false;
	}	
	
		if(months==salarymonth){
			
			if(parseInt(amount) > parseInt(salarypayable) )
			{
			alert("You Advance value should not be greater than Any Payable salary for the "+month+"-"+date);
			return false;
			}
		}
	
		else if(months==salarymonth1){
		
			if(parseInt(amount) > parseInt(salarypayable1))
			{ 
			alert("You Advance value should not be greater than Any Payable salary for the "+month+"-"+date);
			return false;
			}
		}
		
		else if(months==salarymonth2){
			
			if(parseInt(amount) > parseInt(salarypayable2) )
			{
			alert("You Advance value should not be greater than Any Payable salary for the "+month+"-"+date);
			return false;
			}
		}
		else if(months==salarymonth3){
			if(parseInt(amount )> parseInt(salarypayable3) )
			{
			alert("You Advance value should not be greater than Any Payable salary for the "+month+"-"+date);
			return false;
			}
		}
		  if(isNaN(amount)){
		   		alert("Please enter  the  correct  numeric amount value.!");
		   		document.forms["advanceForm1"]["amount"].focus();
		   		return false;
		   	} 
	
	
}

</script>

<script>
	jQuery(function() {
		$("#pp").autocomplete("projlist.jsp");
	});
	
	function checkAdvance() {
		
		 var proj = document.getElementById("project").value;
		    
	       var res = proj.indexOf(":"); 
			
			var p=proj.split("::");
			
			var prjCode = p[1];
		
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Tranaction Saved Successfully");

		}
		if (f == 2) {
			alert("Employee Already Exist");
		}
		if (f == 3) {
			alert("Advance added Successfully");
			window.location.href = "Advance.jsp?action=getdetails&prj="+prjCode;
		}
		if(f==4){
			alert("Record is not added.Please apply again");
			
			
		}
	
	}

	
	
	<%

	String emp_no=request.getParameter("emp_info")==null?"":request.getParameter("emp_info");
	String proj=request.getParameter("prj")==null?"":request.getParameter("prj");
	EmpOffBean eoffbn = new EmpOffBean();


	
	String status="ALL";
	status=request.getParameter("sList")==null?"ALL":request.getParameter("sList");
	
	
	
	RoleDAO obj1=new RoleDAO();
	String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));
	LookupHandler lookuph= new LookupHandler();

	ArrayList<AdvanceBean> advanceList = new ArrayList<AdvanceBean>();
	AdvanceHandler advanceHandler = new AdvanceHandler();
	SimpleDateFormat dateFormat;

	ArrayList<TranBean> projEmpNolist = new ArrayList<TranBean>();
	ArrayList<TranBean> projEmpNmlist = new ArrayList<TranBean>();
	

	EmployeeHandler emph = new EmployeeHandler();
	EmployeeBean ebean = new EmployeeBean();
	EmployeeBean ebean1 = new EmployeeBean();
	String [] payable=null;
	ArrayList<AdvanceBean> list= new ArrayList<AdvanceBean>();
	if(!emp_no.equalsIgnoreCase(""))
	{
		
	  payable=advanceHandler.getPayble(emp_no);
	  
	}
	
	int eno = (Integer)session.getAttribute("EMPNO");
	EmpOffHandler eoffhdlr = new EmpOffHandler();
	eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));

	AdvanceHandler ADH= new AdvanceHandler();
	int site_id=(eoffbn.getPrj_srno());

	int empNo = Integer.parseInt(session.getAttribute("EMPNO").toString());
	ebean = emph.getEmployeeInformation(Integer.toString(empNo));

	if(proj=="")
	{
	 proj=Integer.toString(eoffbn.getPrj_srno());
	 //System.out.println("project"+proj);
	}
		
	String searchList = request.getParameter("sList")==null?"ALL":request.getParameter("sList");
	String action = request.getParameter("action")==null?"":request.getParameter("action");
	String error = request.getParameter("error")==null?"":request.getParameter("error");

	if(searchList.equalsIgnoreCase("PENDING")){
		advanceList = advanceHandler.advanceDisplay(empNo,"PENDING",proj,roleId);

		}else if(searchList.equalsIgnoreCase("SANCTION")){
			advanceList = advanceHandler.advanceDisplay(empNo,"SANCTION",proj,roleId);
		}else if(searchList.equalsIgnoreCase("CANCEL")){
			advanceList = advanceHandler.advanceDisplay(empNo,"CANCEL",proj,roleId);
		}else{
			advanceList = advanceHandler.advanceDisplay(empNo,"ALL",proj,roleId);
	}

	LookupHandler lh=new  LookupHandler(); 


	int trn=0;
	int keys=0;
	int empno1=0;
	int empno=0;
	int flag=-1;
	int prjCode = 0;
	 
		
		try
		{
		flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
		}catch(Exception e)
		{
	//		System.out.println("no flag value"+flag);
			if( request.getParameter("prj")==null)
			{
			session.setAttribute("prjCode", "");
			}
		}
		if(action.equalsIgnoreCase("getdetails"))
		{
			
			
			 prjCode = Integer.parseInt(request.getParameter("prj"));
			// System.out.println("thr firssssssssssssssssst prjcode"+prjCode);
		    session.setAttribute("prjCode", prjCode);
		  
		    session.setAttribute("projEmpNolist", projEmpNolist);
		    int i=0;
		    
		  
		    ArrayList<TranBean> arl=new ArrayList<TranBean>();
		    for(TranBean tbn : projEmpNolist){
		    	TranBean trbn = new TranBean();
		    	
		    	projEmpNmlist.add(trbn);
		    	
		    	
		   
		    	
		    } session.setAttribute("prjCode", prjCode);
		//    System.out.println("the employee are for selected project code as:"+prjCode+ " "+projEmpNmlist.size());
		}







	%>
	
	
	
	
	function getEmpInfo()
	{
		
		var emp_info=document.getElementById("emplist").value;
		
		var proj=document.getElementById("proj").value;
		window.location.href = "Advance.jsp?action=getdetails&prj="+proj+"&emp_info="+emp_info;
	}
	
</script>

</head>
<body onLoad="checkAdvance()" >

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>ADVANCE Master</h1>
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
			<div id="table-content" align="center" style=" max-height:520px; ">
			
	<form  id="form1" name="advanceForm1" action="AdvanceServlet?action=add" method="post" onsubmit="return validate()">
  <table style="width:60%"  id="customers">
  	<tr style="width:100%"><th colspan="2"><font color="white" size="3">ADVANCE  DETAILS</font></th></tr> 
   
   		<tr>
	
		<% if(request.getParameter("prj")==null)
		{
			%>
				<td>Project Name : 
			<input type="text" id="project" name="project" readonly="readonly" style="width: 80%; border: none;"  value="<%=eoffbn.getPrj_name()%>::<%=eoffbn.getPrj_srno() %>">
		
			
		<%
		}else
		{
		ProjectListDAO  pl=new ProjectListDAO();
		ProjectBean pb=new ProjectBean();
		pb=pl.getProjectInfo(Integer.parseInt(request.getParameter("prj")));
		%>
			<td>Current Project  : 
		<input type="text" id="project" name="project" readonly="readonly" style="width: 80%; border: none;" value="<%= pb.getSite_Name()%>::<%=pb.getSite_ID() %>">

		
		<%
		}
	   %></td>
	   <%
	   				 dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date date = new Date();
					 String currentdate = dateFormat.format(date);  
					%>
	   
	    </tr>
			<%if(roleId.equals("1"))
												{%>
												<tr>
												<td colspan="3">Project :
												<input type="text" id="pp" name="pp" style="width: 80%;" autofocus onClick="this.select();" value="<%=request.getParameter("proj")==null?"":request.getParameter("proj").replaceAll(" and ", " & ")%>" title="Enter a character to view the available project list (E.g - %) ">
												 <input type="Button" value="Submit" title="To change Employees Of particular Project Click here" onclick="getTranDetails()" />
										</td>		 </tr>
												
											<%}
											%>
  </table>
<br/>

	<div align="center">
		
		
		
		<table id="customers" width="400" border="1" style="float: none;">
	           
		<tr><th align="center" colspan="8"><font color="white" size="3">APPLY ADVANCE</font></th></tr>
	     <tr>
	      <td align="left">DATE</td>
	      <td><input name="tdate" id="tdate" type="text" readonly="readonly" value="<%=currentdate %>" size="20"></td>

<td>SELECT EMPLOYEE&nbsp; <font color="red"><b>*</b></font></td>
												<td >
												
												 <input type="hidden" name="proj" id="proj" value="<%=request.getParameter("prj")==null? Integer.toString(eoffbn.getPrj_srno()):request.getParameter("prj")%>">
												
												<select 
													style="width: 300px" name="emplist" id="emplist" onchange="getEmpInfo()">
														<option value="0" selected >Select</option>
														<%
															/* ArrayList<AdvanceBean> list= new ArrayList<AdvanceBean>();
																												
														AdvanceHandler ADH= new AdvanceHandler();
												    	 */
												    	 list=ADH.getEmpList(request.getParameter("prj")==null? Integer.toString(eoffbn.getPrj_srno()):request.getParameter("prj"));
												    	 %>
												    	
												    	 <%
												    	for(AdvanceBean lkb :list)
				 				 						{    						 											
				 											
				 				 						%>
				 				      					 
				 				      						
				 				      						<option value="<%=lkb.getEmpNo()%>"title="<%=lkb.getEmpNo()%>" <%=emp_no.equalsIgnoreCase(Integer.toString(lkb.getEmpNo()))?"Selected":"" %> ><%=lkb.getEmpcode() %>-<%=lkb.getName()%></option>
				 				     					<%																				
																													
																												
				 				 						}																
														    						 										
														%>
												</select>
												</td>
						
					</tr>
 			<tr>
												
												
			<td>MONTH & YEAR&nbsp; <font color="red"><b>*</b></font> </td>
						<td >
				
									<select name='month' id='month'>
									<option value="0" selected >Select</option>
									<option value='Jan'> January </option>
									<option value='Feb'> February </option>
									<option value='Mar'> March </option>
									<option value='Apr'> April </option>
									<option value='May'> May </option>
									<option value='Jun'> June</option>
									<option value='Jul'> July</option>
									<option value='Aug'> August</option>
									<option value='Sep'> September</option>
									<option value='Oct'> October </option>
									<option value='Nov'> November</option>
									<option value='Dec'> December </option>
									
									</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
									<select name="date" id="date">
									<option value="0" selected >Select</option>
									<%
									String[] yyyy=ReportDAO.getSysDate().split("-");
									int yr=Integer.parseInt(yyyy[2])-1;
									for (int yy=yr;yy<=yr+2;++yy)
									{
									%>
									<option value='<%=yy%>'><%=yy%></option>
									<%}%>
									</select>
									</td>
											
	  
	      <td align="left">AMOUNT :&nbsp; <font color="red"><b>*</b></font></td>
	      <td><input type="text" name="amount" id="amount" onkeypress="return inputLimiter(event,'Numbers')"/></td>
	 	 
		</tr>
		<tr>
		
		<th colspan="4"> PAYABLE SALARY</th>
		</tr>
		<tr>
		
	<%if(!emp_no.equalsIgnoreCase(""))
	{ %>
	
		<td> <h3> <%=payable[0]==null?"":payable[0] %> </h3></td>
		<td> <h3> <%=payable[1]==null?"":payable[1] %> </h3></td>
		<td> <h3> <%=payable[2]==null?"":payable[2] %> </h3></td>
		<td> <h3> <%=payable[3]==null?"":payable[3] %> </h3></td>
		
		<input type="hidden" id="0" name="" value="<%=payable[0]==null?"":payable[0]%>">
		<input type="hidden" id="1" name="" value="<%=payable[1]==null?"":payable[1]%>">
		<input type="hidden" id="2" name="" value="<%=payable[2]==null?"":payable[2]%>">
		<input type="hidden" id="3" name="" value="<%=payable[3]==null?"":payable[3]%>">
	
		<%
		}%>
	
	<%-- 	<%
		
		if(!emp_no.equalsIgnoreCase(""))
		{
		
			
		int l=payable.length;
		
		for(int i=0;i<l;i++)
		{
			
			if(payable[i]!=null || !payable[i].equals(""))
			{
			String[] id=payable[i].split(":");
			%>
			<td> <h3> <%=payable[i]==null?"":payable[i] %> </h3></td>
			
			<input type="hidden" id="<%=id[0]%>" name="" value="<%=payable[i]==null?"":payable[i]%>">
			
			<%
			}
		}
		}
		%> --%>
		
		</tr>
	    <tr >
	    <td height="31" colspan="4" align="center">
	    	<input type="submit" value="Add" />&nbsp;&nbsp;
	    	<input type="reset" value="Cancel"  /></td></tr>
	  </table>
	  </form>
	  
	</div>
	<br/>
  			<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
  

<div align="center">


<div>
<table id="customers" style="float: none;">

<tr><th>Select List</th><td><select name="type" id="type" onchange="redirect()">

<option value="SELECT" selected >SELECT</option>
<option value="ALL" <%=status.equalsIgnoreCase("ALL")?"Selected":"" %>>ALL</option>
<option value="PENDING" <%=status.equalsIgnoreCase("PENDING")?"Selected":"" %>>PENDING</option>
<option value="SANCTION" <%=status.equalsIgnoreCase("SANCTION")?"Selected":"" %>>SANCTION</option>
<option value="CANCEL" <%=status.equalsIgnoreCase("CANCEL")?"Selected":"" %>>CANCELED</option></select></td></tr>
</table>

</div>

								<div align="center" class="imptable" style="overflow-y:hidden; width: 100%;">
										
										<table style="position:static; max-width: 100%" >
											<tr style="position:static;"align="center" bgcolor="#1F5FA7">
													
														<td width="90" style="color: white;">EMP CODE</td>
														<td width="200" style="color: white;">EMP NAME</td>
														<td width="90" style="color: white;">REQUEST DATE</td>
														<td width="90" style="color: white;">FOR MONTH</td>
														<td width="90" style="color: white;">REQUESTED AMOUNT</td>
														<!-- <td width="90" style="color: white;"> SALARY PAYABLE</td> -->
														<td width="90" style="color: white;">SANCTION AMOUNT</td>
														<td width="110" style="color: white;">SANCTION BY</td>
														<td width="90" style="color: white;">SANCTION DATE </td>
      													<td width="90" style="color: white;">POSTING DATE</td> 
													
														<td width="90" style="color: white;">STATUS</td>
											</tr>
											</table>
											</div>
   <div align="center" class="imptable" style="overflow-y:auto; height:200px; width: 100%;">
  <%if(advanceList.size()<=5)
	 {%>
   <table style="margin-left: 0px ; position:static;"  >
    <% }else{%>
    <table style="margin-left: 16px ; position:static;"  >
    <%} %>
    <%
    if(advanceList.size()!=0){
    	for(AdvanceBean advanceBean : advanceList){
    		ebean = emph.getEmployeeInformation(Integer.toString(advanceBean.getEmpNo()));
    		ebean1 = emph.getEmployeeInformation(Integer.toString(advanceBean.getSanctionBy()));
    	
    		
    		String mon=advanceBean.getForMonth();
    		String monthyear="";
    		String year=mon.substring(0,4);
    		String month=mon.substring(5,7);
    		if(month.equals("01"))
    			month="Jan";
    		else if(month.equals("02"))
    			month="Feb";
    		else if(month.equals("03"))
    			month="Mar";
    		else if(month.equals("04"))
    			month="Apr";
    		else if(month.equals("05"))
    			month="May";
    		else if(month.equals("06"))
    			month="Jun";
    		else if(month.equals("07"))
    			month="Jul";
    		else if(month.equals("08"))
    			month="Aug";
    		else if(month.equals("09"))
    			month="Sep";
    		else if(month.equals("10"))
    			month="Oct";
    		else if(month.equals("11"))
    			month="Nov";
    		else
         		month="Dec";
    		
    	monthyear=month+"-"+year;
    
    	
    	%>
    	
    
    	<tr align="center" style="position:static;">	
    	
    	<td width="90"><%=ebean.getEMPCODE()%></td>
		<td width="200" ><%=lookuph.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getLNAME() %></td>
    	
    	
    	<td width="90"><%=advanceBean.getRequestDate()%></td>
    	<td width="90"><%=monthyear%></td>
  	    <td width="90"><%=advanceBean.getAdvanceAmtRequested()%></td>
    	<%-- <td width="90"> <%=advanceBean.getPayable() %> </td> --%>
    	
    	<%if(advanceBean.getRequestStatus().equalsIgnoreCase("SANCTION")){ %>
    	<td width="90"><%=advanceBean.getSanctionAmt()%></td>
    	<%}
    	else if(advanceBean.getRequestStatus().equalsIgnoreCase("CANCEL")){ %>
    	<td width="90">Not Sanctioned</td>
    	<%}
    	else
    		{%>
    		<td width="90">YET TO SANCTION</td>
    		<%} %>
    	
    	<%if(advanceBean.getRequestStatus().equalsIgnoreCase("SANCTION")){ %>
    	<td width="110" ><%=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
    	<%}
    	else if(advanceBean.getRequestStatus().equalsIgnoreCase("CANCEL")){ %>
    	<td width="110"><%=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
    	<%}
    	else
    		{%>
    		<td width="110">YET TO SANCTION</td>
    		<%} %>
    		
    		
    	<%if(advanceBean.getRequestStatus().equalsIgnoreCase("SANCTION")){ %>
    	<td width="90"><%=advanceBean.getSanctionDate()%></td>
    	<%}
    	else if(advanceBean.getRequestStatus().equalsIgnoreCase("CANCEL")){ %>
    	<td width="90"><%=advanceBean.getSanctionDate() %></td>
    	<%}
    	else
    		{%>
    		<td width="90">YET TO SANCTION</td>
    		<%} %>	
    	
    	
    	
    	
    	<td width="90" ><%=advanceBean.getPostDateInAcc()%></td>
    	<td width="90"><%=advanceBean.getRequestStatus() %></td>
    </tr>
   
    <%}
    } else  {%>
    <tr ><td colspan="10" height="20">No Records Found</td></tr>
    <%}%>
    
  </table>
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