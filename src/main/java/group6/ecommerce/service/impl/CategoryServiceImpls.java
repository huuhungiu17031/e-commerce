package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.CategoryRepository;
import group6.ecommerce.model.Category;
import group6.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpls implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
