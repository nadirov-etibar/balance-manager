package com.balance.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDTO {
    @Schema(description = "Result of the operation", example = "error")
    private String result;

    @Schema(description = "HTTP status code", example = "400")
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Error message provided when the result is 'error'", example = "Invalid input")
    private String message;


    public ErrorDTO(String result, Integer status){
        this.result = result;
        this.status = status;
    }
}
