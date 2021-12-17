package dmytro.kudriavtsev.currency.exchange.repos;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findAllByPostTimeAfter(ZonedDateTime now);

    Optional<ExchangeRate> findExchangeRateByCurrencyAndPostTimeAfter(String currency, ZonedDateTime now);
}
