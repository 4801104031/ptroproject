package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.RoomDTO;
import vn.hienld.admin.dto.ServiceInBuildingDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.ServiceInBuilding;
import vn.hienld.admin.repository.BuildingRepository;
import vn.hienld.admin.repository.ServiceInBuildingRepository;
import vn.hienld.admin.service.ServiceInBuildingService;

@Service
@Slf4j
public class ServiceInBuildingServiceImpl implements ServiceInBuildingService {

    @Autowired
    ServiceInBuildingRepository serviceInBuildingRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<ServiceInBuildingDTO> findAll(ServiceInBuildingDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return serviceInBuildingRepository.findAll(dto.getName(), dto.getStatus(), pageable)
                .map(p -> objectMapper.convertValue(p, ServiceInBuildingDTO.class));
    }

    @Override
    public void delete(ServiceInBuildingDTO dto) {
        try{
            serviceInBuildingRepository.delete(serviceInBuildingRepository.getReferenceById(dto.getId()));
        }catch (Exception ex){
            log.error("Delete service exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    public ServiceInBuilding save(ServiceInBuildingDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    private ServiceInBuilding add(ServiceInBuildingDTO dto) {
        ServiceInBuilding serviceInBuilding = new ServiceInBuilding();
        return getServiceInBuilding(dto, serviceInBuilding);
    }

    private ServiceInBuilding update(ServiceInBuildingDTO dto) {
        ServiceInBuilding serviceInBuilding = serviceInBuildingRepository.getReferenceById(dto.getId());
        return getServiceInBuilding(dto, serviceInBuilding);
    }

    private ServiceInBuilding getServiceInBuilding(ServiceInBuildingDTO dto, ServiceInBuilding serviceInBuilding) {
        serviceInBuilding.setName(dto.getName());
        serviceInBuilding.setAddress(dto.getAddress());
        serviceInBuilding.setEmail(dto.getEmail());
        serviceInBuilding.setPhone(dto.getPhone());
        serviceInBuilding.setStatus(dto.getStatus());
        serviceInBuilding.setDescription(dto.getDescription());
        serviceInBuilding.setBuildingId(dto.getBuildingId());
        serviceInBuilding.setBuildingName(dto.getBuildingName());

        return serviceInBuildingRepository.save(serviceInBuilding);
    }
}
