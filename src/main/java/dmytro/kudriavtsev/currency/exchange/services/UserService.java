package dmytro.kudriavtsev.currency.exchange.services;

import dmytro.kudriavtsev.currency.exchange.entities.User;
import dmytro.kudriavtsev.currency.exchange.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User readById(Long id) {
        return userRepository.getById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
