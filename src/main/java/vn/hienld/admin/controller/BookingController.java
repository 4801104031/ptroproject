package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.BaseResponse;
import vn.hienld.admin.dto.BookingDTO;
import vn.hienld.admin.model.Booking;
import vn.hienld.admin.service.BookingService;

@RestController
@RequestMapping("/v1/booking")
@Slf4j
@CrossOrigin("*")
public class BookingController {
    private final String START_LOG = "Booking {}";
    private final String END_LOG = "Booking {} finished in: {}";

    @Autowired
    private BookingService bookingService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody BookingDTO dto) {
        final String action = "Search Booking";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<BookingDTO> data = bookingService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody BookingDTO dto) {
        final String action = "Breate Booking";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Booking data = bookingService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created Booking successfully!");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody BookingDTO dto) {
        final String action = "Delete Booking";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        bookingService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted Booking successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/change-status")
    public ResponseEntity<BaseResponse> changeStatus(@RequestBody BookingDTO dto) {
        final String action = "Change status Booking";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        bookingService.changeStatus(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Change status Booking successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
