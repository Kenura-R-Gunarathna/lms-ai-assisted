package com.library.model;

import java.time.LocalDate;

/**
 * Version B - AI-Assisted Approach | Layer: MODEL
 * Immutable borrow details with a mutable return date.
 */
public class BorrowRecord {

    private final String memberId;
    private final String bookId;
    private final LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowRecord(String memberId, String bookId, LocalDate borrowDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    public String getMemberId()      { return memberId; }
    public String getBookId()        { return bookId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public boolean isReturned()      { return returnDate != null; }
}
