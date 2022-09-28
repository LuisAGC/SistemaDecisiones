package com.lagc.sistemadecisiones.servicios;

import com.google.inject.Inject;
import com.lagc.sistemadecisiones.modelos.Producto;
import com.lagc.sistemadecisiones.modelos.TipoProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicioProductosImpl implements ServicioProductos {

    private ObservableList<Producto> productoObservableList;

    @Inject
    private DAOProductos daoProductos;


    @Override
    public ObservableList<Producto> obtenerProductos() throws SQLException {
        if(productoObservableList == null){
            productoObservableList = daoProductos.obtenerProductos().stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return productoObservableList;
    }

    @Override
    public Optional<Producto> agregarProducto(Producto producto) {
        Optional<Producto> optionalProducto = daoProductos.guardarProducto(producto);
        if(optionalProducto.isPresent()){
            productoObservableList.add(optionalProducto.get());
            return optionalProducto;
        }
        else {
            return Optional.empty();
        }
    }

}
