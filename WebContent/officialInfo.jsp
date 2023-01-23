<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@page import="payroll.Model.EmpExperBean"%>
 <%@page import="java.util.*"%>
 <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/DeleteRow.js"></script>
<script type="text/javascript">

function inputLimiter(e, allow) {
	var AllowableCharacters = '';
	if (allow == 'Numbers') {
		AllowableCharacters = '1234567890';
	}
	var k;
	k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
	if (k != 13 && k != 8 && k != 0) {
		if ((e.ctrlKey == false) && (e.altKey == false)) {
			return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
		} else {
			return true;
		}
	} else {
		return true;
	}
}

function Showhide()
{
	
document.getElementById("empOffInfEdit").hidden=true;
document.getElementById("addOffInfo").hidden=false;

	}
function addOfficilvalidation()
{
	
	
	/* if(	document.getElementById("empNo").value=="" || 
			document.getElementById("empName").value=="" || 
			document.getElementById("TrnsrNo").value=="" || 
			document.getElementById("TraCode").value=="" ||
			document.getElementById("grade").value=="" || 
			document.getElementById("Desgn").value=="" || 
			document.getElementById("saccntNo").value==""  ||
			document.getElementById("Depart").value=="" || 
			document.getElementById("Descrp").value==""  
		)	
	{
		alert("Please Enter All Data");
		return false;
	
	} */
	if( document.getElementById("TraCode").selectedIndex==0)
	{
		alert("Please Select Life cycle Events");	
		document.getElementById("TraCode").focus;
		return false;
	}
	if( document.getElementById("EffDate").value == "")
		{
		alert("Please Select Effective Date");	
		document.getElementById("EffDate").focus;
		return false;
		}
	if( document.getElementById("prjname").selectedIndex==0)
	{
		alert("Please Assign BRANCH");	
		document.getElementById("prjname").focus;
		return false;
	}
	if( document.getElementById("OrderDate").value == "")
	{
	alert("Please Select Order Date");	
	document.getElementById("OrderDate").focus;
	return false;
	}
	if( document.getElementById("Depart").selectedIndex==0)
	{
		alert("Please Assign Department");	
		document.getElementById("Depart").focus;
		return false;
	}
	if( document.getElementById("Desgn").selectedIndex==0)
	{
		alert("Please Assign Designation");	
		document.getElementById("Desgn").focus;
		return false;
	}
	/* var EMPNO = document.getElementById("mngrid").value;
	if (document.getElementById("mngrid").value == "") {
		alert("Please Select Manager Name or Id !");
		document.getElementById("mngrid").focus();
		return false;
	} */
	var atpos=EMPNO.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Manager Name or Id !");
	  return false;
	  }
		
}
function editOfficilvalidation()
	{
		
		/* if(	document.getElementById("eempNo").value=="" || 
				document.getElementById("eempName").value=="" || 
				document.getElementById("eTrnsrNo").value=="" || 
				document.getElementById("eTraCode").value=="" ||
				document.getElementById("egrade").value=="" || 
				document.getElementById("eDesgn").value=="" || 
				document.getElementById("esaccntNo").value==""  ||
				document.getElementById("eDepart").value=="" || 
				document.getElementById("eDescrp").value==""  
			)	
		{
			alert("Please Enter All Data");
			return false;
		
		} */

		if( document.getElementById("eTraCode").selectedIndex==0)
		{
			alert("Please Select Life cycle Events");	
			document.getElementById("eTraCode").focus;
			return false;
		}
		if( document.getElementById("eEffDate").value == "")
		{
		alert("Please Select Effective Date");	
		document.getElementById("eEffDate").focus;
		return false;
		}
		/* if( document.getElementById("eprjname").selectedIndex==0)
		{
			alert("Please Assign Project");	
			document.getElementById("eprjname").focus;
			return false;
		} */
		if( document.getElementById("eOrderDate").value == "")
		{
			alert("Please Select Order Date");	
			document.getElementById("eOrderDate").focus;
			return false;
		}
		if( document.getElementById("eDepart").selectedIndex==0)
		{
			alert("Please Assign Department ");	
			document.getElementById("eDepart").focus;
			return false;
		}
		if( document.getElementById("eDesgn").selectedIndex==0)
		{
			alert("Please Assign Designation");	
			document.getElementById("eDesgn").focus;
			return false;
		}
		/* var EMPNO = document.getElementById("mngrid").value;
		if (document.getElementById("mngrid").value == "") {
			alert("Please Select Manager Name or Id !");
			document.getElementById("mngrid").focus();
			return false;
		} */
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Manager Name/Id !");
		  return false;
		  }

	}
	
	
