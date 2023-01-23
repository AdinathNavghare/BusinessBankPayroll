<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Core.Filters"%>
<%@page import="payroll.Model.FilterValues"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bonus © DTS3</title>

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"	media="screen" title="default" />
<link rel="stylesheet" type="text/css" media="screen" href="css/MONTHPICK/jquery-ui.css">
<style>
    .ui-datepicker-calendar {
        display: none;
        }
</style>

<script src="js/bonus.js" type="text/javascript"></script>
<script src="js/bonusFilter.js" type="text/javascript"></script>

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
</script>
 <script type="text/javascript">
    $(function() {
        $('.date-picker').datepicker( {
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'M-yy',
            onClose: function(dateText, inst) { 
                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                $(':focus').blur();
                
            },
            beforeShow : function(input, inst) {
                var datestr;
                if ((datestr = $(this).val()).length > 0) {
                    year = datestr.substring(datestr.length-4, datestr.length);
                    month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNamesShort'));
                    $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                    $(this).datepicker('setDate', new Date(year, month, 1));
                    
                }
            }
        });
        
    });
    </script>
 
</head>






<!-- ===========================================java code start here==================================================== -->
<%

	System.out.println("In Java Code");
	ArrayList<FilterValues> result = new ArrayList<FilterValues>();
	Filters filter = new Filters();
	String action = new String();
	String key = new String();
	String forWhat = new String();
	String date  = new String();
	try
	{
		action = request.getParameter("action");
		key = request.getParameter("key");
		forWhat = request.getParameter("forWhat");
		date  = request.getParameter("date");
		
		System.out.println("In Java Code action "+action);
		System.out.println("In Java Code key"+key);
		System.out.println("In Java Code forWhat"+forWhat);
		System.out.println("In Java Code date"+date);
		
		if(action.equalsIgnoreCase("alpha"))
		{
			result = filter.getAlphabeticalList(key,forWhat,date);
		}
		else if(action.equalsIgnoreCase("dept"))
		{
			result = filter.getDeptWiseList(Integer.parseInt(key),forWhat,date);
		}
		else if(action.equalsIgnoreCase("desig"))
		{
			result = filter.getDesigWiseList(Integer.parseInt(key),forWhat,date);
		}
		else if(action.equalsIgnoreCase("grade"))
		{
			result = filter.getGradeWiseList(Integer.parseInt(key),forWhat,date);
		}
		else if(action.equalsIgnoreCase("all"))
		{
			System.out.println("In Java Code 1");
			
			result = filter.getAllEmpList(forWhat,date);
			
			System.out.println("In Java Code 2");
		}else if(action.equalsIgnoreCase("branch"))
		{
			result = filter.getProjectWiseList(Integer.parseInt(key),forWhat,date);
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
<!-- ===========================================java code Ends here==================================================== -->

<body> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Bonus  Posting </h1>
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
				<!-- <form  name="leaveLedgerform" action="CoreServlet"  method="post" onSubmit="return validate()"> -->
				<table>
					<tr>
						<td>
							<% 
								String act = request.getParameter("action")==null?"":request.getParameter("action");
								if( !act.equals("firstTime") && (!act.equals("all")) ){
							%>
							
							<div id="table1" >
							<center>
							<table id="customers" border="1" align="center" style="height: 30px; width: 800px;">		
							   <tr> 
							   		<th colspan="2">Post Bonus</th>
							   </tr>
							   <tr align="center">
							   	<td>
							   		<span>Date :</span>
									<input name="dateinMonth" id="dateinMonth" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
							   	</td>
								<td>
									<span>Select Percentage % :</span>
									<select id = "percent" name ="percent">
						                   		<option value="0">Select</option>
						                   		<option value="1">1</option>
						                   		<option value="2">2</option>
						                   		<option value="3">3</option>
						                   		<option value="4">4</option>
						                   		<option value="5">5</option>
						                   		<option value="6">6</option>
						                   		<option value="7">7</option>
						                   		<option value="8">8</option>
						                   		<option value="9">9</option>
						                   		<option value="10">10</option>
						                   		<option value="11">11</option>
						                   		<option value="12">12</option>
					                </select>
									
								</td>
							   </tr>
							   
								<tr class="alt" align="center">
									<td colspan="2">
										<!-- <div id="displayDiv" style="height: 300px"></div>
										<div id="countEMP">0 Employees Selected</div> -->	
										<input type="button" value="Select Employees" onclick="getFilt()" class="myButton">
										<input type="button" value="Add More Employee" onclick="addFilt()" class="myButton">
										<input type="button" value="Cancel All" onclick="cancelAll()" class="myButton">
									</td>
								</tr>
						    </table>
						    </center>
						   	</div>
						   	<%} %>
						   	
						   	<% 
								act = request.getParameter("action")==null?"":request.getParameter("action");
								if(act.equals("firstTime")){
							%>
						    
						    <!-- To Select Employee by Option           style="visibility:hidden;"-->
						    <div id="table2" >
						    <center>
						    <table border="1" id="customers" align="center" style="height: 30px; width: 800px; margin-top:-15px">
								<tr>
									<th colspan="6">Post Bonus</th>
								</tr>
								<tr>
									<th>Show All</th>
									<th>Alphabetical</th>
									<th>Department Wise</th>
									<th>Designation Wise</th>
									<th>Project Wise</th>
									<th>Grade Wise</th>
								</tr>
								<tr>
									<td><input type="button" class="myButton" value="Show All" onclick="getResultBonus('all','<%=forWhat%>','<%=date%>')"></td>
									<td>
										<select id="alpha" name="alpha" onchange="getResult('alpha','<%=forWhat%>','<%=date%>')">
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
										<select id="dept" name="dept" onchange="getResult('dept','<%=forWhat%>','<%=date%>')">
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
										<select id="desig" name="desig" onchange="getResult('desig','<%=forWhat%>','<%=date%>')">
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
									<select id="branch" name="branch" onchange="getResult('branch','<%=forWhat%>','<%=date%>')">
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
										<select id="grade" name="grade" onchange="getResult('grade','<%=forWhat%>','<%=date%>')">
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
							</center>
						    </div>
						    <%} %>
			<!-- To show the employee for Bonus Posting -->
			<br/>
			<% 
					act = request.getParameter("action")==null?"":request.getParameter("action");
					if(act.equals("all")){
			%>
			<div style="text-align:center">
				<%
					if(result.size()>0)
					{
				%>
					<span style=""><b> <%=result.size() %>&nbsp; Records found.....&nbsp;&nbsp;</b></span>
				<%
					}
				%>
				<input type="button" class="myButton" value="Check All" onclick="check('checkAll')">
				<input type="button" class="myButton" value="Uncheck All" onclick="check('unCheckAll')">
				<input type="button" class="myButton" value="Toggle" onclick="check('toggle')">
			</div>
			<br/>
				<div style="overflow-y:scroll;margin-bottom: 0px;">
					<table border="1" align="center" id="customers">
						<tr height="32">
							<th width="50">Select</th>
							<th width="50">EmpCode</th>
							<th width="250">Name</th>
							<th width="50">Amount</th>
							<th width="50">Date</th>
							
							<th width="50">Select</th>
							<th width="50">EmpCode</th>
							<th width="250">Name</th>
							<th width="50">Amount</th>
							<th width="50">Date</th>
						</tr>
						</table>
				</div>
		
				<div style="max-height:400px;overflow-y:scroll;margin-top: 0px; ">
					<table border="1" id="customers">
						<%
							FilterValues f = new FilterValues();
							int j=0,k=0;
							int min = (result.size() / 2) + (result.size() % 2);
							for(int i = 0;i<min;i++)
							{
								f = result.get(i);
						%>
								<tr>
									<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>" name="<%=f.getEMPCODE()%>"></td>
									<td width="50" align="center"><%=f.getEMPCODE() %></td>
									<td width="250"><%=f.getNAME()%></td>
									<td width="50"><input type="text" id="<%=f.getEMPNO()%>"  size="7" /></td>
									<td width="50">Date</td>
						<%
								j = i+min;
								if(j<result.size())
								{
									f = result.get(j);
									
						%>
									<td width="50" align="center"><input type="checkbox" id="<%=f.getEMPCODE()%>" value="<%=f.getEMPNO()%>" title="<%=f.getNAME()%>"  name="<%=f.getEMPCODE()%>"></td>
									<td width="50" align="center"><%=f.getEMPCODE() %></td>
									<td width="250"><%=f.getNAME()%></td>
									<td width="50"><input type="text" id="<%=f.getEMPNO()%>" size="7"/></td>
									<td width="50">Date</td>
						<%		
								}
								else
								{
						%>
									<td width="50" align="center">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>
									<td width="250">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>
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
									<td width="50"><input type="text" id="<%=f.getEMPNO()%>"  size="7"/></td>
									<td width="50">Date</td>
								
						<%
								}
								else
								{
						%>			<!-- <td width="50" align="center">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>
									<td width="250">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>
									<td width="50" align="center">&nbsp;</td>  -->
						<%			
								}
						%>
								</tr>
						<%		
							}
						%>
					</table>
				</div>   <!-- end of table 3 -->
				
				<%} %>  <!-- end of if for table 3 -->
				
			<br/>
			
		</td>
		<td width="10"></td>		
		</tr>
		</table>
			    <!-- </form> -->
			    
			</center>	    
		    </div>
		   
		    <!--  end table-content  -->
		  
	<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
		<div align="center" style="padding-top: 20%;">
			<h1> Bonus Posting takes 15 to 30 Min....Be Patience</h1>
			<img alt="" src="images/process.gif">
		</div>
	</div>
			
	
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
	<center>
					<input type="button" value="Post Bonus"  class="myButton" onclick="paycal()"/> &nbsp;&nbsp;
					<input type="button"  class="myButton"  value="Cancel"/>				
		    </center>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
        

</body>
</html>