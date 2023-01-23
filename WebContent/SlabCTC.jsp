<%@page import="payroll.DAO.SlabHandler"%>
<%@page import="payroll.Model.SlabBean"%>
<%@page import="payroll.Model.OnAmtBean"%>
<%@page import="payroll.DAO.OnAmtHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Slab Management</title>
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
<script src="js/filter.js"></script>
<%
	String pageName = "Slab.jsp";
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
	LookupHandler lkph = new LookupHandler();
	CodeMasterHandler CMH = new CodeMasterHandler();
	SlabHandler SBH = new SlabHandler();
	
	ArrayList<Lookup> ECAT = new ArrayList<Lookup>();
	ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();
	ArrayList<SlabBean> SBLIST=new ArrayList<SlabBean>();
	
	ECAT=lkph.getSubLKP_DESC("ET");	// ET is Code for Employee Type 
	TRNCODE=CMH.getAllCDMASTList();
	
	int empcat=0;
	int trncd=0;
	String ecat=new String();
	try
	{
		String action =request.getParameter("action");
		if(action.equalsIgnoreCase("getList"))
		{
			ecat=request.getParameter("ecat");
			String[] employ = ecat.split(":");
			trncd=Integer.parseInt(request.getParameter("trncd"));
			empcat=Integer.parseInt(employ[1]);
			SBLIST = SBH.getSlabs(empcat,trncd);
		}
		
	}
	catch(Exception e)
	{
		System.out.println("First Time Loading");
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

	function getList()
	{
		debugger;
		var ecat=document.getElementById("empcat").value;
		var trncd=document.getElementById("select.trncd").value;
		//window.showModalDialog("Lookup.jsp","Lookup Entry","dialogWidth=300px;dialogHeight=300px;center=yes;addressBar=no;");
		if(document.getElementById("empcat").value=="" ||document.getElementById("select.trncd").value=="select")
			{
			alert("please Employee Type and Transaction code"); 
			return false;
			
			}
		window.location.href="SlabCTC.jsp?action=getList&ecat="+ecat+"&trncd="+trncd;
		return true;
	}

	function endSlab(key)
	{
		var flag=confirm("Do you really want to End this Slab?");
		if(flag)
		{
			//alert("Deleted "+key);
			url="SlabServlet?action=endSlab&key="+key;
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
        	if(response == "true")
        	{
				alert("Slab Ended");
				getList();
            }
        	else
        	{
        		alert("Error occured while Ending Slab");
            }        	
    	}
	}
	
	
	function validate()
	{
	 if(document.add.effDate.value=="dd-mmm-yyyy") 
	 {
	 alert("Please Select Effective Date");
	 document.add.effDate.focus();
	 return false;
	 }
	 if(document.add.frmAmt.value=="") 
		 {
		 alert("Please Enter From Amount");
		 document.add.frmAmt.focus();
		 return false;
		 }
	 
	 if(isNaN(document.add.frmAmt.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.frmAmt.focus();
	     return (false);
	   }
	 if(document.add.toAmt.value=="") 
	 {
	 alert("Please Enter To Amount");
	 document.add.toAmt.focus();
	 return false;
	 }
	 if(isNaN(document.add.toAmt.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.toAmt.focus();
	     return (false);
	   }
	 if(document.add.percent.value=="") 
	 {
	 alert("Please Enter Percentage");
	 document.add.percent.focus();
	 return false;
	 }
	 
	 if(isNaN(document.add.percent.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.percent.focus();
	     return (false);
	   }
	 if(document.add.minAmt.value=="") 
	 {
	 alert("Please Enter Minimum Amount");
	 document.add.minAmt.focus();
	 return false;
	 }
	 if(isNaN(document.add.minAmt.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.minAmt.focus();
	     return (false);
	   }
	 
    if(document.add.maxAmt.value=="") 
      {
          alert("Please Enter Maximum Amount");
          document.add.maxAmt.focus();
          return false;
       }
    if(isNaN(document.add.maxAmt.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.maxAmt.focus();
	     return (false);
	   }
    if(document.add.fixAmt.value=="") 
    {
        alert("Please Enter Fix Amount");
        document.add.fixAmt.focus();
        return false;
     } 
    if(isNaN(document.add.fixAmt.value))
	   {
	     alert("Invalid data format.\n\nOnly numbers are allowed.");
	     document.add.fixAmt.focus();
	     return (false);
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
</style>
<script>
	jQuery(function() {
          $("#empcat").autocomplete("lookuplist.jsp");
	});
</script>
</head>
<body  style="overflow: hidden;">

		<!--  start nav-outer-repeat................................................................................................. START -->
		
		<!-- start content-outer ........................................................................................................................START -->
		
		<!-- start content -->
		<div id="div1">
		<div id="content-table-inner"><!--  start table-content  -->
		<div id="table-content">
		
		<font face="Georgia" size="2">
		<center>
		<h3>Slab Management</h3>
		<form action="SlabServlet?action=addnew" method="post" name="add" onSubmit="return validate()">
		<input type="hidden" value="SlabCTC.jsp" id="pageName" name="pageName">
		<table width="800" id="customers" border="1">
		<tr bgcolor="#2F747E" ><td width="780" align="center" valign="middle">&nbsp;</td></tr>
		<tr><td align="center" valign="middle">
		<table width="706" bgcolor="#606060">
		<tr class="alt">
		<td width="271" align="center" valign="middle" bgcolor="#CCCCCC">Employee Name : Number</td>
		<td width="308" align="center" valign="middle" bgcolor="#CCCCCC">Transaction Code</td>
		
		</tr>
		<tr class="alt" height="20">
		  <td  align="center" valign="top" bgcolor="#FFFFFF">
		  <%=ecat%><input type="hidden" name="empcat" id="empcat" size="35" onClick="rset()" value="<%=ecat%>" title="Select Employee ">
		  
		</td>
		  	<td align="left" valign="top" bgcolor="#FFFFFF"> 
		    <%=CodeMasterHandler.getCDesc(trncd) %><input type="hidden" name="select.trncd" id="select.trncd" value="<%=trncd %>">
		   </td>
		</tr>
		</table>
		<br/>   
       <table border="1" id="customers">
        <tr bgcolor="#999999">
          <th width="88">Effective Date</th>
          <th width="81">From Amount</th>
          <th width="69">To Amount</th>
          <th width="76">Percentage</th>
          <th width="87">Minimum Amount</th>
          <th >Maximum Amount</th>
          <th >Fix Amount</th>
          <th width="84">End Slab</th>
		</tr>
		</table>
		<div style="height: 200px; overflow-y: scroll;" id="div1">
		<table border="1" id="customers"  width="auto">
        <%String desc="";
     	int maxsr=0;
  		if (SBLIST.size() > 0)
  		{
			for (SlabBean test1 :SBLIST )
			{
  				String sub=test1.getEMP_CAT()+"-"+test1.getTRNCD()+"-"+test1.getSRNO();
  				desc=CodeMasterHandler.getCDesc(test1.getTRNCD());
  				maxsr=test1.getSRNO();
  	%>
        <tr class="row" bgcolor="#FFFFFF" >
          <td align="center" valign="middle" width="90"><%= test1.getEFFDATE()%></td>
          <td align="center" valign="middle" width="85"><%= test1.getFRMAMT()%></td>
          <td align="center" valign="middle" width="70"><%= test1.getTOAMT()%></td>
          <td align="center" valign="middle" width="80"><%= test1.getPER()%></td>
		  <td align="center" valign="middle" width="90"><%= test1.getMINAMT()%></td>
		  <td align="center" valign="middle" width="111"><%= test1.getMAXAMT()%></td>
		  <td align="center" valign="middle" width="73"><%= test1.getFIXAMT()%></td>
		  <td>
		  		<%
		  			if(test1.getEFFDATE().contains("2099"))
		  			{
		  		%>
		  			<input name="button" type="button" onClick="endSlab('<%=sub%>')" value="End Slab" />
		  		<%
		  			}
		  			else
		  			{
		  		%>		
		  			Ended	
		  		<%
		  			}
		  		%>
		   </td>
        </tr>
    <%
			}
  		}
    
    %>    
  	
  	 </table>
    </div>
   
<tr bgcolor="#2F747E" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr>
  <td align="center" valign="top">

	Add New Slab <label>(Put 0 if not applicable)</label>
	  <table width="314" border="1" id="customers">
        <tr class="alt">
          <td width="128" align="right" valign="middle">Effective Date </td>
          <td width="170" align="left" valign="middle">
          		<label> <input name="effDate" id="effDate" type="text" value="31-DEC-2099" readonly="readonly" /></label>
          </td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr class="alt">
          <td align="right" valign="middle">From Amount </td>
          <td align="left" valign="middle">
          	<label><input type="text" name="frmAmt" id="frmAmt"></label>
          </td>
          <td align="right" valign="middle">To Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="toAmt" id="toAmt"></label>
          </td>
        </tr>
       <tr class="alt">
          <td align="right" valign="middle">Minimum Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="minAmt" id="minAmt"></label>
          </td>
          <td align="right" valign="middle">Maximum Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="maxAmt" id="maxAmt"></label>
          </td>
        </tr>
        <tr class="alt">
        <td align="right" valign="middle">Percentage</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="percent" id="percent"></label>
          </td>
          <td align="right" valign="middle">Fix Amount </td>
          <td align="left" valign="middle">
          	<label><input type="text" name="fixAmt" id="fixAmt"></label>
          </td>
        </tr>
      </table>
	  <p>
    	<label>
  			<input type="submit" name="save" id="save"  value="Save" />
  	    </label>
  		<label>
  			<input name="clear" type="reset" id="clear" value="Cancel" onclick="window.close()" />
  		</label>
  			<input type="hidden" value="<%=maxsr+1%>" name="maxsr" />
	</p>
	
	</td>
  </tr>
<tr><td align="center" valign="middle" bgcolor="#2F747E">&nbsp;</td></tr>
</table>

 </form>
</center>
</font>

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