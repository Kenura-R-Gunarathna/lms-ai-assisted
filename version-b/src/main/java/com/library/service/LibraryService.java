package com.library.service;

import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Version B - AI-Assisted Approach | Layer: SERVICE
 * Business logic. Depends on repositories (not on storage details) and returns
 * result strings instead of printing, which the AI noted improves testability.
 */
public class LibraryService {

    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 10.0;

    private final BookRepository bookRepo;
    private final BorrowRepository borrowRepo;

    // Dependency injection: the service is given its repositories.
    public LibraryService(BookRepository bookRepo, BorrowRepository borrowRepo) {
        this.bookRepo = bookRepo;
        this.borrowRepo = borrowRepo;
    }

    public void addBook(Book book) {
        bookRepo.save(book);
    }

    public String borrowBook(String memberId, String bookId) {
        Book book = bookRepo.findById(bookId);          // O(1) lookup (AI suggestion)
        if (book == null)        return "Book not found.";
        if (!book.isAvailable()) return "Unavailable. You may reserve it.";

        book.setAvailable(false);
        borrowRepo.save(new BorrowRecord(memberId, bookId, LocalDate.now()));
        return "Book issued to member " + memberId;
    }

    public String returnBook(String memberId, String bookId, LocalDate returnDate) {
        BorrowRecord record = borrowRepo.findActive(memberId, bookId);
        if (record == null) return "No active borrow record found.";

        Book book = bookRepo.findById(bookId);
        if (book != null) book.setAvailable(true);
        record.setReturnDate(returnDate);

        double fine = calculateFine(record.getBorrowDate(), returnDate);
        return fine > 0
                ? "Book returned late. Fine due: " + fine
                : "Book returned on time. No fine.";
    }

    // Business rule (written manually - specific to our library).
    public double calculateFine(LocalDate borrowDate, LocalDate returnDate) {
        long lateDays = ChronoUnit.DAYS.between(borrowDate, returnDate) - LOAN_DAYS;
        return lateDays > 0 ? lateDays * FINE_PER_DAY : 0.0;
    }
}
