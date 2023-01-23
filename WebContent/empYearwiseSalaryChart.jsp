<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="payroll.DAO.*,java.util.*"%>
<%@page import="payroll.Model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
		<meta name="viewport" content="width=1024">
		<title>Year wise Total Salary Chart</title>
		<link rel="stylesheet" href="css/cGraph1.css">
		 <link rel="stylesheet" href="css/Chart1.css"> 
		<script type="text/javascript">
		function setYear(str)
		{
		var y1=parseInt(str);
	
		//document.getElementById("yr").innerHTML = y1+2;
		document.getElementById("yr1").innerHTML = y1;
		document.getElementById("yr2").innerHTML = ++y1;
		document.getElementById("yr3").innerHTML = ++y1;
		document.getElementById("graphForm").submit();
		}
		
		
		function Clickheretoprint(panel)
		{ 
			
				var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
				    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
				var content_value = document.getElementById(panel).innerHTML; 
				
				var docprint=window.open("","",disp_setting); 
					docprint.document.open(); 
					docprint.document.write('<html><head><title>Inel Power System</title> <link rel="stylesheet" href="css/cGraph1.css"> <link rel="stylesheet" href="css/Chart1.css">'); 
					docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
					docprint.document.write(content_value);          
					docprint.document.write('</center></body></html>'); 
					docprint.document.close(); 
					docprint.focus(); 
		}
		
		
	function getYr()
	{

		var d = new Date();
		var n = d.getUTCFullYear();
		document.getElementById("year").value=n;
	document.getElementById("year").innerHTML ="   <option selected='selected'> Select</option>  <option value='"+(n-=2)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option> <option value='"+(n-=1)+"'> "+n+"</option>";
	
		
	}	
		</script>
	</head>
	<body>
		
	
		<%
		String year1=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());
		
		int year2=Integer.parseInt(year1);
		SalryGraphBean sgb1=new SalryGraphBean();
		SalryGraphBean sgb2=new SalryGraphBean();
		SalryGraphBean sgb3=new SalryGraphBean();
		int year3=(request.getParameter("year")==null?year2:Integer.parseInt(request.getParameter("year")));
		//System.out.print("dat1    :"+year2);
		//System.out.print("Date 2    : "+year3);
		
		if(request.getParameter("year")=="" || request.getParameter("year")==null)
		{
			int temp1=year2-2,temp2=temp1+1,temp3=temp2+1;
			
			
			//System.out.println("\n request value = null      Date 1  .   : "+temp1);
			//System.out.println(" request value = null      Date 2  ..  : "+temp2);
			//System.out.println(" request value = null      Date 3  ...  : "+temp3);
			//System.out.println("year is ---------------:"+request.getParameter("year"));
			sgb1.setYear(temp1);
			sgb2.setYear(temp2);
			sgb3.setYear(temp3);
		}
		else
		{
			
			int temp1=year3,temp2=temp1+1,temp3=temp2+1;
			//System.out.println("\ndate 1    : "+temp1);
			//System.out.println("Date 2    : "+temp2);
			//System.out.println("Date 3    : "+temp3);
			sgb1.setYear(temp1);
			sgb2.setYear(temp2);
			sgb3.setYear(temp3);
		}
	
		
		
		GraphHandler gh=new GraphHandler();
		
		SalryGraphBean gb1= gh.getTotalAmt(sgb1);
		SalryGraphBean gb2= gh.getTotalAmt(sgb2);
		SalryGraphBean gb3= gh.getTotalAmt(sgb3);
	
		
		
		%>
		
		
		
		
		
		<div align="center">
		<a href="javascript:Clickheretoprint('ch11')">	Click here to print</a>
			<div class="chart" id="ch11">
				<div>
				<table width="700" border="">
				<tr align="left">
				<td>
				<div style="width: 150px;float:left; ">
				<h2>Select Year : </h2> </div>
				<div style="float:left;" align="left">
			      <form action="empYearwiseSalaryChart.jsp" id="graphForm">
			      <select name="year" id="year" onChange="setYear(this.value)" onclick="getYr()">
			      <option>Select</option>
			      </select>
				 </form>
				</div>
				
							  <td >
								<table align="center" cellspacing="5"> 
								  <tr>
								<td><h2>Total Monthly Salary Paid For Year - </h2></td>  
								<td  style="background-color:  rgba(80, 80, 80, 0.6);" > <h2> <div id="yr1" style="position:static"> <%=request.getParameter("year")==null?year2-2:Integer.parseInt(request.getParameter("year")) %> </div></h2> </td>
								<td style="background-color:  rgba(59, 166, 220, 0.3);"> <h2> <div id="yr2" style="position:static"> <%=request.getParameter("year")==null?year2-1:Integer.parseInt(request.getParameter("year"))+1 %></div></h2> </td>
							  <td style="background-color:rgba(230, 126, 40, 0.6);"> <h2> <div id="yr3" style="position:static"><%=request.getParameter("year")==null?year2:Integer.parseInt(request.getParameter("year"))+2 %> </div></h2> </td>
							  </tr>
						    </table>							</tr>
				</table>
				
				</div>
				<table id="data-table" border="1" cellpadding="10" cellspacing="0" summary="">
					<caption align="left">Salary in Lacs</caption>
					<thead>
						<tr>
							<td>&nbsp;</td>
							<th scope="col"> &nbsp;&nbsp;&nbsp;&nbsp;Jan</th>
							<th scope="col">&nbsp;&nbsp;&nbsp;&nbsp;Feb</th>
							<th scope="col">&nbsp;&nbsp;&nbsp;Mar</th>
							<th scope="col">&nbsp;&nbsp;Apr</th>
							<th scope="col">&nbsp;May</th>
							<th scope="col">&nbsp;Jun</th>
							<th scope="col">Jul</th>
							<th scope="col">Aug</th>
							<th scope="col">Sep</th>
							<th scope="col">Oct</th>
							<th scope="col">Nov</th>
							<th scope="col">Dec</th>
							
						</tr> 
					
					</thead>
					<tbody>
						<tr>
						<!--	<th scope="row"></th> -->
					     	<td><%=gb1.getJan() %></td>
							<td><%=gb1.getFeb() %></td>
							<td><%=gb1.getMar() %></td>
							<td><%=gb1.getApr() %></td>
							<td><%=gb1.getMay() %></td>
							<td><%=gb1.getJun() %></td>
							<td><%=gb1.getJul() %></td>
							<td><%=gb1.getAug() %></td>
							<td><%=gb1.getSep() %></td>
							<td><%=gb1.getOct() %></td>
							<td><%=gb1.getNov() %></td>
							<td><%=gb1.getDec() %></td>	
						</tr>
						<tr>
						<!--	<th scope="row"></th> -->
							<td><%=gb2.getJan() %></td>
							<td><%=gb2.getFeb() %></td>
							<td><%=gb2.getMar() %></td>
							<td><%=gb2.getApr() %></td>
							<td><%=gb2.getMay() %></td>
							<td><%=gb2.getJun() %></td>
							<td><%=gb2.getJul() %></td>
							<td><%=gb2.getAug() %></td>
							<td><%=gb2.getSep() %></td>
							<td><%=gb2.getOct() %></td>
							<td><%=gb2.getNov() %></td>
							<td><%=gb2.getDec() %></td>
						</tr>
						<tr>
							<!-- <th scope="row"></th> -->
							<td><%=gb3.getJan() %></td>
							<td><%=gb3.getFeb() %></td>
							<td><%=gb3.getMar() %></td>
							<td><%=gb3.getApr() %></td>
							<td><%=gb3.getMay() %></td>
							<td><%=gb3.getJun() %></td>
							<td><%=gb3.getJul() %></td>
							<td><%=gb3.getAug() %></td>
							<td><%=gb3.getSep() %></td>
							<td><%=gb3.getOct() %></td>
							<td><%=gb3.getNov() %></td>
							<td><%=gb3.getDec() %></td>
														
						</tr>
						
					</tbody>
				</table>
			</div>
		</div>
		<!-- JavaScript at the bottom for fast page loading -->
		
		<!-- Grab jQuery from Google -->
		<script src="js/jquery/jquery.min.js"></script>
		
		<!-- Example JavaScript -->
		<script src="js/empYearwiseSalaryChart.js"></script>
		<%
		
		System.gc();
		
		%>
			</body>
</html>