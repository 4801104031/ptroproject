package vn.hienld.admin.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.BaseResponse;
import vn.hienld.admin.dto.MonthlyReportDTO;
import vn.hienld.admin.model.MonthlyReport;
import vn.hienld.admin.service.MonthlyReportService;

@RestController
@RequestMapping("/v1/bill")
@Slf4j
@CrossOrigin("*")
public class MonthlyReportController {
    private final String START_LOG = "MonthlyReport {}";
    private final String END_LOG = "MonthlyReport {} finished in: {}";

    @Autowired
    private MonthlyReportService monthlyReportService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody MonthlyReportDTO dto) {
        final String action = "search furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<MonthlyReportDTO> data = monthlyReportService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody MonthlyReportDTO dto) {
        final String action = "create furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        MonthlyReport data = monthlyReportService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created furniture successfully!");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody MonthlyReportDTO dto) {
        final String action = "delete furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        monthlyReportService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted furniture successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
