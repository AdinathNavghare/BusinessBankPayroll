package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import payroll.Core.Calculate;
import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Model.AutopostBean;
import payroll.Model.HoPostBean;

public class PostingHandler
{
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	ErrorLog el=new ErrorLog();
	public ArrayList<HoPostBean> gethopostlist()
	{
		ArrayList<HoPostBean> hplist = new ArrayList<HoPostBean>();
		HoPostBean hpb ;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from HOPOST order by BRCD,TRNCD"); 
			while(rs.next())
			{
				hpb = new HoPostBean();
				hpb.setBRCD(rs.getString("BRCD")!=null?rs.getInt("BRCD"):0);
				hpb.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				hpb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				hpb.setSUBSYS_CD(rs.getString("SUBSYS_CD")!=null?rs.getString("SUBSYS_CD"):"");
				hpb.setAC_NO(rs.getString("AC_NO")!=null?rs.getInt("AC_NO"):0);
				hpb.setAMOUNT(rs.getString("AMOUNT")!=null?rs.getInt("AMOUNT"):0);
				hpb.setVOUC_TYPE(rs.getString("VOUC_TYPE")!=null?rs.getString("VOUC_TYPE"):"");
				hplist.add(hpb);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			el.errorLog("PostingHandler.java _ gethopostlist()", e.toString());
		}
		return hplist;
	}
	
	public ArrayList<AutopostBean> getAutoPostList(int branch)
	{
		ArrayList<AutopostBean> list = new ArrayList<AutopostBean>();
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String sql=null;
			if(branch==0)
			{
				sql = "SELECT * FROM AUTOPOST ORDER BY 1,2,3,4";
			}
			else
			{
				sql = "SELECT * FROM AUTOPOST WHERE BRCD = "+branch+" ORDER BY 1,2,3,4";
			}
			ResultSet rs = st.executeQuery(sql);
			AutopostBean APB = null;
			while(rs.next())
			{
				APB = new AutopostBean();
				
				APB.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				APB.setBRCD(rs.getString("BRCD")!=null?rs.getInt("BRCD"):0);
				APB.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				APB.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				APB.setSUBSYS_CD(rs.getString("SUBSYS_CD")!=null?rs.getString("SUBSYS_CD"):"");
				APB.setAC_NO(rs.getString("AC_NO")!=null?rs.getInt("AC_NO"):0);
				APB.setAMOUNT(rs.getString("AMOUNT")!=null?rs.getInt("AMOUNT"):0);
				APB.setVOUC_TYPE(rs.getString("VOUC_TYPE")!=null?rs.getString("VOUC_TYPE"):"");
				APB.setINST_NO(rs.getString("INST_NO")!=null?rs.getInt("INST_NO"):0);
				list.add(APB);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			el.errorLog("PostingHandler.java _ getAutopostlist()", e.toString());
		}
		return list;
	}
	
