package server.books_server.service;


import org.springframework.stereotype.Service;
import server.books_server.entities.Review;
import server.books_server.records.CreateReviewRequest;
import server.books_server.storage.ReviewStorage;

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
        return storage.save(r);
    }
}
