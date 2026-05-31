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
    public ResponseEntity<?> uploadAvatar(
            @PathVariable Long userId,
            @RequestHeader("X-User-Id") Long headerId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!userId.equals(headerId)) {
            return ResponseEntity.status(403).body("Access denied");
        }

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }

        byte[] data = file.getBytes();
        userService.updateAvatarBytes(userId, data);

        return ResponseEntity.ok("ok");
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

