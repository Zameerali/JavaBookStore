import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        BookDAO dao = new BookDAO();
        boolean success = dao.deleteBook(id);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ManageBooksServlet?message=Book deleted successfully");
        } else {
            response.getWriter().println("Error deleting book.");
        }
    }
}