package server.books_server.entities;

import lombok.Getter;
import lombok.Setter;
import server.books_server.enums.ExchangeStatus;

@Getter
@Setter
public class Exchange {
    private Long id;
    private Long requesterId;
    private Long ownerId;
    private Long ownerBookId;
    private Long requesterBookId;
    private ExchangeStatus status = ExchangeStatus.PENDING;
}
