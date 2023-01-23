<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/filter.js"></script>
<title></title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />

<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->
</style>
<link rel="stylesheet" href="css/jquery.treeview.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript">

function popup()
{
	
	//window.showModalDialog("sanctionLeave.jsp",null,"dialogWidth=1400px;dialogHeight=900px;center=yes;dialogleft=yes;addressBar=no;");
	$("#myModal").empty();
	document.getElementById("myModal").style.display = 'Block';
	if(document.getElementById("action").value=="customSearch")
		$("#myModal").load("sanctionLeave.jsp?action=customSearch");
	else{
	$("#myModal").load("sanctionLeave.jsp");
	var f =document.getElementById("flag").value;
	if (f=="1") {
		alert("Leave Sanctioned  Successfully");
	}else if (f =="2") {
		alert("CLs Sanctioned but are converted to PL since there were minimum 5 consecutive CLs");			
	}else if(f=="3"){
		alert("Leave Rejected Successfully");
	}else if(f=="4"){
		alert("Some problem in leave rejection");
	}else if(f=="8"){
			alert("You have already applied leave for this date");
	}else if ((f!="1") && (f!="2") && (f=="3") &&(f=="4") && (f!= "100")(f!= "8")){
				//alert(f);
				alert("Not enough balance");
			} 
	}
	$("#myModal").fadeTo('slow', 0.9);
	$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	//window.location.href="index.jsp";
	}
function closediv() {
	//alert("closing");
	
	document.getElementById("myModal").style.display = "none";
	$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
	window.location.href="index.jsp";
}

</script>
<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action in sanction=="+action);
String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
System.out.println("flag in sanction=="+flag);
%>
</head>
    <body  id="sanctionpop" onload="popup()" >
    
    <%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
<input type="hidden" id="action" value="<%=action%>">
<input type="hidden" id="flag" value="<%=flag%>">
</body>
</html>