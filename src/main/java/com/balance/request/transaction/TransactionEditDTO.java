package com.balance.request.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TransactionEditDTO {
    @Schema(description = "Transaction ID", example = "1")
    @NotNull(message = "Id is required")
    private Integer id;

    @Schema(description = "Category ID", example = "2")
    private Integer categoryId;

    @Schema(description = "Subcategory ID", example = "1")
    private Integer subcategoryId;

    @Schema(description = "Transaction amount", example = "100")
    private BigDecimal amount;

    @Schema(description = "Transaction date", example = "")
    private LocalDate date;
}
