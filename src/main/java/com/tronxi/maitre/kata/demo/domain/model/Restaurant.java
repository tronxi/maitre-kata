package com.tronxi.maitre.kata.demo.domain.model;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class Restaurant {
    private final Map<LocalDate, Tables> tables;

    public boolean isAvailability(Reserve reserve) {
        return Optional.ofNullable(tables.get(reserve.date()))
                .map(tables -> tables.hasAnyWithCapacity(reserve.amount()))
                .orElse(false);
    }

    public void reserve(Reserve reserve) {
        if(!isAvailability(reserve)) throw new IllegalStateException();

        tables.get(reserve.date()).reserve(reserve);
    }
}
