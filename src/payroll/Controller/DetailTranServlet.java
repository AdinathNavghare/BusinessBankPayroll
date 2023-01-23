package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.Programability;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.LMH;
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

/**
 * Servlet implementation class DetailTranServlet
 */
@WebServlet("/DetailTranServlet")
public class DetailTranServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailTranServlet() {
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
		

		HttpSession session= request.getSession();	
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		/*TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean();
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();*/
	    
	    
	    
	    if (action.equalsIgnoreCase("editTranValues1")) 
		{
	    	String proj=request.getParameter("pp")==null?"":request.getParameter("pp");
	    	String date=request.getParameter("date1")==null?"":request.getParameter("date1");
	    	try
	    	{
	    	//System.out.println("into servlet editTranValues1");
	    	ArrayList<Integer> cds= (ArrayList<Integer>)session.getAttribute("cds");
		//	ArrayList<TranBean> al=new ArrayList<TranBean>();
			//al=(ArrayList<TranBean>)session.getAttribute("projEmpNolist");
			
			String[] adv = request.getParameterValues("advValue");
			Float advvals[] = new Float[adv.length];
			String[] loan = request.getParameterValues("loanValue");
			Float loanvals[] = new Float[loan.length];
			String[] tds = request.getParameterValues("tdsValue");
			Float tdsvals[] = new Float[tds.length];
			String[] mob = request.getParameterValues("mobValue");
			Float mobvals[] = new Float[mob.length];
			String[] othr = request.getParameterValues("othrValue");
			Float othrvals[] = new Float[othr.length];
			String[] ea1 = request.getParameterValues("ea1Value");
			Float ea1vals[] = new Float[ea1.length];
			String[] ea2 = request.getParameterValues("ea2Value");
			Float ea2vals[] = new Float[adv.length];
			String[] pl = request.getParameterValues("plValue");
			Float plvals[] = new Float[adv.length];
			String[] abs = request.getParameterValues("absValue");
			Float absvals[] = new Float[adv.length];
			//String[] tot = request.getParameterValues("totValue");
			//Float totvals[] = new Float[adv.length];
			
			for(int i=0; i<adv.length; i++)
			{
				advvals[i] = Float.parseFloat(adv[i].trim());
				loanvals[i] = Float.parseFloat(loan[i].trim());
				tdsvals[i] = Float.parseFloat(tds[i].trim());
				mobvals[i] = Float.parseFloat(mob[i].trim());
				othrvals[i] = Float.parseFloat(othr[i].trim());
				ea1vals[i] = Float.parseFloat(ea1[i].trim());
				ea2vals[i] = Float.parseFloat(ea2[i].trim());
				plvals[i] = Float.parseFloat(pl[i].trim());
				absvals[i] = Float.parseFloat(abs[i].trim());
				//totvals[i] = Float.parseFloat(tot[i].trim());
			//	System.out.print("\t\t"+cds+"\t");
			//	System.out.println(al.get(i).getEMPNO()+"\t"+advvals[i]+"\t"+ loanvals[i]+"\t"+ tdsvals[i]+"\t"+mobvals[i] +"\t"+othrvals[i]+"\t"+ea1vals[i]+"\t"+ea2vals[i]+"\t"+plvals[i]+"\t"+absvals[i]+"\t"+totvals[i]+"\n");
				
			}
			boolean flag=false;
			ArrayList<Float[]> li=new ArrayList<>(Arrays.asList(advvals, loanvals,tdsvals ,mobvals ,othrvals ,ea1vals ,ea2vals ,absvals));
			for(int index=0;index<cds.size();index++)
			{
				
			//	System.out.print(""+cds.get(index)+"\t"+advvals);
			TranHandler thr = new TranHandler();
			
			flag = thr.updateEmpTranNew1(Integer.parseInt(""+cds.get(index)), li.get(index), (ArrayList<TranBean>)session.getAttribute("projEmpNolist"));
			}
			
			
			if(flag)
			{
				//Programability.update_paytran_history(session.getAttribute("EMPNO").toString(),"empDetailtran.jsp");
				
				LMH th = new LMH();
				flag =th.updateLeaveBal((ArrayList<TranBean>)session.getAttribute("projEmpNolist"),plvals,date);
				if(flag)
				{
				proj=proj.replaceAll("&", "and");
				response.sendRedirect("empDetailTran.jsp?action=afterEdit&proj="+proj+"&flag=3");
				}
				else
				{
					response.sendRedirect("empDetailTran.jsp?flag=4");
				}
			}
			else
			{
				response.sendRedirect("empDetailTran.jsp?flag=4");
			}
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		proj=proj.replace("&", "and");
				response.sendRedirect("empDetailTran.jsp?action=afterEdit&proj="+proj+"&flag=4");
	    		
	    	}
		}
	    
	    
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
