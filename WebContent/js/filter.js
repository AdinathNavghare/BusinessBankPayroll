var showList = "";
var month=month;
var ty="";
var list = new Array();
var numList = new Array();
	function initAll()
	{
		showList = "";
		list = new Array();
		numList = new Array();
		document.getElementById("displayDiv").innerHTML="";
		document.getElementById("countEMP").innerHTML="0 Employees Selected";
	}

	/*function getFilter(forWhat,date)
	{
		
		var newList = new Array();
		
		if(forWhat=="Leave")
			{
			var type="";
			var r = document.getElementsByName("type");
			var c = -1;
			for(var i=0; i < r.length; i++)
			{
			   if(r[i].checked) 
			   {
			      c = i; 
			     
			     type=document.getElementById(i+1).value;
			     
			   }
			}
			if (c == -1)
			{
				alert("Please select Leave Type");
				return false;
			}
			
			
			if(document.getElementById("crdmonth").value==""){
				alert("Please select Month & Date To Credit");
				return false;
			}
			else{
				date=document.getElementById(date).value;
			}
			
			
			newList = window.showModalDialog("LeaveFilters.jsp?forWhat="+forWhat+"&type="+type+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
			var myDiv;
			if(newList.length != 0)
			{
				list = newList;
				showList = "";
				for(var i = 0;i<list.length;i++)
				{
					myDiv = "<div class='special' title='"+list[i].value+" : "+list[i].title+"' >"+list[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP('"+list[i].id+"')\" /></div>";
					showList = showList +myDiv;
				}
				createNumList();
				document.getElementById("displayDiv").innerHTML=showList;
				document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
			}
			
			}
		
		else{
		newList = window.showModalDialog("Filters.jsp?forWhat="+forWhat+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
		var myDiv;
		if(newList.length != 0)
		{
			list = newList;
			showList = "";
			for(var i = 0;i<list.length;i++)
			{
				myDiv = "<div class='special' title='"+list[i].value+" : "+list[i].title+"' >"+list[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP('"+list[i].id+"')\" /></div>";
				showList = showList +myDiv;
			}
			createNumList();
			document.getElementById("displayDiv").innerHTML=showList;
			document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
		}
		}
		return true;
	}*/
	function getFilt(forWhat)
	{	
	//	alert("am here for=="+forWhat);
		if(forWhat=="Leave")
		{
				var type="";
				var r = document.getElementsByName("type");
				var r1 = document.getElementsByName("type1");
				var c = -1;
				for(var i=0; i < r.length; i++)
				{
				   if(r[i].checked) 
				   {
				      c = i; 
				     
				     type=document.getElementById(i+1).value;
				     
				   }
				}
				if (c == -1)
				{
					alert("Please select Leave Type");
					return false;
				}
				
				
				if(document.getElementById("crdmonth").value==""){
					alert("Please select Month & Date To Credit");
					return false;
				}
				else{
					//date=document.getElementById(date).value;
					date=document.getElementById("crdmonth").value;
				}
				
				//newList = window.showModalDialog("LeaveFilters.jsp?forWhat="+forWhat+"&type="+type+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
				if( $("#myModal").is(":empty") ) {
		    		//alert("hey div empty");
					}
		    		else{
		    			
		    			/*document.getElementById("myModal").style.display = 'Block';
		    			$("#myModal").load("LeaveFilters.jsp?forWhat="+forWhat+"&type="+type+"&date="+date);
		    			$("#myModal").fadeTo('slow', 0.9);
		    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');*/
		    			document.getElementById("myModal").style.display = 'Block';
		    			$("#myModal").load("LeaveFilters1.jsp?forWhat="+forWhat+"&type="+type+"&date="+date);
		    			$("#myModal").fadeTo('slow', 0.9);
		    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
		    		}
				
		}
		
		else if(forWhat=="attachfile")
			{
			debugger;
			var docname =document.getElementById("docname").value;
			var doctype =document.getElementById("doctype").value;
			var docdesc =document.getElementById("docdesc").value;
			var key =document.getElementById("key").value;
			//alert("name : "+docname+"\ntype : "+doctype+"\ndesc : "+docdesc)
			if(docname== "" || doctype == "" || docdesc=="")
				{
				alert("Please Insert Document Information");
				return false;
				}
				$("#myModal").empty();
    			document.getElementById("myModal").style.display = 'Block';
    			
    			
    			$("#myModal").load("Filters1.jsp?act="+forWhat+"&key="+key+"&docname="+docname+"&doctype="+doctype+"&docdesc="+docdesc);
    			
    			$("#myModal").fadeTo('slow', 0.9);
    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
    			
	    		}
			
		
		else if(forWhat=="editimg")
		{
		debugger;
		
		var key =document.getElementById("key").value;
			$("#myModal").empty();
			document.getElementById("myModal").style.display = 'Block';
			//$("#myModal").load("Filters1.jsp?forWhat="+forWhat+"&date="+month);
			
			//
			
			$("#myModal").load("Filters1.jsp?act="+forWhat+"&key="+key);
			
			$("#myModal").fadeTo('slow', 0.9);
			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
			
    		}
		
			
		
		
		else{
				if(month==""||month==null||month=="-1")
				{
					alert("Please Set Salary Month");
					return false;
				}
				else
				{
					if( $("#myModal").is(":empty") ) {
			    		//alert("hey div empty");
						}
			    		else{
			    			
			    			if(forWhat=='toIncometaxreport'){
			    				var selection=document.getElementById("sheet");
								var type1=selection.options[selection.selectedIndex].value;
								ty=type1;
			    				if(ty==""||ty=="0"||ty==0)
			    				{
				    				alert("Please Select Report Type");
				    				return false;
			    				}else{
			    					$("#myModal").empty();
					    			document.getElementById("myModal").style.display = 'Block';
				    				$("#myModal").load("Filters.jsp?forWhat="+forWhat+"&date="+month+"&type="+ty);
			    				}
			    			}else{
			    				$("#myModal").empty();
				    			document.getElementById("myModal").style.display = 'Block';
				    			$("#myModal").load("Filters.jsp?forWhat="+forWhat+"&date="+month);
			    			}
			    			$("#myModal").fadeTo('slow', 0.9);
			    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
			    		}
					
					//getFilter('toPayCal',month);
				}
		}
	}
	function addFilt(forWhat)
	{
		
		/* if(month == "")
		{
			alert("Please Set Salary Month");	
		}
		else
		{
			addMoreEmp('toPayCal',month);
		} */
		
		if(month==""||month==null||month=="-1")
		{
			alert("Please Set Salary Month");	
		}
		else
		{
			if( $("#myModal").is(":empty") ) {
	    		//alert("hey div empty");
	    		}
	    		else{
	    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	    			document.getElementById("myModal").style.display = 'Block';
	    			$("#myModal").load("Filters.jsp?forWhat="+forWhat+"&date="+month);
	    			
	    			}
			
			//getFilter('toPayCal',month);
		}
	}
	function addMoreEmp(forWhat,date)
	{ 
	//var alist = window.showModalDialog("Filters.jsp?forWhat="+forWhat+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
		
		if(forWhat=="Leave")
				{
							var type="";
							var r = document.getElementsByName("type");
							var c = -1;
							for(var i=0; i < r.length; i++)
							{
							   if(r[i].checked) 
							   {
							      c = i; 
							     
							     type=document.getElementById(i+1).value;
							     
							   }
							}
							if (c == -1)
							{
								alert("Please select Leave Type");
								return false;
							}
							
							
							if(document.getElementById("crdmonth").value==""){
								alert("Please select Month & Date To Credit");
								return false;
							}
							else{
								date=document.getElementById(date).value;
							}
							alist = window.showModalDialog("LeaveFilters.jsp?forWhat="+forWhat+"&type="+type+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
							
				}
		if(forWhat=="toPostBonus")
			{
		var alist = window.showModalDialog("Filters.jsp?forWhat="+forWhat+"&date="+date,"Employee Filter","dialogWidth=1200px;dialogHeight=700px;center=yes;dialogleft=yes;addressBar=no;");
	           }
		var myDiv="";
		var len = list.length;
		var j =0;
		for(var i = 0;i<alist.length;i++)
		{
			var flag = checkDuplicate(alist[i]);
			if(flag)
			{
				myDiv = "<div class='special' title='"+alist[i].value+" : "+alist[i].title+"' >"+alist[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP('"+alist[i].id+"')\" /></div>";
				showList = showList +myDiv;
				list[len + j] = alist[i];
				j++;
			}
		}
		createNumList();
		document.getElementById("displayDiv").innerHTML=showList;
		document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
	}
	
	function cancelAll()
	{
		document.getElementById("displayDiv").innerHTML="";
		list = new Array();
		numList = new Array();
		showList = "";
		document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
	}
	
	function removeEMP(key)
	{
		var newList = "";
		var newArray = new Array();
		var j = 0;
		for(var i = 0; i<list.length;i++)
		{
			if(list[i].id != key)
			{
				var myDiv = "<div class='special' title='"+list[i].value+" : "+list[i].title+"' >"+list[i].value+"<img src='images/Close.png' style='float:right;' title='Remove' onclick=\"removeEMP('"+list[i].id+"')\" /></div>";
				newList = newList + myDiv;
				newArray[j++] = list[i];
			}
		}
		showList = newList;
		list = newArray;
		createNumList();
		document.getElementById("displayDiv").innerHTML=showList;
		document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
	}
	
	function checkDuplicate(key)
	{
		for(var i = 0; i<list.length;i++)
		{
			if(key.id == list[i].id)
			{
				return false;	
			}
		}
		return true;
	}
	
	function createNumList() 
	{
		numList = new Array();
		for(var i = 0; i< list.length;i++ )
		{
			numList[i] = list[i].value;
		}
	}	
	
	//-------------------------------------------Filter.jsp----------------
	
	function getResult(elem,forWhat,date)
	{
		if(elem != "all")
		{
			var key = document.getElementById(elem).value;
			if(key != -1)
				window.location.href="Filters.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key="+key+"&flag1=1";
		}
		else
		{
			window.location.href="Filters.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&key=all &flag1=1";
		}
	}
	
	function getResult2(elem,forWhat,date,type)
	{
		

		// alert("for "+type +""+elem);
		var flag1 =parseInt(document.getElementById("flag1").value);
		if(elem != "all")
		{
			var key = document.getElementById(elem).value;
			if(key != -1)
				window.location.href="LeaveFilters.jsp?forWhat="+forWhat+"&date="+date+"&type="+type+"&action="+elem+"&key="+key+"&flag1=1";
		}
		else
		{
			window.location.href="LeaveFilters.jsp?forWhat="+forWhat+"&date="+date+"&type="+type+"&action="+elem+"&key=all &flag1=1";
			
		}
		
		if(flag1 == 0)
		{
	    	alert("There is No Employee Left For Auto Credit For This Year...!");
		
		} 
		
	
		
		/*// alert("for "+type +""+elem);
		if(elem != "all")
		{
			var key = document.getElementById(elem).value;
			if(key != -1)
				window.location.href="LeaveFilters.jsp?forWhat="+forWhat+"&date="+date+"&type="+type+"&action="+elem+"&key="+key;
		}
		else
		{
			window.location.href="LeaveFilters.jsp?forWhat="+forWhat+"&date="+date+"&type="+type+"&action="+elem+"&key=all";
		}
	*/}
	
	function check(action)
	{
		var boxes = document.getElementsByTagName("input");
		
		for(var i = 0;i<boxes.length;i++)
		{
			
			if(boxes[i].type=="checkbox")
			{
				
				
				if(action == "checkAll")
				{
					
					
						
							document.getElementById(boxes[i].id).checked="checked";
						
						
					
					
				}
				else if(action == "unCheckAll")
				{
					document.getElementById(boxes[i].id).checked=false;	
				}
				else if(action == "toggle")
				{
					if(document.getElementById(boxes[i].id).checked==true)
					{
						document.getElementById(boxes[i].id).checked=false;
					}
					else
					{
						document.getElementById(boxes[i].id).checked="checked";	
					}	
				}
			}
		}
	}
	function checkLoan(action)
	{
		var boxes = document.getElementsByTagName("input");
		for(var i = 0;i<boxes.length;i++)
		{
			if(boxes[i].type=="checkbox")
			{
				if(action == "checkAll")
				{
					document.getElementById(boxes[i].id).checked="checked";
				}
				else if(action == "unCheckAll")
				{
					document.getElementById(boxes[i].id).checked=false;	
				}
				else if(action == "toggle")
				{
					if(document.getElementById(boxes[i].id).checked==true)
					{
						document.getElementById(boxes[i].id).checked=false;
					}
					else
					{
						document.getElementById(boxes[i].id).checked="checked";	
					}	
				}
			}
		}
	}
	
	function showSelected() 
	{
		var action = document.getElementById("hidAction").value;
		var key = document.getElementById("hidKey").value;
		
		var temp = document.getElementById(action);
		for(var i =0;i<temp.length;i++)
		{
			if(temp.options[i].value==key)
			{
				document.getElementById(action).selectedIndex=i;
			}
		}
	}
	var tlist = new Array();
	function getSelectedValues()
	{
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
		quickSort(0,tlist.length-1);
		window.returnValue=tlist;
		window.close();
	}
	
	function quickSort(low,n)
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
		//quickSort(low, lo);
		if(lo == low)
		{
			quickSort(lo+1, n);
		}
		else
		{
			quickSort(lo, n);
		}
	}
	
	function closeFilter()
	{
		window.returnValue=new Array();
		window.close();
	}
	
	function getFilterForIncomeTax(forWhat,date,type)
	{
		alert("2="+type)
		if(month==""||month==null||month=="-1")
		{
			alert("Please Set Salary Month");	
		}
		else
		{
			if( $("#myModal").is(":empty") ) {
	    		//alert("hey div empty");
				}
	    		else{
	    			$("#myModal").empty();
	    			document.getElementById("myModal").style.display = 'Block';
	    			$("#myModal").load("Filters.jsp?forWhat="+forWhat+"&date="+month+"&type="+type);
	    			$("#myModal").fadeTo('slow', 0.9);
	    			$('.nav-outer').fadeTo("slow", 0.5).css('pointer-events', 'none');
	    		}
			
			//getFilter('toPayCal',month);
		}
	}
	
	function getResulttaxreport(elem,forWhat,date,type)
	{
		if(elem != "all")
		{
			var key = document.getElementById(elem).value;
			if(key != -1)
				window.location.href="Filters.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&type="+type+"&key="+key+"&flag1=1";
		}
		else
		{
			window.location.href="Filters.jsp?forWhat="+forWhat+"&date="+date+"&action="+elem+"&type="+type+"&key=all &flag1=1";
		}
	}
	
	function closeFilter1(divid)
	{
		//alert("am here to close");
		//document.getElementById("displayDiv").innerHTML=showList;
		//document.getElementById("countEMP").innerHTML=list.length+" Employees Selected";
		document.getElementById(divid).style.display = "none";
	}
	function tomonth(month)
	{
		if(parseInt(month)==0)
			return "JAN";
		else if(parseInt(month)==1)
			return "FEB";
		else if(parseInt(month)==2)
			return "MAR";
		else if(parseInt(month)==3)
			return "APR";
		else if(parseInt(month)==4)
			return "MAY";
		else if(parseInt(month)==5)
			return "JUN";
		else if(parseInt(month)==6)
			return "JUL";
		else if(parseInt(month)==7)
			return "AUG";
		else if(parseInt(month)==8)
			return "SEP";
		else if(parseInt(month)==9)
			return "OCT";
		else if(parseInt(month)==10)
			return "NOV";
		else if(parseInt(month)==11)
			return "DEC";
	}
	