package com.balance.response;

import com.balance.request.category.CategoryDTO;
import com.balance.request.homePage.HomePageDTO;
import com.balance.request.subcategory.SubcategoryDTO;
import com.balance.request.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HomePageResponseDTO {
    @Schema(description = "Balance", example = "1000")
    private BigDecimal balance;

    @Schema(description = "Transaction list")
    private List<TransactionDTO> transactions;

    @Schema(description = "Category list", example = "[{\"id\": 1, \"type\": \"INCOME\"}]")
    private List<CategoryDTO> categories;

    @Schema(description = "Subcategory list", example = "[{\"id\": 1, \"type\": \"SALARY\"}]")
    private List<SubcategoryDTO> subcategories;

    public HomePageResponseDTO(HomePageDTO homePageDTO) {
        this.balance = homePageDTO.getBalance();
        this.transactions = homePageDTO.getTransactions();
        this.categories = homePageDTO.getCategories();
        this.subcategories = homePageDTO.getSubcategories();
    }
}
