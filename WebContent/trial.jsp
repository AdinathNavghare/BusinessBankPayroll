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


<!-- RUN for remaining employee -->
<!-- RUN for update aadhar number	 -->
<!-- RUN for Transfer	 -->

<!-- update EMPMAST set STATUS='N' where EMPLOYEE_TYPE in (6,10) NONACTIVE -->



<!-- delete from SAL_DETAILS where SAL_MONTH='MAY-2015' and  EMPNO in(select empno from empmast where EMPCODE in ( SELECT distinct EMP_CODE  FROM [NAMCO_DBF].[dbo].[SAL1505])) -->
<!-- delete from ONAMT where EMP_CAT!=0 and  EMP_CAT in(select empno from empmast where EMPCODE in ( SELECT distinct EMP_CODE  FROM [NAMCO_DBF].[dbo].[SAL1505]))-->
<!-- delete from CTCDISPLAY where EMPNO in(select empno from empmast where EMPCODE in ( SELECT distinct EMP_CODE  FROM [NAMCO_DBF].[dbo].[SAL1505])) -->
<!-- delete paytran -->


<body>
 <%
 
 /*
//for insert into EMP_MAST to EMPMAST
  try {
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	Statement st2=con.createStatement();
	Statement st3=con.createStatement();
	ResultSet rs=null;
	ResultSet rs1=null;
	rs1=st3.executeQuery("select * from [NAMCO_DBF].[dbo].[EMP_MAST] order by join_date ");
	
	while(rs1.next())
	{
	String DOJ="";
	DOJ=rs1.getString("JOIN_DATE");
	System.out.println("JOIN_DATE-------"+DOJ);
	rs=st1.executeQuery("select EMP_CODE from [NAMCO_DBF].[dbo].[PERSONL]  where EMP_CODE ='"+rs1.getString("EMP_CODE")+"'");
	int count =1;
	while(rs.next())
	{
	
		st2.execute(" BEGIN "+
			"	DECLARE @CONDITION varchar(100)"+     
			"	declare @fullname varchar(100)   "+
			"	declare @oldfullname varchar(100)   "+
			"	declare @LNAME varchar(100)     "+
			"	 declare @MNAME varchar(100)      "+
			"	 declare @CAST varchar(100)      "+
			"	 declare @CAT varchar(100)      "+
			"	 declare @REL varchar(100)      "+
			"	 declare @CASTCD varchar(100)      "+
			"	 declare @CATEGORYCD varchar(100)      "+
			"	 declare @RELEGENCD varchar(100)      "+
			"	 declare @FNAME varchar(100)     "+
			"	 declare @EMPCODE varchar(100)     "+ 
			"	 declare @GENDER varchar(100)   "+
			"	 declare @DOB varchar(100)   "+
			"	 declare @blood varchar(100)   "+
			"	 declare @DOJ varchar(100)   "+
			"	 declare @PANNO varchar(100)   "+
			"	 declare @empno varchar(100)   "+
			"	 declare @PFNO varchar(100)      "+  
			"	 declare @salute varchar(100)      "+  
			"	 declare @aadhar nvarchar(20)      "+  
			"	 declare @handi varchar(100)      "+
			"  declare @Married varchar(100)    "+  
			"  declare @EMPLOYEE_TYPE  varchar(100)    "+  
			
			"	  SET @CONDITION='"+rs.getString(1)+"'   "+
			"	  SET @empno= (select isnull( (select MAX(empno)+1 from empmast),10))"+
			"	  set @EMPCODE= (SELECT E1.EMP_CODE FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+   
			"	  set @fullname= (SELECT E1.EMP_NAME FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
			"     set @oldfullname= (SELECT E1.OLD_NAME FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
			"	  set @LNAME= LEFT(@fullname, CharIndex(' ', @fullname) - 1)   "+
			"	  SET @MNAME=SUBSTRING(@fullname, CharIndex(' ', @fullname), LEN(@fullname) - charindex(' ', REVERSE(@fullname)) - charindex(' ', @fullname) + 1)"+	
			"	  SET @FNAME=   reverse(left(reverse(@fullname), charindex(' ', reverse(@fullname)) -1))"+ 
			"	  SET @GENDER= (SELECT  CASE WHEN 'M' = (SELECT E1.SEX   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)  THEN 'M' ELSE 'F' END)"+ 
			"	  SET @Married=(SELECT E1.MAR_STAT   FROM [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION) "+
			"	  SET @salute= (SELECT  CASE WHEN 'M' = (SELECT E1.SEX   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)  THEN 1 ELSE (select case when 'M'=@Married or 'W'=@Married then 2 else 3 END) END)"+
			"     SET @CAST=(select isnull((SELECT E1.CASTE from [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION),7))"+
			"     SET @CAT=(select isnull((SELECT E1.CASTE_CD from [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION),10))"+		
			"     SET @REL=(select isnull((SELECT E1.RELIGION from [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION),2))"	+	
			"     SET @DOJ=(SELECT JOIN_DATE from [NAMCO_DBF].[dbo].[EMP_MAST] where emp_code= @CONDITION) "+
			"     SET @CASTCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'CASTE' and LKP_DISC like @CAST)"+
			"     SET @CATEGORYCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE='CATE' and LKP_DISC like @CAT)"+
			"     SET @RELEGENCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'RELIG' and LKP_DISC like @REL)"+			
			"     SET @EMPLOYEE_TYPE=(SELECT E1.TYPE   FROM [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION) "+		
			"	  SET @DOB=(SELECT E1.BIRTH_DT   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) "+
			"     SET @blood=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'BLOOD_GRP' and LKP_DISC like ( select isnull((select BLD_GRP from [NAMCO_DBF].[dbo].[PERSONL] E1  WHERE E1.EMP_CODE LIKE @CONDITION),'BLOOD_GRP')))"+	  
			"     SET @PFNO=(SELECT E1.PF_NO   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+ 
			"	  SET @handi=(SELECT E1.HANDICAP   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) "+	 
			"	  SET @PANNO=(SELECT E1.PAN_NO   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) "+
			"     SET @aadhar= (SELECT  E1.adhar_no FROM [NAMCO_DBF].[dbo].[PMJJ] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
			""+
			"	  INSERT INTO EMPMAST (EMPNO,EMPCODE,LNAME,FNAME,MNAME,GENDER,DOJ,DOB,PANNO,PFNO,married,EMPLOYEE_TYPE,SALUTE,CASTCD,CATEGORYCD,RELEGENCD,STATUS,BGRP,DISABILYN,AADHAARNUM) "+
			"	                VALUES(@empno,@EMPCODE,@LNAME,@MNAME,@FNAME,@GENDER,@DOJ,@DOB,@PANNO,@PFNO,@Married,@EMPLOYEE_TYPE,@salute,@CASTCD,@CATEGORYCD,@RELEGENCD,'A',@blood,@handi,@aadhar)"+
			" "+	  
			
			
			" insert into lookup values('ET',@empno,(select case when GENDER='M' then 'MR.' else ( select case when @Married='M' or @Married='W'  then 'MRS.' else 'MISS' end ) end  from EMPMAST where empno=@empno) +(select  ''+Fname+' '+mname+' '+lname  from EMPMAST where empno=@empno),0) "+
			"  if  @oldfullname is NOT NULL     insert into EDITNAME ([SRNO],[EMPNO],[CHANGEDATE],[OLDNAME],[FNAME],[MNAME],[LNAME]) "+
			"values(1,@empno,'13-Oct-2015',@oldfullname,@MNAME,@FNAME,@LNAME);  	end"+
			" "+
			" ");
			System.out.println("------------------"+(count++));
	}
	
	}
	
	} catch (SQLException e) {
		
		e.printStackTrace();
		ErrorLog el=new ErrorLog();
		el.errorLog("EMP_MAST to EMPMAST trial.jsp",e.toString());
	}
 
    */

    
    
    /* 
    
     // for remaining employees for insert into EMP_MAST to EMPMAST
  try {
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	Statement st2=con.createStatement();
	Statement st3=con.createStatement();
	ResultSet rs=null;
	ResultSet rs1=null;
	
	rs=st1.executeQuery("select * from [NAMCO_DBF].[dbo].[EMP_MAST]  where EMP_CODE  not in(select empcode from empmast) order by join_date ");
	int count =1;
	while(rs.next())
	{
		System.out.println("into loop------------");
		st2.execute(" BEGIN "+
			"	DECLARE @CONDITION varchar(100)"+     
			"	declare @fullname varchar(100)   "+
			"	declare @oldfullname varchar(100)   "+
			"	declare @LNAME varchar(100)     "+
			"	 declare @MNAME varchar(100)      "+
			"	 declare @CAST varchar(100)      "+
			"	 declare @CAT varchar(100)      "+
			"	 declare @REL varchar(100)      "+
			"	 declare @CASTCD varchar(100)      "+
			"	 declare @CATEGORYCD varchar(100)      "+
			"	 declare @RELEGENCD varchar(100)      "+
			"	 declare @FNAME varchar(100)     "+
			"	 declare @EMPCODE varchar(100)     "+ 
			"	 declare @GENDER varchar(100)   "+
			"	 declare @DOB varchar(100)   "+
			"	 declare @blood varchar(100)   "+
			"	 declare @DOJ varchar(100)   "+
			"	 declare @PANNO varchar(100)   "+
			"	 declare @empno varchar(100)   "+
			"	 declare @PFNO varchar(100)      "+  
			"	 declare @salute varchar(100)      "+  
			"	 declare @aadhar nvarchar(20)      "+  
			"	 declare @handi varchar(100)      "+
			"  declare @Married varchar(100)    "+  
			"  declare @EMPLOYEE_TYPE  varchar(100)    "+
			 "     declare @DESIG varchar(100)"+  
		      "     declare @PRJ_SRNO varchar(100)"+  
		      "     declare @PRJ_CD varchar(100)"+ 
			
			
			"	  SET @CONDITION='"+rs.getString("emp_code")+"' "+
			"	  SET @empno= (select isnull( (select MAX(empno)+1 from empmast),10))"+
			"	  set @EMPCODE=@CONDITION "+   
			"	  set @fullname= (SELECT E1.EMP_NAME FROM [NAMCO_DBF].[dbo].[EMP_MAST] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
			"    set @oldfullname=''"+
			"	  set @LNAME= LEFT(@fullname, CharIndex(' ', @fullname) - 1)   "+
			"	  SET @MNAME=SUBSTRING(@fullname, CharIndex(' ', @fullname), LEN(@fullname) - charindex(' ', REVERSE(@fullname)) - charindex(' ', @fullname) + 1)"+	
			"	  SET @FNAME=   reverse(left(reverse(@fullname), charindex(' ', reverse(@fullname)) -1))"+ 
			"	 SET @GENDER= (SELECT  CASE WHEN 'M' = (SELECT E1.SEX   FROM [NAMCO_DBF].[dbo].[personl] E1 WHERE E1.EMP_CODE LIKE @CONDITION)  THEN 'M' ELSE 'F' END)"+ 
			"	SET @Married=(SELECT E1.MAR_STAT   FROM [NAMCO_DBF].[dbo].[personl] E1  WHERE E1.EMP_CODE LIKE @CONDITION) "+
			"	  SET @salute= (SELECT  CASE WHEN 'M' = (SELECT E1.SEX   FROM [NAMCO_DBF].[dbo].[personl] E1 WHERE E1.EMP_CODE LIKE @CONDITION)  THEN 1 ELSE (select case when 'M'=@Married or 'W'=@Married then 2 else 3 END) END)"+
			"     SET @CAST='CASTE'"+
			"     SET @CAT='CATE'"+		
			"     SET @REL='RELIG'"	+	
			"     SET @DOJ=(SELECT JOIN_DATE from [NAMCO_DBF].[dbo].[EMP_MAST] where emp_code= @CONDITION) "+
			"     SET @CASTCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'CASTE' and LKP_DISC like @CAST)"+
			"     SET @CATEGORYCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE='CATE' and LKP_DISC like @CAT)"+
			"     SET @RELEGENCD=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'RELIG' and LKP_DISC like @REL)"+			
			"     SET @EMPLOYEE_TYPE=(SELECT E1.TYPE   FROM [NAMCO_DBF].[dbo].[EMP_MAST] E1  WHERE E1.EMP_CODE LIKE @CONDITION) "+		
			"	  SET @DOB=(SELECT E1.BIRTH_DT   FROM [NAMCO_DBF].[dbo].[personl] E1 WHERE E1.EMP_CODE LIKE @CONDITION) "+
			"     SET @blood=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE= 'BLOOD_GRP' and LKP_DISC='BLOOD_GRP')"+
			" set  @DESIG=(SELECT desg_cd from [NAMCO_DBF].[dbo].[EMP_MAST] where emp_code= @CONDITION) "+
			" set @PRJ_SRNO=(SELECT br_cd from [NAMCO_DBF].[dbo].[EMP_MAST] where emp_code= @CONDITION) "+	
			"         SET @PRJ_CD=(SELECT E1.project_code   FROM PROJECT_SITES E1 WHERE E1.SITE_ID =@PRJ_SRNO)"+
			

			"         INSERT INTO EMPMAST (EMPNO,EMPCODE,LNAME,FNAME,MNAME,PANNO,PFNO,GENDER,DOJ,MARRIED,SALUTE,EMPLOYEE_TYPE,CASTCD,CATEGORYCD,RELEGENCD,STATUS,BGRP,DISABILYN,AADHAARNUM) "+
			"	                   VALUES(@empno,@EMPCODE,@LNAME,@MNAME,@FNAME,'',0,'M',@DOJ,'U',1,@EMPLOYEE_TYPE,0,0,0,'A',0,'N','')"+
			" "+	  
			
			
			"    INSERT INTO EMPTRAN (EMPNO,TRNCD,EFFDATE,ORDER_DT,SRNO,BRANCH,ACNO,DESIG,DEPT,STATUS,PRJ_SRNO,PRJ_CODE,BANK_NAME) "+
            "     VALUES(@EMPNO,10,@DOJ,@DOJ,1,@PRJ_SRNO,0,@DESIG,0,1,@PRJ_SRNO,@PRJ_CD,1) "+
                 
			
			
			" insert into lookup values('ET',@empno,(select  ''+mname+' '+Fname+' '+lname  from EMPMAST where empno=@empno),0) "+
			"    	end");
			System.out.println("------------------"+(count++));
	}
    
    
    
    
    
    
    
    
    
  }
    catch (SQLException e) {
	
	e.printStackTrace();
	ErrorLog el=new ErrorLog();
	el.errorLog("EMP_MAST to EMPMAST trial.jsp",e.toString());
}
    */ 
        
 /*    
//for single from emp_mast to empmast value of adhar number
      try {
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		Statement st3=con.createStatement();
		ResultSet rs=null;
		rs=st1.executeQuery("select empcode from empmast");
		String adhar= new String();
		int count =1;
		while(rs.next())
		{
			
		ResultSet r1=st2.executeQuery("select CONVERT(varchar(100), Cast(ADHAR_NO as decimal(38, 0))) as adhar_no   FROM [NAMCO_DBF].[dbo].[PMJJ]   WHERE EMP_CODE LIKE '"+rs.getString(1)+"'");
		while(r1.next())
		{
			adhar=r1.getString("adhar_no");
		}
		st3.execute(" update  EMPMAST set AADHAARNUM='"+adhar+"'  WHERE EMPCODE LIKE '"+rs.getString(1)+"'  ");
				System.out.println(adhar+"------------------"+rs.getString(1));
		}
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLog el=new ErrorLog();
			el.errorLog("EMP_MAST to EMPMAST trial.jsp",e.toString());
		}  
   */
		
		
