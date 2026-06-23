package com.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// This is the main class that does everything for the library.
// Version A - written by me manually without any AI help.
public class Library {

    // I used ArrayLists because that is what we did in the lectures.
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<BorrowRecord> records = new ArrayList<BorrowRecord>();

    // rules for the library
    int loanDays = 14;          // you can keep a book for 14 days
    double finePerDay = 10.0;   // fine if you are late

    public void addBook(Book book) {
        books.add(book);
    }

    // borrow a book
    public void borrowBook(String memberId, String bookId) {
        Book book = findBook(bookId);

        // NOTE: I forgot this null check in my first version and it crashed
        // when I typed a wrong book id, so I added it after testing.
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isAvailable() == false) {
            System.out.println("Book unavailable. You may reserve it.");
            return;
        }

        book.setAvailable(false);
        BorrowRecord r = new BorrowRecord(memberId, bookId, LocalDate.now());
        records.add(r);
        System.out.println("Book issued to member " + memberId);
    }

    // return a book
    public void returnBook(String memberId, String bookId, LocalDate returnDate) {
        BorrowRecord rec = findActiveRecord(memberId, bookId);
        if (rec == null) {
            System.out.println("No active borrow record found.");
            return;
        }

        Book book = findBook(bookId);
        if (book != null) {
            book.setAvailable(true);
        }
        rec.setReturnDate(returnDate);

        // work out the fine
        double fine = calculateFine(rec.getBorrowDate(), returnDate);
        if (fine > 0) {
            System.out.println("Book returned late. Fine due: " + fine);
        } else {
            System.out.println("Book returned on time. No fine.");
        }
    }

    // fine = number of late days * fine per day
    public double calculateFine(LocalDate borrowDate, LocalDate returnDate) {
        long days = ChronoUnit.DAYS.between(borrowDate, returnDate);
        long lateDays = days - loanDays;
        if (lateDays <= 0) {
            return 0.0;   // returned on time
        }
        double fine = lateDays * finePerDay;
        return fine;
    }

    // go through the list and find the book (linear search)
    public Book findBook(String bookId) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.getId().equals(bookId)) {
                return b;
            }
        }
        return null;   // not found
    }

    // find a borrow record that is not returned yet
    public BorrowRecord findActiveRecord(String memberId, String bookId) {
        for (int i = 0; i < records.size(); i++) {
            BorrowRecord r = records.get(i);
            if (r.getMemberId().equals(memberId)
                    && r.getBookId().equals(bookId)
                    && r.isReturned() == false) {
                return r;
            }
        }
        return null;
    }

    public void listBooks() {
        System.out.println("--- Catalogue ---");
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
}
