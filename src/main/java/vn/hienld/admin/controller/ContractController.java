package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.*;
import vn.hienld.admin.dto.view.PeopleInRoomView;
import vn.hienld.admin.model.Contract;
import vn.hienld.admin.model.Room;
import vn.hienld.admin.service.AccountService;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.ContractService;
import vn.hienld.admin.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/v1/contract")
@Slf4j
@CrossOrigin("*")
public class ContractController {
    private final String START_LOG = "Contract {}";
    private final String END_LOG = "Contract {} finished in: {}";

    @Autowired
    ContractService contractService;
    @Autowired
    RoomService roomService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    AccountService accountService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody ContractDTO dto) {
        final String action = "Search contract";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<ContractDTO> data = contractService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody ContractDTO dto) {
        final String action = "Create contract";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Contract data = contractService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created contract successfully!");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody ContractDTO dto) {
        final String action = "Delete contract";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        contractService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted contract successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/load-building")
    public ResponseEntity<BaseResponse> loadBuilding(@RequestBody Select2DTO dto) {
        final String action = "Load building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName(dto.getKey());
        buildingDTO.setPage(dto.getPage());
        buildingDTO.setSize(dto.getSize());

        Page<BuildingDTO> data = buildingService.findAll(buildingDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/load-room/{buildingId}")
    public ResponseEntity<BaseResponse> loadRoom(@PathVariable Integer buildingId, @RequestBody Select2DTO dto) {
        final String action = "Load room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(dto.getKey());
        roomDTO.setBuildingId(buildingId);
        roomDTO.setPage(dto.getPage());
        roomDTO.setSize(dto.getSize());

        Page<RoomDTO> data = roomService.findByBuildingId(roomDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/load-user")
    public ResponseEntity<BaseResponse> loadUser(@RequestBody Select2DTO dto) {
        final String action = "Load user";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setFullName(dto.getKey());
        accountDTO.setPage(dto.getPage());
        accountDTO.setSize(dto.getSize());

        Page<AccountDTO> data = accountService.getAllUsers(accountDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/load-room-info/{roomId}")
    public ResponseEntity<BaseResponse> loadRoomInfo(@PathVariable Integer roomId) {
        final String action = "Load user";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Room data = roomService.getRoomById(roomId);
        baseResponse.setErr_code("0");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/get-people-in-room/{roomId}")
    public ResponseEntity<BaseResponse> getPeopleInRoom(@PathVariable Integer roomId) {
        final String action = "Load user";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        List<PeopleInRoomView> data = roomService.getPeopelInRoom(roomId);
        baseResponse.setErr_code("0");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
