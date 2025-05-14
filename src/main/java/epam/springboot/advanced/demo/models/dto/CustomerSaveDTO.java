package epam.springboot.advanced.demo.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record CustomerSaveDTO(
        String name,
        @Email
        String email,
        @Min(0)
        BigDecimal money
) {}
