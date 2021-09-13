package dmytro.kudriavtsev.currency.exchange.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    @OneToMany
    @JoinTable(name = "user_wallets", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    })
    private List<Wallet> wallet;

    public User() {
    }

    public User(String name, String surname, String email, List<Wallet> wallet) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }
}
