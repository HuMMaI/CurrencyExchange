package dmytro.kudriavtsev.repotring.dtos;

public class ExchangeDTO {
    private String email;
    private String event;
    private String firstCurrency;
    private double firstSum;
    private String secondCurrency;
    private double secondSum;
    private boolean success;

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

    public double getFirstSum() {
        return firstSum;
    }

    public void setFirstSum(double firstSum) {
        this.firstSum = firstSum;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(String secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public double getSecondSum() {
        return secondSum;
    }

    public void setSecondSum(double secondSum) {
        this.secondSum = secondSum;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

