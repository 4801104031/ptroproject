package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Contract;

import java.time.LocalDateTime;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    @Query(value = "SELECT u FROM contract u where 1 = 1 " +
            "and (?1 is null or u.roomId = ?1 ) " +
            "and (?2 is null or u.buildingId = ?2 ) " +
            "and (?3 is null or u.signDate >= ?3) and (?4 is null or u.signDate <= ?4)"  +
            "and (?5 is null or u.dueDate >= ?5) and (?6 is null or u.dueDate <= ?6)"  +
            "and (?7 is null or u.status = ?7) " +
            "ORDER BY u.createdDate desc")
    Page<Contract> findAll(Integer roomId, Integer buildingId, LocalDateTime fromDateSignDate, LocalDateTime toDateSignDate, LocalDateTime fromDateDueDate, LocalDateTime toDateDueDate, Integer status, Pageable pageable);

    Contract findByRoomId(Integer roomId);
}
