package com.tronxi.maitre.kata.demo.domain.model;

public class Table {
    private final Integer capacity;
    private boolean isReserved;

    public Table(Integer capacity) {
        this.capacity = capacity;
        this.isReserved = false;
    }

    public boolean hasCapacity(Integer capacity) {
        return this.capacity >= capacity;
    }

    public void reserve() {
        this.isReserved = true;
    }

    public Integer capacity() {
        return this.capacity;
    }

    public boolean isReserved() {
        return this.isReserved;
    }
}
