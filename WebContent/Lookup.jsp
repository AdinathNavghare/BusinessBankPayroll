<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="defaulrt" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<title>Lookup Management</title>
<%	String pageName = "Lookup.jsp";
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
	LookupHandler lkp = new LookupHandler();
	ArrayList<Lookup> DESC = new ArrayList<Lookup>();
	ArrayList<Lookup> GROUPDESC = new ArrayList<Lookup>();
	ArrayList<Lookup> SUBTYPE = new ArrayList<Lookup>();
	DESC = lkp.getMainLookup();
	GROUPDESC = lkp.getMainSubLookup();
	String key = new String();
	String selected = "noselected";
	String vals[] = new String[2];
	//to hhold the value coming from url
	try

	{
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("subType"))
		{
			key = request.getParameter("key");
			vals = key.split("-");
			key = vals[1];
			SUBTYPE = lkp.getSubLKP_DESC(vals[0]);
			}
	} catch (Exception e) {
		key = null;
	}
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

	function getSubDetails(SubType)
	{
		//var SubType=document.getElementById("select.subtype").value;
		//alert(SubType);
		var resp=confirm("Do you want to Modify this record?");
		if(resp==true)
		{
			document.getElementById("save").disabled="disabled";
			document.getElementById("update").disabled=false;
			document.getElementById("select.addType").disabled="disabled";
			document.getElementById("select.addGroup").disabled="disabled";
	//		document.getElementById("select.addGroup").disabled=false;
			document.add.action="LookupServlet?action=update";
			url="LookupServlet?action=subDetails&subType="+SubType;
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
        //	var group=lookup[0].getElementsByTagName("select.addGroup")[0].firstChild.nodeValue;
        	

        	document.add.code.value=code;
        	document.add.srno.value=srno;
        	document.add.desc.value=desc;
       // 	document.add.select.addGroup.value=select.addGroup;
        	
        	
        	//alert(code);
    	}
	}
	
	function getSubtype()
	{
		//alert("OK");
		var key=document.getElementById("select.type").value;
		if(key!="select")
		{
			window.location.href="Lookup.jsp?action=subType&key="+key;
		}
		else
		{
			window.location.href="Lookup.jsp";
		}
	}
	function hideDiv()
	{
		var type=document.getElementById("select.type").value;
		if(type!="select")
			document.getElementById("div1").hidden=false;
		else
			document.getElementById("div1").hidden=true;
	}
	function showHide(flag)
	{	
		document.getElementById("save").disabled=false;
		document.getElementById("update").disabled="disabled";
		document.add.action="LookupServlet?action=addnew";
		if(flag==1)
		{
			document.getElementById("select.addType").disabled=false;
			document.getElementById("select.addGroup").disabled=false;
			document.add.code.value="";
        	document.add.srno.value="";
        	document.add.desc.value="";
        	
		}
		else
		{
			document.getElementById("select.addType").selectedIndex=0;
			document.getElementById("select.addType").disabled="disabled";
			document.getElementById("select.addGroup").selectedIndex=0;
			document.getElementById("select.addGroup").disabled="disabled";
			document.getElementById("select.addGroup").disabled=false;
			document.add.code.value="";
			document.add.groupCode.value="";
        	document.add.srno.value="";
        	document.add.desc.value="";
        	
		}
	}

	function getCodeSrno()
	{
		
		var code =document.getElementById("select.addType").value;
		url="LookupServlet?action=maxsr&code="+code;
		xmlhttp.onreadystatechange=callbackCS;
    	xmlhttp.open("GET", url, true);
    	xmlhttp.send();
    	   	
    	
	}
	function callbackCS()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	var code=document.getElementById("select.addType").value;
        	document.add.code.value=code.split("-",1);
        	document.add.srno.value=response;

        	//document.getElementById("srno").value=response;
			//alert(response);
    	}
	}

	
	function confirmation()
	{
		var conf=confirm("Are you sure to make changes?");
		if(conf==true)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	function validate()
	{
	
	if(document.add.code.value=="")
		{
		   alert("please enter code");
		   document.add.code.focus();
		   return false;
	
		}
	if(document.add.srno.value=="")
	  {
		 alert("please enter serialnumber");
		 document.add.srno.focus();
		   return false;
	   }
	if(isNaN(document.add.srno.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.srno.focus();
	     return false;
	   }
	if(document.add.desc.value=="")
	  {
		 alert("please enter description");
		 document.add.desc.focus();
		   return false;
	   }
	 
	 
	}
</script>
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>
</head>
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height: 90%; width:auto">
<!-- start content -->
<div id="content"  >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Lookup Management</h1>
	</div>
	<!-- end page-heading -->

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
			<div id="table-content">
			
			<font face="Georgia" size="2">
<center>
<h3>Lookup Table Management</h3>
<table width="658" border="1" id="customers">
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="middle"><br/>
<table id="customers">
<tr class="alt">
<td width="135" height="20" align="left" >Select Lookup Type</td>
<td width="189" bgcolor="#FFFFFF">
<label>
  <select  name="select.type" id="select.type" onChange="getSubtype()" >
      <option value="select" selected>Select</option>
					<%
						for (Lookup temp : DESC)
						{
							String str = temp.getLKP_CODE() + "-" + temp.getLKP_DESC();
							String test=temp.getLKP_DESC().trim();
							if (test.equalsIgnoreCase(vals[1]))
							{
					%>
					<option value="<%=str%>" selected><%=temp.getLKP_DESC()%></option>
					<%
							} 
							else
							{
					%>
					<option value="<%=str%>"><%=temp.getLKP_DESC()%></option>

					<%
							}

						}
					%>
  </select>
</label>
</td>
</tr>
</table>
<br>
  <table width="577" border="1" id="customers">
    <tr class="alt">
      <th width="101" align="center" bgcolor="#999999" scope="col">Code</th>
      <th width="101" align="center" scope="col">Sr. No. </th>
      <th width="353" scope="col">Description</th>
    </tr>
    </table>
    <div style="height: 150px; overflow-y: scroll; width:600px" id="div1">
    <table width="577" border="0" bgcolor="#CCCCCC" style="margin-left:inherit;">
     <%
  		if (SUBTYPE.size() > 0)
  		{
			for (Lookup test1 : SUBTYPE)
			{
  				String sub=test1.getLKP_CODE()+"-"+test1.getLKP_SRNO();
  	%>
    <tr onClick="getSubDetails('<%=sub%>')" class="row" bgcolor="#FFFFFF" >
      <td width="105" align="center" scope="col"><%=test1.getLKP_CODE()%></td>
      <td width="110" align="center" scope="col"><%=test1.getLKP_SRNO()%></td>
      <td width="370" scope="col" ><%=test1.getLKP_DESC()%></td>
    </tr>
    <%    		
			}
  		}
  	%>
  </table>
</div>
    <span class="style1">#Click on the row to Modify.</span>
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="top">
<form action="LookupServlet?action=addnew" method="post" name="add" onSubmit="return validate()">
Add New Lookup
		<table width="397" border="1"id="customers">

 		<tr align="center" class="alt">	
   		<td align="right" valign="middle" bgcolor="#CCCCCC"><font size="2" face="Georgia">What to Add? </font></td>
  	 	<td valign="middle" bgcolor="#CCCCCC">&nbsp;</td>
   		<td align="left" valign="middle" bgcolor="#CCCCCC">
     	<label>
       <input type="radio" name="check" id="check" value="group" onClick="showHide(0)"/>
       Group</label>
          <label>
       <input type="radio" name="check" id="check" value="element" onClick="showHide(1)"/>
       Group Element</label>
	</td>
 </tr>
 <tr align="center" bgcolor="#FFFFFF">
    <td align="right" valign="middle">Select Lookup Type</td>
    <td valign="middle">&nbsp;</td>
    <td align="left" valign="middle"><select  name="select.addType" id="select.addType" onChange="getCodeSrno(this.value)" disabled="disabled">
      <option value="Default" selected="selected">Select</option>
     						<%
								for (Lookup temp : DESC)
								{
									String str = temp.getLKP_CODE() + "-" + temp.getLKP_DESC();
									String test=temp.getLKP_DESC().trim();
									
							%>
      								<option value="<%=str%>"><%=temp.getLKP_DESC()%></option>
     
      						<%
								}
							%>
    </select></td>
 </tr>

<tr align="center" bgcolor="#FFFFFF">
    <td align="right" valign="middle">Select Group</td>
    <td valign="middle">&nbsp;</td>
    <td align="left" valign="middle"><select  name="select.addGroup" id="select.addGroup" disabled="disabled">
      <option value="0" selected="selected">Select</option>
     						<%
								for (Lookup temp : GROUPDESC)
								{
									String str = temp.getLKP_CODE() + "-" + temp.getLKP_DESC();
									String test=temp.getLKP_DESC().trim();
									
							%>
      								<option value="<%=temp.getLKP_SRNO()%>"><%=temp.getLKP_DESC()%></option>
     
      						<%
								}
							%>
    </select></td>
 </tr>

<!--  
 <tr align="center" bgcolor="#FFFFFF">
    <td align="right" valign="middle">Select Group</td>
    <td valign="middle">&nbsp;</td>
    <td align="left" valign="middle"><select  name="select.addGroup" id="select.addGroup" disabled="disabled">
      <option value="-1" >Select</option>
     </select></td>
 </tr> 
  -->





  <tr>
    <td width="144" align="right" valign="middle" bgcolor="#FFFFFF">Code<font face="Georgia">&nbsp; </font></td>
    <td width="13" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="226" align="left" valign="middle" bgcolor="#FFFFFF" id="code"><input type="text" name="code" id="code"/></td>
  </tr>
  <tr>
    <td align="right" valign="middle" bgcolor="#FFFFFF"><font face="Georgia">Serial Number </font></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td align="left" valign="middle" bgcolor="#FFFFFF" id="srno"><label><input type="text" name="srno" id="srno" readonly="readonly"/>
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
  <input type="submit" name="update"  class="myButton"  id="update" value="Update" disabled="disabled" onClick="return confirmation()">
  </label> 
  <label>
  <input type="submit" name="save" id="save"  class="myButton"  value="Save" />
  </label>
  <label>
  <input type="reset" name="cancel"  class="myButton"  value="Cancel" />
  </label>
</p>
	  </form>

<br/>

</td></tr>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>
</table>


</center>
</font>
			
			
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

<div class="clear">&nbsp;</div>
    

</body>
</html>