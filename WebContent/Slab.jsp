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
<%@page errorPage="error.jsp" isErrorPage="true"%>
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
			session.setAttribute("emp", ecat);
			StringTokenizer st = new StringTokenizer(ecat,":");
		     
			while(st.hasMoreTokens())
		    {
		    	ecat=st.nextToken();
		    }
			trncd=Integer.parseInt(request.getParameter("trncd"));
			empcat=Integer.parseInt(ecat);
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
		
		var ecat=document.getElementById("empcat").value;
		var trncd=document.getElementById("select.trncd").value;
		//window.showModalDialog("Lookup.jsp","Lookup Entry","dialogWidth=300px;dialogHeight=300px;center=yes;addressBar=no;");
		if(document.getElementById("empcat").value=="" ||document.getElementById("select.trncd").value=="select")
			{
			alert("please Employee Type and Transaction code"); 
			return false;
			
			}
		window.location.href="Slab.jsp?action=getList&ecat="+ecat+"&trncd="+trncd;
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
<script type="text/javascript">
function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890';}
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
	
function inputLimiterForFloatType(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890.';}
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
	
</script>


</head>
<body  style="overflow: hidden;">
<div><%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%></div>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Slab Management</h1>
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
<center>
<h2>Slab Management</h2>
<form action="SlabServlet?action=addnew" method="post" name="add" onSubmit="return validate()">
<input type="hidden" value="Slab.jsp" id="pageName" name="pageName">
<table width="800" id="customers" border="1">
<tr bgcolor="#1F5FA7" ><td width="780" align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="middle">
<table width="706" bgcolor="#606060">
<tr class="alt">
<td width="271" align="center" valign="middle" bgcolor="#CCCCCC">Employee Category</td>
<td width="308" align="center" valign="middle" bgcolor="#CCCCCC">Transaction Codes Description</td>
<td width="111" rowspan="2" align="center" valign="middle" bgcolor="#FFFFFF">  <input type="button"  class="myButton"  name="go" value="Show Slabs" onClick=" return getList()"></td>
</tr>
<tr class="alt" height="20">
  <td  align="left" valign="top" bgcolor="#FFFFFF">
  <input type="text" name="empcat" id="empcat" size="35" onClick="rset()" value="<%=session.getAttribute("emp")==null?"EMPLOYEE TYPE:0":session.getAttribute("emp") %>" title="Select Employee" onClick="this.select();">
  
</td>
  <td align="left" valign="top" bgcolor="#FFFFFF"> 
    <select name="select.trncd" id="select.trncd">
    <option value="select" selected>Select</option>
    			<%
						for (CodeMasterBean temp1:TRNCODE)
						{
							
							if (trncd==temp1.getTRNCD())
							{
				 %>
    							<option value="<%=temp1.getTRNCD()%>" selected><%=temp1.getDISC()%></option>
   				 <%
							} 
							else
							{
				%>
    							<option value="<%=temp1.getTRNCD()%>"><%=temp1.getDISC()%></option>
    			<%
							}

						}
				%>
    </select>   </td>
	</tr>
</table>
<br/>   
       <table border="1" id="customers" style="margin-right: 8px;">
        <tr bgcolor="#999999">
          <th width="88">Effective Date</th>
          <th width="81">From Amount</th>
          <th width="69">To Amount</th>
          <th width="76">Percentage</th>
          <th width="67">Minimum Amount</th>
          <th >Maximum Amount</th>
          <th >Fix Amount</th>
          <th width="84">End Slab</th>
		</tr>
		</table>
		<div style="height: 170px; overflow-y: scroll;" id="div1">
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
		  <td align="center" valign="middle" width="70"><%= test1.getMINAMT()%></td>
		  <td align="center" valign="middle" width="111"><%= test1.getMAXAMT()%></td>
		  <td align="center" valign="middle" width="73"><%= test1.getFIXAMT()%></td>
		  <td>
		  		<%
		  			if(test1.getEFFDATE().contains("2099"))
		  			{
		  		%>
		  			<input name="button" type="button" class="myButton"  onClick="endSlab('<%=sub%>')"  style="margin-top: 0px;margin-bottom: 0px;"
		  			value="End Slab" />
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
   
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
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
          	<label><input type="text" name="frmAmt" id="frmAmt"  onkeypress="return inputLimiter(event,'Numbers')"></label>
          </td>
          <td align="right" valign="middle">To Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="toAmt" id="toAmt"  onkeypress="return inputLimiter(event,'Numbers')"></label>
          </td>
        </tr>
       <tr class="alt">
          <td align="right" valign="middle">Minimum Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="minAmt" id="minAmt"  onkeypress="return inputLimiter(event,'Numbers')"></label>
          </td>
          <td align="right" valign="middle">Maximum Amount</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="maxAmt" id="maxAmt"  onkeypress="return inputLimiter(event,'Numbers')"></label>
          </td>
        </tr>
        <tr class="alt">
        <td align="right" valign="middle">Percentage</td>
          <td align="left" valign="middle">
          	<label><input type="text" name="percent" id="percent"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"></label>
          </td>
          <td align="right" valign="middle">Fix Amount </td>
          <td align="left" valign="middle">
          	<label><input type="text" name="fixAmt" id="fixAmt"  onkeypress="return inputLimiterForFloatType(event,'Numbers')"></label>
          </td>
        </tr>
      </table>
	  <p>
    	<label>
  			<input type="submit" class="myButton"  name="save" id="save"  value="Save" />
  	    </label>
  		<label>
  			<input name="clear" class="myButton"  type="reset" id="clear" value="Clear" />
  		</label>
  			<input type="hidden" value="<%=maxsr+1%>" name="maxsr" />
	</p>
	
	</td>
  </tr>
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td></tr>
</table>

 </form>
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