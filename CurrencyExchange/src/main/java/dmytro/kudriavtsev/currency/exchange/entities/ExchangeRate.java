package dmytro.kudriavtsev.currency.exchange.entities;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal sale;

    private BigDecimal purchase;

    private ZonedDateTime postTime;

    public ExchangeRate() {
    }

    public ExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        this.currency = exchangeRateDTO.getCurrency();
        this.sale = exchangeRateDTO.getSale();
        this.purchase = exchangeRateDTO.getPurchase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ZonedDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(ZonedDateTime postTime) {
        this.postTime = postTime;
    }
}
