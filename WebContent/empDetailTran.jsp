<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.Format"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
 <link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" /> 
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">

.ac_results {
	padding: 0px;
	border: 1px solid #cacaca;
	background-color: #f5f6fa;
	overflow: hidden;
	width: 58% !important;
}

</style>



<script>
	jQuery(function() {
		$("#pp").autocomplete("projlist.jsp");
	});
	
	jQuery(function() {
		$("input:text").blur(function(){
			if($(this).val()=="" || $(this).val()==" ")
			{
				 
				if($(this).attr('id')!="pp")
					{					
				alert("Please enter Some value !");
				$(this.value="0.0");
					}
					
			}
			
		});
	});
	
	
</script>

<%
try
{
LookupHandler lkph= new LookupHandler();
TranHandler trnh= new TranHandler();
EmpOffHandler empOffHldr = new EmpOffHandler();
EmployeeHandler emphdlr = new EmployeeHandler();
LookupHandler lkh=new LookupHandler();
CodeMasterHandler CMH = new CodeMasterHandler();
ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();   
ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
ArrayList<TranBean>  listbyEMPNO=new  ArrayList<TranBean> ();
ArrayList<TranBean> projEmpNolist = new ArrayList<TranBean>();
ArrayList<TranBean> projEmpNmlist = new ArrayList<TranBean>();

TRNCODE=CMH.getNoAutocalCDList();


/* String date1=trnh.getSalaryDate();
 */
 
 String date1=request.getParameter("date1")==null?ReportDAO.getSysDate():request.getParameter("date1");

 String[] d={"","",""};
float jdays=0.0f;
String joindate,leftdate;
float days=Calculate.getDays(date1);
d=date1.split("-");
String date_disp = d.length!=0?d[1]+"-"+d[2]:"";


int trncd=0;
String select=new String();
String selectCode = new String();
LeaveMasterHandler obj=new LeaveMasterHandler();
String empName;
String action = request.getParameter("action")==null?"":request.getParameter("action");
//System.out.println("action is"+action);
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type
int trn=0;
int keys=0;
int empno1=0;
int empno=0;
int flag=-1;
String prjCode = "";
try
{  
	
	try
	{
	flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
	}catch(Exception e)
	{
		System.out.println("no flag value"+flag);
		if( request.getParameter("PrjCode")==null)
		{
		session.setAttribute("prjCode", "");
		}
	}
	if(action.equalsIgnoreCase("getdetails"))
	{
		// For Allowances and expenses
		/* String trncd1=request.getParameter("key");
		trn=Integer.parseInt(trncd1);
		select=request.getParameter("selected");
		session.setAttribute("trncd", trn);
		session.setAttribute("selectvalue", select);
		 */
		
		 prjCode = request.getParameter("PrjCode");
		  
	    session.setAttribute("prjCode", prjCode);
	    //projEmpNolist = empOffHldr.getEmpList(prjCode);
	    projEmpNolist = empOffHldr.getEmpList_Status(prjCode,date_disp);
	    session.setAttribute("projEmpNolist", projEmpNolist);
	    int i=0;
	    ArrayList<Integer> cds=new ArrayList<Integer>(Arrays.asList(225,226,228,223,212,130,131,301));
	    session.setAttribute("cds", cds);
	    ArrayList<TranBean> arl=new ArrayList<TranBean>();
	    for(TranBean tbn : projEmpNolist){
	    	TranBean trbn = new TranBean();
	    	for(int x=0;x<cds.size();x++)
	    	{
	    	
	    	trbn=trnh.getTranByEmpno1(tbn.getEMPNO(), cds.get(x));
	    	//System.out.println(trbn.getEMPNO()+"\t"+trbn.getEMPNAME()+"\t"+trbn.getINP_AMT()+"\t"+trbn.getTRNCD()+"\t"+trbn.getTRNDT());
	    	arl.add(trbn);
	    	}
	    	projEmpNmlist.add(trbn);
	    	
	    } 
	    
	    
	  //  System.out.println("empDetailTran Arraylist of= "+arl.size());
	    //session.setAttribute("projEmpNmlist", projEmpNmlist);
	    
	   /*  tranlist = trnh.getTranDetail(trn);
	    session.setAttribute("list", tranlist); */
	    
	}
	
	/* if(action.equalsIgnoreCase("edit"))
    {
		String[] str1=request.getParameter("key1").split(":");
	    empno =Integer.parseInt(str1[0]);
		int trncd1=Integer.parseInt(str1[1]);
		System.out.println("empno is "+empno);
		 
		listbyEMPNO=trnh.getTranByEmpno(empno,trncd1);
		//listbyEMPNO=(ArrayList)session.getAttribute("list");
		
    }
	*/
	if(action.equalsIgnoreCase("afterEdit"))
	{
		 session.removeAttribute("projEmpNmlist");
		 	   
		    int i=0;
		    ArrayList<Integer> cds=new ArrayList<Integer>(Arrays.asList(225,226,228,223,212,130,131,301));
		   
		    ArrayList<TranBean> arl=new ArrayList<TranBean>();
		    
		    prjCode = (String)session.getAttribute("prjCode");
		    projEmpNolist = (ArrayList<TranBean>)session.getAttribute("projEmpNolist");
		    for(TranBean tbn : projEmpNolist){
		    	TranBean trbn = new TranBean();
		    	for(int x=0;x<cds.size();x++)
		    	{
		    	
		    	trbn=trnh.getTranByEmpno1(tbn.getEMPNO(), cds.get(x));
		    	//System.out.println(trbn.getEMPNO()+"\t"+trbn.getEMPNAME()+"\t"+trbn.getINP_AMT()+"\t"+trbn.getTRNCD()+"\t"+trbn.getTRNDT());
		    	arl.add(trbn);
		    	}
		    	projEmpNmlist.add(trbn);
		
		    	 session.setAttribute("projEmpNmlist", projEmpNmlist);
		
		
	} 
	 
}
}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
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


function validate()
{
	if(document.getElementById("pp").value=="" || 0>(document.getElementById("pp").value).indexOf(":"))
		{
		alert("Please Select Project !");
		return false;
		}
	else
		{return true;}
}


	function getTranDetails() {
/* 		 var type = document.getElementById("trncd").value;
		var desc = document.getElementById("trncd");
		var selected = desc.options[desc.selectedIndex].text;  */
		
	var proj=document.getElementById("pp").value;
		var date1=document.getElementById("date1").value;
		var res = proj.indexOf(":"); 
		if(proj=="")
			{			
			//alert("Please Select Project !");
			}
		else
			{
			if(res<0)
				{
				//alert("Please Select Project !");
				document.getElementById("pp").value="";
				document.getElementById("pp").focus();
				}
			else
				{
		var p=proj.split(":");
		var prjCode = p[3];
		//alert("prjcode="+prjCode);
		
		
			if(prjCode == ""){
				
			}
		
		else{
			//window.location.href = "EmpTranNew.jsp?action=getdetails&key="+ type + "&PrjCode="+prjCode+"&selected=" + selected;
			//alert("into else");
			<%
			
			
			%>
			
			proj=proj.replace(/ & /g," and ");
			window.location.href = "empDetailTran.jsp?action=getdetails&PrjCode="+prjCode+"&proj="+proj+"&date1="+date1;
		}
				}
		}
	}
	function update(key) {
		window.showModalDialog("UpdateCodeMast.jsp?key=" + key,null,"dialogWidth:700px; dialogHeight:230px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		getTranCodes();
	}

	function editTranAmount(str) {
		var flag = confirm("Do you want to Edit this Amount");
		if (flag) {

			window.location.href = "empTranDetails.jsp?action=edit&key1=" + str;
		}

	}

	function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Tranaction Saved Successfully..!\n\n\t Please Re-Calculate The salary of Updated Employee..! ");

		}
		if (f == 2) {
			alert("Employee Already Exist");
		}
		if (f == 3) {
			alert("Record updated Successfully..!\n\n\t Please Re-Calculate The salary of Updated Employee..! ");
		}
		
	}

	
	
	function getTotalDays(id)
	{
	var e=document.getElementById("sp1").innerHTML; // Date for number of days
	
		 
	
	var abs = parseFloat(document.getElementById(id).value);  //absent days
		var numb = id.match(/\d/g);
		numb = numb.join("");
		var day=parseFloat(document.getElementById("presentdays"+numb).value); //present days
		var abs = parseFloat(document.getElementById("absValue"+numb).value);  //absent days
		//var pl = parseFloat(document.getElementById("plValue"+numb).value);  //PL days
		if(day<abs|| abs<0)
			{			
			alert("Please enter valid absent days");
			document.getElementById(id).value="0.0";
			//document.getElementById("totValue"+numb).value=(day-(parseFloat(document.getElementById("plValue"+numb).value))-(parseFloat(document.getElementById("absValue"+numb).value))).toFixed(1);
			document.getElementById("totValue"+numb).value=(day-(parseFloat(document.getElementById("absValue"+numb).value))).toFixed(1);
			document.getElementById(id).focus();
			}
		else
			{
		//document.getElementById("totValue"+numb).value=(day-abs-pl).toFixed(1);
		document.getElementById("totValue"+numb).value=(day-abs).toFixed(1);
		document.getElementById("absValue"+numb).value=(abs).toFixed(1);
		document.getElementById("plValue"+numb).value=(pl).toFixed(1);
			}
		day=0;
		numb=0;
		abs=0;
	}
	
	function getcancel() {

		document.getElementById("d1").hidden = true;
	}
	
	function enableTextBoxes()
	{
		var flag=document.getElementById("edit").value;
		var flag1;
		
			
			var r = confirm("Do you want to "+flag+" records!");
			if (r == true)
			{
				if(flag=="Edit")
				{flag1=false;
				document.getElementById("edit").value="Disable";
				}
			else
			{
				flag1=true;
				document.getElementById("edit").value="Edit";
			}
				
		var adv = document.getElementsByName("advValue");
		var loan = document.getElementsByName("loanValue");
		var tds = document.getElementsByName("tdsValue");
		var mob = document.getElementsByName("mobValue");
		var othr = document.getElementsByName("othrValue");
		var ea1 = document.getElementsByName("ea1Value");
		var ea2 = document.getElementsByName("ea2Value");
		var abs = document.getElementsByName("absValue");
		var pl = document.getElementsByName("plValue");
		for(var i=0; i<adv.length; i++)
		{
			document.getElementById(adv[i].id).disabled=flag1;
			document.getElementById(loan[i].id).disabled=flag1;
			document.getElementById(tds[i].id).disabled=flag1;
			document.getElementById(mob[i].id).disabled=flag1;
			document.getElementById(othr[i].id).disabled=flag1;
			document.getElementById(ea1[i].id).disabled=flag1;
			document.getElementById(ea2[i].id).disabled=flag1;
			document.getElementById(abs[i].id).disabled=flag1;
			document.getElementById(pl[i].id).disabled=flag1;
			document.getElementById(adv[i].id).readOnly='true';
			//document.getElementById(mob[i].id).readOnly='true';

		}
		document.getElementById("tranSave").disabled=flag1;
		
		
		
	}
	}

	function showdiv() {
		var e = document.getElementById("addN");
		if (e.style.display == 'none') {
			e.style.display = 'block';
		} else if (e.style.display == 'block') {
			e.style.display = 'none';

		}

	}

	function calTotal(val) {
		
		var count = document.getElementById("count").value;
		var advTotal=0;
		//alert(val);
		for(var i=0; i<count; i++) {
			var temp = document.getElementById(val+i).value; 
			advTotal += parseFloat(temp);
			//alert(parseFloat(document.getElementById(val+0).value));
		}
		document.getElementById(val+"Total").value = advTotal;
	}
	
	
	
	
	/* function validate() {
		var EMPNO = document.getElementById("EMPNO").value;

		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos = EMPNO.indexOf(":");
		if (atpos < 1) {
			alert("Please Select Correct Employee Name");
			return false;
		}

		if (document.getElementById("amount").value == "") {
			alert("Please Enter Amount");
			return false;

		}
		if (isNaN(document.getElementById("amount").value)) {
			alert("Invalid data format.\n\nOnly numbers are allowed.");
			document.tranform.amount.focus();
			return false;
		}
	} */
