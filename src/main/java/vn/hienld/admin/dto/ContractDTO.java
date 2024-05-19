package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.hienld.admin.model.Account;
import vn.hienld.admin.model.Room;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContractDTO {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime signDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dueDate;
    private Integer status;
    // Với status:
    //   +0: hết hiệu lực
    //   +1: Đang thực hiện
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
    private List<AccountDTO> users;
    private String notes;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDateSignDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDateSignDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDateDueDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDateDueDate;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
