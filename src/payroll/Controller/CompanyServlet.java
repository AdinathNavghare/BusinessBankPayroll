package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.CompanyHandler;
import payroll.Model.CompBean;

/**
 * Servlet implementation class CompanyServlet
 */
@WebServlet("/CompanyServlet")
public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyServlet() {
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
		
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		CompanyHandler comphdlr = new CompanyHandler();
		
	if(action.equalsIgnoreCase("add"))
		{
			CompBean bn=new CompBean();
			bn.setCname(request.getParameter("cname") == null?"":request.getParameter("cname"));
			bn.setCadd(request.getParameter("cadd"));
			bn.setCity(request.getParameter("city"));
			bn.setCountry(request.getParameter("select"));
			bn.setPhno(request.getParameter("phoneno") == null?"":request.getParameter("phoneno"));
			bn.setPincode(request.getParameter("pincode") == ""?0:Integer.parseInt(request.getParameter("pincode")));
			bn.setEmail(request.getParameter("email") == null?"":request.getParameter("email"));
			bn.setWebsite(request.getParameter("website") == null?"":request.getParameter("website"));
			bn.setFrmdate(request.getParameter("frmdate") == null?"":request.getParameter("frmdate"));
			bn.setPfno(request.getParameter("pfno") == null?"":request.getParameter("pfno"));
			bn.setEsicno(request.getParameter("esicno") == null?"":request.getParameter("esicno"));
			bn.setTanno(request.getParameter("tanno") == null?"":request.getParameter("tanno"));
			bn.setPanno(request.getParameter("panno") == null?"":request.getParameter("panno"));
			bn.setDomainname(request.getParameter("domain") == null?"":request.getParameter("domain"));
			bn.setCompcode(request.getParameter("ccode") == ""?0:Integer.parseInt(request.getParameter("ccode")));
			String[] offday = request.getParameterValues("offday");
			String days = "";
			if(offday != null){
				for( String day: offday){
					days = days+" "+day;
				 }
			}
			bn.setWoffday(days);
			String[] alterSat = request.getParameterValues("altsatday");
			String week = "";
			if(alterSat != null){
				for( String day: alterSat){
					week = week+" "+day;
				 }
			}
			bn.setAltsat(week);
			int i = comphdlr.store(bn);
			if(i == 0){
				System.out.println("Failed To store.");				
				response.sendRedirect("Comp_Details.jsp");
			}
			else{
				RequestDispatcher rd=request.getRequestDispatcher("/showCompDetails.jsp");
				rd.forward(request, response);
			}
	}
if(action.equalsIgnoreCase("update")){
		
		CompBean bn1 = new CompBean();
		ArrayList<CompBean> complist = new ArrayList<>();
		boolean flag = false;	
		bn1.setCname(request.getParameter("cname") == null?"":request.getParameter("cname"));
		bn1.setCadd(request.getParameter("cadd"));
		bn1.setCity(request.getParameter("city"));
		bn1.setCountry(request.getParameter("select"));
		bn1.setPhno(request.getParameter("phoneno") == null?"--":request.getParameter("phoneno"));
		bn1.setPincode(request.getParameter("pincode") == ""?0:Integer.parseInt(request.getParameter("pincode")));
		bn1.setEmail(request.getParameter("email") == null?"":request.getParameter("email"));
		bn1.setWebsite(request.getParameter("website") == null?"--":request.getParameter("website"));
		bn1.setFrmdate(request.getParameter("frmdate") == null?"":request.getParameter("frmdate"));
		bn1.setPfno(request.getParameter("pfno") == null?"--":request.getParameter("pfno"));
		bn1.setEsicno(request.getParameter("esicno") == null?"--":request.getParameter("esicno"));
		bn1.setTanno(request.getParameter("tanno") == null?"--":request.getParameter("tanno"));
		bn1.setPanno(request.getParameter("panno") == null?"--":request.getParameter("panno"));
		bn1.setDomainname(request.getParameter("domain") == null?"":request.getParameter("domain"));
		bn1.setCompcode(request.getParameter("ccode")==""?0:Integer.parseInt(request.getParameter("ccode")));
		String[] offday = request.getParameterValues("offday");
		String days = "";
		if(offday != null){			
			for( String day: offday){
				days = days+" "+day;
			 }
		}
		bn1.setWoffday(days);
		String[] alterSat = request.getParameterValues("altsatday");
		String week = "";
		if(alterSat != null){
			for( String day: alterSat){
				week = week+" "+day;
			 }
		}
		bn1.setAltsat(week);
		flag = comphdlr.updateDetails(bn1);
		complist = comphdlr.getDetails();
		session.setAttribute("complist", complist);
		if(flag){
			 response.sendRedirect("showCompDetails.jsp");
		}
		else{
			 response.sendRedirect("showCompDetails.jsp");
		}
	}
	}
}