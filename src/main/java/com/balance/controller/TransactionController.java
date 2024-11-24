package com.balance.controller;

import com.balance.request.PaginationRequestDTO;
import com.balance.request.transaction.TransactionCreateDTO;
import com.balance.request.transaction.TransactionDTO;
import com.balance.request.transaction.TransactionDeleteDTO;
import com.balance.request.transaction.TransactionEditDTO;
import com.balance.response.ErrorFieldsStatusDTO;
import com.balance.response.ErrorStatusDTO;
import com.balance.response.SuccessStatusDTO;
import com.balance.response.TransactionResponseDTO;
import com.balance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add-transaction")
    @Operation(summary = "New transaction", description = "Create new transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new transaction successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorFieldsStatusDTO.class))
            )
    })
    public ResponseEntity<SuccessStatusDTO> addTransaction (@Valid @RequestBody TransactionCreateDTO request, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        transactionService.createTransaction(request, httpServletRequest, bindingResult);

        return ResponseEntity.ok(new SuccessStatusDTO("success", 200));
    }

    @PutMapping("/edit-transaction")
    @Operation(summary = "Edit transaction", description = "Edit transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit transaction successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorFieldsStatusDTO.class))
            )
    })
    public ResponseEntity<SuccessStatusDTO> editTransaction (@Valid @RequestBody TransactionEditDTO request, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        transactionService.editTransaction(request, httpServletRequest, bindingResult);

        return ResponseEntity.ok(new SuccessStatusDTO("success", 200));
    }

    @DeleteMapping("/delete-transaction")
    @Operation(summary = "Delete transaction", description = "Delete transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete transaction successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessStatusDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or input",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorStatusDTO.class))
            )
    })
    public ResponseEntity<SuccessStatusDTO> deleteTransaction (@Valid @RequestBody TransactionDeleteDTO request, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        transactionService.deleteTransaction(request, httpServletRequest, bindingResult);

        return ResponseEntity.ok(new SuccessStatusDTO("success", 200));
    }

    @GetMapping("/transactions")
    @Operation(summary = "Transactions", description = "Getting all transactions with pagination and filters, Look PaginationRequestDTO schema")
    public ResponseEntity<TransactionResponseDTO> getTransactions (@Valid @RequestBody PaginationRequestDTO request, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        Page<TransactionDTO> transactionDTO = transactionService.getTransaction(request, httpServletRequest, bindingResult);

        return ResponseEntity.ok(new TransactionResponseDTO(transactionDTO.getContent()));
    }

}
