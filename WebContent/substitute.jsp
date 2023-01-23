<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.*"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Substitute</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		

<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script>
	jQuery(function() {
          $("#EMPNO1").autocomplete("list.jsp");
	});
	function showDiv()
	 {
        document.getElementById('hide1').style.display = "none";
     }
	 
	 function showDiv1()
	 {
        document.getElementById('hide2').style.display = "none";
        
        	
	}
	 function showDiv3()
	 {
        document.getElementById('hide3').style.display = "none";
     }

	function TakeCustId() {
	
	 var fromDate=document.substituteForm.fromdate.value;
     var toDate=document.substituteForm.todate.value;
	 var  leavefrm=document.substituteForm.leavefrm.value;
	 var  leaveupto=document.substituteForm.leaveupto.value;
     fromDate = fromDate.replace(/-/g,"/");
	 toDate = toDate.replace(/-/g,"/");
	 leavefrm=leavefrm.replace(/-/g,"/");
	 leaveupto=leaveupto.replace(/-/g,"/");
	 
		var EMPNO1 = document.getElementById("EMPNO1").value;

		if (document.getElementById("EMPNO1").value == "") {
			alert("Please Insert Employee No");
			document.getElementById("EMPNO1").focus();
			return false;
		}
		var atpos = EMPNO1.indexOf(":");
		if (atpos < 1) {
			alert("Please Select Correct Employee Name");
			return false;
		}
	var d1 = new Date(fromDate);
  	
  	var d2 =new  Date(toDate);
	 
          if (d1.getTime() > d2.getTime()) 
          {
	           alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
	           document.substituteForm.todate.focus();
	           return false;
	       }
    var d3=new Date(leavefrm);
	var d4=new Date(leaveupto);
  	var msofaDay=1000*60*60*24;
  	var days = Math.floor((d3.getTime()- d4.getTime())/(msofaDay));
	
	if(d1.getTime() < d3.getTime())
	{
	           alert("Invalid Date Range!\n fromdate Date cannot less than leave date!");
	           document.substituteForm.fromdate.focus();
	           return false;
	  
	  
	  }	
	 if(d1.getTime() > d4.getTime())
	 {
	           alert("Invalid Date Range!\n fromdate Date cannot be grater than  upto leave date!");
	           document.substituteForm.fromdate.focus();
	           return false;
	   
	   } 
	 if(d2.getTime() > d4.getTime())  
	 {
	           alert("Invalid Date Range!\n period upto Date cannot be grater than  uptoleave date!");
	           document.substituteForm.todate.focus();
	           return false;
	 
	 }
	   
	}
</script>

<%
  LeaveMasterBean lbean= new LeaveMasterBean();
