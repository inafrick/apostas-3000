import java.util.ArrayList;

public class Apostador {
    private String nome;
    private String cpf;
    private ArrayList<Aposta> apostasApostador;

    //Construtor da classe apostador
    public Apostador(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.apostasApostador = new ArrayList<>();
    }

    //Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    //Método que adiciona a aposta para o apostador
    public void adicionaAposta(Aposta a){
        apostasApostador.add(a);
    }

    //Método que devolve todas as apostas do apostador
    public ArrayList<Aposta> pesquisaApostas (){
        return apostasApostador;
    }
}
