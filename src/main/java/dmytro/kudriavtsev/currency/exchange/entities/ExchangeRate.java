package dmytro.kudriavtsev.currency.exchange.entities;

import javax.persistence.*;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private Double sale;

    private Double purchase;

    public ExchangeRate() {
    }

    public ExchangeRate(String currency, Double sale, Double purchase) {
        this.currency = currency;
        this.sale = sale;
        this.purchase = purchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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
