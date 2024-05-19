package vn.hienld.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Furniture extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer status;
    private String image;
    private Integer price;
    private Integer roomId;
    private String roomName;
    private Integer buildingId;
    private String buildingName;
    private String description;
}
