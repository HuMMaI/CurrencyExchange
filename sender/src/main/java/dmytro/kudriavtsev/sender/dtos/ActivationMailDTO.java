package dmytro.kudriavtsev.sender.dtos;

public class ActivationMailDTO {
    private String email;
    private String activationCode;

    public ActivationMailDTO() {
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
