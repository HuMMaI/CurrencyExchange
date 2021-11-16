package dmytro.kudriavtsev.currency.exchange.dtos;

public class KafkaExchangeDTO {
    private String email;
    private Double bought;
    private String boughtCurrency;
    private Double sold;
    private String soldCurrency;
    private boolean success;

    public KafkaExchangeDTO() {
    }

    public KafkaExchangeDTO(ExchangeDTO exchangeDTO, boolean success) {
        this.email = exchangeDTO.getEmail();
        this.boughtCurrency = exchangeDTO.getBoughtCurrency();
        this.sold = exchangeDTO.getSold();
        this.soldCurrency = exchangeDTO.getSoldCurrency();
        this.success = success;
    }

    public KafkaExchangeDTO(ExchangeDTO exchangeDTO, double boughtSum, boolean success) {
        this.email = exchangeDTO.getEmail();
        this.bought = boughtSum;
        this.boughtCurrency = exchangeDTO.getBoughtCurrency();
        this.sold = exchangeDTO.getSold();
        this.soldCurrency = exchangeDTO.getSoldCurrency();
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBought() {
        return bought;
    }

    public void setBought(Double bought) {
        this.bought = bought;
    }

    public String getBoughtCurrency() {
        return boughtCurrency;
    }

    public void setBoughtCurrency(String boughtCurrency) {
        this.boughtCurrency = boughtCurrency;
    }

    public Double getSold() {
        return sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }

    public String getSoldCurrency() {
        return soldCurrency;
    }

    public void setSoldCurrency(String soldCurrency) {
        this.soldCurrency = soldCurrency;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
