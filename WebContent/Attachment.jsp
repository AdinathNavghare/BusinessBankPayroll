<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/filter.js"></script>

<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<% 
		String action=request.getParameter("action")!=null?request.getParameter("action"):"addemp"; 
		System.out.println("ACTION ::"+action);
		/* String action2=request.getParameter("action2")!=null?request.getParameter("action2"):"showemp"; */ 
		int check = 0;
		try
		{
			String Addmore = request.getParameter("Addmore");
			if(Addmore.equalsIgnoreCase("no"))
			{
				check=1; //Record Modified
			}
			else if(Addmore.equalsIgnoreCase("yes"))
			{
				check=2;	// Error Record not Modified
			}
		}
		catch(Exception e)
		{
			System.out.println("First Time Loading dialog");
		}
	%>
	<script type="text/javascript">
	
	function checkVal()
	{
		var flag = document.getElementById("chek").value;
		
		if(flag == 1)
		{
			//alert("Record Updated Successfully!");
			//window.close();
		}
		else if(flag==2)
		{
			//var resp=confirm("Add More Attachment ?");
			if(resp==true)
			{
				window.location.href="Attachment.jsp";
			}
			else
				{
				//window.location.href="viewFiles.jsp";
				
				}
		}
		
	}
	
	function ImageValidation(id)
	{
		
		
		if(document.getElementById("id").value == "")
		{
			alert("Please First Fill Employee Details Form");	
			return false;
		}
		var nBytes = 0;
	    oFiles = document.getElementById("sign").files;
	    nFiles = oFiles.length;
	  for (var nFileId = 0; nFileId < nFiles; nFileId++) {
	  nBytes += oFiles[nFileId].size;
	 	 }
	 
	 if(nBytes > 15360)
		{
		 alert("Signture Image Size Must Be less than 15kb ");
		 return false;
		} 
		
	 
	 var nBytes1 = 0;
	    oFiles = document.getElementById("photo").files;
	    nFiles = oFiles.length;
	for (var nFileId = 0; nFileId < nFiles; nFileId++) {
	  nBytes1 += oFiles[nFileId].size;
	}
	 
	 if(nBytes1 > 20480)
		{
		 alert("Photo Image Size Must Be less than 20kb ");
		 return false;
		} 
		
		
		
		
		var fup = document.getElementById("photo");
	var fupsgn = document.getElementById("sign");
	var fileName = fup.value;
	var fileNamesign = fupsgn.value;
	
	if(fileName=="" && fileNamesign=="")
		{
		alert("Please Select  GIF or JGP or PNG Format photo and Signture image");
		fup.focus();
		return false;
		}
	if(fileName =="")
		{
			alert("Please Select  GIF or JGP or PNG Format photo image");
			fup.focus();
			return false;
		
		}
	if(fileNamesign =="")
	{
		alert("Please Select  GIF or JGP or PNG Format Signture image");
		fupsgn.focus();
		return false;
	
	}
	
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	
	var extsgn = fileNamesign.substring(fileNamesign.lastIndexOf('.') + 1);
	
	
	if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG")
	{
		return true;
	} 
	else
	{
		alert("Upload  GIF or JGP or PNG  Format photo image only");
		fup.focus();
		return false;
		
	}
	
	if(extsgn == "gif" || extsgn == "GIF" || extsgn == "JPEG" || extsgn == "jpeg" || extsgn == "jpg" || extsgn == "JPG" || extsgn == "png" || extsgn == "PNG" )
	{
		return true;
	} 
	else
	{
		alert("Upload  GIF or JPG or PNG Format Signture image only");
		fupsgn.focus();
		return false;
	}
	}
	/* function AttachFile()
	{
		if(document.getElementById("empno").value == "")
		{
			alert("Please First Fill Employee Details Form");	
			return false;
		}
		
		var name =document.getElementById("docname").value;
		var type =document.getElementById("doctype").value;
		var desc =document.getElementById("docdesc").value;
		if(name== "" || type == "" || desc=="")
			{
			alert("Please Insert Document Information");
			return false;
			}
		
		
		window.showModalDialog("uploadattchment.jsp?name="+name+"&type="+type+"&desc="+desc+"&upfile=emp",null,"dialogWidth:690px; dialogHeight:130px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		//window.location.href="attachment?list=show";
	} */
	
	
	/*  function AttachFile1()
	{
		if(document.getElementById("empno").value == "")
		{
			alert("Please First Fill Employee Details Form");	
			return false;
		}
		
		var docname =document.getElementById("docname").value;
		var doctype =document.getElementById("doctype").value;
		var docdesc =document.getElementById("docdesc").value;
		alert("name"+name+"type"+type+"desc"+desc)
		if(name== "" || type == "" || desc=="")
			{
			alert("Please Insert Document Information");
			return false;
			}
		
		
		
		
		
		$.ajax({
			url : 'AttachFile',
			data: 'action=setData&docname='+docname+'&doctype'+doctype+'&docdesc'+docdesc,
			success : function(responseText) {
				alert("data set succesfully..");
			}
		});
	} */
	
	
	
	/* function AttachFile1()
	{
		debugger;
		//alert("welcome Attach....")
		if(document.getElementById("empno").value == "")
		{
			alert("Please First Fill Employee Details Form");	
			return false;
		}
		
		var docname =document.getElementById("docname").value;
		var doctype =document.getElementById("doctype").value;
		var docdesc =document.getElementById("docdesc").value;
		//alert("name : "+docname+"\ntype : "+doctype+"\ndesc : "+docdesc)
		if(docname== "" || doctype == "" || docdesc=="")
			{
			alert("Please Insert Document Information");
			return false;
			}
		
		debugger;
		
		alert("hey div ");
		$("#myModal").empty();
		/* $("#myModal").load("AttachmentPopup.jsp"); 
		document.getElementById("myModal").style.display = 'Block';
		$("#myModal").load("Filters1.jsp&docname="+docname+"&doctype="+doctype+"&docdesc="+docdesc);
		
	//	$("#myModal").load("AttachmentPopup.jsp");
		$("#myModal").fadeTo('slow', 0.9);
		$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');    
		 
		/* debugger; 
		document.getElementById("myModal").style.display = 'Block';
		$("#myModal").load("AttachmentPopup.jsp?docname=" + docname + "&doctype="+ doctype + "&docdesc=" + docdesc +"&action=attachDoc");
		
		$("#myModal").fadeTo('slow', 0.9);
		$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none'); */
		
		
