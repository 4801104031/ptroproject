package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeWarningDTO {
    private Integer id;
    private String title;
    private String problem;
    private Integer severity;
    // Với severity:
    //     +0: Bình thường
    //     +1: Nhắc nhở
    //     +2: Nghiêm trọng
    private String description;
    private Integer typeWarning;
    // Với severity:
    //     +0: Tất cả
    //     +1: Mail
    //     +2: Telegram
    private Integer objectType;
    private Integer objectId;
    private String objectName;
    private String content;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Integer page = 1;
    private Integer size = 10;
}
