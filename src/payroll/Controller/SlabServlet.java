package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.SlabHandler;
import payroll.Model.SlabBean;

/**
 * Servlet implementation class SlabServlet
 */
@WebServlet("/SlabServlet")
public class SlabServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SlabServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			String action = request.getParameter("action")!=null?request.getParameter("action"):"";
			if(action.equalsIgnoreCase("addnew"))
			{
				SlabBean SB= new SlabBean();
				SlabHandler SBH = new SlabHandler();
				String ecat=request.getParameter("empcat");
				String ecat1=request.getParameter("empcat");
				String page = request.getParameter("pageName");
				StringTokenizer st = new StringTokenizer(ecat,":");
				String[] employ= ecat.split(":");
				ecat = employ[1];
				SB.setEFFDATE(request.getParameter("effDate")!=null?request.getParameter("effDate"):"");
				SB.setEMP_CAT(Integer.parseInt(ecat));
				SB.setTRNCD(request.getParameter("select.trncd")!=null?Integer.parseInt(request.getParameter("select.trncd")):0);
				SB.setSRNO(request.getParameter("maxsr")!=null?Integer.parseInt(request.getParameter("maxsr")):0);
				SB.setFRMAMT(request.getParameter("frmAmt")!=null?Integer.parseInt(request.getParameter("frmAmt")):0);
				SB.setTOAMT(request.getParameter("toAmt")!=null?Integer.parseInt(request.getParameter("toAmt")):0);
				SB.setPER(request.getParameter("percent")!=null?Float.parseFloat(request.getParameter("percent")):0);
				SB.setMINAMT(request.getParameter("minAmt")!=null?Integer.parseInt(request.getParameter("minAmt")):0);
				SB.setMAXAMT(request.getParameter("maxAmt")!=null?Integer.parseInt(request.getParameter("maxAmt")):0);
				SB.setFIXAMT(request.getParameter("fixAmt")!=null?Integer.parseInt(request.getParameter("fixAmt")):0);
				SB.setON_AMT_CD(0);	
				boolean flag = SBH.addSlab(SB);
				if(flag)
				{
					request.getRequestDispatcher(page+"?action=getList&ecat="+ecat1+"&trncd="+SB.getTRNCD()).forward(request, response);
				}
				else
				{
					System.out.println("Error in insertion");
				}
			}
			
			else if(action.equalsIgnoreCase("endSlab"))
			{
				String[] key= request.getParameter("key").split("-");   // key[0]->Emp_cat  key[1]->trncd  key[2]-> srno
				SlabHandler SBH = new SlabHandler();
				boolean flag = SBH.endSlab(Integer.parseInt(key[0]),Integer.parseInt(key[1]),Integer.parseInt(key[2]));
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
	}

}
