package com.lagc.sistemadecisiones;

import com.google.inject.Inject;
import com.lagc.sistemadecisiones.modelos.Producto;
import com.lagc.sistemadecisiones.modelos.TipoProducto;
import com.lagc.sistemadecisiones.servicios.ServicioProductos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.util.Optional;

public class AddProductController {

    @FXML
    ListView<TipoProducto> listViewTipoProducto;

    @FXML
    TextField textNombreProducto;

    @FXML
    Spinner<Double> spinnerPrecio;

    @FXML
    TextField textTienda;

    @Inject
    ServicioProductos servicioProductos;


    @FXML
    public void initialize(){
        listViewTipoProducto.setCellFactory(tipoProductoListView -> new ListCell<>(){
            @Override
            public void updateItem(TipoProducto tipoProducto, boolean empty){
                super.updateItem(tipoProducto, empty);
                if (empty || tipoProducto == null){
                    setText(null);
                }
                else {
                    setText(tipoProducto.label);
                }
            }
        });
        listViewTipoProducto.setItems(FXCollections.observableArrayList(
                TipoProducto.PROCESADOR,
                TipoProducto.MEMORIA_RAM,
                TipoProducto.TARJETA_BASE,
                TipoProducto.DISCO_DURO,
                TipoProducto.FUENTE_DE_PODER,
                TipoProducto.TARJETA_DE_VIDEO
                ));

        spinnerPrecio.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0));

    }

    @FXML
    private void agregarActionHandler(ActionEvent event){
        event.consume();
        String name = textNombreProducto.getText().trim();
        TipoProducto tipoProducto = listViewTipoProducto.getSelectionModel().getSelectedItem();
        double precio = spinnerPrecio.getValue();
        String tienda = textTienda.getText().trim();

        if(name == null || tipoProducto == null || tienda == null){
            Notifications.create()
                    .title("Valor vacio!")
                    .text("Uno de los campos esta vacio o no ha seleccionado algun tipo de producto!")
                    .showWarning();
        }
        else {
            Producto producto = new Producto(tipoProducto, name, precio, tienda);
            Optional<Producto> productoOptional = servicioProductos.agregarProducto(producto);
            if (!productoOptional.isPresent()){
                Notifications.create()
                        .title("Error!")
                        .text("Ocurrio un error al tratar de guardar el producto!")
                        .showError();
            }
            else{
                Notifications.create()
                        .title("Producto guardado!")
                        .text("El producto fue guardado exitosamente!")
                        .showConfirm();
            }
        }


    }

}
