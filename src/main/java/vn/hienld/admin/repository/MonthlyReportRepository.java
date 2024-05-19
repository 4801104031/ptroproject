package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.dto.MonthlyReportDTO;
import vn.hienld.admin.model.MonthlyReport;

import java.time.LocalDateTime;

public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Integer> {

    @Query(value = "SELECT new vn.hienld.admin.dto.MonthlyReportDTO(u.id, u.month, u.year, u.roomId, u.roomName, u.buildingId, u.buildingName, u.amountOfElectricity, u.amountOfWater, u.status, u.amountOfElectricity*c.electricFee, u.amountOfWater*c.waterFee, c.internetFee, c.environmentFee, c.parkingFee)  " +
            "FROM monthly_report u inner join contract c on u.roomId = c.roomId where 1 = 1 " +
            "and (?1 is null or u.roomId = ?1 ) " +
            "and (?2 is null or u.payDate >= ?2) and (?3 is null or u.payDate <= ?3) "  +
            "and (?4 is null or u.status = ?4) and u.isDeleted = ?5 " +
            "and (?6 is null or u.buildingId = ?6 ) " +
            "ORDER BY u.createdDate desc")
    Page<MonthlyReportDTO> findAll(Integer roomId, LocalDateTime fromDate, LocalDateTime toDate, Integer status, Integer isDeleted, Integer buildingId, Pageable pageable);

    @Modifying
    @Query(value = "update monthly_report set isDeleted = 0 where id = ?1", nativeQuery = true)
    void delete(Integer id);
}
