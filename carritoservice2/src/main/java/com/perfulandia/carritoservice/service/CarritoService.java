package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class CarritoService {
    private final CarritoRepository repo;

    public CarritoService(CarritoRepository repo) {
        this.repo = repo;
    }

    // Lista carrito
    public List<Carrito> listar() {
        return repo.findAll();
    }

    // Guardar
    public Carrito addItem(Carrito item) {
        return repo.save(item);
    }

    // Eliminar item
    public void removeItem(Long id) {
        repo.deleteById(id);
    }

    // Limpiar carrito
    public void clearCarrito() {
        repo.deleteAll();
    }
}