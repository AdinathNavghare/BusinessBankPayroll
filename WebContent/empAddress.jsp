<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="org.apache.commons.collections.iterators.ArrayListIterator"%>
<%@page import="payroll.Model.EmpAddressBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.*"%>
<%@page import="payroll.DAO.EmpAddrHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Employee</title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
	
	<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
	<script src="js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
	
	
	<script type="text/javascript">
		function Showhide()
			{

				document.getElementById("editAddress").hidden=true;
			//document.getElementById("addempQual").hidden=true;
			}
		function editAddressValidation()
		{
			
			/* if( document.getElementById("empNo").value=="" || 
				document.getElementById("empName").value=="" || 
				document.getElementById("eLine1").value=="" || 
				document.getElementById("eLine2").value=="" || 
				document.getElementById("eLine3").value=="" ||
				document.getElementById("ePincode").value=="" ||
				document.getElementById("eContact").value==""
				
				)	   
				{
					alert("Please Enter All Data");
					return false;
				} */
			 /* if(document.getElementById("eCity").selectedIndex == 0)
			   {
			   alert("Please Select City");  
			   document.getElementById("eCity").focus();
		        return false;
			   }

		   if(document.getElementById("eState").selectedIndex == 0)
			   {
			   alert("Please Select State");  
			   document.getElementById("eState").focus();
		        return false;
			   } */
			   if(document.getElementById("eLine3").value=="" ||document.getElementById("eLine3").value==null){
			   }
			   else{
			  var emailID = document.getElementById("eLine3").value;
			  var pattern= /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		   		var result = emailID.match(pattern);   
		   		if(!result)
		   			{
		   			alert("invalid format of mail Id");
		   			return false;
		   			}
		   		}
			   if(document.getElementById("ePincode").value==""){
			  	alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			   return false;}
			if(document.getElementById("ePincode").value==0){}
			else{var pin = document.getElementById("ePincode").value;
			pinlen = pin.length;
			if(isNaN(pin)|| pinlen !=6)
			{
			alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			document.getElementById("ePincode").focus();
			return false; 
			}}
			
			
			if(document.getElementById("eContact").value==""){
				 alert("Please insert Correct Contact number1  or 0");  
				 document.getElementById("eContact").focus();
			        return false;
			}
			if(document.getElementById("eContact").value==0){}
			else{
			var phoneno = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/; 
			var cntct = document.getElementById("eContact").value;
			
		 if((cntct.match(phoneno))) 
			 {  
			     return true;  
			    }  
		  else {  
			        alert("Please insert Correct Contact number or 0");  
			        document.getElementById("eContact").focus();
			        return false;  
			     }} 
			
		   
				
		}
		 function addAddressValidation()
		{
			 
			 
		
				
			 
			 
			  if(document.getElementById("prLine3").value=="" ||document.getElementById("prLine3").value==null){
			   }
			   else{
			 
				   var emailID = document.getElementById("prLine3").value;
			  
				   var pattern= /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			   		var result = emailID.match(pattern);   
			   		if(!result)
			   			{
			   			alert("invalid format of mail Id");
			   			return false;
			   			}
			   		}
			  
				 if(document.getElementById("prstate").selectedIndex == 0)
					{
						alert("Please Select State of Present Address");	
						document.getElementById("prstate").focus;
						return false;
					}
					
				 if(document.getElementById("prCity").selectedIndex == 0)
					{
						alert("Please Select City of Present Address");	
						document.getElementById("prCity").focus;
						return false;
					}
				 
			   if(document.getElementById("prPincode").value==""){
			  	alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			  	document.getElementById("prPincode").focus();
			   return false;}
			    if(document.getElementById("prPincode").value=="0")
			    {}
			
			    else{var pin = document.getElementById("prPincode").value;
			    pinlen = pin.length;
			   if(isNaN(pin)|| pinlen !=6)
			   {
			    alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			   document.getElementById("prPincode").focus();
			   return false; 
			   }}
			
			
			if(document.getElementById("prContact").value==""){
				 alert("Please insert Correct Contact number or 0");  
				 document.getElementById("prContact").focus();
			        return false;
			}
			
			if(document.getElementById("prContact").value==0){
				
			}
			else{
			          var phoneno = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/; 
		              var cntct = document.getElementById("prContact").value;
			
		              if((cntct.match(phoneno))) 
			          {  
			            
			          }  
		             else {  
			              alert("Please insert Correct Contact number or 0");  
			              document.getElementById("prContact").focus();
			               return false;  
			              }
		          } 
			
			if(document.getElementById("parstate").selectedIndex == 0)
			{
				alert("Please Select State of Parmanent Address");	
				document.getElementById("parstate").focus;
				return false;
			}
			if(document.getElementById("parCity").selectedIndex == 0)
			{
				alert("Please Select City of Parmanent Address");	
				document.getElementById("parCity").focus;
				return false;
			}
			
			if(document.getElementById("parLine3").value=="" ||document.getElementById("parLine3").value==null){
			   }
			   else{
			  var emailID = document.getElementById("parLine3").value;
			  
			  var pattern= /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		   		var result = emailID.match(pattern);   
		   		if(!result)
		   			{
		   			alert("invalid format of mail Id");
		   			return false;
		   			}
		   		}
			   if(document.getElementById("parPincode").value==""){
			  	alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			  	document.getElementById("parPincode").focus();
			   return false;}
			if(document.getElementById("parPincode").value==0){}
			else{var pin = document.getElementById("parPincode").value;
			pinlen = pin.length;
			if(isNaN(pin)|| pinlen !=6)
			{
			alert("Pincode Number Should be In Numeric and 6 Digit or 0");
			document.getElementById("parPincode").focus();
			return false; 
			}}
			
			
			if(document.getElementById("parContact").value==""){
				 alert("Please insert Correct Contact number or 0"); 
				 document.getElementById("parContact").focus();
			        return false;
			}
			if(document.getElementById("parContact").value==0){}
			else{
			var phoneno = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/; 
			var cntct = document.getElementById("parContact").value;
			document.getElementById("parContact").focus();
			
			 if((cntct.match(phoneno))) 
			 {  
			     
			    }  
		  else {  
			        alert("Please insert Correct Contact number or 0");
			        document.getElementById("parContact").focus();
			        return false;  
			     }}  
			 /* 
					 if( document.getElementById("aempNo").value=="" || 
					document.getElementById("aempName").value=="" || 
					document.getElementById("prLine1").value=="" || 
					document.getElementById("prLine2").value=="" || 
					document.getElementById("prLine3").value=="" || 
					document.getElementById("prPincode").value=="" ||
					document.getElementById("prContact").value=="" ||
					
					document.getElementById("parLine1").value=="" || 
					document.getElementById("parLine2").value=="" || 
					document.getElementById("parLine3").value=="" || 
					document.getElementById("parPincode").value=="" ||
					document.getElementById("parContact").value=="" 
					 )	    
					{
						alert("Please Enter All Data");
						return false;
				
					}
					
					
					var phoneno = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/; 
					var cntct = document.getElementById("parContact").value;
					var cntct2 = document.getElementById("prContact").value; 
					  if(!cntct.match(phoneno) || !cntct2.match(phoneno) ) 
						  
					        {  
						  alert("Please insert Correct Contact number"); 
						  document.getElementById("prContact").focus();
					        return false;  
					        }  
					       

					  if((isNaN(document.getElementById("parPincode").value)) || (isNaN(document.getElementById("prPincode").value)))
						{
						alert("Pincode Number Should be In Numeric");
						document.getElementById("parPincode").focus();
						return false;  
						}  */
						
			
			
			} 	
		
	var checked=false;
		function Address()
			{
				if (checked == false)
					{
						checked = true;
						$('#prCity option').clone().appendTo('#parCity');
						var state = document.getElementById("prstate").selectedIndex;
						document.getElementById("parstate").selectedIndex = state;
						var city = document.getElementById("prCity").selectedIndex;
						document.getElementById("parCity").selectedIndex = city;
						document.getElementById("parLine1").value = document.getElementById("prLine1").value;
						document.getElementById("parLine2").value = document.getElementById("prLine2").value;
						document.getElementById("parLine3").value = document.getElementById("prLine3").value;
						document.getElementById("parPincode").value = document.getElementById("prPincode").value;
						document.getElementById("parContact").value = document.getElementById("prContact").value;
    			}
			else
				{
						checked = false;
						document.getElementById("parLine1").value = "";
						document.getElementById("parLine2").value = "";
						document.getElementById("parLine3").value = "";
						document.getElementById("parCity").options.length = 0;;
						document.getElementById("parstate").selectedIndex = 0;
						document.getElementById("parPincode").value = "";
						document.getElementById("parContact").value = "";
			}
		}
	</script>
	<% 
		String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp"; 
	%>
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

        function showPrCity(str)
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
      		document.getElementById("prCity").innerHTML=xmlHttp.responseText ;
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
      

      function showeCity(str)
      {
		
  	     var url="city.jsp";
    	      url +="?count=" +str;
    	      xmlHttp.onreadystatechange = stateChangeEdit;
    	      xmlHttp.open("GET", url, true);
    	      xmlHttp.send(null);
    	   }

      function stateChangeEdit()
      { 
        	      
    	     if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete"){   
    	      document.getElementById("eCity").innerHTML=xmlHttp.responseText ;
    	      }   
      }
      
      function Showhide()
      {
	  	
		document.getElementById("editAddress").hidden=true;
    	 document.getElementById("exitEmpAdde").hidden=true;
		 
		 
          }
      
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
     
  </script>  
