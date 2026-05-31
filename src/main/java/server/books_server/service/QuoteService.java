package server.books_server.service;

import org.springframework.stereotype.Service;
import server.books_server.entities.Quote;
import server.books_server.records.CreateQuoteRequest;
import server.books_server.storage.QuoteStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuoteService {

    private final QuoteStorage storage;

    public QuoteService(QuoteStorage storage) {
        this.storage = storage;
    }

    public List<Quote> getQuotes(Long bookId) {
        return storage.findAll().stream()
                .filter(q -> q.getBookId().equals(bookId))
                .toList();
    }


    public Quote addQuote(Long bookId, Long userId, CreateQuoteRequest req) {
        Quote q = new Quote();
        q.setBookId(bookId);
        q.setUserId(userId);
        q.setText(req.text());
        q.setLikes(0);
        q.setDislikes(0);
        q.setCreatedAt(LocalDateTime.now());
        return storage.save(q);
    }

    public Quote likeQuote(Long bookId, Long quoteId, Long userId) {
        Quote q = storage.getById(quoteId);

        if (q.getLikedBy().contains(userId)) {
            q.getLikedBy().remove(userId);
            q.setLikes(q.getLikes() - 1);
            return storage.save(q);
        }

        if (q.getDislikedBy().contains(userId)) {
            q.getDislikedBy().remove(userId);
            q.setDislikes(q.getDislikes() - 1);
        }

        q.getLikedBy().add(userId);
        q.setLikes(q.getLikes() + 1);

        return storage.save(q);
    }

    public Quote dislikeQuote(Long bookId, Long quoteId, Long userId) {
        Quote q = storage.getById(quoteId);

        if (q.getDislikedBy().contains(userId)) {
            q.getDislikedBy().remove(userId);
            q.setDislikes(q.getDislikes() - 1);
            return storage.save(q);
        }

        if (q.getLikedBy().contains(userId)) {
            q.getLikedBy().remove(userId);
            q.setLikes(q.getLikes() - 1);
        }

        q.getDislikedBy().add(userId);
        q.setDislikes(q.getDislikes() + 1);

        return storage.save(q);
    }


}
