package dmytro.kudriavtsev.currency.exchange.repos;

import dmytro.kudriavtsev.currency.exchange.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
