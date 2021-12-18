package dmytro.kudriavtsev.currency.exchange.handlers;

import dmytro.kudriavtsev.currency.exchange.dtos.ErrorResponseDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.KafkaExchangeDTO;
import dmytro.kudriavtsev.currency.exchange.exceptions.ExchangeException;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import dmytro.kudriavtsev.currency.exchange.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class UserExceptionHandler {

    @Autowired
    private ProducerService producerService;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException (ResponseStatusException responseStatusException, ServletWebRequest servletWebRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                responseStatusException.getStatus().getReasonPhrase(),
                responseStatusException.getStatus().value(),
                responseStatusException.getReason(),
                servletWebRequest.getRequest().getRequestURI()
        );

        return new ResponseEntity<>(errorResponseDTO, responseStatusException.getStatus());
    }

    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException (ExchangeException exchangeException, ServletWebRequest servletWebRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                exchangeException.getStatus().getReasonPhrase(),
                exchangeException.getStatus().value(),
                exchangeException.getReason(),
                servletWebRequest.getRequest().getRequestURI()
        );

        KafkaExchangeDTO kafkaExchangeDTO = new KafkaExchangeDTO(exchangeException.getExchangeDTO(), false);
        producerService.sendMessage(KafkaTopics.REPORTS, kafkaExchangeDTO);

        return new ResponseEntity<>(errorResponseDTO, exchangeException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, ServletWebRequest servletWebRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                "Validation error",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                methodArgumentNotValidException.getMessage(),
                servletWebRequest.getRequest().getRequestURI()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
