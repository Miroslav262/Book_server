package server.books_server.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ImageStorage {

    private final Map<Long, byte[]> images = new ConcurrentHashMap<>();

    public void saveImage(Long bookId, byte[] data) {
        images.put(bookId, data);
    }

    public byte[] getImage(Long bookId) {
        return images.get(bookId);
    }

    public boolean hasImage(Long bookId) {
        return images.containsKey(bookId);
    }
    @PostConstruct
    public void preloadTestImages() throws IOException {
        byte[] img1 = Files.readAllBytes(
                new ClassPathResource("static/images/photo1.png").getFile().toPath()
        );
        images.put(1L, img1);

        byte[] img2 = Files.readAllBytes(
                new ClassPathResource("static/images/photo2.png").getFile().toPath()
        );
        images.put(2L, img2);
    }
}
