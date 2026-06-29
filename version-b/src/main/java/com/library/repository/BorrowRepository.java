package com.library.repository;

import com.library.model.BorrowRecord;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Version B - AI-Assisted Approach | Layer: REPOSITORY
 * Encapsulates data access for borrow records.
 */
@Repository
public class BorrowRepository {

    private final List<BorrowRecord> records = new ArrayList<>();

    public void save(BorrowRecord record) {
        records.add(record);
    }

    public BorrowRecord findActive(String memberId, String bookId) {
        for (BorrowRecord r : records) {
            if (r.getMemberId().equals(memberId)
                    && r.getBookId().equals(bookId)
                    && !r.isReturned()) {
                return r;
            }
        }
        return null;
    }

    public List<BorrowRecord> findAll() {
        return records;
    }
}
