<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Payroll </title>
        <link href="css/layout.css" rel="stylesheet" type="text/css" />
        
    <script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
 <link href="css/layout.css" rel="stylesheet" type="text/css" />
<!-- Custom jquery scripts -->
<script src="js/jquery/custom_jquery.js" type="text/javascript"></script>

<!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD></HEAD> png fix -->
<script src="js/jquery/jquery.pngFix.pack.js" type="text/javascript"></script>
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

<script type="text/javascript">

window.history.forward();
		function checkFlag()
		{	
			
			var flag = parseInt(document.getElementById("action").value);
			if(flag == 1)
			{
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
	
      
		
	<img src="images/Building construction.png" height="300px" width="400px" style="margin-left:100px; margin-top:300px; position:absolute; left:60%; z-index:1000;">
	 <img src="images/backtsk.png" height="300px" width="400px" style=" margin-top: 130px;
    position: absolute;
    right: 52%; z-index:1000;"></img> 
    <div class="container" >
	<img src="images/home.png" style="margin-top:3px; float:left; "></img><b style="text-align:center; font-family:almendra; font-size: 38px;">Harsh Constructions</b>   Private Ltd.
         <div class="innercontainer"> 
		 <!-- Start: login-holder -->
		 <form name="loginform" id="loginform" action="LoginServlet" method="post" onsubmit="return validate()">
<div class="login-holder">

	
	
	<!--  start loginbox ................................................................................. -->
	<div class="loginbox">
	
	 <a href="#" title="Login">
		             <img src="images/login1.png" class="payroll, hover_text" alt="Login" style=" float:left; box-shadow:0 0 0px 0px #ffffff;">
				  </a>
	<!--  start login-inner -->
	
		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<th>Username</th>
			<td><input type="text" name="username" id="username"  style="border-radius:6px; height:24px;  margin: 10px; border: 1px solid #CACACA;"/></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="pass" id="pass"  value="" style="border-radius:6px; height:24px;  margin: 10px; border: 1px solid #CACACA;"  /></td>
		</tr>
		<tr>
			<th></th>
			<td valign="top"><input type="checkbox" style="background-image=../bbk_green.gif" />Remember me</td>
		</tr>
		<tr>
			<th></th>
			<td><input type="submit"  value="Log In" class="buttonred"/></td>
		</tr>
		</table>
	</div>
	</form>
 	<!--  end login-inner -->
	
	<a href="">Forgot Password?</a>
	   
 </div>
 <!--  end loginbox -->
			
        
        
        </div>
		
		
    </body>
</html>