<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.VdaDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.VdaBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
	<%@page import="payroll.Core.ReportDAO"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">

function validate(){
	
	alert("VDA Posting will start after this !");
	var check = prompt("Do you want to post VDA For Current month? \n Type YES or NO.");
	if(check == "yes" || check == "Yes" || check == "YES")
	{
	
		
		return true;
	} else {
		alert("Sorry ! Wrong Input ");
		var check = prompt("Do you want to post VDA For Current month? \n Type YES or NO.");
		if(check == "yes" || check == "Yes" || check == "YES")
		{	
			return true;
		}
		else
			{
			alert("Sorry ! Wrong Input ");
		return false;
			}
	}
}


function validate1()
{

	
	if(document.getElementById("date1").value!="" || document.getElementById("date2").value!="")
	{
	
	alert("VDA Difference Posting will start after this !");
	var check = prompt("Do you want to post VDA Difference For Current month? \n Type YES or NO.");
	if(check == "yes" || check == "Yes" || check == "YES")
	{
	
		
		return true;
	} else {
		alert("Sorry ! Wrong Input ");
		var check = prompt("Do you want to post VDA Difference For Current month? \n Type YES or NO.");
		if(check == "yes" || check == "Yes" || check == "YES")
		{	
			return true;
		}
		else
			{
			alert("Sorry ! Wrong Input ");
		return false;
			}
	}
	}
	else
		{
		alert("Please select month!");
		return false;		
		}
}



function chkflag()
{
	var flag=parseInt(document.getElementById("flag").value);

	if(flag==1)
		{
		alert("VDA Posted Successfully !");
		}
	 if(flag==2)
		{
		alert("Some error into VDA Posting !");
		}
	if(flag==3)
	{
	alert("VDA Difference Posted Successfully !");
	}
 if(flag==4)
	{
	alert("Some error into VDA Difference Posting !");
	}
	if(flag==5)
	{
	alert("VDA slab is finalized successfully");
	}
 if(flag==6)
	{
	alert("Some error in finalizing vda slab");
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
    
    <script>
     function finalize(){
    	 var date=document.getElementById("date").value;
    	 var flag=confirm("Are you sure to end Vda slab whose starting date is "+date+"?");
    	 if(flag)
    		 
    		 {
    		 var check = prompt("Are you really sure ? Then type Yes ");	
 				if(check == "yes" || check == "Yes" || check == "YES"){
 					 window.location.href="VdaServlet?action=finalize&date="+date;
 				} else {
 					alert("Sorry ! Wrong Input ");
 					return false;
 				}
    		 }
    		
    	 else
    		 return false;
    	 
     }
    </script>
    
    <%
	 String pageName = "payRegister.jsp";
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
		 e.printStackTrace();
		//response.sendRedirect("login.jsp?action=0");
	} 

%>
    
    
    
    
    
    <style>
    .ui-datepicker-calendar {
        display: none;
        }
    </style>



<%
String flag=request.getParameter("flag")==null?"0":request.getParameter("flag");
VdaDAO vdaDao=new VdaDAO();
ArrayList<VdaBean> vdaList=vdaDao.checkForFinalizedRecords(); 
%>



 </head>
<body style="overflow: hidden;" onload="chkflag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> VDA POSTING </h1>
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
			<!-- <h2>Profession Tax Statement</h2> -->
			
			<table id="customers">
			<tr>
			
			<td>
			<%  if(vdaList.size()!=0)
			{
			String monthyear="";
    		String year=vdaList.get(0).getDaApplicableDate().substring(0,4);
    		String month=vdaList.get(0).getDaApplicableDate().substring(5,7);
    		
    		if(month.equals("01"))
    			month="Jan";
    		else if(month.equals("02"))
    			month="Feb";
    		else if(month.equals("03"))
    			month="Mar";
    		else if(month.equals("04"))
    			month="Apr";
    		else if(month.equals("05"))
    			month="May";
    		else if(month.equals("06"))
    			month="Jun";
    		else if(month.equals("07"))
    			month="Jul";
    		else if(month.equals("08"))
    			month="Aug";
    		else if(month.equals("09"))
    			month="Sep";
    		else if(month.equals("10"))
    			month="Oct";
    		else if(month.equals("11"))
    			month="Nov";
    		else
         		month="Dec";
    		
    		
    	monthyear=month+"-"+year;
			
			%>
			<form name="form10" action="ReportServlet?action=postvda" method="post" onsubmit="return  validate()">
				<table border="1" id="customers">
					<tr>
						<th> VDA POSTING FORM</th>
					<tr>
					<tr class="alt">
						<td height="80" align="center">
							<table>
								<tr class="alt">
					
									<td>VDA POST TO CURRENT MONTH :<b> <%=monthyear%> </b> </td>
						
									</tr>
					 
								<tr>
									<td colspan="4" align="center"><input type="submit" class="myButton"	value="POST VDA" /></td>
								</tr>
							</table>

			  			</td>
					</tr>
				</table>
			</form>
	
			<!-- </td> -->
			<!-- <td>
			</td> -->
			
<!-- 			<form name="form10" action="ReportServlet?action=postvdadiff" method="post" onsubmit="return  validate1()">
				<table border="1" id="customers">
					<tr>
						<th colspan="2"> VDA DIFFERENCE POST TO CURRENT MONTH</th>
					<tr>
					<tr class="alt">
						<td height="80" align="center">
							<table>
								
								<tr>
									<td>
									VDA Differenece From : </td>
									<td> <input name="date1" id="date1" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
									</td>	
					 			</tr>
					 			
					 			<tr>
									<td>
									VDA Differenece To :</td>
									<td> <input name="date2" id="date2" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
									</td>	
					 			</tr>
					 			
								<tr>
									<td colspan="2" align="center"><input type="submit" class="myButton"	value="POST VDA DIFFERENCE" /></td>
								</tr>
							</table>

			  			</td>
					</tr>
				</table>
			</form> -->
			</td>
			</tr>
			</table>
			
			<br>
			<br>
			<br>
			<br>
			
			<h2>Click this button to finalize the VDA slab</h2>
			<input type="hidden" id="date"  name="Finalize" value="<%=monthyear%>">
			<input type="button" class="myButton" name="Finalize" value="Finalize" onclick="finalize()">
	
			
			
			</center>
			<input type="hidden" id="flag" name="flag" value="<%=flag%>">
			</div>
			<!--  end table-content  -->
		<% 	} else { %>
		
		<h2>NO VDA TO POST </h2>
		<%} %>
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