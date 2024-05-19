package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.*;
import vn.hienld.admin.model.NoticeWarning;
import vn.hienld.admin.service.AccountService;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.NoticeWarningService;
import vn.hienld.admin.service.RoomService;

import java.util.Map;

@RestController
@RequestMapping("/v1/notice-warning")
@Slf4j
@CrossOrigin("*")
public class NoticeWarningController {

    private final String START_LOG = "NoticeWarning {}";
    private final String END_LOG = "NoticeWarning {} finished in: {}";

    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    NoticeWarningService noticeWarningService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody NoticeWarningDTO dto) {
        final String action = "Search NoticeWarning";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<NoticeWarningDTO> data = noticeWarningService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody NoticeWarningDTO dto) {
        final String action = "Create NoticeWarning";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        NoticeWarning data = noticeWarningService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created NoticeWarning successfully!");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody NoticeWarningDTO dto) {
        final String action = "Delete NoticeWarning";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        noticeWarningService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted NoticeWarning successfully!");

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

    @PostMapping("/load-room")
    public ResponseEntity<BaseResponse> loadRoom(@RequestBody Select2DTO dto) {
        final String action = "Load room";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(dto.getKey());
        roomDTO.setPage(dto.getPage());
        roomDTO.setSize(dto.getSize());

        Page<RoomDTO> data = roomService.findAll(roomDTO);
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

        Page<Map<String, String>> data = accountService.getUsersForSelect(accountDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
