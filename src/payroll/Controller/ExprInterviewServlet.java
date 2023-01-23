package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import payroll.DAO.ConnectionManager;
import payroll.DAO.ExprInterviewDao;
import payroll.DAO.RecruitmentDAO;
import payroll.Model.ExprInterviewBean1;
import payroll.Model.ExprInterviewBean6;

/**
 * Servlet implementation class ExprInterviewServlet
 */
@WebServlet("/ExprInterviewServlet")
public class ExprInterviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExprInterviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
        response.setContentType("text/html");		
        HttpSession session=request.getSession();  
        //session.setAttribute("uname","abc"); 
        //String n=(String)session.getAttribute("uname");
		String action=request.getParameter("action");
	    ExprInterviewBean1 expr = new ExprInterviewBean1();
	    ExprInterviewDao edao=new ExprInterviewDao();
	    
		
		if(action.equalsIgnoreCase("AddInfo")){
			
			System.out.println("PAss : : "+request.getParameter("Pvalid"));
			expr.setPName(request.getParameter("pname"));
			expr.setDOB(request.getParameter("dob")==null?null: request.getParameter("dob"));
			expr.setAddress1(request.getParameter("adrs1"));
			expr.setAddress2(request.getParameter("adrs2"));
			expr.setEmail(request.getParameter("mail"));
			expr.setMobileNo(Long.parseLong(request.getParameter("mobile")));
			expr.setMaritalStatus(request.getParameter("marriage"));
			expr.setPassportNo(request.getParameter("passport"));
			expr.setPassportValid(request.getParameter("Pvalid")==null ||request.getParameter("Pvalid")==""?null: request.getParameter("Pvalid"));
			expr.setCrrDate(request.getParameter("crrDate")==null?null: request.getParameter("crrDate"));
			expr.setHobbies(request.getParameter("hob"));
			
			//added
			
			expr.setType(request.getParameter("fresher"));
			System.out.println(request.getParameter(" type:: "+request.getParameter("fresher")));
			
			ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
			ExprInterviewDao rdao = new ExprInterviewDao();
			boolean flag=edao.AddExprInfo1(expr);
			
			//result = rdao.AddExprInfo1(expr);
			String s;
			int i=0;
			for (ExprInterviewBean1 rs : result) {
			
			 i= rs.getExprid();
			}
			PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			session.setAttribute("Exprid",i);
			System.out.println("jnkjnk"+i);
			out1.write(String.valueOf(flag));
			
			
			
		}
		
		if(action.equalsIgnoreCase("UpdatePersonal"))
		{
			int exprid=Integer.parseInt(request.getParameter("exprid"));
			expr.setPName(request.getParameter("pname"));
			expr.setDOB(request.getParameter("dob")==null?"": request.getParameter("dob"));
			expr.setAddress1(request.getParameter("adrs1"));
			expr.setAddress2(request.getParameter("adrs2"));
			expr.setEmail(request.getParameter("mail"));
			expr.setMobileNo(Long.parseLong(request.getParameter("mobile")));
			expr.setMaritalStatus(request.getParameter("marriage"));
			expr.setPassportNo(request.getParameter("passport"));
			expr.setPassportValid(request.getParameter("Pvalid")==null ||request.getParameter("Pvalid")==""?null: request.getParameter("Pvalid"));
			expr.setCrrDate(request.getParameter("crrDate")==null?"": request.getParameter("crrDate"));
			expr.setHobbies(request.getParameter("hob"));
			System.out.println(request.getParameter("passport"));
			
			boolean flag=ExprInterviewDao.UpdatePersonal(expr,exprid);
			if (flag) {
				response.sendRedirect("ExprPersonalDetails.jsp");
			} else {
				//response.sendRedirect("AddProject.jsp");
			}
		}
		if(action.equalsIgnoreCase("addFamilyDetails"))
		{
			//int exprid=Integer.parseInt(request.getParameter("exprid"));
			expr.setRelation(request.getParameter("rRelation"));
			expr.setRelName(request.getParameter("relName"));
			expr.setAge(Integer.parseInt(request.getParameter("rage")));
			expr.setOccupation(request.getParameter("occupation"));
			//expr.setEid(exprid);
			
			ExprInterviewDao rdao = new ExprInterviewDao();
			boolean flag=edao.AddFamilyDetails(expr);
			if(flag=true)
			{
				response.sendRedirect("FreshersFamilyDetails.jsp?action=insertFamilyDetails");
			}
			else
			{
				response.sendRedirect("error.jsp");
			}
		}
		if(action.equalsIgnoreCase("addReference"))
		{
			expr.setRefName(request.getParameter("refname1"));
			expr.setRefName2(request.getParameter("refname2"));
			
			expr.setRefDesig(request.getParameter("refDesig1"));
			expr.setRefDesig(request.getParameter("refDesig2"));
			
			expr.setRefOrganization(request.getParameter("refOrg1"));
			expr.setRefOrganization2(request.getParameter("refOrg2"));
			
			expr.setRefMobile(Long.parseLong(request.getParameter("refMobile1")==""?null:request.getParameter("refMobile1")));
			expr.setRefMobile2(Long.parseLong(request.getParameter("refMobile2")==""?null:request.getParameter("refMobile2")));
			
			expr.setRefMail(request.getParameter("refMail1"));
			expr.setRefMail2(request.getParameter("refMail2"));
			
			ExprInterviewDao rdao = new ExprInterviewDao();
			boolean flag=edao.AddReference(expr);
			if(flag=true)
			{
				response.sendRedirect("FresherReference.jsp?action=insertReference");
			}
			else
			{
				response.sendRedirect("error.jsp");
			}
			
			
			
		}
		
		if(action.equalsIgnoreCase("UpdateEmploy"))
		{
			int employid=Integer.parseInt(request.getParameter("employid"));
			expr.setOrgName(request.getParameter("OrgName"));
			expr.setFromDate(request.getParameter("From"));
			expr.setToDate(request.getParameter("To"));
			expr.setStartPos(request.getParameter("StartPos"));
			expr.setLeavPos(request.getParameter("LeavPos"));
			expr.setLeavReas(request.getParameter("LeavReas"));
			expr.setSalCTC(Float.parseFloat(request.getParameter("SalCTC")));
			expr.setJobDes(request.getParameter("JobDes"));
			
			
			boolean flag=ExprInterviewDao.UpdateEmploy(expr,employid);
			if (flag) {
				response.sendRedirect("ExprEmploymentDetails.jsp");
			} else {
				//response.sendRedirect("AddProject.jsp");
			}
		}
		if(action.equalsIgnoreCase("DeletePersonal"))
		{
			int exprid = Integer.parseInt(request.getParameter("Exprid"));

			int flag = ExprInterviewDao.DeletePersonal(exprid);
			System.out.println("AddProjectDao Flag:" + flag);
			// PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		}
		if(action.equalsIgnoreCase("DeleteEmploy"))
		{
			int employid = Integer.parseInt(request.getParameter("employid"));

			int flag = ExprInterviewDao.DeleteEmploy(employid);
			//System.out.println("AddProjectDao Flag:" + flag);
			// PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		}
		if(action.equalsIgnoreCase("DeleteEducation"))
		{
			
			int tt=Integer.parseInt(request.getParameter("Eid"));
						
			int flag=ExprInterviewDao.DeleteEducation(tt);
			
                PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");		
				out1.write(String.valueOf(flag));
				
		}
		if(action.equalsIgnoreCase("AddEmployRec"))
		{
			///expr.setExprid(Integer.parseInt(request.getParameter("ExprId")));
			expr.setOrgName(request.getParameter("OrgName"));
			expr.setFromDate(request.getParameter("From"));
			expr.setToDate(request.getParameter("To"));
			expr.setStartPos(request.getParameter("StartPos"));
			expr.setLeavPos(request.getParameter("LeavPos"));
			expr.setLeavReas(request.getParameter("LeavReas"));
			expr.setJobDes(request.getParameter("JobDes"));
			expr.setSalCTC(Float.parseFloat(request.getParameter("SalCTC")));
			
			boolean flag = ExprInterviewDao.AddEmployRec(expr);
			PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			
			out1.write(String.valueOf(flag));
			
		}
		
		if(action.equalsIgnoreCase("AddLanguage")){
			
			expr.setLanguage(request.getParameter("l"));
			expr.setSpeak(request.getParameter("s") == "" ? "No" : request.getParameter("s"));
			expr.setRead(request.getParameter("r") == "" ? "No" :request.getParameter("r"));
			expr.setWrite(request.getParameter("w") == "" ? "No" :request.getParameter("w"));
			
			boolean flag1=edao.AddLanguages(expr);
		
			PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");		
			out1.write(String.valueOf(flag1));
		}
		if(action.equalsIgnoreCase("DisplayLang"))
		{
			String str="select * From ExprLanguage where Exprid=(select Top(1) Exprid from ExprPersonalInfo order by 1 desc)";
			String lang = "";
			String s = "";
			String r = "";
			String w = "";
			int id=0;
			Connection con = null;
			Statement st = null,st1=null;
			ResultSet rs = null,rs1=null;
			try {
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(str);
				while (rs.next())
				{
					id=rs.getInt("Lid");
					lang=rs.getString("LangName");
					s=rs.getString("Speaks");
					r=rs.getString("Reads");
					w=rs.getString("Writes");
				}
				response.setContentType("txt/html");
				PrintWriter out1 = response.getWriter();
				out1.write(id+",");
				out1.write(lang+",");
				out1.write(s+",");
				out1.write(r+",");
				out1.write(w+",");
				
				st.close();
				rs.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(action.equalsIgnoreCase("DisplayPersonalInfo"))
		{
			String str="select * From ExprPersonalInfo where Exprid=(select Top(1) Exprid from ExprPersonalInfo order by 1 desc)";
			String name= "";
			String DOB = "";
			String email = "";
			int mob = 0;
			int id=0;
			Connection con = null;
			Statement st = null,st1=null;
			ResultSet rs = null,rs1=null;
			try {
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(str);
				while (rs.next())
				{
					id=rs.getInt("Exprid");
					name=rs.getString("PName");
					DOB=rs.getString("DOB");
					email=rs.getString("Email");
					mob=rs.getInt("MobileNo");
				}
				response.setContentType("txt/html");
				PrintWriter out1 = response.getWriter();
				out1.write(id+",");
				out1.write(name+",");
				out1.write(DOB+",");
				out1.write(email+",");
				out1.write(mob+",");
				
				st.close();
				rs.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		if(action.equalsIgnoreCase("AddEducation")){    
			//	System.out.println("sevlet");
				expr.setQuali(request.getParameter("EDEGREE") == null ? "" : request.getParameter("EDEGREE"));
				expr.setDegree(request.getParameter("degree") == null ? "" : request.getParameter("degree"));
				expr.setCollage(request.getParameter("collage") == null ? "" :request.getParameter("collage"));
				expr.setSpecial(request.getParameter("subject") == null ? "" :request.getParameter("subject"));
				expr.setYear(request.getParameter("passout") == null ? "" : request.getParameter("passout"));
				expr.setStd(request.getParameter("std") == null ? "" : request.getParameter("std"));
				expr.setMarks(Integer.parseInt(request.getParameter("marks") == null ? "0" :request.getParameter("marks")));
				 
				
				boolean flag=edao.AddQualifications(expr);
			
				PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");		
				out1.write(String.valueOf(flag));
				
				/*if(flag=true){
					response.sendRedirect("ExprEducation.jsp");
					System.out.println(" Record Inserted Successfully");
				}
				else{
					System.out.println(" Fail To Inserted Record");
				}*/
				
			}
		if(action.equalsIgnoreCase("UpdateEducation")){    
			System.out.println("UpdateEducation sevlet ");
		    int tt=Integer.parseInt(request.getParameter("eid"));
		    System.out.println("eid is : "+tt);
			expr.setQuali(request.getParameter("EDEGREE") == null ? "" : request.getParameter("EDEGREE"));
			expr.setDegree(request.getParameter("degree") == null ? "" : request.getParameter("degree"));
			expr.setCollage(request.getParameter("collage") == null ? "" :request.getParameter("collage"));
			expr.setSpecial(request.getParameter("subject") == null ? "" :request.getParameter("subject"));
			expr.setYear(request.getParameter("passout") == null ? "" : request.getParameter("passout"));
			expr.setStd(request.getParameter("std") == null ? "" : request.getParameter("std"));
			expr.setMarks(Float.parseFloat(request.getParameter("marks") == null ? "" :request.getParameter("marks")));
			
			
			int flag=edao.UpdateQualifications(expr,tt);
		
			PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");		
			out1.write(String.valueOf(flag));
		
			
		}
		if(action.equalsIgnoreCase("DisplayInfo")){
			System.out.println("other servlet");
		}
		if(action.equalsIgnoreCase("addCompLiteracy")){
			expr.setLitType(request.getParameter("literacy"));
			expr.setLitName(request.getParameter("lit"));
			expr.setProf(request.getParameter("txt2") == "" ? "No" : request.getParameter("txt2"));
			expr.setAvg(request.getParameter("txt3") == "" ? "No" :request.getParameter("txt3"));
			expr.setElementary(request.getParameter("txt4") == "" ? "No" :request.getParameter("txt4"));
			
			boolean flag1=edao.AddCompLiteracy(expr);
		
			PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");		
			out1.write(String.valueOf(flag1));
		}
		if(action.equalsIgnoreCase("AddAppraisalReport")){
			System.out.println("AddAppraisalReport servlet");
			
			//Aid,Name,Age,Quali,Experience,PresentCTC,ExpectedCTC,Position,Score,InterviewerComments,InterviewerName,
			//InterviewDate,Result,Grade,ApruSalaryMonthly,ApruCTC,PM_Name,HR_Name,MD_Name
			
			ExprInterviewBean6 b6=new ExprInterviewBean6();
			b6.setAName(request.getParameter("aname"));
			b6.setAge(Integer.parseInt(request.getParameter("aage")));
			b6.setQuali(request.getParameter("quali"));
			b6.setExperience(request.getParameter("expr"));
			b6.setPresentCTC(Double.parseDouble(request.getParameter("pctc")));
			b6.setExpectedCTC(Double.parseDouble(request.getParameter("ectc")));
			b6.setPosition(request.getParameter("position"));
			b6.setScore(Integer.parseInt(request.getParameter("score")));
			b6.setInterviewerComments(request.getParameter("incomment"));
			b6.setInterviewerName(request.getParameter("interviewer"));
			b6.setInterviewDate(request.getParameter("RDate"));
			b6.setResult(request.getParameter("result"));
			b6.setGrade(request.getParameter("grade"));
			b6.setApruSalaryMonthly(Double.parseDouble(request.getParameter("salmonth")));
			b6.setApruCTC(Double.parseDouble(request.getParameter("ctcmonth")));
			b6.setPM_Name(request.getParameter("pmname"));
			b6.setHR_Name(request.getParameter("hrname"));
			b6.setMD_Name(request.getParameter("mdname"));
			b6.setPM_Date(request.getParameter("ADate"));
			b6.setHR_Date(request.getParameter("AHRDate"));
			b6.setMD_Date(request.getParameter("AMDDate"));
			
			boolean flag=ExprInterviewDao.AddAppraisal(b6);
			
			if(flag=true){
				//response.sendRedirect("ExprEducation.jsp");
				System.out.println(" Record Inserted Successfully");
			}
			else{
				System.out.println(" Fail To Inserted Record");
			}
			
		}
		if(action.equalsIgnoreCase("AddAppraisalReport")){
			System.out.println("AddAppraisalReport servlet");
			
			//Aid,Name,Age,Quali,Experience,PresentCTC,ExpectedCTC,Position,Score,InterviewerComments,InterviewerName,
			//InterviewDate,Result,Grade,ApruSalaryMonthly,ApruCTC,PM_Name,HR_Name,MD_Name
			
			ExprInterviewBean6 b6=new ExprInterviewBean6();
			b6.setAName(request.getParameter("aname"));
			b6.setAge(Integer.parseInt(request.getParameter("aage")));
			b6.setQuali(request.getParameter("quali"));
			b6.setExperience(request.getParameter("expr"));
			b6.setPresentCTC(Double.parseDouble(request.getParameter("pctc")));
			b6.setExpectedCTC(Double.parseDouble(request.getParameter("ectc")));
			b6.setPosition(request.getParameter("position"));
			b6.setScore(Integer.parseInt(request.getParameter("score")));
			b6.setInterviewerComments(request.getParameter("incomment"));
			b6.setInterviewerName(request.getParameter("interviewer"));
			b6.setInterviewDate(request.getParameter("RDate"));
			b6.setResult(request.getParameter("result"));
			b6.setGrade(request.getParameter("grade"));
			b6.setApruSalaryMonthly(Double.parseDouble(request.getParameter("salmonth")));
			b6.setApruCTC(Double.parseDouble(request.getParameter("ctcmonth")));
			b6.setPM_Name(request.getParameter("pmname"));
			b6.setHR_Name(request.getParameter("hrname"));
			b6.setMD_Name(request.getParameter("mdname"));
			b6.setPM_Date(request.getParameter("ADate"));
			b6.setHR_Date(request.getParameter("AHRDate"));
			b6.setMD_Date(request.getParameter("AMDDate"));
			
			boolean flag=ExprInterviewDao.AddAppraisal(b6);
			
			if(flag=true){
				//response.sendRedirect("ExprEducation.jsp");
				response.sendRedirect("ExprInterviewForm6.jsp");

			}
			else{
				System.out.println(" Fail To Inserted Record");
			}
			
		}

		
	}

}
