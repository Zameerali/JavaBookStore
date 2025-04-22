public class OrderDetail {
    private int orderId;
    private int bookId;
    private int quantity;
    private double price;
    public OrderDetail(int orderId, int bookId, int quantity, double price) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
    public int getOrderId() {
         return orderId; }
    public int getBookId() {
         return bookId; }
    public int getQuantity() {
         return quantity; }
    public double getPrice() {
         return price; }

    public void setOrderId(int orderId) { 
        this.orderId = orderId; }
    public void setBookId(int bookId) {
         this.bookId = bookId; }
    public void setQuantity(int quantity) {
         this.quantity = quantity; }
    public void setPrice(double price) { 
        this.price = price; }
}