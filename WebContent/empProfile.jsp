<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Model.EmpAddressBean"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.EmpAwardBean"%>
<%@page import="payroll.Model.EmpExperBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.EmpFamilyBean"%>
<%@page import="payroll.Model.EmpQualBean"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmpAwrdHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.DAO.EmpExperHandler"%>
<%@page import="payroll.DAO.EmplQulHandler"%>
<%@page import="payroll.DAO.EmpAddrHandler"%>
<%@page import="payroll.DAO.EmpFamHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->
<% String empno = session.getAttribute("EMPNO").toString();
 EmployeeHandler emp = new  EmployeeHandler();
EmpFamHandler fam = new EmpFamHandler();
EmpAddrHandler addr= new EmpAddrHandler(); 
EmplQulHandler qual = new EmplQulHandler();
EmpExperHandler exp = new EmpExperHandler();
EmpOffHandler off = new EmpOffHandler();
EmpAwrdHandler award = new EmpAwrdHandler();

 EmployeeBean empbean = emp.getEmployeeInformation(empno);
ArrayList<EmpFamilyBean> empfamilylist = fam.getEmpFamily(empno); 
ArrayList<EmpQualBean> empqullist =qual.getEmpQual(empno);
ArrayList<EmpAddressBean> empaddrlist = addr.getEmpAddress(empno);
ArrayList<EmpExperBean> empexperlist = exp.getEmpExper(empno);
ArrayList<EmpAwardBean> empawardlist=award.getEmpAwardInfo(empno);
ArrayList<EmpOffBean> empofflist = off.getEmpOfficInfo(empno);

%>

<script language="javascript">
function Clickheretoprint()
{ 
		var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
		    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
		var content_vlue = document.getElementById("print_content").innerHTML; 
		
		var docprint=window.open("","",disp_setting); 
			docprint.document.open(); 
			docprint.document.write('<html><head><title>Inel Power System</title>'); 
			docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
			docprint.document.write(content_vlue);          
			docprint.document.write('</center></body></html>'); 
			docprint.document.close(); 
			docprint.focus(); 
}
</script>

