package dmytro.kudriavtsev.currency.exchange.dtos;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExchangeRateDTO {
    @NotNull
    private Currency currency;

    @NotNull
    @Min(0)
    private Double sale;

    @NotNull
    @Min(0)
    private Double purchase;

    public ExchangeRateDTO() {
    }

    public ExchangeRateDTO(ExchangeRate exchangeRate) {
        this.currency = exchangeRate.getCurrency();
        this.sale = exchangeRate.getSale();
        this.purchase = exchangeRate.getPurchase();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }
}
