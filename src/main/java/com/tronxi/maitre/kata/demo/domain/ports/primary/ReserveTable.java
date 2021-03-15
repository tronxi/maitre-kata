package com.tronxi.maitre.kata.demo.domain.ports.primary;


import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableOrder;
import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableResult;

public interface ReserveTable {
    ReserveTableResult reserve(ReserveTableOrder reserveTableOrder);
}
