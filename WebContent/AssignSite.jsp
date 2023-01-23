<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="payroll.Core.ReportDAO"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Assign Site </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">
jQuery(function() {
	$("#EMPNO").autocomplete("list.jsp");
});  
</script>

<script>
jQuery(function() {
	$("#EMPNO1").autocomplete("list.jsp");
});
</script>


<script>
function validate(){
	
var employee=document.getElementById("EMPNO").value;
	
	if(employee=="")
		{
		alert("Select an employee");
		document.getElementById("EMPNO").focus();
		return false;
		}
	

	var result=  updateCount();
	 
	  function updateCount () {
	    var count = $("input[type=checkbox]:checked").size();
	     if(count==0){
	    	alert("At least select one Site..!");
	    	 return false;
	     }else{
	    	 return true;
	     }
	};
	
	var checkedValue = null; 

	var inputElements = document.getElementsByName('checkList');
	
	var k=0;
	for(var i=0; inputElements[i]; ++i){ 
	      if(inputElements[i].checked){k++;
	    	  checkedValue = inputElements[i].value;
	    	 /*  alert(+k+" selected Site is:- "+checkedValue); */
	           
	      }
	      
	      
	      
	}
	
	
	if(result==true)
		var r = confirm("Are you sure to assign this sites ?");
		 
		if (r == true && result==true) {
			return true;
	
		} else{
			return false;
		}
	
}

function checkSite() {
	
	 
	
	var f = parseInt(document.getElementById("flag1").value);

	if (f == 1) {
		alert("Site Assigned Successfully");

	}

	if (f == 2) {
		alert("Successfully deleted the assigned sites..!");

	}

	
	if(f==4){
		alert("Action is not completed .. Please try again");
		}

}


var expanded = false;
function showCheckboxes() {
    var checkboxes = document.getElementById("checkboxes");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}
function getChecked(){
	
	var employee=document.getElementById("EMPNO").value;
	if(employee=="")
	{
	alert("Select an employee");
	document.getElementById("EMPNO").focus();
	return false;
	}
	
	window.location.href="EmpAttendServlet?action=getChecked&empNo="+employee;
	
}
function deletesite(){
	

	var employee=document.getElementById("EMPNO").value;
	if(employee=="")
	{
	alert("Select an employee");
	document.getElementById("EMPNO").focus();
	return false;
	}
	var r= confirm("Are you sure to delete all Assign Site to this employee..!");
	if(r==true){
	window.location.href="EmpAttendServlet?action=deleteAssignSite&empNo="+employee;
	}
	else{
		return false;
	}
}

<%
String empNumber="";
String action= request.getParameter("action")==null?"":request.getParameter("action");
String flag= request.getParameter("flag")==null?"":request.getParameter("flag");
//System.out.println(action);
ArrayList <TranBean>siteList=new ArrayList<TranBean>();
ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
int srno=1;
/* if(request.getParameter("action")!=null)
action=request.getParameter("action"); */
//System.out.println("the action is"+action);
EmployeeHandler EAH=new EmployeeHandler();
String str_disp = "";
if(action.equalsIgnoreCase("display" ))
{	
if(request.getParameter("empno")!=null){
empNumber= request.getParameter("empno").trim();
//System.out.println("empno on jsp"+empNumber);
EmployeeBean ebean=EAH.getEmployeeInformation(empNumber);
str_disp=ebean.getFNAME()+" "+ebean.getLNAME()+":"+ebean.getEMPCODE()+":"+ebean.getEMPNO();


}
}

		
		%>
</script>
</head>
<body  onLoad="checkSite()" > 
 


