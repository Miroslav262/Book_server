package server.books_server.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import server.books_server.entities.User;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, byte[]> avatars = new ConcurrentHashMap<>();

    private long idCounter = 1;

    public UserService() {
        createInitialUsers();
        loadInitialAvatars();
    }


    public User register(String username, String email, String password) {

        boolean exists = users.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (exists) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setId(idCounter++);
        user.setUsername(username);
        user.setEmail(email);
        user.setBio(null);
        user.setAvatar(null);

        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPasswordHash(hash);

        users.put(user.getId(), user);
        return user;
    }
    private void createInitialUsers() {

        User u1 = register("markus", "markus@example.com", "markus");
        u1.setAvatar("1.png");

        User u2 = register("anna", "anna@example.com", "anna");
        u2.setAvatar("2.png");

        User u3 = register("john", "john@example.com", "john");
        u3.setAvatar("3.png");
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public User updateUser(Long id, User updated) {
        User user = users.get(id);
        if (user == null) return null;

        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        user.setBio(updated.getBio());

        return user;
    }


    public User login(String email, String password) {

        User user = users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    public User getById(Long id) {
        return users.get(id);
    }

    public void updateAvatar(Long userId, String filename) {
        User user = users.get(userId);
        if (user == null) throw new RuntimeException("User not found");

        user.setAvatar(filename);
    }

    public void updateAvatarBytes(Long userId, byte[] data) {
        avatars.put(userId, data);

        User user = users.get(userId);
        if (user != null) {
            user.setAvatar("custom");
        }
    }


    private void loadInitialAvatars() {
        try {
            avatars.put(1L, Files.readAllBytes(Path.of("src/main/resources/static/avatars/1.png")));
            avatars.put(2L, Files.readAllBytes(Path.of("src/main/resources/static/avatars/2.png")));
            avatars.put(3L, Files.readAllBytes(Path.of("src/main/resources/static/avatars/3.png")));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public byte[] getAvatarBytes(Long userId) {
        return avatars.get(userId);
    }


}
