package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Result;

public interface ResultRepository extends JpaRepository<Result,Long> {
    boolean existsByResponseResult(String responseResult);

    boolean existsByResponseResultAndIdNot(String responseResult, Long id);
}
