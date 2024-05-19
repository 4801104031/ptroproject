package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportDTO {

    public MonthlyReportDTO(Integer id, Integer month, Integer year, Integer roomId, String roomName,
                            Integer buildingId, String buildingName, Integer amountOfElectricity, Integer amountOfWater, Integer status,
                            Integer electricityBill, Integer waterBill, Integer internetFee,
                            Integer environmentFee, Integer parkingFee) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.roomId = roomId;
        this.roomName = roomName;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.amountOfElectricity = amountOfElectricity;
        this.amountOfWater = amountOfWater;
        this.status = status;
        this.electricityBill = electricityBill;
        this.waterBill = waterBill;
        this.internetFee = internetFee;
        this.environmentFee = environmentFee;
        this.parkingFee = parkingFee;
        this.totalBill = electricityBill + waterBill + internetFee + environmentFee + parkingFee;
    }

    private Integer id;
    private Integer month;
    private Integer year;
    private Integer amountOfElectricity;
    private Integer amountOfWater;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payDate;

    private Integer electricityBill;
    private Integer waterBill;
    private Integer totalBill;
    private Integer servicesBill;
    private Integer internetFee;
    private Integer environmentFee;
    private Integer parkingFee;

    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer buildingId;
    private String buildingName;
    private Integer roomId;
    private String roomName;
    private Integer isDeleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toDate;

    private Integer page = 1;
    private Integer size = 10;
}
