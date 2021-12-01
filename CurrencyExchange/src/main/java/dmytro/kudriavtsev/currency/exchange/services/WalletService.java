package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.WalletDTO;
import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.repos.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Wallet> readAll() {
        return walletRepository.findAll();
    }

    public Wallet readById(Long id) {
        return walletRepository.getById(id);
    }

    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet update(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void delete(Long id) {
        walletRepository.deleteById(id);
    }

    public void updateAll(List<Wallet> wallets) {
        walletRepository.saveAll(wallets);
    }

    public List<WalletDTO> readByUser(String email) {
        List<Wallet> wallets = walletRepository.findAllByEmail(email);

        return wallets.stream()
                .map(WalletDTO::new)
                .collect(Collectors.toList());
    }
}
