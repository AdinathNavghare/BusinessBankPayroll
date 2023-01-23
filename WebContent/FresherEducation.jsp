<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.*" %>
<%@page import="payroll.Model.*" %>

<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.EmpQualBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style type="text/css">

.form-popup {
	
	display: none;
	position: relative;
	}
.form-popup1 {
	
	display: none;
	position: relative;
	}
.form-popup5 {
	
	display: none;
	position: relative;
	}	
	 
</style>

<script> 
  $(document).ready(function() {
	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4,#txtDate5").datepicker({
	    	changeMonth: true, 
	        changeYear: true, 
	        yearRange: "-90:+00",
	    	showOn: 'button',
	        buttonText: 'Show Date',
	        /* buttonImageOnly: true, */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});
  
  function detect(id){
		 
		 try
		 {
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	var response="";
		 	url="ExprInterviewServlet?action=DeleteEducation&Eid="+id;
		 	let s=confirm("do you want to delete this record?"); /*+ window.location.replace("ProjectViewAll.jsp")  */
			 if(s)
				 {
				 	xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{	
						response = xmlhttp.responseText;
						
						 if(response>0 )
							{
							 alert("Record Deleted Successfully!!!"+ window.location.replace("ExprEducation.jsp"))

							 return false;
							}
						 else
						 {
								alert("Record Does not Deleted........Some Error Occure");	
						 }
						}
					};
					xmlhttp.open("POST", url, true);
					xmlhttp.send();
				 }
			}
			catch(e)
			{
				
			}
	}

function hideF()
{
	  
	  document.getElementById("educationAdd").style.display = "none";
}
  
  
   function openForm3() {
		
		var q=document.getElementById("EDEGREE").value;
		var r=document.getElementById("txt11").value;
		var s=document.getElementById("txt12").value;
		var t=document.getElementById("txt13").value;
		var u=document.getElementById("txt14").value;
		var v=document.getElementById("txt15").value;
		var w=document.getElementById("txt16").value;
		
		 try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";                                         
		 	var response="";                   
		 	url="ExprInterviewServlet?action=AddEducation&EDEGREE="+q+"&degree="+r+"&collage="+s+"&subject="+t+"&passout="+u+"&std="+v+"&marks="+w;
		 	  
				 	xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{	
						response = xmlhttp.responseText;
						
						 if(response=="true" )
							{
							 alert(" Successfully save record!!!")
							 return false;
							}
						 else
						 {
								alert("fail...");	
						 }
						}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
				 
				}
				catch(e){} 	
				document.getElementById("myForm").style.display = "block";	
	} 

  </script>
<style type="text/css">
.topics tr{height : 35px;}	

</style>

<%
	String action = request.getParameter("action") != null ? request.getParameter("action") : "ExprPersonalDetails";
	System.out.println("action is " + action);
%>
</head>
<body style="overflow-y:auto;"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"   >
<!-- start content -->
<div id="content" style="background-color: lightgray; " >

	<!--  start page-heading -->
		<div id="content" align="center"  ><!--  start page-heading -->
			<div id="step-holder" align="center">
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FresherPersonalDetails.jsp" >1</a></div>
			<div class="step-light-left"><a href="FresherPersonalDetails.jsp" >Personal Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FreshersFamilyDetails.jsp" >2</a></div>
			<div class="step-light-left"><a href="FreshersFamilyDetails.jsp">Family Details</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no"><a href="FresherEducation.jsp" style="color: white;">3</a></div>
			<div class="step-dark-left"><a href="FresherEducation.jsp"> Qualification</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			<div class="step-no-off"><a href="FresherReference.jsp" >4</a></div>
			<div class="step-light-left"><a href="FresherReference.jsp">References</a></div>
            <!-- <div class="step-light-right">&nbsp;</div>
            
			<div class="step-no-off"><a href="FreshersAppraisalLetter.jsp" >5</a></div>
			<div class="step-light-left"><a href="FreshersAppraisalLetter.jsp">Interview Appraisal Report</a></div> -->
			<div class="step-light-right">&nbsp;</div>
		
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
			<div id="table-content" align="center">
			
				<table id="customers">
					<tr class="alt">
						<th style="width:850px;">Educational Background</th>
					</tr>
				</table>
				
			<%
