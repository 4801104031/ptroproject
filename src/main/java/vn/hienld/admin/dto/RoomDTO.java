package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomDTO {
    private Integer id;
    private String name;
    private Integer status;
    // Với status:
    //   +0: chưa được thuê
    //   +1: đã được thuê
    private Integer amountOfPeople;
    private Integer floor;
    private Integer rent;
    private Integer parkingFee;
    private Integer electricFee;
    private Integer waterFee;
    private Integer area;
    private Integer internetFee;
    private Integer environmentFee;
    private Integer buildingId;
    private String buildingName;
    private String description;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
