package server.books_server.records;

public record UserResponse(
        Long id,
        String username,
        String avatarUrl
) {}
