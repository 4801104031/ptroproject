package vn.hienld.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.*;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.model.Building;
import vn.hienld.admin.service.AccountService;
import vn.hienld.admin.service.BuildingService;
import vn.hienld.admin.service.impl.AccountServiceImpl;
import vn.hienld.admin.service.impl.JwtService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/user")
@CrossOrigin("*")
@Slf4j
public class UserController {

    private final String START_LOG = "Account {}";
    private final String END_LOG = "Account {} finished in: {}";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        String jwtToken = "";
        Long expTime = (long) 24*60*60; // 1day
        Authentication authentication = accountService.authenticate(authRequest);
        if (authentication.isAuthenticated()) {
            jwtToken = jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request !");
        }
        BaseResponse baseResponse = new BaseResponse();
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setExp(expTime);
        authenticateResponse.setAccess_token(jwtToken);
        baseResponse.setErr_code("0");
        baseResponse.setData(authenticateResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody AccountDTO dto) {
        final String action = "Search building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Page<AccountDTO> data = accountService.getAllUsers(dto);
        baseResponse.setErr_code("0");
        baseResponse.setData(data.getContent());
        baseResponse.setTotal(data.getTotalElements());

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<BaseResponse> save(@RequestBody AccountDTO dto) {
        final String action = "Create building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        Account account = accountService.save(dto);
        baseResponse.setErr_code("0");
        baseResponse.setMessage("Created account successfully!");
        baseResponse.setData(account);

        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());

        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody AccountDTO dto) {
        final String action = "Delete building";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);

        BaseResponse baseResponse = new BaseResponse();
        accountService.deleteUser(dto);
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

    @PostMapping("/add-file")
    public ResponseEntity<BaseResponse> addFile(@RequestParam("username") String username ,
                                                @RequestParam("fileUpload") MultipartFile fileUpload){
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
        String nameFile = accountService.uploadAvt(username, fileUpload);
        baseResponse.setErr_code("0");
        baseResponse.setData(nameFile);
        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }


    @GetMapping("/get-files/{username}")
    public ResponseEntity<BaseResponse> getFileByFurnitureId(@PathVariable String username){
        final String action = "get file by id";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);
        String data = accountService.getFile(username);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErr_code("0");
        baseResponse.setData(data);
        sw.stop();
        log.info(END_LOG, action, sw.getTotalTimeSeconds());
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
