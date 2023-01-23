<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="payroll.Model.RelieveInfoBean"%>
<%@page import="payroll.DAO.RelieveInfoHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="payroll.Model.BranchBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Relieving Information</title>
<%
	//String EMPNO =  session.getAttribute("EMPNO").toString();
	String EMPNO = request.getParameter("EMPNO");
  	String url = "EmployeeServlet?action=Relinfo&";
	String act = "";
	EmployeeHandler emph = new EmployeeHandler();
	TranHandler trnh=new TranHandler();
	EmployeeBean empbn = emph.getEmployeeInformation(EMPNO);
	RelieveInfoHandler relh = new RelieveInfoHandler();
	RelieveInfoBean rbn = relh.getRelievInfo(EMPNO);
	String date1=ReportDAO.BOM(trnh.getSalaryDate());
	String paytrandt=emph.getpaytrandate(Integer.parseInt(EMPNO));

	if(rbn.getLEFT_DATE()==null){
		act="ADD";
		url=url+"act=ADD";
	}
	else
	{
		act="EDIT";
		url=url+"act=EDIT";
	}
	
%>

<script type="text/javascript">

function chk()
{
	
	var jnDate=document.getElementById('jnDate').value;
	 var lvDate=document.getElementById('lDate').value;
	 var sal_date=document.getElementById('sal_date').value;
	 
	 
	    jnDate = jnDate.replace(/-/g,"/");
	    lvDate = lvDate.replace(/-/g,"/");
	    sal_date=sal_date.replace(/-/g,"/");

	    var d1 = new Date(jnDate);

	    var d2 =new  Date(lvDate);
	    
	    var d3=new  Date(sal_date);
	    d3.setDate(d3.getDate()-1);

	      if (d2.getTime()!==null && d2.getTime() < d3.getTime()) 
	    {
	    	  
	    		alert("Leaving Date must be Greater or within  Salary Month  !!");
	    		document.getElementById('lDate').value="";
	    		document.getElementById('rDate').value="";
	    		document.getElementById('raDate').value="";
	    		 return false;
	    }
	
	
}

	function getClose()
	{
		window.close();
	}
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		if(flag == 1)
		{
			alert("Record Saved Successfully!");
			window.close();
		}
		else if(flag==2)
		{
			alert("Error in Modification of Record");
		}
		
	}
	 function validation()
	{
		//@niket
		var paytrandt = document.getElementById("paytrandt").value;
		var dt=paytrandt;
		var leftdt = document.getElementById("lDate").value;
		
		paytrandt = paytrandt.replace(/-/g,"/"); //@niket
		leftdt = leftdt.replace(/-/g,"/");
		  
		var d1 = new Date(paytrandt);	//@niket
		var d2 =new  Date(leftdt);//@niket 	
		 	
		if(d2.getTime()>d1.getTime())
		{
			alert("Left Date Should Not be Greater Than "+dt);
			return false;
		}
		else
		{
			return true;
		}
		
		/* if(document.getElementById("trcode").value =="")
		{
			alert("Please Enter Transaction Code ");
			document.getElementById("trcode").focus();
			return false;
		}
		if(document.getElementById("desc").value =="")
		{
			alert("Please Enter Description of Transaction ");
			document.getElementById("desc").focus();
			return false;
		}
		if(document.getElementById("sdesc").value =="")
		{
			alert("Please Enter Description of Transaction ");
			document.getElementById("sdesc").focus();
			return false;
		}
		if(document.getElementById("subsystem").value =="")
		{
			alert("Please Enter Sub System Code ");
			document.getElementById("subsystem").focus();
			return false;
		}
		if(document.getElementById("acno").value =="")
		{
			alert("Please Enter Account Number ");
			document.getElementById("acno").focus();
			return false;
		} */
		} 
