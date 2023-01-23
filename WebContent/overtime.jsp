<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

 


<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String srno = request.getParameter("srno")==null?"":request.getParameter("srno");
ShiftHandler shifthand= new ShiftHandler();
 
ArrayList<ShiftBean> ovtlist=null;
if(action.equalsIgnoreCase("ovetimelist")||action.equalsIgnoreCase("update"))
{
 //ovtlist =  (ArrayList<ShiftBean>)session.getAttribute("ovtlist1");
 ovtlist=shifthand.getList();

}
%>
<script type="text/javascript">


  function validate()
  {
  
  
     if(document.overtimeform.EmpType.value=="Default")
          {   alert("please select EmpType");
              document.overtimeform.EmpType.focus();
              return false;
           }
		  if(document.overtimeform.shiftcode.value=="Default")
		  {
		  alert("please select shiftcode");
		   document.overtimeform.shiftcode.focus();
		   return false;
		  }
		   if(document.overtimeform.grade.value=="Default")
		  {
		  alert("please select grade");
		   document.overtimeform.grade.focus();
		   return false;
		  }
		   if(document.overtimeform.rate.value=="")
			  {
			  alert("please enter rate");
			   document.overtimeform.rate.focus();
			   return false;
			  }
		   var number=document.overtimeform.rate.value;
		   alert(number);
		   if (number != parseInt(number))
			   {
			   alert("please enter only Integer value");
			   
			   document.overtimeform.rate.focus();
			   return false;
			   
			   }

  
  
  
  }