/* 		
		
		
//for insert into EMP_MAST to EMPTRAN
 try {
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	Statement st2=con.createStatement();
	ResultSet rs=null;
	int count =1;
	rs=st1.executeQuery("select distinct empcode from empmast where empcode in (select emp_code from [NAMCO_DBF].[dbo].[EMP_MAST] where  join_date is not null)");
	while(rs.next())
	{
		
	System.out.println(rs.getString(1));
		st2.execute(" BEGIN DECLARE @CONDITION varchar(100)"+ 
 	"declare @DEPT varchar(100)  "+
     "     declare @DESIG varchar(100)"+  
      "     declare @PRJ_SRNO varchar(100)"+  
      "     declare @PRJ_CD varchar(100)"+  
       "     declare @EFFDATE varchar(100)"+ 
        "      declare @ACNO varchar(100)"+
         "        declare @EMPNO varchar(100)"+
          "             SET @CONDITION='"+rs.getString(1)+"'   "+
           "            SET @EMPNO=(SELECT EMPNO FROM EMPMAST WHERE EMPCODE LIKE @CONDITION)"+  
            "           set @DEPT=(select LKP_SRNO FROM LOOKUP WHERE LKP_CODE='DEPT' AND LKP_DISC=((SELECT ISNULL((SELECT E1.DEPT_CD   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),'DEPARTMENT'))))"+   
             "          SET @DESIG= (SELECT E1.DESG_CD   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
              "         SET @PRJ_SRNO=(SELECT E1.BR_CD   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
              "         SET @PRJ_CD=(SELECT E1.project_code   FROM PROJECT_SITES E1 WHERE E1.SITE_ID =@PRJ_SRNO)"+
               "        SET @EFFDATE= (SELECT E1.JOIN_DATE   FROM [NAMCO_DBF].[dbo].[EMP_MAST] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
                "       SET @ACNO=(SELECT E1.BANK_AC   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) "+
                       
                 "      INSERT INTO EMPTRAN (EMPNO,TRNCD,EFFDATE,ORDER_DT,SRNO,BRANCH,ACNO,DESIG,DEPT,STATUS,PRJ_SRNO,PRJ_CODE,BANK_NAME) "+
                  "     VALUES(@EMPNO,10,@EFFDATE,@EFFDATE,1,@PRJ_SRNO,@ACNO,@DESIG,@DEPT,1,@PRJ_SRNO,@PRJ_CD,1) "+
                       
                       " END ");
 
 
                       System.out.println("------===========------------"+(count++));
 
 }
	
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		ErrorLog el=new ErrorLog();
		el.errorLog("EMP_MAST to EMPTRAN trial.jsp",e.toString());
	}
  
 

  
 
 */ 
  
  
  
  
 /*  
  
//for insert into TRANSFER to EMPTRAN

// first of all set null dept to ""(blank) of source table
// get distinct dept from source table 

  try {
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	Statement st2=con.createStatement();
	Statement st3=con.createStatement();
	Statement st4=con.createStatement();
	Statement st5=con.createStatement();
	Statement st6=con.createStatement();
	Statement st7=con.createStatement();
	
	ResultSet rs=null;
	int count =1;
	rs=st1.executeQuery("select * from [NAMCO_DBF].[dbo].[TRANSFER] where emp_cd in (select distinct empcode from empmast) and  SANCTION='Y' and EDATE is not null order by emp_cd,trf_date ");
	while(rs.next())
	{
		String empno="";
		String dept="";
		String dept_srno="";
		String acno="";
		String pr_cd="TNMC - 999";
	
	ResultSet r1=st3.executeQuery("select empno from empmast where empcode='"+rs.getString("EMP_CD")+"'");
		while (r1.next())
		{
			empno=r1.getString("EMPNO");
		}
	
	
	String abc=rs.getString("new_dept")==null?"":rs.getString("new_dept");
	if(abc=="")
	{
		System.out.println(rs.getString("EMP_CD")+"+abc==in BLANCK========="+abc);
		ResultSet r2=st4.executeQuery("select dept from emptran where empno="+empno+" and srno=(select max(srno) from emptran where empno="+empno+")");
		
		if (r2.next())
		{
			dept_srno=r2.getString("dept");
		}
		else
		{
			
			dept_srno="0";
			
		}
		
	}
	else
	{
		ResultSet r3=st5.executeQuery("select LKP_SRNO from LOOKUP where LKP_CODE='DEPT' and LKP_DISC='"+rs.getString("new_dept")+"'");
		if(r3.next())
		{
			dept_srno=r3.getString("LKP_SRNO");
		}
		else
		{
			dept_srno="0";
		}
	}
	
	
	ResultSet r4=st6.executeQuery("select acno from emptran where empno="+empno+" and srno=(select max(srno) from emptran where empno="+empno+")");
	
	if(r4.next())
	{
		acno=r4.getString("acno");
	}
	else
	{
		acno="0";
	}
	
	ResultSet r5=st7.executeQuery("select Project_Code  from Project_Sites where SITE_ID="+rs.getString("NEW_BR_CD")+"");
	while(r5.next())
	{
		pr_cd=r5.getString("Project_Code");
	}
	
	
	
	
	
	
	st2.execute(" BEGIN DECLARE @CONDITION varchar(100)"+ 
 	"declare @DEPT varchar(100)  "+
     "     declare @DESIG varchar(100)"+
     "     declare @dp varchar(100)"+
    
     "     declare @SRNO varchar(100)"+  
      "     declare @PRJ_SRNO varchar(100)"+  
       "     declare @EFFDATE varchar(100)"+ 
        "      declare @ACNO varchar(100)"+
          "             SET @CONDITION='"+rs.getString("EMP_CD")+"'"+
           "            SET @SRNO=(select isnull((select max(srno)+1 from emptran where empno="+empno+"),1))"+
             
             " SET @DESIG="+rs.getString("DESG_CD")+"" +
              "         SET @PRJ_SRNO="+rs.getString("NEW_BR_CD")+"" +
               "        SET @EFFDATE='"+rs.getString("TRF_DATE")+"' "+
                 "      INSERT INTO EMPTRAN (EMPNO,TRNCD,EFFDATE,ORDER_DT,SRNO,BRANCH,ACNO,DESIG,DEPT,STATUS,PRJ_SRNO,BANK_NAME,PRJ_CODE) "+
                  "     VALUES("+empno+",60,@EFFDATE,@EFFDATE,@SRNO,@PRJ_SRNO,'"+acno+"',@DESIG,"+dept_srno+",1,@PRJ_SRNO,1,'"+pr_cd+"') "+
                       
                       " END ");

	

 }
	
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		ErrorLog el=new ErrorLog();
		el.errorLog("TRANSFER to EMPTRAN trial.jsp",e.toString());
	}
  
 

  
  
   
  
   */
  
  
  
  
  
  
  
  
  
