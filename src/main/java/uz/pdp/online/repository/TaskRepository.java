package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
    Optional<Task> findTaskByLanguageId(Long language_id);

    boolean existsTaskByCodeAndIdNot(String code, Long id);

    @Query(value = "SELECT CASE WHEN COUNT(t)>0 THEN TRUE ELSE FALSE END FROM task t\n" +
            "JOIN result r ON t.id=r.task_id WHERE t.id=:id",nativeQuery = true)
    boolean existsTaskInResult(Long id);

}
