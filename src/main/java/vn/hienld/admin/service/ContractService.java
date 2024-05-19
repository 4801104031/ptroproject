package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.ContractDTO;
import vn.hienld.admin.dto.Select2DTO;
import vn.hienld.admin.model.Contract;

import java.util.Map;

public interface ContractService {
    Page<ContractDTO> findAll(ContractDTO dto);
    Contract save(ContractDTO dto);
    void delete(ContractDTO dto);
//    Page<Map<String, Object>> findDiaChiByChaId(Select2DTO dto);
}
