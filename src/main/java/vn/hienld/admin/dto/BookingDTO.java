package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.hienld.admin.model.Room;

import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Integer id;
    private Integer status;
    private Integer rentalDeposit;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateSet;
    private Integer roomId;
    private String roomName;
    private Integer buildingId;
    private String buildingName;
    private String cusName;
    private Integer isDeleted;
    private Integer afterDays;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDate;

    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
