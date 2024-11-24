package com.balance.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class SuccessStatusDTO {
    @Schema(description = "Result of the operation", example = "success", allowableValues = {"success", "error"})
    private String result;

    @Schema(description = "HTTP status code", example = "200")
    private Integer status;

    public SuccessStatusDTO(String result, Integer status){
        this.result = result;
        this.status = status;
    }
}
