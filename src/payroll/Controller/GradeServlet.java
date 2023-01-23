package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import payroll.DAO.GradeHandler;
import payroll.Model.GradeBean;

/**
 * Servlet implementation class GradeServlet
 */
@WebServlet("/GradeServlet")
public class GradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GradeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("saveEndDate"))
		{
			//int post = Integer.parseInt(request.getParameter("postcd"));
			String endDate = request.getParameter("endDate");
	//		System.out.println(endDate);
			String startDate = request.getParameter("startDate");
		//	System.out.println(startDate);
			int gradeCode =Integer.parseInt(request.getParameter("gradeCode"));
		//	System.out.println(gradeCode);
			GradeHandler gradeHandler = new GradeHandler();
			boolean flag=gradeHandler.saveEndDate(endDate,startDate,gradeCode);
			
			if(flag)
			{
				request.getRequestDispatcher("GradeMaster.jsp?type="+gradeCode+"&flag=2").forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("GradeMaster.jsp?flag=0").forward(request, response);
			}
		}
			/*GradeHandler GH = new GradeHandler();
		//	GradeBean GB = GH.getGrade(post, effdt);
			if(GB!=null)
			{
				request.setAttribute("detail", GB);
				request.getRequestDispatcher("Grade.jsp?action=show").forward(request, response);
			}
			else
			{
				response.sendRedirect("Grade.jsp");
			}
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		boolean flag=false;
		if(action.equalsIgnoreCase("addnew"))
		{
			GradeHandler GH = new GradeHandler();
			GradeBean GB = new GradeBean();
			GB.setPOSTCD(Integer.parseInt(request.getParameter("postcd")));
			GB.setALFACD(request.getParameter("alpha"));
			GB.setDISC(request.getParameter("desc"));
			GB.setEFFDT(request.getParameter("effdt"));
			GB.setBASIC(Integer.parseInt(request.getParameter("basic")==""?"0":request.getParameter("basic")));
			GB.setINCR1(Integer.parseInt(request.getParameter("incr1")==""?"0":request.getParameter("incr1")));
			GB.setNOY1(Integer.parseInt(request.getParameter("noy1")==""?"0":request.getParameter("noy1")));
			GB.setINCR2(Integer.parseInt(request.getParameter("incr2")==""?"0":request.getParameter("incr2")));
			GB.setNOY2(Integer.parseInt(request.getParameter("noy2")==""?"0":request.getParameter("noy2")));
			GB.setINCR3(Integer.parseInt(request.getParameter("incr3")==""?"0":request.getParameter("incr3")));
			GB.setNOY3(Integer.parseInt(request.getParameter("noy3")==""?"0":request.getParameter("noy3")));
			GB.setINCR4(Integer.parseInt(request.getParameter("incr4")==""?"0":request.getParameter("incr4")));
			GB.setNOY4(Integer.parseInt(request.getParameter("noy4")==""?"0":request.getParameter("noy4")));
			GB.setINCR5(Integer.parseInt(request.getParameter("incr5")==""?"0":request.getParameter("incr5")));
			GB.setNOY5(Integer.parseInt(request.getParameter("noy5")==""?"0":request.getParameter("noy5")));
			GB.setINCR6(Integer.parseInt(request.getParameter("incr6")==""?"0":request.getParameter("incr6")));
			GB.setNOY6(Integer.parseInt(request.getParameter("noy6")==""?"0":request.getParameter("noy6")));
			GB.setEXG(Integer.parseInt(request.getParameter("exg")==""?"0":request.getParameter("exg")));
			GB.setMED(Integer.parseInt(request.getParameter("medical")==""?"0":request.getParameter("medical")));
			GB.setEDU(Integer.parseInt(request.getParameter("edu")==""?"0":request.getParameter("edu")));
			GB.setLTC(Integer.parseInt(request.getParameter("ltc")==""?"0":request.getParameter("ltc")));
			GB.setCLOSING(Integer.parseInt(request.getParameter("closing")==""?"0":request.getParameter("closing")));
			GB.setCONV(Integer.parseInt(request.getParameter("conv")==""?"0":request.getParameter("conv")));
			GB.setCASH(Integer.parseInt(request.getParameter("cash")==""?"0":request.getParameter("cash")));
			GB.setCLG(Integer.parseInt(request.getParameter("clearing")==""?"0":request.getParameter("clearing")));
			GB.setWASHING(Integer.parseInt(request.getParameter("washing")==""?"0":request.getParameter("washing")));
			GB.setFLDWRK(Integer.parseInt(request.getParameter("field")==""?"0":request.getParameter("field")));
			
			 flag = GH.addGrade(GB);
			if(flag)
			{
				response.sendRedirect("Grade.jsp?action=saved");
			}
			else
			{
				response.sendRedirect("Grade.jsp");
			}
		}
			if(action.equalsIgnoreCase("addGradeData"))
		{
				//System.out.println("Inside addGradeData");
			GradeHandler gradeHandler = new GradeHandler();
			GradeBean gradeBean = new GradeBean();

			for (int index=1;index<=36;index++ ){
				String indexString=Integer.toString(index);
				gradeBean.setSerialNumber(Integer.parseInt(indexString));	

				gradeBean.setGradeCode(request.getParameter("type")==null?0:Integer.parseInt(request.getParameter("type")));
				gradeBean.setStartDate(request.getParameter("startDate")==null?"":request.getParameter("startDate"));
				gradeBean.setEndDate(request.getParameter("endDate")==null?"":request.getParameter("endDate"));

				gradeBean.setBasic(request.getParameter("gradeBasic"+index)==null?0.0f:Float.parseFloat(request.getParameter("gradeBasic"+index)));

				gradeBean.setIncrement(request.getParameter("incrementAmount"+index)==null?0.0f:Float.parseFloat(request.getParameter("incrementAmount"+index)));
				
				gradeBean.setDaValueType(request.getParameter("daValueType"+index)==null?0:Integer.parseInt(request.getParameter("daValueType"+index)));
				gradeBean.setDaPercentOrFixedValue(request.getParameter("daPercentOrFixedValue"+index)==null?0.0f:Float.parseFloat(request.getParameter("daPercentOrFixedValue"+index)));
				gradeBean.setDaValue(request.getParameter("daValue"+index)==null?0.0f:Float.parseFloat(request.getParameter("daValue"+index)));

				gradeBean.setHraValueType(request.getParameter("hraValueType"+index)==null?0:Integer.parseInt(request.getParameter("hraValueType"+index)));
				gradeBean.setHraPercentOrFixedValue(request.getParameter("hraPercentOrFixedValue"+index)==null?0.0f:Float.parseFloat(request.getParameter("hraPercentOrFixedValue"+index)));
				gradeBean.setHraValue(request.getParameter("hraValue"+index)==null?0.0f:Float.parseFloat(request.getParameter("hraValue"+index)));

				
				
				gradeBean.setGradeStatus(request.getParameter("status"+index)==null?0:Integer.parseInt(request.getParameter("status"+index)));
				gradeBean.setCreationDate(request.getParameter("todaysDate")==null?"":request.getParameter("todaysDate"));


				flag = gradeHandler.addGradeData(gradeBean);
			}
			if(flag)
			{

				response.sendRedirect("GradeMaster.jsp");
			}
			else
			{

				response.sendRedirect("GradeMaster.jsp");
			}
		}
		else if(action.equalsIgnoreCase("delete"))
		{/*
			int post = Integer.parseInt(request.getParameter("postcd"));
			String effdt = request.getParameter("effdt");
			GradeHandler GH = new GradeHandler();
			boolean flag = GH.deleteGrade(post, effdt);
			if(flag)
			{
				response.sendRedirect("Grade.jsp?action=deleted");
			}
			else
			{
				GH = new GradeHandler();
				GradeBean GB = GH.getGrade(post, effdt);
				request.setAttribute("detail", GB);
				request.getRequestDispatcher("Grade.jsp?action=notDel").forward(request, response);
			}
		 */}
	}
}