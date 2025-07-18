package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.assembler.CarritoModelAssembler;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito de Compras", description = "API para gestión de items en el carrito de compras")
public class CarritoController {

    private final CarritoService carritoService;
    private final CarritoModelAssembler assembler;

    public CarritoController(CarritoService carritoService, CarritoModelAssembler assembler) {
        this.carritoService = carritoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los ítems del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public CollectionModel<EntityModel<Carrito>> listarCarrito() {
        List<EntityModel<Carrito>> items = carritoService.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(items,
                linkTo(methodOn(CarritoController.class).listarCarrito()).withSelfRel()
        );
    }

    @Operation(summary = "Agregar un ítem al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ítem agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del ítem inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Carrito>> agregarItem(@RequestBody Carrito item) {
        Carrito savedItem = carritoService.addItem(item);
        EntityModel<Carrito> entityModel = assembler.toModel(savedItem);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Eliminar un ítem específico del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ítem eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        carritoService.removeItem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Limpiar el carrito completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito vaciado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping
    public ResponseEntity<Void> limpiarCarrito() {
        carritoService.clearCarrito();
        return ResponseEntity.noContent().build();
    }
}