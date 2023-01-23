
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yearly Bonus Details </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script src="js/filter.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

<%  
String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);
String action=request.getParameter("action")==null?"":request.getParameter("action");
int year=0,nextYear=0;
%>


var checkflag="false";
function check5(field) {
	
  if (checkflag == "false") {
	  for (i = 0; field.length > i; i++) {
      field[i].checked = true;
    }
    checkflag = "true";
    return "Uncheck All";
  } else {
    for (i = 0; field.length > i; i++) {
      field[i].checked = false;
    }
    checkflag = "false";
    return "Check All";
  }
}




function validate(){
	var date = document.getElementById("date").value;
	if(date==0){
		alert("please select the Date !");
		document.getElementById("date").focus();
		return false;
	}
	else
		{
		document.getElementById("act").value="active";
		}
}

function validate2(){
	var date = document.getElementById("date").value;
	if(date==0){
		alert("Please Select The Date !");
		document.getElementById("date").focus();
		return false;
	}
	else
		{
		document.getElementById("act").value="nonactive";
		
		return true;
		}
}

function validate1(){
	
	var date = document.getElementById("date").value;
	if(date==0){
		alert("Please Select The Date !");
		document.getElementById("date").focus();
		return false;
	}
	
	var date1 = document.getElementById("date1").value;
	if(date1==0){
		alert("Please Select The Month For Applicable Bonus !");
		document.getElementById("date").focus();
		return false;
	}
	else{
		 var a=confirm("Are you sure");
		 
		 if(a==true){
			 
			 var b=prompt("Type YES for active And NO for Nonactive Employee(s)");	
				if(b == "yes" || b == "Yes" || b == "YES"){
					
					 document.getElementById("applicable").value="Y";	
					 document.getElementById("act").value="active";
				

				} else {
					document.getElementById("applicable").value="Y";	
					 document.getElementById("act").value="nonactive";
				}
			
	}
	}
}

jQuery(function() {
$("#post").click(function(){

	var result=  updateCount();
 
	 	 function updateCount () {
	 		   var count = $("input[type=checkbox]:checked").size();
	  			   if(count==0){
	    							alert("At least select one employee");
	    				 			return false;
	     					}	else{
	    	 						return true;
	   								  }
						};
			if(result==true)
			var r = confirm("Are you sure to Post Bonus of these employee ?");
 
			if (r == true && result==true) {
	
	 	 		var b=prompt("Type YES for Posting the Bonus to Employee(s)");	
		
	 	 		if(b == "yes" || b == "Yes" || b == "YES"){
							return true;
							} 
							else{
								return false;
								}
				} 
else{
	return false;
}

});
 
 
});
 function checkBonus() {
		
	
	
	var f = parseInt(document.getElementById("flag").value);


	if (f == 2) {
		alert("Bonus Posted Successfully");
	
	}
	if(f==3){
		alert("Bonus  is not posted. Please apply again");
		
		
	}
	
	if(f==4){
		alert("Bonus  is Given to employees. Go for post and calculate salary");
		
		
	}

}

 function changeYear(){
 	if(document.getElementById("date").value=="")
 	{
 	alert("Please select year");
 	return false;
 	}
 	var date=document.getElementById("date").value;
 	document.getElementById("toYear").value=parseInt(date)+1;	
 }
	
 
</script>

  <script type="text/javascript">    
	function ABC(checktoggle)
	{
		/*   if(checktoggle)	{
			  
			  document.getElementById("save").disabled = false;
			  document.getElementById("uncheckAll").disabled = false;
		  }
		  else{
			  document.getElementById("save").disabled = true;
		  } */
		  var checkboxes = new Array(); 
		
		  checkboxes = $("input[type='checkbox']");
		  for (var i=0; i<checkboxes.length; i++)  {
		    if (checkboxes[i].type == 'checkbox')   {
		      checkboxes[i].checked = checktoggle;
		    }
		  }
	  //alert(checkboxes.length);
	}
	</script>
<%
//ArrayList <String> bonusMonths = new Sal_DetailsHandler().getBonusMonths(0, "all");
int eno = (Integer)session.getAttribute("EMPNO");
%>
 </head>
