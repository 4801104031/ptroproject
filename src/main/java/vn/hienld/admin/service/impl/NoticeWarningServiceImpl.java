package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hienld.admin.dto.NoticeWarningDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.NoticeWarning;
import vn.hienld.admin.repository.NoticeWarningRepository;
import vn.hienld.admin.service.NoticeWarningService;

@Service
@Slf4j
public class NoticeWarningServiceImpl implements NoticeWarningService {

    @Autowired
    private NoticeWarningRepository noticeWarningRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<NoticeWarningDTO> findAll(NoticeWarningDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return noticeWarningRepository.findAll(dto.getTitle(), dto.getProblem(), dto.getSeverity(), pageable)
                .map(p -> objectMapper.convertValue(p, NoticeWarningDTO.class));
    }

    @Override
    public void delete(NoticeWarningDTO dto) {
        try {
            noticeWarningRepository.delete(noticeWarningRepository.getReferenceById(dto.getId()));
        }catch (Exception e){
            log.error("Delete notification exception : {}", e.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }

    @Override
    @Transactional
    public NoticeWarning save(NoticeWarningDTO dto) {
        if(dto.getId() == null){
            return add(dto);
        }
        return update(dto);
    }

    private NoticeWarning add(NoticeWarningDTO dto) {
        NoticeWarning noticeWarning = new NoticeWarning();
        initNoticeWarning(dto, noticeWarning);
        return noticeWarning;
    }

    private NoticeWarning update(NoticeWarningDTO dto) {
        NoticeWarning noticeWarning = noticeWarningRepository.findById(dto.getId()).get();
        initNoticeWarning(dto, noticeWarning);
        return noticeWarning;
    }

    private void initNoticeWarning(NoticeWarningDTO dto, NoticeWarning noticeWarning) {
        noticeWarning.setTitle(dto.getTitle());
        noticeWarning.setProblem(dto.getProblem());
        noticeWarning.setSeverity(dto.getSeverity());
        noticeWarning.setDescription(dto.getDescription());
        noticeWarning.setTypeWarning(dto.getTypeWarning());
        noticeWarning.setObjectType(dto.getObjectType());
        noticeWarning.setObjectId(dto.getObjectId());
        noticeWarning.setObjectName(dto.getObjectName());
        noticeWarning.setContent(dto.getContent());

        noticeWarningRepository.save(noticeWarning);
    }
}