String action = request.getParameter("action")==null?"":request.getParameter("action");
String srNO = request.getParameter("srno")==null?"":request.getParameter("srno");
lbean= (LeaveMasterBean) session.getAttribute("subbean");
ArrayList<LeaveMasterBean> listsubst = null;
if(action.equals("showSubList")|| action.equals("editsubst")||action.equals("deleteSubst") )
{
 listsubst =(ArrayList<LeaveMasterBean>)session.getAttribute("substituteList");
}
%>
  
  
</head>
<body style="overflow:hidden" >
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:78%; "  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Add substitute</h1>
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
<%
    if(action.equals("true"))
    {
  %>
     <p style="color: red; size: 10;">Leave application succesfully submited</p>
     <%} %>
     <br>
 
		 <form  name="substituteForm" action="LeaveMasterServlet?action=addsubstitue" method="Post" onSubmit="return TakeCustId();">
          
		   <table border="1" id="customers">
		    <tr>
               <th width="664">Add Substitute</th>
             </tr>
		   <tr class="alt">
               <td height="102">
			        <p>Application Details</p>
					<table>
                      <tr class="alt">
				          <td width="80">ApplicationNO</td><td><input type="text" name="applno" value="<%=lbean.getAPPLNO() %>"  readonly="readonly"/></td>
                          <td width="80">Date</td><td><input type="text" name="date" value="<%=lbean.getTRNDATE() %>" /></td>
                      </tr>
					    <tr class="alt">
				              <td width="80"> Employee NO</td><td>
                                       <input type="text" name="EMPNO"  id="EMPNO" onclick='showHide()';"   value="<%=lbean.getEMPNO() %>" readonly="readonly"/></td>
				               <td> EmpName</td> <td><input type="text" name="empname"  id="empname" onclick='showHide()';" title="Enter Employee Name" value="<%=session.getAttribute("name")%>" readonly="readonly"/>                   </td>
                         </tr>
				   <tr class="alt">
				              <td width="80"> Leave From</td><td>
                               <input type="text" name="leavefrm" id="leavefrm"  onclick='showHide()';"  value="<%= lbean.getFRMDT()%>" readonly="readonly"/></td>
				             <td width="80"> leaveUpto</td> <td><input type="text" name="leaveupto" id="leaveupto" onclick='showHide()';"  value="<%= lbean.getTODT()%>" readonly="readonly"/> </td>
                  </tr>
				   <tr> 
				         <td>Leave Type</td><td> <input type="text" name="leavetype" value="<%= lbean.getLEAVECD()%>" readonly="readonly"/></td>
				  </tr>
					   
		           </table>			 
			   </td>
             </tr>
			 <tr class="alt">
			
			    <td>
				 
				 <p>Add Substitute Employee Details</p>
	                  <table> 
					   <tr class="alt"> 
					       <td  width="80">EmployeeNo</td> <td>   <input type="text" name="EMPNO1"  id="EMPNO1" onClick="showHide()" title="Enter Employee No" ></td>
					   </tr>
					  
					    <tr class="alt"> 
					       <td >Period from</td> 
					       <td  width="80"><input name="fromdate" size="15" id="fromdate" type="text" value="<%= lbean.getFRMDT()%>">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" />
					       </td>
					       <td>Upto</td><td>
					       <input name="todate"  size="15" id="todate" type="text" value="<%= lbean.getTODT()%>">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" />
					       </td>
					     </tr>
					  </table>	
						 
			     </td>
			 </tr>
             
            
			 
			  <tr class="alt">
               <td colspan="4" align="center"><input name="add" type="submit" value="Add" />
                <input type="button" value="Cancel"/></td>
             </tr>
			  
               <tr style="width:664" bgcolor="#92b22c" > <td >&nbsp;</td></tr>
             
             <%
             if(action.equals("showSubList")||action.equals("editsubst")|| action.equals("deleteSubst"))
             {
             %>
			 <tr class="alt">
			    <td height="84">
			    <p>Added substitute Details</p>
				     <table id="customers">
					       <tr>
						       <th width="57">EMPNO</th>
						       <th width="63">APPLNO</th>
						       <th width="47">SRNO</th>
						       <th width="105">SUBSTITUTECD</th>
						       <th width="73">FROMDAT</th>
						       <th width="85">TODAT</th>
							   <th width="50">&nbsp;</th>
							   <th width="50">&nbsp;</th>
						   </tr>
						   <%
						    if(listsubst.size()!=0)
						    {
						    	for(LeaveMasterBean slbean:listsubst)
						    	{	
						    
						   %>
						  
						    <tr >
						      <td><%=slbean.getEMPNO() %></td><td><%=slbean.getAPPLNO() %></td>
							  <td><%=slbean.getSRNO() %></td><td><%=slbean.getSUBSTCD() %></td>
							  <td><%=slbean.getFRMDT() %></td><td><%=slbean.getTODT() %></td>
							  <td>
							  <input type="button" value="Modify" onClick="window.location='substitute.jsp?action=editsubst&srno=<%=slbean.getSRNO()%>'"/></td>
							  <td>
							  <input type="button" value="Delete" onClick="window.location='substitute.jsp?action=deleteSubst&srno=<%=slbean.getSRNO()%>'"/></td>
						   
						   </tr>
						 <%
						   }
						 }
                        }
						 %>
					    
					 </table>
				
			   </td>
			   
			 </tr>
			
			 <tr><td colspan="8" bgcolor="#92b22c">&nbsp;</td></tr>
			 
           </table>
		   
	    </form>
		<br>
		
