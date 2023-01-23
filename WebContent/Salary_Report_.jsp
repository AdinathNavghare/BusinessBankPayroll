<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Salary Report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
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

<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>
<script type="text/javascript">
function check(){
	var ptytpe=document.getElementById("trncd");
	var nillpt = ptytpe.options[ptytpe.selectedIndex].value;
	document.getElementById("pttrncd").value="";
	if( document.getElementById("date").value=="")
	{
		alert("Please Select Date!!!");	
		document.getElementById("date").focus;
		return false;
	}
	else if( document.getElementById("trncd").selectedIndex==0)
	{
		alert("Please Select Report!!!");	
		document.getElementById("trncd").focus;
		return false;
	}
	else if(nillpt==202||nillpt=="202")
		{
			if( document.getElementById("trncd1").selectedIndex==0)
			{
				alert("Please Select PT List!!!");	
				document.getElementById("trncd1").focus;
				return false;
			}
			else
			{
			var pttytpe=document.getElementById("trncd1");
			var nillptt = pttytpe.options[pttytpe.selectedIndex].value;
			document.getElementById("pttrncd").value=nillptt;
			return true;
			}
		
		}
	else if(nillpt==242||nillpt=="242")
	{
		if( document.getElementById("trncd2").selectedIndex==0)
		{
			alert("Please Select Union List!!!");	
			document.getElementById("trncd2").focus;
			return false;
		}
		else
		{
		var pttytpe=document.getElementById("trncd2");
		var nillptt = pttytpe.options[pttytpe.selectedIndex].value;
		document.getElementById("pttrncd").value=nillptt;
		return true;
		}
	
	}

	
	
}
function getCode(code)
{
	    document.getElementById("tname").value=code;
	    var filename="";
	    if(trncd.options[trncd.selectedIndex].value=="999"||trncd.options[trncd.selectedIndex].value=="9999"||trncd.options[trncd.selectedIndex].value=="207"||trncd.options[trncd.selectedIndex].value=="129")
	    {
      	  filename = trncd.options[trncd.selectedIndex].innerHTML;
      	document.getElementById("fname").value=filename;
	    }
	    else if(trncd.options[trncd.selectedIndex].value=="202")
	    	{
	    			var pttytpee=document.getElementById("trncd1");
					var nillpttt = pttytpee.options[pttytpee.selectedIndex].innerHTML;
					
	    		 	filename = nillpttt;
	    		 document.getElementById("fname").value=filename;
	    		 
	    	}
	    else if(trncd.options[trncd.selectedIndex].value=="242")
    	{
    			var pttytpee=document.getElementById("trncd2");
				var nillpttt = pttytpee.options[pttytpee.selectedIndex].innerHTML;
				
    		 	filename = nillpttt;
    		 document.getElementById("fname").value=filename;
    		 
    	}
	    
       
	   
}

function selectPTtype()
{
	if( document.getElementById("date").value=="")
	{
		alert("Please Select Date!!!");	
		document.getElementById("date").focus;
		return false;
	}
	else if( document.getElementById("trncd").selectedIndex==0)
	{
		alert("Please Select Report!!!");	
		document.getElementById("trncd").focus;
		return false;
	}
	var ptytpe=document.getElementById("trncd");
	var nillpt = ptytpe.options[ptytpe.selectedIndex].value;
	if(nillpt==202||nillpt=="202")
		{
		document.getElementById("ptpt1").style.display ='none';
		document.getElementById("ptpt").style.display ='';
		
		}
	else if(nillpt==999||nillpt=="999"||nillpt==9999||nillpt=="9999"||nillpt=="207"||nillpt==207||nillpt=="129"||nillpt==129)
	{
	
	document.getElementById("ptpt").style.display ='none';
	document.getElementById("ptpt1").style.display ='none';
	}
	
	else if(nillpt==242||nillpt=="242")
	{
	document.getElementById("ptpt").style.display ='none';
	document.getElementById("ptpt1").style.display ='';
	
	}
	else{
		document.getElementById("ptpt").style.display ='none';
		document.getElementById("ptpt1").style.display ='none';
	}
	}
	
	
</script>
<%-- <%
	String pageName = "NillReport.jsp";
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

