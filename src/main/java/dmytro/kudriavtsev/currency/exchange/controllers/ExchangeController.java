package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeRateService;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeService;
import dmytro.kudriavtsev.currency.exchange.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<Void> exchange(@RequestBody ExchangeDTO exchangeDTO) {
        User user = userService.readByEmail(exchangeDTO.getEmail());

        ExchangeRate exchangeRate = exchangeRateService.findActualExchangeRates().stream()
                .filter(s -> s.getCurrency().equals(exchangeDTO.getBoughtCurrency())).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Actual exchange rate does not found: %s", exchangeDTO.getBoughtCurrency())));

        exchangeService.exchange(user, exchangeRate, exchangeDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
