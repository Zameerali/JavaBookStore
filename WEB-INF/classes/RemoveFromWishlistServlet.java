import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RemoveFromWishlistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.html");
            return;
        }
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        BookDAO dao = new BookDAO();
        boolean success = dao.removeFromWishlist(userId, bookId);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ViewWishlistServlet?message=Item removed from wishlist successfully");
        } else {
            response.sendRedirect("ViewWishlistServlet?message=Error removing item from wishlist");
        }
    }
}