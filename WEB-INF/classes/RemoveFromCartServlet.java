import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RemoveFromCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        BookDAO dao = new BookDAO();
        boolean success = dao.removeFromCart(userId, bookId);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ViewCartServlet?message=Book removed from cart successfully");
        } else {
            response.sendRedirect("ViewCartServlet?message=Error removing book from cart");
        }
    }
}