<%@page import="java.io.*, java.sql.*,java.util.*,payroll.DAO.ConnectionManager"%>


<html>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<table border="1" id="customers" >


<tr><td colspan="6"><h3>Your Document Attachments Are</h3></td></tr>
<tr><th>Download File</th><th>Document Name</th><th>Document Type</th><th>File Name</th><th>Document Description</th><th></th>
<%
try
{
	
final String SAVE_DIR1 = "uploadFiles";
String appPath1 = request.getServletContext().getRealPath("");
//constructs path of the directory to save uploaded file
String savePath1 = appPath1 + File.separator + SAVE_DIR1;

Connection con = ConnectionManager.getConnection();
Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
ResultSet rs = st.executeQuery("select * from ATTACHMENT  where EMPNO="+session.getAttribute("empno"));

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
	<tr>
	<td><a href="download?act=emp&f=<%=rs.getString("srno")%>">Download File</a></td>
	<td><%=rs.getString("doc_name")==null?"Not Mention":rs.getString("doc_name")%></td>
	<td><%=rs.getString("doc_type")==null?"Not Mention":rs.getString("doc_type")%></td>
	<td><%=rs.getString("filename")==null?"Not Mention":rs.getString("filename")%></td>
	<td><%=rs.getString("doc_desciption")==null?"Not Mention":rs.getString("doc_desciption")%></td>
	<td width="10%" align="center">
						<a	class="btn btn-default btn-xs purple "
						<%-- onclick="detect(<%=session.getAttribute("empno")%>,'<%=rs.getString("filename")%>')"><i class="fa fa-trash-o"></i></a> --%>
						onclick="detect(<%=session.getAttribute("empno")%>,<%=rs.getString("SRNO")%>)"><i class="fa fa-trash-o"></i></a>
							</td>
							
							<%-- <input type="hidden" name="empno" id="empno" value="<%=session.getAttribute("empno")%>">
<input type="hidden" name="srno" id="srno" value="<%=rs.getInt("SRNO")%>"> --%>
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


<script type="text/javascript">
function detect(empno,filename){
	 try
	 {
		 
		/*  var empno=document.getElementById("empno").value
		 var srno=document.getElementById("srno").value */
		 var act=document.getElementById("key").value;
		var xmlhttp=new XMLHttpRequest();
	 	var url="";
	 	var response="";
	 	 url="AttachFile?action=deleteFile&empno="+empno+"&filename="+filename; 
	 	
	 	
	 	let s=confirm("do you want to delete this record?");/*+ window.location.replace("ProjectViewAll.jsp")  */
		 if(s)
			 {
			 	xmlhttp.onreadystatechange=function()
				{
					if(xmlhttp.readyState==4)
					{	
					response = xmlhttp.responseText;
					
					
					 if(response>0 )
						{
						 if(act=="editemp" || act=="addemp")
							 {
							 			 alert("Record Deleted Successfully!!!"+ window.location.replace("Attachment.jsp?action=editemp&list=show"))
						 				return false;
							 }
						 else
							 {
								 alert("Record Deleted Successfully!!!"+ window.location.replace("Attachment.jsp?action=showemp&list=show"))
				 				return false;
							 
							 }
						}
					 else
					 {
							alert("Record Does not Deleted........Some Error Occure");	
					 }
					}
				};
				xmlhttp.open("POST", url, true);
				xmlhttp.send();
			 }
		}
		catch(e)
		{
			
		}
}
 </script>
</html>