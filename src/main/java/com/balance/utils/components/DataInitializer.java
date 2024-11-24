package com.balance.utils.components;

import com.balance.entity.Category;
import com.balance.entity.Subcategory;
import com.balance.repository.CategoryRepository;
import com.balance.repository.SubcategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public DataInitializer(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category incomeCategory = new Category();
            incomeCategory.setType(Category.CategoryType.INCOME);
            categoryRepository.save(incomeCategory);

            Category outcomeCategory = new Category();
            outcomeCategory.setType(Category.CategoryType.OUTCOME);
            categoryRepository.save(outcomeCategory);
        }

        if (subcategoryRepository.count() == 0) {
            Subcategory salarySubcategory = new Subcategory();
            salarySubcategory.setType(Subcategory.SubcategoryType.SALARY);
            subcategoryRepository.save(salarySubcategory);

            Subcategory restaurantsSubcategory = new Subcategory();
            restaurantsSubcategory.setType(Subcategory.SubcategoryType.RESTAURANTS);
            subcategoryRepository.save(restaurantsSubcategory);

            Subcategory marketsSubcategory = new Subcategory();
            marketsSubcategory.setType(Subcategory.SubcategoryType.MARKETS);
            subcategoryRepository.save(marketsSubcategory);

            Subcategory entertainmentSubcategory = new Subcategory();
            entertainmentSubcategory.setType(Subcategory.SubcategoryType.ENTERTAINMENT);
            subcategoryRepository.save(entertainmentSubcategory);

            Subcategory utilitiesSubcategory = new Subcategory();
            utilitiesSubcategory.setType(Subcategory.SubcategoryType.UTILITIES);
            subcategoryRepository.save(utilitiesSubcategory);
        }
    }
}