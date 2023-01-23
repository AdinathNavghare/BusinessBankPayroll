<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<!--  checkbox styling script -->
<script src="js/jquery/ui.core.js" type="text/javascript"></script>
<script src="js/jquery/ui.checkbox.js" type="text/javascript"></script>
<script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
<title>Code Master Maintenance</title>
<%
	String pageName = "CodeMastMTN.jsp";
	int flag=-1;
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
	LookupHandler lkph= new LookupHandler();
	ArrayList<Lookup> DESC=new ArrayList<Lookup>();
	ArrayList<CodeMasterBean> TranCode=new ArrayList<CodeMasterBean>();
 	DESC=lkph.getCDTYPELookup();
	String select=new String();
 	//int t=0;
	try
	{
		String action = request.getParameter("action");
		
		flag =  Integer.parseInt(request.getParameter("flag1")==null?"0":request.getParameter("flag1"));
		if(action.equalsIgnoreCase("getCodes"))
		{
			String key=request.getParameter("key");
			select=request.getParameter("selected");
			CodeMasterHandler CMH=new CodeMasterHandler();
			TranCode=CMH.getCDMAST(key);
		}
	}
	catch(Exception e)
	{
		
	}
%>

<script type="text/javascript">


function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='.1234567890';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {
	    return true;
	  }
	}



function getTranCodes()
{
	var type=document.getElementById("select.type").value;
	var desc=document.getElementById("select.type");
	var selected=desc.options[desc.selectedIndex].text;
	//alert(selected);
	window.location.href="CodeMastMTN.jsp?action=getCodes&key="+type+"&selected="+selected;
}
function hideDiv()
{
	var type=document.getElementById("select.type").value;
	if(type!="0")
		document.getElementById("div1").hidden=false;
	else
		document.getElementById("div1").hidden=true;
}

function update(key)
{
	window.showModalDialog("UpdateCodeMast.jsp?key="+key,null,"dialogWidth:700px; dialogHeight:230px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");	
	getTranCodes();
}
function validation()
{
	if(document.getElementById("select.addType").selectedIndex == 0)
	{
		alert("Please Select Transaction type");
		document.getElementById("select.addType").focus();
		return false;
	}
	if(document.getElementById("trcode").value =="")
	{
		alert("Please Enter Transaction Code ");
		document.getElementById("trcode").focus();
		return false;
	}
	if(document.getElementById("desc").value =="")
	{
		alert("Please Enter Description of Transaction ");
		document.getElementById("desc").focus();
		return false;
	}
	if(document.getElementById("sdesc").value =="")
	{
		alert("Please Enter Short Description of Transaction ");
		document.getElementById("sdesc").focus();
		return false;
	}
	if(document.getElementById("subsystem").value =="")
	{
		alert("Please Enter Sub System Code ");
		document.getElementById("subsystem").focus();
		return false;
	}
	if(document.getElementById("acno").value =="")
	{
		alert("Please Enter Account Number ");
		document.getElementById("acno").focus();
		return false;
	}
	if(document.getElementById("printsequnce").value =="")
	{
		alert("Please Enter sequance number ");
		document.getElementById("printsequnce").focus();
		return false;
	}
	
	
	}
	
function checkFlag() {
	var f = parseInt(document.getElementById("flag1").value);
	if (f == 1) {
		alert("Code added Successfully");
	}
	if (f == 2) {
		alert("Error in adding code!");
	}
}
	
</script>
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

</style>
</head>
<body onload="checkFlag()" style="overflow: hidden;">
<div><%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%></div>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Transaction Codes </h1>
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
			<div id="table-content" style="overflow-y:scroll; height:500px;" >
			<center>

<h3><font face="Georgia" size="4">Code Master Maintenance </font></h3>
<table width="963" height="371" id="customers" border="1">
<tr  bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="middle">
        Transaction Codes
          <select name="select.type" id="select.type" style="width:auto" onChange="getTranCodes()">
          <option value="0" selected>Select</option>
          <%
          		for(Lookup temp:DESC)
          		{
          			if(temp.getLKP_DESC().equalsIgnoreCase(select))
          			{
          %>
          <option value="<%=temp.getLKP_SRNO() %>" selected="selected" ><%=temp.getLKP_DESC() %></option>
          <%
          			}
          			else
          			{
          	%>			
          			<option value="<%=temp.getLKP_SRNO() %>"><%=temp.getLKP_DESC() %></option>
          							
          	<%		}
          		}
          %>
          </select>
        	
       <table  border="0" bgcolor="#999999">
					<tr align="center" valign="middle" bgcolor="#CCCCCC" height="auto">
					  <th width="78">Trans. Code</th>
						<th width="199">Description</th>
					    <th width="87">Print In Slip </th>
					    <th width="83">Gross Y/N</th>
					    <th width="73">Taxable</th>
					    <th width="100">HO Autopost</th>
					    <th width="61">A/C No</th>
					    <th width="134">Subsystem Code </th>
		      </tr>
	    </table>
			<div id="div1" style="max-height: 200px; overflow-y:scroll;max-width:935px; background-color:#FFFFFF;" align="center">
				<table  height="61" border="1" id="customers">
					<%
						for(CodeMasterBean temp1:TranCode)
						{
					%>
						<tr align="left" valign="top" bgcolor="#FFFFFF" class="row" onClick="update('<%=temp1.getTRNCD()%>')">
						  <td width="78" align="center" valign="middle"><%=temp1.getTRNCD() %></td>
						  <td width="199" align="left" valign="middle"><%=temp1.getDISC() %></td>
					      <td width="87" align="center" valign="middle"><%=temp1.getPSLIPYN()%></td>
						    <td width="83" align="center"><%=temp1.getGROSS_YN() %></td>
						    <td width="73" align="center" valign="middle"><%=temp1.getTAXABLE()%></td>
						    <td width="100" align="center" valign="middle"><%=temp1.getAUTOPOST()%></td>
						    <td width="61" align="center" valign="middle"><%=temp1.getACNO()%></td>
						    <td width="116" align="center" valign="middle"><%=temp1.getSUBSYS()%></td>
						</tr>
					<%
						}
					
					%>
			  </table>
			</div></td>
        </tr>
   
