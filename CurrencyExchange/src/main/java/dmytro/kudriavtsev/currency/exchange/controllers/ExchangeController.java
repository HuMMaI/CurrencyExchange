package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
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

@RestController
@CrossOrigin("*")
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

        ExchangeRate exchangeRate = exchangeRateService.findActualExchangeRate(Currency.USD.toString());

        exchangeService.exchange(user, exchangeRate, exchangeDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
