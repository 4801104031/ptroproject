package vn.hienld.admin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer status;
    // Với status:
    //     +0: Đã cọc
    //     +1: Hủy cọc
    private Integer rentalDeposit;
    private LocalDateTime dateSet;
    private Integer roomId;
    private String roomName;
    private Integer afterDays;
    private Integer buildingId;
    private String buildingName;
    private String cusName;
    private Integer isDeleted = 1;
    // Với isDeleted:
    //     +0: Đã xóa
    //     +1: Chưa xóa
}
