package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.Lookup;

public class LookupHandler 
{
	public Lookup getLookup(String desc)
	{
		Lookup lkp=new Lookup();
		String[] CoSr=desc.split("-");		//CoSr means Code SR number
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement stmt2=con.createStatement();
			ResultSet rs=stmt2.executeQuery("SELECT * FROM LOOKUP WHERE LKP_CODE='"+CoSr[0]+"' AND LKP_SRNO="+Integer.parseInt(CoSr[1])+" order by 3 asc");
			while(rs.next())
			{
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
			//	lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return lkp;
	}
	
	public int getMaxSrno(String code)
	{
		int max=0;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT max(LKP_SRNO) FROM LOOKUP WHERE LKP_CODE='"+code+"'");
			while(rs.next())
			{
				max=rs.getString(1)!=null?rs.getInt(1):0;
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return max;
	}
	
	public ArrayList<Lookup> getMainLookup()
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_SRNO=0 ORDER BY 3");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Lookup> getSubLKP_DESC(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT  * FROM LOOKUP WHERE LKP_CODE='"+key+"' AND LKP_SRNO!=0 order by 2 asc");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
			//	lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	public ArrayList<Lookup> getSubNewLKP_DESC(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT  * FROM LOOKUP WHERE LKP_CODE='"+key+"' order by 2 asc");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
			//	lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getLKP_Code(String desc)
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT LKP_CODE FROM LOOKUP WHERE LKP_DISC='"+desc+"'");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public String getLKP_Description(String code)
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT LKP_DISC FROM LOOKUP WHERE LKP_CODE='"+code+"' AND LKP_SRNO=0 ");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public String getLKP_Desc(String code,int srno)//for getting string directly specially  for grid view
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT LKP_DISC FROM LOOKUP WHERE LKP_CODE='"+code+"' AND LKP_SRNO="+srno+" ");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<Lookup> getCDTYPELookup()
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_CODE='CDTYPE' AND LKP_SRNO!=0 order by LKP_SRNO");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean addNewLooup(Lookup lkp) 
	{
		boolean result=false;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			System.out.println("INSERT INTO LOOKUP VALUES('"+lkp.getLKP_CODE()+"',"+lkp.getLKP_SRNO()+",'"+lkp.getLKP_DESC()+"', "+lkp.getLKP_RECR()+")");
			//st.execute("INSERT INTO LOOKUP VALUES('"+lkp.getLKP_CODE()+"',"+lkp.getLKP_SRNO()+",'"+lkp.getLKP_DESC()+"',0)");
			
			System.out.println(lkp.getLKP_RECR());
		//	int i = Integer.parseInt(lkp.getLKP_RECR());

			
			
//			st.execute("INSERT INTO LOOKUP VALUES('"+lkp.getLKP_CODE()+"',"+lkp.getLKP_SRNO()+",'"+lkp.getLKP_DESC()+"', "+lkp.getLKP_RECR()+")");
			st.execute("INSERT INTO LOOKUP VALUES('"+lkp.getLKP_CODE()+"',"+lkp.getLKP_SRNO()+",'"+lkp.getLKP_DESC()+"', "+lkp.getLKP_RECR()+")");
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateLooup(Lookup lkp) 
	{
		boolean result=false;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			System.out.println("UPDATE LOOKUP SET LKP_DISC='"+lkp.getLKP_DESC()+"'  WHERE LKP_CODE='"+lkp.getLKP_CODE()+"' AND LKP_SRNO="+lkp.getLKP_SRNO()+"");
			//st.execute("UPDATE LOOKUP SET LKP_DISC='"+lkp.getLKP_DESC()+"', LKP_RECR="+lkp.getLKP_RECR()+"  WHERE LKP_CODE='"+lkp.getLKP_CODE()+"' AND LKP_SRNO="+lkp.getLKP_SRNO()+"");
			st.execute("UPDATE LOOKUP SET LKP_DISC='"+lkp.getLKP_DESC()+"'  WHERE LKP_CODE='"+lkp.getLKP_CODE()+"' AND LKP_SRNO="+lkp.getLKP_SRNO()+"");
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public int getSRNO(String desc)
	{
		int srNo = 0;
		String[] CoSr=desc.split("-");
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT LKP_SRNO FROM LOOKUP WHERE LKP_CODE='"+CoSr[0]+"' AND LKP_DISC='"+CoSr[1]+"'");
			while(rs.next())
			{
				srNo=rs.getString("LKP_SRNO")!=null?rs.getInt("LKP_SRNO"):0;
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return srNo;
	}
	
	public ArrayList<Lookup> getSubLKP_DESC2(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		String[] CoSr=key.split("-");	//CoSr means Code SR number
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_CODE='"+CoSr[0]+"' AND LKP_RECR='"+CoSr[1]+"'");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				//lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Lookup> getMainSubLookup()
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
//			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_SRNO <> 0 ORDER BY LKP_DISC");
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_SRNO <> 0 and LKP_CODE = 'GD' " +
															"or LKP_SRNO <> 0 and LKP_CODE = 'STATE' ORDER BY LKP_CODE");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				//lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public Lookup getSubLKP_DESCOFF(String key)
	{
		Lookup lkp=new Lookup();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_CODE='"+key+"' AND LKP_SRNO!=0 order by 2 asc");
			while(rs.next())
			{
				
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
			//	lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return lkp;
	}
	
	public String Designation(int code)//for getting string directly specially  for grid view
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(" SELECT LKP_DISC FROM LOOKUP where lkp_srno="+code+" and lkp_code= 'Desig' ");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public String getCode_Desc(String code)//for getting string directly specially  for grid view
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT DISC FROM CDMAST WHERE TRNCD= "+code+" ");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String  getprojectName(int PrjSrno)//param for getting projcet code list
	{
		
	
		Connection con=null;
		ResultSet rs = null;
		
		String SiteNme="";

		//String query="SELECT * FROM Prj";
		//String query="SELECT * FROM Project_Sites order by Site_Name";
	//	String query="select rtrim(project_code)+'  '+rtrim(Site_City) as site_Add,* from Project_Sites order by Project_Code";
		String query="select Site_Name from Project_Sites where Site_isdeleted = 0  and site_id="+PrjSrno+"";
		con = ConnectionManager.getConnectionTech();

		
		try
		{
			Statement st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				
			SiteNme=rs.getString("Site_Name");
			}
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return SiteNme;
	}
	public String getLKP_Dept(String code,String srno)
	{
		System.out.println("i am in getLKP_Dept:: "+code+"  srno:: "+srno);
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT LKP_DISC FROM LOOKUP WHERE LKP_CODE='"+code+"' AND LKP_SRNO="+srno+" ");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<Lookup> getCategoryList(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from LOOKUP where LKP_CODE = 'Cate'");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Lookup> getSubLKP_DESCREASON(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT  * FROM LOOKUP WHERE LKP_CODE='"+key+"' AND LKP_SRNO!=0 order by 2 asc");
			System.out.println("SELECT  * FROM LOOKUP WHERE LKP_CODE='"+key+"' AND LKP_SRNO!=0 order by 2 asc");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public String getLookUp_desc(int code)
	{
		String result=new String();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(" SELECT LKP_DISC FROM LOOKUP where lkp_srno="+code+" and lkp_code= 'LFT_PURP' AND LKP_SRNO!=0");
			while(rs.next())
			{
				result=rs.getString(1)!=null?rs.getString(1):"";
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}