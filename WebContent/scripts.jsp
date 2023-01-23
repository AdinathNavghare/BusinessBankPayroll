<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Dbfimport_Data</h1>

USE [HCPTesting]
GO

/****** Object:  Table [dbo].[Dbfimport_Data]    Script Date: 09/29/2015 18:45:30 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Dbfimport_Data](
	[EMP_CODE] [varchar](max) NULL,
	[BR_CD] [varchar](10) NULL,
	[DESG_CD] [varchar](10) NULL,
	[L1] [varchar](10) NULL,
	[L2] [varchar](10) NULL,
	[L3] [varchar](10) NULL,
	[L4] [varchar](10) NULL,
	[L5] [varchar](10) NULL,
	[L6] [varchar](10) NULL,
	[L7] [varchar](10) NULL,
	[L8] [varchar](10) NULL,
	[L9] [varchar](10) NULL,
	[L10] [varchar](10) NULL,
	[L11] [varchar](10) NULL,
	[L12] [varchar](10) NULL,
	[L13] [varchar](10) NULL,
	[L14] [varchar](10) NULL,
	[L15] [varchar](10) NULL,
	[L16] [varchar](10) NULL,
	[L17] [varchar](10) NULL,
	[L18] [varchar](10) NULL,
	[L19] [varchar](10) NULL,
	[L20] [varchar](10) NULL,
	[L21] [varchar](10) NULL,
	[L22] [varchar](10) NULL,
	[L23] [varchar](10) NULL,
	[L24] [varchar](10) NULL,
	[L25] [varchar](10) NULL,
	[L26] [varchar](10) NULL,
	[L27] [varchar](10) NULL,
	[L28] [varchar](10) NULL,
	[L29] [varchar](10) NULL,
	[L30] [varchar](10) NULL,
	[L31] [varchar](10) NULL,
	[REMARK1] [varchar](50) NULL,
	[REMARK2] [varchar](50) NULL,
	[REMARK3] [varchar](50) NULL,
	[MONTH_DAYS] [varchar](10) NULL,
	[LEV_DAYS] [varchar](10) NULL,
	[LWP_DAYS] [varchar](10) NULL,
	[WOFF_DAYS] [varchar](10) NULL,
	[HOLIDAYS] [varchar](10) NULL,
	[PAY_DAYS] [varchar](10) NULL,
	[P_FLG] [varchar](10) NULL,
	[YEAR] [varchar](10) NULL,
	[MONTH] [varchar](10) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


<h2>Dbfstorefile</h2>
USE [HCPTesting]
GO

/****** Object:  Table [dbo].[Dbfstorefile]    Script Date: 09/29/2015 18:46:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Dbfstorefile](
	[EMPNO] [numeric](5, 0) NULL,
	[FILENAME] [nvarchar](100) NULL,
	[PHOTO] [varbinary](max) NULL,
	[SIGN] [varbinary](max) NULL,
	[ATTACHPATH] [nvarchar](500) NULL,
	[SRNO] [numeric](18, 0) NULL,
	[doc_type] [nvarchar](50) NULL,
	[doc_name] [nvarchar](50) NULL,
	[doc_desciption] [nvarchar](500) NULL,
	[CREATED_BY] [numeric](5, 0) NULL,
	[CREATED_DATE] [date] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO










</body>
</html>