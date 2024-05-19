package vn.hienld.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hienld.admin.model.Furniture;

public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    @Query(value = "SELECT u FROM furniture u where 1 = 1 " +
            "and (?1 is null or lower(u.name) like lower('%' || ?1 || '%') ) " +
            "and (?2 is null or u.roomId = ?2) " +
            "and (?3 is null or u.status = ?3) " +
            "ORDER BY u.createdDate desc")
    Page<Furniture> findAll(String name, Integer roomId, Integer status, Pageable pageable);
}
