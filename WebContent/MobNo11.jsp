<%@page import="payroll.Core.Calculate"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<%
LookupHandler lkph= new LookupHandler();
CodeMasterHandler CMH = new CodeMasterHandler();
ArrayList<CodeMasterBean> TRNCODE=new ArrayList<CodeMasterBean>();   
ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
ArrayList<TranBean>  listbyEMPNO=new  ArrayList<TranBean> ();
TRNCODE=CMH.getNoAutocalCDList();
TranHandler trnh1= new TranHandler();
int eno = (Integer)session.getAttribute("EMPNO");
EmpOffHandler eoffhdlr = new EmpOffHandler();
EmpAddrHandler addhdlr = new EmpAddrHandler();
EmpOffBean eoffbn = new EmpOffBean();
 
eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
//ArrayList<TranBean> tran = new ArrayList<TranBean>();
ArrayList<MobileBean> tran_sort = new ArrayList<MobileBean>();
ArrayList<MobileBean> tran_sort1 = new ArrayList<MobileBean>();

ArrayList<EmpAddressBean> addbn = new ArrayList<EmpAddressBean>();
//tran = trnh1.getTranDetail(223);


ArrayList<MobileBean> tran= new ArrayList<MobileBean>();
		
MobileAllowanceHandler mah=new MobileAllowanceHandler();
String sal_date=trnh1.getSalaryDate();
tran=mah.getMobileAllownceDetail(sal_date);
EmpAddressBean eaddbn = new EmpAddressBean();
String select=new String();
LeaveMasterHandler obj=new LeaveMasterHandler();
String empName;
String empMobile;

String sort_name=request.getParameter("sort_name")==null?"":request.getParameter("sort_name");
String sort_mobile=request.getParameter("sort_mobile")==null?"":request.getParameter("sort_mobile");

if(!sort_name.equals(""))
{
//System.out.println("in sort name"+sort_name);

for (MobileBean tbean : tran) {
	empName = (obj.getempName(tbean.getEmp_no()).toLowerCase());
	
	//System.out.println("in sort name...in for"+sort_name);
			
	if(empName .contains(sort_name.toLowerCase()))
	   {
		
		
		//System.out.println("in sort name***in if"+sort_name);
		
		tran_sort.add(tbean);
	
	
	    } 
	
		
}
if(!sort_name.equals(null))
{
tran=new ArrayList<MobileBean>();
tran=tran_sort;
}
}
else if(!sort_mobile.equals(""))
{
//System.out.println("in sort mobile"+sort_mobile);

for (MobileBean tbean : tran) {
	empMobile = (tbean.getMobile_no().toString());
	
	//System.out.println("in sort name...in for"+sort_mobile);
			
	if(empMobile .contains(sort_mobile.toLowerCase()))
	   {
		//System.out.println("in sort name***in if"+sort_mobile);
		
		tran_sort1.add(tbean);
	
	
	    } 
	
		
}
if(!sort_mobile.equals(null))
{
tran=new ArrayList<MobileBean>();
tran=tran_sort1;
}
}



session.setAttribute("emplist", tran);
int trncd=0;

String action = request.getParameter("action")==null?"":request.getParameter("action");
System.out.println("action is"+action);
TranHandler trnh= new TranHandler();
LookupHandler lkh=new LookupHandler();
ArrayList<Lookup> emplist=new ArrayList<Lookup>(); 
emplist=lkh.getSubLKP_DESC("ET");	// ET is Code for Employee Type
int trn=0;
int keys=0;
int empno1=0;
int empno=0;
int flag=-1;

