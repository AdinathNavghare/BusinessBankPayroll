<%@page import="java.util.List"%>

<%@page import="com.ibm.icu.util.GregorianCalendar"%>
<%@page import="com.ibm.icu.util.Calendar"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>


<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
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

RoleDAO obj1=new RoleDAO();
String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));

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
int prjCode = 0;
try
{  
	
	try
	{
	flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
	}catch(Exception e)
	{
		System.out.println("no flag value"+flag);
		if( request.getParameter("prj")==null)
		{
		session.setAttribute("prjCode", "");
		}
	}
	if(action.equalsIgnoreCase("getdetails"))
	{
		
		
		 prjCode = Integer.parseInt(request.getParameter("prj"));
		 System.out.println("thr firssssssssssssssssst prjcode"+prjCode);
	    session.setAttribute("prjCode", prjCode);
	  
	    session.setAttribute("projEmpNolist", projEmpNolist);
	    int i=0;
	    
	  
	    ArrayList<TranBean> arl=new ArrayList<TranBean>();
	    for(TranBean tbn : projEmpNolist){
	    	TranBean trbn = new TranBean();
	    	
	    	projEmpNmlist.add(trbn);
	    	
	   
	    	
	    } session.setAttribute("prjCode", prjCode);
	//    System.out.println("the employee are for selected project code as:"+prjCode+ " "+projEmpNmlist.size());
	}

}
catch(Exception e)
{
	e.printStackTrace();
	System.out.println("First Time Loading");
}
%>

<%


int eno = (Integer)session.getAttribute("EMPNO");
int z=0;
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpOffBean eoffbn = new EmpOffBean();
ArrayList<TranBean> tran = new ArrayList<TranBean>();
int site_id=0;
String state="";
int h11=0;
boolean check=false;
String string[]=null;



if(roleId.equals("1"))
{
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
if(request.getParameter("prj")==null)
{
	
	
	
	state="edit";
	tran = eoffhdlr.getEmpList(Integer.toString(eoffbn.getPrj_srno()));
	prjCode = eoffbn.getPrj_srno();
	//System.out.println("project code by session"+prjCode);
}
else
{
	state="view";
	tran = eoffhdlr.getEmpList(request.getParameter("prj"));
	
	prjCode=Integer.parseInt(request.getParameter("prj")); 

	
}
}
else{
				eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
				
				
				if(request.getParameter("site_id")==null)
				{
					
					state="edit";
					tran = eoffhdlr.getEmpList(Integer.toString(eoffbn.getPrj_srno()));
					prjCode = eoffbn.getPrj_srno();
					//System.out.println("project code-----++++++++++++++++++++"+prjCode);
				}
				else
				{
					prjCode = eoffbn.getPrj_srno();
					state="view";
					tran = eoffhdlr.getEmpList(request.getParameter("site_id"));
					prjCode=Integer.parseInt(request.getParameter("site_id")); 
				}
				
	
}



session.setAttribute("Prj_Srno", prjCode);
session.setAttribute("emplist", tran);

String date1=request.getParameter("date");

String date2=request.getParameter("date2");

date2=request.getParameter("date")==null?request.getParameter("date2"):request.getParameter("date2");

date1=request.getParameter("date")==null?request.getParameter("date2"):request.getParameter("date");

ArrayList<String> holidays= new ArrayList<String>();
ArrayList<String> weekdays= new ArrayList<String>();

HolidayMasterHandler hmh = new HolidayMasterHandler();

String fromDate="";
String type=" ";

weekdays=hmh.getweekoff(date1,prjCode);

if(weekdays.size()==0)
{
	weekdays=hmh.getweekoff(date1); 
}


System.out.println(weekdays);
	holidays = hmh.getHoldmast(date1,prjCode);
		//System.out.println("HOLIDAY IS ON"+holidays);
		 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");


EmpAttendanceHandler EAH=new EmpAttendanceHandler();


ArrayList Emp_al=new ArrayList();
System.out.println(".......................................");
Emp_al=EAH.getEmpAttend(date1, prjCode, eno,state);
float days=Calculate.getDays(date1);
//

//System.out.println("111111111111111111111111111111111111111111111111111111111111111111111"+day);

emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type
String status="";
if(date1!=null)
{
 status=EAH.getAttendanceStatus(date1, prjCode);
}


try
{  
	
	try
	{
	flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
	}catch(Exception e)
	{
		System.out.println("no flag value"+flag);
	}
	
}
catch(Exception e)
{
	System.out.println("First Time Loading");
}

 
%>

<script>
	jQuery(function() {
		$("#pp").autocomplete("projlist.jsp");
	});
	
	/* jQuery(function() {
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
	}); */
	
	
</script>
<script type="text/javascript">


$(document).ready(function () {
    $('#edit').click(function () {
    	
    	if($('input:text').attr("disabled"))
    		{
    	$('input:text').attr("disabled",false);
    	$('#edit').attr('value','Disable');
    		}
    	else
    		{
    		$('input:text').attr("disabled",true);
        	$('#edit').attr('value','Edit');
    		}
    });
    
    $('#tranSave').click(function () 
    		{
    	$('input:text').removeAttr("disabled");
    	
    		});
   
    $('input:text').focus(function () 
    		{
    	
    		$(this).css("background-color", "yellow");
    
    		});
    		
    $('input:text').blur(function () 
    		{
    	
    		$(this).css("background-color","");
    
    		});
});


jQuery(function() {
	$("#send").click(function(){
		var cnt=0;
		<% for(int j=0;j<Emp_al.size();j++)
		{
			
			
			ArrayList<TranBean> Emp_bean=(ArrayList<TranBean>)Emp_al.get(j);
			for(int c=0;c<Emp_bean.size();c++)
			{
				TranBean ab=new TranBean();
				ab=Emp_bean.get(c);
			%>
			if(document.getElementById("_<%=ab.getEMPNO()%>").value=="" || document.getElementById("_<%=ab.getEMPNO()%>").value==" ")
				{
				cnt++;
				}
			
			<%
				
			}	
			} %>
			
			
		if(cnt<0)
			{
			alert("Blank attendance can't be Send for Approval !\n\n\nPlease Fill some Value!");
			}
			else
			{
			var date2 = document.getElementById("adddate").value;
			
			var r = confirm("Are you sure to send for Approval ?");
			if (r == true) {
				window.location.href="EmpAttendServlet?action=approval&date="+date2;
			} 
			
			}
	});
});


function getTranDetails() {
	
	var date2=document.getElementById("adddate").value;
	var flag=true;
	if(date2=="")
		{
		alert("Please select Date");
		flag= false;
		}
	
		var proj=document.getElementById("pp").value;
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
			
				if(prjCode == ""){
					
				}
			
			else{
				
		proj=proj.replace(/ & /g," and ");
				window.location.href = "EmpPresentSeat.jsp?action=getdetails&prj="+prjCode+"&proj="+proj+"&date2="+date2,"_self";
				
			}
				}
			}
			
		}

function checkFlag() {
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Record updated Successfully");

		}
		else if (f == 2) {
			alert("Into servlet Else");
		}
		else if (f == 3) {
			alert("Something goes wronge");
		}
		else if (f == 4) {
			alert("Successfully Send for Approval!");
		}
		else if (f == 5) {
			alert("Rejected !Please check the data and Resend for Approval!");
		}
		
		document.getElementById("div2").style.display='none';
	}

function fn(id1,id2)
{

document.getElementById(id1).style.display='none';
document.getElementById(id2).style.display='block';

}

function approve_att(prj,dt){

var r = confirm("Are you sure to send for Approval ?");
if (r == true) 

window.location.href="EmpAttendServlet?action=approved&prjCode="+prj+"&date="+dt,"_self";

}

function reject_att(prj,dt){
var r = confirm("Are you sure to Reject ?");
if (r == true) 
window.location.href="EmpAttendServlet?action=reject&prj="+prj+"&date="+dt,"_self";

}

