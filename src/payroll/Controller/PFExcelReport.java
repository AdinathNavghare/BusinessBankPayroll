package payroll.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpExperHandler;

/**
 * Servlet implementation class PFExcelReport
 */
@WebServlet("/PFExcelReport")
public class PFExcelReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PFExcelReport() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub

try{
	Date date = new Date() ;
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);
	HSSFWorkbook hwb=new HSSFWorkbook();
	HSSFSheet sheet =  hwb.createSheet("new sheet");

	HSSFRow rowhead=   sheet.createRow((short)0);
	rowhead.createCell((short) 0).setCellValue("Employee No");
	rowhead.createCell((short) 1).setCellValue("Name");
	rowhead.createCell((short) 2).setCellValue("DOB");
	rowhead.createCell((short) 3).setCellValue("DOJ");
	rowhead.createCell((short) 4).setCellValue("PF No");

	Class.forName("oracle.jdbc.driver.OracleDriver"); //com.mysql.jdbc.Driver
	Connection con = ConnectionManager.getConnection();
	Statement st=con.createStatement();
	ResultSet rs=st.executeQuery("Select * from EMPMAST");
	int i=1;
	while(rs.next()){
	HSSFRow row=   sheet.createRow((short)i);
	row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("EMPNO")));
	row.createCell((short) 1).setCellValue(rs.getString("FNAME")+" "+rs.getString("LNAME"));
	row.createCell((short) 2).setCellValue(EmpExperHandler.dateFormat(rs.getDate("DOB")));
	row.createCell((short) 3).setCellValue(EmpExperHandler.dateFormat( rs.getDate("DOJ")));
	row.createCell((short) 4).setCellValue(rs.getString("PFNO"));
	
	i++;
}
	FileOutputStream fileOut =  new FileOutputStream("D:\\data1.xls");
	hwb.write(fileOut);
	fileOut.close();
System.out.println("Your excel file has been generated!");

} catch ( Exception ex ) {
    System.out.println(ex);

}
    
		
	}

}
