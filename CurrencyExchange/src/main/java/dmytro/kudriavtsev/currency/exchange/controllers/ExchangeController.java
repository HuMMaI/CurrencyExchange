package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/exchange")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<Void> exchange(@RequestBody @Valid ExchangeDTO exchangeDTO) {
        exchangeService.exchange(exchangeDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
