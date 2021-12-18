package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.CreateWalletDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.WalletDTO;
import dmytro.kudriavtsev.currency.exchange.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<WalletDTO> getWallets(@PathVariable String email) {
        return walletService.readByUser(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createWallet(@RequestBody @Valid CreateWalletDTO createWalletDTO) {
        walletService.create(createWalletDTO);
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void updateWallets(@RequestParam("increase") boolean increase, @PathVariable("email") String email) {
        walletService.update(email, increase);
    }
}
