package vn.hienld.admin.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.model.Building;

import java.util.Map;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Cacheable("user")
    @Query(value = "select u from account u where u.username = ?1 and u.isDeleted = 1")
    Optional<Account> findByUsername(String username);


    @Query(value = "SELECT u FROM account u where 1 = 1 " +
            "and (?1 is null or lower(u.fullName) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.gender = ?2) " +
            "and (?3 is null or u.phone = ?3) " +
            "and (?4 is null or u.status = ?4) " +
            "and (?5 is null or u.cityId = ?5) " +
            "and (?6 is null or u.districtId = ?6) " +
            "and (?7 is null or u.wardId = ?7) and u.isDeleted = 1 " +
            "ORDER BY u.createdDate desc")
    Page<Account> findAll(String fullName, Integer gender, String phone, Integer status, Integer cityId, Integer districtId, Integer wardId, Pageable pageable);

    @Query(value = "SELECT u.id as id, u.fullName as name FROM account u where 1 = 1 " +
            "and (?1 is null or lower(u.fullName) like lower('%' || ?1 || '%') ) " +
            "and u.status = 1 " +
            "ORDER BY u.createdDate desc")
    Page<Map<String, String>> findDataForSelect(String fullName, Pageable pageable);

    @Modifying
    @Query(value = "update account set isDeleted = 0 where id = ?1", nativeQuery = true)
    void delete(Integer id);

}
