package dmytro.kudriavtsev.currency.exchange.dtos;

public class KafkaExchangeDTO {
    private String email;
    private ExchangeEvent event;
    private Currency firstCurrency;
    private double firstSum;
    private Currency secondCurrency;
    private double secondSum;
    private boolean success;

    public KafkaExchangeDTO() {
    }

    public KafkaExchangeDTO(ExchangeDTO exchangeDTO, boolean success) {
        this.email = exchangeDTO.getEmail();
        this.event = exchangeDTO.getEvent();
        this.firstCurrency = exchangeDTO.getFirstCurrency();
        this.firstSum = exchangeDTO.getSum();
        this.secondCurrency = exchangeDTO.getSecondCurrency();
        this.success = success;
    }

    public KafkaExchangeDTO(ExchangeDTO exchangeDTO, double secondSum, boolean success) {
        this.email = exchangeDTO.getEmail();
        this.event = exchangeDTO.getEvent();
        this.firstCurrency = exchangeDTO.getFirstCurrency();
        this.firstSum = exchangeDTO.getSum();
        this.secondCurrency = exchangeDTO.getSecondCurrency();
        this.secondSum = secondSum;
        this.success = success;
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

    public double getFirstSum() {
        return firstSum;
    }

    public void setFirstSum(double firstSum) {
        this.firstSum = firstSum;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(Currency secondCurrency) {
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