</head>
<body style="overflow: hidden;"> 
<% LookupHandler lh = new LookupHandler(); %>
		<%@include file="mainHeader.jsp" %>
		<!--  start nav-outer-repeat................................................................................................. START -->
		<%@include file="subHeader.jsp" %>
 
		<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
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
			<div class="step-light-left"><a href="empQual.jsp">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">3</div>
			<div class="step-dark-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="empFamily.jsp">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
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
			<div class="step-no">3</div>
			<div class="step-dark-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience</a></div>
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
	<div id="page-heading">
		<h1>Employee Address Information</h1>
		<!-- end page-heading -->
		</div>
	<%
		if(action.equalsIgnoreCase("showemp"))
		{
			String srNo = request.getParameter("srno")!=null?request.getParameter("srno"):"zero";
		String addrtype=request.getParameter("addrtype")!=null?request.getParameter("addrtype"):"0";
		String edit=request.getParameter("edit")!=null?request.getParameter("edit"):"0";
		ArrayList<EmpAddressBean> empaddrList = (ArrayList<EmpAddressBean>)(session.getAttribute("empaddrList")==null?"":session.getAttribute("empaddrList"));
	%>

		<table border ="0" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<th rowspan="3" class="sized"><img
			src="images/shared/side_shadowleft.jpg" width="20" height="200"
			alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="5" class="sized"><img
			src="images/shared/side_shadowright.jpg" width="20" height="200"
			alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>

 <form>
	<table id="customers" width="100%">
				<tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input type="text" name="empName" id="empName" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr>
			</table>
			<table  id="customers" width="100%">
			<tr><th width="5%" >Type</th>
			<th width="15%">Line1</th>
			<th width="15%">Line2</th>
			<th width="15%">Email Id</th>
			<th width="10%">City</th>
			<th width="10%">State</th>
			<th width="5%">Pincode</th>
			<th width="10%">Contact</th>
			<th width="10%"> Edit </th>
			</tr>
			<% 
			if((empaddrList.size()!=0))
			{
					for(EmpAddressBean empaddrbean : empaddrList)
			{
				
				
					
				%>
		<tr align="center">
			<td width="5%" ><%=empaddrbean.getADDRTYPE() %></td>
			<td width="15%"  align="left"><%=empaddrbean.getADDR1() %></td>
			<td width="15%"  align="left"><%=empaddrbean.getADDR2() %></td>
			<td width="15%" ><%=empaddrbean.getADDR3() %></td>
			<td width="10%" ><%=lh.getLKP_Desc("CITY", empaddrbean.getCITY()) %></td>
			<td width="10%" ><%=lh.getLKP_Desc("STATE",empaddrbean.getSTATE()) %></td>
			<td width="5%" ><%=empaddrbean.getPIN() %></td>
			<td width="10%" ><%=empaddrbean.getTELNO() %></td>
			<td width="10%" ><input type="button" value=" Edit " id="<%=empaddrbean.getID()  %>" class="myButton" onClick="window.location='empAddress.jsp?action=showemp&edit=edit&addrtype=<%=empaddrbean.getADDRTYPE() %>&empno=<%=empaddrbean.getEMPNO()%>&addID=<%=empaddrbean.getID()%>' "></td>
		</tr>
	<%
	
			}
			}
			else
			{
				
				%>
				<tr><td height="30" colspan="9">There is No Information</td></tr>
				<%
			}
			%>
		<tr bgcolor="#1F5FA7"><td align="right" colspan="9" height="20px"></td></tr>
		
		<%
			
		%></table>
	</form>
	
	<% 
	
	
	
	/* for(EmpAddressBean empaddrbean : empaddrList)
			{ */
				
				/* if(addrtype.equalsIgnoreCase(empaddrbean.getADDRTYPE())& edit.equalsIgnoreCase("edit"))
				{ */
					if(edit.equalsIgnoreCase("edit"))
				{
						EmpAddrHandler eah = new EmpAddrHandler();
						String ID=request.getParameter("addID");
						String EMPNO=request.getParameter("empno");
						EmpAddressBean empaddrbean = eah.getEmpAddBYID(EMPNO,ID);
				
					LookupHandler lkhp = new LookupHandler();
		%>
<!-- <form action="EmployeeServlet?action=editempAddress" method="post" name="editaddreform" id="editaddreform" onSubmit="return editAddressValidation()" > -->
<form action="EmployeeServlet?action=editempAddress" method="post" name="editaddreform" id="editaddreform" onSubmit="return editAddressValidation()" >
	<div id="editAddress" >
	<br>
	<h2>Edit The Address Information of <%=session.getAttribute("empname") %>  </h2>
	 <table style="display: none;" id="customers" width="100%" >
			<tr class="alt" >
        <th width="100%">Edit Address </th>  
           	 
            </tr>
		
		<input type="hidden" id="addressID" name = "addressID" value="<%=ID %>"/>
	 <tr><td width="15%" style="background-color: #F5F6FA;"  ><font color="black">Employee Code</font></td>
  		  			<td width="20%" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
					<td width="15%"  style="background-color: #F5F6FA;"  ><font color="black">Employee Name</font></td>
		  			<td width="50%" ><input class="form-control" type="text" name="empName" id="empName" size="40" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td>
		  		</tr> 
		</table>
	
	
	<table  id="customers" width="100%" align="center">
            
            <tr hidden="true" ><td><input class="form-control" type="text" id="addrtype" name="addrtype" value="<%=empaddrbean.getADDRTYPE() %>"></td></tr>
            <tr class="alt">
              <td width="10%" >Line 1</td>
              <td width="90%" colspan="3"><input class="form-control" size=50;  name="eLine1" type="text" id="eLine1" maxlength="50" value="<%=empaddrbean.getADDR1() %>" ></td>
              <td style="display: none;"></td>
              
            </tr>
            <tr class="alt">
              <td width="10%" >Line 2</td>
              <td><input class="form-control" name="eLine2" type="text" id="eLine2" value="<%=empaddrbean.getADDR2() %>" maxlength="50"></td>
              <td>Email Id</td>
              <td><input class="form-control" name="eLine3" type="text" id="eLine3" value="<%=empaddrbean.getADDR3() %>" maxlength="50"></td>
            </tr>
            <tr class="alt">
             
              <td width="83">State</td>
              <td width="204">
              <select class="form-control" name="eState" id="eState" onchange="showeCity(this.value)">  
      					 <option value="0">Select</option>  
    						<%
    						 ArrayList<Lookup> result=new ArrayList<Lookup>();
    						
    						result=lkhp.getSubLKP_DESC("STATE");
    						for(Lookup lkbean : result)
 							{
 									
    						
     						if(lkbean.getLKP_SRNO()== empaddrbean.getSTATE())
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
            <td>City</td>
              <td>
              <select class="form-control" name="eCity" id="eCity" >  
      					 <option value="0">Select</option>  
    						<%
    						ArrayList<Lookup> result2=new ArrayList<Lookup>();
    						result2=lkhp.getSubLKP_DESC("CITY");
    						for(Lookup lkbean : result2)
 							{
 							if(lkbean.getLKP_SRNO()== empaddrbean.getCITY())
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
              <td>Pincode</td>
              <td><input class="form-control" name="ePincode" type="text" id="ePincode" value="<%=empaddrbean.getPIN() %>" maxlength="6"  onkeypress="return inputLimiter(event,'Numbers')"></td>
              <td>Contact</td><td><input class="form-control" name="eContact" type="text" id="eContact" value="<%=empaddrbean.getTELNO() %>" maxlength="11"></td>
            </tr>
            <tr><td colspan="4" align="center"><input type="submit" value="Update"  class="myButton"/> &nbsp; &nbsp;
		<input type="button" value="Cancel"  class="myButton" onClick="Showhide()"/></td></tr>
          </table>
	</div>
	</form>	
	<%  
		}
				
			/* } */
	
	
	if(empaddrList.size()==0)
	{
		LookupHandler lkhp = new LookupHandler();
		%><br>
		<form action="EmployeeServlet?action=addAddress" method="post">
<table width="1033" id="customers" >
		   <tr class="alt" ><td width="508" >Employee Code:-  &nbsp;&nbsp;<%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %>  &nbsp; <input type="hidden" name="aempNo" id="aempNo" size="10"  readonly="readonly" value="<%=session.getAttribute("empno")==null?"":session.getAttribute("empno") %>">
			 &nbsp;&nbsp; Employee Name &nbsp;&nbsp;
		    <input type="text" name="aempName" id="aempName" size="30" readonly="readonly" value="<%=session.getAttribute("empname")==null?"":session.getAttribute("empname") %>" >
		  </td>
	  	 <td width="513"><input type="checkbox"  onChange="Address()">&nbsp;&nbsp;Click Here if Same Present Address emplist in null<input type="hidden" id="addrlist" name="addrlist" value="zero"></td>
		</tr>
		<tr>
		  <td height="46">
		  	<table width="494" id="customers">
            	<tr>
              		<th colspan="4">Present Address</th>
            	</tr>
            	<tr class="alt">
              		<td width="99">Line 1</td>
              		<td width="158" colspan="3"><input class="form-control" name="prLine1" type="text" id="prLine1" maxlength="50"></td>
            	</tr>
            		<tr class="alt">
              		<td>Line 2</td>
             		 <td><input class="form-control" name="prLine2" type="text" id="prLine2" maxlength="50"></td>
              		<td>Email Id</td>
              		<td><input class="form-control" name="prLine3" type="text" id="prLine3" maxlength="50"></td>
            	</tr>
            <tr  class="alt">
            
            <td width="66">State</td>
              <td width="173">
               <select class="form-control" name="prstate" id="prstate" onChange="showPrCity(this.value)">  
       <option value="0">Select</option>  
    <%
    ArrayList<Lookup> result=new ArrayList<Lookup>();
    
    result=lkhp.getSubLKP_DESC("STATE");
 for(Lookup lkbean : result){
     %>
      <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
      <%
 }
     %>
      </select></td>
      
              <td>City</td>
              <td>
              <select class="form-control" name="prCity" id="prCity" style="width: 150px;">  
      <option value='-1'>Select</option>  
      </select>  
              </td>
              
            </tr>
            <tr class="alt">
              <td>Pincode</td>
              <td><input class="form-control"  name="prPincode" type="text" id="prPincode" maxlength="6"></td>
              <td>Contact</td>
              <td><input name="prContact" type="text" id="prContact" maxlength="11"></td>
            </tr>
          </table></td>
			<td height="46">
			<table width="499" id="customers">
				<tr>
				  <th colspan="4">Permanent Address</th>
				</tr>
				<tr class="alt"><td width="84">Line 1</td><td width="158" colspan="3"><input class="form-control" name="parLine1" type="text" id="parLine1" maxlength="50"></td></tr>
				<tr class="alt"><td>Line 2</td><td><input class="form-control" name="parLine2" type="text" id="parLine2" maxlength="50"></td><td>Email Id</td><td><input name="parLine3" type="text" id="parLine3" maxlength="50"></td></tr>
				<tr class="alt">
				<td>State</td><td>
				         <select name="parstate" id="parstate" onChange="showParCity(this.value)">  
       					<option value='-1'>Select</option>  
   						 <%
   							 ArrayList<Lookup> result1=new ArrayList<Lookup>();
   							 result1=lkhp.getSubLKP_DESC("STATE");
 							for(Lookup lkbean : result){
     					%>
      				<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
 							}
    				 	%>
      				</select></td>
				<td>City</td>
               <td  width="227">
               <select name="parCity" id="parCity" style="width: 150px;"></select>  
              </td>
			</tr>
				<tr class="alt"><td>Pincode</td><td><input class="form-control" name="parPincode" type="text" id="parPincode" maxlength="6" onkeypress="return inputLimiter(event,'Numbers')"></td>
				<td>Contact</td><td><input class="form-control" name="parContact" type="text" id="parContact" maxlength="11"  onkeypress="return inputLimiter(event,'Numbers')"></td></tr>
			</table></td>
		</tr>
    <tr><td colspan="10" align="center" bgcolor="#E3E3E3"><input type="submit" value="Submit"  > &nbsp; &nbsp;<input type="button" value="Cancel"/></td></tr>
	</table>
	
<div class="clear" >&nbsp;</div>
</form>
		
		<%
	}
	
}
else if(action.equalsIgnoreCase("addemp"))

{
	ArrayList<EmpAddressBean> empaddrList1 = session.getAttribute("empaddrList")==null?new ArrayList<EmpAddressBean>():(ArrayList<EmpAddressBean>)session.getAttribute("empaddrList");

LookupHandler lkhp= new LookupHandler();

%>	
<form action="EmployeeServlet?action=addAddress" method="post" onsubmit="return addAddressValidation()">
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
	<%
	if(empaddrList1.size()==0)
{
	%>
<form action="EmployeeServlet?action=addAddress" method="post" onSubmit="return addAddressValidation()">
<table width="1033" id="customers" >
		  <tr class="alt" ><td width="508" >Employee No &nbsp;&nbsp;&nbsp; <input type="text" name="aempNo" id="aempNo" size="10"  readonly="readonly" value="<%=session.getAttribute("empno")==null?"":session.getAttribute("empno") %>">
			 &nbsp;&nbsp; Employee Name &nbsp;&nbsp;
		    <input type="text" name="aempName" id="aempName" size="30" readonly="readonly" value="<%=session.getAttribute("empname")==null?"":session.getAttribute("empname") %>" >
		  </td>
	  	 <td width="513"><input type="checkbox"  onChange="Address()">&nbsp;&nbsp;Click Here if Same As Present Address</td>
		</tr>
		<tr>
		  <td height="46">
		  	<table width="494" id="customers">
            	<tr>
              		<th colspan="4">Present Address</th>
            	</tr>
            	<tr class="alt">
              		<td width="99">Line 1</td>
              		<td width="158" colspan="3"><input class="form-control" name="prLine1" type="text" id="prLine1" maxlength="50"></td>
            	</tr>
            		<tr class="alt">
              		<td>Line 2</td>
             		 <td><input class="form-control" name="prLine2" type="text" id="prLine2" maxlength="50"></td>
              		<td>Email Id</td>
              		<td><input class="form-control" name="prLine3" type="text" id="prLine3" maxlength="50"></td>
            	</tr>
            <tr  class="alt">
            
            <td width="66">State</td>
              <td width="173">
               <select class="form-control" name="prstate" id="prstate" onChange="showPrCity(this.value)">  
       <option value="0">Select</option>  
    <%
    ArrayList<Lookup> result=new ArrayList<Lookup>();
    
    result=lkhp.getSubLKP_DESC("STATE");
 for(Lookup lkbean : result){
     %>
      <option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
      <%
 }
     %>
      </select></td>
      
              <td>City</td>
              <td>
              <select class="form-control" name="prCity" id="prCity" style="width: 150px;">  
      <option value='-1'>Select</option>  
      </select>  
              </td>
              
            </tr>
            <tr class="alt">
              <td>Pincode</td>
              <td><input class="form-control" name="prPincode" type="text" id="prPincode" maxlength="6" onkeypress="return inputLimiter(event,'Numbers')"></td>
              <td>Contact</td>
              <td><input class="form-control" name="prContact" type="text" id="prContact" maxlength="11"  onkeypress="return inputLimiter(event,'Numbers')"></td>
              
            </tr>
          </table></td>
			<td height="46">
			<table width="499" id="customers">
				<tr>
				  <th colspan="4">Permanent Address</th>
				</tr>
				<tr class="alt"><td width="84">Line 1</td><td width="158" colspan="3"><input class="form-control" name="parLine1" type="text" id="parLine1" maxlength="50"></td></tr>
				<tr class="alt"><td>Line 2</td><td><input class="form-control" name="parLine2" type="text" id="parLine2" maxlength="50"></td><td>Email Id</td><td><input class="form-control" name="parLine3" type="text" id="parLine3" maxlength="50"></td></tr>
				<tr class="alt">
				<td>State</td><td>
				         <select name="parstate" id="parstate" onChange="showParCity(this.value)">  
       					<option value='-1'>Select</option>  
   						 <%
   							 ArrayList<Lookup> result1=new ArrayList<Lookup>();
   							 result1=lkhp.getSubLKP_DESC("STATE");
 							for(Lookup lkbean : result){
     					%>
      				<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     					 <%
 							}
    				 	%>
      				</select></td>
				<td>City</td>
               <td  width="227">
               <select name="parCity" id="parCity" style="width: 150px;"></select>  
              </td>
			</tr>
				<tr class="alt"><td>Pincode</td><td><input class="form-control" name="parPincode" type="text" id="parPincode" maxlength="6" onkeypress="return inputLimiter(event,'Numbers')"></td><td>Contact</td><td><input class="form-control" name="parContact" type="text" id="parContact" maxlength="11"></td></tr>
			</table></td>
		</tr>
    <tr><td colspan="10" align="center" bgcolor="#E3E3E3"><input type="submit" class="myButton"  value="Submit" > &nbsp; &nbsp;<input type="reset" class="myButton"  value="Cancel"/></td></tr>
	</table>
	
<div class="clear" >&nbsp;</div>
</form><br>


		<%
		}
		if(empaddrList1.size()!=0)
{
%>
<form>
	<table width="1848" id="customers">
				 <tr><td width="261">Employee No</td>
	  		<td width="264"> <%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empno") %>"></td>
				<td width="240">Employee Name</td>
			<td width="240"><input type="text" name="empNo" id="empNo" readonly="readonly" value="<%=session.getAttribute("empname") %>"></td></tr>
			<tr><th >Type</th><th width="264">Line1</th>
			<th width="240">Line2</th>
			<th>Email Id</th><th width="233">City</th>
			<th width="45">State</th>
			<th width="224">Pincode</th>
			<th width="242">Contact</th>
			
			</tr>
			<% 
			
			for(EmpAddressBean empaddrbean : empaddrList1)
			{
				
				%>
		<tr >
			<td><%=empaddrbean.getADDRTYPE() %></td>
			<td><%=empaddrbean.getADDR1() %></td>
			<td><%=empaddrbean.getADDR2() %></td>
			<td><%=empaddrbean.getADDR3() %></td>
			<td><%=lh.getLKP_Desc("CITY", empaddrbean.getCITY())  %></td>
			<td><%=lh.getLKP_Desc("STATE", empaddrbean.getSTATE())%></td>
			<td><%=empaddrbean.getPIN() %></td>
			<td><%=empaddrbean.getTELNO() %></td>
			
		</tr>
	<%
	
			}
			
			
			%>

		<tr bgcolor="#85A02F"><td align="right" colspan="9" height="20px"></td></tr>
		
		<%
			
		%></table>
	</form><br>

<%
}

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

</div>
<!--  end content -->
<div class="clear" >&nbsp;</div>
</div>


</div>
<!--  end content-outer........................................................END -->
</div>
<div class="clear">&nbsp;</div>
 



    
</body>
</html>