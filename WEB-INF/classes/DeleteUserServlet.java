import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        String username = request.getParameter("username");
        BookDAO dao = new BookDAO();
        boolean success = dao.deleteUser(username);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ManageUsersServlet?message=User deleted successfully");
        } else {
            response.getWriter().println("Error deleting user.");
        }
    }
}