try
{  
	
	try
	{
	flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
	}catch(Exception e)
	{
		System.out.println("no flag value"+flag);
	}
	if(action.equalsIgnoreCase("getdetails"))
	{
		
		String trncd1=request.getParameter("key");
		trn=Integer.parseInt(trncd1);
		select=request.getParameter("selected");
		session.setAttribute("trncd", trn);
		session.setAttribute("selectvalue", select);
		//ArrayList<TranBean> tranlist=new ArrayList<TranBean>();
	    	
		
		
		tranlist = trnh.getTranDetail(trn);
	    	session.setAttribute("list", tranlist);
	    	
	   } 
	
	
	if(action.equalsIgnoreCase("edit"))
    {
		
		System.out.println("in edit...."+action);
		String[] str1=request.getParameter("key1").split(":");
	    empno =Integer.parseInt(str1[0]);
		int trncd1=Integer.parseInt(str1[1]);
		System.out.println("empno is "+empno);
		 
		listbyEMPNO=trnh.getTranByEmpno(empno,223);
		//listbyEMPNO=(ArrayList)session.getAttribute("list");
		
    }
	if(session.getAttribute("trncd")!=null)
	{
		 keys=(Integer)session.getAttribute("trncd");
		 tranlist=trnh.getTranDetail(keys);
	}
	 
}
catch(Exception e)
{
	System.out.println("First Time Loading");
}
String date1=trnh.getSalaryDate();
String[] d={"","",""};
float jdays=0.0f;
String joindate;
float days=Calculate.getDays(date1);
d=date1.split("-");
String date_disp = d.length!=0?d[1]+"-"+d[2]:"";
%>



<script type="text/javascript">

$(document).keydown(function(e){
    if (e.keyCode == 40) { 
       
       var focusedElementId =   $("*:focus").attr("id");
      
       var numb = focusedElementId.match(/\d/g);
   	
   	numb = numb.join("");
   	var count=parseInt(numb)+1;
   	
    
   	document.getElementById('bill'+count).focus();
       
      
     
    }
});


/* function setFocus(id)
{
	
	
	var numb = id.match(/\d/g);
	
	numb = numb.join("");
	var count=parseInt(numb)+parseInt("1");
	
	document.getElementById("bill"+(count)).focus();
	
}
 */


function calculate(id)
{
	
var abs = parseFloat(document.getElementById(id).value);  //absent days

	var numb = id.match(/\d/g);
	
	numb = numb.join("");
	
	var all=document.getElementById("allow"+numb).value==""?"0.0":parseFloat(document.getElementById("allow"+numb).value); // allowance amt
	var abs =document.getElementById("bill"+numb).value==""?"0.0":parseFloat(document.getElementById("bill"+numb).value);  //bill amt
	
	if(document.getElementById("allow"+numb).value=="")
		{
		
		document.getElementById("allow"+numb).value='0.0';
		}
	
	if(document.getElementById("bill"+numb).value=="")
	{
	
	document.getElementById("bill"+numb).value='0.0';
	}
	
	if(((parseFloat(all))-(parseFloat(abs)))>0)
		{
		document.getElementById("tranValue"+numb).value='0.0';
		}
	else
		{
		document.getElementById("tranValue"+numb).value=((Math.abs((parseFloat(all))-(parseFloat(abs))))).toFixed(1);
		}
document.getElementById(id).focus();
	

	numb=0;
	abs=0;


	
}

   function getInfo()
   {
	   
	   if(document.getElementById("emn").value=="" && document.getElementById("emo").value=="" )
		{
		alert("Please enter proper details...!");
		}
	else
		{
		var s=document.getElementById("emn").value;
		var m=document.getElementById("emo").value;
		window.location.href='MobNo11.jsp?sort_name='+s+'&sort_mobile='+m;
		
		}
	   
	   
   }

  
	function getTranDetails() {
		var type = document.getElementById("trncd").value;
		var desc = document.getElementById("trncd");
		var selected = desc.options[desc.selectedIndex].text;
		//alert(selected);
		window.location.href = "empTranDetails.jsp?action=getdetails&key="+ type + "&selected=" + selected;
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
			alert("Tranaction Saved Successfully");

		}
		if (f == 2) {
			alert("Employee Already Exist");
		}
		if (f == 3) {
			alert("Record updated Successfully");
		}
	}

	function getcancel() {

		document.getElementById("d1").hidden = true;
	}
	
	function enableTextBoxes()
	{
		 var mobded = document.getElementsByName("tranValue");
		for(var i=0; i<mobded.length; i++)
		{
			document.getElementById(mobded[i].id).disabled=false;
		} 
		document.getElementById("tranSave").disabled=false;
	}
	

	function showdiv() {
		var e = document.getElementById("addN");
		if (e.style.display == 'none') {
			e.style.display = 'block';
		} else if (e.style.display == 'block') {
			e.style.display = 'none';

		}

	}

