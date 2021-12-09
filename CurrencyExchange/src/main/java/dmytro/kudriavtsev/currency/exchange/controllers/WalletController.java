package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.CreateWalletDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.WalletDTO;
import dmytro.kudriavtsev.currency.exchange.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/{email}")
    public ResponseEntity<List<WalletDTO>> getWallets(@PathVariable String email) {
        List<WalletDTO> wallets = walletService.readByUser(email);

        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestBody CreateWalletDTO createWalletDTO) {
        walletService.create(createWalletDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Void> updateWallets(@RequestParam("increase") boolean increase, @PathVariable("email") String email) {
        walletService.update(email, increase);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
