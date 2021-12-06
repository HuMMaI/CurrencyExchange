package dmytro.kudriavtsev.currency.exchange.dtos;

public class UserActivationDTO {
    private String email;
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
