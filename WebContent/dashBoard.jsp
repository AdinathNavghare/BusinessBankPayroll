<%@page import="net.sf.json.JSONArray"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.GraphBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->


       
        
       <!--  <script type="text/javascript">
         
            var chartData = [];
            var chartDatabar=[];
            var url="";
            var xmlhttp;
            
            if(window.XMLHttpRequest)
          	{
          		xmlhttp=new XMLHttpRequest;
          	}
          	else //if(window.ActivXObject)
          	{   
          		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
          	}
             function getData()
            {
            	url="displayphoto?action=pie";
      			xmlhttp.onreadystatechange=callback;
      			
          		xmlhttp.open("POST", url, true);
          		xmlhttp.send();
              }
              
             function getData1()
             {
             	url="displayphoto?action=bar";
       			xmlhttp.onreadystatechange=callback1;
       			xmlhttp.open("POST", url, true);
           		xmlhttp.send();
               } 
            

          	function callback()
          	{
              	if(xmlhttp.readyState==4)
          		{
              		
              		var response= xmlhttp.responseXML;
              		var data=response.getElementsByTagName("data");
              		var x = data[0].getElementsByTagName("prjname");
              		
              		
                  for (var i=0; i <x.length; i++)
              		{
              			
              			var newElement = {};
              		    newElement['country'] = data[0].getElementsByTagName("prjname")[i].firstChild.nodeValue;
              		    newElement['visits'] = data[0].getElementsByTagName("emp")[i].firstChild.nodeValue;
              		  	chartData.push(newElement);
              		 	
              		}
          		
                  		
              	}
              	
          	}
              	
              	 function callback1()
              	{
                  	if(xmlhttp.readyState==4)
              		{
                  		
                  		var response= xmlhttp.responseXML;
                  		var data=response.getElementsByTagName("data");
                  		var x = data[0].getElementsByTagName("prjname");
                  		
                  		
                      for (var i=0; i <x.length; i++)
                  		{
                  			
                  			var newElement = {};
                  		    newElement['country'] = data[0].getElementsByTagName("prjname")[i].firstChild.nodeValue;
                  		    //alert(data[0].getElementsByTagName("e")[i].firstChild.nodeValue)
                  		    newElement['visits'] = data[0].getElementsByTagName("emp")[i].firstChild.nodeValue;
                  		  	newElement['color'] = "#FF5600";
                  		    chartDatabar.push(newElement);
                  		
                  		}
                      
                		
                      	  	
                  	}
              
          	} 
              
              AmCharts.ready(function () {
                // PIE CHART
            
             
                
                getData();
               
             
        		
              
               // window.alert("hi").setTimeout(function() {  }, 50); 
                
                
               var chartcircle = new AmCharts.AmPieChart();
             
               
               chartcircle.dataProvider = chartData;
               /* window.setTimeout(function(){
            	   alert(chartData.length);
               },5000);  */
               chartcircle.titleField = "country";
               chartcircle.valueField = "visits";

                // LEGEND
                var legend = new AmCharts.AmLegend();
                legend.align = "center";
                legend.markerType = "circle";
                chartcircle.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
                chartcircle.addLegend(legend);
                setTimeout(chartcircle.write("chartdiv"),2000);
                // WRITE
                chartcircle.write("chartdiv");
                
                getData1();
                
                var chartbar = new AmCharts.AmSerialChart();
               //alert("hello");
                chartbar.dataProvider = chartDatabar;
			    
			    chartbar.categoryField = "country";
			    //chartbar.startDuration = 1;
			
			     // AXES
			     // category
			     var categoryAxis = chartbar.categoryAxis;
			     categoryAxis.labelRotation = 45; // this line makes category values to be rotated
			     categoryAxis.gridAlpha = 0;
			     categoryAxis.fillAlpha = 1;
			     categoryAxis.fillColor = "#FAFAFA";
			     categoryAxis.gridPosition = "start";
			
			     // value
			     var valueAxis = new AmCharts.ValueAxis();
			     valueAxis.dashLength = 5;
			     valueAxis.title = "Number of Employees";
			     valueAxis.axisAlpha = 0;
			     chartbar.addValueAxis(valueAxis);
			
			     // GRAPH
			     var graph = new AmCharts.AmGraph();
			     graph.valueField = "visits";
			     graph.colorField = "color";
			     graph.balloonText = "<b>[[category]]: [[value]]</b>";
			     graph.type = "column";
			     graph.lineAlpha = 0;
			     graph.fillAlphas = 1;
			     chartbar.addGraph(graph);
			
			     // CURSOR
			     var chartCursor = new AmCharts.ChartCursor();
			     chartCursor.cursorAlpha = 0;
			     chartCursor.zoomable = false;
			     chartCursor.categoryBalloonEnabled = false;
			     chartbar.addChartCursor(chartCursor);
			
			
			     // WRITE
			     chartbar.write("chartdiv1");
                
               
              
            });    
            
           
			
            
		
        </script>
        
  -->
  <link href="css/style.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="js/jquery-1.6.3.js"></script>
