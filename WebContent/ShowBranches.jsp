<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="payroll.Model.BranchBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

<%

ArrayList<BranchBean> sortedBranchList1= new ArrayList<BranchBean>();
ArrayList<BranchBean> sortedBranchList2= new ArrayList<BranchBean>();

ArrayList<BranchBean> branchList = new ArrayList<BranchBean>();


String action = request.getParameter("action")==null?"":request.getParameter("action");

BranchDAO branchDAO=new BranchDAO();

if(action.equalsIgnoreCase("showBranchList")){
  branchList = branchDAO.getBranchList();

}
if(action.equalsIgnoreCase("sortedBranchList")){
	  branchList = (ArrayList<BranchBean>)session.getAttribute("sortedBranchList");
	 
	}


%>


function getInfo()
{
	   
	   if( document.getElementById("branchName").value=="" )
		{
		alert("Please enter proper details...!");
		}
	  
	else
		{
		//var s=document.getElementById("emn").value;
		var branchName=document.getElementById("branchName").value;
		window.location.href='BranchServlet?action=sort_branch&branchName='+branchName;
		
		}
	  
}




</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>

</head>
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Edit Branch Details</h1>
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



<table id="customers">
			<tr> <td>			<table width="700" id="customers">
			<tr>
			<td width="700" >Branch Name:<input class="form-control"  type="text" id="branchName" name="branchName" placeHolder="Enter your search string here  " style="width: 80%;" autofocus onClick="this.select();"/></td>
				
			<!--  <td width="385">Role Name:<input type="text" id="emn" name="emn" style="width: 80%;" autofocus onClick="this.select();"/></td>
			 --><td align="center" ><input class="myButton"  type="Button" value="Search" onclick="getInfo()" /></td>
				</tr></table></td></tr>
					
			 <tr> <td>
			<table width="100%" id="customers">
				        <tr>
				             <th width="50">Branch Code</th>
				             <th width="150">Branch Name</th>
				              <th width="100">Zone</th>
				   			
					           <th width="100">City</th>
					
					           <th width="150">State</th>
					            <th width="100">Modify</th>
					  
				         </tr>
			 </table>
			 </td></tr>
			 <tr><td>
			  <div style="height: 145px; overflow-y: scroll; width: 100%; margin-right: -1px;" id="div1"  >
			 <table id="customers" width="100%">
			  
			<%
			
		
				String userName;
				
				int i=0;
				int u=0;
				for(BranchBean branchBean:branchList)
				{
				    
					
				
				
			%>
			
			<tr> <td width="50" align="center"><%=branchBean.getBranchCode() %>  </td>
			
			<% 
			/* int userid=Rlbean.getUSERID();
			 ArrayList<UsrMast> list1=new ArrayList<UsrMast>();
						 UsrHandler usmhandler1=new UsrHandler();
				 	 list1=usmhandler1.getUserList();
						 */
						   LookupHandler lookupHandler = new LookupHandler();
							 %>
			<td width="150"><%=branchBean.getBranchName()==null?"":branchBean.getBranchName()%>  </td> 
			<td width="100" align="center"><%=branchBean.getZone()==null?"":branchBean.getZone()%>  </td> 
			<td width="100" align="center"><%=lookupHandler.getLKP_Desc("CITY",branchBean.getCity())==null?"":lookupHandler.getLKP_Desc("CITY",branchBean.getCity())%> </td>
			<td width="150" align="center"><%=lookupHandler.getLKP_Desc("STATE",branchBean.getState())==null?"":lookupHandler.getLKP_Desc("STATE",branchBean.getState())%></td>
		
			 
			 <td  width="100" style="text-align: center;"><input class="myButton"  type="button" value=" Edit " onClick="window.location='Branch.jsp?action=editBranch&brncd=<%=branchBean.getBranchCode() %>'"/></td>
	
				</tr>
				 
			 <%
			   } 
			
			 %>
				 
			
			</table>
			</div>
			 </td></tr>
			
			</table>






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
	
	
	
	</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
	</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
</body>
</html>
