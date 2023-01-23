function addemployeValidation()
{

	if(document.getElementById("aSALUTE").selectedIndex==0)
	{
		alert("Please Select Salute e.g MR,MRS...");
		setTimeout("document.getElementById('aSALUTE').focus()", 50);
		return false;

	}
if(document.getElementById("FEmpname").value == "")
	{
		alert("Please Enter First Name of Employee");
		setTimeout("document.getElementById('FEmpname').focus()", 50);
		return false;

	}

if(document.getElementById("LEmpname").value == "")
{
		alert("Please Enter Last Name of Employee");
		setTimeout("document.getElementById('LEmpname').focus()", 50);
		
		return false;

}
if(document.getElementById("empcd").value == "")
{
		alert("Please Enter EMPCODE of Employee");
		setTimeout("document.getElementById('empcd').focus()", 50);
		
		return false;

}

if(document.getElementById("gender").selectedIndex==0)
{
	alert("Please Select Gender ");
	setTimeout("document.getElementById('gender').focus()", 50);
	return false;
}						

if(document.getElementById("aSALUTE").value=="1" && document.getElementById("gender").value!="M")
{
	alert("Please select Right salute and gender");
	setTimeout("document.getElementById('aSALUTE').focus()", 50);
	return false;

}

if(document.getElementById("aSALUTE").value=="2" && document.getElementById("gender").value!="F")
{
	alert("Please select Right salute and gender");
	setTimeout("document.getElementById('aSALUTE').focus()", 50);
	return false;

}
if(document.getElementById("aSALUTE").value=="3" && document.getElementById("gender").value!="F")
{
	alert("Please select Right salute and gender");
	setTimeout("document.getElementById('aSALUTE').focus()", 50);
	return false;

}



if(document.getElementById("dob").value== "")
{
	alert("Please Select Date Of Birth");
	//setTimeout("document.getElementById('dob').focus()", 50);
	return false;

}


if(document.getElementById("jdate").value== "")
{
	alert("Please Select Date Of Joining");
	return false;

}


	var jdate = document.getElementById("jdate").value;

	var dob=document.getElementById("dob").value;

	jdate=parseMyDateForEmp(jdate);
	dob=parseMyDateForEmp(dob);
	
	var marriagedate=document.getElementById("marrydate").value;

	if(marriagedate!="")
	{	
		marriagedate=parseMyDateForEmp(marriagedate);

			if (dob >= marriagedate ) 
			{
				alert("Marriage Date should not be earlier than Birth date !");
				document.getElementById("marrydate").value = "";
				document.getElementById("marrydate").focus();
				return false;
			}
						
			if(document.getElementById("gender").value=="M")
			{
				 myears = Math.floor((marriagedate.getTime() - dob.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
				 
				 if (myears < 21) {
					 alert("Age of Male Employee must be at least 21 for marriage! ");
					 document.getElementById("marrydate").focus();
					 return false;
				 }
			}
			if(document.getElementById("gender").value=="F")
			{
				myears = Math.floor((marriagedate.getTime() - dob.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
				if (myears < 18) {
				     alert("Age of Female Employee must be at least 18 for marriage!");
				     document.getElementById("marrydate").focus();
				     return false;
				}
			}
		}	
	
	if (dob >= jdate ) 
	{
		alert("Joining Date should not be earlier than Birth date !");
		document.getElementById("dob").value = "";
		return false;
	}
	
	 years = Math.floor((jdate.getTime() - dob.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
     if (years < 18) {
         alert("Age of Employee must be at least 18 for working! ");
         document.getElementById("jdate").focus();
         return false;
     }

	var pfjDate = document.getElementById("pfjDate").value;
	if(pfjDate==""){	}
	
	else{			
		 pfjDate=parseMyDateForEmp(pfjDate);	     
         if ((jdate > pfjDate) ) {
	     alert("PF joining date should not be earlier than joining date!");
	     document.getElementById("pfjDate").value = "";
	    	return false;
}

}


/*if(document.getElementById("prj").value=="0")
{
	alert("Please Assign Initial Branch to Employee");
	document.getElementById("prj").focus();
	return false;

}*/

if(document.getElementById("leavedate").value=="")
{
	alert("Please Select leave Confirmation Date...");
	setTimeout("document.getElementById('leavedate').focus()", 50);
	return false;

}

var jdate2 = document.getElementById("jdate").value;
var ldate = document.getElementById("leavedate").value;

jdate2=parseMyDateForEmp(jdate2);
ldate=parseMyDateForEmp(ldate);


if (ldate !=null && jdate2 > ldate) 
{
	alert("Leave Confirmation date should not be less than joining date !");
	document.getElementById("leavedate").value = "";
	return false;
}



alert("Please fill entire employee official information in Sixth option");


}

function parseMyDateForEmp(s) {
	
	var m = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug',
			'sep', 'oct', 'nov', 'dec' ];
	var match = s.match(/(\d+)-([^.]+)-(\d+)/);
	var date = match[1];
	var monthText = match[2];
	var year = match[3];
	var month = m.indexOf(monthText.toLowerCase());
	return new Date(year, month, date);
}
	function changesecbond()
	{
		if(document.getElementById("SecBond").selectedIndex == 2)
		{
			document.getElementById("Depamnt").value ='0';		
		}
		

	}
	
	// for upadate validation
	function updatevalidation()
	{
	
		var jnDate=document.getElementById('jdate').value;
		 var lvDate=document.getElementById('ldate').value;
		 
		    jnDate = jnDate.replace(/-/g,"/");
		    lvDate = lvDate.replace(/-/g,"/");

		    var d1 = new Date(jnDate);

		    var d2 =new  Date(lvDate);
		
		    
		   
		    
		    var pfjDate = document.getElementById("pfjDate").value;
		  
		    var jdate = document.getElementById("jdate").value;

			var dob=document.getElementById("dob").value;
			jdate=parseMyDateForEmp(jdate);
			dob=parseMyDateForEmp(dob);
				
			
		

		      if (d2.getTime()!==null && d1.getTime() > d2.getTime()) 
		    {
		    		alert("Joining Date must be Less than left Date..!!");
		    		document.getElementById('ldate').focus();
		    		return false;
		    }
		
		
		if(document.getElementById("aSALUTE").selectedIndex==0)
		{
			alert("Please Select Salute e.g MR,MRS...");
			setTimeout("document.getElementById('aSALUTE').focus()", 50);
			return false;

		}
	

	if(document.getElementById("gender").selectedIndex==0)
	{
		alert("Please Select Gender ");
		setTimeout("document.getElementById('gender').focus()", 50);
		return false;

	}				

	if(document.getElementById("jdate").value== "")
	{
		alert("Please Select Date Of Joining");
		
		return false;

	}
if(document.getElementById("dob").value== "")
{
	alert("Please Select Date Of Birth");
	//setTimeout("document.getElementById('dob').focus()", 50);
	return false;

}


if ((dob >= jdate ) ) {
	alert("Joining Date should not be earlier than Birth date!");
	return false;
}

if(pfjDate==""){	} 
else{
pfjDate=parseMyDateForEmp(pfjDate);
if ((jdate > pfjDate) ) {
     alert("PF joining date should not be earlier than joining date!");
     document.getElementById("pfjDate").value = "";
    	return false;
}


}
	
	}
	function TextCheck(id)
	{
		var check = document.getElementById(id).value;
		fcs = document.getElementById(id);
		var matches = check.match(/\d+/g);
		if (matches != null) {
		    alert("Please Insert Characters Only ");
		    //document.getElementById(id).focus();
		    setTimeout("fcs.focus()", 50);
		    return false;
		}
	}
	