</script>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<body onLoad="checkVal()">
<center>
<br/>
<form action="<%=url%>" method="post" onSubmit="return validation()">
          
    <table border="1" id="customers">
    <tr class="alt"><th colspan="4">Add Relieving Information</th></tr>
	<tr align="left" class="alt">
	<td width="130" >Employee Code</td>
	  			<td width="180" ><%=session.getAttribute("empcode")==null?"":session.getAttribute("empcode") %><input type="hidden" name="aempNo" id="aempNo" readonly="readonly" value="<%=(session.getAttribute("empno")==null?"":session.getAttribute("empno")) %>"></td>
				<td width="150" >Employee Name</td>
			<td width="150" ><%=(session.getAttribute("empname")==null?"":session.getAttribute("empname")) %></td></tr>
		<tr class="alt">
		  
		<td >Left Date </td>
		  <td ><input type="text" name="lDate" id="lDate" value="<%=rbn.getLEFT_DATE()==null?"":rbn.getLEFT_DATE()%>" onchange="chk()" readonly="readonly">
		  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('lDate', 'ddmmmyyyy')" />
		  </td>
	   
			<td>Branch </td>
			<td>
			<select name="branch" id="branch" >
					<option value="0">All</option>  
			    <%
			    	ArrayList<BranchBean> result = new ArrayList<BranchBean>();
			    	BranchDAO bdao = new BranchDAO();
			    	result = bdao.getBranchList();
			    	for(BranchBean brBean : result){
			    		
			
			    	%>
					<option value="<%=brBean.getBranchName()%>"><%=brBean.getBranchName()%></option>  
				     <%
					}
				     %>
				     </select></td></tr>
				    
				     <tr class="alt">
		  <td >Join Date </td>
		  <td ><input type="text" name="jnDate" id="jnDate" readonly="readonly"  value="<%=empbn.getDOJ()%>">
		 </td>
		  <td >Resignation Date </td>
		  <td ><input type="text" name="rDate" id="rDate" value="<%=rbn.getRESGN_DATE()==null?"":rbn.getRESGN_DATE()%>" onchange="chk()" readonly="readonly">
		  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('rDate', 'ddmmmyyyy')" />
		  </td></tr>
				     
				     
			
		<tr class="alt">
		<td >Resignation Accepted Date </td>
		  <td ><input type="text" name="raDate" id="raDate" value="<%=rbn.getRESGN_ACCTD_DATE()==null?"":rbn.getRESGN_ACCTD_DATE()%>" onchange="chk()" readonly="readonly">
		  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
				onClick="javascript:NewCssCal('raDate', 'ddmmmyyyy')" />
		  </td>
		  <td valign="top">Extra Reason </td>
		  <td align="left" colspan="3">
		  <textarea name="aReason" id="aReason" ><%=rbn.getREASON()==null?"":rbn.getREASON() %>
		  </textarea>
		  </td>
		</tr>

			
				<tr class="alt">
			<td>Reason</td>
				<td>
				
					 <select name="left_reason" id="left_reason" style="width:145px; height: 22px;">
					<option value="0" selected="selected">Select</option>
					<%
					
			    	ArrayList<Lookup> result1=new ArrayList<Lookup>();
					LookupHandler lkhp= new LookupHandler();
					result1=lkhp.getSubLKP_DESCREASON("LFT_PURP");
					String srno = rbn.getLEFT_REASON();
					for(Lookup lkbean : result1){
						System.out.println("result1:: "+result1.size());
						if(rbn.getLEFT_REASON()!=null)
						{
							
							if(Integer.parseInt(srno) == lkbean.getLKP_SRNO())
							{
						%>
								<option value="<%=lkbean.getLKP_SRNO()%>" title="<%=lkhp.getLookUp_desc(lkbean.getLKP_SRNO())%>" selected="selected"><%=lkbean.getLKP_DESC()%></option>
						<%
							}
							else
							{
						%>
						       <option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>
						<%
							}
					}
					else
					{
				
					%>
								<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>
					 <%
					}
				     %>
				     <%
					}
				     %>
					</select> 
				</td>
				<%-- <td> 
					Is Terminated	
					<%
						String flag= "";
					if(rbn.getTERMINATE()!=null)
						if(rbn.getTERMINATE().equalsIgnoreCase("Yes"))
							flag= "checked='checked'";
					%>
					<input type="checkbox" <%=flag %> name="term" value="Yes">
				</td> --%>
				<%-- <td>
					<%
						flag="";
					if(rbn.getDEATH()!=null)
					if(rbn.getDEATH().equalsIgnoreCase("Yes"))
						flag= "checked='checked'";
					%>
					Is Death <input type="checkbox"  <%=flag %> name="death" value="Yes">
				</td> --%>
			</tr>
				
		
		<tr class="alt"><td colspan="4" align="center">
		<%
			if(act.equalsIgnoreCase("ADD")){
				%>
		<input type="submit" class="myButton" value="Save" />
		<%
			}
			else{
				%>
		
		<input type="submit" class="myButton" value="Update" /> &nbsp;&nbsp;&nbsp;
		<%
			}
		%>
		<input type="reset" class="myButton" value="Cancel" onclick="window.close()" /></td></tr></table>
	
		<input type="hidden" id='sal_date' name='sal_date' value='<%=date1%>'>
		<input type="hidden" id='paytrandt' name='paytrandt' value='<%=paytrandt%>'>
</form>

</center>
 <input type="hidden" value="<%=request.getParameter("check")==null?"":request.getParameter("check")%>" name="chek" id="chek"> 
</body>
</html>