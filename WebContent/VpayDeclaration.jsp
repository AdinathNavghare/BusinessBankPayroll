<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.Model.ItStandardBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.Model.VpayBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.VpayHandler"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Variable Payment Declaration &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->

</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
 
<%	String pageName = "VpayDeclaration.jsp";
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
	String emp="";
	int empno =-1;
	try
	{
		emp=request.getParameter("EMPNO");
		empno = Integer.parseInt(request.getParameter("EMPNO").split(":")[1]); 
	}	
	catch(Exception e)
	{
		
	}
	VpayHandler VH = new VpayHandler();
	ArrayList<VpayBean> VPList = new ArrayList<VpayBean>();
	if(empno !=-1)
	{
		VPList = VH.getVpay(empno);
	}
	
	TranHandler TH = new TranHandler();
	int[] trans = TH.getITSList();
	ArrayList<ItStandardBean> ITList = TH.getITStandardList();
	HashSet<Integer> s1 = new HashSet<Integer>();
	
	for(int i= 0; i<trans.length;i++)
	{
		s1.add(trans[i]);
	}
	for(VpayBean V :VPList)
	{
		if(s1.contains(V.getTRNCD()))
		{
			s1.remove(V.getTRNCD());
		}
		else
		{
			s1.add(V.getTRNCD());
		}
	}
	trans = new int[s1.size()];
	int ii=0;
	for(int x :s1)
	{
		trans[ii++] = x;
	} 
	Date d = new Date();
	String date = new SimpleDateFormat("dd-MMM-yyyy").format(d);
%>

