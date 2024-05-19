package vn.hienld.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServicePackageIn {
    Integer serviceId;
    List<ServicePackageDTO> data;
}
