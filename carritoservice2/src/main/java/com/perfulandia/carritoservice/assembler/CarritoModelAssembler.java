package com.perfulandia.carritoservice.assembler;

import com.perfulandia.carritoservice.controller.CarritoController;
import com.perfulandia.carritoservice.model.Carrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito item) {
        return EntityModel.of(item,
                linkTo(methodOn(CarritoController.class).eliminarItem(item.getId())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).listarCarrito()).withRel("carrito"),
                linkTo(methodOn(CarritoController.class).agregarItem(item)).withRel("add"),
                linkTo(methodOn(CarritoController.class).eliminarItem(item.getId())).withRel("delete"),
                linkTo(methodOn(CarritoController.class).limpiarCarrito()).withRel("clear")
        );
    }
}