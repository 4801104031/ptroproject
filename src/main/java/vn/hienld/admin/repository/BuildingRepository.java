package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Building;

import java.util.Map;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query(value = "SELECT u FROM building u where 1 = 1 " +
            "and (?1 is null or lower(u.addressDetail) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.cityId = ?2) " +
            "and (?3 is null or u.districtId = ?3) " +
            "and (?4 is null or u.wardId = ?4) " +
            "and (?5 is null or u.status = ?5) " +
            "ORDER BY u.createdDate desc")
    Page<Building> findAll(String addressDetail, Integer cityId, Integer districtId, Integer wardId, Integer status, Pageable pageable);

    @Query(value = "select u.id, u.ten as name from dm_diachi u where 1 = 1 " +
            "and ((?1 is null and u.chaid is null) or u.chaid = ?1) " +
            "and (?2 like '' or (lower(u.ten) like %?#{escape([1])}% escape ?#{escapeCharacter()})) order by u.ten",
            nativeQuery = true)
    Page<Map<String, Object>> findDiaChiByChaId(Integer chaId, String lower, Pageable pageable);
}
