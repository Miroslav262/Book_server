package server.books_server.records;

public record CreateReviewRequest(
        String text,
        int rating
) {}
