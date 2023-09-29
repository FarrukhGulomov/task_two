package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.Language;

public interface LanguageRepository extends JpaRepository<Language,Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name,Long id);

    @Query(value = "SELECT CASE WHEN COUNT(l)>0 THEN TRUE ELSE FALSE END FROM language l JOIN task t\n" +
            "ON l.id=t.language_id WHERE l.id=:id",nativeQuery = true)
    boolean existLanguageInTask(Long id);
}
