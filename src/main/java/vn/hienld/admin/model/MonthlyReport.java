package vn.hienld.admin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "monthly_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReport extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer month;
    private Integer year;
    private Integer amountOfElectricity;
    private Integer amountOfWater;
    private Integer status;
    // Với status:
    //     +0: Chưa thanh toán
    //     +1: Đã thanh toán
    private Integer isDeleted = 1;
    // Với status:
    //     +0: Đã xóa
    //     +1: Chưa xóa
    private Integer buildingId;
    private String buildingName;
    private Integer roomId;
    private String roomName;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime payDate;
}
