<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<% String Updated=request.getParameter("Updated")==null?"":request.getParameter("Updated"); 
System.out.println("updated..."+Updated);%>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
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
<script type="text/javascript">
function checkFlag()
{
var fg=document.getElementById("Updated").value;
if(fg=="done")
	{
	alert("Salary Difference added into arrears...");
	}
 if(fg=="notupdated")
	{
	alert("Not Updated This Time.. Try Again !!!");
	}
}

function validation()
{
var date=document.getElementById("incrmntmonth").value;

if(date=="")
	{
	alert("Please Select Salary Increment  Date !!!");
	return false;
	}
else 
	{
	return true;
	}
}
</script>

</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1> Salary Differance </h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
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
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>

									<form action="TransactionServlet?action=salarydiff" method="post" >

										<table id="customers" width="1000" align="center">
											<tr>
												<th> Salary Differance </th>
											</tr>

											<tr>
						
									<td width="800" align="center">
												<table style="width: 100% !important" >
														<tr>
					<td > Current Month Date:
											
								<input name="date"  id="date" type="text" readonly="readonly" value="<%=( ReportDAO.getSysDate()) %>" >
								<!-- onFocus="if(value=='dd-mmm-yyyy') {value=''}"
								onBlur="if(value=='') {value='';}">&nbsp; -->
								
							
							</td>
															<td  align="left">Salary change Date : 
															<input name="incrmntmonth" id="incrmntmonth" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
															
															</td>
															
														<td  align="left">Type : 
															<select id="incrmnttype" name="incrmnttype">
					                                       	<option value="0" selected>Select</option>
					                                        <option value="Increment">Increment Diff</option>
						                                    <option value="VDA">VDA Diff</option>
						                                    </select>
															
															
															</td>
															<td  align="left"><input type="submit"  id="print" value="Calculate Differance"  onclick="return validation();"/>
															
															</td>
																
															</tr> 

													</table>
													</td>
											</tr>

					

											</tr>



											<tr>
												<td>
													
												</td>


											</tr>
											<%try
											{
												if (session.getAttribute("trncd") != null ) {
											%>
											<tr>
												<td align="center" bgcolor="#1F5FA7"><input type="button"
													value="Edit" onClick="enableTextBoxes()" height="150" width="150" />
												<input type="submit" id="tranSave"
													value="Save" disabled="disabled" /></td>
											</tr>
											<%
												} else {
											%>
											<tr>
												<td align="left" bgcolor="#1F5FA7">&nbsp;</td>
											</tr>
											<%
												}
											}
											catch(Exception e)
											{
												%>
												<script type="text/javascript">
													window.location.href="login.jsp?action=2";
												</script>
											<%
											}
											%>


									 
											
										</table>


									</form>
									<br>

									 
							

								</center>

							</div>
							<!--  end table-content  -->

							<div class="clear"></div>

						</div> <!--  end content-table-inner ............................................END  -->
					</td>
					<td id="tbl-border-right"></td>
				</tr>
				<tr>
					<th class="sized bottomleft"></th>
					<td id="tbl-border-bottom">&nbsp;</td>
					<th class="sized bottomright"></th>
				</tr>
				
			</table>
			<input type="hidden" id="Updated"  value="<%=Updated %>" />
			<div class="clear">&nbsp;</div>

		</div>
		<!--  end content -->
		<div class="clear">&nbsp;</div>
	</div>
	<!--  end content-outer........................................................END -->

	<div class="clear">&nbsp;</div>


</body>
</html>