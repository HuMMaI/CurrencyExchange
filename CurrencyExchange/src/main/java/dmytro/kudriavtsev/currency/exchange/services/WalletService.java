package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.CreateWalletDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.WalletDTO;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.repos.UserRepository;
import dmytro.kudriavtsev.currency.exchange.repos.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet create(CreateWalletDTO createWalletDTO) {
        User user = userRepository.findByEmail(createWalletDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with email %s not found", createWalletDTO.getEmail())));

        Wallet wallet = new Wallet(createWalletDTO.getCurrency().toString(), BigDecimal.ZERO, user);

        Wallet savedWallet = walletRepository.save(wallet);

        user.getWallet().add(savedWallet);
        userRepository.save(user);

        return wallet;
    }

    public void update(String email, boolean increase) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with email %s not found", email)));

        List<Wallet> wallets = user.getWallet();

        if (increase) {
            wallets.forEach(s -> s.setSum(s.getSum().add(BigDecimal.valueOf(10000))));
        } else {
            wallets.forEach(s -> {
                if (s.getSum().compareTo(BigDecimal.valueOf(10000)) < 0) {
                    s.setSum(BigDecimal.ZERO);
                } else {
                    s.setSum(s.getSum().subtract(BigDecimal.valueOf(10000)));
                }
            });
        }

        walletRepository.saveAll(wallets);
    }

    public void updateAll(List<Wallet> wallets) {
        walletRepository.saveAll(wallets);
    }

    public List<WalletDTO> readByUser(String email) {
        List<Wallet> wallets = walletRepository.findAllByUserEmail(email);

        return wallets.stream()
                .map(WalletDTO::new)
                .collect(Collectors.toList());
    }
}
