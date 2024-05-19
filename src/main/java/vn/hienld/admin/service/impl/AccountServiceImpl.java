package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.AccountDTO;
import vn.hienld.admin.dto.AuthRequest;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.repository.AccountRepository;
import vn.hienld.admin.service.AccountService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Account save(AccountDTO accountDTO) {
        if (accountDTO.getId() == null) {
            return addUser(accountDTO);
        }
        return editUser(accountDTO);
    }

    public Account addUser(AccountDTO dto) {
        Account account = new Account();
        Optional<Account> acc = repository.findByUsername(dto.getUsername());
        if (acc.isPresent()) {
            throw new BadRequestException("Tài khoản đã tồn tại!");
        }
        account.setUsername(dto.getUsername());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        return getAccount(dto, account);
    }

    private Account getAccount(AccountDTO dto, Account account) {
        account.setRole(dto.getRole());
        account.setAvatar(dto.getAvatar() != null ? dto.getAvatar() : account.getAvatar());
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setDob(dto.getDob());
        account.setGender(dto.getGender());
        account.setCitizenIdentificationNumber(dto.getCitizenIdentificationNumber());
        account.setIssuedBy(dto.getIssuedBy());
        account.setFullName(dto.getFullName());
        account.setIssuedOn(dto.getIssuedOn());
        account.setNumberFailures(0);
        account.setPermanentAddress(dto.getPermanentAddress());
        account.setStatus(dto.getStatus());
        account.setCityId(dto.getCityId());
        account.setWardId(dto.getWardId());
        account.setDistrictId(dto.getDistrictId());
        account.setCityName(dto.getCityName());
        account.setWardName(dto.getWardName());
        account.setDistrictName(dto.getDistrictName());
        account.setDescription(dto.getDescription());
        account.setMoveInDate(dto.getMoveInDate());

        return repository.save(account);
    }

    public Account editUser(AccountDTO dto) {
        Account account = repository.findByUsername(dto.getUsername()).get();
        if (dto.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if(dto.getAvatar() != null) {
            try {
                ClassPathResource resource = new ClassPathResource(".");
                String absolutePath = resource.getFile().getAbsolutePath();
                Path imagesPath = Paths.get(absolutePath, "images");
                File file = new File(imagesPath + "/" + account.getAvatar());
                file.delete();
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        return getAccount(dto, account);
    }

    @Override
    public Page<AccountDTO> getAllUsers(AccountDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return repository.findAll(dto.getFullName(), dto.getGender(), dto.getPhone(), dto.getStatus(), dto.getCityId(), dto.getDistrictId(), dto.getWardId(), pageable)
                .map(p -> objectMapper.convertValue(p, AccountDTO.class));
    }

    @Override
    public Page<Map<String, String>> getUsersForSelect(AccountDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return repository.findDataForSelect(dto.getKey(), pageable);
    }

    @Override
    @Transactional
    public void deleteUser(AccountDTO dto) {
        try {
            repository.delete(dto.getId());
        } catch (Exception ex) {
            log.error("Delete account exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

//    @PostConstruct
//    public void addUserTest() {
//        Account account = new Account();
//        account.setUsername("hienld");
//        account.setRole("ROLE_ADMIN");
//        account.setNumberFailures(1);
//        account.setPassword(passwordEncoder.encode("290401"));
//        account.setPhone("0356888380");
//        repository.save(account);
//    }

    @Override
    public Authentication authenticate(AuthRequest authentication) throws AuthenticationException {
        String username = authentication.getUsername();
        String password = authentication.getPassword();
        Optional<Account> acc = repository.findByUsername(username);
        if (!acc.isPresent()) {
            throw new BadRequestException("Tài khoản không tồn tại.");
        }
        if (acc.get().getNumberFailures() > 5) {
            throw new BadRequestException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị.");
        }
        boolean result = passwordEncoder.matches(password, acc.get().getPassword());
        if (!result) {
            acc.get().setNumberFailures(acc.get().getNumberFailures() + 1);
            repository.saveAndFlush(acc.get());
            throw new BadRequestException("Thông tìn tài khoản, mật khẩu không chính xác");
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(acc.get().getRole()));
        return new UsernamePasswordAuthenticationToken(username, null, grantedAuths);
    }

    @Override
    public String uploadAvt(String username, MultipartFile fileUpload) {
        String nameFile;
        LocalDateTime myObj = LocalDateTime.now();
        try {
            ClassPathResource resource = new ClassPathResource(".");
            String absolutePath = resource.getFile().getAbsolutePath();
            Path imagesPath = Paths.get(absolutePath, "images");
            if (!Files.exists(imagesPath)) {
                Files.createDirectories(imagesPath);
            }
            InputStream inputStream = fileUpload.getInputStream();
            String ext = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyyHHmmss");

            String formattedDate = myObj.format(myFormatObj);
            nameFile = formattedDate + "_" + username + "." + ext;
            Files.copy(inputStream, imagesPath.resolve(nameFile),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return nameFile;
    }

    @Override
    public String getFile(String username) {
        Account account = repository.findByUsername(username).get();
        try {
            ClassPathResource resource = new ClassPathResource(".");
            String absolutePath = resource.getFile().getAbsolutePath();
            Path imagesPath = Paths.get(absolutePath, "images");
            String image = account.getAvatar();
            if (image.endsWith(".png")) {
                File file = new File(imagesPath + "/" + image);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileContent);
                return  "data:image/png;base64," + base64;
            }
            if (image.endsWith(".jpg")) {
                File file = new File(imagesPath + "/" + image);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileContent);
                return  "data:image/jpg;base64," + base64;
            }
            if (image.endsWith(".gif")) {
                File file = new File(imagesPath + "/" + image);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileContent);
                return "data:image/gif;base64," + base64;
            }
            if (image.endsWith(".bmp")) {
                File file = new File(imagesPath + "/" + image);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileContent);
                return  "data:image/bmp;base64," + base64;
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

}
