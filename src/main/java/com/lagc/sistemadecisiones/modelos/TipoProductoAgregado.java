package com.lagc.sistemadecisiones.modelos;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public record TipoProductoAgregado (TipoProducto tipoProducto, CheckBox checkBox, ComboBox<Producto> comboBox){}
