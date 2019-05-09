package model;

/**
 * Created by sharaf on 09/05/2019.
 */
public class BookSearchResult {

    private int isbn;

    public int getIsbn() {
        return isbn;
    }

    private String bookTitle, year, price ,quantity;

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public BookSearchResult(int isbn, String bookTitle, String year, String price, String quantity) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }
}
