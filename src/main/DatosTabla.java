package main;

public class DatosTabla {
    private int id; //A buscar en Palabras Reservadas private String tipo; private String uso;
    private String tipo;
    private String uso;

    DatosTabla(int id) {
        this.id = id;
        this.tipo = null;
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