function print(DivID)
{ 
	var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
    var content_vlue = document.getElementById(DivID).innerHTML;
    content_vlue = content_vlue.replace("max-height","m");
    //content_vlue = content_vlue.replace("overflow-y","y");
    var docprint=window.open("","",disp_setting); 
	docprint.document.open(); 
	docprint.document.write('<html><head><title></title>'); 
	docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 12px;} </style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
	docprint.document.write(content_vlue);          
	docprint.document.write('</center><hr></body></html>'); 
	docprint.document.close(); 
	docprint.focus(); 
}