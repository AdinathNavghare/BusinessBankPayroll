// JavaScript Document
function addOfficilvalidation()
{
	
	
	if(	document.getElementById("empNo").value=="" || 
			document.getElementById("empName").value=="" || 
			document.getElementById("TrnsrNo").value=="" || 
			document.getElementById("TraCode").value=="" ||
			document.getElementById("branch").value=="" ||
			document.getElementById("grade").value=="" || 
			document.getElementById("Desgn").value=="" || 
			document.getElementById("saccntNo").value==""  ||
			document.getElementById("Depart").value=="" || 
			document.getElementById("Descrp").value==""  
		)	
	{
		alert("Please Enter All Data");
		return false;
	
	}
	if( document.getElementById("EffDate").value == "")
		{
		alert("Please Select Effective Date");	
		document.getElementById("EffDate").focus;
		return false;
		}
	if( document.getElementById("OrderDate").value == "")
	{
	alert("Please Select Order Date");	
	document.getElementById("OrderDate").focus;
	return false;
	}
	

		
}


	function editOfficilvalidation()
	{
		
		if(	document.getElementById("eempNo").value=="" || 
				document.getElementById("eempName").value=="" || 
				document.getElementById("eTrnsrNo").value=="" || 
				document.getElementById("eTraCode").value=="" ||
				document.getElementById("ebranch").value=="" ||
				document.getElementById("egrade").value=="" || 
				document.getElementById("eDesgn").value=="" || 
				document.getElementById("esaccntNo").value==""  ||
				document.getElementById("eDepart").value=="" || 
				document.getElementById("eDescrp").value==""  
			)	
		{
			alert("Please Enter All Data");
			return false;
		
		}

		if( document.getElementById("eEffDate").value == "")
		{
		alert("Please Select Effective Date");	
		document.getElementById("eEffDate").focus;
		return false;
		}
	if( document.getElementById("eOrderDate").value == "")
	{
	alert("Please Select Order Date");	
	document.getElementById("eOrderDate").focus;
	return false;
	}
	}
// JavaScript Document