<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%-- <%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%> --%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.*"%>
<%@page import="payroll.Core.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DATA CONVERSION</title>
 <script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
</script>
</head>
<body>
<%


//FOR SAL1612 table to paytran_stage
String emppp="";  
try {
	Connection con=ConnectionManager.getConnection();
	Statement stmt=con.createStatement();
	Statement stmt1=con.createStatement();
	Statement stmt2=con.createStatement();
	Statement stmt3=con.createStatement();
	ResultSet rs;
	ResultSet rs2;
	ResultSet rs1;
	String table ="SAL1303";
	String table1="paytran_apr26";
	String dt="31-mar-2013";
	String mnth="apr-2014";
	String empcode="";
	String empno="";
	int trncd; 
	int count=1;
	String col="";
rs1=stmt.executeQuery("select empcode,empno from empmast where empcode in (select distinct emp_code from  "+table+" )"+ 
" order by empno ");	

while(rs1.next())
{
	empcode=rs1.getString("empcode");
	empno=rs1.getString("empno");
	emppp=empno;
rs=stmt1.executeQuery("select * from [namco_aug].[dbo].[cds] order by trncd");
while(rs.next())
{
trncd=rs.getInt("trncd");
col=rs.getString("DISC");
try
{
rs2=stmt2.executeQuery("select "+col+" from "+table+" where emp_code like '"+empcode+"'");
}
catch(Exception e)
{
	System.out.println("into continue......."+e); 
	continue;
	 
}
while(rs2.next())
{
	
	if(trncd!=999 && trncd!=998)
	{
		if(trncd==997)
		{
			trncd=999;
			stmt3.execute(" if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
					" update paytran_apr26 set INP_AMT="+rs2.getFloat(1)+""+
					" where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'','','"+dt+"','P')  ");
			System.out.println(" if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
					" update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
					" where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'','','"+dt+"','P')  ");
			stmt3.execute(" if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD=199) "+
					" update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
					" where EMPNO="+empno+" and TRNCD=199  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+",199,0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
			stmt3.execute(" if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD=198) "+
					" update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+
					" where EMPNO="+empno+" and TRNCD=198  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+",198,0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
																	
		}else
		{
			
		if(trncd>100 && trncd<200)
		{
		stmt3.execute("if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
				"update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
				"where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
				"insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
		}
		else
		{
			{
				stmt3.execute("if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
						"update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
						"where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
						"insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
		"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
				}
			
			System.out.println("1234");
			System.out.println(" if exists(select * from paytran_apr26 where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
					"update paytran_apr26 set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
					"where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
					"insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
					"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
			
		}
		 if(trncd>=101 && trncd <=196)
		{ 
			/*  stmt3.execute(" begin insert into onamt (emp_cat,trncd,srno,onamtcd,amt_type) "+
							"values("+empno+","+trncd+",1,"+trncd+",'C'); "+
							"  insert into ctcdisplay (empno,trncd,valuetype,value,dependon,inp_amt,pf,esic,pt)"+
							" values("+empno+","+trncd+",1,"+rs2.getFloat(1)+","+(trncd==101?199:trncd)+","+rs2.getFloat(1)+",1,0,1) end") ; */
			 
			//" insert into slab ([EFFDATE],[EMP_CAT],[TRNCD],[SRNO],[FRMAMT],[TOAMT],[PER],[MINAMT],[MAXAMT],[FIXAMT],[ON_AMT_CD]) "+
					//		" Values('31-Dec-2099',"+empno+","+trncd+",1,0,9999999,0,0,0,"+rs2.getFloat(1)+",0)"+
						//	 
		} 					
		}
	}
	else
	{
		String c1="";
		String c2="";
		if(trncd==998)
		{c1="CAL_AMT";
		stmt3.execute(" begin update "+table1+" set "+c1+"= "+rs2.getFloat(1)+"  where empno="+empno+" and  trndt= '"+dt+"' and trncd=999 end ");
		
		}else if(trncd==999)
		{
			c1="ADJ_AMT";
			c2="NET_AMT";
			System.out.println ("ak check ");
			System.out.println (" begin update "+table1+" set "+c1+"= "+rs2.getFloat(1)+" , "+c2+"= "+rs2.getFloat(1)+"  where empno="+empno+" and  trndt= '"+dt+"' and trncd=999 end ");
			
			stmt3.execute(" begin update "+table1+" set "+c1+"= "+rs2.getFloat(1)+" , "+c2+"= "+rs2.getFloat(1)+"  where empno="+empno+" and  trndt= '"+dt+"' and trncd=999 end ");
		}
		
	}

}


}

/* stmt3.execute(" begin insert into sal_details "+ 
"(EMPNO,SAL_MONTH,SAL_STATUS,SAL_PAID_DATE)"+
" values("+empno+",'"+mnth+"','FINALIZED','"+dt+"')  end "); */  
//System.out.println("EMPNO===="+emppp);
System.out.println("count ==== :  "+count++);
emppp="";
}
}
          		catch(Exception e)
          		{
          			 System.out.println("EMPNO===="+emppp+"--------------------MASTER ERROR-----------------"+e);
          			 ErrorLog el=new ErrorLog();
          				el.errorLog("SALARY CONVERTION trial.jsp",e.toString());
          		}



  
%>

</body>
</html>