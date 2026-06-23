package com.library;

// librarian manages the books
public class Librarian extends User {

    public Librarian(String id, String name) {
        super(id, name);
    }

    public String getRole() {
        return "Librarian";
    }
}
