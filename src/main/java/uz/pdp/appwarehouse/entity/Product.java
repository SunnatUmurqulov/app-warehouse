package uz.pdp.appwarehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.appwarehouse.entity.template.AbsEntity;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbsEntity {
    @ManyToOne(optional = false)
    private Category category;

    @OneToOne
    private Attachment photo;

    @Column(nullable = false,unique = true)
    private String code;
    @ManyToOne
    private Measurement measurement;
}
