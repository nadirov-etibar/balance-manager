package com.balance.request.homePage;

import com.balance.request.category.CategoryDTO;
import com.balance.request.subcategory.SubcategoryDTO;
import com.balance.request.transaction.TransactionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HomePageDTO {
    private BigDecimal balance;
    private List<CategoryDTO> categories;
    private List<SubcategoryDTO> subcategories;
    private List<TransactionDTO> transactions;
}
