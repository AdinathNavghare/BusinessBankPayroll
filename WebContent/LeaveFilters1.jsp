<%@page import="payroll.Core.UtilityDAO"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Core.LeaveFilter1"%>
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
	int flag1=-1;
	ArrayList<FilterValues> result = new ArrayList<FilterValues>();
	
	
	LeaveFilter1 filter = new LeaveFilter1();
	
	
	//LeaveFilter filter = new LeaveFilter();
	String action = new String();
	String key = new String();
	
	String forWhat = new String();
	String date  = new String();
	String type = new String();
	try
	{
		flag1 =Integer.parseInt(request.getParameter("flag1")==null?"-1":request.getParameter("flag1"));
		
		action = request.getParameter("action");
		System.out.println("this is action..."+action);
		key = request.getParameter("key");
		System.out.println("this is key..."+key);
		forWhat = String.valueOf(((request.getParameter("forWhat")==""||request.getParameter("forWhat")==null)?request.getAttribute("forWhat"):request.getParameter("forWhat")));
		System.out.println("this is forwhat..."+forWhat);
		request.setAttribute("forWhat",forWhat);
		date  = String.valueOf(((request.getParameter("date")==""||request.getParameter("date")==null)?request.getAttribute("date"):request.getParameter("date")));
		System.out.println("this is payroll date..."+date);
		
		
		
		type = String.valueOf(((request.getParameter("type")==""||request.getParameter("type")==null)?request.getAttribute("type"):request.getParameter("type")));
		request.setAttribute("type",type);
		//,//type = request.getParameter("type");
		
		//type = String.valueOf(sessionStorage.setItem("type"));
		
		System.out.println("this is type: "+type);
		
		
		
		
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
			result = filter.getGradeWiseList(Integer.parseInt(key),forWhat,date,type);
			
		}
		else if(action.equalsIgnoreCase("all"))
		{
			System.out.println("this is type: 1"+type);
			result = filter.getAllEmpList(forWhat,date,type);
			
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
<script>
//document.getElementById("type").value = sessionStorage.getItem("type");
	/*  jQuery(function() {
		$("#pp").autocomplete("projlist.jsp");
	}); 
	 */
	
	 function  noBack()
	{
		window.history.forward();
	}

	
	setTimeout("noBack()", 0);
	window.onunload = function() 
	{
	    null
	}; 
	
</script>
<script type="text/javascript">
function getResultprj(elem,forWhat,date)
{
	if(elem != "all")
	{
		
		var br=document.getElementById("pp").value;
		var site=br.split(":");
		var key = site[3];
		
		if(key != -1)
			window.location.href="LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key="+key;
	}
	else
	{
		window.location.href="LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key=all";
	}
}
function getResult1(elem,forWhat,date)
{ 
	debugger;
	var key="";
	
	var type = sessionStorage.getItem("type");
	//alert("element...."+type);
	    		if( $('#myModal').is(":empty") ) {
	    		//alert("div empty");
	    		}
	    		else{
	    			//alert("div not empty");
	    		}
	    		//alert("element...."+elem);
	    if(elem !== "all")
		{
			 key = document.getElementById(elem).value;
			 //alert("getting records..."+key);
			if(key != -1)
				$("#myModal").load("LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key="+key+"&type="+type);
				//window.location.href="LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key="+key;
		}
		else
		{ 
			//alert("getting all");
			$("#myModal").load("LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key=all&type="+type);
			//window.location.href="LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key=all";
		}
	    	
	    	
}
function getResultprj1(elem,forWhat,date1,type)
{ 
	if(elem != "all")
	{
		
		/* var br=document.getElementById("pp").value;
		var site=br.split(":"); */
		var selection=document.getElementById(elem);
		var type1=selection.options[selection.selectedIndex].value;
		var key = type1;
		
		if(key != -1)
			$("#myModal").load("LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date1+"&action="+elem+"&key="+key+"&type="+type);
	}
	else
	{
		$("#myModal").load("LeaveFilters1.jsp?forWhat="+forWhat+"&date="+date1+"&action="+elem+"&key=all&type="+type);
	}
}


var tlist = new Array();
var showList = "";
var list = new Array();
var numList = new Array();
var myDiv;
function getSelectedValues1()
{
	$('#finalizecheckbox').attr('checked', false);
	var boxes = document.getElementsByTagName("input");
	var j =0;
	for(var i = 0;i<boxes.length;i++)
	{
		if(boxes[i].type=="checkbox")
		{
			if(document.getElementById(boxes[i].id).checked==true)
			{
				tlist[j]=boxes[i];
				j++;
			}
		}
	}
	
	quickSort1(0,tlist.length-1);
	if(tlist.length != 0)
	{
		list = tlist;
		showList = "";
		for(var i = 0;i<list.length;i++)
		{
			myDiv = "<div class='special' title='"+list[i].value+" : "+list[i].title+"' >"+list[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP1('"+list[i].id+"')\" /></div>";
			showList = showList +myDiv;
		}
		createNumList1();
		$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
		document.getElementById("displayDiv").innerHTML=showList;
		document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
		document.getElementById("myModal").style.display = 'none';
	}
	else
		{
		$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
		document.getElementById("displayDiv").innerHTML=showList;
		document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
		document.getElementById("myModal").style.display = 'none';
		}
	//window.returnValue=tlist;
	//getFilter("1",tlist);
	//window.close();
	
}




function createNumList1() 
{
	numList = new Array();
	for(var i = 0; i< list.length;i++ )
	{
		numList[i] = list[i].value;
	}
}	
function quickSort1(low,n)
{
	var lo = low;
	var hi = n;
	if (lo >= n)
	{
		return;
	}
	var midIndex = Math.floor((lo + hi) / 2) ;
	var mid = parseInt(tlist[midIndex].value);
	while (lo < hi)
	{
	 	while (lo<hi && parseInt(tlist[lo].value) < mid)
	  	{
	  		lo++;
	  	}
	  	while (lo < hi && parseInt(tlist[hi].value) > mid) 
	  	{
	  		hi--;
	  	}
	  	if (lo < hi)
	  	{
		 	 var T = tlist[lo];
		 	tlist[lo] = tlist[hi];
		 	tlist[hi]= T;
	  	}
	}
	if (hi < lo)
	{
		var T = hi;
		hi = lo;
		lo = T;
	}
	quickSort1(low, lo);
	if(lo == low)
	{
		quickSort1(lo+1, n);
	}
	else
	{
		quickSort1(lo, n);
	}
}
function removeEMP1(key)
{
	var newList = "";
	var newArray = new Array();
	var j = 0;
	for(var i = 0; i<list.length;i++)
	{
		if(list[i].id != key)
		{
			var myDiv = "<div class='special' title='"+list[i].value+" : "+list[i].title+"' >"+list[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP1('"+list[i].id+"')\" /></div>";
			newList = newList + myDiv;
			newArray[j++] = list[i];
		}
	}
	showList = newList;
	list = newArray;
	createNumList1();
	document.getElementById("displayDiv").innerHTML=showList;
	document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
}
function closeFilter11()
{
	$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
	document.getElementById("displayDiv").innerHTML=showList;
	document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
	document.getElementById("myModal").style.display = "none";
}
function closediv() {
	document.getElementById("myModal").style.display = "none";
	$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
}

</script>
</head>
<body onload="showSelected()" >
<!-- <div class="modal-content" id="myModal" > -->
<img src='images/Close.png' style='float:right;' title='Remove' onclick="closediv()"><br>
<!-- <span class="close" >&times;</span> -->
<input type="hidden" id="hidAction" value="<%=action%>">
<input type="hidden" id="hidKey" value="<%=key%>">
<input type="hidden" name="type" id="type" value="<%=key%>" >
<center><br/>
	<table border="1" id="customers">
	<tr>
		<th>Show All</th>
		<th>Alphabetical</th>
		<th>Department Wise</th>
		<th>Designation Wise</th>
		<th>Project Wise</th>
		<!-- <th>Grade Wise</th> -->
	</tr>
	<tr>
		<td><input type="button" class="myButton" value="Show All" onclick="getResult1('all','<%=forWhat%>','<%=date%>')"></td>
		
		
		<td>
		
			<select id="alpha" name="alpha" onchange="getResult1('alpha','<%=forWhat%>','<%=date%>')">
		
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
		
			<select id="dept" name="dept" onchange="getResult1('dept','<%=forWhat%>','<%=date%>')">
		
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
		
			<select id="desig" name="desig" onchange="getResult1('desig','<%=forWhat%>','<%=date%>')">
		
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
		
		<select id="branch" name="branch" onchange="getResult1('branch','<%=forWhat%>','<%=date%>')">
		      					 
      					 <option value="0">Select</option>  
    						<%
    						EmpOffHandler ofh = new EmpOffHandler();
    						ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
    						
    						list=ofh.getprojectCode();
    						for(EmpOffBean lkb :list)
 							{
 							%>
      						<option value="<%=lkb.getPrj_srno()%>" title="<%=lkb.getPrj_code()%>"><%-- <%=UtilityDAO.FixStr(lkb.getPrj_code()+" -->", 15,2)%>  --%><%=lkb.getPrj_name()%></option>
      						  
     					 	<%
     					 	}
     					 	%>
     					 	</select>
		</td>
		<%-- <td>
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
		</td> --%>
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
	<div style="max-height:200px;overflow-y:scroll;margin-top: 0px; ">
	
	<tr>
	<td><font size="6">
     
	<%
		if(result.size()<=0 && flag1 >-1 )
		{
	%>
			There is No Employee...!
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
		<%		
					}
		
				%>
	</div>
	<br/>
	
	<input type="button" class="myButton" value="OK" onclick="getSelectedValues1()">
	<input type="button" class="myButton" value="Cancel" onclick="closeFilter11()">
	<input type="hidden" name="flag1" id="flag1" value="<%=flag1%>">
</center>
</body>
</html>