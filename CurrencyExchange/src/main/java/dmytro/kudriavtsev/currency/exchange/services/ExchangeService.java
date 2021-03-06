package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.exceptions.ExchangeException;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeService {
    @Autowired
    private WalletService walletService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private UserService userService;

    public void exchange(ExchangeDTO exchangeDTO) {
        User user = userService.readByEmail(exchangeDTO.getEmail());
        ExchangeRateDTO exchangeRate = exchangeRateService.findActualExchangeRate(Currency.USD);

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

        BigDecimal sum;
        BigDecimal firstWalletSum;
        BigDecimal secondWalletSum;

        switch(exchangeDTO.getEvent()) {
            case SALE:
                sum = sumCalculator(exchangeDTO.getFirstCurrency(), exchangeRate.getPurchase(), exchangeDTO.getSum());

                if (exchangeDTO.getSum().compareTo(firstCurrencyWallet.getSum()) > 0) {
                    throw new ExchangeException(HttpStatus.BAD_REQUEST,
                            String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                    user.getEmail(), exchangeDTO.getSum(), exchangeDTO.getFirstCurrency().toString()), exchangeDTO);
                }

                firstWalletSum = firstCurrencyWallet.getSum().subtract(exchangeDTO.getSum());
                secondWalletSum = secondCurrencyWallet.getSum().add(sum);
                break;

            case PURCHASE:
                sum = sumCalculator(exchangeDTO.getFirstCurrency(), exchangeRate.getSale(), exchangeDTO.getSum());

                if (sum.compareTo(secondCurrencyWallet.getSum()) > 0) {
                    throw new ExchangeException(HttpStatus.BAD_REQUEST,
                            String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                    user.getEmail(), sum, exchangeDTO.getSecondCurrency().toString()), exchangeDTO);
                }

                firstWalletSum = firstCurrencyWallet.getSum().add(exchangeDTO.getSum());
                secondWalletSum = secondCurrencyWallet.getSum().subtract(sum);
                break;

            default:
                throw new ExchangeException(HttpStatus.BAD_REQUEST, "Wrong exchange event!", exchangeDTO);
        }

        firstCurrencyWallet.setSum(firstWalletSum);
        secondCurrencyWallet.setSum(secondWalletSum);

        List<Wallet> wallets = Arrays.asList(firstCurrencyWallet, secondCurrencyWallet);
        walletService.updateAll(wallets);

        sendKafkaMessages(exchangeDTO, sum);
    }

    private void sendKafkaMessages(ExchangeDTO exchangeDTO, BigDecimal sum) {
        KafkaExchangeDTO kafkaExchangeDTO = new KafkaExchangeDTO(exchangeDTO, sum, true);
        producerService.sendMessage(KafkaTopics.REPORTS, kafkaExchangeDTO);
        producerService.sendMessage(KafkaTopics.MAIL, kafkaExchangeDTO);
    }

    private Optional<Wallet> findWalletByCurrency(List<Wallet> wallets, Currency currency) {
        return wallets.stream()
                .filter(s -> s.getCurrency().equals(currency.toString()))
                .findFirst();
    }

    private BigDecimal sumCalculator(Currency exchangeCurrency, BigDecimal exchangeRate, BigDecimal exchangeSum) {
        BigDecimal sum;

        if (exchangeCurrency == Currency.USD) {
            sum = exchangeSum.multiply(exchangeRate);
        } else {
            sum = exchangeSum.divide(exchangeRate, 2, RoundingMode.HALF_UP);
        }

        return sum;
    }
}
