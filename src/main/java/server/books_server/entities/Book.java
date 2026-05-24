package server.books_server.entities;

import lombok.Getter;
import lombok.Setter;
import server.books_server.enums.BookCondition;

import java.util.List;

@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private BookCondition condition;
    private Integer price;
    private boolean exchangeAvailable;
    private List<String> photos;
    private String description;
    private Long ownerId;
    private boolean inCatalog = false;

}

