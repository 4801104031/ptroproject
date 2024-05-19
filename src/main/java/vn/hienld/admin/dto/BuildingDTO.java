package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BuildingDTO {
    private Integer id;
    private String name;
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    private Integer wardId;
    private String wardName;
    private String addressDetail;
    private Integer status;
    // Với status:
    //     +0: Chưa đầy
    //     +1: Đã đầy
    //     +2: Hiện đang tu sửa có thể đăng ký trước
    private Integer amountRooms;
    private Integer floors;
    private String description;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Integer accountId;
    private Integer page = 1;
    private Integer size = 10;

}
