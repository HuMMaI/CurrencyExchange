package dmytro.kudriavtsev.currency.exchange.dtos;

import javax.validation.constraints.NotNull;

public class UserActivationDTO {
    @NotNull
    private String email;

    @NotNull
    private String activationCode;

    public UserActivationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
