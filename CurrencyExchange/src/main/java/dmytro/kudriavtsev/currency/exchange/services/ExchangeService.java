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

                if (exchangeDTO.getSum() > firstCurrencyWallet.getSum()) {
                    throw new ExchangeException(HttpStatus.BAD_REQUEST,
                            String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                    user.getEmail(), exchangeDTO.getSum(), exchangeDTO.getFirstCurrency().toString()), exchangeDTO);
                }

                firstWalletSum = BigDecimal.valueOf(firstCurrencyWallet.getSum()).subtract(BigDecimal.valueOf(exchangeDTO.getSum()));
                secondWalletSum = BigDecimal.valueOf(secondCurrencyWallet.getSum()).add(sum);
                break;

            case PURCHASE:
                sum = sumCalculator(exchangeDTO.getFirstCurrency(), exchangeRate.getSale(), exchangeDTO.getSum());

                if (sum.doubleValue() > secondCurrencyWallet.getSum()) {
                    throw new ExchangeException(HttpStatus.BAD_REQUEST,
                            String.format("User %s hasn't got enough money for this operation. You need %.2f %s",
                                    user.getEmail(), sum, exchangeDTO.getSecondCurrency().toString()), exchangeDTO);
                }

                firstWalletSum = BigDecimal.valueOf(firstCurrencyWallet.getSum()).add(BigDecimal.valueOf(exchangeDTO.getSum()));
                secondWalletSum = BigDecimal.valueOf(secondCurrencyWallet.getSum()).subtract(sum);
                break;

            default:
                throw new ExchangeException(HttpStatus.BAD_REQUEST, "Wrong exchange event!", exchangeDTO);
        }

        firstCurrencyWallet.setSum(firstWalletSum.doubleValue());
        secondCurrencyWallet.setSum(secondWalletSum.doubleValue());

        List<Wallet> wallets = Arrays.asList(firstCurrencyWallet, secondCurrencyWallet);
        walletService.updateAll(wallets);

        sendKafkaMessages(exchangeDTO, sum.doubleValue());
    }

    private void sendKafkaMessages(ExchangeDTO exchangeDTO, double sum) {
        KafkaExchangeDTO kafkaExchangeDTO = new KafkaExchangeDTO(exchangeDTO, sum, true);
        producerService.sendMessage(KafkaTopics.REPORTS, kafkaExchangeDTO);
        producerService.sendMessage(KafkaTopics.MAIL, kafkaExchangeDTO);
    }

    private Optional<Wallet> findWalletByCurrency(List<Wallet> wallets, Currency currency) {
        return wallets.stream()
                .filter(s -> s.getCurrency().equals(currency.toString()))
                .findFirst();
    }

    private BigDecimal sumCalculator(Currency exchangeCurrency, double exchangeRate, double exchangeSum) {
        BigDecimal sum;

        BigDecimal exchangeRateDecimal = new BigDecimal(exchangeRate);
        BigDecimal exchangeSumDecimal = new BigDecimal(exchangeSum);

        if (exchangeCurrency == Currency.USD) {
            sum = exchangeSumDecimal.multiply(exchangeRateDecimal);
        } else {
            sum = exchangeSumDecimal.subtract(exchangeRateDecimal);
        }

        return sum;
    }
}
