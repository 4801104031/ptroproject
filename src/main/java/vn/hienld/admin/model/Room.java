package vn.hienld.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer status;
    // Với status:
    //   +0: chưa được thuê
    //   +1: đã được thuê
    //   +2: đã được cọc
    private Integer amountOfPeople;
    private Integer floor;
    private Integer area;
    private Integer rent;
    private Integer parkingFee;
    private Integer electricFee;
    private Integer waterFee;
    private Integer internetFee;
    private Integer environmentFee;
    private Integer buildingId;
    private String buildingName;
    private String description;
}
