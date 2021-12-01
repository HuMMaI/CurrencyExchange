package dmytro.kudriavtsev.currency.exchange.controllers;

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
}
