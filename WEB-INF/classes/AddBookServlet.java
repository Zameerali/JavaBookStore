import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        BookDAO dao = new BookDAO();
        boolean success = dao.addBook(title, author, genre, price, stock);
        dao.closeConnection();
        if (success) {
            response.sendRedirect("ManageBooksServlet?message=Book added successfully");
        } else {
            response.getWriter().println("Error adding book.");
        }
    }
}