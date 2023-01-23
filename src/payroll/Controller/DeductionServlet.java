package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.DeductHandler;
import payroll.DAO.EmpOffHandler;
import payroll.Model.DeductBean;
import payroll.Model.EmpOffBean;


/**
 * Servlet implementation class DeductionServlet
 */
@WebServlet("/DeductionServlet")
public class DeductionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeductionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("delete"))
		{
			DeductHandler DH = new DeductHandler();
			String[] key = request.getParameter("key").split("-");
			int empno = Integer.parseInt(key[0]);
			int trncd = Integer.parseInt(key[1]);
			int srno = Integer.parseInt(key[2]);
			boolean flag = DH.delDedction(empno, trncd, srno);
			if(flag)
			{
				ArrayList<DeductBean> Dlist = DH.getDeduction(empno);
				request.setAttribute("Dlist", Dlist);
				request.getRequestDispatcher("Deduction.jsp?action=showList&empno="+empno).forward(request, response);
			}
			else
			{
				System.out.println("Error in saving deduction");
			}
		}else if(action.equalsIgnoreCase("homeforDeduction")) {
			int empno = Integer.parseInt((request.getParameter("no")==null)?"0":request.getParameter("no"));
			request.getRequestDispatcher("Deduction.jsp?action=showList&empno="+empno).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession se=request.getSession();
		String action= request.getParameter("action");
		String uid=se.getAttribute("EMPNO").toString();	
		String user_code = se.getAttribute("UID").toString();
		if(action.equalsIgnoreCase("addnew"))
		{
			EmpOffHandler EH=new EmpOffHandler();
			EmpOffBean offBean=new EmpOffBean();
			DeductBean DB = new DeductBean();
			DeductHandler DH = new DeductHandler();
			int empno = Integer.parseInt(request.getParameter("empno"));
			offBean= EH.getEmpOfficAddInfo(request.getParameter("empno"));
			int trncd = Integer.parseInt(request.getParameter("select.trncd"));
			int srno = DH.getMaxSrno(empno, trncd)+1;
			DB.setEMPNO(empno);
			DB.setTRNCD(trncd);
			DB.setSRNO(srno);
			DB.setAC_NO(Long.parseLong(request.getParameter("acno")));
			DB.setSANC_DATE(request.getParameter("sancDate"));
			DB.setSANC_AMT(Integer.parseInt(request.getParameter("sacnAmt")));
			DB.setCUMUYN(request.getParameter("select.cuml"));
			DB.setACTYN(request.getParameter("select.active"));
			DB.setAMOUNT((trncd==205?Integer.parseInt(request.getParameter("sacnAmt")):Float.parseFloat(request.getParameter("install"))));
			DB.setSUBSYS_CD((trncd==205?(request.getParameter("acno")):request.getParameter("subSysCode")));
			DB.setBODSANCNO((trncd==205?(request.getParameter("acno")):request.getParameter("sancNo")));
			DB.setINT_RATE(0.0f);
			DB.setREPAY_START((trncd==205?(request.getParameter("sancDate")):request.getParameter("startDate")));
			DB.setEND_DATE((trncd==205?(request.getParameter("sancDate")):request.getParameter("endDate")));
			DB.setNo_Of_Installment((trncd==205?Integer.parseInt(request.getParameter("sacnAmt")):Integer.parseInt(request.getParameter("Installments"))));
			//DB.setPrj_srno(offBean.getPrj_srno());
			DB.setPrj_srno(Integer.parseInt(request.getParameter("branch")));
			DB.setDesig(offBean.getDESIG());
			boolean flag = DH.addDeduction(DB,uid);
			if(flag)
			{
			
				ArrayList<DeductBean> Dlist = DH.getDeduction(empno);
				se.setAttribute("Dlist", Dlist);
				request.getRequestDispatcher("Deduction.jsp?action=showList&empno="+empno).forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("Deduction.jsp?flag=4").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("list"))
		{
			try
			{
				String []empno1 = request.getParameter("EMPNO").split(":");
				int empno = Integer.parseInt(empno1[2].trim());
				DeductHandler DH = new DeductHandler();
				System.out.println("-------------"+request.getParameter("EMPNO"));
				
				ArrayList<DeductBean> Dlist = DH.getDeduction(empno);
				System.out.println("============="+Dlist.size());
				se.setAttribute("Dlist", Dlist);
				request.getRequestDispatcher("Deduction.jsp?action=showList&empno="+empno).forward(request, response);
			}
			catch(Exception e)
			{
				response.sendRedirect("Deduction.jsp");
			}
		}
		else if(action.equalsIgnoreCase("details"))
		{
			String[] key = request.getParameter("key").split("-");
			int empno = Integer.parseInt(key[0]);
			int trncd = Integer.parseInt(key[1]);
			int srno = Integer.parseInt(key[2]);
			DeductHandler DH = new DeductHandler();
			DeductBean DB = DH.getSingleDed(empno, trncd, srno);
		}
		
		else if(action.equalsIgnoreCase("check_table_alw"))
		{ 
		
		 
		try
		   {
			System.out.println("akshaynikam");
			String flags = "0";
			System.out.println("akshay006" );
			//String []empno1 = request.getParameter("EMPNO").split(":");
			//System.out.println("akshay006"+empno1);
			String empno = request.getParameter("empno");
			System.out.println("akshay006"+empno);
			
   	          Connection conn=ConnectionManager.getConnection();
   	       //  String empcd =  request.getParameter("empcd");
 	          String overlap="select  inp_amt from paytran where empno  ="+empno+" and trncd=129 ";
 	         System.out.println("akshay"+overlap);
             Statement stmt=conn.createStatement();
           	 ResultSet rslt=stmt.executeQuery(overlap);
              while(rslt.next())
		       {
			     //conn.commit();
            	  flags= (String) (rslt.getString("inp_amt")==null?"0":rslt.getString("inp_amt"));
			     //flags=(Integer)(rslt.getString("inp_amt")==null ?0:rslt.getString("inp_amt"));
		       }
		      conn.close();
		      System.out.println("akshayflags"+flags);
				    PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
				    out1.write(flags.toString());
						 
			     
            } 
  	     catch (SQLException e) 
  	       {
	         e.printStackTrace();
	        }
			
		}
		else if(action.equalsIgnoreCase("addtableallws"))
		{ 
		
		 
		try
		   {
			System.out.println("akshaynikam");
			 
			System.out.println("akshay006" );
			String []empno1 = request.getParameter("EMPNO").split(":");
			System.out.println("akshay007"+empno1);
			//Integer.parseInt(empno1[2].trim());
			String empno = empno1[2].trim();
			String amt = request.getParameter("amt")==null?"0":request.getParameter("amt");
			System.out.println("akshay008"+empno);
			System.out.println("akshay009"+amt);
			
   	          Connection conn=ConnectionManager.getConnection();
   	        String trndt =" "; 
 	          String overlap="select  trndt from paytran where empno  ="+empno+" and trncd=101 ";
 	         System.out.println("akshay"+overlap);
             Statement stmt=conn.createStatement();
           	 ResultSet rslt=stmt.executeQuery(overlap);
              while(rslt.next())
		       {  
            	  trndt=  rslt.getString("trndt");
			      }
              System.out.println(
             		 " if exists (select *   from paytran where TRNCD =129 and EMPNO ="+ empno +")  "+
             		 " update paytran set inp_amt= "+ amt +" where TRNCD =129 and EMPNO ="+ empno +"   "+
             		 "  else INSERT INTO [paytran] ([TRNDT],[EMPNO],[TRNCD],[SRNO] ,[INP_AMT],[CAL_AMT] ,[ADJ_AMT]    "+
             		 " ,[ARR_AMT],[NET_AMT] ,[CF_SW] ,[USRCODE],[UPDDT],[STATUS]) VALUES   "+
             		 " ('"+ trndt +"' ,"+ empno +",129 ,0, "+ amt +" ,0,0,0 ,0 ,'*','' ,'"+ trndt +"' ,'N')   "
             		  ) ;
              stmt.executeUpdate(
            		 " if exists (select *   from paytran where TRNCD =129 and EMPNO ="+ empno +")  "+
            		 " update paytran set inp_amt= "+ amt +" where TRNCD =129 and EMPNO ="+ empno +"   "+
            		 "  else INSERT INTO [paytran] ([TRNDT],[EMPNO],[TRNCD],[SRNO] ,[INP_AMT],[CAL_AMT] ,[ADJ_AMT]    "+
            		 " ,[ARR_AMT],[NET_AMT] ,[CF_SW] ,[USRCODE],[UPDDT],[STATUS]) VALUES   "+
            		 " ('"+ trndt +"' ,"+ empno +",129 ,0, "+ amt +",0,0,0 ,0 ,'*','' ,'"+ trndt +"' ,'N')   "
            		  ) ;
		      conn.close();
		      
			     
            } 
  	     catch (SQLException e) 
  	       {
	         e.printStackTrace();
	        }
		response.sendRedirect("table_allowance.jsp?status=yes");
		}
		
		
		else if(action.equalsIgnoreCase("modify"))
		{
			int empno=Integer.parseInt(request.getParameter("empno"));
			int trncd=Integer.parseInt(request.getParameter("trncd"));
			int srno=Integer.parseInt(request.getParameter("srno"));
			
			DeductBean DB= new DeductBean();
			DeductHandler DH = new DeductHandler();
			
			DB.setAC_NO(Long.parseLong(request.getParameter("acno")));
			DB.setSANC_DATE(request.getParameter("sancDate"));
			DB.setSANC_AMT(Integer.parseInt(request.getParameter("sacnAmt")));
			DB.setCUMUYN(request.getParameter("select.cuml"));
			DB.setACTYN(request.getParameter("select.active"));
			DB.setAMOUNT((trncd==205?Integer.parseInt(request.getParameter("sacnAmt")):Float.parseFloat(request.getParameter("install"))));
			DB.setSUBSYS_CD((trncd==205?(request.getParameter("acno")):request.getParameter("subSysCode")));
			DB.setBODSANCNO((trncd==205?(request.getParameter("acno")):request.getParameter("sancNo")));
			DB.setINT_RATE(0.0f);
			DB.setREPAY_START((trncd==205?(request.getParameter("sancDate")):request.getParameter("startDate")));
			DB.setEND_DATE((trncd==205?(request.getParameter("sancDate")):request.getParameter("endDate")));
			DB.setNo_Of_Installment((trncd==205?Integer.parseInt(request.getParameter("sacnAmt")):Integer.parseInt(request.getParameter("intRate"))));
			
			DB.setPrj_srno(Integer.parseInt(request.getParameter("branch")));
			
			/*DB.setAMOUNT(Float.parseFloat(request.getParameter("install")));
			DB.setSUBSYS_CD(request.getParameter("subSysCode"));
			DB.setAC_NO(Long.parseLong(request.getParameter("acno")));
			DB.setBODSANCNO(request.getParameter("sancNo"));
			DB.setSANC_DATE(request.getParameter("sancDate"));
			DB.setSANC_AMT(Integer.parseInt(request.getParameter("sacnAmt")));
			DB.setNo_Of_Installment(Integer.parseInt(request.getParameter("intRate")));
			DB.setREPAY_START(request.getParameter("startDate"));
			DB.setEND_DATE(request.getParameter("endDate"));
			DB.setCUMUYN(request.getParameter("select.cuml"));
			DB.setACTYN(request.getParameter("select.active"));*/
			boolean flag = DH.updateDeduction(empno, trncd, srno, DB,uid);
			if(flag)
			{
				response.sendRedirect("updateDeduction.jsp?action=close&key="+empno+"-"+trncd+"-"+srno);
			}
			else
			{
				response.sendRedirect("updateDeduction.jsp?action=keep&key="+empno+"-"+trncd+"-"+srno);
			}
		}
	}
}