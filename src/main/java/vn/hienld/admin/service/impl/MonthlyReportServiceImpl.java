package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hienld.admin.dto.MonthlyReportDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.MonthlyReport;
import vn.hienld.admin.repository.ContractRepository;
import vn.hienld.admin.repository.MonthlyReportRepository;
import vn.hienld.admin.service.MonthlyReportService;

import java.util.Objects;

@Service
@Slf4j
public class MonthlyReportServiceImpl implements MonthlyReportService {

    @Autowired
    private MonthlyReportRepository monthlyReportRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ContractRepository contractRepository;

    @Override
    public Page<MonthlyReportDTO> findAll(MonthlyReportDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return monthlyReportRepository.findAll(dto.getRoomId(), dto.getFromDate(), dto.getToDate(), dto.getStatus(), dto.getIsDeleted(), dto.getBuildingId(), pageable)
                .map(p -> objectMapper.convertValue(p, MonthlyReportDTO.class));
    }

    @Override
    @Transactional
    public void delete(MonthlyReportDTO dto) {
        try {
            monthlyReportRepository.delete(dto.getId());
        }catch (Exception e){
            log.error("Delete monthlyReport exception :{}", e.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    public MonthlyReport save(MonthlyReportDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    private MonthlyReport add(MonthlyReportDTO dto) {
        MonthlyReport monthlyReport = new MonthlyReport();
        monthlyReport.setAmountOfElectricity(dto.getAmountOfElectricity());
        monthlyReport.setAmountOfWater(dto.getAmountOfWater());
        monthlyReport.setMonth(dto.getMonth());
        monthlyReport.setYear(dto.getYear());
        monthlyReport.setRoomId(dto.getRoomId());
        monthlyReport.setRoomName(dto.getRoomName());
        monthlyReport.setBuildingId(dto.getBuildingId());
        monthlyReport.setBuildingName(dto.getBuildingName());
        monthlyReport.setStatus(0);

        return monthlyReportRepository.save(monthlyReport);
    }

    private MonthlyReport update(MonthlyReportDTO dto) {
        MonthlyReport monthlyReport = monthlyReportRepository.getReferenceById(dto.getId());
        if(dto.getAmountOfElectricity() != null &&!Objects.equals(dto.getAmountOfElectricity(), monthlyReport.getAmountOfElectricity())){
            monthlyReport.setAmountOfElectricity(dto.getAmountOfElectricity());
        }
        if(dto.getAmountOfWater() != null && !Objects.equals(dto.getAmountOfWater(), monthlyReport.getAmountOfWater())){
            monthlyReport.setAmountOfWater(dto.getAmountOfWater());
        }
        if (dto.getStatus() != null){
            monthlyReport.setStatus(dto.getStatus());
        }

        return monthlyReportRepository.save(monthlyReport);
    }
}
