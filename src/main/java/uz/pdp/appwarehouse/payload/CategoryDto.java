package uz.pdp.appwarehouse.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryDto {
    private String name;
    private Integer parentCategoryId;
}
