<%@page import="java.io.*, java.sql.*,java.util.*,payroll.DAO.ConnectionManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
		<%
			String msg = request.getParameter("msg")==null?"":request.getParameter("msg");
		%>
	</head>
	<body style="overflow: hidden;" >
			<div id="content-outer">
				<div id="content">
					<div id="page-heading">
						<center><h1>Your Document Attachments Are</h1>
								<h3 align="center" style="color:red;"><%=msg%></h3>
						</center>
					</div>
						<center>
						<div id="scrolling" style="height: 200px; overflow-y: scroll; background-color: #FFFFFF;" align="center">		
							<table border="1" id="customers" >
								<tr>
									<th>Download File</th>
									<th>Uploaded Date</th>
									<th>File Name</th>
									<!-- <th>Remove File</th> -->
								</tr>	
									<%
										try
											{
												final String SAVE_DIR1 = "uploadFiles";
												String appPath1 = request.getServletContext().getRealPath("");
												String savePath1 = appPath1 + File.separator + SAVE_DIR1;
												Connection con = ConnectionManager.getConnection();
												Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
												ResultSet rs = st.executeQuery("select * from SiteWise_File_Upload  where PRJ_SRNO="+session.getAttribute("Prj_Srno")+"order by srno");
												if(!rs.next())
													{
									%>
														<tr>
															<td colspan="3"><h3>There Are No Files Uploaded By U !!! </h3></td>
														</tr>
									<%
													}
													rs.beforeFirst();
													while(rs.next())
													{
														if(!(rs.getString("filename").equals("")))
															{
									%>
																<tr>
																	<td><a href="download?act=site&f=<%=rs.getInt("srno")%>"><img src="images/dwnld-bun.png" height="30" /> </a></td>
																	<%-- <td><%=rs.getInt("PRJ_SRNO")==0?"Not Mention":rs.getInt("PRJ_SRNO")%></td> --%>
																	<td><%=rs.getString("upddt")==null?"Not Mention":rs.getString("upddt")%></td>	
																	<td><%=rs.getString("filename")==null?"Not Mention":rs.getString("filename")%></td>
																	<%-- <td><a href="download?act=delete&f=<%=rs.getInt("srno")%>&p=<%=rs.getInt("PRJ_SRNO")%>"><img src="images/delete.png" height="30" width="45"  /> </a></td> --%>
																</tr>
									<%
															}
													}        
											}catch(Exception e)
											{
											e.printStackTrace();	
											}
									%>
							</table>
						</div>	
						</center>
					</div>
				</div>
	</body>
</html>