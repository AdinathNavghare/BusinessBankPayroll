<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CTCHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="java.util.Locale"%>
<%@page import="com.ibm.icu.text.NumberFormat"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.AutopostBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>CTC  &copy; DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>


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
	
	
	$(document).ready(function () {
	    $('#edit').click(function () {
	    	
	    	if($('input:text').attr("disabled"))
	    		{
	    	$('input:text').attr("disabled",false);
	    	document.getElementById("stage").disabled = false  ;
			 document.getElementById("grade").disabled = false ;
			 document.getElementById("pf").disabled = false ;
			 document.getElementById("pt").disabled = false ;
			 document.getElementById("esic").disabled = false ;
			 document.getElementById("createSSBT").disabled = false ;
			 document.getElementById("promodemo").disabled = false  ;
			 document.getElementById("orderno").disabled = false  ;
	    	$('#edit').attr('value','Disable');
	    		}
	    	else
	    		{
	    		$('input:text').attr("disabled",true);
	    		document.getElementById("stage").disabled = true  ;
	   		 document.getElementById("grade").disabled = true ;
	   		 document.getElementById("pf").disabled = true ;
	   		 document.getElementById("pt").disabled = true ;
	   		 document.getElementById("esic").disabled = true ;
	   		 document.getElementById("createSSBT").disabled = true ;
	   		 document.getElementById("promodemo").disabled = true  ;
	   		 document.getElementById("orderno").disabled = true  ;
	        	$('#edit').attr('value','Edit');
	    		}
	    });
	    $('input:text').attr("disabled",true);
		document.getElementById("stage").disabled = true  ;
		 document.getElementById("grade").disabled = true ;
		 document.getElementById("pf").disabled = true ;
		 document.getElementById("pt").disabled = true ;
		 document.getElementById("esic").disabled = true ;
		 document.getElementById("createSSBT").disabled = true ;
		 document.getElementById("promodemo").disabled = true  ;
		 document.getElementById("orderno").disabled = true  ;
	});   
	
	
	function chk()
	{ var empno=document.getElementById("EMPNO").value;
	var res=empno.indexOf(":");
	if(res>0)
		{
		return true;
		}
	else
		{
		alert("Please select Employee !");
		document.getElementById("EMPNO").value="";
		document.getElementById("EMPNO").focus();
		return false;}
	
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
				var oa=parseInt(document.getElementById("otherVal").value);
				var ar=parseInt(document.getElementById("insuVal").value);
				var ara=parseInt(document.getElementById("colVal").value);
				document.getElementById("span1").innerHTML=xreq1.responseText;
				document.getElementById("basic").value=document.getElementById("bsc1").value;
				document.getElementById("basicValType").value=1;
				var a = document.getElementById("basicVal").value=document.getElementById("bsc1").value;
				var tot1=document.getElementById("total_Ern").value=parseInt(document.getElementById("basicVal").value);
				var tott1=document.getElementById("yearlyTotalErn").value=tot1*12;
				document.getElementById("yearlyBasicVal").value=12 * (document.getElementById("bsc1").value);
				
				
				document.getElementById("da").value=document.getElementById("d1").value;
				document.getElementById("daValType").value=1;
				var b=document.getElementById("daVal").value=document.getElementById("d1").value;
				var tot2=document.getElementById("total_Ern").value=tot1+parseInt(document.getElementById("daVal").value);
				var tott2=document.getElementById("yearlyTotalErn").value=tot2*12;
				document.getElementById("yearlyDaVal").value=12 * (document.getElementById("d1").value);
				
				document.getElementById("vda").value=document.getElementById("v1").value;
				document.getElementById("vdaValType").value=1;
				var c=document.getElementById("vdaVal").value=document.getElementById("v1").value;
				var tot3=document.getElementById("total_Ern").value=tot2+parseInt(document.getElementById("vdaVal").value);
				var tott3=document.getElementById("yearlyTotalErn").value=tot3*12;
				document.getElementById("yearlyvdaVal").value=12 * (document.getElementById("v1").value);
				
				document.getElementById("hra").value=document.getElementById("h1").value;
				document.getElementById("hraValType").value=1;
				var d=document.getElementById("hraVal").value=document.getElementById("h1").value;
				var tot4=document.getElementById("total_Ern").value=tot3+parseInt(document.getElementById("hraVal").value);
				var tott4=document.getElementById("yearlyTotalErn").value=tot4*12;
				document.getElementById("yearlyHraVal").value=12 * (document.getElementById("h1").value);
				
				document.getElementById("edu").value=document.getElementById("e1").value;
				document.getElementById("eduValType").value=1;
				var f=document.getElementById("eduVal").value=document.getElementById("e1").value;
				var tot5=document.getElementById("total_Ern").value=tot4+parseInt(document.getElementById("eduVal").value);
				var tott6=document.getElementById("yearlyTotalErn").value=tot5*12;
				document.getElementById("yearlyEduVal").value=12 * (document.getElementById("e1").value);
				 
				document.getElementById("medi").value=document.getElementById("m1").value;
				document.getElementById("mediValType").value=1;
				var e=document.getElementById("mediVal").value=document.getElementById("m1").value;
				var tot6=document.getElementById("total_Ern").value=tot5+parseInt(document.getElementById("mediVal").value);
				var tott5=document.getElementById("yearlyTotalErn").value=tot6*12;
				
				document.getElementById("yearlymediVal").value=12 * (document.getElementById("m1").value);
				
				var tot7=document.getElementById("total_Ern").value=tot6+parseInt(document.getElementById("c1").value);
				var tot8=document.getElementById("total_Ern").value=tot7+oa;
				var tot9=document.getElementById("total_Ern").value=tot8+ar;
				var tot10=document.getElementById("total_Ern").value=tot9+ara;
				var tott6=document.getElementById("yearlyTotalErn").value=tot10*12;
				document.getElementById("monthly").value=document.getElementById("total_Ern").value;
				document.getElementById("annual").value=document.getElementById("yearlyTotalErn").value;
																				
				document.getElementById("total").value=tot10;
				document.getElementById("yearlyTotal").value=tott6;
				document.getElementById("conv").value=document.getElementById("c1").value;
				document.getElementById("convValType").value=1;
				var g=document.getElementById("convVal").value=document.getElementById("c1").value;
				
				document.getElementById("yearlyConvVal").value=12 * (document.getElementById("c1").value);
				
				
				
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
	
	
	
	
	
	
</script>
<%
	CodeMasterHandler CMH = new CodeMasterHandler();
EmployeeBean empBean=new EmployeeBean();
EmployeeHandler EmpH=new EmployeeHandler(); 
CTCHandler CH=new CTCHandler();
	String action = "";
	String emp="";
	TranHandler TH = new TranHandler();	
	
	
	
	
	
	ArrayList<TranBean> incTranVals = new ArrayList<TranBean>();
	try
	{
		action = request.getParameter("action")==null?"":request.getParameter("action").toString();
		if(action.equalsIgnoreCase("2"))	//return from Save
		{
			emp = request.getAttribute("emp").toString();
			incTranVals = (ArrayList<TranBean>)request.getAttribute("incTranVals");
			
		}
		else
		{
			System.out.println("INTO ELSE..............");
			System.out.println("EMPNO="+session.getAttribute("EMPNO").toString());
			 incTranVals = TH.getCTCDISPLAY(Integer.parseInt(session.getAttribute("EMPNO").toString()));
		}
		
	}
	catch(Exception e)
	{
	e.printStackTrace();	
	}
		

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
		
		document.getElementById("monthly").value = Math.round((document.getElementById("annual").value / 12));
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
				
				document.getElementById(view).value = Math.round(((basedAmt * percent)/100.00));
				document.getElementById(yearly).value = Math.round((document.getElementById(view).value * 12.00));
				
			}
			else
			{
				
				document.getElementById(view).value = percent;
				document.getElementById(yearly).value = Math.round((document.getElementById(view).value * 12.00));
				
			}
			
			if(per == "basic")
			{
				calcMonthly(1);
			}
		}
		else
		{
			
			document.getElementById(per).value = 0.0;
			document.getElementById(view).value = 0.0;
			document.getElementById(yearly).value = 0.0;
		}
		
		calcTotal();
	}
	
	function calcTotal()
	{
		var a = document.getElementById("basicVal").value==""?0:document.getElementById("basicVal").value;
		var t1=document.getElementById("total_Ern").value=parseInt(a);
		var b = document.getElementById("daVal").value==""?0:document.getElementById("daVal").value;
		var t2=document.getElementById("total_Ern").value=t1+parseInt(b);
		var j = document.getElementById("vdaVal").value==""?0:document.getElementById("vdaVal").value;
		var t7=document.getElementById("total_Ern").value=t2+parseInt(j);
		var c = document.getElementById("hraVal").value==""?0:document.getElementById("hraVal").value;
		var t=document.getElementById("total_Ern").value=t7+parseInt(c);
		var l = document.getElementById("otherVal").value==""?0:document.getElementById("otherVal").value;
		var t3=document.getElementById("total_Ern").value=t+parseInt(l);
		var d = document.getElementById("eduVal").value==""?0:document.getElementById("eduVal").value;
		var t4=document.getElementById("total_Ern").value=t3+parseInt(d);
		var m = document.getElementById("mediVal").value==""?0:document.getElementById("mediVal").value;
		var t11=document.getElementById("total_Ern").value=t4+parseInt(m);
		
		var e = document.getElementById("insuVal").value==""?0:document.getElementById("insuVal").value;
		var t5=document.getElementById("total_Ern").value=t11+parseInt(e);
		
		var i = document.getElementById("convVal").value==""?0:document.getElementById("convVal").value;
		var t9=document.getElementById("total_Ern").value=t5+parseInt(i);
		
		var h = document.getElementById("colVal").value==""?0:document.getElementById("colVal").value;
		var t6=document.getElementById("total_Ern").value=t9+parseInt(h);
		
		/* v ar k = document.getElementById("spAllowVal").value==""?0:document.getElementById("spAllowVal").value;
		var t8=document.getElementById("total_Ern").value=t7+parseInt(k); 
		 */
		
		document.getElementById("total_Ern").value=t6;
		
		
		//var total_Ern = parseFloat(a)+parseFloat(b)+parseFloat(c)+parseFloat(d)+parseFloat(e)+parseFloat(h)+parseFloat(i)+parseFloat(j)+parseFloat(k)+parseFloat(l)+parseFloat(m);
		//total_Ern = Math.round(total_Ern);
		//document.getElementById("total_Ern").value=t11;
		document.getElementById("total").value=t6; 
		document.getElementById("monthly").value=t6;
/* 		var f = document.getElementById("pfeVal").value==""?0:document.getElementById("pfeVal").value;
		var g = document.getElementById("addLessVal").value==""?0:document.getElementById("addLessVal").value; */
		
		/* var total_Ded = parseFloat(f)+parseFloat(g);
		total_Ded = Math.round(total_Ded);
		document.getElementById("total_Ded").value=total_Ded;  */
		
		//var total = parseFloat(a)+parseFloat(b)+parseFloat(c)+parseFloat(d)+parseFloat(e)+parseFloat(h)+parseFloat(i)+parseFloat(j)+parseFloat(k)+parseFloat(l)+parseFloat(m);
		//total = Math.round(total);
		
		
		//Total of yearly amount
		
		/* var yearlyBasic = document.getElementById("yearlyBasicVal").value==""?0:document.getElementById("yearlyBasicVal").value;
		var yearlyDA = document.getElementById("yearlyDaVal").value==""?0:document.getElementById("yearlyDaVal").value;
		var yearlyHra = document.getElementById("yearlyHraVal").value==""?0:document.getElementById("yearlyHraVal").value;
		var yearlyEdu = document.getElementById("yearlyEduVal").value==""?0:document.getElementById("yearlyEduVal").value;
		var yearlyInsu = document.getElementById("yearlyInsuVal").value==""?0:document.getElementById("yearlyInsuVal").value;
		var yearlyCOL = document.getElementById("yearlyColVal").value==""?0:document.getElementById("yearlyColVal").value;
		var yearlyother = document.getElementById("yearlyotherVal").value==""?0:parseInt(document.getElementById("otherVal").value)*12;
		var yearlyvda = document.getElementById("yearlyvdaVal").value==""?0:document.getElementById("yearlyvdaVal").value;
		var yearlymedi = document.getElementById("yearlymediVal").value==""?0:document.getElementById("yearlymediVal").value;
		var yearlySplAllow = document.getElementById("yearlySpAllowVal").value==""?0:document.getElementById("yearlySpAllowVal").value;
		var yearlyConv = document.getElementById("yearlyConvVal").value==""?0:document.getElementById("yearlyConvVal").value;
		
		var yearlyTotalErn = parseFloat(yearlyBasic) + parseFloat(yearlyDA) + parseFloat(yearlyHra) + parseFloat(yearlyEdu) + parseFloat(yearlyInsu) + parseFloat(yearlyCOL)
							+ parseFloat(yearlyConv) + parseFloat(yearlyvda) + parseFloat(yearlySplAllow)+parseFloat(yearlyother) + parseFloat(yearlymedi);
		yearlyTotalErn = Math.round(yearlyTotalErn); */
		document.getElementById("yearlyTotalErn").value = t6*12;
		
	/* 	var yearlyPf = document.getElementById("yearlyPfeVal").value==""?0:document.getElementById("yearlyPfeVal").value;
		var yearlyAddLessAmt = document.getElementById("yearlyAddLessVal").value==""?0:document.getElementById("yearlyAddLessVal").value; */
		
	/* 	var yearlyTotalDed = parseFloat(yearlyPf) + parseFloat(yearlyAddLessAmt);
		yearlyTotalDed = Math.round(yearlyTotalDed);
		document.getElementById("yearlyTotalDed").value = yearlyTotalDed;  */
		
		/* var yearlyTotal = parseFloat(yearlyBasic) + parseFloat(yearlyDA) + parseFloat(yearlyHra) + parseFloat(yearlyEdu) + parseFloat(yearlyInsu) + parseFloat(yearlyCOL)
							+ parseFloat(yearlyConv) + parseFloat(yearlyvda) + parseFloat(yearlySplAllow)+parseFloat(yearlyother)+ parseFloat(yearlymedi);
		yearlyTotal = Math.round(yearlyTotal); */
		document.getElementById("yearlyTotal").value = t6*12; 
		document.getElementById("annual").value=t6*12;
		
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
			alert("No Annual CTC entered, Can't create Salary Structure");
			return false;
		}
		if(document.getElementById("basic").value == "")
		{
			alert("No BASIC value entered, Can't create Salary Structure");
			return false;
		}
		
		var monthly_gross = document.getElementById("monthly").value;
		var total = document.getElementById("total").value;
		
		if(monthly_gross != total){
			alert("Salary Structure is not Match");
			return false;
		}
		if(document.getElementById("date").value == "")
			{
			alert("Please Select Effective Date !");
			return false;
			}
		if(document.getElementById("promodemo").value == "0")
			{
			alert("Please Select Action Type !");
			return false;
			}
		if(document.getElementById("orderno").value == "")
			{
			alert("Please Enter Order Number !");
			return false;
			}
		if(document.getElementById("grade").value == "0")
			{
			alert("Please Select Grade !");
			return false;
			}
		if(document.getElementById("stage").value == "0")
			{
			alert("Please Select Stage !");
			return false;
			}
		
		
	
	var ans=confirm("Are you sure to Create Employee CTC ?");
	if(!ans)
		{
		return false
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
	
	function hideFeilds(){
	
		
		 document.getElementById("stage").disabled = false  ;
		 document.getElementById("grade").disabled = false ;
		 document.getElementById("pf").disabled = false ;
		 document.getElementById("pt").disabled = false ;
		 document.getElementById("esic").disabled = false ;
		 document.getElementById("createSSBT").disabled = false ;
		 document.getElementById("promodemo").disabled = false  ;
		 document.getElementById("orderno").disabled = false  ;
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
	
	
	
	function compareDate()
	{
		
		var E_date=document.getElementById("date").value;
		var dt=document.getElementById("doj").value;
		var max_date=document.getElementById("max_date").value;
		var min_date=document.getElementById("min_date").value;
	    o_Date = dt.replace(/-/g,"/");
	    e_date=E_date.replace(/-/g,"/");

	    
	    mx=max_date.replace(/-/g,"/");
	    mn=min_date.replace(/-/g,"/");
	    
	    
	     
	    var d1 = new Date(e_date);

	    var d2 =new  Date(o_Date);
	   
	    var d_mx = new Date(mx);
		var d_mn =new  Date(mn);
		if (d1.getTime()<d2.getTime()) 
	    {
			
	    		alert("Effective Date must be Greater or within the month of Joining !\n\t Please Select Effective Date again !");
	    		document.getElementById('date').value="";
	    		
	    }
		else if(d1.getTime() < d_mn.getTime() || d1.getTime() > d_mx.getTime())
			{
			
			alert("Effective Date must be within the Current Salary month !\n\t Please Select Effective Date again !");
    		document.getElementById('date').value="";
			
			}
		
		
		
		
	}
</script>

</head>
<body style="overflow: hidden;" onLoad="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; "  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Display Cost To Company (CTC)</h1>
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
			<div id="table-content" style="overflow-y:auto; max-height:500px; ">
			<table width="950" border="1" align="center">
			  <tr><td align="center"><br/>
			  <form action="CTCServlet?action=create" method="post" onKeyPress="return event.keyCode != 13;" onSubmit="return validate()">
			  <table style="width: 90%;" height="66" border="1">
			   <tr bgcolor="#eaeaea">
			   <%	
			   
			   empBean=EmpH.getEmployeeInformation(emp==null || emp.equalsIgnoreCase("")?session.getAttribute("EMPNO").toString():emp);
			   
			   
			   
			   String max_date=CH.getMAXDateinPaytran(Integer.toString(empBean.getEMPNO()));
			   String min_date=CH.getMINDateinPaytran(Integer.toString(empBean.getEMPNO()));
			   
				emp=empBean.getFNAME()+" "+empBean.getLNAME()+":"+empBean.getEMPCODE()+":"+empBean.getEMPNO();
			   
			   
			   %>
			     <td height="33" colspan="2">&nbsp;Type Employee Name or Number 
			       <input name="EMPNO" type="text" id="EMPNO"  onClick="this.select();" value="<%=emp%>" disabled="disabled" size="40">
			       <input type="button" value="Check" onclick="empCTC()"/>
			       <input type="button" value="EDIT" id="edit"  />
			     </td>
			   </tr>
			   <tr>
			   <%
			   double sum = 0.0d;
			   double gross = 0.0d;
			   float pf=0.0f;
			   float pt=0.0f;
			   float esic=0.0f;
			   for(TranBean tb : incTranVals){
				  sum = sum+tb.getNET_AMT();
				  pf=tb.getPf();
				  pt=tb.getPt();
				  esic=tb.getEsic();
				  
			   }
			   gross = 12*sum;
			   System.out.println("SUM="+sum);
			   System.out.println("SUM*12="+sum*12);
			   %>
			     <td width="261">&nbsp;Annual Total  
			       <input name="annual" type="text" id="annual"  readonly="readonly" onKeyUp="calcMonthly(0)" value="<%=gross %>" dir="rtl" tabindex="1" disabled="disabled">
			       Rs.</td>
			     <td width="272">&nbsp;Monthly Gross 
			       <input name="monthly" type="text" id="monthly" readonly="readonly" disabled="disabled" value="<%= sum %>" style="background-color:silver;" dir="rtl">
			       Rs.</td>
			   </tr>
			   
			   <tr>
			   <td width="261">
			   Joining Date : <%=empBean.getDOJ()==null?ReportDAO.getSysDate():empBean.getDOJ()%> 
			   <input type="hidden" id='doj' value="<%=empBean.getDOJ()==null?ReportDAO.getSysDate():empBean.getDOJ()%>">
			   <input type="hidden" id='max_date' value="<%=max_date%>">
			   <input type="hidden" id='min_date' value="<%=min_date%>">
			      
			   </td> 
			   <td width="272">
			       Effective Date : 
			       <input name="date" size="20"
							id="date" type="text" value="" readonly="readonly"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}" onchange="compareDate()">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
			       </td>
			   </tr>
			   
			   <tr>
			   <td width="261">
			   Action Type : <select name='promodemo' id='promodemo'  title='Select the Action' style='width: 210px;margin-right: 12px;'  disabled="disabled">
			      <option value='0'>SELECT</option>
			      <option value='50'>PROMOTION</option>
			      <option value='80'>DEMOTION</option>
			      <option value='142'>SPCL PROMOTION</option></select>
			      
			   </td> 
			   <td width="272">
			       Order Number : 
			       <input name="orderno" type="text" id="orderno"   disabled="disabled" value=""  dir="rtl">
			       </td>
			   </tr>
			   
			   <tr height="25">
			   
			   <td colspan="3">
			      Select Grade :
			      <select name='grade' id='grade' onchange="getStageInfo()" title='Select the Grade of Employee!' style="margin-right: 10px;"  disabled="disabled">
			      <option value='0'>Select</option>
			      <%
				      LookupHandler lkh=new LookupHandler();
				  	  ArrayList<Lookup> lkp_list=lkh.getSubLKP_DESC("DESIG");
				      
				  	  for(Lookup lkp_bean:lkp_list)
				      {	 
				  		  %>
							<option value="<%=lkp_bean.getLKP_SRNO()%>" <%=(lkp_bean.getLKP_SRNO()==empBean.getGRADE())?"Selected":"" %> ><%=lkp_bean.getLKP_DESC()%></option>
					     
					      <%	 
				  	    }
			      %>
			     
			        
			      
			      </select> 	
			      
			      
			      
			      Select Stage :
			      <select name='stage' id='stage' onchange="getGradeInfo()" style='width: 60px;margin-right: 20px;' title='Select the stage of Employee!'  disabled="disabled">
			      	<option value='0'>Select</option>
			      	<option value='<%=empBean.getSTAGE()%>' selected="selected"><%=empBean.getSTAGE()%></option>
			      </select>
			      
			      <span id='span1'>
			      </span>
			   
			   
			      &nbsp;PF &nbsp;
			      				<%if(pf==1){%>
			       				<input name="pf" type="checkbox" id="pf" value="true" checked="checked"   disabled="disabled">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%}else{ %>
			       				<input name="pf" type="checkbox" id="pf" value="true"  disabled="disabled">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%} %>
			       				Eligible for PT &nbsp;
			       				<%if(pt==1){ %>
			       				<input name="pt" type="checkbox" id="pt" value="true" checked="checked"  disabled="disabled">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%}else{ %>
			       				<input name="pt" type="checkbox" id="pt" value="true"   disabled="disabled">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       				<%} %>
			   					Eligible for ESIC &nbsp;
			   					<%if(esic==1){ %>
			   					<input name="esic" type="checkbox" id="esic" value="true" checked="checked"  disabled="disabled">
			   					<%}else{ %>
			      				<input name="esic" type="checkbox" id="esic" value="true"   disabled="disabled">
			      				<%} %>
			      </td>
			   </tr>
			   </table>
			   
			   <p>&nbsp;</p>
			   <table style="line-height: 20px;" width="850" border="1">
               	<tr align="center" bgcolor="#eaeaea">
                  <td width="50" align="center"><b>Sr. </b></td>
                  <td width="132"><b>Type</b></td>
                  <td width="80"><b>Value Type</b> </td>
                  <td width="70"><b>Value</b></td>
                  <td width="70"><b>Based On</b></td>
                  <td width="130"><b>Monthly Amount</b></td>
                  <td width="120"><b>Yearly Amount</b></td>
                  
                </tr>
                <tr>
                <% float addition=0.0f;
			   		for(TranBean tb : incTranVals){
						if(tb.getTRNCD()==101){%>
				 <td align="center" bgcolor="#FFFFFF">1</td>
			     <td align="left" bgcolor="#FFFFFF">&nbsp;Basic </td>
			     <td align="center"><select name="basicValType" id="basicValType" onChange="calcValue('basic','basicOn','basicVal','yearlyBasicVal','basicValType')">
			     
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
			     </select></td>
			     <td align="center">&nbsp;<input name="basic" type="text" value="<%= tb.getINP_AMT() %>" id="basic" size="8" onkeyup="calcValue('basic','basicOn','basicVal','yearlyBasicVal','basicValType')" disabled="disabled" autocomplete="off"  dir="rtl" tabindex="2" autocomplete="false"></td>
			     <td align="center" bgcolor="#FFFFFF"><select name="basicOn" id="basicOn">
			     <option value="199">Gross</option>
			     </select></td>
			     <%addition=addition+tb.getNET_AMT(); %>
			     <td bgcolor="#FFFFFF">&nbsp;<input name="basicVal" type="text" size="15" id="basicVal" value="<%= tb.getNET_AMT() %>" disabled="disabled" dir="rtl"> Rs.</td>
			     <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyBasicVal" type="text" id="yearlyBasicVal" value="<%= tb.getNET_AMT()*12 %>" disabled="disabled" size="13" dir="rtl"> Rs.</td>
			     </tr>
				<%}		  
			   	}
                	for(TranBean tb : incTranVals){
                		if(tb.getTRNCD()==102){	%>
                <tr>
                  <td align="center" bgcolor="#FFFFFF">2</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;D.A. </td>
                  <td align="center"><select name="daValType" id="daValType" onChange="calcValue('da','daOn','daVal','yearlyDaVal','daValType')">
                   
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="da" type="text" value="<%= tb.getINP_AMT() %>" id="da" size="8" onkeyup="calcValue('da','daOn','daVal','yearlyDaVal','daValType')" disabled="disabled" dir="rtl" tabindex="3"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="daOn" id="daOn" onChange="calcValue('da','daOn','daVal','yearlyDaVal','daValType')">
                    
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                    
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="daVal" type="text" id="daVal" size="15" value="<%= tb.getNET_AMT() %>" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyDaVal" type="text" id="yearlyDaVal" value="<%= tb.getNET_AMT()*12 %>" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                	
                	for(TranBean tb : incTranVals){
                        if(tb.getTRNCD()==138){%>
                	<tr bgcolor="#FFFFFF">
                    <td align="center">3</td>
                    <td align="left">&nbsp;V.D.A</td>
                    <td align="center"><select name="vdaValType" id="vdaValType" onChange="calcValue('vda','vdaOn','vdaVal','yearlyvdaVal','vdaValType')">
                    
                    <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     	<option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
                    
                    </select></td>
                    <td align="center">&nbsp;<input name="vda" onkeypress="return inputLimiter(event,'Numbers')" value="<%=tb.getINP_AMT()%>"  disabled="disabled"  type="text" id="vda" size="8" autocomplete="off" onKeyUp="calcValue('vda','vdaOn','vdaVal','yearlyvdaVal','vdaValType')" dir="rtl" tabindex="11"></td>
                    <td align="center"><select name="vdaOn" id="vdaOn" onChange="calcValue('vda','vdaOn','vdaVal','yearvdausVal','vdaValType')">
                      <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                      <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                    </select></td>
                     <%addition=addition+tb.getNET_AMT();%>
                    <td>&nbsp;<input name="vdaVal" type="text" id="vdaVal" size="15"  value="<%= tb.getNET_AMT() %>"  readonly="readonly" dir="rtl"  disabled="disabled" > Rs.</td>
                    <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyvdaVal" type="text" id="yearlyvdaVal"  value="<%= tb.getNET_AMT()*12 %>"  disabled="disabled"  readonly="readonly" size="13" dir="rtl"> Rs.</td>
                  </tr>
                	
                <%
                        }
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==103){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">4</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;HRA </td>
                  <td align="center"><select name="hraValType" id="hraValType" onChange="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')">
                    
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="hra" type="text" id="hra" value="<%= tb.getINP_AMT() %>" onKeyUp="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')" size="8" autocomplete="off" disabled="disabled" dir="rtl" tabindex="4"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="hraOn" id="hraOn" onChange="calcValue('hra','hraOn','hraVal','yearlyHraVal','hraValType')">
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT();%>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="hraVal" type="text" size="15" value="<%= tb.getNET_AMT() %>" id="hraVal" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyHraVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyHraVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                
                for(TranBean tb : incTranVals){
                    if(tb.getTRNCD()==129){%>
                
                <tr>
                <td align="center" bgcolor="#FFFFFF">5</td>
                <td align="left" bgcolor="#FFFFFF">&nbsp;Other Allow.  </td>
                <td align="center"><select name="otherValType" id="otherValType" onChange="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')">
                  <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
                    
                </select></td>
                <td align="center">&nbsp;<input name="other" onkeypress="return inputLimiter(event,'Numbers')"  value="<%= tb.getINP_AMT() %>"  disabled="disabled"   type="text" id="other" size="8" autocomplete="off" onKeyUp="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')" dir="rtl" tabindex="5"></td>
                <td align="center" bgcolor="#FFFFFF"><select name="otherOn" id="otherOn" onChange="calcValue('other','otherOn','otherVal','yearlyotherVal','otherValType')">
                 
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                </select></td>
                <%addition=addition+tb.getNET_AMT();%>
                <td bgcolor="#FFFFFF">&nbsp;<input name="otherVal" type="text" size="15" id="otherVal" value="<%= tb.getNET_AMT() %>"   disabled="disabled"  readonly="readonly" dir="rtl"> Rs.</td>
                <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyotherVal" type="text" id="yearlyotherVal"  value="<%= tb.getNET_AMT()*12 %>"   disabled="disabled" readonly="readonly" size="13" dir="rtl"> Rs.</td>
              </tr>
                
                
                
              <%} 
      	}
                
                
                
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==105){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">6</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;Education  </td>
                  <td align="center"><select name="eduValType" id="eduValType" onChange="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')">
                    
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="edu" type="text" id="edu" disabled="disabled" value="<%= tb.getINP_AMT() %>" onKeyUp="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')" size="8" autocomplete="off"  dir="rtl" tabindex="5"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="eduOn" id="eduOn" onChange="calcValue('edu','eduOn','eduVal','yearlyEduVal','eduValType')">
                     <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="eduVal" type="text" value="<%= tb.getNET_AMT() %>" size="15" id="eduVal" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyEduVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyEduVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                
                
                for(TranBean tb : incTranVals){
                    if(tb.getTRNCD()==104){%>
                <tr>
                <td align="center" bgcolor="#FFFFFF">7</td>
                <td align="left" bgcolor="#FFFFFF">&nbsp;Medical Allow. </td>
                <td align="center"><select name="mediValType" id="mediValType" onChange="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')">
                  <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
                </select></td>
                <td align="center">&nbsp;<input name="medi" onkeypress="return inputLimiter(event,'Numbers')"  value="<%= tb.getINP_AMT() %>"  disabled="disabled"  type="text" id="medi" size="8" autocomplete="off" onKeyUp="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')" dir="rtl" tabindex="5"></td>
                <td align="center" bgcolor="#FFFFFF"><select name="mediOn" id="mediOn" onChange="calcValue('medi','mediOn','mediVal','yearlymediVal','mediValType')">
                   <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                </select></td>
                <%addition=addition+tb.getNET_AMT();%>
                <td bgcolor="#FFFFFF">&nbsp;<input name="mediVal" type="text" size="15" id="mediVal" readonly="readonly"  value="<%= tb.getNET_AMT() %>"  disabled="disabled" dir="rtl"> Rs.</td>
                <td bgcolor="#FFFFFF">&nbsp;<input name="yearlymediVal" type="text" id="yearlymediVal" readonly="readonly"  value="<%= tb.getNET_AMT()*12 %>"  disabled="disabled" size="13" dir="rtl"> Rs.</td>
              </tr>
                
                 <%} 
                	}
                
                
                
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==140){%>
                
                <tr>
                  <td align="center" bgcolor="#FFFFFF">8</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;AREABDV </td>
                  <td align="center"><select name="insuValType" id="insuValType" onChange="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')">
                    
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="insu" type="text" value="<%= tb.getINP_AMT() %>" id="insu" onKeyUp="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')" size="8" disabled="disabled" autocomplete="off"  dir="rtl" tabindex="6"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="insuOn" id="insuOn" onChange="calcValue('insu','insuOn','insuVal','yearlyInsuVal','insuValType')">
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="insuVal" type="text" size="15" value="<%= tb.getNET_AMT() %>" id="insuVal" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyInsuVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyInsuVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==108){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">9</td>
                  <td align="left">&nbsp;Conv</td>
                  <td align="center"><select name="convValType" id="convValType" onChange="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')">
                   
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="conv" type="text" value="<%= tb.getINP_AMT() %>" id="conv" onKeyUp="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')" size="8" autocomplete="off" disabled="disabled"  dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="convOn" id="convOn" onChange="calcValue('conv','convOn','convVal','yearlyConvVal','convValType')">
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="convVal" type="text" id="convVal" size="15" value="<%= tb.getNET_AMT() %>" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyConvVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyConvVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==107){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">10</td>
                  <td align="left">&nbsp;Special Allowance</td>
                  <td align="center"><select name="spAllowValType" id="spAllowValType" onChange="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')">
                  
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="spAllow" type="text" value="<%= tb.getINP_AMT() %>" id="spAllow"  onKeyUp="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')" size="8" autocomplete="off" disabled="disabled" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="spAllowOn" id="spAllowOn" onChange="calcValue('spAllow','spAllowOn','spAllowVal','yearlySpAllowVal','spAllowValType')">
                    <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="spAllowVal" type="text" id="spAllowVal" size="15" disabled="disabled" value="<%= tb.getNET_AMT() %>" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlySpAllowVal" type="text" id="yearlySpAllowVal" value="<%= tb.getNET_AMT()*12 %>" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                <%} 
                	}
                /* for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==135){ */%>
                
                <%-- <tr bgcolor="#FFFFFF">
                  <td align="center">11</td>
                  <td align="left">&nbsp; Bonus</td>
                  <td align="center"><select name="bonusValType" id="bonusValType" onChange="calcValue('bonus','bonusOn','bonusVal','yearlyBonusVal','bonusValType')">
                   
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="bonus" type="text" value="<%= tb.getINP_AMT() %>" id="bonus" size="8" autocomplete="off"  onKeyUp="calcValue('bonus','bonusOn','bonusVal','yearlyBonusVal','bonusValType')" disabled="disabled" dir="rtl" tabindex="11"></td>
                  <td align="center"><select name="bonusOn" id="bonusOn" onChange="calcValue('bonus','bonusOn','bonusVal','yearlyBonusVal','bonusValType')">
                     <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="bonusVal" type="text" id="bonusVal" size="15" disabled="disabled" value="<%= tb.getNET_AMT() %>" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyBonusVal" type="text" id="yearlyBonusVal" disabled="disabled" value="<%= tb.getNET_AMT()*12 %>" size="13" dir="rtl"> Rs.</td>
                </tr> --%>
                <%/* } 
                	} */
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==141){%>
                
                <tr bgcolor="#FFFFFF">
                  <td align="center">11</td>
                  <td align="left">&nbsp;AREAALL</td>
                  <td align="center"><select name="colValType" id="colValType" onChange="calcValue('col','colOn','colVal','yearlyColVal','colValType')">
                   
			     <option value="0" <%=tb.getSRNO()==0?"selected":"" %>>Percentage</option>
			     <option value="1" <%=tb.getSRNO()==1?"selected":"" %>>Fixed Value</option>
			     
                     </select></td>
                  <td align="center">&nbsp;<input name="col" type="text" value="<%= tb.getINP_AMT() %>" id="col" size="8" onKeyUp="calcValue('col','colOn','colVal','yearlyColVal','colValType')" autocomplete="off" disabled="disabled" dir="rtl" tabindex="10"></td>
                  <td align="center"><select name="colOn" id="colOn" onChange="calcValue('col','colOn','colVal','yearlyColVal','colValType')">
                     <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%addition=addition+tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="colVal" type="text" id="colVal" value="<%= tb.getNET_AMT() %>" size="15" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyColVal" value="<%= tb.getNET_AMT()*12 %>" type="text" id="yearlyColVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr>
                
                 <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total_Ern" type="text" disabled="disabled" readonly="readonly" value="<%=addition %>" size="15" id="total_Ern" dir="rtl" style="background-color:#7fe5ff">/- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotalErn" type="text" readonly="readonly" value="<%=addition *12%>" id="yearlyTotalErn" disabled="disabled" size="13" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>
                
                <tr>
                	<td colspan="7"><b>Deductions</b></td>
                </tr>
                <%} 
                	}
                float deduction=0.0f;
                /* float deduction=0.0f;
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==201){ */%>
                
                <%-- <tr>
                  <td align="center" bgcolor="#FFFFFF">13</td>
                  <td align="left" bgcolor="#FFFFFF">&nbsp;PF Employer 12% </td>
                  <td align="center"><select name="pfeValType" id="pfeValType" onChange="calcValue('pfe','pfeOn','pfeVal','yearlyPfeVal','pfeValType')">
                    <option value="0" selected>Percentage</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="pfe" type="text" value="<%= tb.getINP_AMT() %>" id="pfe" size="8" autocomplete="off" onKeyUp="calcValue('pfe','pfeOn','pfeVal','yearlyPfeVal','pfeValType')" value="12" disabled="disabled" dir="rtl" tabindex="8"></td>
                  <td align="center" bgcolor="#FFFFFF"><select name="pfeOn" id="pfeOn" onChange="calcValue('pfe','pfeOn','pfeVal','yearlyPfeVal','pfeValType')">
                     <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                  <%deduction =deduction + tb.getNET_AMT(); %>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="pfeVal" type="text" id="pfeVal" value="<%= tb.getNET_AMT() %>" size="15" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyPfeVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyPfeVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr> --%>
                <%/*} 
                	}
                for(TranBean tb : incTranVals){
                if(tb.getTRNCD()==127){*/%>
                <%-- <%-- <tr bgcolor="#FFFFFF">
                  <td align="center">14</td>
                  <td align="left">&nbsp;Add Less Amount </td>
                  <td align="center"><select name="addLessValType" id="addLessValType" onChange="calcValue('addLess','addLessOn','addLessVal','yearlyAddLessVal','addLessValType')">
                    <option value="1">Fixed Value</option>
                  </select></td>
                  <td align="center">&nbsp;<input name="addLess" type="text" value="<%= tb.getINP_AMT() %>" id="addLess" size="8" autocomplete="off" onKeyUp="calcValue('addLess','addLessOn','addLessVal','yearlyAddLessVal','addLessValType')" disabled="disabled" dir="rtl" tabindex="9"></td>
                  <td align="center"><select name="addLessOn" id="addLessOn" onChange="calcValue('addLess','addLessOn','addLessVal','yearlyAddLessVal','addLessValType')">
                     <option value="101" <%=tb.getADJ_AMT()==101?"selected":""%>>Basic</option>
                    <option value="199" <%=tb.getADJ_AMT()==199?"selected":""%>>Gross</option>
                  </select></td>
                   <%deduction =deduction + tb.getNET_AMT(); %>
                  <td>&nbsp;<input name="addLessVal" type="text" id="addLessVal" size="15" value="<%= tb.getNET_AMT() %>" disabled="disabled" dir="rtl"> Rs.</td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyAddLessVal" type="text" value="<%= tb.getNET_AMT()*12 %>" id="yearlyAddLessVal" disabled="disabled" size="13" dir="rtl"> Rs.</td>
                </tr> --%>
                <%/*}} */ %>
                
               <tr>
                  <td colspan="5" align="right">Total Rs.&nbsp;&nbsp;</td>
                  <td>&nbsp;<input name="total_Ded" type="text" disabled="disabled" size="15" value="<%=deduction %>" id="total_Ded" dir="rtl" style="background-color:#7fe5ff"> /- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotalDed" type="text" value="<%=deduction*12 %>" id="yearlyTotalDed" disabled="disabled" size="13" dir="rtl" style="background-color:#eaeaea"> /-</td>
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
                  <td>&nbsp;<input name="total" type="text" disabled="disabled" size="15" id="total" readonly="readonly" value="<%=sum%>" dir="rtl" style="background-color:#7fe5ff"> /- </td>
                  <td bgcolor="#FFFFFF">&nbsp;<input name="yearlyTotal" type="text" id="yearlyTotal"  readonly="readonly" value="<%=gross%>" disabled="disabled" size="13" dir="rtl" style="background-color:#eaeaea"> /-</td>
                </tr>	
              </table>
              
              <a href="javascript:print()"> <input type="button" name="Print" value="Print"></a>
			   <input type="submit" name="Submit" value="Create Salary Structure"  id="createSSBT"  disabled="disabled" />
			   <input type="reset" name="reset" value="Clear">
			   
              
			   <br/></form></td>
			  </tr></table>
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
    
</body>
