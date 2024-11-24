package com.balance.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LoginStatusDTO {
    @Schema(description = "Result of the operation", example = "success", allowableValues = {"success", "error"})
    private String result;

    @Schema(description = "HTTP status code", example = "200")
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "JWT token for authentication (returned only in success responses)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    public LoginStatusDTO(String result, Integer status){
        this.result = result;
        this.status = status;
    }

    public LoginStatusDTO(String result, Integer status, String token){
        this.result = result;
        this.status = status;
        this.token = token;
    }
}
