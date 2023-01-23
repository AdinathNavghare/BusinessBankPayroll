<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Apply Bonus © copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />
<script src="js/filter.js"></script>
<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->
</style>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="css/jquery.treeview.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.treeview.js" type="text/javascript"></script>

 <script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
    
    function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890.';
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
    function checkFlag() {		
		var f = parseInt(document.getElementById("flag").value);
		if (f == 1) {
			alert("Bonus Saved Successfully");
		}
		if (f == 2) {
			alert("Error in Saving Details !");
		}
    }
</script>

<script type="text/javascript">

	var xmlhttp;
	var url="";
	var date;
	
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

function ApplyBonus()
{
	if(numList != "")
	{
		
		var month = document.getElementById("month").value;
		var percent = document.getElementById("percent").value;
		alert(month);
	
	if(month == "0" )
	{
		alert("Please Select Salary Month");	
		return false;
	}
	if(percent == "" )
	{
		alert("Please Select Salary Percentage ( % ) ");
		return false;
	}
	
	var resp=confirm("Are You Sure to Apply Bonus?");
		if(resp==true)
		{
			document.getElementById("process").hidden=false;
			url="BonusServlet?action=ApplyBonus&list="+numList+"&date=01-"+month+"&percent="+percent;
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					alert(response);
					document.getElementById("process").hidden=true;
					initAll();
				}
				
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
		}
	}
	else
	{
		alert("No Employee is Selected");
	}
}


function callback()
{
	if(xmlhttp.readyState==4)
	{
		var response = xmlhttp.responseText;
		document.getElementById("process").style.display="block";
	}
}

function applyBonusFilt()
{
	var month = document.getElementById("month").value;
	var percent = document.getElementById("percent").value;
	
	if(month == "0" )
	{
		alert("Please Select Salary Month");	
		return false;
	}
	if(percent == "" )
	{
		alert("Please Select Salary Percentage ( % ) ");
		return false;
	}
	
	getFilter('toPayCal',month);
	
}
</script>
 <script>
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
		  if(checktoggle)	{
			  
			  document.getElementById("save").disabled = false;
			  document.getElementById("uncheckAll").disabled = false;
		  }
		  else{
			  document.getElementById("save").disabled = true;
		  }
		  var checkboxes = new Array(); 
		
		  checkboxes = $("input[type='checkbox']");
		  for (var i=0; i<checkboxes.length; i++)  {
		    if (checkboxes[i].type == 'checkbox')   {
		      checkboxes[i].checked = checktoggle;
		    }
		  }
	  
	}
	
	
	 function enableButton(){	

			/* var checkboxes = $("input[type='checkbox']"),
			submitButt = $("input[type='submit']"); */
			
			    if ($("input[type='checkbox']").attr('checked')==true){ 
			        //do something
			    	$("#save").attr("disabled",false);
			    }
			    else
			    	{
			    	$("#save").attr("disabled",true);
			    	}
			
			/* checkboxes.click(function() {
				   submitButt.attr("disabled", !checkboxes.is(":checked"));
			}); */
			 
		} 
    </script>

<%
try
{
	int year=0,nextYear=0;

String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);
String action=request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<BonusBean> list = new ArrayList<BonusBean>();
BonusHandler bonusHandler=new BonusHandler();
String flag = "";
flag = request.getParameter("flag")==null?"0":request.getParameter("flag");

%>


<%
	String pageName = "ApplyBonus.jsp";
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