<tr class="alt"><td align="center" valign="middle">&nbsp;</td></tr>
<tr valign="top"><td align="center">
<form action="CodeMasterServlet?action=addnew" method="post" onSubmit="return validation()">
      <input type="hidden" id="page" name="page" value="CodeMastMTN.jsp">    
    <table width="767" height="214" border="0" bgcolor="#2f747e" id="customers">
	<tr class="alt"><th colspan="5"> Add New Code Information </th>
	</tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#FFFFFF">Select Type</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><label>
      <select name="select.addType" id="select.addType" style="width:auto" class="form-control" >
          <option value="0" selected>Select</option>
          <%
          		for(Lookup temp:DESC)
          		{
          			String str=temp.getLKP_DESC()+"-"+temp.getLKP_SRNO();
          			if(temp.getLKP_DESC().equalsIgnoreCase(select))
          			{
          %>
          <option value="<%=str%>" selected="selected" ><%=temp.getLKP_DESC() %></option>
          <%
          			}
          			else
          			{
          	%>			
          			<option value="<%=str%>"><%=temp.getLKP_DESC() %></option>
          							
          	<%		}
          		}
          %>
          </select>
      </label></td>
     <!--  <td></td> --><!-- <td></td><td></td> -->
       <td align="left" valign="middle" bgcolor="#FFFFFF">&nbsp;</td>
      	<td align="right" valign="middle" bgcolor="#FFFFFF">TransactionCode</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><input type="text" name="trcode" id="trcode" class="form-control" 
      placeholder="Enter number"></td>
    </tr>
    <tr class="alt">
      <td width="124" height="24" align="right" valign="middle" bgcolor="#FFFFFF">Description</td>
      <td width="213" align="left" valign="middle" bgcolor="#FFFFFF"><input name="desc" type="text" id="desc" size="30" maxlength="50" class="form-control" ></td>
      <td width="27" align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
      <td width="135" align="right" valign="middle" bgcolor="#FFFFFF">Short Description</td>
      <td width="246" align="left" valign="middle" bgcolor="#FFFFFF"><input name="sdesc" id="sdesc" type="text" maxlength="14" class="form-control" ></td>
    </tr>
    <tr class="alt">
      <td height="18" align="right" valign="middle" bgcolor="#FFFFFF">Print In Slip</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><label>
        <select name="select.print" id="select.print" class="form-control" >
          <option value="Y">YES</option>
          <option value="N" selected="selected">NO</option>
        </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#FFFFFF">Gross Y/N   </td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><label>
      <select name="select.autoCalc" id="select.autoCalc" class="form-control" >
        <option value="Y">YES</option>
        <option value="N" selected="selected">NO</option>
      </select>
      </label></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#FFFFFF">Taxable</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><label>
      <select name="select.taxable" id="select.taxable" class="form-control" >
        <option value="Y">YES</option>
        <option value="N" selected="selected">NO</option>
      </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#FFFFFF">SubsystemCode </td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><input type="text" name="subsystem" id="subsystem" class="form-control" ></td>
    </tr>
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#FFFFFF">HO Autoposting</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><label>
      <select name="select.autop" id="select.autop" class="form-control" >
        <option value="Y">YES</option>
        <option value="N">NO</option>
      </select>
      </label></td>
      <td align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#FFFFFF">A/C No.</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"><input name="acno" id="acno" type="text" size="15" class="form-control" placeholder="Enter number"></td>
    </tr>
    
    <tr class="alt">
      <td height="24" align="right" valign="middle" bgcolor="#FFFFFF">Printing Sequance</td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"> <input class="form-control" name="printsequnce" id="printsequnce" type="text" size="15" onkeypress="return inputLimiter(event,'Numbers')" placeholder="Enter number" >  (E.g. 1,2,3....)    </td>
      <td align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
      <td align="right" valign="middle" bgcolor="#FFFFFF"> </td>
      <td align="left" valign="middle" bgcolor="#FFFFFF"></td>
    </tr>
    
    <tr align="center" valign="middle" class="alt">
      <td height="26" colspan="5" bgcolor="#FFFFFF">
          <label>
          <input type="submit" class="myButton"  name="Submit2" value="Save" />
          </label>
          <label>
          <input type="reset" class="myButton"  name="Submit3" value="Cancel" />
        </label></td>
    </tr>
  </table>

</form></td>
</tr>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>
</table>
<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
</center>

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