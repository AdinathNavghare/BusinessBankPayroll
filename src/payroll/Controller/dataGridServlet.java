package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Enumeration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.userServices;

/**
 * Servlet implementation class dataGridServlet
 */
@WebServlet("/dataGridServlet")
public class dataGridServlet extends HttpServlet {
	 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String actionValue = "";
        String useridselected = "";
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            useridselected = (String) en.nextElement();            
            actionValue = request.getParameter(useridselected);  
        }
        if(useridselected.trim().equalsIgnoreCase("")){
            request.setAttribute("errorMessage","Cannot find user id");
            request.getRequestDispatcher("/datagrid.jsp").forward(request, response);
        }else{            
            if(actionValue.contains("Remove")){    
                userServices userServis = new userServices();
                if(userServis.deleteUser(useridselected)){
                    request.setAttribute("successMessage", "Successfully delete user: <b>" + useridselected + "</b>");
                    request.getRequestDispatcher("/datagrid.jsp").forward(request, response);
                }else{
                    request.setAttribute("errorMessage", "Failed to delete user: <b>" + useridselected + "</b>, please try again");                    
                   request.getRequestDispatcher("/datagrid.jsp").forward(request, response);
                }                
            }else{
                //process redirect to modify user page with query string user id
                //redirect to modify page
           }
        }
    }

}
