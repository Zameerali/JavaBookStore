import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        BookDAO dao = new BookDAO();
        List<Order> orders = dao.getAllOrders();
        dao.closeConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Manage Orders</title>");
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
        out.println("<h3>Manage Orders</h3>");
        out.println("<h4>Existing Orders</h4>");
        out.println("<table border='1' cellpadding='5' cellspacing='0'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Order ID</th>");
        out.println("<th>User ID</th>");
        out.println("<th>Order Date</th>");
        out.println("<th>Status</th>");
        out.println("<th>Total Price</th>");
        out.println("<th>Actions</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        for (Order order : orders) {
            double totalPrice = 0;
            for (OrderDetail detail : order.getDetails()) {
                totalPrice += detail.getPrice() * detail.getQuantity();
            }
            out.println("<tr>");
            out.println("<td>" + order.getId() + "</td>");
            out.println("<td>" + order.getUserId() + "</td>");
            out.println("<td>" + order.getOrderDate() + "</td>");
            out.println("<td>" + order.getStatus() + "</td>");
            out.println("<td>" + totalPrice + "</td>");
            out.println("<td>");
            out.println("<form action='UpdateOrderStatusServlet' method='post' class='admin-form-container'>");
            out.println("<input type='hidden' name='orderId' value='" + order.getId() + "'>");
            out.println("<select name='status' required>");
            out.println("<option value='Processing'" + ("Processing".equals(order.getStatus()) ? " selected" : "") + ">Processing</option>");
            out.println("<option value='Shipped'" + ("Shipped".equals(order.getStatus()) ? " selected" : "") + ">Shipped</option>");
            out.println("<option value='Delivered'" + ("Delivered".equals(order.getStatus()) ? " selected" : "") + ">Delivered</option>");
            out.println("</select>");
            out.println("<input type='submit' value='Update Status'>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan='6'>");
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
        out.println("</div>");
        String message = request.getParameter("message");
        if (message != null) {
            out.println("<script>alert('" + message + "');</script>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}