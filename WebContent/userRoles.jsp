<%@page import="payroll.Model.RoleBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<style type="text/css">
<!--
body,td,th {
	font-family: Georgia;
}
-->
</style>

<style type="text/css">
.ac_results {
	padding: 0px;
	border: 1px solid #84a10b;
	background-color: #84a10b;
	overflow: hidden;
}

.ac_results ul {
	width: 100%;
	list-style-position: outside;
	list-style: none;
	padding: 0;
	margin: 0;
}

.ac_results li {
	margin: 0px;
	padding: 2px 5px;
	cursor: default;
	display: block;
	color: #fff;
	font-family: verdana;
	cursor: pointer;
	font-size: 12px;
	line-height: 16px;
	overflow: hidden;
}

.ac_loading {
	background: white url('../images/indicator.gif') right center no-repeat;
}

.ac_odd {
	background-color: #84a10b;
	color: #ffffff;
}

.ac_over {
	background-color: #5a6b13;
	color: #ffffff;
}

.input_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: 1px solid #84a10b;
	padding: 2px;
	width: 150px;
	color: #000;
	background: white url(../images/search.png) no-repeat 3px 2px;
	padding-left: 17px;
}

#accordion {
	list-style: none;
	padding: 0 0 0 0;
	width: 170px;
}

#accordion li {
	display: block;
	background-color: #2c6da0;
	font-weight: bold;
	margin: 1px;
	cursor: pointer;
	padding: 35 35 35 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	height: 30px;
	text-decoration: none;
}

#accordion li:hover {
	background-color: #74ACFF;
}

#accordion ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
}

#accordion ul li {
	font-weight: normal;
	cursor: auto;
	padding: 0 0 0 7px;
}

#accordion a {
	text-decoration: none;
	color: #FFFFFF;
}

#accordion a:hover {
	text-decoration: underline;
	text-shadow: #FF9927;
}

#accordion ul li.submenu {
	background-color: #66CCFF;
}

#accordion ul li.submenu:hover {
	background-color: #74ACFF;
}
</style>



<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>
<%
	String pageName = "userRoles.jsp";
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
%>
<%
	int action = -1;
	try {
		action = Integer.parseInt(request.getParameter("action"));
	} catch (Exception e) {
		System.out.println("First Time Loading");
	}
%>

<script type="text/javascript">
            $(document).ready(function(){
            	    $(".uname").change(function(){
                	
                    var uname = $(this).val();
                    if(uname.length > 4){
                        $(".status").html("<img src='images/loading.gif'><font color=gray> Checking availability...</font>");
                         $.ajax({
                            type: "POST",
                            url: "UserServlet",
                            data: "uname="+uname+"&action=check",
                            success: function(msg){

                            	if(msg == 1){
                             	   $(".status").html("<font color=green>"+uname + " is Available</font>");
                             	   
                              }else {
                             	 $(".status").html("<font color=red>"+uname + " is already in use</font>"); 
                             	 $("#uname").val('');
                              }
                                
                            }
                        }); 
                         
                    }
                    else{
                         
                        $(".status").html("<font color=red>username shold be <b>4</b> chars</font>");
                    }
                    
                });
            	    
            	//for chk Employee Id
            	    $("#empid").focusout(function(){
                    	var empid = $(this).val();
                    	if(empid.length > 4){
                            $(".IdStatus").html("<img src='images/loading.gif'><font color=gray> Checking availability...</font>");
                             $.ajax({
                                type: "POST",
                                url: "UserServlet",
                                data: "empid="+empid+"&action=checkID",
                                success: function(msg){

                                	if(msg == 1){
                                 	   $(".IdStatus").html("<font color=green>"+empid + " is Available</font>");
                                 	   
                                  }else {
                                 	 $(".IdStatus").html("<font color=red>"+empid + " is already in use</font>"); 
                                 	 $("#empid").val('');
                                  }
                                    
                                }
                            }); 
                             
                        }
                        else{
                             
                            //$(".IdStatus").html("<font color=red>Try Again</font>");
                        }
                        
                    });
            });
        </script>
