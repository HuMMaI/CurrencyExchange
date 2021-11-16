package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.repos.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
