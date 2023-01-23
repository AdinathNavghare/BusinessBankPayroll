<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.awt.Desktop.Action"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Increment @ DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">

<script type="text/javascript">
	$(function () {
	    $(document).keydown(function (e) {
	        return (e.which || e.keyCode) != 116;
	    });
	});
	
	
	/* jQuery(function() {
		
		var checkboxes = $("input[type='checkbox']"),
	    submitButt = $("input[type='submit']");

	checkboxes.click(function() {
	    submitButt.attr("disabled", !checkboxes.is(":checked"));
	}); */
	
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
	
	function calculate(cStage,nStage,empno){
		
		var grade = document.getElementById("grade").value;
		var cStage = document.getElementById(cStage).value;
		var nStage = document.getElementById(nStage).value;
	
		if(grade =="" && cStage=="" && nStage==""){
			alert("Values can't be blank !");
			return false;
		}
		if(cStage==""){
			alert("Number of increment can not blank !");
			return false;
		}
		if(nStage==""){
		//	alert("Number of increment can not blank !");
			return false;
		}
		
		grade =	parseInt(grade);
		cStage =	parseInt(cStage);
		nStage =	parseInt(nStage);
		var total = cStage+nStage;
		
		//alert("total "+total);
		if(grade == 1 || grade == 2 || grade ==10){
			if(total >20){
				alert("Number of Increment + current satge should not be greater than 20");
			}
			return false;
		}
		if(grade == 11){
			if(total >21){
				alert("Number of Increment + current satge should not be greater than 21");
			}
			return false;
		}
		if(grade == 3 || grade == 4){
			if(total >31){
				alert("Number of Increment + current satge should not be greater than 31");
			}
			return false;
		}
		if(grade == 5 || grade == 6 || grade == 7 || grade == 8 || grade == 9 || grade == 3 ){
			if(total >36){
				alert("Number of Increment + current satge should not be greater than 36");
			}
			return false;
		}
		else{
			return true;
		}	
	}
	 
	function ABC(formname, checktoggle)
	{
		  if(checktoggle)	{
			  document.getElementById("save").disabled = false;
			  document.getElementById("uncheckAll").disabled = false;
		  }
		  else{
			  document.getElementById("save").disabled = true;
		  }
		  var checkboxes = new Array(); 
		  checkboxes = document[formname].getElementsByTagName('input');
		  for (var i=0; i<checkboxes.length; i++)  {
		    if (checkboxes[i].type == 'checkbox')   {
		      checkboxes[i].checked = checktoggle;
		    }
		  }
	  
	}

	 function enableButton(formname){	

		var checkboxes = $("input[type='checkbox']"),
		submitButt = $("input[type='submit']");

		checkboxes.click(function() {
			   submitButt.attr("disabled", !checkboxes.is(":checked"));
		});
		 
	} 
	 
	 function getGradeInfo() {
			var grade = document.getElementById("grade").value;
		//	alert(grade);
			
			if(grade == "0" && grade == ""){
				alert("Please Select Proper Grade !");
				document.getElementById("grade").focus();
				return false;
			}
			else{
				window.location.href = "Increament.jsp?action=getDtails&grade="+grade;	
			}
		}
	
	 function processDetails(){
		 
		 
		 
		 
	 }

</script>

