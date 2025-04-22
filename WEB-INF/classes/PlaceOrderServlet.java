import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PlaceOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        BookDAO dao = new BookDAO();
        boolean success = dao.placeOrder(userId);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ViewCartServlet?message=Order placed successfully");
        } else {
            response.sendRedirect("ViewCartServlet?message=Error placing order");
        }
    }
}