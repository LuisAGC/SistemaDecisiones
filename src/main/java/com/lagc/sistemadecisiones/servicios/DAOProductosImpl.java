package com.lagc.sistemadecisiones.servicios;

import com.lagc.sistemadecisiones.modelos.Producto;
import com.lagc.sistemadecisiones.modelos.TipoProducto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOProductosImpl implements DAOProductos{

    Connection conn;

    public DAOProductosImpl() throws SQLException {
        String url = "jdbc:sqlite:productos.db";
        conn = DriverManager.getConnection(url);
    }

    @Override
    public List<Producto> obtenerProductos() throws SQLException {
        String sql = "SELECT tipo, nombre, precio, tienda FROM Productos";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Producto> productos = new ArrayList<>();

        while(rs.next()) {
            TipoProducto tipoProducto = switch (rs.getString("tipo")){
                case "Procesador" -> TipoProducto.PROCESADOR;
                case "Memoria RAM" -> TipoProducto.MEMORIA_RAM;
                case "Tarjeta base" -> TipoProducto.TARJETA_BASE;
                case "Disco duro" -> TipoProducto.DISCO_DURO;
                case "Fuente de poder" -> TipoProducto.FUENTE_DE_PODER;
                case "Tarjeta de video" -> TipoProducto.TARJETA_DE_VIDEO;
                default -> TipoProducto.PROCESADOR;
            };
            Producto producto = new Producto(tipoProducto, rs.getString("nombre"), rs.getDouble("precio"), rs.getString("tienda"));
            productos.add(producto);
        }

        return productos;
    }

    @Override
    public Optional<Producto> guardarProducto(Producto producto) {
        String sql = "INSERT INTO Productos (tipo, nombre, precio, tienda) VALUES (?,?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.tipoProducto().label);
            stmt.setString(2, producto.nombreProducto());
            stmt.setDouble(3, producto.precio());
            stmt.setString(4, producto.nombreTienda());
            stmt.executeUpdate();
            return Optional.of(producto);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
