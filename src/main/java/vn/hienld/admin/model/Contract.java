package vn.hienld.admin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "contract")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime signDate;
    private LocalDateTime dueDate;
    private Integer status;
    private Integer roomId;
    private String roomName;
    private Integer buildingId;
    private String buildingName;
    private Integer rent;
    private Integer parkingFee;
    private Integer electricFee;
    private Integer waterFee;
    private Integer internetFee;
    private Integer environmentFee;
    private String notes;
}
