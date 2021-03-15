package com.tronxi.maitre.kata.demo.domain.usecases;

import com.tronxi.maitre.kata.demo.domain.model.*;
import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableOrder;
import com.tronxi.maitre.kata.demo.domain.model.reservetable.ReserveTableResult;
import com.tronxi.maitre.kata.demo.domain.ports.secondary.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        ReserveTableOrder reserve = new ReserveTableOrder(LocalDate.of(2015, 10, 15), 5);

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        assertEquals(ReserveTableResult.REJECTED, reserveTableResult);
    }

    @Test
    public void shouldRejectWhenRestaurantHasNotTableForReservationDate() {
        //GIVEN
        ReserveTableOrder reserve = new ReserveTableOrder(LocalDate.of(2099, 10, 15), 5);
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

        Map<LocalDate, Tables> tables = new HashMap<>();
        tables.put(date, new Tables(Arrays.asList(new Table(4))));

        ReserveTableOrder reserve = new ReserveTableOrder(date, 5);
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

        Map<LocalDate, Tables> tables = new HashMap<>();
        tables.put(date, new Tables(Arrays.asList(new Table(5))));
        Restaurant restaurant = new Restaurant(tables);

        ReserveTableOrder reserve = new ReserveTableOrder(date, 5);
        when(restaurantRepository.find()).thenReturn(restaurant);

        //WHEN
        ReserveTableResult reserveTableResult = reserveTableUseCase.reserve(reserve);

        //THEN
        //assertTrue(restaurant.getTables().get(date).getTables().get(0).isReserved());
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult);
    }

    @Test
    public void shouldReserveTableWithBestCapacity() {
        //GIVEN
        LocalDate date = LocalDate.of(2099, 10, 15);

        Map<LocalDate, Tables> tables = new HashMap<>();
        tables.put(date, new Tables(Arrays.asList(new Table(5), new Table(4))));
        Restaurant restaurant = new Restaurant(tables);

        ReserveTableOrder reserve1 = new ReserveTableOrder(date, 4);
        ReserveTableOrder reserve2 = new ReserveTableOrder(date, 5);
        when(restaurantRepository.find()).thenReturn(restaurant);

        //WHEN
        ReserveTableResult reserveTableResult1 = reserveTableUseCase.reserve(reserve1);
        ReserveTableResult reserveTableResult2 = reserveTableUseCase.reserve(reserve2);

        //THEN
        //assertFalse(restaurant.getTables().get(date).getTables().get(0).isReserved());
        //assertTrue(restaurant.getTables().get(date).getTables().get(1).isReserved());
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult1);
        assertEquals(ReserveTableResult.ACCEPTED, reserveTableResult2);
    }

}