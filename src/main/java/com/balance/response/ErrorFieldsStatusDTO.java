package com.balance.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorFieldsStatusDTO extends ErrorDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Detailed validation errors", example = "{\"field1\": \"error1\", \"field2\": \"error2\"}")
    private Map<String, String> errors;
}
