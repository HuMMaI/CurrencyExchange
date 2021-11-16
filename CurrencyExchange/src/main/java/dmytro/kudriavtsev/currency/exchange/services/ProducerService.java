package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, KafkaExchangeDTO> kafkaTemplate;

    public void sendMessage(KafkaTopics topicName, KafkaExchangeDTO kafkaExchangeDTO) {
        kafkaTemplate.send(topicName.getTopicName(), kafkaExchangeDTO);
    }
}
