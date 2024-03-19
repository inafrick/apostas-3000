import java.util.ArrayList;
import java.util.Random;

public class Sorteio {
    private ArrayList<Integer> numerosSorteados;
    private ArrayList<Aposta> apostas;

    //Construtor da classe Sorteio
    public Sorteio() {
        this.numerosSorteados = new ArrayList<>(5);
        this.apostas = new ArrayList<>();
    }

    //Getters
    public ArrayList<Integer> getNumerosSorteados(){
        return numerosSorteados;
    }
    public ArrayList<Aposta> getApostas() {
        return apostas;
    }

    //Método que cadastra aposta
    public boolean cadastraAposta (Aposta a){
        apostas.add(a);
        a.getApostador().adicionaAposta(a);
        return true;
    }

    //Método que randomiza os números de 1 a 50 e atribui ao sorteio
    public void criaSorteio (){
        Random random = new Random();
        for (int i=0; i<5; i++){
            numerosSorteados.add(random.nextInt(1,51));
        }
    }

    //Método que pesquisa um apostador e devolve sua referência, se existir
    public Apostador pesquisaApostador(String cpf){
        for (Aposta a : apostas){
            if (a.getApostador().getCpf().equals(cpf)){
                return a.getApostador();
            }
        }
        return null;
    }

    //Retorna um valor booleano de acordo se há uma aposta sorteada ou não
    public boolean apostaSorteada() {
        return !apostasSorteadas().isEmpty();
    }

    //Cria a lista de apostas vencedoras do sorteio
    public ArrayList<Aposta> apostasSorteadas() {
        ArrayList<Aposta> apostasVencedoras = new ArrayList<>(); // Lista para armazenar as apostas vencedoras

        for (Aposta a : apostas) {
            int[] numeros = a.getNumerosApostados();
            boolean todosNumerosSorteados = true; // Variável para controlar se todos os números estão sorteados

            for (int numeroApostado : numeros) {
                boolean numeroSorteado = false; // Variável para controlar se o número atual está sorteado

                for (int numeroSorteadoAtual : numerosSorteados) {
                    if (numeroApostado == numeroSorteadoAtual) {
                        numeroSorteado = true; // Marca que o número está sorteado
                        break;
                    }
                }

                if (!numeroSorteado) {
                    todosNumerosSorteados = false;
                    break;
                }
            }

            if (todosNumerosSorteados) {
                apostasVencedoras.add(a); // Adiciona a aposta vencedora à lista de apostas vencedoras
            }
        }

        return apostasVencedoras;
    }

}
