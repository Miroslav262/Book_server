package server.books_server.service;

import org.springframework.stereotype.Service;
import server.books_server.entities.Book;
import server.books_server.records.CreateBookRequest;
import server.books_server.storage.BookStorage;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookStorage bookStorage;

    public BookService(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    public List<Book> getBooks(String query) {
        return bookStorage.findAll().stream()
                .filter(b -> query == null ||
                        b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public Book getBook(Long id) {
        return bookStorage.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(CreateBookRequest req, Long ownerId) {
        Book b = new Book();
        b.setTitle(req.title());
        b.setAuthor(req.author());
        b.setGenre(req.genre());
        b.setCondition(req.condition());
        b.setPrice(req.price());
        b.setExchangeAvailable(req.exchangeAvailable());
        b.setPhotos(req.photos());
        b.setDescription(req.description());
        b.setOwnerId(ownerId);
        return bookStorage.save(b);
    }

    public void buyBook(Long id, Long userId) {
        Optional<Book> opt = bookStorage.findById(id);
        if (opt.isEmpty()) return;
        Book book = opt.get();
        if (book.getOwnerId().equals(userId)) return;
        book.setOwnerId(userId);
        book.setInCatalog(false);
        bookStorage.save(book);
    }

    public boolean publishBook(Long id, Long userId) {
        Optional<Book> opt = bookStorage.findById(id);
        if (opt.isEmpty()) return false;

        Book book = opt.get();

        if (!book.getOwnerId().equals(userId)) {
            return false;
        }

        book.setInCatalog(true);
        book.setExchangeAvailable(true);

        bookStorage.save(book);
        return true;
    }
    public boolean updateBook(Long id, CreateBookRequest req, Long userId) {
        Optional<Book> opt = bookStorage.findById(id);
        if (opt.isEmpty()) return false;

        Book book = opt.get();

        if (!book.getOwnerId().equals(userId)) return false;

        book.setTitle(req.title());
        book.setAuthor(req.author());
        book.setGenre(req.genre());
        book.setCondition(req.condition());
        book.setDescription(req.description());
        book.setPrice(req.price());

        bookStorage.save(book);
        return true;
    }

    public boolean deleteBook(Long id, Long userId) {
        Optional<Book> opt = bookStorage.findById(id);
        if (opt.isEmpty()) return false;

        Book book = opt.get();

        if (!book.getOwnerId().equals(userId)) return false;

        bookStorage.delete(id);
        return true;
    }




}