/*   
  
//For EMPAUX FOR personl  

// first convert telephone to varchar in database
  
   try {
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		ResultSet rs=null;
		rs=st1.executeQuery("select empcode from empmast order by empno");
		int count =1;
		while(rs.next())
		{
		System.out.println(rs.getString(1));
			st2.execute(" BEGIN "+
				"	DECLARE @CONDITION varchar(100) "+
				""+			
				"	 declare @ADDR1 varchar(100)   "+
				"	 declare @ADDR2 varchar(100)  "+
				"  declare @ADDR3 varchar(100) "+
				"	 declare @city varchar(100)  "+
				"	 declare @PIN varchar(100)  "+
				"	 declare @st varchar(100)  "+
				"	 declare @ph varchar(100)"+
				" declare @empno varchar(100)  "+
				"  SET @CONDITION='"+rs.getString(1)+"' "+
				"	  SET @empno=  (select empno  from empmast where  EMPCODE LIKE @CONDITION)"+
				" SET @ADDR1= (select isnull((SELECT E1.P_ADDR1   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),''))"+
				" SET @ADDR2=(SELECT isnull((SELECT E1.p_ADDR2   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),''))"+
					  
					  
		"			  SET @ADDR3=(SELECT E1.P_ADDR3   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) +' ,'"+
		"			  SET @city=(select lkp_srno from lookup where lkp_code='CITY' and lkp_DISC=(select isnull((SELECT E1.p_CITY  FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),'CITY')))"+
		"			  SET @PIN=(SELECT E1.P_PINCD   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+
		"			  SET @st=(SELECT LKP_RECR  FROM LOOKUP where lkp_CODE='CITY' and lkp_SRNO=@city)"+
					  
		"			  SET @ph=(select isnull((SELECT E1.P_PHONE   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),0))"+ 
		"			  INSERT INTO EMPAUX (EMPNO,ADDR1,ADDR2,PIN,TELNO,ADDRTYPE,STATE) "+
		"			                VALUES(@empno,@ADDR1+@ADDR2,@ADDR3+@city,@PIN,@ph,'PA',@st)"+
					  
					   
		" SET @ADDR1= (select isnull((SELECT E1.t_ADDR1   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),''))"+
		" SET @ADDR2=(SELECT isnull((SELECT E1.t_ADDR2   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),''))"+
		"			   SET @ADDR3=(SELECT E1.t_ADDR3   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION) +' ,'"+
		"			  SET @city=(select lkp_srno from lookup where lkp_code='CITY' and lkp_DISC=(select isnull((SELECT E1.T_CITY  FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),'CITY')))"+
				
		"			  SET @PIN=(SELECT E1.t_PINCD   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION)"+ 
					  
		"			  SET @ph=(select isnull((SELECT E1.t_PHONE   FROM [NAMCO_DBF].[dbo].[PERSONL] E1 WHERE E1.EMP_CODE LIKE @CONDITION),0))"+ 
		"			  INSERT INTO EMPAUX (EMPNO,ADDR1,ADDR2,PIN,TELNO,ADDRTYPE,STATE) "+
		"			  VALUES(@empno,@ADDR1+@ADDR2,@ADDR3+@city ,@PIN,@ph,'CA',@st) "+
					  
					 "  END ");
				System.out.println("-------///-----------"+(count++));
		}
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLog el=new ErrorLog();
			el.errorLog("PERSONL to EMPAUX trial.jsp",e.toString());
		}
 
 
  
  */
 
 
