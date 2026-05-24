package server.books_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.books_server.entities.Exchange;
import server.books_server.service.ExchangeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/request")
    public ResponseEntity<?> createRequest(@RequestBody Map<String, Long> body) {
        Long requesterId = body.get("fromUserId");
        Long ownerId = body.get("toUserId");
        Long requesterBookId = body.get("offeredBookId");
        Long ownerBookId = body.get("requestedBookId");

        Exchange ex = exchangeService.createRequest(
                requesterId,
                ownerId,
                requesterBookId,
                ownerBookId
        );

        return ResponseEntity.ok(ex);
    }

    @GetMapping("/{userId}")
    public List<Exchange> getUserExchanges(@PathVariable Long userId) {
        return exchangeService.getUserExchanges(userId);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> accept(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        boolean ok = exchangeService.acceptExchange(id, userId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Cannot accept");
    }


    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectExchange(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        boolean ok = exchangeService.rejectExchange(id, userId);
        return ok ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body("Cannot reject");
    }


}
