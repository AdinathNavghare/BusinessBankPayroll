// JavaScript Document
function editExpvalidation()
{
	
	if(	document.getElementById("aempNo").value==""	||
			document.getElementById("aempName").value=="" || 
			document.getElementById("srNo").value=="" || 
			document.getElementById("eOrgNamee").value=="" ||
			document.getElementById("eDesg").value=="" || 
			document.getElementById("eSalary").value=="" )	 
		{
			alert("Please Enter All Data");
			return false;
		
		}

		if( document.getElementById("eFrmDate").value == "")
			{
				alert("Please Ffrom Date");	
				document.getElementById("eFrmDate").focus;
				return false;
			}
		if( document.getElementById("aEffDate").value == "")
			{
			alert("Please Effective Date");	
			document.getElementById("aEffDate").focus;
			return false;
			}	
		
}


	function addExpvalidation()
	{
		
			if( document.getElementById("aempNo").value=="" || 
				document.getElementById("aempName").value=="" || 
				document.getElementById("aexOrgName").value=="" || 
				document.getElementById("aexDesg").value=="" ||
				document.getElementById("aexSalary").value== "" 
			 )	
			{
				alert("Please Enter All Data");
				return false;
		
			}
		if(document.getElementById("aexFrmDate").value == "")
			{
				alert("Please Select From Date");	
				document.getElementById("aexFrmDate").focus;
				return false;
			}
		if(document.getElementById("aexToDate").value=="")
			{
				alert("Please Select TO Date");	
				document.getElementById("aexToDate").focus;
				return false;
			}		
	
	}
// JavaScript Document