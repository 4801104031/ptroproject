package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.RoomDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.dto.view.PeopleInRoomView;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Room;
import vn.hienld.admin.repository.BuildingRepository;
import vn.hienld.admin.repository.PeopleInRoomRepository;
import vn.hienld.admin.repository.RoomRepository;
import vn.hienld.admin.service.RoomService;

import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PeopleInRoomRepository peopleInRoomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<RoomDTO> findAll(RoomDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return roomRepository.findAll(dto.getName(), dto.getAmountOfPeople(), dto.getStatus(), pageable)
                .map(p -> objectMapper.convertValue(p, RoomDTO.class));
    }

    @Override
    public Room save(RoomDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    private Room add(RoomDTO dto) {
        Room room = new Room();
        return getRoom(dto, room);
    }

    private Room getRoom(RoomDTO dto, Room room) {
        room.setName(dto.getName());
        room.setAmountOfPeople(dto.getAmountOfPeople());
        room.setElectricFee(dto.getElectricFee());
        room.setEnvironmentFee(dto.getEnvironmentFee());
        room.setInternetFee(dto.getInternetFee());
        room.setWaterFee(dto.getWaterFee());
        room.setFloor(dto.getFloor());
        room.setStatus(dto.getStatus());
        room.setBuildingName(dto.getBuildingName());
        room.setDescription(dto.getDescription());
        room.setBuildingId(dto.getBuildingId());
        room.setRent(dto.getRent());
        room.setParkingFee(dto.getParkingFee());
        room.setArea(dto.getArea());

        return roomRepository.save(room);
    }

    private Room update(RoomDTO dto) {
        Room room = roomRepository.getReferenceById(dto.getId());
        return getRoom(dto, room);
    }

    @Override
    public void delete(RoomDTO dto) {
        try{
            roomRepository.delete(roomRepository.getReferenceById(dto.getId()));
        }catch (Exception ex){
            log.error("Delete building exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    public Page<RoomDTO> findByBuildingId(RoomDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return roomRepository.findByBuildingId(dto.getName(), dto.getBuildingId(), pageable)
                .map(p -> objectMapper.convertValue(p, RoomDTO.class));
    }

    @Override
    public Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new BadRequestException("Can not find room"));
    }

    @Override
    public List<PeopleInRoomView> getPeopelInRoom(Integer roomId) {
        return peopleInRoomRepository.findPeopleInRoom(roomId);
    }
}
