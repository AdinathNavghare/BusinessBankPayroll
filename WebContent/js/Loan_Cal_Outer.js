function check() { 
	var loanamt = top.document.addForm.loan_amt.value;
	var paymnt = top.document.addForm.pay_month.value;
	var rate = top.document.addForm.loan_per.value;
	
	if(loanamt=="" || isNaN(parseFloat(loanamt)) || loanamt==0) { 
		alert("Please enter a valid loan amount.");
		top.document.addForm.loan_amt.value="";
		top.document.addForm.loan_amt.focus();	
		return false; 
	} else if(paymnt=="" || isNaN(parseFloat(paymnt)) || paymnt==0) {
		alert("Please enter a valid number of payments.");
		top.document.addForm.pay_month.value="";
		top.document.addForm.pay_month.focus();
		return false; 
	} else if(rate=="" || isNaN(parseFloat(rate)) || rate==0) {
		alert("Please enter the interest rate.");
		top.document.addForm.loan_per.value="";
		top.document.addForm.loan_per.focus();
		return false; 
	} else {
		show(); 
	}
}

function clearScreen() { 
	top.document.addForm.loan_amt.value="";
	top.document.addForm.pay_month.value="";
	top.document.addForm.loan_per.value="";
	top.document.getElementById("pmt").innerHTML="";
	top.document.getElementById("det").innerHTML="";
}

function fixVal(value,numberOfCharacters,numberOfDecimals,padCharacter) { 
	var i, stringObject, stringLength, numberToPad;            // define local variables

	value=value*Math.pow(10,numberOfDecimals);                 // shift decimal point numberOfDecimals places
	value=Math.round(value);                                   //  to the right and round to nearest integer

	stringObject=new String(value);                            // convert numeric value to a String object
	stringLength=stringObject.length;                          // get length of string
	while(stringLength<numberOfDecimals) {                     // pad with leading zeroes if necessary
		stringObject="0"+stringObject;                     // add a leading zero
		stringLength=stringLength+1;                       //  and increment stringLength variable
	}

	if(numberOfDecimals>0) {			           // now insert a decimal point
		stringObject=stringObject.substring(0,stringLength-numberOfDecimals)+"."+
		stringObject.substring(stringLength-numberOfDecimals,stringLength);
	}

	if (stringObject.length<numberOfCharacters && numberOfCharacters>0) {
		numberToPad=numberOfCharacters-stringObject.length;      // number of leading characters to pad
		for (i=0; i<numberToPad; i=i+1) {
			stringObject=padCharacter+stringObject;
		}
	}

	return stringObject;                                       // return the string object
}

function show() {
	var amount=parseFloat(top.document.addForm.loan_amt.value);
	var numpay=parseInt(top.document.addForm.pay_month.value);
	var rate=parseFloat(top.document.addForm.loan_per.value);
	var start_date = top.document.addForm.startDate.value;
	  
	start_date = start_date.replace(/-/g,"/");
	//alert(start_date);
	var myDate = new Date(start_date);
	
	rate=rate/100;
	var monthly=rate/12;
	var payment=((amount*monthly)/(1-Math.pow((1+monthly),-numpay)));
	var total=payment*numpay;
	var interest=total-amount;

	var output = "";
	var detail = "";

	output += "<table border='1' align='left' style='width:15%;'> \
			<tr><td>Loan amount:</td><td align='center'>Rs."+amount+"</td></tr><tr><td>Num payments:</td> \
			<td align='center'>"+numpay+"</td></tr><tr><td>Annual Rate:</td><td align='center'>"+fixVal(rate,0,4,' ')+"</td></tr> \
			<tr><td>Monthly Rate:</td><td align='center'>"+fixVal(monthly,0,5,' ')+"</td></tr><tr><td>Monthly Payment:</td> \
			<td align='center'>Rs."+fixVal(payment,0,2,' ')+"</td></tr><tr><td>Total Paid:</td><td align='center'>Rs."+fixVal(total,0,2,' ')+"</td></tr> \
			<tr><td>Total Interest:</td><td align='center'>Rs."+fixVal(interest,0,2,' ')+"</td></tr></table>";

	detail += "<table border='1' align='left' cellpadding='5' cellspacing='0' width='25%' style='font-family:courier;font-size:12px'> \
			<tr><td align='center' valign='bottom' bgcolor='white'><b>Proj Date</b></td><td align='center' valign='bottom' bgcolor='white'><b>Pmt</b></td><td align='right' valign='bottom' bgcolor='white'><b>Amount</b></td> \
			<td align='right' valign='bottom' bgcolor='white'><b>Interest</b></td><td align='right' valign='bottom' bgcolor='white'><b>Principal</b></td> \
			<td align='right' valign='bottom' bgcolor='white'><b>Balance</b></td></tr><tr><td align='center' bgcolor='white'>0</td> \
			<td align='center' bgcolor='white'>&nbsp;</td><td align='center' bgcolor='white'>&nbsp;</td><td align='center' bgcolor='white'>&nbsp;</td><td align='center' bgcolor='white'>&nbsp;</td> \
			<td align='right' bgcolor='white'>"+fixVal(amount,0,2,' ')+"</td></tr>";

	newPrincipal=amount;

	var i = 1;
	while (i <= numpay) {
		newInterest=monthly*newPrincipal;
		reduction=payment-newInterest;
		newPrincipal=newPrincipal-reduction;
		
		
		myDate.setMonth( myDate.getMonth() + 1 );
		var currDay   = myDate.getDate();
		var currMonth = myDate.getMonth() + 1;
		var currYear  = myDate.getFullYear();
		currDateStr   = currDay + "/" + currMonth + "/" + currYear; 
		
				
		detail += "<tr><td align='center'>"+currDateStr+"</td><td align='center'>"+i+"</td><td align='right' bgcolor='white'>"+fixVal(payment,0,2,' ')+"</td> \
				<td align='right' bgcolor='white'>"+fixVal(newInterest,0,2,' ')+"</td> \
				<td align='right' bgcolor='white'>"+fixVal(reduction,0,2,' ')+"</td> \
				<td align='right' bgcolor='white'>"+fixVal(newPrincipal,0,2,' ')+"</td></tr>";

		i++;
	}

	detail += "</table>";

	document.getElementById("pmt").innerHTML = output;
	document.getElementById("det").innerHTML = detail;
}
