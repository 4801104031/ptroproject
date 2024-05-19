package vn.hienld.admin.dto;

import lombok.Data;

@Data
public class ServicePackageDTO {
    private String name;
    private String price;
    private String limitPeople;
    private String limitProduct;
    private String description;
    private String note;
}
