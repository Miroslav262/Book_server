package server.books_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.books_server.entities.Book;
import server.books_server.entities.Exchange;
import server.books_server.records.CreateBookRequest;
import server.books_server.records.ProposeExchangeRequest;
import server.books_server.service.BookService;
import server.books_server.service.ExchangeService;
import server.books_server.storage.ImageStorage;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ExchangeService exchangeService;
    private final ImageStorage imageStorage;

    public BookController(BookService bookService,
                          ExchangeService exchangeService,
                          ImageStorage imageStorage) {
        this.bookService = bookService;
        this.exchangeService = exchangeService;
        this.imageStorage = imageStorage;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String query) {
        return bookService.getBooks(query);
    }

    @PostMapping
    public Book createBook(@RequestBody CreateBookRequest req,
                           @RequestHeader("X-User-Id") Long userId) {
        return bookService.createBook(req, userId);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/{id}/buy")
    public void buy(@PathVariable Long id,
                    @RequestHeader("X-User-Id") Long userId) {
        bookService.buyBook(id, userId);
    }


    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        imageStorage.saveImage(id, file.getBytes());
        return ResponseEntity.ok("Uploaded");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        if (!imageStorage.hasImage(id)) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = imageStorage.getImage(id);

        return ResponseEntity
                .ok()
                .header("Content-Type", "image/jpeg")
                .body(data);
    }
    @PostMapping("/{id}/publish")
    public ResponseEntity<?> publishBook(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        boolean ok = bookService.publishBook(id, userId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Cannot publish");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody CreateBookRequest req,
            @RequestHeader("X-User-Id") Long userId
    ) {
        boolean ok = bookService.updateBook(id, req, userId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Cannot update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        boolean ok = bookService.deleteBook(id, userId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Cannot delete");
    }





}

