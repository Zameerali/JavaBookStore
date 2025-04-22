public class WishlistItem {
    private int bookId;
    private String title;
    private String author;
    private double price;

    public WishlistItem(int bookId, String title, String author, double price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    public int getBookId() { return
         bookId; }
    public String getTitle() { 
        return title; }
    public String getAuthor() {
         return author; }
    public double getPrice() {
         return price; }
    public void setBookId(int bookId) {
         this.bookId = bookId; }
    public void setTitle(String title) { 
        this.title = title; }
    public void setAuthor(String author) { 
        this.author = author; }
    public void setPrice(double price) { 
        this.price = price; }
}