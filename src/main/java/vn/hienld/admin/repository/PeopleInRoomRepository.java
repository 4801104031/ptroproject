package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.dto.view.PeopleInRoomView;
import vn.hienld.admin.model.PeopleInRoom;

import java.util.List;

public interface PeopleInRoomRepository extends JpaRepository<PeopleInRoom, Integer> {
    @Modifying
    @Query(value = "delete from people_in_room p where p.roomId = ?1", nativeQuery = true)
    void deleteByRoomId(int roomId);

    @Query(value =
            "select new vn.hienld.admin.dto.view.PeopleInRoomView(u.customerId,  u.customerName) " +
                    "from people_in_room u where 1 = 1 " +
                    "and (:roomId is null or u.roomId = :roomId) ")
    List<PeopleInRoomView> findPeopleInRoom(Integer roomId);
}
