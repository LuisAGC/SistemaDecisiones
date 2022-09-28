package com.lagc.sistemadecisiones.servicios;

import com.lagc.sistemadecisiones.modelos.Producto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAOProductos {
    public List<Producto> obtenerProductos() throws SQLException;
    public Optional<Producto> guardarProducto(Producto producto);
}
