package com.tronxi.maitre.kata.demo.domain.usecases;

import com.tronxi.maitre.kata.demo.domain.model.Reserve;
import com.tronxi.maitre.kata.demo.domain.model.ReserveTableResult;
import com.tronxi.maitre.kata.demo.domain.model.Restaurant;
import com.tronxi.maitre.kata.demo.domain.ports.primary.ReserveTable;
import com.tronxi.maitre.kata.demo.domain.ports.secondary.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ReserveTableUseCase implements ReserveTable {

    private final RestaurantRepository restaurantRepository;

    @Override
    public ReserveTableResult reserve(Reserve reserve) {
        if(isInvalidDate(reserve)) return ReserveTableResult.REJECTED;

        Restaurant restaurant = restaurantRepository.find();
        if(!restaurant.isAvailability(reserve)) return ReserveTableResult.REJECTED;

        restaurant.reserve(reserve);
        restaurantRepository.save(restaurant);
        return ReserveTableResult.ACCEPTED;
    }

    private boolean isInvalidDate(Reserve reserve) {
        return reserve.getDate().isBefore(LocalDate.now());
    }
}
