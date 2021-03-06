package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rate")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/actual")
    public ExchangeRateDTO getActualExchangeRates() {
        return exchangeRateService.findActualExchangeRate(Currency.USD);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeRateDTO createExchangeRate(@RequestBody @Valid ExchangeRateDTO exchangeRateDTO) {
        return exchangeRateService.create(exchangeRateDTO);
    }
}