//For QUAL FROM QUAL_DTL  
  
   /* try {
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		ResultSet rs=null;
		rs=st1.executeQuery("select emp_cd,srno from QUAL_DTL order by emp_cd,srno");
		int count =1;
		while(rs.next())
		{
		System.out.println(rs.getString(1));
			st2.execute("  BEGIN DECLARE @CONDITION varchar(100) "+
				 	" declare @REM varchar(100)  		"+
			          "declare @CLASS varchar(100)  "+
			      "     declare @DEGREE varchar(100)  "+
			       "     declare @INST varchar(100) "+
			        "      declare @PER varchar(100)"+		
			         "        declare @EMPNO varchar(100)"+
			          "       declare @PASSYEAR varchar(100)"+
			           "            SET @CONDITION='"+rs.getString(1)+"' "+
			            "           SET @EMPNO=(SELECT EMPNO FROM EMPMAST WHERE EMPCODE LIKE '"+rs.getString(1)+"')					"+  
			             "          set @REM=  (SELECT SUBJECT FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+")  "+
			              "         SET @CLASS=(SELECT CLASS FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+") "+		
			               "        SET @DEGREE= (SELECT QUAL_CD   FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+")"+
			                "       SET @INST=(SELECT  UNIV_INST   FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+")"+
			                 "      SET @PER= (SELECT PERCENTAGE   FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+")"+
			                  "     SET @PASSYEAR=(SELECT YEAR_PASS  FROM QUAL_DTL WHERE EMP_CD LIKE @CONDITION and srno="+rs.getString(2)+")"+
			                       
			                   "    INSERT INTO QUAL (EMPNO,SRNO,DEGREE,INST,PER,PASSYEAR,CLASS,REM,STATUS) "+
			                    "   VALUES(@EMPNO,"+rs.getString(2)+",@DEGREE,@INST,@PER,@PASSYEAR,@CLASS,@REM,1) "+
			                       
			                     "   END ");
				System.out.println("------------------"+(count++));
		}
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLog el=new ErrorLog();
			el.errorLog("QUAL_DTL to QUAL trial.jsp",e.toString());
		}
  */
 
  
  
  
  
   
  
