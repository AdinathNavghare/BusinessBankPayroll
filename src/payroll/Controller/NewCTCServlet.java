package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.CodeMasterHandler;
import payroll.DAO.TranHandler;
import payroll.Model.CodeMasterBean;
import payroll.Model.TranBean;

/**
 * Servlet implementation class NewCTCServlet
 */
@WebServlet("/NewCTCServlet")
public class NewCTCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewCTCServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	doPost(request, response);	//request.getRequestDispatcher("NewCTCServlet?action=checkCTC&EMPNO="+request.getParameter("EMPNO")).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession session = request.getSession();
			String user = session.getAttribute("name").toString();
			
			String action = request.getParameter("action");
			if(action.equalsIgnoreCase("save"))
			{
				String emp = request.getParameter("saveEmp");
				String[] employ = request.getParameter("saveEmp").split(":"); 
				int empno = Integer.parseInt(employ[1]);
				CodeMasterHandler CMH = new CodeMasterHandler();
				ArrayList<CodeMasterBean> incomeList = CMH.getCDMAST("1"); 	// Income Codes
				ArrayList<CodeMasterBean> deductList = CMH.getCDMAST("2"); 	// Deduction Codes
				incomeList.remove(incomeList.size()-1);	//	Removed 199
				incomeList.remove(incomeList.size()-1);//	Removed 999
				TranHandler TH = new TranHandler();
				int i=1;
				int trncd =0;
				int val=0;
				
				ArrayList<Integer> incVals = new ArrayList<Integer>();
				ArrayList<Integer> dedVals = new ArrayList<Integer>();
				incVals.add(0);
				dedVals.add(0);
				
				TranBean TB = new TranBean();
				TB.setEMPNO(empno);
				TB.setTRNCD(199);
				TB.setINP_AMT(Integer.parseInt(request.getParameter("saveCTC")));
				TB.setADJ_AMT(0);
				TB.setARR_AMT(0);
				TB.setCAL_AMT(0);
				TB.setNET_AMT(0);
				TB.setSRNO(0);
				TB.setCF_SW("*");
				TB.setUSRCODE(user);
				TH.checkAndInsertTran(TB);
				
				for(CodeMasterBean CMB : incomeList)
				{
					trncd = CMB.getTRNCD();
					val = Integer.parseInt(request.getParameter("incAmount"+i)==""?"0":request.getParameter("incAmount"+i));
					if(val != 0)
					{
						TB = new TranBean();
						TB.setEMPNO(empno);
						TB.setTRNCD(trncd);
						TB.setINP_AMT(val);
						TB.setADJ_AMT(0);
						TB.setARR_AMT(0);
						TB.setCAL_AMT(0);
						TB.setNET_AMT(0);
						TB.setSRNO(0);
						TB.setCF_SW("*");
						TB.setUSRCODE(user);
						
						TH.checkAndInsertTran(TB);
					}
					incVals.add(val);
					i++;
				}
				i=1;
				for(CodeMasterBean CMB : deductList)
				{
					trncd = CMB.getTRNCD();
					val = Integer.parseInt(request.getParameter("dedAmount"+i)==""?"0":request.getParameter("dedAmount"+i));
					if(val != 0)
					{
						TB = new TranBean();
						TB.setEMPNO(empno);
						TB.setTRNCD(trncd);
						TB.setINP_AMT(val);
						TB.setADJ_AMT(0);
						TB.setARR_AMT(0);
						TB.setCAL_AMT(0);
						TB.setNET_AMT(0);
						TB.setSRNO(0);
						TB.setCF_SW("*");
						TB.setUSRCODE(user);
						
						TH.checkAndInsertTran(TB);
					}
					dedVals.add(val);
					i++;
				}
				
				request.setAttribute("emp", emp);
				request.setAttribute("incVals", incVals);
				request.setAttribute("dedVals", dedVals);
				request.getRequestDispatcher("newCTC.jsp?action=1").forward(request, response);
			}
			else if(action.equalsIgnoreCase("check"))
			{
				try
				{
					String emp = request.getParameter("EMPNO");
					String[] employ = request.getParameter("EMPNO").split(":"); 
					int empno = Integer.parseInt(employ[1]);
					TranHandler TH = new TranHandler();
					
					ArrayList<String> incTranVals = TH.getCTCTranValues(empno, 1);
					ArrayList<String> dedTranVals = TH.getCTCTranValues(empno, 2);
					
					request.setAttribute("emp", emp);
					request.setAttribute("incTranVals", incTranVals);
					request.setAttribute("dedTranVals", dedTranVals);
					//request.getRequestDispatcher("newCTC.jsp?action=2").forward(request, response);
					request.getRequestDispatcher("trialCTC.jsp?action=2").forward(request, response);
				}
				catch(Exception e)
				{
					response.sendRedirect("newCTC.jsp");
				}
			}
			else if(action.equalsIgnoreCase("checkCTC"))
			{
				try
				{
					String flag=request.getParameter("flag")==null?"0":request.getParameter("flag");
					String emp = request.getParameter("EMPNO");
					String[] employ = request.getParameter("EMPNO").split(":"); 
					int empno = Integer.parseInt(employ[2]);
					TranHandler TH = new TranHandler();
					
					ArrayList<TranBean> incTranVals = TH.getCTCDISPLAY(empno);
					
					request.setAttribute("emp", empno);
					request.setAttribute("incTranVals", incTranVals);
					//request.getRequestDispatcher("CheckCTC.jsp?action=2").forward(request, response);
					System.out.println("INTO CHCK CTC in newCTCSRVLT");
					request.getRequestDispatcher("trialCTC.jsp?flag="+flag+"&action=2").forward(request, response);
				}
				catch(Exception e)
				{
					System.out.println("INTO EXCEPTION in newCTCSRVLT");
					response.sendRedirect("CheckCTC.jsp");
				}
			}
		}
		catch(Exception e)
		{
			response.sendRedirect("login.jsp?action=0");
		}
	}

}
