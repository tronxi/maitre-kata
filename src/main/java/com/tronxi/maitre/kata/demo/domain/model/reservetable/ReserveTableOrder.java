package com.tronxi.maitre.kata.demo.domain.model.reservetable;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReserveTableOrder {
    private final LocalDate date;
    private final Integer amount;
}
