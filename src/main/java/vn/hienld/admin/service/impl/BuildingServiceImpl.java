package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.AutoSyncRoomDTO;
import vn.hienld.admin.dto.BuildingDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.model.Building;
import vn.hienld.admin.model.Room;
import vn.hienld.admin.model.ServicePackage;
import vn.hienld.admin.repository.AccountRepository;
import vn.hienld.admin.repository.BuildingRepository;
import vn.hienld.admin.repository.RoomRepository;
import vn.hienld.admin.repository.ServicePackageRepository;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.util.DataFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Override
    public Page<BuildingDTO> findAll(BuildingDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return buildingRepository.findAll(dto.getAddressDetail(), dto.getCityId(), dto.getDistrictId(), dto.getWardId(), dto.getStatus(), pageable)
                .map(p -> objectMapper.convertValue(p, BuildingDTO.class));
    }

    @Override
    public Building save(BuildingDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    public Building add(BuildingDTO dto) {
        Building building = new Building();
        buildEntity(dto, building);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> acc = accountRepository.findByUsername(username);
        building.setAccountId(acc.get().getId());

        return buildingRepository.save(building);
    }

    public Building update(BuildingDTO dto) {
        Building building = buildingRepository.getReferenceById(dto.getId());
        buildEntity(dto, building);

        return buildingRepository.save(building);
    }

    private void buildEntity(BuildingDTO dto, Building building) {
        building.setName(dto.getName());
        building.setAddressDetail(dto.getAddressDetail());
        building.setAmountRooms(dto.getAmountRooms());
        building.setStatus(dto.getStatus());
        building.setCityId(dto.getCityId());
        building.setDistrictId(dto.getDistrictId());
        building.setWardId(dto.getWardId());
        building.setCityName(dto.getCityName());
        building.setDistrictName(dto.getDistrictName());
        building.setWardName(dto.getWardName());
        building.setFloors(dto.getFloors());
        building.setDescription(dto.getDescription());
    }

    @Override
    public void syncRoom(AutoSyncRoomDTO dto) {
        List<Room> rooms = new ArrayList<>();
        Integer amountRoomsInit = roomRepository.countByBuildingId(dto.getBuildingId());

        if(amountRoomsInit < dto.getAmountRooms()){
            for (int i = 0; i < dto.getAmountRooms() - amountRoomsInit; i++) {
                Room room = new Room();
                room.setName(dto.getBuildingName() + "-" + (dto.getAmountRooms() -(i+1)));
                room.setAmountOfPeople(dto.getAmountOfPeople());
                room.setElectricFee(dto.getElectricFee());
                room.setEnvironmentFee(dto.getEnvironmentFee());
                room.setInternetFee(dto.getInternetFee());
                room.setWaterFee(dto.getWaterFee());
                room.setStatus(dto.getStatus());
                room.setBuildingName(dto.getBuildingName());
                room.setDescription(dto.getDescription());
                room.setBuildingId(dto.getBuildingId());

                rooms.add(room);
            }
        }
        roomRepository.saveAllAndFlush(rooms);
    }

    @Override
    public List<Map<String, Object>> viewRooms(Integer buildingId, Integer floor) {
        return roomRepository.getDataToViewRooms(buildingId, floor);
    }

    @Override
    public void delete(BuildingDTO dto) {
        try{
            buildingRepository.delete(buildingRepository.getReferenceById(dto.getId()));
        }catch (Exception ex){
            log.error("Delete building exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    public Page<Map<String, Object>> findDiaChiByChaId(Select2DTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return buildingRepository.findDiaChiByChaId(dto.getCha_id(), DataFormat.lower(dto.getKey()), pageable);
    }
}
