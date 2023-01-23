	<%@page import="payroll.Model.EmpOffBean"%>
	<%@page import="payroll.DAO.EmpOffHandler"%>
	<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.LMB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

 <script>
	
	jQuery(function() {
	//	alert("alert 2");
		$("#empno").autocomplete("list.jsp");
	}); 
</script>
 


<script type="text/javascript">

	function validation() { 
		
		if(document.getElementById("check").checked == true){
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				document.getElementById("check").checked == false;
				return false;
			}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
		}
		if(document.getElementById("check1").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			
			 var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
			}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
		}
		if(document.getElementById("check2").checked == true){
			if(document.getElementById("leavetyp").value=="0"){
				alert("Please select Leave Type");
				document.getElementById("leavetyp").focus();
				return false;
			}
			if(document.getElementById("leavetyp").value=="2"){
			if(document.getElementById("Leave").value=="0"){
				alert("Please select Leave");
				document.getElementById("Leave").focus();
				return false;
			}
		  }
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("empBranch").value=="0"){
				alert("Please select Type");
				document.getElementById("empBranch").focus();
				return false;
			}
				if(document.getElementById("empBranch").value=="1"){
				if(document.getElementById("emptyp").value=="0"){
					alert("Please select Employee");
					document.getElementById("emptyp").focus();
					return false;
				}
				if(document.getElementById("emptyp").value=="2"){
				if(document.getElementById("empno").value==""){	
					alert("Please select Employee");
					document.getElementById("empno").focus();
					return false;
				}
			}
		}
				if(document.getElementById("empBranch").value=="2"){	
					if(document.getElementById("branchtyp").value=="0"){
					alert("Please select Branch");
					document.getElementById("branchtyp").focus();
					return false;
					}
					if(document.getElementById("branchtyp").value=="2"){
					if(document.getElementById("Branch").value=="Select"){
						alert("Please select Branch");
						document.getElementById("Branch").focus();
						return false;
					}
					}
					if(document.getElementById("branchtyp").value=="3"){
						if(document.getElementById("rangeFrom").value=="Select"){
							alert("Please select Branch");
							document.getElementById("rangeFrom").focus();
							return false;
						}
					if(document.getElementById("rangeTo").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeTo").focus();
						return false;
					}
					}
				}
		}
		
		if(document.getElementById("check6").checked == true){
			if(document.getElementById("leavetyp").value=="0"){
				alert("Please select Leave Type");
				document.getElementById("leavetyp").focus();
				return false;
			}
			if(document.getElementById("leavetyp").value=="2"){
			if(document.getElementById("Leave").value=="0"){
				alert("Please select Leave");
				document.getElementById("Leave").focus();
				return false;
			}
		  }
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("empBranch").value=="0"){
				alert("Please select Type");
				document.getElementById("empBranch").focus();
				return false;
			}
			if(document.getElementById("empBranch").value=="1"){
				if(document.getElementById("emptyp").value=="0"){
					alert("Please select Employee");
					document.getElementById("emptyp").focus();
					return false;
				}
				if(document.getElementById("emptyp").value=="2"){
				if(document.getElementById("empno").value==""){	
					alert("Please select Employee");
					document.getElementById("empno").focus();
					return false;
				}
			}
		}
			if(document.getElementById("empBranch").value=="2"){	
				if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
				if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
				}
				if(document.getElementById("branchtyp").value=="3"){
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeTo").focus();
					return false;
				}
				}
			}
			
		}
		
		if(document.getElementById("check7").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("empBranch").value=="0"){
				alert("Please select Type");
				document.getElementById("empBranch").focus();
				return false;
			}
			if(document.getElementById("empBranch").value=="1"){
				if(document.getElementById("emptyp").value=="0"){
					alert("Please select Employee");
					document.getElementById("emptyp").focus();
					return false;
				}
				if(document.getElementById("emptyp").value=="2"){
				if(document.getElementById("empno").value==""){	
					alert("Please select Employee");
					document.getElementById("empno").focus();
					return false;
				}
			}
		}
			if(document.getElementById("empBranch").value=="2"){	
				if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
				if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
				}
				if(document.getElementById("branchtyp").value=="3"){
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeTo").focus();
					return false;
				}
				}
			}
		}
		
		if(document.getElementById("check8").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
			
		}
		
		if(document.getElementById("check9").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
			
		}	
		
		if(document.getElementById("check5").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
			}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
		}
		
		if(document.getElementById("check4").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("empBranch").value=="0"){
				alert("Please select Type");
				document.getElementById("empBranch").focus();
				return false;
			}
			if(document.getElementById("empBranch").value=="1"){
				if(document.getElementById("emptyp").value=="0"){
					alert("Please select Employee");
					document.getElementById("emptyp").focus();
					return false;
				}
				if(document.getElementById("emptyp").value=="2"){
				if(document.getElementById("empno").value==""){	
					alert("Please select Employee");
					document.getElementById("empno").focus();
					return false;
				}
			}
		}
			if(document.getElementById("empBranch").value=="2"){	
				if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
				if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
				}
				if(document.getElementById("branchtyp").value=="3"){
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeTo").focus();
					return false;
				}
				}
			}
		}
		
		if(document.getElementById("check10").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
			
		}	

		
		if(document.getElementById("check11").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
			
		}	

		
		
		if(document.getElementById("check12").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
			if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
			}
			if(document.getElementById("branchtyp").value=="3"){
				if(document.getElementById("rangeFrom").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
			if(document.getElementById("rangeTo").value=="Select"){
				alert("Please select Branch");
				document.getElementById("rangeTo").focus();
				return false;
			}
			}
			
		}	

		if(document.getElementById("check3").checked == true){
			if(document.getElementById("frmdate").value==""){
				alert("Please select from date");
				document.getElementById("frmdate").focus();
				return false;
			}
			if(document.getElementById("todate").value==""){
				alert("Please select to date");
				document.getElementById("todate").focus();
				return false;
			}
			var fromDate=document.getElementById("frmdate").value;
			 var toDate=document.getElementById("todate").value;
			   fromDate = fromDate.replace(/-/g,"/");
				toDate = toDate.replace(/-/g,"/");
				var d1 = new Date(fromDate);	 	
			 	var d2 =new  Date(toDate);
			 	
			 if (d1.getTime() > d2.getTime())
			 {
				   alert("\t\t Invalid Date Range!\n\n FromDate can't be greater than TODate!");
				   document.getElementById("todate").focus();
				   document.getElementById("todate").value="";
				   return false;
			  }
			if(document.getElementById("empBranch").value=="0"){
				alert("Please select Type");
				document.getElementById("empBranch").focus();
				return false;
			}
			if(document.getElementById("empBranch").value=="1"){
				if(document.getElementById("emptyp").value=="0"){
					alert("Please select Employee");
					document.getElementById("emptyp").focus();
					return false;
				}
				if(document.getElementById("emptyp").value=="2"){
				if(document.getElementById("empno").value==""){	
					alert("Please select Employee");
					document.getElementById("empno").focus();
					return false;
				}
			}
		}
			if(document.getElementById("empBranch").value=="2"){	
				if(document.getElementById("branchtyp").value=="0"){
				alert("Please select Branch");
				document.getElementById("branchtyp").focus();
				return false;
				}
				if(document.getElementById("branchtyp").value=="2"){
				if(document.getElementById("Branch").value=="Select"){
					alert("Please select Branch");
					document.getElementById("Branch").focus();
					return false;
				}
				}
				if(document.getElementById("branchtyp").value=="3"){
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeTo").focus();
					return false;
				}
				}
			}
			
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
		document.getElementById('branc').style='table-row';
			
		}
		else if(type =="0")
			{
			document.getElementById('branc').style.display='none';
			document.getElementById('specific').style.display='none';
			}
		else if(type =="3")
		{
		document.getElementById('specific').style='table-row';	
		document.getElementById('branc').style.display='none';
		}
	}
 	
 	function selectleave()
	{
 		var Ltype =document.getElementById("leavetyp").value;
 		if(Ltype =="1")
		{
	     document.getElementById('selectLeave').style.display='none';
		}
 		if(Ltype =="2")
		{
	     document.getElementById('selectLeave').style='table-row';
		}
	}
  	function employeeBranch(){
 		var empbranch =document.getElementById("empBranch").value;
 		if(empbranch =="1")
		{
 		 document.getElementById('employee').style='table-row';
	     document.getElementById('emptyp').style='table-row';
	     document.getElementById('branchtyp').style.display='none';
	     document.getElementById('branches').style.display='none';
	     document.getElementById('branc').style.display='none';
	     document.getElementById('specific').style.display='none';
		}
 		if(empbranch =="2")
		{
 		 document.getElementById('branches').style='table-row';
	     document.getElementById('branchtyp').style='table-row';
	     document.getElementById('employee').style.display='none';
	     document.getElementById('selectEmployee').style.display='none';
		}
 		if(empbranch =="0")
		{
 			 document.getElementById('branchtyp').style.display='none';
 		     document.getElementById('branches').style.display='none';
 		     document.getElementById('branc').style.display='none';
 		     document.getElementById('specific').style.display='none';
 		     document.getElementById('employee').style.display='none';
 		     document.getElementById('selectEmployee').style.display='none';
		}
 	} 
 	function selectemployee(){
 		var emptype =document.getElementById("emptyp").value;
 		if(emptype =="1")
		{
	     document.getElementById('selectEmployee').style.display='none';
		}
 		if(emptype =="2")
		{
	     document.getElementById('selectEmployee').style='table-row';
		}
 	}
 	
 	function balanceLeaveList() {
 		
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('dates').style.display='none';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('branches').style='table-row';
		document.getElementById('employee1').style.display='none';
		document.getElementById('branchtyp').style='table-row';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
		
		//document.getElementById('branc').style.display='none';
		//document.getElementById('specific').style.display='none';
	//	var element = document.getElementById('branchtyp');
	 //   element.value = 'SELECT';
	 //   document.getElementById("branchtyp").value = "SELECT";
	}
 	
 	function InputFilter() {
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('dates').style='table-row';
		document.getElementById('branches').style='table-row';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('employee1').style.display='none';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
	}
 	function LeaveAvailedDetailList() {
 			document.getElementById('ltype').style='table-row';
 			document.getElementById('dates').style='table-row';
 			document.getElementById('specificdate').style.display='none';
 			document.getElementById('employee1').style='table-row';
 			document.getElementById('branches').style.display='none';
 			document.getElementById("frmdate").value="";
 			document.getElementById("todate").value="";
 			document.getElementById('buttonReport').style='table-row';
 		}
 	function cancelLeaveList() {
			document.getElementById('dates').style='table-row';
			document.getElementById('branches').style='table-row';
			document.getElementById('specificdate').style.display='none';
			document.getElementById('ltype').style.display='none';
			document.getElementById('employee').style.display='none';
			document.getElementById('employee1').style.display='none';
			document.getElementById('selectEmployee').style.display='none';
			document.getElementById('selectLeave').style.display='none';
			document.getElementById('branchtyp').style='table-row';
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById('buttonReport').style='table-row';
		}
 	function negativeBalLeaveList() {
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('dates').style='table-row';
		document.getElementById('branches').style='table-row';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('employee1').style.display='none';
		document.getElementById('branchtyp').style='table-row';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
	}
	function notSanctionLeaveList() {
			document.getElementById('dates').style='table-row';
		//	document.getElementById('employee').style='table-row';
			document.getElementById('employee1').style='table-row';
			document.getElementById('specificdate').style.display='none';
			document.getElementById('ltype').style.display='none';
			document.getElementById('selectLeave').style.display='none';
			document.getElementById('branches').style.display='none';
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById('buttonReport').style='table-row';
		}
	function appDateWiseLeaveList() {
			document.getElementById('ltype').style='table-row';
			document.getElementById('dates').style='table-row';
			document.getElementById('employee1').style='table-row';
			document.getElementById('employee').style.display='none';
	//		document.getElementById('selectEmployee').style='table-row';
			document.getElementById('specificdate').style.display='none';
			document.getElementById('branches').style.display='none';
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById('buttonReport').style='table-row';
	}
	 function LwpGreater25List() {
		    document.getElementById('dates').style='table-row';
			document.getElementById('branches').style='table-row';
			document.getElementById('specificdate').style.display='none';
			document.getElementById('ltype').style.display='none';
			document.getElementById('employee').style.display='none';
			document.getElementById('employee1').style.display='none';
			document.getElementById('selectEmployee').style.display='none';
			document.getElementById('selectLeave').style.display='none';
			document.getElementById('branchtyp').style='table-row';
	//	document.getElementById('selectEmployee').style.display='none';
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById('buttonReport').style='table-row';
	} 
	function LwpList() {
			document.getElementById('dates').style='table-row';
		//	document.getElementById('employee').style='table-row';
			document.getElementById('employee1').style='table-row';
			document.getElementById('specificdate').style.display='none';
			document.getElementById('ltype').style.display='none';
			document.getElementById('selectLeave').style.display='none';
			document.getElementById('branches').style.display='none';
			document.getElementById("frmdate").value="";
			document.getElementById("todate").value="";
			document.getElementById('buttonReport').style='table-row';
	}
	function lMarking() {
		document.getElementById('dates').style='table-row';
		document.getElementById('branches').style='table-row';
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('employee1').style.display='none';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('branchtyp').style='table-row';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
	}
	function creditBalance() {
		document.getElementById('dates').style='table-row';
		document.getElementById('branches').style='table-row';
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('employee1').style.display='none';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('branchtyp').style='table-row';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
	}
	
	function balanceLeave() {
		document.getElementById('dates').style='table-row';
		document.getElementById('branches').style='table-row';
		document.getElementById('specificdate').style.display='none';
		document.getElementById('ltype').style.display='none';
		document.getElementById('employee').style.display='none';
		document.getElementById('employee1').style.display='none';
		document.getElementById('selectEmployee').style.display='none';
		document.getElementById('selectLeave').style.display='none';
		document.getElementById('branchtyp').style='table-row';
		document.getElementById("frmdate").value="";
		document.getElementById("todate").value="";
		document.getElementById('buttonReport').style='table-row';
	}
	
	
	function LeaveEncashList() {
				document.getElementById('dates').style='table-row';
				document.getElementById('employee1').style='table-row';
				document.getElementById('specificdate').style.display='none';
				document.getElementById('ltype').style.display='none';
				document.getElementById('selectLeave').style.display='none';
				document.getElementById('branches').style.display='none';
				document.getElementById("frmdate").value="";
				document.getElementById("todate").value="";
				document.getElementById('buttonReport').style='table-row';
			}
	
