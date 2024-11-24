package com.balance.response;

import com.balance.request.transaction.TransactionDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class TransactionResponseDTO {
    private List<TransactionDTO> transactions;

    // Constructor to map all transactions' fields to respective lists
    public TransactionResponseDTO(List<TransactionDTO> content) {
       this.transactions = content;
    }
}
