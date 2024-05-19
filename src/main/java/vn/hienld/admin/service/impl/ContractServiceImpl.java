package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.AccountDTO;
import vn.hienld.admin.dto.ContractDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.model.Contract;
import vn.hienld.admin.model.PeopleInRoom;
import vn.hienld.admin.repository.ContractRepository;
import vn.hienld.admin.repository.PeopleInRoomRepository;
import vn.hienld.admin.service.ContractService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PeopleInRoomRepository peopleInRoomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<ContractDTO> findAll(ContractDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return contractRepository.findAll(dto.getRoomId(), dto.getBuildingId(), dto.getFromDateSignDate(), dto.getToDateSignDate(), dto.getFromDateDueDate(), dto.getToDateDueDate(), dto.getStatus(), pageable)
                .map(p -> objectMapper.convertValue(p, ContractDTO.class));
    }

    @Override
    @Transactional
    public Contract save(ContractDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    @Override
    public void delete(ContractDTO dto) {
        try{
            Contract contract = contractRepository.getReferenceById(dto.getId());
            peopleInRoomRepository.deleteByRoomId(contract.getRoomId());
            contractRepository.delete(contract);
        }catch (Exception ex){
            log.error("Delete contract exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    private Contract add(ContractDTO dto) {
        Contract contract = new Contract();
        initContract(dto, contract);
        return initPeopleInRoom(dto, contract);
    }

    private Contract initContract(ContractDTO dto, Contract contract) {
        contract.setRoomId(dto.getRoomId());
        contract.setStatus(dto.getStatus());
        contract.setRoomName(dto.getRoomName());
        contract.setBuildingId(dto.getBuildingId());
        contract.setBuildingName(dto.getBuildingName());
        contract.setDueDate(dto.getDueDate());
        contract.setSignDate(dto.getSignDate());
        contract.setElectricFee(dto.getElectricFee());
        contract.setEnvironmentFee(dto.getEnvironmentFee());
        contract.setInternetFee(dto.getInternetFee());
        contract.setWaterFee(dto.getWaterFee());
        contract.setRent(dto.getRent());
        contract.setParkingFee(dto.getParkingFee());
        contract.setNotes(dto.getNotes());

        return contract;
    }

    private Contract update(ContractDTO dto) {
        Contract contract = contractRepository.getReferenceById(dto.getId());
        Contract updateContract = initContract(dto, contract);

        peopleInRoomRepository.deleteByRoomId(dto.getRoomId());

        return initPeopleInRoom(dto, updateContract);
    }

    private Contract initPeopleInRoom(ContractDTO dto, Contract updateContract) {
        List<PeopleInRoom> peopleInRooms = new ArrayList<>();
        for (AccountDTO acc : dto.getUsers()){
            PeopleInRoom peopleInRoom = new PeopleInRoom();
            peopleInRoom.setRoomId(dto.getRoomId());
            peopleInRoom.setCustomerId(acc.getId());
            peopleInRoom.setCustomerName(acc.getFullName());

            peopleInRooms.add(peopleInRoom);
        }
        peopleInRoomRepository.saveAll(peopleInRooms);

        return contractRepository.save(updateContract);
    }
}
