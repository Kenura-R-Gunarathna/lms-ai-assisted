package com.library.repository;

import com.library.model.Book;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Version B - AI-Assisted Approach | Layer: REPOSITORY
 * Encapsulates data access for books. The AI suggested a HashMap for O(1)
 * lookup instead of the linear ArrayList scan used in Version A.
 */
public class BookRepository {

    private final Map<String, Book> catalogue = new HashMap<>();

    public void save(Book book) {
        catalogue.put(book.getId(), book);
    }

    public Book findById(String bookId) {
        return catalogue.get(bookId);   // O(1)
    }

    public boolean exists(String bookId) {
        return catalogue.containsKey(bookId);
    }

    public Collection<Book> findAll() {
        return catalogue.values();
    }
}
