package dmytro.kudriavtsev.currency.exchange.dtos;

import dmytro.kudriavtsev.currency.exchange.entities.User;

import javax.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
