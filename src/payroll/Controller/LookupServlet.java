package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.LookupHandler;
import payroll.Model.Lookup;

/**
 * Servlet implementation class LookupServlet
 */
@WebServlet("/LookupServlet")
public class LookupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LookupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action=request.getParameter("action");
		String result= new String();
		if(action.equalsIgnoreCase("subDetails"))
		{
			String subType= request.getParameter("subType");
			LookupHandler lkph=new LookupHandler();
			Lookup lkp=lkph.getLookup(subType);
			
			result="<root><lookup>";
			result+="<code>"+lkp.getLKP_CODE()+"</code>";
			result+="<srno>"+lkp.getLKP_SRNO()+"</srno>";
			result+="<desc>"+lkp.getLKP_DESC().replaceAll("&", "AND")+"</desc>";
			result+="</lookup></root>";
			response.setContentType("text/xml");
		}
		else if(action.equalsIgnoreCase("maxsr"))
		{
			String[] code= request.getParameter("code").split("-");
			LookupHandler lkph=new LookupHandler();
			result=String.valueOf(lkph.getMaxSrno(code[0])+1);
			response.setContentType("text/html");
		}
		PrintWriter out =response.getWriter();
		out.write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action=request.getParameter("action");
		boolean result=false;
		String key=new String();
		PrintWriter out =response.getWriter();
		if(action.equalsIgnoreCase("addnew"))
		{
			Lookup lkp=new Lookup();
			LookupHandler lkph=new LookupHandler();
			lkp.setLKP_CODE(request.getParameter("code").trim());
			lkp.setLKP_SRNO(Integer.parseInt(request.getParameter("srno").trim()));
			lkp.setLKP_DESC(request.getParameter("desc").trim());
			lkp.setLKP_RECR(Integer.parseInt(request.getParameter("select.addGroup")));
		
		//	key=(request.getParameter("select.addType")==null?request.getParameter("code").trim()+"-"+request.getParameter("desc").trim():request.getParameter("select.addType"));
	/*key=(request.getParameter("select.addGroup")==null?
			request.getParameter("select.addType")==null?request.getParameter("code").trim()+"-"+
					request.getParameter("desc").trim():request.getParameter("select.addGroup"):request.getParameter("select.addType"));
	*/
			
			key=(request.getParameter("select.addType")==null?request.getParameter("code").trim()+"-"+
					request.getParameter("desc").trim():request.getParameter("select.addType"));

			result=lkph.addNewLooup(lkp);
			if(result)
			{
				request.getRequestDispatcher("Lookup.jsp?action=subType&key="+key).forward(request, response);
			}
			else
			{
				out.write("Error in saving record");
			}
		}
		if(action.equalsIgnoreCase("update"))
		{
			Lookup lkp=new Lookup();
			LookupHandler lkph=new LookupHandler();
			lkp.setLKP_CODE(request.getParameter("code").trim());
			lkp.setLKP_SRNO(Integer.parseInt(request.getParameter("srno").trim()));
			lkp.setLKP_DESC(request.getParameter("desc").trim());
			//lkp.setLKP_RECR(Integer.parseInt(request.getParameter("select.addGroup").trim()));
			result=lkph.updateLooup(lkp);
			key=lkp.getLKP_CODE()+"-"+lkph.getLKP_Description(lkp.getLKP_CODE());
			if(result)
			{
				request.getRequestDispatcher("Lookup.jsp?action=subType&key="+key).forward(request, response);
			}
			else
			{
				out.write("Error in saving record");
			}
		}
	}
}