<script src="js/raphael-min.js" type="text/javascript"></script>
<script src="js/elycharts.js" type="text/javascript"></script>



<%
ArrayList<GraphBean> grlist=ReportDAO.getProjectwiseEmployee();

ArrayList<GraphBean> grlistdesg=ReportDAO.getDesignationwiseEmployee();

JSONArray jsona = JSONArray.fromObject(grlist);
JSONArray jsonadesg = JSONArray.fromObject(grlistdesg);
System.out.println("json 1 is ...."+jsonadesg);
%>
<script type="text/javascript">

var jsonarray = <%=jsona%>
var json_projectcode=[];
var json_totalemp=[];


var jsonarraydesg = <%=jsonadesg%>
var json_desg=[];
var json_totalempdesg=[];


json_data = eval(jsonarray);
json_datadesg = eval(jsonarraydesg);

var projectcode=new Array();
var totalemp=new Array();
var totalemp_tooltip=new Array();

var desgnation=new Array();
var totalempdesg=new Array();
var totalempdesg_tooltip=new Array();


var j=0;
var j1=0;

for(var i=json_data.length-1;i>=0;i--){
	projectcode[j]=json_data[i].prj_Code;
	totalemp[j]=json_data[i].totalemp;
	j++;
	
}

for(var i=json_datadesg.length-1;i>=0;i--){
	desgnation[j]=json_datadesg[i].designation;
	totalempdesg[j]=json_datadesg[i].totalemp;
	j++;
	
}

for (var i=json_data.length-1;i>=0;i--){
	totalemp_tooltip[i]="<div class='label'><p class='charlab'>Total Emp: " + totalemp[i] + " </p></div>";
	
}

