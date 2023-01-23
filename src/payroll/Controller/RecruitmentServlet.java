package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;


import payroll.DAO.RecruitmentDAO;

import payroll.Model.RecruitmentBean;

/**
 * Servlet implementation class RecruitmentServlet
 */
@WebServlet("/RecruitmentServlet")
public class RecruitmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecruitmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		response.setContentType("txt/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		String action = request.getParameter("action");

		RecruitmentBean Rbean = new RecruitmentBean();

		if (action.equalsIgnoreCase("RecruitInfo")) {
			
			String RDate=request.getParameter("RDate");
			java.util.Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(RDate);
			String RecDate = sdf.format(date);
			
			//taskbean.setPROJECT((request.getParameter("pnm")==null?"":request.getParameter("pnm")));
			/*Rbean.setRecruitDate(RecDate);
			Rbean.setManagerName(request.getParameter("ProManName"));
			
			Rbean.setDesig(request.getParameter("Desig"));
			Rbean.setDept(request.getParameter("Dept"));
			Rbean.setNoOfRequirement(Integer.parseInt(request.getParameter("ReqNo")));
			Rbean.setReqType(request.getParameter("ReqType"));
			Rbean.setEducation(request.getParameter("Education"));
			Rbean.setEmpType(request.getParameter("EmpType"));
			
			Rbean.setSalCTCFrom(Integer.parseInt(request.getParameter("SalFrom")));
			Rbean.setSalCTCTo(Integer.parseInt(request.getParameter("SalTo")));
			
			Rbean.setBondRequire(request.getParameter("BondYesNo"));
			Rbean.setDocRequire(request.getParameter("DocYesNo"));
			Rbean.setMinExp(Float.parseFloat(request.getParameter("MinExp")));
			Rbean.setProbPeriod(Integer.parseInt(request.getParameter("ProbPeriod")));
			Rbean.setBondTenure(Float.parseFloat(request.getParameter("BondTenure")));
			Rbean.setSkills(request.getParameter("SkillArea"));
			 
			Rbean.setSpecialReq(request.getParameter("SpecialReqArea"));
			*/
			
			
			Rbean.setRecruitDate(RecDate==null?"":RecDate);
			Rbean.setManagerName(request.getParameter("ProManName")==null?"":request.getParameter("ProManName"));
			
			Rbean.setDesig(request.getParameter("Desig")==null?"":request.getParameter("Desig"));
			Rbean.setDept(request.getParameter("Dept")==null?"":request.getParameter("Dept"));
			Rbean.setNoOfRequirement(Integer.parseInt(request.getParameter("ReqNo")==""?"0":request.getParameter("ReqNo")));
			Rbean.setReqType(request.getParameter("ReqType")==null?"":request.getParameter("ReqType"));
			Rbean.setEducation(request.getParameter("Education")==null?"":request.getParameter("Education"));
			Rbean.setEmpType(request.getParameter("EmpType")==null?"":request.getParameter("EmpType"));
			
			Rbean.setSalCTCFrom(Integer.parseInt(request.getParameter("SalFrom")==""?"0":request.getParameter("SalFrom")));
			Rbean.setSalCTCTo(Integer.parseInt(request.getParameter("SalTo")==""?"0":request.getParameter("SalTo")));
			
			Rbean.setBondRequire(request.getParameter("BondYesNo")==null?"":request.getParameter("BondYesNo"));
			Rbean.setDocRequire(request.getParameter("DocYesNo")==null?"":request.getParameter("DocYesNo"));
			Rbean.setMinExp(Float.parseFloat(request.getParameter("MinExp")==""?"0":request.getParameter("MinExp")));
			Rbean.setProbPeriod(Integer.parseInt(request.getParameter("ProbPeriod")==""?"0":request.getParameter("ProbPeriod")));
			Rbean.setBondTenure(Float.parseFloat(request.getParameter("BondTenure")==""?"0":request.getParameter("BondTenure")));
			Rbean.setSkills(request.getParameter("SkillArea")==null?"":request.getParameter("SkillArea"));
			 
			Rbean.setSpecialReq(request.getParameter("SpecialReqArea")==null?"":request.getParameter("SpecialReqArea"));
			
			String jobLoc= request.getParameter("JobLoc")==null?"":request.getParameter("JobLoc");
			/*
			 * String[] s=jobLoc.split(","); for(int i=0;i<s.length;i++) {
			 * System.out.println("locations: "+s[i]); }
			 */
			//Rbean.setJobLocation(s);
			
			boolean flag = RecruitmentDAO.RecruitDetails(Rbean,jobLoc);
			if (flag) {
				response.sendRedirect("RecruitmentForm.jsp");
			} else {
				//response.sendRedirect("AddProject.jsp");
			}

		}
		else if (action.equalsIgnoreCase("RecruitUpdate")) {
			
			int rid=Integer.parseInt(request.getParameter("rid"));
			String RDate=request.getParameter("RDate");
			java.util.Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(RDate);
			String RecDate = sdf.format(date);
			
			Rbean.setRecruitDate(RecDate);
			Rbean.setManagerName(request.getParameter("ProManName").trim());
			
			Rbean.setDesig(request.getParameter("Desig").trim());
			Rbean.setDept(request.getParameter("Dept").trim());
			Rbean.setNoOfRequirement(Integer.parseInt(request.getParameter("ReqNo").trim()));
			Rbean.setReqType(request.getParameter("ReqType").trim());
			Rbean.setEducation(request.getParameter("Education").trim());
			Rbean.setEmpType(request.getParameter("EmpType").trim());
			
			Rbean.setSalCTCFrom(Integer.parseInt(request.getParameter("SalFrom").trim()));
			Rbean.setSalCTCTo(Integer.parseInt(request.getParameter("SalTo").trim()));
			
			Rbean.setBondRequire(request.getParameter("BondYesNo").trim());
			Rbean.setDocRequire(request.getParameter("DocYesNo").trim());
			Rbean.setMinExp(Float.parseFloat(request.getParameter("MinExp")));
			Rbean.setProbPeriod(Integer.parseInt(request.getParameter("ProbPeriod").trim()));
			Rbean.setBondTenure(Float.parseFloat(request.getParameter("BondTenure").trim()));
			Rbean.setSkills(request.getParameter("SkillArea").trim());
			 
			Rbean.setSpecialReq(request.getParameter("SpecialReqArea").trim());
			
			String jobLoc= request.getParameter("JobLoc");
			
			
			  String[] s=jobLoc.split(",");
			  String Lid=null;
			  String LName=null;
			  for(int i=0;i<s.length;i++)
			  {
				  Lid=s[0];
				  LName=s[1];
				  System.out.println("locations: "+s[i]);
			  }
			 
			  int LocId=Integer.parseInt(Lid);
			//Rbean.setJobLocation(s);
			
			boolean flag = RecruitmentDAO.RecruitUpdate(Rbean,jobLoc,rid,LocId);
			if (flag) {
				response.sendRedirect("RecruitmentForm.jsp");
			} else {
				response.sendRedirect("EditRecruit.jsp");
			}

		}

		else if (action.equalsIgnoreCase("Delete"))
		{
			int rid = Integer.parseInt(request.getParameter("Rid"));

			int flag = RecruitmentDAO.DeleteRecruit(rid);
			System.out.println("AddProjectDao Flag:" + flag);
			// PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		} 
	}

}
