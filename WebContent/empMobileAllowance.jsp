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
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script>
	jQuery(function() {
		$("#emp").autocomplete("list.jsp");
	});
	
	
</script>

<%
String action=request.getParameter("action")==""||request.getParameter("action")==null?"":request.getParameter("action");
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
LeaveMasterHandler obj=new LeaveMasterHandler();

MobileBean editmb=new MobileBean();
if(session.getAttribute("editmb")==null||session.getAttribute("editmb")=="")
{
	editmb=new MobileBean();
	
	editmb.setEmp_no(0);
	editmb.setMobile_no("");
	editmb.setConnection_type(0);
	editmb.setEff_date("");
	editmb.setPrj_srno(0);
	editmb.setService_provider(0);
	editmb.setCharges(000);
}
else
{
editmb=(MobileBean)session.getAttribute("editmb");
}
LMH lmh=new LMH();
ProjectListDAO pl=new ProjectListDAO();
ProjectBean pb=new ProjectBean();
ProjectBean editpb=new ProjectBean();

ArrayList<MobileBean> list= new ArrayList<MobileBean>();
MobileAllowanceHandler mah=new MobileAllowanceHandler();
list=mah.getAllMobileAllowance();

String readonly="";
if(action.equalsIgnoreCase("edit"))
{
	readonly="readonly='readonly'";
	editpb=pl.getProjectInfo(editmb.getPrj_srno());
}	



int flag=-1;
%>
<script type="text/javascript">

function validate()
{
var emp=document.getElementById("emp").value;
var mob=document.getElementById("mobile_no").value;
var date=document.getElementById("eff_date").value;
var chr=document.getElementById("charges").value;
	
if(date=="")
{
alert("Please select effective date!");
return false;
}
	if(emp=="")
		{
		alert("Please Enter Employee !");
		return false;
		}
	if(mob=="")
	{
	alert("Please Enter Mobile Number !");
	return false;
	}
	else
		{
		if(mob.length<10)
			{
			alert("Please Enter valid Mobile Number !");
			return false;
			}
		
		}
	
	if(chr=="")
	{
	alert("Please Enter Charges !");
	return false;
	}
	
}


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

