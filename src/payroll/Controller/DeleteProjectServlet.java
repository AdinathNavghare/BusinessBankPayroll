package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import payroll.DAO.AddProjectDAO;
import payroll.DAO.ConnectionManager;
import payroll.Model.AddProjectBean;

/**
 * Servlet implementation class DeleteProjectServlet
 */

@WebServlet("/DeleteProjectServlet")
public class DeleteProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteProjectServlet() {
		super();
		// TODO Auto-generated constructor stub

	}

	/*
	 * protected void doGet(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // TODO Auto-generated
	 * method stub
	 * System.out.println("Project id string"+request.getParameter("projectId"));
	 * int projectId=Integer.parseInt(request.getParameter("projectId"));
	 * System.out.println("Project Id"+projectId); String
	 * sql="select * from AddProject where id="+projectId; AddProjectDAO apd=new
	 * AddProjectDAO(); AddProjectBean apb=new AddProjectBean(); String proName="";
	 * String status=""; String pt=""; String pp=""; String rd=""; String pd="";
	 * String ud=""; String lu=""; String tu=""; String devd=""; String dd="";
	 * String std="";
	 * 
	 * Connection con=null; Statement st=null; ResultSet rs=null; try {
	 * con=ConnectionManager.getConnection(); st=con.createStatement();
	 * rs=st.executeQuery(sql); while(rs.next()) {
	 * 
	 * apb.setPRONAME(rs.getString("ProjectName"));
	 * proName=rs.getString("ProjectName");
	 * 
	 * apb.setPROSTATUS(rs.getString("PStatus")); status=rs.getString("PStatus");
	 * 
	 * apb.setPROTYPE(rs.getString("PType")); pt=rs.getString("PType");
	 * 
	 * apb.setPROPRIORITY(rs.getString("PPriority")); pp=rs.getString("PPriority");
	 * 
	 * apb.setREVIEWDATE(rs.getString("ReviewDate")); rd=rs.getString("ReviewDate");
	 * 
	 * apb.setLIVEURL(rs.getString("LiveUrl")); lu=rs.getString("LiveUrl");
	 * 
	 * apb.setTESTURL(rs.getString("TestUrl")); tu=rs.getString("TestUrl");
	 * 
	 * apb.setPRODESCRIPTION(rs.getString("ProDescription"));
	 * pd=rs.getString("ProDescription");
	 * 
	 * apb.setUATDATE(rs.getString("DesignDate")); dd=rs.getString("DesignDate");
	 * 
	 * apb.setDEVELOPDATE(rs.getString("DevelopDate"));
	 * devd=rs.getString("DevelopDate");
	 * 
	 * apb.setSITETESTDATE(rs.getString("SiteTestDate"));
	 * std=rs.getString("SiteTestDate");
	 * 
	 * apb.setUATDATE(rs.getString("UATDate")); ud=rs.getString("UATDate");
	 * 
	 * 
	 * }
	 * 
	 * response.setContentType("txt/html"); PrintWriter out1=response.getWriter();
	 * out1.write(proName+" "); out1.write(status+" "); out1.write(pp+" ");
	 * out1.write(pt+" "); out1.write(rd+" "); out1.write(lu+" ");
	 * out1.write(tu+" "); out1.write(pd+" "); out1.write(dd+" ");
	 * out1.write(devd+" "); out1.write(std+" "); out1.write(ud+" ");
	 * 
	 * st.close(); rs.close(); con.close();
	 * 
	 * } catch(Exception e) { e.printStackTrace(); } }
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		System.out.println("action:" + action);

		if (action.equals("delete")) {

			/* response.sendRedirect("ProFilter.jsp?action=post?id="+id); */
			/*
			 * Connection con=null; PreparedStatement pst=null; ResultSet rs=null; String
			 * query="DELETE FROM AddProject WHERE projectId="+id; try {
			 * con=ConnectionManager.getConnection();
			 * 
			 * pst=con.prepareStatement(query); pst.setInt(1, id); int
			 * flag=pst.executeUpdate(); if(flag!=0) {
			 * response.sendRedirect("ProFilter.jsp?flag="+flag); } con.close();
			 * pst.close(); rs.close(); }catch(Exception se) { se.printStackTrace(); }
			 */
		} else if (action.equalsIgnoreCase("getProjectData")) {
			AddProjectDAO apd = new AddProjectDAO();
			AddProjectBean apb = new AddProjectBean();
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			// String query="select
			// ProjectName,PStatus,PType,PPriority,ReviewDate,ProDescription from AddProject
			// where projectId="+pid;
			String pid = request.getParameter("EMPNO");
			String query = "select ProjectName,PStatus,PType,PPriority,ReviewDate,ProDescription from AddProject where projectId="
					+ pid + " or ProjectName=" + pid;

			try {
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(query);
				while (rs.next()) {
					apb.setPRONAME(rs.getString("ProjectName"));
					apb.setPROSTATUS(rs.getString("PStatus"));
					apb.setPROTYPE(rs.getString("PType"));

				}
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (action.equalsIgnoreCase("getProjectReportData")) {

			response.setContentType("txt/html");
			PrintWriter out1 = response.getWriter();
			// out1.write(String.valueOf(flag));
			AddProjectDAO apd = new AddProjectDAO();
			AddProjectBean apb = new AddProjectBean();

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			Integer flag = 0;
			// String query="select
			// ProjectName,PStatus,PType,PPriority,ReviewDate,ProDescription from AddProject
			// where projectId="+pid;
			String pid = request.getParameter("select");
			String query = "select ProjectName,PStatus,PType,PPriority,ReviewDate,UATDate,ProDescription from AddProject where  ProjectName="
					+ pid;

			try {
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(query);
				while (rs.next()) {
					apb.setPRONAME(rs.getString("ProjectName"));
					apb.setPROSTATUS(rs.getString("PStatus"));
					apb.setPROTYPE(rs.getString("PType"));
					apb.setPROPRIORITY(rs.getString("PPriority"));
					apb.setREVIEWDATE(rs.getString("ReviewDate"));
					apb.setPRODESCRIPTION(rs.getString("ProDescription"));
					apb.setUATDATE(rs.getString("UATDate"));
				}
				if (apb != null) {
					flag = 1;
					out1.write(String.valueOf(flag));
					response.sendRedirect("AddProjectReport.jsp?apb=" + apb);
				}

				rs.close();
				con.close();
				st.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("viewAllProject")) {

			int projectId = Integer.parseInt(request.getParameter("projectId"));
			String sql = "select * from AddProject where ProjectId=" + projectId;
			String sql1="select * from Task where ProjectId="+projectId;
			AddProjectDAO apd = new AddProjectDAO();
			AddProjectBean apb = new AddProjectBean();
			System.out.println(projectId);
			String proName = "";
			String status = "";
			String pt = "";
			String pp = "";
			String sd = "";
			String ed = "";
			String pd = "";
			String ud = "";
			String lu = "";
			String tu = "";
			String devd = "";
			String dd = "";
			String std = "";
			int id=0;
			Connection con = null;
			Statement st = null,st1=null;
			ResultSet rs = null,rs1=null;
			try {
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					id=rs.getInt("ProjectId");
					
					
System.out.println("Project Name"+rs.getString("ProjectName"));
					apb.setPRONAME(rs.getString("ProjectName"));
					proName = rs.getString("ProjectName");
					

					apb.setPROSTATUS(rs.getString("Pstatus"));
					status = rs.getString("Pstatus");

					apb.setPROTYPE(rs.getString("PType"));
					pt = rs.getString("PType");

					apb.setPROPRIORITY(rs.getString("PPriority"));
					pp = rs.getString("PPriority");

					apb.setREVIEWDATE(rs.getString("StartDate"));
				sd= rs.getString("StartDate");
					
					apb.setUATDATE(rs.getString("EndDate"));
					ed= rs.getString("EndDate");


					apb.setLIVEURL(rs.getString("LiveUrl"));
					lu = rs.getString("LiveUrl");

					apb.setTESTURL(rs.getString("TestUrl"));
					tu = rs.getString("TestUrl");

					apb.setPRODESCRIPTION(rs.getString("ProDescription"));
					pd = rs.getString("ProDescription");
					
					

					

					
				}
				st1 = con.createStatement();
				rs1 = st1.executeQuery(sql1);
				String tname="",tt="",ts="",tp="",tsd="",tds="";
				int tid=0;
				String task="";
				while(rs1.next())
				{
					tid=rs1.getInt("TASK_ID");
					tname=rs1.getString("TASK_NAME");
					tt=rs1.getString("TASK_TYPE");
					ts=rs1.getString("TASK_STATUS");
					tp=rs1.getString("TASK_PRIORITY");
					tsd=rs1.getString("TASK_START_DATE");
					tds=rs1.getString("DUE_DATE");
					task=task+tid+","+tname+","+tt+","+ts+","+tp+","+tsd+","+tds+",*,";
					
				}
				response.setContentType("txt/html");
				PrintWriter out1 = response.getWriter();
				out1.write(id+",");
				out1.write(proName+",");
				out1.write(status+",");
				out1.write(pt+",");
				out1.write(pp+",");
				out1.write(lu+",");
				out1.write(tu+",");
				out1.write(sd+",");
				out1.write(ed+",");
				out1.write(pd+",");
				out1.write(task);
				/*
				 * out1.write(tid+","); out1.write(tname+","); out1.write(tt+",");
				 * out1.write(ts+","); out1.write(tp+","); out1.write(tsd+",");
				 * out1.write(tds+",");
				 */

				st.close();
				rs.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
