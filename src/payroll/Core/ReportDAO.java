package payroll.Core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;
import payroll.Model.GraphBean;
import payroll.Model.RepoartBean;




/**
 * @author 
 *
 */
public class ReportDAO{
	
	
	//******************
	public static String getPastdate(){
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.MONTH, -6);
	Date result = cal.getTime();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(result);
	return dt;
	}
	public static boolean OpenCon(String Pusr, String Ppwd, String PClinServ,RepoartBean rb) {

		try {
			
			
			/*Class.forName("oracle.jdbc.driver.OracleDriver");
			Cn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1522:XE", "payroll", "payroll");*/
			Connection Cn = ConnectionManager.getConnection();
			rb.setCn(Cn);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}
		
	public static void inithead(RepoartBean repBean){ //Function for UtilityDAO or uti
		
		repBean.setHdg1("");
		repBean.setHdg2("");
		repBean.setHdg3("");
		repBean.setHdg4("");
		repBean.setHdg5("");
		repBean.setHdg6("");
		repBean.setHdg7("");
		repBean.setLineLen(0);
		repBean.setPageLen(0);
		repBean.setLnCount(0);
		repBean.setPageNo(0);
	
		
	}
	public static String concation(){
		Date ddt = new Date();
		String stt = ddt.toString().replaceAll("\\s","");
		String fin = stt.replaceAll(":", "_");
		return fin;
	}
	public static void make_prn_file(String fname,RepoartBean repBean){
		//FileName = fname;
		File file = new File(fname);
		FileWriter Fp;
		try
		{
			Fp = new FileWriter(file);
			repBean.setFp(Fp);
		}
		catch(Exception e)
		{
			System.out.println("Error in make_prn_file Function "+e);
		}
	}
	
	public static String addSpaces(String str, int num)
	{
		String result="";
		String temp="";
		for(int i=0;i<num;i++)
		{
			temp+=" ";
		}
		result=str + temp;
		return result;
	}
	
	public static String getSysDate(){
		java.util.Date date = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String dt = format.format(date);
		return dt;
	}
	
	 public static String BOM(String date)
     {  
		
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d = null;  
         
		try {
			d = sdf1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         calendar.set(Calendar.DAY_OF_MONTH, 1);  
         java.util.Date dddd = calendar.getTime();  
         return sdf1.format(dddd);  
     }  
	 
	 public static String Boy(String date)
     {  
		
		 String boy ="";
		 int month = getMonth(date);
		 if(month < 4){
			 boy = "01-APR-"+ (Integer.parseInt(date.substring(7,11))-1);	 
		 }else{
			 boy = "01-APR-"+ (Integer.parseInt(date.substring(7,11)));
		 }
		  
         return boy; 
     }  
	 
	 public static String Eoy(String date)
     {  
		
		 String eoy ="";
		 int month = getMonth(date);
		 if(month < 4){
			eoy = "31-MAR-"+ (Integer.parseInt(date.substring(7,11)));	 
		 }else{
			 eoy = "31-MAR-"+ (Integer.parseInt(date.substring(7,11))+1);
		 }
		  
         return eoy; 
     }  
	 
	 public static String BoFinancialy(String date)
		{  

			String boy ="";
			int month = getMonth(date);
			if(month < 3){
				boy = "01-APR-"+ (Integer.parseInt(date.substring(7,11))-1);	 
			}else{
				boy = "01-APR-"+ (Integer.parseInt(date.substring(7,11)));
			}

			return boy; 
		}  

	 public static String EoFinancialy(String date)
		{  
			String eoy ="";
			int month = getMonth(date);
			if(month < 3){
				eoy = "31-MAR-"+ (Integer.parseInt(date.substring(7,11)));	 
			}else{
				eoy = "31-MAR-"+ (Integer.parseInt(date.substring(7,11))+1);
			}

			return eoy; 
		}  
	 public static int getMonth(String date)
     {  
		
         SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d = null;
		try {
			d = sdf1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         int month = calendar.get(Calendar.MONTH);
		return month;
         
     }  
   
     public static String EOM(String date)   
     {  
     	
    	 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d = null;
         try
         {
			d = sdf1.parse(date);
		 }
         catch (ParseException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
         java.util.Date dddd = calendar.getTime();  
         return sdf1.format(dddd);  
     }  

     
     public static String EOM1(String date)   
     {  
     	
    	 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
         java.util.Date d = null;
         try
         {
			d = sdf1.parse(date);
		 }
         catch (ParseException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
         java.util.Date dddd = calendar.getTime();  
         return sdf1.format(dddd);  
     }  

     
     public static String DateAdd(char type, int num, String date)
     {
    	 String result = "";
    	 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d = null;
         try
         {
			d = sdf1.parse(date);
		 }
         catch (ParseException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         switch (type)
         {
         	case 'm'|'M' : calendar.add(Calendar.MONTH,num );
						   break;
         	case 'd'|'D' : calendar.add(Calendar.DATE,num );
			   				break;
         	case 'y'|'Y' : calendar.add(Calendar.YEAR,num );
			   			    break;
         	default:
         				break;
		}
         
        d = calendar.getTime();
        result = sdf1.format(d);
    	 
    	return result;
     }
     
	public static void println(String ps,int col,int breakln,boolean pg_break,String PHeadCall,RepoartBean repBean) //Default value not assign because java problem
	{
		int i = 0;
		boolean Set_foot_fl;




		if(breakln >= 1)
		{
			int test=0;//testing

			if( repBean.getStstr().length() <= col )
			{
				repBean.setStstr(repBean.getStstr() + addSpaces("", col-repBean.getStstr().length()) + ps);
			}


			try 
			{

				
				repBean.getFp().write(repBean.getStstr());


				if(breakln >= 1)
				{
					for(i = 1;i <= breakln;i++)
					{
						repBean.getFp().write(13);
						repBean.getFp().write(10);
						//Fp.write(String.valueOf((char)15));
						//Fp.write(String.valueOf((char)15));
					}
					repBean.setStstr("");

				}
				repBean.setLnCount(repBean.getLnCount()+ breakln);

				if(pg_break || (repBean.getLnCount() > repBean.getPageLen() && repBean.isFoot_Fl()))
				{
					if(repBean.isFoot_Fl())
					{
						repBean.setFoot_Fl(false);
						Set_foot_fl = true;
					}
					else
					{
						Set_foot_fl = false;
					}

					repBean.setStstr("");
					//page-footer Foot1,Foot2           remaining 

					repBean.getFp().write(12);//chk it as compare to chr(12) in Vb
					repBean.setLnCount(0);		
					repBean.setPageNo(repBean.getPageNo()+1);

					repBean.setFoot_Fl(false);

					if(PHeadCall.equals("BANK"))
					{
						//page_head(repBean.getBrnNo(), repBean.getHdg1(),repBean.getHdg2(),repBean.getHdg3(),repBean.getHdg4(),repBean.getHdg5(),repBean.getHdg6(),repBean.getHdg7(),repBean);



						if(repBean.isBar1()&&repBean.isBar2())
						{
							ReportDAO.println("", 10, 5, false, "",repBean);
							ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(),repBean.getHdg2(),repBean.getHdg3(),repBean.getHdg4(),repBean.getHdg5(),repBean.getHdg6(),repBean.getHdg7(),repBean);

						}
						else
						{
							if(repBean.isBar1())
							{
								ReportDAO.println("", 10, 5, false, "",repBean);
								ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(),repBean.getHdg2(),repBean.getHdg3(),repBean.getHdg4(),repBean.getHdg5(),"",repBean.getHdg7(),repBean);
							}
							else if(repBean.isBar2())
							{
								ReportDAO.println("", 10, 5, false, "",repBean);
								ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(),repBean.getHdg2(),"",repBean.getHdg4(),repBean.getHdg5(),repBean.getHdg6(),repBean.getHdg7(),repBean);
							}
							else
							{
								ReportDAO.println("", 10, 5, false, "",repBean);
								ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(),repBean.getHdg2(),"",repBean.getHdg4(),repBean.getHdg5(),"",repBean.getHdg7(),repBean);
							}
						}



	//	ph sir 				//println(String.valueOf((char)27)+String.valueOf((char)103),1,1,false,"",repBean);		
						println(UtilityDAO.stringOfSize(231, '-'), 10, 1, false, "",repBean);

					}

					if(Set_foot_fl)
					{
						repBean.setFoot_Fl(false);
					}
					repBean.setStstr("");

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			if(repBean.getStstr().equals(""))  //changed 
			{
				if(ps.length() <= col)
				{
					repBean.setStstr(addSpaces("", col - ps.length()) + ps);
				}
			}
			if(repBean.getStstr().length() <= col)
			{
				repBean.setStstr(repBean.getStstr() + addSpaces("", col - repBean.getStstr().length()) + ps);
			}
		}





	}
	
	
	public static void page_head(int branch_no,String head1,String head2,String head3,String head4,String head5,String head6,String head7,RepoartBean repBean){
		boolean pany = false;
		int ctr;
		int Lsize;
		/*'pageno = 1
	    'linelen = 80
	    'pagelen = 65
		 */	

		Connection Cn = repBean.getCn();
		ResultSet brn = null;
		try 
		{
			Statement st = Cn.createStatement();
			brn = st.executeQuery("SELECT * FROM BRANCH WHERE BRCD ="+branch_no);
			Lsize = repBean.getLineLen();
			if(repBean.getP_Mode()!=null)
			{
				if(repBean.getP_Mode().equalsIgnoreCase("C"))
				{
					Lsize = (int)(Lsize / 13.2) * 8;
				}
			}
			//println Chr(27) + Chr(18), 1, fp ,1 
			println(getSysDate(), repBean.getLineSpace(), 0,false,"BANK",repBean);
			ctr = (repBean.getLineLen() - 65) / 2 + 11;//***************What is 65
			if(ctr < 11)
			{
				String orgName = "Orgnization Name";
				ctr = 12;
				println(orgName, ctr, 0, false, "BANK",repBean);
				if(ctr + orgName.length() < Lsize - 11)
				{
					ctr = Lsize - 11;
					println("Page:"+repBean.getPageNo(), ctr, 1, false, "BANK",repBean);
				}
				else
				{
					ctr = ctr + orgName.length() + 8;
					println("Page:"+repBean.getPageNo(), ctr, 1, false, "BANK",repBean);
				}
				ctr = (Lsize - 45) / 2 + 1;
				println("Address", ctr, 1, false, "BANK",repBean);
				if(repBean.getP_Mode().equalsIgnoreCase("C"))
				{
					Lsize = repBean.getLineLen();
					//println Chr(27) + Chr(15),0,Fp,0;
				}
				else
				{
					//println Chr(27) + Chr(18),0,Fp,0;
				}
				ctr = (Lsize - repBean.getHdg1().length()) / 2 + 1;
				if(brn.next())
				{
					println(brn.getString("BRNAME"), 1, 1, false, "BANK",repBean);
				}
				else if(repBean.getBrnNo() == 999)
				{
					println("All Branches", 10, 1, false, "BANK",repBean);
				}
				else
				{
					println("Branch Not Found", 10, 1, false, "BANK",repBean);
				}
			}
			if(repBean.getBrnNo() == 999)
			{
				ctr = (repBean.getLineLen() - repBean.getHdg1().length()) / 2 + 1;
			}
			else
			{
				ctr = (repBean.getLineLen() - repBean.getHdg1().length()) / 2;
			}

			if(head1.length() != 0)
			{
				println(head1, ctr, 1, false, "BANK",repBean);
				//println(UtilityDAO.stringOfSize(231,'-'), 10, 1, false, "BANK",repBean);

				println(UtilityDAO.stringOfSize(repBean.getLineLen(),'-'), repBean.getLineSpace(), 1, false, "BANK",repBean); //******Remaining **************
			}
			if(head2.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head2, 10, 1, false, "BANK",repBean);

			}
			if(head3.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head3, 10, 1, false, "BANK",repBean);
			}
			if(head4.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head4, 10, 1, false, "BANK",repBean);

			}
			if(head5.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head5, 10, 1, false, "BANK",repBean);
			}
			if(head6.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head6, 10, 1, false, "BANK",repBean);
			}
			if(head7.length() != 0)
			{
				pany = true;
				ctr = (repBean.getLineLen() - head2.length()) / 2 + 1;
				println(head7, 10, 1, false, "BANK",repBean);
			}
			if(pany)
			{
				//println(String(LineLen,"-"), 0, 1, false, "BANK"); //******Remaining **************
			}
			repBean.setFoot_Fl(true);

		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}
	}

	/*public static void page_footer(String Foot1,String Foot2,boolean Continue){
		int ctr;
		Foot_Ctr = Foot_Ctr + 1;
		//println(String(LineLen,"-"), 0, 1, false, "BANK"); //******Remaining **************
		if(Foot1.length() != 0){
			println(Foot1, 1, 1, false, "BANK");
			if(Foot2.length() != 0){
				println(Foot2, 1, 1, false, "BANK");
			}
			//println(String(LineLen,"-"), 0, 1, false, "BANK"); //******Remaining **************
		}
		ctr = LineLen - 12;
		if(Continue){
			println("Continue...", ctr, 1, false, "BANK");
		}
		Foot_Fl = true;
	}*/
	
	public static float get_leave_bal(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
				//	Bal ="select top 1 TOTCR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select min(baldt) from leavebal l2 where baldt between '"+fdate+"' and '"+tdate+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select top 1 bal from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.srno=(select min(srno) from leavebal l2 where baldt between '"+fdate+"' and '"+tdate+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd ) order by BALDT,srno desc";
					
					//Bal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+p_leavecd+"  AND EMPNO="+p_empNo+"ORDER BY leavecd,baldt desc , srno DESC";	
					
				}
				else
				{
				//	Bal ="select top 1 TOTCR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select min(baldt) from leavebal l2 where baldt between '"+fdate+"' and '"+tdate+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
				// akshay adding qur for opening balance by ph sir 
				Bal = "select top 1 bal from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.srno=(select min(srno) from leavebal l2 where baldt between '"+fdate+"' and '"+tdate+"'  and l2.empno = l1.empno and l2.leavecd=l1.leavecd) order by BALDT,srno desc";
					
					//Bal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+p_leavecd+"  AND EMPNO="+p_empNo+"ORDER BY leavecd,baldt desc , srno DESC";
				}
				
				System.out.println(Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_bal=rs_tmp.getFloat("bal");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	
	
	
	// total leave Taken
	public static float get_leave_avail(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
				//	Bal ="select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.srno In (select max(srno) from leavebal l2 where baldt  < '"+ReportDAO.EOM(tdate)+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					
					Bal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+p_leavecd+"  AND EMPNO="+p_empNo+"ORDER BY leavecd,baldt desc , srno DESC";
					
				}
				else
				{
				//	Bal = "select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.srno in (select max(srno) from leavebal l2 where baldt  <  '"+ReportDAO.EOM(tdate)+"'  and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+p_leavecd+"  AND EMPNO="+p_empNo+"ORDER BY leavecd,baldt desc , srno DESC";
				}
				
				
				System.out.println("bal is @@@@@@@@@@"+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_bal=rs_tmp.getFloat("TOTDR");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	
	
	
	public static float get_leave_avail_this_month(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
					Bal ="select sum(days) as total from leavetran where EMPNO="+p_empNo+" and FRMDT between '"+ReportDAO.BOM(tdate)+"' and '"+ReportDAO.EOM(tdate)+"' and leavecd = "+p_leavecd+" and status='' ";
					
				}
				else
				{
					Bal ="select sum(days) as total from leavetran where EMPNO="+p_empNo+" and FRMDT between '"+ReportDAO.BOM(tdate)+"' and '"+ReportDAO.EOM(tdate)+"' and leavecd = "+p_leavecd+" ";
				}
				
				
				System.out.println("bal in current month"+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_bal=rs_tmp.getFloat("total");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	
	
	
	public static float get_leave_encash(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
					//Bal ="select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as encash from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd=1 and l1.LEAVEPURP ='4' and (l1.status ='ENCASHED' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";
				}
				else
				{
					//Bal = "select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'  and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as encash from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd=1 and l1.LEAVEPURP ='4' and (l1.status ='ENCASHED' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";

				}
				
			System.out.println("ak bal ==="+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					//leave_bal=rs_tmp.getFloat("TOTDR");
					
					leave_bal=rs_tmp.getFloat("encash");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	public static float get_leave_lapse(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
					//Bal ="select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as lapse from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.LEAVEPURP ='6' and (l1.status ='SANCTION' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";
				}
				else
				{
					//Bal = "select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'  and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as lapse from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.LEAVEPURP ='6' and (l1.status ='SANCTION' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";

				}
				
			System.out.println("ak bal ==="+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					//leave_bal=rs_tmp.getFloat("TOTDR");
					
					leave_bal=rs_tmp.getFloat("lapse");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	public static float get_leave_credit(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				
				
				if(OPENCLOSE == "O")
				{
					//Bal ="select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"' and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as credit from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.LEAVEPURP ='5' and (l1.status ='SANCTION' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";
				}
				else
				{
					//Bal = "select TOTDR from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.baldt=(select max(baldt) from leavebal l2 where baldt between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'  and l2.empno = l1.empno and l2.leavecd=l1.leavecd)";
					Bal ="select sum(days)  as credit from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and l1.LEAVEPURP ='5' and (l1.status ='SANCTION' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";

				}
				
			System.out.println("ak bal ==="+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					//leave_bal=rs_tmp.getFloat("TOTDR");
					
					leave_bal=rs_tmp.getFloat("credit");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}
	public static float get_leave_lwp(int p_empNo,int p_leavecd,String fdate,String tdate, String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		String leaveReson ="";
		
		if(p_leavecd ==1){
			
			leaveReson ="PL CONVERTED TO LWP";
			
		}
		
		
		else if(p_leavecd ==2){
			
			leaveReson ="SL CONVERTED TO LWP";
			
		}
		else if(p_leavecd ==3){
			
			leaveReson ="CL CONVERTED TO LWP";
			
		}
		
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(fdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					fdate = "09-Nov-2000";
				}
				
				
				
			
					Bal ="select sum(days)  as lwp from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd=7 and l1.lreason ='"+leaveReson+"' and (l1.status ='sanction' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";				
				
				System.out.println("BAl"+ Bal);
			
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_bal=rs_tmp.getFloat("lwp");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}

	
	public static float get_leave_late(int p_empNo,int p_leavecd,String fdate,String tdate, String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		String leaveReson ="";
		
		if(p_leavecd ==1){
			
			leaveReson ="LEAVE ENTRIES CONVERTED TO PL";
			
		}
		
		
		else if(p_leavecd ==2){
			
			leaveReson ="LEAVE ENTRIES CONVERTED TO SL";
			
		}
		else if(p_leavecd ==3){
			
			leaveReson ="LEAVE ENTRIES CONVERTED TO CL";
			
		}
		
		
		
		float leave_bal=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(fdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					fdate = "09-Nov-2000";
				}
				
				
				
			
					Bal ="select sum(days)  as lwp from leavetran l1 where l1.empno="+p_empNo+"  and l1.lreason ='"+leaveReson+"' and (l1.status ='sanction' or l1.status ='1') and l1.trndate between '"+ReportDAO.BOM(fdate)+"' and '"+ReportDAO.EOM(tdate)+"'";				
				
				System.out.println("BAl"+ Bal);
			
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_bal=rs_tmp.getFloat("lwp");
					
					//System.out.println("bal is "+leave_bal);
				}
				else {
					leave_bal=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_bal;
		
	}

	
	
	// sl count 
	public static float get_Sl_Count(int p_empNo,int p_leavecd,String fdate,String tdate,String OPENCLOSE,RepoartBean repBean)
	{
		ResultSet rs_tmp = null;
		String Bal="";
		float leave_COUNT=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			try
			{
				Statement st = repBean.getCn().createStatement();
				Date date1 = (Date) sdf.parse(tdate);
				Date date2 = (Date) sdf.parse("09-Nov-2000");
				int result = date1.compareTo(date2);
				if(result == -1)
				{
					tdate = "09-Nov-2000";
				}
				
				if(OPENCLOSE == "O")
				{
					
					/*Bal ="select COUNT(*) AS TOTAL from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and  baldt between '"+ReportDAO.EOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and TOTDR!=0 ";*/
					//Bal ="select COUNT(*) AS TOTAL from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and  baldt between '"+ReportDAO.BOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and TOTDR!=0 ";
					Bal ="select COUNT(*) AS TOTAL from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+" and  TRNDATE between '"+ReportDAO.BOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and l1.STATUS='SANCTION' ";
				}
				else
				{
					//System.out.println("fdateG@@"+fdate);
					/*Bal ="select COUNT(*) AS TOTAL from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+"  and  baldt between '"+ReportDAO.EOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and TOTDR!=0 ";*/
					//Bal ="select COUNT(*) AS TOTAL from leavebal l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+"  and  baldt between '"+ReportDAO.BOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and TOTDR!=0 ";
					Bal ="select COUNT(*) AS TOTAL from leavetran l1 where l1.empno="+p_empNo+" and l1.leavecd="+p_leavecd+"  and TRNDATE between '"+ReportDAO.BOM(fdate)+"'and '"+ReportDAO.EOM(tdate)+"' and l1.STATUS='SANCTION' ";
				}
				System.out.println("bal is @@@@@@@@@@"+Bal);
				rs_tmp = st.executeQuery(Bal);
				
				if(rs_tmp.next())
				{
					leave_COUNT=rs_tmp.getFloat("TOTAL");
				}
				else {
					leave_COUNT=0;
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return leave_COUNT;
		
	}
	
	
	
	
	
	
	
	
	
	public static String getWishMessage(String type,Connection cn)
	{
		String message="";
		ResultSet rs;
		Statement st;
		try
		{
			st=cn.createStatement();
			rs = st.executeQuery("SELECT TOP 1 message FROM wishmessage where types='"+type+"' ORDER BY NEWID()");
		if(rs.next())
			message=rs.getString("message");
		
		
		}
		catch(Exception e){e.printStackTrace();
		}
		return message;
		
	}
	
	
	public static ArrayList<GraphBean> getProjectwiseEmployee()
	{
		ArrayList<GraphBean> grlist = new ArrayList<GraphBean>();
		GraphBean gb;
		Connection conn;
		ResultSet rs;
		Statement st;
		try
		{
			conn=ConnectionManager.getConnection();
			st=conn.createStatement();
			//String query= "select count(e.empno) as totalemp ,p.prj_code from empmast e,emptran t,prj p where e.empno=t.empno and p.prj_srno=t.prj_srno and e.dol is null group by p.prj_code order by count(e.empno) asc";
			String query= "select count(e.empno) as totalemp ,t.prj_code from empmast e,emptran t where e.empno=t.empno and e.dol is null group by t.prj_code order by t.prj_code desc";// count(e.empno) asc";
			rs=st.executeQuery(query);
			
			while(rs.next())
			{
				gb=new GraphBean();
				gb.setTotalemp(rs.getString(1));
				gb.setPrj_Code(rs.getString(2));
				grlist.add(gb);
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}
		
		return grlist; 
	}

public static ArrayList<GraphBean> getDesignationwiseEmployee()
{

	ArrayList<GraphBean> grlist = new ArrayList<GraphBean>();
	GraphBean gb;
	Connection conn;
	ResultSet rs;
	Statement st;
	try
	{
		conn=ConnectionManager.getConnection();
		st=conn.createStatement();
		//String query= "select count(e.empno) as totalemp ,t.desig from empmast e,emptran t where e.empno=t.empno and t.desig=(select desig from emptran  where empno=e.empno) and e.dol is null group by t.desig order by count(e.empno) asc ";
		String query= "select count(e.empno) as totalemp ,t.desig from empmast e,emptran t where e.empno=t.empno and t.desig=(select desig from emptran  where empno=e.empno and SRNO = (select MAX(SRNO)from EMPTRAN where EMPNO = e.EMPNO and status=1))  and t.srno =(select MAX(SRNO) from EMPTRAN where EMPNO=E.EMPNO) and e.dol is null  group by t.desig order by count(e.empno) asc ";
		///System.out.println(query);
		rs=st.executeQuery(query);
	
	    LookupHandler lkhp=new LookupHandler();
		while(rs.next())
		{
			gb = new GraphBean();
			gb.setTotalemp(rs.getString(1));
			gb.setDesignation(lkhp.getLKP_Desc("desig",rs.getInt(2)) );
		
			grlist.add(gb);
		}
		conn.close();
	}catch(Exception e){e.printStackTrace();}
	
	return grlist; 

}

//------ Method to get Date after adding month in the date in DD-MON-YYYY format---------
	public static String addMonthINDATE(String date,int month)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
  	String date1="";
  	java.util.Date ddd0=new java.util.Date(date); 
  	Calendar cal = Calendar.getInstance();
	    cal.setTime(ddd0);
	    cal.add(Calendar.MONTH, month);
	    java.util.Date ddd1=cal.getTime();
	    date1=format.format(ddd1);
	    return date1;
	}

	
	public static String getServerDate() {

		Connection conn = ConnectionManager.getConnection();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String today = "";
		try {
			conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			String s = "select getdate() as date";
			ResultSet rslt = st.executeQuery(s);
			if (rslt.next()) {
				today = sdf.format(rslt.getDate("date"));
			}

			conn.close();
		}

		catch (Exception e) {
			today=ReportDAO.getSysDate();
			e.printStackTrace();
		}
		return today;
	}	
	
	 public static String BOYForJanToDec(String date){
		 String year=date.substring(7,11);
		   Calendar cal = Calendar.getInstance();
		   cal.set(Calendar.YEAR, Integer.parseInt(year));
		   cal.set(Calendar.DAY_OF_YEAR, 1);    
		   Date start = cal.getTime();
		   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
		   String BOY=simpleDateFormat.format(start);
		return BOY;  //01-Jan-YEAR
		 
	 }
	 public static String BOYForJanToDec1(String date){
		 String year=date.substring(7,11);
		   Calendar cal = Calendar.getInstance();
		   cal.set(Calendar.YEAR, Integer.parseInt(year));
		   cal.set(Calendar.DAY_OF_YEAR, 1);    
		   Date start = cal.getTime();
		   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		   String BOY=simpleDateFormat.format(start);
		return BOY; 
		 
	 }

	 public static String EOYForJanToDec(String date){
		 System.out.println("Dste : "+date);
	   String year=date.substring(7,11);
	   System.out.println("year : "+year);
	   Calendar cal = Calendar.getInstance();
	   cal.set(Calendar.YEAR, Integer.parseInt(year));

	   cal.set(Calendar.MONTH, 11); // 11 = december
	   cal.set(Calendar.DAY_OF_MONTH, 31);    
	   Date start = cal.getTime();
	   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
	   String EOY=simpleDateFormat.format(start);
	return EOY; //31-Dec-YEAR

	 }
	
	
	
	 
	 
	 public static int getTOTAL(int code,int site_id,String date)
		{
			//SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
			if(site_id==998)
			{
				site_id=80;// here is the last siteid becoz there is no site like 998 so we have to add last site_id
			}
			Connection con = ConnectionManager.getConnection();
			int total=0;
			
			try 
			{
				Statement st = con.createStatement();
				
				String query="SELECT SUM(E.net_amt)as total ,t.PRJ_SRNO " +
						"FROM paytran E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.SRNO = (SELECT MAX(SRNO) " +
						"FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND  e.trncd="+code+"   and t.prj_srno="+site_id+"  "  +
						" and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' )   group by t.PRJ_SRNO";
				
				query = query +" union SELECT SUM(E.net_amt)as total ,t.PRJ_SRNO " +
								"FROM paytran_stage E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.SRNO = (SELECT MAX(SRNO) " +
								"FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND  e.trncd="+code+"   and t.prj_srno="+site_id+"  "  +
								" and E.empno in (select Distinct empno from paytran_stage where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' )   group by t.PRJ_SRNO";
						
				
				
				System.out.println(query);
				
				ResultSet rs1 = st.executeQuery(query);
				
				while(rs1.next())
				{
					total= rs1.getInt("total");
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return total;
		}
	public static String getMonthinString(int premonth) {
		String month="";
		if(premonth==0)
		{
			month= "JAN";
		}
		else if(premonth==1)
		{
			month="FEB";
		}
		else if(premonth==2)
		{
			month="MAR";
		}
		else if(premonth==3)
		{
			month="APR";
		}
		else if(premonth==4)
		{
			month="MAY";
		}
		else if(premonth==5)
		{
			month="JUN";
		}
		else if(premonth==6)
		{
			month="JUL";
		}
		else if(premonth==7)
		{
			month="AUG";
		}
		else if(premonth==8)
		{
			month="SEP";
		}
		else if(premonth==9)
		{
			month="OCT";
		}
		else if(premonth==10)
		{
			month="NOV";
		}
		else if(premonth==11)
		{
			month="DEC";
		}
		
		return month;
	}
	
	 
	/*public static int getNoOfDays(String  ForDate) {
		int day=0;
		String [] date=ForDate.split("-");
		String month=date[1];
		int year=Integer.parseInt(date[2]);
		if(month.equalsIgnoreCase("JAN"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("FEB"))
		{
				if(year%4==0)
				{
				day = 29;
				}
				else
				{
				day = 28;
				}
		}
		else if(month.equalsIgnoreCase("MAR"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("APR"))
		{
			day = 30 ;
		}
		else if(month.equalsIgnoreCase("MAY"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("JUN"))
		{
			day = 30;
		}
		else if(month.equalsIgnoreCase("JUL"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("AUG"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("SEP"))
		{
			day = 30;
		}
		else if(month.equalsIgnoreCase("OCT"))
		{
			day = 31;
		}
		else if(month.equalsIgnoreCase("NOV"))
		{
			day = 30;
		}
		else if(month.equalsIgnoreCase("DEC"))
		{
			day = 31;
		}
		
		return day;
	}*/
	public static String differenceBetweenDates(Date fromDate, Date toDate) {
		
		
		
		 long days = 0,Months = 0,years = 0;
		 String  DIFF="";
       /* try
        {
             long diffInMilliSec = fromDate.getTime() - toDate.getTime();
             days = (diffInMilliSec / (1000 * 60 * 60 * 24)) % 7;
             years =  (diffInMilliSec / (1000l * 60 * 60 * 24 * 365));
             Months = diffInMilliSec / (24 * 60 * 60 * 12 * 1000)% 365;
          //   Months = (diffInMilliSec / (1000 * 60 * 60 * 24)) % 12; 365.24 * 24 * 60 * 60 * 1000 / 12;
             
            DIFF = years+"-"+Months+"-"+days;
            System.out.println("DIFF:  "+DIFF);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        
        Calendar fromDate1=Calendar.getInstance();
        Calendar toDate1=Calendar.getInstance();
        fromDate1.setTime(toDate);
        toDate1.setTime(fromDate);
        int increment = 0;
        int year,month,day;
        System.out.println(fromDate1.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (fromDate1.get(Calendar.DAY_OF_MONTH) > toDate1.get(Calendar.DAY_OF_MONTH)) {
            increment =fromDate1.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
         System.out.println("increment"+increment);
// DAY CALCULATION
        if (increment != 0) {
            day = (toDate1.get(Calendar.DAY_OF_MONTH) + increment) - fromDate1.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate1.get(Calendar.DAY_OF_MONTH) - fromDate1.get(Calendar.DAY_OF_MONTH);
        }

// MONTH CALCULATION
        if ((fromDate1.get(Calendar.MONTH) + increment) > toDate1.get(Calendar.MONTH)) {
            month = (toDate1.get(Calendar.MONTH) + 12) - (fromDate1.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate1.get(Calendar.MONTH)) - (fromDate1.get(Calendar.MONTH) + increment);
            increment = 0;
        }

// YEAR CALCULATION
        year = toDate1.get(Calendar.YEAR) - (fromDate1.get(Calendar.YEAR) + increment);
//     return   year+"\tYears\t\t"+month+"\tMonths\t\t"+day+"\tDays";
        System.out.println(" yearsss  "+year+"    monthsss    "+month+"       daysssss:  "+day);
        DIFF = year+"-"+month+"-"+day;
        
        
       /* Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(fromDate);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(toDate);*/

       /* int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);
        System.out.println("AAAAA:  "+diffYear+"  year    "+diffMonth+" diffMonth");*/
        
        
	    return DIFF;
	}
	
	
	public static String BOMMYSQL(String date)
    {  
		
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date d = null;  
        
		try {
			d = sdf1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 
		 
		 Calendar calendar = Calendar.getInstance();  
        calendar.setTime(d);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        java.util.Date dddd = calendar.getTime();  
        java.sql.Date sqlDate = new java.sql.Date(dddd.getTime());
        // return sdf1.format(dddd);
         return sqlDate.toString();
    }
	
	 public static String EOMMYSQL(String date)   
     {  
     	
    	 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d = null;
         try
         {
			d = sdf1.parse(date);
		 }
         catch (ParseException e) 
         {
			e.printStackTrace();
		 }
         Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
         java.util.Date dddd = calendar.getTime();  
         java.sql.Date sqlDate = new java.sql.Date(dddd.getTime());
        // return sdf1.format(dddd);
         return sqlDate.toString();
     }
	 
	 //new added G
	 public static String BOM1(String date)
     {  
		
      //   SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
         SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
         java.util.Date d = null;  
         
		try {
			d = sdf1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 Calendar calendar = Calendar.getInstance();  
         calendar.setTime(d);  
         calendar.set(Calendar.DAY_OF_MONTH, 1);  
         java.util.Date dddd = calendar.getTime();  
         return sdf1.format(dddd);  
     }  
	
	 
	 public static String getPreMonth(String premonth) {
			String month="";
			if(premonth.equalsIgnoreCase("JAN"))
			{
				month= "DEC";
			}
			else if(premonth.equalsIgnoreCase("FEB"))
			{
				month="JAN";
			}
			else if(premonth.equalsIgnoreCase("MAR"))
			{
				month="FEB";
			}
			else if(premonth.equalsIgnoreCase("APR"))
			{
				month="MAR";
			}
			else if(premonth.equalsIgnoreCase("MAY"))
			{
				month="APR";
			}
			else if(premonth.equalsIgnoreCase("JUN"))
			{
				month="MAY";
			}
			else if(premonth.equalsIgnoreCase("JUL"))
			{
				month="JUN";
			}
			else if(premonth.equalsIgnoreCase("AUG"))
			{
				month="JUL";
			}
			else if(premonth.equalsIgnoreCase("SEP"))
			{
				month="AUG";
			}
			else if(premonth.equalsIgnoreCase("OCT"))
			{
				month="SEP";
			}
			else if(premonth.equalsIgnoreCase("NOV"))
			{
				month="OCT";
			}
			else if(premonth.equalsIgnoreCase("DEC"))
			{
				month="NOV";
			}
			
			return month;
		}
	 
	 
		public static String getMonthinString1(String premonth) {
			String month="";
			if(premonth.equalsIgnoreCase("01"))
			{
				month= "JAN";
			}
			else if(premonth.equalsIgnoreCase("02"))
			{
				month="FEB";
			}
			else if(premonth.equalsIgnoreCase("03"))
			{
				month="MAR";
			}
			else if(premonth.equalsIgnoreCase("04"))
			{
				month="APR";
			}
			else if(premonth.equalsIgnoreCase("05"))
			{
				month="MAY";
			}
			else if(premonth.equalsIgnoreCase("06"))
			{
				month="JUN";
			}
			else if(premonth.equalsIgnoreCase("07"))
			{
				month="JUL";
			}
			else if(premonth.equalsIgnoreCase("08"))
			{
				month="AUG";
			}
			else if(premonth.equalsIgnoreCase("09"))
			{
				month="SEP";
			}
			else if(premonth.equalsIgnoreCase("10"))
			{
				month="OCT";
			}
			else if(premonth.equalsIgnoreCase("11"))
			{
				month="NOV";
			}
			else if(premonth.equalsIgnoreCase("12"))
			{
				month="DEC";
			}
			
			return month;
		}

}


