<style>
.form-popup 
   {
      display: none;
	  position: absolute;
	  bottom: 10%;
	  right: 14%;
	 
	  border: 3px solid #f1f1f1;
	  z-index: 9;
	  width:75%; /* Full width */
  	  height: 35%; /* Full height */
  	  overflow: hidden; /* Enable scroll if needed */
	  background-color: rgb(0,0,0); /* Fallback color */ 
      background-color: rgba(0,0,0,0.4); /* Black w/ opacity */			  
  } 
</style>
<div class="form-popup" id="myForm" align="center">
  
  <div id="modal-content" style="overflow-y:auto; margin-top:15px; width:900px;height:90%;">
			<table id="customers">
				<tbody class="Emp_Data"  align="center" >
								
								
				</tbody>
			</table>		
		</div>
</div>
<script>
$(document).mouseup(function (e){

	var container = $("#myForm");

	if (!container.is(e.target) && container.has(e.target).length === 0){

		container.fadeOut();
		
	}
}); 
function openForm(emp) {
	var html='';
	 var xmlhttp=new XMLHttpRequest();
	  var response3="";
	      xmlhttp.onreadystatechange=function()
	        {
	    	  if (xmlhttp.readyState==4)
	          {
	        	response3 = xmlhttp.responseText.split(",");
	        	var n=response3.length;
	    		
	    		  html += '<tr style="background-color: #1F5FA7; color:white; width:50% position: relative;"><th colspan="7">Employee Information</th></tr>'
		    
	    		  html +='<tr><th width="100">Emp_Id</th><th  width="200">EMP_NAME</th><th width="200">ProjectName</th><th  width="185">TaskName</th><th  width="200">SubTaskName</th>';
	    		  html +=' <tr>';
	    		html+='<tr class="alt">';
	    		for(var i=0;i<n-1;i++)
	    			{	
	    				//html+='<td style="background-color: white;  width="50%" fon position: relative;"><button type="button" class="myButton" onclick="remove()">Remove</button></td>'
	    				if(response3[i]=='*')
	    					{
	    					html+='</tr>';
	    					}
	    				else
	    					{
	    					  html +='<td style="background-color: white;  width:50%  position: relative;">'+response3[i]+'</td>';
	    					}
	    			}
	    		  $('.Emp_Data').html(html);  
	          } 
	        }
	      xmlhttp.open("POST","AddProjectServlet?action=EmpDetail&empid="+emp,true);
	      xmlhttp.send(); 
  document.getElementById("myForm").style.display = "block";
}

</script>