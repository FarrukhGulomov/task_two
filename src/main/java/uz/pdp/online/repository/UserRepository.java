package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    @Query(value = "SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM users u JOIN\n" +
            "result r ON u.id = r.user_id WHERE u.id=:id",nativeQuery = true)
    boolean existsByUserInResult(Long id);
}
