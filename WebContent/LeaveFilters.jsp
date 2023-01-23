<%@page import="payroll.Core.LeaveFilter"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Core.Filters"%>
<%@page import="payroll.Model.FilterValues"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/filter.js"></script>
<title>Employee Selection Filters</title>
<%
 
     int elist =-1;
     int flag1=-1;
	ArrayList<FilterValues> result = new ArrayList<FilterValues>();
	LeaveFilter filter = new LeaveFilter();
	String action = new String();
	String key = new String();
	String forWhat = new String();
	String date  = new String();
	String type=new String();
	try
	{
		
		flag1 =Integer.parseInt(request.getParameter("flag1")==null?"-1":request.getParameter("flag1"));
		action = request.getParameter("action");
		System.out.println("hiii  this "+action);

		key = request.getParameter("key");
		System.out.println("hiii  this "+key);

		forWhat = request.getParameter("forWhat");
		System.out.println("hiii  this "+forWhat);

		date  = request.getParameter("date");
		System.out.println("hiii  this "+date);

		type=request.getParameter("type");
		System.out.println("hiii  this "+type);

		if(action.equalsIgnoreCase("alpha"))
		{
			result = filter.getAlphabeticalList(key,forWhat,date,type);
		}
		else if(action.equalsIgnoreCase("dept"))
		{
			result = filter.getDeptWiseList(Integer.parseInt(key),forWhat,date,type);
		}
		else if(action.equalsIgnoreCase("desig"))
		{
			result = filter.getDesigWiseList(Integer.parseInt(key),forWhat,date,type);
		}
		else if(action.equalsIgnoreCase("grade"))
		{
			System.out.println("hiii  this jsp calling grade ");

			result = filter.getGradeWiseList(Integer.parseInt(key),forWhat,date,type);
		}
		else if(action.equalsIgnoreCase("all"))
		{
			System.out.println("hiii  this jsp calling all ");
			result = filter.getAllEmpList(forWhat,date, type);
		}else if(action.equalsIgnoreCase("branch"))
		{
			result = filter.getProjectWiseList(Integer.parseInt(key),forWhat,date,type);
		}
		
	}
	catch(Exception e)
	{
		
	}
	
	LookupHandler lkph = new LookupHandler();
	ArrayList<Lookup> deptList = lkph.getSubLKP_DESC("DEPT");
	ArrayList<Lookup> desigList = lkph.getSubLKP_DESC("DESIG");
	ArrayList<Lookup> gradeList = lkph.getSubLKP_DESC("GD");
%>
<style type="text/css">
body{
	font-family: helvetica;
	font-size: 12px;

}
</style>