</head>
<body onload="checkFlag()" > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Apply Bonus</h1>
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
				
			<%	if(action.equalsIgnoreCase("showBonus"))
{ %>
 <form  name="form" action="BonusServlet"  method="post" > 
   <div id="scrolling"	style="height: 400px; overflow-y: scroll; width: 1200px;
			 background-color: #FFFFFF;"align="left">
				<table id="customers" style="width: 1200px;" >
			
					
						
						   <tr> 
						   	<th colspan="5">Apply Bonus</th></tr>
						   <tr class="alt" >
							
		 <%
		 String stringYear=(request.getParameter("date")==null?(String)session.getAttribute("year").toString():request.getParameter("date"));
		 year=Integer.parseInt(stringYear);
		  nextYear=year+1; 
		 //float percent=Float.parseFloat(request.getParameter("percent")==null?"0.0":request.getParameter("percent"));
			 list = bonusHandler.getEmployeeDetailsByCode(year);
		
		 %>
		 
		  <td style="text-align: center;" colspan="5" >FOR FINANCIAL YEAR : <b><%=year%>-<%=nextYear%> </b>					                 		
				

							</td>
		<%-- <td style="text-align: center;"> <b>SELECTED PERCENTAGE : <%=percent%> </b></td> --%>

				</tr>
					<tr>
						<th colspan="5">Bonus Details</th>
					</tr>
				
						
							<tr>
								<th width="50px">EmpCode</th>
								<th width="100px">Name</th>
								<th width="100px">Amount For Bonus</th>
								<th width="100px">Bonus</th>												
																				
							</tr>
						
						
				  
					
						
						<%if(list.size()!=0){ %>
				
			
								<% 
									for(BonusBean bonusBean: list){
										//System.out.println("in for loop " + list.size());
								%>		
										<tr>
										<td width="50px" align="center">
											<input type="checkbox" id="checklist" value="<%=bonusBean.getEmpno()%>" 
											       name="checklist" onclick="enableButton()" /> 
											<input type="text" id="code_<%=bonusBean.getEmpno()%>" name="code_<%=bonusBean.getEmpno()%>"  value="<%=bonusBean.getEmpCode()%>" readonly="readonly" size="6" >			
										</td>
										<td width="100px" align="center">
											<input type="text" id="name_<%=bonusBean.getEmpno()%>" name="name_<%=bonusBean.getEmpno()%>" 
												  style="text-align: center;"
												   value="<%=bonusBean.getEmpName()%>" readonly="readonly" size="50" >
											<%-- <span id="name_<%=bonusBean.getEmpno()%>"> <%=bonusBean.getEmpName()%> </span> --%>
										</td>
										<td width="100px" align="center">									
											<input type="text" id="amt_<%=bonusBean.getEmpno()%>" name="amt_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getAmtForBonus() %>" readonly="readonly" size="10" >								
										</td>
										<td width="100px" align="center">
											<input type="text" id="bon_<%=bonusBean.getEmpno()%>" name="bon_<%=bonusBean.getEmpno()%>" value="<%=bonusBean.getBonus() %> "  readonly="readonly" size="10" >
										</td>			
										
									
									</tr>
												
								<% 
									}
								%>	
																																
								
						<%}else{ %>
								<tr >No Records found</tr>
						<% }%>
							

					
					
		
					    </table>		

</div>
<input type="button" class="myButton" id="checkAll" value="Check All" onclick="ABC(true)" /> 
							 <input type="button" class="myButton" id="uncheckAll" value="UnCheck All"  onclick="ABC(false)" disabled="disabled" />  
					
							 <input type="hidden"  id="action" name="action"  value="saveDetails"  />  
							 <input type="submit" class="myButton" id="save" name ="save" value="Save" disabled="disabled" />
</form>
<%}else{ %>
				<table>
					<tr>
						<td>
						<form  action="ApplyBonus.jsp?action=showBonus" method="Post" onSubmit="return TakeCustId()">
							<table id="customers" width="553" align="center">		
							   <tr> 
							   	<th colspan="5">Apply Bonus</th></tr>
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
<!-- 		<td>
									                 		
									<span>Select Percentage % </span>
									<input type="text" id="percent"  name ="percent" maxlength="5" size="5" onkeypress="return inputLimiter(event,'Numbers')"/>
								</td> -->
			
							   </tr>
							<!-- 	<tr class="alt" align="center">
									<td colspan="2">
										<div id="displayDiv" style="height: 300px"></div>
										<div id="countEMP">0 Employees Selected</div>
										<input type="button" value="Select Employees" onclick="applyBonusFilt()" class="myButton">								
										<input type="button" value="Cancel All" onclick="cancelAll()" class="myButton">
									</td>
								</tr>
								 -->
						<!-- 		<tr class="alt">
									<td  align="center" colspan="2"> 
										<input type="button" value="Apply Bonus"  class="myButton" onclick="ApplyBonus()"/> 
										<input type="button"  class="myButton"  value="Cancel"/>
									</td>
								</tr> -->
			<tr><td  colspan="5"><center> <input class="myButton" type="submit"  value="Submit" >
			</center></td>
			
				</tr>	
					    </table>
				</form>
				</td> </tr></table>
					 <% }%> 
						</td>
						<td width="10"></td>		
					</tr>
				</table>
			    <!-- </form> -->
			</center>	    
		    </div>
		    <!--  end table-content  -->
		  
	<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
		<div align="center" style="padding-top: 20%;">
			<h1> Bonus Posting takes 15 to 30 Min....Be Patience</h1>
			<img alt="" src="images/process.gif">
		</div>
	</div>
			
	
		<div class="clear"></div>
	<input type="hidden" name="flag" id="flag" value="<%=flag%>">
<%


}
catch(Exception e)
{
e.printStackTrace();

%>
<script type="text/javascript">

window.location.href="login.jsp?action=0";

</script>
<%
}%>	 
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
