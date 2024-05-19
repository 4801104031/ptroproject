package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Booking;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT u FROM booking u where 1 = 1 " +
            "and (?1 is null or lower(u.cusName) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.roomId = ?2) " +
            "and (?3 is null or u.status = ?3) " +
            "and (?4 is null or u.dateSet >= ?4) and (?5 is null or u.dateSet <= ?5) "  +
            "and u.isDeleted = ?6 "+
            "ORDER BY u.createdDate desc")
    Page<Booking> findAll(String fullName, Integer roomId, Integer status, LocalDateTime fromDate, LocalDateTime toDate, Integer isDeleted, Pageable pageable);

    @Modifying
    @Query(value = "update booking set isDeleted = 0 where id = ?1", nativeQuery = true)
    void delete(Integer bookingId);

    @Modifying
    @Query(value = "update booking set status = 1 where id = ?1", nativeQuery = true)
    void changeStatus(Integer bookingId);
}