%> --%>

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
		<h1>Salary Report</h1>
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
			
			<form name="salrpt" action="ReportServlet" method ="post" onSubmit="return check()" >
			<table border="1" id="customers" align="center">
			<tr>
				<th>Salary Report</th>
			<tr>
			<tr class="alt">
				<td  align="center">
				
			<table align="center">
				<tr class="alt" height="30" align="center">
                	<input type="hidden" id="action" name="action" value="salaryreport"></input>
                	<input type="hidden" id="pttrncd" name="pttrncd" />
					<td>Select Date</td>
					<td align="left">
					<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
					</td>
				</tr>
				<tr class="alt" height="30" align="center">	
					<td align="center">Select Report :
					</td>
					<td><select style="width:250px" name="trncd" id="trncd" onchange="selectPTtype()">
						<option value="0" selected>Select</option>
						<option value="999" >Nill Salary List</option>
						<option value="9999" >Net Salary List</option>
						<option value="129" >Table Allowance List</option>
						<option value="207" >GI Summery List</option>
						<option value="242" >Union List</option>
						<option value="202" >PT List</option>
						<option value="GI" >Group Insurance List</option>
						<option value="BDVabove1500" >(Basic+DA+VDA) Above 15000 List(PF)</option>
					<%-- <%
    					ArrayList<CodeMasterBean> getresult =new ArrayList<CodeMasterBean>();
    					CodeMasterHandler cmd = new CodeMasterHandler();
    					getresult = cmd.getNoAutocalCDListReport();
    					for(CodeMasterBean lkbean : getresult){
 					%>
      					<option value="<%=lkbean.getTRNCD()%>"><%=lkbean.getDISC()%></option>  
     				<%
     					}
     				%> --%>
						</select>
					</td>
				</tr>
				
				
				<tr id="ptpt" class="alt" height="30" align="center" style="display: none; ">	
					<td align="center">Select PT List :
					</td>
					<td><select style="width:250px" name="trncd1" id="trncd1" >
						<option value="0" selected>Select</option>
						<option value="202" >Nill PT List</option>
						<option value="2022" >PT Distribution List</option>
					<%-- <%
    					ArrayList<CodeMasterBean> getresult =new ArrayList<CodeMasterBean>();
    					CodeMasterHandler cmd = new CodeMasterHandler();
    					getresult = cmd.getNoAutocalCDListReport();
    					for(CodeMasterBean lkbean : getresult){
 					%>
      					<option value="<%=lkbean.getTRNCD()%>"><%=lkbean.getDISC()%></option>  
     				<%
     					}
     				%> --%>
						</select>
					</td>
				</tr>
				
				<tr id="ptpt1" class="alt" height="30" align="center" style="display: none; ">	
					<td align="center">Select Union List :
					</td>
					<td><select style="width:250px" name="trncd1" id="trncd2" >
						<option value="0" selected>Select</option>
						<option value="242" >Union Member List(Branchwise)</option>
						<option value="2422" >Union Distribution List</option>
					<%-- <%
    					ArrayList<CodeMasterBean> getresult =new ArrayList<CodeMasterBean>();
    					CodeMasterHandler cmd = new CodeMasterHandler();
    					getresult = cmd.getNoAutocalCDListReport();
    					for(CodeMasterBean lkbean : getresult){
 					%>
      					<option value="<%=lkbean.getTRNCD()%>"><%=lkbean.getDISC()%></option>  
     				<%
     					}
     				%> --%>
						</select>
					</td>
				</tr>
				
				
				
				<tr  class="alt" height="30" align="center">
				<td colspan="2">
				<!-- <table width="100%" bordercolor="white" style="border-collapse: collapse;" border="0"><tr> -->
					<!-- <td align="center"> --><input type="submit" class="myButton"  style="float: left; margin-left: 25%;" value="Before Finalize" onclick="getCode('paytran')" />
						     	<!-- <input type="hidden" value="" id="tname" name="tname"> --><!-- </td> -->
					 <!-- <td align="center"> --><input type="submit" class="myButton" id ="2"  style="float:right; margin-right: 1%; vertical-align: top;"  value="After Finalize" onclick="getCode('paytran_stage')" />
					<!-- </td> -->
					<input type="hidden" id="tname" name="tname" value="">
						<input type="hidden" id="fname" name="fname" value="">
					<!-- </tr>
				</table> -->
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