package payroll.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.Model.AddProjectBean;

/**
 * Servlet implementation class EditProjectDAO
 */
@WebServlet("/EditProjectDAO")
public class EditProjectDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProjectDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		String action=request.getParameter("action");
		System.out.print(action);
		
		ResultSet rs=null;
		PreparedStatement pst=null;
		AddProjectBean trbn = null;
		
		try
		{
			
			if(action.equalsIgnoreCase("EditProjectDetailes"))
			{
				System.out.println("if Statement...");
				Connection conn = ConnectionManager.getConnection();
				String sql="insert into AddProject(PType,Pstatus,ProjectName,LiveUrl,TestUrl,ReviewDate,DesignDate,DevelopDate,SiteTestDate,UATDate,ProDescription) values(?,?,?,?,?,?,?,?,?,?,?);"; 
				String pro_type=request.getParameter("typeselect");
				String pro_status=request.getParameter("status");
				String pro_name=request.getParameter("projectName");
				String pro_lurl=request.getParameter("LUrl");
				String pro_turl=request.getParameter("TUrl");
				String pro_reviewDate=request.getParameter("review");
				String pro_design=request.getParameter("design");
				String pro_develope=request.getParameter("develop");
				String pro_sitetest=request.getParameter("sitetest");
				String pro_uat=request.getParameter("uat");
				String pro_description=request.getParameter("post-text");
				pst=conn.prepareStatement(sql);
				pst.setString(1, pro_type);
				pst.setString(2, pro_status);
				pst.setString(3, pro_name);
				pst.setString(4, pro_lurl);
				pst.setString(5, pro_turl);
				pst.setString(6, pro_reviewDate);
				pst.setString(7, pro_design);
				pst.setString(8, pro_develope);
				pst.setString(9, pro_sitetest);
				pst.setString(10, pro_uat);
				pst.setString(11, pro_description);
				int result=pst.executeUpdate();
				if(result>0)
				{
					System.out.println("sucessfully inserted...");
				}
				
		
			}
			else 
			{
				/* int id=Integer.parseInt(request.getParameter("id")); */
				Connection conn = ConnectionManager.getConnection();
				
				try 
				{
					
					/* Statement st = conn.createStatement(); */
					System.out.println("In getAdminDetails Handler");
					int id=Integer.parseInt(request.getParameter("id"));
					String sql="select * from AddProject where ProjectId=?";
					pst.setInt(1, id);
					 rs = pst.executeQuery("select * from AddProject where ProjectId="+id);
					while(rs.next()){
						trbn = new AddProjectBean();
						trbn.setPROID(rs.getInt(1));
						trbn.setPROTYPE(rs.getString(2));
						trbn.setPROSTATUS(rs.getString(3));
						trbn.setPRONAME(rs.getString(4));
						trbn.setLIVEURL(rs.getString(5));
						trbn.setTESTURL(rs.getString(6));
						trbn.setREVIEWDATE(rs.getString(7));
						trbn.setDESIGNDATE(rs.getString(8));
						trbn.setDEVELOPDATE(rs.getString(9));
						trbn.setSITETESTDATE(rs.getString(10));
						trbn.setUATDATE(rs.getString(11));
						trbn.setPRODESCRIPTION(rs.getString(12));
					}
					
					conn.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				/* return trbn; */
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();	
		}
		
	
		
	}
	
		/*
		 * // TODO Auto-generated method stub doGet(request, response);
		 */
	}


