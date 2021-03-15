package com.tronxi.maitre.kata.demo.infrastructure.persistence;

import com.tronxi.maitre.kata.demo.domain.model.Restaurant;
import com.tronxi.maitre.kata.demo.domain.ports.secondary.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RestaurantRepositoryAdapter implements RestaurantRepository {

    @Override
    public Restaurant find() {
        return new Restaurant(Collections.emptyMap());
    }

    @Override
    public void save(Restaurant restaurant) {

    }
}
