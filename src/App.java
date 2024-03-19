import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class App {
    private Scanner scanner;
    private Sorteio sorteio;
    private int rgAp;

    //Construtor da classe App
    public App() {
        scanner = new Scanner(System.in);
        rgAp = 1000;
        sorteio = new Sorteio();
    }

    //Método que executa o menu principal do programa
    public void executa() {
        int op;
        boolean flag = true;
        System.out.print("------------------------------------------------------------\n");
        System.out.println("Bem-vindo ao Apostas3000, seu método fácil de ganhar dinheiro!");
        while (flag) {
            System.out.println("O que você deseja fazer? Digite um número.\n");
            System.out.println("[1] Cadastrar aposta");
            System.out.println("[2] Lista de apostas");
            System.out.println("[3] Finalizar apostas e iniciar sorteio");
            System.out.println("[4] Finalizar o programa");
            op = scanner.nextInt();

            switch (op) {
                case 1:
                    cadastraApostasMenu(); //Método que cadastra as apostas
                    break;

                case 2:
                    if (sorteio.getApostas().isEmpty()){ //Verifica se existe alguma aposta cadastrada
                        System.out.println("------------------------------------------------------------");
                        System.out.println("Nenhuma aposta cadastrada ainda. Tente novamente...");
                        System.out.println("------------------------------------------------------------");
                    }
                    else {
                        listaApostas(); //Se houver, lista elas no console para o usuário
                    }
                    break;

                case 3:
                    if (sorteio.getApostas().isEmpty()){
                        System.out.println("------------------------------------------------------------");
                        System.out.println("Nenhuma aposta cadastrada ainda. Tente novamente...");
                        System.out.println("------------------------------------------------------------");
                    }
                    else {
                        sorteio(); //Sorteia os números
                        flag = false;
                    }
                    break;

                case 4: //Finaliza o programa
                    System.out.println("Finalizando o Apostas3000...");
                    flag = false;
                    break;

                default: //Retorna uma mensagem caso o usuário digite uma opção que não consta no menu
                    System.out.println("Opção inexistente. Tente inserir um número válido.");
                    break;
            }
        }
    }

    private void cadastraApostasMenu() {
        int op = 0;
        int[] numeros;
        System.out.println("------------------------------------------------------------\n");
        System.out.println("Façam suas apostas!");
        while (op != 3) {
            scanner.nextLine();
            System.out.println("Insira o nome: ");
            String nome = scanner.nextLine();
            while (nome.isEmpty()) {
                System.out.println("Erro! Nome inválido. Insira novamente.");
                nome = scanner.nextLine();
            }
            System.out.println("Insira o CPF: ");
            String cpf = scanner.nextLine();
            while (cpf.isEmpty()) {
                System.out.println("Erro! CPF inválido. Insira novamente.");
                cpf = scanner.nextLine();
            }
            Apostador ap = sorteio.pesquisaApostador(cpf);
            if (ap == null) {
                ap = new Apostador(nome, cpf);
            }
            System.out.println("Olá, " + nome + "! Você deseja escolher seus números ou tirar " +
                    "a sorte grande na aposta surpresinha (escolhemos os cinco " +
                    "números para você)?");
            System.out.println("[1] Aposta manual");
            System.out.println("[2] Aposta surpresinha");
            System.out.println("[3] Voltar");
            op = scanner.nextInt();
            switch (op) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Insira cinco números de 1 a 50: ");
                    numeros = new int[5];
                    int i = 0;
                    while (i < 5) {
                        int numero = scanner.nextInt();
                        if (numero >= 1 && numero <= 50) {
                            numeros[i] = numero;
                            i++;
                        } else {
                            System.out.println("Erro! Insira um número válido.");
                        }
                    }
                    Aposta aposta = new Aposta(ap, numeros, rgAp);
                    if (sorteio.cadastraAposta(aposta)) {
                        rgAp++;
                        System.out.println("Aposta cadastrada com sucesso!");
                        System.out.println("Seus números são:");
                        for (i = 0; i < numeros.length; i++) {
                            System.out.print(" " + numeros[i]);
                        }
                        System.out.println("\nSeu registro de aposta: " + aposta.getRegistroAposta());
                    } else {
                        System.out.println("Erro para cadastrar aposta. Tente novamente.");
                    }
                    break;
                case 2:
                    numeros = new int[5];
                    Random random = new Random();
                    System.out.println("Criando uma aposta para você...");
                    System.out.print("Seus números são:");
                    for (i = 0; i < numeros.length; i++) {
                        numeros[i] = random.nextInt(1, 51);
                        System.out.print(" " + numeros[i]);
                    }
                    Aposta aposta1 = new Aposta(new Apostador(nome, cpf), numeros, rgAp);
                    System.out.println("\nSeu registro de aposta: " + rgAp);
                    if (sorteio.cadastraAposta(aposta1)) {
                        rgAp++;
                        System.out.println("\nAposta cadastrada com sucesso!");
                    } else {
                        System.out.println("\nErro para cadastrar aposta. Tente novamente.");
                    }
                    break;
                case 3:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inexistente. Tente inserir um número válido.");
                    break;
            }
            System.out.println("Você deseja retornar para o menu principal?");
            System.out.println("Digite [3] se sim, digite qualquer outro número se não.");
            op = scanner.nextInt();
        }
    }

    private void listaApostas() {
        System.out.println("------------------------------------------------------------");
        ArrayList<Aposta> apostas = sorteio.getApostas();
        for (Aposta a : apostas) {
            int[] numeros = a.getNumerosApostados();
            System.out.print("\nNome: " + a.getApostador().getNome() + " - CPF: " +
                    a.getApostador().getCpf() + " - Registro de aposta: " + a.getRegistroAposta());
            System.out.print("\nNúmeros:");
            for (int n : numeros) {
                System.out.print(" " + n);
            }
        }
        System.out.println("\n------------------------------------------------------------");
    }

    private void sorteio() {
        int op;
        boolean flag2 = true;

        int index = 0;
        Random random = new Random();
        sorteio.criaSorteio();

        int novoNumero = random.nextInt(1, 51);
        while (!sorteio.apostaSorteada()) {
            if (index == 24) {
                break;
            }
            boolean flag = true;
            while (flag) {
                for (int n : sorteio.getNumerosSorteados()) {
                    if (novoNumero == n) {
                        novoNumero = random.nextInt(1, 51);
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
            index++;
            sorteio.getNumerosSorteados().add(novoNumero);
        }

        if (index == 25) {
            System.out.println("Não houveram vencedores.");
            System.out.println("Lista de números sorteados:");
            for (int n : sorteio.getNumerosSorteados()) {
                System.out.print(" " + n);
            }
            System.out.println("Número de rodadas: " + (sorteio.getNumerosSorteados().size() - 4));
        } else {

            System.out.println("Números sorteados!");
            System.out.println("Deseja passar para a fase de apuração?");
            System.out.println("[0] Não");
            System.out.println("[1] Sim");
            op = scanner.nextInt();
            while (flag2) {
                switch (op) {
                    case 0:
                        System.out.println("Finalizando o Apostas3000...");
                        flag2 = false;
                        break;
                    case 1:
                        apuracao();
                        flag2 = false;
                        break;
                    default:
                        System.out.println("Insira uma opção válida.");
                }
            }
        }
    }

    private void apuracao() {
        System.out.println("------------------------------------------------------------\n");
        System.out.println("[Iniciando apuração...]");
        System.out.print("Lista de números sorteados:");
        for (int n : sorteio.getNumerosSorteados()) {
            System.out.print(" " + n);
        }
        System.out.println("\n------------------------------------------------------------");
        System.out.println("Número de rodadas: " + (sorteio.getNumerosSorteados().size() - 4));
        System.out.println("------------------------------------------------------------");
        System.out.println("Quantidade de apostas vencedoras: " + sorteio.apostasSorteadas().size());
        System.out.println("------------------------------------------------------------");
        System.out.println("Lista de apostas vencedoras:");
        if (sorteio.apostasSorteadas().isEmpty()){
            System.out.println("Nenhuma aposta vencedora.");
        }
        else {
            for (Aposta a : sorteio.apostasSorteadas()) {
                int[] numeros = a.getNumerosApostados();
                System.out.print("Nome: " + a.getApostador().getNome() + " - CPF: " +
                        a.getApostador().getCpf() + " - Registro de aposta: " + a.getRegistroAposta() + "\n");
                System.out.print("Números:");
                for (int n : numeros) {
                    System.out.print(" " + n);
                }
            }
        }
        System.out.println("\nGame Over! Obrigado por jogar.");
        System.out.println("Feito por: Inácio Pimentel");
    }

    // Método para ordenar apostas vencedoras pelo nome do apostador utilizando compare
    public void ordenarApostasVencedorasPorNome() {
        ArrayList<Aposta> apostasVencedoras = sorteio.apostasSorteadas();

        Comparator<Aposta> comparador = new Comparator<Aposta>() {
            @Override
            public int compare(Aposta aposta1, Aposta aposta2) {
                return aposta1.getApostador().getNome().compareToIgnoreCase(aposta2.getApostador().getNome());
            }
        };
    }
}
