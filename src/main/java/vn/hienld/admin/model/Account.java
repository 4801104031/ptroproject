package vn.hienld.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Entity(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
    private Integer id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;
    private String role;
    private Integer numberFailures;
    private String avatar;
    private String fullName;
    private String email;
    private String phone;
    private LocalDateTime dob;
    private String citizenIdentificationNumber;
    private Integer gender;
    // Với gender:
    //     +0: Khác
    //     +1: Nam
    //     +2: Nữ
    private String issuedBy;
    private LocalDateTime issuedOn;
    private LocalDateTime moveInDate;
    private String permanentAddress;
    private String description;
    private Integer status;
    // Với status:
    //     +0: Không hoạt động
    //     +1: Đang hoạt động
    private Integer cityId;
    private Integer districtId;
    private Integer wardId;
    private String cityName;
    private String districtName;
    private String wardName;
    private Integer isDeleted = 1;
    // Với status:
    //     +0: Đã xóa
    //     +1: Chưa xóa
}
