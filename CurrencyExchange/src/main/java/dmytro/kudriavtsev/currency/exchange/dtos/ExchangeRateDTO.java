package dmytro.kudriavtsev.currency.exchange.dtos;

import dmytro.kudriavtsev.currency.exchange.entities.ExchangeRate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeRateDTO {
    @NotNull
    private Currency currency;

    @NotNull
    @Min(0)
    private BigDecimal sale;

    @NotNull
    @Min(0)
    private BigDecimal purchase;

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

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    public BigDecimal getPurchase() {
        return purchase;
    }

    public void setPurchase(BigDecimal purchase) {
        this.purchase = purchase;
    }
}
