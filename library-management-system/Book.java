import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrow() {
        this.isBorrowed = true;
    }
    public void giveBack() {
        this.isBorrowed = false;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " - " + title + " by " + author + (isBorrowed ? " (Borrow)" : "");
    }
}