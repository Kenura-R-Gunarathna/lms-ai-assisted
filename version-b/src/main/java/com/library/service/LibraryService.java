package com.library.service;

import com.library.exception.ConflictException;
import com.library.exception.NotFoundException;
import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Version B - AI-Assisted Approach | Layer: SERVICE
 * Business logic. Depends on repositories (not on storage details). On failure
 * it throws domain exceptions that Spring maps to HTTP 404 / 409, keeping the
 * controller thin - a cleaner separation than Version A (AI suggestion).
 */
@Service
public class LibraryService {

    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 10.0;

    private final BookRepository bookRepo;
    private final BorrowRepository borrowRepo;

    // Constructor injection: Spring supplies the repositories.
    public LibraryService(BookRepository bookRepo, BorrowRepository borrowRepo) {
        this.bookRepo = bookRepo;
        this.borrowRepo = borrowRepo;
    }

    // Seed a few books at startup so the catalogue is not empty.
    @PostConstruct
    void seedCatalogue() {
        addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
        addBook(new Book("B002", "Effective Java", "Joshua Bloch"));
        addBook(new Book("B003", "The Pragmatic Programmer", "Hunt & Thomas"));
    }

    public void addBook(Book book) {
        bookRepo.save(book);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(bookRepo.findAll());
    }

    public String borrowBook(String memberId, String bookId) {
        Book book = bookRepo.findById(bookId);          // O(1) lookup (AI suggestion)
        if (book == null) {
            throw new NotFoundException("Book not found.");
        }
        if (!book.isAvailable()) {
            throw new ConflictException("Unavailable. You may reserve it.");
        }
        book.setAvailable(false);
        borrowRepo.save(new BorrowRecord(memberId, bookId, LocalDate.now()));
        return "Book issued to member " + memberId;
    }

    public String returnBook(String memberId, String bookId, LocalDate returnDate) {
        BorrowRecord record = borrowRepo.findActive(memberId, bookId);
        if (record == null) {
            throw new NotFoundException("No active borrow record found.");
        }
        Book book = bookRepo.findById(bookId);
        if (book != null) {
            book.setAvailable(true);
        }
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
