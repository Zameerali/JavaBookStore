import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        BookDAO dao = new BookDAO();
        boolean success = dao.addToCart(userId, bookId, quantity);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ViewBooksServlet?message=Book added to cart successfully");
        } else {
            response.sendRedirect("ViewBooksServlet?message=Error adding book to cart");
        }
    }
}