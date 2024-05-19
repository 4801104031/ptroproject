package vn.hienld.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoSyncRoomDTO {
    private String name;
    private Integer status;
    private Integer amountOfPeople;
    private Integer floor;
    private Integer electricFee;
    private Integer waterFee;
    private Integer internetFee;
    private Integer environmentFee;
    private Integer buildingId;
    private String buildingName;
    private String description;
    private Integer amountRooms;
}
