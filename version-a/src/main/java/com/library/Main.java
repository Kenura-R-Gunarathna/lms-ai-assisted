package com.library;

import java.time.LocalDate;

// Main class to test my library system.
// I first wrote a menu with Scanner but for the report demo I just call the
// methods directly so it runs the same way every time.
public class Main {

    public static void main(String[] args) {

        Library library = new Library();

        // make some users to show the inheritance works
        User student = new Student("S001", "Kenura");
        User librarian = new Librarian("L001", "Ms. Perera");
        User admin = new Administrator("A001", "Mr. Silva");
        System.out.println(student);
        System.out.println(librarian);
        System.out.println(admin);
        System.out.println();

        // add books
        library.addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
        library.addBook(new Book("B002", "Effective Java", "Joshua Bloch"));
        library.addBook(new Book("B003", "The Pragmatic Programmer", "Hunt & Thomas"));
        library.listBooks();
        System.out.println();

        // try to borrow
        library.borrowBook("S001", "B002");
        library.borrowBook("S001", "B002");   // already borrowed
        library.borrowBook("S001", "B999");   // wrong id
        System.out.println();

        // return on time (5 days) - no fine
        library.returnBook("S001", "B002", LocalDate.now().plusDays(5));
        System.out.println();

        // borrow again and return late (20 days) - fine
        library.borrowBook("S001", "B001");
        library.returnBook("S001", "B001", LocalDate.now().plusDays(20));
        System.out.println();

        library.listBooks();
    }
}