/*	}

	function editImages(){
		var empno =document.getElementById("id").value;
		alert("empno : "+empno);
		//window.location.href="Attachment.jsp?action=addemp";
		$("#myModal").load("AttachmentPopup.jsp?action=editImages");
		document.getElementById("myModal").style.display = 'Block';
		$("#myModal").fadeTo('slow', 0.9);
		$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	}
	 */
	 
	 
	/*  function AttachFile1()
		{
			debugger;
			//alert("welcome Attach....")
			if(document.getElementById("empno").value == "")
			{
				alert("Please First Fill Employee Details Form");	
				return false;
			}
			
			var docname =document.getElementById("docname").value;
			var doctype =document.getElementById("doctype").value;
			var docdesc =document.getElementById("docdesc").value;
			//alert("name : "+docname+"\ntype : "+doctype+"\ndesc : "+docdesc)
			if(docname== "" || doctype == "" || docdesc=="")
				{
				alert("Please Insert Document Information");
				return false;
				}
			
			
		getFilt('attachfile');	
	
		
		}

		function editImages(){
			var empno =document.getElementById("id").value;
			alert("empno : "+empno);
			//window.location.href="Attachment.jsp?action=addemp";
			$("#myModal").load("AttachmentPopup.jsp?action=editImages");
			document.getElementById("myModal").style.display = 'Block';
			$("#myModal").fadeTo('slow', 0.9);
			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
		} */
		
	</script>
	
	
	
	

	
