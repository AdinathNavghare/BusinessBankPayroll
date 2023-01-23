<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Add New Lookup</title>
<%
	LookupHandler lkp = new LookupHandler();
	ArrayList<Lookup> DESC = new ArrayList<Lookup>();
	DESC = lkp.getMainLookup();

%>
<script type="text/javascript">
	var xmlhttp;
	var url="";
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	function showHide(flag)
	{
		if(flag==1)
		{
			document.getElementById("select.type").disabled=false;
		}
		else
		{
			document.getElementById("select.type").selectedIndex=0;
			document.getElementById("select.type").disabled="disabled";
		}
	}

	function getCodeSrno()
	{
		var code =document.getElementById("select.type").value;
		url="LookupServlet?action=maxsr&code="+code;
		xmlhttp.onreadystatechange=callback;
    	xmlhttp.open("GET", url, true);
    	xmlhttp.send();
	}
	function callback()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	document.add.code.value=document.getElementById("select.type").value;
        	document.add.srno.value=response;
		}
	}
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


</head>

<body>
<font face="Georgia" size="2">
<center>
<h3>Add New Lookup</h3>
<table width="658">
<tr bgcolor="#606060" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr>
	<td align="center" valign="middle"><br/>
	  <form action="LookupServlet?action=addnew" method="post" name="add">
		<table width="397" border="0" bgcolor="#606060">

 		<tr align="center" bgcolor="#CCCCCC">	
   		<td align="right" valign="middle" bgcolor="#CCCCCC"><font size="2" face="Georgia">What to Add? </font></td>
  	 	<td valign="middle" bgcolor="#CCCCCC">&nbsp;</td>
   		<td align="left" valign="middle" bgcolor="#CCCCCC">
     	<label>
       <input type="radio" name="check" value="group" onclick="showHide(0)"/>
       Group</label>
          <label>
       <input type="radio" name="check" value="element" onclick="showHide(1)"/>
       Group Element</label>
	</td>
 </tr>
 <tr align="center" bgcolor="#FFFFFF">
    <td align="right" valign="middle">Select Lookup Type</td>
    <td valign="middle">&nbsp;</td>
    <td align="left" valign="middle"><select  name="select.type" id="select.type" onchange="getCodeSrno()" disabled="disabled">
      <option value="select" selected="selected">Select</option>
     						<%
								for (Lookup temp : DESC)
								{
									//String str = temp.getLKP_CODE() + "-" + temp.getLKP_DESC();
									String test=temp.getLKP_DESC().trim();
									
							%>
      								<option value="<%=temp.getLKP_CODE()%>"><%=temp.getLKP_DESC()%></option>
     
      						<%
								}
							%>
    </select></td>
 </tr>
  <tr>
    <td width="144" align="right" valign="middle" bgcolor="#FFFFFF">Code<font face="Georgia">&nbsp; </font></td>
    <td width="13" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="226" align="left" valign="middle" bgcolor="#FFFFFF" id="code"><input type="text" name="code" id="code"/></td>
  </tr>
  <tr>
    <td align="right" valign="middle" bgcolor="#FFFFFF"><font face="Georgia">Serial Number </font></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td align="left" valign="middle" bgcolor="#FFFFFF" id="srno"><label><input type="text" name="srno" id="srno"/>
       </label></td>
  </tr>
  <tr>
    <td align="right" valign="middle" bgcolor="#FFFFFF">Description</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td align="left" valign="middle" bgcolor="#FFFFFF" id="desc"><input type="text" name="desc" id="desc"/></td>
  </tr>
</table>
<p>
  <label>
  <input type="submit" name="save" value="Save" />
  </label>
  <label>
  <input type="reset" name="cancel" value="Cancel" />
  </label>
</p>
	  </form>
	</td>
  </tr>
  <br/>
<tr bgcolor="#606060" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="top"><br/>

<br/></td>
</tr>
<tr><td align="center" valign="middle" bgcolor="#606060">&nbsp;</td>
</tr>
</table>


</center>
</font>

</body>
</html>
