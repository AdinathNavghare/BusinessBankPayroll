<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Employee List</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}
.style1 {
	color: #FF0000
}
</style>

<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>
<%
	LookupHandler lkh=new LookupHandler();
	ArrayList<Lookup> lkp_Categorylist=lkh.getCategoryList("CATE");
  %> 
<script type="text/javascript">
var xmlhttp;
var url="";

if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}


	function validation()
	{
		var type = document.getElementById("type").value;
		var fromDate = document.getElementById("date").value;
	  // 	var toDate=document.getElementById("date1").value;
	  
	   	var fromage=document.getElementById("agefrom");
		var fromageval=fromage.options[fromage.selectedIndex].value;
		
		var toage=document.getElementById("ageto");
		var toageval=toage.options[toage.selectedIndex].value;
		
		var desigtype1=document.getElementById("desigtype");
		var desigtype1val=desigtype1.options[desigtype1.selectedIndex].value;
		
		var branchtype1=document.getElementById("branchtype");
		var branchtype1val=branchtype1.options[branchtype1.selectedIndex].value;
		
		if(type !==  "PromotionList" ){
		  if(type !==  "Doj"){
			if(document.getElementById("date").value=="")
			{
			alert("Please Select Date");
			document.getElementById("date").focus();
			return false;
			}
		  }
		}
		if(type =="PromotionList"){
			var fd = document.getElementById("fromdate").value;
			var td = document.getElementById("tdate").value;
			if (document.getElementById("fromdate").value == "") {
				alert("Please Select From Date");
				document.getElementById("fromdate").focus();
				return false;
			} else if (document.getElementById("tdate").value == "") {
				alert("Please Select To Date");
				document.getElementById("tdate").focus();
				return false;
			}
			fd = fd.replace(/-/g,"/");
			td = td.replace(/-/g,"/");
			var d1 = new Date(fd);
			var d2 = new Date(td);
			if (d1.getTime() > d2.getTime()) {
				alert("Invalid Date Range! fromdate Date cannot greater than TODate!");
				 document.getElementById("tdate").focus();
				return false;
			}
			if (document.getElementById("branchtyp").value =="0") {
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
			}
			if (document.getElementById("branchtyp").value =="2") {
				 if(document.getElementById("Branch").value=="Select"){
						alert("Please select Branch");
						document.getElementById("Branch").focus();
						return false;
					}
				}
			
			if(document.getElementById("branchtyp").value=="3"){
				 RangeFrom = document.getElementById("rangeFrom").value;
				 RangeTo =document.getElementById("rangeTo").value;
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Range from");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Range To");
					document.getElementById("rangeTo").focus();
					return false;
				}
			//	 if(+RangeFrom > +RangeTo){
				if(Number(RangeFrom) > Number(RangeTo)){
					alert("Please select Proper Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
				}
		}
			
		if(type =="Doj"){
			var fd = document.getElementById("fromdate").value;
			var td = document.getElementById("tdate").value;
			if (document.getElementById("fromdate").value == "") {
				alert("Please Select From Date");
				document.getElementById("fromdate").focus();
				return false;
			} else if (document.getElementById("tdate").value == "") {
				alert("Please Select To Date");
				document.getElementById("tdate").focus();
				return false;
			}
			fd = fd.replace(/-/g,"/");
			td = td.replace(/-/g,"/");
			var d1 = new Date(fd);
			var d2 = new Date(td);
			if (d1.getTime() > d2.getTime()) {
				alert("Invalid Date Range! fromdate Date cannot greater than TODate!");
				 document.getElementById("tdate").focus();
				return false;
			}
		}
			
		
		if(document.getElementById("type").value=="0")
		{
			alert("Please Select Report Type");
			document.getElementById("type").focus();
			return false;
		}
		if(parseInt(fromageval)>parseInt(toageval))
		{
			alert("Please select Proper Age Range !!!");
			return false;
		}
		if(desigtype1val=="rangewise")
		{
			var from_desig1=document.getElementById("dewise_from");
			var from_desig1val=from_desig1.options[from_desig1.selectedIndex].value;
			var to_desig1=document.getElementById("dewise_to");
			var to_desig1val=to_desig1.options[to_desig1.selectedIndex].value;
			if(parseInt(from_desig1val)>parseInt(to_desig1val))
				{
				alert("Please select Proper Designation Range !!!");
				return false;
				}
		}
		if(branchtype1val=="rangewise")
		{
			var from_branch1=document.getElementById("Branch_from");
			var from_branch1val=from_branch1.options[from_branch1.selectedIndex].value;
			var to_branch1=document.getElementById("Branch_to");
			var to_branch1val=to_branch1.options[to_branch1.selectedIndex].value;
			if(parseInt(from_branch1val)>parseInt(to_branch1val))
				{
				alert("Please select Proper Branch Range !!!");
				return false;
				}
		}
		if(type =="Category"){
			if(document.getElementById("category").value=="Select"){
			alert("Please select category");
			document.getElementById("category").focus();
			return false;
			}
		}
	/* 	document.getElementById("process").hidden=false;
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				var response=xmlhttp.responseText;
		        document.getElementById("viewPdf").innerHTML=response;
		        document.getElementById("process").hidden=true;
		        document.getElementById("viewPdf").hidden=false;
			}
				
		};
		xmlhttp.open("GET",true);
		xmlhttp.send();
		 */
		
		
		
	}
	
	
	function getVal(){
		
		var type = document.getElementById("type").value;
		/* if(type == "Gender"){alert("gender");
			 document.getElementById("genderTD").style.display = '';
			 document.getElementById("desi").style='table-row';
		}else{
			document.getElementById("genderTD").style.display = 'none';
			document.getElementById("desi").style.display = 'none';
		} */
		if(type == "Project"){
			 document.getElementById("branchtd").style.display = '';
		}else{
			document.getElementById("branchtd").style.display = 'none';
		}
		if(type == "EmpSign"){
			 document.getElementById("branchtd").style.display = '';
		}else{
			document.getElementById("branchtd").style.display = 'none';
		}
		if(type == "Designation"){
			 document.getElementById("desi").style.display = '';
		}else{
			document.getElementById("desi").style.display = 'none';
		}
		if(type == "Department"){
			 document.getElementById("dept").style.display = '';
		}else{
			document.getElementById("dept").style.display = 'none';
		}
		if(type == "Age"){
			document.getElementById("from_age").style.display = '';
			document.getElementById("to_age").style.display = '';
			document.getElementById("desig_type").style.display = '';
			document.getElementById("branch_type").style.display = '';
		}
	//	if(type != "Age")
	   else
		{
			document.getElementById("from_age").style.display = 'none';
			document.getElementById("to_age").style.display = 'none';
			document.getElementById("desig_type").style.display = 'none';
			document.getElementById("branch_type").style.display = 'none';
			document.getElementById("from_desig").style.display = 'none';
			document.getElementById("to_desig").style.display = 'none';
			document.getElementById("from_branch").style.display = 'none';
			document.getElementById("Branch_to").style.display = 'none';
			document.getElementById("to_branch").style.display = 'none';
		}
		if(type == "PromotionList"){
			 
			 	 
			 document.getElementById("frmdate").style.display = '';
			 document.getElementById("todate").style.display = '';
			 document.getElementById("forbranches").style.display = '';
			 document.getElementById("selectdate").style.display = 'none';
		}
		else if(type == "Doj"){
			 document.getElementById("frmdate").style.display = '';
			 document.getElementById("todate").style.display = '';
			 document.getElementById("forbranches").style.display = 'none';
			 document.getElementById("selectdate").style.display = 'none';
			 document.getElementById("branc").style.display = 'none';
		}
		else{
			document.getElementById("selectdate").style.display = '';
			document.getElementById("frmdate").style.display = 'none';
			document.getElementById("todate").style.display = 'none';
			document.getElementById("forbranches").style.display = 'none';
			document.getElementById("specific").style.display = 'none';		
			document.getElementById("branc").style.display = 'none';
			
		}
		if(type == "Category"){
			document.getElementById("categoryWise").style.display = '';	
		}else{
			document.getElementById("categoryWise").style.display = 'none';
		}
		if(type == "Gender"){
		 document.getElementById("genderTD").style.display = '';
	//	 document.getElementById("desi").style='table-row';
		 document.getElementById("brancDesig").style='table-row';
	}
	else{
		document.getElementById("genderTD").style.display = 'none';
		document.getElementById("desi").style.display = 'none';
		document.getElementById("empbranc").style.display = 'none';
		document.getElementById("brancDesig").style.display = 'none';
	}
	}
	function getType(){
		var RepType=document.getElementById("Desigbranc").value;
		if(RepType=="1"){
		 document.getElementById("desi").style='table-row';
		 document.getElementById("empbranc").style.display = 'none';
		}
		else if(RepType=="2"){
		 document.getElementById("empbranc").style='table-row';
		 document.getElementById("desi").style.display = 'none';
		}
		else if(RepType=="0"){
		 document.getElementById("desi").style.display = 'none';
		 document.getElementById("empbranc").style.display = 'none';
		}
	}
	function disigselect()
	{
		var selection=document.getElementById("desigtype");
		var type=selection.options[selection.selectedIndex].value;
		if(type=="all"){
				document.getElementById("from_desig").style.display = 'none';
				document.getElementById("to_desig").style.display = 'none';
		}
		else if(type=="specific"){
				document.getElementById("from_desig").style.display = '';
				document.getElementById("to_desig").style.display = 'none';
		}
		else if(type=="rangewise"){
				document.getElementById("from_desig").style.display = '';
				document.getElementById("to_desig").style.display = '';
		}
		
		
	}
	function branchselect()
	{
		
		var selection=document.getElementById("branchtype");
		var type=selection.options[selection.selectedIndex].value;
		if(type=="all"){
				document.getElementById("from_branch").style.display = 'none';
				document.getElementById("to_branch").style.display = 'none';
		}
		else if(type=="specific"){
				document.getElementById("from_branch").style.display = '';
				document.getElementById("to_branch").style.display = 'none';
		}
		else if(type=="rangewise"){
				document.getElementById("from_branch").style.display = '';
				document.getElementById("to_branch").style.display = '';
		}
	}
	
	function selectbranch()
	{
		var type =document.getElementById("branchtyp").value;
	   
		if(type =="1")
		{
		 document.getElementById('branc').style.display='none';
		 document.getElementById('specific').style.display='none';
		}
		else if(type =="2")
		{
	    document.getElementById('specific').style.display='none';
		document.getElementById('branc').style.display='table-row';
			
		}
		else if(type =="0")
			{
			document.getElementById('branc').style.display='none';
			document.getElementById('specific').style.display='none';
			}
		else if(type =="3")
		{
		document.getElementById('specific').style.display='table-row';
		document.getElementById('branc').style.display='none';
		}
	}
