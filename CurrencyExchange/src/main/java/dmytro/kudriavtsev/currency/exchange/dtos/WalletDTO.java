package dmytro.kudriavtsev.currency.exchange.dtos;

import dmytro.kudriavtsev.currency.exchange.entities.Wallet;

public class WalletDTO {
    private long id;
    private String currency;
    private double sum;

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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
