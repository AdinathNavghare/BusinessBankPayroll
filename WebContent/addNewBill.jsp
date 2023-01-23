<%@page import="org.jfree.data.time.RegularTimePeriod"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.BillBean"%>
<%@page import="payroll.DAO.BillHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/datePicker.css" type="text/css" media="screen" title="default" />	
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker1.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/Loan_Cal_Outer.js"></script>

<title>Add Bill Details</title>
<%
	int flag= -1;
	//flag= Integer.parseInt(request.getParameter("flag")==null?"0":request.getParameter("flag"));
	try
	{
		String action = request.getParameter("action")==null?"first":request.getParameter("action");
		if(action.equalsIgnoreCase("0"))
			flag =0; //error message
		else if(action.equalsIgnoreCase("1"))
			flag=1; //Record deleted
		else if(action.equalsIgnoreCase("2"))
			flag=2;	//Record Added
		else if(action.equalsIgnoreCase("3"))
			flag=3;	//Record Edited
		/* else if(action.equalsIgnoreCase("first"))
			flag=0;	//Record Edited */			
	}
	catch(Exception e){
		System.out.println("First Time Loading dialog");
		e.printStackTrace();	
	}	 
	
	//Outer try catch for complete page......
	try
	{
		CodeMasterHandler codeMasterHandler = new CodeMasterHandler();
		CodeMasterBean codeMasterBean = new CodeMasterBean();
		LookupHandler lookupHandler = new LookupHandler();
		EmployeeHandler employeeHandler = new EmployeeHandler();
		EmployeeBean employeeBean = new EmployeeBean();
		BillHandler billHandler = new BillHandler();
		BillBean billBean = new BillBean();
		int trncd = 0;
		int empno=0;
		int fromyear=0;
		int toyear=0;
		String fyear ="";
		String tyear ="";
		
		int billid = 0;
		float amt=0;
		float totalamt=0;
		
		//Inner try catch ......
	 	try
		{
			trncd = Integer.parseInt(request.getParameter("trncd")==null?"0":request.getParameter("trncd"));
			empno= Integer.parseInt(request.getParameter("empno"));
			billid= Integer.parseInt(request.getParameter("billid")==null?"0":request.getParameter("billid"));
			amt = Float.parseFloat(request.getParameter("amt"));
			codeMasterBean= codeMasterHandler.getCodeDetails(trncd);
			employeeBean = employeeHandler.getEmployeeInformation(Integer.toString(empno));
		}
		catch(Exception e){ System.out.println("First Time Loading"); }	
%>

<script type="text/javascript">

function checkVal()
{
	var f = document.getElementById("flag").value;
	if(f == 0)
	{
		alert("Somthing Went Wrong!");
	}
	else if(f == 1)
	{
		alert("Record Deleted Successfully!");
	}
	else if(f== 2)
	{
		alert("Record Add Successfully!");
	} 
	else if(f== 3)
	{
		alert("Record updated Successfully!");
	} 
	
}
	
	function getClose()
	{
		window.close();
	}	
	
	function editDeatails(billid,empno,trncd)
	{			
		window.location.href= "addNewBill.jsp?action=editDetails&billid="+billid+"&empno="+empno+"&trncd="+trncd;
		<%					
	 	    String action = request.getParameter("action")==null?"0":request.getParameter("action");
		 	if(action.equalsIgnoreCase("editDetails"))
			{  
				billid = Integer.parseInt(request.getParameter("billid"));				
				empno = Integer.parseInt(request.getParameter("empno"));	
			   	trncd = Integer.parseInt(request.getParameter("trncd"));
				codeMasterBean= codeMasterHandler.getCodeDetails(trncd);
				employeeBean = employeeHandler.getEmployeeInformation(Integer.toString(empno));
				billBean=billHandler.getBillIdDetails(billid,empno);					
			}	
		%>	
	}

	function deleteDetails(billid,empno){
		document.getElementById("editDiv").disabled= true;	
		var check = confirm("Are you sure to delete!");
		if(check){
			window.location.href = "BillServlet?action=deleteDetails&billid="+billid+"&empno="+empno;
		}
	}
	
	
	function validation()
	{
		//alert("Heloo");				
	 	if(document.getElementById("forWhom").value=="0" && document.getElementById("forWhom").value==""){
			alert("Please select type of bill for Applied");
			document.getElementById("forWhom").focus();
			return false;
		} 
		if(document.getElementById("amount").value==0 && document.getElementById("amount").value==""){
			alert("Please select Amount");
			document.getElementById("amount").focus();
			return false;
		}
		if(document.getElementById("billNo").value==0 && document.getElementById("billNo").value==""){
			alert("Please select bill Number");
			document.getElementById("billNo").focus();
			return false;
		}
		if(document.getElementById("fromDate").value==0 && document.getElementById("fromDate").value==""){
			alert("Please Select from Date");
			document.getElementById("fromDate").focus();
			return false;
		}
		if(document.getElementById("toDate").value==0 && document.getElementById("toDate").value==""){
			alert("Please Select to Date");
			document.getElementById("toDate").focus();
			return false;
		}		
		
		<% 
			fromyear = Integer.parseInt(session.getAttribute("year").toString());
			toyear = fromyear+1;
			fyear = "1-apr-"+fromyear;
			tyear = "31-march-"+toyear;
		%>	

		var fy ="<%=fyear%>" ;
		var ty ="<%=tyear%>" ;	
	
		fy = parseMyDateForEmp(fy);
		ty = parseMyDateForEmp(ty);
		
		var fromD = document.getElementById("fromDate").value;
		var toD = document.getElementById("toDate").value;		
				
		fromD = parseMyDateForEmp(fromD);
		toD = parseMyDateForEmp(toD);
		
		if(toD < fromD){
			alert("To date should be greter than from date");
			document.getElementById("toDate").focus();
			return false;			
		}	
		
		if(fromD  < fy){
			alert("From date should be in financial Year !");
			document.getElementById("formDate").focus();
			return false;
		}	
		
		if(toD > ty){
			alert("To date should be in financial Year !");
			document.getElementById("toDate").focus();
			return false;
		}
		//return false;
	}
	
	function parseMyDateForEmp(s) {
		
		var m = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug',
				'sep', 'oct', 'nov', 'dec' ];
		var match = s.match(/(\d+)-([^.]+)-(\d+)/);
		var date = match[1];
		var monthText = match[2];
		var year = match[3];
		var month = m.indexOf(monthText.toLowerCase());
		return new Date(year, month, date);
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

	function inputLimiterForFloat(e,allow) {
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
<body onLoad="checkVal()">
<center>
<table id="ccustomers">

<tr valign="top"><td align="center">
<div id ="editDiv">
<%
	if(action.equalsIgnoreCase("editDetails"))
	{		
%> 
<form  name="editform" id="editform" action="BillServlet?action=updateDetails" method="post" onSubmit="return validation()">
        
<table id="ccustomers">
<tr valign="top">
	<td align="center">	        
		    <table width="662" height="150" border="0" bgcolor="#2F747E">
			<tr height="26" align="center" valign="middle" class="alt">
			  <td  colspan="5" style="color:#ffffff;"> EDIT <%=codeMasterBean.getDISC()%> FOR <%=lookupHandler.getLKP_Desc("SALUTE", employeeBean.getSALUTE())+" "+ employeeBean.getFNAME()+" "+employeeBean.getLNAME()%> </td>
		    </tr>
		    <tr height="26" class="alt">
		      <td width="100" align="center"  bgcolor="#F5F6FA">For Whom</td>
		      <%if(trncd==524)
		      { %>
			      <td width="100" align="center"  bgcolor="#F5F6FA">    
				      <select name="forWhom" id="forWhom" >  
				      					 <!-- <option value="0">Select....</option> -->  
				    		<%
				  				ArrayList<Lookup> result=new ArrayList<Lookup>();
				    			result=lookupHandler.getSubLKP_DESC("RELATION");
				    			//System.out.println("result"+result.size());
				 				for(Lookup lkbean : result){
					     			if((lkbean.getLKP_SRNO()==1) ||(lkbean.getLKP_SRNO()==2) || (lkbean.getLKP_SRNO()==5) || (lkbean.getLKP_SRNO()==6) || (lkbean.getLKP_SRNO()==7)||(lkbean.getLKP_SRNO()==8))
					     			{%>
					      				<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
					     			<%}			     									 							
				 				}%>
				     </select>	  
			     </td>    						
		      <%}  
		      else{ %>
			      <td width="100" align="center" valign="middle" bgcolor="#F5F6FA">
			      	<input name="forWhom" id="forWhom" type="text" maxlength="14" value=" " disabled="disabled">
			      </td>
		      <% }%>     		      
		   
		      <td width="100"  align="center" valign="middle" bgcolor="#F5F6FA">Amount</td>
		      <td width="120" align="center"  bgcolor="#F5F6FA">
		      	<input name="amount" type="text" value="<%=billBean.getAmount()==0?"":billBean.getAmount()%>" id="amount"  size="10" onkeypress="return inputLimiter(event,'Numbers')">
		      </td>
		      <!-- this values is required for update details at form is submitted -->
		      <% billid = billBean.getBillId(); %> 
		      <% trncd = billBean.getTrncd();  %> 
		      
		      <td width="120" align="center"  bgcolor="#F5F6FA">Bill Number</td>  
		      <td width="100" align="center" bgcolor="#F5F6FA">
		      		<input name="billNo" id="billNo" type="text" maxlength="" size="10" value="<%=billBean.getBill_tel_no()==0?"":billBean.getBill_tel_no()%>" >
		      </td>  
		   </tr>
		   
		   <tr height="26" class="alt">
		      <td width="110" align="center"  bgcolor="#F5F6FA">From Date &nbsp; <font color="red"><b>*</b></font></td>
		     
			  <td  colspan="2" width="210" align="center"  bgcolor="#F5F6FA">
			  	<input size="14" name="fromDate" class="form-control" id="fromDate" type="text" 
								 value="<%=billBean.getFrom_Date()==null?"":billBean.getFrom_Date()%>"   readonly="readonly">&nbsp;&nbsp;
				<img src="images/cal.gif"  
					 style="vertical-align: middle; cursor: pointer;" 
					 onClick="javascript:NewCssCal('fromDate', 'ddmmmyyyy')"/> 									
			  </td>    						
		      <td width="100" height="24" align="center" valign="middle" bgcolor="#F5F6FA">To date &nbsp; <font color="red"><b>*</b></font></td>
		      <td colspan="2" width="210" align="center"  bgcolor="#F5F6FA">
		      		<input size="14" name="toDate" id="toDate" class="form-control" 
						   type="text" value="<%=billBean.getTo_Date()==null?"":billBean.getTo_Date()%>" readonly="readonly"> &nbsp;&nbsp;
					<img src="images/cal.gif"  style="vertical-align: middle; cursor: pointer;"
						 onClick="javascript:NewCssCal('toDate', 'ddmmmyyyy')" />									
		      </td>
		   </tr>		   		   
		   
		    <tr height="26" class="alt" align="center" valign="middle" >
		      <td  colspan="6" bgcolor="#F5F6FA">
		          <label>	        
                    <input type="submit" name="update" id="update" value="Update"   />                    	                 
		          </label>
		          <label>
		          <input type="button" name="Submit3" value="Cancel" onClick="getClose()"/>
		        </label>
		      </td>
		    </tr>
		 </table>
		 
 		 <input type="hidden" name="billid" id="billid" value="<%=billid%>"> 
		 <input type="hidden" name="empNo" id="empNo" value="<%=empno%>">
		 <input type="hidden" name="trncd" id="trncd" value="<%=trncd%>">	 			
	</td>
</tr>	
</table>
</form>
</div>
<% 
}
else{
%>
<div id="addDiv">
	<form  name="addform" id="addform" action="BillServlet?action=add" method="post" onSubmit="return validation()">
    
	<table id="ccustomers">
	<tr valign="top">
		<td align="center">	        
			    <table width="662" height="150" border="0" bgcolor="#2F747E">
				<tr height="26" align="center" valign="middle" class="alt">
				  <td  colspan="5" style="color:#ffffff;"> ADD <%=codeMasterBean.getDISC()%> FOR <%=lookupHandler.getLKP_Desc("SALUTE", employeeBean.getSALUTE())+" "+ employeeBean.getFNAME()+" "+employeeBean.getLNAME()%> </td>
			    </tr>
			    <tr height="26" class="alt">
			      <td width="100" align="center"  bgcolor="#F5F6FA">For Whom</td>
			      <%if(trncd==524)
			      { %>
				      <td width="100" align="center"  bgcolor="#F5F6FA">    
					      <select name="forWhom" id="forWhom" >  
					      		 <!-- <option value="0">Select....</option> -->  
					    		 <%
					  				ArrayList<Lookup> result=new ArrayList<Lookup>();
					    			result=lookupHandler.getSubLKP_DESC("RELATION");
					    			//System.out.println("result"+result.size());
					 				for(Lookup lkbean : result){
					     				if((lkbean.getLKP_SRNO()==1) ||(lkbean.getLKP_SRNO()==2) || (lkbean.getLKP_SRNO()==5) || (lkbean.getLKP_SRNO()==6) || (lkbean.getLKP_SRNO()==7)||(lkbean.getLKP_SRNO()==8))
					     				{%>
					      					<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
					     				<%}				     										 							
					 				}%>
					     </select>	  
				    </td>    						
			      <%}  
			      else{ %>
				      <td width="100" align="center" valign="middle" bgcolor="#F5F6FA">
				      	<input name="forWhom" id="forWhom" type="text" maxlength="14" value=" " disabled="disabled">
				      </td>
			      <% }%>     			      
			   
			      <td width="100"  align="center" valign="middle" bgcolor="#F5F6FA">Amount</td>
			      <td width="120" align="center"  bgcolor="#F5F6FA">
			      	<input name="amount" type="text" value="<%=billBean.getAmount()==0?"":billBean.getAmount()%>" id="amount"  size="10" onkeypress="return inputLimiter(event,'Numbers')">
			      </td>
			       <!-- this values is required for update details at form is submitted -->
				      <% billid = billBean.getBillId(); %> 
				      <% trncd = billBean.getTrncd();  %> 		      		     		      
			      <td width="120" align="center"  bgcolor="#F5F6FA">Bill Number</td>  
			      <td width="100" align="center" bgcolor="#F5F6FA">
			      		<input name="billNo" id="billNo" type="text" maxlength="" size="10" value="<%=billBean.getBill_tel_no()==0?"":billBean.getBill_tel_no()%>" >
			      </td>  
			   </tr>
			   
			   <tr height="26" class="alt">
			      <td width="110" align="center"  bgcolor="#F5F6FA">From Date &nbsp; <font color="red"><b>*</b></font></td>
			     
				  <td  colspan="2" width="210" align="center"  bgcolor="#F5F6FA">
				  	<input size="14" name="fromDate" class="form-control" id="fromDate" type="text" 
									 value="<%=billBean.getFrom_Date()==null?"":billBean.getFrom_Date()%>"   readonly="readonly">&nbsp;&nbsp;
					<img src="images/cal.gif"  
						 style="vertical-align: middle; cursor: pointer;" 
						 onClick="javascript:NewCssCal('fromDate', 'ddmmmyyyy')"/> 
										
				  </td>    						
			      <td width="100" height="24" align="center" valign="middle" bgcolor="#F5F6FA">To date &nbsp; <font color="red"><b>*</b></font></td>
			      <td colspan="2" width="210" align="center"  bgcolor="#F5F6FA">
			      		<input size="14" name="toDate" id="toDate" class="form-control" 
							   type="text" value="<%=billBean.getTo_Date()==null?"":billBean.getTo_Date()%>" readonly="readonly"> &nbsp;&nbsp;
						<img src="images/cal.gif"  style="vertical-align: middle; cursor: pointer;"
							 onClick="javascript:NewCssCal('toDate', 'ddmmmyyyy')" />									
			      </td>
			   </tr>			   
			   			   
			    <tr height="26" class="alt" align="center" valign="middle" >
			      <td  colspan="6" bgcolor="#F5F6FA">
			          <label>		        
			       	  	<input class="myButton" type="submit" name="save" id="save" value="Save" class="myButton"/>	      
			          </label>
			          <label>
			          <input class="myButton" type="button" name="Submit3" value="Cancel" onClick="getClose()" />
			        </label>
			      </td>
			    </tr>
			 </table>
			 
	 		 <input type="hidden" name="billid" id="billid" value="<%=billid%>"> 
			 <input type="hidden" name="empNo" id="empNo" value="<%=empno%>">
			 <input type="hidden" name="trncd" id="trncd" value="<%=trncd%>">			 
						
		</td>
	</tr>	
</table>
</form>
</div>
<%
}
%> 

</td>
</tr>

<!--  @@@@@@@@@@@@@@@@@@ This is new Table for view Bill Detalis @@@@@@@@@@@@@@@@@@@@@ -->
<%
		trncd = Integer.parseInt(request.getParameter("trncd"));
		System.out.println("Trncd for view details div "+ trncd);
		codeMasterBean= codeMasterHandler.getCodeDetails(trncd);
		employeeBean = employeeHandler.getEmployeeInformation(Integer.toString(empno));
%>
<tr>		
	<td align="center">
	<div id="viewDiv">
		<table width="662" height="181" border="0" bgcolor="#2F747E">
   
		<tr height="26" align="center" class="alt">
		  <td colspan="5" style="color:#ffffff;">VIEW  <%=codeMasterBean.getDISC()%></td>
		</tr>
	    <tr  height="26" class="alt">
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA">SRNO</td>
	      <td width="90"  align="center" valign="middle" bgcolor="#F5F6FA">AMT</td>
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA">ForWhom</td>
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA">Date</td>  
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA">Edit/Delete</td>  
	    </tr>
	 <%
	 
		fromyear = Integer.parseInt(session.getAttribute("year").toString());	 
		ArrayList<BillBean> result=new ArrayList<BillBean>();
		result=billHandler.getBillDatails(trncd,fromyear);		
		//System.out.println("result"+result.size());
		
		for(BillBean billbean : result)
		{
			totalamt = billbean.getAmount()+ totalamt;
	 %> 
    	 <tr  height="26" class="alt">
	      <td width="90"  align="center" valign="middle" bgcolor="#F5F6FA"><%=billbean.getBillId() %></td>
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA"><%=billbean.getAmount() %></td>    
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA"> <%=lookupHandler.getLKP_Desc("RELATION", billbean.getForWhom())%></td>
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA"><%=billbean.getCreatedOn() %></td>  
	      <td width="90" align="center" valign="middle" bgcolor="#F5F6FA">
	      	<input type="button" name="edit" id="edit"  value="Edit" onClick="editDeatails('<%=billbean.getBillId() %>','<%=empno %>', '<%=trncd %>')"/>
	      	<input type="button" name="Submit4" value="Delete" onClick="deleteDetails('<%=billbean.getBillId() %>','<%=empno%>')"/>
		  </td>       
	    </tr>
	    
     <%
		}
	  %>
	
	     <tr height="26" align="center" valign="middle" class="alt">
	   
	      	<td  bgcolor="#F5F6FA">Total</td>
	        <td  align="center" bgcolor="#F5F6FA"><%=totalamt %></td>
	         <td  colspan="3" align="center" bgcolor="#F5F6FA"></td>
	    </tr>
	 <%   
	    }
	catch(Exception e)
	{
		System.out.println("First Time Loading");
		e.printStackTrace();
	}
     %>	
  </table>
  </div>
  </td>
</tr>

	
</table>
<input type="hidden" value="<%=flag%>" name="flag" id="flag">
</center>

</body>
</html>