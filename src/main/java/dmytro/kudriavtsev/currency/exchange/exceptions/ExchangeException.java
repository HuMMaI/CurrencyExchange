package dmytro.kudriavtsev.currency.exchange.exceptions;

import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExchangeException extends ResponseStatusException {
    private ExchangeDTO exchangeDTO;

    public ExchangeException(HttpStatus status) {
        super(status);
    }

    public ExchangeException(HttpStatus status, String reason, ExchangeDTO exchangeDTO) {
        super(status, reason);
        this.exchangeDTO = exchangeDTO;
    }

    public ExchangeException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ExchangeException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public ExchangeDTO getExchangeDTO() {
        return exchangeDTO;
    }

    public void setExchangeDTO(ExchangeDTO exchangeDTO) {
        this.exchangeDTO = exchangeDTO;
    }
}
