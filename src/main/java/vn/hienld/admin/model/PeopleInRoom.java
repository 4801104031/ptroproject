package vn.hienld.admin.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "people_in_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleInRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer customerId;
    private String customerName;
    private Integer roomId;
}
