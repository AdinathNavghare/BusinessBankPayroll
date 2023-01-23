<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.io.*, java.sql.*,java.util.*"%>
<%@page import="payroll.Controller.DbfInsert_DataServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
		var xmlhttp;
		var url = "";
		if(window.XMLHttpRequest)
		{
			xmlhttp = new XMLHttpRequest;
		}
		else //if(window.ActivXObject)
		{   
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function RemoveAttachment(srno,empno)
		{
			var flag = confirm("Remove Attachment ?");
			if(flag)
			{
				url = "EmployeeServlet?action=Remove_Dbf&srno="+srno+"&empno="+empno;
				
				xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState == 4)
					{
						var response = xmlhttp.responseText;
						if(response == "removed")
						{
							window.location.href = "attach_Dbf.jsp";
						}
						else
						{
							alert("Error in removing attachment.!");
						}
					}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
			}
		}
		function savedata(srno,empno){
			var flag = prompt("Enter the month & year in MM/YYYY format.");
			if(flag){
				var chk = confirm("Are you really want to Save for "+flag);
				if(chk){
					alert("hopki0okopkopki");
					window.location.href = "DbfInsert_DataServlet?action=saveData&srno="+srno+"&empno="+empno+"&date="+flag;
				}else{
					return false;
				}
			}
			
			/* return false; */
		}
		</script>
</head>

<table border="1" id="customers" >
	<tr>
		<td colspan="6"><h3>Your Document Attachments Are</h3></td>
	</tr>
	<tr>
		<th>Document Name</th>
		<!-- <th>Document Type</th> -->
		<th>File Name</th>
		<th>Document Description</th>
		<th>show files</th>
		<th>save in DB</th>
		<th>Remove</th>
	</tr>
<%
try
{
	
final String SAVE_DIR1 = "uploadFiles";
String appPath1 = request.getServletContext().getRealPath("");
//constructs path of the directory to save uploaded file
String savePath1 = appPath1 + File.separator + SAVE_DIR1;

Connection con = ConnectionManager.getConnection();
Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs = st.executeQuery("select * from  Dbfstorefile order by srno ");

%>

  
<%
if(!rs.next())
{
%>
	<tr><td colspan="6">There Is No Attachment</td></tr>
<%
}

rs.beforeFirst();
while(rs.next())
{
	if(!(rs.getString("filename").equals("")))
	{
	
	%>
	<tr align="center">
		<td><%=rs.getString("doc_name")==null?"Not Mention":rs.getString("doc_name")%></td>
		<%-- <td><%=rs.getString("doc_type")==null?"Not Mention":rs.getString("doc_type")%></td> --%>
		<td align="left"><%=rs.getString("filename")==null?"Not Mention":rs.getString("filename")%></td>
		<td><%=rs.getString("doc_desciption")==null?"Not Mention":rs.getString("doc_desciption")%></td>
		<td><a href="display_Dbf.jsp?f=<%=rs.getString("ATTACHPATH")%>">Show data</a></td>
		<td><input type="button" value="Save" onclick="savedata('<%= rs.getString("srno")%>','<%= rs.getString("empno")%>')"></input></td>
		<td><a href="" onclick="RemoveAttachment('<%= rs.getString("srno")%>','<%= rs.getString("empno")%>')">Remove</a></td>
	</tr>
	<%
	}
}   
  
%>
     <%
}catch(Exception e)
{
e.printStackTrace();	
}
%>
</table>
</html>