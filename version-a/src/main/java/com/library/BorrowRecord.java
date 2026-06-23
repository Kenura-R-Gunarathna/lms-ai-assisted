package com.library;

import java.time.LocalDate;

// keeps track of who borrowed what and when
public class BorrowRecord {

    String memberId;
    String bookId;
    LocalDate borrowDate;
    LocalDate returnDate;   // stays null until returned

    public BorrowRecord(String memberId, String bookId, LocalDate borrowDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // if returnDate is set then it means the book was returned
    public boolean isReturned() {
        if (returnDate == null) {
            return false;
        } else {
            return true;
        }
    }
}
