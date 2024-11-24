package com.balance.service;

import com.balance.entity.Category;
import com.balance.entity.Subcategory;
import com.balance.entity.Transaction;
import com.balance.entity.User;
import com.balance.exception.HandleHttpErrors;
import com.balance.repository.CategoryRepository;
import com.balance.repository.SubcategoryRepository;
import com.balance.repository.TransactionRepository;
import com.balance.repository.UserRepository;
import com.balance.request.PaginationRequestDTO;
import com.balance.request.transaction.TransactionCreateDTO;
import com.balance.request.transaction.TransactionDTO;
import com.balance.request.transaction.TransactionDeleteDTO;
import com.balance.request.transaction.TransactionEditDTO;
import com.balance.response.ErrorStatusDTO;
import com.balance.utils.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
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

    public Page<TransactionDTO> getTransaction(PaginationRequestDTO request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        String token = jwtUtil.extractToken(httpRequest);

        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (bindingResult.hasErrors()){
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        } else {
            Pageable pageable = PageRequest.of(request.getPage() -1, request.getSize());

            Specification<Transaction> spec = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user"), user)
            );

            if (request.getStartDate() != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("date"), request.getStartDate())
                );
            }

            if (request.getEndDate() != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getEndDate())
                );
            }

            if (request.getCategoryId() != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("category").get("id"), request.getCategoryId())
                );
            }

            if (request.getSubcategoryId() != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("subcategory").get("id"), request.getSubcategoryId())
                );
            }

            if (request.getAmount() != null) {
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("amount"), request.getAmount()));
            }


            return  transactionRepository.findAll(spec, pageable).map(transaction -> new TransactionDTO(
                    transaction.getId(),
                    transaction.getCategory().getId(),
                    transaction.getSubcategory().getId(),
                    transaction.getAmount(),
                    transaction.getDate()
            ));
        }
    }

    public Transaction createTransaction(TransactionCreateDTO request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        String token = jwtUtil.extractToken(httpRequest);

        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (bindingResult.hasErrors()){
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        } else {
            BigDecimal balance = transactionRepository.findByUser(user).stream()
                    .map(transaction -> {
                        if (transaction.getCategory().getType() == Category.CategoryType.OUTCOME) {
                            return transaction.getAmount().negate();
                        } else {
                            return transaction.getAmount();
                        }
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if(balance.compareTo(request.getAmount()) < 0 && request.getCategoryId() == 2) {
                throw new HandleHttpErrors("Balance cannot be negative");
            } else {
                Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
                Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));

                Transaction transaction = new Transaction();
                transaction.setCategory(category);
                transaction.setUser(user);
                transaction.setSubcategory(subcategory);
                transaction.setAmount(request.getAmount());
                transaction.setDate(LocalDate.now());

                return transactionRepository.save(transaction);
            }
        }
    }

    public Transaction editTransaction(TransactionEditDTO request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        String token = jwtUtil.extractToken(httpRequest);

        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (bindingResult.hasErrors()){
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        } else {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

            Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));

            Transaction transaction = transactionRepository.findByIdAndUser(request.getId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found or not authorized"));

            transaction.setAmount(request.getAmount());
            transaction.setCategory(category);
            transaction.setSubcategory(subcategory);
            transaction.setUser(user);

            return transactionRepository.save(transaction);
        }
    }

    public void deleteTransaction(TransactionDeleteDTO request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        String token = jwtUtil.extractToken(httpRequest);
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (bindingResult.hasErrors()){
            throw new HandleHttpErrors("Validation errors occurred", bindingResult);
        } else {

            Transaction transaction = transactionRepository.findByIdAndUser(request.getId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found or not authorized"));

            transactionRepository.delete(transaction);
        }
    }
}
