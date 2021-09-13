package dmytro.kudriavtsev.currency.exchange.repos;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
