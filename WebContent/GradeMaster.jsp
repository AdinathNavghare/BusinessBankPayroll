<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.VdaDAO"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.GradeBean"%>
<%@page import="payroll.DAO.GradeHandler"%>
<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.AdvanceBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.AdvanceHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Grade Master</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/date.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

<%
ArrayList<GradeBean> gradeWiseList =new ArrayList<GradeBean>();
GradeHandler gradeHandler=new GradeHandler();
String type=request.getParameter("type")==null?"0":request.getParameter("type");
GradeBean gradeBean=new GradeBean();
gradeBean=gradeHandler.getGradeData(type,1);
//System.out.println("the gradeBean is"+gradeBean.getBasic());
String maxEndDate=gradeHandler.getMaxEndDate(type);
System.out.println("max end date"+maxEndDate);
String action=request.getParameter("action")==null?"":request.getParameter("action");
VdaDAO vdaDao=new VdaDAO();
/* if(!type.equals("0")){
	gradeWiseList=gradeHandler.getGrade(type);
	System.out.println("size of gradelist"+gradeWiseList.size());
} */

int flag=-1;
try
{
	flag = Integer.parseInt(request.getParameter("flag"));
}
catch(Exception e)
{
}



%>



function calcValue(gradeBasic,PercentOrFixedValue,ValueType,Value)
{

   if(document.getElementById(ValueType).value==0)
	   
	   {
	   document.getElementById(Value).value=document.getElementById(PercentOrFixedValue).value;
	   }
   else{
	var percent = document.getElementById(PercentOrFixedValue).value;
	var basic= document.getElementById(gradeBasic).value;;
	document.getElementById(Value).value =(((basic*percent)/100.00).toFixed(2));

   }

	
}



