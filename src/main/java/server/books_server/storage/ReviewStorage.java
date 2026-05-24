package server.books_server.storage;

import org.springframework.stereotype.Component;
import server.books_server.entities.Review;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReviewStorage {

    private final Map<Long, Review> reviews = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<Review> findAll() {
        return new ArrayList<>(reviews.values());
    }

    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(reviews.get(id));
    }

    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(idGen.getAndIncrement());
        }
        reviews.put(review.getId(), review);
        return review;
    }
}
