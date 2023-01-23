package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class navigateDatagrid
 */
@WebServlet("/navigateDatagrid")
public class navigateDatagrid extends HttpServlet {
	 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buttonAction = request.getParameter("action");
        HttpSession session = request.getSession(false);
        int limitStart = 0;
        int limitMax = 15;
        int pageSize = 15;
        int allUserCount = 0;
        int pageIndex = 0; //start page from index 0
        session = request.getSession(false);
        if (session.getAttribute("limitStart") != null) {
            limitStart = Integer.parseInt(session.getAttribute("limitStart").toString());
        } else {
            session.setAttribute("limitStart", limitStart);
        }
        if (session.getAttribute("limitMax") != null) {
            limitMax = Integer.parseInt(session.getAttribute("limitMax").toString());
        } else {
            session.setAttribute("limitMax", limitMax);
        }
        if (session.getAttribute("pageIndex") != null) {
            pageIndex = Integer.parseInt(session.getAttribute("pageIndex").toString());
        } else {
            session.setAttribute("pageIndex", pageIndex);
        }
        if (session.getAttribute("pageSize") != null) {
            pageSize = Integer.parseInt(session.getAttribute("pageSize").toString());
        } else {
            session.setAttribute("pageSize", pageSize);
        }
        if (buttonAction.equalsIgnoreCase("Next")) {
            limitStart += pageSize;
            limitMax = pageSize;//max row return from query
            pageIndex += 1;           
        }else if(buttonAction.equalsIgnoreCase("Previous")){
            limitStart -= pageSize;
            limitMax = pageSize;//max row return from query
            pageIndex -= 1;
        }else{
            //reset all value to default
            limitStart  = 0;
            limitMax = 15;//max row return from query
            pageSize = Integer.parseInt(buttonAction);
            pageIndex = 0;
        }
        session.setAttribute("limitMax", limitMax);
        session.setAttribute("pageIndex", pageIndex);
        session.setAttribute("limitStart", limitStart);
        session.setAttribute("pageSize", pageSize);
        response.sendRedirect(request.getContextPath() + "/datagrid.jsp");
    }


}
