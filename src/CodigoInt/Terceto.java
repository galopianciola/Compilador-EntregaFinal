package CodigoInt;

public class Terceto {
    private int numero;
    private String op1;
    private String op2;
    private String operador;
    private String resultado;

    public Terceto(String operador, String op1, String op2) {
        this.operador = operador;
        this.op1 = op1;
        this.op2 = op2;
        this.resultado = null;
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

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public boolean esVariable(int op){
        if(op == 1)
            return !this.op1.contains("[");
        else
            return !this.op2.contains("[");
    }
}
