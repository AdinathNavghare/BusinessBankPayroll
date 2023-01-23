<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attachment Form</title>

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

  
  <style type="text/css">
  /*----------for checkbox------- */

.multiselect {
  width: 150px;
} 

.selectBox {
  position: relative;
}

.selectBox select {
  width: 100%;
  font-weight: bold;
}

.overSelect {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

#checkboxes {
  display: none;
  border: 1px #dadada solid;
}

#checkboxes label {
  display: block;
}

#checkboxes label:hover {
  background-color: #1e90ff;
} 

 </style>
	
	
</head>
<body style="overflow: hidden">

	<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Attachment</h1>
			</div>
			<!-- end page-heading -->

          <div align="center">
             <tr class="alt">
        		<td>&nbsp;</td>
        		<td colspan="3"><input type="button" class="myButton" value="AddTask"
				onClick="window.document.location.href='AddTask.jsp'">&nbsp;&nbsp;
				
				</td>
			</tr>
         </div>

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
									<form action="AddTaskServlet?action=taskattach" name="attachmentform" method="post">
                                      <!-- <form action="upload" method="post" enctype="multipart/form-data"> -->
										<table id="customers" align="center">
											<tr class="alt">
												<th colspan="4">Attachment</th>
											</tr>

											<div class="container">

												<tr class="alt">
													<td>Attach File is :</td>
													<!-- <td><form action="upload" method="post" enctype="multipart/form-data"></td> -->
													<td><input type="text" name="description" /></td>


												</tr>

												<tr class="alt">
													<td></td>
													<td><input type="file" name="file" />
													    <input type="submit" />
													</td>
												</tr>


												<tr class="alt">
													<td>&nbsp;</td>
													<td colspan="3">
														<!-- <input type="submit" value="Save" name="Save" class="myButton">&nbsp;&nbsp; -->
														<!-- <input type="reset" value="cancel" class="myButton">&nbsp;&nbsp; -->
														 <a href="AddTask.jsp"><input onclick="this.parentNode.href=AddTask.jsp;" type="button" value="Cancel" name="cancel" class="myButton"/></a>
													</td>
												</tr>

												

											</div>
										</table>
									</form>
								</center>
							</div>

							<!--  end table-content  -->

							<div class="clear"></div>

						</div> <!--  end content-table-inner ............................................END  -->
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

</body>
</html>

