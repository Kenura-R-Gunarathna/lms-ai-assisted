package com.library;

import com.library.model.Administrator;
import com.library.model.Book;
import com.library.model.Librarian;
import com.library.model.Student;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import com.library.service.LibraryService;
import java.time.LocalDate;

/**
 * Version B - AI-Assisted Approach
 * Wires the layers together (Model -> Repository -> Service) and runs a demo.
 */
public class Main {

    public static void main(String[] args) {

        // Compose the layers.
        BookRepository bookRepo = new BookRepository();
        BorrowRepository borrowRepo = new BorrowRepository();
        LibraryService library = new LibraryService(bookRepo, borrowRepo);

        // Users (inheritance).
        User student = new Student("S001", "Kenura");
        User librarian = new Librarian("L001", "Ms. Perera");
        User admin = new Administrator("A001", "Mr. Silva");
        System.out.println(student);
        System.out.println(librarian);
        System.out.println(admin);
        System.out.println();

        // Catalogue.
        library.addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
        library.addBook(new Book("B002", "Effective Java", "Joshua Bloch"));
        library.addBook(new Book("B003", "The Pragmatic Programmer", "Hunt & Thomas"));

        // Borrow / return flows (service returns strings).
        System.out.println(library.borrowBook("S001", "B002"));
        System.out.println(library.borrowBook("S001", "B002")); // unavailable
        System.out.println(library.borrowBook("S001", "B999")); // not found
        System.out.println(library.returnBook("S001", "B002", LocalDate.now().plusDays(5)));
        System.out.println();

        System.out.println(library.borrowBook("S001", "B001"));
        System.out.println(library.returnBook("S001", "B001", LocalDate.now().plusDays(20)));
    }
}
