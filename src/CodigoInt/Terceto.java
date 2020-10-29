package CodigoInt;

public class Terceto {
    private int numero;
    private String op1;
    private String op2;
    private String operador;
    //private TablaSimbolos tablaSimbolos;
    private String tipoSalto;

    public Terceto(String operador, String op1, String op2) {
        this.operador = operador;
        this.op1 = op1;
        this.op2 = op2;
        //tablaSimbolos=null;
        //tipoSalto=null;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int i){
        this.numero = i;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOperador() {
        return operador;
    }
}
