package vn.hienld.admin.dto;

import lombok.Data;

@Data
public class Select2DTO {
    private String key;
    private Integer status;
    private Integer cha_id;
    // cha_id used to for load address(province => district => ward)
    private Integer page = 1;
    private Integer size = 10;


}