<script type="text/javascript">
	var xmlhttp;
	var url ="";
	if(window.XMLHttpRequest)
	{
		xmlhttp = new XMLHttpRequest;
	}
	else
	{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function saveRecord() 
	{
		var empno = document.getElementById("EMPNO").value;
		var trncd = document.getElementById("trncd").value;
		var date = document.getElementById("date").value;
		var amt = document.getElementById("amt").value;
		url="VpayServlet?action=add&empno="+empno+"&trncd="+trncd+"&date="+date+"&amt="+amt;
		xmlhttp.onreadystatechange=callback;
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
	
	function callback()
	{
		if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	if(response =="true")
        	{
        		alert("Record Inserted Successfully");
        		document.getElementById("form1").submit();
        	}
        	else if(response == "false")
        	{
        		alert("Error in Record Insertion");
        	}
        	else if(response == "max")
        	{
        		alert("Value Entered is Greater Than Maximum Limit");
        	}
		}
	}
	
	var upSrno=0;
	var trncd = 0;
	function callUpdate(srno,old,desc,uptran)
	{
		upSrno = parseInt(srno);
		trncd = uptran;
		document.getElementById("process").hidden=false;
		document.getElementById("oldVal").innerHTML=old;
		document.getElementById("cdTitle").innerHTML=desc;
	}
	
	function hideDiv()
	{
		document.getElementById("process").hidden=true;
		document.getElementById("newVal").value="";
	}
	
	function updateVal() 
	{
		var amt = document.getElementById("newVal").value;
		url="VpayServlet?action=update&srno="+upSrno+"&amt="+amt+"&trncd="+trncd;
		xmlhttp.onreadystatechange=callbackUP;
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
	function callbackUP()
	{
		if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	if(response =="true")
        	{
        		alert("Record Updated Successfully");
        		document.getElementById("form1").submit();
        		hideDiv();
        	}
        	else if(response == "false")
        	{
        		alert("Error in Record Updation");
        		hideDiv();
        	}
        	else if(response == "max")
        	{
        		alert("Value Entered is Greater Than Maximum Limit");
        	}
        	
		}
	}
	
</script>

</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Variable Payment Declaration </h1>
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
		<td align="center">
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			  <table width="1187" border="1">
			    <tr><td colspan="2" align="center">
			  <p>&nbsp;</p>
			  <table width="629" border="1">
			  	<tr>
					<td><form name="form1" id="form1" method="post" action="VpayDeclaration.jsp">
						&nbsp;Type Employee Name or Number
                     	 <input name="EMPNO" type="text" value="<%=(emp==null?"":emp)%>" id="EMPNO" onClick="showHide()" size="50">&nbsp;&nbsp;
                      	<input name="Search" type="submit" id="Search" value="Search">
                      </form>                      </td>
			  	</tr>
			  </table>
			  <br/>
			  </td>
		        </tr>
			    <tr>
			      <td width="613" align="center" valign="top"><p>&nbsp;</p>
			        <table width="808" border="1" id="customers">
                    <tr>
                      <th align="center" scope="col">Sr.No.</th>
                      <th align="left" scope="col">Transaction Code </th>
                      <th align="left" scope="col">Declaration Date </th>
                      <th align="left" scope="col">Amount Declared </th>
                      <th scope="col">Amount Claimed </th>
                    </tr>
                    <%
                	int i=1;
                	for(VpayBean VP:VPList)
                	{
                %>
                    <tr>
                      <td align="center">&nbsp;<%=i++ %></td>
                      <td align="left">&nbsp;<%=CodeMasterHandler.getCDesc(VP.getTRNCD())%></td>
                      <td align="center">&nbsp;<%=VP.getDECL_DATE() %></td>
                      <td align="right">&nbsp;<%=VP.getAMT_DECLARE() %>
                        <input name="Update" type="button" id="Update" value="Update" onClick="callUpdate('<%=VP.getSRNO()%>','<%=VP.getAMT_DECLARE()%>','<%=CodeMasterHandler.getCDesc(VP.getTRNCD())%>','<%=VP.getTRNCD()%>')"></td><td align="right">&nbsp;<%=VP.getAMT_CLAIMED() %></td>
                    </tr>
                <%
                	}
                %>
                    <tr align="center">
                      <td colspan="5" bgcolor="#CCCCCC">Add New Variable Payment Declaration Here </td>
                    </tr>
                    <tr>
                      <td align="center"><%=i%></td>
                      <td width="250" align="center"><select name="trncd" id="trncd">
                          <option value="-1">Select</option>
                          <%
                    	for(int j=0; j<trans.length;j++)
                    	{	
                    %>
                          <option value="<%=trans[j]%>"><%=CodeMasterHandler.getCDesc(trans[j]) %></option>
                    <%
                    	}
                    %>
                        </select></td>
                      <td align="center"><input name="date" type="text" id="date"
	                    	 onFocus="if(value=='dd-mmm-yyyy') {value=''}" onBlur="if(value=='') {value='dd-mmm-yyyy';}" value="<%=date %>" size="12" maxlength="12" readonly="readonly" >
	                    	 &nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;"
	                    	 onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
                      <td align="center"><input name="amt" type="text" id="amt" dir="rtl"></td>
                      <td><input name="Save" type="button" id="Save" value="Save" onClick="saveRecord()"></td>
                    </tr>
                  </table>
		          <p>&nbsp;</p></td>
		          <td width="352" align="center" valign="top"><p>Standard Values </p>
		            <table width="366" border="1" id="customers">
                    <tr>
                      <th align="center" scope="col">Sr.No.</th>
                      <th scope="col">Transaction Code </th>
                      <th scope="col">Amount </th>
                    </tr>
                    <%
                    	int k = 1;
                    	for(ItStandardBean IB : ITList)
                    	{
                    %>
                    <tr>
                      <td align="center">&nbsp;<%=k++ %></td>
                      <td>&nbsp;<%=CodeMasterHandler.getCDesc(IB.getTRCD()) %></td>
                      <td align="right">&nbsp;<%=IB.getAMOUNT() %></td>
                    </tr>
	                <%
                    	}
	                %>
                  </table><p>&nbsp;</p></td>
			    </tr>
			  </table>
		</div>
		
			<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.9;" hidden=true; >
				<div align="center" style="margin-top: 15%;padding-top:50px;padding-bottom:50px; background-color:#CCCCCC; width: 300px;">
					<table border="1">
					  <tr>
					    <td colspan="2" height="25">&nbsp;<span id="cdTitle"></span>&nbsp;</td>
					  </tr>
					  <tr>
					    <td height="25">&nbsp;Current Value&nbsp;</td>
					    <td align="right"><span id="oldVal"></span></td>
					  </tr>
					  <tr>
					    <td height="30">&nbsp;New Value&nbsp;</td>
					    <td><input type="text" name="newVal" id="newVal" dir="rtl"></td>
					  </tr>
					  <tr>
					    <td height="32" colspan="2" align="center">
				        <input name="Cancel" type="button" id="Cancel" value="Cancel" onClick="hideDiv()">
				        <input name="update" type="button" id="update" value="Update" onClick="updateVal()"></td>
					  </tr>
					</table>
				</div>
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