</script>

<script src="js/MONTHPICK/jquery.js"></script>
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
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>

</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employees List</h1>
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
			
			<form name="emplistForm" action="ReportServlet"  onSubmit="return validation()">
			<table border="1" id="customers" align="center" style="width: 31%;">
			<tr>
				<th>EMPLOYEES LIST</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				<input type="hidden" id="action" name="action" value="emplist"></input>
				<table align="center">
					<tr class="alt" height="30" align="letf" id="selectdate"> 
						<td>Select Date</td>
						<td align="left">
							<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
							
					</tr>
					<tr>
						<td >Report Type</td>
						<td  align="left">
							<select id="type" name="type" onchange="getVal()">
								<option value="0">Select</option>
						<!--	<option value="general">Generalise Report</option> -->
								<option value="Age">Age Wise</option>
								<option value="Designation">Designation Wise</option>
								<option value="Department">Department Wise</option>
								<option value="Doj">Date Of Joining Wise</option> 
							 	<option value="Dol">Date Of Leaving Wise</option>
								<option value="Grade">Grade Wise</option>  
							 	 	
							 	<option value="Handicap">Handicap Employee</option>
							 	<option value="BloodGroup">Blood Group Wise</option>
							 	<option value="Qualification">Qualification Wise</option>
							 	<option value="Category">Category Wise</option>
							 	<option value="Gender">Gender wise</option>
							 	<option value="Project">Branch Wise</option>	
								<option value="Branchempsummery">Branch Wise Employee Summery</option>
								<option value="PromotionList">Designation Wise Promotion List</option>
							    <option value="Seniority">DOJ Wise Seniority List</option> 
							     <option value="EmpSign">Employee Signature</option> 
							<!-- 	<option value="An">Annotation Report</option> -->
							<!-- 	<option value="S">Structure Report</option> -->
							<!-- 	<option value="S">Employee List by Name</option> --> 	
								
							</select>
			         </td>	
					</tr>
					<tr id="genderTD" style="display: none;" >
						<td >Select Gender</td>
						<td  >
							<input type="radio" name="gender" value="male"   > Male<br>
  							<input type="radio" name="gender" value="female"  > Female<br>
  							<input type="radio" name="gender" value="all" checked > ALL
						</td>
					</tr>
			<tr id="brancDesig" style="display:none">
			<td>Select Type</td>
     		<td>
			<select name="Desigbranc" id="Desigbranc" onchange="getType()" style="width: 145px;">  
      	 	<option value="0">All</option>
      	 	<option value="1">Desig Wise</option>
      	 	<option value="2">Branch Wise</option>   	
      	 	</select></td></tr>	
      	 	
			<tr id="desi" style="display:none;">
    		<td >Desig Wise</td>
     		<td>
			<select name="dewise" id="dewise">  
    		<%
    		LookupHandler lkh7=new LookupHandler();
    		ArrayList<Lookup> lkp_list7=lkh7.getSubLKP_DESC("DESIG");
		    for(Lookup lkp_bean:lkp_list7)
			{
			%>
			<option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			<%	  
			 }
			 %>		</select>
     		</td>
     		</tr> 
     		
     		<tr id="empbranc" style="display:none">
			<td>Branch Wise</td>
     		<td>
			<select name="EmpBranch" id="EmpBranch">  
      	<!-- 	<option value="Select">Select</option>   -->
    		<%
			EmpOffHandler eh7 = new EmpOffHandler();
			ArrayList<EmpOffBean> ebn7 = new ArrayList<EmpOffBean>();
			ebn7 = eh7.getprojectCode();
			for(EmpOffBean eopbn : ebn7)
			{
			%>
			<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
			<%		}
			%>
     		</select>
     		</td>
     		</tr>
     		
					<tr id="from_age" style="display: none;" >
						<td >From Age</td>
						<td  >
							<select name="agefrom" id="agefrom" style="width:140px">  
      					  		<%for(int i=18;i<60;i++)
								{
								%>
								<option value="<%=i%>"><%=i%></option>
								<%}
								%>
    						</select>
						</td>
					 </tr>
					<tr id="to_age" style="display: none;" >
						<td >To Age</td>
						<td  >
							<select name="ageto" id="ageto" style="width:140px">  
      					  		<%for(int i=18;i<60;i++)
								{
								%>
								<option value="<%=i%>"><%=i%></option>
								<%}
								%>
    						</select>
						</td>
					</tr>
					<tr id="desig_type" style="display: none;" >
						<td>Designation Type</td>
						<td>
							<select name="desigtype" id="desigtype" style="width:140px" onchange="disigselect() ">  
      					  		<option value="all">All</option>
								<option value="specific">Specific</option>	
								<option value="rangewise">Ragne-Wise</option>
    							</select>
						</td>
					</tr>
					<tr  class="alt" id="from_desig" style="display:none">
    				
					   <td>From Designation  </td>
     			      		<td>
								<select name="dewise_from" id="dewise_from" style="width:140px">  
      					  			 
    											
    			<%
    			LookupHandler lkh4=new LookupHandler();
    			ArrayList<Lookup> lkp_list4=lkh4.getSubLKP_DESC("DESIG");
			      for(Lookup lkp_bean:lkp_list4)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_SRNO()+". "+lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>		</select>
     			      		</td>
     			    </tr>
     			    <tr  class="alt" id="to_desig" style="display:none">  		
    					<td>To Designation  </td>
     			      		<td>
								<select name="dewise_to" id="dewise_to" style="width:140px">  
      					  			 
    											
    			<%
    			LookupHandler lkh5=new LookupHandler();
    			ArrayList<Lookup> lkp_list5=lkh5.getSubLKP_DESC("DESIG");
			      for(Lookup lkp_bean:lkp_list5)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_SRNO()+". "+lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>		</select>
     			      		</td>
     			      		</tr>	
				<tr id="branch_type" style="display: none;" >
						<td>Branch Type</td>
						<td>
							<select name="branchtype" id="branchtype" style="width:140px" onchange="branchselect() ">  
      					  		<option value="all">All</option>
								<option value="specific">Specific</option>
								<option value="rangewise">Ragne-Wise</option>
    						</select>
						</td>
					</tr>
					<tr id="from_branch" style="display: none;" >
						 <td>From Branch </td>
     			      		<td>
								<select name="Branch_from" id="Branch_from" style="width:140px">  
      					  			<%
														EmpOffHandler eh4 = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn4 = new ArrayList <EmpOffBean>();
														ebn4 = eh4.getprojectCode();
														for(EmpOffBean eopbn : ebn4)
														{
												%>
														<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getPrj_srno()+". "+eopbn.getSite_name()%></option>
												<%		}
												%>
    										
    									
     			      			</select>
     			      		</td>
     			      		</tr>
     			      	<tr id="to_branch" style="display: none;" >
						 <td>To Branch  </td>
     			      		<td>
								<select name="Branch_to" id="Branch_to" style="width:140px">  
      					  			<%
														EmpOffHandler eh5 = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn5 = new ArrayList <EmpOffBean>();
														ebn5 = eh5.getprojectCode();
														for(EmpOffBean eopbn : ebn5)
														{
												%>
														<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getPrj_srno()+". "+eopbn.getSite_name()%></option>
												<%		}
												%>
    										
    									
     			      			</select>
     			      		</td>
     			      		</tr>
					<tr id="branchtd" style="display: none;" >
						 <td>Select Branch Wise </td>
     			      		<td>
								<select name="Branch1" id="Branch1" style="width:140px">  
      					  			<option value="All">All</option>  
    											
    											<%
														EmpOffHandler eh = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn = new ArrayList <EmpOffBean>();
														ebn = eh.getprojectCode();
														for(EmpOffBean eopbn : ebn)
														{
												%>
														<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
    										
    									
     			      			</select>
     			      		</td>
     			      		</tr>
                            <tr  class="alt" id="desi" style="display:none">
    				
					   <td>Select Designation Wise </td>
     			      		<td>
								<select name="dewise" id="dewise" style="width:140px">  
      					  			<option value="ALL">All</option>  
    											
    			<%
    			  ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
			      for(Lookup lkp_bean:lkp_list)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>		</select>
     			      		</td>
     			      		</tr>
     			  <tr  class="alt" id="dept" style="display:none">
    				
					   <td>Select Depatrment Wise </td>
     			      		<td>
								<select name="depwise" id="depwise" style="width:140px">  
      					  			<option value="ALL">All</option>  
    											
    			<%
    			LookupHandler lkh1=new LookupHandler();
    			ArrayList<Lookup> lkp_list1=lkh.getSubLKP_DESC("dept");
			      for(Lookup lkp_bean:lkp_list1)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>		</select>
     			      		</td>
     			      		</tr>
     			      		<tr class="alt" height="30" align="center" id="frmdate" style="display: none">
							<td align="left" style="font-weight: 400; font-size: unset;">Select From Date:-</td>
							<td align="left"><input name="fromdate" id="fromdate" style="width: 145px; height: 25px; font-size: 13px;" type="text" onBlur="if(value=='')" readonly="readonly">
							&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align: middle; cursor: pointer;" onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" />
							</td>
							</tr>
     			      		<tr class="alt" height="30" align="center" id="todate" style="display: none">
							<td align="left" style="font-weight: 400; font-size: unset;">Select TO Date:-</td>
							<td align="left"><input name="tdate" id="tdate" style="width: 145px; height: 25px; font-size: 13px;" type="text" onBlur="if(value=='')" readonly="readonly">
							&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align: middle; cursor: pointer;" onClick="javascript:NewCssCal('tdate', 'ddmmmyyyy')" />
							</td>
							</tr>
							<tr class="alt" height="30" id="forbranches" style="display:none">
							<td style="font-weight: 400; font-size: unset;">Select Designation</td>
							<td>	
							<select id="branchtyp" name="branchtyp"  onchange="selectbranch()" style="width: 145px; height: 25px;"> 
							<option value="0" selected="selected">SELECT</option>
							<option value="1">ALL</option>
							<option value="2">Specific</option>
							<option value="3">RANGE</option>
											</select>
											</td></tr>
											<tr class="alt" id="branc" style="display:none">
					   						<td>Specific Desig</td>
     			       						<td>
					   						<select name="Branch" id="Branch" style="width:145px; height: 25px;" >  
						      				   <option value="Select">Select</option>  
						    				   <%-- <%
												for(EmpOffBean eopbn : ebn)
												{
												%>
												<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%> --%>
												<%
    			
			     			 for(Lookup lkp_bean:lkp_list)
			     			 {
			     			 %>
			     			 <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			     			 <%	  
			     			 }
			     			 %>
						     			      	</select>
						     			      	</td>
						     			      	<td style="height:25px;"></td>
						     			      	</tr>
						     			      	
						     		<!-- 	      	<tr class="alt" id="branc" style="display:none"> -->
    				
						        				<tr class="alt" height="30"  id="specific" style="display:none">
												<td >Range From</td>
												<td  align="left">
												<select name="rangeFrom" id="rangeFrom" style="width:70px; height: 25px;">  
      					  						<option value="Select">Select</option>  
    											
    												<%
    			
			     			 						for(Lookup lkp_bean:lkp_list)
			     			 						{
			     			 						%>
			     									 <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_SRNO()%></option>
			     									 <%	  
			     										 }
			     			 							%>
     			      							</select>
     			      							<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
												&nbsp;Range To&nbsp;
												<select name="rangeTo" id="rangeTo" style="width:70px; height: 25px;">  
      					  						<option value="Select">Select</option>  
    											<%
    			
			     			 						for(Lookup lkp_bean:lkp_list)
			     			 						{
			     			 						%>
			     									 <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_SRNO()%></option>
			     									 <%	  
			     									 }
			     			 						%>
     			      							</select>
												</td>			
												</tr>
												<tr class="alt" id="categoryWise" style="display:none">
					   						<td>Select Category</td>
     			       						<td>
					   						<select name="category" id="category" style="width:145px; height: 23px;" >  
						      				   <option value="Select">SELECT</option>  
						      				   <option value="All">ALL</option> 
												<%
												for(Lookup lkp_bean:lkp_Categorylist)
								     			 {
								     			 %>
								     			 <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
								     			 <%	  
								     			 }
								     			 %>
						     			      	</select>
						     			      	</td>
<!-- 						     			      	<td style="height:25px;"></td>
 -->						     			      	</tr>
     			    	<tr>	
						<td colspan="2" align="center">
							<input type="submit" class="myButton" value="Get Report" />
						</td>
					</tr>
				</table>

			  </td>
			</tr>
		</table>
	</form>	   
			</center>
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		
		<div id="viewPdf"  hidden="true" align="center"></div>
		<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
			<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
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
    

</body>
</html>