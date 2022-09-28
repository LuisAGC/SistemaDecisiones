package com.lagc.sistemadecisiones.servicios;

import com.lagc.sistemadecisiones.modelos.Producto;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface ServicioProductos {

    public ObservableList<Producto> obtenerProductos() throws SQLException;
    public Optional<Producto> agregarProducto(Producto producto);

}
