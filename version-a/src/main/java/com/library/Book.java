package com.library;

// Version A - my own code, no AI used.
// Just a simple book class to hold the details.
public class Book {

    String id;        // book id like B001
    String title;
    String author;
    boolean available;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;   // new book is available by default
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // print nicely
    public String toString() {
        String status;
        if (available) {
            status = "Available";
        } else {
            status = "Borrowed";
        }
        return "[" + id + "] " + title + " by " + author + " (" + status + ")";
    }
}
