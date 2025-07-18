package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoServiceTest {

    @Mock
    private CarritoRepository repo;

    @InjectMocks
    private CarritoService carritoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar_ShouldReturnAllItems() {
        // Arrange
        Carrito item1 = new Carrito(1L, 101L, 2, 19.99);
        Carrito item2 = new Carrito(2L, 102L, 1, 29.99);
        List<Carrito> expectedItems = Arrays.asList(item1, item2);

        when(repo.findAll()).thenReturn(expectedItems);

        // Act
        List<Carrito> actualItems = carritoService.listar();

        // Assert
        assertEquals(expectedItems, actualItems);
        verify(repo, times(1)).findAll();
    }

    @Test
    void addItem_ShouldSaveItem() {
        // Arrange
        Carrito newItem = new Carrito(1, 103L, 3, 9.99);
        Carrito savedItem = new Carrito(3L, 103L, 3, 9.99);

        when(repo.save(newItem)).thenReturn(savedItem);

        // Act
        Carrito result = carritoService.addItem(newItem);

        // Assert
        assertEquals(savedItem, result);
        verify(repo, times(1)).save(newItem);
    }

    @Test
    void removeItem_ShouldDeleteItem() {
        // Arrange
        Long itemId = 1L;
        when(repo.existsById(itemId)).thenReturn(true);

        // Act
        carritoService.removeItem(itemId);

        // Assert
        verify(repo, times(1)).deleteById(itemId);
    }

    @Test
    void clearCarrito_ShouldDeleteAllItems() {
        // Act
        carritoService.clearCarrito();

        // Assert
        verify(repo, times(1)).deleteAll();
    }
}