<%@page import="java.util.concurrent.CountDownLatch"%>
<%-- <%@page import="com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array"%> --%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="payroll.DAO.GratuityDAO"%>
<%@page import="payroll.DAO.TestHandler"%>
<%@page import="payroll.Model.testBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="payroll.DAO.GradeHandler"%>
<%@page import="payroll.Model.GradeBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Core.Utility"%>
<%@page import="java.util.Calendar"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Connection"%>
<%@page import="payroll.Core.VDA_Calculation"%>
<%@page import="payroll.DAO.VdaDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%


try
{
	

	System.out.println((10 & 20));

	
	
	
}
catch(ArithmeticException r)
{
	int a=10/0;
}
catch(Exception e)
{
	System.out.println("into main exception");	
}
/* catch(ArithmeticException r)
{
	int a=10/0;
} */
/* 



CountDownLatch latch = new CountDownLatch(1);

String emp="118,128,139,185,204,241,246,267,736,814";
String emp1="379,388,446,447,448,449,450,451,452,454";
String emp2="789,790,791,792,793,794,795,796,797,798";
MyThread t1 = new MyThread(latch,"30-Sep-2015",emp,"1111");
MyThread t2 = new MyThread(latch,"30-Sep-2015",emp1,"2222");
MyThread t3 = new MyThread(latch,"30-Sep-2015",emp2,"3333");
new Thread(t1).start();
new Thread(t2).start();
new Thread(t3).start();
//Do whatever you want
latch.countDown();          //This will inform all the threads to start
//Continue to do whatever

t1=null;
t2=null;
t3=null;
System.out.println("\n\n\n-----------------------EXECUTED--------------------------");





String s="Sachin";  
s.concat(" Tendulkar");//concat() method appends the string at the end  
System.out.println("========"+s);
 */

/*

String[] fruits1 = new String[] {"Pineapple","Apple", "Orange", "Banana","C","A"}; 

Arrays.sort(fruits1);
	
int i=0;
for(String temp: fruits1){
	System.out.println("fruits " + ++i + " : " + temp);
}

Fruit[] fruits = new Fruit[4];

Fruit pineappale = new Fruit("Pineapple", "Pineapple description",70); 
Fruit apple = new Fruit("Apple", "Apple description",100); 
Fruit orange = new Fruit("Orange", "Orange description",80); 
Fruit banana = new Fruit("Banana", "Banana description",90);

fruits[0]=pineappale;
fruits[1]=apple;
fruits[2]=orange;
fruits[3]=banana;

Arrays.sort(fruits);

for(Fruit f: fruits){

System.out.println("fruits Q=" +f.getFruitName()+"="+f.getFruitDesc()+"="+f.getQuantity());

}

System.out.println("\n\n");

Arrays.sort(fruits,  Fruit.FruitNameComparator);

for(Fruit f: fruits){

System.out.println("fruits N=" +f.getFruitName()+"="+f.getFruitDesc()+"="+f.getQuantity());

}



}



/* 
String INPUT ="This is java which is java";
System.out.println(INPUT);
 char ch[]=INPUT.toCharArray();

for(int i=INPUT.length()-1;i>=0;i--)
System.out.print(ch[i]);
 
StringBuffer sb=new StringBuffer(INPUT);

System.out.println("--"+sb.reverse());

 */

 /* String REGEX ="is java";
 String INPUT ="This is java which is java";
 String REPLACE =" ";

 String []mm=REGEX.split(" ");
 String []sr=INPUT.split(" ");
 
 
 for (int j=0;j<mm.length-1;j++)
	 {
		 
		 if(!sr[i].contains(mm[j]))
		 {
			 System.out.print(sr[i]);
		 }
		 
		 
	 }
	 System.out.println("");
	 
 } */
 
 /* 
 
Pattern p =Pattern.compile(REGEX);
// get a matcher object
System.out.println("before REPLACE= "+INPUT);
Matcher m = p.matcher(INPUT);
INPUT = m.replaceAll(REPLACE);
System.out.println("AFTER REPLACE= "+INPUT);


 */







/* GratuityDAO ob=new GratuityDAO(); 
ob. getGratuityAMt("465");	 */	

/* 
TestHandler t=new TestHandler();

ArrayList<testBean> tb= new ArrayList<testBean>();
tb=t.getAllLeave("128");

 */


/* 


--------------------------------
--------------------------------
--------------------------------
--------------------------------
--------------------------------
1>ADD VDA POSTING in Menues
--------------------------------
2>add field in VDAMAST 
as ..
FINALIZE_FLAG int default null 
--------------------------------
3>
//script for vda difference posting table

USE [NAMCO_PAYROLL_TEST]
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Vda_Difference](
	[EMPNO] [numeric](18, 0) NULL,
	[MONTH_OF] [date] NULL,
	[GIVEN_VDA] [numeric](30, 2) NULL,
	[PAID_DAYS] [numeric](18, 2) NULL,
	[EXACT_VDA] [numeric](30, 2) NULL,
	[VDA_DIFF] [numeric](18, 2) NULL,
	[CALCULATED_MONTH] [date] NULL
) ON [PRIMARY]

GO












/* 
float a=2393.85F;
String abc=Float.toString(a);
System.out.println("-------------"+abc);
 String[] frac =abc.split(".");

System.out.println(""+frac.length); 






     
VDA_Calculation vc=new VDA_Calculation();
//vc.vdaCalc("6","1-Sep-2015","desig");
GradeHandler GH=new GradeHandler();
ArrayList<GradeBean> gradeList =new ArrayList<GradeBean>();

 for(int i=6;i<=6;i++)
{
gradeList=GH.getGradeDetails("6");
System.out.println(gradeList.size());
 for(GradeBean gBean: gradeList)
	 {
		int srno=gBean.getSerialNumber();
		float	 basic=gBean.getBasic();
		float	 da=gBean.getDaValue();
		float	hra=gBean.getHraValue();
 */
/* 
VDA_Calculation.vda_Calc_new("1","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("2","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("3","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("4","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("5","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("6","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("7","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("8","21-Nov-2015","desig",1314,1344,1344);
VDA_Calculation.vda_Calc_new("9","21-Nov-2015","desig",1314,1344,1344); 
  */
	/*  } }*/

 
      /* SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
    	String date2="" ,date1="";
	    Date ddd0=new Date(ReportDAO.BOM("1-Jul-2015"));
	 
	 
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(ddd0);
	    cal.add(Calendar.MONTH, 1);
	    System.out.println("------"+ cal.getTime());
	    Date ddd1=cal.getTime();
	    date1=format.format(ddd1);
	    System.out.println("-date 1-----"+date1);
	    cal.setTime(ddd1);
	    cal.add(Calendar.MONTH, 1);
	    System.out.println("------"+ cal.getTime());
	    Date ddd2=cal.getTime();
	    date2=format.format(ddd2);
	    System.out.println("--date2----"+date2); */
%>

</body>
</html>