<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User maintainance Table</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />




<script language="javascript">


 


    function validate()
    {

    	alert("validate function is calling");
    	
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

<script type="text/javascript">
        window.history.forward();
        function noBack()
         {
             window.history.forward();
         }
    </SCRIPT>
 
</head>
<body onLoad="noBack();" onpageshow="if (event.persisted) noBack();"  >

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
 <img alt="" src="images/wall.jpg" width="850" height="400"/>
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