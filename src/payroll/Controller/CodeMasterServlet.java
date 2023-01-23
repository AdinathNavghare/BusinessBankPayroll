package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.CodeMasterHandler;
import payroll.Model.CodeMasterBean;

/**
 * Servlet implementation class CodeMasterServlet
 */
@WebServlet("/CodeMasterServlet")
public class CodeMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeMasterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("del"))
		{
			int trncd = Integer.parseInt(request.getParameter("trncd"));
			String[] key = request.getParameter("select").split("-");
			CodeMasterHandler CMH = new CodeMasterHandler();
			boolean flag = CMH.deleteCode(trncd);
			if(flag)
			{
				response.sendRedirect("CodeMastMTN.jsp?action=deleted&key="+key[1]+"&selected="+key[0]);
			}
			else
			{
				response.sendRedirect("CodeMastMTN.jsp?action=notDeleted&key="+key[1]+"&selected="+key[0]);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		String selected="";
		if(action.equalsIgnoreCase("addnew"))
		{
			String page = request.getParameter("page");
			CodeMasterBean CMB= new CodeMasterBean();
			CodeMasterHandler CMBH=new CodeMasterHandler();
			CMB.setTRNCD(Integer.parseInt(request.getParameter("trcode")));
			CMB.setDISC(request.getParameter("desc"));
			CMB.setSDESC(request.getParameter("sdesc"));
			CMB.setPSLIPYN(request.getParameter("select.print"));
			CMB.setTAXABLE(request.getParameter("select.taxable"));
			CMB.setSUBSYS(request.getParameter("subsystem"));
			CMB.setAUTOPOST(request.getParameter("select.autop"));
			CMB.setACNO(Integer.parseInt(request.getParameter("acno")));
			CMB.setGROSS_YN(request.getParameter("select.autoCalc"));
			selected=request.getParameter("select.addType");
			String[] key=selected.split("-");
			CMB.setCDTYPE(Integer.parseInt(key[1]));
			CMB.setPrintsequnce(Integer.parseInt(request.getParameter("printsequnce")));
			boolean flag=CMBH.addCDMast(CMB);
			if(flag)
			{
				/*request.getRequestDispatcher(page+"?action=getCodes&key="+key[1]+"&selected="+key[0]).forward(request, response);*/
				response.sendRedirect("CodeMastMTN.jsp?flag1=1");
			}
			else
			{
				/*response.sendRedirect(page);*/
				response.sendRedirect("CodeMastMTN.jsp?flag1=2");
			}
		}
		if(action.equalsIgnoreCase("update"))
		{
			CodeMasterBean CMB= new CodeMasterBean();
			CodeMasterHandler CMBH=new CodeMasterHandler();
			CMB.setTRNCD(Integer.parseInt(request.getParameter("trcode")));
			CMB.setDISC(request.getParameter("desc"));
			CMB.setSDESC(request.getParameter("sdesc"));
			CMB.setPSLIPYN(request.getParameter("select.print"));
			CMB.setTAXABLE(request.getParameter("select.taxable"));
			CMB.setSUBSYS(request.getParameter("subsystem"));
			CMB.setAUTOPOST(request.getParameter("select.autop"));
			CMB.setACNO(Integer.parseInt(request.getParameter("acno")));
			CMB.setGROSS_YN(request.getParameter("select.autoCalc"));
			CMB.setPrintsequnce(Integer.parseInt(request.getParameter("printsequnce")));
			selected=request.getParameter("select.addType");
			boolean flag=CMBH.updateCdmast(CMB);
			if(flag)
			{
				response.sendRedirect("UpdateCodeMast.jsp?action=1&key="+CMB.getTRNCD());
			}
			else
			{
				response.sendRedirect("UpdateCodeMast.jsp?action=0&key="+CMB.getTRNCD());
			}
		}
	}
}