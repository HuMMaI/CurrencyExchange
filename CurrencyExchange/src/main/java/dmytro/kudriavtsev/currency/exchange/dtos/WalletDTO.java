package dmytro.kudriavtsev.currency.exchange.dtos;

import dmytro.kudriavtsev.currency.exchange.entities.Wallet;

import java.math.BigDecimal;

public class WalletDTO {
    private long id;
    private String currency;
    private BigDecimal sum;

    public WalletDTO() {
    }

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.currency = wallet.getCurrency();
        this.sum = wallet.getSum();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
