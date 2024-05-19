package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import vn.hienld.admin.dto.BaseResponse;
import vn.hienld.admin.dto.BuildingDTO;
import vn.hienld.admin.dto.ServiceInBuildingDTO;
import vn.hienld.admin.dto.ServicePackageIn;
import vn.hienld.admin.model.ServiceInBuilding;
import vn.hienld.admin.model.ServicePackage;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.FurnitureService;
import vn.hienld.admin.service.ServiceInBuildingService;
import vn.hienld.admin.service.ServicePackageService;

import java.util.List;

@RestController
@RequestMapping("/v1/services-in-building")
@CrossOrigin("*")
@Slf4j
public class ServicesInBuildingController {
    private final String START_LOG = "ServiceInBuilding {}";
    private final String END_LOG = "ServiceInBuilding {} finished in: {}";

    @Autowired
    ServiceInBuildingService serviceInBuildingService;
    @Autowired
    ServicePackageService servicePackageService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    FurnitureService furnitureService;


    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody ServiceInBuildingDTO dto) {
        final String action = "Search ServiceInBuilding";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<ServiceInBuildingDTO> data = serviceInBuildingService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody ServiceInBuildingDTO dto) {
        final String action = "Create ServiceInBuilding";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        ServiceInBuilding data = serviceInBuildingService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created ServiceInBuilding successfully!");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody ServiceInBuildingDTO dto) {
        final String action = "Delete ServiceInBuilding";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        serviceInBuildingService.delete(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted ServiceInBuilding successfully!");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/load-building")
    public ResponseEntity<BaseResponse> loadBuilding(@RequestBody BuildingDTO dto) {
        final String action = "Load building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<BuildingDTO> data = buildingService.findAll(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/list-packages/{serviceId}")
    public ResponseEntity<BaseResponse> listPackage(@PathVariable("serviceId") Integer serviceId) {
        final String action = "Load packages";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        List<ServicePackage> data = servicePackageService.findPackageByServiceId(serviceId);
        baseResponse.setErr_code("0");
        baseResponse.setData(data);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/edit-packages")
    public ResponseEntity<BaseResponse> editPackage(@RequestBody ServicePackageIn dto) {
        final String action = "Load building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        servicePackageService.save(dto.getData(), dto.getServiceId());
        baseResponse.setErr_code("0");
        baseResponse.setData("Thay đổi thông tin các gói cước thành công.");

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