<body onload="checkBonus()" style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" align="left">
<!-- start content -->
<div id="content"  style="margin-left: -10px;">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Bonus calculation</h1>
	</div>
	<!-- end page-heading -->

	<table  border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	
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
		<div id="content-table-inner" >
			<!--  start table-content  -->
			<div id="table-content">
			<center>
				<%	if(action.equalsIgnoreCase("showBonus"))
{ %>

<form  name="form" action="BonusServlet?action=bonusCal"  method="post"> 
  <div id="scrolling"	style="height: 500px; overflow-y: scroll; width: 1570px;
			 background-color: #FFFFFF;"align="center">
<table id="customers" style="width: 450px;" >

	
		<!-- <table id="customers"  align="center">	 -->	
		   <tr> 
		   	<th colspan="16" >Bonus Provision</th>
		   	</tr>
		   <tr class="alt" >
		
<%
String stringYear=(request.getParameter("date")==null?(String)session.getAttribute("year").toString():request.getParameter("date"));
year=Integer.parseInt(stringYear);
nextYear=year+1; 
float percent=Float.parseFloat(request.getParameter("percent")==null?"0.0":request.getParameter("percent"));
ArrayList<BonusBean> list = new ArrayList<BonusBean>();
BonusHandler bonusHandler=new BonusHandler();
list = bonusHandler.getEmployeeDetailsFromBonusCal(year,percent);
String string="";
%>

<td colspan="8" style="text-align: center;" >FOR FINANCIAL YEAR : <b><%=year%>-<%=nextYear%> </b>					                 		


			</td>
<td colspan="8"  style="text-align: center;"> <b>SELECTED PERCENTAGE : 
<input type="text" id="percent" name="percent" value=<%=percent %> readonly="readonly"> </b></td>
</tr>

<!-- <table id="customers" style="width:1000px" align="center"> -->
	<tr class="alt">
		<th colspan="16">Bonus Details</th>
	</tr>
	
		
				
			<tr>
				<th width="10">Employee Code</th>
				<!-- <th width="5">Branch</th> -->
				<th width="30">Name</th>
				<th width="20">Apr-<%=year%1000%></th>
				<th width="30">May-<%=year%1000%></th>
				<th width="30">Jun-<%=year%1000%></th>
				<th width="30">Jul-<%=year%1000%></th>
				<th width="30">Aug-<%=year%1000%></th>
				<th width="30">Sep-<%=year%1000%></th>
				<th width="30">Oct-<%=year%1000%></th>
				<th width="30">Nov-<%=year%1000%></th>
				<th width="30">Dec-<%=year%1000%></th>
				<th width="30">Jan-<%=nextYear%1000%></th>
				<th width="30">Feb-<%=nextYear%1000%></th>
				<th width="30">Mar-<%=nextYear%1000%></th>
				<th width="30">Amount For Bonus</th>
				<th width="20">Bonus</th>
																
																
		
		
   </tr>
	<tr>
		
			<!-- <div id="scrolling"	style="height: 380px; overflow-y: scroll; width: 1200px;
			 background-color: #FFFFFF;"align="center"> -->
		<%if(list.size()!=0){ %>
																							

					
				<% System.out.println("before  for loop " + list.size());
					int count=1;
						for(BonusBean bonusBean: list){
						
						//	System.out.println("jun_"+bonusBean.getEmpno());
							//BonusBean bonusBean=list.get(i);
				%>		
						
						<td width="10" align="center">
						
						<input type="checkbox" id="checklist" name="checklist"  value="<%=bonusBean.getEmpno()%>" size="4"
						style="margin-left: -11px; "  checked onclick="return false;"  >
							<input style="text-align: center;margin-right: -9px;" type="text" id="code_<%=bonusBean.getEmpno()%>" name="code_<%=bonusBean.getEmpno()%>"  value="<%=bonusBean.getEmpCode()%>" readonly="readonly" size="4" />
										
						<%-- </td>
						<td width="5" align="center">
							<input type="text" id="site_<%=bonusBean.getEmpno()%>" name="site_<%=bonusBean.getEmpno()%>" value="<%=count++%>" readonly="readonly" size="8" >
						</td> --%>
						<td width="30" align="center">
							<input type="text" id="name_<%=bonusBean.getEmpno()%>" name="name_<%=bonusBean.getEmpno()%>" 
								  style="text-align: center;"
								   value="<%=bonusBean.getEmpName()%>" readonly="readonly" size="30" >
							<%-- <span id="name_<%=bonusBean.getEmpno()%>"> <%=bonusBean.getEmpName()%> </span> --%>
						</td>
						<td width="20" align="center">									
							<input type="text" id="apr_<%=bonusBean.getEmpno()%>" name="apr_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getApramt()%>" readonly="readonly" size="8" >								
						</td>
						<td width="30" align="center">
							<input type="text" id="may_<%=bonusBean.getEmpno()%>" name="may_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getMayamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="jun_<%=bonusBean.getEmpno()%>" name="jun_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getJunamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="jul_<%=bonusBean.getEmpno()%>" name="jul_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getJulamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="aug_<%=bonusBean.getEmpno()%>" name="aug_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getAugamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="sep_<%=bonusBean.getEmpno()%>" name="sep_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getSepamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="oct_<%=bonusBean.getEmpno()%>" name="oct_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getOctamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="nov_<%=bonusBean.getEmpno()%>" name="nov_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getNovamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="dec_<%=bonusBean.getEmpno()%>" name="dec_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getDecamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="jan_<%=bonusBean.getEmpno()%>" name="jan_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getJanamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="feb_<%=bonusBean.getEmpno()%>" name="feb_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getFebamt()%>" readonly="readonly" size="8" >
						</td>
							<td width="30" align="center">
							<input type="text" id="mar_<%=bonusBean.getEmpno()%>" name="mar_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getMaramt()%>" readonly="readonly" size="8" >
						</td>		
						<td width="30" align="center">
							<input type="text" id="amt_<%=bonusBean.getEmpno()%>" name="amt_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getAmtForBonus()%>" readonly="readonly" size="10" >
						</td>
						<td width="20" align="center">
							<input type="text" id="bon_<%=bonusBean.getEmpno()%>" name="bon_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getBonus()%>" readonly="readonly" size="10" >
						</td>	
					<%	 %>
					
					</tr>
					
						
				<% 
					}
				%>	
		
		<%}else{ %>
				<tr >No Records found</tr> 
		<% }%>
			<!-- </div> -->

	
					
	
		
<!-- 	<tr>
		<td align="left" bgcolor="#1F5FA7">&nbsp;</td>
	</tr> -->
<!-- </table> -->
<!-- </td>
</tr> -->
<!-- </table> -->		
</table>
</div>
		
			 <input class="myButton" type="submit" id="save" name ="save" value="Save"  />
			<!-- <input  class="myButton" type="submit" id="finalize" name ="finalize" value="Finalize" /> -->
			<!-- <input type="button" id="checkAll" value="Check All" onclick="ABC(true)" /> -->
		
</form>


<%}else{ %>
			<table>
					<tr>
						<td>
						<form  action="YearlyBonusReport.jsp?action=showBonus" method="Post" ">
							<table id="customers" width="1200" align="center">		
							   <tr> 
							   	<th colspan="16">Bonus Provision</th></tr>
							   <tr class="alt" >
								<!-- <td align="center"> -->
								 	<!-- <span> Financial Year </span>	 -->			 	
								 	
	<td style="width:300px; text-align:center;"><b>For The Financial Year : April-</b></td>
		<td>	<select id="date" onchange="changeYear()" name="date">		
		
						<option value="" selected>Select</option>	
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>
						<option value="<%=dateInInt%>"><%=dateInInt%></option>
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
</select>

		</td>
		<td style="width:80px; text-align:center;"><b>To March-</b></td>
		<td>
		<input type="text" name="toYear" id="toYear" 
									style="width: 40px;" readonly="readonly" />
		</td>
		<td>
									                 		
									<span>Select Percentage % </span>
									<input type="text" id="percent"  name ="percent" maxlength="5" size="5" onkeypress="return inputLimiter(event,'Numbers')"/>
								</td>
			
							   </tr>
						
			<tr><td  colspan="5"><center> <input class="myButton" type="submit"  value="Submit" onclick="ABC(true)" >
			</center></td>
			
				</tr>	
					    </table>
				</form>
				</td> </tr></table>
		<% }%> 
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
				
		<!--  start table-content  -->
		<div id="table-content">
			<center>
				<div>
					<!-- <h2>Profession Tax Statement</h2> -->
	
				</div>
				<br></br>
				
			</center>	
			
				
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
<!--  end content-table-inner........................................................END -->	
<div class="clear">&nbsp;</div>
<!-- End content -->
 
<!-- End content-outer ........................................................................................................................START -->
</body>
</html>
