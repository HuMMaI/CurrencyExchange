package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.MessageDTO;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, MessageDTO<?>> kafkaTemplate;

    public <T> void sendMessage(KafkaTopics topicName, T message) {
        MessageDTO<T> messageDTO = new MessageDTO<>();
        messageDTO.setData(message);
        kafkaTemplate.send(topicName.getTopicName(), messageDTO);
    }
}
