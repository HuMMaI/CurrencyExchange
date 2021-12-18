package dmytro.kudriavtsev.currency.exchange.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExchangeDTO {
    @NotBlank
    private String email;

    @NotNull
    private ExchangeEvent event;

    @NotNull
    private Currency firstCurrency;

    @NotNull
    private Currency secondCurrency;

    @NotNull
    @Min(0)
    private Double sum;

    public ExchangeDTO() {
    }

    public ExchangeDTO(String email, ExchangeEvent event, Currency firstCurrency, Currency secondCurrency, Double sum) {
        this.email = email;
        this.event = event;
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.sum = sum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ExchangeEvent getEvent() {
        return event;
    }

    public void setEvent(ExchangeEvent event) {
        this.event = event;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
