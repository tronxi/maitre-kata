package com.tronxi.maitre.kata.demo.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Data
public class Restaurant {
    private final Map<LocalDate, Table> tables;

    public boolean isAvailability(Reserve reserve) {
        return Optional.ofNullable(tables.get(reserve.getDate()))
                .map(table -> table.hasCapacity(reserve.getAmount()))
                .orElse(false);
    }

    public void reserve(Reserve reserve) {
        if(!isAvailability(reserve)) throw new IllegalStateException();

        Table table = tables.get(reserve.getDate());
        Table newTable = new Table(table.getCapacity() - reserve.getAmount());
        tables.put(reserve.getDate(), newTable);
    }
}
