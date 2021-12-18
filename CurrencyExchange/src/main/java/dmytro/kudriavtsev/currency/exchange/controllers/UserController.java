package dmytro.kudriavtsev.currency.exchange.controllers;

import dmytro.kudriavtsev.currency.exchange.dtos.UserActivationDTO;
import dmytro.kudriavtsev.currency.exchange.dtos.UserDTO;
import dmytro.kudriavtsev.currency.exchange.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getByEmail(@PathVariable("email") String email) {
        return new UserDTO(userService.readByEmail(email));
    }

    @PostMapping("/activate/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean sendActivationMail(@PathVariable String email) {
        return userService.createActivationCode(email);
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateEmail(@RequestBody @Valid UserActivationDTO userActivationDTO) {
        boolean isActivate = userService.activateUser(userActivationDTO);

        if (isActivate) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
