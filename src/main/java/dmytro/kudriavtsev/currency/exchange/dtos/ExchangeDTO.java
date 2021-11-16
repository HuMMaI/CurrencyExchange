package dmytro.kudriavtsev.currency.exchange.dtos;

public class ExchangeDTO {
    private String email;
    private String boughtCurrency;
    private Double sold;
    private String soldCurrency;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
