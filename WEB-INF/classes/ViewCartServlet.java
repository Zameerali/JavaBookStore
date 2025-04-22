import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !session.getAttribute("role").equals("USER")) {
            response.sendRedirect("login.html");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        BookDAO dao = new BookDAO();
        List<CartItem> cartItems = dao.getCartItems(userId);
        dao.closeConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>View Cart</title>");
        out.println("<link rel='stylesheet' href='styles.css'>");
        out.println("<script>");
        out.println("function showAlert(message) {");
        out.println("  alert(message);");
        out.println("}");
        out.println("window.onload = function() {");
        out.println("  const urlParams = new URLSearchParams(window.location.search);");
        out.println("  const message = urlParams.get('message');");
        out.println("  if (message) {");
        out.println("    showAlert(message);");
        out.println("    const newUrl = window.location.origin + window.location.pathname;");
        out.println("    window.history.replaceState({}, document.title, newUrl);");
        out.println("  }");
        out.println("}");
        out.println("</script>");
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
        // String message = request.getParameter("message");
        // if (message != null) {
        //     out.println("<script>showAlert('" + message + "');</script>");
        // }
        out.println("<h3>Your Cart</h3>");
        if (cartItems.isEmpty()) {
            out.println("<p>Your cart is empty.</p>");
        } else {
            out.println("<table border='1' cellpadding='5' cellspacing='0'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Book ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Author</th>");
            out.println("<th>Price</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Total</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            double grandTotal = 0.0;
            for (CartItem item : cartItems) {
                double itemTotal = item.getQuantity() * item.getPrice();
                grandTotal += itemTotal;
                out.println("<tr>");
                out.println("<td>" + item.getBookId() + "</td>");
                out.println("<td>" + item.getTitle() + "</td>");
                out.println("<td>" + item.getAuthor() + "</td>");
                out.println("<td>" + item.getPrice() + "</td>");
                out.println("<td>" + item.getQuantity() + "</td>");
                out.println("<td>" + itemTotal + "</td>");
                out.println("<td>");
                out.println("<form action='UpdateCartServlet' method='post' style='display:inline;'>");
                out.println("<input type='hidden' name='bookId' value='" + item.getBookId() + "'>");
                out.println("<input type='number' name='quantity' value='" + item.getQuantity() + "' min='1' required>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
                out.println("<form action='RemoveFromCartServlet' method='post' style='display:inline;'>");
                out.println("<input type='hidden' name='bookId' value='" + item.getBookId() + "'>");
                out.println("<input type='submit' value='Remove'>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("<h4>Grand Total: " + grandTotal + "</h4>");
            out.println("<form action='PlaceOrderServlet' method='post'>");
            out.println("<input type='submit' value='Place Order'>");
            out.println("</form>");
        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}