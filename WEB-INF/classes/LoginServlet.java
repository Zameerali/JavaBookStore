import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        BookDAO dao = new BookDAO();
        User user = dao.login(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getId()); 
            if (user.getRole().equalsIgnoreCase("ADMIN")) { 
                response.sendRedirect("admin-dashboard.html"); 
            } else {
                response.sendRedirect("user-dashboard.html"); 
            }
        } else {
            response.getWriter().write("Invalid username or password.");
        }
                dao.closeConnection();
    }
}