<%@page import="payroll.Model.OnAmtBean"%>
<%@page import="payroll.DAO.OnAmtHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.StringTokenizer"%>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
	<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		
	
		
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

 <script>
	jQuery(function() {
          $("#empcat").autocomplete("lookuplist.jsp");
	});
</script>
 
  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Code Dependency Management</title>

<%
	LookupHandler lkph = new LookupHandler();
	CodeMasterHandler CMH=new CodeMasterHandler();
	OnAmtHandler OAH = new OnAmtHandler();
	
	ArrayList<Lookup> ECAT = new ArrayList<Lookup>();
	ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();
	ArrayList<OnAmtBean> OALIST=new ArrayList<OnAmtBean>();
	
	ECAT=lkph.getSubLKP_DESC("ET");
	TRNCODE=CMH.getAllCDMASTList();
	
	int empcat=0;
	int trncd=0;
	String[] emp=new String[3];
	String ecat="";
	try
	{
		String action =request.getParameter("action");
		if(action.equalsIgnoreCase("getList"))
		{
			emp=request.getParameter("ecat").split(":");
			ecat=request.getParameter("ecat");
			
		    trncd=Integer.parseInt(request.getParameter("trncd"));
			//empcat=Integer.parseInt(ecat[0]);
			empcat=Integer.parseInt(emp[1]);
			OALIST=OAH.getOnAmtList(empcat,trncd);
		}
		
	}
	catch(Exception e)
	{
		
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

	function deleteRow(key)
	{
		var flag=confirm("Do you really want to Delete this row?");
		if(flag)
		{
			//alert("Deleted "+key);
			url="OnAmtServlet?action=del&key="+key;
			xmlhttp.onreadystatechange=callback;
    		xmlhttp.open("POST", url, true);
    		xmlhttp.send();
			
		}
	}

	function callback()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	if(response=="true")
        	{
				alert("Record Deleted");
				getList();
            }
        	else
        	{
        		alert("Error occured while deleting Record");
            }        	
    	}
	}
	function getList()
	{
		var ecat=document.getElementById("empcat").value;
		var trncd=document.getElementById("select.trncd").value;
		//window.showModalDialog("Lookup.jsp","Lookup Entry","dialogWidth=300px;dialogHeight=300px;center=yes;addressBar=no;");
		window.location.href="onAmtCTC.jsp?action=getList&ecat="+ecat+"&trncd="+trncd;
	}
	function validate()
	{
		 
		if( document.getElementById('select.addTrncd').value =="select")
		{
			alert("Please Select on Amount Transaction Code");
			return false;
			
			}
		if( document.getElementById('amtType').value =="Select")
		{
			alert("Please Select on Amount Type");
			return false;
			
			}
		 
	}
	function rset()
	 {
		document.getElementById('empcat').value = '';
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
		<!-- start content -->
		 
		<div id="content-table-inner"><!--  start table-content  -->
		<div id="div1">
		<div id="table-content"><br>
		<br>
		
		
		
		<font face="Georgia" size="2">
		<center>
		<h3>Code Dependency Management</h3>
		<form action="OnAmtServlet?action=addnew" method="post" name="add" onSubmit="return validate()">
		<input type="hidden" value="onAmtCTC.jsp" id="pageName" name="pageName">
		<table width="714" id="customers">
		<tr ><td align="center" valign="middle" bgcolor="#2F747E">&nbsp;</td></tr>
		<tr><td align="center" valign="middle"><br/>
		<table width="596" id="customers">
		<tr id="alt">
		<td  width="235" height="20" align="center" valign="middle">Employee Name : Number</td>
		<td width="304" align="center" valign="middle" >Transaction Code</td>
		<td width="41" rowspan="2" bgcolor="#FFFFFF"><label>
		  <input type="button" name="go" value="GO" onClick="getList()">
		</label></td>
		</tr>
		<tr>
			  <td height="20" align="center" bgcolor="#FFFFFF"> 
			 
			 	<%=ecat %><input type="hidden" name="empcat" id="empcat" size="35" onClick="rset()" value="<%=ecat%>" title="Select Employee ">
			 </td>
			 <td bgcolor="#FFFFFF" valign="top"> 
			  <%=CodeMasterHandler.getCDesc(trncd)%> <input type="hidden" name="select.trncd" id="select.trncd" value="<%=trncd%>">
			  </td>
		  </tr>
		</table>
		<br/>   
    <div style="height: 200px; overflow-y: scroll; width:auto;" id="div1">
     <table border="0" bgcolor="#CCCCCC" width="auto">
    <tr bgcolor="#999999">
      <th width="50"  align="center"   scope="col">Code</th>
      <th width="200"  align="center" scope="col">Description</th>
      <th width="300"  scope="col">Depends On</th>
      <th >Amount Type</th>
      <th width="70"  scope="col">&nbsp;</th>
    </tr>
     <%String desc="";
     	int maxsr=0;
  		if (OALIST.size() > 0)
  		{
			for (OnAmtBean test1 :OALIST )
			{
  				String sub=test1.getEMP_CAT()+"-"+test1.getTRNCD()+"-"+test1.getSRNO();
  				desc=CodeMasterHandler.getCDesc(test1.getTRNCD());
  				maxsr=test1.getSRNO();
  	%>
    <tr class="row" bgcolor="#FFFFFF" >
      <td height="26"  align="center" valign="middle" scope="col"><%=test1.getTRNCD()%></td>
      <td  align="center" valign="middle" scope="col"><%=CodeMasterHandler.getCDesc(test1.getTRNCD())%></td>
      <td align="left" valign="middle"  scope="col" ><%=CodeMasterHandler.getCDesc(test1.getONAMTCD())%></td>
      <td align="center" valign="middle" ><%=test1.getAMT_TYPE() %></td>
      <td align="center" valign="top"  scope="col" ><input type="button" value="Delete" onClick="deleteRow('<%=sub%>')" /></td>
    </tr>
    <%    		
			}
  		}
  	%>
  </table>
</div>
   
<tr  class="alt" ><td align="center" valign="middle"><label>[I:- Input Amount] &nbsp; [C:- Calculated Amount]</label></td></tr>
<tr>
  <td align="center" valign="top">

Add Code Dependency
		<table  border="1">
          <tr align="center" valign="middle">
            <th width="302" align="center" scope="col">Description</th>
            <th width="308" scope="col">Select On which It is Dependent ? </th>
            <th>Select Amount Type</th>
            </tr>
          <tr align="left" valign="middle">
            <td><%=CodeMasterHandler.getCDesc(trncd)%></td>
            <td valign="middle"> 
              <select name="select.addTrncd" id="select.addTrncd">
    		     <option value="select" selected>Select</option>
    			<%
						for (CodeMasterBean temp1:TRNCODE)
						{
				 %>
    							<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
   				<%
						}
				%>
    
    		</select>
            </td>
            <td>
            <select name="amtType" id="amtType">
            	<option value="C" selected="selected">Select</option>
            	<option value="I" >Input Amount</option>
            	<option value="C" >Calculated Amount</option>
            </select>
            </td>
            </tr>
        </table>
		<p>
    <label><br/>
  <input type="submit" name="save" id="save"  value="Save" />
  </label>
  <label>
  <input type="button" name="cancel" value="Cancel" onclick="window.close()"/>
  </label>
  <input type="hidden" value="<%=maxsr+1%>" name="maxsr" />
</p>
<br/>

</td></tr>
<tr><td align="center" valign="middle" bgcolor="#2F747E">&nbsp;</td>
</tr>
</table>

 </form>
</center>
</font>
</div>
<!--  end table-content  -->

<div class="clear"></div>

</div>
<!--  end content-table-inner ............................................END  -->
</div>


<div class="clear">&nbsp;</div>


<!--  end content -->

</div>

<!--  end content-outer........................................................END -->
 



</body>
</html>