package com.tronxi.maitre.kata.demo.domain.ports.primary;


import com.tronxi.maitre.kata.demo.domain.model.Reserve;
import com.tronxi.maitre.kata.demo.domain.model.ReserveTableResult;

public interface ReserveTable {
    ReserveTableResult reserve(Reserve reserve);
}
