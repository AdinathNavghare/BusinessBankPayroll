package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ExtraFieldHandler;
import payroll.Model.ExtraFieldBean;

/**
 * Servlet implementation class ExtraFieldServlet
 */
@WebServlet("/ExtraFieldServlet")
public class ExtraFieldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExtraFieldServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		ExtraFieldHandler efh = new ExtraFieldHandler();
		boolean flag=false;
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		if(action.equalsIgnoreCase("updateFieldtab"))
		{
			try
			{
				String fname=request.getParameter("fname");
				String desc = request.getParameter("desc");
				int key = Integer.parseInt(request.getParameter("key"));
				ExtraFieldBean efbean;
				efbean = new ExtraFieldBean();
				efbean.setFieldNo(key);
				efbean.setColumnName("F"+key);
				efbean.setFieldName(fname==null?"":fname);
				efbean.setFieldDesc(desc==null?"":desc);
				flag=efh.insertData(efbean);
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				if(flag)
				{
					out.write("true");
				}
				else
				{
					out.write("false");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(action.equalsIgnoreCase("saveOtherDetail"))
		{
			Object[] a = new Object[21];
			String empno = request.getParameter("empno");
			try
			{
				for(int i=1;i<21;i++)
				{
					a[i]=request.getParameter("F"+i)==null?"":request.getParameter("F"+i);
				}
				efh.insertOtherDetail(a,empno);
				session.removeAttribute("empno");
				request.getRequestDispatcher("searchEmployee.jsp").forward(request, response);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(action.equalsIgnoreCase("updateOtherDetail"))
		{
			int num =efh.getFieldCountOT();
			Object[] a = new Object[num];
			String empno = request.getParameter("empno");
			for(int i=1;i<num;i++)
			{
				a[i]=request.getParameter("F"+i)==null?"":request.getParameter("F"+i);
			}
			efh.updateOtherDetail(a,empno);
			//request.getRequestDispatcher("otherDetail.jsp?action=showemp").forward(request, response);
			request.getRequestDispatcher("searchEmployee.jsp").forward(request, response);
		}
	}
}