function chk_effective_date(dt)
{
	var E_date=document.getElementById("max_Eff_date").value;
	
	
    O_Date = dt.replace(/-/g,"/");
    E_date=E_date.replace(/-/g,"/");

    var d1 = new Date(E_date);

    var d2 =new  Date(O_Date);
//	alert("d1: "+d1.getTime()+" d2: "+d2.getTime());
	if (d2.getTime() < d1.getTime() ) 
    {
    	  
    		alert("Effective Date must be Greater or within the month of previous Effective Date  !!");
    		document.getElementById('EffDate').value="";
    		document.getElementById('OrderDate').value="";
    		
    		 return false;
    }
	
}	
	
function chk(dt)
{

	
	var jnDate=document.getElementById("doj").value;
	// var O_Date=document.getElementById("EffDate").value;
	// var E_date=document.getElementById("max_Eff_date").value;
	 
	 
	 
	 
	    jnDate = jnDate.replace(/-/g,"/");
	    O_Date = dt.replace(/-/g,"/");
	    //E_date=sal_date.replace(/-/g,"/");

	    var d1 = new Date(jnDate);

	    var d2 =new  Date(O_Date);
	    
	    //var d3=new  Date(E_date);

	      if (d2.getTime() < d1.getTime() ) 
	    {
	    	  
	    		alert("Date must be Greater of Last Effective date !!");
	    		document.getElementById('EffDate').value="";
	    		document.getElementById('OrderDate').value="";
	    		
	    		 return false;
	    }
	
	    return chk_effective_date(dt);
	    	
		
	
}	
	 function chkappdt()
	{
		try
		{
		var cureffdate1=document.getElementById("EffDate").value;
		var lastEffdate;
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response2="";
 	 	url="EmployeeServlet?action=chkEffectbetDate&cureffdate="+cureffdate1;

	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
			response2 = xmlhttp.responseText;
			cureffdate1 = cureffdate1.replace(/-/g,"/");
			lastEffdate = response2.replace(/-/g,"/");
		    var d1 = new Date(cureffdate1);
		    var d2 =new  Date(lastEffdate);
		    if (d1.getTime() < d2.getTime() ) 
		    {
		    		alert("Effective Date must be Greater or within the month of previous Effective Date..");
		    		document.getElementById('EffDate').value="";
		    		 return false;
		    }
		    
		}
		
		};
 
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
	}
	catch(e)
	{
		
	}
	} 
	
	
</script>
<script>
	jQuery(function() {
          $("#mngrid").autocomplete("list.jsp");
	});
</script>	

<% 
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp"; 
LookupHandler lh = new LookupHandler();
EmpOffHandler ofh = new EmpOffHandler();

EmployeeHandler emph = new EmployeeHandler();
EmployeeBean empbn = emph.getEmployeeInformation((String)session.getAttribute("empno"));

String DOJ=empbn.getDOJ();
%>
</head>
<body  style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:78%; ">


<input type="hidden" id="doj" value="<%=DOJ%>">


<!-- start content -->
<% if(action.equalsIgnoreCase("addemp"))
{
		%>
	<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empQual.jsp">Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="empFamily.jsp">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">6</div>
			<div class="step-dark-left"><a href="officialInfo.jsp">Official Info</a></div>
			<div class="step-dark-right">&nbsp;</div>
		
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		</div>
<%
	}
