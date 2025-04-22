import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManageBooksServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
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
        out.println("<title>Manage Books</title>");
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
        out.println("<h3>Manage Books</h3>");
        out.println("<form action='AddBookServlet' method='post' class='admin-form-container'>");
        out.println("<h4>Add a New Book</h4>");
        out.println("<label for='title'>Title:</label>");
        out.println("<input type='text' id='title' name='title' required><br>");
        out.println("<label for='author'>Author:</label>");
        out.println("<input type='text' id='author' name='author' required><br>");
        out.println("<label for='genre'>Genre:</label>");
        out.println("<input type='text' id='genre' name='genre' required><br>");
        out.println("<label for='price'>Price:</label>");
        out.println("<input type='number' id='price' name='price' required><br>");
        out.println("<label for='stock'>Stock:</label>");
        out.println("<input type='number' id='stock' name='stock' required><br>");
        out.println("<input type='submit' value='Add Book'>");
        out.println("</form>");
        out.println("<h4>Existing Books</h4>");
        out.println("<table>");
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
            out.println("<form action='UpdateBookServlet' method='post' class='admin-form-container'>");
            out.println("<input type='hidden' name='id' value='" + book.getId() + "'>");
            out.println("<input type='text' name='title' value='" + book.getTitle() + "' required>");
            out.println("<input type='text' name='author' value='" + book.getAuthor() + "' required>");
            out.println("<input type='text' name='genre' value='" + book.getGenre() + "' required>");
            out.println("<input type='number' name='price' value='" + book.getPrice() + "' required>");
            out.println("<input type='number' name='stock' value='" + book.getStock() + "' required>");
            out.println("<input type='submit' value='Update'>");
            out.println("</form>");
            out.println("<form action='DeleteBookServlet' method='post' class='admin-form-container'>");
            out.println("<input type='hidden' name='id' value='" + book.getId() + "'>");
            out.println("<input type='submit' value='Delete'>");
            out.println("</form>");
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