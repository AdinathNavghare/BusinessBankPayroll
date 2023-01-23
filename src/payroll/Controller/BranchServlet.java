package payroll.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.BranchDAO;
import payroll.DAO.RoleDAO;
import payroll.Model.BranchBean;
import payroll.Model.RoleBean;

/**
 * Servlet implementation class BranchServlet
 */
@WebServlet("/BranchServlet")
public class BranchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		ArrayList<BranchBean> branchList = new ArrayList<BranchBean>();
		BranchDAO branchDAO = new BranchDAO();
		HttpSession session = request.getSession();
		/*if(action.equalsIgnoreCase("showBranch")){
			//System.out.println("in get");
		//	branchList = brnDAO.getBranchDetails();
			request.getRequestDispatcher("Branch.jsp?action=show").forward(request, response);
		} */
		if(action.equalsIgnoreCase("sort_branch"))
		{
			
			 branchList=branchDAO.getBranchList();
			
			// String sort_rname=request.getParameter("sort_rname")==null?"":request.getParameter("sort_rname");
			 String branchName=request.getParameter("branchName")==null?"":request.getParameter("branchName");
			// System.out.println("/////"+request.getParameter("sort_rname"));
			// System.out.println("*****"+request.getParameter("username"));
			 
			 ArrayList<BranchBean> sortedBranchList= new ArrayList<BranchBean>();
	
			
			 
			 for (BranchBean branchBean:branchList) {
			 	
			 	/*i=Rlbean.getROLEID();
			 	 rolename=roledao.getroleName(i);*/
			
			  if(!branchName.equals("")  && branchBean.getBranchName().toLowerCase().contains(branchName.toLowerCase()))
				 {sortedBranchList.add(branchBean);}
			 	}
			if(sortedBranchList.size()<=0)
			 {
				 sortedBranchList=branchList;
			 }
			 
			 
			 session.setAttribute("sortedBranchList", sortedBranchList);
			 response.sendRedirect("ShowBranches.jsp?action=sortedBranchList");
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		ArrayList<BranchBean> branchList = new ArrayList<BranchBean>();
		HttpSession session = request.getSession();
		BranchDAO brnachDAO = new BranchDAO();
		String todaysDate=request.getParameter("todaysDate");
		
		// time
		String firstShiftFrom =request.getParameter("firstShiftFromHours")+"."+request.getParameter("firstShiftFromMinutes");
		String firstShiftTo=request.getParameter("firstShiftToHours")+"."+request.getParameter("firstShiftToMinutes");
		String secondShiftFrom =request.getParameter("secondShiftFromHours")+"."+request.getParameter("secondShiftFromMinutes");
		String secondShiftTo=request.getParameter("secondShiftToHours")+"."+request.getParameter("secondShiftToMinutes");
		String firstCashFrom =request.getParameter("firstCashFromHours")+"."+request.getParameter("firstCashFromMinutes");
		String firstCashTo =request.getParameter("firstCashToHours")+"."+request.getParameter("firstCashToMinutes");
		String secondCashFrom =request.getParameter("secondCashFromHours")+"."+request.getParameter("secondCashFromMinutes");
		String secondCashTo =request.getParameter("secondCashToHours")+"."+request.getParameter("secondCashToMinutes");
		
		//testing weekoffs
		String[] offday = request.getParameterValues("offday");
		String days = "";
		if(offday != null){
			for( String day: offday){
				days = days+day;
			 }
		}
		//saturdays
		String[] alterSat = request.getParameterValues("altsatday");
		String week = "";
		if(alterSat != null){
			for( String day: alterSat){
				week = week+day;
			 }
		}
	
	
      
		
		
		int loggedEmployee=Integer.parseInt(request.getParameter("loggedEmployee"));
		if(action.equalsIgnoreCase("AddBranch")){
			BranchBean branchBean = new BranchBean();
			branchBean.setBranchCode(request.getParameter("branchCode")==null?0:Integer.parseInt(request.getParameter("branchCode")));
			branchBean.setBranchName(request.getParameter("branchName")==null?"":request.getParameter("branchName"));
			branchBean.setZone(request.getParameter("zone")==null?"":request.getParameter("zone"));
			branchBean.setAddress1(request.getParameter("address1")==null?"":request.getParameter("address1"));
			branchBean.setAddress2(request.getParameter("address2")==null?"":request.getParameter("address2"));
			branchBean.setAddress3(request.getParameter("address3")==null?"":request.getParameter("address3"));
			
			branchBean.setCity(request.getParameter("city")==null?0:Integer.parseInt(request.getParameter("city")));
			branchBean.setState(request.getParameter("state")==null?0:Integer.parseInt(request.getParameter("state")));
			
			branchBean.setPincode(request.getParameter("pincode")==null?0:Integer.parseInt(request.getParameter("pincode")));
			branchBean.setPhone1(request.getParameter("phone1")==null?"":request.getParameter("phone1"));	
			branchBean.setPhone2(request.getParameter("phone2")==null?"":request.getParameter("phone2"));	
			branchBean.setFax(request.getParameter("fax")==null?"":request.getParameter("fax"));	
			branchBean.setStartDate(request.getParameter("startDate")==null?"":request.getParameter("startDate"));
			branchBean.setEffectiveDate(request.getParameter("effectiveDate")==null?"":request.getParameter("effectiveDate"));
			// setting time
			branchBean.setFirstShiftFrom(Double.parseDouble(firstShiftFrom));
			branchBean.setFirstShiftTo(Double.parseDouble(firstShiftTo));
			branchBean.setSecondShiftFrom(Double.parseDouble(secondShiftFrom));
			branchBean.setSecondShiftTo(Double.parseDouble(secondShiftTo));
			branchBean.setFirstCashFrom(Double.parseDouble(firstCashFrom));
			branchBean.setFirstCashTo(Double.parseDouble(firstCashTo));
			branchBean.setSecondCashFrom(Double.parseDouble(secondCashFrom));
			branchBean.setSecondCashTo(Double.parseDouble(secondCashTo));
			
			
			//setting weekoff
			branchBean.setWoffday(days);
			branchBean.setAltersat(week);
			
			String siteStatus="";
			int isDeleted=0;
			
			if(Integer.parseInt( request.getParameter("siteStatus"))==1)
			{
				isDeleted=1;
				siteStatus="Closed";
			}
			else{
				isDeleted=0;
				siteStatus="Open";
			}
		
			//status and is deleted
			branchBean.setIsDeleted(isDeleted);
			branchBean.setSiteStatus(siteStatus);
			
			
			Boolean flag= brnachDAO.addBranch(branchBean,todaysDate,loggedEmployee);
		//	branchList = brnachDAO.getBranchDetails();
			session.setAttribute("brnList", branchList);
			if(flag)
			{
				response.sendRedirect("Branch.jsp?flag1=1");
			}
			
			
			
		}
		if(action.equalsIgnoreCase("EditBrnch")){
			BranchBean branchBean = new BranchBean();
				
			branchBean.setBranchCode(request.getParameter("branchCode")==null?0:Integer.parseInt(request.getParameter("branchCode")));
			branchBean.setBranchName(request.getParameter("branchName")==null?"":request.getParameter("branchName"));
			branchBean.setZone(request.getParameter("zone")==null?"":request.getParameter("zone"));
			branchBean.setAddress1(request.getParameter("address1")==null?"":request.getParameter("address1"));
			branchBean.setAddress2(request.getParameter("address2")==null?"":request.getParameter("address2"));
			branchBean.setAddress3(request.getParameter("address3")==null?"":request.getParameter("address3"));
			
			branchBean.setCity(request.getParameter("city")==null?0:Integer.parseInt(request.getParameter("city")));
			branchBean.setState(request.getParameter("state")==null?0:Integer.parseInt(request.getParameter("state")));
			
			branchBean.setPincode(request.getParameter("pincode")==null?0:Integer.parseInt(request.getParameter("pincode")));
			branchBean.setPhone1(request.getParameter("phone1")==null?"":request.getParameter("phone1"));	
			branchBean.setPhone2(request.getParameter("phone2")==null?"":request.getParameter("phone2"));	
			branchBean.setFax(request.getParameter("fax")==null?"":request.getParameter("fax"));	
			branchBean.setStartDate(request.getParameter("startDate")==null?"":request.getParameter("startDate"));
			branchBean.setEffectiveDate(request.getParameter("effectiveDate")==null?"":request.getParameter("effectiveDate"));
			
			// setting time
			branchBean.setFirstShiftFrom(Double.parseDouble(firstShiftFrom));
			branchBean.setFirstShiftTo(Double.parseDouble(firstShiftTo));
			branchBean.setSecondShiftFrom(Double.parseDouble(secondShiftFrom));
			branchBean.setSecondShiftTo(Double.parseDouble(secondShiftTo));
			branchBean.setFirstCashFrom(Double.parseDouble(firstCashFrom));
			branchBean.setFirstCashTo(Double.parseDouble(firstCashTo));
			branchBean.setSecondCashFrom(Double.parseDouble(secondCashFrom));
			branchBean.setSecondCashTo(Double.parseDouble(secondCashTo));
			
			
			//setting weekoff
			branchBean.setWoffday(days);
			branchBean.setAltersat(week);
			String siteStatus="";
			
			
			if(Integer.parseInt( request.getParameter("siteStatus"))==1)
			{
				siteStatus="Closed";
			}
			else{
			
				siteStatus="Open";
			}
		
			int siteIsdeleted=(Integer.parseInt( request.getParameter("siteStatus"))==1?1:0);
		
			branchBean.setSiteStatus(siteStatus);
			
			
			Boolean flag=brnachDAO.updateBranchDetails(branchBean,todaysDate,loggedEmployee,siteIsdeleted);
		//	branchList = brnachDAO.getBranchDetails();
			session.setAttribute("brnList", branchList);
			if(flag)
			{
				response.sendRedirect("Branch.jsp?flag1=1");
			}
			
			
			
		}
	}

}
