package vn.hienld.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.ServicePackageDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.ServicePackage;
import vn.hienld.admin.repository.ServicePackageRepository;
import vn.hienld.admin.service.ServicePackageService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ServicePackageServiceImpl implements ServicePackageService {

    @Autowired
    ServicePackageRepository servicePackageRepository;

    @Override
    public List<ServicePackage> findPackageByServiceId(Integer serviceId) {
        return servicePackageRepository.findServicePackageByServiceId(serviceId);
    }

    @Override
    public void save(List<ServicePackageDTO> dto, Integer serviceId) {
        try {
            servicePackageRepository.deleteByServiceId(serviceId);

            List<ServicePackage> servicePackages = new ArrayList<>();
            for (ServicePackageDTO e : dto) {
                ServicePackage servicePackage = new ServicePackage();
                servicePackage.setServiceId(serviceId);
                servicePackage.setName(e.getName());
                servicePackage.setPrice(e.getPrice());
                servicePackage.setLimitPeople(e.getLimitPeople());
                servicePackage.setLimitProduct(e.getLimitProduct());
                servicePackage.setNote(e.getNote());
                servicePackage.setDescription(e.getDescription());

                servicePackages.add(servicePackage);
            }
            servicePackageRepository.saveAll(servicePackages);
        }catch (Exception ex){
            log.error("There are some problems when editing the package : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }
}
