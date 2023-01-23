package payroll.Controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.AddProjectDAO;
import payroll.DAO.ConnectionManager;
import payroll.DAO.ProjectReportDAO;
import payroll.Model.AddProjectBean;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.lowagie.text.DocumentException;



@WebServlet("/AddProjectServlet")
@MultipartConfig

public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;

	public AddProjectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		response.setContentType("txt/html");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		String action = request.getParameter("action");

		AddProjectBean addbean = new AddProjectBean();

		if (action.equalsIgnoreCase("addProject")) {

			addbean.setPROTYPE(request.getParameter("typeselect").trim());
			addbean.setPROSTATUS(request.getParameter("status").trim());
			addbean.setPRONAME(request.getParameter("firstname").trim());
			addbean.setLIVEURL(request.getParameter("LUrl").trim());
			addbean.setTESTURL(request.getParameter("TUrl").trim());
			addbean.setREVIEWDATE(request.getParameter("review").trim());
			addbean.setDESIGNDATE(request.getParameter("design").trim());
			/*
			 * addbean.setDEVELOPDATE(request.getParameter("devlop").trim());
			 * addbean.setSITETESTDATE(request.getParameter("sitetest").trim());
			 * addbean.setUATDATE(request.getParameter("uat").trim());
			 */
			addbean.setPRODESCRIPTION(request.getParameter("post-text").trim());
			addbean.setPROPRIORITY(request.getParameter("priority").trim());
			boolean flag = AddProjectDAO.addProject(addbean);
			if (flag) {
				response.sendRedirect("AddNewProject.jsp");
			} else {
				//response.sendRedirect("AddProject.jsp");
			}

		} else if (action.equalsIgnoreCase("addProject1")) {

			addbean.setPROTYPE(request.getParameter("typeselect").trim());
			addbean.setPROSTATUS(request.getParameter("status").trim());
			addbean.setPRONAME(request.getParameter("firstname").trim());
			addbean.setLIVEURL(request.getParameter("LUrl").trim());
			addbean.setTESTURL(request.getParameter("TUrl").trim());
			addbean.setREVIEWDATE(request.getParameter("review").trim());
			addbean.setDESIGNDATE(request.getParameter("design").trim());
			/*
			 * addbean.setDEVELOPDATE(request.getParameter("devlop").trim());
			 * addbean.setSITETESTDATE(request.getParameter("sitetest").trim());
			 * addbean.setUATDATE(request.getParameter("uat").trim());
			 */
			addbean.setPRODESCRIPTION(request.getParameter("post-text").trim());
			addbean.setPROPRIORITY(request.getParameter("priority").trim());

			boolean flag = AddProjectDAO.addProject(addbean);
			if (flag == true) {
				response.sendRedirect("AddProjectTabs.jsp");
			} else {
				JOptionPane.showMessageDialog(null, this, "Data is not save please try again!!", 0);
			}
		} 
		else if (action.equalsIgnoreCase("addTeam"))
		{
		
			String s = "";
			int subid=Integer.parseInt(request.getParameter("q"));
			System.out.println("subbbbiddd:"+subid);
			int empid=Integer.parseInt(request.getParameter("empid"));
			System.out.println("subbbbiddd:"+empid);
			addbean.setEMPID(empid);
			addbean.setSUBTID(subid);
			int flag = AddProjectDAO.addTeam(addbean);
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		} 
		else if (action.equalsIgnoreCase("SubToEmp"))
		{
			response.setContentType("txt/html");
			PrintWriter out1 = response.getWriter();
			String s = "";
			int subid=Integer.parseInt(request.getParameter("q"));
			String sql="select Emp_Id from Assigner where Sub_Id="+subid;
			int id=0;
			String pro=""; 
			Connection con = null;
			Statement st = null,st1=null;
			ResultSet rs = null,rs1=null;
			try {
				
				
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next())
				{
					id=rs.getInt("Emp_Id");
					out1.write(id+",");
				}
				/*
				 * out1.write(empid+","); out1.write(EmpName+","); out1.write(ProName+",");
				 * out1.write(TskName+","); out1.write(SubName+",");
				 */
				

				st.close();
				rs.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		else if (action.equalsIgnoreCase("EmpDetail"))
		{
			int empid=Integer.parseInt(request.getParameter("empid"));
			String sql=" select a.Emp_Id,e.Name,t.PROJECT_NAME,t.TASK_NAME,s.SubTName from Task t inner join SubTask s on t.TASK_ID=s.TASK_ID \r\n" + 
					"    inner join Assigner a on s.Sub_ID=a.Sub_Id inner join Employee_PM e on a.Emp_Id=e.Emp_Id where a.Emp_Id="+empid;
			String EmpName="",ProName="",TskName="",SubName="" ,pro="";
			Connection con = null;
			Statement st = null,st1=null;
			ResultSet rs = null,rs1=null;
			try {
				
				
				con = ConnectionManager.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next())
				{
					//int id=rs.getInt("Sub_Id");
					EmpName=rs.getString("Name");
					ProName=rs.getString("PROJECT_NAME");
					TskName=rs.getString("TASK_NAME");
					SubName=rs.getString("SubTName");	
					pro=pro+empid+","+EmpName+","+ProName+","+TskName+","+SubName+",*,";
				}
				response.setContentType("txt/html");
				PrintWriter out1 = response.getWriter();
				out1.write(pro);
				/*
				 * out1.write(empid+","); out1.write(EmpName+","); out1.write(ProName+",");
				 * out1.write(TskName+","); out1.write(SubName+",");
				 */
				

				st.close();
				rs.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}


			
		}
		else if (action.equalsIgnoreCase("updateTeam")) 
		{
			int pid = Integer.parseInt(request.getParameter("Pid"));
			System.out.println(request.getParameter("Pid"));
			String s = "";
			String team[] = request.getParameterValues("projects[team][]");
			for (int i = 0; i < team.length; i++) {
				System.out.println("i:" + team[i]);
			}
			if (team != null) {
				for (String t : team) {
					s = s + "" + t;
					s = s + ",";
				}
			}
			addbean.setPROID(pid);
			addbean.setPROJECTTEAM(s.trim());
			// System.out.println("checkbox:" + s);
			boolean flag = AddProjectDAO.updateTeam(addbean);
			System.out.println("first flag"+flag);
			if (flag == true) {
				response.sendRedirect("EditProject.jsp?Pid="+pid);
			} else {
				response.sendRedirect("error.jsp");
			}
		} 
		else if (action.equalsIgnoreCase("updateProject")) {
			int add = Integer.parseInt(request.getParameter("add"));

			addbean.setPROTYPE(request.getParameter("typeselect").trim());
			addbean.setPROSTATUS(request.getParameter("status").trim());
			addbean.setPRONAME(request.getParameter("firstname").trim());
			addbean.setLIVEURL(request.getParameter("LUrl").trim());
			addbean.setTESTURL(request.getParameter("TUrl").trim());
			addbean.setREVIEWDATE(request.getParameter("review").trim());
			addbean.setDESIGNDATE(request.getParameter("design").trim());
			/*
			 * addbean.setDEVELOPDATE(request.getParameter("devlop").trim());
			 * addbean.setSITETESTDATE(request.getParameter("sitetest").trim());
			 * addbean.setUATDATE(request.getParameter("uat").trim());
			 */
			addbean.setPRODESCRIPTION(request.getParameter("post-text").trim());
			addbean.setPROPRIORITY(request.getParameter("priority").trim());

			boolean flag = AddProjectDAO.updateProject(addbean, add);

			if (flag == true)
			{
				response.sendRedirect("ProjectViewAll.jsp");
			} 
			else
			{
				JOptionPane.showMessageDialog(null, this, "Data is not update please try again!!", 0);
			}

		}
		else if (action.equalsIgnoreCase("Delete"))
		{
			int pid = Integer.parseInt(request.getParameter("Pid"));

			int flag = AddProjectDAO.DeleteProject(pid);
			System.out.println("AddProjectDao Flag:" + flag);
			// PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		} 
		else if (action.equalsIgnoreCase("Delete1"))
		{
			int pid = Integer.parseInt(request.getParameter("Pid"));

			int flag = AddProjectDAO.DeleteProject1(pid);
			System.out.println("AddProjectDao Flag:" + flag);
			// PrintWriter out1 = response.getWriter();
			response.setContentType("text/html");
			// out1.write(String.valueOf(flag));
			flag = 1;
			response.getWriter().write(String.valueOf(flag));
		} 
		else if (action.equalsIgnoreCase("ForTask"))
		{
			Connection con1=ConnectionManager.getConnection();
			PreparedStatement ps=null;
			ResultSet rs1=null;
			response.setContentType("text/html");
			String TaskName="";
			int tskid;
			int y=Integer.parseInt(request.getParameter("q"));
			PrintWriter out1=response.getWriter();
			PrintWriter out2=response.getWriter();
 			System.out.println("value of y:"+y);
 			/* String drop = "<script>document.writeln(select)</script>";
 			out.println("value: " + drop); */
 			String str2="select TASK_ID,TASK_NAME from Task where PROJECTId="+y+"and TCONDITION='Active'";
 			try {
				ps=con1.prepareStatement(str2);
				rs1=ps.executeQuery();
 			while(rs1.next())
 			{
 				tskid=rs1.getInt("TASK_ID");
 				TaskName=rs1.getString("TASK_NAME");
 				
 				out1.write(tskid+ ":"+TaskName+",");
 				
 			}
 			System.out.println("TaskName:"+TaskName);
 			
 			ps.close();
			rs1.close();
			
 			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			

		}
		else if (action.equalsIgnoreCase("ForSubTask"))
		{
			Connection con1=ConnectionManager.getConnection();
			PreparedStatement ps=null;
			ResultSet rs1=null;
			response.setContentType("text/html");
			String subName="";
			int subid;
			int y=Integer.parseInt(request.getParameter("q"));
			PrintWriter out1=response.getWriter();
 			System.out.println("value of y:"+y);
 			String str2="select Sub_ID,SubTName from SubTask where TASK_ID="+y;
 			try {
				ps=con1.prepareStatement(str2);
				rs1=ps.executeQuery();
 			while(rs1.next())
 			{
 				subid=rs1.getInt("Sub_ID");
 				subName=rs1.getString("SubTName");
 				
 				out1.write(subid+ ":"+subName+",");
 				
 			}
 			System.out.println("TaskName:"+subName);
 			
 			ps.close();
			rs1.close();
			
 			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			

		} 
		else if(action.equalsIgnoreCase("generatePDF"))
		{
			String offerdate=request.getParameter("OfferDate");
			String joindate=request.getParameter("JDate");
			String desig=request.getParameter("desig");
			String Empname=request.getParameter("Empname");
			String gender=request.getParameter("gender");
			String ctcMon=request.getParameter("ctcMon");
			String ctcAnnum=request.getParameter("ctcAnnum");
			String profund1=request.getParameter("profund");
			String profund2=request.getParameter("profund2");
			String esi1=request.getParameter("esi");
			String esi2=request.getParameter("esi2");
			String ca=request.getParameter("ca");
			String cca=request.getParameter("cca");
			String ea=request.getParameter("ea");
			String ma=request.getParameter("ma");
			String incentive=request.getParameter("incentive");
			String gross=request.getParameter("gross");
			String pt=request.getParameter("pt");
			String totaldeduc=request.getParameter("totaldeduc");
			String net=request.getParameter("net");
			String EmpContri=request.getParameter("EmpContri");
			String hra=request.getParameter("hra");
			String basic=request.getParameter("basic");
			String skill=request.getParameter("skill");
			String AnumSal=request.getParameter("AnumSal");
			String ProbPeriod=request.getParameter("ProbPeriod");
			String chkBox=request.getParameter("chkBox");
			
			System.out.println(Empname+"jhgfdsdfgh");
			
			

			String filePath1 = getServletContext().getRealPath("")+ File.separator + "OfferLetter1.pdf";
			
			String imagepath =getServletContext().getRealPath("/images/systech2.png");
			
			try {
				
				AddProjectDAO.genPDF(filePath1,imagepath,offerdate,joindate,desig,Empname, gender,ctcMon,ctcAnnum,profund1,profund2,esi1,esi2,ca,cca,ma,ea,incentive,gross,pt,totaldeduc,net,EmpContri,hra,basic,skill,AnumSal,ProbPeriod,chkBox);
				System.out.println(offerdate+""+joindate+""+desig+""+Empname+""+gender+"kkkkk");
				System.out.println(Empname+"MMMMM");
				
				final int BUFSIZE = 4096;
				File file = new File(filePath1);
				int length = 0;
				ServletOutputStream outStream = response.getOutputStream();
				ServletContext context = getServletConfig().getServletContext();
				String mimetype = context.getMimeType(filePath1);
				if (mimetype == null) 
				{
					mimetype = "application/octet-stream";
				}
				response.setContentType(mimetype);
				response.setContentLength((int) file.length());
				String fileName = (new File(filePath1)).getName();
				response.setHeader("Content-Disposition", "attachment; filename=\""	+ fileName + "\"");
				byte[] byteBuffer = new byte[BUFSIZE];
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				while ((in != null) && ((length = in.read(byteBuffer)) != -1)) 
				{
					outStream.write(byteBuffer, 0, length);
				}
				in.close();
				outStream.close();
				if (file.exists())
				{
					file.delete();
				}
			} catch (DocumentException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
		}

		/*else if (action.equalsIgnoreCase("display")) {
			String filename = "";
			String extension = "";
			String saveNewPath = "";
			HttpSession session1 = request.getSession();
			String act = request.getParameter("action") == null ? "" : request.getParameter("action");
			System.out.println("servl" + act);
			System.out.println("hiii helloo" + act);
			
			 String [] list = new String[30]; 
			try {
				Connection con = ConnectionManager.getConnection();
				String saveFile = "";
				final String SAVE_DIR = "uploadFiles";
				String contentType = request.getContentType();
				if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
					DataInputStream in = new DataInputStream(request.getInputStream());
					int count = in.available();
					int formDataLength = request.getContentLength();
					System.out.println("formDataLength" + formDataLength);
					byte dataBytes[] = new byte[formDataLength];
					int byteRead = 0;
					int totalBytesRead = 0;
					while (totalBytesRead < formDataLength) {
						byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
						totalBytesRead += byteRead;
					}
					System.out.println("totalBytesRead " + totalBytesRead);
					String file = new String(dataBytes);
					// List items = upload.parseRequest(request);
					saveFile = file.substring(file.indexOf("filename=\"") + 10);
					System.out.println(saveFile.toString());
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
					int lastIndex = contentType.lastIndexOf("=");
					String boundary = contentType.substring(lastIndex + 1, contentType.length());
					int pos;
					pos = file.indexOf("filename=\"");
					System.out.println("path is" + pos);
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					System.out.println("path is" + pos);
					int boundaryLocation = file.indexOf(boundary, pos) - 4;
					int startPos = ((file.substring(0, pos)).getBytes()).length;
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
					String appPath = request.getServletContext().getRealPath("");
					// constructs path of the directory to save uploaded file
					String savePath = appPath + File.separator + SAVE_DIR;
					System.out.println("path is" + savePath);
					// creates the save directory if it does not exists
					File fileSaveDir = new File(savePath);
					if (!fileSaveDir.exists()) {
						fileSaveDir.mkdir();
					}
					System.out.println("path is" + saveFile);
					saveFile = savePath + "\\" + saveFile;
					File f = new File(saveFile);
					filename = (new File(saveFile)).getName();
					// Statement st = (Statement) con.createStatement();
					System.out.println("file name is " + filename);
					String basename = FilenameUtils.getBaseName(filename);
					System.out.println(" file is without extension" + basename);
					int i = filename.lastIndexOf('.');
					if (i > 0) {
						extension = filename.substring(i + 1);
					}
					System.out.println("extension is " + extension);
					String renamefile = basename;// here concat employee number
					// saveNewPath=savePath+"\\"+renamefile+"."+extension;
					saveNewPath = "F:\\uploadFiles\\" + renamefile + "." + extension;
					PreparedStatement ps = con
							.prepareStatement("insert into ATTACHMENT1(ATTACHPATH,FILENAME) values(?,?)");
					ps.setString(1, saveNewPath);
					ps.setString(2, filename);

					ps.executeUpdate();
					
					 * session1.removeAttribute("doc_name"); session1.removeAttribute("doc_type");
					 * session1.removeAttribute("doc_desc");
					 
					FileOutputStream fileOut = new FileOutputStream(f);
					fileOut.write(dataBytes, startPos, (endPos - startPos));
					fileOut.flush();
					fileOut.close();
					File f2 = new File(saveFile);
					File newfile = new File(saveNewPath);
					f2.renameTo(newfile);
					request.getRequestDispatcher("AddProjectTabs.jsp?action=close").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
*/
			}
}