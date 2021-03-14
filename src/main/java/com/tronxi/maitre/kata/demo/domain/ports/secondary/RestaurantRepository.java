package com.tronxi.maitre.kata.demo.domain.ports.secondary;


import com.tronxi.maitre.kata.demo.domain.model.Restaurant;

public interface RestaurantRepository {
    Restaurant find();
    void save(Restaurant restaurant);
}
