package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.SystemOutLogger;

import com.itextpdf.text.log.SysoCounter;

/*import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
*/
import payroll.Model.AddTaskBean;
import payroll.DAO.AddTaskDao;
import payroll.DAO.ConnectionManager;
import net.sf.json.JSONObject;
/**
 * Servlet implementation class AddTaskServlet
 */
//@Controller
@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
    
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}


		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("text/html");
		
		HttpSession session=request.getSession();
		String action=request.getParameter("action");
	
		System.out.println(action);
		
		if(action.equalsIgnoreCase("addtask")){
		AddTaskBean taskbean=new AddTaskBean();
		
		taskbean.setPROJECT((request.getParameter("pnm")==null?"":request.getParameter("pnm")));
		taskbean.setTYPE(request.getParameter("type"));	
		taskbean.setNAME(request.getParameter("name"));
		taskbean.setSTATUS(request.getParameter("status"));
		taskbean.setPRIORITY(request.getParameter("priority"));
		//taskbean.setSUBTASK((request.getParameter("subtask")==null?"":request.getParameter("name")));
		
		/*String s = "";
		int i;
		String Assin [] = request.getParameterValues("tasks[assigned_to][]");
		//String Assin [] = request.getParameterValues("assignedTo");
		
		if(Assin !=null)
		{
			for(String t : Assin)
			{
				s=s+""+t;
				s=s+',';
			}
		}
		
		taskbean.setASSIGNED_TO(s);*/
		
		
		//taskbean.setASSIGNED_TO(request.getParameter("assignedTo"));
		
		//taskbean.setCREATED_BY(request.getParameter("created_by"));
		taskbean.setESTIMATED_TIME(request.getParameter("estiTime"));
		/*empbean.setDOB(rs.getDate("DOB") == null ? "" : dateFormat(rs
				.getDate("DOB")));*/
		
		taskbean.setSTART_DATE(request.getParameter("startdate"));
		taskbean.setDUE_DATE(request.getParameter("duedate"));
		/*SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	       Date date= formatter.parse(stdt);
	        du=formatter.parse(dudt);*/
		taskbean.setDISCRIPTION(request.getParameter("description"));
		
		
/*		taskbean.setPROGRESS(Integer.parseInt(request.getParameter("progress")));*/
		//PROJECT,TASK_TYPE,NAME,TASK_STATUS,TASK_PRIORITY,LABEL,ESTIMATED_TIME,TASK_START_DATE,DUE_DATE,DISCRIPTION,ProjectId
		
		boolean flag = AddTaskDao.addTask(taskbean);
		if(flag){
			response.sendRedirect("AddTask.jsp");
		}else
		{
			response.sendRedirect("ViewTask.jsp");
		}

		}
	
	  /* else if(action.equalsIgnoreCase("addtime")){
		 System.out.println("in time action");
		AddTaskBean taskbean=new AddTaskBean();
		
		taskbean.setESTIMATED_TIME(request.getParameter("estiTime"));
		taskbean.setSTART_DATE(request.getParameter("startdate"));
		taskbean.setDUE_DATE(request.getParameter("duedate"));
		taskbean.setPROGRESS(Integer.parseInt(request.getParameter("progress")));
		
		boolean flag=AddTaskDao.addTime(taskbean);
		if(flag==true){
			response.sendRedirect("TaskAttach.jsp");
		}else{
			response.sendRedirect("Time.jsp");
		}
	}*/
	  
		
		if(action.equalsIgnoreCase("ShowTask")){
			
			//System.out.println("AddTaskServlet in show action...........");
			AddTaskDao adao=new AddTaskDao();
			AddTaskBean taskbean=new AddTaskBean();
			int tt=Integer.parseInt(request.getParameter("task"));
			int pid=Integer.parseInt(request.getParameter("proid"));				
			
			String pro=request.getParameter("pronm");
			taskbean.setPROJECT(pro);
			taskbean.setTYPE(request.getParameter("type")==null?"":request.getParameter("type"));	
			taskbean.setNAME(request.getParameter("name")==null?"":request.getParameter("name"));
			taskbean.setSTATUS(request.getParameter("status")==null?"":request.getParameter("status"));
			taskbean.setPRIORITY(request.getParameter("priority")==null?"":request.getParameter("priority"));
			taskbean.setSUBTASK(request.getParameter("subtask")==null?"":request.getParameter("subtask"));
			
			/*String s = "";
			int i;
			String subtask [] = request.getParameterValues("subtask");
			System.out.println("hi task edit");
			
			if(subtask !=null)
			{
				for(String t : subtask)
				{
					s = s +""+ t;
						System.out.println(s);
				}
			}*/
			
			
			/*String s = "";
			int i;
			String Assin [] = request.getParameterValues("tasks[assigned_to][]");
			if(Assin !=null)
			{
				for(String t : Assin)
				{
					s=s+""+t;
					s=s+',';
					  String split = s;

	     				StringTokenizer tokenizer = new StringTokenizer(split, ",");
	     
	     				while (tokenizer.hasMoreTokens()) {
	         			
	         			String splitVal= (String) tokenizer.nextToken();
	         			System.out.println("Splited value is : "+splitVal);
	         			taskbean.setASSIGNED_TO(splitVal);
	     		}        
                     
				}
			}
			
			//taskbean.setASSIGNED_TO(splitVal);
			
			taskbean.setASSIGNED_TO(s);*/
			
			//taskbean.setDISCRIPTION(request.getParameter("description")==null?"":request.getParameter("description"));
			
			taskbean.setESTIMATED_TIME(request.getParameter("estiTime"));
			taskbean.setSTART_DATE(request.getParameter("startdate"));
			taskbean.setDUE_DATE(request.getParameter("duedate"));  
			taskbean.setDISCRIPTION(request.getParameter("description"));
						
			boolean flag;
			try {
				flag = AddTaskDao.editTask(taskbean, tt, pro,pid);
				if(flag){
					response.sendRedirect("ViewTask.jsp");
				}else
				{
					response.sendRedirect("AddTask.jsp");
				}
			} catch (SQLException e) {
			 
				e.printStackTrace();
			}
		}
		
		/*if(action.equalsIgnoreCase("EditTime"))
		{
			AddTaskDao adao=new AddTaskDao();
			AddTaskBean taskbean=new AddTaskBean();
			int tt=Integer.parseInt(request.getParameter("task"));
			System.out.println(tt);
			 
			taskbean.setESTIMATED_TIME(request.getParameter("estiTime"));
			taskbean.setSTART_DATE(request.getParameter("startdate"));
			taskbean.setDUE_DATE(request.getParameter("duedate"));
			taskbean.setPROGRESS(Integer.parseInt(request.getParameter("progress")));
			
			
			boolean flag;
			try {
					flag=AddTaskDao.updateTime(taskbean,tt);
					
					if(flag){
						response.sendRedirect("ViewTask.jsp?");
					}else{
						response.sendRedirect("AddTask.jsp");
					}
				} catch (SQLException e) {
				
					e.printStackTrace();
			}
			
		}*/
		
		
		if(action.equalsIgnoreCase("Delete"))
		{
			
			int tt=Integer.parseInt(request.getParameter("id"));
						
			int flag=AddTaskDao.DeleteTask(tt);
			
                PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");		
				out1.write(String.valueOf(flag));
				
		}
		
		if(action.equalsIgnoreCase("AddSubTask")){
			
			AddTaskDao adao=new AddTaskDao();
			AddTaskBean taskbean=new AddTaskBean();
			int tt=Integer.parseInt(request.getParameter("task"));
			//System.out.println(tt);
			
			taskbean.setPROJECT(request.getParameter("pnm")==null?"":request.getParameter("pnm"));
			taskbean.setNAME(request.getParameter("name")==null?"":request.getParameter("name"));
			taskbean.setSUBTASK(request.getParameter("subtask")==null?"":request.getParameter("subtask"));
			System.out.println(request.getParameter("name"));
			
			boolean flag=AddTaskDao.AddSubTask(taskbean,tt);
			
			if(flag){
				System.out.println("sub task added successfully.........");
				response.sendRedirect("AddTask.jsp");
			}else
			{
				System.out.println("fail to add sub task ........");
				response.sendRedirect("ViewTask.jsp");
			}
		}
		
           if(action.equalsIgnoreCase("editsubtask")){
        	 
        	 String Sid=null,Stask=null;
        	AddTaskBean taskbean=new AddTaskBean();
			int tt=Integer.parseInt(request.getParameter("task"));
			//System.out.println("tid : "+tt);
			
			String a=request.getParameter("subtask");
			//System.out.println("subtask : "+a);
			
			String[] data = a.split(",");
			
			for(int i=0;i<data.length;i++){
				 Sid=data[0];
				 Stask=data[1];
			}
			
			int subid=Integer.parseInt(Sid);
			taskbean.setSUBTASK(request.getParameter("othrst")=="" ? "": request.getParameter("othrst"));
			System.out.println(request.getParameter("othrst"));
			
			boolean flag;
			
				flag = AddTaskDao.editSubTask(taskbean, tt,subid);
				if(flag){
					System.out.println("Subtask updated successfully");
					response.sendRedirect("AddTask.jsp");
				}else
				{
					System.out.println("fail to update subtask");
					response.sendRedirect("ViewTask.jsp");
				}
			
		}
           
           /*if(action.equalsIgnoreCase("removerecord"))
   		    {
   			
		   			int tt=Integer.parseInt(request.getParameter("id"));		   						
		   			int flag=AddTaskDao.removerecordTask(tt);
		                PrintWriter out1 = response.getWriter();
		   				response.setContentType("text/html");
		   				out1.write(String.valueOf(flag));
   				
   		    }*/
           
           if(action.equalsIgnoreCase("updatetask")){
   			
   			//System.out.println("AddTaskServlet in show action...........");
   			AddTaskDao adao=new AddTaskDao();
   			AddTaskBean taskbean=new AddTaskBean();
   			int tt=Integer.parseInt(request.getParameter("task"));
   			//System.out.println(tt);
   			
   			taskbean.setPROJECT(request.getParameter("pnm")==null?"":request.getParameter("pnm"));
   			taskbean.setTYPE(request.getParameter("type")==null?"":request.getParameter("type"));	
   			taskbean.setNAME(request.getParameter("name")==null?"":request.getParameter("name"));
   			taskbean.setSTATUS(request.getParameter("status")==null?"":request.getParameter("status"));
   			taskbean.setPRIORITY(request.getParameter("priority")==null?"":request.getParameter("priority"));
   			taskbean.setSUBTASK(request.getParameter("subtask")==null?"":request.getParameter("subtask"));
   			
   			taskbean.setESTIMATED_TIME(request.getParameter("estiTime"));
   			taskbean.setSTART_DATE(request.getParameter("startdate"));
   			taskbean.setDUE_DATE(request.getParameter("duedate"));  
   			taskbean.setDISCRIPTION(request.getParameter("description"));
   			
   			boolean flag;
   			flag = AddTaskDao.updateTask(taskbean, tt);
			if(flag){
				response.sendRedirect("AddTask.jsp");
			}else
			{
				response.sendRedirect("ViewTask.jsp");
			}
   		}
           
           else if (action.equalsIgnoreCase("checktask")) {
   			
   			Integer flags = null;
   			
   			
   			try {
   				Connection conn = ConnectionManager.getConnection();
   				String name = request.getParameter("name");
   				String pnm = request.getParameter("pnm");
   				System.out.println("PROJECT_NAME is ...:"+pnm);
   				//System.out.println("TASK_NAME...:  "+name);
   				   				
   				String overlap = "select  count(*) as count from Task where TASK_NAME  ='"+ name +"' and PROJECT_NAME ='"+ pnm +"'";
   				
   				Statement stmt = conn.createStatement();
				ResultSet rslt = stmt.executeQuery(overlap);
				while (rslt.next()) {
					
					flags = rslt.getInt("count");
				}
				conn.close();

				PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");
				out1.write(flags.toString());

			} catch (SQLException e) {
				e.printStackTrace();
			}

   		}
           
	}
}