</head>
<style >
#headerlist
{
color:#CCCCCC;
}
</style>
<body style="overflow:hidden; " > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>

 <% LookupHandler lh = new LookupHandler();
 EmpOffHandler ofh = new EmpOffHandler(); %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:78%; " >
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee Details </h1>
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
				<a href="javascript:Clickheretoprint()">	Click here to print</a> 
				<div align="center" id="print_content" style="height: auto;">
					
					<table border="1" id="customers"	>
						<tr>
							<td valign="top">
								<table width="465" border="1">
								<tr><th colspan="4">Employee</th></tr>
								<tr><td width="103">Employee Name</td>
								<td width="193"><%=empbean.getFNAME() %> <%=empbean.getLNAME() %></td>
								<td width="92">Employee Id</td>
								<td width="57"><%=empbean.getEMPNO() %></td>
								</tr>
								<%
								for(EmpAddressBean empaddrbean : empaddrlist)
								{
									if(empaddrbean.getADDRTYPE().equalsIgnoreCase("CA"))
								{
									%>
									<tr>
								  <td>  Local Address :-</td><td colspan="3"> <%=empaddrbean.getADDR1() %> <%=empaddrbean.getADDR2() %> <%=empaddrbean.getADDR3() %></td>
								  <tr><td></td><td colspan="3"> <%=lh.getLKP_Desc("CITY", empaddrbean.getCITY())  %> <%=lh.getLKP_Desc("STATE", empaddrbean.getSTATE()) %> <%=empaddrbean.getPIN() %> Contact No:- <%=empaddrbean.getTELNO() %></td></tr>
								
									<%
								}
								if(empaddrbean.getADDRTYPE().equalsIgnoreCase("PA"))
								{
									%>
									<tr>
								  <td>  Permanent Address :- </td><td  colspan="3"><%=empaddrbean.getADDR1() %> <%=empaddrbean.getADDR2() %> <%=empaddrbean.getADDR3() %></td>
								  <tr><td></td><td  colspan="3"> <%=lh.getLKP_Desc("CITY", empaddrbean.getCITY()) %> <%=lh.getLKP_Desc("STATE", empaddrbean.getSTATE()) %> <%=empaddrbean.getPIN() %> Contact No:- <%=empaddrbean.getTELNO() %></td></tr>
								
									<%
								}
								}
								%>
								
					  </table>
							</td>
							<td valign="top">
							<table>
					   			<tr><th>PHOTO </th><th> SIGN</th></tr>
					 			<tr>
					  					<td><img width='150' height='80' id="profile" style="z-index: 7;" src="DisplayPhotoServlet?type=PHOTO&empno=<%=empbean.getEMPNO() %>" ></td>
					  					<td><img width='150' height='80' id="sign" style="z-index: 7;" src="DisplayPhotoServlet?type=SIGN&empno=<%=empbean.getEMPNO() %>" ></td>
					  			</tr>		
					  
					 		</table>
							<!-- <td valign="top"> -->
								<table width="443" border="1">
							<tr><th colspan="4">Personal Details</th></tr>
							<tr><td width="93">Gender</td>
							<td width="94"><%=empbean.getGENDER() %></td>
							<td width="102">Date of Birth</td>
							<td width="134"><%=empbean.getDOB() %></td>
							</tr>
							<tr><td>Place of Birth</td><td><%=empbean.getBPLACE() %></td><td>Residancy</td><td><%=empbean.getRESIDSTAT() %></td></tr>
							<tr><td>Marital Status</td><td><%=empbean.getMARRIED() %></td><td>Marriage Date</td><td><%=empbean.getMARRIEDDT() %></td></tr>
							<tr><td>Blood Group</td><td><%=empbean.getBGRP() %></td><td>Handicap</td><td><%=empbean.getDISABILYN() %></td></tr>											
					  </table> 
							</td>
						</tr>
					<tr><td colspan="3">
								<table width="922" border="1" >
								<tr><th colspan="8">Family Information</th></tr>
								<tr bgcolor="#CCCCCC" ><td width="55">SrNo</td>
								<td width="166">Name</td>
								<td width="69">Relation</td>
								<td width="58">Gender</td>
								<td width="61">DOB</td>
							<td width="131">Education</td>
								<td width="70">Depended</td>
								<td width="122">Occupation</td>
								</tr>
							   <%for(EmpFamilyBean fambean :empfamilylist)
								{
									
									%>
								
								<tr><td><%=fambean.getSRNO() %></td><td><%=fambean.getNAME() %></td><td><%=lh.getLKP_Desc("RELN", fambean.getRELATION()) %></td><td><%=fambean.getGENDER() %></td><td><%=fambean.getDOB() %></td>
								<td><%=lh.getLKP_Desc("ED", fambean.getQUALI())%></td><td><%=fambean.getDEPENDYN() %></td><td><%=fambean.getOCCUPATION() %></td></tr>
								<%} %>   
						</table>
					</td></tr>
						<tr>
							<td>
								<table width="465" border="1">
							<tr><th colspan="4">Official Details</th></tr>
							<tr><td width="119">Employee Type</td>
							<td width="60"><%=empbean.getEMPTYPE() %></td>
							<td width="120">Joining Date</td>
							<td width="60"><%=empbean.getDOJ() %></td>
							</tr>
							<tr><td>MLWF No</td><td><%=empbean.getMLWFNO() %></td><td>PF Joining Date</td><td><%=empbean.getPFOPENDT() %></td></tr>
							<tr><td>PF Nominee</td><td><%=empbean.getPFNOMINEE() %></td><td>Nominee Relation</td><td><%=empbean.getPFNOMIREL() %></td></tr>
							<tr><td>Security Bond</td><td><%=empbean.getSBOND() %></td><td>Security Amount</td><td><%=empbean.getDEPAMT() %></td></tr>
				    </table>
							</td>
							<td valign="top">
							<table width="443" border="1">
							<tr><th colspan="4">Religion Details</th></tr>
							<tr><td width="112">Cast</td><td width="202"><%=lh.getLKP_Desc("CASTE", empbean.getCASTCD())  %></td>
							</tr>
							<tr><td>Category</td><td><%=lh.getLKP_Desc("CATE", empbean.getCATEGORYCD())   %></td></tr>
							<tr><td>Religion</td><td><%=lh.getLKP_Desc("RELIG", empbean.getCATEGORYCD()) %></td></tr>
					  </table>
							</td>
						</tr>
						
						
						<tr>
							<td colspan="2">
											<table width="920" border="1">
							<tr><th width="95" colspan="5">Qualification</th></tr>
							<tr  bgcolor="#CCCCCC"><td width="64">Sr No</td><td width="163">Degree</td>
							<td width="326">University</td>
							<td width="70">Percentage</td>
							<td width="84">Year of Passing</td>
							</tr>
							
							<% for(EmpQualBean qualbean : empqullist)
								{%>
						
							<tr><td><%=qualbean.getSRNO() %></td><td><%=lh.getLKP_Desc("ED",qualbean.getDEGREE()) %></td><td><%=qualbean.getINST() %></td>
							<td><%=qualbean.getPERCENT() %></td><td><%=qualbean.getPASSYEAR() %></td></tr>
							<%} %>
					  </table>
							</td>
							
						</tr>
						
						
						
						<tr>
							<td colspan="2">
								<table width="920" border="1">
								<tr><th width="76" colspan="6">Experience</th>
								</tr>
								<tr  bgcolor="#CCCCCC"><td>SrNo</td><td width="309">Organization Name</td>
								<td width="81">From Date</td>
								<td width="84">To Date</td>
								<td width="190">Position</td>
								<td width="98">Salary</td>
								</tr>
								<%
								for(EmpExperBean expbean: empexperlist)
								{
								%>
								<tr><td><%=expbean.getSRNO() %></td><td><%=expbean.getORGNAME() %></td><td><%=expbean.getFROMDT() %></td><td><%=expbean.getTODT() %></td><td><%=expbean.getPOST() %></td>
								<td><%=expbean.getSALARY() %></td></tr>
								<%} %>
					  </table>
							</td>
						</tr>
						
						<tr>
							<td colspan="2">
								<table width="920" border="1">
								<tr><th colspan="8">Posting Details</th>
								</tr>
								<tr  bgcolor="#CCCCCC"><td width="32">SrNo</td><td width="38">Ord_No</td>
								<td width="0">Ord_Date</td>
								<td width="365">Branch</td>
								<td width="110">Acc.No</td>
								<td width="560">Grade</td>
								<td width="773">Designation</td>
								<td width="221">Department</td>
								</tr>
								<%
								for(EmpOffBean offbean : empofflist)
								{
								%>
								<tr><td><%=offbean.getSRNO() %></td><td><%=offbean.getORDER_NO() %></td><td><%=offbean.getORDER_DT() %></td><td><%=ofh.getDescrption("BRNAME","BRANCH","BRCD",offbean.getBRANCH()) %></td><td><%=offbean.getACNO() %></td>
								<td><%=ofh.getDescrption("DISC","GRADE","POSTCD",offbean.getGRADE()) %></td><td><%=lh.getLKP_Desc("DESIG", offbean.getDESIG())  %></td><td><%=lh.getLKP_Desc("DEPT",  offbean.getDEPT()) %></td></tr>
								<%
								}
								%>
						  </table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table width="920" border="1">
								<tr ><th width="98" colspan="5">Awards Details</th>
								</tr>
								<tr  bgcolor="#CCCCCC"><td width="40">SrNo</td><td width="58">Ord_No</td>
								<td width="160">Transaction</td>
								<td width="44">Date</td>
								<td width="468">Description</td>
								</tr>
								<%
								for(EmpAwardBean awardbean : empawardlist )
								{
								%>
								<tr><td><%=awardbean.getSRNO() %></td><td><%=awardbean.getORDER_NO() %></td><td><%=lh.getLKP_Desc("AWARD",awardbean.getTRNCD()) %></td>
								<td><%=awardbean.getEFFDATE() %></td><td><%=awardbean.getMDESC() %></td>
								<%} %>
								</tr>
						  </table>
							</td>
						</tr>
						
					</table>	
				</div>
					
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