package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceInBuildingDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer status;
    // Với status:
    //    + 0: không hoạt động
    //    + 1: đang hoạt động
    private String phone;
    private String email;
    private String address;
    private Integer buildingId;
    private String buildingName;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
