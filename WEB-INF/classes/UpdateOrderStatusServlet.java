import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateOrderStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        BookDAO dao = new BookDAO();
        boolean success = dao.updateOrderStatus(orderId, status);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ManageOrdersServlet?message=Order status updated successfully");
        } else {
            response.getWriter().println("Error updating order status.");
        }
    }
}