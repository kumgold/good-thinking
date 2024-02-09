package com.example.spring_test.test;

import java.util.List;
import lombok.Data;

@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
