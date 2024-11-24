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
public class TransactionDTO {
    @Schema(description = "Transaction ID", example = "1")
    @NotNull(message = "ID is required")
    private Integer id;

    @Schema(description = "Category ID", example = "2")
    @NotNull(message = "Category Id is required")
    private Integer categoryId;

    @Schema(description = "Subcategory ID", example = "1")
    @NotNull(message = "Subcategory id is required")
    private Integer subcategoryId;

    @Schema(description = "Transaction amount", example = "100")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Schema(description = "Transaction date", example = "", nullable = true)
    private LocalDate date;
}
