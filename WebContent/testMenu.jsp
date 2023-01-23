<%@page import="java.util.Iterator"%>
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
<script type="text/javascript" src="js/datetimepicker.js"></script>




<script type="text/javascript">

	
	var xmlhttp;
	var url="";
	
	if(window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest;
	}
	else //if(window.ActivXObject)
	{   
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function loadLists(obj) 
	{
		var index = document.getElementById(obj).selectedIndex;
		if(index != 0)
		{
			var rid = document.getElementById(obj).value;
			url="RoleServlet?action=loadLists&rid="+rid;
			
			xmlhttp.onreadystatechange=callback;
    		xmlhttp.open("GET", url, true);
    		xmlhttp.send();
		}
	}
	
	function callback()
	{
    	if(xmlhttp.readyState==4)
		{
        	var response=xmlhttp.responseText;
        	var result = response.split("$");
        	document.getElementById("notAssg").innerHTML = result[0];
        	document.getElementById("assg").innerHTML = result[1];
    	}
	}
	
	function move(direction)
	{
		var src = document.getElementById('notAssign');
		var trg = document.getElementById('assign');
		var tem;
	 
		if(direction)
		{
			tem = src;
			src = trg;
			trg = tem;
		}
	 
		var selected = [];
	 
		for(var i in src.options)
		{
			if(src.options[i].selected)
			{
				trg.options.add(new Option(src.options[i].text, src.options[i].value));
				selected.unshift(i);
			}
		}
	 
		for(i in selected)
			src.options.remove(selected[i]);
	}
	
	function selectAll()
	{
		var box = document.getElementById('assign');
		var len = box.length;
		for(var i = 0; i < len; i++)
		{
			document.getElementById('assign').options[i].selected = true;
		}
	}
	function loadList () 
	{
		var role = document.getElementById("rolename").value;
		window.location.href="RoleServlet?action=showRoleMenu&rolename="+role;
	}
	
function checkVal()
	{
		var flag = document.getElementById("check").value;
		if(flag == 1)
		{
			alert("Saved Successfully");
			window.location.href="assignMenu.jsp";
		}
		if(flag == -1)
		{
			alert("Error in Saving Please Try Again");
			window.location.href="assignMenu.jsp";
		}
		loadLists();
	}



function Showhide()
	{
		document.getElementById("update").hidden=true;
	}


function validate1()
{
  if(document.userroleform.userid.value=="Default")
   {
	  alert(" please Select userName");
	  document.userroleform.userid.focus();
	  return false;
	  
   }
  if(document.userroleform.role.value=="Default")
	  {
	    alert("please select role");
	    document.userroleform.role.focus();
	    return false;
	  
	   
	  }
  if(document.userroleform.status.value=="Default")
  {
    alert("please select status");
    document.userroleform.status.focus();
    return false;
  
   
  }
	
	
	
}
</script>

<%
String action = request.getParameter("action")==null?"":request.getParameter("action");

int rid =0;
ArrayList<RoleBean> Mlist=null;
if(action.equalsIgnoreCase("menulist"))
{
	Mlist = (ArrayList<RoleBean>)request.getAttribute("mlist");
	rid = Integer.parseInt(request.getAttribute("rid").toString());
}

ArrayList<RoleBean> Rolelist=null;
if(action.equalsIgnoreCase("showroleList")||action.equalsIgnoreCase("updateRole"))
{
	Rolelist = (ArrayList<RoleBean>)session.getAttribute("UroleList");

}


RoleDAO RD = new RoleDAO();
ArrayList<RoleBean> roleList =RD.getAllRole(); 
int action1= 0;
try
{
	action1 = Integer.parseInt(request.getParameter("action"));
}
catch(Exception e)
{
}
%>
</head>
<body  style="overflow: hidden;" onLoad="checkVal()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
  
  <div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Menu ManageMent</h1>
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
			 
			   <table border="1">
			          <tr> 
					  <td width="500" align="center" height="300" valign="top">
					  <br>
					      <table id="customers" width="500" align="center" >
				
				  <tr><th colspan="3">RoleMenu</th></tr>
				 
				
				 
				<tr class="alt"> 
					
					<td width="145">RoleName</td>
					<td width="529" colspan="2">
					<select name="rolename" id="rolename" onChange="loadList()">
                      <option value="Select" selected>Select</option>
                      <%
							RoleDAO RDAO = new RoleDAO();
                      		ArrayList<RoleBean> roles = RDAO.getAllRole();
                      		for(RoleBean RB : roles)
                      		{
                      			if(rid == RB.getROLEID())
                      			{
                      				 %>
                                     <option value="<%=RB.getROLEID()%>" selected="selected"><%=RB.getROLENAME()%></option>
                                     <%		
                      			}
                      			else
                      			{
			                      %>
			                      <option value="<%=RB.getROLEID()%>"><%=RB.getROLENAME()%></option>
			                      <%
                      			}
                      		}
                      %>
                    
                    </select></td>
				</tr>
				
				<%
			if(action.equals("menulist"))
			{
				 %>
				 
			<tr><td colspan="3">	 
			<table width="416" id="customers" >
				<tr>
				 <th></th>
				<th colspan="6">Menu Name</th>
					 
				</tr>
			<%
			
			Iterator<RoleBean> it = Mlist.iterator();
			   while(it.hasNext())
			   {
				   RoleBean bean1 = it.next();
			%>
			
			<tr>
				<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
				<td> <%=bean1.getMENU_NAME()%> </td> 
			 	<%	if(it.hasNext())
			 		{
			 			bean1 = it.next();
			 	%>
						<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
						<td> <%=bean1.getMENU_NAME()%> </td> 
				<%
						
						if(it.hasNext())
		 				{
		 					bean1 = it.next();
		 					%>
							<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
							<td> <%=bean1.getMENU_NAME()%> </td> 
					<%
			 			}
			 		}
				%>
				</tr>
			 <%
			   } 
			
			 %>
				<tr class="alt"><th colspan="7"></th></tr>
			
			</table>
		</td></tr>
		 <%} %>
		 	</table>
					  </td>
					  
					  <td valign="top" align="center">
					  <br>
					  
					        <form  name="roleform" action="RoleServlet?action=createNewRole" method="post" onSubmit="return validate()">
				<table id="customers" width="488" align="center" >
				
				 <tr> <th colspan="4">Create New Role</th></tr>
				 
				
				 
				<tr class="alt"> 
					<td width="68">RoleName</td>
					<td width="158">
					   <input type="text" name="rolename"/>
					
					</td>
					<td width="77">Description</td>
					<td width="165">
					<input type="text" name="desc"/>
					</td>
		
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">&nbsp; &nbsp;
				  <input type="reset" value="Cancel"></td></tr>
				</table>
			</form>
					  </td>
					  	  
				 </tr>
			 <tr>
			    <td valign="top">
			       <form action="RoleServlet?action=aasignMenu" method="post" onSubmit="selectAll()">
			<center>
			<br>
				<table id="customers">
					<tr>
							<th>Select Role</th>
							<th>
								<select id="role" name= "role" onChange="loadLists('role')">
									<option value="0" selected="selected">Select</option>
								<%
									for(RoleBean role:roleList)
									{
								
								%>
									<option value="<%=role.getROLEID()%>"><%=role.getROLENAME()%></option>
								<%
								
									}
								%>
								</select>
							</th>
					</tr>	
				</table>
				
				<br/>
			
			    <table width="400" height="47" border="1" id="customers">
                  <tr align="center" valign="middle">
                    <td width="175" align="center">Copy From Role</td>
                    <td width="50">&nbsp;</td>
                  <td>
								<select id="role1" name= "role1" onChange="loadLists('role1')">
									<option value="0" selected="selected">Select</option>
								<%
									for(RoleBean role:roleList)
									{
								
								%>
									<option value="<%=role.getROLEID()%>"><%=role.getROLENAME()%></option>
								<%
								
									}
								%>
								</select>
							</td>
                  </tr>
				  <tr align="center" valign="middle">
                    <th width="175" align="center">Not Assigned </th>
                    <th width="50">&nbsp;</th>
                    <th width="175" align="center">Assigned</th>
                  </tr>
                  <tr>
                    <td align="center"><span id = "notAssg"></span></td>
                    <td align="center" valign="middle">
                      <input type="button" name="in" value=">>" onClick="move(0)"><br/>
                      <input type="button" name="out" onClick="move(1)" value="<<">                  	</td>
                    <td align="center"><span id = "assg"></span></td>
                  </tr>
                  <tr>
                  <td colspan="3" align="center"><input type="submit" name="submit" value="Save"></td>
                  </tr>
                </table>
			    <p>&nbsp;</p>
			</center>
			</form>
				</td>
			          <td valign="top" align="center">
					  <br>
			               <form  name="userroleform" action="RoleServlet?action=adduserRole" method="post"onsubmit="return validate()">
				<table id="customers" width="511" align="center">
				
				<th colspan="4">Add UserRole</th>
				<tr class="alt"> 
					<td width="63">UserID</td>
					<td width="158">
					<select name="userid" id="userid" style="width:100px;">
					     <option value="Default" selected="selected">Select</option>
						 <% 
						 ArrayList<UsrMast> list=new ArrayList<UsrMast>();
						 UsrHandler usmhandler=new UsrHandler();
						 list=usmhandler.getUserList();
						 for(UsrMast ubean:list)
						 { %>
						  <option value="<%=ubean.getUSERID() %>" ><%=ubean.getUNAME() %></option>
						 <%} %>
						  
				    </select>
					
					</td>
					<td width="67">Role ID</td>
					<td width="203">
					<select name="role" id="role" style="width:100px;">
                      <option value="Default" selected="selected"  >Select</option>
                      <%
							RoleDAO RDAO1 = new RoleDAO();
                      		ArrayList<RoleBean> roles1 = RDAO.getAllRole();
                      		for(RoleBean RB : roles)
                      		{
                      %>
                      <option value="<%=RB.getROLEID()%>"><%=RB.getROLENAME()%></option>
                      <%
                      		}
                      %>
                    
                    </select>			</td>
				
				<tr class="alt"><td> Status</td> <td><select name="status" id="status" style="width:100px">
						<option value="Default" selected="selected">Select</option>
						<option value="Active">Active</option>
						<option value="InActive">InActive</option>
					</select> </td> <td colspan="2"> </td></tr>
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">				  &nbsp; &nbsp;
				  <input type="reset" value="Cancel"></td></tr>
				</table>
			
			</form>
			<br>
			<table width="475" id="customers">
				<tr>
				  
				 <th width="70">UserName</th>
				<th width="70">UserID</th>
				   			
					<th width="65">Role ID</th>
					
					<th width="85">Status</th>
					<th width="85">LastModifyDate</th>
					<th width="60">Edit</th>
				</tr>
			 
			<%
			
			if(action.equals("showroleList")||action.equalsIgnoreCase("updateRole"))
			{
				String rolename;
				RoleDAO roledao=new RoleDAO();
				int i=0;
				int u=0;
				for(RoleBean Rlbean:Rolelist)
				{
				    i=Rlbean.getROLEID();
					 rolename=roledao.getroleName(i);
			%>
			
			<tr>  
			
			<% 
			int userid=Rlbean.getUSERID();
			ArrayList<UsrMast> list1=new ArrayList<UsrMast>();
						 UsrHandler usmhandler1=new UsrHandler();
						 list=usmhandler1.getUserList();
						 for(UsrMast ubean:list)
						 {
							 if(userid== ubean.getUSERID())
							 {
							 
							 %>
			<td width="60"><%=ubean.getUNAME()%>  </td> 
			<%}
						 }				 
							 %>
			<td width="60"><%=Rlbean.getUSERID() %>  </td> 
			<td width="65"><%=rolename %> </td>
			<td width="85"><%=Rlbean.getSTATUS() %></td>
			<td width="85"><%=Rlbean.getLASTMOD_DATE() %></td>
			 
			 
				<td><input type="button" value="Edit" onClick="window.location='testMenu.jsp?action=updateRole&useroleid=<%=Rlbean.getUSER_ROLE_ID()%>'"/></td>
				</tr>
			 <%
			   } 
			}
			 %>
				<tr class="alt"><th colspan="7"></th></tr>
			
			</table>
			 
			<br>
			<%
			if(action.equalsIgnoreCase("updateRole"))
			{
				String userid=request.getParameter("useroleid")==null?"":request.getParameter("useroleid");
				for(RoleBean Rlbean:Rolelist)
				{
					if(userid.equalsIgnoreCase(Integer.toString(Rlbean.getUSER_ROLE_ID())))
					{
			%>
			<div  id="update" name="update">
			<form action="RoleServlet?action=EdituserRole" method="post">
				<table id="customers" align="center">
				
				<th colspan="4">Edit UserRole</th>
				<tr class="alt"> 
					<td width="63">UserID</td>
					<td width="158">
					   <input type="text" name="userid" value="<%=Rlbean.getUSERID()%>"/>
					
					</td>
					<td width="67">Role ID</td>
					<td width="210">
					<select name="role" id="role" style="width:100px;">
                       
                      <%
							RoleDAO RDAO2 = new RoleDAO();
                      		ArrayList<RoleBean> roles2 = RDAO.getAllRole();
                      		for(RoleBean RB : roles2)
                      		{
                      			
                      			//System.out.print("user_rol_id--- "+RB.getUSER_ROLE_ID()+" rol id---"+RB.getROLEID());
                      if(RB.getROLEID() == Rlbean.getROLEID())	
                      {
                    	  System.out.println("userid is"+userid+"rollid "+RB.getROLEID());
                      %>
                      <option value="<%=RB.getROLEID()%>" selected="selected"><%=RB.getROLENAME()%></option>
                      <%}
                      else
                      {%>
                      
                       <option value="<%=RB.getROLEID()%>"><%=RB.getROLENAME()%></option>
                       <%} %>
                      <%
                      		}
                      %>
                    </select>			</td>
				<tr class="alt"><td> Status</td> <td><select name="status" id="status" style="width:100px;">
						
						<%if(Rlbean.getSTATUS().equalsIgnoreCase("Active")) 
						{
						%>
						<option value="Active" selected="selected">Active</option>
						<option value="InActive">InActive</option>
						<%}
						else
						{%>
						<option value="InActive" selected="selected">InActive</option>
						<option value="Active">Active</option>
						<%} %>
					</select> </td> <td colspan="2"> </td>
					
					<input type="hidden" name="uid" value="<%=Rlbean.getUSER_ROLE_ID()%>"/>
					</tr>
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">	 &nbsp; &nbsp;
				  <input type="button" value="Cancel" onClick="Showhide()"></td></tr>
				</table>
			</form>
			</div>
			<%}
				}
			}
					%>
			          </td>
			 </tr>  
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
	<div class="clear">&nbsp;</div>

</div>

<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->
<div class="clear">&nbsp;</div>
</body>
</html>