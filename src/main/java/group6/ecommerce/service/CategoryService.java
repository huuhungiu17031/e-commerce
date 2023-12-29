package group6.ecommerce.service;

import group6.ecommerce.model.Category;

public interface CategoryService {
    Category findCategoryByName(String name);
}
