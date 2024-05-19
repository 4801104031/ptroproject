package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.ServiceInBuildingDTO;
import vn.hienld.admin.model.ServiceInBuilding;

public interface ServiceInBuildingService {
    Page<ServiceInBuildingDTO> findAll(ServiceInBuildingDTO dto);
    void delete(ServiceInBuildingDTO dto);
    ServiceInBuilding save(ServiceInBuildingDTO dto);
}
