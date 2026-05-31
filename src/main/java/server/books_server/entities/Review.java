package server.books_server.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Review {
    private Long id;
    private Long bookId;
    private Long userId;
    private String text;

    private int rating;

    private int likes;
    private int dislikes;

    private LocalDateTime createdAt;

    private Set<Long> likedBy = new HashSet<>();
    private Set<Long> dislikedBy = new HashSet<>();
}
