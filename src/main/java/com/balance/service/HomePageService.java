package com.balance.service;

import com.balance.entity.Category;
import com.balance.entity.Transaction;
import com.balance.entity.User;
import com.balance.repository.CategoryRepository;
import com.balance.repository.SubcategoryRepository;
import com.balance.repository.TransactionRepository;
import com.balance.repository.UserRepository;
import com.balance.request.category.CategoryDTO;
import com.balance.request.homePage.HomePageDTO;
import com.balance.request.subcategory.SubcategoryDTO;
import com.balance.request.transaction.TransactionDTO;
import com.balance.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomePageService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public HomePageDTO getHomePage(HttpServletRequest httpRequest) {
        String token = jwtUtil.extractToken(httpRequest);

        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BigDecimal balance = transactionRepository.findByUser(user).stream()
                .map(transaction -> {
                    if (transaction.getCategory().getType() == Category.CategoryType.OUTCOME) {
                        return transaction.getAmount().negate();
                    } else {
                        return transaction.getAmount();
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getId(), category.getType().name()))
                .collect(Collectors.toList());


        List<SubcategoryDTO> subcategories = subcategoryRepository.findAll().stream()
                .map(subcategory -> new SubcategoryDTO(subcategory.getId(), subcategory.getType().name()))
                .collect(Collectors.toList());

        List<TransactionDTO> recentTransactions = transactionRepository.findTop10ByUserOrderByDateDesc(user).stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getCategory().getId(),
                        transaction.getSubcategory().getId(),
                        transaction.getAmount(),
                        transaction.getDate()))
                .collect(Collectors.toList());

        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setBalance(balance);
        homePageDTO.setCategories(categories);
        homePageDTO.setSubcategories(subcategories);
        homePageDTO.setTransactions(recentTransactions);

        return homePageDTO;
    }
}
