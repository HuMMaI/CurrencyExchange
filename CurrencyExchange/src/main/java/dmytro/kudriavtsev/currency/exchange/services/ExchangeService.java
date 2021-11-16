package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.entities.Wallet;
import dmytro.kudriavtsev.currency.exchange.exceptions.ExchangeException;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import dmytro.kudriavtsev.currency.exchange.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExchangeService {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProducerService producerService;

    public void exchange(User user, ExchangeRate exchangeRate, ExchangeDTO exchangeDTO) {
        Wallet walletWithSoldCurrency = user.getWallet().stream()
                .filter(s -> s.getCurrency().equals(exchangeDTO.getSoldCurrency()))
                .findFirst()
                .orElseThrow(() -> new ExchangeException(HttpStatus.NOT_FOUND,
                        String.format("User %s has not got wallet for this currency: %s",
                                user.getEmail(), exchangeDTO.getSoldCurrency()), exchangeDTO));

        if (exchangeDTO.getSold() > walletWithSoldCurrency.getSum()) {
            throw new ExchangeException(HttpStatus.BAD_REQUEST,
                    String.format("User %s does not have enough money: %.2f",
                            user.getEmail(), walletWithSoldCurrency.getSum()), exchangeDTO);
        }

        Wallet walletWithBoughtCurrency = user.getWallet().stream()
                .filter(s -> s.getCurrency().equals(exchangeDTO.getBoughtCurrency()))
                .findFirst()
                .orElseGet(() -> {
                    Wallet wallet = new Wallet();
                    wallet.setUser(user);
                    wallet.setCurrency(exchangeDTO.getBoughtCurrency());
                    wallet.setSum(0.0);

                    Wallet createdWallet = walletService.create(wallet);
                    user.getWallet().add(createdWallet);
                    userRepository.save(user);

                    return createdWallet;
                });

        double boughtSum = exchangeDTO.getSold() * exchangeRate.getSale();

        walletWithBoughtCurrency.setSum(walletWithBoughtCurrency.getSum() + boughtSum);
        walletWithSoldCurrency.setSum(walletWithSoldCurrency.getSum() - exchangeDTO.getSold());

        List<Wallet> wallets = Arrays.asList(walletWithSoldCurrency, walletWithBoughtCurrency);

        walletService.updateAll(wallets);

        KafkaExchangeDTO kafkaExchangeDTO = new KafkaExchangeDTO(exchangeDTO, boughtSum, true);

        producerService.sendMessage(KafkaTopics.REPORTS, kafkaExchangeDTO);
        producerService.sendMessage(KafkaTopics.MAIL, kafkaExchangeDTO);
    }

}
