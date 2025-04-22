import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewBooksServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || !session.getAttribute("role").equals("USER")) {
            response.sendRedirect("login.html");
            return;
        }
        BookDAO dao = new BookDAO();
        List<Book> books = dao.getAllBooks();
        dao.closeConnection();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>View Catalog</title>");
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
        out.println("<h3>Available Books</h3>");
        out.println("<table border='1' cellpadding='5' cellspacing='0'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Title</th>");
        out.println("<th>Author</th>");
        out.println("<th>Genre</th>");
        out.println("<th>Price</th>");
        out.println("<th>Stock</th>");
        out.println("<th>Actions</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        for (Book book : books) {
            out.println("<tr>");
            out.println("<td>" + book.getTitle() + "</td>");
            out.println("<td>" + book.getAuthor() + "</td>");
            out.println("<td>" + book.getGenre() + "</td>");
            out.println("<td>" + book.getPrice() + "</td>");
            out.println("<td>" + book.getStock() + "</td>");
            out.println("<td>");
            out.println("<form action='AddToCartServlet' method='post' style='display:inline;'>");
            out.println("<input type='hidden' name='bookId' value='" + book.getId() + "'>");
            out.println("<input type='number' name='quantity' min='1' max='" + book.getStock() + "' required>");
            out.println("<input type='submit' value='Add to Cart'>");
            out.println("</form>");
            out.println("<form action='AddToWishlistServlet' method='post' style='display:inline;'>");
            out.println("<input type='hidden' name='bookId' value='" + book.getId() + "'>");
            out.println("<input type='submit' value='Add to Wishlist'>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}