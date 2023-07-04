package uz.pdp.appwarehouse.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.appwarehouse.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Measurement extends AbsEntity {

}