</head>
<body  style="overflow: hiddent"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">
<% if(action.equalsIgnoreCase("addemp"))
				{
	
				%>
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="empQual.jsp">  Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="em`pFamily.jsp">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no">8</div>
			<div class="step-dark-left"><a href="Attachment.jsp">Attachment</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		</div>
		<%
				}
		
		else if(action.equalsIgnoreCase("editemp"))
		{

		%>
<div id="content"  ><!--  start page-heading -->
	<div id="step-holder">
	<div class="step-light-right">&nbsp;</div>
	<div class="step-no-off">1</div>
	<div class="step-light-left"><a href="employee.jsp" >Employee Detail</a></div>
	<div class="step-light-right">&nbsp;</div>
	<div class="step-no-off">2</div>
	<div class="step-light-left"><a href="empQual.jsp">  Qualification </a></div>
	<div class="step-light-right">&nbsp;</div>
	<div class="step-no-off">3</div>
	<div class="step-light-left"><a href="empAddress.jsp"> Address </a></div>
	<div class="step-light-right">&nbsp;</div>
	<div class="step-no-off">4</div>
	<div class="step-light-left"><a href="em`pFamily.jsp">Family </a></div>
    <div class="step-light-right">&nbsp;</div>
    <div class="step-no-off">5</div>
	<div class="step-light-left"><a href="empExper.jsp">Experience </a></div>
	<div class="step-light-right">&nbsp;</div>
	<div class="step-no-off">6</div>
	<div class="step-light-left"><a href="officialInfo.jsp">Official Info</a></div>
	<div class="step-light-right">&nbsp;</div>

	<div class="step-no-off">7</div>
	<div class="step-light-left"><a href="awardInfo.jsp">Award</a></div>
	<div class="step-light-right">&nbsp;</div>
	
	<div class="step-no">8</div>
	<div class="step-dark-left"><a href="Attachment.jsp">Attachment</a></div>
	<div class="step-dark-right">&nbsp;</div>
	
	<div class="step-no-off">9</div>
	<div class="step-light-left"><a href="otherDetail.jsp">Other Detail</a></div>
	<div class="step-light-round">&nbsp;</div>
	<div class="clear"></div>
</div>
<%
		
		
		}
		else if(action.equalsIgnoreCase("showemp"))
		{
			
		%>
	<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=employee" >Employee Detail</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=empQual"> Qualification </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">3</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=address"> Address </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">4</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=family">Family </a></div>
            <div class="step-light-right">&nbsp;</div>
            <div class="step-no-off">5</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=experience">Experience</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">6</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=officialInfo">Official Info</a></div>
			<div class="step-light-right">&nbsp;</div>
		
			<div class="step-no-off">7</div>
			<div class="step-light-left"><a href="EmployeeServlet?action=awardInfo">Award</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no">8</div>
			<div class="step-dark-left"><a href="Attachment.jsp?action=showemp">Attachment</a></div>
			<div class="step-dark-right">&nbsp;</div>
			
			<div class="step-no-off">9</div>
			<div class="step-light-left"><a href="otherDetail.jsp?action=showemp">Other Detail</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
	</div>
	<% 
		} 
		
	%>
	
	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Attachments </h1>
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
			
			<%
			if(action.equalsIgnoreCase("addemp"))
			{		
				String list = request.getParameter("list")==null?"":request.getParameter("list");
				System.out.println("list is "+list);
				if (request.getParameter("status") != null) 
				{
					%><p align="center"><font color="green"> <%=request.getParameter("status")%></font></p>
					<%
				}
				%>
				<table id="customers"><tr><td>
					<form id="form1" enctype="multipart/form-data" action="#" method="post" >
					<input type="hidden" id="key" name="key" value="editemp">
							 <table width="448" border="1" >
                <tr>
                    <td>Employee Code :</td>
                    <td><%=session.getAttribute("empcode") %><input  type="hidden"  name="id" id="id" readonly="readonly" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
                    
                    </td>
                </tr>
                <tr>
                    
                    <td><input  type="hidden"  name="title"/></td>
                </tr>
                <tr>
                	<td width="216"><img  src="images/img.jpg" height="80" width="100"></td> 
                	<td valign="bottom" width="216"><img alt="" src="images/sign.jpg" height="38" width="192"></td> 
                </tr>
                <tr>
                   <!--  <td>Select Photo <input type="file"  name="photo" id="photo" />  </td>
                    <td>Select Signature Image<input type="file"  name="sign" id="sign" /></td> -->
                </tr>
                <tr><td colspan="2" align="center"><input type="button" onclick="getFilt('editimg')"  class="myButton"  value=" Insert Images"></td></tr>
            </table>
            
					</form>
					</td><td valign="top">
				<form action="#" enctype="multipart/form-data"   method="post">
		<table ><!-- here take empno from session -->
			<tr><td>Document Type&nbsp; <font color="red"><b>*</b></font> <input  type="hidden"  name="empno" id="empno" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
			<input type="hidden" id="key" name="key" value="editemp">
			<!-- <input type="hidden" id="key" name="key" value="addemp">
			<input type="hidden" id="list" name="list" value="show"/> -->
			</td> 
			<!-- 	<td><select class="form-control" name="doctype" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td> -->
					<td><select class="form-control" name="docType" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td>
			</tr>
			<tr><td>Document Name&nbsp; <font color="red"><b>*</b></font></td><td><input class="form-control" type="text" name="docname" id="docname"></td></tr>
			<tr><td valign="top">Document Description&nbsp; <font color="red"><b>*</b></font></td><td><textarea class="form-control" name="docdesc" id="docdesc" cols="30"></textarea></td></tr>
			
			<!-- new field -->
					<!-- <tr><td valign="top">Select File&nbsp; <font color="red"><b>*</b></font></td><td><input type="file"  name="attach" id="attach" /></td></tr> -->
			
			<!-- <tr><td colspan="2" align="center"><input type="button"  class="myButton" onclick="AttachFile1()"   value="Attach File" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel"></td></tr> -->
			<tr><td colspan="2" align="center"><input type="button"  class="myButton" onclick="getFilt('attachfile')"   value="Attach Files" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel"></td></tr>
				
			<%
				if(list.equalsIgnoreCase("show"))
				{
					
					%>
					<center>
					<%@include file="viewFiles.jsp" %>
					</center>
					<%
				}
				%>
			
		</table>
		<%-- <center>
					<%@include file="viewFiles.jsp" %>
					</center> --%>
		</form>
				</td></tr></table>
				<!--  show files  -->
				
				
				<br>	
				<%-- <%
				if(list.equalsIgnoreCase("show"))
				{
					
					%>
					<center>
					<%@include file="viewFiles.jsp" %>
					</center>
					<%
				}
				%> --%>
				
			</div>
			<!--  end table-content  -->
			<%
			}
			
			
			
			//2 div **********************************************
			
			if(action.equalsIgnoreCase("editemp"))
			{
				
				
				
				
				String list = request.getParameter("list")==null?"":request.getParameter("list");
				System.out.println("list is "+list);
		%>
		<table id="customers"><tr><td>
			<form id="form1" enctype="multipart/form-data" action="AddPhotoServlet" method="post" onsubmit="return ImageValidation()">
			 <table width="448" border="1" >
			 <tr>
            <td>Employee Code :</td>
            <td><%=session.getAttribute("empcode") %><input  type="hidden"  name="id" id="id" readonly="readonly" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
            </td>
        </tr>
         <tr>
            <td><input  type="hidden"  name="title"/></td>
        </tr>
        
			
			<tr>
        	<td width="216"> <img src="DisplayPhotoServlet?type=PHOTO&empno=<%= session.getAttribute("empno")%>" width="200" height="150	"/> </td> 
        	<td valign="bottom" width="216"><img alt="" src="DisplayPhotoServlet?type=SIGN&empno=<%= session.getAttribute("empno")%>" height="150" width="192"></td> 
        </tr>
        
        
       
        <!-- <tr>
        	<td width="216"><img  src="images/img.jpg" height="80" width="100"></td> 
        	<td valign="bottom" width="216"><img alt="" src="images/sign.jpg" height="50" width="192"></td> 
        </tr> -->
        <!-- <tr>
            <td>Select Photo <input type="file"  name="photo" id="photo" />  </td>
            <td>Select Signature Image<input type="file"  name="sign" id="sign" /></td>
        </tr> -->
        <tr>
        	<td colspan="2" align="center">
        		<input type="button"  class="myButton" onclick="getFilt('editimg')"  value=" Edit Images">
        		<input type="button"  class="myButton"  value=" Remove Images">
        		</td>
        	
        </tr>
        
        
         <%-- <%} %> --%>
    </table>
    
			</form>
			
			
			</td><td valign="top">
		<form action="#" enctype="multipart/form-data"   method="post">
		<table ><!-- here take empno from session -->
			<tr><td>Document Type&nbsp; <font color="red"><b>*</b></font> <input  type="hidden"  name="empno" id="empno" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
			 <input type="hidden" id="key" name="key" value="editemp">
		<!--	<input type="hidden" id="list" name="list" value="show"/> -->
			</td> 
			<!-- 	<td><select class="form-control" name="doctype" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td> -->
					<td><select class="form-control" name="docType" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td>
			</tr>
			<tr><td>Document Name&nbsp; <font color="red"><b>*</b></font></td><td><input class="form-control" type="text" name="docname" id="docname"></td></tr>
			<tr><td valign="top">Document Description&nbsp; <font color="red"><b>*</b></font></td><td><textarea class="form-control" name="docdesc" id="docdesc" cols="30"></textarea></td></tr>
			
			<!-- new field -->
					<!-- <tr><td valign="top">Select File&nbsp; <font color="red"><b>*</b></font></td><td><input type="file"  name="attach" id="attach" /></td></tr> -->
			
			<!-- <tr><td colspan="2" align="center"><input type="button"  class="myButton" onclick="AttachFile1()"   value="Attach File" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel"></td></tr> -->
			<tr><td colspan="2" align="center"><input type="button"  class="myButton" onclick="getFilt('attachfile')"   value="Attach Files" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel"></td></tr>
				
			<%
				if(list.equalsIgnoreCase("show"))
				{
					
					%>
					<center>
					<%@include file="viewFiles.jsp" %>
					</center>
					<%
				}
				%>
			
		</table>
		<%-- <center>
					<%@include file="viewFiles.jsp" %>
					</center> --%>
		</form>
		</td></tr></table>
		<!--end show files  -->
		<%} 
			
			// end 2 div **************************************************
			
			
			if(action.equalsIgnoreCase("showemp"))
			{
				
				
				String list = request.getParameter("list")==null?"":request.getParameter("list");
				System.out.println("list is "+list);
		%>
		<table id="customers"><tr><td>
			<form id="form1" enctype="multipart/form-data" action="#" method="post">
			 <table width="448" border="1" >
			 <tr>
            <td>Employee Code :</td>
            <td><%=session.getAttribute("empcode") %><input  type="hidden"  name="id" id="id" readonly="readonly" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
            </td>
        </tr>
         <tr>
            <td><input  type="hidden"  name="title"/></td>
        </tr>
        
			
			<tr>
        	<td width="216"> <img src="DisplayPhotoServlet?type=PHOTO&empno=<%= session.getAttribute("empno")%>" width="200" height="150	"/> </td> 
        	<td valign="bottom" width="216"><img alt="" src="DisplayPhotoServlet?type=SIGN&empno=<%= session.getAttribute("empno")%>" height="150" width="192"></td> 
        </tr>
       
        <!-- <tr>
        	<td width="216"><img  src="images/img.jpg" height="80" width="100"></td> 
        	<td valign="bottom" width="216"><img alt="" src="images/sign.jpg" height="50" width="192"></td> 
        </tr> -->
        <!-- <tr>
            <td>Select Photo <input type="file"  name="photo" id="photo" />  </td>
            <td>Select Signature Image<input type="file"  name="sign" id="sign" /></td>
        </tr> -->
        <tr>
        	<td colspan="2" align="center">
        		<input type="button"  class="myButton"  value=" Edit Images" onclick="getFilt('editimg')">
        		<input type="button"  class="myButton"  value=" Remove Images">
        		</td>
        	
        </tr>
         <%-- <%} %> --%>
    </table>
    
			</form>
			
			
			</td><td valign="top">
		<form action="#" enctype="multipart/form-data"   method="post">
		<table ><!-- here take empno from session -->
			<tr><td>Document Type&nbsp; <font color="red"><b>*</b></font> <input  type="hidden"  name="empno" id="empno" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
			<input type="hidden" id="key" name="key" value="showemp">
			<!-- <input type="hidden" id="key" name="key" value="addemp">
			<input type="hidden" id="list" name="list" value="show"/> -->
			</td> 
			<!-- 	<td><select class="form-control" name="doctype" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td> -->
					<td><select class="form-control" name="docType" id="doctype">
					<option value="0">Select</option>
						<option value="Education">Education</option>
						<option value="Other">Other</option>
					</select>
					</td>
			</tr>
			<tr><td>Document Name&nbsp; <font color="red"><b>*</b></font></td><td><input class="form-control" type="text" name="docname" id="docname"></td></tr>
			<tr><td valign="top">Document Description&nbsp; <font color="red"><b>*</b></font></td><td><textarea class="form-control" name="docdesc" id="docdesc" cols="30"></textarea></td></tr>
			
			<!-- new field -->
				<!--  <tr><td valign="top">Select File&nbsp; <font color="red"><b>*</b></font></td><td><input type="file"  name="attach" id="attach" /></td></tr> -->
			
			<tr><td colspan="2" align="center"><input type="button" onclick="getFilt('attachfile')"  class="myButton"  value="Attach File" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton"  value="Cancel"></td></tr>
				
			<%-- <%
				if(list.equalsIgnoreCase("show"))
				{
					
					%> --%>
					<%-- <center>
					<%@include file="viewFiles.jsp" %>
					</center> --%>
					<%-- <%
				}
				%> --%>
			
		</table>
		<center>
					<%@include file="viewFiles.jsp" %>
					</center>
		</form>
		</td></tr></table>
		<!--end show files  -->
		<%
		
	} %>
			<%-- <%
			if(action.equalsIgnoreCase("showemp"))
			{
				%> --%>
				<%-- <center>
				<form action="AttachFile?action=attachFile" method="post">
				<table id="customers">
					<tr>
						<td>Employee Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=session.getAttribute("empcode") %>
						<input  type="hidden" readonly="readonly" name="empno" id="empno"  value="<%=session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/></td>
						<td>Employee Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form-control" type="text" readonly="readonly" name="empno" size="40" value="<%=session.getAttribute("empname")==null?"":session.getAttribute("empname")%>"/></td>
					</tr>
					<tr>
					<td valign="top">
				
				<table ><!-- here take empno from session -->
					
					<tr><td>Document Type&nbsp; <font color="red"><b>*</b></font> 
						<input type="hidden" id="key" name="key" value="showemp"/>
						<input type="hidden" id="action" name="action" value="showemp"/>
						<input type="hidden" id="list" name="list" value="show"/>
					</td> 
						<td><select class="form-control" name="doctype" id="doctype">
							<option value="0">Select</option>
								<option value="Education">Education</option>
								<option value="Other">Other</option>
							</select>
							</td>
					</tr>
					<tr><td>Document Name&nbsp; <font color="red"><b>*</b></font></td><td><input class="form-control" type="text" name="docname" id="docname"></td></tr>
					<tr><td valign="top">Document Description&nbsp; <font color="red"><b>*</b></font></td><td><textarea class="form-control" name="docdesc" id="docdesc" cols="30"></textarea></td></tr>
					
					
					
					<tr><td colspan="2" align="center"><input type="submit"  class="myButton"  value="Attach File" >&nbsp;&nbsp;&nbsp;<input type="reset" class="myButton" value="Cancel"></td></tr>
					
				</table>
				
				</td>
					<td><%@include file="viewFiles.jsp" %></td>
					</tr>
				</table>
				</form>
				
				
					
					
					</center> --%>
				<%-- <%} %> --%>
				
				
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
	<input type="hidden" value="" name="chek" id="chek">
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>

<!-- modal box -->

    

</body>
</html>