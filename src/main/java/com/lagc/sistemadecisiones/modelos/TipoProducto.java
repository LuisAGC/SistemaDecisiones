package com.lagc.sistemadecisiones.modelos;

public enum TipoProducto {
    PROCESADOR("Procesador"),
    MEMORIA_RAM("Memoria RAM"),
    TARJETA_BASE("Tarjeta Base"),
    DISCO_DURO("Disco Duro"),
    FUENTE_DE_PODER("Fuente de Poder"),
    TARJETA_DE_VIDEO("Tarjeta de Video");

    public final String label;

    private TipoProducto(String label){
        this.label = label;
    }
}
