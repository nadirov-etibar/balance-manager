package com.balance.controller;

import com.balance.request.user.UserRegistrationDTO;
import com.balance.response.ErrorFieldsStatusDTO;
import com.balance.response.ErrorStatusDTO;
import com.balance.response.LoginStatusDTO;
import com.balance.response.SuccessStatusDTO;
import com.balance.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/registration")
    @Operation(summary = "Register a new user", description = "Allows a new user to register.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Register successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorStatusDTO.class))
            )
    })
    public ResponseEntity<SuccessStatusDTO> registrationUser (@Valid @RequestBody UserRegistrationDTO request, BindingResult bindingResult){
        authService.registrationUser(request, bindingResult);

        return ResponseEntity.ok(new SuccessStatusDTO("success", 200));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login a user", description = "Authenticate a user with username and password.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorStatusDTO.class))
            )
    })
    public ResponseEntity<LoginStatusDTO> loginUser (@RequestBody UserRegistrationDTO request, BindingResult bindingResult){
        String token = authService.loginUser(request, bindingResult);

        return ResponseEntity.ok(new LoginStatusDTO("success", 200, token));
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "Logout a user", description = "Invalidate the current user session.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Something gets wrong",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorStatusDTO.class))
            )
    })
    public ResponseEntity<SuccessStatusDTO> logoutUser (HttpServletRequest request){
        authService.logoutUser(request);

        return ResponseEntity.ok(new SuccessStatusDTO("success", 200));
    }
}
