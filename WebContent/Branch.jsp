<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.jfree.data.time.Week"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="payroll.Model.BranchBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
	<script type="text/javascript" src="js/jquery.datePicker.js"></script>



<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<title>Branch Management</title>
<script language="javascript" type="text/javascript">  
    	 	var xmlHttp ;
      		
    	 	if (window.XMLHttpRequest) { // Mozilla, Safari, ...
    	 		xmlHttp = new XMLHttpRequest();
    	 	    } else if (window.ActiveXObject) { // IE
    	 	      try {
    	 	    	 xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    	 	      } 
    	 	      catch (e) {
    	 	        try {
    	 	        	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    	 	        } 
    	 	        catch (e) {}
    	 	      }
    	 	    }

        function showCity(str)
        {
				
    			var url="city.jsp";
      			url +="?count=" +str;
      			xmlHttp.onreadystatechange = stateChange;
      			xmlHttp.open("GET", url, true);
      			xmlHttp.send(null);
      }

      function stateChange()
      {   
      	if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
          	{   
      		document.getElementById("city").innerHTML=xmlHttp.responseText ;
      		}   
      }


      
      function showParCity(str)
      {
    	
  	     var url="city.jsp";
    	      url +="?count=" +str;
    	      xmlHttp.onreadystatechange = stateChange2;
    	      xmlHttp.open("GET", url, true);
    	      xmlHttp.send(null);
    	   }
 
       function stateChange2()
      { 
        	      
    	     if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete"){   
    	      document.getElementById("parCity").innerHTML=xmlHttp.responseText ;
    	      }   
      }  
      function movetoNext(current, nextFieldID) {
    	  if (current.value.length >= current.maxLength) {
    	  document.getElementById(nextFieldID).focus();
    	  }
    	  }
      
      function checksat(objName, chkBox) {
    		

    		
    		document.getElementById(objName).style.display = (chkBox.checked) ? "none":"";
    		KeepCount();
    	}
      
      function KeepCount1() {

    		var NewCount = 0;
    		
    		if (document.branch.sun.checked)
    		{NewCount = NewCount + 1;}

    		if (document.branch.mon.checked)
    		{NewCount = NewCount + 1;}

    		if (document.branch.tue.checked)
    		{NewCount = NewCount + 1;}

    		if (document.branch.wed.checked)
    		{NewCount = NewCount + 1;}

    		if (document.branch.thu.checked)
    		{NewCount = NewCount + 1;}
    			
    		if (document.branch.fri.checked)
    		{NewCount = NewCount + 1;}

    		if (document.branch.sat.checked)
    		{
    		NewCount = NewCount + 1;}
    		 

    		
    		if (NewCount == 3)
    		{
    		alert('Pick Just Two Please');
    		
    		document.branch; 
    		return false;
    		}
    		document.forms["branch"]["offday"].focus(); return false;
    	} 
      
      
      function KeepCount() {

    		var NewCount = 0;
    		
    		if (document.editBranch.sun.checked)
    		{NewCount = NewCount + 1;}

    		if (document.editBranch.mon.checked)
    		{NewCount = NewCount + 1;}

    		if (document.editBranch.tue.checked)
    		{NewCount = NewCount + 1;}

    		if (document.editBranch.wed.checked)
    		{NewCount = NewCount + 1;}

    		if (document.editBranch.thu.checked)
    		{NewCount = NewCount + 1;}
    			
    		if (document.editBranch.fri.checked)
    		{NewCount = NewCount + 1;}

    		if (document.editBranch.sat.checked)
    		{
    		NewCount = NewCount + 1;}
    		 

    		
    		if (NewCount == 3)
    		{
    		alert('Pick Just Two Please');
    		
    		document.editBranch; 
    		return false;
    		}
    		document.forms["editBranch"]["offday"].focus(); return false;
    	} 
      
      
      function validateAdd(){  	  
  	  var state=document.getElementById("state").value;
  	var city=document.getElementById("city").value;
  	var startDate = document.branch.startDate.value;
  	var effectiveDate=document.branch.effectiveDate.value;
  	
    var pincode = document.forms["branch"]["pincode"].value;
    
    	  if(state=="" || state==0)
    	  {
    	  alert("Please select the state");
    	  return false;
    	  }
    	  if(city=="" || city==0 || city==-1 )
    	  {
    	  alert("Please select the city");
    	  return false;
    	  }	
    	  if(pincode=="" || pincode.length !=6){
    			alert("Please enter 6 digit pin code.!");
    		    document.forms["branch"]["pincode"].focus();
    		    return false;	
    		} 
    	  
    	  startDate = startDate.replace(/-/g, "/");
    	  if (document.branch.startDate.value == "") {
    			alert("please select Start Date");
    			document.branch.startDate.focus();
    			return false;
    		}
    	  effectiveDate=effectiveDate.replace(/-/g, "/");
    	  if (document.branch.effectiveDate.value == "") {
  			alert("please select Effective Date");
  			document.branch.effectiveDate.focus();
  			return false;
  		}
    	  var result=confirm("The details you are saving are going to affect everywhere. Do you want to save the branch details? ");
		  if(result==false)
			  return false;  
    }
      function validateEdit(){  	  
      	  var state=document.getElementById("state").value;
      	var city=document.getElementById("city").value;
      	var startDate = document.editBranch.startDate.value;
      	var effectiveDate=document.editBranch.effectiveDate.value;
      	
        var pincode = document.forms["editBranch"]["pincode"].value;
     
        
        	  if(state=="" || state==0)
        	  {
        	  alert("Please select the state");
        	  return false;
        	  }
        	  if(city=="" || city==0 || city==-1 )
        	  {
        	  alert("Please select the city");
        	  return false;
        	  }	
        	   if(pincode=="" || pincode.length !=6){
           		alert("Please enter 6 digit pin code.!");
           	    document.forms["editBranch"]["pincode"].focus();
           	    return false;	
           	} 
        	  
        	  startDate = startDate.replace(/-/g, "/");
        	  if (document.editBranch.startDate.value == "") {
        			alert("Please select Start Date");
        			document.editBranch.startDate.focus();
        			return false;
        		}
        	  effectiveDate=effectiveDate.replace(/-/g, "/");
        	  if (document.editBranch.effectiveDate.value == "") {
      			alert("Please select Effective Date");
      			document.editBranch.effectiveDate.focus();
      			return false;
      		}
        	  var result=confirm("Do you want to save the branch details? ");
    		  if(result==false)
    			  return false;  
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
      

      function inputLimiterSecond(e,allow) {
      		  var AllowableCharacters = '';
      		  if (allow == 'Numbers'){AllowableCharacters='1234567890-+';}
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
      
      
      function checkBranch() {
    		var f = parseInt(document.getElementById("flag1").value);
  
    		if (f ==1) {
    			alert("Details saved Successfully");

    		}

    	

    	}
     
  </script>  





</head>
 <%
 
 String pageName = "Branch.jsp";
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
<body onload="checkBranch()" >
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:80%; ">
<!-- start content -->
<div id="content">
<div id="page-heading"><h1>Branch Details</h1>
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
			<div id="table-content" >
 		<center>	
 <%
 int flag1=-1;
	try
		{
		flag1=Integer.parseInt(request.getParameter("flag1")==null?"":request.getParameter("flag1")); 
		}catch(Exception e)
		{
	
			
		}
 
 BranchDAO brnDAO = new BranchDAO();
 String weekOffFirst="";
 String weekOffSecond="";
 String saturdayFirst="";
 String saturdaySecond="";
 String saturdayThird="";
 String saturdayFourth="";
 String saturdayFifth="";
 String startDate="";
 String effectiveDate="";
 String action = request.getParameter("action")==null?"":request.getParameter("action");
 System.out.println("action is"+action);
 if(!action.equalsIgnoreCase("editBranch")){
	
	%> 

			<form name="branch" action="BranchServlet?action=AddBranch" method="post" onsubmit=" return validateAdd()">
			
			  <table width="100%"  id="customers">
			   
			    <tr> <th colspan="4">Add New Branch</th></tr>
				<tr> <td >Branch Code :<font color="red"><b>*</b></font></td>			
			         <td colspan="3"><input class="form-control" type="text" id="branchCode" name="branchCode" onkeypress="return inputLimiter(event,'Numbers')"  placeholder="Enter branch code.Once saved cannot be altered" required size="55"></td>
			   </tr>
		       <tr>  <td >	Branch Name: <font color="red"><b>*</b></font></td>
		             <td colspan="3"> <input class="form-control"  type="text" id="branchName" name="branchName" placeholder="Please enter branch name" required size="55"></td>
		       </tr>
			   <tr>  <td >Zone: <font color="red"><b>*</b></font></td>
			   		 <td colspan="3"><input class="form-control"  type="text" id="zone" name="zone" required placeholder="Please enter zone"  size="55"></td>
			   </tr>
			   <tr>  <td >Address: <font color="red"><b>*</b></font></td>
			         <td colspan="3"><input class="form-control"  type="text" id="address1" name="address1"  onkeyup="movetoNext(this, 'address2')" required placeholder="Address Line 1" maxlength="40" size="55"/></td>
			   </tr>
			   <tr>  <td ></td>
			         <td colspan="3"><input class="form-control"  type="text" id="address2" name="address2"  maxlength="40"  onkeyup="movetoNext(this, 'address3')" placeholder="Address Line 2" size="55"/></td>
			   </tr>
			   <tr>  <td ></td>
			         <td colspan="3"><input class="form-control"  type="text" id="address3" name="address3"  maxlength="40" placeholder="Address Line 3"  size="55"/></td>
			   </tr>
			   <tr>
                    <td  >State  : <font color="red"><b>*</b></font></td>
                    <td> <select class="form-control"  name="state" id="state"  onChange="showCity(this.value)">  
		                 <option value="0">Select</option>  
			          <%
			             ArrayList<Lookup> result=new ArrayList<Lookup>();
			             LookupHandler lkhp = new LookupHandler();
			             result=lkhp.getSubLKP_DESC("STATE");
			             for(Lookup lkbean : result){
			          %>
				         <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
				      <%
				     }
				      %>
		               </select></td>
            
		             <td>City: <font color="red"><b>*</b></font></td>
          		     <td>   <select class="form-control"  name="city" id="city" >  
		                   <option value="0">Select</option>  
			             <%
						    ArrayList<Lookup> result1=new ArrayList<Lookup>();
						    LookupHandler lkhp1 = new LookupHandler();
						    result1=lkhp1.getSubLKP_DESC("CITY");
						    for(Lookup lkbean : result1){
			             %>
				           <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
				         <%
				         }
				        %>
		                </select>
                    </td>
              
			   </tr>
               <tr> <td>Pin Code : <font color="red"><b>*</b></font></td>
               		 <td><input class="form-control"  type="text" id="pincode" name="pincode" onkeypress="return inputLimiter(event,'Numbers')" placeholder="Enter pincode here" required  maxlength="6" size="22"/></td>
               		
                    <td>Phone : </td>
                    <td><input  class="form-control" type="text" id="phone1" onkeypress="return inputLimiterSecond(event,'Numbers')" placeholder="Enter phone number here" name="phone1" size="22" /></td>
              </tr>
            
              <tr>  <td>Phone :</td>
                    <td>   <input class="form-control"  type="text" id="phone2" onkeypress="return inputLimiterSecond(event,'Numbers')"   placeholder="Enter phone number here"  name="phone2" size="22" /></td>
                    <td>Fax :</td>
            	 	<td><input class="form-control"  type="text" id="fax" onkeypress="return inputLimiterSecond(event,'Numbers')"   placeholder="Enter fax number here"  name="fax" align="right" size="22"/></td>
              </tr>
            
              <tr>  <td>Start Date : <font color="red"><b>*</b></font></td>
                    <td> <input class="form-control"  name="startDate"  size="22" id="startDate"
													type="text" onBlur="if(value=='')" required   placeholder="Click on calendar icon"   readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" />
													</td>
                    <td>Effective Date : <font color="red"><b>*</b></font></td>
                    <td><input class="form-control"  name="effectiveDate"  size="22" id="effectiveDate"
													type="text" onBlur="if(value=='')" required  placeholder="Click on calendar icon"    readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('effectiveDate', 'ddmmmyyyy')" />
													</td>
             </tr>
             <tr>  <td >FIRST SHIFT FROM :</td>
                   <td> <select class="form-control"  style="width: 50px" name="firstShiftFromHours" id="firstShiftFromHours"><option value="0">0</option>
                         <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                         <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                         <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                        </select>

                        <select class="form-control"  style="width: 50px" name="firstShiftFromMinutes" id="firstShiftFromMinutes">
                         <option value="00">00</option> <option value="30">30</option>
                        </select>    
                  </td>
         
                  <td>FIRST SHIFT TO :  </td>
                   <td>   <select class="form-control"  style="width: 50px" name="firstShiftToHours" id="firstShiftToHours"><option value="0">0</option>
                        <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                        <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                        <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                       </select>

                       <select class="form-control"  style="width: 50px" name="firstShiftToMinutes" id="firstShiftToMinutes">
                        <option value="00">00</option> <option value="30">30</option>
                       </select></td>  
            </tr>
            
            <tr>  <td>SECOND SHIFT FROM  : </td>
                   <td>   <select class="form-control"  style="width: 50px" name="secondShiftFromHours" id="secondShiftFromHours"><option value="0">0</option>
                           <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                           <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                           <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                           </select>

                           <select class="form-control"  style="width: 50px" name="secondShiftFromMinutes" id="secondShiftFromMinutes">
                              <option value="00">00</option> <option value="30">30</option> 
                           </select>  </td>
                  <td>SECOND SHIFT TO   :  </td>
                   <td> <select class="form-control"  style="width: 50px" name="secondShiftToHours" id="secondShiftToHours"><option value="0">0</option>
                        <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                        <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                        <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                        </select>

                        <select class="form-control"  style="width: 50px" name="secondShiftToMinutes" id="secondShiftToMinutes">
                         <option value="00">00</option> <option value="30">30</option>
                        </select>   </td>
            </tr>
            
            <tr>  <td>FIRST CASH FROM :</td>
                  <td> <select class="form-control"  style="width: 50px" name="firstCashFromHours" id="firstCashFromHours"><option value="0">0</option>
                            <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                            <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                            <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                       </select>

                      <select class="form-control"  style="width: 50px" name="firstCashFromMinutes" id="firstCashFromMinutes">
                          <option value="00">00</option> <option value="30">30</option>
                      </select>  </td>
                 <td>FIRST CASH TO : </td>
                 <td>  <select class="form-control"  style="width: 50px" name="firstCashToHours" id="firstCashToHours"><option value="0">0</option>
                        <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                        <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                        <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                       </select>

                       <select class="form-control"  style="width: 50px" name="firstCashToMinutes" id="firstCashToMinutes">
                        <option value="00">00</option> <option value="30">30</option>
                       </select>   </td>
            </tr>
            
            <tr>  <td>SECOND CASH FROM :</td>
                  <td> <select class="form-control"  style="width: 50px" name="secondCashFromHours" id="secondCashFromHours"><option value="0">0</option>
                        <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option>
                        <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                        <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option> 
                       </select>

                       <select class="form-control"  style="width: 50px" name="secondCashFromMinutes" id="secondCashFromMinutes">
                         <option value="00">00</option> <option value="30">30</option>
                       </select>  </td>
                  <td>SECOND CASH TO :  </td>
                   <td> <select class="form-control"  style="width: 50px" name="secondCashToHours" id="secondCashToHours"><option value="0">0</option>
                         <option value="1">1</option>  <option value="2">2</option><option value="3">3</option><option value="4">4</option> 
                         <option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                         <option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option>
                        </select>

                        <select class="form-control"  style="width: 50px" name="secondCashToMinutes" id="secondCashToMinutes">
                         <option value="00">00</option> <option value="30">30</option>
                        </select>  </td>
            </tr>
            
            
            <tr  style="height:30px;"><td>Select Week Off Day :</td><td colspan="3"><span style="font-size:75%"></span>
							   <input class="form-control"  type="checkbox" name="offday" id="sun" value="sun" onClick="return KeepCount1()"/>&nbsp;Sun&nbsp;&nbsp;
											<input class="form-control"  type="checkbox" name="offday" id="mon" value="mon" onClick="return KeepCount1()" />&nbsp;Mon&nbsp;&nbsp;
											<input class="form-control"  type="checkbox" name="offday" id="tue" value="tue" onClick="return KeepCount1()" />&nbsp;Tue&nbsp;&nbsp;
											<input class="form-control"  type="checkbox" name="offday" id="wed" value="wed" onClick="return KeepCount1()"/>&nbsp;Wed&nbsp;&nbsp;
											<input class="form-control"  type="checkbox" name="offday" id="thu" value="thu" onClick="return KeepCount1()" />&nbsp;Thu&nbsp;&nbsp;
											<input class="form-control"  type="checkbox" name="offday" id="fri" value="fri" onClick="return KeepCount1()" />&nbsp;Fri&nbsp;&nbsp;
										    <input class="form-control"  type="checkbox" name="offday" id="sat"value="sat" onclick="checksat('rowSubOrders',this);">&nbsp;Sat&nbsp;

							</td>
			</tr>			
			<tr id="rowSubOrders" >	 <td >Alternate Saturday ?</td>
						<td colspan="3">		<input class="form-control"  type="checkbox" name="altsatday" id="one" value="1" />&nbsp;1 &nbsp;&nbsp; &nbsp;&nbsp;
												<input class="form-control"  type="checkbox" name="altsatday" id="two" value="2" />&nbsp;2 &nbsp;&nbsp; &nbsp;&nbsp;
												<input class="form-control"  type="checkbox" name="altsatday" id="three" value="3" />&nbsp;3 &nbsp;&nbsp; &nbsp;&nbsp;
												<input class="form-control"  type="checkbox" name="altsatday" id="four" value="4" />&nbsp;4 &nbsp;&nbsp; &nbsp;&nbsp;
												<input class="form-control"  type="checkbox" name="altsatday" id="five" value="5" />&nbsp;5<br></td>
			

			</tr>
			
			<tr>
			
                      <td >BRANCH STATUS :</td>  
                     <td colspan="3"> <select class="form-control"  style="width: 140px" name="siteStatus" id="siteStatus"><option value="1">Non-Active</option>  
                      <option value="0">Active</option>  
                   </select></td>
     
			</tr>
			
            
            <tr><td colspan="4" align="center"><input class="myButton"  type="submit" value="Save" > <input class="myButton" type="reset" value="Clear"></td></tr>
            <%
            EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
            String todaysDate=empAttendanceHandler.getServerDate();
            int loggedEmployee = (Integer)session.getAttribute("EMPNO");
            %>
            <input class="form-control"  name="todaysDate" id="todaysDate" type="hidden" value="<%=todaysDate %>" />
             <input class="form-control"  name="loggedEmployee" id="loggedEmployee" type="hidden" value="<%=loggedEmployee%>" />
			</table>
            
</form>
<br/>
<%
 }
 


if(action.equalsIgnoreCase("editBranch")){
	 System.out.println("action is"+action);
	int brncd = request.getParameter("brncd")==null?0:Integer.parseInt(request.getParameter("brncd"));
 BranchBean brnBean = brnDAO.getParticularBranchDetails(brncd);
	 LookupHandler lh = new LookupHandler(); 



			if(brnBean.getBranchCode () == brncd ){
		//for hours		
	
				String  firstShiftFromHours=String.valueOf(brnBean.getFirstShiftFrom());
              

				firstShiftFromHours=firstShiftFromHours.length()==4?firstShiftFromHours.substring(0,2):firstShiftFromHours.substring(0,1);
				 
				String firstShiftToHours=String.valueOf(brnBean.getFirstShiftTo());
              

				firstShiftToHours=firstShiftToHours.length()==4?firstShiftToHours.substring(0,2):firstShiftToHours.substring(0,1);
				 
				String secondShiftFromHours=String.valueOf(brnBean.getSecondShiftFrom());
              

				secondShiftFromHours=secondShiftFromHours.length()==4?secondShiftFromHours.substring(0,2):secondShiftFromHours.substring(0,1);
				  
				String secondShiftToHours=String.valueOf(brnBean.getSecondShiftTo());
              

				 secondShiftToHours=secondShiftToHours.length()==4?secondShiftToHours.substring(0,2):secondShiftToHours.substring(0,1);
				  
				String firstCashFromHours=String.valueOf(brnBean.getFirstCashFrom());
             

				firstCashFromHours=firstCashFromHours.length()==4?firstCashFromHours.substring(0,2):firstCashFromHours.substring(0,1);
				 
				String firstCashToHours=String.valueOf(brnBean.getFirstCashTo());
               

				firstCashToHours=firstCashToHours.length()==4?firstCashToHours.substring(0,2):firstCashToHours.substring(0,1);
				  
				String secondCashFromHours=String.valueOf(brnBean.getSecondCashFrom());
              

				secondCashFromHours=secondCashFromHours.length()==4?secondCashFromHours.substring(0,2):secondCashFromHours.substring(0,1);
				   
				String secondCashToHours=String.valueOf(brnBean.getSecondCashTo());
             

				secondCashToHours=secondCashToHours.length()==4?secondCashToHours.substring(0,2):secondCashToHours.substring(0,1);
				   
	  //for minutes
	  
	  	String  firstShiftFromMinutes=String.valueOf(brnBean.getFirstShiftFrom());
        

				firstShiftFromMinutes=firstShiftFromMinutes.length()==4?firstShiftFromMinutes.substring(3,4)+"0":firstShiftFromMinutes.substring(2,3)+"0";
				
				String firstShiftToMinutes=String.valueOf(brnBean.getFirstShiftTo());
                

				firstShiftToMinutes=firstShiftToMinutes.length()==4?firstShiftToMinutes.substring(3,4)+"0":firstShiftToMinutes.substring(2,3)+"0";
				
				String secondShiftFromMinutes=String.valueOf(brnBean.getSecondShiftFrom());
                

				secondShiftFromMinutes=secondShiftFromMinutes.length()==4?secondShiftFromMinutes.substring(3,4)+"0":secondShiftFromMinutes.substring(2,3)+"0";
				
				String secondShiftToMinutes=String.valueOf(brnBean.getSecondShiftTo());
              

				 secondShiftToMinutes=secondShiftToMinutes.length()==4?secondShiftToMinutes.substring(3,4)+"0":secondShiftToMinutes.substring(2,3)+"0";
				 String firstCashFromMinutes=String.valueOf(brnBean.getFirstCashFrom());
                

				firstCashFromMinutes=firstCashFromMinutes.length()==4?firstCashFromMinutes.substring(3,4)+"0":firstCashFromMinutes.substring(2,3)+"0";
				String firstCashToMinutes=String.valueOf(brnBean.getFirstCashTo());
               

				firstCashToMinutes=firstCashToMinutes.length()==4?firstCashToMinutes.substring(3,4)+"0":firstCashToMinutes.substring(2,3)+"0";
				String secondCashFromMinutes=String.valueOf(brnBean.getSecondCashFrom());
              

				secondCashFromMinutes=secondCashFromMinutes.length()==4?secondCashFromMinutes.substring(3,4)+"0":secondCashFromMinutes.substring(2,3)+"0";
				String secondCashToMinutes=String.valueOf(brnBean.getSecondCashTo());
              

				secondCashToMinutes=secondCashToMinutes.length()==4?secondCashToMinutes.substring(3,4)+"0":secondCashToMinutes.substring(2,3)+"0";
%>
<div align="center">
<form action="BranchServlet?action=EditBrnch" Name="editBranch" method="post" onsubmit=" return validateEdit()">
		
		<table width="100%"  id="customers">
			<tr><th colspan="4">Edit Branch</th></tr>
			
			<tr><td >Branch Code: </td>
			    <td colspan="3"><input class="form-control"   type="text"   id="branchCode" readonly="readonly" name="branchCode" onkeypress="return inputLimiter(event,'Numbers')"  value="<%=brnBean.getBranchCode()%>" required size="55"></td>
			</tr>
		    <tr> <td>	Branch Name: <font color="red"><b>*</b></font></td>
		         <td colspan="3"><input class="form-control"   type="text" id="branchName" name="branchName" value="<%=brnBean.getBranchName()==null?"":brnBean.getBranchName()%>" required size="55"></td></tr>
			<tr> <td>Zone: <font color="red"><b>*</b></font></td>
			     <td  colspan="3"><input class="form-control"   type="text" id="zone" name="zone" value="<%=brnBean.getZone()==null?"":brnBean.getZone()%>" required size="55"></td></tr>
			<tr> <td>Address: <font color="red"><b>*</b></font></td>
			     <td colspan="3"> <input class="form-control"   type="text" id="address1" name="address1"  onkeyup="movetoNext(this, 'address2')" required value="<%=brnBean.getAddress1()==null?"":brnBean.getAddress1()%>"  maxlength="40" size="55"/></td></tr>
			<tr> <td></td><td colspan="3"><input class="form-control"   type="text" id="address2" name="address2"  maxlength="40"  onkeyup="movetoNext(this, 'address3')"  value="<%=brnBean.getAddress2()==null?"":brnBean.getAddress2()%>" size="55"/></td></tr>
			<tr> <td></td><td colspan="3"><input class="form-control"   type="text" id="address3" name="address3"  maxlength="40"  value="<%=brnBean.getAddress3()==null?"":brnBean.getAddress3()%>"   size="55"/></td></tr>
			 <tr>
                    <td >State  : <font color="red"><b>*</b></font></td>
                    <td> <select class="form-control"   name="state" id="state"  onChange="showCity(this.value)">  
		       <option value="0">Select</option>  
			    <%
			   
				 ArrayList<Lookup> result=new ArrayList<Lookup>();
				
				
				result=lh.getSubLKP_DESC("STATE");
				for(Lookup lkbean : result)
				{
			
					
						if(lkbean.getLKP_SRNO()== brnBean.getState())
				{
					
				%>
					<option value="<%=lkbean.getLKP_SRNO()%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>  
					<%}
				else {
						%>
					<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
			 		<%
					}}%>
		      </select></td>
            
		     <td>City: <font color="red"><b>*</b></font> </td>
		     <td>
		      <select class="form-control"   name="city" id="city" >  
		       <option value="0">Select</option>  
			    <%
    						ArrayList<Lookup> result2=new ArrayList<Lookup>();
    						result2=lh.getSubLKP_DESC("CITY");
    						for(Lookup lkbean : result2)
 							{
 							if(lkbean.getLKP_SRNO()== brnBean.getCity())
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
            <tr><td>Pin Code : <font color="red"><b>*</b></font></td>
                <td><input class="form-control"   type="text" id="pincode" name="pincode" onkeypress="return inputLimiter(event,'Numbers')"  onkeypress="return inputLimiter(event,'Numbers')" value="<%=brnBean.getPincode()==0?"":brnBean.getPincode()%>" required maxlength="6" size="22"/></td>
                <td>Phone :</td>
                 <td>   <input class="form-control"   type="text" id="phone1" onkeypress="return inputLimiterSecond(event,'Numbers')"  name="phone1" value="<%=brnBean.getPhone1()==null?"":brnBean.getPhone1()%>" size="22" /></td>
            </tr>
            
              <tr><td>Phone :</td>
                 <td><input class="form-control"   type="text" id="phone2" onkeypress="return inputLimiterSecond(event,'Numbers')"  name="phone2" value="<%=brnBean.getPhone2()==null?"":brnBean.getPhone2()%>" size="22" /></td>
                <td>Fax :</td>
                <td>      	 <input class="form-control"   type="text" id="fax" onkeypress="return inputLimiterSecond(event,'Numbers')"  name="fax" align="right" value="<%=brnBean.getFax()==null?"":brnBean.getFax()%>" size="22"/></td>
            </tr>
            
            <%
            if(brnBean.getStartDate()!=null){
            startDate=brnBean.getStartDate();
          
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
             Date tempDate=simpleDateFormat.parse(startDate);
             SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-YYYY");           
             startDate= outputDateFormat.format(tempDate);
             effectiveDate=brnBean.getEffectiveDate();
             tempDate=simpleDateFormat.parse(effectiveDate);
             effectiveDate= outputDateFormat.format(tempDate);
            }
             %>
            
            
              <tr><td>Start Date : <font color="red"><b>*</b></font> </td>
                 <td>
                  <input class="form-control"   name="startDate"  size="22" id="startDate"
													type="text" onBlur="if(value=='')" value="<%=startDate==null?"":startDate%>" required  readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" />
													</td>
              <td>Effective Date : <font color="red"><b>*</b></font> </td>
           <td><input class="form-control"   name="effectiveDate"  size="22" id="effectiveDate"
													type="text" onBlur="if(value=='')" value="<%=effectiveDate==null?"":effectiveDate%>"  required  readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('effectiveDate', 'ddmmmyyyy')" />
													</td>
            </tr>
               <tr><td >FIRST SHIFT FROM :</td>
                   <td><select class="form-control"   style="width: 50px" name="firstShiftFromHours"   id="firstShiftFromHours">
                       <option value="<%=firstShiftFromHours%>"><%=firstShiftFromHours%></option>
                       <option value="0" <%=firstShiftFromHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
			  		   <option value="1" <%=firstShiftFromHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option> 
			  		   <option value="2" <%=firstShiftFromHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
			  		   <option value="3" <%=firstShiftFromHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
			  		   <option value="4" <%=firstShiftFromHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
					   <option value="5" <%=firstShiftFromHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
					   <option value="6" <%=firstShiftFromHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
					   <option value="7" <%=firstShiftFromHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
					   <option value="8" <%=firstShiftFromHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
					   <option value="9" <%=firstShiftFromHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					   <option value="10" <%=firstShiftFromHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					   <option value="11" <%=firstShiftFromHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					   <option value="12" <%=firstShiftFromHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
                       </select>

                      <select class="form-control"   style="width: 50px" name="firstShiftFromMinutes" id="firstShiftFromMinutes" >
                      <option value="<%=firstShiftFromMinutes%>"><%=firstShiftFromMinutes%></option>
                        <option value="00" <%=firstShiftFromMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
					   <option value="30" <%=firstShiftFromMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>

                       </select>    
               </td>
         
              <td>FIRST SHIFT TO :  </td>
                    <td> <select class="form-control"   style="width: 50px" name="firstShiftToHours"  id="firstShiftToHours">
                       <option value="<%=firstShiftToHours%>"><%=firstShiftToHours%></option>
                       <option value="0" <%=firstShiftToHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
			  		   <option value="1" <%=firstShiftToHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option> 
			  		   <option value="2" <%=firstShiftToHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
			  		   <option value="3" <%=firstShiftToHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
			  		   <option value="4" <%=firstShiftToHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
					   <option value="5" <%=firstShiftToHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
					   <option value="6" <%=firstShiftToHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
					   <option value="7" <%=firstShiftToHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
					   <option value="8" <%=firstShiftToHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
					   <option value="9" <%=firstShiftToHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					   <option value="10" <%=firstShiftToHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					   <option value="11" <%=firstShiftToHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					   <option value="12" <%=firstShiftToHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
                      </select>

						<select class="form-control"   style="width: 50px" name="firstShiftToMinutes" id="firstShiftToMinutes" >
						<option value="<%=firstShiftToMinutes%>"><%=firstShiftToMinutes%></option>
						  <option value="00" <%=firstShiftToMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
					   <option value="30" <%=firstShiftToMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>

                    </select>  </td>
            </tr>
            
               <tr><td >SECOND SHIFT FROM  : </td>
                    <td>  <select class="form-control"   style="width: 50px" name="secondShiftFromHours" id="secondShiftFromHours" >
                       <option value="<%=secondShiftFromHours%>"><%=secondShiftFromHours%></option>
                       <option value="0" <%=secondShiftFromHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
			  		   <option value="1" <%=secondShiftFromHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option> 
			  		   <option value="2" <%=secondShiftFromHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
			  		   <option value="3" <%=secondShiftFromHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
			  		   <option value="4" <%=secondShiftFromHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
					   <option value="5" <%=secondShiftFromHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
					   <option value="6" <%=secondShiftFromHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
					   <option value="7" <%=secondShiftFromHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
					   <option value="8" <%=secondShiftFromHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
					   <option value="9" <%=secondShiftFromHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					   <option value="10" <%=secondShiftFromHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					   <option value="11" <%=secondShiftFromHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					   <option value="12" <%=secondShiftFromHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
                      </select>

						<select class="form-control"  style="width: 50px" name="secondShiftFromMinutes" id="secondShiftFromMinutes">
						<option value="<%=secondShiftFromMinutes%>"><%=secondShiftFromMinutes%></option>
						  <option value="00" <%=secondShiftFromMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
											  <option value="30" <%=secondShiftFromMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>
				</select>  </td>
            <td>SECOND SHIFT TO  : </td>
               <td> <select class="form-control"   style="width: 50px" name="secondShiftToHours" id="secondShiftToHours">
                 <option value="<%=secondShiftToHours%>"><%=secondShiftToHours%></option>
                 <option value="0" <%=secondShiftToHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
			  		  <option value="1" <%=secondShiftToHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option> 
			  		  <option value="2" <%=secondShiftToHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
			  		  <option value="3" <%=secondShiftToHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
			  		  <option value="4" <%=secondShiftToHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
					  <option value="5" <%=secondShiftToHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
					  <option value="6" <%=secondShiftToHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
					  <option value="7" <%=secondShiftToHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
					  <option value="8" <%=secondShiftToHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
					  <option value="9" <%=secondShiftToHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					  <option value="10" <%=secondShiftToHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					  <option value="11" <%=secondShiftToHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					  <option value="12" <%=secondShiftToHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
                  </select>

						<select class="form-control"   style="width: 50px" name="secondShiftToMinutes" id="secondShiftToMinutes">
						<option value="<%=secondShiftToMinutes%>"><%=secondShiftToMinutes%></option>
						 <option value="00" <%=secondShiftToMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
											  <option value="30" <%=secondShiftToMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>
						</select>   </td>
	 </tr>
            
               <tr><td>FIRST CASH FROM : </td>
                    <td>  <select class="form-control"   style="width: 50px" name="firstCashFromHours" id="firstCashFromHours">
                       <option value="<%=firstCashFromHours%>"><%=firstCashFromHours%></option>
                       <option value="0" <%=firstCashFromHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
                       <option value="1" <%=firstCashFromHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option>
                       <option value="2" <%=firstCashFromHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
                       <option value="3" <%=firstCashFromHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
                       <option value="4" <%=firstCashFromHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
                       <option value="5" <%=firstCashFromHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
                       <option value="6" <%=firstCashFromHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
                       <option value="7" <%=firstCashFromHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
                       <option value="8" <%=firstCashFromHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
                       <option value="9" <%=firstCashFromHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					   <option value="10" <%=firstCashFromHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					   <option value="11" <%=firstCashFromHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					   <option value="12" <%=firstCashFromHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
</select>

<select class="form-control"   style="width: 50px" name="firstCashFromMinutes" id="firstCashFromMinutes">
<option value="<%=firstCashFromMinutes%>"><%=firstCashFromMinutes%></option>
  <option value="00" <%=firstCashFromMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
					  <option value="30" <%=firstCashFromMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>

</select>  </td>
            <td>FIRST CASH TO :  </td>
                 <td> <select class="form-control"   style="width: 50px" name="firstCashToHours" id="firstCashToHours">
                   <option value="<%=firstCashToHours%>"><%=firstCashToHours%></option>
                    <option value="0"<%=firstCashToHours.equalsIgnoreCase("0")?"hidden":"" %> >0</option>
				   <option value="1" <%=firstCashToHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option>  
				   <option value="2" <%=firstCashToHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
				   <option value="3" <%=firstCashToHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
				   <option value="4" <%=firstCashToHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
				   <option value="5" <%=firstCashToHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
				   <option value="6" <%=firstCashToHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
				   <option value="7" <%=firstCashToHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
				   <option value="8" <%=firstCashToHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
				   <option value="9" <%=firstCashToHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
				   <option value="10" <%=firstCashToHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
				   <option value="11" <%=firstCashToHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
				   <option value="12" <%=firstCashToHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
</select>

<select class="form-control"   style="width: 50px" name="firstCashToMinutes" id="firstCashToMinutes">
<option value="<%=firstCashToMinutes%>"><%=firstCashToMinutes%></option>
 <option value="00"  <%=firstCashToMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option>
					<option value="30"  <%=firstCashToMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>

</select>   </td>
            </tr>
            
               <tr><td>SECOND CASH FROM : </td>
                     <td> <select class="form-control"   style="width: 50px" name="secondCashFromHours" id="secondCashFromHours">
                       <option value="<%=secondCashFromHours%>"><%=secondCashFromHours%></option>
                       <option value="0"  <%=secondCashFromHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
					<option value="1"  <%=secondCashFromHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option>
					<option value="2"  <%=secondCashFromHours.equalsIgnoreCase("2")?"hidden":"" %>>2</option>
					<option value="3"  <%=secondCashFromHours.equalsIgnoreCase("3")?"hidden":"" %>>3</option>
					<option value="4"  <%=secondCashFromHours.equalsIgnoreCase("4")?"hidden":"" %>>4</option>
					<option value="5"  <%=secondCashFromHours.equalsIgnoreCase("5")?"hidden":"" %>>5</option>
					<option value="6"  <%=secondCashFromHours.equalsIgnoreCase("6")?"hidden":"" %>>6</option>
					<option value="7"  <%=secondCashFromHours.equalsIgnoreCase("7")?"hidden":"" %>>7</option>
					<option value="8"  <%=secondCashFromHours.equalsIgnoreCase("8")?"hidden":"" %>>8</option>
					<option value="9"  <%=secondCashFromHours.equalsIgnoreCase("9")?"hidden":"" %>>9</option>
					<option value="10" <%=secondCashFromHours.equalsIgnoreCase("10")?"hidden":"" %>>10</option>
					<option value="11" <%=secondCashFromHours.equalsIgnoreCase("11")?"hidden":"" %>>11</option>
					<option value="12" <%=secondCashFromHours.equalsIgnoreCase("12")?"hidden":"" %>>12</option>
</select>

<select class="form-control"   style="width: 50px" name="secondCashFromMinutes" id="secondCashFromMinutes">
<option value="<%=secondCashFromMinutes%>"><%=secondCashFromMinutes%></option>
 <option value="00" <%=secondCashFromMinutes.equalsIgnoreCase("00")?"hidden":"" %>>00</option> 
			  <option value="30" <%=secondCashFromMinutes.equalsIgnoreCase("30")?"hidden":"" %>>30</option>

</select>  </td>
            <td>SECOND CASH TO :</td>
                 <td>  <select class="form-control"   style="width: 50px" name="secondCashToHours" id="secondCashToHours">
                    <option value="<%=secondCashToHours%>"><%=secondCashToHours%></option>
                    <option value="0" <%=secondCashToHours.equalsIgnoreCase("0")?"hidden":"" %>>0</option>
		    <option value="1" <%=secondCashToHours.equalsIgnoreCase("1")?"hidden":"" %>>1</option>
		    <option value="2"<%=secondCashToHours.equalsIgnoreCase("2")?"hidden":""%>>2</option>
		    <option value="3" <%=secondCashToHours.equalsIgnoreCase("3")?"hidden":""%>>3</option>
		    <option value="4" <%=secondCashToHours.equalsIgnoreCase("4")?"hidden":""%>>4</option>
			<option value="5" <%=secondCashToHours.equalsIgnoreCase("5")?"hidden":""%>>5</option>
			<option value="6" <%=secondCashToHours.equalsIgnoreCase("6")?"hidden":""%>>6</option>
			<option value="7" <%=secondCashToHours.equalsIgnoreCase("7")?"hidden":""%>>7</option>
			<option value="8" <%=secondCashToHours.equalsIgnoreCase("8")?"hidden":""%>>8</option>
			<option value="9" <%=secondCashToHours.equalsIgnoreCase("9")?"hidden":""%>>9</option>
			<option value="10" <%=secondCashToHours.equalsIgnoreCase("10")?"hidden":""%>>10</option>
			<option value="11" <%=secondCashToHours.equalsIgnoreCase("11")?"hidden":""%>>11</option>
			<option value="12" <%=secondCashToHours.equalsIgnoreCase("12")?"hidden":""%>>12</option>
		   </select>
</select>

<select class="form-control"   style="width: 50px" name="secondCashToMinutes" id="secondCashToMinutes">
<option value="<%=secondCashToMinutes%>"><%=secondCashToMinutes%></option>
 <option value="00" <%=secondCashToMinutes.equalsIgnoreCase("00")?"hidden":""%>>00</option>
			<option value="30" <%=secondCashToMinutes.equalsIgnoreCase("30")?"hidden":""%>>30</option>

</select>  </td>
            </tr>
            <%
            
          
          
            if(brnBean.getWoffday()!=null)
            { if(brnBean.getWoffday().length()>3){
             weekOffFirst=brnBean.getWoffday().substring(0,3);
            weekOffSecond=brnBean.getWoffday().substring(3,6);
            }
            else if(brnBean.getWoffday().length()==3){
            	weekOffFirst=brnBean.getWoffday().substring(0,3);
            }
            else{
            	
            }
           } 
            
           
            if(brnBean.getAltersat()!=null)
            {
            	if(brnBean.getAltersat().length()==5)
            
            {
            	saturdayFirst=brnBean.getAltersat().substring(0,1);
            	saturdaySecond=brnBean.getAltersat().substring(1,2);
            	saturdayThird=brnBean.getAltersat().substring(2,3);
            	saturdayFourth=brnBean.getAltersat().substring(3,4);
            	saturdayFifth=brnBean.getAltersat().substring(4,5);
            }
            if(brnBean.getAltersat().length()==4)
            {
            	saturdayFirst=brnBean.getAltersat().substring(0,1);
            	saturdaySecond=brnBean.getAltersat().substring(1,2);
            	saturdayThird=brnBean.getAltersat().substring(2,3);
            	saturdayFourth=brnBean.getAltersat().substring(3,4);
            	
            }
            if(brnBean.getAltersat().length()==3)
            {
            	saturdayFirst=brnBean.getAltersat().substring(0,1);
            	saturdaySecond=brnBean.getAltersat().substring(1,2);
            	saturdayThird=brnBean.getAltersat().substring(2,3);
            	
            }
            if(brnBean.getAltersat().length()==2)
            {
            	saturdayFirst=brnBean.getAltersat().substring(0,1);
            	saturdaySecond=brnBean.getAltersat().substring(1,2);
            	
            }
            if(brnBean.getAltersat().length()==1)
            {
            	saturdayFirst=brnBean.getAltersat().substring(0,1);
            	
            }
            if(brnBean.getAltersat().length()==1)
            {}
            
            } 
           
            
            %>
            
            
            
            <tr  style="height:30px;"><td>Select Week Off Day :<br /><span style="font-size:75%">(At max 2)</span></td><td colspan="3"><span style="font-size:75%"></span>
            <%
           
            if(weekOffFirst.equals("Sun") || (weekOffSecond.equals("Sun"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="sun" value="sun" onClick="return KeepCount()"/>Sun
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="sun" value="sun" onClick="return KeepCount()"/>Sun
							<% }%>
							
							 <%if(weekOffFirst.equals("mon") || (weekOffSecond.equals("mon"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="mon" value="mon" onClick="return KeepCount()"/>&nbsp;Mon&nbsp;&nbsp;
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="mon" value="mon" onClick="return KeepCount()"/>&nbsp;Mon&nbsp;&nbsp;
							<% }%>
							
							 <%if(weekOffFirst.equals("tue") || (weekOffSecond.equals("tue"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="tue" value="tue" onClick="return KeepCount()"/>&nbsp;Tue&nbsp;&nbsp;
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="tue" value="tue" onClick="return KeepCount()"/>&nbsp;Tue&nbsp;&nbsp;
							<% }%>
							
							 <%if(weekOffFirst.equals("wed") || (weekOffSecond.equals("wed"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="wed" value="wed" onClick="return KeepCount()"/>&nbsp;Wed&nbsp;&nbsp;
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="wed" value="wed" onClick="return KeepCount()"/>&nbsp;Wed&nbsp;&nbsp;
							<% }%>
							 <%if(weekOffFirst.equals("thu") || (weekOffSecond.equals("thu"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="thu" value="thu" onClick="return KeepCount()"/>&nbsp;Thu&nbsp;&nbsp;
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="thu" value="thu" onClick="return KeepCount()"/>&nbsp;Thu&nbsp;&nbsp;
							<% }%>
							 <%if(weekOffFirst.equals("fri") || (weekOffSecond.equals("fri"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="fri" value="fri" onClick="return KeepCount()"/>&nbsp;Fri&nbsp;&nbsp;
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="fri" value="fri" onClick="return KeepCount()"/>&nbsp;Fri&nbsp;&nbsp;
							<% }%>
							 <%if(weekOffFirst.equals("sat") || (weekOffSecond.equals("sat"))) { %>
							<input class="form-control"   type="checkbox" name="offday" checked="checked" id="sat" value="sat" onClick="return KeepCount()"/>&nbsp;Sat
							<%} else {%>
							<input class="form-control"   type="checkbox" name="offday" id="sat" value="sat" onclick="checksat('rowSubOrders',this)"/>&nbsp;Sat
							<% }%>
						</td>
						</tr>
					
						
				<tr id="rowSubOrders"> <td  >Alternate Saturday ? : </td>
							
					<td colspan="3">	<%if(saturdayFirst.equals("1") ){%>
							<input class="form-control"   type="checkbox" name="altsatday" checked="checked" id="one" value="1" />&nbsp;1 &nbsp;&nbsp;&nbsp;
										<%}else {%>
                        <input class="form-control"   type="checkbox" name="altsatday" id="one" value="1" />&nbsp;1 &nbsp;&nbsp;&nbsp;
                        <%} %>
                        
                        	<%if(saturdayFirst.equals("2")|| saturdaySecond.equals("2")){%>
							<input class="form-control"   type="checkbox" name="altsatday" checked="checked" id="two" value="2" />&nbsp;2 &nbsp;&nbsp;&nbsp;
										<%}else {%>
                        <input class="form-control"   type="checkbox" name="altsatday" id="two" value="2" />&nbsp;2 &nbsp;&nbsp;&nbsp;
                        <%} %>
                        
                        
                        	<%if(saturdayFirst.equals("3")|| saturdaySecond.equals("3")|| saturdayThird.equals("3")){%>
							<input class="form-control"   type="checkbox" name="altsatday" checked="checked" id="three" value="3" />&nbsp;3 &nbsp;&nbsp;&nbsp;
										<%}else {%>
                        <input class="form-control"   type="checkbox" name="altsatday" id="three" value="3" />&nbsp;3 &nbsp;&nbsp;&nbsp;
                        <%} %>
                        
                        	<%if(saturdayFirst.equals("4")|| saturdaySecond.equals("4")|| saturdayThird.equals("4")||saturdayFourth.equals("4")){%>
							<input class="form-control"   type="checkbox" name="altsatday" checked="checked" id="four" value="4" />&nbsp;4 &nbsp;&nbsp;&nbsp;
										<%}else {%>
                        <input class="form-control"   type="checkbox" name="altsatday" id="four" value="4" />&nbsp;4 &nbsp;&nbsp;&nbsp;
                        <%} %>
                        
                        	<%if(saturdayFirst.equals("5")|| saturdaySecond.equals("5")|| saturdayThird.equals("5")||saturdayFourth.equals("5")|| saturdayFifth.equals("5")){%>
							<input class="form-control"   type="checkbox" name="altsatday" checked="checked" id="five" value="5" />&nbsp;5 &nbsp;&nbsp;&nbsp;
										<%}else {%>
                        <input class="form-control"   type="checkbox" name="altsatday" id="five" value="5" />&nbsp;5 &nbsp;&nbsp;&nbsp;
                        <%} %>
						<br></td>	
						
			</tr>
			
			
            
            	<tr>
			
                      <td>BRANCH STATUS :</td>
                      <td colspan="3"><select class="form-control"   style="width: 140px" name="siteStatus" id="siteStatus">
                      <%
                    
                      int siteStatusValue=0;
                      
                      if(brnBean.getIsDeleted()==0){
                    	  siteStatusValue=1;
                      }
                      else{
                    	  siteStatusValue=0;
                      }
                  
                      %>
                      <% if(siteStatusValue==1) {%>
                      <option value="0" selected="selected">Active</option>
                      
                      <option value="1">Non-Active</option>  
                      <%} else{ %>
                      <option value="1"  selected="selected">Non-Active</option>
                      <option value="0">Active</option>
                      <% }%>  
                  </select>
     
			</tr>
            <tr><td colspan="4" align="center"><input class="myButton"   type="submit" value="Save" > <input class="myButton"   type="reset" value="Clear"></td></tr>
            <%
            EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
            String todaysDate=empAttendanceHandler.getServerDate();
            int loggedEmployee = (Integer)session.getAttribute("EMPNO");
            %>
            <input name="todaysDate" id="todaysDate" type="hidden" value="<%=todaysDate %>" />
             <input name="loggedEmployee" id="loggedEmployee" type="hidden" value="<%=loggedEmployee%>" />
			</table>
		
			

</form><br/>

</div>
<%
			}}
	%>
		<a href="ShowBranches.jsp?action=showBranchList" style="font-size: 20px;">Click here to see information of all branches</a>

<%--<div style=" height:130px;"  >
<form>
 <h3>Last Added Branch</h3>
	<table width="100%" id="customers" "  >
				 
			<tr><th width="8%" >Branch Code</th>
			<th width="10%">Branch Name</th>
			<th width="16%">Addr Line1</th>
			<th width="14%">Addr Line2</th>
			<th width="12%">Addr Line3</th>
			<th width="4%">City</th>
			<th width="4%">State</th>
			
			<th width="8%">Modify</th>
			
			</tr>
			<% 
			
			
				
				 ArrayList<BranchBean> brnList = brnDAO.getBranchDetails();
				 LookupHandler lh = new LookupHandler(); 
			
			
			if((brnList.size()!=0))
			{
					for(BranchBean brnBean : brnList)
			{
				
				%>
		<tr >
			<td style="text-align: center;"><%=brnBean.getBranchCode() %></td>
			<td style="text-align: center;"><%=brnBean.getBranchName() %></td>
			<td style="text-align: center;"><%=brnBean.getAddress1() %></td>
			<td style="text-align: center;"><%=brnBean.getAddress2() %></td>
			<td style="text-align: center;"><%=brnBean.getAddress3() %></td>
			<td style="text-align: center;"><%=lh.getLKP_Desc("CITY", brnBean.getCity())%></td>
			<td style="text-align: center;"><%=lh.getLKP_Desc("STATE", brnBean.getState())%></td>
			<td style="text-align: center;"><input type="button" value=" Edit " onClick="window.location='Branch.jsp?action=editBranch&brncd=<%=brnBean.getBranchCode() %>'"></td>
			<%} %>
		</tr>
	<%
	
			}	
			else
			{
				
				%>
				<tr><td height="30" colspan="9">There is No Information</td></tr>
				<%
			}
			%>
	<!-- 	<tr bgcolor="#85A02F"><td align="right" colspan="9" height="20px"></td></tr> -->
		
		<%
			
		%></table> 
		<br>
	</form>


</div> --%>
</div>
		<center>	   
			 <input type="hidden" name="flag1" id="flag1" value="<%=flag1 %>">
			<!--  end table-content  -->
	
	    </div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right">
		   </td>
		 </tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>
	<div class="clear">&nbsp;</div>
	<div class="clear"></div>
		    
	

</div>

</div>

</body>
</html>