</script>

</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 85%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Employee Transaction Details</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" size="7" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" size="7" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content" style="height: 540px;">
								<center>

									<form action="DetailTranServlet?action=editTranValues1" method="post">

										<table id="customers" style="width: 100% !important" align="center">
											<tr>
												<th>Employee Transaction Details</th>
											</tr>
<%
//System.out.println(request.getParameter("proj")==null?"":request.getParameter("proj"));


%>
											<tr>
												<td  align="center">
												<table style="width: 100% !important" >
												<tr>
												
												
												<td style="width: 40% ">For Month: <input name="date1" id="date1"
													 type="text" value="<%=date1%>" readonly="readonly" 
													  onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							                          onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
						                            	src="images/cal.gif" align="absmiddle"
							                            style="vertical-align: middle; cursor: pointer;"
							                            onClick="javascript:NewCssCal('date1', 'ddmmmyyyy')" />
							                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                            <span id="sp1"><font size="+1"><%=date_disp%></font></span>
							                            </td>
												
												
												<td>Project :
												<input type="text" id="pp" name="pp" style="width: 80%;" autofocus onClick="this.select();" value="<%=request.getParameter("proj")==null?"":request.getParameter("proj").replaceAll(" and ", " & ")%>" title="Enter a character to view the available project list (E.g - %) ">
												 <input type="Button" value="Submit" onclick="getTranDetails()" />
												<%-- </td>
												</tr>
														<tr>

															<td>Project Code : <select
																name="prjCode" id="prjCode" onChange="getTranDetails()">
																	<option value="select" selected>Select</option>
																	<%
    																ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
																	EmpOffHandler ofh = new EmpOffHandler();
    						 										list=ofh.getprojectCode();
    						 										int p;
    						 										if(prjCode==""||prjCode==null){
    						 											p=0;
    						 										}else{
    						 										 p= Integer.parseInt(prjCode);
    						 										}
    						 										for(EmpOffBean lkb :list)
    						 				 						{    						 											
    						 											if (p == lkb.getPrj_srno())  {
    						 				 						%>
    						 				      						<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getSite_name()%>" selected><%=lkb.getPrj_name()%></option>  
    						 				     					<%
    						 				     					} else {
    						 				     					%>
    						 				     						<option value="<%=lkb.getPrj_srno()%>"title="<%=lkb.getSite_name()%>"><%=lkb.getPrj_name()%></option>
    						 				     					<%}
    						 				 						}	%>
															</select>
																&nbsp;&nbsp;&nbsp;&nbsp; --%>
															<font size="+1">
															
															<%-- <span id="sp1"><%=date_disp%></span>
															
															<input type="hidden" id="date1" name="date1" value="<%=date1%>"> --%>
															</font>
															</tr> 

													</table>
													</td>
											</tr>

											<tr><td>
					<div  class="imptable" >
													<table >
														<tr  bgcolor="#2f747e"  >														
															<td style="width: 25px; text-align:center; color: white;"><font size="2"><b>Sr. No</b></font></td>
															<td style="width: 46px; text-align:center;  color: white;" ><font size="2"><b>Emp Code</b></font></td>
															<td style="width: 200px; text-align:center;  color: white;"><font size="2"><b>Emp Name</b></font></td>
															<td style="width: 65px; text-align:center;  color: white;"><font size="2"><b>Advance</b></font></td>
															<td style="width: 67px; text-align:center; color: white;"><font size="2"><b>Loan</b></font></td>
															<td style="width: 67px; text-align:center;  color: white;"><font size="2"><b>TDS</b></font></td>
															<td style="width: 67px; text-align:center;  color: white; "><font size="2"><b>Mob Ded</b></font></td>
															<td style="width: 65px; text-align:center;  color: white;"><font size="2"><b>Other Ded</b></font></td>
															<td style="width: 67px; text-align:center;  color: white;"><font size="2"><b>Earning amt1</b></font></td>
															<td style="width: 67px; text-align:center;  color: white;"><font size="2"><b>Earning amt2</b></font></td>
															<td style="width: 67px; text-align:center; color: white;"><font size="2"><b>P.L.</b></font></td> 
															<td style="width: 67px; text-align:center; color: white;"><font size="2"><b>Absent</b></font></td>
															<td style="width: 60px; text-align:center; color: white;"><font size="2"><b>Present Days</b></font></td>															
															
															
															
															
															
															
														</tr>
													</table>

											</div>
											</td>
											</tr>

											<tr>
												<td>
													<div   class="imptable" style="height: 400px;overflow-y:auto;width:102% ">
														<table>


															<%
															
																int index=0;
																int srno=1;
															//System.out.println("into display");
															//System.out.println("Emp num list="+projEmpNmlist.size());
																float advTotal = 0;
																 float loanTotal = 0;
																 float tdsTotal = 0;
																 float mobTotal = 0;
																 float othrTotal = 0;
																 float er1Total = 0;
																 float er2Total = 0;
																 float plTotal = 0;
																 float absntTotal = 0;
																 
																for (TranBean tbean : projEmpNmlist) {
																	
																	EmployeeBean ebean=new EmployeeBean();
																	ebean=emphdlr.getEmployeeInformation(Integer.toString(tbean.getEMPNO()));
																	joindate="";
																	leftdate="";
																	joindate=ebean.getDOJ();
																	leftdate=ebean.getDOL()==""||ebean.getDOL()==null?"31-Dec-2999":ebean.getDOL();
																	
																	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
																	String end="" ,start="";
																	 Date ddd0=new Date(ReportDAO.BOM(date1));
																		Date ddd1=new Date(ReportDAO.EOM(date1));
																		
																		Date ddd2=new Date(joindate);
																		Date ddd3=new Date(leftdate);
																	
																		if(ddd2.equals(ddd1)||ddd2.equals(ddd0)||(ddd2.after(ddd0) && ddd2.before(ddd1)))
																		{
																			System.out.println("joindate in same month");
																			
																			if(ddd2.equals(ddd1)||ddd2.equals(ddd0)||(ddd2.after(ddd0) && ddd2.before(ddd1)))
																			{
																			days=0;
																			
																			//for joining date in same month
																			Date d1 = null;
																			Date d2 = null;
																			d1 = format.parse(joindate);
																			d2 = format.parse(ReportDAO.EOM(date1));	
																			 
																			long diff = d2.getTime() - d1.getTime();
																			long diffDays = diff / (24 * 60 * 60 * 1000);
																			days=diffDays;
																			days+=1;
																			}
																			if(ddd3.equals(ddd1)||ddd3.equals(ddd0) ||(ddd3.after(ddd0) && ddd3.before(ddd1)) )
																			{
																				
																				days=0;
																				//for leftdate in same month
																					Date d1 = null;
																					Date d2 = null;
																					d1 = format.parse(joindate);
																					d2 = format.parse(leftdate);	
																					 
																					long diff = d2.getTime() - d1.getTime();
																					long diffDays = diff / (24 * 60 * 60 * 1000);
																					days=diffDays;
																					days+=1;
																			 
																			}
																		}
																		else if(ddd3.equals(ddd1)||ddd3.equals(ddd0) ||(ddd3.after(ddd0) && ddd3.before(ddd1)) )
																		{
																		 System.out.println("left in same month");
																			if(ddd3.equals(ddd1)||ddd3.equals(ddd0) ||(ddd3.after(ddd0) && ddd3.before(ddd1)) )
																			{ 
																		 days=0;
																		//for leftdate in same month
																			Date d1 = null;
																			Date d2 = null;
																			d1 = format.parse(ReportDAO.BOM(leftdate));
																			d2 = format.parse(leftdate);	
																			 
																			long diff = d2.getTime() - d1.getTime();
																			long diffDays = diff / (24 * 60 * 60 * 1000);
																			days=diffDays;
																			days+=1;
																			}	
																		 if(ddd2.equals(ddd1)||ddd2.equals(ddd0)||(ddd2.after(ddd0) && ddd2.before(ddd1)))
																			{
																			days=0;
																			
																			Date d1 = null;
																			Date d2 = null;
																			d1 = format.parse(joindate);
																			d2 = format.parse(leftdate);	
																			 
																			long diff = d2.getTime() - d1.getTime();
																			long diffDays = diff / (24 * 60 * 60 * 1000);
																			days=diffDays;
																			days+=1;
																			}	
																		}
																	 
																		else if((ddd2.equals(ddd1)||ddd2.equals(ddd0)||(ddd2.after(ddd0) && ddd2.before(ddd1)))
																			&&  (ddd3.equals(ddd1)||ddd3.equals(ddd0) ||(ddd3.after(ddd0) && ddd3.before(ddd1))))
																			 
																		{
																		 days=0;
																		 System.out.println("both in same month");
																			
																		//for joining and leftdate in same month
																		 	Date d1 = null;
																			Date d2 = null;
																			d1 = format.parse(joindate);
																			d2 = format.parse(leftdate);	
																			long diff = d2.getTime() - d1.getTime();
																			long diffDays = diff / (24 * 60 * 60 * 1000);
																			days=diffDays;
																			days+=1;
																			 
																		}
																		else
																		{
																			days=Calculate.getDays(date1);	
																		}
																	 
																	 AdvanceHandler AH=new AdvanceHandler();
																	 LoanHandler LH=new LoanHandler();
																	LMH lmh=new LMH();
																	empName = obj.getempName(tbean.getEMPNO());
																	String str = tbean.getEMPNO()+":"+tbean.getTRNCD();
																	//trncd=tbean.getTRNCD();
															%>

															<tr>
																<td style="width: 25px;"><%=srno%></td>
																<td style="width: 46px; "><%=EmployeeHandler.getEmpcode(tbean.getEMPNO())%></td>
																<td style="width: 200px; "><%=empName%></td>
							<!-- from adv_pay_requested advancehandler --> 									
                											<%--	<td style="width: 67px;"><input type="text" size="7" id="advValue<%=index %>" name="advValue" value="<%=(AH.getEmpAdvance(Integer.toString(tbean.getEMPNO()),date1))%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')"  onkeyup="calTotal('advValue')" onmouseup="calTotal('advValue')" onblur="calTotal('advValue')"></td>  --%> 
                												
                												  <td style="width: 67px;"><input type="text" size="7" id="advValue<%=index %>" name="advValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 225)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')"  onkeyup="calTotal('advValue')" onmouseup="calTotal('advValue')" onblur="calTotal('advValue')"></td>
                												<td style="width: 67px;"><input type="text" size="7" id="loanValue<%=index %>" name="loanValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 226)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('loanValue')" onmouseup="calTotal('loanValue')" onblur="calTotal('loanValue')"></td> 
                				<!-- from loan_details loanhandler--> 
                												<%-- <td style="width: 67px;"><input type="text" size="7" id="loanValue<%=index %>" name="loanValue" value="<%=(LH.getEmpLoan(Integer.toString(tbean.getEMPNO()), date1)).getMonthly_install()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('loanValue')" onmouseup="calTotal('loanValue')" onblur="calTotal('loanValue')"></td> --%>
                												<td style="width: 67px;"><input type="text" size="7" id="tdsValue<%=index %>" name="tdsValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 228)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('tdsValue')" onmouseup="calTotal('tdsValue')" onblur="calTotal('tdsValue')"></td>
                												<td style="width: 67px;"><input type="text" size="7" id="mobValue<%=index %>" name="mobValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 223)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('mobValue')" onmouseup="calTotal('mobValue')" onblur="calTotal('mobValue')"></td>
                												<td style="width: 65px;"><input type="text" size="7" id="othrValue<%=index %>" name="othrValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 212)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('othrValue')" onmouseup="calTotal('othrValue')" onblur="calTotal('othrValue')"></td>
                												<td style="width: 67px;"><input type="text" size="7" id="ea1Value<%=index %>" name="ea1Value" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 130)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('ea1Value')" onmouseup="calTotal('ea1Value')" onblur="calTotal('ea1Value')"></td>
                												<td style="width: 67px;"><input type="text" size="7" id="ea2Value<%=index %>" name="ea2Value" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 131)).getINP_AMT()%>" disabled="disabled" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('ea2Value')" onmouseup="calTotal('ea2Value')" onblur="calTotal('ea2Value')"></td>
                												<td style="width: 67px;"><input type="text" size="7" id="plValue<%=index %>" name="plValue" value="<%=lmh.getLeaveBal(tbean.getEMPNO(),date1)%>" disabled="disabled" onchange="getTotalDays(this.id)" onClick="this.select();" onkeypress="return inputLimiter(event,'Numbers')" onkeyup="calTotal('plValue')" onmouseup="calTotal('plValue')" onblur="calTotal('plValue')"></td> 
                												<td style="width: 67px;"><input type="text" size="7" id="absValue<%=index %>" name="absValue" value="<%=(trnh.getTranByEmpno1(tbean.getEMPNO(), 301)).getINP_AMT()%>" onchange="getTotalDays(this.id)" maxlength="3" onClick="this.select();"  onkeypress="return inputLimiter(event,'Numbers')"  onkeyup="calTotal('absValue')" disabled="disabled" onmouseup="calTotal('absValue')" onblur="calTotal('absValue')"></td>
                												<input type="hidden" id="presentdays<%=index %>" name="preValue" value="<%=days%>">
                												<td style="width: 60px;"><input type="text" size="7" id="totValue<%=index %>" name="totValue" value="<%=days -(trnh.getTranByEmpno1(tbean.getEMPNO(), 301)).getINP_AMT()%>" disabled="disabled"></td>
                												
																<%-- <td style="width: 60px;"><%=tbean.getTRNDT()==null?"       ":tbean.getTRNDT()%></td> --%>
																<%-- <td width="85" align="center"><input type="button" value="Edit"
																	onClick="editTranAmount('<%=str%>')" /></td> --%>
															</tr>
															<%
															index++;
															srno++;
																
															 advTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 225)).getINP_AMT();
															  loanTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 226)).getINP_AMT();
															  tdsTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 228)).getINP_AMT();
															  mobTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 223)).getINP_AMT();
															  othrTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 212)).getINP_AMT();
															  er1Total += (trnh.getTranByEmpno1(tbean.getEMPNO(), 130)).getINP_AMT();
															  er2Total += (trnh.getTranByEmpno1(tbean.getEMPNO(), 131)).getINP_AMT();
															  plTotal += lmh.getLeaveBal(tbean.getEMPNO(),date1);
															  absntTotal += (trnh.getTranByEmpno1(tbean.getEMPNO(), 301)).getINP_AMT();
																}
															
															%>
															
														</table>

													</div>

												</td>


											</tr>
											
											
											<tr>
												<td>
												<div  class="imptable" >
												<table>
												
												 <%
												 if(projEmpNmlist.size()>0)
												 {
												 %>
												 <tr>
												 					
												<input type="hidden" id="count" value="<%=index%>"/>			
												 <td align="right" style="width: 305px; background-color:#2f747e; color:#ffffff;">TOTAL :</td>
													 
												<td style="width: 67px;"><input type="text" size="7" id="advValueTotal" readonly="readonly" value="<%=advTotal %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="loanValueTotal" readonly="readonly" value="<%=loanTotal %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="tdsValueTotal" readonly="readonly" value="<%=tdsTotal %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="mobValueTotal" readonly="readonly" value="<%=mobTotal %>"></td>
												<td style="width: 65px;"><input type="text" size="7" id="othrValueTotal" readonly="readonly" value="<%=othrTotal %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="ea1ValueTotal" readonly="readonly" value="<%=er1Total %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="ea2ValueTotal" readonly="readonly" value="<%=er2Total %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="plValueTotal" readonly="readonly" value="<%=plTotal %>"></td>
												<td style="width: 67px;"><input type="text" size="7" id="absValueTotal" readonly="readonly" value="<%=absntTotal %>"></td>
												<td style="width: 60px;">&nbsp;</td>
												</tr>
												<% }
												 else
												 {
													 if(action.equalsIgnoreCase("getdetails"))
													 {
														 %>
														<center>
															<font color="red" size="4"> No records founds !</font>
															</center>
															
															 <%	
													 }
												 }
												 %>
												 </table>
												 </div>
												 </td>
											</tr>
											
											<tr style="height: 30px;">
												 <td align="center" bgcolor="#929292">
												 <%
												 if(projEmpNmlist.size()>0)
												 {
												 %>
												 <input type="button" id="edit"
													value="Edit" onClick="enableTextBoxes()" height="150" width="150" />
												<input type="submit" id="tranSave"	value="Save" onclick="return validate()"  disabled="disabled"/>
												<% }
												 
												 %>
												</td>
											</tr>
											
											<%-- 
											
								 
											 <tr><td align="center">
											 	<div id="addN" style="display: none">
										<%-- <form name="tranform" action="TransactionServlet?action=addtran" method="post">

											<input type="hidden" name="trancd"
												value="<%=session.getAttribute("trncd")%>">

											<table id="customers" width="">
												<tr>
													<th colspan="5">Add new Transaction</th>
												</tr>
												<tr>
													 <td>Employee NO</td>
													<td><input type="text" size="7"  name="EMPNO" id="EMPNO"
														onClick="showHide()" title="Enter Employee No"></td>
													<td>Amount</td>
													<td><input type="text" size="7"  name="" id="" value="1"/></td>
													<td><input type="text" size="7"  name="" id="" value="1"/></td>
													<td><input type="text" size="7"  name="" id="" value="1"/></td>
													<td><input type="text" size="7"  name="" id="" value="1"/></td>
													<td><input type="text" size="7"  name="" id="" value="1"/></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td>
													<td><input type="text" size="7"  name="" id="" /></td> 
													<td align="center"><input type="submit" value="Save" />
														<input type="button" value="Cancel" onClick="showdiv()" /></td>
												</tr>

											</table>

										</form> 
										</div>
										
										<div id="d1">
									
										<form action="TransactionServlet?action=edittran" method="post">
											<%
												if (action.equalsIgnoreCase("edit")) {
													for (TranBean tbean1 : listbyEMPNO) {
														int e = tbean1.getEMPNO();
														if (empno == e) {
											%>
											<table id="customers">
												<tr>
													<th colspan="6">Edit Employee Transaction</th>
												</tr>

												<tr>
													<td width="100">Employee Number</td>
													<td width="80"><%=tbean1.getEMPNO()%></td>
													<td width="80">TransactionCode</td>
													<td width="80"><%=tbean1.getTRNCD()%></td>

												</tr>
												<tr>
													<td width="80">Amount</td>
													<td width="80">
													<input type="text" name="amount" value="<%=tbean1.getINP_AMT()%>"></td>
													<td colspan="2">
													<input type="submit" value="Save" />
													<input type="button" value="Cancel" onClick="getcancel()" />
													</td>
												<tr>
												
									
												
												
											</table>

											<input type="hidden" name="empno" value="<%=tbean1.getEMPNO()%>"> 
											<input type="hidden" name="trncd" value="<%=tbean1.getTRNCD()%>">
											<%
												}
													}
												}
											%>
										</form>
									</div>
										
										
                                      </td></tr> --%>

									 
											
										</table>


									</form>
									<br>

									 
									<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">

								</center>
<%
}
catch(Exception e)
{
	e.printStackTrace();
	%>
	
	<script type="text/javascript">
	
	window.location.href="login.jsp?action=0";
	</script>
	
	<%
}
%>
							</div>
							<!--  end table-content  -->

							<div class="clear"></div>

						</div> <!--  end content-table-inner ............................................END  -->
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