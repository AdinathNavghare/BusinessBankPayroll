<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Dbf.DbfDataType"%>
<%@page import="Dbf.StringUtils"%>
<%@page import="Dbf.DbfField"%>
<%@page import="Dbf.DbfHeader"%>
<%@page import="Dbf.DbfReader"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->




<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>






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
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Testing</h1>
	</div>
	<!-- end page-heading -->
	<div align="center">
	
    </div>

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
			<div id="table-content" style="overflow-y:auto; max-height:520px; max-width:1200px;">
			<form action="Import" method="post" >
		<table id = "customers">
		<tr class="alt">
		<%
			String path = request.getParameter("f");
			File dbf = new File(path);
			DbfReader reader = new DbfReader(dbf);
			DbfHeader header = reader.getHeader();
			String[] titles = new String[header.getFieldsCount()];
			for (int i = 0; i < header.getFieldsCount(); i++) {
    		DbfField field = header.getField(i);
    		titles[i] = StringUtils.rightPad(field.getName(), field.getFieldLength(), ' ');
   			 //System.out.print(titles[i]);//for getting headers
 		%>
 			<th><%= titles[i] %></th>
 
		<%    
		}
		%>
		</tr>
		<%
			Object[] row;
			while ((row = reader.nextRecord()) != null) {
		%>
		<tr>
			<%
    			for (int i = 0; i < header.getFieldsCount(); i++) {	
        			DbfField field = header.getField(i);
        			String value = field.getDataType() == DbfDataType.CHAR? new String((byte[]) row[i]): String.valueOf(row[i]);
                	//System.out.print(value);
    		%>
    				<td><%= value %></td>
		    <%
		    }
			%>
    	</tr>
		    <%
				}
			 %>
 		</table>
 		</form>
 		 
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