</head>
<body onload="showSelected()" >
<input type="hidden" id="hidAction" value="<%=action%>">
<input type="hidden" id="hidKey" value="<%=key%>">
<center><br/>
	<table border="1" id="customers">
	<tr>
		<th>Show All</th>
		<th>Alphabetical</th>
		<th>Department Wise</th>
		<th>Designation Wise</th>
		<th>Project Wise</th>
		<th>Grade Wise</th>
	</tr>
	<tr>
		<td><input type="button" class="myButton" value="Show All" onclick="getResult2('all','<%=forWhat%>','<%=date%>','<%=type%>')"></td>
		<td>
			<select id="alpha" name="alpha" onchange="getResult2('alpha','<%=forWhat%>','<%=date%>','<%=type%>')">
				<option value="-1">Select</option>
				<option value="A">A</option>
				<option value="B">B</option>
				<option value="C">C</option>
				<option value="D">D</option>
				<option value="E">E</option>
				<option value="F">F</option>
				<option value="G">G</option>
				<option value="H">H</option>
				<option value="I">I</option>
				<option value="J">J</option>
				
				<option value="K">K</option>
				<option value="L">L</option>
				<option value="M">M</option>
				<option value="N">N</option>
				<option value="O">O</option>
				<option value="P">P</option>
				<option value="Q">Q</option>
				<option value="R">R</option>
				<option value="S">S</option>
				<option value="T">T</option>
				<option value="U">U</option>
				<option value="V">V</option>
				<option value="W">W</option>
				<option value="X">X</option>
				<option value="Y">Y</option>
				<option value="Z">Z</option>
			</select>
		</td>
		<td>
			<select id="dept" name="dept" onchange="getResult2('dept','<%=forWhat%>','<%=date%>','<%=type%>')">
				<option value="-1">Select</option>
				<%
					for(Lookup lkp:deptList)
					{
				%>
					<option value="<%=lkp.getLKP_SRNO()%>"><%=lkp.getLKP_DESC()%></option>
				<%
					}
				%>
			</select>
		</td>
		<td>
			<select id="desig" name="desig" onchange="getResult2('desig','<%=forWhat%>','<%=date%>','<%=type%>')">
				<option value="-1">Select</option>
				<%
					for(Lookup lkp:desigList)
					{
				%>
					<option value="<%=lkp.getLKP_SRNO()%>"><%=lkp.getLKP_DESC()%></option>
				<%
					}
				%>
			</select>
		</td>
		<td>
		<select id="branch" name="branch" onchange="getResult2('branch','<%=forWhat%>','<%=date%>','<%=type%>')">
      					 <option value="0">Select</option>  
    						<%
    						EmpOffHandler ofh = new EmpOffHandler();
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						
    						list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
 							%>
      						<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getPrj_name()%>"><%=lkb.getPrj_code()%></option>
      						  
     					 	<%
     					 	}
     					 	%>
     					 	</select>
		</td>
		<td>
			<select id="grade" name="grade" onchange="getResult2('grade','<%=forWhat%>','<%=date%>','<%=type%>')">
			
				<option value="-1">Select</option>               
				<%
					for(Lookup lkp:gradeList)
					{
				%>
					<option value="<%=lkp.getLKP_SRNO()%>"><%=lkp.getLKP_DESC()%></option>
				<%
					}
				%>
			</select>
		</td>
	</tr>
		
	</table>
	<br/>
	<%
		if(result.size()>0)
		{
	%>
			<%=result.size() %>&nbsp; Records found...
	<%
		}
	%>
	<input type="button" class="myButton" value="Check All" onclick="check('checkAll')">
	<input type="button" class="myButton" value="Uncheck All" onclick="check('unCheckAll')">
	<input type="button" class="myButton" value="Toggle" onclick="check('toggle')">
	<br/><br/>
	<div style="overflow-y:scroll;margin-bottom: 0px;">
		<table border="1" id="customers">
			<tr height="32">
				<th width="50">Select</th>
				<th width="50">EmpCode</th>
				<th width="250">Name</th>
				<th width="50">Select</th>
				<th width="50">EmpCode</th>
				<th width="250">Name</th>
				<th width="50">Select</th>
				<th width="50">EmpCode</th>
				<th width="250">Name</th>
			</tr>
			</table>
	</div>
		<div style="max-height:400px;overflow-y:scroll;margin-top: 0px; ">
	<tr>
	<td><font size="5">
     
	<%
		if(result.size()<=0 && flag1 >-1)
		{
	%>
			There is No Employee Left For Auto Credit For This Year...!
	<%
		}
		else{
		
	%></font></td>
		</tr>
			<table border="1" id="customers">
				<%
					FilterValues f = new FilterValues();
					int j=0,k=0;
					int min = (result.size() / 3) + (result.size() % 3);
					elist = min;
					System.out.println("getAllEmpList11  "+elist);
					for(int i = 0;i<min;i++)
					{
						f = result.get(i);
				%>
						<tr>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>" name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
				<%
						j = i+min;
						if(j<result.size())
						{
							f = result.get(j);
							
				%>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>"  name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
				<%		
						}
						else
						{
				%>
							<td width="50" align="center">&nbsp;</td><td width="50" align="center">&nbsp;</td><td width="250">&nbsp;</td>
				<%			
						}
						k = j+min;
						if(k<result.size())
						{
							f = result.get(k);
				%>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>" name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
						
				<%
						}
						else
						{
				%>		<td width="50" align="center">&nbsp;</td><td width="50" align="center">&nbsp;</td><td width="250">&nbsp;</td>
				<%			
						}
				%>
						</tr>
				<%		
					}
		
				%>
		</table	>
		<%		
					}
		
				%>
	</div>
	<%-- <div style="max-height:400px;overflow-y:scroll;margin-top: 0px; ">
			<table border="1" id="customers">
				<%
					FilterValues f = new FilterValues();
					int j=0,k=0;
					int min = (result.size() / 3) + (result.size() % 3);
					for(int i = 0;i<min;i++)
					{
						f = result.get(i);
				%>
						<tr>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>" name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
				<%
						j = i+min;
						if(j<result.size())
						{
							f = result.get(j);
							
				%>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>"  name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
				<%		
						}
						else
						{
				%>
							<td width="50" align="center">&nbsp;</td><td width="50" align="center">&nbsp;</td><td width="250">&nbsp;</td>
				<%			
						}
						k = j+min;
						if(k<result.size())
						{
							f = result.get(k);
				%>
							<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>" name="<%=f.getEMPCODE()%>"></td>
							<td width="50" align="center"><%=f.getEMPCODE() %></td>
							<td width="250"><%=f.getNAME()%></td>
						
				<%
						}
						else
						{
				%>		<td width="50" align="center">&nbsp;</td><td width="50" align="center">&nbsp;</td><td width="250">&nbsp;</td>
				<%			
						}
				%>
						</tr>
				<%		
					}
				%>
		</table>
	</div> --%>
	<br/>
	
	<input type="button" class="myButton" value="OK" onclick="getSelectedValues()">
	<input type="button" class="myButton" value="Cancel" onclick="closeFilter()">
	<input type="hidden" name="flag1" id="flag1" value="<%=flag1%>">
</center>
</body>
</html>