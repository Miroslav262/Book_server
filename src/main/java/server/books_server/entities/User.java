package server.books_server.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private String bio;
    private String avatar;
}
