package dmytro.kudriavtsev.sender.dtos;

public class ExchangeDTO {
    private String email;
    private Double bought;
    private String boughtCurrency;
    private Double sold;
    private String soldCurrency;
    private boolean success;

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

