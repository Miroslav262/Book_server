package server.books_server.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import server.books_server.entities.Book;
import server.books_server.enums.BookCondition;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class BookStorage {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public BookStorage() {

        Book b1 = new Book();
        b1.setTitle("Война и мир");
        b1.setAuthor("Лев Толстой");
        b1.setGenre("Роман");
        b1.setCondition(BookCondition.USED);
        b1.setPrice(75);
        b1.setExchangeAvailable(true);
        b1.setPhotos(List.of("photo1.png"));
        b1.setDescription("Эпический роман о войне и судьбах людей.");
        b1.setOwnerId(2L);
        b1.setInCatalog(true);
        save(b1);

        Book b2 = new Book();
        b2.setTitle("Преступление и наказание");
        b2.setAuthor("Фёдор Достоевский");
        b2.setGenre("Роман");
        b2.setCondition(BookCondition.NEW);
        b2.setPrice(60);
        b2.setExchangeAvailable(false);
        b2.setPhotos(List.of("photo2.jpg"));
        b2.setDescription("История Раскольникова и его внутренней борьбы.");
        b2.setOwnerId(1L);
        b2.setInCatalog(true);
        save(b2);
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idGen.getAndIncrement());
        }
        books.put(book.getId(), book);
        return book;
    }
    @PostConstruct
    public void debugBooks() {
        books.forEach((id, book) -> {
            System.out.println("BOOK ID = " + id + " → " + book.getTitle());
        });
    }

    public List<Book> findByOwner(Long ownerId) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (ownerId.equals(b.getOwnerId())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> findCatalogBooks() {
        return books.values().stream()
                .filter(Book::isInCatalog)
                .toList();
    }

    public boolean delete(Long id) {
        return books.remove(id) != null;
    }


}
