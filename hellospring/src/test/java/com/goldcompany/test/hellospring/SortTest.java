package com.goldcompany.test.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {
    Sort sort;

    @BeforeEach
    void beforeEach() {
        sort = new Sort();
    }

    @Test
    void sort() {
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sortBy3Items() {
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b", "ccc"));
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
