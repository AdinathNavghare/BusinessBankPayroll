package payroll.Core;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.EmployeeHandler;
import payroll.DAO.LeaveMasterHandler;
import payroll.Model.EmpOffBean;
import payroll.Model.EmployeeBean;
import payroll.Model.LeaveMassBean;

public class Utility {
    //-------- Method to format the date in DD-MON-YYYY format -----------
    public static String dateFormat(Date date) {
        if (date == null) {
            return "---";
        }
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        result = format.format(date);
        return result;
    }

    public static void makeFile(String fileName) {
        System.out.println("Creating File");
        File file = new File(fileName);
        try {
            FileWriter FW = new FileWriter(file);
            FW.close();
            System.out.println("File Created");
        } catch (Exception e) {
            //UsrHandler.senderrormail(e,"utility.makeFile");
            System.out.println("File Not Created");
        }
    }

    public void autoLeaveCredit(String emp, int srno, String month) {
        try {
            Connection con = ConnectionManager.getConnection();
            LeaveMasterHandler LMH = new LeaveMasterHandler();
            ArrayList < LeaveMassBean > list = LMH.getleavetypeList("SELECT * FROM LEAVEMASS WHERE SRNO =" + srno);

            String[] empl = emp.split(",");
            int[] empList = new int[empl.length];
            for (int i = 0; i < empl.length; i++) {
                empList[i] = Integer.parseInt(empl[i]);
            }

            if (list.size() > 0) {
                LeaveMassBean lbean = list.get(0);
                int leaveNum = lbean.getCRLIM();

                int factor = 0;
                if (leaveNum > 0) {
                    if (lbean.getFREQUENCY().equalsIgnoreCase("M")) //Monthly
                    {
                        if (leaveNum >= 12) {
                            factor = 12;
                        } else {
                            factor = leaveNum;
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("Q")) //Quarterly
                    {
                        if (leaveNum >= 3) {
                            factor = 3;
                        } else {
                            factor = leaveNum;
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("H")) //Half Yearly
                    {
                        if (leaveNum >= 2) {
                            factor = 2;
                        } else {
                            factor = leaveNum;
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("Y")) //Yearly
                    {
                        factor = 1;
                    }

                    // calling credit method to credit the leaves for particular year don't delete comment and month is current selection 
                    creditMethod(empList, factor, lbean, leaveNum, month, con);
                }
            }
            con.close();
        } catch (Exception e) {
            //UsrHandler.senderrormail(e,"utility.addleavecredit");
            e.printStackTrace();
        }
    }

    public void creditMethod(int[] empList, int factor, LeaveMassBean lbean, int days, String month, Connection con) {
        int leaves = 0;
        int restLeaves = 0;
        leaves = days / factor;
        restLeaves = days % factor;
        int[] leaveVals = new int[factor];
        for (int i = 0; i < factor; i++) {
            leaveVals[i] = leaves;
        }
        if (restLeaves > 0) {
            for (int i = 0; i < restLeaves; i++) {
                leaveVals[i]++;
            }
        }
        int incr = 0;
        if (lbean.getFREQUENCY().equalsIgnoreCase("M")) {
            incr = 1;
        }
        if (lbean.getFREQUENCY().equalsIgnoreCase("Q")) {
            incr = 3;
        }
        if (lbean.getFREQUENCY().equalsIgnoreCase("H")) {
            incr = 6;
        }
        if (lbean.getFREQUENCY().equalsIgnoreCase("Y")) {
            incr = 12;
        }

        String[] dates = getDates(factor, incr, month, con);

        try {
            Statement st = con.createStatement();
            Statement st01 = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet rs = null;
            ResultSet rs01 = null;
            ResultSet rs1 = null;
            ResultSet rsDoj = null;
            Statement stDOJ = con.createStatement();
            int finalDays = 0;

            // for loop according to no. of employeeessss
            for (int i = 0; i < empList.length; i++) {
                for (int j = 0; j < factor; j++) {

                    rs1 = st1.executeQuery("SELECT * FROM LEAVETRAN WHERE LEAVECD=" + lbean.getLEAVECD() + " AND EMPNO=" + empList[i] + " AND TRNTYPE='C' AND LEAVEPURP=5 AND APPLDT='" + dates[j] + "' AND FRMDT='" + dates[j] + "' AND STATUS=1");
                    if (rs1.next()) {
                        System.out.println("Record Already present for Employee " + empList[i]);
                    } else {



                        Statement st2 = con.createStatement();
                        ResultSet rs2 = null;
                        rs2 = st2.executeQuery("select isnull((SELECT MAX(APPLNO)+1 FROM LEAVETRAN),1)");
                        int applno = 0;
                        if (rs2.next()) {
                            applno = rs2.getInt(1);
                        }

                        EmployeeHandler eh = new EmployeeHandler();
                        EmployeeBean ebean = eh.getEmployeeInformation(Integer.toString(empList[i]));
                        String dt = ebean.getDOJ();

                        if (lbean.getFREQUENCY().equalsIgnoreCase("Y")) {
                            float months = 0;
                            float month_diff = 0;

                            ResultSet rs11 = st.executeQuery("SELECT DATEDIFF(MONTH, '" + lbean.getFBEGINDATE() + "', '" + dt + "') as month");

                            if (rs11.next()) {
                                months = rs11.getInt("month");
                                //System.out.println("month in DOJ check :"+month);
                            }
                            rs11.close();
                            if (months > 0) {

                                rs11 = st.executeQuery("SELECT DATEDIFF(MONTH, '" + dt + "', DATEADD(yy, DATEDIFF(yy,0,'" + lbean.getFBEGINDATE() + "') + 1, 0)) as month_diff");
                                //System.out.println("...........SELECT DATEDIFF(MONTH, '"+dt+"', DATEADD(yy, DATEDIFF(yy,0,'"+lbean.getFBEGINDATE()+"') + 1, 0)) as month_diff");
                                if (rs11.next()) {
                                    month_diff = rs11.getInt("month_diff");
                                    //System.out.println("month in DIFFERANCE check :"+month_diff);
                                }
                                rs11.close();

                                if (month_diff > 0) {
                                    //System.out.println(month_diff+"DAYS---before-----"+days);
                                    days = (int)(days * month_diff / 12);

                                    //System.out.println("DAYS---after-----"+days);
                                }


                            }


                        }
                        String  date_lastyear="";
                        String  LEAVE_END_DATE="";
                        String  query1 ="select top 1 * from LEAVE_YEAR where STATUS ='CLOSE' order by srno desc";
                        rs=st.executeQuery(query1);
                        while (rs.next()) {
                        	date_lastyear = rs.getString("LEAVE_START_DATE");
                        	LEAVE_END_DATE = rs.getString("LEAVE_END_DATE");
                            System.out.println("date_lastyeardate_lastyear:--" + date_lastyear);
                        }
                        if (lbean.getLEAVECD() == 1) {
                        	
                            rs = st.executeQuery("select ceiling ( " +
                                " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                "and status='A')-  " +
                                " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end  from PAYTRAN_STAGE where TRNCD in (301,302) and TRNDT between  " +
                                " '" + date_lastyear + "'  and " +
                                "'" +LEAVE_END_DATE + "'  " +
                                " and EMPNO=" + empList[i] + ")*(2.5*12/365),0)) as finalDays ");
                            System.out.println("select ceiling ( " +
                                " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                "and status='A')-  " +
                                " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end  from PAYTRAN_STAGE where TRNCD in (301,302) and TRNDT between  " +
                                " '" +date_lastyear + "'  and " +
                                "'" +LEAVE_END_DATE + "'  " +
                                " and EMPNO=" + empList[i] + ")*(2.5*12/365),0)) as finalDays ");
                        }
                        if (lbean.getLEAVECD() == 2) {
                            rs = st.executeQuery("select ceiling ( " +
                                " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                "and status='A')-  " +
                                " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end  from PAYTRAN_STAGE where TRNCD in (301,302) and TRNDT between  " +
                                " '" +date_lastyear + "'  and " +
                                "'" + LEAVE_END_DATE + "'  " +
                                " and EMPNO=" + empList[i] + ")*(0.83*12/365),0)) as finalDays ");
                            System.out.println("select ceiling ( " +
                                    " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                    "and status='A')-  " +
                                    " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end  from PAYTRAN_STAGE where TRNCD in (301,302) and TRNDT between  " +
                                    " '" +date_lastyear + "'  and " +
                                    "'" + LEAVE_END_DATE + "'  " +
                                    " and EMPNO=" + empList[i] + ")*(0.83*12/365),0)) as finalDays ");
                            
                        }
                        if (lbean.getLEAVECD() == 3) {
                            rs = st.executeQuery("select ceiling ( " +
                                " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                "and status='A')-  " +
                                " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end   from PAYTRAN_STAGE where TRNCD in (301,302)and TRNDT between  " +
                                " '" + date_lastyear + "'  and " +
                                "'" + LEAVE_END_DATE + "'  " +
                                " and EMPNO=" + empList[i] + ")*(1.25*12/365),0)) as finalDays ");
                            System.out.println("select ceiling ( " +
                                    " (select CRLIM from leavemass where  LEAVECD=" + lbean.getLEAVECD() + "" +
                                    "and status='A')-  " +
                                    " round( (SELECT CASE when Isnull(Sum(net_amt), 0)<24then 0 else Isnull(Sum(net_amt), 0)end   from PAYTRAN_STAGE where TRNCD in (301,302)and TRNDT between  " +
                                    " '" + date_lastyear + "'  and " +
                                    "'" + LEAVE_END_DATE + "'  " +
                                    " and EMPNO=" + empList[i] + ")*(1.25*12/365),0)) as finalDays ");
                            
                        }
                        if (rs.next()) {
                            finalDays = rs.getInt("finalDays");
                            System.out.println("finalDays:--" + finalDays);
                        }
                        EmpOffHandler eoh = new EmpOffHandler();
                        EmpOffBean eob = eoh.getEmpOfficAddInfo(Integer.toString(empList[i]));
                        int prjsrno = eob.getPrj_srno();
                        System.out.println("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS) " +
                            "VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','C',5," + finalDays + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1)");
                        // to insert into leave tran
                        st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                            "VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','C',5," + finalDays + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'AUTOCR')");

                        rs = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE BALDT <= '" + dates[j] + "' AND EMPNO = " + empList[i] + " AND LEAVECD = " + lbean.getLEAVECD() + " ORDER BY leavecd,baldt desc , srno DESC");


                        //after leavetran insertion leave bal insertion starts from here if exist only if condition work else else will work
                        if (rs.next()) {
                            if (lbean.getLEAVECD() == 1) //pl
                            {
                                int maxcf = lbean.getMAXCF();
                                String DOR = "";
                                System.out.println("value for maximum carry forward" + maxcf);

                                float totalbal = 0;
                                float totalcredit = 0;
                                float leaveEncash = 0;



                                // akshay nikam %% for retirement auto credit pl
                                System.out.println("pl issue regarding retireeeeeeeeeeeeeeee");

                               // String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, DATEADD(Year,58,(select dob from EMPMAST where EMPNO=" + empList[i] + "))), 106), 11), ' ', '-') as DOR";
                                //  String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, (select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + ")), 106), 11), ' ', '-') as DOR";
                                String query =  "select replace(right(convert(varchar,(select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + "), 106), 11), ' ', '-') as DOR";
                                System.out.println(query);

                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    DOR = rsDoj.getString("DOR");

                                }


                                int maxcf1 = lbean.getMAXCF();
                                System.out.println("value for maximum carry forward" + DOR);


                                /*totalcredit=(leaveVals[j]);
                                totalbal=(leaveVals[j]);*/
                                float onemonth_leave = 2.5f;

                                // for retirement code start here 
                                
                                System.out.println("DOR : "+DOR+":: MONTH : "+month);
                                
                                if (ReportDAO.EOYForJanToDec(DOR).equalsIgnoreCase(ReportDAO.EOYForJanToDec(month))) {

                                    System.out.println("DOR " + ReportDAO.EOYForJanToDec(DOR));
                                    System.out.println("Dt " + ReportDAO.EOYForJanToDec(dt));
                                    //AS PER NAMCO RULE LWP<25 AND (RETIRE / NOT RETIRE) THIS YEAR THEN CREDIT SL 10 ... LWP>=25 (RETIRE / NOT RETIRE) THEN CREDIT BY LWP RATIO
                                    //BY @niket
                                    //for proper calculation... @ni
                                    //query = "select round ( round((DATEDIFF(dd,'" + ReportDAO.BOYForJanToDec(month) + "','" + DOR + "')+1)/30* " + onemonth_leave + ",1),0) as total";
                                   /* query = "select round ( round((((DATEDIFF(dd,'" + ReportDAO.BOYForJanToDec(month) + "','" + DOR + "')+1) * " + onemonth_leave + ")/30) ,1),0) as total";
                                    System.out.println("Round 1 query for pl" + query);
                                    rsDoj = stDOJ.executeQuery(query);

                                    while (rsDoj.next()) {
                                        totalbal = rsDoj.getFloat("total");

                                        //  update leave transaction up to retirement date and set totatal days as per retirement date.

                                    }*/
                                    totalbal=finalDays!=lbean.getCRLIM()?finalDays:lbean.getCRLIM();
                                    
                                    System.out.println("update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate = '" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 1  and status = 1 and LREASON='AUTOCR' ;");

                                    st01.executeUpdate("update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate ='" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 1  and status = 1 and LREASON='AUTOCR' ");


                                    totalcredit = (rs.getInt("TOTCR") + totalbal);
                                    totalbal = (rs.getInt("BAL") + totalbal);

                                    if (totalbal >= maxcf) {
                                        leaveEncash = totalbal - maxcf;

                                        totalbal = maxcf;
                                        System.out.println("value for maximum carry forward" + maxcf);
                                    }




                                    if (totalcredit > maxcf) {

                                        totalcredit = maxcf + leaveEncash;

                                        System.out.println("1" + totalcredit);
                                        //encashLeave code 1 start here

                                        System.out.println("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + applno + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");

                                        st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveEncash - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");
                                        //enacAshment leave for the next year
                                        System.out.println("2" + "INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveEncash - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");

                                        System.out.println("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal) + "," + totalcredit + ",0)");

                                        st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal) + "," + totalcredit + ",0)");

                                    }
                                    // IF there is Encash.... by code
                                    System.out.println("working if not encash?");
                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + "," + leaveEncash + ")");
                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");

                                    System.out.println("3" + "INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + "," + leaveEncash + ")");
                                    System.out.println("4" + "INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");

                                } else {
  //akshay for 2018  changes                                 /* totalcredit = (rs.getInt("TOTCR") + finalDays);*/
                                    totalcredit = (rs.getInt("BAL") + finalDays);
                                    totalbal = (rs.getInt("BAL") + finalDays);

                                    if (totalbal >= maxcf) {
                                        leaveEncash = totalbal - maxcf;

                                        totalbal = maxcf;
                                        System.out.println("value for maximum carry forward" + maxcf);
                                    }


                                    if (totalcredit > maxcf) {

                                        totalcredit = maxcf + leaveEncash;

                                        System.out.println("1" + totalcredit);
                                        //encashLeave code 1 start here

                                        System.out.println("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + applno + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");

                                        st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveEncash - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");
                                        //enacAshment leave for the next year
                                        System.out.println("2" + "INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',4," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveEncash - 1) + ",'" + dates[j] + "'),23),1,'Leave Encash')");

                                        System.out.println("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal) + "," + totalcredit + ",0)");

                                        st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal) + "," + totalcredit + ",0)");


                                    }


                                    // IF there is Encash.... by code
                                    System.out.println("working if not encash?");
                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalcredit + "," + leaveEncash + ")");
                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");

                                    System.out.println("3" + "INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalcredit + "," + leaveEncash + ")");
                                    System.out.println("4" + "INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");

                                }


                            }


                            if (lbean.getLEAVECD() == 2) //sl
                            {
                                int maxcf = lbean.getMAXCF();
                                String DOR = "";
                                System.out.println("value for maximum carry forward" + maxcf);


                                float totalbal = 0;
                                float totalcredit = 0;
                                float leaveEncash = 0;




                                // akshay nikam %% for retirement auto credit sl                                System.out.println("sl issue regarding retireeeeeeeeeeeeeeee");

                               // String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, DATEADD(Year,58,(select dob from EMPMAST where EMPNO=" + empList[i] + "))), 106), 11), ' ', '-') as DOR";
                                //String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, (select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + ")), 106), 11), ' ', '-') as DOR";
                                String query =  "select replace(right(convert(varchar,(select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + "), 106), 11), ' ', '-') as DOR";
                                System.out.println(query);

                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    DOR = rsDoj.getString("DOR");

                                }


                                int maxcf1 = lbean.getMAXCF();
                                System.out.println("value for maximum carry forward" + DOR);


                                /*totalcredit=(leaveVals[j]);
                                totalbal=(leaveVals[j]);*/
                                float onemonth_leave = 0.83f;

                                // for retirement code start here 

                                if (ReportDAO.EOYForJanToDec(DOR).equalsIgnoreCase(ReportDAO.EOYForJanToDec(month))) 
                                    {

                                    System.out.println("DOR " + ReportDAO.EOYForJanToDec(DOR));
                                    System.out.println("Dt " + ReportDAO.EOYForJanToDec(dt));
                                    System.out.println("CRLIM "+lbean.getCRLIM());
                                    
                                    //AS PER NAMCO RULE LWP<25 AND (RETIRE / NOT RETIRE) THIS YEAR THEN CREDIT SL 10 ... LWP>=25 (RETIRE / NOT RETIRE) THEN CREDIT BY LWP RATIO
                                    //BY @niket
                                    /*query = "select round ( round((DATEDIFF(dd,'" + ReportDAO.BOYForJanToDec(month) + "','" + DOR + "')+1)/30* " + onemonth_leave + ",1),0) as total";
                                    System.out.println("Round 1 query for pl" + query);
                                    rsDoj = stDOJ.executeQuery(query);

                                    while (rsDoj.next()) {
                                        totalbal = rsDoj.getFloat("total");

                                        //  update leave transaction up to retirement date and set totatal days as per retirement date.

                                    }*/
                                    
                                  //BY @niket
                                    totalbal=finalDays!=lbean.getCRLIM()?finalDays:lbean.getCRLIM();
                                  
                                    System.out.println("update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate = '" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 2  and status = 1 and LREASON='AUTOCR' ;");

                                    st01.executeUpdate("update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate ='" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 2  and status = 1 and LREASON='AUTOCR' ");


                                    totalcredit = (rs.getInt("TOTCR") + totalbal);
                                    totalbal = (rs.getInt("BAL") + totalbal);

                                    if (totalbal >= maxcf) {
                                        leaveEncash = totalbal - maxcf;

                                        totalbal = maxcf;
                                    }

                                    float totaldebit = 0;
                                    totaldebit = totalcredit - totalbal;

                                    if (totalcredit > maxcf) {
                                        totalcredit = maxcf + leaveEncash;
                                        totaldebit = 0;

                                        //encashLeave code start here bt for namco it is not in encash, in this lapse will work

                                        System.out.println("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','D',6," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Lapse')");

                                        st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','D',6," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Lapse')");

                                        st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal + leaveEncash) + "," + totalcredit + ",0 )");


                                        //lapse leave for the next year

                                    }

                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + "," + totaldebit + ")");

                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");



                                } else {
                                 /*   totalcredit = (rs.getInt("TOTCR") + finalDays);
                                    totalbal = (rs.getInt("BAL") + finalDays);

                                    if (totalbal >= maxcf) {
                                        leaveEncash = totalbal - maxcf;
                                        totalbal = maxcf;
                                    }

                                    float totaldebit = 0;
                                    totaldebit = totalcredit - totalbal;

                                    if (totalcredit > maxcf) {
                                        totalcredit = maxcf + leaveEncash;
                                        totaldebit = 0;*/
                                	System.out.println("for AutoCredit Sl");	
                                	
  //akshay for 2018  changes    /*	totalcredit = (rs.getInt("TOTCR") + finalDays);*/
                                	totalcredit = (rs.getInt("BAL") + finalDays);
                                    totalbal = (rs.getInt("BAL") + finalDays);
                                    
                                    System.out.println("totalcredit is "+totalcredit);
                                    System.out.println("totalbal is "+totalbal);
                                    System.out.println("finalDays is "+finalDays);
                                    System.out.println("maxcf is "+maxcf);
                                  
                                        if (totalbal >= maxcf) {
                                            leaveEncash = totalbal - maxcf;

                                            totalbal = maxcf;
                                            System.out.println("value for maximum carry forward" + maxcf);
                                        
                                     
                                        System.out.println("leaveEncash is "+leaveEncash);
                                        System.out.println("totalbal1 is "+totalbal);
                                        System.out.println("totalcredit is "+totalcredit);
                                        }

                                    float totaldebit = 0;
                                    totaldebit = totalcredit - totalbal;
                                    System.out.println("totaldebit is "+totaldebit);
                                    
                                    if (totalcredit > maxcf) {
                                        totalcredit = maxcf + leaveEncash;
                                        totaldebit = 0;
                                	
                                        
                                        //encashLeave code start here bt for namco it is not in encash, in this lapse will work

                                        System.out.println("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','D',6," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Lapse')");

                                        st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                            "VALUES(" + prjsrno + "," + (applno + 1) + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','D',6," + leaveEncash + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (finalDays - 1) + ",'" + dates[j] + "'),23),1,'Leave Lapse')");

                                        //st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal + leaveEncash) + "," + totalcredit + ",0 )");
                                        st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal  + "," + totalcredit + ",0 )");


                                        //lapse leave for the next year

                                    }

                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalcredit + "," + totaldebit + ")");

                             st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");
                               //     st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalcredit + ",0)");
                                    
                                    System.out.println("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalcredit + "," + totaldebit + ")");
                                    System.out.println("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");
                                }
                            }

                            if (lbean.getLEAVECD() == 3) //cl
                            {

                                //String joiningdate="select DOR from empmast where empno = "+empList[i]+" and doj between '"+ReportDAO.BOYForJanToDec(month)+"' and '"+ReportDAO.EOYForJanToDec(month)+"' ";
                                String DOR = "";
                                // for new joining employeee only cl is applicable for joining year
                             

                                //String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, DATEADD(Year,58,(select dob from EMPMAST where EMPNO=" + empList[i] + "))), 106), 11), ' ', '-') as DOR";
                                //String query = "select replace(right(convert(varchar,  DATEADD(DAY,-1, (select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + ")), 106), 11), ' ', '-') as DOR";
                                String query =  "select replace(right(convert(varchar,(select RETIREMENT_EXT_PERIOD  from EMPMAST where EMPNO=" + empList[i] + "), 106), 11), ' ', '-') as DOR";
                                System.out.println(" 1 this is...."+query);

                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    DOR = rsDoj.getString("DOR");

                                }





                                int maxcf = lbean.getMAXCF();
                                System.out.println("2 this is...."+"value for maximum carry forward" + DOR);

                                float totalbal = 0;
                                float totalcredit = 0;
                               /* totalcredit = (leaveVals[j]);
                                totalbal = (leaveVals[j]);*/
                                totalcredit = finalDays;
                                totalbal = finalDays;
                                float onemonth_leave = 1.25f;

                                // for retirement code start here 

                                if (ReportDAO.EOYForJanToDec(DOR).equalsIgnoreCase(ReportDAO.EOYForJanToDec(month))) {

                                    System.out.println("3 DOR " + ReportDAO.EOYForJanToDec(DOR));
                                    System.out.println("4 Dt " + ReportDAO.EOYForJanToDec(dt));
                                    //for proper calculation...@ni
                                    //query = "select round ( round((DATEDIFF(dd,'" + ReportDAO.BOYForJanToDec(month) + "','" + DOR + "')+1)/30* " + onemonth_leave + ",1),0) as total";
                                   // query = "select round ( round((((DATEDIFF(dd,'" + ReportDAO.BOYForJanToDec(month) + "','" + DOR + "')+1) * " + onemonth_leave + ")/30) ,1),0) as total";
                     //@akshay for 01=last date previous month or 2 >=last date current month       //   
                                    
                                    query = "  select round ( round((((DATEDIFF(dd, '" + ReportDAO.BOYForJanToDec(month) + "',  " +
                                    		" (select  CASE when (select  SUBSTRING ('" + DOR + "',1,2))=01 then  " +  
                                    		" (SELECT CONVERT(DATE,(select DATEADD(MONTH, DATEDIFF(MONTH, -1,'" + DOR + "')-1, -1))))  " +
                                    		"  else (SELECT CONVERT(DATE,(select  DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,'" + DOR + "')+1,0))))  ) end ) " +
                                    		"  )+1) * 1.25)/30) ,1),0)  as total ";
                                    
                                    
                                    System.out.println("5 Round 2 query " + query);
                                    rsDoj = stDOJ.executeQuery(query);

                                    while (rsDoj.next()) {
                                        totalbal = rsDoj.getFloat("total");

                                        //  update leave transaction up to retirement date and set totatal days as per retirement date.

                                    }
                                    System.out.println("6 update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate = '" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 3  and status = 1 and LREASON='AUTOCR' ;");
                                    st01.executeUpdate("update leavetran set todt =convert(date,'" + DOR + "',105)  , days = " + totalbal + " " +
                                        " where trndate ='" + dates[j] + "' and empno = " + empList[i] + "  " +
                                        " and leavecd = 3  and status = 1 and LREASON='AUTOCR' ");

                                }



                                if ((rs.getInt("BAL") > 0)) {



                                    //encashLeave code 1 start here
                                    st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + ",0," + totalcredit + "," + (rs.getInt("BAL") + rs.getInt("TOTDR")) + ") ");
                                    System.out.println("7 INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + ",0," + totalcredit + "," + (rs.getInt("BAL") + rs.getInt("TOTDR")) + ") ");;


                                    System.out.println("8 INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                        "VALUES(" + prjsrno + "," + applno + "," + 1 + "," + empList[i] + ",'" + dates[j] + "','D',6," + (rs.getInt("BAL") + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveVals[j] - 1) + ",'" + dates[j] + "'),23),1,'Leave lapse')"));

                                    st.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS,LREASON) " +
                                        "VALUES(" + prjsrno + "," + (applno + 1) + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + dates[j] + "','D',6," + (rs.getInt("BAL") + ",'" + dates[j] + "','" + dates[j] + "',CONVERT(NVARCHAR,DATEADD(DAY," + (leaveVals[j] - 1) + ",'" + dates[j] + "'),23),1,'Leave Lapse')"));
                                    //enacAshment leave for the next year

                                }








                                st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");
                                System.out.println("9 INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)");;


                            }





                        } else {


                            if (lbean.getLEAVECD() == 3) //cl
                            {



                                // for new joining employeee only cl is applicable for joining year

                                String joiningdate = "";
                                float onth = 0.0f;
                                String query = "select doj from empmast where empno = " + empList[i] + " " +
                                    /*"and doj between '"+ReportDAO.BOYForJanToDec(month)+"' and '"+ReportDAO.EOYForJanToDec(month)+"'   " +*/ //add in query for current year  of joining employee
                                    " ";

                                System.out.println(query);
                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    joiningdate = rsDoj.getDate("DOj") == null ? "" : dateFormat(rsDoj.getDate("DOj"));
                                    onth = ReportDAO.getMonth(joiningdate);
                                }




                                System.out.println("month of joining inside cl for a employeeeeeeeeeeeeeeeee" + onth);


                                int maxcf = lbean.getMAXCF();

                                System.out.println("value for maximum carry forward" + maxcf);
                                float totalbal = 0;



                                query = "select round ( round(DATEDIFF(dd,'" + joiningdate + "','" + ReportDAO.EOYForJanToDec(month) + "')/30* 1.25,1),0) as total";
                                System.out.println(query);
                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    totalbal = rsDoj.getFloat("total");

                                }

                                /*	if(onth!=0){
                                	
                                		float totalval= (float) leaveVals[j];
                                		totalbal=(12-onth)*(totalval/12);
                                	
                                	}
                                	else{
                                		totalbal=(leaveVals[j]);
                                		}*/

                                int totalcredit = 0;
                                totalcredit = (finalDays);

                                st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + ",1,0,0,0)"); // for pl
                                st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + ",2,0,0,0)"); // for sl						
                                st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + totalbal + "," + totalbal + ",0)"); // for Cl
                                //st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('"+dates[j]+"',"+empList[i]+","+lbean.getLEAVECD()+","+totalbal+","+totalcredit+",0)");// for Cl

                            } //ends here

                            // if emp is not in joining year then pl and sl
                            else {

                                String joiningyear = "";
                                //float onth = ReportDAO.getMonth(joiningdate);

                                float totalbal = leaveVals[j];

                                float onemonth_leave = 0.0f;

                                if (lbean.getLEAVECD() == 1) {

                                    onemonth_leave = (float) 2.5;
                                    //onemonth_leave=leaveVals[j]/12;
                                }

                                if (lbean.getLEAVECD() == 2) {

                                    onemonth_leave = (float) 0.83;
                                    //onemonth_leave=leaveVals[j]/12;

                                }

                                String query = "select replace(right(convert(varchar, DATEADD(Year,-1,'" + dates[j] + "'), 106), 11), ' ', '-')  as joinyear ";
                                System.out.println("joining year" + query);

                                rsDoj = stDOJ.executeQuery(query);

                                while (rsDoj.next()) {
                                    joiningyear = rsDoj.getString("joinyear") == null ? "" : (rsDoj.getString("joinyear"));
                                }


                                if (ReportDAO.EOYForJanToDec(joiningyear).equalsIgnoreCase(ReportDAO.EOYForJanToDec(dt))) {



                                    query = "select round ( round(DATEDIFF(dd,'" + dt + "','" + ReportDAO.EOYForJanToDec(dt) + "')/30* " + onemonth_leave + ",1),0) as total";

                                    System.out.println("date diff for joining year" + query);
                                    //	rsDoj=stDOJ.executeQuery(query);

                                    while (rsDoj.next()) {
                                        totalbal = rsDoj.getFloat("total");

                                    }

                                }



                                System.out.println("at last");
                                st1.executeUpdate("INSERT INTO LEAVEBAL VALUES('" + dates[j] + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (totalbal) + "," + (finalDays) + ",0)");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            //UsrHandler.senderrormail(e,"utility.creditmethod");
            e.printStackTrace();
        }
    }

    public String[] getDates(int factor, int freq, String date, Connection con) {
        String[] result = new String[factor];
        try {
            Statement st = con.createStatement();
            String sql = "SELECT ";
            for (int i = 0; i < factor; i++) {
                sql += " Convert(nvarchar,dateadd(day," + (i * freq) + ",'" + date + "'),23),";
            }
            sql = sql.substring(0, sql.length() - 1);
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                for (int i = 0; i < factor; i++) {
                    result[i] = rs.getString(i + 1);
                }
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            //UsrHandler.senderrormail(e,"utility.getDates");
            e.printStackTrace();
        }
        return result;
    }

    public void autoLeaveDebit(String emp, int srno) {
        try {
            Connection con = ConnectionManager.getConnection();
            Statement st = con.createStatement();
            Statement stUP = con.createStatement();
            ResultSet lbal = null;
            ResultSet ltran = null;
            LeaveMasterHandler LMH = new LeaveMasterHandler();
            ArrayList < LeaveMassBean > list = LMH.getleavetypeList("SELECT * FROM LEAVEMASS WHERE SRNO = " + srno);
            System.out.println("1:-" + "SELECT * FROM LEAVEMASS WHERE SRNO = " + srno);
            String[] empl = emp.split(",");
            int[] empList = new int[empl.length];
            for (int i = 0; i < empl.length; i++) {
                empList[i] = Integer.parseInt(empl[i]);
            }
            if (list.size() > 0) {
                LeaveMassBean lbean = list.get(0);
                int maxCF = lbean.getMAXCF();
                for (int i = 0; i < empList.length; i++) {
                    int leaveTaken = 0;
                    int bal = 0;
                    int totCR = 0;
                    int totDR = 0;
                    int avail = 0;
                    int toCF = 0;
                    int toElap = 0;
                    ltran = st.executeQuery("SELECT ISNULL(SUM(DAYS),0) FROM LEAVETRAN WHERE EMPNO=" + empList[i] + " AND LEAVECD=" + lbean.getLEAVECD() + " AND TRNTYPE='D' AND STATUS='SANCTION' AND FRMDT >= '" + lbean.getFBEGINDATE() + "' AND TODT <='" + lbean.getFENDDATE() + "'");
                    //	ltran = st.executeQuery("SELECT ISNULL(SUM(DAYS),0) FROM LEAVETRAN WHERE EMPNO="+empList[i]+" AND LEAVECD="+lbean.getLEAVECD()+" AND TRNTYPE='D' AND STATUS='SANCTION' AND FRMDT >= '2016-01-01' AND TODT <='2016-12-31'");
                    System.out.println("2:-" + "SELECT ISNULL(SUM(DAYS),0) FROM LEAVETRAN WHERE EMPNO=" + empList[i] + " AND LEAVECD=" + lbean.getLEAVECD() + " AND TRNTYPE='D' AND STATUS='SANCTION' AND FRMDT >= '" + lbean.getFBEGINDATE() + "' AND TODT <='" + lbean.getFENDDATE() + "'");
                    if (ltran.next()) {
                        leaveTaken = ltran.getInt(1);
                        System.out.println("3:-" + leaveTaken);
                    }
                    lbal = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE EMPNO =" + empList[i] + " AND LEAVECD =" + lbean.getLEAVECD() + " AND BALDT <= '" + lbean.getFENDDATE() + "' ORDER BY BALDT DESC");
                    //		lbal = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE EMPNO ="+empList[i]+" AND LEAVECD ="+lbean.getLEAVECD()+" AND BALDT <= '2016-12-31' ORDER BY BALDT DESC");
                    System.out.println("4:-" + "SELECT TOP 1 * FROM LEAVEBAL WHERE EMPNO =" + empList[i] + " AND LEAVECD =" + lbean.getLEAVECD() + " AND BALDT <= '" + lbean.getFENDDATE() + "' ORDER BY BALDT DESC");
                    if (lbal.next()) {
                        bal = lbal.getInt("BAL");
                        totCR = lbal.getInt("TOTCR");
                        totDR = lbal.getInt("TOTDR");
                    }


                    if (lbean.getFREQUENCY().equalsIgnoreCase("Y")) {
                        if (leaveTaken < lbean.getCRLIM()) {
                            avail = lbean.getCRLIM() - leaveTaken;
                            System.out.println("5:-" + avail);
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("H")) {
                        if (leaveTaken < (lbean.getCRLIM() * 2)) {
                            avail = (lbean.getCRLIM() * 2) - leaveTaken;
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("Q")) {
                        if (leaveTaken < (lbean.getCRLIM() * 4)) {
                            avail = (lbean.getCRLIM() * 4) - leaveTaken;
                        }
                    } else if (lbean.getFREQUENCY().equalsIgnoreCase("M")) {
                        if (leaveTaken < (lbean.getCRLIM() * 12)) {
                            avail = (lbean.getCRLIM() * 12) - leaveTaken;
                        }
                    }


                    /*if(leaveTaken < lbean.getCRLIM())
                    {
                    	avail = lbean.getCRLIM() - leaveTaken;
                    }*/

                    if (avail >= maxCF) {
                        toCF = maxCF;
                        toElap = avail - maxCF;
                        System.out.println("6:-" + toCF);
                        System.out.println("7:-" + toElap);
                    } else {
                        toCF = avail;
                        toElap = 0;
                        System.out.println("8:-" + toCF);
                    }
                    if (bal > 0) {
                        if (bal >= lbean.getMAXCUMLIM()) {

                            if (lbean.getFREQUENCY().equalsIgnoreCase("Y")) {
                                //toElap = toElap + (toCF<(bal-lbean.getMAXCUMLIM())? toCF:(bal-lbean.getMAXCUMLIM()) );
                                toElap = (toCF < (bal - lbean.getMAXCUMLIM()) ? toCF : (bal - lbean.getMAXCUMLIM()));
                                toCF = 0;
                                System.out.println("9:-" + toElap);
                            } else {
                                toElap = bal - toCF;
                            }

                        } else {

                            if ((bal + toCF) > lbean.getMAXCUMLIM()) {
                                toElap = toElap + ((bal + toCF) - lbean.getMAXCUMLIM());
                                toCF = lbean.getMAXCUMLIM() - bal;
                                toElap = toCF;
                                System.out.println("10:-" + toElap);
                                System.out.println("11:-" + toCF);
                                System.out.println("12:-" + toElap);
                            }



                        }
                    }
                    if (toElap > 0) {
                        ltran = st.executeQuery("SELECT * FROM LEAVETRAN WHERE LEAVECD=" + lbean.getLEAVECD() + " AND EMPNO=" + empList[i] + " AND TRNDATE='" + lbean.getFENDDATE() + "'" +
                            " AND TRNTYPE='D' AND LEAVEPURP=6 AND DAYS=" + toElap + " AND APPLDT='" + lbean.getFENDDATE() + "' AND FRMDT=CONVERT(NVARCHAR,DATEADD(DAY," + ((toElap - 1) * (-1)) + ",'" + lbean.getFENDDATE() + "'),23) AND TODT='" + lbean.getFENDDATE() + "' AND STATUS=1");
                        System.out.println("13:-" + "SELECT * FROM LEAVETRAN WHERE LEAVECD=" + lbean.getLEAVECD() + " AND EMPNO=" + empList[i] + " AND TRNDATE='" + lbean.getFENDDATE() + "'" +
                            " AND TRNTYPE='D' AND LEAVEPURP=6 AND DAYS=" + toElap + " AND APPLDT='" + lbean.getFENDDATE() + "' AND FRMDT=CONVERT(NVARCHAR,DATEADD(DAY," + ((toElap - 1) * (-1)) + ",'" + lbean.getFENDDATE() + "'),23) AND TODT='" + lbean.getFENDDATE() + "' AND STATUS=1");
                        if (ltran.next()) {
                            System.out.println("Already Debited");
                        } else {

                            System.out.println("001");

                            Statement st1 = con.createStatement();
                            ResultSet rs2 = null;
                            rs2 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
                            int applno = 0;
                            if (rs2.next()) {
                                applno = rs2.getInt(1);
                            }
                            EmpOffHandler eoh = new EmpOffHandler();
                            EmpOffBean eob = eoh.getEmpOfficAddInfo(Integer.toString(empList[i]));
                            int prjsrno = eob.getPrj_srno();




                            stUP.executeUpdate("INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS) " +
                                " VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + lbean.getFENDDATE() + "','D',6," + toElap + ",'" + lbean.getFENDDATE() + "',CONVERT(NVARCHAR,DATEADD(DAY," + ((toElap - 1) * (-1)) + ",'" + lbean.getFENDDATE() + "'),23),'" + lbean.getFENDDATE() + "',1)");
                            System.out.println("14:-" + "INSERT INTO LEAVETRAN(PRJ_SRNO,APPLNO,LEAVECD,EMPNO,TRNDATE,TRNTYPE,LEAVEPURP,DAYS,APPLDT,FRMDT,TODT,STATUS)  VALUES(" + prjsrno + "," + applno + "," + lbean.getLEAVECD() + "," + empList[i] + ",'" + lbean.getFENDDATE() + "','D',6," + toElap + ",'" + lbean.getFENDDATE() + "',CONVERT(NVARCHAR,DATEADD(DAY," + ((toElap - 1) * (-1)) + ",'" + lbean.getFENDDATE() + "'),23),'" + lbean.getFENDDATE() + "',1)");
                        }
                    }
                    lbal = st.executeQuery("SELECT * FROM LEAVEBAL WHERE BALDT='" + lbean.getFENDDATE() + "' AND EMPNO=" + empList[i] + " AND LEAVECD=" + lbean.getLEAVECD() + " AND BAL=" + (bal - toElap) + " AND TOTCR=" + (totCR) + " AND TOTDR=" + (totDR + toElap) + "");
                    //		lbal = st.executeQuery("SELECT * FROM LEAVEBAL WHERE BALDT='2016-12-31' AND EMPNO="+empList[i]+" AND LEAVECD="+lbean.getLEAVECD()+" AND BAL="+(bal-toElap)+" AND TOTCR="+(totCR)+" AND TOTDR="+(totDR+toElap)+"");

                    System.out.println("15" + "SELECT * FROM LEAVEBAL WHERE BALDT='" + lbean.getFENDDATE() + "' AND EMPNO=" + empList[i] + " AND LEAVECD=" + lbean.getLEAVECD() + " AND BAL=" + (bal - toElap) + " AND TOTCR=" + (totCR) + " AND TOTDR=" + (totDR + toElap) + "");
                    if (lbal.next()) {
                        System.out.println("Record present");
                    } else {
                        System.out.println("avail  " + avail);
                        System.out.println("encalp" + toElap);

                        //	stUP.executeUpdate("INSERT INTO LEAVEBAL VALUES('"+lbean.getFENDDATE()+"',"+empList[i]+","+lbean.getLEAVECD()+","+(bal)+","+(totCR)+","+(totDR)+")");
                        stUP.executeUpdate("INSERT INTO LEAVEBAL VALUES('2016-12-31'," + empList[i] + "," + lbean.getLEAVECD() + "," + (bal) + "," + (totCR) + "," + (totDR) + ")");
                        System.out.println("16" + "INSERT INTO LEAVEBAL VALUES('" + lbean.getFENDDATE() + "'," + empList[i] + "," + lbean.getLEAVECD() + "," + (bal) + "," + (totCR) + "," + (totDR) + ")");
                    }
                }
            }
            con.close();
        } catch (Exception e) {
            //UsrHandler.senderrormail(e,"utility.leaveautodebit");
            e.printStackTrace();
        }
    }
    public String  retirement_extLeaveCredit(String empno,String leaveDays,String retirementdate,int crdby,String credited)  throws ParseException
	{
		 String flags = "";
	   try
	   {
		   Connection con = ConnectionManager.getConnection();
		   Statement st = con.createStatement();
           ResultSet rs = null;
           rs = st.executeQuery("SELECT isnull((SELECT MAX(APPLNO)+1 FROM LEAVETRAN),1)");
           int applno = 0;
           if (rs.next()) {
               applno = rs.getInt(1);
           }
           ResultSet rs1 = null;
           rs1 = st.executeQuery("select * from leavebal where EMPNO= "+empno+" and LEAVECD=3 order by BALDT desc,srno desc");
           float bal = 0.0f;
           float totalCR = 0.0f;
           if (rs1.next()) {
        	   bal = rs1.getFloat("BAL");
        	   totalCR = rs1.getFloat("TOTCR");
           }
           
           
           EmpOffHandler eoh = new EmpOffHandler();
           EmpOffBean eob = eoh.getEmpOfficAddInfo(empno);
           int prjsrno = eob.getPrj_srno();
           
           String insertquery="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
					"DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,(select convert(date,getdate())))";
			PreparedStatement Pstat = con.prepareStatement(insertquery);
			Pstat.setString(1,empno);
			Pstat.setInt(2,3);
			Pstat.setString(3, retirementdate);
		    Pstat.setString(4, "C");
		    Pstat.setInt(5, applno);
		    Pstat.setString(6, retirementdate);
		    Pstat.setString(7, retirementdate);
		    Pstat.setInt(8, 5);
		    Pstat.setString(9, leaveDays);
		    Pstat.setString(10, "1");
		    Pstat.setInt(11, prjsrno);
		    Pstat.setString(12, retirementdate);
		    Pstat.setString(13, "AUTOCR");
		    Pstat.setInt(14,  crdby);
		   // Pstat.setString(15, retirementdate);	
		    
		    System.out.println("In credit Leave :---"+insertquery);
		    Pstat.executeUpdate();
		    
		    Float TotalBal = bal + Float.parseFloat(leaveDays);
		    Float TotalCR =Float.parseFloat(credited) + Float.parseFloat(leaveDays);
		    System.out.println("TotalBal: "+TotalBal+"  TotalCR: "+TotalCR);
		    String SQL="INSERT INTO LEAVEBAL VALUES((select convert(date,getdate()))," + empno + ",3," + TotalBal+ ","+TotalCR+",0.0)";
		    System.out.println("In credit Leave :---"+SQL);
		    st.execute(SQL);
		    
		    flags = "1";
		    
		    Pstat.close();
		    st.close();
		    con.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
}