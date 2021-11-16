package dmytro.kudriavtsev.currency.exchange.repos;

import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
