import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        String username = request.getParameter("username");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        String newRole = request.getParameter("newRole");
        BookDAO dao = new BookDAO();
        boolean success = dao.updateUser(username, newUsername, newPassword, newRole);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ManageUsersServlet?message=User updated successfully");
        } else {
            response.getWriter().println("Error updating user.");
        }
    }
}