function inputLimiter(e,allow) {
		  var AllowableCharacters = '';
		  if (allow == 'Numbers'){AllowableCharacters='PpAaHh';}
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
	
	function check()
	{
		var date1 = document.getElementById("date").value;
		if(date1=="")
			{
			alert("Please select date !");
			}
		else
			{
			window.location.href="EmpPresentSeat.jsp?date="+date1,"_self";
			}
		
	}
	/* function act()
	{
		var date = document.getElementById("date").value;
		
		var r = confirm("Are you sure to send for Approval ?");
		if (r == true) {
			window.location.href="EmpAttendServlet?action=approval&date="+date;
		} 
		
	
	} */
	
	
	function attd()
	{
		window.location.href="attendanceMain.jsp?status=rejected","_self";
	}
	function appr()
	{
		window.location.href="approveAttendance.jsp?status=all","_self";
	}
	
	
	
	
</script>
<script type="text/javascript">
function method(){
	
	<%for(int i=0;i<=(Emp_al.size()*3); i++){ %>
	if(document.getElementById(<%=i%>).value=="")

	document.getElementById(<%=i%>).value="P";
<%}%>}
</script>
</head>
<body onLoad="checkFlag()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employees Attendance </h1>
	</div>
	<!-- end page-heading -->

	<table id="content-table" style="width: 100%;">
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

									<form action="EmpAttendServlet?action=insert"method="post" onsubmit="validate()">

										
												

													<table id="customers" style="width: 95%;">
													<tr>
													<th colspan="3">Employee Present Days Details</th>
													</tr>
														<tr>
																<td>Project Name : 
															<%if(roleId.equals("1"))
																{ if(request.getParameter("prj")==null)
																	{
																%>
																<%=eoffbn.getPrj_name()%>
																<%
															}else
															{
																ProjectListDAO pl=new ProjectListDAO();
																ProjectBean pb=new ProjectBean();
																pb=pl.getProjectInfo(Integer.parseInt(request.getParameter("prj")));
																
																
																%>
																<%=pb.getSite_Name() %>
																<%
															}
																} else{
																	ProjectListDAO pl=new ProjectListDAO();
																	ProjectBean pb=new ProjectBean();
																	pb=pl.getProjectInfo(Integer.parseInt(request.getParameter("site_id")));
																	
																	%>
																	<%=pb.getSite_Name() %>
																	<%
																}%>
																
																
																
															<%if(roleId.equals("1"))
									{%>
									<td>Select Date :
							
								<input name="adddate" size="20" id="adddate" value="<%=date1 %>" disabled="disabled" type="text">&nbsp;
									<img src="images/cal.gif" align="middle"	style="vertical-align: middle; cursor: pointer;"	onClick="javascript:NewCssCal('adddate', 'ddmmmyyyy')" />
							
								 </td>
								<%}
								else{
								 %>
																<td> Date :
																<input name="adddate" size="20" id="adddate" value="<%=date1==null?"":date1%>" disabled="disabled" readonly="readonly" type="text" onchange="check()">&nbsp;
															
																</td>
														<%} %>			
															<td>
									
															
															<%if(status.equalsIgnoreCase("saved") || status.equalsIgnoreCase("rejected"))
																{
																
																if(request.getParameter("prj")==null)
																{
																if(roleId.equals("1")){
																%>
																 
																 <input type="button" value="Edit" id="edit" height="150" width="150" /> 
                                                             <%} %>
																<input type="submit" id="tranSave" value="Save"/>
																<input type="button" id="send" value="Send for Approval"/>
																
															<%
																}
																else if(request.getParameter("prj")!=null)
																{
																
																	if(roleId.equals("1")){
																%>
																 
																 <input type="button" value="Edit" id="edit" height="150" width="150" /> 
                                                             <%} %>
																 
																<input type="submit" id="tranSave" value="Save"/>
																<input type="button" id="send" value="Send for Approval"/>
															<%
																}
																}
															else if(status.equalsIgnoreCase("pending")){
																
																
																
																
															if(request.getParameter("prj")!= null)
																{ 
																
																%>
																
																
																<input type="button" value="Edit" id="edit" height="150" width="150" /> 
																<input type="submit" id="tranSave" value="Save"/>			
																<input type="hidden" name="st" value='pending'>													
																<input type="button" id="" value="Approved" onclick="approve_att('<%=prjCode%>','<%=date1%>')">
																<input type="button" id="" value="Reject" onclick="reject_att('<%=prjCode%>','<%=date1%>')">
																
																<%}
															
															else if(request.getParameter("prj")== null)
															{ 
																
														if(roleId.equals("1")){	
														%>
															<input type="button" value="Edit" id="edit" height="150" width="150" /> 
																<input type="submit" id="tranSave" value="Save"/>			
																<input type="hidden" name="st" value='pending'>													
																<input type="button" id="" value="Approved" onclick="approve_att('<%=prjCode%>','<%=date1%>')">
																<input type="button" id="" value="Reject" onclick="reject_att('<%=prjCode%>','<%=date1%>')">
															
															<%}else{
														%>
														Already Send for Approval
														<%	
															
															}}
																else
																{%>
																Already Send for Approval
																<%	
																}
															}
															if(status.equalsIgnoreCase("approved"))
															{
																%>
																<!-- <input	type="button" value="Edit" id="edit" height="150" width="150" />
																<input type="submit" id="tranSave" value="Save"/> -->
																Successfully Approved
																<%	
															}
					
			 if(request.getParameter("prj")!=null)
			{
			%>
				
				<input type="Button" value="Back" onclick="appr()">
			<%}else
			{
				%>
				
				<input type="Button" value="Back" onclick="attd()">
			<%
			}
			
			%>
															 </td>
														</tr>
														
												<%if(roleId.equals("1"))
												{%>
												<tr>
												<td colspan="3">Project :
												<input type="text" id="pp" name="pp" style="width: 80%;" autofocus onClick="this.select();" value="<%=request.getParameter("proj")==null?"":request.getParameter("proj").replaceAll(" and ", " & ")%>" title="Enter a character to view the available project list (E.g - %) ">
												 <input type="Button" value="Submit" onclick="getTranDetails()" />
												 </tr>
												
											<%}
											%>
												</table>
													
												
													
													<center>
												<br>
												<h3>DAYS-
												<input type="button" onclick="fn('div2','div1')" value="1-15" title="Click on the button to view Result of date 1 to 15 !"/>
												<input type="button" onclick="fn('div1','div2')" value="16-above" title="Click on the button to view Result of date 16 to above  !"/>
												</h3>
										
												<div style="height: 400px; width: 1180px;">
												
												<%
												int s=0,e=0,s1=0,e1=0;
												for(int start=1;start<=2;start++)
												{
												
													
													
													if(start==1)
													{
														s=1;e=15;
														
													}
													if(start==2)
													{
														s=16;e=(int)days;
													}
													
												%>
												<center>
											<div id="div<%=start%>" align="center" style=" height: 390px;width: 1150px;">
												<%if(Emp_al.size()<9){%>
												<div align="center" class="imptable" style="overflow:hidden; width: 102%;">
												<% }else{%>
												<div align="center" class="imptable" style="overflow:hidden; width: 100.5%;">
												<% }%>
												<table  style="width: 90%"; >
												
												<tr bgcolor="#2f747e" style="height: 35px;">
															
																<td style="width: 6%;" > <input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:white; background-color: #2f747e ;text-align: center;"  name="SR.No." value="SR.No" ></td>
																<td style="width: 8%;" > <input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:white; background-color: #2f747e ;text-align: center;"  name="Emp Code" value="Emp Code" ></td>
																<td style="width: 22%;" > <input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:white; background-color: #2f747e ;text-align: center;"  name="Emp name" value="Employee Name" ></td>
																<%
																for(int i=s;i<=e;i++)
																{
																%>
															 	<th style="width: 4%;"> <input type="text" size="4" maxlength="2"  disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 80%;  border:none;color:white; background-color: #2f747e ;text-align: center;"  name="SR.No." value= <%=i<=9?"&nbsp;"+i:i%> ></th> 
																<%
																}
																
															%>
														</tr> 
														</table>
													</div>
												
												<div  align="center" class="imptable" style="overflow-y:auto; height: 330px;width: 102%;">
														
												<table  style="width: 90%;"  >
												
														<%
																int srno=1;
																int index = 0;
															for(int j=0;j<Emp_al.size();j++)
															{
																
																
																ArrayList<TranBean> Emp_bean=(ArrayList<TranBean>)Emp_al.get(j);
																%>
																<tr style="height: 35px;">
																<%
																if(start==1)
																{
																	s1=0;e1=14;
																}
																if(start==2)
																{
																	s1=15;e1=(int)days-1;
																}
																

																for(int c=s1;c<=e1;c++)
																{
																	check=false;
																	for(int l=0;l<holidays.size();l++ )
																	{
																	string=(holidays.get(l)).split("-");
																	h11=Integer.parseInt(string[0]);
																	if(h11==c+1)
																		check=true;
																	}
																	
																	TranBean ab= new TranBean();
																	
																	//Attend_bean ab=new Attend_bean();
																	ab=Emp_bean.get(c);
															
																	if( c==0||c==15)
																	{
																		empName = obj.getempName(ab.getEMPNO());
																	
																		%>
																		
																		 <td style="width: 6%;"><input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:black; background-color: white ;text-align: center;"  value="<%=srno%>" ></td>
																		<td style="width: 8%;"><input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:black; background-color: white ;text-align: center;" value="<%=EmployeeHandler.getEmpcode(ab.getEMPNO())%>" ></td>
																		<td style="width: 22%;" ><input type="text"   disabled="disabled"  onclick="this.click()" readonly="readonly" style="width: 100%;  border:none;color:black; background-color: white ;text-align: left;"  value="<%=empName%>"></td> 
																		
																		<%
																		
																	}
																	%>
																	
																	<%String DATE_FORMAT = "yyyy MM dd";
    																SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    																Calendar c1 = Calendar.getInstance(); // today
    																int d1=Integer.parseInt(date1.substring(0,2)),d2=d1-1,d3=d2-1;   // according to the selected date to admin
    																
    																//System.out.println("Today is " + sdf.format(c1.getTime()));
    																
    																//int d1=c1.getTime().getDate(),d2=d1-1,d3=d2-1; // if according to date attendance to admin also
    																
    																
    																//System.out.println("Welcome11111111111112222222222222222211111111111111");				
    																String years=date1.substring(7,11);
    																	
    																	int year=Integer.parseInt(years);
    																	String months=date1.substring(3,6);
    																	int month=Integer.parseInt(years);
    																	
    																	 int s11=0; int s22=0; int s33=0;  int s44=0;   int s55=0;
    	    															 int s66=0; int s77=0; int s88=0;  int s99=0; int s00=0;
    																	
    																	//	System.out.println(year);
    																	
    																	
    															
    																
    																	// auto generated sundays code starts here
    																	if(months.equals("Jan"))
    																		month=0;
    																	else if(months.equals("Feb"))
    																		month=1;
    																	else if(months.equals("Mar"))
    																		month=2;
    																	else if(months.equals("Apr"))
    																		month=3;
    																	else if(months.equals("May"))
    																		month=4;
    																	else if(months.equals("Jun"))
    																		month=5;
    																	else if(months.equals("Jul"))
    																		month=6;
    																	else if(months.equals("Aug"))
    																		month=7;
    																	else if(months.equals("Sep"))
    																		month=8;
    																	else if(months.equals("Oct"))
    																		month=9;
    																	else if(months.equals("Nov"))
    																		month=10;
    																	else
    																		month=11; 

    																//	System.out.println("MOnth"+month); 
    																	// year = Calendar.getInstance().get(Calendar.YEAR);
    																   //  month =Calendar.getInstance().get(Calendar.MONTH);   // current month
    															//put the month u want
    															     Calendar cal = new GregorianCalendar(year, month, 1);
    															    List<Integer> Sundays = new ArrayList<Integer>();
    															    List<Integer> otherDays = new ArrayList<Integer>();
    															    List<Integer> abc = new ArrayList<Integer>();
    															    do {
    															        int day = cal.get(Calendar.DAY_OF_WEEK);
    															        int dayInMonth = (cal.get(Calendar.DAY_OF_MONTH));
    															        if ( day == Calendar.SUNDAY) {
    															            Sundays.add(dayInMonth);
    															         //  System.out.println("SUNDAY"+dayInMonth);
    															            abc.add(dayInMonth);
    															        } else {
    															            otherDays.add(dayInMonth);
    															           // System.out.println(Calendar.DATE+"	"+dayInMonth);
    															           
    															        }
    															        cal.add(Calendar.DAY_OF_YEAR, 1);
    															    }  while (cal.get(Calendar.MONTH) == month);
    															
    															  
    															    if(abc.size()==5)
    															   {
    																   s11=abc.get(0); s22=abc.get(1); s33=abc.get(2);s44=abc.get(3);s55=abc.get(4);
    															   }
    															   
    															   if(abc.size()==4)
    															   {
    																   s11=abc.get(0); s22=abc.get(1); s33=abc.get(2);s44=abc.get(3);
    															   }

    															   if(abc.size()==0){}
    															   // auto generated sundays code ends here...........!!!!!!!!!!!!
    															    
    															    
    															   /* if(weekdays.size()==10)
    			    													
													    		    {
	    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
	    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
														    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
														    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
														    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
														    		    s66=Integer.parseInt(weekdays.get(5).substring(0,2));
	    															    s77=Integer.parseInt(weekdays.get(6).substring(0,2));
														    		    s88=Integer.parseInt(weekdays.get(7).substring(0,2));
														    		    s99=Integer.parseInt(weekdays.get(8).substring(0,2));
														    		    s00=Integer.parseInt(weekdays.get(9).substring(0,2));
												    	  		    }	
    															    else   if(weekdays.size()==9)
    			    													
													    		    {
	    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
	    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
														    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
														    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
														    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
														    		    s66=Integer.parseInt(weekdays.get(5).substring(0,2));
	    															    s77=Integer.parseInt(weekdays.get(6).substring(0,2));
														    		    s88=Integer.parseInt(weekdays.get(7).substring(0,2));
														    		    s99=Integer.parseInt(weekdays.get(8).substring(0,2));
												    	  		    }	
    															    else    if(weekdays.size()==8)
    			    													
													    		    {
	    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
	    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
														    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
														    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
														    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
														    		    s66=Integer.parseInt(weekdays.get(5).substring(0,2));
	    															    s77=Integer.parseInt(weekdays.get(6).substring(0,2));
														    		    s88=Integer.parseInt(weekdays.get(7).substring(0,2));
														    		  
												    	  		    }	
    															    else   if(weekdays.size()==7)
    			    													
													    		    {
	    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
	    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
														    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
														    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
														    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
														    		    s66=Integer.parseInt(weekdays.get(5).substring(0,2));
	    															    s77=Integer.parseInt(weekdays.get(6).substring(0,2));
														    		   
												    	  		    }	
    															    else  if(weekdays.size()==6)
    			    													
													    		    {
	    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
	    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
														    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
														    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
														    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
														    		    s66=Integer.parseInt(weekdays.get(5).substring(0,2));
	    															   
												    	  		    }	
    															 				   else   if(weekdays.size()==5)
    													
    															    		    {
    			    															    s11=Integer.parseInt(weekdays.get(0).substring(0,2));
    			    															    s22=Integer.parseInt(weekdays.get(1).substring(0,2));
    																    		    s33=Integer.parseInt(weekdays.get(2).substring(0,2));
    																    		    s44=Integer.parseInt(weekdays.get(3).substring(0,2));
    																    		    s55=Integer.parseInt(weekdays.get(4).substring(0,2));
															    	  		    }	
    															    		    else if(weekdays.size()==4)
    															    		    {
    															    		    	 s11=Integer.parseInt(weekdays.get(0).substring(0,2));
    																	    		 s22=Integer.parseInt(weekdays.get(1).substring(0,2));
    																	    		 s33=Integer.parseInt(weekdays.get(2).substring(0,2));
    																	    		 s44=Integer.parseInt(weekdays.get(3).substring(0,2));
    																	        }
    															    		    else if(weekdays.size()==3)
    															    		    {
    															    		    	 s11=Integer.parseInt(weekdays.get(0).substring(0,2));
    															    		    	 s22=Integer.parseInt(weekdays.get(1).substring(0,2));
    																	    		 s33=Integer.parseInt(weekdays.get(2).substring(0,2));
    															    		    } 
    															    		    else if(weekdays.size()==2)
    															    		    {
    															    		    	 s11=Integer.parseInt(weekdays.get(0).substring(0,2));
    																	    		 s22=Integer.parseInt(weekdays.get(1).substring(0,2));
    																	        } 
    															    		    
    															    		    else if(weekdays.size()==1)
    															    		    {
    															    		    	  s11=Integer.parseInt(weekdays.get(0).substring(0,2));
    															     		    }   
    															    		    
    															    		    else if(weekdays.size()==0)
    															    		    	{
    															    		    	s11=0;
    															    		    	} */
    															    		    	
 
																	if(c+1==d1||c+1==d2||c+1==d3 )
																	{
																		if(c+1==s11||c+1==s22||c+1==s33||c+1==s44||c+1==s55||c+1==s66||c+1==s77||c+1==s88||c+1==s99||c+1==s00)
																		{%>	
																		<td style="width: 4%;" align="center">
																		<input type="text" size="4" name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="2" value="WO"
																	 	disabled="disabled" onfocus="this.select()" title="Date= <%=c+1%>" style="width: 80%;color:black; text-align: center;text-transform: uppercase;"  onkeypress="return inputLimiter(event,'Numbers')">
																		</td>
																		<%
																		
																	}
																		else{
												
																			if(check)
																			
																			{
																				%>	
																				<td style="width: 4%;" align="center">
																				<input type="text" size="4" name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="2" value="HD" readonly="readonly"	
																			 	disabled="disabled" onfocus="this.select()" title="Date= <%=c+1%>" style="width: 80%;color:black; text-align: center;text-transform: uppercase;" onkeypress="return inputLimiter(event,'Numbers')">
																				</td>
																				<%
																			}
																			else{
																		%>
																
																		<td style="width: 4%;" style="width: 4%;" align="center">
																		
																		<input type="text" size="4" id="<%=z++%>" name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="1" value="<%=ab.getVal()==null?"P":ab.getVal()%>"
																	  onfocus="this.select()" title="Date=   <%=c+1%>" style="width: 80%;color:black; text-align: center;text-transform: uppercase;" onkeypress="return inputLimiter(event,'Numbers')">
																		</td>
																	
																		<%
																			}
																		}
																	}
																	else
																	{
																		if(c+1==s11||c+1==s22||c+1==s33||c+1==s44||c+1==s55||c+1==s66||c+1==s77||c+1==s88||c+1==s99||c+1==s00)
																		{%>	
																		<td style="width: 4%;" align="center">
																		<input type="text" size="4" name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="2" value="WO"
																	 readonly="readonly"	 disabled="disabled" onfocus="this.select()" title="Date= <%=c+1%>" style="width: 80%;color:black; text-align: center;text-transform: uppercase;" onkeypress="return inputLimiter(event,'Numbers')">
																		</td>
																		<%
																		
																	}
																		else{
																			if(check)
																			{
																				%>	
																				<td style="width: 4%;" align="center">
																				<input type="text" size="4" name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="2" value="HD"
																			 readonly="readonly"		disabled="disabled" onfocus="this.select()" title="Date= <%=c+1%>" style="width: 80%;color:black;text-align: center;text-transform: uppercase;" onkeypress="return inputLimiter(event,'Numbers')">
																				</td>
																				<%
																			}
																			else{
																		%>
																		
																		<td style="width: 4%;" align="center">
																		<input type="text" size="4"  name="_<%=ab.getEMPNO()%>" id="_<%=ab.getEMPNO()%>" maxlength="1" value="<%=ab.getVal()==null?"P":ab.getVal()%>"
																	 disabled="disabled" onfocus="this.select()" title="Date=   <%=c+1%>" style="width: 80%;color:black;text-align: center;text-transform: uppercase;" onkeypress="return inputLimiter(event,'Numbers')">
																		</td>
																		
																		<%
																			}
																		}
																	} %>
																	<%
																}
																%>
																</tr>
															<%	
															srno++;
															}
															%>
															
														</table>
														
														</div>
														</div>
														
														</center>
														<%
												}
														%>
														
														</div>
														</center>
														<h3>
													P:Present&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													A:Absent&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													H:Half Day&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													WO:Week Off&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													HD:Holiday&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</h3>
														
									  </form> 
										<input type="button" name="Button" value="Fill ALL Present" title="Click on the button to feel the present in all !" onclick="return method()"/> 						

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

<script type="text/javascript" >
	
	document.getElementById("div2").style.display='none';
	
	
	</script>
</body>
</html>
