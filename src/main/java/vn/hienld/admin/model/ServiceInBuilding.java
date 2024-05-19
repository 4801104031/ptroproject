package vn.hienld.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInBuilding extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer status;
    // Với status:
    //    + 0: không hoạt động
    //    + 1: đang hoạt động
    private String phone;
    private String email;
    private String address;
    private Integer buildingId;
    private String buildingName;
}
