package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Room;

import java.util.List;
import java.util.Map;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT u FROM room u where 1 = 1 " +
            "and (?1 is null or lower(u.name) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.amountOfPeople = ?2) " +
            "and (?3 is null or u.status = ?3) " +
            "ORDER BY u.createdDate desc")
    Page<Room> findAll(String name, Integer amountOfPeople, Integer status, Pageable pageable);

    Integer countByBuildingId(Integer buildingId);

    @Query(value = "SELECT u FROM room u where 1 = 1 " +
            "and (?1 is null or lower(u.name) like lower('%' || ?1 || '%') ) " +
            "and u.buildingId = ?2 " +
            "ORDER BY u.createdDate desc")
    Page<Room> findByBuildingId(String name, Integer buildingId, Pageable pageable);

    @Query(value = "select r.name as name, r.status as status from room r where r.buildingId = ?1 and r.floor = ?2")
    List<Map<String, Object>> getDataToViewRooms(Integer buildingId, Integer floor);
}