<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto; max-height:83%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Site Assign </h1>
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
			
			
			<form name="assign_site" action="EmpAttendServlet?action=assignSite" method="post" onsubmit="return validate()">
			<div class="imptable">
		<table id="customers" style="width: 900px">

					<tr class="alt">

						<th colspan="3">Assign Site</th>
					
		</tr>
		<tr class="alt">
			<td width="200" >SELECT EMPLOYEE  NAME OR ID &nbsp; <font color="red"><b>*</b></font>	</td>
							<td width="100" ><input type="text" name="EMPNO" size="40" id="EMPNO"title="Enter Employee Name" value="<%= str_disp%>" >
						 <input type="button"	value="Get Assigned Sites" onclick="getChecked()"/> </td>
					</tr>			
						
		 <!--   <select style="width: 700px;" name="branch" id="branch" >  
      					 <option value="All">All</option>  
    				 </select>
    				 <div class="overSelect"></div>
    				</div> 
    				    				<div style=  " width:700px; height: 200px; overflow: auto; "  id="checkboxes">	 -->
    				
    				<%
    				if(action.equalsIgnoreCase("display"))
    						{	
    					    	%>
    					    	<tr class="alt">
						
							<td width="200">BRANCH / PROJECT NAME</td>
		  <td style="width: 700px">
		  
		  <div class="multiselect">
		  <div class="selectBox" onclick="showCheckboxes()" style="width:680px; height: 250px; overflow: auto; ">				
    			<%
    				if(request.getAttribute("ProjectList") != null)
    					siteList=(ArrayList <TranBean>)request.getAttribute("ProjectList");			
    			//	System.out.println("CHECK"+siteList.size());	
    					
    						EmpOffHandler ofh = new EmpOffHandler();
    						list=ofh.getprojectCode();
    					  
    						for(EmpOffBean lkb :list)
 							{
    					
    							boolean check=false;
 							for(TranBean tb:siteList){
      						
      							if(lkb.getPrj_srno()==Integer.parseInt(	tb.getSite_id())){
      								check=true;
      							//	System.out.println(tb.getSite_id());
      							}
      						}	
      					
      							if(check){	%>
          						<label style="color: black;" for="<%=lkb.getPrj_srno()%>">
          					
          						<input type= "checkbox" id="<%=lkb.getPrj_srno()%>" onclick="showSelected()" 
          						name="checkList" value="<%=lkb.getPrj_srno()%> :: <%=lkb.getPrj_name()%> " checked="checked"/>&nbsp;<%=lkb.getPrj_name()%> </label>   
          						  
         					 	<%
      							}
      							else
      							{
      								%>	<label style="color: black;" for="<%=lkb.getPrj_srno()%>">
      	          					
              						<input type= "checkbox" id="<%=lkb.getPrj_srno()%>" onclick="showSelected()" 
              						name="checkList" value="<%=lkb.getPrj_srno()%> :: <%=lkb.getPrj_name()%> " />&nbsp;<%=lkb.getPrj_name()%> </label>   
      						<%	}
      							
     					 	%>
     			<br>
     			<% 
}
    						%>	
    						</div>
	  </div>
		  
		  </td>		</tr>
    						
    						
    						<tr class="alt">
    						<td width="200" >ASSIGNED SITES :&nbsp; 	</td>
    						<td width="100">  
    						 <div class="selectBox" onclick="showCheckboxes()" style="width:680px; height:120px; overflow: auto; ">
    						
    						<% if(siteList.size()!=0){
    								for(EmpOffBean lkb :list)
    							{
    						for(TranBean tb:siteList){
    							if(lkb.getPrj_srno()==Integer.parseInt(	tb.getSite_id())){
    							%>
    								<br><%=srno++%>&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<%=lkb.getPrj_name()%>
    							
    					<% }} }}
    						else{%>
    						<br><br><br>
    						<font style="color: red;" size="2">No site is assigned to this employee..!</font>
    					<%} %>
    					
    					</div>
    						 </td>
    						 
    						</tr>	
    						
	
		  
		  </td>		</tr>	
		  <tr class="alt">
						<td align="center" colspan="2">
						 <input type="button" value ="Delete All Assign Site" onclick="deletesite()" title="to delete all assigned sites to selected employee click here " >
						 &nbsp;&nbsp;&nbsp;
						<input type="submit"	title="click to assign sites..!" value="Submit" onclick=" "/>
						</td>
							</tr>									
					
    						<%}%>
    						
			
						<!-- <td style="display:none;">Report Type </td> -->
		
						
						
				
		</table>
		</div>
		</form>
</center>
<br>

			  
</div>
  <input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
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