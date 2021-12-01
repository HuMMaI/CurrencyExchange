package dmytro.kudriavtsev.currency.exchange.dtos;

public class ExchangeDTO {
    private String email;
    private ExchangeEvent event;
    private Currency firstCurrency;
    private Currency secondCurrency;
    private double sum;

    public ExchangeDTO() {
    }

    public ExchangeDTO(String email, ExchangeEvent event, Currency firstCurrency, Currency secondCurrency, double sum) {
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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