</script>
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
<body > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Leave Reports New</h1>
	</div>
	<!-- end page-heading -->
    <form action="ReportServlet?action=leaveReportnew" method="post" name="add" onsubmit="return  validation()"> 
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
			 <table border="1" id="customers" align="center">
			<tr>
				<th width="100" colspan="2" >Leave Reports New</th>
			</tr>
			
			<tr class="alt" height="30" >
						<td><b>Select Leave Report</b></td>
			
			
				<td>	
						<label>
       <input type="radio" name="check" id="check" value="balanceLeaveList" onclick = "balanceLeaveList()" />
       Balance Leave List</label>
		<label>
       <input type="radio" name="check" id="check1" value="LeaveAvailedList"  onclick = "InputFilter()"/>
       Leave Availed List</label>	 
       <label>
       <input type="radio" name="check" id="check2" value="LeaveAvailedDetailList" onclick = "LeaveAvailedDetailList()"/>
       Leave Availed Detail List</label>    
        <label>
       <input type="radio" name="check" id="check3" value="LeaveEncashList"  onclick = "LeaveEncashList()"/>
       Leave Encashed List</label>
       <label>
       <input type="radio" name="check" id="check4" value="LwpList"  onclick = "LwpList()"/>
       LWP List</label> 
       <label>
       <input type="radio" name="check" id="check5" value="LwpGreater25List"  onclick = "LwpGreater25List()"/>
       LWP Greater than 25 List</label>     
	</td>
	
	</tr>
	
	<tr class="alt" height="30" >
						<td><b></b></td>
			
			
				<td>	
						<label>
       <!-- <input type="radio" name="check" id="check" value="entryDateWiseLeaveList" />
       Entry Date Wise Leave List</label>
		<label> -->
       <input type="radio" name="check" id="check6" value="appDateWiseLeaveList"  onclick = "appDateWiseLeaveList()"/>
       Application Date Wise Leave List</label>	 
       <label>
       <input type="radio" name="check" id="check7" value="notSanctionLeaveList" onclick = "notSanctionLeaveList()"/>
       Not Sanctioned Leave List</label>    
       <!-- <label>
       <input type="radio" name="check" id="check" value="notAuthoriseLeaveList"  onclick = "notAuthoriseLeaveList()"/>
       Not Authorised Leave List</label>-->
       <label> 
       <input type="radio" name="check" id="check8" value="negativeBalLeaveList"  onclick = "negativeBalLeaveList()"/>
       Negative Balance Leave List</label> 
       <label>
       <input type="radio" name="check" id="check9" value="cancelLeaveList"  onclick = "cancelLeaveList()" />
        Cancel Leave List</label>    
	</td>
	
	
	</tr>
	
	 <tr class="alt" height="30" >
						<td><b></b></td>
			
			
				<td>	
						<label>
       <input type="radio" name="check" id="check10" value="lMarking"  onclick = "lMarking()"/>
       LMarking Report</label>
       
       		
						<label>
       <input type="radio" name="check" id="check11" value="CreditBalance"  onclick = "creditBalance()"/>
       Credit And Balance Leave List Report</label>
       
       
       	<label>
       <input type="radio" name="check" id="check12" value="BalanceLeave"  onclick = "balanceLeave()"/>
       Balance Leave List Report</label>
       
       </td>
       
       </tr> 
       <tr class="alt" height="30" id="ltype" style="display:none">
						<td><b>Leave Type</b></td>
						<td>	
						<select id="leavetyp" name="leavetyp"  onchange="selectleave()"> 
						<option value="0" selected="selected">Select</option>
						<option value="1">All</option>
						<option value="2">Specific</option>
						</select>
						</td></tr>
						
       					<tr class="alt" id="selectLeave" style="display:none">
					   <td>Select Leave </td>
     			       <td>
					   <select name="Leave" id="Leave" >  
      				   <option value="0" selected="selected">Select</option>
						<option value="1">PL</option>
						<option value="2">SL</option>
						<option value="3">CL</option>
						<option value="4">COFF_L</option>
						<option value="6">LATE_L</option>
						<option value="7">LWP</option>
						<option value="8">MAT_L</option>
				<!-- 	<option value="9">TRF_L</option>   -->	
						<option value="9">COVID19_L</option>
						<option value="10">SUSPE_L</option>
     			      	</select>
     			      	</td>
     			      	</tr>
       					
		                <tr class="alt" height="30"  id="dates" style="display:none"> 
       					<td ><b>From Date</b></td>
						<td  align="left">
						<input name="frmdate" id="frmdate" type="text"  readonly="readonly" size="25"  placeholder="">&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('frmdate', 'ddmmmyyyy')" />
     			      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>To Date</b>&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="todate" id="todate" type="text"  readonly="readonly" size="25" >&nbsp;<img
							src="images/cal.gif" align="middle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
						</td>			
       					</tr>
       					
       					<tr class="alt" height="30" id="employee1">
						<td><b>Select Type</b></td>
						<td>	
						<select id="empBranch" name="empBranch"  onchange="employeeBranch()"> 
						<option value="0" selected="selected">Select</option>
						<option value="1">Employee Wise</option>
						<option value="2">Branch Wise</option>
						</select>
						</td></tr>
       					
       					<tr class="alt" height="30" id="employee" style="display:none">
						<td><b>Employee</b></td>
						<td>	
						<select id="emptyp" name="emptyp"  onchange="selectemployee()"> 
						<option value="0" selected="selected">Select</option>
						<option value="1">All</option>
						<option value="2">Specific</option>
						</select>
						</td></tr>
       					
       					
       					<tr class="alt" id="selectEmployee" style="display:none">
					   <td>Select Employee </td>
     			       <td colspan="4">
     			       <input size='45' type="text" id="empno" name="empno" placeholder="Enter Employee Name" font-size:16px">
     			       <!-- <input type="text" name="EMPNO" id="EMPNO" 
						size="40"  placeholder="Enter Employee NAME OR ID"> -->
						</td>
     			      	</tr>
       					
       					<tr class="alt" height="30" id="branches" style="display:none">
						<td><b>Select Branch</b></td>
						<td>	
						<select id="branchtyp" name="branchtyp"  onchange="selectbranch()"> 
						<option value="0" selected="selected">SELECT</option>
						<option value="1">ALL</option>
						<option value="2">Branch_Wise</option>
						<option value="3">RANGE</option>
						</select>
						</td></tr>
	
	 					<tr class="alt" id="branc" style="display:none">
					   <td>Select Branch Wise </td>
     			       <td>
					   <select name="Branch" id="Branch" style="width:140px">  
      				   <option value="Select">Select</option>  
    				   <%
						EmpOffHandler eh = new EmpOffHandler();
						ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
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
       					<tr class="alt" id="branc" style="display:none">
    				
        				<tr class="alt" height="30"  id="specific" style="display:none">
						<td ><b>Range From</b></td>
						<td  align="left">
						<select name="rangeFrom" id="rangeFrom" style="width:140px">  
      					  			<option value="Select">Select</option>  
    											
    											<%
														EmpOffHandler eh1 = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn1 = new ArrayList<EmpOffBean>();
														ebn1 = eh1.getprojectCode();
														for(EmpOffBean eopbn1 : ebn1)
														{
												%>
														<option value="<%=eopbn1.getSite_id()%>"><%=eopbn1.getSite_id()%></option>
												<%		}
												%>
     			      			</select>
     			      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>Range To</b>&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="rangeTo" id="rangeTo" style="width:140px">  
      					  			<option value="Select">Select</option>  
    											<%
														EmpOffHandler eh2 = new EmpOffHandler();
														ArrayList<EmpOffBean> ebn2 = new ArrayList<EmpOffBean>();
														ebn2 = eh2.getprojectCode();
														for(EmpOffBean eopbn2 : ebn2)
														{
												%>
														<option value="<%=eopbn2.getSite_id()%>"><%=eopbn2.getSite_id()%></option>
												<%		}
												%>
     			      			</select>
						</td>			
						</tr>
						
						
							<tr class="alt" id="specificdate" style="display:none">
							<td>Select Date</td>
							<td bgcolor="#FFFFFF">
							<input name="date" id="date" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
							</td>
						  	</tr>
						
						
       					<!-- <tr class="alt" height="30"  id="specificdate" style="display:none">
						<td ><b>Select Month</b></td>
						<td  align="left"><input class="form-control" type="text" name="month" size="12" id="month">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>Select Year</b>
						<input class="form-control" type="text" name="year" size="11" id="year">
						</td>			
						</tr> -->
       
       
       
       
       <!--  <tr class="alt" height="30" >
						<td><b>Select Leave Type</b></td>
			
			
				<td>	
						<select id="leavetyp" name="leavetyp">
						<option value="0" selected="selected">SELECT</option>
						<option value="cl">CL</option>
						<option value="pl">PL</option>
						<option value="sl">SL</option>
						<option value="lwp">LWP</option>
						 </select>
			         
	</td></tr> -->
       
  <!--      <tr class="alt" height="30" >
						<td><b>Select Employee</b></td>
			
			
				<td>	
						<input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO" 
                       placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name" >
			         
	</td></tr> -->
	
	<!-- <tr class="alt" height="30" >
                 
						<td ><b>From Date</b></td>
						 <td  align="left"><input  name="fromdate" id="fromdate" type="text" placeholder="Please select the Date" value="">
             &nbsp;<img src="images/cal.gif" style="vertical-align: left; cursor: pointer;"
			onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b>To Date</b>
						<input  name="todate" id="todate" type="text" placeholder="Please select the Date" value="">
             &nbsp;<img src="images/cal.gif" style="vertical-align: left; cursor: pointer;"
			onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>			

					</tr> -->
	<tr  id="buttonReport"  style="display:none">
	<td colspan="2" align="center"  >
	 <input  type="submit" value="Get Report" class="myButton"  /></td>
		</td>
	</tr>
	
</table>

<br/>

			    <div id="viewPdf"  hidden="true">
			   </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
				<%-- <input type="hidden" name="flag" id="flag" value="<%=flag%>"> --%>
				<input type="hidden" name="hleave" id="hleave" >
				<input type="hidden" name="specificleave" id="specificleave" >
				<input type="hidden" name="htype" id="htype" >
				<input type="hidden" name="hemptype" id="hemptype" >
				<input type="hidden" name="hbranch" id="hbranch" >
				<input type="hidden" name="hbrwise" id="hbrwise" >
				<input type="hidden" name="hbrrange" id="hbrwise" >
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
	</form>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    
       
</body>
</html>