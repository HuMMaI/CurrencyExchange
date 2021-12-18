package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rate")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/actual")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateDTO getActualExchangeRates() {
        return exchangeRateService.findActualExchangeRate(Currency.USD);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateDTO createExchangeRate(@RequestBody ExchangeRateDTO exchangeRateDTO) {
        return exchangeRateService.create(exchangeRateDTO);
    }
}
