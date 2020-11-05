package main;

public class DatosTabla {
    private int id; //A buscar en Palabras Reservadas private String tipo; private String uso;
    private String tipo;
    private String uso;
    private boolean declarada;
    private boolean parametroRef;
    private int llamadosMax;
    private int llamadosActuales;
    private int orden;

    public DatosTabla() {
        this.id = 0;
        this.tipo = null;
        this.uso = null;
        this.declarada = false;
        this.parametroRef = false;
        this.llamadosMax = 0;
        this.llamadosActuales = 0;
        this.orden = -1;
    }

    public boolean isDeclarada() {
        return declarada;
    }

    public void setDeclarada(boolean declarada) {
        this.declarada = declarada;
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

    public void setLlamadosMax(String llamados) {
        this.llamadosMax = Integer.parseInt(llamados.substring(0, llamados.indexOf("_")));
    }

    public int getLlamadosActuales() { return llamadosActuales; }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getOrden() {
        return orden;
    }

    public void incremetarLlamados() {
        this.llamadosActuales++;
    }


}