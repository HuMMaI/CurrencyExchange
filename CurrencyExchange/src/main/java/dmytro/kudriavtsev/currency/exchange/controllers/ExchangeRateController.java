package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rate")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public ResponseEntity<List<ExchangeRate>> getAllExchangeRates() {
        return new ResponseEntity<>(exchangeRateService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/actual")
    public ResponseEntity<List<ExchangeRate>> getActualExchangeRates() {
        return new ResponseEntity<>(exchangeRateService.findActualExchangeRates(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        exchangeRateService.create(exchangeRate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
