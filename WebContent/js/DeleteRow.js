var xmlhttp;
var url="";
if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}


function deleteRow(action1,action,srno,newUrl)
{
	var flag = confirm("Are you Sure to Delete this Record?");
	if(flag)
	{
		var empno= document.getElementById("empNo").value;
		url="DeleteServlet?action1="+action1+"&action="+action+"&empno="+empno+"&srno="+srno;
		
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				var response = xmlhttp.responseText;
				if(response=="true")
				{
					alert("Record Deleted");
					window.location.href=newUrl;
				}
				else
				{
					if(action=="office"){
						alert("\t\tSorry You Can't Delete ! \nSalary Transaction Has Done On This Record");
					}
					else{
						alert("Unable to delete, try Again!");
					}
				}
			}
		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
}

function deleteRecord(action1,action,srno,newUrl)
{
	var flag = confirm("Delete Record ?");
	if(flag)
	{
		url="DeleteServlet?action1="+action1+"&action="+action+"&srno="+srno;
		
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				var response = xmlhttp.responseText;
				if(response=="true")
				{
					alert("Record Deleted");
					window.location.href=newUrl;
				}
				else
				{
					alert("Unable to delete, try Again!");
				}
			}
		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
}

function settlement(trcode, newUrl){
	
	var flag = confirm("  Are you sure?\nAfter Settlement Details can't be changed.!");
	if(flag){
		
		url = "TripMasterServlet?action=settlement&trcode="+trcode;
		
		xmlhttp.onreadystatechange=function()
		{
			if(xmlhttp.readyState==4)
			{
				var response = xmlhttp.responseText;
				if(response=="true")
				{
					alert("Settlement has done successfully.");
					window.location.href=newUrl;
				}
				else
				{
					alert("Settlement failed, try Again !");
				}
			}
		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send();
	}
	
}
