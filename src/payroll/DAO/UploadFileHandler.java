package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import payroll.Model.UploadFilesBean;

public class UploadFileHandler {
	Connection con=null;

public ArrayList<UploadFilesBean> showAllFiles(){
	Statement st = null;
	ResultSet rs = null;
	ArrayList<UploadFilesBean> uploadfilelist = new ArrayList<UploadFilesBean>();
	try
	{
		con = ConnectionManager.getConnection();
		st = con.createStatement();
		UploadFilesBean filebean;
		rs=st.executeQuery("select * from SiteWise_File_Upload order by PRJ_SRNO,SRNO");
		while(rs.next()){
			filebean = new UploadFilesBean();
			filebean.setPRJ_SRNO(rs.getInt("PRJ_SRNO"));
			filebean.setFILENAME(rs.getString("FILENAME")==null?"--":rs.getString("FILENAME"));
			filebean.setATTACHPATH(rs.getString("ATTACHPATH")==null?"--":rs.getString("ATTACHPATH"));
			filebean.setSRNO(rs.getInt("SRNO"));
			filebean.setUPDDT(rs.getString("UPDDT")==null?"--":rs.getString("UPDDT"));
			filebean.setUSRCODE(rs.getString("USRCODE")==null?"--":rs.getString("USRCODE"));
			filebean.setEMPNO(rs.getInt("EMPNO"));
			uploadfilelist.add(filebean);
		}
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
		return uploadfilelist;
}

public ArrayList<UploadFilesBean> showAllFiles_Project(int prj_srno){
	Statement st = null;
	ResultSet rs = null;
	ArrayList<UploadFilesBean> uploadfilelist = new ArrayList<UploadFilesBean>();
	try
	{
		con = ConnectionManager.getConnection();
		st = con.createStatement();
		UploadFilesBean filebean;
		rs=st.executeQuery("select * from SiteWise_File_Upload where PRJ_SRNO ="+prj_srno+" order by PRJ_SRNO,SRNO");
		while(rs.next()){
			filebean = new UploadFilesBean();
			filebean.setPRJ_SRNO(rs.getInt("PRJ_SRNO"));
			filebean.setFILENAME(rs.getString("FILENAME")==null?"--":rs.getString("FILENAME"));
			filebean.setATTACHPATH(rs.getString("ATTACHPATH")==null?"--":rs.getString("ATTACHPATH"));
			filebean.setSRNO(rs.getInt("SRNO"));
			filebean.setUPDDT(rs.getString("UPDDT")==null?"--":rs.getString("UPDDT"));
			filebean.setUSRCODE(rs.getString("USRCODE")==null?"--":rs.getString("USRCODE"));
			filebean.setEMPNO(rs.getInt("EMPNO"));
			uploadfilelist.add(filebean);
		}
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return uploadfilelist;
}

}
