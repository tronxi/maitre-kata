package com.tronxi.maitre.kata.demo.domain.model;

import lombok.Data;

@Data
public class Table {
    private final Integer capacity;

    public boolean hasCapacity(Integer capacity) {
        return this.capacity >= capacity;
    }
}
