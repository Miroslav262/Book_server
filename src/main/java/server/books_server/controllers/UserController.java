package server.books_server.controllers;

import org.springframework.web.bind.annotation.*;
import server.books_server.entities.Book;
import server.books_server.entities.User;
import server.books_server.records.UserResponse;
import server.books_server.service.UserService;
import server.books_server.storage.BookStorage;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BookStorage bookStorage;

    public UserController(UserService userService, BookStorage bookStorage) {
        this.userService = userService;
        this.bookStorage = bookStorage;
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User u = userService.getUserById(id);

        return new UserResponse(
                u.getId(),
                u.getUsername(),
                "http://localhost:3000/avatars/" + u.getId()
        );
    }


    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updated
    ) {
        return userService.updateUser(id, updated);
    }
    @GetMapping("/{id}/books")
    public List<Book> getUserBooks(@PathVariable Long id) {
        return bookStorage.findByOwner(id);
    }
}
