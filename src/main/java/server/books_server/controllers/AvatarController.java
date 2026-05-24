package server.books_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.books_server.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final UserService userService;

    public AvatarController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public String uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }

        byte[] data = file.getBytes();
        userService.updateAvatarBytes(userId, data);

        return "ok";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long userId) {

        byte[] data = userService.getAvatarBytes(userId);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "image/png")
                .body(data);
    }
}

