<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.util.Locale"%>
<%@page import="com.ibm.icu.text.NumberFormat"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.AutopostBean"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title> CTC  &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/jquery.treeview.css" />

<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/jquery.treeview.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->



<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
.style3 {font-size: 1.5em}
-->
</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<%
	String action = "";
	try
	{
		action = request.getParameter("action");
	}
	catch(Exception e)
	{
		
	}

%>

<%
	String pageName = "CTC.jsp";
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
	
	
	LookupHandler lkh=new LookupHandler();
	ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
	
	
%>
<script type="text/javascript">
var month="";
var xmlhttp;
var url="";
var date;
if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

function getNegtvSalMonths()
{
	url="CTCServlet?action=noctc";
	
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4)
		{
			var response1 = xmlhttp.responseText;
			document.getElementById("nsalary").innerHTML = response1;
			$("#atree").treeview({
				collapsed: false,
				animated: "medium",
				control:"#sidetreecontrol1",
				persist: "location"
			});
			if(response1.length!=0)
			document.getElementById("ctc_div").hidden=false;
			
		}
		
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
	
}



	function calcMonthly(flag)
	{
		document.getElementById("monthly").value = Math.round((document.getElementById("annual").value / 12).toFixed(2));
		calcTotal();
	/* 	if(document.getElementById("basic").value!="")
		{ */
			 if(flag == 0)
			{
				calcValue('basic','basicOn','basicVal','basicValType');
			} 
			
			calcValue('da','daOn','daVal','daValType');
			calcValue('hra','hraOn','hraVal','hraValType');
			calcValue('vda','vdaOn','vdaVal','vdaValType');
			calcValue('other','otherOn','otherVal','otherValType');
			calcValue('edu','eduOn','eduVal','eduValType');
			calcValue('medi','mediOn','mediVal','mediValType');
			calcValue('insu','insuOn','insuVal','insuValType');
			calcValue('conv','convOn','convVal','convValType');
			calcValue('vda','vdaOn','vdaVal','vdaValType');
			calcValue('spAllow','spAllowOn','spAllowVal','spAllowValType');
			calcValue('col','colOn','colVal','colValType');
		/* 	calcValue('pfe','pfeOn','pfeVal','pfeValType');
			calcValue('addLess','addLessOn','addLessVal','addLessValType'); */
		/* }
		else
		{
			document.getElementById("basic").value=0;
		}
		 */
	}
	
	function calcValue(per,based,view,yearly,type)
	{
		var percent = document.getElementById(per).value;
		if(percent!="")
		{
			var code = document.getElementById(based).value;
			var basedAmt = 0;
			if(code==101)
			{
				basedAmt = document.getElementById("basicVal").value;
			}
			else if(code==199)
			{
				basedAmt = document.getElementById("monthly").value;
			}
			var typeVal = document.getElementById(type).value;
			if(typeVal ==0)
			{
				document.getElementById(view).value = Math.round(((basedAmt * percent)/100.00).toFixed(2));
				document.getElementById(yearly).value = Math.round((document.getElementById(view).value * 12.00).toFixed(2));
			}
			else
			{
				document.getElementById(view).value = percent;
				document.getElementById(yearly).value = Math.round((document.getElementById(view).value * 12.00).toFixed(2));
			}
			if(per == "basic")
			{
				calcMonthly(1);
			}
		}
		else
		{
			document.getElementById(per).value = 0;
			document.getElementById(view).value = 0;
			document.getElementById(yearly).value = 0;
		}
		calcTotal();
	}
	
	function calcTotal()
	{
		
		var a = document.getElementById("basicVal").value==""?0:document.getElementById("basicVal").value;
		var b = document.getElementById("daVal").value==""?0:document.getElementById("daVal").value;
		var c = document.getElementById("hraVal").value==""?0:document.getElementById("hraVal").value;
		var d = document.getElementById("eduVal").value==""?0:document.getElementById("eduVal").value;
		var e = document.getElementById("insuVal").value==""?0:document.getElementById("insuVal").value;
		var h = document.getElementById("colVal").value==""?0:document.getElementById("colVal").value;
		
		var j = document.getElementById("vdaVal").value==""?0:document.getElementById("vdaVal").value;
		var k = document.getElementById("spAllowVal").value==""?0:document.getElementById("spAllowVal").value;
		var i = document.getElementById("convVal").value==""?0:document.getElementById("convVal").value;
		var l = document.getElementById("otherVal").value==""?0:document.getElementById("otherVal").value;
		var m = document.getElementById("mediVal").value==""?0:document.getElementById("mediVal").value;
		
		
		
		var total_Ern = parseFloat(a)+parseFloat(b)+parseFloat(c)+parseFloat(d)+parseFloat(e)+parseFloat(h)+parseFloat(i)+parseFloat(j)+parseFloat(k)+parseFloat(l)+parseFloat(m);
		total_Ern = Math.round(total_Ern);
		document.getElementById("total_Ern").value=total_Ern;
		
/* 		var f = document.getElementById("pfeVal").value==""?0:document.getElementById("pfeVal").value;
		var g = document.getElementById("addLessVal").value==""?0:document.getElementById("addLessVal").value; */
		
		/* var total_Ded = parseFloat(f)+parseFloat(g);
		total_Ded = Math.round(total_Ded);
		document.getElementById("total_Ded").value=total_Ded;  */
		
		var total = parseFloat(a)+parseFloat(b)+parseFloat(c)+parseFloat(d)+parseFloat(e)+parseFloat(h)+parseFloat(i)+parseFloat(j)+parseFloat(k)+parseFloat(l)+parseFloat(m);
		total = Math.round(total);
		document.getElementById("total").value=total; 
		
		//Total of yearly amount
		
		var yearlyBasic = document.getElementById("yearlyBasicVal").value==""?0:document.getElementById("yearlyBasicVal").value;
		var yearlyDA = document.getElementById("yearlyDaVal").value==""?0:document.getElementById("yearlyDaVal").value;
		var yearlyHra = document.getElementById("yearlyHraVal").value==""?0:document.getElementById("yearlyHraVal").value;
		var yearlyEdu = document.getElementById("yearlyEduVal").value==""?0:document.getElementById("yearlyEduVal").value;
		var yearlyInsu = document.getElementById("yearlyInsuVal").value==""?0:document.getElementById("yearlyInsuVal").value;
		var yearlyCOL = document.getElementById("yearlyColVal").value==""?0:document.getElementById("yearlyColVal").value;
		var yearlyother = document.getElementById("yearlyotherVal").value==""?0:document.getElementById("yearlyotherVal").value;
		var yearlyvda = document.getElementById("yearlyvdaVal").value==""?0:document.getElementById("yearlyvdaVal").value;
		var yearlymedi = document.getElementById("yearlymediVal").value==""?0:document.getElementById("yearlymediVal").value;
		var yearlySplAllow = document.getElementById("yearlySpAllowVal").value==""?0:document.getElementById("yearlySpAllowVal").value;
		var yearlyConv = document.getElementById("yearlyConvVal").value==""?0:document.getElementById("yearlyConvVal").value;
		
		var yearlyTotalErn = parseFloat(yearlyBasic) + parseFloat(yearlyDA) + parseFloat(yearlyHra) + parseFloat(yearlyEdu) + parseFloat(yearlyInsu) 
							+ parseFloat(yearlyCOL) + parseFloat(yearlyConv) + parseFloat(yearlyvda) + parseFloat(yearlySplAllow) + parseFloat(yearlyother) 
							+ parseFloat(yearlymedi);
		yearlyTotalErn = Math.round(yearlyTotalErn);
		document.getElementById("yearlyTotalErn").value = yearlyTotalErn;
		
	/* 	var yearlyPf = document.getElementById("yearlyPfeVal").value==""?0:document.getElementById("yearlyPfeVal").value;
		var yearlyAddLessAmt = document.getElementById("yearlyAddLessVal").value==""?0:document.getElementById("yearlyAddLessVal").value; */
		
	/* 	var yearlyTotalDed = parseFloat(yearlyPf) + parseFloat(yearlyAddLessAmt);
		yearlyTotalDed = Math.round(yearlyTotalDed);
		document.getElementById("yearlyTotalDed").value = yearlyTotalDed;  */
		
		var yearlyTotal = parseFloat(yearlyBasic) + parseFloat(yearlyDA) + parseFloat(yearlyHra) + parseFloat(yearlyEdu) + parseFloat(yearlyInsu) + parseFloat(yearlyCOL)
							+ parseFloat(yearlyConv) + parseFloat(yearlyvda) + parseFloat(yearlySplAllow)+parseFloat(yearlyother)+ parseFloat(yearlymedi);
		yearlyTotal = Math.round(yearlyTotal);
		document.getElementById("yearlyTotal").value = yearlyTotal; 
		
		//document.getElementById("annual").value = yearlyTotal; 
		//document.getElementById("monthly").value=total; 
		
		
		
	}
	
	function validate() 
	{
		if(document.getElementById("EMPNO").value == "")
		{
			alert("Invalid Employee");
			return false;
		}
		if(document.getElementById("annual").value == "")
		{
			alert("No Annual CTC is entered, Can't create Salary Structure");
			return false;
		}
		if(document.getElementById("date").value==""){
			alert("No Effective Date is entered, Can't create Salary Structure");
			return false;
		}
		
		if(document.getElementById("basic").value == "")
		{
			alert("No BASIC value is entered, Can't create Salary Structure");
			return false;
		}
		
		if(document.getElementById("grade").value == "0")
		{
			alert("Please Select Grade..!!");
			return false;
		}
		
		var monthly_gross = document.getElementById("monthly").value;
		var total = document.getElementById("total").value;
		
		if(monthly_gross != total){
			alert("Salary Structure is not matching");
			return false;
		}
		
		var ans=confirm("Are you sure to Create Employee CTC ?");
		if(!ans)
			{
			return false;
			}
		
		
	}
	
	
	function empCTC()
	{
		var empno=document.getElementById("EMPNO").value;
		
		var res=empno.indexOf(":"); 
		
		if(res>0)
			{
			
		
			window.location.href = "NewCTCServlet?action=checkCTC&EMPNO="+empno;
		
			}
		else
			{
			alert("Please select Employee !");
			document.getElementById("EMPNO").value="";
			document.getElementById("EMPNO").focus();
			
			}
		
	}
	
	function checkFlag() 
	{
		
		var action = document.getElementById("action").value;
		if(action == "saved")
		{
			alert("Salary Structure Created Successfully.\n"+
					"You can verify it through Slab Management,"+
					" On Amount and Salary transaction Menu");
		}
		if(action == "notsaved")
		{
		alert("Please Select Proper Effective Date and then Create Salary Structure again !"); 
		
		}
	}
	
	function print()
	{ 
			var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
			    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
			var content_vlue = document.getElementById("table-content").innerHTML; 
			
			var docprint=window.open("","",disp_setting); 
				docprint.document.open(); 
				docprint.document.write('<html><head><title>Inel Power System</title>'); 
				docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
				docprint.document.write(content_vlue);          
				docprint.document.write('</center></body></html>'); 
				docprint.document.close(); 
				docprint.focus(); 
	}
	
	function getGradeInfo()
	{
		var gd=document.getElementById("grade").value;
		var stage=document.getElementById("stage").value;
		
		if(gd==0)
			{
			alert("Please select grade first!");
			
			}
		else if(stage==0)
			{
			alert("Please select stage first!");
			alert("ani");
			}
		else
			{
			
			var xreq1;
			if(window.XMLHttpRequest)
			{
			xreq1=new XMLHttpRequest();
			}
			else
			{
			xreq1=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xreq1.onreadystatechange=function ()
			{
				
			if( (xreq1.readyState==4) && (xreq1.status==200) )
			{
				
				document.getElementById("span1").innerHTML=xreq1.responseText;
				document.getElementById("basic").value=document.getElementById("bsc1").value;
				document.getElementById("basicValType").value=1;
				document.getElementById("basicVal").value=document.getElementById("bsc1").value;
				var tot1=document.getElementById("total_Ern").value=parseInt(document.getElementById("basicVal").value);
				var tott1=document.getElementById("yearlyTotalErn").value=tot1*12;
				document.getElementById("yearlyBasicVal").value=12 * (document.getElementById("bsc1").value);
				
				document.getElementById("da").value=document.getElementById("d1").value;
				document.getElementById("daValType").value=1;
				document.getElementById("daVal").value=document.getElementById("d1").value;
				var tot2=document.getElementById("total_Ern").value=tot1+parseInt(document.getElementById("daVal").value);
				var tott2=document.getElementById("yearlyTotalErn").value=tot2*12;
				
				document.getElementById("yearlyDaVal").value=12 * (document.getElementById("d1").value);
				
				document.getElementById("vda").value=document.getElementById("v1").value;
				document.getElementById("vdaValType").value=1;
				document.getElementById("vdaVal").value=document.getElementById("v1").value;
				var tot3=document.getElementById("total_Ern").value=tot2+parseInt(document.getElementById("vdaVal").value);
				var tott3=document.getElementById("yearlyTotalErn").value=tot3*12;
				 
				document.getElementById("yearlyvdaVal").value=12 * (document.getElementById("v1").value);
				
				document.getElementById("hra").value=document.getElementById("h1").value;
				document.getElementById("hraValType").value=1;
				document.getElementById("hraVal").value=document.getElementById("h1").value;
				var tot4=document.getElementById("total_Ern").value=tot3+parseInt(document.getElementById("hraVal").value);
				var tott4=document.getElementById("yearlyTotalErn").value=tot4*12;
				 
				document.getElementById("yearlyHraVal").value=12 * (document.getElementById("h1").value);
				
				document.getElementById("edu").value=document.getElementById("e1").value;
				document.getElementById("eduValType").value=1;
				document.getElementById("eduVal").value=document.getElementById("e1").value;
				var tot5=document.getElementById("total_Ern").value=tot4+parseInt(document.getElementById("eduVal").value);
				var tott6=document.getElementById("yearlyTotalErn").value=tot5*12;
				
				document.getElementById("yearlyEduVal").value=12 * (document.getElementById("e1").value);
				
				document.getElementById("medi").value=document.getElementById("m1").value;
				document.getElementById("mediValType").value=1;
				document.getElementById("mediVal").value=document.getElementById("m1").value;
				var e=document.getElementById("mediVal").value=document.getElementById("m1").value;
				var tot6=document.getElementById("total_Ern").value=tot5+parseInt(document.getElementById("mediVal").value);
				var tottt=document.getElementById("yearlyTotalErn").value=tot6*12;
				document.getElementById("yearlymediVal").value=12 * (document.getElementById("m1").value);
				
				
				var tot8=document.getElementById("total_Ern").value=tot6+oa;
				var tottt1=document.getElementById("yearlyTotalErn").value=tot8*12;
				var tot9=document.getElementById("total_Ern").value=tot8+ar;
				var tottt2=document.getElementById("yearlyTotalErn").value=tot9*12;
				var toto=document.getElementById("total_Ern").value=tot9+sa;
				var tottt3=document.getElementById("yearlyTotalErn").value=toto*12;
				var tot10=document.getElementById("total_Ern").value=toto+ara;
				var tottt4=document.getElementById("yearlyTotalErn").value=tot10*12;
				var tot7=document.getElementById("total_Ern").value=tot10+parseInt(document.getElementById("c1").value);
				var tott6=document.getElementById("yearlyTotalErn").value=tot7*12;
				
				document.getElementById("total").value=document.getElementById("total_Ern").value;
				document.getElementById("yearlyTotal").value=document.getElementById("yearlyTotalErn").value;
				
				document.getElementById("monthly").value=document.getElementById("total_Ern").value;
				document.getElementById("annual").value=document.getElementById("yearlyTotalErn").value;
																				
				//document.getElementById("total").value=tot10;
				//document.getElementById("yearlyTotal").value=tott6;
				
				document.getElementById("conv").value=document.getElementById("c1").value;
				document.getElementById("convValType").value=1;
				document.getElementById("conVal").value=document.getElementById("c1").value;
				document.getElementById("yearlyConVal").value=12 * (document.getElementById("c1").value);
				
				//document.getElementById("total_Ern").value=0;
				/* var h=document.getElementById("total_Ern").value=a+b+c+d+e+f+g;
				document.getElementById("yearlyTotalErn").value=12*h;*/
				
				
			
				
			}
			};	
				xreq1.open("get","Gradeinfo.jsp?grade="+gd+"&stage="+stage,true);
				xreq1.send();
				
				}
		
	}
		function getStageInfo()
		{
			
			var gd=document.getElementById("grade").value;
			
			if(gd==0)
				{
				alert("Please select grade !");
				}
			else
				{
				
				var xreq1;
				if(window.XMLHttpRequest)
				{
				xreq1=new XMLHttpRequest();
				}
				else
				{
				xreq1=new ActiveXObject("Microsoft.XMLHTTP");
				}
				xreq1.onreadystatechange=function ()
				{
					
				if( (xreq1.readyState==4) && (xreq1.status==200) )
				{
					
				document.getElementById("stage").innerHTML=xreq1.responseText;
				}
				};
				xreq1.open("get","Stagelist.jsp?grade="+gd,true);
					xreq1.send();
				
				
				 
				}
				}
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

	
</script>

</head>
<body style="overflow: hidden;" onLoad="checkFlag(),getNegtvSalMonths()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%;">
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Cost To Company (CTC)</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" style="overflow-y:scroll; max-height:70%; ">
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
		<div id="content-table-inner" >
		
			<!--  start table-content  -->
			<div id="table-content" style="overflow-y:auto; max-height:70%; ">
			<table>
			<tr>
			<td>
			<table width="850" border="1" align="center">
			  <tr><td align="center"><br/>
			  <form action="CTCServlet?action=create" method="post" onKeyPress="return event.keyCode != 13;" onSubmit="return validate()">
			  <table style="width: 95%; line-height: 30px" height="66" border="1">
			   <tr bgcolor="#eaeaea">
			     <td height="33" colspan="3" align="center">Type Employee Name or Number 
			       <input name="EMPNO" type="text" id="EMPNO" onClick="showHide()" size="41" class="form-control ac_input">
			       <input type="button" value="Check" style="margin-left: 10px;" onclick="empCTC()" class="myButton"/>
			       </td>
			   </tr>
			   <tr>
			     <td width="261">&nbsp;Annual Total  
			       <input name="annual" type="text" class="form-control ac_input" id="annual" onkeypress="return inputLimiter(event,'Numbers')"  onKeyUp="calcMonthly(0)" dir="rtl" tabindex="1" autocomplete="off"> 
			     
			    <!--  <input name="annual" type="text" id="annual"   onKeyUp="calcMonthly(0)" dir="rtl" tabindex="1" autocomplete="off"> -->
			       
			     
			       Rs.</td>
			     <td width="272">&nbsp;Monthly Gross 
			       <input name="monthly" type="text" id="monthly" readonly="readonly" style="background-color:silver;" dir="rtl">
			       Rs.</td>
			       <td>
			       Effective Date : 
			       <input name="date" size="20"
							id="date" type="text" class="form-control ac_input"  readonly="readonly"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
			       </td>
			   </tr>
			   <tr height="25">
			      <td colspan="3">
			      Select Grade :
			      <select name='grade' id='grade' onchange="getStageInfo()" title='Select the Grade of Employee!' style="margin-right: 10px;" >
			      <option value='0'>Select</option>
			      <%
			      for(Lookup lkp_bean:lkp_list)
			      {
			      %>
			      <option value='<%=lkp_bean.getLKP_SRNO()%>'><%=lkp_bean.getLKP_DESC()%></option>
			      <%	  
			      }
			      %>
			        
			      
			      </select> 	
			      
			      
			      
			      Select Stage :
			      <select name='stage' id='stage' onchange="getGradeInfo()" style='width: 80px;margin-right: 20px;' title='Select the stage of Employee!' >
			      <option value='0'>Select</option>
			      </select>
			      
			      <span id='span1'>
			      </span>
			      
			      &nbsp;PF &nbsp;
			       				<input name="pf" type="checkbox" id="pf" value="true" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			     				PT &nbsp;
			       				<input name="pt" type="checkbox" id="pt" value="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   					ESIC &nbsp;
			      				<input name="esic" type="checkbox" id="esic" value="true">
			      </td>
			   </tr>
			   </table>
			   
			   <p>&nbsp;</p>
			   <table width="820" border="1" style="line-height: 21px">
               	<tr align="center" bgcolor="#eaeaea">
                  <td width="50" align="center"><b>Sr. </b></td>
                  <td width="132"><b>Type</b></td>
                  <td width="80"><b>Value Type</b> </td>
                  <td width="100"><b>Value</b></td>
                  <td width="70"><b>Based On</b></td>
                  <td width="180"><b>Monthly Amount</b></td>
                  <td width="180"><b>Yearly Amount</b></td> 
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">1</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Basic </td>
                  <td align="center"><select name="basicValType" id="basicValType"  onChange="calcValue('basic','basicOn','basicVal','yearlyBasicVal','basicValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                    </select></td>
                  <td align="center">&nbsp;<input name="basic" type="text" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  id="basic" size="8" autocomplete="off" onKeyUp="calcValue('basic','basicOn','basicVal','yearlyBasicVal','basicValType')" dir="rtl" tabindex="2" autocomplete="false"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="basicOn" id="basicOn">
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="basicVal" type="text" size="15" id="basicVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyBasicVal" type="text" id="yearlyBasicVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">2</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;D.A </td>
                  <td align="center"><select name="daValType" id="daValType" onChange="calcValue('da','daOn','daVal','yearlyDaVal','daValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="da" onkeypress="return inputLimiter(event,'Numbers')"  type="text" class="form-control ac_input" id="da" size="8" autocomplete="off" onKeyUp="calcValue('da','daOn','daVal','yearlyDaVal','daValType')" dir="rtl" tabindex="3"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="daOn" id="daOn" onChange="calcValue('da','daOn','daVal','yearlyDaVal','daValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="daVal" type="text" id="daVal" size="15" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyDaVal" type="text" id="yearlyDaVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                 
                <tr bgcolor="#FFFFFF">
                  <td align="center">3</td>
                  <td align="left">&nbsp;V.D.A</td>
                  <td align="center"><select name="vdaValType" id="vdaValType" onChange="calcValue('vda','vdaOn','vdaVal','yearlyvdaVal','vdaValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="vda" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="vda" size="8" autocomplete="off" onKeyUp="calcValue('vda','vdaOn','vdaVal','yearlyvdaVal','vdaValType')" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="vdaOn" id="vdaOn" onChange="calcValue('vda','vdaOn','vdaVal','yearvdausVal','vdaValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td>&nbsp;<input name="vdaVal" type="text" id="vdaVal" size="15" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyvdaVal" type="text" id="yearlyvdaVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">4</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;H.R.A. </td>
                  <td align="center"><select name="hraValType" id="hraValType" onChange="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="hra" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="hra" size="8" autocomplete="off" onKeyUp="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')" dir="rtl" tabindex="4"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="hraOn" id="hraOn" onChange="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="hraVal" type="text" size="15" id="hraVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyHraVal" type="text" id="yearlyHraVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">5</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Other Allow.  </td>
                  <td align="center"><select name="otherValType" id="otherValType" onChange="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="other"  class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="other" size="8" autocomplete="off" onKeyUp="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')" dir="rtl" tabindex="5"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="otherOn" id="otherOn" onChange="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="otherVal" type="text" size="15" id="otherVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyotherVal" type="text" id="yearlyotherVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">6</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp; Education </td>
                  <td align="center"><select name="eduValType"  id="eduValType" onChange="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="edu" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="edu" size="8" autocomplete="off" onKeyUp="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')" dir="rtl" tabindex="5"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="eduOn" id="eduOn" onChange="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="eduVal" type="text" size="15" id="eduVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyEduVal" type="text" id="yearlyEduVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">7</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Medical Allow. </td>
                  <td align="center"><select name="mediValType" id="mediValType" onChange="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="medi" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="medi" size="8" autocomplete="off" onKeyUp="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')" dir="rtl" tabindex="5"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="mediOn" id="mediOn" onChange="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="mediVal" type="text" size="15" id="mediVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlymediVal" type="text" id="yearlymediVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">8</td>
                  <td align="left">&nbsp;Conv</td>
                  <td align="center"><select name="convValType" id="convValType" onChange="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="conv"  class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="conv" size="8" autocomplete="off" onKeyUp="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="convOn" id="convOn" onChange="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td>&nbsp;<input name="convVal" type="text" id="convVal" size="15" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyConvVal" type="text" id="yearlyConvVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">9</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;AREABDV </td>
                  <td align="center"><select name="insuValType" id="insuValType" onChange="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="insu" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="insu" size="8" autocomplete="off" onKeyUp="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')" dir="rtl" tabindex="6"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="insuOn" id="insuOn" onChange="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')">
                    <option value="101" >Basic</option>
                    <option value="199"selected>Gross</option>
                  </select></td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="insuVal" type="text" size="15" id="insuVal" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyInsuVal" type="text" id="yearlyInsuVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">10</td>
                  <td align="left">&nbsp;Special Allowance</td>
                  <td align="center"><select name="spAllowValType" id="spAllowValType" onChange="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="spAllow" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="spAllow" size="8" autocomplete="off" onKeyUp="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="spAllowOn" id="spAllowOn" onChange="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td>&nbsp;<input name="spAllowVal" type="text" id="spAllowVal" size="15" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlySpAllowVal" type="text" id="yearlySpAllowVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">11</td>
                  <td align="left">&nbsp;AREAALL</td>
                  <td align="center"><select name="colValType" id="colValType" onChange="calcValue('col','colOn','colVal','yearlyColVal','colValType')">
                    <option value="0" >Percentage</option>
                    <option value="1" selected>Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="col" class="form-control ac_input" onkeypress="return inputLimiter(event,'Numbers')"  type="text" id="col" size="8" autocomplete="off" onKeyUp="calcValue('col','colOn','colVal','yearlyColVal','colValType')" dir="rtl" tabindex="10"></td>
                  <td align="center"><select name="colOn" id="colOn" onChange="calcValue('col','colOn','colVal','yearlyColVal','colValType')">
                    <option value="101" selected>Basic</option>
                    <option value="199">Gross</option>
                  </select></td>
                  <td>&nbsp;<input name="colVal" type="text" id="colVal" size="15" readonly="readonly" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyColVal" type="text" id="yearlyColVal" readonly="readonly" size="13" dir="rtl"> Rs.</td>
                </tr>
                
           
                 <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total_Ern" type="text" readonly="readonly"   size="15" id="total_Ern" dir="rtl" style="background-color:#7fe5ff">/- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotalErn" type="text" id="yearlyTotalErn" readonly="readonly" size="13" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>
                
                <tr bgcolor="#CCCCCC">
                  <td align="center">&nbsp;</td>
                  <td align="left">&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total" type="text" readonly="readonly" size="15" id="total" dir="rtl" style="background-color:#7fe5ff"> /- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotal" type="text" id="yearlyTotal" readonly="readonly" size="13" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>	
              </table>
               <a href="javascript:print()"> <input type="button" name="Print" value="Print" class="myButton"></a>
			   <input type="submit" name="Submit" value="Create Salary Structure" class="myButton">
			   <input type="reset" name="reset" value="Clear" class="myButton">
			   
			   <br/></form></td>
			  </tr></table>
			  </td>
			  <td>&nbsp;&nbsp;&nbsp;</td>
			  <td valign="top">
			  
			  <div id="ctc_div" style=" border-radius:15px; width: 10; height: 80; " >
		  	<center>
				 <table id="customers"  align="left" >
					<!-- <tr > <td>CTC UNAVAILABLE</td></tr> -->
					<tr >
						<td>
						<div id="sidetreecontrol1"><a href="?#"> Collapse  All</a> | <a href="?#">Expand All</a></div>
						<div style="overflow-y: auto; height:  482px;">
							<table style=" height: 475px;">
								<tr class="alt" align="left">
									<td width="220"  valign="top"  >
										<div id ="nsalary" ></div> 
									</td>
					</tr></table></div></td></tr>
					
				</table> 
				
				
			
			</center>	
		  </div>
			  </td>
			  </tr>
			  </table>
			</div>
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
    
<input type="hidden" id="action" name="action" value="<%=action%>">
</body>
</html>