else if(action.equalsIgnoreCase("showemp"))
	{

	%>
<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual"> Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=Family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">6</div>
			<div class="step-dark-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-dark-right">&nbsp;</div>
		
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">8</div>
			<div class="step-light-left"><a href="Attachment.jsp?action=showemp">Attachment</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp?action=showemp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		</div>
<% 
	} 
%>
<div id="page-heading">
<h1> Employee Official Information</h1>
</div>
<!-- end page-heading -->
<%
String  ono="",od="",ac="";int srno=0, bk=0,de=0,dpt=0;
if(action.equalsIgnoreCase("showemp"))
{
	String srNo = request.getParameter("srno")!=null?request.getParameter("srno"):"0";
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	
	ArrayList<EmpOffBean> empoffList = (ArrayList<EmpOffBean>)session.getAttribute("empoffList");
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);
	%>
	
		<table border="0" width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="300"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="300"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
	

		<table id="customers" width="100%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
		  			<td width="50%" ><input type="text" name="empNo" id="empNo" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr>
			</table>
		<table  id="customers" width="100%" >
		<tr ><th width="5%" >SRNO</th>
		<th width="10%" >ORDER NO</th>
		<th width="10%" >ORDER DATE</th>
		<th width="30%" >BRANCH</th>
		<th width="15%" >ACNO</th>
		<th width="15%" >BANK NAME</th>
		<th width="20%" > DESIG </th>
		<th width="12%" >DEPT</th>
		<!-- <th width="60"> Delete</th> -->
		</tr>
		<%
		String max_eff_date=DOJ;
		int EMPNO=0;
			for(EmpOffBean empoffbean : empoffList)
			{ 
						
				//date = fromformat.parse(empexperbean.getTODT());
				if(empoffbean.getSRNO()>srno)
				{
				
				ono=empoffbean.getORDER_NO();
				od=empoffbean.getORDER_DT();
				ac=empoffbean.getACNO();
				bk=empoffbean.getBANK_NAME();
				de=empoffbean.getDESIG();
				dpt=empoffbean.getDEPT();
				}
				
				%>
				<tr align="center"><td width="5%" ><%=empoffbean.getSRNO() %></td>
				<td width="10%"  ><%=empoffbean.getORDER_NO() %></td>
				<td width="10%" ><%=empoffbean.getORDER_DT()%></td>
				<td width="30%" ><%=empoffbean.getPrj_name()%></td>
				<td width="15%" ><%=empoffbean.getACNO()%></td>
				<td width="15%" ><%=lh.getLKP_Desc("BANK", empoffbean.getBANK_NAME())%></td>
				<td width="20%" ><%=lh.getLKP_Desc("DESIG",empoffbean.getDESIG())%></td>
				<td width="12%" ><%=lh.getLKP_Desc("DEPT",empoffbean.getDEPT()) %></td>
				<%-- <td><input type="button" value="Edit"  onClick="window.location='officialInfo.jsp?action=showemp&hidediv=yes&srno=<%=empoffbean.getSRNO() %>'"/>
					&nbsp;<input type="button" value="Delete" onClick="deleteRow('emp','office','<%=empoffbean.getSRNO() %>','EmployeeServlet?action=officialInfo')">
				</td> --%>
				<input type="hidden" id="empno01" name="empno01" value="<%=empoffbean.getEMPNO() %>">
			</tr>
			

				<% //onClick="window.location='empExper.jsp?action=showemp&srno=<%=empexperbean.getSRNO() >'";
				max_eff_date=empoffbean.getORDER_DT();
				EMPNO= empoffbean.getEMPNO();
			}
	%>
	
	<tr bgcolor="#1F5FA7"><td align="right" colspan="8"> 
	<input type="hidden" id="max_Eff_date" value="<%=max_eff_date%>">
	<input type="hidden" id="offempno" value="<%=EMPNO%>">
	</td></tr>
	</table>
	
	<form action="EmployeeServlet?action=addOffcInfo&offinfo=addmore" method="post" onSubmit="return addOfficilvalidation()">
	<%
	if(hidediv.equalsIgnoreCase("yes"))
	{
	%>
	<div id="addOffInfo"  hidden="true" style="width: 100%;" >
	<%}
	else{
	%>
	<div id="addOffInfo"  style="width: 100%;" >
	<%}%><br/>
	<h2>Add the Official Information of <%=session.getAttribute("empname") %> </h2>
