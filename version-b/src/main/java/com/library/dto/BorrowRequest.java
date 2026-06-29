package com.library.dto;

/**
 * Version B - AI-Assisted Approach | Layer: DTO
 * Request body for POST /api/library/borrow.
 */
public record BorrowRequest(String memberId, String bookId) {
}
