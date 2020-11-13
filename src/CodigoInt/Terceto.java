package CodigoInt;

public class Terceto {
    private int numero;
    private String op1;
    private String op2;
    private String operador;
    private String resultado;
    private String tipo;

    public Terceto(String operador, String op1, String op2) {
        this.operador = operador;
        this.op1 = op1;
        this.op2 = op2;
        this.resultado = null;
        this.tipo = null;
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

    public int nroTerceto(int op){
        if(op == 1) {
            if (op1.contains("["))
                return Integer.parseInt(op1.substring(1,op1.lastIndexOf("]")));
        }
        else
            if (op2.contains("["))
                return Integer.parseInt(op2.substring(1,op2.lastIndexOf("]")));
        return -1;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
