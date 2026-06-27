package com.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Version B - AI-Assisted Approach | JUnit 5 tests.
 * The AI drafted the first three "happy/rejection path" tests; the developer
 * added the three fine / edge-case tests below.
 */
class LibraryServiceTest {

    private LibraryService library;

    @BeforeEach
    void setUp() {
        library = new LibraryService(new BookRepository(), new BorrowRepository());
        library.addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
    }

    // ---- AI-generated ----------------------------------------------------
    @Test
    void borrowAvailableBook_succeeds() {
        assertEquals("Book issued to member S001", library.borrowBook("S001", "B001"));
    }

    @Test
    void borrowUnavailableBook_isRejected() {
        library.borrowBook("S001", "B001");
        assertEquals("Unavailable. You may reserve it.", library.borrowBook("S002", "B001"));
    }

    @Test
    void borrowMissingBook_returnsNotFound() {
        assertEquals("Book not found.", library.borrowBook("S001", "B999"));
    }

    // ---- edge cases added manually by the developer ----------------------
    @Test
    void returnOnTime_hasNoFine() {
        library.borrowBook("S001", "B001");
        assertEquals("Book returned on time. No fine.",
                library.returnBook("S001", "B001", LocalDate.now().plusDays(10)));
    }

    @Test
    void returnLate_chargesCorrectFine() {
        LocalDate borrow = LocalDate.of(2026, 1, 1);
        // 20 days total - 14 day loan = 6 late days * 10.0 = 60.0
        assertEquals(60.0, library.calculateFine(borrow, borrow.plusDays(20)));
    }

    @Test
    void returnWithoutBorrow_isHandled() {
        assertEquals("No active borrow record found.",
                library.returnBook("S001", "B001", LocalDate.now()));
    }
}