<script type="text/javascript">
	function validate() {

		var cpass = document.userform.confpass.value;
		var pass = document.userform.pass.value;

		var empid = document.getElementById("empid").value;

		if (document.getElementById("empid").value == "") {
			alert("Please select employee id");
			document.getElementById("empid").focus();
			return false;
		}
		var atpos = empid.indexOf(":");
		if (atpos < 1) {
			alert("Please Select Correct Employee Name");
			return false;
		}

		if (document.userform.uname.value == "") {
			alert("please enter the username");
			document.userform.uname.focus();
			return false;

		}

		if (document.userform.pass.value == "") {
			alert("please enter the password");
			document.userform.pass.focus();
			return false;
		}

		if (cpass !== pass) {
			alert("please enter correct confirm password");
			document.userform.confpass.focus();
			return false;
		}
		if (document.userform.status.value == "Select") {
			alert("please enter the status");
			document.userform.status.focus();
			return false;
		}
		
		
		
		
		
		if(document.getElementById("eLine3").value=="" ||document.getElementById("eLine3").value==null){
			alert("Please enter email id !");
			return false;
		   }
		   else{
		  var emailID = document.getElementById("eLine3").value;
	   		atpos = emailID.indexOf("@");
	   		dotpos = emailID.lastIndexOf(".");
	   		if(emailID==null || emailID==""){
		   		alert("Please enter correct email id");
		  		document.getElementById("eLine3").focus();
		   		return false;
	   		}
	  		if (atpos < 1 || ( dotpos - atpos < 2 )){
	       		alert("Please enter email ID in format abc@gmail.com");
	       		document.getElementById("eLine3").focus();
	       		return false;
	        }  }
		
		
		
		
		
		
		

	}

	function checkFlag() {
		var flag = parseInt(document.getElementById("action").value);
		if (flag == 1) {
			alert("Record Saved Successfully");
		} else if (flag == 0) {
			alert("Error in Record Saving");
		}

	}
</script>
<script>
	jQuery(function() {
		$("#empid").autocomplete("list.jsp");
	});
</script>
</head>
<body onLoad="checkFlag()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer">
		<!-- start content -->
		<div id="content">
			<div id="page-heading">
				<h1>Create New User</h1>
			</div>
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
									<form name="userform" action="UserServlet?action=addnew"
										method="post" onSubmit="return validate()">
										<table width="371" border="1" align="center" id="customers">
											<tr>
												<th colspan="3">New User Creation</th>
											</tr>
											<tr>
												<td width="165" align="right">Employee ID&nbsp;&nbsp;</td>
												<td width="14">&nbsp;</td>
												<td width="170" align="center"><input type="text"
													name="empid" id="empid" class="empid" onClick="showHide()"
													title="Select EPPLOYEE ID"><span class="IdStatus"></span></td>
											</tr>
											<tr>
												<td align="right">Username&nbsp;&nbsp;</td>
												<td>&nbsp;</td>
												<td align="center"><input type="text" name="uname"
													id="uname" class="uname"><span class="status"></span></td>
											</tr>
											<tr>
												<td align="right">Password&nbsp;&nbsp;</td>
												<td>&nbsp;</td>
												<td align="center"><input type="password" name="pass"
													id="pass"></td>
											</tr>
											<tr>
												<td align="right">Confirm Password&nbsp;&nbsp;</td>
												<td>&nbsp;</td>
												<td align="center"><input type="password"
													name="confpass" id="confpass"></td>
											</tr>
											
											<tr>
											
											 <td 	align="right">Email Id</td>
											 <td>&nbsp;</td>
              								<td align="center"><input name="eLine3" type="text" id="eLine3" value="" maxlength="50"></td>
           
											</tr>
											<tr>
												<td align="right">Status&nbsp;&nbsp;</td>
												<td>&nbsp;</td>
												<td><label> &nbsp;&nbsp;&nbsp;&nbsp; <select
														name="status" id="status">
															<option value="Select" selected="selected">Select</option>
															<option value="Active">Active</option>
															<option value="Suspend">Suspended</option>
													</select>
												</label></td>
											</tr>

											<tr align="center">
												<td height="38" colspan="3"><label> <input
														type="submit" name="Submit1" value="Save" class="myButton">
												</label>&nbsp;&nbsp; <label> <input type="reset"
														name="clear" value="Clear" class="myButton">
												</label> &nbsp; <input type="submit" name=submit2
													value="Save & AsignRole" class="myButton" /></td>
											</tr>
										</table>
									</form>
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
			<div class="clear">&nbsp;</div>

		</div>
		<!--  end content -->
		<div class="clear">&nbsp;</div>
	</div>
	<!--  end content-outer........................................................END -->

	<div class="clear">&nbsp;</div>

	<input type="hidden" name="action" id="action" value="<%=action%>">
</body>
</html>