function calcValueOnBasic(gradeBasic,daPercentOrFixedValue,daValueType,daValue,hraPercentOrFixedValue,hraValueType,hraValue)
{
//FOR DA
   if(document.getElementById(daValueType).value==0)	   
	   {
	   document.getElementById(daValue).value=document.getElementById(daPercentOrFixedValue).value;
	   }
   else{
	var percent = document.getElementById(daPercentOrFixedValue).value;
	var basic= document.getElementById(gradeBasic).value;
	document.getElementById(daValue).value =(((basic*percent)/100.00).toFixed(2));
   }
   
 //FOR HRA
   if(document.getElementById(hraValueType).value==0)
   {
   document.getElementById(hraValue).value=document.getElementById(hraPercentOrFixedValue).value;
   }
else{
var percent = document.getElementById(hraPercentOrFixedValue).value;
var basic= document.getElementById(gradeBasic).value;
document.getElementById(hraValue).value =(((basic*percent)/100.00).toFixed(2));	
}
 //FOR VDA
		
	
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

function validateForEndDate(){
	var end=document.grade.endDate.value;
	var	endDate = end.replace(/-/g, "/");	
	var  endDateForSend=end;
	 endDate=new Date(endDate);
	alert(endDate);
	var start=document.grade.startDate.value;
	var	startDate = start.replace(/-/g, "/");
	 startDate=new Date(startDate);
	 

	 var endDateEntered=document.getElementById("endDate").value;

	 if(endDateEntered=="" || endDateEntered==" ")
	  {
	  alert("Please select the End Date");
	  return false;
	  }
	 
	 
	alert(startDate);
	<%
	boolean checkVdaCalculated=false;
	

	

	
		System.out.println("hi"+gradeBean.getStartDate());
		if(gradeBean.getStartDate()!=null){
			System.out.println("this is welcome");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
			Date tempDate=new Date();
			tempDate=simpleDateFormat.parse(gradeBean.getStartDate());
			String  start=outputDateFormat.format(tempDate);	 
			System.out.println(start);
		checkVdaCalculated=vdaDao.checkVdaCalculatedFlag(ReportDAO.BOM(start));
		
	System.out.println("checkvdacalculated"+checkVdaCalculated);
	}
	if(maxEndDate!=null && checkVdaCalculated ){%>

	


	
	var maxEndDate='<%=maxEndDate%>';
	
	maxEndDate=maxEndDate.replace(/-/g,"/");
	var maxEndDateFinal=new Date(maxEndDate);
	//alert(maxEndDate);
		<%if(vdaDao.getMaxBatchId()!=1  ){%>
	/* if (maxEndDateFinal.getTime() > endDate.getTime()) {
		alert("Invalid Date Range!\n\n End Date can't be earlier than previous End Date!");
		alert("End Date of previous slab for this Code is"+maxEndDate);
		document.grade.endDate.focus();
		return false;
	} */
    <%}%>
if (startDate.getTime() > endDate.getTime()) {
	alert("Invalid Date Range!\n\n End Date can't be earlier than Start Date!");
	document.grade.endDate.focus();
	return false;
}
var gradeCode=document.getElementById("type").value;
var p=confirm("Do you really want to end this salary structure?");
if(p==true){
var q= confirm("Are you sure to confirm the end of salary structure?");
if(q==true){
	window.location.href="GradeServlet?action=saveEndDate&endDate="+endDateForSend+"&startDate="+start+"&gradeCode="+gradeCode;
}

}
<%} %>

<%if( !checkVdaCalculated){%>
        alert("VDA calculations are not done for this slab");
        return false;
        <%}%>
        
 }

	

function validate() {
    

	var startDate = document.grade.startDate.value;

	 var type=document.getElementById("type").value;

	 if(type=="" || type==0)
	  {
	  alert("Please select the grade");
	  return false;
	  }

	if (startDate == "") {
		alert("Please select Start Date");
		document.grade.startDate.focus();
		return false;
	}
	
	
/* 	startDate = startDate.replace(/-/g,"/");
   	endDate = endDate.replace(/-/g,"/");
	var d1 = new Date(startDate);
  	
  	var d2 =new  Date(endDate);
  	
  			if (d1.getTime() > d2.getTime()) 
  				 {
	   				alert("Invalid Date Range!\n Start Date cannot greater than End Date!");
	  				document.getElementById("endDate").focus();
	   				return false;
	   			 }
	 */
  			 for(var i=1;i<=36;i++){
				 if(document.getElementById("gradeBasic"+i).value==""){
					 alert("One or more Grade Basic Values are  blank");
				     return false;
				 }
				 if(document.getElementById("gradeBasic"+i).value==0){
					 alert("First record of Grade Basic should not be zero");
				     return false;
				 }
				 if(document.getElementById("incrementAmount"+i).value==""){
					 alert("One or more Increment Amount Values are  blank ");
				     return false;
				 }
				 
				 if(document.getElementById("daPercentOrFixedValue"+i).value==""){
					 alert("One or more  Percentage/Fixed Value/Values of DA are blank");
				     return false;
				 }
				 if( document.getElementById("daValue"+i).value==""){
					 alert("One or more DA Values are blank");
				     return false;
				 }
				 if(document.getElementById("hraPercentOrFixedValue"+i).value==""){
					 alert("One or more  Percentage/Fixed Value/Values of HRA are blank");
				     return false;
				 }
				 if( document.getElementById("hraValue"+i).value==""){
					 alert("One or more HRA Values are blank");
				     return false;
				 }
			
			 }
	//		return validateForEndDateSavingFirstTime();
  			var x= confirm("Are you sure to save the salary structure?Once saved can not be edited");
  			if(x==false){
  			return false;
  			}
  			else{
  				var y=confirm("Do you want to save salary structure?");
  				if(y==false){
  		  			return false;
  		  			}
  				else{
  					return true;
  				}
  			}
  	
  			
  			
}

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
	
		  function getRecords(){
		  	 var code=document.getElementById("type").value;
			 if(code!=0)
				window.location.href="GradeMaster.jsp?type="+code,"_self"; 
			 else
				 window.location.href="GradeMaster.jsp";
		 }
			   
			    
			 function enterBasicAndIncrement(index){
					
					
				 var textbox=parseInt(index);
				
				 for(var i=index+1;i<=36;i++){
					
					 var value=parseInt(document.getElementById("gradeBasic"+textbox).value==""?0:document.getElementById("gradeBasic"+textbox).value);	
					
				 document.getElementById("gradeBasic"+i).value = (parseInt(document.getElementById("incrementAmount"+textbox).value==""?0:document.getElementById("incrementAmount"+textbox).value)	+value);
				 document.getElementById("incrementAmount"+i).value = document.getElementById("incrementAmount"+textbox).value;
				 document.getElementById("gradeBasic"+i).readOnly=true;
				
				 
				 calcValueOnBasic("gradeBasic"+i,"daPercentOrFixedValue"+i,"daValueType"+i ,"daValue"+i,"hraPercentOrFixedValue"+i,"hraValueType"+i,"hraValue"+i);
		  
				  
				// calcValueOnBasic(gradeBasic,daPercentOrFixedValue,daValueType,daValue,hraPercentOrFixedValue,hraValueType,hraValue);
				
		//		 gradeBasic,daPercentOrFixedValue,daValueType,daValue,hraPercentOrFixedValue,hraValueType,hraValue
				 textbox++;
				 			

				 }
				 
				 }
			 function checkFlag()
				{	
					
					
					var flag = <%=flag%>;
					if(flag == 1)
					{
						alert("Slab Saved Successfully..!!");
						window.location.href="GradeMaster.jsp";
					}
					
					else if(flag == 2)
						{
						alert(" End date for this slab saved Successfully..!!");
						window.location.href="GradeMaster.jsp";
						}
					
					else if(flag == 0)
					{
						alert("You are out of session Please Login again");
						window.location.href="login.jsp?action=0";
					} 
					
					
				}
			 
          
function indexPage(){
	
	window.location.href="index.jsp";

}
		
function resetPressed() {
	var a=confirm("Are you sure to reset all entered values?");
	if(a)
		return true;
	else
		return false;
} 
//VALIDATING ON SELECTION OF END DATE ITSELF
function validateEndDate(startDate,endDate){
	
	var start = document.getElementById("startDate").value;
	var end = document.getElementById("endDate").value;
	/* alert(start);
	alert(end); */

	start = parseMyDate(start);
	end = parseMyDate(end);
	if (start > end) {
		alert("Please select valid date !");
		document.getElementById("endDate").value = "";
		document.getElementById("endDate").focus();
	}
}

			 

</script>
</head>
 <%
 
 String pageName = "GradeMaster.jsp";
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

<body onload="checkFlag()">
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: auto; max-height: 82%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Grade Details</h1>
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
			<div id="table-content" align="center" >
			
								<form name="grade" action="GradeServlet?action=addGradeData"
										method="post" onSubmit="return validate()">
										
										<table id="customers" style="margin-top:10px;float: none;">
											<tr>
												<th colspan="4">Enter grade details</th>
											</tr>
											<tr class="alt">
						 	<td>Grade Code : <font color="red"><b>*</b></font>	</td>
							
							
										<td colspan="3"><select  name="type" id="type" style="width:400px;" onchange="getRecords()">
										 <option value="0">Select</option>  
				<% 
				String startDate="";
				String endDate="";
				Date tempDate=new Date();
				 String newStartDate="";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
				 
				 if(gradeBean.getVdaValue()!=0.0){
				 startDate=gradeBean.getStartDate();
		         endDate=gradeBean.getEndDate(); 
		         
	              tempDate=simpleDateFormat.parse(startDate); 
	                 
	             startDate= outputDateFormat.format(tempDate);
	             tempDate=simpleDateFormat.parse(endDate);
	             endDate=outputDateFormat.format(tempDate);
	           
	          				 }
				 
				 
					
				 if(maxEndDate!=null){
				 Date date =simpleDateFormat.parse(maxEndDate); 
				

				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 
				 calendar.add(Calendar.DAY_OF_MONTH, 1);
				 date= calendar.getTime();
				  

				 
						newStartDate=  outputDateFormat.format(date);
			   
				 }
				
				 //System.out.println("action is"+request.getParameter("action"));
				 System.out.println("Start date : "+startDate);
				 System.out.println("newStartDate  :"+newStartDate);
				ArrayList<Lookup> result=new ArrayList<Lookup>();
			    LookupHandler lkhp = new LookupHandler();
			    result=lkhp.getSubLKP_DESC("DESIG");
			    for(Lookup lkbean : result){
			     %>
				      <option value="<%=lkbean.getLKP_SRNO()%>"  <%=lkbean.getLKP_SRNO()==Integer.parseInt(type)?"Selected":""%> ><%=lkbean.getLKP_DESC()%></option>  
				      <%
				 }
				     %>
												</select></td>
												</tr>
											
										
											
											<tr class="alt">
											<td>START DATE :  <font color="red"><b>*</b></font></td>
											<%
											 if(!type.equals("0") && gradeBean.getBasic()!=0.0 && !action.equalsIgnoreCase("saveEndDate") ){ %>	
												
											
												<td><input class="form-control" type="text" id="startDate"  name="startDate" value="<%=startDate%>" readonly="readonly">
												
												</td>
																								
												<%}else if( !action.equalsIgnoreCase("saveEndDate") ) {%>
												
											
													<%if(newStartDate!="") {%>
													<td><input class="form-control" type="text" id="startDate"  name="startDate" value="<%=newStartDate%>" readonly="readonly"></td>
													<%} else { %>
												<td><input class="form-control" name="startDate" size="20" id="startDate" 
													type="text" onBlur="if(value=='')" readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" />
												</td>
												<% }%>
												<%} else if(action.equalsIgnoreCase("saveEndDate") ){ %>
											
												<td><input class="form-control" type="text" id="startDate"  name="startDate" value="<%=newStartDate%>" readonly="readonly">
												</td>
												<%}%>
										<td>		  End Date : 
										<%if(!endDate.equals("31-Dec-2098")) {%>
										<input class="form-control" name="endDate" size="20" id="endDate"
													type="text" onBlur="if(value=='')" value="<%=endDate%>"
													 readonly="readonly"/>&nbsp;
													 <%}else{ %>
													 <input class="form-control" name="endDate" size="20" id="endDate"
													type="text" onBlur="if(value=='')" readonly="readonly"
													onchange="validateEndDate('startDate','endDate')">&nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;" 
													onClick="javascript:NewCssCal('endDate', 'ddmmmyyyy')" />
													<% }%>
											<%
											%>		
        
            </td>
												<!-- <td>END  DATE : &nbsp; 
												<td><input name="endDate" size="20" id="endDate"
													type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('endDate', 'ddmmmyyyy')" /></td> -->
											</tr>
								
									
										</table>
									
										<br>
										<br>
										<% if(!type.equals("0") && gradeBean.getBasic()!=0.0 ){ %>	
				
							  <div  align="center" class="imptable " style="overflow-y:auto;  width: 100%;"> 		
							<center>		
									<table id="customers" class="gradetbl"  width="100%" style=" margin-right: 0px;">
               	<tr>
                  <td width="40" align="center"><b>Sr.No. </b></td>
                  <td width="80"><b>Grade Basic</b></td>
                  <td width="100" align="center"><b>Increment Amount</b> </td>
                                    
                     <td width="270"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  DA </b>
                  
                  <br>&nbsp;&nbsp;&nbsp;<font>Value Type</font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <font>  Percentage</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>Amount </font>
                  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <font> /Fixed Value</font>
                  
                  
                  <!-- <b>Value</b> -->
                  </td>
            
                  
                     <td width="270"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 HRA </b>
                  
                  <br>&nbsp;&nbsp;&nbsp; <font>Value Type</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <font>Percentage</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>Amount</font> 
                  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <font>/Fixed Value</font>
                  
                  
                  <!-- <b>Value</b> -->
                  </td>
                   <%if(gradeBean.getVdaValue()!=0.0 ){ %>
                  <td width="130" align="center"><b>VDA Value </b>
                  </td>
               <% }else{%>
                    <td style="display: none;" width="130" align="center">
                    </td>
                    <%} %>
                                    </tr>
                                  </table>
                                  </center>
                       </div>
                  
          <div  align="center" class="imptable"  style="overflow-y:auto; height: 295px;width: 100%;">       
                     
                             	<table id="customers" width="100" style=" margin-right: -15px;">
                             	               
                <!-- new one -->
                <%for (int index=1;index<=36;index++ ){
                
                		gradeBean=gradeHandler.getGradeData(type,index);
                	
             %>
                	<tr>
                     <td width="40" align="center" bgcolor="#FFFFFF" ><input class="form-control"  type="hidden" readonly="readonly" id="<%=index%>" name="<%=index%>"> <%=index%></td>
                     <td  width="80"  align="center" bgcolor="#FFFFFF">
                    <input class="form-control" type="text" size="10" readonly="readonly"  name="<%="gradeBasic"+index%>" readonly="readonly" style="text-align: right;" id="<%="gradeBasic"+index%>" value="<%=gradeBean.getBasic()%>" 
                    onkeyup="calcValueOnBasic('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')" />
                    
                  
   </td>
                     <td  width="100"  align="center">
                     <input class="form-control" class="form-control" type="text"  size="10"  readonly="readonly" name="<%="incrementAmount"+index%>" style="text-align: right;" value="<%=gradeBean.getIncrement()%>"   id="<%="incrementAmount"+index%>"  onkeyup="enterBasicAndIncrement(<%=index%>)"/>
                       </td>
                            <%-- FOR DA--%>
                       
                     <td width="270">	
      
                     <%if(gradeBean.getDaValueType()==0){ %>
                       <input class="form-control" type="text" size="10"  readonly="readonly" name="<%="daValueType"+index%>" style="text-align: center;" value="Fixed Value"   id="<%="daValueType"+index%>"  onchange="calcValue('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>')"/>
                     
                      
                       
                       <%} else{ %>
                      <input class="form-control" type="text" size="10"  readonly="readonly" name="<%="daValueType"+index%>" style="text-align: center;" value="Percentage"   id="<%="daValueType"+index%>"  onchange="calcValue('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>')"/>
                        
                       <%} %>
                  
                     <input class="form-control" type="text" size="10" readonly="readonly" style="text-align: right;" value="<%=gradeBean.getDaPercentOrFixedValue()%>"   name="<%="daPercentOrFixedValue"+index%>" id="<%="daPercentOrFixedValue"+index%>"  onkeyup="calcValue('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>')" />
                     <input class="form-control" type="text" size="10"  readonly="readonly" style="text-align: right;" value="<%=gradeBean.getDaValue()%>"   name="<%="daValue"+index%>"  id="<%="daValue"+index%>"  /> 
                     
                     <!-- <b>Value</b> -->
                     </td>
                          <%-- FOR VDA--%>

                     
                    <%-- FOR HRA--%>
                     
                         <td width="270">
                     
                         <%if(gradeBean.getHraValueType()==0){ %>
                       <input class="form-control" type="text" size="10"  readonly="readonly" name="<%="hraValueType"+index%>" style="text-align: center;" value="Fixed Value"   id="<%="hraValueType"+index%>"  onchange="calcValue('<%="gradeBasic"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')"/>

                       <%} else{ %>
                       <input class="form-control" type="text" size="10"  readonly="readonly" name="<%="hraValueType"+index%>" style="text-align: center;" value="Percentage"   id="<%="hraValueType"+index%>"  onchange="calcValue('<%="gradeBasic"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')"/>
                         
                       <%} %>
                  
                      <input class="form-control" type="text" size="10"  readonly="readonly" style="text-align: right;" value="<%=gradeBean.getHraPercentOrFixedValue()%>"   name="<%="hraPercentOrFixedValue"+index%>"   id="<%="hraPercentOrFixedValue"+index%>"     onkeyup="calcValue('<%="gradeBasic"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')" />
                      <input class="form-control" type="text" size="10"  readonly="readonly" style="text-align: right;" value="<%=gradeBean.getHraValue()%>"   name="<%="hraValue"+index%>"    id="<%="hraValue"+index%>"/> 
                     
                      </td> 
                      <%-- FOR STATUS--%>
                      
                      <%if(gradeBean.getVdaValue()!=0.0 ){ %>
                     <td width="130"  align="center">
            
                <input class="form-control" type="text" size="10"  readonly="readonly" style="text-align: center;" value="<%=gradeBean.getVdaValue() %>" />
          </td>
                       <%}else{%>
                     
                       
                         <td width="130" style="display: none;"  align="center">
                
                   
                     </td>
                     
                       <!-- <b>Value</b> -->
                     
                   </tr>  
                <%} }%>
                  
    
                
             
              </table>
              </div>
             <br>
             <br>  
            <!--   End Date For this Slab : <input name="endDate" size="20" id="endDate"
													type="text" onBlur="if(value=='')"  readonly="readonly">&nbsp;<img
										src="images/cal.gif" align="middle" 
													style="vertical-align: middle; cursor: pointer;" 
													onClick="javascript:NewCssCal('endDate', 'ddmmmyyyy')" /> -->
			<%if(endDate.equals("31-Dec-2098") || endDate.equals("") ) {%>										
          <input class="myButton" type="button" id="saveEndDate" value="Save End Date" onclick=" return validateForEndDate()"/> 
          <input class="myButton" type="reset" id="clear" value="Reset" />  
          <% }%>
               
           
                <%
            EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
            String todaysDate=empAttendanceHandler.getServerDate();
            int loggedEmployee = (Integer)session.getAttribute("EMPNO");
            %>
            <input name="todaysDate" id="todaysDate" type="hidden" value="<%=todaysDate %>" />
						</form>
												
								<br/>
				
										
										<br>
										<br>
									
				<% }else  if(!type.equals("0") && gradeBean.getBasic()==0.0 ){ %>	
				
							  <div  align="center" class="imptable" style="overflow-y:hidden;  width: 100%;"> 		
									
										<table id="customers"  class="gradetbl"  width="100%" style=" margin-right: 0px;">
               	<tr >
                  <td width="40" align="center"><b>Sr.No. </b></td>
                  <td width="80"><b>Grade Basic</b></td>
                  <td width="100" align="center"><b>Increment Amount</b> </td>
                                    
                  <td width="270"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  DA </b>
                  
                  <br>&nbsp;&nbsp;&nbsp;<font> Value Type</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <font> Percentage</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>Amount</font> 
                  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <font>/Fixed Value</font>
                  
                  
                  <!-- <b>Value</b> -->
                  </td>
                  
            
                     <td width="270"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <font>HRA</font> </b>
                  
                  <br>&nbsp;&nbsp;&nbsp; <font>Value Type</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <font>Percentage</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>Amount</font> 
                  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <font>/Fixed Value</font>
                  
                  
                  <!-- <b>Value</b> -->
                  </td>
                 <!--  <td width="130" align="center"><b>Status </b>
               
                    
                    </td> -->
                                    </tr>
                 
                 
                 
                                  </table>
                       </div>
                  
                     <div  align="center" class="imptable"  style="overflow-y:auto; height: 295px;width: 100%;">
                       
                     <table id="customers" width="100" style=" margin-right: -15px;">         
                                        
                <!-- new one -->
                <%for (int index=1;index<=36;index++ ){
                
                		gradeBean=gradeHandler.getGradeData(type,index);
                	
             %>
                	<tr>
                     <td width="40" align="center" bgcolor="#FFFFFF" ><input  type="hidden" id="<%=index%>" name="<%=index%>"> <%=index%></td>
                     <td  width="80"  align="center" bgcolor="#FFFFFF">
                    
                 
                      <%if(index==1){ %>
                     <input class="form-control" type="text" size="10"  name="<%="gradeBasic"+index%>" style="text-align: right;" onkeypress="return inputLimiter(event,'Numbers')" id="<%="gradeBasic"+index%>" value="<%=gradeBean.getBasic()%>" 
                     onkeyup="calcValueOnBasic('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>'),enterBasicAndIncrement(<%=index%>)"
                     />
                    <%}else{ %>
                     <input class="form-control" type="text"  size="10"   readonly="readonly" name="<%="gradeBasic"+index%>" style="text-align: right;" id="<%="gradeBasic"+index%>" value="<%=gradeBean.getBasic()%>" onkeyup="calcValueOnBasic('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')" />
                    
                    <%} %>
                     </td>
                     <td  width="100"  align="center">
                     <input class="form-control" type="text"  size="10" name="<%="incrementAmount"+index%>" onkeypress="return inputLimiter(event,'Numbers')" style="text-align: right;" value="<%=gradeBean.getIncrement()%>"   id="<%="incrementAmount"+index%>"  onkeyup="enterBasicAndIncrement(<%=index%>)"/>
                       </td>
                       <!-- FOR DA-->
                     <td width="270">
                     	
                    <select class="form-control" name="<%="daValueType"+index%>"  id="<%="daValueType"+index%>" onchange="calcValue('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>')" >
                       <option value="0" selected>Fixed Value</option>
                       <option value="1">Percentage</option>
                     </select>
                   <input class="form-control" type="text" size="10" onkeypress="return inputLimiter(event,'Numbers')"  style="text-align: right;" value="<%=gradeBean.getDaPercentOrFixedValue()%>"   name="<%="daPercentOrFixedValue"+index%>" id="<%="daPercentOrFixedValue"+index%>"  onkeyup="calcValue('<%="gradeBasic"+index%>','<%="daPercentOrFixedValue"+index%>','<%="daValueType"+index%>','<%="daValue"+index%>')" />
                     <input class="form-control" type="text" size="10" readonly="readonly"  style="text-align: right;" value="<%=gradeBean.getDaValue()%>"   name="<%="daValue"+index%>"  id="<%="daValue"+index%>"  /> 
                     
                     <!-- <b>Value</b> -->
                     </td>
                       <!-- FOR VDA-->
            
                       <!-- FOR HRA-->
                         <td width="270">
                    <select class="form-control" name="<%="hraValueType"+index%>" id="<%="hraValueType"+index%>" onchange="calcValue('<%="gradeBasic"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')"  >
                       <option value="0" selected>Fixed Value</option>
                       <option value="1">Percentage</option>
                     </select>
                     <input class="form-control" type="text"  size="10" onkeypress="return inputLimiter(event,'Numbers')" style="text-align: right;" value="<%=gradeBean.getHraPercentOrFixedValue()%>"   name="<%="hraPercentOrFixedValue"+index%>"   id="<%="hraPercentOrFixedValue"+index%>"  onkeyup="calcValue('<%="gradeBasic"+index%>','<%="hraPercentOrFixedValue"+index%>','<%="hraValueType"+index%>','<%="hraValue"+index%>')" />
                      <input class="form-control" type="text"  size="10"  readonly="readonly"  style="text-align: right;" value="<%=gradeBean.getHraValue()%>"   name="<%="hraValue"+index%>"    id="<%="hraValue"+index%>"/> 
               
               
                 <%--     
                      </td> 
                     <td width="130"  align="center">
                     <select style="width:70px;" name="<%="status"+index%>" id="<%="status"+index%>" >
                     <option value="-1" selected="selected">New</option>
               <option value="0">Open</option>
                       <option value="1">Closed</option>
                       </select>
                     <!-- <b>Value</b> -->
                     </td> --%>
                     
                     
                   </tr>  
                <% }%>
                  
                 
                
                
                
                
         
                
             
              </table>
              </div>
               <br><br>
              <input class="myButton" type="submit" id="save" value="Save" /> 
            
               <input class="myButton" type="Reset" id="clear" value="Reset" onclick="return resetPressed()"/>  
           
                <%
            EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
            String todaysDate=empAttendanceHandler.getServerDate();
            int loggedEmployee = (Integer)session.getAttribute("EMPNO");
            %>
            <input name="todaysDate" id="todaysDate" type="hidden" value="<%=todaysDate %>" />
						</form>
												
								<br/>
				<% }%>				
					
			                      								
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