if(action.equalsIgnoreCase("EditEducation"))
{
	System.out.print("hi action EditEducation");
	int eid = Integer.parseInt(request.getParameter("Eid"));
	System.out.print("hi eid is  : "+eid);
	String hidediv = request.getParameter("hidediv")!=null?request.getParameter("hidediv"):"";
	ArrayList<ExprInterviewBean1> result1 = new ArrayList<ExprInterviewBean1>();
	ExprInterviewDao rdao1 = new ExprInterviewDao();
	result1=rdao1.EditEducation((int) eid);
	for (ExprInterviewBean1 rs : result1) {
%>			
     <form action="ExprInterviewServlet?action=UpdateEducation" method="Post">
      <input type="hidden" name="eid" id="eid" value="<%=eid%>">
		<div>
			<table width="861" border="1" bordercolor="#000000" id="customers" class="topics">
			<tr class="alt">
			<td style="text-align: center;">Qualification</td>
				<td>
					 	<select name="EDEGREE" id="EDEGREE" value="<%=rs.getQuali() %>" >  
      					 <option value="<%=rs.getQuali() %>"><%=rs.getQuali() %></option>  
    						<%
    					
  							  ArrayList<Lookup> result5=new ArrayList<Lookup>();
    						LookupHandler lkhp5= new LookupHandler();
    						result5=lkhp5.getSubLKP_DESC("ED");
    						EmpQualBean Qualbean=new EmpQualBean();
    						
    						for(Lookup lkbean : result5)
 							{				
    						
     						if(lkbean.getLKP_SRNO()== Qualbean.getDEGREE())
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
					<td style="text-align: center;">Degree</td>
					  <td ><input type="text" style="width:90%; " id="txt11" name="degree" value="<%=rs.getDegree()%>"></td>
			</tr>
					<tr class="alt">
					 <td style="text-align: center;">University/College</td>
					  <td colspan="3"><input type="text" style="width:90%; " id="txt12" name="collage" value="<%=rs.getCollage()%>"></td>
					  
					</tr>
					
					<tr class="alt">
					<td style="text-align: center;">Discipline/Subjects/Specialization</td>
					  <td><input type="text" style="width:90%; "id="txt13" name="subject" value="<%=rs.getSpecial()%>"></td>
					 
					 <td style="text-align: center;">Year of Passing</td>
					  <td><input type="text" style="width:90%; "id="txt14" name="passout" value="<%=rs.getYear() %>"></td>
					
					</tr>
					<tr   class="alt">
					 <td style="text-align: center;">Class</td>
					  <td><input type="text" style="width:90%; "id="txt15" name="std" value="<%=rs.getStd()%>"></td>
					
					<td style="text-align: center;">% Marks</td>
					  <td ><input type="text" style="width:90%; "id="txt16" name="marks" value="<%=rs.getMarks()%>">
					  </td>
					 	</tr>
					
					
					<tr class="alt"><td colspan="4" align="center"><input type="submit" class="myButton"  value="Update"   /> 
				&nbsp; &nbsp;
			<input type="reset" class="myButton"  value="Cancel"/></td></tr>                        
				<tbody class="form_data3" style="background-color: white;">
			  </tbody>
					
		  </table>	
		  
		</div>
		</form>
	
	
				
		<%}
	if(hidediv.equalsIgnoreCase("yes"))
	{
	%>
	<div id="educationAdd"  hidden="true" style="width: 100%;">
	<%}
	else{
	%>
	<div id="educationAdd" style="width: 100%;"   >
	<%}}%>		
				
				
				
				
				
				
   
   <div id="educationAdd" >
     <form action="#" method="Post">
      
		<div>
			<table width="861" border="1" bordercolor="#000000" id="customers" class="topics">
			<tr class="alt"><td style="text-align: center;">Qualification</td>
				<td>
					 	<select name="EDEGREE" id="EDEGREE" >  
      					 <option value="none">Select</option>  
    						<%
    					
  							  ArrayList<Lookup> result2=new ArrayList<Lookup>();
    						LookupHandler lkhp2 = new LookupHandler();
    						result2=lkhp2.getSubLKP_DESC("ED");
    						EmpQualBean empQualbean=new EmpQualBean();
    						
    						for(Lookup lkbean : result2)
 							{				
    						
     						if(lkbean.getLKP_SRNO()== empQualbean.getDEGREE())
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
					<td style="text-align: center;">Degree</td>
					  <td ><input type="text" style="width:90%; " id="txt11" name="degree"></td>
			</tr>
					<tr class="alt">
					 <td style="text-align: center;">University/College</td>
					  <td colspan="3"><input type="text" style="width:90%; " id="txt12" name="collage"></td>
					  
					</tr>
					
					<tr class="alt">
					<td style="text-align: center;">Discipline/Subjects/Specialization</td>
					  <td><input type="text" style="width:90%; "id="txt13" name="subject"></td>
					 
					 <td style="text-align: center;">Year of Passing</td>
					  <td><input type="text" style="width:90%; "id="txt14" name="passout"></td>
					
					</tr>
					<tr   class="alt">
					 <td style="text-align: center;">Class</td>
					  <td><input type="text" style="width:90%; "id="txt15" name="std" ></td>
					
					<td style="text-align: center;">% Marks</td>
					  <td ><input type="text" style="width:90%; "id="txt16" name="marks">
					  </td>
					 	</tr>
					
					
					<tr class="alt"><td colspan="4" align="center"><input type="button" class="myButton"  value="Submit"  onclick="openForm3()" /> 
				&nbsp; &nbsp;
			<input type="reset" class="myButton"  value="Cancel"/></td></tr>                        
				<tbody class="form_data3" style="background-color: white;">
			  </tbody>
					
		  </table>	
		  
		</div>
		<br><br>
		
		
		<div class="form-popup" id="myForm" align="center">
		<form action="#" class="form-container">
		<!-- <div style="overflow-y:scroll;height:120px;"> -->
		<table id="customers" align="center"  class="tablecontainer" width="74%"> <!--style="font-size: 12;"  -->
		<tbody>
		<tr ><td colspan="9" align="center" >All Information</td></tr>
				<tr style="border:solid;   border-color: black;">
				<th width="30">Action</th >
                   	<th width="20">Education Id</th >
                   	<th width="20">Qualification</th>
		        	<th width="50">Degree</th>
		        	<th width="100">Collage</th>
		        	<th width="100">Special Subject</th>
		       	   <th width="70">Passing Year</th>
		       	   <th width="50">Class</th>
	       			<th width="30">Marks</th>
		     		
</tr>
</tbody>
</table>
<div style="overflow-y: scroll; height: 120px;width:970px;">
<table id="customers"  align="center" class="tablecontainer">
<tr class="alt">
		<%
		
   		 ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		ExprInterviewDao edao = new ExprInterviewDao();
			result=edao.getQualificationDetail();
	  
			  for(ExprInterviewBean1 rss : result)
			  {
   %>           
				   <tr>
					<td width="100" align="center">
					
				    <%-- <a href="#" class="btn btn-default btn-xs purple" onclick="detect(<%=rss.getExprid()%>)"><i class="fa fa-trash-o"></i></a> --%>
					<a href="#" class="btn btn-default btn-xs purple" onclick="window.document.location.href='ExprEducation.jsp?action=EditEducation&hidediv=yes&Eid=<%=rss.getEid()%>'" ><i class="fa fa-edit"></i></a></td>
					 
					 <td width="43"><%=rss.getEid()%></td> 
				    <td width="84"><%=rss.getQuali()%></td> 
					<td width="70"><%=rss.getDegree() %></td>     
					<td width="100"><%=rss.getCollage() %></td>
					 <td width="100"><%=rss.getSpecial() %></td>    
			        <td width="70"><%=rss.getYear() %></td>
			        <td width="100"><%=rss.getStd() %></td> 
			        <td width="100"><%=rss.getMarks()%></td>   
					
					</tr>
					<%} %>
	 
				</table>
			</div>
		</form>
	</div>
		
		
		</form>
		
		
		
	</div>
		
		</div>
			<!--  end table-content  -->
	
			<!-- <div class="clear"></div> -->
		 
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
	<!-- <div class="clear">&nbsp;</div> -->

</div>
<!--  end content -->
<!-- <div class="clear">&nbsp;</div> -->
</div>
<!--  end content-outer........................................................END -->
</div>
<!-- <div class="clear">&nbsp;</div> -->
		
		<div class="clear">&nbsp;</div>

    
<!-- <script>
function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}

function show(Eid) {
	alert("hi");
	document.getElementById("showE").style.display = "block";
	 document.getElementById("addE").style.display = "none";

}

/* function closeForm() {
  document.getElementById("addE").style.display = "none";

}
 */

</script>
 -->
</body>
</html>