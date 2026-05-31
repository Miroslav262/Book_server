package server.books_server.records;

public record UserResponse(
        Long id,
        String username,
        String email,
        String bio,
        String avatarUrl
) {}
