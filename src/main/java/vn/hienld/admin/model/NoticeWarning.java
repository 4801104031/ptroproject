package vn.hienld.admin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "notice_warning")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeWarning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    // Với typeWarning:
    //     +0: Tất cả
    //     +1: Mail
    //     +2: Telegram
    private Integer objectType;
    private Integer objectId;
    private String objectName;
    private String content;

}
