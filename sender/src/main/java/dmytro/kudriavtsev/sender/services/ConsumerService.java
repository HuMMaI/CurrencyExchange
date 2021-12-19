package dmytro.kudriavtsev.sender.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.kudriavtsev.sender.dtos.ActivationMailDTO;
import dmytro.kudriavtsev.sender.dtos.ExchangeDTO;
import dmytro.kudriavtsev.sender.dtos.MessageDTO;
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
    public void consumeMail(@Payload String message) throws JsonProcessingException {
        MessageDTO<ExchangeDTO> messageDTO = new ObjectMapper().readValue(message, new TypeReference<>() {});
        ExchangeDTO exchangeDTO = messageDTO.getData();

        sendMail(exchangeDTO.getEmail(), "Exchange information",
                String.format("Your exchanged was successful!\nYou exchange %.2f %s to %.2f %s.",
                        exchangeDTO.getFirstSum(),
                        exchangeDTO.getFirstCurrency(),
                        exchangeDTO.getSecondSum(),
                        exchangeDTO.getSecondCurrency()));
    }

    @KafkaListener(topics = "activation", groupId = "group_id")
    public void consumeActivationMail(@Payload String message) throws JsonProcessingException {
        MessageDTO<ActivationMailDTO> messageDTO = new ObjectMapper().readValue(message, new TypeReference<>() {});

        System.out.println("[Activation code: " + messageDTO.getData().getActivationCode() + "]");

        sendMail(messageDTO.getData().getEmail(), "Activation",
                String.format("You need to activate your email to using exchange service.\nActivation code: %s",
                        messageDTO.getData().getActivationCode()));
    }

    private void sendMail(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        mailSender.send(simpleMailMessage);
    }
}