for (var i=json_datadesg.length-1;i>=0;i--){
	totalempdesg_tooltip[i]="<div class='label'><p class='charlab'>"+desgnation[i]+":<br>" + totalempdesg[i] + " </p></div>";
	
}

	 $(function() {
		  $("#chart").chart({
		  template: "line_basic_6",
		  tooltips: {
		    serie1: totalemp_tooltip
		   
		  },
		  values: {
		    serie1: totalemp
		   
		   
		  },
		  labels: projectcode,
		  defaultSeries: {
		    type: "bar",
		    stacked: true
		  },
		  axis: {
		    r: {
		      max: 100,
		      suffix: "%"
		    }
		  }
		});

		});

		$.elycharts.templates['line_basic_6'] = {
		  type: "line",
		  margins: [50, 60, 60, 60],
		  defaultSeries: {
		    highlight: {
		      newProps: {
		        r: 8,
		        opacity: 1
		      },
		      overlayProps: {
		        fill: "white",
		        opacity: 0.2
		      }
		    }
		  },
		  series: {
		    serie1: {
		      color: "90-#003000-#009000",
		      tooltip: {
		        frameProps: {
		          stroke: "green"
		        }
		      }
		    }
		  },
		  defaultAxis: {
		    labels: true,
		    
		  },
		  
		  axis: {
		    x: {
		      labelsRotate: 45,
		      labelsProps: {
		        font: "15px Verdana"
		      }
		    }
		  },
		  
		  features: {
		    grid: {
		      draw: true,
		      forceBorder: true,
		      ny: 5
		    }
		  },
		  barMargins: 10
		};

		 
		
		 $(function() {
			  $("#chartdiv").chart({
			  template: "line_basic_2",
			  tooltips: {
			    serie1: totalemp_tooltip,
			   
			  },
			  values: {
			    serie1: totalemp,
			    
			  },
			  labels: projectcode,
			  defaultSeries: {
			    fill: true,
			    stacked: false,
			    highlight: {
			      scale: 1
			    },
			    startAnimation: {
			      active: true,
			      type: "grow",
			      easing: "bounce"
			    }
			  }
			});

			});

			$.elycharts.templates['line_basic_2'] = {
			  type: "line",
			  margins: [50, 50, 60, 50],
			  defaultSeries: {
			    plotProps: {
			      "stroke-width": 4
			    },
			    dot: true,
			    dotProps: {
			      stroke: "white",
			      "stroke-width": 2
			    }
			  },
			  series: {
			    serie1: {
			      color: "green"
			    },
			    serie2: {
			      color: "blue"
			    }
			  },
			  defaultAxis: {
			    labels: true,
			   
			  },
			  
			  axis: {
				    x: {
				      labelsRotate: 45,
				      
				      labelsProps: {
				        font: "15px Verdana",
				        	
				      }
				    }
				  },
			  features: {
			    grid: {
			      draw: [true, false],
			      props: {
			        "stroke-dasharray": "-"
			      }
			    },
			    legend: {
			      horizontal: false,
			      width: 300,
			      height: 700,
			      x: 220,
			      y: 250,
			      dotType: "circle",
			      dotProps: {
			        stroke: "white",
			        "stroke-width": 10
			      },
			      borderProps: {
			        opacity: 0.3,
			        fill: "#008800",
			        "stroke-width": 0
			      }
			    }
			  }
			};


			
			function Clickheretoprint(panel)
			{ 
				
					var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
					    disp_setting+="scrollbars=yes,width=1000, height=800, left=200, top=10"; 
					var content_value = document.getElementById(panel).innerHTML; 
					
					var docprint=window.open("","",disp_setting); 
						docprint.document.open(); 
						docprint.document.write('<html><head><title>Inel Power System</title>'); 
						docprint.document.write("<style type=\"text/css\">	body,td,th { font-family: Times New Roman; font-size: 14px;}	</style></head><body onLoad=\"self.print()\" style=\"font-family:Times New Roman; font-size:9px;\" ><center>");          
						docprint.document.write(content_value);          
						docprint.document.write('</center></body></html>'); 
						docprint.document.close(); 
						docprint.focus(); 
			}
		
</script>

</head>
<body  style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h2> Employees Information </h1>
	</div>
	<!-- end page-heading -->

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
			<div id="table-content">
			 <!-- <div id="chartdiv" style="width: 100%; height: 400px;"></div> -->
			
        <table align="center" >
           <tr><td> <a href="javascript:Clickheretoprint('barchart')">	Click here to print</a> </td></tr>
            <tr><td width="600px">
          <div id="barchart"><font size="3" style="font: bolder;">Project wise Employees  </font>  
          <br><font size="2" style="font: bolder;">Graph on Project Code(x) Vs Number of Employees(y)  </font>
          <div id="chart" style="width: 900px; height: 400px;padding-left: 20px;" ></div></div>
            </td></tr>
            <tr>
                
              </tr>
              <tr>  
                </tr>
                 <tr><td> <a href="javascript:Clickheretoprint('linechart')">	Click here to print</a> </td></tr>
                <tr><td width="600px">
          <div id="linechart"><font size="3" style="font: bolder;">Designation wise Employess  </font>  
          <br><font size="2" style="font: bolder;">Graph on Designation(x) Vs Number of Employees(y)  </font>
          <div id="chartdiv" style="width: 900px; height: 400px;padding-left: 20px;" ></div></div>
            </td></tr>
            
        </table>
			
			
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