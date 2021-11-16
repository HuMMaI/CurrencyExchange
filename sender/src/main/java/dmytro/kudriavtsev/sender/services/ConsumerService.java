package dmytro.kudriavtsev.sender.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.sender.dtos.ExchangeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @KafkaListener(topics = "mail", groupId = "group_id")
    public void consume(@Payload String message) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(message);
        ExchangeDTO exchangeDTO = new ObjectMapper().treeToValue(jsonNode, ExchangeDTO.class);

        if (exchangeDTO.isSuccess()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(exchangeDTO.getEmail());
            simpleMailMessage.setSubject("Exchange information");
            simpleMailMessage.setText(String.format("Your exchanged was successful!\nYou exchange %.2f %s to %.2f %s.", exchangeDTO.getSold(), exchangeDTO.getSoldCurrency(), exchangeDTO.getBought(), exchangeDTO.getBoughtCurrency()));

            mailSender.send(simpleMailMessage);
        }
    }

}
