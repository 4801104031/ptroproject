package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.AutoSyncRoomDTO;
import vn.hienld.admin.dto.BaseResponse;
import vn.hienld.admin.dto.BuildingDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.model.Building;
import vn.hienld.admin.service.BuildingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/building")
@Slf4j
@CrossOrigin("*")
public class BuildingController {
    private final String START_LOG = "Building {}";
    private final String END_LOG = "Building {} finished in: {}";

    @Autowired
    BuildingService buildingService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody BuildingDTO dto) {
        final String action = "Search building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<BuildingDTO> data = buildingService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody BuildingDTO dto) {
        final String action = "Create building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Building building = buildingService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created building successfully!");
        baseResponse.setData(building);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody BuildingDTO dto) {
        final String action = "Delete building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        buildingService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted building successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/dia-chi")
    public ResponseEntity<BaseResponse> getByChaId(@RequestBody Select2DTO dto) {
        final String action = "Load address with cha_id = " + dto.getCha_id() + ", key = " + dto.getKey();
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<Map<String, Object>> page = buildingService.findDiaChiByChaId(dto);
        baseResponse.setData(page.getContent());
        baseResponse.setTotal(page.getTotalElements());
        sw.stop();

        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/syn")
    public ResponseEntity<BaseResponse> syncRoom(@RequestBody AutoSyncRoomDTO dto) {
        final String action = "Sync Room in building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        buildingService.syncRoom(dto);
        baseResponse.setErr_code("0");
        sw.stop();

        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/view-rooms/{buildingId}/{floor}")
    public ResponseEntity<BaseResponse> syncRoom(@PathVariable Integer buildingId, @PathVariable Integer floor) {
        final String action = "View Room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        List<Map<String, Object>> data = buildingService.viewRooms(buildingId, floor);
        baseResponse.setErr_code("0");
        baseResponse.setData(data);
        sw.stop();

        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
