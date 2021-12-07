package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeEvent;
import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.exceptions.ExchangeException;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeService {
    @Autowired
    private WalletService walletService;

    @Autowired
    private ProducerService producerService;

    public void exchange(User user, ExchangeRate exchangeRate, ExchangeDTO exchangeDTO) {
        if (user.getWallet().isEmpty()) {
            throw new ExchangeException(HttpStatus.BAD_REQUEST,
                    String.format("User %s hasn't got any wallets!", user.getEmail()), exchangeDTO);
        }

        Wallet firstCurrencyWallet = findWalletByCurrency(user.getWallet(), exchangeDTO.getFirstCurrency())
                .orElseThrow(() -> new ExchangeException(HttpStatus.NOT_FOUND,
                        String.format("User %s hasn't got wallet for this currency: %s",
                                user.getEmail(), exchangeDTO.getFirstCurrency().toString()), exchangeDTO));

        Wallet secondCurrencyWallet = findWalletByCurrency(user.getWallet(), exchangeDTO.getSecondCurrency())
                .orElseThrow(() -> new ExchangeException(HttpStatus.NOT_FOUND,
                        String.format("User %s hasn't got wallet for this currency: %s",
                                user.getEmail(), exchangeDTO.getSecondCurrency().toString()), exchangeDTO));

        double sum;

        if (exchangeDTO.getEvent() == ExchangeEvent.SALE) {
            if (exchangeDTO.getFirstCurrency() == Currency.USD) {
                sum = exchangeDTO.getSum() * exchangeRate.getPurchase();
            } else {
                sum = exchangeDTO.getSum() / exchangeRate.getPurchase();
            }

            if (sum > firstCurrencyWallet.getSum()) {
                throw new ExchangeException(HttpStatus.BAD_REQUEST,
                        String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                user.getEmail(), exchangeDTO.getSum(), exchangeDTO.getFirstCurrency().toString()), exchangeDTO);
            }

            double firstSum = Math.round((firstCurrencyWallet.getSum() - exchangeDTO.getSum()) * 100.0) / 100.0;
            double secondSum = Math.round((secondCurrencyWallet.getSum() + sum) * 100.0) / 100.0;
            firstCurrencyWallet.setSum(firstSum);
            secondCurrencyWallet.setSum(secondSum);
        } else {
            if (exchangeDTO.getFirstCurrency() == Currency.USD) {
                sum = exchangeDTO.getSum() * exchangeRate.getSale();
            } else {
                sum = exchangeDTO.getSum() / exchangeRate.getSale();
            }

            if (sum > secondCurrencyWallet.getSum()) {
                throw new ExchangeException(HttpStatus.BAD_REQUEST,
                        String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                user.getEmail(), exchangeDTO.getSum(), exchangeDTO.getSecondCurrency().toString()), exchangeDTO);
            }

            double firstSum = Math.round((firstCurrencyWallet.getSum() + exchangeDTO.getSum()) * 100.0) / 100.0;
            double secondSum = Math.round((secondCurrencyWallet.getSum() - sum) * 100.0) / 100.0;
            firstCurrencyWallet.setSum(firstSum);
            secondCurrencyWallet.setSum(secondSum);
        }

        List<Wallet> wallets = Arrays.asList(firstCurrencyWallet, secondCurrencyWallet);
        walletService.updateAll(wallets);

        KafkaExchangeDTO kafkaExchangeDTO = new KafkaExchangeDTO(exchangeDTO, sum, true);
        producerService.sendMessage(KafkaTopics.REPORTS, kafkaExchangeDTO);
        producerService.sendMessage(KafkaTopics.MAIL, kafkaExchangeDTO);
    }

    private Optional<Wallet> findWalletByCurrency(List<Wallet> wallets, Currency currency) {
        return wallets.stream()
                .filter(s -> s.getCurrency().equals(currency.toString()))
                .findFirst();
    }
}
