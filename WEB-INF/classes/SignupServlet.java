import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        BookDAO dao = new BookDAO();
        boolean isRegistered = dao.signUp(username, password, role);
        if (isRegistered) {
            response.sendRedirect("login.html");
            //RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
            // dispatcher.forward(request, response);
        } else {
            response.getWriter().write("Registration failed. Please try again.");
        }
        dao.closeConnection();
    }
}
