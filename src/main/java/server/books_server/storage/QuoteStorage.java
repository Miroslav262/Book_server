package server.books_server.storage;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {

        Quote q1 = new Quote();
        q1.setBookId(1L);
        q1.setUserId(10L);
        q1.setText("Иногда одно правильное решение меняет всю жизнь.");
        q1.setLikes(3);
        q1.setDislikes(0);
        this.save(q1);

        Quote q2 = new Quote();
        q2.setBookId(1L);
        q2.setUserId(11L);
        q2.setText("Очень_длинная_цитата_для_тестирования_переноса_текста");
        q2.setLikes(1);
        q2.setDislikes(0);
        this.save(q2);

        Quote q3 = new Quote();
        q3.setBookId(2L);
        q3.setUserId(12L);
        q3.setText("Книга — это машина времени, созданная человеком.");
        q3.setLikes(42);
        q3.setDislikes(2);
        this.save(q3);

        Quote q4 = new Quote();
        q4.setBookId(2L);
        q4.setUserId(15L);
        q4.setText("Мы становимся тем, что читаем.");
        q4.setLikes(5);
        q4.setDislikes(0);
        this.save(q4);

        Quote q5 = new Quote();
        q5.setBookId(1L);
        q5.setUserId(16L);
        q5.setText("Не всякая книга достойна того, чтобы быть дочитанной.");
        q5.setLikes(1);
        q5.setDislikes(7);
        this.save(q5);
    }

}
