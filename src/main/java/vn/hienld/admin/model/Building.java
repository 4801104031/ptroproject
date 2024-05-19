package vn.hienld.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "building")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "building_seq")
    @SequenceGenerator(name = "building_seq", sequenceName = "building_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer cityId;
    private Integer districtId;
    private Integer wardId;
    private String cityName;
    private String districtName;
    private String wardName;
    private String addressDetail;
    private Integer status;
    // Với status:
    //     +0: Chưa đầy
    //     +1: Đã đầy
    private Integer amountRooms;
    private Integer floors;
    private String description;
    private Integer accountId;
}
