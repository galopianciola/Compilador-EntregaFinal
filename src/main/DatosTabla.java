package main;

public class DatosTabla {
    private int id; //A buscar en Palabras Reservadas private String tipo; private String uso;
    private String tipo;
    private String uso;
    private boolean declarada;
    private boolean parametroRef;
    private String llamados;

    public DatosTabla() {
        this.id = 0;
        this.tipo = null;
        this.uso = null;
        this.declarada = false;
        this.parametroRef = false;
        this.llamados = "";
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

    public String getLlamados() {
        return llamados;
    }

    public void setLlamados(String llamados) {
        this.llamados = llamados;
    }
}