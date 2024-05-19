package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FurnitureDTO {
    private Integer id;
    private String name;
    private Integer status;
    private String image;
    private Integer price;
    private Integer roomId;
    private String roomName;
    private Integer buildingId;
    private String buildingName;
    private String lastModifiedBy;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
