package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Building;
import vn.hienld.admin.model.ServiceInBuilding;

public interface ServiceInBuildingRepository extends JpaRepository<ServiceInBuilding, Integer> {
    @Query(value = "SELECT u FROM services u where 1 = 1 " +
            "and (?1 is null or lower(u.name) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.status = ?2) " +
            "ORDER BY u.createdDate desc")
    Page<ServiceInBuilding> findAll(String name, Integer status, Pageable pageable);
}
