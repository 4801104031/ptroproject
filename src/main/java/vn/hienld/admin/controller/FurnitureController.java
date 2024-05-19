package vn.hienld.admin.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.*;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Furniture;
import vn.hienld.admin.repository.FurnitureRepository;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.FurnitureService;
import vn.hienld.admin.service.RoomService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/v1/furniture")
@Slf4j
@CrossOrigin("*")
public class FurnitureController {
    private final String START_LOG = "Furniture {}";
    private final String END_LOG = "Furniture {} finished in: {}";

    @Autowired
    FurnitureService furnitureService;
    @Autowired
    RoomService roomService;
    @Autowired
    BuildingService buildingService;

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody FurnitureDTO furnitureDTO) {
        final String action = "search furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<FurnitureDTO> data = furnitureService.findAll(furnitureDTO);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody FurnitureDTO furnitureDTO) {
        final String action = "create furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Furniture furniture = furnitureService.save(furnitureDTO);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created furniture successfully!");
        baseResponse.setData(furniture);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody FurnitureDTO furnitureDTO) {
        final String action = "delete furniture";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        furnitureService.delete(furnitureDTO);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted furniture successfully!");

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

    @GetMapping("/get-files/{furnitureId}")
    public ResponseEntity<BaseResponse> getFileByFurnitureId(@PathVariable Integer furnitureId){
        final String action = "get file by id";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);
        Map<String, String> data = furnitureService.getFile(furnitureId);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErr_code("0");
        baseResponse.setData(data);
        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete-file/{furnitureId}")
    public ResponseEntity<BaseResponse> deleteFile(@PathVariable Integer furnitureId, @RequestParam("name") String name){
        final String action = "delete file";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);
        BaseResponse baseResponse = new BaseResponse();
        furnitureService.deleteFile(furnitureId, name);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Deleted file successfully!");
        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/add-file")
    public ResponseEntity<BaseResponse> addFile(@RequestParam("name") String name ,
                                  @RequestParam("fileUpload") List<MultipartFile> fileUpload){
        final String action = "add file";
        StopWatch sw = new StopWatch();

        sw.start();
        log.info(START_LOG, action);
        BaseResponse baseResponse = new BaseResponse();
        if(fileUpload == null || fileUpload.isEmpty()){
            baseResponse.setErr_code("0");
            baseResponse.setData("");
            sw.stop();
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        List<String> nameFiles = furnitureService.addFile(name, fileUpload);
        baseResponse.setErr_code("0");
        baseResponse.setData(nameFiles);
        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
