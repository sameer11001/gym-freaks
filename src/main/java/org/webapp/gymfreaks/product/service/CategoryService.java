package org.webapp.gymfreaks.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.product.model.Category;
import org.webapp.gymfreaks.product.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByCategoryName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
        if (!category.isPresent()) {
            throw new RuntimeException();
        }
        return category.get();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

}
