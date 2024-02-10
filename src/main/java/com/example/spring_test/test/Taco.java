package com.example.spring_test.test;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.spring_test.test.design.Ingredient;

import lombok.Data;

@Data
public class Taco {

    private Long id;
    private Date createdAt;
    
    @NotNull
    @NotBlank
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}
