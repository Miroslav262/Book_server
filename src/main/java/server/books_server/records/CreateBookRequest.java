package server.books_server.records;

import server.books_server.enums.BookCondition;

import java.util.List;

public record CreateBookRequest(
        String title,
        String author,
        String genre,
        BookCondition condition,
        Integer price,
        boolean exchangeAvailable,
        List<String> photos,
        String description
) {}

