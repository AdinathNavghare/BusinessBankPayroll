<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.UserMasterHandler"%>
<%@page import="payroll.Model.UserMasterBean"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User maintainance Table</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />

<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<!--  jquery core -->
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>

<!--  checkbox styling script -->
<script src="js/jquery/ui.core.js" type="text/javascript"></script>
<script src="js/jquery/ui.checkbox.js" type="text/javascript"></script>
<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$('input').checkBox();
	$('#toggle-all').click(function(){
 	$('#toggle-all').toggleClass('toggle-checked');
	$('#mainform input[type=checkbox]').checkBox('toggle');
	return false;
	});
});
</script>



<!--  styled select box script version 1 -->
<script src="js/jquery/jquery.selectbox-0.5.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.styledselect').selectbox({ inputClass: "selectbox_styled" });
});
</script>




<!--  styled select box script version 2 -->
<script src="js/jquery/jquery.selectbox-0.5_style_2.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.styledselect_form_1').selectbox({ inputClass: "styledselect_form_1" });
	$('.styledselect_form_2').selectbox({ inputClass: "styledselect_form_2" });
});
</script>

<!--  styled select box script version 3 -->
<script src="js/jquery/jquery.selectbox-0.5_style_2.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.styledselect_pages').selectbox({ inputClass: "styledselect_pages" });
});
</script>

<!--  styled file upload script -->
<script src="js/jquery/jquery.filestyle.js" type="text/javascript"></script>


<!-- Custom jquery scripts -->
<script src="js/jquery/custom_jquery.js" type="text/javascript"></script>

<!-- Tooltips -->
<script src="js/jquery/jquery.tooltip.js" type="text/javascript"></script>
<script src="js/jquery/jquery.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$('a.info-tooltip ').tooltip({
		track: true,
		delay: 0,
		fixPNG: true, 
		showURL: false,
		showBody: " - ",
		top: -35,
		left: 5
	});
});
</script>


<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>


<!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD></HEAD> png fix -->
<script src="js/jquery/jquery.pngFix.pack.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
$(document).pngFix( );
});
</script>


<style type="text/css">

	/*Empnew Table template*/
#customers
{
font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
width:auto;
border-collapse:collapse;
}
#customers td, #customers th 
{
font-size:1em;
border:1px solid #98bf21;
padding:3px 7px 2px 7px;
}
#customers th 
{
font-size:1.1em;
text-align:left;
padding-top:5px;
padding-bottom:4px;
background-color:#85A02F;
color:#ffffff;
}
#customers tr.alt td 
{
color:#000000;
background-color:#EAF2D3;
}
	/*End Empnew table template*/
	/*Textbox template*/
input.twitterStyleTextbox
 {
    border: 1px solid #c4c4c4;
    width: 180px;
    height: 18px;
    font-size: 13px;
    padding: 4px 4px 4px 4px;
    border-radius: 4px;
    -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
    box-shadow: 0px 0px 8px #d9d9d9;
    -moz-box-shadow: 0px 0px 8px #d9d9d9;
    -webkit-box-shadow: 0px 0px 8px #d9d9d9;
}

input.twitterStyleTextbox:focus
 {
    outline: none;
    border: 1px solid #7bc1f7;
    box-shadow: 0px 0px 8px #7bc1f7;
    -moz-box-shadow: 0px 0px 8px #7bc1f7;
    -webkit-box-shadow: 0px 0px 8px #7bc1f7;
}
/*End textbox design*/
 

</style>

<script language="javascript">



