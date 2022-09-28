module com.lagc.sistemadecisiones {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.guice;

    opens com.lagc.sistemadecisiones;
    exports com.lagc.sistemadecisiones;
    exports com.lagc.sistemadecisiones.dependencyinjection;
    exports com.lagc.sistemadecisiones.servicios;
    opens com.lagc.sistemadecisiones.dependencyinjection;
    opens com.lagc.sistemadecisiones.servicios;
}