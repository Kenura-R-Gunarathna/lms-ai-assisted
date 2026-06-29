package com.library.controller;

import com.library.dto.BorrowRequest;
import com.library.dto.ReturnRequest;
import com.library.model.Book;
import com.library.service.LibraryService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * Version B - AI-Assisted Approach | Layer: CONTROLLER
 * Thin REST layer. It only maps HTTP requests to service calls; all business
 * logic lives in {@link LibraryService}, and error-to-status mapping is handled
 * by the domain exceptions.
 */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    // GET /api/library/books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return service.listBooks();
    }

    // POST /api/library/borrow
    @PostMapping("/borrow")
    public String borrow(@RequestBody BorrowRequest request) {
        return service.borrowBook(request.memberId(), request.bookId());
    }

    // DELETE /api/library/return
    @DeleteMapping("/return")
    public String returnBook(@RequestBody ReturnRequest request) {
        return service.returnBook(
                request.memberId(),
                request.bookId(),
                LocalDate.parse(request.returnDate()));
    }
}
