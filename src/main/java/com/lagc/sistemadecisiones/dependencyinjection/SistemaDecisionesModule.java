package com.lagc.sistemadecisiones.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.lagc.sistemadecisiones.servicios.DAOProductos;
import com.lagc.sistemadecisiones.servicios.DAOProductosImpl;
import com.lagc.sistemadecisiones.servicios.ServicioProductos;
import com.lagc.sistemadecisiones.servicios.ServicioProductosImpl;
import javafx.fxml.FXMLLoader;

public class SistemaDecisionesModule extends AbstractModule {

    @Override
    protected void configure(){
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(ServicioProductos.class).to(ServicioProductosImpl.class).in(Singleton.class);
        bind(DAOProductos.class).to(DAOProductosImpl.class).in(Singleton.class);
    }

}
