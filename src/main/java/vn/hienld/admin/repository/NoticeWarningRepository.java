package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.NoticeWarning;

public interface NoticeWarningRepository extends JpaRepository<NoticeWarning, Integer> {
    @Query(value = "SELECT u FROM notice_warning u where 1 = 1 " +
            "and (?1 is null or lower(u.title) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or lower(u.problem) like lower('%' || ?2 || '%') ) " +
            "and (?3 is null or u.severity = ?3) " +
            "ORDER BY u.createdDate desc")
    Page<NoticeWarning> findAll(String title, String problem, Integer severity, Pageable pageable);
}
