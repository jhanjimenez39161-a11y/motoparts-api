package com.motoparts.api.dto;

public record RepuestoRequestDTO(
    String nombre,
    String categoria,
    Double precio,
    Integer stock
) {}