function checkFlag() {
	
		var f = parseInt(document.getElementById("flag1").value);

		if (f == 1) {
			alert("Tranaction Saved Successfully");

		}
		if (f == 2) {
			alert("Error in insertion");
		}
		if (f == 3) {
			alert("Record updated Successfully");
		}
		
	}
	
	function getProject()
	{
		
		var empname=document.getElementById("emp").value;
		var date=document.getElementById("eff_date").value;
		var eno=empname.split(":");
		var empno=eno[2];
		
		if(empname.contains(":")) 
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
	document.getElementById("prj_span").innerHTML=xreq1.responseText;
	
	}
	}
	xreq1.open("post","project_info.jsp?empno="+empno+"&eff_date="+date,"true");
	xreq1.send();
	
		}
		
	}
	
	function edit(eno,eff_date,prj,mob)
	{
		
		var ans=confirm("Do you want to edit");
		if(ans)
			{
		window.location.href="MobileAllowanceServlet?action=edit&empno="+eno+"&date="+eff_date+"&prj="+prj+"&mob="+mob;
			}
		
	}
	
	
	
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
				<h1>Employee Mobile Allowance Details</h1>
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
							<div id="table-content" style="height: 480px;">
								<center>
								<div  style="height: 480px;">

									
									<%
											
											if(action.equalsIgnoreCase("edit"))
											{
												%>	 
												<form action="MobileAllowanceServlet?action=update" method="get" onsubmit="return validate()">			
															
															<input type="hidden" name="action" value="update">
															<%
											}
											else
											{
												%>	 
												<form action="MobileAllowanceServlet?action=insert" method="post" onsubmit="return validate()">			
												<%	
											}
											
											%>
									
									
									

										<table id="customers" style="height: 80%;" "center">
											<tr>
												<th>Employee Mobile Allowance Details</th>
											</tr>

											<tr>
												<td  align="center">
												<table id="customers" style="height:100%;width: 100%" >
												<tr class="alt">
												<th>Effective Date  : <font color="red"> &nbsp;&nbsp;*</font></th>
												<td>
												
												<input size="14" name="eff_date" id="eff_date" type="text" 
												value="<%=action.equalsIgnoreCase("edit")?(editmb.getEff_date()).equals(null)?"":editmb.getEff_date() :""%>"
												readonly="readonly" onfocus="javascript:NewCssCal('eff_date', 'ddmmmyyyy')">
												<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
												onClick="javascript:NewCssCal('eff_date', 'ddmmmyyyy')" />
													
												</td>
												<td>&nbsp;&nbsp;&nbsp;</td>
												<th> Emp Name : <font color="red"> &nbsp;&nbsp;*</font></th>
												<td>
												<input type="text" id="emp" name="emp" size="50" onClick="this.select();" title="Enter a character to view the available Employee's list (E.g - %) "
												value="<%=action.equalsIgnoreCase("edit")?obj.getempName(editmb.getEmp_no())+":"+EmployeeHandler.getEmpcode(editmb.getEmp_no())+":"+editmb.getEmp_no() :""%>" onchange="getProject()" onblur="getProject()" <%=readonly%> >
												 </td>
												 </tr>
												 <tr class="alt">
												 <th>Project :<font color="red"> &nbsp;&nbsp;*</font></th>
												 <td colspan="4">
												 <span id="prj_span"> 
												 <input style="border: none;" type="text" id="prj_no" name="prj_no" size="100"  value="<%=action.equalsIgnoreCase("edit")?editpb.getSite_Name()+":"+editpb.getProject_Code()+":"+editpb.getSite_ID():""%>"  readonly="readonly">
												  </span>
												 </td>
												 </tr>
												 <tr class="alt">
												 <th>Alloted Mobile No :<font color="red"> &nbsp;&nbsp;*</font></th>
												 <td><input type="text" id="mobile_no" name="mobile_no" maxlength="10"
												 value="<%=action.equalsIgnoreCase("edit")?editmb.getMobile_no().equals(null)?"":editmb.getMobile_no():""%>"
												  onkeypress="return inputLimiter(event,'Numbers')"></td>
												 <td>&nbsp;&nbsp;&nbsp;</td>
												 <th>Service Provider :</th>
												 <td>
												 <select name="service_provider" id="service_provider" >  
      											 <option value="0">Select</option>  
    											<%
  												  ArrayList<Lookup> result=new ArrayList<Lookup>();
    												LookupHandler lkhp= new LookupHandler();
    												result=lkhp.getSubLKP_DESC("SERVICE PROVIDER");
 													for(Lookup lkbean : result){
 														if(action.equalsIgnoreCase("edit"))
 														{
 															%>
 		      												 <option value="<%=lkbean.getLKP_SRNO()%>" <%=lkbean.getLKP_SRNO()==editmb.getService_provider()?"selected='selected'":""%>><%=lkbean.getLKP_DESC()%></option>   
 		     											 <%
 														}
 													%>
      												<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     											 <%}%>
     											 </select>
												 
												 </td>
												 </tr>
												 <tr class="alt">
												 <th>Connection Type :</th>
												 <td>
												 
												 <select name="connection_type" id="connection_type" >  
      											 <option value="0">Select</option>  
    											<%
  												  result=new ArrayList<Lookup>();
    												lkhp= new LookupHandler();
    												result=lkhp.getSubLKP_DESC("connection_type");
 													for(Lookup lkbean : result){
 														
 														if(action.equalsIgnoreCase("edit"))
 														{
 															%>
 		      												 <option value="<%=lkbean.getLKP_SRNO()%>" <%=lkbean.getLKP_SRNO()==editmb.getConnection_type()?"selected='selected'":""%> ><%=lkbean.getLKP_DESC()%></option>  
 		     											 <%
 														}
 													%>
      												<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
     											 <%}%>
     											 </select>
												 
												 
												 </td>
												 <td>&nbsp;&nbsp;&nbsp;</td>
												 <th> Allowance (Rs.):<font color="red"> &nbsp;&nbsp;*</font></th>
												 <td>
												 <input type="text" id="charges" name="charges" maxlength="10" 
												 value="<%=action.equalsIgnoreCase("edit")?editmb.getCharges():""%>"
												 onkeypress="return inputLimiter(event,'Numbers')">
												 
												 </td>
												 </tr>
												 <tr class="alt">
												  <th> Status :<font color="red"> &nbsp;&nbsp;*</font></th>
												  <td colspan="4">
												 
												 <select name="status" id="status" >  
      											 <option value="A">Active</option>
      											 <option value="N">Non-Active</option>  
												 </select>
												 </tr>
												 </table>
												 </td>
												 </tr>
												 </table>
												 
												 
											<br>
											<br>
												 
										<input type="submit" value="SUBMIT">
										<input type="reset" value="CLEAR">		  
									<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
									</form>
									<br>
									<br>
									
									<%try
									{ 
									
									if(list.size()>0)
									{
									
									%>
									<div id="ddd" style="height: 250px;overflow-y: scroll;">
									 <table id="customers">
									<tr class="alt">
									<th>SR.NO</th>
									<th>EMP CODE</th>
									<th>NAME</th>
									<th>PROJECT</th>
									<th>EFFECTIVE DATE</th>
									<th>MOBILE</th>
									<th>Allowance</th>
									<th>ACTION</th> 
									</tr>
									<%
									int srno=1;
										for(MobileBean mb:list)
										{
											pb=pl.getProjectInfo(mb.getPrj_srno());
									%>
									<tr class="alt">
									<td><%=srno++ %></td>
									<td><%=mb.getEmp_no()==0?"":EmployeeHandler.getEmpcode(mb.getEmp_no())%></td>
									<td><%=mb.getEmp_no()==0?"":obj.getempName(mb.getEmp_no())%></td>
									
									<td><%=mb.getPrj_srno()==0?"":pb.getSite_Name()%></td>
									<td><%=mb.getEff_date()==""?"":mb.getEff_date()%></td>
									<td><%=mb.getMobile_no().equalsIgnoreCase("")?"":mb.getMobile_no()%></td>
									<td><%=mb.getCharges()==0?"0.0":mb.getCharges()%></td>
									<td><input type="button" value='EDIT' onclick="edit('<%=mb.getEmp_no()%>','<%=mb.getEff_date()%>','<%=mb.getPrj_srno()%>','<%=mb.getMobile_no()%>')">   </td>
									</tr>
									<%
									
									}
									}
									
									}
									catch(Exception e)
									{
									
										
										%>
										
										<script type="text/javascript">
										
										document.getElementById("ddd").style.display='none';
										
										</script>
										<% 
									}
									
									%>
									</table> 
									</div>
									</div>
									
									
									
									
									

<%-- 								</center>
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
	 --%>						</div>
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
	