package com.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.library.exception.ConflictException;
import com.library.exception.NotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Version B - AI-Assisted Approach | JUnit 5 tests.
 * Each test maps to a REST API scenario. The AI drafted the borrow
 * happy/rejection-path tests; the developer added the fine / edge-case tests.
 */
class LibraryServiceTest {

    private LibraryService library;

    @BeforeEach
    void setUp() {
        // Build the service directly (no Spring context); seed one book.
        library = new LibraryService(new BookRepository(), new BorrowRepository());
        library.addBook(new Book("B001", "Clean Code", "Robert C. Martin"));
    }

    // ---- AI-generated: POST /borrow scenarios ----------------------------
    @Test // 200 OK
    void borrowAvailableBook_succeeds() {
        assertEquals("Book issued to member S001", library.borrowBook("S001", "B001"));
    }

    @Test // 409 Conflict
    void borrowUnavailableBook_isRejected() {
        library.borrowBook("S001", "B001");
        assertThrows(ConflictException.class, () -> library.borrowBook("S002", "B001"));
    }

    @Test // 404 Not Found
    void borrowMissingBook_returnsNotFound() {
        assertThrows(NotFoundException.class, () -> library.borrowBook("S001", "B999"));
    }

    // ---- edge cases added manually: DELETE /return scenarios -------------
    @Test // 200 OK, no fine
    void returnOnTime_hasNoFine() {
        library.borrowBook("S001", "B001");
        assertEquals("Book returned on time. No fine.",
                library.returnBook("S001", "B001", LocalDate.now().plusDays(10)));
    }

    @Test // fine calculation
    void returnLate_chargesCorrectFine() {
        LocalDate borrow = LocalDate.of(2026, 1, 1);
        // 20 days total - 14 day loan = 6 late days * 10.0 = 60.0
        assertEquals(60.0, library.calculateFine(borrow, borrow.plusDays(20)));
    }

    @Test // 404 Not Found
    void returnWithoutBorrow_isHandled() {
        assertThrows(NotFoundException.class,
                () -> library.returnBook("S001", "B001", LocalDate.now()));
    }
}
