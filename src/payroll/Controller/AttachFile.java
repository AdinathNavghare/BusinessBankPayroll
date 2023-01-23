package payroll.Controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;


import payroll.Core.ReportDAO;
import payroll.DAO.ConnectionManager;


/**
 * Servlet implementation class AttachFile
 */
@WebServlet("/AttachFile")
public class AttachFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttachFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String act = request.getParameter("action")==null?"":request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		if(act.equalsIgnoreCase("attachFile"))
		{
			String filename="";
			String extension = "";
			String saveNewPath="";
			HttpSession session = request.getSession();
			//String act = request.getParameter("act")==null?"":request.getParameter("act");
			System.out.println("servl"+act);
			String [] list = new String[3];
			try
			{
				System.out.println("servl 1 ::"+act+" doc name"+request.getParameter("docname"));
				String empno = null;
				if(act.equalsIgnoreCase("attachFile"))
				{
					empno = (String) session.getAttribute("empno");
				}
				else if(act.equalsIgnoreCase("site"))
				{
					 int empp = (int) session.getAttribute("EMPNO");
					 empno = Integer.toString(empp);
				 }
				System.out.println("servl  2 ::"+act);
				//String empno = "6002";
				String doc_name= (String) session.getAttribute("doc_name");
				
				
				/*String doc_name= request.getParameter("docname");*/
				
				String doc_type=(String) session.getAttribute("doc_type");
				/*String doc_type=request.getParameter("docType");*/
				
				String doc_desc=(String) session.getAttribute("doc_desc");
				/*String doc_desc=request.getParameter("docdesc");*/
				
				System.out.println("doc name "+doc_name+" doc_type "+doc_type+"  doc_desc "+doc_desc);
				Connection con = ConnectionManager.getConnection();
				String saveFile="";
				final String SAVE_DIR = "uploadFiles";
				String contentType = request.getContentType();
				System.out.println("contentType   ::"+contentType+"   ::"+contentType.indexOf("multipart/form-data"));
				
				
				if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
				{
					DataInputStream in = new DataInputStream(request.getInputStream());
					int count = in.available();
					int formDataLength = request.getContentLength();
					System.out.println("formDataLength"+formDataLength);
					byte dataBytes[] = new byte[formDataLength];
					int byteRead = 0;
					int totalBytesRead = 0;
					while (totalBytesRead < formDataLength) 
					{
						byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
						totalBytesRead += byteRead;
					}
					System.out.println("totalBytesRead "+totalBytesRead);
					String file = new String(dataBytes);
				//	List items = upload.parseRequest(request);  
					saveFile = file.substring(file.indexOf("filename=\"") + 10);
					System.out.println("1 : "+saveFile.toString());
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
					int lastIndex = contentType.lastIndexOf("=");
					String boundary = contentType.substring(lastIndex + 1,contentType.length());
					int pos;
					pos = file.indexOf("filename=\"");
					System.out.println("path is"+pos);
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					System.out.println("path is"+pos);
					int boundaryLocation = file.indexOf(boundary, pos) - 4;
					int startPos = ((file.substring(0, pos)).getBytes()).length;
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
					String appPath = request.getServletContext().getRealPath("");
				// 	constructs path of the directory to save uploaded file
					String savePath = appPath + File.separator + SAVE_DIR;
					System.out.println("path is"+savePath);
				// 	creates the save directory if it does not exists
					File fileSaveDir = new File(savePath);
					if (!fileSaveDir.exists()) 
					{
						fileSaveDir.mkdir();
					}
					System.out.println("path is"+saveFile);
					saveFile=savePath+"\\"+saveFile;
					File f = new File(saveFile);
					filename = (new File(saveFile)).getName();
					Statement st = con.createStatement();
					System.out.println("file name is "+filename);
					String basename = FilenameUtils.getBaseName(filename);
					System.out.println(" file is without extension"+basename);
					int i = filename.lastIndexOf('.');
					if (i > 0) 
					{
						extension = filename.substring(i+1);
					}
					System.out.println("extension is "+extension);
					String renamefile = basename+""+empno;//here concat employee number 
					saveNewPath=savePath+"\\"+renamefile+"."+extension;
					if(act.equalsIgnoreCase("attachFile"))
					{
						
						System.out.println("in if statement ::"+act);
						int srno=0;
						ResultSet rs=st.executeQuery("select max(SRNO) from ATTACHMENT where EMPNO="+empno);
						System.out.println("in if statement 2 ::"+act);
						while(rs.next())
						{
							
							srno=rs.getInt(1);
						}
						srno=srno+1;
						ResultSet rs1=st.executeQuery("select FILENAME from ATTACHMENT where EMPNO="+empno+" and FILENAME='"+filename+"'");
						if(rs1.next())
						{
							
							System.out.println("record gettinggg");
							request.getRequestDispatcher("Attachment.jsp?action=exists").forward(request, response);	
						}
						else
						{
							System.out.println("in if statement 4 ::"+act);
							PreparedStatement ps = con.prepareStatement("insert into ATTACHMENT(EMPNO,ATTACHPATH,FILENAME,SRNO,doc_name,doc_type,doc_desciption) values(?,?,?,?,?,?,?)");
							ps.setString(1, empno);
							ps.setString(2, saveNewPath);
							ps.setString(3, filename);
							ps.setInt(4, srno);
							ps.setString(5, doc_name);
							ps.setString(6, doc_type);
							ps.setString(7, doc_desc);
							ps.executeUpdate();
							/*session.removeAttribute("doc_name");
				 			session.removeAttribute("doc_type");
							session.removeAttribute("doc_desc");*/
							FileOutputStream fileOut = new FileOutputStream(f);
							fileOut.write(dataBytes, startPos, (endPos - startPos));
							fileOut.flush();
							fileOut.close();
							File f2 =new File(saveFile);
							File newfile =new File(saveNewPath);
							f2.renameTo(newfile);
							//request.getRequestDispatcher("Attachment.jsp?action=showemp&list=show").forward(request, response);
							String key=(String) session.getAttribute("key");
							if(act.equalsIgnoreCase("attachfile") && key.equalsIgnoreCase("addemp") || key.equalsIgnoreCase("editemp"))
							{
								response.sendRedirect("Attachment.jsp?action=editemp&list=show");
							}
							else
							{
								response.sendRedirect("Attachment.jsp?action=showemp&list=show");
							}
						}
					}
					/*if(act.equalsIgnoreCase("site"))
					{
						String uid = session.getAttribute("UID").toString();
						String user = session.getAttribute("name").toString();
					//	String emp = session.getAttribute("EMPNO").toString();
						String updatename = empno+"-"+uid+"-"+user;
						int site_id = Integer.parseInt(session.getAttribute("Prj_Srno").toString());
						int srno=0;
						ResultSet rs=st.executeQuery("select max(SRNO) from SiteWise_File_Upload where PRJ_SRNO="+site_id);
						while(rs.next())
						{
							srno=rs.getInt(1);
						}
						srno=srno+1;
						ResultSet rs1=st.executeQuery("select FILENAME from SiteWise_File_Upload where PRJ_SRNO="+site_id+" and FILENAME='"+filename+"'");
						if(rs1.next())
						{
							System.out.println("record gettinggg");
							request.getRequestDispatcher("uploadattchment.jsp?action=exists").forward(request, response);	
						}
						else
						{
							PreparedStatement ps = con.prepareStatement("insert into SiteWise_File_Upload(PRJ_SRNO,FILENAME,ATTACHPATH,SRNO,UPDDT,USRCODE,EMPNO) values(?,?,?,?,?,?,?)");
							ps.setInt(1, site_id);
							ps.setString(2, filename);
							ps.setString(3,saveNewPath );
							ps.setInt(4, srno);
							ps.setString(5,ReportDAO.getSysDate());
							ps.setString(6,updatename );
							ps.setString(7,empno );
							ps.executeUpdate();
							FileOutputStream fileOut = new FileOutputStream(f);
							fileOut.write(dataBytes, startPos, (endPos - startPos));
							fileOut.flush();
							fileOut.close();
							File f2 =new File(saveFile);
							File newfile =new File(saveNewPath);
							f2.renameTo(newfile);
							request.getRequestDispatcher("uploadattchment.jsp?action=close").forward(request, response);
						}*/
						
					//}
				}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
		
		
		if(act.equalsIgnoreCase("deleteFile"))
		{
			
			response.setContentType("text/html"); 
			String empno=request.getParameter("empno");
			String filename=request.getParameter("filename");
			
			
			System.out.println("empno="+empno+" filename="+filename);
			
			
			try {
				
				Connection con=ConnectionManager.getConnection();
				
				//by filename
				/*PreparedStatement ps=con.prepareStatement("delete from ATTACHMENT where EMPNO="+empno+" and FILENAME like '%"+filename+"%'");*/
				
				//by SRNO
				PreparedStatement ps=con.prepareStatement("delete from ATTACHMENT where EMPNO="+empno+" and SRNO="+filename);
							
				int flag=ps.executeUpdate();
				
				response.getWriter().write(String.valueOf(flag));
				
			} 
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}

	}
	
	}
