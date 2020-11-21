package main;

public class DatosTabla {
    private int id; //A buscar en Palabras Reservadas private String tipo; private String uso;
    private String tipo;
    private String uso;
    private boolean parametroRef;
    private int llamadosMax;
    private int llamadosActuales;
    private int orden;
    private int cantParametros;

    public DatosTabla() {
        this.id = 0;
        this.tipo = null;
        this.uso = null;
        this.parametroRef = false;
        this.llamadosMax = 0;
        this.llamadosActuales = 0;
        this.orden = -1;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public boolean isParametroRef() {
        return parametroRef;
    }

    public void setParametroRef(boolean parametroRef) {
        this.parametroRef = parametroRef;
    }

    public int getLlamadosMax() {
        return llamadosMax;
    }

    public void setLlamadosMax(int llamados) {
        this.llamadosMax = llamados;
    }

    public int getLlamadosActuales() { return llamadosActuales; }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getOrden() {
        return orden;
    }

    public void incrementarLlamados() {
        this.llamadosActuales++;
    }

    public int getCantParametros() {
        return cantParametros;
    }

    public void setCantParametros(int cantParametros) {
        this.cantParametros = cantParametros;
    }
}