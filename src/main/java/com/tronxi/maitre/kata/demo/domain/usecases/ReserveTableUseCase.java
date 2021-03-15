package com.tronxi.maitre.kata.demo.domain.usecases;

import com.tronxi.maitre.kata.demo.domain.model.Reserve;
import com.tronxi.maitre.kata.demo.domain.model.Restaurant;
import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableOrder;
import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableResult;
import com.tronxi.maitre.kata.demo.domain.ports.primary.ReserveTable;
import com.tronxi.maitre.kata.demo.domain.ports.secondary.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReserveTableUseCase implements ReserveTable {

    private final RestaurantRepository restaurantRepository;

    @Override
    public ReserveTableResult reserve(ReserveTableOrder reserveTableOrder) {
        Reserve reserve = map(reserveTableOrder);
        if(reserve.isInvalidDate()) return ReserveTableResult.REJECTED;

        Restaurant restaurant = restaurantRepository.find();
        if(!restaurant.isAvailability(reserve)) return ReserveTableResult.REJECTED;

        restaurant.reserve(reserve);
        restaurantRepository.save(restaurant);
        return ReserveTableResult.ACCEPTED;
    }

    private Reserve map(ReserveTableOrder reserveTableOrder) {
        return new Reserve(reserveTableOrder.getDate(), reserveTableOrder.getAmount());
    }

}
