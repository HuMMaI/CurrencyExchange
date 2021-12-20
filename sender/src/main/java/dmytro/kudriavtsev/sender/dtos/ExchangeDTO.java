package dmytro.kudriavtsev.sender.dtos;

import java.math.BigDecimal;

public class ExchangeDTO {
    private String email;
    private String event;
    private String firstCurrency;
    private BigDecimal firstSum;
    private String secondCurrency;
    private BigDecimal secondSum;
    private boolean success;

    public ExchangeDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(String firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public BigDecimal getFirstSum() {
        return firstSum;
    }

    public void setFirstSum(BigDecimal firstSum) {
        this.firstSum = firstSum;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(String secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public BigDecimal getSecondSum() {
        return secondSum;
    }

    public void setSecondSum(BigDecimal secondSum) {
        this.secondSum = secondSum;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