/*   //sql QUERY to insert from BRANCH1 to Project_Sites
  
 try {
	 int count=1;
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		st1.execute("  BEGIN  insert into  [DTS3_DEMO_DB_fOR_JAVA].[dbo].[Project_Sites] "+
	"	  ([Site_ID],[Project_Code],[Site_name]      ,[Site_Location]      ,[ADDR1]      ,[ADDR2]      ,[ADDR3]      ,[SIte_CITY]      ,[PINCD]      ,[PHONE1] "+
	"	  ,[PHONE2]      ,[FAX]      ,[START_DT]      ,[EDATE]             ,[I_SHIFT_F] "+
	"	  ,[I_SHIFT_T]      ,[II_SHIFT_F]      ,[II_SHIFT_T]      ,[I_CASH_F]      ,[I_CASH_T]      ,[II_CASH_F] "+
	"	  ,[II_CASH_T]      ,[WEEK_OFF],[Site_Status] ,[Site_Isdeleted]  ) "+
		        
		        
	"	   select [BR_CD] as Site_ID,'TNMC - '+ CONVERT(varchar,BR_CD) as Project_Code,[BR_NAME] as Site_name     ,[ZONE]      ,[ADDR1]      ,[ADDR2]      ,[ADDR3]    "+
	"	     , (select LKP_SRNO from lookup where LKP_DISC=[CITY] and LKP_CODE='CITY')  as city      ,ISNULL([PINCODE],0) as PINCD "+
	"	  ,[PHONE1]      ,[PHONE2]      ,[FAX]      ,[START_DT]  ,EDATE    ,[I_SHIFT_F]     "+
	"	   ,[I_SHIFT_T]      ,[II_SHIFT_F] "+
	"	  ,[II_SHIFT_T]      ,[I_CASH_F]      ,[I_CASH_T]      ,[II_CASH_F]      ,[II_CASH_T] "+  
	"	      ,(SUBSTRING(ISNULL(WOFFDAY1,''), 1, 3)+''+SUBSTRING(ISNULL(WOFFDAY2,''), 1, 3) )AS WEEK_OFF,   "+
	"	             'Open',1 FROM [NAMCO_DBF].[dbo].[BRANCH1] order by BR_CD  END ");
  System.out.println("------------------"+(count++));
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} */
   
 /*  
// for VDAPARAMETER CONVERSION


try {
	Connection con=ConnectionManager.getConnection();
	Statement stmt=con.createStatement();
	Statement stmt1=con.createStatement();
	Statement stmt2=con.createStatement();
	Statement stmt3=con.createStatement();
	ResultSet rs=null;
	ResultSet rs2=null;
	ResultSet rs1=null;
	String table =" [NAMCO_DBF].[dbo].[VDAPARA] ";
	System.out.println("select * from "+table+" order by YY,MM");
rs=stmt.executeQuery("select * from "+table+" order by YY,MM");
while (rs.next()){
String yy=""+rs.getInt("YY");
String mm=""+rs.getInt("MM");
String index=rs.getString("INDEX");
String month="";
System.out.println(yy+"----------"+mm);
if(mm.equalsIgnoreCase("1")){month="Jan";}
else if(mm.equalsIgnoreCase("2")){month="Feb";}
else if(mm.equalsIgnoreCase("3")){month="Mar";}
else if(mm.equalsIgnoreCase("4")){month="Apr";}
else if(mm.equalsIgnoreCase("5")){month="May";}
else if(mm.equalsIgnoreCase("6")){month="Jun";}
else if(mm.equalsIgnoreCase("7")){month="Jul";}
else if(mm.equalsIgnoreCase("8")){month="Aug";}
else if(mm.equalsIgnoreCase("9")){month="Sep";}
else if(mm.equalsIgnoreCase("10")){month="Oct";}
else if(mm.equalsIgnoreCase("11")){month="Nov";}
else if(mm.equalsIgnoreCase("12")){month="Dec";}


String dt="01-"+month+"-"+yy;

stmt1.execute("insert into [DTS3_DEMO_DB_fOR_JAVA].[dbo].[VDAMAST]"+  
  "([VDA_APPLICABLE_DATE],[MBAN_DIVIDEND],[MBAN_DIVISOR],[VDA_INDEX],[VDA_DATE],[MONTH],[INDEX_FOR],[STATUS])"+
  "values('"+dt+"',1333,3,4.93,'"+dt+"','"+dt+"',"+index+",'A')");

}

}
	catch(Exception e)
	{
		e.printStackTrace();
		ErrorLog el=new ErrorLog();
		el.errorLog("BRANCH1 to PROJECT_SITES trial.jsp",e.toString());
	}

 */



 
 

  

//FOR SAL201504 table to paytran_stage
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
	String table ="SAL1609";
	String table1="PAYTRAN";
	String dt="30-sep-2016";
	String mnth="sep-2016";
	String empcode="";
	String empno="";
	int trncd;
	int count=1;
	String col="";
rs1=stmt.executeQuery("select empcode,empno from empmast where empcode in (select distinct emp_code from  "+table+" )"+ 
"and status='A' order by empno ");	

