package com.tronxi.maitre.kata.demo.domain.model;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class Tables {
    private final List<Table> tables;

    public boolean hasAnyWithCapacity(Integer capacity) {
         return tables.stream()
                 .filter(table -> !table.isReserved())
                 .anyMatch(table -> table.hasCapacity(capacity));
    }

    public void reserve(Reserve reserve) {
        tables.stream()
                .filter(table -> !table.isReserved())
                .filter(table -> table.hasCapacity(reserve.amount()))
                .sorted(Comparator.comparingInt(Table::capacity))
                .findFirst()
                .ifPresent(Table::reserve);
    }
}
