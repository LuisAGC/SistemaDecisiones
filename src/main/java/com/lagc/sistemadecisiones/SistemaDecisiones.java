package com.lagc.sistemadecisiones;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lagc.sistemadecisiones.dependencyinjection.SistemaDecisionesModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;

public class SistemaDecisiones extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Injector injector = Guice.createInjector(new SistemaDecisionesModule());

        FXMLLoader fxmlLoader = injector.getInstance(FXMLLoader.class);
        fxmlLoader.setLocation(getClass().getResource("/com/lagc/sistemadecisiones/main.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 835, 515);
        stage.setTitle("Sistema de decisiones");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}