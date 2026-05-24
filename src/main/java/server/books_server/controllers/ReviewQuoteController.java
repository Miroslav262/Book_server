package server.books_server.controllers;

import org.springframework.web.bind.annotation.*;
import server.books_server.entities.Quote;
import server.books_server.entities.Review;
import server.books_server.records.CreateQuoteRequest;
import server.books_server.records.CreateReviewRequest;
import server.books_server.service.QuoteService;
import server.books_server.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/books/{bookId}")
public class ReviewQuoteController {

    private final ReviewService reviewService;
    private final QuoteService quoteService;

    public ReviewQuoteController(ReviewService reviewService, QuoteService quoteService) {
        this.reviewService = reviewService;
        this.quoteService = quoteService;
    }

    @GetMapping("/reviews")
    public List<Review> getReviews(@PathVariable Long bookId) {
        return reviewService.getReviews(bookId);
    }

    @PostMapping("/reviews")
    public Review addReview(@PathVariable Long bookId,
                            @RequestHeader("X-User-Id") Long userId,
                            @RequestBody CreateReviewRequest req) {
        return reviewService.addReview(bookId, userId, req);
    }

    @GetMapping("/quotes")
    public List<Quote> getQuotes(@PathVariable Long bookId) {
        return quoteService.getQuotes(bookId);
    }

    @PostMapping("/quotes")
    public Quote addQuote(@PathVariable Long bookId,
                          @RequestHeader("X-User-Id") Long userId,
                          @RequestBody CreateQuoteRequest req) {
        return quoteService.addQuote(bookId, userId, req);
    }

}

