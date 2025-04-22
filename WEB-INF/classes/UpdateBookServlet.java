import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect("login.html");
            return;
        }
        try {
            String idStr = request.getParameter("id");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String genre = request.getParameter("genre");
            String priceStr = request.getParameter("price");
            String stockStr = request.getParameter("stock");
            if (idStr == null || title == null || author == null || genre == null || priceStr == null || stockStr == null) {
                throw new NumberFormatException("One or more parameters are missing.");
            }
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            BookDAO dao = new BookDAO();
            boolean success = dao.updateBook(id, title, author, genre, price, stock);
            dao.closeConnection();
            if (success) {
                response.sendRedirect("ManageBooksServlet?message=Book updated successfully");
            } else {
                response.getWriter().println("Error updating book.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid input: " + e.getMessage());
        }
    }
}