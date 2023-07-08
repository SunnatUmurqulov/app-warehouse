package uz.pdp.appwarehouse.entity.template;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private boolean active = true;
}