<table  id="customers" width="100%">
		  		
			<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input class="form-control" type="text" name="empName" id="empName" size="40" readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td>
		  		</tr>
		
		<tr class="alt">
		  <td>Order Date&nbsp; <font color="red"><b>*</b></font> </td>
			<td><input class="form-control" type="text" name="OrderDate" id="OrderDate" value="<%=ReportDAO.getSysDate()%>" readonly="readonly" onchange="chk(this.value)">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('OrderDate', 'ddmmmyyyy')" />
			
			</td>
			
		<td >Effective Date&nbsp; <font color="red"><b>*</b></font> </td>
		  <td >
	 	  <input class="form-control" type="text" name="EffDate" id="EffDate" readonly="readonly" onchange="chkappdt();"> 
		<%--   <input class="form-control" type="text" name="EffDate" id="EffDate" value="<%=session.getAttribute("jdate")%>" readonly="readonly" onchange="chk(this.value)"> --%>
		  <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('EffDate', 'ddmmmyyyy')" />
		  
		  </td></tr>
	   <tr class="alt">	<td>Life cycle Events &nbsp; <font color="red"><b>*</b></font></td>
	     <td>	<select  class="form-control" name="TraCode" id="TraCode" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult =new ArrayList<Lookup>();
    						 LookupHandler lkhp = new LookupHandler();
    						 getresult=lkhp.getSubLKP_DESC("SERVIC");
    						for(Lookup lkbean : getresult)
 							{
    							if(lkbean.getLKP_SRNO() == 50 || lkbean.getLKP_SRNO() == 80)
								{ %>
									<!-- <option value=""></option>     -->
									<%								 
									}else {
									%>
				 					<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>    
									 	<%}	
     					 	}
     					 	%>
     			 </select>
		 		
		 
		 </td>
			<td>Order Number </td>
			<td><input class="form-control" type="text" name="OrderNo" id="OrderNo" value="<%=ono%>"></td></tr>
		<tr class="alt">
		  <td>BRANCH Name&nbsp; <font color="red"><b>*</b></font></td>
		  <td colspan="3">
		  
		   <select class="form-control" name="prjname" id="prjname"  >  
      					 <option value="0">Select</option>  
    						<%
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						
    						list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
    							
    							
 							%>
      						 <%--  <option value="<%=lkb.getPrj_code()%>" title="<%=lkb.getSite_name() %>"><%=lkb.getPrj_name()%> --%>
      						  <option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name() %>"><%=lkb.getPrj_name()%>
     					 	<%
     					 	}
 							
     					 	%>
     			 </select>
		  
		  
		  
		  </td>
		  
		</tr>
		<tr class="alt">
		  <td>Manager Name/Id&nbsp;</td>
		  <td colspan="3">
		  	<input class="form-control" type="text" name="mngrid" id="mngrid" size="35" />
		  </td>
		  
		</tr>
		<tr class="alt">
		  <td>Department&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			<select class="form-control" name="Depart" id="Depart" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult2 =new ArrayList<Lookup>();
    						LookupHandler lkhp2 = new LookupHandler();
    						getresult2=lkhp2.getSubLKP_DESC("DEPT");
    						for(Lookup lkbean : getresult2)
 							{
    							
    							
    							if(dpt==lkbean.getLKP_SRNO())
    							{
    								%>
    	      						<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
    	     					 	<%
    							}
    							else
    							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
 							}
     					 	%>
     			 </select>
			
			</td>
		  <td>Designation&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			
			<select class="form-control" name="Desgn" id="Desgn" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult1 =new ArrayList<Lookup>();
    						LookupHandler lkhp1 = new LookupHandler();
    						getresult1=lkhp1.getSubLKP_DESC("DESIG");
    						for(Lookup lkbean : getresult1)
 							{
    							if(de==lkbean.getLKP_SRNO())
    							{
    								%>
    	      						<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
    	     					 	<%
    							}
    							else
    							{
    							
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
 							}
     					 	%>
     			 </select>
			
			
			</td>
		</tr><tr class="alt">
		  <td>Saving A/c No </td>
		  <td><input class="form-control" name="saccntNo" type="text" id="saccntNo" value="<%=ac.trim()%>" onkeypress="return inputLimiter(event,'Numbers')" maxlength="17"></td>
		  <td>Bank Name </td>
			<td>
			<select class="form-control" name="BankName" id="BankName" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult4 =new ArrayList<Lookup>();
    						LookupHandler lkhp4 = new LookupHandler();
    						getresult4=lkhp4.getSubLKP_DESC("BANK");
    						for(Lookup lkbean : getresult4)
 							{
    							if(bk==lkbean.getLKP_SRNO())
    							{
    								%>
    	      						<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
    	     					 	<%
    							}
    							else
    							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
 							}
     					 	%>
     			 </select>
			
			</td>
			
			
		</tr>
		<tr class="alt">
		  <td>Description </td>
		  <td align="left">
		  <textarea class="form-control" name="Descrp" id="Descrp" maxlength="50"></textarea>
		  
		  
		 </td>
		 <td>Office Document No.</td>
		  <td><input class="form-control" name="TrnsrNo" type="text" id="TrnsrNo" value="" maxlength="4"></td>
		
		 
		</tr>

		
		<tr class="alt"><td colspan="4" align="center"><input type="submit" class="myButton"  value="Save" /> &nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel" /></td></tr>
				
    </table>
    </div>
<div class="clear">&nbsp;</div>
</form>

<%

	for(EmpOffBean empoffbean : empoffList)
	{
		if(srNo.equalsIgnoreCase(String.valueOf(empoffbean.getSRNO())))
		{
						
	%>
	<form name="editform" id="editform" action="EmployeeServlet?action=editoffInfo" method="post" onSubmit="return editOfficilvalidation()">
	<div id="empOffInfEdit"  >
	
	<h2>Edit The Information of <%=session.getAttribute("empname") %> </h2>
			<table width="677" id="customers">
		  		<tr class="alt"><td width="143" >Employee Code</td>
	  			<td width="172" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="eempNo" id="eempNo"  readonly="readonly" value="<%=session.getAttribute("empno") %>" ></td>
				<td width="145" >Employee Name</td>
			<td width="197" ><input class="form-control" type="text" name="eempName" id="eempName" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td></tr>
		<tr class="alt">
		<td>Order Date&nbsp; <font color="red"><b>*</b></font> </td>
			<td><input class="form-control" type="text" name="eOrderDate" id="eOrderDate" value="<%=empoffbean.getORDER_DT() %>" readonly="readonly" onchange="chk(this.value)">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eOrderDate', 'ddmmmyyyy')" />
			</td>
		  <td >Effective Date&nbsp; <font color="red"><b>*</b></font> </td>
		  <td ><input class="form-control" type="text" name="eEffDate" id="eEffDate" readonly="readonly" onchange="chk(this.value);chkappdt();">
		  <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('eEffDate', 'ddmmmyyyy')" />
		  </td></tr>
	   <tr class="alt">	<td>Life cycle Events &nbsp; <font color="red"><b>*</b></font></td>
	     <td>
		 
		 <select class="form-control" name="eTraCode" id="eTraCode" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result2 = new ArrayList<Lookup>();
    						LookupHandler lkhp3 = new LookupHandler();
    						 result2=lkhp3.getSubLKP_DESC("SERVIC");
    					
 							
    						for(Lookup lkbean : result2)
 							{
 								
     						if(lkbean.getLKP_SRNO()== empoffbean.getTRNCD())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
		 
		  </td>
			<td>Order Number </td>
			<td><input class="form-control" name="eOrderNo" type="text" id="eOrderNo" value="<%=empoffbean.getORDER_NO() %>" maxlength="10"></td></tr>
		<tr class="alt">
		  <td>BRANCH Name&nbsp; <font color="red"><b>*</b></font></td>
		  <td colspan="3">
			 <select class="form-control" name="eprjname" id="eprjname" >  
					<%-- changes done <%=empoffbean.getPrj_name()%> --%>
      				 <option value="<%=empoffbean.getPrj_code()%>"><%=empoffbean.getPrj_name() %></option>  

      					 <!-- <option value="0">Select</option> -->  
    						<%
    						// ArrayList<Lookup> result22=new ArrayList<Lookup>();
    						// EmpOffHandler ekhp22= new EmpOffHandler();
    						// result22=ekhp22.getGradeBranchList("prj");
    						
    						ArrayList<EmpOffBean> list2 = new ArrayList<EmpOffBean>();
    						list2=ofh.getprojectCode();
    						for(EmpOffBean lkb : list2)
    						{
    							%>
    						<%-- changes done <%=empoffbean.getPrj_name()%> --%>
    							<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name()%>"><%=lkb.getPrj_name() %></option>
    							<%}%>
 							
    						<%-- for(Lookup lkbean : result22)
 							{
 								
     						if(lkbean.getLKP_SRNO()== empoffbean.getBRANCH())
     						 {
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%> --%>
     			 </select>
		  
		        <%--  <%
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						
    						list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
 							%>
      						<option value="<%=lkb.getPrj_srno()%>"><%=lkb.getPrj_code()%></option>  
     					 	<%
     					 	}
     					 	%>
		   --%>
		  
		  </td>

		  
		</tr>
		<tr class="alt">
		  <td>Manager Name/Id&nbsp;</td>
		  <td colspan="3">
		  	<input class="form-control" type="text" name="mngrid" id="mngrid" size="35" placeholder="enter 0 if not applicable" />
		  </td>
		  
		</tr>
		<tr class="alt">
		   <td>Department&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			
			<select class="form-control" name="eDepart" id="eDepart" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result11=new ArrayList<Lookup>();
    						LookupHandler lkhp11 = new LookupHandler();
    						 result11=lkhp11.getSubLKP_DESC("DEPT");
    					
 							
    						for(Lookup lkbean : result11)
 							{
 								
     						if(lkbean.getLKP_SRNO()== empoffbean.getDEPT())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
			
			</td>
		  <td>Designation&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			
			
			<select class="form-control" name="eDesgn" id="eDesgn" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result13 = new ArrayList<Lookup>();
    						LookupHandler lkhp23 = new LookupHandler();
    						 result13=lkhp23.getSubLKP_DESC("DESIG");
    					
 							
    						for(Lookup lkbean : result13)
 							{
 								
     						if(lkbean.getLKP_SRNO()== empoffbean.getDESIG())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
			
			
			
			
			
			</td>
		</tr><tr class="alt">
		  <td>Saving A/c No&nbsp; <font color="red"><b>*</b></font> </td>
		  <td><input class="form-control" name="esaccntNo" type="text" id="esaccntNo" onkeypress="return inputLimiter(event,'Numbers')" value="<%=empoffbean.getACNO() %>" maxlength="17"></td>
		  <td>Bank Name&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			
			<select  class="form-control"   name="eBankName" id="eBankName" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result12=new ArrayList<Lookup>();
    						LookupHandler lkhp12 = new LookupHandler();
    						 result12=lkhp12.getSubLKP_DESC("BANK");
    					
 							
    						for(Lookup lkbean : result12)
 							{
 								
     						if(lkbean.getLKP_SRNO()== empoffbean.getBANK_NAME())
     						{
     							
     						%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     							<%}
     						else {
     								%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 		<%}
     					 		}%>
     			 </select>
			
			</td>
		</tr>
		<tr class="alt">
		  <td>Description </td>
		  <td align="left">
		  <textarea class="form-control" name="eDescrp" id="eDescrp" maxlength="50"><%=empoffbean.getMDESC() %></textarea>
		  </td><td>Office Document No.</td>
		  <td><input class="form-control" type="text" name="eTrnsrNo" id="eTrnsrNo" value="<%=empoffbean.getSRNO() %>" readonly="readonly"></td>
		
		 
		</tr>

		
		<tr class="alt"><td colspan="4" align="center"><input type="submit"  class="myButton" value="Update" /> &nbsp;&nbsp;&nbsp;<input type="reset" value="Cancel"  class="myButton" onClick="Showhide()"/></td></tr>
				
    </table>
    </div>
    <div class="clear">&nbsp;</div>
    </form>
    
<%
	}
	
	}
	if(srNo.equalsIgnoreCase("addinfo"))
	{
	%>
	

	<%
}
}
else if(action.equalsIgnoreCase("addemp"))
{
	/* EmployeeHandler emph = new EmployeeHandler();
	EmployeeBean empbn = emph.getEmployeeInformation((String)session.getAttribute("empno")); */
%>
<form action="EmployeeServlet?action=addOffcInfo" method="post" onSubmit="return addOfficilvalidation()">
		<table border="0" width="auto" cellpadding="0" cellspacing="0" >
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="300"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="300"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>	
<form action="EmployeeServlet?action=addOffcInfo" method="post" onSubmit="return addOfficilvalidation()">
<div >
<table width="677" id="customers">
		  	<tr class="alt"><td width="143" >Employee Code</td>
	  			<td width="172" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"": session.getAttribute("empno"))%>"></td>
				<td width="145" >Employee Name</td>
			<td width="197" ><input class="form-control" type="text" name="empName" id="empName" readonly="readonly" value="<%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %>"></td></tr> 
		<tr class="alt">
		 <td>Order Date&nbsp; <font color="red"><b>*</b></font> </td>
			<td><input class="form-control" type="text" name="OrderDate" id="OrderDate" value="<%=empbn.getDOJ() %>" readonly="readonly">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('OrderDate', 'ddmmmyyyy')" />
			
			</td>
		<td >Effective Date&nbsp; <font color="red"><b>*</b></font> </td>
		  <td ><input class="form-control" type="text" name="EffDate" id="EffDate" value="<%=empbn.getDOJ() %>" readonly="readonly">
		  
		  <img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('EffDate', 'ddmmmyyyy')" />
		  
		  </td><!-- <td colspan="2"></td> --></tr>
	   <tr class="alt">	<td>Life cycle Events &nbsp; <font color="red"><b>*</b></font> </td>
	     <td>
		 <select class="form-control" name="TraCode" id="TraCode" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> getresult =new ArrayList<Lookup>();
    						LookupHandler lkhp = new LookupHandler();
    						getresult=lkhp.getSubLKP_DESC("SERVIC");
    						for(Lookup lkbean : getresult)
 							{
    							if(lkbean.getLKP_SRNO() == 50 ||lkbean.getLKP_SRNO() == 80)
								{ %>
								<!-- 	<option value=""></option>    --> 
									<%								 
									}else {
									%>
				 					<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>    
									 	<%}	
     					 	}
     					 	%>
     			 </select>
			 </td>
			<td>Order Number </td>
			<td><input class="form-control" name="OrderNo" type="text" id="OrderNo" value="" maxlength="10"></td></tr>
		<tr class="alt">
		  <td>BRANCH Name&nbsp; <font color="red"><b>*</b></font></td>
		  <td colspan="3" >
		  <select class="form-control" name="prjname" id="prjname" >  
      					 <option value="0">Select</option>
    						<%
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						
    						 list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
    							System.out.println("code="+lkb.getPrj_code());
 							%>
      						<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name() %>"><%=lkb.getPrj_name()%></option>  
     					 	<%
     					 	}
     					 	%>
     			 </select>   
		  </td>
		  <%-- <td>Order Date </td>
			<td><input type="text" name="OrderDate" id="OrderDate" value="<%=empbn.getDOJ() %>" readonly="readonly">
			<img
				src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('OrderDate', 'ddmmmyyyy')" />
			
			</td> --%>
		</tr>
		<tr class="alt">
		<tr class="alt">
		  <td>Manager Name/Id&nbsp;</td>
		  <td colspan="3">
		  	<input class="form-control" type="text" name="mngrid" id="mngrid" size="35" placeholder="enter 0 if not applicable"/>
		  </td>
		  
		</tr>
		  <td>Department&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			<select class="form-control" name="Depart" id="Depart" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result3=new ArrayList<Lookup>();
    						LookupHandler lkhp3 = new LookupHandler();
    						 result3=lkhp3.getSubLKP_DESC("DEPT");
    						for(Lookup lkbean : result3)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			 </select>
			
			
			</td>
		  <td>Designation&nbsp; <font color="red"><b>*</b></font></td>
			<td>
			<select class="form-control" name="Desgn" id="Desgn" >  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result2=new ArrayList<Lookup>();
    						LookupHandler lkhp1 = new LookupHandler();
    						 result2=lkhp1.getSubLKP_DESC("DESIG");
    						for(Lookup lkbean : result2)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			 </select>
			
			</td>
		</tr><tr class="alt">
		  <td>Saving A/c No&nbsp; <font color="red"><b>*</b></font> </td>
		  <td><input class="form-control" name="saccntNo" type="text" id="saccntNo" onkeypress="return inputLimiter(event,'Numbers')" value="" maxlength="17"></td>
		  <td>Bank Name&nbsp; <font color="red"><b>*</b></font></td>
			<td>
      				 <select class="form-control" name="BankName" id="BankName" > 
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result5=new ArrayList<Lookup>();
    						LookupHandler lkhp5 = new LookupHandler();
    						 result5=lkhp5.getSubLKP_DESC("BANK");
    						for(Lookup lkbean : result5)
 							{
 							%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 	<%
     					 	}
     					 	%>
     			 </select>
			
			
			</td>
		</tr>
		<tr class="alt">
		  <td>Description </td>
		  <td align="left" colspan="3"><textarea name="Descrp" id="Descrp"  maxlength="50"></textarea>
		  </td>
		 
		</tr>

		
		<tr class="alt"><td colspan="4" align="center"><input type="submit"  class="myButton"  value="Save" /> &nbsp;&nbsp;&nbsp;<input type="reset" value="Cancel"  class="myButton"  onClick="Showhide()"/></td></tr>
				
    </table>
    </div>
<div class="clear">&nbsp;</div>
</form>	

<%
}
%>

</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>

</form>
<center>
	<br/>
		<b>If You Want To Add or Update Employee Relieving Information Please Click Here
		<input type="button"  class="myButton"  value="Relieve Info" onClick="openRelieveBox('<%=(session.getAttribute("empno")==null?"": session.getAttribute("empno"))%>')"/>
	 </b>
</center>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    </div>
<script type="text/javascript">
function openRelieveBox(EMPNO)
{
	//window.location.href='RelievingNew.jsp?EMPNO='+EMPNO;
///	window.showModalDialog('RelievingNew.jsp?EMPNO='+EMPNO,null,"dialogWidth:830px; dialogHeight:270px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");

	$("#myModal").empty();
	document.getElementById("myModal").style.display = 'Block';
	$("#myModal").load("RelievingNew.jsp?EMPNO="+EMPNO);

$("#myModal").fadeTo('slow', 0.9);
$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	

	
	}
</script>
</body>     
</html>