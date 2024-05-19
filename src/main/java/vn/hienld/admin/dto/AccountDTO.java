package vn.hienld.admin.dto;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private Integer id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;
    private String role;
    private Integer numberFailures = 0;
    private String avatar;
    private String fullName;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dob;
    private String citizenIdentificationNumber;
    private Integer gender;
    private String issuedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime issuedOn;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime moveInDate;
    private String permanentAddress;
    private String description;
    private Integer status;
    private Integer cityId;
    private Integer districtId;
    private Integer wardId;
    private String cityName;
    private String districtName;
    private String wardName;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private Integer isDeleted;

    private Integer page = 1;
    private Integer size = 10;
    private String key;
}
