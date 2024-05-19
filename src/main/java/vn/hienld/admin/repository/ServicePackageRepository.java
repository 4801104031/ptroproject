package vn.hienld.admin.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.ServicePackage;

import java.util.List;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer> {
    List<ServicePackage> findServicePackageByServiceId(Integer serviceId);

    @Modifying
    @Query(value = "delete from services_package u where u.serviceId = ?1")
    @Transactional
    void deleteByServiceId(Integer serviceId);
}