function Update(srno)
{
	
	window.location.href="overtime.jsp?action=update&srno="+srno;
	}
	function Showhide()
	{
		document.getElementById("update").hidden=true;
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
		<h1> OverTime Management  </h1>
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
			 
			
			
			<form  name="overtimeform"action="ShiftServlet?action=addovertime" method="post" onSubmit="return validate()">
				<table id="customers" align="center">
				
				<tr><th colspan="4"> OverTime</th></tr>
				<%
				    LookupHandler lkph=new  LookupHandler(); 
				
				%>
				
				 
				<tr class="alt"> 
					<td width="63">EmpType</td>
					<td width="87">
					<select name="EmpType" id="EmpType">
					<option value="Default" selected="selected">Select</option>	 
						<%
  							  ArrayList<Lookup> result=new ArrayList<Lookup>();
    						LookupHandler lkhp= new LookupHandler();
    						result=lkhp.getSubLKP_DESC("ET");
 							for(Lookup lkbean : result)
							{%>
 					<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>  
 						<%
 					      }
 					   %>
      			
     					 
					</select></td>
					<td width="67">ShiftCode</td>
					<td width="167">
					<select name="shiftcode" id="shiftcode">
						<option value="Default" selected="selected">Select</option>
						<option value="1">First</option>
						<option value="2">Second</option>
						<option value="3">General</option>
						<option value="4">Night</option>
					</select>
					</td>
					
     				
				</tr>
				<tr class="alt">
					<td>Grade</td><td>
					
					 <select name="grade" id="grade" >  
      					 <option value="Default" selected="selected" >Select</option>  
    						<%
    						
  							  ArrayList<Lookup> result1 =new ArrayList<Lookup>();
    						 EmpOffHandler ekhp= new EmpOffHandler();
    						
    						 result1=ekhp.getGradeBranchList("GRADE");
 							for(Lookup lkbean : result1)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 		
     					 	<%	}%>
     			 </select>
					
					</td>
					<td>Rate</td><td><input type="text" name="rate"/></td>
				</tr>
				<tr class="alt" align="center"><td colspan="4"> 
				<input type="submit" value="Save"> &nbsp; &nbsp;<input type="reset" value="Reset"></td></tr>
				
				</table>
			
			</form>
			<br>
			
			
			
			<table width="883" id="customers">
				<tr>
					<th width="60">SrNo</th>
					<th width="200">EmpType</th>
					<th width="100">Grade</th>
					
					<th width="110">ShiftCode</th>
					<th width="134">Rate</th>
					<th width="51">Edit</th>
				</tr>
			<%
			
			
			if(action.equalsIgnoreCase("ovetimelist")|| action.equalsIgnoreCase("update"))
			{
				
				String desc="";
				String desc1="";
				LookupHandler lookup=new LookupHandler();  
			  if(!(ovtlist == null))
			  {
				for(ShiftBean sbean1 : ovtlist)
				{
					desc1=Integer.toString( sbean1.getEmptype());
					
					Lookup lkp=lookup.getLookup("ET-"+desc1);
					
					
			%>
			
			<tr align="center"><td width="60"><%=sbean1.getSrno() %></td><td width="170"><%=lkp.getLKP_DESC() %></td><td width="90"><%=sbean1.getGrade() %></td><td><%=sbean1.getShiftcode() %></td><td width="80"><%=sbean1.getRate() %></td>
				<td><input type="button" value="Edit" onClick="Update(<%=sbean1.getSrno()%>)"/></td>
				</tr>
			
			<%}
				
			  }
		}
		%>
			<tr class="alt"><th colspan="6"></th></tr>  
			</table>
			
			<br>
			
			<%
			if(action.equalsIgnoreCase("update"))
			{
				for(ShiftBean sb : ovtlist)
				{
				  if(srno.equalsIgnoreCase(Integer.toString(sb.getSrno())))
				{
			%>
			<form action="ShiftServlet?action=updateovertime" method="post">
				<div  id="update" name="update">
				
				<table id="customers" align="center">
				<tr><th colspan="4"> Edit OverTime</th></tr>
				<tr class="alt"> 
					
					<td width="63">EmpType</td>
					<td width="87">
					<select name="EmpType" id="EmpType" style="width: 380px;">
						 <option value="Default" selected="selected">Select</option>	 
						<%
  							  ArrayList<Lookup> result2=new ArrayList<Lookup>();
    						LookupHandler lkhp2= new LookupHandler();
    						result2=lkhp.getSubLKP_DESC("ET");
 							for(Lookup lkbean : result2){
 								
 									%>
 									<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
 									<%
 									}
 							%>
      			
     					 
					</select></td>
					
					<td width="71">Shiftcode </td>
					<td width="169">
					<%
						String First = "notselected";
						String Second = "notselected";
						String General = "notselected";
						String Night = "notselected";
						
						if(sb.getShiftcode()==1){
							First = "selected";
						}else if(sb.getShiftcode()==2){
							Second = "selected";
						}else if(sb.getShiftcode()==3){
							General = "selected";
						}else if(sb.getShiftcode()==4){
							Night = "selected";
						}
						
						%>
					<select name="shiftcode" id="shiftcode">
						<option value="Default" selected="selected">Select</option>	 
						<option value="1" <%=First %>>First</option>
						<option value="2" <%=Second %>>Second</option>
						<option value="3" <%=General %>>General</option>
						<option value="4" <%=Night %>>Night</option>
					</select>
					</td>
					
     				
				</tr>
				<tr class="alt">
					<td>Grade</td><td>
					
					 <select name="grade" id="grade" >  
      					<option value="Default" selected="selected">Select</option>	 
    						<%
    						
  							  ArrayList<Lookup> result3 =new ArrayList<Lookup>();
    						 EmpOffHandler ekhp2= new EmpOffHandler();
    						 result3=ekhp.getGradeBranchList("GRADE");
 							for(Lookup lkbean : result3)
 							{
     						%>
      						<option value="<%=lkbean.getLKP_SRNO()%>" ><%=lkbean.getLKP_DESC()%></option>  
     					 		
     					 	<%	}%>
     			 </select>
					
					</td>
					<td>Rate</td><td><input type="text" name="rate" value="<%=sb.getRate()%>"/>
					<input type="hidden" name="srno" value="<%=sb.getSrno() %>">
					</td>
					
				</tr>
				<tr class="alt" align="center"><td colspan="4"> 
				<input type="submit" value="Save"> &nbsp; &nbsp;<input type="button" value="Cancel" onClick="Showhide()"></td></tr>
				
				</table>
			<%}} }%>
			</div>
			</form>
			
			
			
			  </center>
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