while(rs1.next())
{
	empcode=rs1.getString("empcode");
	empno=rs1.getString("empno");
	emppp=empno;
 rs=stmt1.executeQuery("select * from [NAMCO_DBF].[dbo].[cds] order by trncd");
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
			stmt3.execute(" if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
					" update PAYTRAN set INP_AMT="+rs2.getFloat(1)+""+
					" where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'','','"+dt+"','P')  ");
			System.out.println(" if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
					" update PAYTRAN set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
					" where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'','','"+dt+"','P')  ");
			stmt3.execute(" if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD=199) "+
					" update PAYTRAN set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
					" where EMPNO="+empno+" and TRNCD=199  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+",199,0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
			stmt3.execute(" if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD=198) "+
					" update PAYTRAN set CAL_AMT="+rs2.getFloat(1)+
					" where EMPNO="+empno+" and TRNCD=198  else "+ 
					" insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
	"values('"+dt+"',"+empno+",198,0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
																	
		}else
		{
			
		if(trncd>100 && trncd<200)
		{
		stmt3.execute("if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
				"update PAYTRAN set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
				"where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
				"insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
		}
		else
		{
			{
				stmt3.execute("if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNCD="+trncd+" ) "+
						"update PAYTRAN set CAL_AMT="+rs2.getFloat(1)+",NET_AMT="+rs2.getFloat(1)+" "+
						"where EMPNO="+empno+" and TRNCD="+trncd+"  else "+ 
						"insert into "+table1+" (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS)"+
		"values('"+dt+"',"+empno+","+trncd+",0,"+rs2.getFloat(1)+","+rs2.getFloat(1)+",0.0,0.0,"+rs2.getFloat(1)+",'*','','"+dt+"','P')  ");
				}
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

  
   
     

   
  
    
  //for converting leave table into LEAVETRAN
   /*
   try {
	  
	 /*
	    LEAVE TYPE 
	  3=CL
	  4=COFF_L
	  5=ENCSH_L
	  6=LATE_L
	  7=LWP
	  8=MAT_L
	 1= PL
	 2=SL
	  9=TRF_L  
	  
	
	 int count=1;
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		Statement st3=con.createStatement();
		ResultSet rs= st1.executeQuery("Select * from LEV_DTL_29_sep where"+
				" 	lev_type='SL'   AND APP_DATE "+
				"between '26-aug-2016' and '26-sep-2016' and"+
				" from_dt<='30-sep-2016' order by EMP_CODE");
			while(rs.next())
			{
				int LEAVECD =2;
				  int LEAVECD =rs.getString("LEV_TYPE").equalsIgnoreCase("PL")?1:rs.getString("LEV_TYPE").equalsIgnoreCase("SL")?2:rs.getString("LEV_TYPE").equalsIgnoreCase("CL")?3:
				rs.getString("LEV_TYPE").equalsIgnoreCase("COFF_L")?4:rs.getString("LEV_TYPE").equalsIgnoreCase("ENCSH_L")?5:rs.getString("LEV_TYPE").equalsIgnoreCase("LATE_L")?6:
					rs.getString("LEV_TYPE").equalsIgnoreCase("LWP")?7:rs.getString("LEV_TYPE").equalsIgnoreCase("MAT_L")?8:rs.getString("LEV_TYPE").equalsIgnoreCase("TRF_L")?9:10	;// ,[EMPNO],[TRNDATE],[TRNTYPE],[APPLNO],[LREASON],[APPLDT],[FRMDT],[TODT],[SANCAUTH],[STATUS],[DAYS]	 
			
				String empno="";
		ResultSet r=st3.executeQuery("select empno from empmast where empcode like '"+rs.getString("EMP_CODE")+"'");			
		while(r.next())
		{
			empno=r.getString("EMPNO");
		}

		st2.execute(" insert into  leavetran " +
		"([LEAVECD],[EMPNO],[TRNDATE],[TRNTYPE],[APPLNO],[LREASON],[APPLDT],[FRMDT],[TODT],[STATUS],[DAYS])"+
		" VALUES("+LEAVECD+","+empno+",'"+rs.getString("APP_DATE")+"','D',(select isnull((select max(applno)+1 from leavetran),1) )"+
		" ,'"+rs.getString("REASON")+"', "+
		//" ,'PL CONVERTED TO LWP', "+
		//" ,'LATE MARK CONVERTED TO PL', "+
		"'"+rs.getString("SAN_DATE")+"','"+rs.getString("FROM_DT")+"','"+rs.getString("TO_DT")+"','"+(rs.getString("SANCTION").equalsIgnoreCase("Y")?"SANCTION":"1")+"',"+rs.getString("LDAYS")+")");		
			
			System.out.println("count :  "+count++);
			
			}	
				
  }
  catch(Exception e)
  {
	  e.printStackTrace();
	  ErrorLog el=new ErrorLog();
		el.errorLog("LEAVE INTO LEAVETRAN trial.jsp",e.toString());
  } 
   
   

 /*  
  
  // for Epmloyers PF CODES besure the Base TRNCD SHOULD GET EVALUATED FIRST
  
  try {
	  
	  String BgnDate="1-May-2015";
	  String table="paytran_stage";
	  String status="F";
	  ArrayList<String> cdss= new  ArrayList<String>(Arrays.asList("136","137","233","234","235"));
	  //136(PF CAL),231,232,233,234,235,137(EPS CAL),237(MLWF),143(BONUS CAL AMT)
	  
	 int count=1;
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		Statement st3=con.createStatement();
		float amt=0.0f;
		float wrk_days=Calculate.getDays(BgnDate);
		ResultSet rs1=st1.executeQuery("select distinct empno from "+table+" where trndt between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"' order by empno");
		 while(rs1.next())
		 {
			  ResultSet rs2=st2.executeQuery("select isnull((select inp_amt from "+table+" where trndt between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"' and empno="+rs1.getString(1)+" and trncd=301),0)");
			 while(rs2.next())
			 {
				 wrk_days= wrk_days - rs2.getFloat(1);
				 
			 }
			
			for (String trncd:cdss)
			{
			float onAmt1 = VDA_Calculation.onAmount(Integer.parseInt(trncd),Integer.parseInt(rs1.getString(1)),ReportDAO.EOM(BgnDate),0,wrk_days, con,table);
			float	Wrk_Amt = Math.round(VDA_Calculation.checkSlab(Integer.parseInt(trncd),ReportDAO.EOM(BgnDate), onAmt1,0, Integer.parseInt(rs1.getString(1)), con));
				
			
			st3.execute("IF EXISTS( select * from "+table+" where  EMPNO="+rs1.getString(1)+" and  TRNCD="+trncd+"  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"' )"+
						" update  "+table+" set cal_amt="+Wrk_Amt+",net_amt="+Wrk_Amt+"  where  EMPNO="+rs1.getString(1)+" and  TRNCD="+trncd+"  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'"+			
						" ELSE "+			
						" insert into "+table+" ([TRNDT],[EMPNO],[TRNCD],[SRNO],[INP_AMT],[CAL_AMT],[ADJ_AMT],[ARR_AMT],[NET_AMT],[CF_SW],[UPDDT],[STATUS])"+
			                  "  values('"+ReportDAO.EOM(BgnDate)+"',"+rs1.getString(1)+","+trncd+",0,"+Wrk_Amt+","+Wrk_Amt+",0,0,"+Wrk_Amt+",'','"+ReportDAO.EOM(BgnDate)+"','"+status+"')");	
			}	
			 
			
			Statement epf_cal=con.createStatement();
			String epf_cal_query="update "+table+" 	set " +
								"CAL_AMT=((select p1.cal_amt from "+table+" p1 where p1.EMPNO=st.EMPNO and  p1.TRNCD=201 and  p1.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')" +
								" - ( select p2.cal_amt from "+table+" p2 where p2.EMPNO=st.EMPNO and p2.TRNCD=232  and  p2.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'))," +
								"NET_AMT=((select p1.cal_amt from "+table+" p1 where p1.EMPNO=st.EMPNO and  p1.TRNCD=201 and  p1.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')" +
								" - (select p2.cal_amt from "+table+" p2 where p2.EMPNO=st.EMPNO and p2.TRNCD=232  and  p2.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'))   	" +
										"from "+table+" st	  where  TRNCD=231  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'	" +
										"and EMPNO in(select EMPNO from "+table+" 	where  EMPNO="+rs1.getString(1)+" and  TRNCD=201  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')";
			
			epf_cal.executeUpdate(epf_cal_query);
			
		 }
  }
  catch(Exception e)
  {
	  e.printStackTrace();
	  ErrorLog el=new ErrorLog();
		el.errorLog("Epmloyers PF CODES trial.jsp",e.toString());
  }
  
  
  
  
  
  
   */
  
  
  
  
  
  
   
  
   // for leaveBalance table from salary table to leavebal
  
  
/*   try {
		 int count=1;
			Connection con=ConnectionManager.getConnection();
			Statement st1=con.createStatement();
			Statement st2=con.createStatement();
			Statement st3=con.createStatement();
			
			
			String table =" [NAMCO_DBF].[dbo].[SAL1504] ";
			String dt="30-Apr-2015";
			String mnth="Apr-2015";
			String empcode="";
			String empno="";
			int trncd;
			String col="";
			
			
			
			ResultSet rs= st1.executeQuery("select * from "+table+" order by emp_code ");
				while(rs.next())
				{
					
				ResultSet rs1=st3.executeQuery("select empcode,empno from empmast where empcode LIKE '"+rs.getString("EMP_CODE")+"'");	

					while(rs1.next())
					{
						empcode=rs1.getString("empcode");
						empno=rs1.getString("empno");
						float cl=rs.getFloat("BAL_CL");
						float pl=rs.getFloat("BAL_PL");
						float ml=rs.getFloat("BAL_ML");
						
						float op_cl=rs.getFloat("OP_CL");
						float op_pl=rs.getFloat("OP_PL");
						float op_ml=rs.getFloat("OP_ML");
						

			st2.execute(" insert into  LEAVEBAL " +
			"([BALDT],[EMPNO],[LEAVECD],[BAL],[TOTCR],[TOTDR])"+
			" VALUES('"+dt+"',"+empno+",1,"+op_pl+","+op_pl+",0)");	
			System.out.println(count++);
			st2.execute(" insert into  LEAVEBAL " +
					"([BALDT],[EMPNO],[LEAVECD],[BAL],[TOTCR],[TOTDR])"+
					" VALUES('"+dt+"',"+empno+",2,"+op_ml+","+op_ml+",0)");
			System.out.println(count++);
			st2.execute(" insert into  LEAVEBAL " +
					"([BALDT],[EMPNO],[LEAVECD],[BAL],[TOTCR],[TOTDR])"+
					" VALUES('"+dt+"',"+empno+",3,"+op_cl+","+op_cl+",0)");	
			
			System.out.println(count++);
			}	
				}
					
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  ErrorLog el=new ErrorLog();
			el.errorLog("LEAVEBAL trial.jsp",e.toString());
	  }
  
  */
  
/*   
  
    // for LIC DEDUCTION MASTER to DEDMAST 
   
   
   try {
 		 int count=1;
 			Connection con=ConnectionManager.getConnection();
 			Statement st1=con.createStatement();
 			Statement st2=con.createStatement();
 			Statement st3=con.createStatement();
 			
 			
 			String table =" [NAMCO_DBF].[dbo].[SAL1504] ";
 			String dt="30-Apr-2015";
 			String mnth="Apr-2015";
 			String empcode="";
 			String empno="";
 			int trncd;
 			String col="";
 		/* 	
DEDMAST=========== 			
[EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],
[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT] 			
================================== 
		 
		ResultSet rs= st1.executeQuery("select * from [NAMCO_DBF].[dbo].[LIC_MST] order by EDATE ");
 			while(rs.next())
 				{
 				ResultSet rs1=st3.executeQuery("select empcode,empno from empmast where empcode LIKE '"+rs.getString("EMP_CD")+"'");	
				while(rs1.next())
 					{
 					empno=rs1.getString("empno");
 					}
 					st3.execute("insert into DEDMAST  ([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],"+
 							"[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT],[TYPE],[DESIG],[PRJ_SRNO])"+
 							" VALUES ("+empno+
 									",205,"+rs.getString("PSR_NO")+","+
 									""+rs.getString("POL_AMT")+",0,"+rs.getString("POL_NO")+","+
 									"(select isnull((select max(BODSANCNO) from dedmast),0)+1 ),"+
									"'"+rs.getString("EDATE")+"',"+
									""+rs.getString("POL_AMT")+","+
									"0,"+
									"'"+rs.getString("EDATE")+"',"+
									"'"+rs.getString("EDATE")+"',"+
									"'Y','Y',0,0,"+
									""+rs.getString("TYPE")+","+
									""+rs.getString("DESG_CD")+","+
									""+rs.getString("BR_CD")+" )");	
    
 //EMP_CD	EMP_NAME	TYPE	DESG_CD	BR_CD	PSR_NO	POL_NO	POL_AMT	POL_STAT	USER	EDATE
 
 }
   }
   catch(Exception e)
   {
	   e.printStackTrace();
	   ErrorLog el=new ErrorLog();
		el.errorLog("LIC DEDMAST trial.jsp",e.toString());
   } 
   
    */
   
/* // for  LOAN DEDUCTION MASTER to DEDMAST 
   
   
   try {
 		 int count=1;
 			Connection con=ConnectionManager.getConnection();
 			Statement st1=con.createStatement();
 			Statement st2=con.createStatement();
 			Statement st3=con.createStatement();
 			
 			
 			String table =" [NAMCO_DBF].[dbo].[SAL1504] ";
 			String dt="30-Apr-2015";
 			String mnth="Apr-2015";
 			String empcode="";
 			String empno="";
 			String trncd="";
 			String col="";
 		/* 	
DEDMAST=========== 			
[EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],
[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT] 			
================================== 
first ask what to do for  br_cd=0 in  [NAMCO_DBF].[dbo].[LOAN_MST]
second add trncd column with respect to loan types in [NAMCO_DBF].[dbo].[LOAN_MST]

----------------------------TRNCD
LNHYP-S :Hypothecation Loan 208  update NAME to HYPOTHECATION LOAN
ADVMED-S:medical			209 update name to MEDICAL loan INTEREST
LASOBL  :bank interest      210 update NAME to OTHER BANK LOAN interest
LNOTH-S :other              226
CCINST-S:CASH CREDIT		227 update NAME to CASH CREDIT interest
REHSG-S :housing			244
ADVFST-S:festival			246
ADVGS-S: GOld				253
ADVMRG-S:Mortgage			257

update [NAMCO_PAYROLL].[dbo].cdmast set DISC='HYPOTHECATION LOAN' , sdesc='HYP Loan' where TRNCD =208
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='MEDICAL INST' , sdesc='MEDICAL INST' where TRNCD =209
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='OTHER BANK INST' , sdesc='OTH BNK INST' where TRNCD =210 
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='CASH CREDIT INST' , sdesc='CC INST' where TRNCD =227 

*/
		/* ResultSet rs= st1.executeQuery("select * from [NAMCO_DBF].[dbo].[LOAN_MST] order by EDATE ");
 				while(rs.next())
 				{
 			
 					     if(rs.getInt("trncd")==1){trncd="208";}
 					else if(rs.getInt("trncd")==2){trncd="209";}
 					else if(rs.getInt("trncd")==3){trncd="210";} 
 					else if(rs.getInt("trncd")==4){trncd="226";}
					else if(rs.getInt("trncd")==5){trncd="227";}
					else if(rs.getInt("trncd")==6){trncd="244";}					
					else if(rs.getInt("trncd")==7){trncd="246";}
					else if(rs.getInt("trncd")==8){trncd="253";}
					else if(rs.getInt("trncd")==9){trncd="257";}
					
 				ResultSet rs1=st3.executeQuery("select empcode,empno from empmast where empcode LIKE '"+rs.getString("EMP_CD")+"'");	
				while(rs1.next())
 					{
					empno=rs1.getString("empno");
 					}
				
				if(empno.equalsIgnoreCase(""))
				{}
				else
				{
				String edate= rs.getString("EDATE")==null?"31-Dec-2099":rs.getString("EDATE");
				st2.execute("insert into DEDMAST  ([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],"+
 							"[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT],[TYPE],[DESIG],[PRJ_SRNO])"+
 							" VALUES ("+empno+
 									","+trncd+",((select isnull((select max(SRNO) from dedmast),0)+1 )),"+
 									""+rs.getString("LINST")+",0,"+rs.getString("L_ACCNO")+","+
 									"(select isnull((select max(SRNO) from dedmast),0)+1),"+
									"'"+edate+"',"+
									""+rs.getString("LINST")+","+
									"0,"+
									"'"+edate+"',"+
									"'"+edate+"',"+
									"'Y','Y',0,0,"+
									""+trncd+","+
									""+rs.getString("DESG_CD")+","+
									""+rs.getString("BR_CD")+" )");	
   *//*  
EMP_CD	EMP_NAME	DESG_CD	BR_CD	LTYPE	L_ACCNO	LINST	LSTAT	AUTH_FLG	USER	EDATE	trncd
 
  
  EMP_CD	EMP_NAME	TYPE	DESG_CD	BR_CD	PSR_NO	POL_NO	POL_AMT	POL_STAT	USER	EDATE
 
 */ 
 /*  
 System.out.println (empno+"......completed ");
				}	
 				}
 				System.out.println ("ALL EMP......completed ");
   }
   catch(Exception e)
   {
	   e.printStackTrace();
	   ErrorLog el=new ErrorLog();
		el.errorLog("LOAN DEDMAST trial.jsp",e.toString());
   }  
   
   
    
  */  
   
   
   /* 
   // Insert into reliving info

	Connection conn;
	conn= ConnectionManager.getConnection();
	conn.setAutoCommit(false);
	
	boolean flag= false; 
	try
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		Statement st = conn.createStatement();
		Statement st3 = conn.createStatement();
		
		
		ResultSet  rs1=st3.executeQuery("select * from [NAMCO_DBF].[dbo].[EMP_MAST2] where REGN_DT is not null or RETI_DATE is not null");
		
		while(rs1.next())
		{
			String empno="";
			String rs_date="";
			String rt_date="";
			Statement st1 = conn.createStatement();
			ResultSet r=st1.executeQuery("select empno from namco_payroll.dbo.empmast where empcode='"+rs1.getString("EMP_CODE")+"'");
			if(r.next())
			{
			empno=r.getString(1);
			}
			
			String dt=rs1.getDate("REGN_DT")==null?sdf.format(rs1.getDate("RETI_DATE")):sdf.format(rs1.getDate("REGN_DT"));
		
		Statement st2 = conn.createStatement();
		st2.execute("insert into namco_payroll.dbo.RELIEVEINFO(EMPNO,LEFT_DATE,RESGN_DATE,RESGN_ACCTD_DATE,REASON,NTC_PERIOD,TERMINATE,DEATH) "+ 
		" values("+empno+",'"+dt+"','"+dt+"','"+dt+"','','NA','Yes','')");
		Statement stupdate = conn.createStatement();
		stupdate.executeUpdate("update namco_payroll.dbo.EMPMAST SET DOL = '"+dt+"',STATUS = 'N', UMODDATE ='"+dt+"' where EMPNO ="+empno+"");
		
		System.out.println(""+empno);
		
		
		}
	conn.commit();
		
	}	
	catch(Exception e)
	{
		 e.printStackTrace();
		 ErrorLog el=new ErrorLog();
		 el.errorLog("Reliving Info trial.jsp",e.toString());		
	}
   
   
   
   
    */
   
   
  
%>








</body>
</html>