</script>
<%
	/* String pageName = "MobNo11.jsp";
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
 */

%>

</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Employee Mobile Transaction</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
								<h3>
(Enter values in Bill Amount Column...! Use DOWN-ARROW to move next  )</h3>
									<form action="MobileAllowanceServlet?action=insert_Mdeduction" method="post">

										<table id="customers" width="800" align="center">
											<tr>
												<th>Employee Mobile Transaction Details</th>
											</tr>
											<tr>
												<td>
												<table>
												<tr><td width="385" align="center" >Emp Name:<input type="text" id="emn" name="emn" style="width: 80%;" autofocus onClick="this.select();" ></td>
												
												<td width="385">Mob No:<input type="text" id="emo" name="emo" style="width: 80%;" autofocus onClick="this.select();" value=""></td>
												
												 <td align="center" bgcolor="#929292"><input type="Button" value="Submit" onclick="getInfo()" /></td>
												
												<td><%=date_disp%></td>
												</tr>
												</table>
												</td>												
											</tr>
											<tr>
												<td>
													<table width="930">
														<tr>
															<th width="114">Emp No.</th>
															<th width="202">Emp Name</th>
															<th width="166">Mobile No </th>
															<th width="166">Allowable Amount</th>
															<th width="166">Bill Amount</th>
															<th width="166">Deduction Amount</th>																													
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<div id="scrolling"
														style="height: 320px; overflow-y: scroll; max-width: 1000px; background-color: #FFFFFF;"
														align="center">
														<table width="930">
															<%
																int index=0;
																float total = 0;
																for (MobileBean tbean : tran) {
																	empName = obj.getempName(tbean.getEmp_no());
																	
															%>
															<tr align="center">
																<td width="148" ><input style="border: none;text-align: center;" type="text" style=text-align:center "emnp<%=++index %>" size="9" name="emnp" value="<%=tbean.getEmp_no()%>" readonly="readonly"></td>
																<td width="550" align="left"><%=empName%></td>
																<td width="160" align="center"> <input style="text-align: center;border: none;" type="text" id ="mob<%=index %>" name="mob" readonly="readonly" value="<%=tbean.getMobile_no()%>"></td>
																<td width="160"><input style="text-align: right;border: none;" type="text" id ="allow<%=index %>" name="allow" readonly="readonly" value="<%=tbean.getCharges()%>"> </td>
																<td width="160"><input style="text-align: right;" type="text" id ="bill<%=index %>" name="bill"  value="<%=tbean.getBillcharges()%>" autocomplete="off" onfocus="this.select()" onkeyup="calculate(this.id)" onblur="setFocus(this.id)"> </td>
																 <td width="160"><input style="text-align: right;border: none;" type="text" id="tranValue<%=index %>" name="tranValue" value="<%=Math.abs((tbean.getCharges()-tbean.getBillcharges()>=0)?0.0:(tbean.getCharges()-tbean.getBillcharges()))%>"	readonly="readonly"></td>
																
															</tr>
															<% total += tbean.getBillcharges()==0?0:tbean.getBillcharges();
															
															} %>
															
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<table width="918">
														<%-- <tr>
															<td width="3800" align="right" bgcolor="#2f747e"><font color="white">TOTAL :</font></td>
															<td width="220" align="center"><input type="text" align="right" value="<%=total%>"></td>																													
														</tr> --%>
														<tr><td align="center" bgcolor="#929292">
												<input type="submit" id="tranSave" value="Save" /></td></tr>
													</table>
												</td>
											</tr>								
										</table>
									</form>
									<br>
									<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">					
								</center>
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