<!-- This is form for edit substitute employee  -->
		<%
		 if(action.equalsIgnoreCase("editsubst"))
		 {
			 for(LeaveMasterBean slbean:listsubst)
		    	{
					if(srNO.equalsIgnoreCase(String.valueOf(slbean.getSRNO())))
					{
		%>
		 
		 <div id="hide2" style="display:block;">
		<center>
		   <form action="LeaveMasterServlet?action=modifySubst" method="post">
		      <table id="customers">
			       <tr>
			       <th colspan="4">Edit Substitute</th>
				   </tr>
				   <tr class="alt">
				      <td>EMPNO</td><td><input type="text" name="EMPNO" value="<%=slbean.getEMPNO()%>" readonly="readonly"/></td>
					  <td>APPLNO</td><td><input type="text" name="applno" value="<%=slbean.getAPPLNO()%>"  readonly="readonly"/></td>
				  </tr>
				  <tr class="alt">
				     <td>SRNO</td><td><input type="text" name="srno" value="<%=slbean.getSRNO()%>" readonly="readonly"/></td>
				     <td>SUBSTITUTECD</td><td><input type="text" name="EMPNO1"  value="<%=slbean.getSUBSTCD()%>"/></td>  
				  </tr>
				   <tr class="alt">
				     <td>FROMDT</td><td><input type="text" name="frmdate" value="<%=slbean.getFRMDT()%>"/></td>
				     <td>TODATE</td><td><input type="text" name="todate" value="<%=slbean.getTODT()%>"/></td>  
				  </tr>
	           <tr class="alt"> 
	               <td align="center" colspan="4">
				    <input type="submit" value="Save"/> <input type="button" value="Cancel" onClick="showDiv1()"/> 
				    </td>
				 </tr>
			  <tr bgcolor="#92b22c"> 
	               <td align="center" colspan="4">&nbsp;
				   
				    </td>
				 </tr>
			  
			  </table>
		   
		   </form>
		   </center>
		   </div>
		   
		   <%}
		    	}
		     }
		    %>
       <br>
       
       <!-- This is form for delete substitute  -->
       
       <%
		 if(action.equalsIgnoreCase("deleteSubst"))
		 {
			 for(LeaveMasterBean slbean:listsubst)
		    	{
					if(srNO.equalsIgnoreCase(String.valueOf(slbean.getSRNO())))
					{
		%>
		 
		 <div id="hide3">
		   <form action="LeaveMasterServlet?action=deleteSubst" method="post">
		      <table id="customers">
			       <tr>
			       <th colspan="4">delete Substitute</th>
				   </tr>
				   <tr class="alt">
				      <td>EMPNO</td><td><input type="text" name="EMPNO" value="<%=slbean.getEMPNO()%>" readonly="readonly"/></td>
					  <td>APPLNO</td><td><input type="text" name="applno" value="<%=slbean.getAPPLNO()%>"  readonly="readonly"/></td>
				  </tr>
				  <tr class="alt">
				     <td>SRNO</td><td><input type="text" name="srno" value="<%=slbean.getSRNO()%>" readonly="readonly"/></td>
				     <td>SUBSTITUTECD</td><td><input type="text" name="EMPNO1"  value="<%=slbean.getSUBSTCD()%>"/></td>  
				  </tr>
				   <tr class="alt">
				     <td>FROMDT</td><td><input type="text" name="frmdate" value="<%=slbean.getFRMDT()%>"/></td>
				     <td>TODATE</td><td><input type="text" name="todate" value="<%=slbean.getTODT()%>"/></td>
				  </tr>
				  <tr class="alt">
				     <td>STATUS</td><td><input type="text" name="status" value="98"/></td><td colspan="2">&nbsp;</td>  
				  </tr>
	           <tr class="alt"> 
	               <td align="center" colspan="4">
				    <input type="submit" value="Save"/> <input type="button" value="Cancel" onClick="showDiv3()"/> 
				    </td>
				 </tr>
			  <tr bgcolor="#92b22c"> 
	               <td align="center" colspan="4">&nbsp;
				   
				    </td>
				 </tr>
			  
			  </table>
		   
		   </form>
		   </div>
		    <%}
		    	}
		     }
		    %>
		  </center>
		   </div>
			</div>
			<!--  end table-content  -->
			<div class="clear"></div>
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
	</div>
	<div class="clear">&nbsp;</div>
</div>
<!--  end content -->
<div class="clear">&nbsp;</div>

<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    

</body>
</html>