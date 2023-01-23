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
		<link rel="stylesheet" type="text/css" href="datepickr.css" />
 
<script language="javascript">



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
	   alert("Contact Number should be Numeric");
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
   else if(document.userForm.password.value !='' && document.userForm.repassword.value != '')
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
</head>
<body>

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

<form  name="userForm" action="UserMasterServlet?action=add" method="post" onSubmit="return validate()">
     <h3>USER MAINTAINANCE TABLE</h3>
      <table width="624" cellpadding="0" cellspacing="0" id="customers"align="center">
	<tr>
		<th width="622">User Maintainance</th>
    </tr>
</table>
<table width="587"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" height="236" border="0">
	 
	<tr class="alt">
		<td width="105" height="29">User Code</td>
		<td><input type="text" name="usercode" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="29">User Name</td>
		<td width="173"><input type="text" name="username" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="29">Password</td>
		<td><input type="password" name="password" /></td>
		<td width="129">Re-Enter Password</td>
		<td width="170"><input type="password" name="repassword" /></td>
	</tr>

	<tr class="alt">
		<td height="29">User Status</td>
		<td><input type="text" name="status" /></td>
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
		<td><input type="text" name="Issuedate"  id="datepick"  /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
		
	<tr class="alt">
		<td height="31" colspan="4" align="center">
		<input type="submit" value="Add"/> <input type="reset"   value="Cancel" />
		</td>
	</tr>
</table>
<div style=" width:640px;background-color:#92b22c">&nbsp;</div>

</form>
<script type="text/javascript" src="datepickr.min.js"></script>
	<script type="text/javascript">
			new datepickr('datepick');
			
			new datepickr('datepick2', {
				'dateFormat': 'm/d/y'
			});
			
			new datepickr('datepick3', {
				'fullCurrentMonth': false,
				'dateFormat': 'l, F j'
			});
			
			new datepickr('datepick4', {
				dateFormat: '\\l\\e jS F Y', /* need to double escape characters that you don't want formatted */
				weekdays: ['dimanche', 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi'],
				months: ['janvier', 'février', 'mars', 'avril', 'mai', 'juin', 'juillet', 'août', 'septembre', 'octobre', 'novembre', 'décembre'],
				suffix: { 1: 'er' },
				defaultSuffix: '' /* the suffix that is used if nothing matches the suffix object, default 'th' */
			});
		</script>

</div>
<br/><br/>
<div >
<form  action="#" method="get">
     <h3>USER TABLE</h3>
      
<table width="513"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" border="0" >
	<tr class="alt"><th width="105" height="20" >User Name</th>
	<th width="84">User Code</th>           			
	<th width="81">password</th>
	<th width="73" align="right">User Status</th>
	<th width="78">User Level</th>
	<th width="92">&nbsp;</th>
	</tr>
	</table>
	<div style="height: 145px; overflow-y: scroll; width:600px"" id="div1">
	<table width="597"  cellpadding="0" cellspacing="0"
	bordercolor="#000000" id="customers" align="center" height="31" border="0">

		<% for(UserMasterBean bean:list)
		{
			String code=bean.getUsercode();
		%>	
 <tr  class="row"  bgcolor="#FFFFFF">
		<td width="136"><%=bean.getUsername()%></td>
		<td width="107" height="31"><%=bean.getUsercode()%></td>
		<td width="98" ><%=bean.getUserpwd()%></td>
		<td width="111" ><%=bean.getStatus()%></td>
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