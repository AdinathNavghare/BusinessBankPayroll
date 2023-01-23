
<%@page import="javax.mail.SendFailedException"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="payroll.DAO.*" %>
<%@page import="payroll.Model.*" %>

<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.EmpQualBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<!--for datepicker  -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style type="text/css">

.form-popup {
	
	display: none;
	position: relative;
	}
	
.form-popup1 {
	
	display: none;
	position: relative;
	}	

</style>

<script> 
  $(document).ready(function() {
	    $("#txtDate,#txtDate1,#txtDate2,#txtDate3,#txtDate4,#txtDate5").datepicker({
	    	changeMonth: true, 
	        changeYear: true, 
	        yearRange: "-90:+00",
	    	showOn: 'button',
	        buttonText: 'Show Date',
	       /*  / buttonImageOnly: true, / */
	        buttonImage: 'images/cal.gif',
	        dateFormat: "yy-mm-dd" 	
	    });
	});

 
  
  
  function openForm1() {
		
		var q=document.getElementById("literacy").value;
		var r=document.getElementById("lit").value;
		var s=document.getElementById("txt2").value;
		var t=document.getElementById("txt3").value;
		var u=document.getElementById("txt4").value;
		
		 try
			{
			
			var xmlhttp=new XMLHttpRequest();
		 	var url="";                                         
		 	var response="";                   
		 	url="ExprInterviewServlet?action=addCompLiteracy&literacy="+q+"&lit="+r+"&txt2="+s+"&txt3="+t+"&txt4="+u;
		 	  
				 	xmlhttp.onreadystatechange=function()
					{
						if(xmlhttp.readyState==4)
						{	
						response = xmlhttp.responseText;
						
						 if(response=="true" )
							{
							 alert(" Successfully save record!!!")
							 return false;
							}
						 else
						 {
								alert("fail...");	
						 }
						}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
				 
				}
				catch(e){} 	
				document.getElementById("myForm").style.display = "block";	
	}
  
  
  var html="";
  var html1="";
  function openForm2() {
  	
  	var s = [];
      var r = [];
      var w = [];
      
      $.each($("input[name='knowledge']:checked"), function(){            
          s.push($(this).val());   
      });
      var a= s.join("") 
      
      $.each($("input[name='knowledge1']:checked"), function(){            
          r.push($(this).val());
      });
      var b=r.join("");  
      
      $.each($("input[name='knowledge2']:checked"), function(){            
          w.push($(this).val());
      }); 
      var c=w.join("");
      
      var lang=document.getElementById("txt1").value;
  		
  	 try
  		{
  		
  		var xmlhttp=new XMLHttpRequest();
  	 	var url="";
  	 	var response="";
  	 	url="ExprInterviewServlet?action=AddLanguage&l="+lang+"&r="+b+"&w="+c+"&s="+a;
  	 	  
  			 	xmlhttp.onreadystatechange=function()
  				{
  					if(xmlhttp.readyState==4)
  					{	
  					response = xmlhttp.responseText;
  					
  					 if(response=="true" )
  						{
  						 alert(" Successfully save record!!!")
  						 return false;
  						}
  					 else
  					 {
  							alert("fail...");	
  					 }
  					}
  			};
  			xmlhttp.open("POST", url, true);
  			xmlhttp.send();
  			 
  			}
  			catch(e){}
  			
  	///////////////////////////////////////////////////////////		
  			var xmlhttp1=new XMLHttpRequest();
  		  var response3="";
  		  //alert(ProjectId);
  		      xmlhttp1.onreadystatechange=function()
  		        {
  		    	 
  		    	  
  		    	  if (xmlhttp1.readyState==4)
  		          {
  		        	
  		        	response3 = xmlhttp1.responseText.split(",");
  		        	var n=response3.length;
  		        	
			  	
			  	html +='<tr id="h1"><td>'+response3[0]+'</td><td>'+response3[1]+'</td><td>'+response3[2]+'</td><td>'+response3[3]+'</td><td>'+response3[4]+'</td><td align="center"><button  id="button" style="background-color:#1F5FA7;color:white;" onclick="removeRow('+response3[0]+')" type="button">Remove</button></td></tr>';
			  	
			  $('.form_data').html(html);
  
  		          }
  		    	  
  		    	 
  		        }
  		      xmlhttp1.open("POST","ExprInterviewServlet?action=DisplayLang",true);
  		    //xmlhttp.open("POST","ExprInterviewServlet?action=DisplayLang&exprid="+ProjectId,true);
  		      xmlhttp1.send(); 
  		      
    document.getElementById("myForm1").style.display = "block";
    
  }
  </script>
  
<style type="text/css">
.topics tr{height : 35px;}	

</style>

</head>
<body style="overflow-y:auto;"> 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:auto;height:80%;"  >
<!-- start content -->
<div id="content"  >

	<!--  start page-heading -->
<div id="content" align="center"  ><!--  start page-heading -->
			<div id="step-holder" align="center">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprPersonalDetails.jsp" >1</a></div>
			<div class="step-light-left"><a href="ExprPersonalDetails.jsp" >PersonalDetail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEmploymentDetails.jsp" >2</a></div>
			<div class="step-light-left"><a href="ExprEmploymentDetails.jsp">EMPLOYMENTDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprFamilyDetails.jsp" >3</a></div>
			<div class="step-light-left"><a href="ExprFamilyDetails.jsp"> FamilyDetails</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprEducation.jsp" >4</a></div>
			<div class="step-light-left"><a href="ExprEducation.jsp">Qualification</a></div>
            <div class="step-light-right">&nbsp;</div>
			<div class="step-no-off"><a href="ExprSalaryDrawn.jsp" >5</a></div>
			<div class="step-light-left"><a href="ExprSalaryDrawn.jsp">LAST DRAWN SALARY</a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off"><a href="ExprReference.jsp" >6</a></div>
			<div class="step-light-left"><a href="ExprReference.jsp">REFERENCES</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no"><a href="ExprOther.jsp" style="color: white;">7</a></div>
			<div class="step-dark-left"><a href="ExprOther.jsp">OtherDetails</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
		
		</div>
		
				
	</div>
		
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
			<div id="table-content" align="center">
			
				<table id="customers">
					<tr class="alt">
						<th style="width:850px;">Other Details</th>
					</tr>
				</table>
   
   <div id="addE" >
     <form action="#" method="Post">
		<div>
			<table width="861" border="1" bordercolor="#000000" id="customers" class="topics">
			<tr bordercolor="#000000">
			<td></td>
			<td>Select Name</td>
			<td>Literacy Name</td>
			<td width="30">Proficient</td>
			<td width="30">Average</td>
			<td width="30" colspan="2">Elementary</td>
			</tr>
			
			<tr class="alt"><td style="text-align: center;">Literacy</td>
				<td>
					 	<select name="literacy" id="literacy" >  
      					 <option value="none">Select</option>  
    						<option value="os">Operating System</option>
    						<option value="lang">Languages</option>
    						<option value="pack">Packages</option>
     			        </select>
					 </td>
					
					  <td ><input type="text" style="width:90%; " id="lit" name="lit"></td>
					  <td><input type="checkbox" id="txt2" name="knowledge" value="Yes" ></td>
					  <td><input type="checkbox"  id="txt3" name="knowledge1" value="Yes" ></td>
					  <td colspan="2"><input type="checkbox" id="txt4" name="knowledge2" value="Yes" ></td>
					 			  
			</tr>
					
					<tr class="alt"><td colspan="6" align="center" class="alt">
					
					 <!--  <input type="submit"  style="padding:4px 8px 4px;background-color:#1F5FA7;color:white;" value="Save" class="myButton" onclick="openForm()" /> --> 
					 <input type="button" class="myButton"  value="Submit"  onclick="openForm1()" />
					<!-- <input type="button"  value="Save"  class="myButton" onclick="openForm()"> -->
					</td></tr>                        
				<tbody class="form_data3" style="background-color: white;">
			  </tbody>
					
		  </table>	
		  
		</div>
		
		</form>
		<br><br>
		
		<!-- ---Start Div for view (button) records -->

<div class="form-popup" id="myForm" align="center">
		<form action="#" class="form-container">
		
		<table id="customers" align="center"  class="tablecontainer" width="74%"> 
		<tbody>
		<tr ><td colspan="9" align="center" >All Information</td></tr>
			 <tr style="border:solid;   border-color: black;">
			    	<th width="30">Select Literacy</th >
                   	<th width="20">Literacy</th >
                   	<th width="20">Proficient</th>
		        	<th width="50">Average</th>
		        	<th width="100">Elementary</th>	
             </tr>
</tbody>
</table>
<!-- <div style="overflow-y: scroll; height: 120px;width:970px;"> -->
<table id="customers"  align="center" class="tablecontainer">
<tr class="alt"></tr>
		
		
	 
	</table>
</div>
		
			
			
	<br><br>		
	
			<div  width:880px;">
		<div align="center"><h2>LANGUAGES KNOWN:(Mother tongue first)</h2> </div>
		<div>
		<form action="#" class="form-container">
			<table class="myTable" width="861" border="1" bordercolor="#000000" id="customers" >
					<tr align="center"  class="alt">
					 <th>Language</th>
					 <th>Speak</th>
					 <th>Read</th>
					 <th>Write</th>
					 <th></th>
					</tr>
					<tr  align="center"  class="alt">
						
					 <td><input type="text" id="txt1" name="lang" ></td>
					  <td><input type="checkbox" id="txt7" name="knowledge" value="Yes" ></td>
					  <td><input type="checkbox"  id="txt8" name="knowledge1" value="Yes" ></td>
					  <td><input type="checkbox" id="txt9" name="knowledge2" value="Yes" ></td>
					  <!-- <td> <button style="padding:4px 8px 4px;background-color:#1F5FA7;color:white;" type="button" onclick="openForm1()" >SAVE</button></td> -->
					  <td colspan="4" align="center"><input type="button" onclick="openForm2()" class="myButton"  value="Submit"  ></td>
					</tr>
		   </table>	
		</form>
		</div>
		<br><br>
		<div class="form-popup1" id="myForm1" align="center" >
			<table class="myTable" width="861" border="1" bordercolor="#000000" id="customers" align="center" >
			<tr align="center"  class="alt">
						<th></th>
						 <th>Language</th>
						 <th>Speak</th>
						 <th>Read</th>
						 <th>Write</th>
						<th></th>
						</tr>
			<tbody class="form_data" style="background-color: white;">
			
			</tbody>
			</table>
		</div>	
	</div>
	
			
			
		</form>
	</div>

<!-- -------End Div view button----- -->
		
		
	</div>
	<br><br>
	
	
	
		
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
</div>
<div class="clear">&nbsp;</div>
		
		<div class="clear">&nbsp;</div>

<script type="text/javascript">

function openForm() {
	  document.getElementById("myForm").style.display = "block";
	  document.getElementById("addE").style.display = "block";
	}
/* function openForm1() {
	  document.getElementById("myForm1").style.display = "block";
	  
	} */

	function closeForm() {
	  document.getElementById("myForm").style.display = "none";
	}

</script>
    


</body>
</html>