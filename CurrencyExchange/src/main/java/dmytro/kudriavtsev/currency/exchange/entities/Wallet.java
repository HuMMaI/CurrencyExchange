package dmytro.kudriavtsev.currency.exchange.entities;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private Double sum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Wallet() {
    }

    public Wallet(String currency, Double sum, User user) {
        this.currency = currency;
        this.sum = sum;
        this.user = user;
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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
