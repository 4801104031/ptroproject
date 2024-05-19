package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.AutoSyncRoomDTO;
import vn.hienld.admin.dto.BuildingDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.model.Building;
import vn.hienld.admin.model.ServicePackage;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    Page<BuildingDTO> findAll(BuildingDTO dto);
    Building save(BuildingDTO dto);
    void delete(BuildingDTO dto);
    Page<Map<String, Object>> findDiaChiByChaId(Select2DTO dto);
    void syncRoom(AutoSyncRoomDTO dto);
    List<Map<String, Object>> viewRooms(Integer buildingId, Integer floor);
}
