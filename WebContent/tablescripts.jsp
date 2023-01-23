<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>





<!-- 
--------------------------------
insert into cdmast mvda
(197	MVDA	N	N	N	---	0	0	0	N	-	-	0	-	0	-	0	-	MVDA	-	-	-                   	-	-	-         	-	1	NULL	NULL	999)
--------------------------------
insert into slab 
EFFDATE	EMP_CAT	TRNCD	SRNO	FRMAMT		TOAMT	PER	MINAMT	MAXAMT	FIXAMT	ON_AMT_CD
2099-12-31	0	197	1	0.00	200.00		200.00	0.00	0.00	0.00	0
2099-12-31	0	197	3	426.00	600.00		15.00	425.00	500.00	500.00	0
2099-12-31	0	197	5	751.00	900.00		5.00	750.00	800.00	800.00	0
2099-12-31	0	197	7	1101.00	9999999.00	2.00	900.00	1100.00	1100.00	0
2099-12-31	0	197	2	201.00	425.00		20.00	425.00	500.00	500.00	0
2099-12-31	0	197	4	601.00	750.00		10.00	425.00	500.00	500.00	0
2099-12-31	0	197	6	901.00	1100.00		2.00	0.00	0.00	200.00	0


--------------------------------
insert ACTUAL_TOTAL_AMT_PAID column in dedmast
also insert into history and tr
--------------------------------

--------------------------------

--------------------------------
1>***********ADD VDA POSTING in Menues
--------------------------------
2>*********add field in VDAMAST 
as ..
FINALIZE_FLAG int default null 
--------------------------------
3>*******
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

 */
-----------------------------------------------------------------------------------------
4>***********USE [NAMCO_PAYROLL]
GO
/****** Object:  Table [dbo].[GRATUITYDETAILS]    Script Date: 12/12/2015 13:31:41 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GRATUITYDETAILS](
	[EMPNO] [int] NULL,
	[TRNDT] [date] NULL,
	[UPDDT] [date] NULL,
	[AMOUNT] [numeric](18, 2) NULL,
	[UPDATE_BY] [int] NULL
) ON [PRIMARY]

GO
-----------------------------------------------------------------------------------------
5>***********CREATE TRNCD in CDMAST 144 gratuity

144	GRATUITY	Y	N	Y	---	0	0	0	N	-	-	0	-	0	-	0	-	GRATUITY	-	-	-                   	-	-	-         	-	1	NULL	NULL	199
-----------------------------------------------------------------------------------------
6>**********insert on amt 144
EMP_CAT	TRNCD	SRNO	ONAMTCD	AMT_TYPE
0	144	1	101	C
0	144	2	102	C

-----------------------------------------------------------------------------------------
7>**********Insert SLAB 144
EFFDATE		EMP_CAT	TRNCD	SRNO	FRMAMT	TOAMT		PER		MINAMT	MAXAMT		FIXAMT	ON_AMT_CD
2099-12-31	 0	     144	1	    0.00	9999999.00	100.00	0.00	300000.00	15.00	0

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------







8>*******************run the code of reliving info on trial.jsp









-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
9>************update date of joining

UPDATE EMST
		SET  EMST.DOJ=E1.JOIN_DATE 
  		FROM [NAMCO_PAYROLL].[DBO].[EMPMAST] EMST
  		LEFT OUTER JOIN [NAMCO_DBF].[DBO].[EMP_MAST2] E1 ON E1.EMP_CODE=EMST.EMPCODE
 		WHERE E1.EMP_CODE= EMST.EMPCODE
 		
 		
select  e.DOJ,e1.JOIN_DATE from [NAMCO_DBF].[dbo].[EMP_MAST2] e1,[NAMCO_PAYROLL].[dbo].[empmast] e
 where e.empcode= e1.emp_code 	
-----------------------------------------------------------------------------------------

10>*************update date of birth

UPDATE EMST
		SET  EMST.DOB=E1.BIRTH_DT 
  		FROM [NAMCO_PAYROLL].[DBO].[EMPMAST] EMST
  		LEFT OUTER JOIN [NAMCO_DBF].[DBO].[PERSONL1] E1 ON E1.EMP_CODE=EMST.EMPCODE
 		WHERE E1.EMP_CODE= EMST.EMPCODE

select  e.empcode,e1.emp_code,e.DOB,e1.birth_dt 
from [NAMCO_DBF].[dbo].[PERSONL1] e1,[NAMCO_PAYROLL].[dbo].[empmast] e
 where e.empcode= e1.emp_code

-----------------------------------------------------------------------------------------
11>*************EMPTRAN effective date 

UPDATE empt
		SET EMPT.Effdate=EMST.DOJ ,
			EMPT.order_dt=EMST.DOJ 
				
  		FROM [NAMCO_PAYROLL].[DBO].[EMPMAST] EMST
  		LEFT OUTER JOIN [NAMCO_DBF].[DBO].[PERSONL1] E1 ON E1.EMP_CODE=EMST.EMPCODE
  		inner join  	[NAMCO_PAYROLL].[dbo].[EMPTRAN] empt  on 	empt.EMPNO=EMST.EMPNO
 		WHERE E1.EMP_CODE= EMST.EMPCODE and empt.EMPNO=EMST.EMPNO
  		

select  e.empcode,e1.emp_code,e.DOJ,e.EMPNO, empt.EMPNO,empt.ORDER_DT,empt.EFFDATE
	from [NAMCO_DBF].[dbo].[PERSONL1] e1 left outer join [NAMCO_PAYROLL].[dbo].[empmast] e
	on 	E1.EMP_CODE=e.EMPCODE
	inner join  	[NAMCO_PAYROLL].[dbo].[EMPTRAN] empt  on 	empt.EMPNO=e.EMPNO
 	WHERE E1.EMP_CODE= e.EMPCODE and empt.EMPNO=e.EMPNO



-----------------------------------------------------------------------------------------
12>******************LEAVE ENCASHMENT


USE [NAMCO_PAYROLL]
GO

/****** Object:  Table [dbo].[LEAVE_ENCASHMENT]    Script Date: 12/12/2015 18:42:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[LEAVE_ENCASHMENT](
	[EMPNO] [int] NULL,
	[SITE_ID] [int] NULL,
	[LEAVE_BAL] [float] NULL,
	[MAX_LIMIT] [float] NULL,
	[ENCASH_APPLICABLE] [float] NULL,
	[MONTHLY_GROSS] [float] NULL,
	[ESIC_AMT] [float] NULL,
	[ENCASHMENT_AMT] [float] NULL,
	[CREATED_BY] [int] NULL,
	[CREATED_DATE] [date] NULL,
	[STATUS] [varchar](50) NULL,
	[LEAVE_ENCASHMENT_SANCTION] [float] NULL,
	[LEAVE_ENCASHMENT_DATE] [date] NULL,
	[FROM_DATE] [date] NULL,
	[TO_DATE] [date] NULL,
	[SRNO] [int] IDENTITY(1,1) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO




-----------------------------------------------------------------------------------------



13**************-Rerun the leave COnversion functions




-----------------------------------------------------------------------------------------
14>************Required for PT statement
Update the site_State in Project_Sites table

update Project_Sites set SITE_STATE=21 where SITE_ID not in (12,40) --maharashtra
update Project_Sites set SITE_STATE=12 where SITE_ID  in (43) --Surat
update Project_Sites set SITE_STATE=2 where SITE_ID  in (40)--Hydrabad
-----------------------------------------------------------------------------------------

TEST HANDLER


-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------
 -->












</body>
</html>