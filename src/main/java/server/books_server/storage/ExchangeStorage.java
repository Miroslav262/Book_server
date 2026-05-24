package server.books_server.storage;


import org.springframework.stereotype.Component;
import server.books_server.entities.Exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ExchangeStorage {

    private final Map<Long, Exchange> exchanges = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<Exchange> findAll() {
        return new ArrayList<>(exchanges.values());
    }

    public Optional<Exchange> findById(Long id) {
        return Optional.ofNullable(exchanges.get(id));
    }

    public Exchange save(Exchange exchange) {
        if (exchange.getId() == null) {
            exchange.setId(idGen.getAndIncrement());
        }
        exchanges.put(exchange.getId(), exchange);
        return exchange;
    }
}
