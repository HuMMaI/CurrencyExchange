package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.repos.ExchangeRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRate> readAll() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate readById(Long id) {
        return exchangeRateRepository.getById(id);
    }

    public ExchangeRate create(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    public ExchangeRate update(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    public void delete(Long id) {
        exchangeRateRepository.deleteById(id);
    }
}
