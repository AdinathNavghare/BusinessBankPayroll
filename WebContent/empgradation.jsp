
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<!--  checkbox styling script -->

<link rel="stylesheet" type="text/css" media="all" href="jsDatePick_ltr.min.css" />

<title>Employee Gradation</title>



</head>
<body onLoad="hideDiv()" style="overflow: hidden;"><center><font face="Georgia"  size="2">
<div >
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
 <%@include file="subHeader.jsp"%></div>
</div>
<div style="height: 500px; overflow-y: scroll; width:100%"" id="div1">
<div id="content-table-inner"><!--  start table-content  -->


<div id="table-content">

<br>

<br>
<div style="border:thick">

<form  name="userForm" action="UserMasterServlet?action=add" method="post" onSubmit="return validate()">
     <h3>Employee Gradation</h3>
      <table width="1067" cellpadding="0" cellspacing="0" id="customers"align="center">
	<tr>
		<th width="1065" height="22">Employee Gradation</th>
    </tr>
</table>
  
	 <table width="1012"  cellspacing="0" cellpadding="0" align="center" height="194" border="0" id="customers">
	
		<tr class="alt"><td width="1012"> <table> <tr class="alt">
		<td width="119"  height="29">PinCode</td>
		<td width="203"><input type="text" name="Description" /></td>
		<td width="133" > Alpha Code</td>
		<td width="158" ><input type="text" name="effDate"/></td>
	 </tr>
	<tr class="alt">
		<td  height="29">Description</td>
		<td width="203"><input type="text" name="Description" /></td>
		<td > Effective Date</td><td ><input type="text" name="effDate"/></td>
	 </tr>
	 <tr class="alt">
		<td height="29"   >Basic</td>
		<td   colspan="3"><input type="text" name="Basic" /></td>
		
	 </tr></table> </td></tr>
    <tr class="alt" style="width: 300"><td height="47" colspan="4"> 
	
	<table bgcolor="#333333" id="">
	<tr><td>1st Increment</td><td>2nd Increment</td><td>3rd Increment</td><td>4th Increment</td><td>5th Increment</td><td>6th Increment</td></tr>
    <tr style="color:#FFFFFF"><td width="160" height="29"   > 
        <input type="text" name="firstI"size="20" /></td><td width="162"  > 
        <input type="text" name="secondI" size="20"/></td><td width="160"  > 
          <input type="text" name="thirdI" size="20"/></td><td width="170"  > 
            <input type="text" name="fourthI"size="20" /></td><td width="162"  > 
              <input type="text" name="fifthI" size="20"/></td><td width="158"  > 
                <input type="text" name="sixthI"size="20" /></td></tr>
	
	 <tr><td>No Of Years</td><td>No Of Years</td><td>No Of Years</td><td>No Of Years</td><td>No Of Years</td><td>No Of Yearst</td></tr>
	<tr style="color:#FFFFFF"><td height="29"><input type="text" name="year1"size="20" /></td><td><input type="text" name="year2" size="20"/></td><td><input type="text" name="year3"size="20" /></td><td><input type="text" name="year4"size="20" /></td><td><input type="text" name="year5" size="20"/></td><td><input type="text" name="year6" size="20"/></td></tr>
   <tr><td>Medical Exps </td>
   <td>Education Exps </td>
   <td>Closing Exps </td>
   <td>Clg Exps </td>
   <td>Cash </td>
   <td>Ltc Exps </td>
   </tr>
   <tr><td width="160"><font size="2" face="Georgia">&nbsp; </font>
     <input type="text" name="medicalexp" /></td> <td width="162"><font size="2" face="Georgia">&nbsp; </font>
       <input type="text"  name="eduExp"/></td> <td width="160"><font size="2" face="Georgia">&nbsp; </font>
         <input type="text"  name="closeExp"/></td> <td width="170"><font size="2" face="Georgia">&nbsp; </font>
           <input type="text"  name="clgExp"/></td> <td width="162"><font size="2" face="Georgia">&nbsp; </font>
             <input type="text"  name="cashExp"/></td> <td><font size="2" face="Georgia">&nbsp; </font>
     <input type="text" name="Ltcexp" /></td></tr>
	 <td>Conveyance Exps </td>
	 <td>FieldWork </td>
	 <td>Washing Exps </td>
	 <td>Exg Exps </td><td> 
            </td><td> 
            </td>
   <tr ><td><font size="2" face="Georgia">&nbsp; </font>
     <input type="text" name="Ltcexp" /></td> <td><font size="2" face="Georgia">&nbsp; </font>
       <input type="text"  name="conveExp"/></td> <td><font size="2" face="Georgia">&nbsp; </font>
         <input type="text"  name="fieldwork"/></td> <td><font size="2" face="Georgia">&nbsp; </font>
         <input type="text"  name="washExp"/></td> <td> 
            </td><td> 
            </td></tr>
   
   
   </table>
	</td>
	</tr>
	
	
	<tr class="alt">
		<td height="31" colspan="4" align="center">
		<input type="submit" value="Add"/> <input type="button" value="Modify"/>  <input type="button" value="Delete"/> <input type="button" value="Report"/> <input type="reset"   value="Exit" /> <input type="button" value="Save"/> <input type="reset" value="Cancel"/>
		</td>
	</tr>
</table>
<div style=" width:1084px;background-color:#92b22c">&nbsp;</div>

</form>
</div>
</div>
</div>
</div>

</font>

</center>
</body>
</html>