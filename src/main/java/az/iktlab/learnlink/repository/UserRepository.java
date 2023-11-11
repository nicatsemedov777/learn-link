package az.iktlab.learnlink.repository;

import az.iktlab.learnlink.entity.User;
import az.iktlab.learnlink.repository.projection.BalanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u.balance  as balance FROM User u WHERE u.id = ?1")
    BalanceProjection findBalanceById(String id);


}
