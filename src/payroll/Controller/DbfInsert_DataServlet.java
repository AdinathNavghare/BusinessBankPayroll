package payroll.Controller;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.Calculate;
import payroll.DAO.ConnectionManager;
import payroll.DAO.SearchDAO;
import payroll.DAO.UsrHandler;
import payroll.Model.SearchBean;

import Dbf.DbfDataType;
import Dbf.DbfField;
import Dbf.DbfHeader;
import Dbf.DbfReader;
import Dbf.StringUtils;

/**
 * Servlet implementation class Kuch_Bhi
 */
@WebServlet("/DbfInsert_DataServlet")
public class DbfInsert_DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbfInsert_DataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String action = request.getParameter("action")==null?"":request.getParameter("action");

		if(action.equalsIgnoreCase("getdata")){
			ArrayList<SearchBean> list = new ArrayList<SearchBean>();
			String date = request.getParameter("date");
			String[] month = date.split("-");
			SearchDAO sdao = new SearchDAO();
			list = sdao.getRecords(Calculate.getMonth(date),month[2]);
			request.setAttribute("list", list);
			request.setAttribute("date", date);
			request.getRequestDispatcher("SearchRecords.jsp?action=show").forward(request, response);
		}else if(action.equalsIgnoreCase("getattend")){
			ArrayList<SearchBean> list = new ArrayList<SearchBean>();
			String date = request.getParameter("date");
			String[] month = date.split("-");
			SearchDAO sdao = new SearchDAO();
			list = sdao.getAttendRecords(Calculate.getMonth_DBF(month[1]),month[2],date);
			request.setAttribute("list", list);
			request.setAttribute("date", date);
			request.getRequestDispatcher("attendanceDisp.jsp?action=show").forward(request, response);
		}
		else if(action.equalsIgnoreCase("saveData"))
		{	
		
		Connection con = ConnectionManager.getConnection();
		String srno = request.getParameter("srno");
		System.out.println("srno"+srno);
		String emp = request.getParameter("empno");
		System.out.println("emp"+emp);
		String date = request.getParameter("date");
		System.out.println("emp"+date);
		String File = null;
		try{
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select attachpath from Dbfstorefile where empno="+emp+" and srno="+srno);
			if(rst.next()){
				File = rst.getString(1);
			}
		}catch(Exception e){
		
			e.printStackTrace();
		}
		String []year = date.split("/");
		System.out.println("FILENAME"+File);
		java.io.File dbf = new java.io.File(File);
		DbfReader reader = new DbfReader(dbf);
		DbfHeader header = reader.getHeader();
		String[] titles = new String[header.getFieldsCount()];
		for (int i = 0; i < header.getFieldsCount(); i++) {
		    DbfField field = header.getField(i);
		    titles[i] = StringUtils.rightPad(field.getName(), field.getFieldLength(), ' ');
		    System.out.print(titles[i]);//for getting headers
		}
	
		Object[] row;
		  		
		String bean_array[] = new String[header.getFieldsCount()];
	
		Statement str;
		
		String status = "false";
		
		try {
			str = con.createStatement();
	//		String query = "select * from Dbfimport_Data where year = '"+year[1]+"' and mon_th = '"+year[0]+"' ";
		//	ResultSet res = str.executeQuery(query);
		//	if(res.next()){
		//		System.out.println("Record already Present in db");
	//			request.getRequestDispatcher("attach_Dbf.jsp?action=present").forward(request, response);
	//		}
	//		else
			{
				
				String sql = null;
		while ((row = reader.nextRecord()) != null) {
		//	System.out.println("inside while");
		    for (int i = 0; i < header.getFieldsCount(); i++) {
		    	
		        DbfField field = header.getField(i);
		        String value = field.getDataType() == DbfDataType.CHAR? new String((byte[]) row[i]): String.valueOf(row[i]);
		                
		            
		           /*     if((value=="" || value==null) &&(i==4 || i==5 || i==6 || i==7 || i==8 || i==9|| i==10 || i==11 || i==12 || i==13 || i==14 || i==15 || i==16 || i==17 || i==18 || i==19 || i==20 || 
		                		i==21 || i==22 || i==23 || i==24 || i==25 || i==26 || i==27 || i==28 || i==29 || i==30 || i==31 || i==32 || i==33 || i==34 ))
		                	value="P";*/
		                System.out.print("value"+value);
		                bean_array[i] = value;   
		    }
			
			  sql = "insert into Dbfimport_Data values  (";
			 for(int j=0;j<bean_array.length;j++){
				
				 		 if(j==1){
						// sql = sql +"'"+year[1]+"',";
					 }
					 sql = sql +"'"+bean_array[j]+"',"; 
				 
			 }
			 
			if(!sql.equals("insert into Dbfimport_Data values  (")){
			//	sql = sql + 0 +",";
		 	sql = sql.substring(0, sql.lastIndexOf(",")) + ","+year[1] +" , " +year[0] +"  ) ";
			// 	System.out.println("sql "+sql);
			}

			 try {  
					Statement st =con.createStatement();
				//	String check =  "Select * from Dbfimport_Data where emp_code =  '"+bean_array[0]+"' and year = '"+year[1]+"' and month = '"+bean_array[1]+"' ";
				//	ResultSet chk = st.executeQuery(check);
				//	if(chk.next()){
			//			System.out.println(chk.getString("emp_code"));
			//			status = "final";
			//		}
		//	 else
			 {
					
					if(!sql.equals("insert into Dbfimport_Data values  (")){
						//String delt = "Select * from Dbfimport_Data where emp_code = '"+bean_array[0]+"' and year = '"+year[1]+"' and month = '"+bean_array[1]+"'";
				//		System.out.println(delt);
				//	ResultSet del = st.executeQuery(delt);
				//	if(del.next()){
			//			System.out.println("in delete");
						//st.execute("delete from Dbfimport_Data where emp_code = '"+bean_array[0]+"' and year = '"+year[1]+"' and month = '"+bean_array[1]+"'");
				//		}
					
		//			System.out.println("status"+status);
					}
					st.execute(sql);
					status= "true";
					}
			 }
		
			 catch (SQLException e) {
				
					e.printStackTrace();
				}
		   
		   // System.out.println("");
			}
		if(status.equalsIgnoreCase("true")){
			request.getRequestDispatcher("attach_Dbf.jsp?action=saved").forward(request, response);
		 }else if (status.equalsIgnoreCase("final")) {
			 request.getRequestDispatcher("attach_Dbf.jsp?action=finalized").forward(request, response);
		}
		else{
			System.out.println("inside else---------------------------- ");
		request.getRequestDispatcher("attach_Dbf.jsp?action=notpresent").forward(request, response);
		 }
		
		con.close();
			}}	catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int uid = (Integer)session.getAttribute("UID");
		ArrayList<SearchBean> list = new ArrayList<>();
		String date = request.getParameter("date");
		String[] month = date.split("-");
		SearchDAO sdao = new SearchDAO();
		list = sdao.getAttendRecords(Calculate.getMonth_DBF(month[1]),month[2],date);
		boolean flag = sdao.addToCalculate(list,date,uid);
		request.setAttribute("list", list);
		request.setAttribute("date", date);
		if(flag){
			request.getRequestDispatcher("attendanceDisp.jsp?action=show&msg=saved").forward(request, response);
		}else{
			request.getRequestDispatcher("attendanceDisp.jsp?action=show&msg=error").forward(request, response);
		}
	}

}
