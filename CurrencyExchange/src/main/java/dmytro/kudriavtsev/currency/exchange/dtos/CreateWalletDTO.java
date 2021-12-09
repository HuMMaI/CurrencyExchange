package dmytro.kudriavtsev.currency.exchange.dtos;

import javax.validation.constraints.NotNull;

public class CreateWalletDTO {
    @NotNull
    private String email;

    @NotNull
    private Currency currency;

    public CreateWalletDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
