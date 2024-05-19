package vn.hienld.admin.service;

import vn.hienld.admin.dto.ServicePackageDTO;
import vn.hienld.admin.model.ServicePackage;

import java.util.List;

public interface ServicePackageService {
    List<ServicePackage> findPackageByServiceId(Integer serviceId);
    void save(List<ServicePackageDTO> dto, Integer serviceId);
}
