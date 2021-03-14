package com.tronxi.maitre.kata.demo.domain.usecases;

import com.tronxi.maitre.kata.demo.domain.model.Reserve;
import com.tronxi.maitre.kata.demo.domain.model.ReserveTableResult;
import com.tronxi.maitre.kata.demo.domain.model.Restaurant;
import com.tronxi.maitre.kata.demo.domain.model.Table;
import com.tronxi.maitre.kata.demo.domain.ports.secondary.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReserveTableUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReserveTableUseCase reserveTableUseCase;

    @Test
    public void shouldRejectWhenReservationDateIsInvalid() {
        //GIVEN
        Reserve reserve = new Reserve(LocalDate.of(2015, 10, 15), 5);

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        assertEquals(ReserveTableResult.REJECTED, reserveTableResult);
    }

    @Test
    public void shouldRejectWhenRestaurantHasNotTableForReservationDate() {
        //GIVEN
        Reserve reserve = new Reserve(LocalDate.of(2099, 10, 15), 5);
        when(restaurantRepository.find()).thenReturn(new Restaurant(Collections.emptyMap()));

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        assertEquals(ReserveTableResult.REJECTED, reserveTableResult);
    }

    @Test
    public void shouldRejectWhenRestaurantHasNotAvailableTableForReservationDate() {
        //GIVEN
        LocalDate date = LocalDate.of(2099, 10, 15);

        Map<LocalDate, Table> tables = new HashMap<>();
        tables.put(date, new Table(4));

        Reserve reserve = new Reserve(date, 5);
        when(restaurantRepository.find()).thenReturn(new Restaurant(tables));

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        assertEquals(ReserveTableResult.REJECTED, reserveTableResult);
    }

    @Test
    public void shouldAcceptWhenRestaurantHasAvailableTableForReservationDate() {
        //GIVEN
        LocalDate date = LocalDate.of(2099, 10, 15);

        Map<LocalDate, Table> tables = new HashMap<>();
        tables.put(date, new Table(5));
        Restaurant restaurant = new Restaurant(tables);

        Reserve reserve = new Reserve(date, 5);
        when(restaurantRepository.find()).thenReturn(restaurant);

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        verify(restaurantRepository).save(restaurant);
        assertEquals(0, restaurant.getTables().get(date).getCapacity());
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult);
    }

    @Test
    public void shouldAcceptWhenRestaurantHasAvailableTableForTwoReservationDate() {
        //GIVEN
        LocalDate date = LocalDate.of(2099, 10, 15);

        Map<LocalDate, Table> tables = new HashMap<>();
        tables.put(date, new Table(11));
        Restaurant restaurant = new Restaurant(tables);

        Reserve reserve = new Reserve(date, 5);
        when(restaurantRepository.find()).thenReturn(restaurant);

        //WHEN
        ReserveTableResult reserveTableResult1 = reserveTableUseCase.reserve(reserve);
        ReserveTableResult reserveTableResult2 = reserveTableUseCase.reserve(reserve);

        //THEN
        assertEquals(1, restaurant.getTables().get(date).getCapacity());
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult1);
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult2);
    }

}