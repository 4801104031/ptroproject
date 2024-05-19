package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.MonthlyReportDTO;
import vn.hienld.admin.model.Furniture;
import vn.hienld.admin.model.MonthlyReport;

public interface MonthlyReportService {
    Page<MonthlyReportDTO> findAll(MonthlyReportDTO dto);
    void delete(MonthlyReportDTO dto);
    MonthlyReport save(MonthlyReportDTO dto);
}
