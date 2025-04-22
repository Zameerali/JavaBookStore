import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "root");
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
   public User login(String username, String password) {
        String query = "SELECT id, role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    String role = rs.getString("role");
                    return new User(userId, username, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return null;
    }

    
    public boolean signUp(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
         String query = "INSERT  into users(username,password,role) where ? ? ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true; 
        } catch (SQLException e) {
            System.err.println("Sign-up error: " + e.getMessage());
        }
        return false;
    }
   public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT id, title, author, genre, price, stock FROM books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching books: " + e.getMessage());
        }
        return books;
    }
 public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, role FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

     public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        return executeUpdate(query, username);
    }
    public boolean updateUser(String username, String newUsername, String newPassword, String newRole) {
    String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE username = ?";
    return executeUpdate(query, newUsername, newPassword, newRole, username);
}
 public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, user_id, order_date, status FROM orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDate("order_date"),
                    rs.getString("status"),
                    getOrderDetailsByOrderId(rs.getInt("id"))
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
        return orders;
    }
    private List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String query = "SELECT book_id, quantity, price FROM order_details WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetail detail = new OrderDetail(
                        orderId,
                        rs.getInt("book_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching order details: " + e.getMessage());
        }
        return details;
    }
     public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }
    public boolean addToWishlist(int userId, int bookId) {
        String query = "INSERT INTO wishlist (user_id, book_id) VALUES (?, ?)";
        return executeUpdate(query, userId, bookId);
    }
    public boolean removeFromWishlist(int userId, int bookId) {
        String query = "DELETE FROM wishlist WHERE user_id = ? AND book_id = ?";
        return executeUpdate(query, userId, bookId);
    }
      public List<WishlistItem> getWishlistItems(int userId) {
        List<WishlistItem> wishlistItems = new ArrayList<>();
        String query = "SELECT w.book_id, b.title, b.author, b.price FROM wishlist w JOIN books b ON w.book_id = b.id WHERE w.user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WishlistItem item = new WishlistItem(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price")
                    );
                    wishlistItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching wishlist items: " + e.getMessage());
        }
        return wishlistItems;
    }
    public boolean addToCart(int userId, int bookId, int quantity) {
        String query = "INSERT INTO cart (user_id, book_id, quantity) VALUES (?, ?, ?)";
        return executeUpdate(query, userId, bookId, quantity);
    }
    public boolean removeFromCart(int userId, int bookId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND book_id = ?";
        return executeUpdate(query, userId, bookId);
    }
    public boolean updateCart(int userId, int bookId, int quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE user_id = ? AND book_id = ?";
        return executeUpdate(query, quantity, userId, bookId);
    }
     public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT c.book_id, c.quantity, b.title, b.author, b.price FROM cart c JOIN books b ON c.book_id = b.id WHERE c.user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                    );
                    cartItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cart items: " + e.getMessage());
        }
        return cartItems;
    }
    public boolean placeOrder(int userId) {
        String orderQuery = "INSERT INTO orders (user_id, order_date, status) VALUES (?, NOW(), 'Processing')";
        try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, userId);
            orderStmt.executeUpdate();
            try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    transferCartToOrder(userId, orderId);
                    clearCart(userId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Order placement error: " + e.getMessage());
        }
        return false;
    }
    private void transferCartToOrder(int userId, int orderId) throws SQLException {
        String cartQuery = "SELECT book_id, quantity FROM cart WHERE user_id = ?";
        try (PreparedStatement cartStmt = conn.prepareStatement(cartQuery)) {
            cartStmt.setInt(1, userId);
            try (ResultSet rs = cartStmt.executeQuery()) {
                while (rs.next()) {
                    int bookId = rs.getInt("book_id");
                    int quantity = rs.getInt("quantity");
                    String orderDetailsQuery = "INSERT INTO order_details (order_id, book_id, quantity, price) " +
                            "VALUES (?, ?, ?, (SELECT price FROM books WHERE id = ?))";
                    try (PreparedStatement orderDetailsStmt = conn.prepareStatement(orderDetailsQuery)) {
                        orderDetailsStmt.setInt(1, orderId);
                        orderDetailsStmt.setInt(2, bookId);
                        orderDetailsStmt.setInt(3, quantity);
                        orderDetailsStmt.setInt(4, bookId);
                        orderDetailsStmt.executeUpdate();
                    }
                }
            }
        }
    }
    private void clearCart(int userId) throws SQLException {
        String clearCartQuery = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement clearCartStmt = conn.prepareStatement(clearCartQuery)) {
            clearCartStmt.setInt(1, userId);
            clearCartStmt.executeUpdate();
        }
    }
    private boolean executeUpdate(String query, Object... params) {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            return false;
        }
    } 
       public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, order_date, status FROM orders WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                        rs.getInt("id"),
                        userId,
                        rs.getDate("order_date"),
                        rs.getString("status"),
                        getOrderDetailsByOrderId(rs.getInt("id"))
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
        return orders;
    }
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
public boolean bookExists(String title, String author) {
    String query = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException e) {
        System.err.println("Error checking book existence: " + e.getMessage());
    }
    return false;
}
public boolean updateBookStock(String title, String author, int stock) {
    String query = "UPDATE books SET stock = stock + ? WHERE title = ? AND author = ?";
    return executeUpdate(query, stock, title, author);
}
public boolean addBook(String title, String author, String genre, double price, int stock) {
    if (bookExists(title, author)) {
        return updateBookStock(title, author, stock);
    } else {
        String query = "INSERT INTO books (title, author, genre, price, stock) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, title, author, genre, price, stock);
    }
}
public boolean updateBook(int id, String title, String author, String genre, double price, int stock) {
    String query = "UPDATE books SET title = ?, author = ?, genre = ?, price = ?, stock = ? WHERE id = ?";
    return executeUpdate(query, title, author, genre, price, stock, id);
}

public boolean deleteBook(int id) {
    String query = "DELETE FROM books WHERE id = ?";
    return executeUpdate(query, id);
}
}
