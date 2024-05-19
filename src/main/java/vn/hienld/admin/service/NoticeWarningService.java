package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import vn.hienld.admin.dto.NoticeWarningDTO;
import vn.hienld.admin.model.Furniture;
import vn.hienld.admin.model.NoticeWarning;

public interface NoticeWarningService {
    Page<NoticeWarningDTO> findAll(NoticeWarningDTO dto);
    void delete(NoticeWarningDTO dto);
    NoticeWarning save(NoticeWarningDTO dto);
}
