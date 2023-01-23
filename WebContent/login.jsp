<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Payroll </title>
        <link href="css/layout.css" rel="stylesheet" type="text/css" />
        
    <script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
 <link href="css/layout.css" rel="stylesheet" type="text/css" />
<!-- Custom jquery scripts -->
<script src="js/jquery/custom_jquery.js" type="text/javascript"></script>

<!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD></HEAD> png fix -->

   <%
	int action=-1;
	try
	{
		action = Integer.parseInt(request.getParameter("action"));
	}
	catch(Exception e)
	{
	}

%>
 <style type="text/css">
            .flable{
                color:gray;
            }
            .status{
                font-family:verdana;
                font-size:12px;
            }
            .uname{
                color:blue;
            }
        </style>
        <script src="jquery.js" type="text/javascript"></script>
        


<script type="text/javascript">
$(function() {
	 window.history.forward();
    return false;
});
window.history.forward();
		function checkFlag()
		{	
			
			//var flag = parseInt(document.getElementById("action").value);
			var flag = <%=action%>;
			if(flag == 1)
			{
				window.history.forward();
				alert("Log Out Successfully");
				window.location.href="login.jsp";
			}
			else if(flag == 0)
			{
				alert("You are out of session Please Login again");
				window.location.href="login.jsp";
			} 
			else if(flag == 2)
			{
				alert("Wrong Username Or Password is Entered, Please try again!!");
				window.location.href="login.jsp";
			} 
			else if(flag == 3)
			{
				alert("Someone Else has Logged in with the same Credentials, to Continue Please Log In Again");
				window.location.href="login.jsp";
			} 
			window.history.forward();
		}
		
		function  noBack()
		{
			window.history.forward();
		}
		
		
		function validate()
		{
			if(document.loginform.username.value=="")
			{
				 alert("please enter userName");
				 document.loginform.username.focus();
				 return false;
				}
			if(document.loginform.pass.value=="")
			{
				 alert("please enter password");
				 document.loginform.pass.focus();
				 return false;
				}
			
			
		}
		
</script>     

    </head>
    <body  id="login-bg" onload="checkFlag()" onunload="noBack()">
	
      
		
	<!-- <img src="images/Building construction.png" height="300px" width="300px" style="margin-left:100px; margin-top:300px; position:absolute; left:60%; z-index:1000;"> -->

    <div class="container" >
 	<img src="images/BusinessBankLogo.png" style="height:250px;width:23	8px;margin-top:3px; float:left; "></img>
         <div class="innercontainer"> 
		 <!-- Start: login-holder -->
		 <form name="loginform" id="loginform" action="LoginServlet" method="post" onsubmit="return validate()">
		 <input type="hidden" name="action" id="action" value="login"></input>
<div class="login-holder">

	
	
	<!--  start loginbox ................................................................................. -->
	<div class="loginbox">
	<table>
	<tr>
	<td>
	 <a href="#" title="Login">
		             <img src="images/login1.png" class="payroll, hover_text" alt="Login" style=" float:left; box-shadow:0 0 0px 0px #ffffff;">
				  </a>
				  </td>
	<!--  start login-inner -->
	<td>
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<th>Username</th>
			<td><input type="text" name="username" id="username"  style="border-radius:6px; height:24px;  margin: 10px; border: 1px solid #CACACA;"/><span class="status"></span></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="pass" id="pass"  value="" style="border-radius:6px; height:24px;  margin: 10px; border: 1px solid #CACACA;"  /></td>
		</tr>
		 <tr>
			<th></th>
			<td valign="top"><!-- <input type="checkbox" style="background-image=../bbk_green.gif" />Remember me --> &nbsp;&nbsp;&nbsp;</td>
		</tr> 		<tr>
			<td><input type="submit"  value="Login" style="height:24px;width:75px;" /></td>
			<td></td>
		</tr>
		 <tr>
			<th></th>
			<td valign="top">&nbsp;&nbsp;&nbsp;</td>
		</tr>
		
	
	</table>
		</td>
		</tr>
		<tr style="text-align:right; ">

			<td>
		<input type="hidden" name="action" id="action" value="<%=action%>" />
	<a href="forgotpwd.jsp">Forgot Password?</a>
	</td>
	</tr>
		</table>
	</div>
	</form>
 	<!--  end login-inner -->
	
	   
 </div>
 <!--  end loginbox -->
			
        
        
        </div>
		
		
    </body>
</html>