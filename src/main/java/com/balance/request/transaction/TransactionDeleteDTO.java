package com.balance.request.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDeleteDTO {
    @Schema(description = "Transaction ID", example = "1")
    @NotNull(message = "ID is required")
    private Integer id;
}
