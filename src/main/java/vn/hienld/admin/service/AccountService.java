package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.AccountDTO;
import vn.hienld.admin.dto.AuthRequest;
import vn.hienld.admin.model.Account;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AccountService {
    Account save(AccountDTO dto);
    Page<AccountDTO> getAllUsers(AccountDTO dto);
    Page<Map<String, String>> getUsersForSelect(AccountDTO dto);
    void deleteUser(AccountDTO dto);
    Authentication authenticate(AuthRequest authentication);
    String uploadAvt(String username,  MultipartFile fileUpload);
    String getFile(String username);
}
