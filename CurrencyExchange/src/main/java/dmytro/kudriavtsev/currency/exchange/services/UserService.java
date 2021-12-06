package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.dtos.UserActivationDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.MessageDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.UserDTO;
import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.kafka.KafkaTopics;
import dmytro.kudriavtsev.currency.exchange.repos.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProducerService producerService;

    public UserService(UserRepository userRepository, ProducerService producerService) {
        this.userRepository = userRepository;
        this.producerService = producerService;
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User readById(Long id) {
        return userRepository.getById(id);
    }

    public User create(UserDTO userDTO) {
        User user = new User(userDTO);

        return userRepository.save(user);
    }

    public void createActivationCode(String email) {
        User user = new User(email);
        user.setActivationCode(generateRandomCode());

        User savedUser = userRepository.save(user);

        UserActivationDTO userActivationDTO = new UserActivationDTO();
        userActivationDTO.setEmail(savedUser.getEmail());
        userActivationDTO.setActivationCode(savedUser.getActivationCode());

        MessageDTO<UserActivationDTO> messageDTO = new MessageDTO<>();
        messageDTO.setData(userActivationDTO);

        producerService.sendMessage(KafkaTopics.ACTIVATION_MAIL, messageDTO);
    }

    private String generateRandomCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public UserDTO update(UserDTO userDTO) {
        User user = readByEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());

        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User readByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with email %s not found", email)));
    }

    public boolean activateUser(UserActivationDTO userActivationDTO) {
        User user = readByEmail(userActivationDTO.getEmail());

        return user.getActivationCode().equals(userActivationDTO.getActivationCode());
    }
}
