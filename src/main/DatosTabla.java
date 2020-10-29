package main;

public class DatosTabla {
    private int id; //A buscar en Palabras Reservadas private String tipo; private String uso;
    private String tipo;
    private String uso;
    private boolean declarada;

    public DatosTabla() {
        this.id = 0;
        this.tipo = null;
        this.uso = null;
        this.declarada = false;
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
}