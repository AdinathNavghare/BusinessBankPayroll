<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="java.text.Format"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="payroll.DAO.LookupHandler"%>
	<%@page import="payroll.Model.EmployeeBean"%>
	<%@page import="java.util.*"%>
	<%@page import="payroll.Model.Lookup"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<script type="text/javascript" src="js/empValidation.js"></script>
<script type="text/javascript" src="js/date.js"></script>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<%
String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp";
System.out.println("action is "+action);
EmployeeBean empbean=new EmployeeBean();
String EmpName="";
/* Date date = new Date();
SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
String dt = format.format(date);
String[] parts = dt.split("-");
String curYear = parts[3]; */ 

SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
fromformat.setLenient(false);
if(action.equalsIgnoreCase("showemp"))
{ 
	
	 empbean=(EmployeeBean)request.getAttribute("empbean"); 
	 EmpName=empbean.getFNAME()+" "+empbean.getMNAME()+" "+empbean.getLNAME();	
}
%>
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

	
	function ImageUpload()
	{
		window.showModalDialog("uploadImage.jsp?",null,"dialogWidth:600px; dialogHeight:170px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		window.location.href="EmployeeServlet?action=employee";
	}
	function SignImageUpload()
	{
	window.showModalDialog("uploadSign.jsp?",null,"dialogWidth:600px; dialogHeight:160px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	window.location.href="EmployeeServlet?action=employee";
	}
	function editName()
	{
		var resp=confirm("Do you want to Change Name?");
		if(resp==true){
		 var no=document.getElementById("empno").value;
		 var Ename = document.getElementById("Empname").value;
		 var salu = document.getElementById("aSALUTE").value;
		window.showModalDialog("editName.jsp?fname="+Ename+"&no="+no+"&salu="+salu,null,"dialogWidth:850px; dialogHeight:160px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		window.location.href="EmployeeServlet?action=employee";
		}
	}
	

	function checkFlag()
	{
		var retirement_ext_date = document.getElementById("retireExtPr").value;
		var retirement_date = document.getElementById("anotation").value;
		var DT = retirement_date.split('-');
		var year = DT[2];
		var month=0;

		if(DT[1]=="Jan"){month=1;}
		if(DT[1]=="Feb"){month=2;}
		if(DT[1]=="Mar"){month=3;}
		if(DT[1]=="Apr"){month=4;}
		if(DT[1]=="May"){month=5;}
		if(DT[1]=="Jun"){month=6;}
		if(DT[1]=="Jul"){month=7;}
		if(DT[1]=="Aug"){month=8;}
		if(DT[1]=="Sep"){month=9;}
		if(DT[1]=="Oct"){month=10;}
		if(DT[1]=="Nov"){month=11;}
		if(DT[1]=="Dec"){month=12;}
		
	     var annotationDT= DT[2]+'-'+month+'-'+DT[0];
	        try
	    	{	
	    	var xmlhttp=new XMLHttpRequest();
	     	var url="";
	     	var response="";
	      	url="EmployeeServlet?action=retExtDate&annotationDT="+annotationDT;
	    	xmlhttp.onreadystatechange=function()
	    	{
	    		if(xmlhttp.readyState==4)
	    		{
	    		response = xmlhttp.responseText;
	    		 if(response==0 )
	    			{
	    			 document.getElementById('retireExtPr').style.display = 'none';
	    			}
	    		 else{
	    			 document.getElementById('retireExtPr').style.display = 'table-row';
	    		 }
	    		}
	    		};
	    	xmlhttp.open("POST", url, true);
	    	xmlhttp.send();
	    	}
	    	catch(e)
	    	{
	    		
	    	}
	        
	    	try
		    	{
		    	var xmlhttp1=new XMLHttpRequest();
		     	var url1="";
		     	var response1="";
		     	url1="EmployeeServlet?action=checkRetExtDate";
		    	xmlhttp1.onreadystatechange=function()
		    	{
		    		if(xmlhttp1.readyState==4)
		    		{
		    		response1 = xmlhttp1.responseText;
		    		 if(response1==0 )
		    			{
		    			 document.getElementById('edob').style.display = 'table-row';
		    			 document.getElementById('edob1').style.display = 'none';
		    			}
		    		 else{
		    			 document.getElementById('edob1').style.display = 'table-row';
		    			 document.getElementById('edob').style.display = 'none';
		    		 }
		    		}
		    		};
		    	xmlhttp1.open("POST", url1, true);
		    	xmlhttp1.send();
		    	}
		    	catch(e)
		    	{
		    		
		    	}
		    var confirmDate = document.getElementById("confirmdate").value;
	   // 	var probationStartDate = document.getElementById("prob_start_dates");
	   // 	var probationEndDate = document.getElementById("prob_end_dates");
	    	if(confirmDate === null || confirmDate === ""){
	    		document.getElementById("probstart").disabled = false;
	    		document.getElementById("probend").disabled = false;
	    		/* document.getElementById("prob_start_date").disabled = false;
	    		document.getElementById("prob_end_date").disabled = false;
	    		document.getElementById("prob_start_dates").disabled = false;
	    		document.getElementById("prob_end_dates").disabled = false; */
	    		
	    		/* document.getElementById("probstartdates").disabled = false;
	    		document.getElementById("probenddates").disabled = false; */
	    		
	    	}else{
	    		document.getElementById("probstart").disabled = true;
	    		document.getElementById("probend").disabled = true;
	    		/* document.getElementById("prob_start_date").disabled = true;
	    		document.getElementById("prob_end_date").disabled = true; */
	    		
	    		/* document.getElementById("probstartdates").disabled = true;
	    		document.getElementById("probenddates").disabled = true; */
	    	}
		    	
			var flag=document.getElementById("flag").value;
			if(flag!="")
			{
			
			if(flag==1)
				{
				alert("Record updated Successfully !");
				}
			else if(flag==2)
				{
				alert("Error in updating record");
				}
	}
}			
	function checkempcd()
	{
		
		var empcd = document.getElementById("empcd").value;
		//alert(empcd);
	
	try
	{
	
	var xmlhttp=new XMLHttpRequest();
 	var url="";
 	var response2="";
 	

 	url="EmployeeServlet?action=checkempcd&empcd="+empcd;

	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
		
		response2 = xmlhttp.responseText;
		 if(response2>0 )
			{
			 alert("You have already  THIS EMPCD try again...");
			 
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
		 
	
	
function fn()
{
var dob=document.getElementById("dob").value;
var arr = dob.split("-");
var counter=0;
var day=arr[0];
var month=0;
document.getElementById("dob").style.display = "row";
if(arr[1]=="Jan"){month=0;}
if(arr[1]=="Feb"){month=1;}
if(arr[1]=="Mar"){month=2;}
if(arr[1]=="Apr"){month=3;}
if(arr[1]=="May"){month=4;}
if(arr[1]=="Jun"){month=5;}
if(arr[1]=="Jul"){month=6;}
if(arr[1]=="Aug"){month=7;}
if(arr[1]=="Sep"){month=8;}
if(arr[1]=="Oct"){month=9;}
if(arr[1]=="Nov"){month=10;}
if(arr[1]=="Dec"){month=11;}

var year=arr[2];

var d = new Date(year,month,day-1);
d.setFullYear( d.getFullYear() + 58 );

document.getElementById("anotation").value=GetDate(convertDate(d));
document.getElementById("extperdate").value=GetDate(convertDate(d));
}



function convertDate(inputFormat) {
	  function pad(s) { return (s < 10) ? '0' + s : s; }
	  var d = new Date(inputFormat);
	  return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('-');
	} 
function GetDate(str)
{
         // alert(str);
		   var arr = str.split('-');
           var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
           var c=arr[1];
           --c;
                    
           var formatddate = arr[0]  + '-' +months[c] + '-' + arr[2];
              return formatddate;
        }


</script>
</head>
<body style="overflow:hidden;" onload="checkFlag()"> 
<div >
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
</div> 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:80%; "  >
<!-- start content -->
<% if(action.equalsIgnoreCase("addemp"))
{
		//In this If for New Employee page ......show menus for nevigation 
		%>
	<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">1</div>
			<div class="step-dark-left"><a href="employee.jsp" >Employee Detail</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empQual.jsp">Qualification</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="empFamily.jsp">Family</a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="empExper.jsp">Experience</a></div>
			<div class="step-light-right">&nbsp;</div>
		
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
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
//In else for Existing Employee Menus.........
	%>
<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">1</div>
			<div class="step-dark-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
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

<!-- end page-heading -->
<%	if(action.equalsIgnoreCase("addemp"))
{// form tag for adding new emplyee information
	session.removeAttribute("empno");
	session.removeAttribute("empname");
	session.removeAttribute("empQualList");
	session.removeAttribute("empaddrList");
	session.removeAttribute("empfamilyList");
	session.removeAttribute("empexperList");
	session.removeAttribute("empawardList");
	session.removeAttribute("empoffList");
		

%>
<form id="employeeform" name="employeeform" action="EmployeeServlet?action=addemployee" method="post" onsubmit="return addemployeValidation()">
<%
}else
{
	// form tag for updating existing emplyee information
%>
<form id="employeeform" name="employeeform" action="EmployeeServlet?action=editemployee" method="post" onSubmit="return updatevalidation()">
<%	
}
%>
<div id="page-heading"><h1>Employee Information</h1></div>
<table border="0" width="100%" cellpadding="0" cellspacing="0"
	id="content-table">
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
		<td bgcolor="#f5f6fa"><!--  start content-table-inner ...................................................................... START -->
		
		<div id="content-table-inner">  <!--  start table-content  -->
		<div id="table-content"> 
		
	<font size="4"></font>
	
		<table width="1171" id="customers"> 
		  
			<%if(action.equalsIgnoreCase("showemp"))
			{
				
			%>
			<tr class="alt" >
				<td width="140" >Employee Code</td>
				<td width="170"><%=empbean.getEMPCODE() %><input type="hidden" size="20" name="empno"
					id="empno" value="<%=empbean.getEMPNO() %>" readonly="readonly"/></td>
				<td width="144">Employee Name &nbsp;</td>
				<td colspan="4" width="800"><select name="aSALUTE" id="aSALUTE" >  
      					 <option value="0">Select</option>  
    						<%
    					
  							  ArrayList<Lookup> resultsalute=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						resultsalute=lkhp.getSubLKP_DESC("SALUTE");
 							for(Lookup lkbean : resultsalute)
 							{
     						if(lkbean.getLKP_SRNO()== empbean.getSALUTE())
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
				  <input type="text" size="45" name="Empname"
					id="Empname" value="<%=EmpName %>" ondblclick="editName()" readonly="readonly" />
					<input type="button"   class="myButton"  value="Edit Name" onClick="editName()">
				  Status &nbsp; &nbsp;
				  <select name="statusemp" id="statusemp" >
            <option value="0" >Select..</option>
            <%
				if((empbean.getSTATUS()).equalsIgnoreCase("A"))
				{
				%>
            <option value="A" selected="selected" >Active</option>
            <option value="N">Non Active</option>
            <%
				}
				else if((empbean.getSTATUS()).equalsIgnoreCase("N"))
				{
					%>
            <option value="A" >Active</option>
            <option value="N" selected="selected">Non Active</option>
            <%
            }
				else{
					
					%>
					 <option value="A" >Active</option>
            <option value="N">Non Active</option>
					<%
				}
            %>
			</select>
				  
				  </td>
			</tr>
			<%	
			}
			
			else
			{
				%>
				<tr align="left">
				
				<td width="110">Employee Name &nbsp; <font color="red"><b>*</b></font>
				<td  width="80"><select name="aSALUTE" id="aSALUTE" >  
      					 <option value="0">Select</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("SALUTE");
 							for(Lookup lkbean : result)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     					 }/// end of for loop..........
     					 
     					 %>
     			 </select></td>
				<td width="800" colspan="6" >First&nbsp; <font color="red"><b>*</b></font>
				  <input  name="FEmpname" type="text"
					id="FEmpname" value="" maxlength="20" onBlur="TextCheck(this.id)"/>
				Middle&nbsp; <font color="red"><b>*</b></font><input  name="MEmpname" type="text"
					id="MEmpname" value="" maxlength="20" onBlur="TextCheck(this.id)"/>
				Last&nbsp; <font color="red"><b>*</b></font><input  name="LEmpname" type="text"
					id="LEmpname" value="" maxlength="20" onBlur="TextCheck(this.id)"/>
				 
				 EMPCODE&nbsp; <font color="red"><b>*</b></font>
				 <input  name="empcd" type="text"
					id="empcd" value="" onBlur="checkempcd()" />
				 </td>
                 </tr>

<% }%>
		</table>
		<%-- <%if(action.equalsIgnoreCase("showemp"))
		{
			%>
		<td width="200" valign="top">
		<table><tr><td valign="top"></td>
		</tr></table></td>
		<%} %> --%>
		
		
		    <table width="1171" cellpadding="0" cellspacing="0" id="customers">
			<tr>
				<th colspan="2" >Personal Detail</th>  
				<th colspan="2" >Physical Detail</th>
				<th colspan="2" width="280">Religion Detail</th>
				<th ></th>
		 </tr>
	
         
          <tr class="alt">
            <td width="156">Gender &nbsp; <font color="red"><b>*</b></font></td>
            <td>
            <%if(action.equalsIgnoreCase("showemp"))
            {
            	%>
            	
					<select style="width: 138px" name="gender" id="gender">
					<% 
					if(empbean.getGENDER().equalsIgnoreCase("M"))
					{	%>
					<option value="0">Select.....</option>
					<option value="M" selected="selected"> Male </option>
					<option value="F"> Female </option>
					</select>
            	<%}
            	else
            	{
            		%>
            		<option value="0">Select.....</option>
					<option value="M" > Male </option>
					<option value="F" selected="selected"> Female </option>
            		</select>
            		<% 
            	}
            }
            else{
            	%>
            	
					<select style="width: 138px"  name="gender" id="gender">
					<option value="0">Select.....</option>
					<option value="M"> Male </option>
					<option value="F"> Female </option>
					</select>
				
            	<%
	            }
            	%>            </td>
            <td>Weight</td>
            <td width="170"><input name="weight2" type="text" id="weight2"
					value="<%=empbean.getWEIGHT() %>" size="20" maxlength="3" /></td>
            <td width="120">Cast</td>
            <td width="164">
            <%if(action.equalsIgnoreCase("showemp"))
            {
            	%>
           
					
					<select style="width: 150px" name="cast2" id="cast2" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp1= new LookupHandler();
    						result=lkhp1.getSubLKP_DESC("CASTE");
 							for(Lookup lkbean : result){
     						if(lkbean.getLKP_SRNO()== empbean.getCASTCD())
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
     						}}%>
     			 </select>
					
			<%}
            
            else
            {
            	%>
            	<select style="width: 150px" name="cast2" id="cast2" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("CASTE");
 							for(Lookup lkbean : result)
 							{
     						
     						%>
     						
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
            	<%
            	
            }%>					</td>
           <%--  <td colspan="2" rowspan="4"><img width='200' height='130' id="profile" style="z-index: 7;" src="displayphoto?type=PHOTO&empno=<%=empbean.getEMPNO() %>" ></td> --%>
           <td colspan="2" rowspan="4"><img width='200' height='130' id="profile" style="z-index: 7;" src="DisplayPhotoServlet?type=PHOTO&empno=<%=empbean.getEMPNO() %>" ></td>
            </tr>
          <tr>
            <td>Place Of Birth</td>
            <td width="178"><input type="text" name="placedob"
					id="placedob"  value="<%=empbean.getBPLACE() %>" /></td>
            
          
          
          
          
          <td>Blood Group</td>
            <td>
             <%if(action.equalsIgnoreCase("showemp"))
            {
            	%>
            		
					 <select name="bldgrp" id="bldgrp" style="width: 138px;">  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("BLOOD_GRP");
 							for(Lookup lkbean : result){
 								
 								if(lkbean.getLKP_SRNO()== Integer.parseInt(empbean.getBGRP()))
 								{
 									%>
 									<option value="<%=lkbean.getLKP_SRNO()%>"==null?"":value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option> 
 									<%
 								}
 								
 								else{
     						%>
      							<option value="<%=lkbean.getLKP_SRNO()%>"==null?"":"<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>   
     					 <%
     					 }}%>
     			 </select>
					 
					 
			<%}
            
            else
            {
            	%>
            	<select name="bldgrp" id="bldgrp" style="width: 138px;">  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("BLOOD_GRP");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"==null?"":"<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
            	<%
            	
            }%>           </td>
            
            
            
            
			
            <td>Category</td>
            <td>
             <%if(action.equalsIgnoreCase("showemp"))
            {
            	%>
					 <select name="category2" id="category2" style="width: 150px;">  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("CATE");
 							for(Lookup lkbean : result){
 								
 								if(lkbean.getLKP_SRNO()== empbean.getCATEGORYCD())
 								{
 									%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option> 
 									<%
 									
 								}
 								else{
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}
     					 }%>
     			 </select>
					 
					 
			<%}
            
            else
            {
            	%>
            	<select name="category2" id="category2" style="width: 150px;">  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("CATE");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
            	<%
            	
            }%>           </td>
            </tr>
          <tr class="alt" >
          	<td>Date Of Birth<font color="red"><b style="margin-left: 5px;">*</b></font></td><!-- </td> --> 
            	<td id="edob" style="display: row;"><input  name="dob" id="dob" type="text" value="<%=empbean.getDOB()==null?"":empbean.getDOB()%>" onchange="fn()" readonly="readonly">
             	&nbsp;<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('dob', 'ddmmmyyyy')" />			</td>
				<!-- <td id="edob1"></td> -->
				
            
            <td>Height (in cm)</td>
            <td><input name="height2" type="text" id="height2"
					value="<%=empbean.getHEIGHT() %>" maxlength="3" />           </td>
            <td height="24">Religion</td>
            <td  align="left">
            <% if(action.equalsIgnoreCase("showemp"))
            {
            	%>
          <select style="width: 150px" name="relegion" id="relegion" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RELIG");
 							for(Lookup lkbean : result){
 								
 							if(lkbean.getLKP_SRNO()== empbean.getRELEGENCD())
 							{
 								
 								%>
 								<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
 								<%
 							}
 							else{
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}}%>
     			 </select>
			<%}
            
            else
            {
            	%>
            	<select style="width: 150px" name="relegion" id="relegion" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RELIG");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
            	<%
            	
            }%> 		</td>
          </tr>
          <tr>
            
<td>Marital Status</td>
            <%if(action.equalsIgnoreCase("showemp"))
            {
            %>
            <td>
					 <select style="width: 138px" name="marrystatus" id="marrystatus" onChange="changeValid()">
            	<option value="0">Select...</option>
					 <% if(empbean.getMARRIED().equalsIgnoreCase("U"))
					 {
						 %>
						<option value="U" selected="selected">UnMarried</option>
            	<option value="M">Married</option>
            	<option value="D">Divorced</option>
            	<option value="W">Widow</option>
						 <% 
					 }
						 else if(empbean.getMARRIED().equalsIgnoreCase("M"))
						 {
							%>
							<option value="U">UnMarried</option>
            				<option value="M" selected="selected">Married</option>
            				<option value="D">Divorced</option>
            				<option value="W">Widow</option>
							<% 
							}
						 else if(empbean.getMARRIED().equalsIgnoreCase("D"))
						 {
						%>
						<option value="U">UnMarried</option>
            			<option value="M">Married</option>
            			<option value="D" selected="selected">Divorced</option>
            			<option value="W">Widow</option>
						<%	 
						 }
						 else if(empbean.getMARRIED().equalsIgnoreCase("W"))
						 {
						%>
						 <option value="U">UnMarried</option>
            	<option value="M">Married</option>
            	<option value="D">Divorced</option>
            	<option value="W" selected="selected">Widow</option>
							 <%
						 }
						 %>
				</select></td>
					 
			<%
			}
            else
            {
            	%>
            	<td><select style="width: 138px" name="marrystatus" id="marrystatus" onChange="changeValid()">
            	<option value="U">Select...</option>
            	<option value="U">UnMarried</option>
            	<option value="M">Married</option>
            	<option value="D">Divorced</option>
            	<option value="W">Widow</option>
            	</select></td>
            	<%
            	
            }
            %>
            <td width="161">Physically Handicap</td>
           
           <% if(action.equalsIgnoreCase("showemp"))
        	   
        	   {
        	   
        	   %>
        	
        	   <td ><select style="width: 138px" name="phandicap2" id="phandicap2">
            	
            	<% if(empbean.getDISABILYN().equalsIgnoreCase("Y"))
            		{
            			%>
            			<option value="0">Select...</option>
            	<option value="Y" selected="selected">Yes</option>
            	<option value="N">No</option>
            			<%
            		}else
            		{
            			%>
            			<option  value="0">Select...</option>
            	<option value="Y">Yes</option>
            	<option value="N" selected="selected">No</option>
            			<%
            		}
            	%>
            	
            	
            	</select>
				
				
				
				</td>
        	   <%
        	   }
           else
        	   {
        	    %>
        	    <td><select style="width: 138px" name="phandicap2" id="phandicap2">
            	<option  value="0">Select...</option>
            	<option value="Y">Yes</option>
            	<option value="N">No</option>
            	
            	</select></td>
        	    <%
        	   }%>
           
           
          </tr>
        	 <%if(action.equalsIgnoreCase("showemp"))
         	{
        		 %>
        		 <tr class="alt" id="Marrydate" style="display:none;">
        		 <%
        	 }%>
          
            <td class="alt" >Marriage Date</td>
            <td>
					<input  name="marrydate" id="marrydate" type="text" value="<%=empbean.getMARRIEDDT() %>"
				readonly="readonly">&nbsp;<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('marrydate', 'ddmmmyyyy')" />			</td>	 
					 
					 <td colspan="6">&nbsp;</td>
          </tr>
          <tr>
		   <td>Residancy</td>
           
          <td  align="left">
           <%if(action.equalsIgnoreCase("showemp"))
				{
        	  
					%>
			<select style="width: 138px" name="residancy" id="residancy">  
      					 <option value="0">Select</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RESICD");
 							for(Lookup lkbean : result){
     						System.out.println("srno is "+lkbean.getLKP_SRNO()+"res no "+empbean.getRESIDSTAT());
     						if(lkbean.getLKP_SRNO() == empbean.getRESIDSTAT())
     						{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
     						}
     						else{
     							
     							%>
     							<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>
     							<%
     						}
 							}%>
     			 </select>
					
					<%
				}
					
				else
				{
					%>
					<select style="width: 138px" name="residancy" id="residancy">  
      					 <option value="0">Select</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("RESICD");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
					<%
					
				}
					%>           </td>
           
           
            <td align="left">Driving Licence No.</td>
            <td align="left"><input name="drvLicNumber" type="text"
					id="drvLicNumber"  value="<%=empbean.getDRVLISCNNO() %>" maxlength="15" /></td>
            <td align="left">Vehicle Name</td>
            <td align="left" colspan="2"><input name="vehicalname2" type="text" id="vehicalname2"
					value="<%=empbean.getVEHICLEDES() %>" maxlength="30" />
					
					<%if(action.equalsIgnoreCase("showemp"))
					{
						%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:ImageUpload()">Click Here Upload New Image</a>
					<%
					}
					%>
					</td>
          </tr>
        </table>
		<table width="1158" cellpadding="0" cellspacing="0" border="1"
			bordercolor="#000000" id="customers">
			
		</table>

		<table width="1170" border="1" cellpadding="0" cellspacing="0"
			bordercolor="#000000">
	
		</table>
		<table width="1171" cellpadding="0" cellspacing="0" border="1"
			bordercolor="#000000" id="customers">
			<tr align="left">
				
				<th colspan="2">Identity Detail</th>
				<th colspan="2">Joining and Left Detail</th>
				<th colspan="4">Employee And Other Detail</th>
			</tr>
		
			<tr>
				
				<td width="156">Pan number.</td>
				<td width="178"><input name="panno" type="text" id="panno"
					 value="<%=empbean.getPANNO() %>" maxlength="10" /></td>
				<td width="161">Joining Date<font color="red"><b>*</b></font></td>
				<td width="170"><input  name="jdate" id="jdate" type="text" value="<%=empbean.getDOJ()==null?"":empbean.getDOJ()%>"
				readonly="readonly" style="width: 118px;"/>&nbsp;
				<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('jdate', 'ddmmmyyyy')" />
				</td>
		 	<% if(!action.equalsIgnoreCase("showemp"))
				{
				%>
				<td style="width: 167px;">Confirmation Date</td>
				<td>
					<input name="confirmdate" type="text" id="confirmdate" value="" style="width: 118px;"/>&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('confirmdate', 'ddmmmyyyy')" />
				</td>
					<%
				}
				else
				{
					%>
					<td style="width: 167px;">Confirmation Date</td>
				<td>
					<input name="confirmdate" type="text" id="confirmdate" value="<%=empbean.getCONFIRM_DATE() %>" style="width: 118px;"/>&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('confirmdate', 'ddmmmyyyy')" />
				</td>
				<%
					
				}
							%>
				
				<td style="width: 155px;">Super Anotation</td>
				<td width="364">
				<%if(action.equalsIgnoreCase("showemp"))
				{
					if(!empbean.getDOB().equalsIgnoreCase(""))
					{
					
					%>
						<input name="anotation" id="anotation" readonly="readonly" value="<% 
						Calendar date11 = Calendar.getInstance();
				        date11.setTime(new Date (empbean.getDOB()));
				        date11.add(Calendar.DATE,-1);
				         Format f = new SimpleDateFormat("dd-MMM-yyyy");
				         f.format(date11.getTime());
			          	date11.add(Calendar.YEAR,58);
			  			f.format(date11.getTime()); %><%=f.format(date11.getTime())%>">				
						
					<%
					}
					else
					{
						%>
						<input name="anotation" id="anotation" readonly="readonly" value="">
						<%						
					}
				}
					
				else
				{
					%>
					
						<input name="anotation" id="anotation" type="text"  readonly="readonly">
					<%
					
				}
							%>
				
				
				</td>
		</tr>
			<tr class="alt">
				
				<td>Passport number.</td>
				<td><input name="passportno" type="text" id="passportno"
					value="<%=empbean.getPASSPORTNO()%>" maxlength="15" /></td>
				<td>Left Date</td>
				<td>
				<% if(action.equalsIgnoreCase("showemp"))
				{
				%>
				<input  name="ldate" id="ldate" type="text" value="<%=empbean.getDOL() %>" disabled="disabled">
				<%}
				else
				{
					%>
					<input  name="ldate" id="ldate" type="text" value="" disabled="disabled">
					<%
				}
				%>
				
				
				</td>
				<td>MLWF Number.</td>
				<td><input name="mlwfno" type="text" id="mlwfno"
					value="<%=empbean.getMLWFNO() %>" maxlength="20" /></td>
					
					
										<!-- added new -->
										
					<td>Employee Type</td>
		<td  align="left">
            <% if(action.equalsIgnoreCase("showemp"))
            {
            	%>
          <select style="width: 150px" name="emptype" id="emptype" >  
      					 
      				<%-- 	 <option value="0">Select....</option>   --%>  
      					   
<%--       					 <%if(empbean.getEMPTYPE()==1){
      						System.out.println("123");
      						 %>
      					 
      					 <option value="0" >PERMNANT</option>
      					 <option value="1" selected="selected" >TRN/PROB/CNTR</option>
      					 <%}
      					 else{ %>
      					  <option value="0" selected="selected" >PERMNANT</option>
      					<option value="1">TRN/PROB/CNTR</option>
      					 <%} %>
      					
 --%>      					 
    						<%
  							ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubNewLKP_DESC("EMP_CATEGORY");
 							for(Lookup lkbean : result){
 								System.out.println("lkbean.getLKP_SRNO():"+lkbean.getLKP_SRNO()+" lkbean.getLKP_DESC():"+lkbean.getLKP_DESC());
 							if(lkbean.getLKP_SRNO()== empbean.getEMPTYPE())
 							{
 								if(lkbean.getLKP_SRNO()==0)
 								{
 								System.out.println("lkbean.getLKP_SRNO():"+lkbean.getLKP_SRNO()); 
 								
 								%>
 								<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected">PERMNANT</option>  
 								<%
 								 }
 								else
 								{%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>
 								<% }
 								
 							}
 							else{
 								System.out.println("lkbean.getLKP_SRNO() NEW:"+lkbean.getLKP_SRNO()); 
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <% }
 							
 							} %>
     			 </select>
			<%}
            
            else
            {
            	%>
            	<select style="width: 150px" name="emptype" id="emptype" >  
      					 <option value="0">Select....</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubNewLKP_DESC("EMP_CATEGORY");
 							for(Lookup lkbean : result){
 								System.out.println("lkbean.getLKP_SRNO():"+lkbean.getLKP_SRNO()+" lkbean.getLKP_DESC():"+lkbean.getLKP_DESC());
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     					
     			<%-- 		  <option value="0">PERMANANT</option>  
     					  <option value="1">TRN/PROB/CNTR</option>
     				--%>	 
     					 
     			 </select>
            	<%
            	
            }%> 		</td>

			</tr>
			
			
			<!--emp type  -->


			<tr>
				<td>UAN Number</td><!--   ON THE PLACE OF IDENTITY   -->
				<td><input type="text" name="identity" id="identity"
					value="<%=empbean.getIDENTITY() %>" /></td>
				<td>Left Reason</td>
				<td>
				<%if(action.equalsIgnoreCase("showemp"))
				{
					
					%>
										
					<select style="width: 138px" name="lreason" id="lreason" disabled="disabled" >  
      					 <option value="0">Select</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("LEFTCD");
 							for(Lookup lkbean : result){
 								if(lkbean.getLKP_SRNO()== empbean.getRESNLFTCD())
 								{
 									%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
 									<%
 									}
 								else{
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}
     					 }%>
     			 </select>
					
					
					
					<%
				}
					
				else
				{
					%>
					<select style="width: 138px" name="lreason" id="lreason" disabled="disabled">  
      					 <option value="0" selected="selected">Select</option>  
    						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("LEFTCD");
 							for(Lookup lkbean : result){
     						%>
      			<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%}%>
     			 </select>
					<%
					
				}
							%>
		</td>
				<td>Other Detail</td>
				<td><input name="odetail" type="text" id="odetail"
					value="<%=empbean.getOTHERDTL() %>" size="60" maxlength="60" style="width: 143px;"/></td>

			</tr>
			<tr class="alt"><td> Aadhaar Card Number</td>
			<td align="left"> <input name="aadharNumber" type="text" id="aadharNumber" value="<%=empbean.getAADHAARNUM() %>" maxlength="12"></td>
			<%-- <td colspan="2">Initial Project :<font color="red"><b>*</b></font></td>
			<td colspan="2">
			
			<% if(!action.equalsIgnoreCase("showemp"))
				{
				%>
			<select style="width: 300px" name="prj" id="prj" >  
      					 <option value="0">Select</option>
    						<%
    						EmpOffHandler ofh = new EmpOffHandler();
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
     			 <%} %>
			</td> --%>
			<% if(!action.equalsIgnoreCase("showemp"))
				{
				%>
			<td> File Number</td>
			<td align="left"> <input name="fileNumber" type="text" id="fileNumber" value=""></td>
			
			<% }
			else{%>
			<td> File Number</td>
			<td align="left"> <input name="fileNumber" type="text" id="fileNumber" value="<%=empbean.getFILENUMBER() %>"></td>
			<!-- </tr> -->
			<%}
				%>
			<% if(!action.equalsIgnoreCase("showemp"))
				{
				%>
			<td> Prob/Trn/Contr Start Date</td>
			<td align="left"> <input name="prob_start_date" type="text" id="prob_start_date" value="" style="width: 118px;"/>
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
			onClick="javascript:NewCssCal('prob_start_date', 'ddmmmyyyy')" />
			</td>
			<!-- <td> Prob. End Date</td>
			<td align="left"> <input name="prob_end_date" type="text" id="prob_end_date" value="" style="width: 118px;"/>
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('prob_end_date', 'ddmmmyyyy')" />
			</td> -->
			<% }
			else{%>
			<td> Prob/Trn/Contr Start Date</td>
			<td align="left" id="probstart"> <input name="prob_start_date" type="text" id="prob_start_date" value="<%=empbean.getPROB_START_DATE()%>" style="width: 118px;"/>&nbsp;
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
			onClick="javascript:NewCssCal('prob_start_date', 'ddmmmyyyy')" id="probstartdates"/>
			</td>
			<%-- <td > Prob. End Date</td>
			<td align="left" id="probend"> <input name="prob_end_date" type="text" id="prob_end_date" value="<%=empbean.getPROB_END_DATE()%>" style="width: 70px;"/>
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('prob_end_date', 'ddmmmyyyy')" id="probenddates"/>
			</td> --%>
			</tr>
			
			<%}
				%>			
					
			<% if(!action.equalsIgnoreCase("showemp"))
				{
				%>
				
			<tr >
				<td style="width: 194px;">Leave Comfirmation Date<font color="red"><b>*</b></font></td>
				<td>
					<input name="leavedate" type="text"  readonly="readonly" id="leavedate" value="" maxlength="10" style="width: 127px;"/>&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('leavedate', 'ddmmmyyyy')" />
				</td>
				<!-- <td style="width: 194px;">Retirement Ext. Period</td>
				<td>
					<input name="extperdate" type="text" id="extperdate" value="" style="width: 127px;"/>&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('extperdate', 'ddmmmyyyy')" />
				</td> -->
				<td></td>
				<td></td>
				<td > Prob/Trn/Contr End Date</td>
			<td align="left" id="probend"> <input name="prob_end_date" type="text" id="prob_end_date" value="<%=empbean.getPROB_END_DATE()%>" style="width: 118px;"/>
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('prob_end_date', 'ddmmmyyyy')" id="probenddates"/>
			</td>
				
			</tr>
			<% }
			else{%>
				<tr >
				<td style="width: 194px;">Leave Comfirmation Date<font color="red"><b>*</b></font></td>
				<td>
					<input name="leavedate" type="text" id="leavedate" readonly="readonly" style="width: 127px;" value="<%=empbean.getLEAVEDATE()==null?"":empbean.getLEAVEDATE() %>" maxlength="10" />&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('leavedate', 'ddmmmyyyy')" />
				</td>
				<td style="width: 194px;">Retirement Ext. Period</td>
				<td id ="retireExtPr">
					<input name="extperdate" type="text" id="extperdate" style="width: 125px;" value="<%=empbean.getRETIREMENT_EXT_PERIOD() %>" style="width: 127px;"/>&nbsp;
					<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
						onClick="javascript:NewCssCal('extperdate', 'ddmmmyyyy')" />
				</td>
				 <td></td> 
				<td > Prob/Trn/Contr End Date</td>
			<td align="left" id="probend"> <input name="prob_end_date" type="text" id="prob_end_date" value="<%=empbean.getPROB_END_DATE()%>" style="width: 118px;"/>
			<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					onClick="javascript:NewCssCal('prob_end_date', 'ddmmmyyyy')" id="probenddates"/>
			</td>
			</tr>
							
			<%}
				%>
				
				
				 <tr class="alt">
				<td>LIC Gratuity Number</td>
			    <td align="left"> <input name="licGratNumber" type="text" id="licGratNumber" value="<%=empbean.getLIC_GRAT_NO()==0?"":empbean.getLIC_GRAT_NO() %>" maxlength="15"  onkeypress="return inputLimiter(event,'Numbers')"></td>	
			
				<td> LIC Leave Encashment Number</td>
			    <td align="left"> <input name="leaveEncashNo" type="text" id="leaveEncashNo" value="<%=empbean.getLIC_LEAVE_ENCASH_NO()==0?"":empbean.getLIC_LEAVE_ENCASH_NO() %>" maxlength="15" onkeypress="return inputLimiter(event,'Numbers')"></td>
			
			    <td> LIC Group Insurance Number</td>
			    <td align="left"> <input name="groupInsuranceNo" type="text" id="groupInsuranceNo" value="<%=empbean.getLIC_GROUP_INSURANCE_NO() == 0 ?"":empbean.getLIC_GROUP_INSURANCE_NO()%>" maxlength="15" onkeypress="return inputLimiter(event,'Numbers')"></td>
			
			</tr>
			<tr>
			    <td> LIC Leave Encash Policy No</td>
			    <td align="left"> <input name="leaveEncashPolicyNo" type="text" id="leaveEncashPolicyNo" value="<%=empbean.getLIC_LEAVE_ENCASH_POLICY_NO()==0?"":empbean.getLIC_LEAVE_ENCASH_POLICY_NO() %>" maxlength="15" onkeypress="return inputLimiter(event,'Numbers')"></td>
			
			    <td> LIC Grat Policy No</td>
			    <td align="left"> <input name="gratPolicyNo" type="text" id="gratPolicyNo" value="<%=empbean.getLIC_GRAT_POLICY_NO()== 0 ?"":empbean.getLIC_GRAT_POLICY_NO()%>" maxlength="15" onkeypress="return inputLimiter(event,'Numbers')"></td>
			
			                 
			 </tr> 
	
			</table>



		<table width="1171" cellpadding="0" cellspacing="0" border="1"
			bordercolor="#000000" id="customers">
			<tr align="left">
				<th colspan="7">PF Detail</th>

			</tr>
		
			<tr align="left" class="alt">
				<td width="156" >PF NO</td>
				<td width="178"><input type="text" name="pfno" id="pfno"
					value="<%=empbean.getPFNO() %>" maxlength="16"/></td>

				<td width="161">PF Join Date</td>
				<td width="170"   >
				<input size="14" name="pfjDate" id="pfjDate" type="text" value="<%=empbean.getPFOPENDT()==null?"":empbean.getPFOPENDT() %>"
				readonly="readonly" >&nbsp;<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('pfjDate', 'ddmmmyyyy')" />			</td>
				<td width="320"></td>
				<td width="222" rowspan="2"   align="right">
				<img width='200' height='40' id="sign" style="z-index: 7;" src="DisplayPhotoServlet?type=SIGN&empno=<%=empbean.getEMPNO() %>" >
				<br>
				<%if(action.equalsIgnoreCase("showemp"))
				{
					%>
				<a href="javascript:SignImageUpload()">Click Here Upload Sign Image</a>
				<%
				}%>
				</td>
			</tr>
				<tr>
				<td >PF Nominee</td>
				<td ><input type="text" name="pfNominee"
					id="pfNominee" value="<%=empbean.getPFNOMINEE() %>" /></td>
				<td width="138">Nominee Relation.</td>
				<td  ><input type="text" name="NomRel" id="NomRel"
					value="<%=empbean.getPFNOMIREL() %>" /></td>
			    </tr>
		</table>
		<table width="1171" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000" id="customers">
			<tr align="left">

				<th colspan="7">Security Detail</th>
			</tr>
		
			<tr>
				<td width="140">Security Bond</td>
				
				<%if(action.equalsIgnoreCase("showemp"))
            {
            %>
           <td width="170">
					<select style="width: 138px" name="SecBond" id="SecBond" onChange="changesecbond()">
				<option>Select...</option>
            	
            	<% if(empbean.getSBOND().equalsIgnoreCase("Y"))
            		{
            			%>
            			
            	<option value="Y" selected="selected">Yes</option>
            	<option value="N">No</option>
            			<%
            		}else
            		{
            			%>
            			
            	<option value="Y">Yes</option>
            	<option value="N" selected="selected">No</option>
            			<%
            		}
            	%>
            	
            	
            	</select>
					
					
					</td>
			<%
			}
            else
            {
            	%>
            	<td width="170"><select  name="SecBond" id="SecBond" onChange="changesecbond()">
            	<option value="0">Select...</option>
            	<option value='Y'>Yes</option>
            	<option value='N'>No</option>
            	
            	</select></td>
            	<%
            	
            }
            %>	
				<td width="145">Deposite Amount</td>
				<td  width="165"><input type="text" name="Depamnt" id="Depamnt"
					 value="<%=empbean.getDEPAMT() %>" /></td>
					 
					 <td colspan="2" width="542"></td>

			</tr>
		</table>
		<table width="100%">
 		<% if(action.equalsIgnoreCase("addemp"))
 			{
 			%>
 			<tr><td  colspan="10" align="center">
 			<div class="employeedtl">
				<ul>
					<li>	
						<input  type="submit" value="Submit"   class="myButton"  id="ans" name="ans"> 
					</li>
					<li> 
						<input  type="button"   class="myButton"  value="Cancel" onClick="window.document.location.href='searchEmployee.jsp'">
					</li>
				</ul>
			</div>
		</td></tr>
 			<%
 			}
 			else{
 				%>
 				<tr>
	 				<td  colspan="10" align="center">
						<div class="employeedtl">
							<ul>
								<li>
									<input  type="submit" value="Update"   class="myButton"  id="ans" name="ans"> 
									</li>
								<li> 
									<input  type="button"   class="myButton"  value="Cancel" onClick="window.document.location.href='searchEmployee.jsp'">
								</li>
							</ul>
						</div>
					</td>
				</tr>
 				<%
 				
 			}
 			%>
	
 </table>
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
	
	</form>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->


</div></div>
<!--  end content-outer........................................................END -->
<%
if(action.equalsIgnoreCase("addemp"))
{
	%>
	<script type="text/javascript">
	$(':input','#employeeform') .not(':button, :submit, :reset, :hidden') .val('') .removeAttr('checked') .removeAttr('selected');
	
    </script>
	
	<% }%>




    
<input type="hidden" id='flag' name='flag' value='<%=request.getParameter("flag")==null?"":request.getParameter("flag")%>'> 
</body>
</html>