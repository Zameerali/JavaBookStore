import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private String status;
    private List<OrderDetail> details;
    public Order(int id, int userId, Date orderDate, String status, List<OrderDetail> details) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.details = details;
    }
    public int getId() {
         return id; }
    public int getUserId() {
         return userId; }
    public Date getOrderDate() {
         return orderDate; }
    public String getStatus() {
         return status; }
    public List<OrderDetail> getDetails() {
         return details; }
    public void setId(int id) {
         this.id = id; }
    public void setUserId(int userId) { 
        this.userId = userId; }
    public void setOrderDate(Date orderDate) {
         this.orderDate = orderDate; }
    public void setStatus(String status) {
         this.status = status; }
    public void setDetails(List<OrderDetail> details) {
         this.details = details; }
}