function getUserDetails(usercode)
{
	//var SubType=document.getElementById("select.subtype").value;
	//alert(SubType);
	var resp=confirm("Do you want to Modify this record?");
	if(resp==true)
	{
		
		document.add.action="UserMasterServlet?action=Modify";
		url="UserMasterServlet?action="+code;
		xmlhttp.onreadystatechange=callback;
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
}
function callback()
{
	if(xmlhttp.readyState==4)
	{
    	var response=xmlhttp.responseXML;
    	var lookup=response.getElementsByTagName("lookup");
    	var code=lookup[0].getElementsByTagName("code")[0].firstChild.nodeValue;
    	var srno=lookup[0].getElementsByTagName("srno")[0].firstChild.nodeValue;
    	var desc=lookup[0].getElementsByTagName("desc")[0].firstChild.nodeValue;

    	document.add.code.value=code;
    	document.add.srno.value=srno;
    	document.add.desc.value=desc;
    	//alert(code);
	}
}




    function showOrHide() 
    {
        var div = document.getElementById("showOrHideDiv");
        if (div.style.display == "block") 
        {
            div.style.display = "none";
        }
        else 
        {
            div.style.display = "block";
        }
    }
    function validate()
    {
       var code=document.userForm.usercode.value;
       var pass=document.userForm.password.value;
       var repass=document.userForm.repassword.value;
       if(document.userForm.username.value=="" || document.userForm.usercode.value=="" || document.userForm.password.value=="" || document.userForm.status.value=="" )
        {
          alert("All Field Are Mandatory");
          document.userForm.usercode.focus();
          return false;
        }
       else if((isNaN(document.userForm.usercode.value)))
    	   {
    	   alert("usercode  should be Numeric");
    	   document.userForm.usercode.focus();
    	    
    	     
    	     return false;
    	   }
       else if( code.length> 10)
       {
    	   alert("user code  should be less than 10 digit");
    	   document.userForm.usercode.focus();
    	     return false;
    	   }
       else if(pass.length()>20)
       {
    	   alert("password  should be less than 20 digit");
    	   document.userForm.password.focus();
    	     return false;

    	   }

       else if(pass != repass)
       {
    	   alert(" re entered password  should be same as password");
    	   document.userForm.repassword.focus();
    	     return false;
   	   }
	   
    } 

</script>
<%
 
  UserMasterBean bean1 = new UserMasterBean();
  ArrayList<UserMasterBean> list =(ArrayList<UserMasterBean>)session.getAttribute("currentUser");

%>

<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>


</head>
<body style="overflow: hidden;">
<div><%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%></div>
<!-- start content-outer ........................................................................................................................START -->

<!-- start content -->
<div style="height: 500px; overflow-y: scroll; width:100%"" id="div1">
<div id="content-table-inner"><!--  start table-content  -->
<div id="table-content"><br>
<br>

<center>
<div style="border:thick">
<% 


		String usercode=request.getParameter("Code")!=null?request.getParameter("Code"):"zero";
		ArrayList<UserMasterBean> userList = (ArrayList<UserMasterBean>)session.getAttribute("currentUser");
	
%>


<%

for(UserMasterBean ubean :userList)
{
	
	if(usercode.equalsIgnoreCase(String.valueOf(ubean.getUsercode())))
	{
	
%>
<form  name="userForm" action="user?action=update" method="post" onSubmit="return validate()">
     <h3>USER UPDATE TABLE</h3>
      <table width="624" cellpadding="0" cellspacing="0" id="customers"align="center">
	<tr>
		<th width="622">User Update</th>
    </tr>
</table>
<table width="587"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" height="236" border="0">
	<tr class="alt">
		<td width="105" height="29">User Code</td>
		<td editable="true"><input type="text" name="usercode" value="<%=ubean.getUsercode()%>"  readOnly="true"/></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="29">User Name</td>
		<td width="173"><input type="text" name="username" value="<%=ubean.getUsername()%>" readOnly="true" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="29">Password</td>
		<td><input type="password" name="password" value="<%=ubean.getUserpwd()%>"/></td>
		<td width="129">Re-Enter Password</td>
		<td width="170"><input type="text" name="repassword" /></td>
	</tr>

	<tr class="alt">
		<td height="29">User Status</td>
		<td><input type="text" name="status" value="<%=ubean.getStatus()%>"  readOnly="true" /></td>
		<td width="129">Department Allowed</td>
		<td width="170"><input type="text" name="alloweddpt" /></td>
	</tr>
	<tr class="alt">
		<td height="29">User Level</td>
		<td><input type="text" name="level" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt" >
		<td height="29">User Issue By</td>
		<td><input type="text" name="IssueBy" /></td>
		<td>User Update</td>
		<td><input type="text" name="userupdate" /></td>
	</tr>
	<tr class="alt">
		<td height="29">User Issue Date</td>
		<td><input type="text" name="Issuedate" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="31" colspan="4" align="center">
		<input type="submit" value="Update"/> <input type="reset"value="Cancel" onClick="window.location='UserMasterServlet?action=cancel'"/>
		</td>
	</tr>
</table>
<div style=" width:640px;background-color:#92b22c">&nbsp;</div>

</form>
<%} 
}
%>
</div>
<br/><br/>
<div >
<form  action="#" method="get">
     <h3>USER TABLE</h3>
      
<table width="513"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" height="20" border="0">
	<tr class="alt">
	  <th width="105" height="20" >User Name</th>
	  <th width="84">User Code</th>           			
	<th width="81">password</th>
	<th width="73" align="right">User Status</th>
	<th width="78">User Level</th>
	<th width="93">&nbsp;</th>
	</tr>
	</table>
	<div style="height: 145px; overflow-y: scroll; width:600px"" id="div1">
	<table width="597"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" height="31" border="0">

		<% for(UserMasterBean bean:list)
		{			
		%>	
 <tr  class="row"  bgcolor="#FFFFFF">
		<td width="136"><%=bean.getUsername()%></td>
		<td width="107" height="31"><%=bean.getUsercode() %></td>
		<td width="98" ><%=bean.getUserpwd() %></td>
		<td width="111" ><%=bean.getStatus() %></td>
		<td width="116" >&nbsp;</td>
		<td width="103" ><input type="button" Value="Modify" onClick="window.location='upDateUser.jsp?action=showdetails&Code=<%=bean.getUsercode()%>'"/></td>
	
	</tr> 
	<%} %>
	
</table>
</div>
<div style=" width:600px;background-color:#92b22c">&nbsp;</div>
</form>

</div>
</center>

</div>
<!--  end table-content  -->

<div class="clear"></div>

</div>
<!--  end content-table-inner ............................................END  -->
<div class="clear">&nbsp;</div>
<!--  end content -->
<!--  end content-outer........................................................END -->
</div>

</body>
</html>