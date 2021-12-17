package dmytro.kudriavtsev.currency.exchange.entities;

import dmytro.kudriavtsev.currency.exchange.dtos.Currency;
import dmytro.kudriavtsev.currency.exchange.dtos.ExchangeRateDTO;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Currency currency;

    private Double sale;

    private Double purchase;

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

    public ZonedDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(ZonedDateTime postTime) {
        this.postTime = postTime;
    }
}