	public int[] getBranches()
	{
		int[] list = null;
		Connection con = null;
		int count = 0;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT DISTINCT(BRCD) FROM AUTOPOST ORDER BY BRCD");
			if(rs.next())
			{
				rs.last();
				count = rs.getRow();
				rs.beforeFirst();
				list = new int[count];
				int i =0;
				while(rs.next())
				{
					list[i] = rs.getString(1)!=null?rs.getInt(1):0;
					i++;
				}
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			el.errorLog("PostingHandler.java _ getBranches()", e.toString());
		}
		return list;
	}
	
	
public boolean vda_post()
{
	boolean flag=false;
	Connection con = null;
	int count=1;
	try
	{
		con = ConnectionManager.getConnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery("SELECT DISTINCT(EMPNO),TRNDT  FROM PAYTRAN WHERE TRNCD=101 ORDER BY EMPNO");
		while(rs.next())
		{
			
			
			ResultSet rs1 = st1.executeQuery("SELECT TOP(1) * FROM VDATRAN WHERE EMPNO="+rs.getInt("EMPNO")+" ORDER BY TRNDT DESC");
			
			while(rs1.next())
			{
			st2.execute("UPDATE PAYTRAN SET INP_AMT="+rs1.getDouble("VDA")+" WHERE TRNCD=138 AND EMPNO="+rs.getInt("EMPNO"));
			System.out.println("UPDATE PAYTRAN SET INP_AMT="+rs1.getDouble("VDA")+" WHERE TRNCD=138 AND EMPNO="+rs.getInt("EMPNO")+"");
			System.out.println("count : "+count++);
			}
			
			
		}
			
		con.commit();
		flag=true;
		con.close();
		
	}
	catch(Exception e)
	{
		flag=false;
		e.printStackTrace();
		el.errorLog("PostingHandler.java _ VDA_post()", e.toString());
		
	}
	
	
	return flag;
}	


public boolean vda_Diff_Post(String from_date,String to_date)
{
	boolean flag=false;
	Connection con = null;
	String upper_frm_date=from_date;
	String upper_to_date=to_date;
	String temp_frm="";
	String current_paytran_date="";
	try
	{
		con = ConnectionManager.getConnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st5 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement st6 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		

		ResultSet rs = st.executeQuery("SELECT DISTINCT(EMPNO),TRNDT FROM PAYTRAN WHERE TRNCD=199 ORDER BY EMPNO");
		while(rs.next())
		{

			current_paytran_date=sdf.format(rs.getDate("TRNDT"));
			
			boolean f=true;
		while(f)	
		{
			
		ResultSet rs4=st4.executeQuery("select case when  Convert(nvarchar,dateadd(month,1,'"+from_date+"'),23)  <=   Convert(nvarchar,dateadd(month,1,'"+to_date+"'),23) then 1 else 0 end");
		if (rs4.next())
		{
			
			
			
		if(rs4.getInt(1)==1)
		{
			//System.out.println(" ----------into date chk true frm<= to ");
			double vda=0;
			float absent=0;
			float wrkdays=Calculate.getDays(from_date);
			float pdays=0;
			ResultSet rs2=st3.executeQuery("select trncd,trndt ,net_amt  from PAYTRAN_STAGE where" +
										   " EMPNO="+rs.getInt("EMPNO")+" and TRNCD in (138,301) and " +
										   " TRNDT between '"+ReportDAO.BOM(from_date)+"' and '"+ReportDAO.EOM(from_date)+"' " +
										   "  order by TRNDT,trncd");
			while(rs2.next())
			{
				
				if(rs2.getInt("trncd")==138)
				{
					vda=rs2.getDouble("NET_AMT");
				}
				else if(rs2.getInt("trncd")==301)
				{
					absent=rs2.getFloat("NET_AMT");
				}
				
			}
			
			//System.out.println("VDA======"+vda);
			//System.out.println("absent==="+absent);
			
			pdays=wrkdays-absent;
			
			if(pdays!=0)
			{
			
			ResultSet rs1 = st1.executeQuery("SELECT TOP(1) * FROM VDATRAN WHERE EMPNO="+rs.getInt("EMPNO")+" ORDER BY TRNDT DESC");
			
			while(rs1.next())
			{
				
			double temp_amt=	rs1.getDouble("VDA") *pdays/wrkdays;
				
			//System.out.println("---given vda="+	vda);
			//System.out.println("---PAID_DAYS="+	pdays);
			//System.out.println("---EXACT_VDA="+	temp_amt);
			//System.out.println("---DIFF     ="+	(temp_amt-vda));			
			//System.out.println("---temp_vad amt="+	temp_amt);
			
			
			st2.executeUpdate("IF EXISTS (SELECT * FROM Vda_Difference where empno=" +rs.getInt("EMPNO")+" and " +
								" MONTH_OF between '"+ReportDAO.BOM(from_date)+"' and '"+ReportDAO.EOM(from_date)+"' ) " +
								" update Vda_Difference SET  GIVEN_VDA="+vda+" , PAID_DAYS="+pdays+", EXACT_VDA="+temp_amt+" " +
								",VDA_DIFF="+(temp_amt-vda)+", CALCULATED_MONTH='"+current_paytran_date+"'  where empno=" +rs.getInt("EMPNO")+" and " +
								" MONTH_OF between '"+ReportDAO.BOM(from_date)+"' and '"+ReportDAO.EOM(from_date)+"'"+
								
								"  ELSE  INSERT INTO Vda_Difference ([EMPNO],[MONTH_OF],[GIVEN_VDA],[PAID_DAYS],[EXACT_VDA] ,[VDA_DIFF] ,[CALCULATED_MONTH])" +
								"	 values("+rs.getInt("EMPNO")+" ,'"+from_date+"', "+vda+" ,"+pdays+","+temp_amt+","+(temp_amt-vda)+",'"+current_paytran_date+"')" );
			
			
			//System.out.println("AFTER EXCECUTE..........");
			
			
			}
			}
			else
			{
				System.out.println("-----------------------PRESENT DAYS for "+from_date+" = "+pdays);
			}
		
			
			
			vda=0;
			absent=0;	
			
		}
		else
		{
			
			System.out.println("setting flaseeeeeeeeeeeeeeeeeeeeeeee");
			f=false;
		}
		System.out.println(" select Convert(nvarchar,dateadd(month,1,'"+from_date+"'),23) ");
		ResultSet rs5=st5.executeQuery(" select Convert(nvarchar,dateadd(month,1,'"+from_date+"'),23) ");
		while(rs5.next())
		{
			temp_frm=sdf.format(rs5.getDate(1));
		}
		
		from_date=temp_frm;
		System.out.println("INCREMENTED DATE=           "+from_date);//remain date check
		
		
		}
		else
		{
			
			System.out.println("setting flaseeeeeeeeeeeeeeeeeeeeeeee");
			f=false;
		}
		
		
		
		}
		//st6.executeUpdate
		//System.out.println("-----------------------------//////////////--------------------------------------------------");
		st6.executeUpdate("IF EXISTS (SELECT * FROM PAYTRAN WHERE EMPNO="+rs.getInt("EMPNO")+" AND TRNCD=142)" +
				" UPDATE PAYTRAN SET " +
				"INP_AMT= ( select  (select isnull((select sum(VDA_DIFF) from Vda_Difference where empno="+rs.getInt("EMPNO")+" and month_of between '"+ReportDAO.BOM(upper_frm_date)+"' and '"+ReportDAO.EOM(upper_to_date)+"'),0)) " +
						" from paytran WHERE TRNCD=142 AND EMPNO="+rs.getInt("EMPNO")+")  WHERE TRNCD=142 AND EMPNO="+rs.getInt("EMPNO")+" "+
				" ELSE INSERT INTO PAYTRAN(trndt,empno,trncd,srno,inp_amt,cal_amt,adj_amt,arr_amt,net_amt,cf_sw,status)" +
				" values('"+current_paytran_date+"',"+rs.getInt("EMPNO")+",142,0," +
				"( select  (select isnull((select sum(VDA_DIFF) from Vda_Difference where empno="+rs.getInt("EMPNO")+" and month_of between '"+ReportDAO.BOM(upper_frm_date)+"' and '"+ReportDAO.EOM(upper_to_date)+"'),0)) " +
						" from paytran WHERE TRNCD=142 AND EMPNO="+rs.getInt("EMPNO")+"),0,0,0,0,'*','A')");

		
		}
		
		
		
		
		
		
		
		
		
		
		con.commit();
		flag=true;
		con.close();
		
	}
	catch(Exception e)
	{
		flag=false;
		e.printStackTrace();
		el.errorLog("PostingHandler.java _ VDA_Diff_Post()", e.toString());
	}
	
	
	return flag;
}




}
