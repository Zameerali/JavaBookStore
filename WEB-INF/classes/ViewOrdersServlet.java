import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !session.getAttribute("role").equals("USER")) {
            response.sendRedirect("login.html");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        BookDAO dao = new BookDAO();
        List<Order> orders = dao.getOrdersByUserId(userId);
        dao.closeConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>View Orders</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='navbar'>");
        out.println("<h2>Book Store</h2>");
        out.println("<ul>");
        out.println("<li><a href='user-dashboard.html'>Dashboard</a></li>");
        out.println("<li><a href='ViewBooksServlet'>View Catalog</a></li>");
        out.println("<li><a href='ViewCartServlet'>View Cart</a></li>");
        out.println("<li><a href='ViewOrdersServlet'>View Orders</a></li>");
        out.println("<li><a href='ViewWishlistServlet'>View Wishlist</a></li>");
        out.println("<li><a href='LogoutServlet'>Logout</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<div class='main-content'>");
        out.println("<h3>Your Orders</h3>");
        if (orders.isEmpty()) {
            out.println("<p>You have no orders.</p>");
        } else {
            out.println("<table border='1' cellpadding='5' cellspacing='0'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Order ID</th>");
            out.println("<th>Order Date</th>");
            out.println("<th>Status</th>");
            out.println("<th>Details</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            for (Order order : orders) {
                out.println("<tr>");
                out.println("<td>" + order.getId() + "</td>");
                out.println("<td>" + order.getOrderDate() + "</td>");
                out.println("<td>" + order.getStatus() + "</td>");
                out.println("<td>");
                out.println("<table border='1' cellpadding='5' cellspacing='0' width='100%'>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>Book ID</th>");
                out.println("<th>Quantity</th>");
                out.println("<th>Price</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                for (OrderDetail detail : order.getDetails()) {
                    out.println("<tr>");
                    out.println("<td>" + detail.getBookId() + "</td>");
                    out.println("<td>" + detail.getQuantity() + "</td>");
                    out.println("<td>" + detail.getPrice() + "</td>");
                    out.println("</tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}