package com.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Version A - my own code, no AI used.
// Traditional style: I kept everything in the one controller class like we did
// in the lecture examples. It stores the data and does all the logic here.
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    // in-memory lists (no database for this assignment)
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<BorrowRecord> records = new ArrayList<BorrowRecord>();

    int loanDays = 14;          // you can keep a book for 14 days
    double finePerDay = 10.0;   // fine if you return it late

    // add some books when the app starts so there is something to borrow
    public LibraryController() {
        books.add(new Book("B001", "Clean Code", "Robert C. Martin"));
        books.add(new Book("B002", "Effective Java", "Joshua Bloch"));
        books.add(new Book("B003", "The Pragmatic Programmer", "Hunt & Thomas"));
    }

    // GET /api/library/books  -> list all books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // POST /api/library/borrow  body: {"memberId":"S001","bookId":"B001"}
    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody Map<String, String> body) {
        String memberId = body.get("memberId");
        String bookId = body.get("bookId");

        Book book = findBook(bookId);
        // NOTE: I forgot this null check in my first version and it crashed when
        // I posted a wrong book id, so I added it after testing.
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        if (book.isAvailable() == false) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Book unavailable. You may reserve it.");
        }

        book.setAvailable(false);
        records.add(new BorrowRecord(memberId, bookId, LocalDate.now()));
        return ResponseEntity.ok("Book issued to member " + memberId);
    }

    // DELETE /api/library/return  body: {"memberId","bookId","returnDate"}
    @DeleteMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody Map<String, String> body) {
        String memberId = body.get("memberId");
        String bookId = body.get("bookId");
        LocalDate returnDate = LocalDate.parse(body.get("returnDate"));

        BorrowRecord rec = findActiveRecord(memberId, bookId);
        if (rec == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No active borrow record found.");
        }

        Book book = findBook(bookId);
        if (book != null) {
            book.setAvailable(true);
        }
        rec.setReturnDate(returnDate);

        double fine = calculateFine(rec.getBorrowDate(), returnDate);
        if (fine > 0) {
            return ResponseEntity.ok("Book returned late. Fine due: " + fine);
        } else {
            return ResponseEntity.ok("Book returned on time. No fine.");
        }
    }

    // fine = late days * fine per day
    public double calculateFine(LocalDate borrowDate, LocalDate returnDate) {
        long days = ChronoUnit.DAYS.between(borrowDate, returnDate);
        long lateDays = days - loanDays;
        if (lateDays <= 0) {
            return 0.0;
        }
        return lateDays * finePerDay;
    }

    // linear search through the list
    private Book findBook(String bookId) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            if (b.getId().equals(bookId)) {
                return b;
            }
        }
        return null;
    }

    private BorrowRecord findActiveRecord(String memberId, String bookId) {
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
}
