public class Aposta {

    private int[] numerosApostados;
    private int registroAposta;
    private Apostador apostador; //cada aposta guarda um objeto apostador que contém nome, cpf e um ArrayList de apostas

    //Construtor da classe
    public Aposta(Apostador apostador, int[] numerosApostados, int registroAposta) {
        this.apostador = apostador;
        this.numerosApostados = numerosApostados;
        this.registroAposta = registroAposta;
    }

    //Getters
    public Apostador getApostador(){
        return apostador;
    }

    public int[] getNumerosApostados() {
        return numerosApostados;
    }

    public int getRegistroAposta() {
        return registroAposta;
    }
}
