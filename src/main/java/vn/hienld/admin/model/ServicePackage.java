package vn.hienld.admin.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "services_package")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePackage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String price;
    private String limitPeople;
    private String limitProduct;
    private String description;
    private String note;
    private Integer serviceId;
}
