package com.example.spring_test.test.ingredient;

import com.example.spring_test.test.design.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
}
