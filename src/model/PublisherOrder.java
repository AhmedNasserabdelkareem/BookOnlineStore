package model;

/**
 * Created by sharaf on 10/05/2019.
 */
public class PublisherOrder {

    private int isbn;
    private String publisherName;

    public int getIsbn() {
        return isbn;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public PublisherOrder(int isbn, String publisherName) {

        this.isbn = isbn;
        this.publisherName = publisherName;
    }
}
