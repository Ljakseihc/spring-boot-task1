package epam.springboot.advanced.demo.models.dto;

import java.math.BigDecimal;

public record ProductSaveDTO(
        String name,
        BigDecimal cost,
        Boolean isAvailable
) {
}
