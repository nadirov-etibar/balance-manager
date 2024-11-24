package com.balance.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaginationRequestDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Start date for filtering", example = "2024-11-14", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "End date for filtering", example = "2024-11-14", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate endDate;

    @Schema(description = "Category ID for filtering", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer categoryId;

    @Schema(description = "Subcategory ID for filtering", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer subcategoryId;

    @Schema(description = "Amount for filtering", example = "100.00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BigDecimal amount;

    @NotNull(message = "Page number is required")
    @Positive(message = "Page number must be greater than 0")
    @Schema(description = "Page number for pagination", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int page = 1;

    @NotNull(message = "Size per page is required")
    @Positive(message = "Size per page must be greater than 0")
    @Schema(description = "Size per page for pagination", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private int size = 10;
}
