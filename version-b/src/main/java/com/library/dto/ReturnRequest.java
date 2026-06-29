package com.library.dto;

/**
 * Version B - AI-Assisted Approach | Layer: DTO
 * Request body for DELETE /api/library/return. returnDate is an ISO date
 * string (e.g. "2026-06-30").
 */
public record ReturnRequest(String memberId, String bookId, String returnDate) {
}
