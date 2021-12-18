package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;
import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.repos.ExchangeRateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRateDTO create(ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate(exchangeRateDTO);
        exchangeRate.setPostTime(ZonedDateTime.now());

        return new ExchangeRateDTO(exchangeRateRepository.save(exchangeRate));
    }

    public ExchangeRateDTO findActualExchangeRate(Currency currency) {
        ZonedDateTime now = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCurrencyAndPostTimeAfter(currency, now)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Actual exchange rate does not found: %s", currency)));

        return new ExchangeRateDTO(exchangeRate);
    }
}
