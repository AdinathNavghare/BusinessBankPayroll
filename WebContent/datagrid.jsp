<%@page import="payroll.DAO.userServices"%>
<%@page import="payroll.Model.userProfile"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
 
            function ConfirmOnDelete(item) {
              if (confirm("Are you sure to delete " + item + "?") === true)
                  return true;
              else
           return false;
           }
        </script>
    </head>
    <body>
        <h1>Data Grid In JSP</h1>
         
 
         
 
        <fieldset>
            <legend>Users Management</legend>
            ${errorMessage}
            ${successMessage}
             
 
            <div>
                <form action="<c:url value="/dataGridServlet" />" method="POST">
                      <%
                          int limitStart = 0;
                          int limitMax = 15;
                          int pageSize = 15;
                          int allUserCount = 0;
                          int pageIndex = 0; //start page from index 0
                          session = request.getSession(false);
 
                          //check cookie
                          Cookie[] collectionCookies = request.getCookies();
                          String cookieValue = "";
                          for (Cookie c : collectionCookies) {
                              if (c.getName().equalsIgnoreCase("FirstTimeAccessUserManager")) {
                                  if (c.getValue().equalsIgnoreCase("YES")) {
                                      c.setValue("NO");
                                      cookieValue = "NO";
                                  } else if (c.getValue().equalsIgnoreCase("")) {
                                      c.setValue("YES");
                                      session.removeAttribute("limitStart");
                                      session.removeAttribute("limitMax");
                                      session.removeAttribute("pageIndex");
                                      session.removeAttribute("pageSize");
                                  }
                              }
                          }
                          if (cookieValue.equalsIgnoreCase("")) {
                              Cookie cookie = new Cookie("FirstTimeAccessUserManager", "YES");
                              response.addCookie(cookie);
                          }
 
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
 limitMax = pageSize;
 
                          userServices usersServ = new userServices();
 
                          List<userProfile> _collectionOfUserProfile = new ArrayList<userProfile>();                          
 
 
                          allUserCount = usersServ.countTableDataRow("usersprofile");
 
                          _collectionOfUserProfile = usersServ.getAllUsers(limitStart, limitMax);                         
 
 
                          String tableuser = "<table class=\"mainTable\" cellspacing=\"0\" rules=\"all\" id=\"MainContent_GridView1\" style=\"border-color:Gray;border-width:1px;border-style:Solid;width:95%;border-collapse:collapse;\">";
                          tableuser += "<tr style=\"color:White;background-color:#6699CC;font-weight:bold; padding:4px;\">";
                          tableuser += "<th scope=\"col\">No.</th>";
                          tableuser += "<th scope=\"col\">User ID</th>";
                          tableuser += "<th scope=\"col\">First Name</th>";
                          tableuser += "<th scope=\"col\">Last Name</th>";
                          tableuser += "<th scope=\"col\">Email</th>";
                          tableuser += "<th scope=\"col\">&nbsp;</th>";
                          tableuser += "<th scope=\"col\">&nbsp;</th></tr>";
 
                          int numberRecord = pageIndex * pageSize;
                          int balance = allUserCount - numberRecord;
                          int startRekodToShow = numberRecord + 1;
                          int index = startRekodToShow;
                          System.out.println("SIZE-============"+_collectionOfUserProfile.size());
                          for (userProfile u : _collectionOfUserProfile) {
                              tableuser += "<tr style=\"border-color:Gray;border-width:1px;border-style:Solid;\">";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:20px;padding:4px;\">";
                              tableuser += Integer.toString(index);
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:100px;padding:4px;\">";
                              tableuser += u.getUserid();
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:80px;padding:4px;\">";
                              tableuser += u.getFirstName();
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:80px;padding:4px;\">";
                              tableuser += u.getLastName();
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:80px;padding:4px;\">";
                              tableuser += u.getEmailAddress();
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:50px;padding:4px;\">";
                              tableuser += "<input type=\"submit\" class=\"buttonLikeLink\" name=\"" + u.getUserid() + "\" onclick=\"return ConfirmOnDelete('" + u.getUserid() + "');\" value=\"Remove\" />";
                              tableuser += "</td>";
                              tableuser += "<td style=\"border-color:#CCCCCC;border-width:1px;border-style:Solid;width:50px;padding:4px;\">";
                              tableuser += "<input type=\"submit\" class=\"buttonLikeLink\" name=\"" + u.getUserid() + "\" value=\"Modify\" ></input>";
                              tableuser += "</td>";
                              tableuser += "</tr>";
                              index++;
                          }
                          tableuser += "</table>";
                          out.print(tableuser);
                      %>
            </form>
 
           <!-- Create logic next and previous in this section -->
            <%
                if (allUserCount > pageSize) {
                    String form = "";
                    if (pageIndex > 0) {
                        form += "<h1>Page " + Integer.toString(pageIndex) + "</h1>";
                    }
                    form += "<form action=\"" + request.getContextPath() + "/navigateDatagrid\" method=\"POST\" >";
                    if (limitStart > 0) {
                        if (balance > pageSize) {
                            if ((pageSize + numberRecord) == allUserCount) {
                                //do not show next
                            } else {
                                //show next
                                form += "<input type=\"submit\" class=\"buttonNav\" id=\"bNext\" name=\"action\" value=\"Next\" />";
                            }
                        } else {
                            if ((balance + numberRecord) == allUserCount) {
                                //do not show next
                            } else {
                                //show next
                                form += "<input type=\"submit\" class=\"buttonNav\" id=\"bNext\" name=\"action\" value=\"Next\" />";
                            }
                        }
                       if (startRekodToShow != 1) {
                            form += "<input type=\"submit\" class=\"buttonNav\" id=\"bPrevious\" name=\"action\" value=\"Previous\" />";
                        }
                    } else {
                        form += "<input type=\"submit\" class=\"buttonNav\" id=\"bNext\" name=\"action\" value=\"Next\" />";
                    }
                    form += "</form>";
                    out.print(form);
                }
            %>
        </div>
         
 
         
 
        <%
            if (allUserCount > 15) {
                String grid_row_controller = "<div id=\"MainContent_PanelDropDownGVPage\" style=\"display:inline;\">";
                grid_row_controller += "Total Users Per Page :";
                grid_row_controller += "<form method=\"POST\" action=\"" + request.getContextPath() + "/navigateDatagrid\" id=\"pageRowform\" >";
                grid_row_controller += "<select name=\"action\" style=\"width:50px;\" onchange=\"document.forms['pageRowform'].submit()\">";
 
                if (pageSize == 10) {
                    grid_row_controller += "<option value=\"10\" selected=\"selected\">10</option>";
                } else {
                    grid_row_controller += "<option value=\"10\">10</option>";
                }
 
                if (pageSize == 15) {
                    grid_row_controller += "<option selected=\"selected\" value=\"15\">15</option>";
                } else {
                    grid_row_controller += "<option value=\"15\">15</option>";
                }
 
                if (pageSize == 25) {
                    grid_row_controller += "<option selected=\"selected\" value=\"25\">25</option>";
                } else {
                    grid_row_controller += "<option value=\"25\">25</option>";
                }
                if (pageSize == 35) {
                    grid_row_controller += "<option selected=\"selected\" value=\"35\">35</option>";
                } else {
                    grid_row_controller += "<option value=\"35\">35</option>";
                }
                if (pageSize == 50) {
                    grid_row_controller += "<option selected=\"selected\" value=\"50\">50</option>";
                } else {
                    grid_row_controller += "<option value=\"50\">50</option>";
                }
 
                grid_row_controller += "</select>";
                grid_row_controller += "</form>";
                out.print(grid_row_controller);
            }
        %>        
         
 
        Show         
        <%
 
            if (allUserCount > pageSize) {
                if (pageIndex == 0) {
                    out.print("1 - " + pageSize);
                } else {
                    if (balance > pageSize) {
                        out.print(startRekodToShow + " - " + (pageSize + numberRecord));
                    } else {
                        out.print(startRekodToShow + " - " + (balance + numberRecord));
                    }
                }
            } else if (allUserCount == 0) {
                out.print("0");
            } else {
                out.print("1 - " + allUserCount);
            }
        %>      
        Record(s)  From 
        <%
            out.print(allUserCount);
        %>
        User(s)
         
 
    </fieldset>
</body>
</html>