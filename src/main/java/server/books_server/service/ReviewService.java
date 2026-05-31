package server.books_server.service;


import org.springframework.stereotype.Service;
import server.books_server.entities.Review;
import server.books_server.records.CreateReviewRequest;
import server.books_server.storage.ReviewStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewStorage storage;

    public ReviewService(ReviewStorage storage) {
        this.storage = storage;
    }

    public List<Review> getReviews(Long bookId) {
        return storage.findAll().stream()
                .filter(r -> r.getBookId().equals(bookId))
                .toList();
    }

    public Review addReview(Long bookId, Long userId, CreateReviewRequest req) {
        Review r = new Review();
        r.setBookId(bookId);
        r.setUserId(userId);
        r.setText(req.text());
        r.setRating(req.rating());
        r.setLikes(0);
        r.setDislikes(0);
        r.setCreatedAt(LocalDateTime.now());

        return storage.save(r);
    }
    public Review likeReview(Long bookId, Long reviewId, Long userId) {
        Review r = storage.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!r.getBookId().equals(bookId)) {
            throw new RuntimeException("Review does not belong to this book");
        }

        if (r.getLikedBy().contains(userId)) {
            r.getLikedBy().remove(userId);
            r.setLikes(r.getLikes() - 1);
        } else {

            if (r.getDislikedBy().remove(userId)) {
                r.setDislikes(r.getDislikes() - 1);
            }

            r.getLikedBy().add(userId);
            r.setLikes(r.getLikes() + 1);
        }

        return storage.save(r);
    }

    public Review dislikeReview(Long bookId, Long reviewId, Long userId) {
        Review r = storage.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!r.getBookId().equals(bookId)) {
            throw new RuntimeException("Review does not belong to this book");
        }

        if (r.getDislikedBy().contains(userId)) {
            r.getDislikedBy().remove(userId);
            r.setDislikes(r.getDislikes() - 1);
        } else {

            if (r.getLikedBy().remove(userId)) {
                r.setLikes(r.getLikes() - 1);
            }

            r.getDislikedBy().add(userId);
            r.setDislikes(r.getDislikes() + 1);
        }

        return storage.save(r);
    }

}