<script type="text/javascript">

	function checkFlag() {		
		var f = parseInt(document.getElementById("flag").value);
		if (f == 1) {
			alert("Saved Successfully");
		}
		if (f == 2) {
			alert("Error in Saving Details !");
		}
		if (f == 3) {
			alert("Processed Successfully");
		}
		if (f == 4) {
			alert("Error in Processing Details !");
		}	
	}
	
	function  noBack()
	{
		window.history.forward();
	}
	setTimeout("noBack()", 0);
	window.onunload = function() 
	{
	    null;
	};
	
	// ======================================== Form Validation start from here =========================================
	function validation(){
	var a  = document.getElementById("flag").value;
	
	var chkboxes = new Array(); 
	chkboxes = document['form3'].getElementsByTagName('input');
	
	for (var i=0; i<chkboxes.length; i++)  {
	    if (chkboxes[i].type == 'checkbox')   {
		     if(chkboxes[i].checked){	    	
		    	// alert("chkboxes "+chkboxes[i].value);	    	
		    	var empno = chkboxes[i].value;
		    	var grade = document.getElementById("grade").value;
				var cStage = document.getElementById("stage_"+empno).value;
				var nStage = document.getElementById("newStage_"+empno).value;
			    	
					if(grade =="" && cStage=="" && nStage==""){
						alert("Values can't be blank !");
						document.getElementById("grade").focus();
						return false;
					}
					if(cStage==""){
						alert("Current stage can not blank !");
						document.getElementById("stage_"+empno).focus();
						return false;
					}
					if(nStage==""){
						alert("Number of increment can not blank !");
						document.getElementById("newStage_"+empno).focus();
						return false;
					}
					
					grade =	parseInt(grade);
					cStage =	parseInt(cStage);
					nStage =	parseInt(nStage);
					var total = cStage+nStage;
				
					//alert("total "+total);
					
					if(grade == 1 || grade == 2 || grade ==10){
						if(total >20){
							alert("Number of Increment + current satge should not be greater than 20");
							document.getElementById("newStage_"+empno).focus();
							return false;
						}				
					}
					if(grade == 11){
						if(total >21){
							alert("Number of Increment + current satge should not be greater than 21");
							document.getElementById("newStage_"+empno).focus();
							return false;
						}					
					}
					if(grade == 3 || grade == 4){
						if(total >31){
							alert("Number of Increment + current satge should not be greater than 31");
							document.getElementById("newStage_"+empno).focus();
							return false;
						}						
					}
					if(grade == 5 || grade == 6 || grade == 7 || grade == 8 || grade == 9 || grade == 3 ){
						if(total >36){
							alert("Number of Increment + current satge should not be greater than 36");
							document.getElementById("newStage_"+empno).focus();
							return false;
						}
					}
					
		     } // end of 2nd if in for loop
	    } // end of 1st if in for loop
	  } // End of for loop
	
	// ------------------------------------Confirmation alert related code------------------------------------------------
		var result = updateCount();
		function updateCount(){
			var count  = $("input[type=checkbox]:checked").size();
			if(count==0){
				alert("Al Least Select One CheckBox");
				return false;
			}
			else{
				return true;
			}					 
		}
		
		if(result == true){
			var r=  confirm("Are you sure to Save Or Process Increament");
		}
		
		if(r==true && result== true){
			var p = prompt("Type Yes to Process Increment for selected Employee."); 
			if(p=="yes" || p=="Yes" || p=="YES"){	
				return true;  // false for testing purpose either set true
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}		
	// ------------------------------------Confirmation alert related code------------------------------------------------
	
	return true;  // false for testing purpose either set true
	}
	// ======================================== Form Validation End here =========================================
</script>

 <!-- =========================================Code Start here =========================================================-->
<script type="text/javascript"> 
	<%
	try
	{  
		String flag = "";
		int totalEmployee = 0;
		int grade =0;
		int nStage =0;
		String actionVal ="";
		LookupHandler lkh = new LookupHandler();
		TranHandler tHandler = new TranHandler();
		ArrayList<Lookup> lkp_list = lkh.getSubLKP_DESC("desig");
		IncrementHandler inHandler = new IncrementHandler();
		IncrementBean bean = new IncrementBean(); 
		ArrayList<IncrementBean> list = new ArrayList<IncrementBean>();
		
		String action = request.getParameter("action")==null?"0":request.getParameter("action");
		
		if(action.equalsIgnoreCase("getDtails")){
			
			flag = request.getParameter("flag")==null?"0":request.getParameter("flag");
			grade = Integer.parseInt(request.getParameter("grade"));
		//	nStage = Integer.parseInt(request.getParameter("nStage"));
			list = inHandler.getEmployeeDetailsByGrade(grade);
			totalEmployee = list.size();
		}
		if(action.equalsIgnoreCase("saveDetails")){
			
			flag = request.getParameter("flag")==null?"0":request.getParameter("flag");
			grade = Integer.parseInt(request.getParameter("grade"));
			list = inHandler.getSavedEmployeeDetails(grade);
			totalEmployee = list.size();
		}
		
	
	%>
	
	

</script>

<style type="text/css">
	.alignSpan{
		text-align: center;
	}
</style>


</head>
<body onLoad="checkFlag()" onunLoad="noBack()"  style="overflow: hidden;">
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>

<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
	<!-- start content -->
	<div id="content">
	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Increment Details</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
		<tr>
			<th rowspan="3" class="sized">
			<img  src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
			<th class="topleft"></th>
			<td id="tbl-border-top">&nbsp;</td>
			<th class="topright"></th>
			<th rowspan="3" class="sized">
				<img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
		</tr>
		<tr>
			<td id="tbl-border-left"></td>
			<td>
			<!--  start content-table-inner ...................................................................... START -->
			<div id="content-table-inner">
			<!--  start table-content  -->
			<div id="table-content">
			<center>
		
			<form  name="form3"  action="IncrementServlet?" method="post" onsubmit="return validation()">
			<div id="">
				<center>
			  
				<table id="customers" width="800" align="center">
					<tr>
						<td><span><b>Number of Employee <%=totalEmployee%></b></span></td>
						<th>Select Grade :</th>
						<td>
							<select name="grade" id= "grade" onchange="getGradeInfo()" title='Select the Grade of Employee!' style="margin-right: 10px;" >
									<option value='0'>Select</option>
							    <%
							    for(Lookup lkp_bean : lkp_list)
							    {
							    %>
							    	<option value='<%=lkp_bean.getLKP_SRNO()%>' <%=lkp_bean.getLKP_SRNO() == grade ?"Selected":"" %>   ><%=lkp_bean.getLKP_DESC()%></option>							    
							    <%	  
							    }
							    %>							        
			      			</select>
						</td>	
					</tr>
				
				</table>
				</center>	
			</div>
			<br>
			
		
				<table id="customers" width="800" align="center">
					<tr>
						<th>Increment Details</th>
					</tr>
					<tr>
						<td>
							<table width="1100">
							<tr>
								<th width="50">EmpCode</th>
								<th width="150">Name</th>
								<th width="50">Basic</th>
								<th width="50">D.A.</th>												
								<th width="50">V.D.A</th>
								<th width="50">HRA</th>	
								<th width="50">Current Stage</th>	
								<th width="50">Next Basic</th>		
								<th width="70">Number of Increment</th>															
							</tr>
							</table>
						</td>
				   </tr>
					<tr>
						<td>
							<div id="scrolling"	style="height: 380px; overflow-y: scroll; max-width: 100%; background-color: #FFFFFF;"align="center">
						<%if(list.size()!=0){%>
				
								<table width="1100">
								<% 
									for(IncrementBean Inbean : list){
										//System.out.println("in for loop " + list.size());
								%>		
										<tr>
										<td width="50" align="center">
											<input type="checkbox" id="checklist" value="<%=Inbean.getEmpno()%>" 
											       name="checklist" onclick="enableButton('form3')" /> 
											<input type="text" id="code_<%=Inbean.getEmpno()%>" name="code_<%=Inbean.getEmpno()%>"  value="<%=Inbean.getEmpCode()%>" readonly="readonly" size="6" >			
										</td>
										<td width="150" align="center">
											<input type="text" id="name_<%=Inbean.getEmpno()%>" name="name_<%=Inbean.getEmpno()%>" 
												   value="<%=Inbean.getEmpName()%>" readonly="readonly" size="25" >
											<%-- <span id="name_<%=Inbean.getEmpno()%>"> <%=Inbean.getEmpName()%> </span> --%>
										</td>
										<td width="50" align="center">									
											<input type="text" id="basic_<%=Inbean.getEmpno()%>" name="basic_<%=Inbean.getEmpno()%>" value="<%=Inbean.getBasic() %>" readonly="readonly" size="6" >								
										</td>
										<td width="50" align="center">
											<input type="text" id="da_<%=Inbean.getEmpno()%>" name="da_<%=Inbean.getEmpno()%>" value="<%=Inbean.getDa() %> "  readonly="readonly" size="6" >
										</td>			
										<td width="50" align="center">
											<input type="text" id="vda_<%=Inbean.getEmpno()%>" name="vda_<%=Inbean.getEmpno()%>" value=" <%=Inbean.getVda() %>" readonly="readonly"   size="6" >
										</td>
										<td width="50" align="center">
											<input type="text" id="hra_<%=Inbean.getEmpno()%>" name="hra_<%=Inbean.getEmpno()%>" value="<%= Inbean.getHra()%>" readonly="readonly"  size="6" >
										</td>	
										<td width="50" align="center">
											<input type="text" id="stage_<%=Inbean.getEmpno()%>" name="stage_<%=Inbean.getEmpno()%>" value="<%=Inbean.getStage()%>" readonly="readonly" size="6" >
										</td>										
										<td width="50" align="center">
											<input type="text" id="newBasic_<%=Inbean.getEmpno()%>" name="newBasic_<%=Inbean.getEmpno()%>" 
													value="<%=Inbean.getNewBasic()%>"  readonly="readonly" size="6" >
										</td>	
								<% if(action.equalsIgnoreCase("getDtails")){ %>											
										<td width="70" align="center">  <!--  newStage is Number of Increment -->
											<input type="text" id="newStage_<%=Inbean.getEmpno()%>"   name="newStage_<%=Inbean.getEmpno()%>" 
												   value="<%=Inbean.getNewstage()%>" maxlength="2" size="5" placeholder="1"
												   onkeypress="return inputLimiter(event,'Numbers')"
												   onkeyup ="return calculate('stage_<%=Inbean.getEmpno()%>','newStage_<%=Inbean.getEmpno()%>', '<%=Inbean.getEmpno()%>')">
											 
										</td>
								<% }else if(action.equalsIgnoreCase("saveDetails")) {%>		
										<td width="70" align="center">  <!--  newStage is Number of Increment -->
										<script type="text/javascript"> 
										
										</script>
											<input type="text" id="newStage_<%=Inbean.getEmpno()%>"   name="newStage_<%=Inbean.getEmpno()%>" 
												   value="<%=Inbean.getNewstage()%>" maxlength="2" size="5" placeholder="1" readonly="readonly"
												   onkeypress="return inputLimiter(event,'Numbers')"
												   onkeyup ="return calculate('stage_<%=Inbean.getEmpno()%>','newStage_<%=Inbean.getEmpno()%>', '<%=Inbean.getEmpno()%>')">
											 
										</td>
								<% } %>	
									</tr>
												
								<% 
									}
								%>	
																																
								</table>
						<%}else{ %>
								<span >No Records found</span>
						<% }%>
							</div>

						</td>
					</tr>	

					<tr>
						<td align="center" bgcolor="#1F5FA7">
					<% if(action.equalsIgnoreCase("getDtails") ||  action.equalsIgnoreCase("saveDetails") ){ %>
							 <input type="button" id="checkAll" value="Check All" onclick="ABC('form3',true)" /> 
							 <input type="button" id="uncheckAll" value="UnCheck All"  onclick="ABC('form3',false)" disabled="disabled" />  
					<% if(action.equalsIgnoreCase("getDtails")){ %>
							 <input type="hidden" id="action" name="action"  value="saveDetails"  />  
							 <input type="submit" id="save" name ="save" value="Save" disabled="disabled" />
					<%}else if(action.equalsIgnoreCase("saveDetails")) {%>
							  <input type="hidden" id="action1" name="action1"  value="processDetials"  /> 
							  <input type="submit" id="save" name ="save" value="Process Increament"  disabled="disabled" /> 						
					<% }  }%>
						</td>
					</tr>
						
					<tr>
						<td align="left" bgcolor="#1F5FA7">&nbsp;</td>
					</tr>
			</table>
			</form>

			<br>
			
			</center>
		</div>		
		<input type="hidden" name="flag" id="flag" value="<%=flag%>">
<% 				
		}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>		
		<!--  end table-content  -->
		<div class="clear"></div>

		</div> <!--  end content-table-inner ............................................END  -->
		
		 <!-- To display pdf on brawser -->
		<div id="viewPdf"  hidden="true" align="center"></div>
		<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
					<img alt="" src="images/process.gif">
				</div>
		</div>
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