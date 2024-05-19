package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.RoomDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.dto.view.PeopleInRoomView;
import vn.hienld.admin.model.Room;

import java.util.List;

public interface RoomService {
    Page<RoomDTO> findAll(RoomDTO dto);
    Room save(RoomDTO dto);
    void delete(RoomDTO dto);
    Page<RoomDTO> findByBuildingId(RoomDTO roomDTO);
    Room getRoomById(Integer roomId);
    List<PeopleInRoomView> getPeopelInRoom(Integer roomId);
}
