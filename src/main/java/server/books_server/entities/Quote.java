package server.books_server.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quote {
    private Long id;
    private Long bookId;
    private Long userId;
    private String text;
    private int likes;
    private int dislikes;
}
