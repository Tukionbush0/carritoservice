package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCarrito_ShouldReturnAllItems() {
        // Arrange
        Carrito item1 = new Carrito(1L, 101L, 2, 19.99);
        Carrito item2 = new Carrito(2L, 102L, 1, 29.99);
        List<Carrito> expectedItems = Arrays.asList(item1, item2);

        when(carritoService.listar()).thenReturn(expectedItems);

        // Act
        List<Carrito> actualItems = carritoController.listarCarrito();

        // Assert
        assertEquals(expectedItems, actualItems);
        verify(carritoService, times(1)).listar();
    }

    @Test
    void agregarItem_ShouldReturnSavedItem() {
        // Arrange
        Carrito newItem = new Carrito(1, 103L, 3, 9.99);
        Carrito savedItem = new Carrito(3L, 103L, 3, 9.99);

        when(carritoService.addItem(newItem)).thenReturn(savedItem);

        // Act
        Carrito result = carritoController.agregarItem(newItem);

        // Assert
        assertEquals(savedItem, result);
        verify(carritoService, times(1)).addItem(newItem);
    }

    @Test
    void eliminarItem_ShouldCallService() {
        // Arrange
        Long itemId = 1L;

        // Act
        carritoController.eliminarItem(itemId);

        // Assert
        verify(carritoService, times(1)).removeItem(itemId);
    }

    @Test
    void limpiarCarrito_ShouldCallService() {
        // Act
        carritoController.limpiarCarrito();

        // Assert
        verify(carritoService, times(1)).clearCarrito();
    }
}