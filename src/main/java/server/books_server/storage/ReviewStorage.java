package server.books_server.storage;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {

        Review r1 = new Review();
        r1.setBookId(1L);
        r1.setUserId(21L);
        r1.setText("Книга читается легко, сюжет держит внимание до самого конца. Особенно понравилась атмосфера.");
        r1.setLikes(5);
        r1.setDislikes(0);
        this.save(r1);

        Review r2 = new Review();
        r2.setBookId(1L);
        r2.setUserId(22L);
        r2.setText("Хорошая работа автора. Есть моменты, которые хотелось бы раскрыть глубже, но в целом впечатление положительное.");
        r2.setLikes(2);
        r2.setDislikes(1);
        this.save(r2);

        Review r3 = new Review();
        r3.setBookId(2L);
        r3.setUserId(23L);
        r3.setText("Сильная книга. Заставляет задуматься о вещах, которые обычно не замечаешь в повседневной жизни.");
        r3.setLikes(12);
        r3.setDislikes(0);
        this.save(r3);

        Review r4 = new Review();
        r4.setBookId(2L);
        r4.setUserId(24L);
        r4.setText("Местами тяжёлая, но очень глубокая. Определённо стоит прочитать хотя бы раз.");
        r4.setLikes(7);
        r4.setDislikes(1);
        this.save(r4);

        Review r5 = new Review();
        r5.setBookId(2L);
        r5.setUserId(25L);
        r5.setText("Приятный стиль, интересные персонажи. Но концовка показалась немного поспешной.");
        r5.setLikes(3);
        r5.setDislikes(2);
        this.save(r5);

        Review r6 = new Review();
        r6.setBookId(1L);
        r6.setUserId(26L);
        r6.setText("Хорошая книга для спокойного вечера. Не шедевр, но оставляет тёплое ощущение.");
        r6.setLikes(4);
        r6.setDislikes(0);
        this.save(r6);

        Review r7 = new Review();
        r7.setBookId(1L);
        r7.setUserId(27L);
        r7.setText("Очень мотивирующая книга. После прочтения хочется действовать и менять свою жизнь.");
        r7.setLikes(15);
        r7.setDislikes(1);
        this.save(r7);

        Review r8 = new Review();
        r8.setBookId(2L);
        r8.setUserId(28L);
        r8.setText("Хорошая подача материала, много практических мыслей. Рекомендую.");
        r8.setLikes(9);
        r8.setDislikes(0);
        this.save(r8);
    }

}
