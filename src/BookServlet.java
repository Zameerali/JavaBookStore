import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Test database connection
        Connection connection = BookDAO.getConnection();

        // Check if the connection is successful
        if (connection != null) {
            response.getWriter().write("Database connection successful!");
        } else {
            response.getWriter().write("Database connection failed.");
        }
    }
}
