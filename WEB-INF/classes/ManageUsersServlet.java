import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        BookDAO dao = new BookDAO();
        List<User> users = dao.getAllUsers();
        dao.closeConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Manage Users</title>");
        out.println("<link rel='stylesheet' href='admin-styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='navbar'>");
        out.println("<h2>Admin Dashboard</h2>");
        out.println("<ul>");
        out.println("<li><a href='admin-dashboard.html'>Dashboard</a></li>");
        out.println("<li><a href='ManageBooksServlet'>Manage Books</a></li>");
        out.println("<li><a href='ManageUsersServlet'>Manage Users</a></li>");
                out.println("<li><a href='ManageOrdersServlet'>Manage Orders</a></li>");
        out.println("<li><a href='LogoutServlet'>Logout</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<div class='main-content'>");
        out.println("<h3>Manage Users</h3>");
        out.println("<h4>Existing Users</h4>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Username</th>");
        out.println("<th>Role</th>");
        out.println("<th>Actions</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        for (User user : users) {
            out.println("<tr>");
            out.println("<td>" + user.getUsername() + "</td>");
            out.println("<td>" + user.getRole() + "</td>");
            out.println("<td>");
            out.println("<form action='UpdateUserServlet' method='post' style='display:inline;'>");
            out.println("<input type='hidden' name='username' value='" + user.getUsername() + "'>");
            out.println("<input type='text' name='newUsername' value='" + user.getUsername() + "' required>");
            out.println("<input type='password' name='newPassword' placeholder='New Password' required>");
            out.println("<select name='newRole' required>");
            out.println("<option value='USER'" + ("USER".equals(user.getRole()) ? " selected" : "") + ">USER</option>");
            out.println("<option value='ADMIN'" + ("ADMIN".equals(user.getRole()) ? " selected" : "") + ">ADMIN</option>");
            out.println("</select>");
            out.println("<input type='submit' value='Update'>");
            out.println("</form>");
            out.println("<form action='DeleteUserServlet' method='post' style='display:inline;'>");
            out.println("<input type='hidden' name='username' value='" + user.getUsername() + "'>");
            out.println("<input type='submit' value='Delete'>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        String message = request.getParameter("message");
        if (message != null) {
            out.println("<script>alert('" + message + "');</script>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}