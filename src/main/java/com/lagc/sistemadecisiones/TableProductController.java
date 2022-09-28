package com.lagc.sistemadecisiones;

import com.google.inject.Inject;
import com.lagc.sistemadecisiones.modelos.Producto;
import com.lagc.sistemadecisiones.modelos.TipoProducto;
import com.lagc.sistemadecisiones.modelos.TipoProductoAgregado;
import com.lagc.sistemadecisiones.servicios.ServicioProductos;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableProductController {
    private ObservableList<Producto> observableProductosList;
    private List<TipoProductoAgregado> tipoProductoAgregadoList;

    @FXML
    TableView<Producto> tablaProductos;

    @FXML
    ComboBox<Producto> comboProcesador;

    @FXML
    ComboBox<Producto> comboRam;

    @FXML
    ComboBox<Producto> comboTarjetaBase;

    @FXML
    ComboBox<Producto> comboDiscoDuro;

    @FXML
    ComboBox<Producto> comboFuentePoder;

    @FXML
    ComboBox<Producto> comboTarjetaVideo;

    @FXML
    CheckBox checkBoxProcesador;

    @FXML
    CheckBox checkBoxRam;

    @FXML
    CheckBox checkBoxTarjetaBase;

    @FXML
    CheckBox checkBoxDiscoDuro;

    @FXML
    CheckBox checkBoxFuentePoder;

    @FXML
    CheckBox checkBoxTarjetaVideo;

    @FXML
    CheckBox checkBoxMayorCosto;

    @FXML
    CheckBox checkBoxMenorCosto;

    @FXML
    CheckBox checkBoxMayorCostoTienda;

    @FXML
    CheckBox checkBoxMenorCostoTienda;

    @FXML
    CheckBox checkBoxProductosEspecificos;

    @Inject
    ServicioProductos servicioProductos;

    @FXML
    public void initialize() {
        try {
            observableProductosList = servicioProductos.obtenerProductos();
            tablaProductos.setItems(observableProductosList);
            initializeTablaProductos();
            initializeComboBoxes(observableProductosList);
            tipoProductoAgregadoList = new ArrayList<>();
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.PROCESADOR, checkBoxProcesador, comboProcesador));
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.MEMORIA_RAM, checkBoxRam, comboRam));
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.TARJETA_BASE, checkBoxTarjetaBase, comboTarjetaBase));
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.DISCO_DURO, checkBoxDiscoDuro, comboDiscoDuro));
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.FUENTE_DE_PODER, checkBoxFuentePoder, comboDiscoDuro));
            tipoProductoAgregadoList.add(new TipoProductoAgregado(TipoProducto.TARJETA_DE_VIDEO, checkBoxTarjetaVideo, comboTarjetaVideo));
        } catch (SQLException e) {
            observableProductosList = FXCollections.emptyObservableList();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error!");
            alert.setContentText("Ocurrio un error al tratar de leer la base de datos \"productos.db\"");
            alert.showAndWait();
            Notifications.create()
                    .title("Error")
                    .text("Ocurrio un error al tratar de leer la base de datos \"productos.db\"")
                    .showError();
        }
    }

    private Predicate<Producto> distinctProductNameByType(TipoProducto tipoProducto){
        Set<String> seen = ConcurrentHashMap.newKeySet();
        return producto -> seen.add(producto.nombreProducto()) && producto.tipoProducto().equals(tipoProducto);
    }

    private ListCell<Producto> getListCell(){
        return new ListCell<Producto>(){
            @Override
            protected void updateItem(Producto producto, boolean empty){
                super.updateItem(producto, empty);
                if (producto == null || empty) {
                    setText(null);
                } else {
                    setText(producto.nombreProducto());
                }
            }
        };
    }
    private void initializeComboBoxes(ObservableList<Producto> productos){
        comboProcesador.setCellFactory(listView -> getListCell());
        comboRam.setCellFactory(listView -> getListCell());
        comboTarjetaBase.setCellFactory(listView -> getListCell());
        comboDiscoDuro.setCellFactory(listView -> getListCell());
        comboFuentePoder.setCellFactory(listView -> getListCell());
        comboTarjetaVideo.setCellFactory(listView -> getListCell());

        comboProcesador.setButtonCell(getListCell());
        comboRam.setButtonCell(getListCell());
        comboTarjetaBase.setButtonCell(getListCell());
        comboDiscoDuro.setButtonCell(getListCell());
        comboFuentePoder.setButtonCell(getListCell());
        comboTarjetaVideo.setButtonCell(getListCell());

        FilteredList<Producto> filteredProcesador = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.PROCESADOR));
        FilteredList<Producto> filteredRam = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.MEMORIA_RAM));
        FilteredList<Producto> filteredTarjetaBase = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.TARJETA_BASE));
        FilteredList<Producto> filteredDiscoDuro = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.DISCO_DURO));
        FilteredList<Producto> filteredFuentePoder = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.FUENTE_DE_PODER));
        FilteredList<Producto> filteredTarjetaVideo = new FilteredList<>(productos, distinctProductNameByType(TipoProducto.TARJETA_DE_VIDEO));

        comboProcesador.setItems(filteredProcesador);
        comboRam.setItems(filteredRam);
        comboTarjetaBase.setItems(filteredTarjetaBase);
        comboDiscoDuro.setItems(filteredDiscoDuro);
        comboFuentePoder.setItems(filteredFuentePoder);
        comboTarjetaVideo.setItems(filteredTarjetaVideo);

        comboProcesador.getSelectionModel().selectFirst();
        comboRam.getSelectionModel().selectFirst();
        comboTarjetaBase.getSelectionModel().selectFirst();
        comboDiscoDuro.getSelectionModel().selectFirst();
        comboFuentePoder.getSelectionModel().selectFirst();
        comboTarjetaVideo.getSelectionModel().selectFirst();
    }

    private void initializeTablaProductos(){
        TableColumn<Producto, String> tipoProductoColumna = new TableColumn<>("Tipo de Producto");
        tipoProductoColumna.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().tipoProducto().label)
        );

        TableColumn<Producto, String> nombreProductoColumna = new TableColumn<>("Nombre");
        nombreProductoColumna.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().nombreProducto())
        );

        TableColumn<Producto, Double> precioProductoColumna = new TableColumn<>("Precio");
        precioProductoColumna.setCellValueFactory(
                p -> new SimpleDoubleProperty(p.getValue().precio()).asObject()
        );

        TableColumn<Producto, String> nombreTiendaColumna = new TableColumn<>("Tienda");
        nombreTiendaColumna.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().nombreTienda())
        );

        tablaProductos.getColumns().setAll(tipoProductoColumna, nombreProductoColumna, precioProductoColumna, nombreTiendaColumna);
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    @FXML
    private void generarActionHandler(ActionEvent event){
        event.consume();
        ObservableList<Producto> productos = tablaProductos.getItems();
        try(PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("resultado.txt")))){
            output.println("------------------------------------------------------------------------");
            if(checkBoxMayorCosto.isSelected()) {
                printHighestPricedProducts(output, productos);
            }
            output.println("------------------------------------------------------------------------");
            if(checkBoxMenorCosto.isSelected()) {
                printLowestPricedProducts(output, productos);
            }
            output.println("------------------------------------------------------------------------");
            if(checkBoxMayorCostoTienda.isSelected()) {
                printHighestPricesByStore(output, productos);
            }
            output.println("------------------------------------------------------------------------");
            if(checkBoxMenorCostoTienda.isSelected()) {
                printLowestPricesByStore(output, productos);
            }
            output.println("------------------------------------------------------------------------");
            if(checkBoxProductosEspecificos.isSelected()) {
                printLowestPricesBySpecificProductName(output, productos);
            }
            output.flush();
            Notifications.create()
                    .title("Archivo generado!")
                    .text("El archivo de texto \"resultado.txt\" fue generado correctamente")
                    .showInformation();
        }
        catch (IOException e) {
            Notifications.create()
                    .title("Error!")
                    .text("Ocurrio un error al tratar de escribir el archivo resultado.txt")
                    .showError();
        }
    }

    private void printProduct(PrintWriter writer, Optional<Producto> productoOptional){
        if(productoOptional.isPresent()){
            Producto producto = productoOptional.get();
            writer.printf("%s: %s Precio: %.2f Tienda: %s\n",
                    producto.tipoProducto().label,
                    producto.nombreProducto(),
                    producto.precio(),
                    producto.nombreTienda()
            );
        }
    }

    private Optional<Producto> selectHighestPriceByProduct(ObservableList<Producto> productos, CheckBox checkBox, TipoProducto tipo) {
        return productos.stream()
                .filter(p -> checkBox.isSelected())
                .filter(p -> p.tipoProducto().equals(tipo))
                .max(Comparator.comparingDouble(Producto::precio));
    }

    private Optional<Producto> selectLowestPriceByProduct(ObservableList<Producto> productos, CheckBox checkBox, TipoProducto tipo) {
        return productos.stream()
                .filter(p -> checkBox.isSelected())
                .filter(p -> p.tipoProducto().equals(tipo))
                .min(Comparator.comparingDouble(Producto::precio));
    }

    private Optional<Producto> selectLowestPriceBySpecificProductName(ObservableList<Producto> productos, CheckBox checkBox, TipoProducto tipo, String nombre) {
        if(nombre == null || nombre.isBlank()){
            return Optional.empty();
        }
        return productos.stream()
                .filter(p -> checkBox.isSelected())
                .filter(p -> p.tipoProducto().equals(tipo))
                .filter(p -> p.nombreProducto().equals(nombre))
                .min(Comparator.comparingDouble(Producto::precio));
    }

    private void printHighestPricedProducts(PrintWriter writer, ObservableList<Producto> productos){
        tipoProductoAgregadoList.stream()
                .forEach(pa -> printProduct(writer,
                        selectHighestPriceByProduct(productos, pa.checkBox(), pa.tipoProducto())));
    }

    private void printLowestPricedProducts(PrintWriter writer, ObservableList<Producto> productos){
        tipoProductoAgregadoList.stream()
                .forEach(pa -> printProduct(writer,
                        selectLowestPriceByProduct(productos, pa.checkBox(), pa.tipoProducto())));
    }

    private double sumHighestPricedProducts(ObservableList<Producto> productos){
        List<Optional<Producto>> sumStream = new ArrayList<>();
        tipoProductoAgregadoList.stream()
                .forEach(pa ->  sumStream.add(selectHighestPriceByProduct(productos, pa.checkBox(), pa.tipoProducto())));
        return sumStream.stream().flatMap(Optional::stream).mapToDouble(p -> p.precio()).reduce(Double::sum).orElse(0.0);
    }

    private double sumLowestPricedProducts(ObservableList<Producto> productos){
        List<Optional<Producto>> sumStream = new ArrayList<>();
        tipoProductoAgregadoList.stream()
                .forEach(pa ->  sumStream.add(selectLowestPriceByProduct(productos, pa.checkBox(), pa.tipoProducto())));
        return sumStream.stream().flatMap(Optional::stream).mapToDouble(p -> p.precio()).reduce(Double::sum).orElse(0.0);
    }

    private void printHighestPricesByStore(PrintWriter writer, ObservableList<Producto> productos){
        List<String> tiendas = productos.stream()
                .map(p -> p.nombreTienda())
                .distinct()
                .sorted(String::compareTo)
                .toList();
        for(String tienda : tiendas){
            ObservableList<Producto> productosTienda = productos.stream()
                    .filter(p -> p.nombreTienda().equals(tienda))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            writer.println("**************************************************************");
            writer.println(tienda);
            printHighestPricedProducts(writer, productosTienda);
            writer.printf("Costo total: %.2f\n", sumHighestPricedProducts(productosTienda));
        }
    }

    private void printLowestPricesByStore(PrintWriter writer, ObservableList<Producto> productos){
        List<String> tiendas = productos.stream()
                .map(p -> p.nombreTienda())
                .distinct()
                .sorted(String::compareTo)
                .toList();
        for(String tienda : tiendas){
            ObservableList<Producto> productosTienda = productos.stream()
                    .filter(p -> p.nombreTienda().equals(tienda))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            writer.println("**************************************************************");
            writer.println(tienda);
            printLowestPricedProducts(writer, productosTienda);
            writer.printf("Costo total: %.2f\n", sumLowestPricedProducts(productosTienda));
        }
    }

    private void printLowestPricesBySpecificProductName(PrintWriter writer, ObservableList<Producto> productos){
        tipoProductoAgregadoList.stream().forEach(pa -> printProduct(writer,
                selectLowestPriceBySpecificProductName(
                        productos, pa.checkBox(), pa.tipoProducto(), pa.comboBox().getSelectionModel().getSelectedItem().nombreProducto())));
    }
}
