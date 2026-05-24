package server.books_server.storage;

import org.springframework.stereotype.Component;
import server.books_server.entities.Quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class QuoteStorage {

    private final Map<Long, Quote> quotes = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<Quote> findAll() {
        return new ArrayList<>(quotes.values());
    }

    public Optional<Quote> findById(Long id) {
        return Optional.ofNullable(quotes.get(id));
    }

    public Quote save(Quote quote) {
        if (quote.getId() == null) {
            quote.setId(idGen.getAndIncrement());
        }
        quotes.put(quote.getId(), quote);
        return quote;
    }
}
