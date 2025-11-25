import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class moodMapAnalyzer {

    private static String nomeFicheiro;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);
        System.out.print("Introduza o nome do ficheiro: ");
        nomeFicheiro = input.nextLine();
        Input();
        criarOutput();
    }

    public static void Input() throws FileNotFoundException {

        File file = new File("C:\\Users\\rmvmq\\OneDrive\\Desktop\\Uni\\APROG\\Trabalho pratico\\" + nomeFicheiro);
        Scanner ficheiro = new Scanner(new File("Input.txt"));

        String descricao = "";
        int numPessoas = 0;
        int numDias = 0;
        int[][] moods = null;

        descricao = ficheiro.nextLine();
        String[] numeros = ficheiro.nextLine().split(" ");
        numPessoas = Integer.parseInt(numeros[0]);
        numDias = Integer.parseInt(numeros[1]);
        moods = new int[numPessoas][numDias];

        ficheiro.close();
    }

    public static void tabelaMoods(int[][] moods, int numPessoas, int numDias) {                 // b)
        System.out.println("b) Mood (nivel/dia(pessoa)");
        
        System.out.print("dia       : ");
        for (int dia = 0; dia < numDias; dia++) {
            System.out.printf("%3d ", dia);
        }
        System.out.println();

        System.out.print("----------|");
        for (int dia = 0; dia < numDias; dia++) {
            System.out.print("---|");
        }
        System.out.println();
        
        for (int pessoa = 0; pessoa < numPessoas; pessoa++) {
            System.out.printf("Pessoa #%d : ", pessoa);
            for (int dia = 0; dia < numDias; dia++) {
                System.out.printf("%3d ", moods[pessoa][dia]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void mediaDiaria(int[][] moods, int numPessoas, int numDias) {                // c)
        System.out.println("c) Média diária dos moods:");
        double[] mediaDiaria = new double[numDias];

        for (int dia = 0; dia < numDias; dia++) {
            int soma = 0;
            for (int pessoa = 0; pessoa < numPessoas; pessoa++) {
                soma += moods[pessoa][dia];
            }
            mediaDiaria[dia] = (double) soma / numPessoas;

            System.out.print("dia       : ");
            for (dia = 0; dia < numDias; dia++) {
                System.out.printf("%3d ", dia);
            }
            System.out.println();

            System.out.print("----------|");
            for (dia = 0; dia < numDias; dia++) {
                System.out.print("---|");
            }
            System.out.println();

            System.out.print("mood      ");
            for (dia = 0; dia < numDias; dia++) {
                System.out.printf("%5.1f", mediaDiaria[dia]);
            }
            System.out.println("%n%n");
        }
    }

    public static void mediasPessoais(int[][] moods, int numPessoas, int numDias) {              // d)

    }

    public static void melhoresDias(int[][] moods, int numPessoas, int numDias) {                // e)
        double[] mediaDiaria = new double[numDias];

        for (int dia = 0; dia < numDias; dia++) {
            int soma = 0;
            for (int person = 0; person < numPessoas; person++) {
                soma = soma + moods[person][dia];
            }
            mediaDiaria[dia] = (double) soma / numPessoas;
        }

        double maior = mediaDiaria[0];
        for (int day = 1; day < numDias; day++) {
            if (mediaDiaria[day] > maior) {
                maior = mediaDiaria[day];
            }
        }

        System.out.printf("e) Dias com melhor média de mood (%.1f) : ", maior);
        for (int dia = 0; dia < numDias; dia++) {
            if (mediaDiaria[dia] == maior) {
                System.out.print(" " + dia);
            }
        }
        System.out.println("\n");
    }

    public static void percentagensMood(int[][] moods, int numPessoas, int numDias) {           // f)

    }

    public static void problemasEmocionais(int[][] moods, int numPessoas, int numDias) {        // g)
        System.out.println("g) Pessoas com problemas emocionais:");
        boolean existe = false;

        for (int pessoa = 0; pessoa < numPessoas; pessoa++) {
            int maiorSequencia = 0;
            int sequencia = 0;

            for (int dia = 0; dia < numDias; dia++) {

                if (moods[pessoa][dia] < 3) {

                    sequencia = sequencia + 1;
                    if (sequencia > maiorSequencia) {

                        maiorSequencia = sequencia;
                    }
                } else {
                    sequencia = 0;
                }
            }

            if (maiorSequencia >= 2) {
                System.out.printf("Pessoa #%d  : %d dias consecutivos%n", pessoa, maiorSequencia);
                existe = true;
            }
        }

        if (existe == false) {
            System.out.println("Ninguém");
        }
        System.out.println();
    }

    public static void graficosMoods(int[][] moods, int numPessoas, int numDias) {               // h)

    }

    public static void recomendacoesTerapia(int[][] moods, int numPessoas, int numDias) {       // i)
        System.out.println("i) Recomenda-se Terapia:");

        for (int pessoa = 0; pessoa < numPessoas; pessoa++) {
            // Count longest streak of low mood days
            int maiorSequencia = 0;
            int sequencia = 0;

            for (int dia = 0; dia < numDias; dia++) {
                if (moods[pessoa][dia] < 3) {
                    sequencia = sequencia + 1;
                    if (sequencia > maiorSequencia) {
                        maiorSequencia = sequencia;
                    }
                } else {
                    sequencia = 0;
                }
            }

            if (maiorSequencia >= 5) {
                System.out.printf("Pessoa #%d  : suporte psicológico%n", pessoa);
            } else if (maiorSequencia >= 2) {
                System.out.printf("Pessoa #%d  : ouvir música%n", pessoa);
            }
        }
        System.out.println();
    }

    public static void pessoasSemelhantes(int[][] moods, int numPessoas, int numDias) {         // j)

    }

    public static void criarOutput(int numPessoas, int numDias, int[][] moods, String[] numeros) throws FileNotFoundException {

        String nomeOutput = nomeFicheiro.replace("Input", "") + "Output.txt";
        File ficheiroOutput = new File(nomeOutput);
        PrintWriter writer = new PrintWriter(ficheiroOutput);

        tabelaMoods(moods, numPessoas, numDias);
        mediaDiaria(moods, numPessoas, numDias);
        mediasPessoais(moods, numPessoas, numDias);
        melhoresDias(moods, numPessoas, numDias);
        percentagensMood(moods, numPessoas, numDias);
        problemasEmocionais(moods, numPessoas, numDias);
        graficosMoods(moods, numPessoas, numDias);
        recomendacoesTerapia(moods, numPessoas, numDias);
        pessoasSemelhantes(moods, numPessoas, numDias);

        writer.close();
    }
}
