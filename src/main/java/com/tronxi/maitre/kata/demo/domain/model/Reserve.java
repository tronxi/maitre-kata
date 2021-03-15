package com.tronxi.maitre.kata.demo.domain.model;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class Reserve {
    private final LocalDate date;
    private final Integer amount;

    public Integer amount() {
        return this.amount;
    }

    public LocalDate date() {
        return this.date;
    }

    public boolean isInvalidDate() {
        return this.date.isBefore(LocalDate.now());
    }
}
