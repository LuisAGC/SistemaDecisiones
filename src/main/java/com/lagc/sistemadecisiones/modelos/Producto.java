package com.lagc.sistemadecisiones.modelos;

public record Producto(
        TipoProducto tipoProducto,
        String nombreProducto,
        double precio,
        String nombreTienda) {
}
