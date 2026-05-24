package server.books_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.books_server.entities.Book;
import server.books_server.entities.Exchange;
import server.books_server.enums.ExchangeStatus;
import server.books_server.storage.BookStorage;
import server.books_server.storage.ExchangeStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeStorage exchangeStorage;
    private final BookStorage bookStorage;

    public Exchange createRequest(Long requesterId, Long ownerId, Long requesterBookId, Long ownerBookId) {
        Exchange exchange = new Exchange();
        exchange.setRequesterId(requesterId);
        exchange.setOwnerId(ownerId);
        exchange.setRequesterBookId(requesterBookId);
        exchange.setOwnerBookId(ownerBookId);
        exchange.setStatus(ExchangeStatus.PENDING);

        return exchangeStorage.save(exchange);
    }

    public List<Exchange> getUserExchanges(Long userId) {
        return exchangeStorage.findAll().stream()
                .filter(ex -> ex.getOwnerId().equals(userId) || ex.getRequesterId().equals(userId))
                .filter(ex -> ex.getStatus() == ExchangeStatus.PENDING)
                .toList();
    }


    public boolean acceptExchange(Long id, Long userId) {
        Optional<Exchange> opt = exchangeStorage.findById(id);
        if (opt.isEmpty()) return false;

        Exchange ex = opt.get();

        if (!ex.getOwnerId().equals(userId)) return false;
        if (ex.getStatus() != ExchangeStatus.PENDING) return false;

        Optional<Book> requesterBookOpt = bookStorage.findById(ex.getRequesterBookId());
        Optional<Book> ownerBookOpt = bookStorage.findById(ex.getOwnerBookId());

        if (requesterBookOpt.isEmpty() || ownerBookOpt.isEmpty()) return false;

        Book requesterBook = requesterBookOpt.get();
        Book ownerBook = ownerBookOpt.get();

        Long temp = requesterBook.getOwnerId();
        requesterBook.setOwnerId(ownerBook.getOwnerId());
        ownerBook.setOwnerId(temp);

        requesterBook.setExchangeAvailable(false);
        ownerBook.setExchangeAvailable(false);

        requesterBook.setInCatalog(false);
        ownerBook.setInCatalog(false);

        bookStorage.save(requesterBook);
        bookStorage.save(ownerBook);

        ex.setStatus(ExchangeStatus.ACCEPTED);
        exchangeStorage.save(ex);

        return true;
    }





    public boolean rejectExchange(Long id, Long userId) {
        Optional<Exchange> opt = exchangeStorage.findById(id);
        if (opt.isEmpty()) return false;

        Exchange ex = opt.get();
        if (!ex.getOwnerId().equals(userId) && !ex.getRequesterId().equals(userId)) {
            return false;
        }

        ex.setStatus(ExchangeStatus.REJECTED);
        exchangeStorage.save(ex);

        return true;
    }

}
