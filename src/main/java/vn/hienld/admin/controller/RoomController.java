package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.*;
import vn.hienld.admin.model.Room;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.FurnitureService;
import vn.hienld.admin.service.RoomService;

@RestController
@RequestMapping("/v1/room")
@Slf4j
@CrossOrigin("*")
public class RoomController {
    private final String START_LOG = "Room {}";
    private final String END_LOG = "Room {} finished in: {}";

    @Autowired
    RoomService roomService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    FurnitureService furnitureService;


    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody RoomDTO dto) {
        final String action = "Search room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<RoomDTO> data = roomService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody RoomDTO dto) {
        final String action = "Create room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Room room = roomService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created room successfully!");
        baseResponse.setData(room);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody RoomDTO dto) {
        final String action = "Delete room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        roomService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted room successfully!");

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

    @PostMapping("/load-furniture")
    public ResponseEntity<BaseResponse> search(@RequestBody FurnitureDTO furnitureDTO) {
        final String action = "Load furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<FurnitureDTO> data = furnitureService.findAll(furnitureDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
